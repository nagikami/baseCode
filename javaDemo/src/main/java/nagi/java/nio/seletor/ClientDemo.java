package nagi.java.nio.seletor;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ClientDemo {
    public static void main(String[] args) throws Exception {
        //get channel, bind port
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));

        //switch to non blocking
        socketChannel.configureBlocking(false);

        //create buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            //write data to buffer
            byteBuffer.put(scanner.nextLine().getBytes());

            //switch write mode to read
            byteBuffer.flip();

            //write data to channel from buffer
            socketChannel.write(byteBuffer);

            byteBuffer.clear();
        }
        //close channel
        socketChannel.close();
    }
}
