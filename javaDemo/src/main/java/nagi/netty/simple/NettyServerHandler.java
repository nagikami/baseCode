package nagi.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 自定义一个Handler需要继续netty规定好的某个HandlerAdapter(模板)
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 取数据实际(这里我们可以读取客户端发送的消息)
     * @param ctx 上下文对象,含有管道pipeline,通道channel,地址
     * @param msg 就是客户端发送的数据 默认Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 有一个非常耗时长的业务-> 异步执行 -> 提交任务至该channel对应的NIOEventLoop的taskQueue中

        // 解决方案1 用户程序自定义的普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("current thread: " + Thread.currentThread().getName());
                System.out.println("task 1");
            }
        }); // 获取当前处理线程，提交任务至任务队列

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("current thread: " + Thread.currentThread().getName());
                System.out.println("task 2");
            }
        }); // 两个任务提交至同一线程，顺序执行

        // 解决方案2:用户自定义定时任务->该任务是提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("current thread: " + Thread.currentThread().getName());
                System.out.println("task 3");
            }
        }, 2, TimeUnit.SECONDS);

        System.out.println("current thread: " + Thread.currentThread().getName() + " socket channel: " + ctx.channel());
        System.out.println("Current Context: " + ctx);
        Channel channel = ctx.channel(); // 与pipeline相互引用
        ChannelPipeline pipeline = ctx.pipeline(); // 双向链表

        // 将msg转成一个ByteBuf，ByteBuf是Netty提供的，不是NIO的ByteBuffer
        ByteBuf message = (ByteBuf) msg;
        System.out.println("Message from client: " + message.toString(StandardCharsets.UTF_8));
    }

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
