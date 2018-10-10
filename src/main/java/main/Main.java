package main;
import javax.swing.JFrame;

import pkg.GUI;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI("untitled");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gui.setBounds(0, 0, 870, 500);
        gui.setVisible(true);
    }
}