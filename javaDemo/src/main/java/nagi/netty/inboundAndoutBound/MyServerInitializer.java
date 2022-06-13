package nagi.netty.inboundAndoutBound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        /**
         * 出入站处理器要保持相对顺序
         * 入站从head遍历处理器（encode、handler），出站从tail遍历处理器（handler、decode）
         * handler接收的消息类型必须与处理类型一致，否则不执行
         */
        ChannelPipeline pipeline = ch.pipeline();

        // 入站的handler进行解码 MyByteToLongDecoder
        pipeline.addLast(new MyByteLongDecoder());
        // 出站的handler进行编码
        pipeline.addLast(new MyByteLongEncoder());
        // 自定义的handler 处理业务逻辑
        pipeline.addLast(new MyServerHandler());
        System.out.println("MyServerInitializer invoked");
    }
}
