package me.michael.physicssim;

import me.michael.physicssim.input.KeyHandler;
import me.michael.physicssim.input.MouseHandler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Debugger implements IUpdate, IRender {

    private final static int DEBUG_OPTIONS = 10;
    private final static int DEBUG_ENABLE_KEY = KeyEvent.VK_F1;
    private final static int DEBUG_OPTION_LEFT = KeyEvent.VK_F2;
    private final static int DEBUG_OPTION_TOGGLE = KeyEvent.VK_F3;
    private final static int DEBUG_OPTION_RIGHT = KeyEvent.VK_F4;

    private final Game game;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    public Debugger(Game game, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.game = game;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    }

    private boolean enabled;
    private final boolean[] options = new boolean[DEBUG_OPTIONS];
    private int index;

    @Override
    public void update(double dt, Game game) {
        if(keyHandler.wasKeyPressed(DEBUG_ENABLE_KEY)) {
            enabled = !enabled;
        } else if(keyHandler.wasKeyPressed(DEBUG_OPTION_LEFT)) {
            index--;
            if(index < 0)
                index = options.length - 1;
        } else if(keyHandler.wasKeyPressed(DEBUG_OPTION_RIGHT)) {
            index++;
            if(index >= options.length)
                index = 0;
        } else if(keyHandler.wasKeyPressed(DEBUG_OPTION_TOGGLE)) {
            options[index] = !options[index];
        }
    }

    @Override
    public void render(Graphics g, Game game) {
        if(!enabled)
            return;

        displayFPS(g);
        drawDebugInfo(g);
    }

    private void displayFPS(Graphics g) {
        if(options[0]) {
            final String fpsUps = "FPS: " + game.getCurrentFPS() + ", UPS: " + game.getCurrentUPS();
            g.setColor(Color.WHITE);
            g.drawString(fpsUps, 1, 12);
        }
    }

    private void drawDebugInfo(Graphics g) {

    }
}
