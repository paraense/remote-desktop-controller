package br.com.remote.client.windows;

import br.com.remote.client.windows.components.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class RemoteScreen extends JFrame {

    public static Boolean MOUSE_ONE_SCREEN;
    public static Boolean LEFT_MOUSE_BUTTON_CLICKED = Boolean.FALSE;
    public static Boolean RIGHT_MOUSE_BUTTON_CLICKED = Boolean.FALSE;

    public static int MOUSE_MOVEMENT_X;
    public static int MOUSE_MOVEMENT_Y;


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


        imagePanel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                LEFT_MOUSE_BUTTON_CLICKED = e.getButton() == MouseEvent.BUTTON1;
                RIGHT_MOUSE_BUTTON_CLICKED = e.getButton() == MouseEvent.BUTTON3;
            }
        });

        imagePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                MOUSE_MOVEMENT_X = e.getX();
                MOUSE_MOVEMENT_X = e.getY();
            }
        });

        return imagePanel;
    }
}
