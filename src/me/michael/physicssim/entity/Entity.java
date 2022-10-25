package me.michael.physicssim.entity;

import me.michael.physicssim.Game;
import me.michael.physicssim.input.KeyHandler;
import me.michael.physicssim.world.World;

abstract class Entity {

    protected final KeyHandler keyHandler = Game.getKeyHandler();

    protected long entityId;
    protected World world;
    protected int x, y;

    public Entity(long entityId, World world, int x, int y) {
        this.entityId = entityId;
        this.world = world;
        this.x = x;
        this.y = y;
    }

    public long getEntityId() {
        return entityId;
    }

    public World getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
