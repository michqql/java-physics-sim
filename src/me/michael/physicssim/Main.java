package me.michael.physicssim;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndOpenWindow);
    }

    public static void createAndOpenWindow() {
        JFrame frame = new JFrame("Physics Sim");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Game game = new Game();
        frame.add(game);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }
}
