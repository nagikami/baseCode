package nagi.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBuf01 {
    public static void main(String[] args) {
        /**
         *创建一个ByteBuf
         * 1. 创建对象，该对象包含一个数组arr,是一个byte[10]
         * 2. 在netty的buffer中不需要使用flip进行反转，ByteBuf底层维护了readerIndex和writerIndex
         * 3. 通过readerIndex和writerIndex和capacity，将buffer分成三个区域
         * 0---readerIndex 已经读取的区域
         * readerIndex---writerIndex，可读的区
         * writerIndex -- capacity, 可写的区域
         */
        ByteBuf byteBuf = Unpooled.buffer(10);

        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.writeByte(i));
        }

        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());
        }
    }
}
