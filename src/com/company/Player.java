package com.company;

import java.awt.*;
import java.io.File;

public class Player extends GameObject {

    handler playerHandler;
    private int rect_width = 50;
    private int rect_height = 50;
    private Color colorBox = new Color(255, 255,255);
    Bullet[] bullets = new Bullet[6];
    private boolean fireCannonOne = true;
    private short currentBullet = 0;
    private boolean canFire = true;
    private long initialTime;
    private boolean timerAvail = true;
    private boolean isAlive = true;

    private File shootSound = new File("C:\\Users\\Paul\\IdeaProjects\\FinalProject\\src\\sounds\\shoot2.wav");
    private File deathSound = new File("C:\\Users\\Paul\\IdeaProjects\\FinalProject\\src\\sounds\\death.wav");

    Player(int x, int y, ID id, handler playerHandler){
        super(x, y, id);
        this.playerHandler = playerHandler;
        for (int i = 0; i < 6; i++){
            bullets[i] = new Bullet(-100, -100, ID.Bullet);
            playerHandler.addObject(bullets[i]);


        }
        playerHandler.setPlayerAlive(isAlive);

    }
    //Bounds the player within the boundaries of the screen and destroys them if they collide with an enemy object.
    public void collide(){
        //The following keep the player in bounds
        if (getBounds().x < 0){
            x = 0;
        }
        if (getBounds().y < 0){
            y = 0;
        }
        if (getBounds().x + rect_width > 800){
            x = 800 - rect_width;
        }
        if (getBounds().y + rect_height > 600){
            y = 600 - rect_height;
        }

        for (int i = 0; i < playerHandler.object.size(); i++){
            GameObject tempObject = playerHandler.object.get(i);

            if (tempObject.getId() == ID.EnemyBullet || tempObject.getId() == ID.Enemy){
                if (getBounds().intersects(tempObject.getBounds())){
                   isAlive = false;
                   playerHandler.setPlayerAlive(false);
                   playerHandler.PlaySound(deathSound);

                }

            }
        }
    }
    //Alternates player bullets between the left and right sides of the model.
    public void fire(){

        if (currentBullet > 5)
            currentBullet = 0;
        if (!bullets[currentBullet].isOnScreen()){
            if (fireCannonOne){
                bullets[currentBullet].x = x;
                bullets[currentBullet].y = y;
                fireCannonOne = false;
                playerHandler.setPlayerFire(false);
            }
            else{
                bullets[currentBullet].x = x + rect_width - 5;
                bullets[currentBullet].y = y;
                fireCannonOne = true;
                playerHandler.setPlayerFire(false);
            }
            ++currentBullet;
            canFire = false;
        }
        playerHandler.PlaySound(shootSound);
    }

    //Adds a timer that enforces a cooldown between player shots.
    public void canFireCooldown(){
        if (timerAvail) {
            initialTime = System.currentTimeMillis();
            timerAvail = false;
            return;
        }
        if (System.currentTimeMillis() - initialTime >= 250){
            timerAvail = true;
            canFire = true;
        }
    }

    public void tick() {
        if (isAlive) {
            x += velX;
            y += velY;
            canFireCooldown();
            if (playerHandler.isPlayerFire() && canFire)
                fire();

            collide();
            if (playerHandler.isMovingUP()) velY = -5;
            else if (!playerHandler.isMovingUP() && !playerHandler.isMovingDOWN()) velY = 0;

            if (playerHandler.isMovingDOWN()) velY = 5;
            else if (!playerHandler.isMovingDOWN() && !playerHandler.isMovingUP()) velY = 0;

            if (playerHandler.isMovingLEFT()) velX = -5;
            else if (!playerHandler.isMovingLEFT() && !playerHandler.isMovingRIGHT()) velX = 0;

            if (playerHandler.isMovingRIGHT()) velX = 5;
            else if (!playerHandler.isMovingLEFT() && !playerHandler.isMovingRIGHT()) velX = 0;
        }
        else if(playerHandler.isPlayerAlive())
            isAlive = true;

    }


    public void render(Graphics g) {
        if (isAlive) {
            g.setColor(colorBox);
            g.fillRect(x, y, rect_width, rect_height);
        }

    }


    public Rectangle getBounds() {
        return new Rectangle(x,y, rect_width, rect_height);
    }
}
