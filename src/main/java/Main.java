import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            P2PFileTransfer p2PFileTransfer = new P2PFileTransfer();
            Thread serverThread = new Thread(() -> {
                try {
                    p2PFileTransfer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
//            Thread clientThread = new Thread(() -> {
//                try {
//                    p2pClient.send("192.168.1.139", "hola".getBytes(StandardCharsets.UTF_8));
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });

            serverThread.start();
//            clientThread.start();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
