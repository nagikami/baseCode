package nagi.java.nio.channel.socketchannel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {
    public static void main(String[] args) throws Exception {
        //listening port
        int port = 9999;

        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
        //only listen, does not transfer data
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);

        while (true) {
            //acquire new connection
            SocketChannel accept = serverSocketChannel.accept();
            if (accept == null) {
                System.out.println("current no connection");
                Thread.sleep(1000);
            } else {
                System.out.println("connection established from " + accept.getRemoteAddress());
                buffer.rewind(); //set pointer to 0
                while (buffer.hasRemaining()) {
                    accept.write(buffer);
                }
                accept.close();
            }
        }
    }
}
