package nagi.java.nio.channel.filechannel;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelTransferFromDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile("e:\\testData\\01.txt", "rw");
        FileChannel fromChannel = accessFile.getChannel();

        RandomAccessFile accessFile1 = new RandomAccessFile("e:\\testData\\02.txt", "rw");
        FileChannel toChannel = accessFile1.getChannel();

        int position = 0;
        long size = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, size);

        fromChannel.close();
        toChannel.close();

    }
}
