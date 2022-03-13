package nagi.java.nio.buffer;

import java.nio.ByteBuffer;

public class ReadOnlyBufferDemo {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte)i);
        }

        //create readOnlyBuffer as view
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        for (int i = 3; i < 5; i++) {
            byte b = byteBuffer.get(i);
            byteBuffer.put(i, (byte)(b * 10));
        }

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
    }
}
