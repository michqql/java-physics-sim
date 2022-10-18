package me.michael.physicssim.world;

import java.awt.*;

public abstract class Block {

    protected final World world;
    protected int x, y;
    protected Color color = new Color(0xffffffff, true);

    public Block(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;
    }

    public abstract void updatePhysics();
}
