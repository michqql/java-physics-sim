package me.michael.physicssim.gfx;

import java.awt.image.BufferedImage;

public class Sprite extends Texture {

    private final SpriteSheet parentSheet;
    private final BufferedImage image;

    public Sprite(SpriteSheet parentSheet, BufferedImage image) {
        this.parentSheet = parentSheet;
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }
}
