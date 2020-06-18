package com.company;

import java.awt.*;

public class Box extends GameObject {
    public Box(int x, int y, ID id){
        super(x, y, id);


    }


    public void tick() {
        x += velX;
        y += velY;

    }


    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 32, 32);

    }


    public Rectangle getBounds() {
        return null;
    }
}
