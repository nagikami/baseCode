package nagi.java.nio.channel.socketchannel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
    public static void main(String[] args) throws Exception {
        //create channel and invoke connect
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));

        //config blocking
        socketChannel.configureBlocking(true);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int read = socketChannel.read(buffer);
        while (read != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char)buffer.get());
            }
            buffer.clear();
            read = socketChannel.read(buffer);
        }
        socketChannel.close();
    }
}
