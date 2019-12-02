package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/*
Handle's player keyboard input.

 */

public class KeyInput extends KeyAdapter {

    handler keyhandler;


    public KeyInput(handler keyhandler){
        this.keyhandler = keyhandler;

    }

    //Movement commands are sent to the event handler which can process the individual player methods.
    //The most notable play is firing boolean is set on space bar but this applies for movement actions asewell.
    public void keyPressed(KeyEvent ke){
        int key = ke.getKeyCode();
        for (int i = 0; i < keyhandler.object.size(); i++){
            GameObject tempObject = keyhandler.object.get(i);
            if (tempObject.getId() == ID.Player){
                if (key == KeyEvent.VK_W) {
                    keyhandler.setMovingUP(true);

                }
                if (key == KeyEvent.VK_A) {
                    keyhandler.setMovingLEFT(true);
                }
                if (key == KeyEvent.VK_S) {
                    keyhandler.setMovingDOWN(true);
                }
                if (key == KeyEvent.VK_D) {
                    keyhandler.setMovingRIGHT(true);
                }
                if (key == KeyEvent.VK_SPACE){
                    keyhandler.setPlayerFire(true);
                }
                if (key == KeyEvent.VK_CONTROL){
                    if (!keyhandler.isPlayerAlive()){
                        keyhandler.resetLevel();
                    }
                }
            }
        }
    }

    //On key release these booleans are set back to false.
    public void keyReleased(KeyEvent ke){
        int key = ke.getKeyCode();
        for (int i = 0; i < keyhandler.object.size(); i++){
            GameObject tempObject = keyhandler.object.get(i);
            if (tempObject.getId() == ID.Player){
                if (key == KeyEvent.VK_W)
                    keyhandler.setMovingUP(false);
                if (key == KeyEvent.VK_A)
                    keyhandler.setMovingLEFT(false);
                if (key == KeyEvent.VK_S)
                    keyhandler.setMovingDOWN(false);
                if (key == KeyEvent.VK_D)
                    keyhandler.setMovingRIGHT(false);
                if (key == KeyEvent.VK_SPACE) {
                    keyhandler.setPlayerFire(false);
                    }
                }
            }
        }
    }







