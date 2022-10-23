package me.michael.physicssim.world;

import static me.michael.physicssim.world.World.TILES_PER_CHUNK_SIDE;

public class Chunk {

    private final long chunkX, chunkY;
    private final Block[] blocks = new Block[World.TILES_PER_CHUNK];

    public Chunk(int chunkX, int chunkY) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    /**
     *
     * @param relX between 0 (inclusive) and maximum tiles per chunk side (exclusive)
     * @param relY between 0 (inclusive) and maximum tiles per chunk side (exclusive)
     * @return
     */
    public Block getBlockAt(int relX, int relY) {
        int index = relY * TILES_PER_CHUNK_SIDE + relX;
        return blocks[index];
    }
}
