package nagi.java.nio.async;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AsyncFileChannelCompletionDemo {
    public static void main(String[] args) throws Exception {
        //create channel
        final AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(Paths.get("E:\\testData\\01.txt"), StandardOpenOption.READ);
        final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //create future task with callback, attachment 附加缓冲区，用于在回调中处理指定数据
        asynchronousFileChannel.read(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            //invoked on read completed or buffer if full
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result " + result);
                attachment.flip();
                System.out.println(new String(attachment.array(), 0, attachment.limit()));
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
                attachment.flip();
                System.out.println(new String(attachment.array(), 0, attachment.limit()));
            }
        });

        Thread.sleep(10000);
        asynchronousFileChannel.close();
    }
}
