package me.michael.physicssim.world;

import me.michael.physicssim.world.blocks.AirBlock;
import me.michael.physicssim.world.blocks.VoidBlock;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class World {

    public static final Random RANDOM = new Random();

    private final int worldWidth = 20, worldHeight = 20;
    private final Block[] blocks = new Block[worldWidth * worldHeight];
    private final AirBlock airBlock = new AirBlock(this);
    private final VoidBlock voidBlock = new VoidBlock(this);

    public Block getBlockAt(int x, int y) {
        // Invert y position, so y=0 is at bottom of world, and y=(worldHeight - 1) is at top of world
        int invertedY = worldHeight - 1 - y;

        // Convert x and y position into index
        int index = invertedY * worldWidth + x;
        // Check index is in array bounds
        if(index < 0 || index >= blocks.length) // invalid position, return void block
            return voidBlock;

        Block block = blocks[index];
        return block != null ? block : airBlock;
    }

    public void setBlockAt(int x, int y, Block block) {
        // Invert y position, so y=0 is at bottom of world, and y=(worldHeight - 1) is at top of world
        int invertedY = worldHeight - 1 - y;

        // Convert x and y position into index
        int index = invertedY * worldWidth + x;
        // Check index is in array bounds
        if(index < 0 || index >= blocks.length) // invalid position, return
            return;

        blocks[index] = block;
        if(block != null)
            block.setXY(x, y); // Update blocks position
    }

    boolean moveBlockRelative(Block block, int relX, int relY) {
        int newX = block.getX() + relX;
        // bound the new X position between 0 and (worldWidth - 1)
        newX = Math.max(0, Math.min(worldWidth - 1, newX));
        int newY = block.getY() + relY;
        int invertedY = worldHeight - 1 - newY;

        int index = invertedY * worldWidth + newX;
        if(index >= blocks.length) {
            // block is out of bounds (below the world)
            if(block.fallsOutOfWorld)
                // remove the block from where it was
                // as it has technically moved (fallen out of world)
                setBlockAt(block.getX(), block.getY(), null);
            return block.fallsOutOfWorld; // returns true if the block fell out of the world
        }
        if(index < 0)
            return false;

        // Only move block if the position is empty
        if(blocks[index] != null) // Position is not empty, return
            return false;

        setBlockAt(block.getX(), block.getY(), null);
        blocks[index] = block;
        block.setXY(newX, newY);
        return true;
    }

    public void createBlock(int x, int y, Class<? extends Block> type) {
        // Instantiate a new block object
        Constructor<? extends Block> blockConstructor;
        try {
            blockConstructor = type.getConstructor(World.class);
        } catch (NoSuchMethodException e) {
            return;
        }

        Block blockObject;
        try {
            blockObject = blockConstructor.newInstance(this);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            return;
        }

        setBlockAt(x, y, blockObject);
    }

    public void update() {
        for(Block block : blocks) {
            if(block != null && !block.updated) {
                block.physicsUpdate();
                block.updated = true;
            }
        }

        for(Block block : blocks) {
            if(block != null)
                block.updated = false;
        }
    }

    public void render(Graphics g) {
        for(int x = 0; x < worldWidth; x++) {
            for(int y = 0; y < worldHeight; y++) {
                int index = y * worldWidth + x;
                Block block = blocks[index];
                g.setColor(Color.WHITE);
                g.drawRect(200 + x * 16, 200 + y * 16, 16, 16);
                if(block == null)
                    continue;

                g.setColor(block.color);
                g.fillRect(200 + x * 16, 200 + y * 16, 16, 16);
            }
        }
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
}
