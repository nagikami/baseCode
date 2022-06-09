package nagi.java.bio;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BioClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 9999), 1000);
        try (OutputStream outputStream = socket.getOutputStream()) {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                outputStream.write(scanner.nextLine().getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
