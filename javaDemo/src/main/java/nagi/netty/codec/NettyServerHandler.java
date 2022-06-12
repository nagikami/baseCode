package nagi.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 自定义一个Handler需要继续netty规定好的某个HandlerAdapter(模板)
 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        // 读取从客户端发送的StudentPojo.Student
        System.out.println("message from client: id: " + msg.getId() + " name: " + msg.getName());
    }

//    /**
//     * 读取数据(这里可以读取客户端发送的消息)
//     * @param ctx 上下文对象,含有管道pipeline,通道channel,地址
//     * @param msg 就是客户端发送的数据 默认Object
//     * @throws Exception
//     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        StudentPOJO.Student message = (StudentPOJO.Student) msg;
//        // 读取从客户端发送的StudentPojo.Student
//        System.out.println("message from client: id: " + message.getId() + " name: " + message.getName());
//    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**
         * writeAndFlush是write + flush
         * 将数据写入到缓存，并刷新，通常对这个发送的数据进行编码
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client", StandardCharsets.UTF_8));
    }

    /**
     *处理异常, 一般是需要关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
