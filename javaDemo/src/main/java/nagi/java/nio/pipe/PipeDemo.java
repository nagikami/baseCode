package nagi.java.nio.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeDemo {
    public static void main(String[] args) throws Exception {
        //create pipe, underlying on socket channel
        Pipe pipe = Pipe.open();
        //get sink
        Pipe.SinkChannel sink = pipe.sink();

        ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);
        byteBuffer1.put("hello".getBytes());

        byteBuffer1.flip();
        //writer data to sink from buffer1
        sink.write(byteBuffer1);

        //get source
        Pipe.SourceChannel source = pipe.source();

        ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);

        //read data from source to buffer2
        source.read(byteBuffer2);

        byteBuffer1.flip();
        System.out.println(new String(byteBuffer2.array(), 0, byteBuffer2.limit()));
    }
}
