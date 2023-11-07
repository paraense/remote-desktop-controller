package br.com.remote.client;

import br.com.remote.client.windows.RemoteScreen;
import br.com.remote.client.windows.components.ImagePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

public class RenderingScreen  implements Runnable {
    private static RemoteScreen screen = null;
    private Socket socket;
    private final String hostName;
    private final int width;
    private final int height;

    public RenderingScreen(Socket socket, String hostName, int width, int height) {
        this.socket = socket;
        this.hostName = hostName;
        this.width = width;
        this.height = height;
    }

    private  void renderImage(BufferedImage image) {
        if (screen == null) {
            screen = new RemoteScreen(width, height, new ImagePanel(image), hostName);
        } else {
            screen.changeImage(image);
        }
    }

    private BufferedImage getImage(byte[] bytes) throws IOException {
        var inputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(inputStream);
    }


    public static RemoteScreen getScreen() {
        return screen;
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
