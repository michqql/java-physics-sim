package me.michael.physicssim;

import me.michael.physicssim.input.KeyHandler;
import me.michael.physicssim.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Camera implements IUpdate, IRender {

    /*

    New Velocity = old_velocity * (1 - delta_time * transition_speed) + desired_velocity * (delta_time * transition_speed)

    transition_speed is one of a few constants (based on terrain type) largely simulated friction,
                     water/ground has a transition speed of 4.0,
                     and to have a smidge of air control, the air transition speed is 0.7.

    desired_velocity is a unit length "running force" (whatever the entity is trying to do) multiplied by the
                     characters max speed for what they're standing on, e.g. higher for land than water.

     */

    private final KeyHandler keyHandler;

    private World world;

    private double x, y; // position
    private double vx, vy; // velocity
    private double ax, ay; // acceleration

    private double friction = 50;
    private double movementSpeed = 100.0;

    public Camera(KeyHandler keyHandler, World world) {
        this.keyHandler = keyHandler;
        this.world = world;
    }

    @Override
    public void update(double dt) {
        // Implementation 2
        // New Velocity = old_velocity * (1 - delta_time * transition_speed) + desired_velocity * (delta_time * transition_speed)
        ax = 0;
        ay = 0;
        if(keyHandler.isKeyDown(KeyEvent.VK_W)) // move up, decrease y value
            ay -= movementSpeed;

        if(keyHandler.isKeyDown(KeyEvent.VK_S))
            ay += movementSpeed;

        if(keyHandler.isKeyDown(KeyEvent.VK_A))
            ax -= movementSpeed;

        if(keyHandler.isKeyDown(KeyEvent.VK_D))
            ax += movementSpeed;

        vx = vx * (1 - friction * dt) + ax * (friction * dt);
        vy = vy * (1 - friction * dt) + ay * (friction * dt);

        x += vx * dt;
        y += vy * dt;

        // Implementation 2
//        ax = keyHandler.getTicksKeyHeld(KeyEvent.VK_D) - keyHandler.getTicksKeyHeld(KeyEvent.VK_A);
//        ay = keyHandler.getTicksKeyHeld(KeyEvent.VK_S) - keyHandler.getTicksKeyHeld(KeyEvent.VK_W);
//
//        // v = u + at
//        vx = vx + (ax * dt);
//        vy = vy + (ay * dt);
//
//        // s = ut + (1/2)at^2
//        x += (vx * dt) + ((1 / 2D) * ax * (dt * dt));
//        y += (vy * dt) + ((1 / 2D) * ay * (dt * dt));

        // Implementation 1
//        if(keyHandler.isKeyDown(KeyEvent.VK_W)) // move up, decrease y value
//            y--;
//
//        if(keyHandler.isKeyDown(KeyEvent.VK_S))
//            y++;
//
//        if(keyHandler.isKeyDown(KeyEvent.VK_A))
//            x--;
//
//        if(keyHandler.isKeyDown(KeyEvent.VK_D))
//            x++;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect((int) x, (int) y, 1, 1);

        // Implementation 2
        g.setColor(Color.WHITE);
        g.drawString("ax: " + ax + ", ay: " + ay, 1, 100);
        g.drawString("vx: " + vx + ", vy: " + vy, 1, 120);
        g.drawString(" x: " +  x + ",  y: " +  y, 1, 140);
    }
}
