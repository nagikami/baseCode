package nagi.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

public class NettyServer {
    public static void main(String[] args) {
        /**
         * 创建两个线程组bossGroup和workerGroup
         * bossGroup 只是处理连接请求（accept） , 真正的和客户端业务处理（read、write），会交给 workerGroup完成
         * 两个都是无限循环
         * bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数为默认实际 cpu核数 * 2
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();

        } finally {

        }
    }
}
