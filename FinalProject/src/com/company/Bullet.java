/*
Bullet object, derived from GameObjects. Emitted from both players and enemies.
 */

package com.company;
import java.awt.*;
public class Bullet extends GameObject {
    private boolean onScreen = false;


    public boolean isOnScreen() {
        return onScreen;
    }

    public Bullet(int x, int y, ID id){
        super(x, y, id);
        if (id == ID.Bullet){
            velY = -20;
        }
        if (id == ID.EnemyBullet){
            velY = 10;
        }
    }
    //If a bullet object is not within the boundaries of the screen it's onSceen boolean is set to false and it is moved off the screen for future ticks.
    public void checkScreen(){
        if (x > 0 && x < 800){
            if (id == ID.EnemyBullet)
                onScreen = true;
            else if (y > 0 && y < 650){
                onScreen = true;
            }
            else
                onScreen = false;
        }
        else onScreen = false;
    }
    //Hides off screen bullets away and keeps them in place.
    public void tick() {
        checkScreen();
        if (isOnScreen()) {
            x += velX;
            y += velY;
        }
        else{
            x = -100;
            y = -100;
        }
    }
    public void render(Graphics g) {
        if (isOnScreen()) {

            if (id == ID.Bullet) {
                g.setColor(Color.WHITE);
                g.fillRect(x, y, 5, 50);
            }
            if (id == ID.EnemyBullet) {
                g.setColor(Color.YELLOW);
                g.fillRect(x, y, 25, 25);
            }
        }
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 50 );
    }
}
