package me.michael.physicssim.debug;

import javax.swing.*;
import java.awt.*;

public abstract class DebugWindow extends JPanel implements Runnable {

    private JFrame frame;
    private Thread thread;
    private boolean running;

    public DebugWindow(String debugTitle, int width, int height) {
        SwingUtilities.invokeLater(() -> {
            this.frame = new JFrame(debugTitle);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(true);

            super.setPreferredSize(new Dimension(width, height));
            super.setBackground(Color.BLACK);
            super.setDoubleBuffered(true);
            super.setFocusable(false);

            frame.add(this);
            frame.pack();

            frame.setFocusable(false);
            frame.setLocation(0, 0);
            frame.setVisible(true);
        });
    }

    public void startDebug() {
        if(running)
            return;

        running = true;
        this.thread = new Thread(this);
        thread.start();
    }

    public void stopDebug() {
        if(!running)
            return;

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        final double desiredUpdates = 60.0D;
        final double time = 1_000_000_000 / desiredUpdates;
        long initialTime = System.nanoTime();
        double delta = 0;

        while (running) {

            long currentTime = System.nanoTime();
            delta += (currentTime - initialTime) / time;
            initialTime = currentTime;

            if (delta >= 1)
                repaint();
        }
    }

    public abstract void render(Graphics g);

    @Override
    protected final void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }
}
