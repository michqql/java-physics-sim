package me.michael.physicssim.world.blocks;

import me.michael.physicssim.world.Block;
import me.michael.physicssim.world.World;

import java.awt.*;
import java.util.*;
import java.util.List;

public class WoodBlock extends Block {

    public WoodBlock(World world) {
        super(world, Color.ORANGE);
    }

    @Override
    public void physicsUpdate() {
        Queue<Block> toVisit = new LinkedList<>();
        Set<Block> visited = new HashSet<>();
        boolean supported = false;

        toVisit.add(this);

        while(toVisit.size() > 0) {
            Block current = toVisit.poll();
            visited.add(current);

            // Get up, down, left, right blocks and add them to the visited queue
            // if one of these blocks is a supporting block, check the support flag
            List<Block> toCheck = new ArrayList<>();
            toCheck.add(current.getRelativeBlock(0, 1)); // up
            toCheck.add(current.getRelativeBlock(0, -1)); // down
            toCheck.add(current.getRelativeBlock(-1, 0)); // left
            toCheck.add(current.getRelativeBlock(1, 0)); // right

            for(Block check : toCheck) {
                if(check instanceof WoodBlock && !visited.contains(check)) {
                    toVisit.add(check);
                } else if(check instanceof StoneBlock) {
                    supported = true;
                }
            }
        }

        if(!supported) {
            // Go through all visited blocks and remove them
            for (Block block : visited) {
                block.remove();
            }
        } else {
            // Flag each block as being checked/updated this physics update tick
            for(Block block : visited) {
                block.markUpdated();
            }
        }
    }
}
