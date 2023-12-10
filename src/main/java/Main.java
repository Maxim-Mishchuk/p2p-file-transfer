import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            P2PClient p2pClient = new P2PClient();
            Thread serverThread = new Thread(() -> {
                try {
                    p2pClient.init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Thread clientThread = new Thread(() -> {
                try {
                    p2pClient.send("hello", "192.168.1.139");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            serverThread.start();
            Thread.sleep(1000);
            clientThread.start();
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
