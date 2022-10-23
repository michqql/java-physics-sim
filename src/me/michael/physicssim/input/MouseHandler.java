package me.michael.physicssim.input;

import me.michael.physicssim.IUpdate;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener, IUpdate {

    private final int numButtons = 256;
    private final int[] longHoldButtons = new int[numButtons];
    private boolean[] lastButtons = new boolean[numButtons];
    private final boolean[] buttons = new boolean[numButtons];

    private int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isButtonDown(int code) {
        if(code < 0 || code >= numButtons)
            return false;
        return buttons[code];
    }

    public boolean wasButtonPressed(int code) {
        if(code < 0 || code >= numButtons)
            return false;
        return buttons[code] && !lastButtons[code];
    }

    public boolean wasButtonReleased(int code) {
        if(code < 0 || code >= numButtons)
            return false;
        return !buttons[code] && lastButtons[code];
    }

    public int getTicksKeyHeld(int code) {
        if(code < 0 || code >= numButtons)
            return -1;
        return longHoldButtons[code];
    }

    @Override
    public void update() {
        this.lastButtons = buttons;
        for(int code = 0; code < buttons.length; code++) {
            if(buttons[code])
                longHoldButtons[code]++;
        }
    }

    // unused method
    @Override
    public void render(Graphics g) {}

    // Button presses
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();
        if(code >= numButtons)
            return;

        buttons[code] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();
        if(code >= numButtons)
            return;

        buttons[code] = false;
        longHoldButtons[code] = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Motion
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }
}
