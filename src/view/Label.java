package view;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    boolean multiLine;
    boolean center;

    public Label() {
        this("");
    }

    public Label(String text) {
        this(text, 20, false, SwingConstants.CENTER);
    }

    public Label(String text, boolean multiLine, int alignment) {
        this(text, 20, true, alignment);
    }

    public Label(String text, int fontSize, boolean multiLine, int alignment) {
        setFont(new Font("Comic Sans MS", Font.PLAIN, fontSize));
        setForeground(Color.white);

        center = false;
        if (alignment == SwingConstants.CENTER) {
            center = true;
        }
        this.multiLine = multiLine;

        //sets the horizontal alignment of the label
        setHorizontalAlignment(alignment);
        setVerticalAlignment(SwingConstants.CENTER);
        setText(text);
    }

    @Override
    public void setText(String text) {
        if (multiLine) {
            if (center) {
                text = "<html><center>" + text + "</center></html>"; //align in the center
            } else {
                text = "<html>" + text + "</html>";
            }
        }
        super.setText(text);
    }
}
