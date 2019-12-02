/*
The most senior class object, Bullet, Enemy, Player, and Block all inherit from Gameobject.
 */
package com.company;
import java.awt.*;
public abstract class GameObject {

    protected int x, y;
    //The velocity values set the direction of movement for each GameObject per tick.
    protected float velX = 0, velY = 0;
    protected ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public GameObject(int x, int y, ID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    //Generates the tick actions for every frame ran by the game.
    public abstract void tick();
    //For every frame, this determines what graphic will be displayed to the screen.
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();




}
