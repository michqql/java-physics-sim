package me.michael.physicssim;

import me.michael.physicssim.entity.Player;
import me.michael.physicssim.input.KeyHandler;
import me.michael.physicssim.input.MouseHandler;
import me.michael.physicssim.world.World;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {

    // Window size
    public static final int TILE_SIZE = 16;
    final int scale = 3;

    final int scaledTileSize = TILE_SIZE * scale;
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

    // Static Game objects
    private static KeyHandler keyHandler;
    private static MouseHandler mouseHandler;
    private final Debugger debugger;

    private World world;
    private Player player;

    public Game() {
        super.setPreferredSize(new Dimension(width, height));
        super.setBackground(Color.BLACK);
        super.setDoubleBuffered(true);
        super.setFocusable(true);

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        this.debugger = new Debugger(this, keyHandler, mouseHandler);

        super.addKeyListener(keyHandler);
        super.addMouseListener(mouseHandler);
        super.addMouseMotionListener(mouseHandler);

        this.world = new World();
        this.player = world.spawnPlayer();
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

        // Game objects
        player.update(dt, this);
        world.update(dt, this);

        debugger.update(dt, this);
        keyHandler.update(dt, this);
        mouseHandler.update(dt, this);
    }

    private void render() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        debugger.render(g, this);

        // Game objects
        world.render(g, this);
        player.render(g, this);

        g.dispose();
    }

    public void setDesiredFPS(double desiredFPS) {
        this.desiredFPS = desiredFPS;
        this.timeF = 1_000_000_000 / desiredFPS;
    }

    public int getCurrentFPS() {
        return globalFrames;
    }

    public void setDesiredUPS(double desiredUPS) {
        this.desiredUPS = desiredUPS;
        this.timeU = 1_000_000_000 / desiredUPS;
    }

    public int getCurrentUPS() {
        return globalUpdates;
    }

    public static KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public static MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
