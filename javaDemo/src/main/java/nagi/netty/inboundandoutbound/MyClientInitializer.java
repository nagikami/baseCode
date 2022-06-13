package nagi.netty.inboundandoutbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个出站的handler对数据进行一个编码
        pipeline.addLast(new MyByteLongEncoder());
        // 入站的解码器(入站handler )
        pipeline.addLast(new MyByteLongDecoder());
        // 加入一个自定义的handler，入站处理业务
        pipeline.addLast(new MyClientHandler());
    }
}
