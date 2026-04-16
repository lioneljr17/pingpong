package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame {
	
	GamePanel panel;
	JButton startButton, stopButton;

	GameFrame(){
		panel = new GamePanel();
		
		JPanel buttonPanel = new JPanel();
		startButton = new JButton("Start Game");
		stopButton = new JButton("Stop Game");
		stopButton.setEnabled(false);
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		
		this.setLayout(new BorderLayout());
		this.add(buttonPanel, BorderLayout.NORTH);
		this.add(panel, BorderLayout.CENTER);
		
		startButton.addActionListener(e -> {
			panel.startGame();
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
		});
		stopButton.addActionListener(e -> {
			panel.stopGame();
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
		});
		
		this.setTitle("pong time");
		this.setResizable(true);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}

}
