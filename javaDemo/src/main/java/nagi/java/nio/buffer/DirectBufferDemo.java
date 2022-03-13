package nagi.java.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DirectBufferDemo {
    //直接缓冲区 用户空间地址和内核空间地址映射到相同的物理地址
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("D:\\testData\\01.txt");
        FileChannel channel1 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\testData\\02.txt");
        FileChannel channel2 = fileOutputStream.getChannel();

        //create direct buffer by ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            byteBuffer.clear();
            int read = channel1.read(byteBuffer);
            if (read == -1) {
                break;
            }

            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                channel2.write(byteBuffer);
            }
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
