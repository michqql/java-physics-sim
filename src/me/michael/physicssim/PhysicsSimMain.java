package me.michael.physicssim;

import me.michael.physicssim.world.World;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class PhysicsSimMain implements Runnable {

    private static boolean started = false;
    public static void main(String[] args) {
        if(!started) {
            started = true;
            new PhysicsSimMain().start();
        }
    }

    private final Thread thread;
    private boolean running = false;
    private double desiredFPS;
    private double desiredUPS;
    private double timeU;
    private double timeF;

    private String title = "Physics Sim";
    private int width = 900, height = 700;
    private Window window;
    private int globalFrames, globalUpdates;

    private World world;

    public PhysicsSimMain() {
        setDesiredFPS(60.0);
        setDesiredUPS(60.0);
        this.thread = new Thread(this);
    }

    public void start() {
        if(running)
            return;

        running = true;
        window = new Window(title, width, height);
        world = new World(16, 16);
        run();
    }

    public void stop() {
        if(!running)
            return;

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long initialTime = System.nanoTime();
        double deltaU = 0, deltaF = 0;
        int frames = 0, updates = 0;
        long timer = System.currentTimeMillis();

        while (running) {

            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                globalFrames = frames;
                globalUpdates = updates;
                frames = 0;
                updates = 0;
                timer += 1000;
            }
        }
    }

    private void update() {
        world.update();
        world.createSandBlockAt(5, 6);
    }

    private void render() {
        BufferStrategy bs = window.getBufferStrategy();
        if(bs == null)
            return;

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        // Draw objects in here
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + globalFrames + ", UPS: " + globalUpdates, 1, 12);

        g.setColor(Color.BLACK);
        world.render(g);

        g.dispose();
        bs.show();
    }

    public void setDesiredFPS(double desiredFPS) {
        this.desiredFPS = desiredFPS;
        this.timeF = 1_000_000_000 / desiredFPS;
    }

    public void setDesiredUPS(double desiredUPS) {
        this.desiredUPS = desiredUPS;
        this.timeU = 1_000_000_000 / desiredUPS;
    }
}
