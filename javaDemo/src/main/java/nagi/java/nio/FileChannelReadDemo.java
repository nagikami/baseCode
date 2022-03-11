package nagi.java.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelReadDemo {
    public static void main(String[] args) throws Exception {
        //打开一个文件 获取fd(文件描述符)
        RandomAccessFile accessFile = new RandomAccessFile("e:\\testData\\01.txt", "rw");
        //create file channel
        FileChannel channel = accessFile.getChannel();

        //create buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //read data from channel to buffer
        int read = channel.read(byteBuffer);
        while (read != -1) {
            System.out.println("read " + read);
            //mode change write -> read
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.println((char)byteBuffer.get());
            }
            byteBuffer.clear();
            read = channel.read(byteBuffer);
        }
        accessFile.close();
        System.out.println("end");
    }
}
