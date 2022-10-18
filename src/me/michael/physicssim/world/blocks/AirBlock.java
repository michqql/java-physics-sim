package me.michael.physicssim.world.blocks;

import me.michael.physicssim.world.Block;
import me.michael.physicssim.world.World;

public class AirBlock extends Block {

    public AirBlock(World world, int x, int y) {
        super(world, x, y);
    }

    @Override
    public void updatePhysics() {
        // air has no physics!
    }
}
