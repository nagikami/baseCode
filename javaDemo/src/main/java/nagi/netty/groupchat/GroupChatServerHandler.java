package nagi.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    // 使用一个list管理
    //public static List<Channel> channels = new ArrayList<Channel>();

    // 使用一个hashmap管理（单对单聊天）
    //public static Map<String, Channel> channels = new HashMap<>();
    //public static Map<User, Channel> channelMap = new HashMap<>();

    // 定义一个channel组，管理所有的channel，GlobalEventExecutor.INSTANCE)是全局的事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // handlerAdded 表示连接建立，一旦连接，第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        /**
         * 将该客户加入聊天的信息推送给其它在线的客户端
         * 该方法会将channelGroup中所有的channel遍历，并发送消息
         */
        channelGroup.writeAndFlush("[client]" + channel.remoteAddress() + " join chat "
                + simpleDateFormat.format(new Date()) + "\n");
        // 将当前channel加入到channelGroup
        channelGroup.add(channel);
    }

    // 断开连接, 将xx客户离开信息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[client]" + channel.remoteAddress() + " leave chat "
                + simpleDateFormat.format(new Date()) + "\n");
    }

    // 表示channel处于活动状态,提示xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " online");
    }

    // 表示channel处于不活动状态,提示xx离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " offline");
    }

    // 读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        // 遍历channelGroup, 根据不同的情况，回送不同的消息
        channelGroup.forEach((ch) -> {
            if (channel != ch) { // 不是当前的channel,转发消息
                ch.writeAndFlush("[client]" + channel.remoteAddress() + " send message: " + msg + "\n");
            } else { // 回显自己发送的消息给自己
                ch.writeAndFlush("[I]send message: " + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
