package br.com.remote.windows;

import javax.swing.*;
import java.awt.*;

public class RemoteScreen extends JFrame {

    public RemoteScreen(int width, int height, JPanel panel, String title) {
        super(title);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void changeImage(JPanel imagePanel){
        setContentPane(imagePanel);
        revalidate();
        repaint();
    }

}
