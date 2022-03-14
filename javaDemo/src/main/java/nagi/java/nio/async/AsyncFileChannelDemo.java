package nagi.java.nio.async;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsyncFileChannelDemo {
    public static void main(String[] args) throws Exception {
        //create channel
        AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(Paths.get("E:\\testData\\01.txt"), StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //create future task
        Future<Integer> read = asynchronousFileChannel.read(byteBuffer, 0);

        //rolling task state
        while (!read.isDone()) Thread.sleep(1000);

        //read result from buffer
        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
        asynchronousFileChannel.close();
    }
}
