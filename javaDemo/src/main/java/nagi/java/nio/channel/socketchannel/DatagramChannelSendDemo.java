package nagi.java.nio.channel.socketchannel;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelSendDemo {
    public static void main(String[] args) throws Exception {
        DatagramChannel datagramChannel = DatagramChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8888);

        ByteBuffer byteBuffer = ByteBuffer.wrap("hello world".getBytes());

        while (true) {
            datagramChannel.send(byteBuffer, inetSocketAddress);
            byteBuffer.rewind();
            System.out.println("send over");
            Thread.sleep(1000);
        }
    }
}
