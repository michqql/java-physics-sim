package me.michael.physicssim.world;

import me.michael.physicssim.Game;
import me.michael.physicssim.IRender;
import me.michael.physicssim.IUpdate;
import me.michael.physicssim.entity.Player;

import java.awt.*;

public class World implements IUpdate, IRender {

    public static final int TILES_PER_CHUNK_SIDE = 16;
    public static final int TILES_PER_CHUNK = TILES_PER_CHUNK_SIDE * TILES_PER_CHUNK_SIDE;
    private static final int VIEW_DISTANCE = 3;

    private boolean allowRetrievalFromUnloadedChunks = false;

    private final int centerIndex = (VIEW_DISTANCE * VIEW_DISTANCE) / 2;
    private Chunk[] chunks = new Chunk[VIEW_DISTANCE * VIEW_DISTANCE];
    private long chunkX, chunkY;

    public World() {
        for(int i = 0; i < chunks.length; i++) {
            int x = i % VIEW_DISTANCE;
            int y = i / VIEW_DISTANCE;

            chunks[i] = new Chunk(x, y);
        }
    }

    @Override
    public void update(double dt, Game game) {
    }

    @Override
    public void render(Graphics g, Game game) {
        final int offset = TILES_PER_CHUNK_SIDE * Game.TILE_SIZE;
        for(Chunk chunk : chunks) {
            chunk.render(g, game);

            g.setColor(Color.RED);
            int x = (int) (chunk.getChunkX() * offset - game.getPlayer().getX() - (game.getWidth() / 2));
            int y = (int) (chunk.getChunkY() * offset - game.getPlayer().getY() - (game.getHeight() / 2));
            g.drawRect(x, y, offset, offset);

            g.drawString(chunk.getChunkX() + ", " + chunk.getChunkY(), x + 2, y + 13);
        }
    }

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

    public Player spawnPlayer() {
        return new Player(0, this, 0, 0);
    }
}
