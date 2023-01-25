package me.michael.physicssim;

import me.michael.physicssim.input.KeyHandler;
import me.michael.physicssim.input.MouseHandler;
import me.michael.physicssim.player.GameMaster;
import me.michael.physicssim.world.World;
import me.michael.physicssim.world.blocks.SandBlock;
import me.michael.physicssim.world.blocks.StoneBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

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

    // World
    private final World world = new World();
    private final GameMaster gameMaster;

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

        this.gameMaster = new GameMaster(keyHandler, world);

        world.setBlockAt(4, 0, new StoneBlock(world));
        world.setBlockAt(5, 0, new StoneBlock(world));
        world.setBlockAt(6, 0, new StoneBlock(world));
    }

    public void start() {
        if(running)
            return;

        setDesiredFPS(60.0);
        setDesiredUPS(20.0);

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
        gameMaster.update();

        keyHandler.update();
        mouseHandler.update();
    }

    private void render() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final String fpsUps = "FPS: " + globalFrames + ", UPS: " + globalUpdates;
        final String position = "X: " + mouseHandler.getX() + ", Y: " + mouseHandler.getY();
        final String aKeyHeld = "A: " + (keyHandler.isKeyDown(KeyEvent.VK_A) ? "pressed" : "released") +
                ", length: " + keyHandler.getTicksKeyHeld(KeyEvent.VK_A);

        g.setColor(Color.WHITE);
        g.drawString(fpsUps, 1, 12);
        g.drawString(position, 1, 25);
        g.drawString(aKeyHeld, 1, 38);

        world.render(g);
        gameMaster.render(g);

        g.setColor(Color.BLACK);

        g.dispose();
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
