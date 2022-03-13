package nagi.java.nio.channel.socketchannel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelReceiveDemo {
    public static void main(String[] args) throws Exception {
        DatagramChannel datagramChannel = DatagramChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8888);
        datagramChannel.bind(inetSocketAddress);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            byteBuffer.clear();
            datagramChannel.receive(byteBuffer);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.println((char)byteBuffer.get());
            }
        }

    }
}
