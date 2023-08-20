package br.com.remote.server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class CaptureScreen implements Runnable {
    private static final String IMG_EXTENSION = "jpeg";
    private final Socket socket;
    private final int width;
    private final int height;


    public CaptureScreen(Socket socket, int width, int height) {
        this.socket = socket;
        this.width = width;
        this.height = height;
    }

    private void sendImage(OutputStream outputStream, BufferedImage image) throws IOException {
        outputStream.write(imageToByteArray(image));
    }

    private byte[] imageToByteArray(BufferedImage image) throws IOException {
        var byteArrayOut = new ByteArrayOutputStream();
        ImageIO.write(image, IMG_EXTENSION, byteArrayOut);
        return byteArrayOut.toByteArray();
    }

    private BufferedImage capture() throws AWTException {
        return new Robot().createScreenCapture(new Rectangle(width, height));
    }


    @Override
    public void run() {

         try {
             while (true) {
                 var outPut = socket.getOutputStream();
                 var image = capture();
                 sendImage(outPut, image);
             }
         } catch (IOException e) {
             throw new RuntimeException(e);

         } catch (AWTException e) {
             System.err.println("Erro ao capturar tela");
         }

    }
}
