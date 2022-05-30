package com.company;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Visualizer extends JFrame{


    public Visualizer(){
        this.add(new displayArray());
        this.setTitle("Sorting Algorithm");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        Visualizer visualizer = new Visualizer();
    }
}
