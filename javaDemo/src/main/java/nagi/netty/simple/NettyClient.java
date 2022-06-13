package nagi.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws Exception {
        // 客户端需要一个事件循环组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建客户端启动对象，注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup) //设置线程组
                    .channel(NioSocketChannel.class) // 设置客户端通道的实现类(反射)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler()); // 加入自己的处理器
                        }
                    });

            System.out.println("client is ready");

            // 启动客户端去连接服务器端，ChannelFuture netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).sync();
            // 给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
