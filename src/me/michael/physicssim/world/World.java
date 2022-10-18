package me.michael.physicssim.world;

import me.michael.physicssim.IUpdate;
import me.michael.physicssim.world.blocks.AirBlock;
import me.michael.physicssim.world.blocks.SandBlock;

import java.awt.*;

public class World implements IUpdate {

    private final int blocksPerChunk = 16;
    private final int width, height, pixelsPerBlock;
    private Block[] blocks;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixelsPerBlock = 32;
        this.blocks = new Block[width * height];
    }

    public void update() {
        Block[] updated = new Block[blocks.length];

        for(int i = blocks.length - 1; i >= 0; i--) {
            Block block = blocks[i];
            if(block == null)
                continue;

            block.updatePhysics();
            int newX = block.x;
            int newY = block.y;
            int newIndex = newY * blocksPerChunk + newX;

            updated[newIndex] = blocks[i];
        }
        blocks = updated;
    }

    public void render(Graphics g) {
        for(int i = blocks.length - 1; i >= 0; i--) {
            Block block = blocks[i];
            if(block == null) {
                g.setColor(Color.WHITE);
                int x = i % width;
                int y = i / width;
                g.drawRect(x * pixelsPerBlock, y * pixelsPerBlock, pixelsPerBlock, pixelsPerBlock);
                g.drawString(String.valueOf(i), x * pixelsPerBlock + 1, y * pixelsPerBlock + 1);
                continue;
            }

            g.setColor(block.color);
            g.fillRect(block.x * pixelsPerBlock, block.y * pixelsPerBlock, pixelsPerBlock, pixelsPerBlock);
        }

//        for(Block block : blocks) {
//            if(block == null)
//                continue;
//
//            int x = block.x * 16;
//            int y = block.y * 16;
//            g.setColor(block.color);
//            g.fillRect(x, y, 16, 16);
//        }
    }

    public Block getBlockAt(int x, int y) {
        int index = y * blocksPerChunk + x;
        if(index < 0 || index >= blocks.length)
            return null;

        Block block = blocks[index];
        if(block == null)
            block = blocks[index] = new AirBlock(this, x, y);

        return block;
    }

    public void createSandBlockAt(int x, int y) {
        int index = y * blocksPerChunk + x;
        if(index < 0 || index >= blocks.length)
            return;

        SandBlock sand = new SandBlock(this, x, y);
        blocks[index] = sand;
    }
}
