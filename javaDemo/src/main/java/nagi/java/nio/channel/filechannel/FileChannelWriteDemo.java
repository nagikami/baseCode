package nagi.java.nio.channel.filechannel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelWriteDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile("e:\\testData\\02.txt", "rw");
        FileChannel channel = accessFile.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.clear();

        byteBuffer.put("hello world!".getBytes());

        //mode change write -> read
        byteBuffer.flip();

        //write data from buffer to channel
        while (byteBuffer.hasRemaining()) {
            channel.write(byteBuffer);
        }
        channel.close();
    }
}
