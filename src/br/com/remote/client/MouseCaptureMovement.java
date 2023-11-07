package br.com.remote.client;

import br.com.remote.client.windows.RemoteScreen;

import java.io.IOException;
import java.net.Socket;

public class MouseCaptureMovement implements Runnable {

    private final Socket socket;
    private final RemoteScreen remoteScreen;

    public MouseCaptureMovement(Socket socket, RemoteScreen remoteScreen) {
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

               out.write(currentMousePositionToBytes());
               out.flush();

               Thread.sleep(2);
           }
       } catch (IOException e) {
           throw new RuntimeException(e);

       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
    }

    private byte[] currentMousePositionToBytes() {
        var mousePositions = CaptureType.MOUSE_MOVEMENT+ ":"
                             + RemoteScreen.MOUSE_MOVEMENT_X +";"
                             + RemoteScreen.MOUSE_MOVEMENT_Y +"\n";

        return mousePositions.getBytes();
    }
}
