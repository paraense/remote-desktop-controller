package br.com.remote.client;

import br.com.remote.client.windows.RemoteScreen;

import java.io.IOException;
import java.net.Socket;

public class MouseCapture implements Runnable {

    private final Socket socket;
    private final RemoteScreen remoteScreen;

    public MouseCapture(Socket socket, RemoteScreen remoteScreen) {
        this.socket = socket;
        this.remoteScreen = remoteScreen;
    }

    @Override
    public void run() {
       try {
           var out = socket.getOutputStream();

           while(true) {

               if(!remoteScreen.isActive() || !RemoteScreen.MOUSE_ONE_SCREEN) {
                   continue;
               }

               int x = RemoteScreen.MOUSE_X;
               int y = RemoteScreen.MOUSE_Y;

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
