package view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

//class for making an image button
public class ImageButton extends JButton {
    public ImageButton(String imageName) {
        this(imageName, 300, 70);
    }

    public ImageButton(String imageName, int width, int height) {

        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images" + imageName)))); //img bg
        setVisible(true);

        addActionListener(e -> {
            new AudioPlayer("click.wav").play(); //click sound
        });

    }

}
