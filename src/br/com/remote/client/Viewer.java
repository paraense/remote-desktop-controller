package br.com.remote.client;

import br.com.remote.windows.RemoteScreen;
import br.com.remote.windows.components.ImagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

public class Viewer {
    private static boolean IS_FIRST_IMG = true;
    private static RemoteScreen screen = null;
    private static String HOST = null;

    public static void main(String[] args) throws IOException {
        HOST = JOptionPane
                .showInputDialog("Digite o Endereço IP do computador que deseja acessar")
                .trim();

        try(var connection = new Socket(HOST, 3322)) {
            var inputStream = connection.getInputStream();
            while (true) {
                int count = 0;
                byte[] bytes = new byte[1024 * 1024];

                do {
                    count += inputStream.read(bytes, count, bytes.length - count);
                } while (!(count > 4 && bytes[count - 2] == (byte) -1 && bytes[count - 1] == (byte) -39));

                var image = getImage(bytes);
                renderImage(image);
            }
        }
    }


    private static void renderImage(BufferedImage image) {
        if (IS_FIRST_IMG) {
            screen = new RemoteScreen(1366, 780, new ImagePanel(image), HOST);
            IS_FIRST_IMG = false;
        } else {
            screen.changeImage(new ImagePanel(image));
        }
    }

    private static BufferedImage getImage(byte[] bytes) throws IOException {
        var inputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(inputStream);
    }

}
