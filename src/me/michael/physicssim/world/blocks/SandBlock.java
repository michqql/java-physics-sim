package me.michael.physicssim.world.blocks;

import me.michael.physicssim.world.Block;
import me.michael.physicssim.world.World;

import java.awt.*;

public class SandBlock extends Block {

    public SandBlock(World world) {
        super(world, Color.YELLOW);
        super.fallsOutOfWorld = true;
    }

    @Override
    public void physicsUpdate() {
        tryFall();
    }

    private void tryFall() {
        // Check if there is a block below this position
        if(getRelativeBlock(0, -1).isAirOrVoid()) {
            // there is no block below this position, so can update
            moveBlock(0, -1);
            return;
        }

        // Otherwise, check if we can move to the side and down
        int relX = World.RANDOM.nextBoolean() ? -1 : 1;
        if(getRelativeBlock(relX, 0).isAirOrVoid() && getRelativeBlock(relX, -1).isAirOrVoid()) {
            moveBlock(relX, -1);
            return;
        }

        // Try the other x direction
        relX = -relX;
        if(getRelativeBlock(relX, 0).isAirOrVoid() && getRelativeBlock(relX, -1).isAirOrVoid()) {
            moveBlock(relX, -1);
        }
    }
}
