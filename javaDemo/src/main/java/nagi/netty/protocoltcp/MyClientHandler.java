package nagi.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.StandardCharsets;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用客户端发送10条数据hello nagi编号，测试粘包、分包
        String message = "hello nagi";
        int length = message.length();
        byte[] content = message.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < 10; i++) {
            // 创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLength(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("message from server: length: " + length + " message: " + new String(content, StandardCharsets.UTF_8));
        System.out.println("client receive count: " + ++count);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
