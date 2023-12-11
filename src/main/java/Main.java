import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        P2PFileTransfer p2pFileTransfer = new P2PFileTransfer();
        p2pFileTransfer.start();
        p2pFileTransfer.send("192.168.1.90", Path.of("export", "pizza.png"));
    }
}
