package br.com.remote.client;

import br.com.remote.windows.RemoteScreen;
import br.com.remote.windows.components.ImagePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

public class RenderingScreen  implements Runnable {
    private static boolean IS_FIRST_IMG = true;
    private static RemoteScreen screen = null;
    private final Socket socket;
    private final String hostName;

    public RenderingScreen(Socket socket, String hostName) {
        this.socket = socket;
        this.hostName = hostName;
    }

    private  void renderImage(BufferedImage image) {
        if (IS_FIRST_IMG) {
            screen = new RemoteScreen(1366, 780, new ImagePanel(image), hostName);
            IS_FIRST_IMG = false;
        } else {
            screen.changeImage(new ImagePanel(image));
        }
    }

    private BufferedImage getImage(byte[] bytes) throws IOException {
        var inputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(inputStream);
    }
    @Override
    public void run() {

        try {
           var inputStream = socket.getInputStream();

           while (true) {
                int count = 0;
                byte[] bytes = new byte[1024 * 1024];

                do {
                    count += inputStream.read(bytes, count, bytes.length - count);
                } while (!(count > 4 && bytes[count - 2] == (byte) -1 && bytes[count - 1] == (byte) -39));

                var image = getImage(bytes);
                renderImage(image);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
