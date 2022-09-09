import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Future;

import static java.nio.file.StandardOpenOption.*;

public class AsyncFileChannelTest {
    @Test
    public void testIO() {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("E:\\data\\text.txt", "rw")) {
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            StringBuilder message = new StringBuilder();
            while (read != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    message.append(Charset.forName(StandardCharsets.UTF_8.toString()).decode(buffer));
                }
                buffer.clear();
                read = channel.read(buffer);
            }
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAsyncIOWithoutCallback() {
        // do something before I/O
        stage1();
        // from resource
        // Path path = Paths.get(URI.create(this.getClass().getResource("/test.txt").toString()));
        // from disk
        Path path = Paths.get("E:\\data\\text.txt");
        try {
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, READ, WRITE, CREATE);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Future<Integer> future = fileChannel.read(buffer, 0);
            // do something without waiting for I/O

            // wait until I/O complete
            Integer read = future.get();

            // do something after I/O
            stage2();

            // stage2 maybe
            StringBuilder message = new StringBuilder();
            buffer.flip();
            while (buffer.hasRemaining()) {
                message.append(Charset.forName(StandardCharsets.UTF_8.toString()).decode(buffer));
            }
            buffer.clear();
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAsyncIOWithCallback() {
        // do something before I/O
        stage1();
        // from resource
        // Path path = Paths.get(URI.create(this.getClass().getResource("/test.txt").toString()));
        // from disk
        Path path = Paths.get("E:\\data\\text.txt");
        try {
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, READ, WRITE, CREATE);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    // do something after I/O in different thread
                    stage2();

                    // stage2 maybe
                    System.out.println("read bytes count: " + result);
                    StringBuilder message = new StringBuilder();
                    attachment.flip();
                    while (attachment.hasRemaining()) {
                        message.append(Charset.forName(StandardCharsets.UTF_8.toString()).decode(buffer));
                    }
                    attachment.clear();
                    System.out.println(message);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    // do something
                }
            });
            // do something without waiting for I/O

            // hold main thread before stage2 completing
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stage1() {

    }

    public void stage2() {

    }
}
