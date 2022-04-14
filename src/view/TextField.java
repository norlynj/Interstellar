package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Objects;

public class TextField extends JTextField{
    private final String placeHolder; //original default text
    boolean hasBeenClicked;

    public TextField(final String placeHolder) {
        this.placeHolder = placeHolder;
        hasBeenClicked = false;

        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setSize(200, 40);
        setText(placeHolder);
        setFont(new Font("Source Sans Pro", Font.PLAIN, 20)); //font for the textfield
        setBorder(new EmptyBorder(5, 15, 5, 15)); //padding
        setForeground(Color.GRAY);

        //if the textfield has been pressed
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getDisplayedText().equals(placeHolder) && !hasBeenClicked) {
                    setText(""); //the default text is deleted when pressed
                    setForeground(Color.BLACK);
                    hasBeenClicked = true;
                }
            }

            //if the focus is gone from the textfield
            @Override
            public void focusLost(FocusEvent e) {
                if (getDisplayedText().equals("")) { //if the textbox is empty, then the default text is displayed again
                    setText(placeHolder);
                    setForeground(Color.GRAY);
                    hasBeenClicked = false;
                }
            }

        });
    }

    //bg image for the input textfield
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/interstellar/input.png"))).getImage(), 0, 0, null);
        super.paintComponent(g);
    }

    @Override
    public String getText() {
        if (isEmpty()) {
            return "";
        }
        return super.getText();
    }

    private String getDisplayedText() {
        return super.getText();
    }

    //if the textfield is empty, it returns true, otherwise false
    public boolean isEmpty() {
        return getForeground().equals(Color.GRAY);
    }

   //textfield refresh, everything goes back to the original
    public void refresh() {
        setText(placeHolder);
        setForeground(Color.GRAY);
        hasBeenClicked = false;
    }
}
