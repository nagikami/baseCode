package nagi.java.nio.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharsetDemo {
    public static void main(String[] args) {
        Charset charset = Charset.forName(StandardCharsets.UTF_8.toString());

        CharBuffer charBuffer = CharBuffer.wrap("hello");

        ByteBuffer encode = charset.encode(charBuffer);
        for (int i = 0; i < charBuffer.limit(); i++) {
            System.out.println(encode.get(i));
        }

        CharBuffer decode = charset.decode(encode);
        System.out.println(new String(decode.array(), 0, decode.limit()));
    }
}
