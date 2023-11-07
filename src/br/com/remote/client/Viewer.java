package br.com.remote.client;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class Viewer {

    private static int widthScreenClient;
    private static int heightScreenClient;


    public static void main(String[] args) throws IOException {
        var host = JOptionPane
                .showInputDialog("Digite o Endereço IP do computador que deseja acessar")
                .trim();

        try(final var connection = new Socket(host, 3322)) {

           setScreenSize(connection.getInputStream());

           // Streaming de tela
           var renderingScreen = new RenderingScreen(connection, host, widthScreenClient, heightScreenClient);
           new Thread(renderingScreen).start();

           // Captura a posição do mouse
           Thread.sleep(1000);
           var mouseCapture = new MouseCaptureMovement(connection, RenderingScreen.getScreen());
           new Thread(mouseCapture).start();

            // Clicks do Mouse
            Thread.sleep(1000);
            var mouseClick = new MouseCaptureClick(connection, RenderingScreen.getScreen());
            mouseClick.run();
            // TODO: Capturar teclas pressionadas


        }catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar o computador na rede. " +
                                                                       "\n Verifique se o ip informado está correto e tente novamente");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setScreenSize(InputStream inputStream) {
        try {
            var inputStreamReader = new InputStreamReader(inputStream);
            var reader = new BufferedReader(inputStreamReader);

            var line = reader.readLine();

            String[] size = line.split(":");
            widthScreenClient = Integer.parseInt(size[0]) + 5;
            heightScreenClient = Integer.parseInt(size[1]) + 40;

        } catch (IOException e) {
            System.err.println("Erro ao obter o tamanho da tela do client");
        }
    }
}
