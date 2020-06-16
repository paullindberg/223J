package com.company;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    private boolean isRunning = false;
    private Thread mainthread;
    private handler mainhandler;
    private int WIDTH = 800, HEIGHT = 600;

    public Main(){
        new Window(WIDTH, HEIGHT, "Cube Shooter By Paul Lindberg", this);
        mainhandler = new handler();
        mainhandler.addObject(new Player(350, 550, ID.Player, mainhandler));
        this.addKeyListener(new KeyInput(mainhandler));
        Start();


    }

    //Initializes the game loop.
    private void Start(){
        isRunning = true;
        mainthread = new Thread(this); //This refers to the run method for this class.
        mainthread.start();

    }

    private void Stop(){
        isRunning = false;
        //Stopping the thread is performed with the join method, needs to be surrounded by a try/catch as this can fail.
        try {
            mainthread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //Execution of the main game loop, utilizes threads.
    //The following game loop was written by Markus Persson. Special thanks to Notch for sharing this code with the community.
    public void run(){
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                //updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
                //updates = 0;
            }
        }
        Stop();
    }

    //Updates everything in the game
    public void tick(){
        mainhandler.tick();
    }

    //Renders everything in the game
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            //Creates buffers that will store frames
            // in them before they are rendered to the screen. Loading frames behind the scenes.
            return;
        }

        Graphics g = bs.getDrawGraphics();
        /*
        Everything between these two blocks is where we actually draw elements to our game.

         */

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        mainhandler.render(g);
        g.dispose();
        bs.show();


    }


    public static void main(String[] args) {
	new Main();
    }
}
