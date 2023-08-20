package br.com.remote.server;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MouseActions implements Runnable {

    private final InputStream inputStream;

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

              var positions = reader.readLine();

              if(positions == null) {
                  continue;
              }

              String[] coordinates = positions.split(";");
              int x = Integer.parseInt(coordinates[0]);
              int y = Integer.parseInt(coordinates[1]);

              robot.mouseMove(x, y);
          }
      } catch (IOException e) {
          throw new RuntimeException(e);

      } catch (AWTException e) {
          throw new RuntimeException(e);
      }

    }
}
