package com.company;
import javax.swing.JFrame;
import java.awt.*;

public class Window extends JFrame {

    public Window(int width, int height, String title, Main main){
        super(title);
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        add(main);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);





    }

}
