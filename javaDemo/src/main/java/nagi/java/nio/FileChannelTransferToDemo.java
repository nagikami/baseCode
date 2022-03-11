package nagi.java.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelTransferToDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile("e:\\testData\\01.txt", "rw");
        FileChannel fromChannel = accessFile.getChannel();

        RandomAccessFile accessFile1 = new RandomAccessFile("e:\\testData\\02.txt", "rw");
        FileChannel toChannel = accessFile1.getChannel();

        int position = 0;
        long size = fromChannel.size();

        fromChannel.transferTo(position, size, toChannel);

        fromChannel.close();
        toChannel.close();

    }
}
