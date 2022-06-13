package nagi.netty.inboundAndoutBound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class MyByteLongEncoder extends MessageToByteEncoder<Long> {
    // 编码方法
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyByteLongEncoder invoked");
        System.out.println("message: " + msg);
        out.writeLong(msg);
    }
}
