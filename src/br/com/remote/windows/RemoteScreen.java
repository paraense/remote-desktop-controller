package br.com.remote.windows;

import br.com.remote.windows.components.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class RemoteScreen extends JFrame {

    public static Boolean MOUSE_ONE_SCREEN;
    public static int MOUSE_X;
    public static int MOUSE_Y;

    public RemoteScreen(int width, int height, ImagePanel panel, String title) {
        super(title);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setContentPane(preparePanel(panel));
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void changeImage(ImagePanel imagePanel){
        setContentPane(preparePanel(imagePanel));
        revalidate();
        repaint();
    }

    private ImagePanel preparePanel(ImagePanel imagePanel) {
        imagePanel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                MOUSE_ONE_SCREEN = Boolean.TRUE;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                MOUSE_ONE_SCREEN = Boolean.FALSE;
            }
        });

        imagePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                MOUSE_X = e.getX();
                MOUSE_Y = e.getY();
            }
        });


        return imagePanel;
    }
}
