package me.michael.physicssim.world.blocks;

import me.michael.physicssim.world.Block;
import me.michael.physicssim.world.World;

import java.awt.*;

public class AirBlock extends Block {

    public AirBlock(World world) {
        super(world, new Color(0f, 0f, 0f, 0f));
    }

    @Override
    public void physicsUpdate() {}
}
