package me.michael.physicssim.world;

import me.michael.physicssim.IRender;
import me.michael.physicssim.IUpdate;
import me.michael.physicssim.gfx.Texture;

import java.awt.*;

public abstract class Block implements IUpdate, IRender {

    protected final Texture texture;

    // Location
    protected final World world;
    protected Chunk chunk;
    protected int x, y;

    public Block(Texture texture, World world) {
        this.texture = texture;
        this.world = world;
    }
}
