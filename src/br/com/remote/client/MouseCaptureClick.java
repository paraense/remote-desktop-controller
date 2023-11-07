package br.com.remote.client;

import br.com.remote.client.windows.RemoteScreen;

import java.io.IOException;
import java.net.Socket;

public class MouseCaptureClick implements Runnable {

    private final Socket socket;
    private final RemoteScreen remoteScreen;

    public MouseCaptureClick(Socket socket, RemoteScreen remoteScreen) {
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

                if(RemoteScreen.LEFT_MOUSE_BUTTON_CLICKED || RemoteScreen.RIGHT_MOUSE_BUTTON_CLICKED) {
                    out.write(clickCapture());
                    out.flush();
                    Thread.sleep(2);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private byte[] clickCapture() {

        var mousePositions = CaptureType.MOUSE_CLICK + ":";

        if(RemoteScreen.LEFT_MOUSE_BUTTON_CLICKED) {
            mousePositions = mousePositions.concat("LEFT\n");
        }

        if(RemoteScreen.RIGHT_MOUSE_BUTTON_CLICKED) {
            mousePositions = mousePositions.concat( "RIGHT\n");
        }

        RemoteScreen.LEFT_MOUSE_BUTTON_CLICKED = Boolean.FALSE;
        RemoteScreen.RIGHT_MOUSE_BUTTON_CLICKED = Boolean.FALSE;

        return mousePositions.getBytes();
    }
}
