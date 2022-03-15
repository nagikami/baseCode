package nagi.java.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {
    public void startServer() throws Exception {
        //create selector
        Selector selector = Selector.open();

        //create ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //bind port
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);

        //register
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //loop, wait new connection
        for(;;) {
            //get ready operations
            selector.select();

            //get selected-key set of this selector
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            //dispatch event
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                //remove processed key
                iterator.remove();

                if (selectionKey.isAcceptable()) {
                    acceptOperator(serverSocketChannel, selector);
                }

                if (selectionKey.isReadable()) {
                    readOperator(selectionKey, selector);
                }
            }
        }

    }

    public void acceptOperator(ServerSocketChannel serverSocketChannel, Selector selector) throws Exception {
        //crate socketChannel made of connection
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        //register
        socketChannel.register(selector, SelectionKey.OP_READ);

        //reply info to client
        socketChannel.write(Charset.forName(StandardCharsets.UTF_8.toString()).encode("WellCome to this chat room"));

    }

    public void readOperator(SelectionKey selectionKey, Selector selector) throws Exception {
        //get ready channel
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //read message
        int read = socketChannel.read(byteBuffer);
        StringBuilder message = new StringBuilder();
        while (read > 0) {
            byteBuffer.flip();
            message.append(Charset.forName(StandardCharsets.UTF_8.toString()).decode(byteBuffer));
            byteBuffer.clear();
            read = socketChannel.read(byteBuffer);
        }

        //broadcast message
        if (message.length() > 0) {
            broadCast(selector, socketChannel, message);
        }
    }

    private void broadCast(Selector selector, SocketChannel socketChannel, StringBuilder message) throws IOException {
        //get all registered key of channel
        Set<SelectionKey> selectionKeys = selector.keys();

        //broadcast message, except current channel
        for (SelectionKey selectionKey : selectionKeys) {
            Channel channel = selectionKey.channel();
            if (channel instanceof SocketChannel && channel != socketChannel) {
                ((SocketChannel) channel).write(Charset.forName(StandardCharsets.UTF_8.toString()).encode(message.toString()));
            }
        }
    }

    public static void main(String[] args) {
        try {
            new ChatServer().startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
