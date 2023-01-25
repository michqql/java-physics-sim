package me.michael.physicssim.util;

import me.michael.physicssim.world.Block;
import me.michael.physicssim.world.blocks.AirBlock;
import me.michael.physicssim.world.blocks.SandBlock;
import me.michael.physicssim.world.blocks.StoneBlock;
import me.michael.physicssim.world.blocks.WoodBlock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Blocks {

    private static final Set<Class<? extends Block>> BLOCK_TYPES;

    static {
        BLOCK_TYPES = new HashSet<>();
        BLOCK_TYPES.addAll(Arrays.asList(
                AirBlock.class,
                StoneBlock.class,
                SandBlock.class,
                WoodBlock.class
        ));
    }

    public static Set<Class<? extends Block>> getBlockTypes() {
        return BLOCK_TYPES;
    }
}
