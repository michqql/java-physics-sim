package me.michael.physicssim;

import me.michael.physicssim.input.KeyHandler;
import me.michael.physicssim.input.MouseHandler;
import me.michael.physicssim.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Game extends JPanel implements Runnable {

    // Window size
    final int tileSize = 16;
    final int scale = 3;

    final int scaledTileSize = tileSize * scale;
    final int tileColumns = 16;
    final int tileRows = 12;

    final int width = scaledTileSize * tileColumns;
    final int height = scaledTileSize * tileRows;

    // Game loop
    private Thread thread;
    private boolean running;
    private double desiredFPS;
    private double desiredUPS;
    private double timeU;
    private double timeF;
    private int globalFrames, globalUpdates;

    // Game objects
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    private World world;
    private Camera camera;

    public Game() {
        super.setPreferredSize(new Dimension(width, height));
        super.setBackground(Color.BLACK);
        super.setDoubleBuffered(true);
        super.setFocusable(true);

        this.keyHandler = new KeyHandler();
        this.mouseHandler = new MouseHandler();

        super.addKeyListener(keyHandler);
        super.addMouseListener(mouseHandler);
        super.addMouseMotionListener(mouseHandler);

        this.camera = new Camera(keyHandler, world);
    }

    public void start() {
        if(running)
            return;

        setDesiredFPS(60.0);
        setDesiredUPS(60.0);

        running = true;
        this.thread = new Thread(this);
        thread.start();
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
                update(deltaU);
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

    private void update(double dt) {
        dt /= desiredUPS;

        keyHandler.update(dt);
        mouseHandler.update(dt);

        camera.update(dt);
    }

    private void render() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Debug info
        final String fpsUps = "FPS: " + globalFrames + ", UPS: " + globalUpdates;
        g.setColor(Color.WHITE);
        g.drawString(fpsUps, 1, 12);
        drawDebugInfo(g);

        // Game objects
        camera.render(g);

        g.dispose();
    }

    private boolean debugEnabled = false;
    private void drawDebugInfo(Graphics g) {
        if(keyHandler.wasKeyPressed(KeyEvent.VK_F1)) {
            debugEnabled = !debugEnabled;
        }

        if(!debugEnabled)
            return;

        final String position = "X: " + mouseHandler.getX() + ", Y: " + mouseHandler.getY();
        final String aKeyHeld = "A: " + (keyHandler.isKeyDown(KeyEvent.VK_A) ? "pressed" : "released") +
                ", length: " + keyHandler.getTicksKeyHeld(KeyEvent.VK_A);

        g.drawString(position, 1, 25);
        g.drawString(aKeyHeld, 1, 38);
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
