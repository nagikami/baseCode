package nagi.netty.inboundandoutbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("server ip: " + ctx.channel().remoteAddress());
        System.out.println("message from server: " + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler invoked");
        ctx.writeAndFlush(23456L);
        //ctx.writeAndFlush("abab"); // 消息类型不匹配，跳过处理器
    }
}
