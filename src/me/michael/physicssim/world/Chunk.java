package me.michael.physicssim.world;

import me.michael.physicssim.Game;
import me.michael.physicssim.IRender;
import me.michael.physicssim.IUpdate;

import java.awt.*;

import static me.michael.physicssim.world.World.TILES_PER_CHUNK_SIDE;
import static me.michael.physicssim.world.World.TILES_PER_CHUNK;

public class Chunk implements IUpdate, IRender {

    private final long chunkX, chunkY;
    private final Block[] blocks = new Block[TILES_PER_CHUNK];

    public Chunk(int chunkX, int chunkY) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    @Override
    public void render(Graphics g, Game game) {
        final int len = Game.TILE_SIZE;
        final int offset = TILES_PER_CHUNK_SIDE * len;
        final int xOffset = (int) (chunkX * offset - game.getPlayer().getX());
        final int yOffset = (int) (chunkY * offset - game.getPlayer().getY());

        for(int i = 0; i < blocks.length; i++) {
            int x = i % TILES_PER_CHUNK_SIDE;
            int y = i / TILES_PER_CHUNK_SIDE;

            g.setColor(Color.WHITE);
            g.drawRect(xOffset + x * len, yOffset + y * len, Game.TILE_SIZE, Game.TILE_SIZE);
        }
    }

    @Override
    public void update(double dt, Game game) {

    }

    public long getChunkX() {
        return chunkX;
    }

    public long getChunkY() {
        return chunkY;
    }

    /**
     *
     * @param relX between 0 (inclusive) and maximum tiles per chunk side (exclusive)
     * @param relY between 0 (inclusive) and maximum tiles per chunk side (exclusive)
     * @return the block at the given position
     */
    public Block getBlockAt(int relX, int relY) {
        int index = relY * TILES_PER_CHUNK_SIDE + relX;
        return blocks[index];
    }

    public void setBlockAt(int relX, int relY, Block block) {
        int index = relY * TILES_PER_CHUNK_SIDE + relX;
        blocks[index] = block;
    }
}
