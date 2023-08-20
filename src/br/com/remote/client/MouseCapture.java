package br.com.remote.client;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class MouseCapture implements Runnable {

    private final Socket socket;

    public MouseCapture(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
       try {
           var out = socket.getOutputStream();

           while(true) {

               int x = MouseInfo.getPointerInfo().getLocation().x;
               int y = MouseInfo.getPointerInfo().getLocation().y;

               var mousePositions = x+";"+y+"\n";
               out.write(mousePositions.getBytes());
               out.flush();

               Thread.sleep(2);
           }
       } catch (IOException e) {
           throw new RuntimeException(e);

       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }

    }
}
