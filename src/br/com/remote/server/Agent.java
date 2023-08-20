package br.com.remote.server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class Agent {

    private static final int PORT = 3322;

    public static void main(String[] args) {

        try {
            final ServerSocket socket = new ServerSocket(PORT);
            var client = socket.accept();
            System.out.println("Conexão estabelecida com " + client.getInetAddress());
            
            while (true) {
                var outPut = client.getOutputStream();
                var image = capture(1980, 1080);
                sendImage(outPut, image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendImage(OutputStream outputStream, BufferedImage image) throws IOException {
        outputStream.write(imageToByteArray(image));
        System.out.println("Enviando Imagem..");
    }

    private static byte[] imageToByteArray(BufferedImage image) throws IOException {
        var byteArrayOut = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", byteArrayOut);
        return byteArrayOut.toByteArray();
    }

    private static BufferedImage capture(int width, int height) throws AWTException {
        return new Robot().createScreenCapture(new Rectangle(width, height));
    }
}
