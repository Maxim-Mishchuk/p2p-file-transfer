import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class P2PClient {
    private final int PORT = 44305;

    public P2PClient() throws IOException {

    }

    public void init() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT);) {
            Socket client = serverSocket.accept();
            byte[] data = IOUtils.toByteArray(client.getInputStream());
            System.out.println(IOUtils.toString(data, "UTF-8"));
        }
    }

    public void send(String data, String address) throws IOException {
        try (Socket socket = new Socket(address, PORT)) {
            IOUtils.write(data.getBytes(StandardCharsets.UTF_8), socket.getOutputStream());
        }
    }
}
