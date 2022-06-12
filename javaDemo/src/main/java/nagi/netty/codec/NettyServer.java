package nagi.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class NettyServer {
    public static void main(String[] args) throws Exception {
        /**
         * 创建两个线程组bossGroup和workerGroup
         * bossGroup只是处理连接请求（accept），真正的和客户端业务处理（read，write），会交给workerGroup完成
         * 两个都是无限循环
         * bossGroup和workerGroup含有的子线程(NioEventLoop)的个数默认为实际cpu核数 * 2
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel作为服务端监听的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置服务器连接等待队列大小（bossGroup）
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态（workerGroup）
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象(匿名对象)
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 在pipeline加入ProtoBufDecoder,指定对哪种对象进行解码
                            pipeline.addLast("decoder", new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    }); // 给workerGroup的EventLoop对应的管道设置处理器

            System.out.println("server is ready");

            //启动服务器(并绑定端口) 绑定一个端口并且同步, 生成了一个 ChannelFuture 对象
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();

            //给channelFuture注册监听器，监控我们关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("Listen port successfully");
                    } else {
                        System.out.println("Listen port failed");
                    }
                }
            });

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
