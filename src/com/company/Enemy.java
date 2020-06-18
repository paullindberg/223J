package com.company;

import java.awt.*;
import java.io.File;
import java.util.Random;

public class Enemy extends GameObject {
    private short currentBullet = 0;
    private boolean canFire = false;
    private long initialTime;
    private boolean timerAvail = true;
    private handler enemyHandler;
    private Bullet[] bullets = new Bullet[2];
    private int rect_width = 50;
    private boolean spawned = false;

    private long randomTime;
    private boolean moveTimerAvail = true;
    private long randomCD;
    private boolean isAlive = true;

    private Random rand = new Random();
    private float randomShotWait;

    public Enemy(int x, int y, ID id, handler enemyHandler){
        super(x, y, id);
        this.enemyHandler = enemyHandler;
        velX = 10;
        for (int i = 0; i < 2; i++){
            bullets[i] = new Bullet(-100, -100, ID.EnemyBullet);
            enemyHandler.addObject(bullets[i]);
        }
    }
    //Each enemy is given a buffer of two bullets that it can fire before waiting for the oldest bullet to dissapear.
    public void fire(){
        if (currentBullet > 1)
            currentBullet = 0;
        bullets[currentBullet].x = x + 12;
        bullets[currentBullet].y = y;
        currentBullet++;
        canFire = false;
    }
    //Generates a new offscreen enemy
    public void respawn(){
        isAlive = true;
        x = - 500;
        y = -100;
    }
    //Standard collide function for enemies, keeps their movement inside the bounds of the screen and destroys them when colliding with player bullets.
    public void collide(){
        if (getBounds().x < 0){
            x = 0;
            velX *= -1;
            y += 20;
        }
        if (getBounds().x + rect_width > 800){
            x = 800 - rect_width;
            velX *= -1;
            y += 20;
        }
        for (int i = 0; i < enemyHandler.object.size(); i++) {
            GameObject tempObject = enemyHandler.object.get(i);

            if (tempObject.getId() == ID.Bullet) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    isAlive = false;
                    tempObject.x = -100;
                    tempObject.y = -100;
                    enemyHandler.setKills(enemyHandler.getKills() + 1);
                    System.out.println("KIlls: " + enemyHandler.getKills());
                    enemyHandler.spawnEnemy();
                }
            }
        }
    }
    //RNG timing function that staggers the shots between enemy attacks.
    public void fireCooldown(){

        if (timerAvail) {
            int randint = rand.nextInt(4);
            if (randint == 0)
                randomShotWait = 1000;
            if (randint == 1)
                randomShotWait = 1500;
            if (randint == 2)
                randomShotWait = 500;
            if (randint == 3)
                randomShotWait = 750;

            initialTime = System.currentTimeMillis();
            timerAvail = false;
            return;
        }
        if (System.currentTimeMillis() - initialTime >= randomShotWait){
            timerAvail = true;
            canFire = true;
        }
    }
    //RNG timing function that causes enemies to change directions randomly.
    public void randomMove(){
        if (moveTimerAvail){

            moveTimerAvail = false;
            randomTime = System.currentTimeMillis();

            int randInt = rand.nextInt(4);
            if (randInt == 0)
                randomCD = 500;
            if (randInt == 1)
                randomCD = 1000;
            if (randInt == 2)
                randomCD = 1500;
            if (randInt == 3)
                randomCD = 2000;
        }
        if (System.currentTimeMillis() - randomTime  >= randomCD){
            moveTimerAvail = true;
            velX *= -1;
        }
    }
    public void tick(){
        if (isAlive) {
            if (!spawned) {
                if (x > 0)
                    spawned = true;
            }
            x += velX;
            y += velY;
            if (spawned)
                randomMove();
            fireCooldown();
            if (canFire)
                fire();
            if (spawned)
                collide();
        }
        else
            respawn();
    }
    public void render(Graphics g){
        if (isAlive) {
            g.setColor(Color.RED);
            g.fillRect(x, y, rect_width, rect_width);
        }
    }
    public Rectangle getBounds(){
        return new Rectangle(x, y, rect_width, rect_width);

    }

}
