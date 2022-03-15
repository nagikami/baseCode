package nagi.java.nio.seletor;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerDemo {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                iterator.remove();

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel severSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    //SocketChannel socketChannel = serverSocketChannel.accept();
                    SocketChannel socketChannel = severSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.write(ByteBuffer.wrap("hello".getBytes()));
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }

                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int read = socketChannel.read(byteBuffer);
                    while (read > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                        byteBuffer.clear();
                        read = socketChannel.read(byteBuffer);
                    }
                }
            }
        }
    }
}
