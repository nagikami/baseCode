package nagi.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * 1.SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * 2.HttpObject客户端和服务器端相互通讯的数据被封装成HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    //channelRead0 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("channel: " + ctx.channel() + "\npipeline: " + ctx.pipeline() + "\nchannel from pipeline: " + ctx.pipeline().channel());
        System.out.println("handler: " + ctx.handler());

        //判断msg是不是httpRequest请求
        if (msg instanceof HttpRequest) {
            System.out.println("type of ctx: " + ctx.getClass());
            System.out.println("pipeline hashcode: " + ctx.pipeline().hashCode());
            System.out.println("TestHttpServerHandler: " + this.hashCode());
            System.out.println("type of msg: " + msg.getClass());
            System.out.println("address of client: " + ctx.channel().remoteAddress());

            // 获取http请求
            HttpRequest message = (HttpRequest) msg;
            URI uri = new URI(message.uri());
            //获取uri,过滤指定的资源
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("no response");
                return;
            }

            //回复信息给浏览器[http协议]
            ByteBuf responseMessage = Unpooled.copiedBuffer("hello client", StandardCharsets.UTF_8);

            //构造一个http的响应，即httpResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, responseMessage);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, responseMessage.readableBytes());

            //将构建好 response返回
            ctx.writeAndFlush(response);
        }
    }
}
