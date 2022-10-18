package me.michael.physicssim.world.blocks;

import me.michael.physicssim.world.Block;
import me.michael.physicssim.world.World;

import java.awt.*;

public class SandBlock extends Block {

    public SandBlock(World world, int x, int y) {
        super(world, x, y);
        this.color = Color.YELLOW;
    }

    @Override
    public void updatePhysics() {
        Block block = world.getBlockAt(x, y - 1);
        if(block == null)
            return; // This is not a valid position

        if(block instanceof AirBlock) {
            // There is space to move down
            this.y--;
        }
    }
}
