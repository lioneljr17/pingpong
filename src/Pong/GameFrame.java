package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame {
	
	GamePanel panel;

	GameFrame(){
		panel = new GamePanel();
		this.add(panel);
		this.setTitle("pong time");
		this.setResizable(true);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setState(Frame.ICONIFIED);
		// this.setState(Frame.MAXIMIZED_BOTH);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		System.out.println("work");
		
	}

}
