package br.com.remote.client.windows;

import javax.swing.*;
import java.awt.*;

public class InitialScreen extends JFrame {
    private static final String TITLE = "Remote Desktop Control";

    public InitialScreen(int width, int height) {
        super(TITLE);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
