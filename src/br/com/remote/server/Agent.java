package br.com.remote.server;


import javax.swing.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Agent {

    private static final int PORT = 3322;

    public static void main(String[] args) {

        welcomeMessage();

        try(final ServerSocket serverSocket = new ServerSocket(PORT)) {
            var socket = serverSocket.accept();
            System.out.println("Conexão recebida: " + socket.getInetAddress());

            var captureScreen = new CaptureScreen(socket, 1366, 780);
            var mouseActions = new MouseActions(socket.getInputStream());

            captureScreen.run();
            mouseActions.run();

        }catch (IOException e) {
            System.err.println("Erro ao estabelecer conexão");
        }
    }


    private static void welcomeMessage() {
       try{
           var ipAddress = String.valueOf(InetAddress.getLocalHost());
           ipAddress = ipAddress.substring(ipAddress.indexOf("/") + 1);

           JOptionPane.showMessageDialog(null,
                   "Bem vindo ao RNC. Você está pronto para receber uma conexão " +
                           "\n Seu endereço IP para conexão é : "+ ipAddress);

       } catch (UnknownHostException e) {
           System.err.println("Ocorreram erros ao tenta recuperar o endereço IP do Host");
       }
    }

}
