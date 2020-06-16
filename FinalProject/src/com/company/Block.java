/*
A more flexible GameObject, handles the bouncing cover and the scoreboard but could be retooled to handle more objects.
Simply a moving animated rectangle.
 */

package com.company;

import java.awt.*;
import java.io.File;
import java.util.Random;

public class Block extends GameObject {
    handler blockhandler;
    int rect_width = 150;
    int rect_height = 60;
    private Random rand = new Random();
    private int alpha = 100;
    private Color blockColor =  new Color (255, 255, 0, alpha);
    private boolean isAlive = true;
    private int health = 12;
    Font displayFont = new Font("TimesRoman", Font.PLAIN, 24);
    private File blockSound = new File("C:\\Users\\Paul\\IdeaProjects\\FinalProject\\src\\sounds\\block.wav");


    public boolean isAlive() {
        return isAlive;
    }
    //Regenerates the bouncing cover version of this object.

    public void respawn(){
        int randint = rand.nextInt(2);
        isAlive = true;
        alpha = 100;
        health = 12;
        blockColor = new Color(255, 255, 0, alpha);
        if (randint == 0){
            x = 100;
            y = 100;
            velY = -5;
            velX = 5;
        }
        else if (randint == 1){
            x = 700;
            y = 50;
            velY = -5;
            velX = -5;
        }
    }

    //Randomizes the initial velocity of the bouncing cover.
    public Block(int x, int y, ID id, handler blockHandler){
        super(x, y, id);
        this.blockhandler = blockHandler;
        int randint = rand.nextInt(1);
        if (randint == 0){
            velY = 5;
            velX = -5;
        }
        if (randint == 1){
            velY = 5;
            velX = 5;

        }
    }
    //Keeps the bouncing cover in bounds. The cover object blocks all bullets either enemy of player bullets.
    //Player bullets reduce the cover's health and eventually destroy it.

    public void collide() {
        if (getBounds().x < 0){
            x = 0;
            velX *= -1;
        }
        if (getBounds().y < 0){
            y = 0;
            velY *= -1;
        }
        if (getBounds().x + rect_width > 800){
            x = 800 - rect_width;
            velX *= -1;
        }
        if (getBounds().y + rect_height > 600){
            y = 600 - rect_height;
            velY *= -1;
        }
        //Handles the block object's health, listens for all bullet objects and destroys them if they collide with the cover.
        for (int i = 0; i < blockhandler.object.size(); i++) {
            GameObject tempObject = blockhandler.object.get(i);
            if (tempObject.getId() == ID.Bullet || tempObject.getId() == ID.EnemyBullet) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    tempObject.x = -100;
                    tempObject.y = -100;
                    if (tempObject.getId() == ID.Bullet) {
                        blockhandler.PlaySound(blockSound);
                        health--;
                        if (health <= 0)
                            alpha = 0;
                        else if (health <= 3)
                            alpha = 50;
                        else if (health  <= 6)
                            alpha = 62;
                        else if (health <= 9)
                            alpha = 74;
                        else if (health <= 12)
                            alpha = 100;
                        blockColor = new Color(255, 255, 0, alpha);
                        if (alpha <= 0){
                            isAlive = false;
                            x = -300;
                            y = -300;
                        }
                    }


                }
            }

        }
    }

    public void tick(){
        if (blockhandler.isPlayerAlive() && isAlive && id == ID.Block) {
            collide();
            x += velX;
            y += velY;
        }
    }
    public void render(Graphics g){
        if (blockhandler.isPlayerAlive() && isAlive && id == ID.Block) {
            g.setColor(blockColor);
            g.fillRect(x, y, rect_width, rect_height);

        }
        //In most cases the block object is the bouncing cover, but with Enum identifiers block object also refers to the current score and top score objects.
        if (id == ID.Text){
            g.setFont(displayFont);
            g.setColor(Color.WHITE);
            g.drawString("Score: " + blockhandler.getKills(), x, y);
        }
        if (id == ID.HighScore){
            g.setFont(displayFont);
            g.setColor(Color.WHITE);
            g.drawString("Top Score: " + blockhandler.getHighScore(), x, y);
        }
        if (id == ID.Continue){
            g.setFont(displayFont);
            g.setColor(Color.WHITE);
            g.drawString("Press SHIFT to Continue", x, y);
        }



    }
    public Rectangle getBounds(){
        return new Rectangle(x, y, rect_width, rect_height);

    }

}
