package nagi.java.nio.chat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ChatClient {

    public void startClient() throws Exception {
        //create channel
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.01", 9999));

        Scanner scanner = new Scanner(System.in);

        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        new Thread(new EventListener(selector)).start();

        //send message
        while (scanner.hasNext()) {
            String message = scanner.nextLine();

            if (message.length() > 0) {
                socketChannel.write(Charset.forName(StandardCharsets.UTF_8.toString()).encode("client1: " + message));
            }
        }
    }

    public static void main(String[] args) {
        try {
            new ChatClient().startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class EventListener implements Runnable{
        private Selector selector;

        public EventListener(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {
            try {
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

                        if (selectionKey.isReadable()) {
                            readOperator(selectionKey, selector);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            System.out.println(message);
            //register again
            socketChannel.register(selector, SelectionKey.OP_READ);
        }
    }
}
