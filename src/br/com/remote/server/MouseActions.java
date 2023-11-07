package br.com.remote.server;

import br.com.remote.client.CaptureType;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MouseActions implements Runnable {

    private final InputStream inputStream;

    private static final String SEPARATOR = ";";

    public MouseActions(InputStream outputStream) {
        this.inputStream = outputStream;
    }


    @Override
    public void run() {
      try {
          var robot = new Robot();
          var inputStreamReader = new InputStreamReader(this.inputStream);
          var reader = new BufferedReader(inputStreamReader);

          while (true) {

              var command = reader.readLine();

              if(command == null) {
                  continue;
              }

              CaptureType captureType = getType(command);

              if(CaptureType.MOUSE_MOVEMENT == captureType) {
                  command = removePrefix(command);
                  String[] coordinates = command.split(SEPARATOR);
                  int x = Integer.parseInt(coordinates[0]);
                  int y = Integer.parseInt(coordinates[1]);

                  robot.mouseMove(x, y);
                  continue;
              }

              if(CaptureType.MOUSE_CLICK == captureType) {
                  command = removePrefix(command);
                  MouseButtons button = MouseButtons.valueOf(command);

                  if(MouseButtons.LEFT == button){
                      robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                      Thread.sleep(2);
                      robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                  }

                  if(MouseButtons.RIGHT == button){
                      robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                      Thread.sleep(2);
                      robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                  }
              }
          }
      } catch (IOException e) {
          throw new RuntimeException(e);

      } catch (AWTException e) {
          throw new RuntimeException(e);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }

    }

    private CaptureType getType(String command) {
        String type = command.substring(0, command.indexOf(":"));
        return CaptureType.valueOf(type);
    }

    private String removePrefix(String command) {
        return command.substring(command.indexOf(":")+1);
    }
}
