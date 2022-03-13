package nagi.java.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class DirectBufferWithMapDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\testData\\01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        //crate direct buffer by map of channel
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 10);

        map.put(0, (byte)'n');
        map.put(6, (byte)'k');
        randomAccessFile.close();
    }
}
