package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Main window frame for the Pong game.
 * Contains the game panel and control buttons.
 */
public class GameFrame extends JFrame {
	
	/** The main game panel where gameplay occurs */
	GamePanel panel;
	/** Button to start the game */
	JButton startButton;
	/** Button to stop the game */
	JButton stopButton;

	/**
	 * Constructs the game frame with buttons and game panel.
	 * Sets up the UI layout and event handlers.
	 */
	GameFrame(){
		// Create the game panel
		panel = new GamePanel();
		
		// Create control buttons
		JPanel buttonPanel = new JPanel();
		startButton = new JButton("Start Game");
		stopButton = new JButton("Stop Game");
		stopButton.setEnabled(false); // Initially disabled
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		
		// Set up layout: buttons on top, game panel in center
		this.setLayout(new BorderLayout());
		this.add(buttonPanel, BorderLayout.NORTH);
		this.add(panel, BorderLayout.CENTER);
		
		// Add button event listeners
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
		
		// Configure window properties
		this.setTitle("pong time");
		this.setResizable(true);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null); // Center on screen
		
	}

}
