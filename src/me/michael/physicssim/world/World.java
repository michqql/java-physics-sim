package me.michael.physicssim.world;

public class World {

    public static final int TILES_PER_CHUNK_SIDE = 16;
    public static final int TILES_PER_CHUNK = TILES_PER_CHUNK_SIDE * TILES_PER_CHUNK_SIDE;
    private static final int VIEW_DISTANCE = 3;

    private boolean allowRetrievalFromUnloadedChunks = false;

    private final int centerIndex = (VIEW_DISTANCE * VIEW_DISTANCE) / 2;
    private Chunk[] chunks = new Chunk[VIEW_DISTANCE * VIEW_DISTANCE];
    private long chunkX, chunkY;

    public Chunk getChunkAt(int x, int y) {
        // is this position within the loaded area?
        int dist = VIEW_DISTANCE / 2;
        long dx = chunkX - x;
        long dy = chunkY - y;
        if(Math.abs(dx) <= dist && Math.abs(dy) <= dist) {
            int index = (int) (centerIndex - (dy * VIEW_DISTANCE + dx));
            return chunks[index];
        }

        // the chunk is not within the loaded area
        if(allowRetrievalFromUnloadedChunks) {
            // load chunk at x, y

        }
        return null;
    }
}
