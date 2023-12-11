import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class P2PFileTransfer {
    private final int PORT = 44305;
    private final FileReceiver fileReceiver = new FileReceiver();
    private final FileSender fileSender = new FileSender();
    private class FileSender {
        public void send(String address, Path path) throws IOException {
            try (
                    InputStream is = Files.newInputStream(path);
                    Socket connection = new Socket(address, PORT);
                    DataOutputStream dos = new DataOutputStream(connection.getOutputStream())
            ) {
                dos.writeUTF(path.getFileName().toString());
                dos.writeLong(Files.size(path));
                IOUtils.copy(is, dos, 4096);
            }
        }
    }

    private class FileReceiver {
        public void receive(Socket clientSocket) throws IOException {
            try (
                    DataInputStream dis = new DataInputStream(clientSocket.getInputStream())
            ) {
                Path filePath = Path.of("received", dis.readUTF());
                long fileSize = dis.readLong();
                byte[] buffer = new byte[4096];
                try (OutputStream os = Files.newOutputStream(filePath)) {
                    int bytes = 0;
                    while (fileSize > 0 && (bytes = dis.read(buffer, 0, (int)Math.min(buffer.length, fileSize))) != -1) {
                        os.write(buffer, 0 , bytes);
                        fileSize -= bytes;
                    }
                }
            }
        }
    }


    public P2PFileTransfer() throws IOException {

    }

    public void start() throws IOException {
        while (true) {
            System.out.println("New init");
            init();
        }
    }

    public void init() throws IOException {
        try (
                ServerSocket serverSocket = new ServerSocket(PORT);
                Socket client = serverSocket.accept()
        ) {
            fileReceiver.receive(client);
        }
    }

    public void send(String address, Path path) throws IOException {
        fileSender.send(address, path);
    }
}
