package me.michael.physicssim;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Window {

    private final String title;
    private final int width, height;

    private JFrame frame;
    private Canvas canvas;

    private BufferedImage image;
    private int[] pixels;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        this.frame = new JFrame(title);
        this.canvas = new Canvas();
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        final Dimension dim = new Dimension(width, height);
        frame.setPreferredSize(dim);
        canvas.setPreferredSize(dim);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();

        frame.setVisible(true);
    }

    public BufferStrategy getBufferStrategy() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null)
            canvas.createBufferStrategy(2);
        return bs;
    }
}
