package view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Frame extends JFrame {

    JWindow window = new JWindow();

    public Frame(String name) {

        loadImage();  //SplashScreen

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(name);
        setResizable(false);
        getContentPane().setPreferredSize(new Dimension(1000, 563));
        pack();
        pack();
        setLocationRelativeTo(null);
    }

    private void loadImage() {

        //splash screen
        window.getContentPane().add(
                new JLabel("", new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/interstellar/1.png"))), SwingConstants.CENTER));

        window.setSize(580, 278);
        window.setLocationRelativeTo(null); //set position to the center screen

        window.setVisible(true);
        try {
            Thread.sleep(2000); //show the splash screen for 2 seconds
        } catch (InterruptedException e) {
            e.getMessage();
        }

        window.dispose(); //disappears then after

    }
}
