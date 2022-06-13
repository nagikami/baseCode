package nagi.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("\n\n\nmessage from client: length: " + length + " message: " + new String(content,StandardCharsets.UTF_8));
        System.out.println("server receive count: " + (++this.count));


        String responseContent = UUID.randomUUID().toString();
        int responseLength = responseContent.length();
        byte[] responseContentBytes = responseContent.getBytes(StandardCharsets.UTF_8);
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLength(responseLength);
        messageProtocol.setContent(responseContentBytes);

        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active");
    }
}
