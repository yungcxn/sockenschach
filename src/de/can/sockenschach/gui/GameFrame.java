package de.can.sockenschach.gui;

import de.can.sockenschach.Sockenschach;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame {

    public GamePane gamePane;

    public GameFrame(String title){
        super(title);
        gamePane = new GamePane();

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                Sockenschach.instance.running = false;
                System.exit(0);
            }
        });

    }

    public void run(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }


        this.setLayout(new BorderLayout());
        this.add(gamePane);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);

    }



}
