package br.com.remote.client;

import br.com.remote.windows.RemoteScreen;
import br.com.remote.windows.components.ImagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class Viewer {
    public static void main(String[] args) throws IOException {
        var host = JOptionPane
                .showInputDialog("Digite o Endereço IP do computador que deseja acessar")
                .trim();

        try(final var connection = new Socket(host, 3322)) {
           var renderingScreen = new RenderingScreen(connection, host);
           var mouseCapture = new MouseCapture(connection);

           renderingScreen.run();
           mouseCapture.run();
        }catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar o computador na rede. " +
                                                                       "\n Verifique se o ip informado está correto e tente novamente");
        }
    }
}
