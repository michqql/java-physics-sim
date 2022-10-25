package me.michael.physicssim.entity;

import me.michael.physicssim.Game;
import me.michael.physicssim.IRender;
import me.michael.physicssim.IUpdate;
import me.michael.physicssim.debug.DebugWindow;
import me.michael.physicssim.util.math.Vector2;
import me.michael.physicssim.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity implements IUpdate, IRender {

    private final static double sqrt2 = Math.sqrt(2.0D);

    // Debugger
    private final DebugWindow debug = new DebugWindow("Player Debug", 400, 100) {
        @Override
        public void render(Graphics g) {
            g.setColor(Color.WHITE);
            g.drawString("x: " + Player.this.x + ", y: " + Player.this.y, 1, 12);
            g.drawString("y: " + Player.this.y, 200, 12);

            g.drawString("dx: " + Player.this.dx, 1, 24);
            g.drawString("dy: " + Player.this.dy, 200, 24);
        }
    };

    // Movement
    private final double speed = 200;
    private double dx, dy;

    public Player(long entityId, World world, int x, int y) {
        super(entityId, world, x, y);
        debug.startDebug();
    }

    @Override
    public void update(double dt, Game game) {
        dx = 0;
        dy = 0;

        if(keyHandler.isKeyDown(KeyEvent.VK_W))
            dy += (y - speed * dt) - y;
        if(keyHandler.isKeyDown(KeyEvent.VK_S))
            dy += (y + speed * dt) - y;
        if(keyHandler.isKeyDown(KeyEvent.VK_A))
            dx += (x - speed * dt) - x;
        if(keyHandler.isKeyDown(KeyEvent.VK_D))
            dx += (x + speed * dt) - x;

        if(dy != 0 && dx != 0) {
            dy /= sqrt2;
            dx /= sqrt2;
        }

        x += dx;
        y += dy;
    }

    @Override
    public void render(Graphics g, Game game) {
        g.setColor(Color.GREEN);
        g.drawRect(x, y, 1, 1);
    }
}
