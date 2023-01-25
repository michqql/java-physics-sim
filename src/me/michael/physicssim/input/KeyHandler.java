package me.michael.physicssim.input;

import me.michael.physicssim.IUpdate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener, IUpdate {

    private final int numKeys = 256;
    private final int[] longHoldKeys = new int[numKeys];
    private boolean[] lastKeys = new boolean[numKeys];
    private final boolean[] keys = new boolean[numKeys];

    public boolean isKeyDown(int keyCode) {
        if(keyCode < 0 || keyCode >= numKeys)
            return false;
        return keys[keyCode];
    }

    public boolean wasKeyPressed(int keyCode) {
        if(keyCode < 0 || keyCode >= numKeys)
            return false;
        return keys[keyCode] && !lastKeys[keyCode];
    }

    public boolean wasKeyReleased(int keyCode) {
        if(keyCode < 0 || keyCode >= numKeys)
            return false;
        return !keys[keyCode] && lastKeys[keyCode];
    }

    public int getTicksKeyHeld(int keyCode) {
        if(keyCode < 0 || keyCode >= numKeys)
            return -1;
        return longHoldKeys[keyCode];
    }

    @Override
    public void update() {
        System.arraycopy(keys, 0, lastKeys, 0, keys.length);
        for(int keyCode = 0; keyCode < keys.length; keyCode++) {
            if(keys[keyCode])
                longHoldKeys[keyCode]++;
        }
    }

    // unused method
    @Override
    public void render(Graphics g) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() >= numKeys)
            return;

        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() >= numKeys)
            return;

        keys[e.getKeyCode()] = false;
        longHoldKeys[e.getKeyCode()] = 0;
    }

    // unused method
    @Override
    public void keyTyped(KeyEvent e) {}
}
