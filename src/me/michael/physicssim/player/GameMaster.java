package me.michael.physicssim.player;

import me.michael.physicssim.input.KeyHandler;
import me.michael.physicssim.util.Blocks;
import me.michael.physicssim.world.Block;
import me.michael.physicssim.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameMaster {

    private final KeyHandler keyHandler;
    private final World world;

    // The position the player has selected
    private int selectedX, selectedY;

    // Delete mode will remove blocks instead of placing
    private boolean delete;

    // The block type to place
    private Class<? extends Block> selectedBlockType;
    private int index = 0;
    private final ArrayList<Class<? extends Block>> listOfBlockTypes = new ArrayList<>();
    private boolean fastPlace;

    public GameMaster(KeyHandler keyHandler, World world) {
        this.keyHandler = keyHandler;
        this.world = world;
        initBlockList();
    }

    private void initBlockList() {
        listOfBlockTypes.addAll(Blocks.getBlockTypes());
        selectedBlockType = listOfBlockTypes.get(index);
    }

    public void update() {
        if(keyHandler.isKeyDown(KeyEvent.VK_A)) {
            selectedX--;
        }
        if(keyHandler.isKeyDown(KeyEvent.VK_D)) {
            selectedX++;
        }
        if(keyHandler.isKeyDown(KeyEvent.VK_W)) {
            selectedY--;
        }
        if(keyHandler.isKeyDown(KeyEvent.VK_S)) {
            selectedY++;
        }

        selectedX = Math.max(0, Math.min(world.getWorldWidth() - 1, selectedX));
        selectedY = Math.max(0, Math.min(world.getWorldHeight() - 1, selectedY));

        if((fastPlace && keyHandler.isKeyDown(KeyEvent.VK_SPACE)) || (!fastPlace && keyHandler.wasKeyReleased(KeyEvent.VK_SPACE))) {
            // invert y first
            int invertedY = world.getWorldHeight() - 1 - selectedY;
            if(delete) {
                world.setBlockAt(selectedX, invertedY, null);
            } else {
                world.createBlock(selectedX, invertedY, selectedBlockType);
            }
        }

        if(keyHandler.wasKeyReleased(KeyEvent.VK_RIGHT)) {
            index++;
            if(index >= listOfBlockTypes.size())
                index = 0;

            selectedBlockType = listOfBlockTypes.get(index);
        }

        if(keyHandler.wasKeyReleased(KeyEvent.VK_UP)) {
            fastPlace = !fastPlace;
        }

        if(keyHandler.wasKeyReleased(KeyEvent.VK_DOWN)) {
            delete = !delete;
        }
    }

    public void render(Graphics g) {
        g.setColor(delete ? Color.RED : Color.GREEN);
        g.drawRect(200 + selectedX * 16, 200 + selectedY * 16, 16, 16);
    }
}
