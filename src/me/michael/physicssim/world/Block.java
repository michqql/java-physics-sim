package me.michael.physicssim.world;

import me.michael.physicssim.world.blocks.AirBlock;
import me.michael.physicssim.world.blocks.VoidBlock;

import java.awt.*;

public abstract class Block {

    protected final World world;
    protected final Color color;
    protected boolean updated = false;

    protected boolean fallsOutOfWorld;

    // The position of this block
    // This can only be set via the package-private method setXY
    private int x, y;

    protected Block(World world, Color color) {
        this.world = world;
        this.color = color;
    }

    // Only the World should use this method to update this blocks location
    void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Should only be called by subclasses, used to move this block
    protected void moveBlock(int relX, int relY) {
        world.moveBlockRelative(this, relX, relY);
    }

    public Block getRelativeBlock(int relX, int relY) {
        return world.getBlockAt(x + relX, y + relY);
    }

    public void remove() {
        world.setBlockAt(x, y, null);
    }

    public abstract void physicsUpdate();
    public void markUpdated() {
        this.updated = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAirOrVoid() {
        return this instanceof AirBlock || this instanceof VoidBlock;
    }
}
