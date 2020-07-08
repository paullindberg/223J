package com.company;

import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import javax.sound.sampled.Clip;

/*
Event Handler, the main "director" for the game actions.
This handler is responsible for grabbing ALL gameObjects currently on the screen and updating them with the tick rate. As well as spawning/de-spawning and managing the scoreboard.

 */

public class handler {
    //LINKED LIST OF ALL OBJECTS
    LinkedList<GameObject> object = new LinkedList<GameObject>(); //Uses a linked list because the amount of objects the game is currently using is unknown/dynamic
    private boolean movingUP = false, movingDOWN = false, movingLEFT = false, movingRIGHT = false;
    private boolean playerFire = false;
    private boolean playerAlive;
    private int kills = 0;
    private int enemyFactor = 3;
    private Block movingBlock;
//    private String scoresFilePath = "C:\\Users\\Paul\\IdeaProjects\\FinalProject\\scores.txt";
    private int highScore = 0;
    private Block continueBTN = new Block(-200, -200, ID.Continue, this);

//    public void writeScores(int i){
//        try(FileWriter fileWriter = new FileWriter(scoresFilePath)) {
//            String fileContent = Integer.toString(i);
//            fileWriter.write(fileContent);
//        } catch (IOException e) {
//
//        }
//    }

//    public void readScores(){
//        File file = new File(scoresFilePath);
//        try {
//
//            Scanner sc = new Scanner(file);
//            highScore = Integer.parseInt(sc.nextLine());
//
//        }catch(IOException ie) {
//        ie.printStackTrace();
//    }
//}
        public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    //Players whatever sound file path is sent to it.
    public void PlaySound(File Sound){
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Sound));
            clip.start();


        }catch(Exception e)
        {

        }
    }
    //Collects and processes date and resets spawns to the starting state of the game.
    public void resetLevel(){

        int enemies = 0;
        int spawns = kills / enemyFactor;
        kills = 0;
        playerAlive = true;
        continueBTN.setX(-200);
        continueBTN.setY(-200);
        movingBlock.respawn();
        for (int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            if (tempObject.id == ID.Player){
                tempObject.x = 350;
                tempObject.y = 550;
            }
            if (tempObject.id == ID.Enemy){
                enemies++;
                tempObject.x = -500;
                tempObject.y = -100;
            }
            if (tempObject.id == ID.EnemyBullet){
                tempObject.x = -100;
                tempObject.y = -100;
            }
            if (spawns > 0 && tempObject.id == ID.Enemy){
                removeObject(tempObject);
                spawns--;
            }
        }
    }

    public boolean isPlayerAlive() {
        return playerAlive;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setPlayerAlive(boolean playerAlive) {
        this.playerAlive = playerAlive;
    }

    //The main constructor spawns the initial GameObjects.
    public handler(){
//        readScores();
        object.add(new Enemy(-500, -100, ID.Enemy, this));
        object.add(new Enemy(-500, -100, ID.Enemy, this));
        object.add(new Enemy(-500, -100, ID.Enemy, this));
        object.add(new Enemy(-500, -100, ID.Enemy, this));
        movingBlock = new Block(100, 200, ID.Block, this);
        //The following line adds a moving cover feature. It works but I feel it's a failed feature from a game design
        //perspective, so it had been commented out.
        //object.add(movingBlock);
        object.add(new Block(0, 500, ID.Text, this));
        object.add(new Block(0, 400, ID.HighScore, this));
        object.add(continueBTN);
    }

    //Increasing difficulty factor, spawns a new enemy for every three enemies defeated.
    public void spawnEnemy(){
        if (kills % enemyFactor == 0) {
            object.add(new Enemy(-500, -100, ID.Enemy, this));
        }
        if (kills % 20 == 0 && !movingBlock.isAlive())
            movingBlock.respawn();
    }

    public boolean isPlayerFire() {
        return playerFire;
    }

    public void setPlayerFire(boolean playerFire) {
        this.playerFire = playerFire;
    }

    public boolean isMovingUP() {
        return movingUP;
    }

    public void setMovingUP(boolean movingUP) {
        this.movingUP = movingUP;
    }

    public boolean isMovingDOWN() {
        return movingDOWN;
    }

    public void setMovingDOWN(boolean movingDOWN) {
        this.movingDOWN = movingDOWN;
    }

    public boolean isMovingLEFT() {
        return movingLEFT;
    }

    public void setMovingLEFT(boolean movingLEFT) {
        this.movingLEFT = movingLEFT;
    }

    public boolean isMovingRIGHT() {
        return movingRIGHT;
    }

    public void setMovingRIGHT(boolean movingRIGHT) {
        this.movingRIGHT = movingRIGHT;
    }

    //The first initial tick function, calls the tick function for all other objects within the linked list.
    public void tick(){
        for (int i = 0; i < object.size(); i++){
            GameObject tempObect = object.get(i);
            tempObect.tick();
        }
        if (!playerAlive){
            if (kills > highScore) {
//                writeScores(kills);
                highScore = kills;
            }
            continueBTN.setX(250);
            continueBTN.setY(300);

        }
    }
    //Renders all other objects in the linked list.
    public void render(Graphics g){
        for (int i = 0; i < object.size(); i++){
            GameObject tempObect = object.get(i);
            tempObect.render(g);
        }
    }
    //Adds object to the linked list.
    public void addObject(GameObject tempObject){
        object.add(tempObject);

    }

    public void removeObject(GameObject tempObject){
        object.remove(tempObject);
    }

}
