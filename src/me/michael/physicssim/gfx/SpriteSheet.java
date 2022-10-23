package me.michael.physicssim.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SpriteSheet {

    private final String path;
    private final int width, height;
    private final BufferedImage image;

    private int spriteWidth, spriteHeight;

    public SpriteSheet(String path) {
        this.path = path;
        // load sprite sheet image
        try {
            InputStream in = SpriteSheet.class.getResourceAsStream(path);
            if(in == null) {
                throw new RuntimeException("Could not find image at location: " + path);
            }
            this.image = ImageIO.read(in);
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e) {
            throw new RuntimeException("Could not find image at location: " + path, e);
        }
    }
}
