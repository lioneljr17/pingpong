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
	/** Text field for player 1 name */
	JTextField player1Field;
	/** Text field for player 2 name */
	JTextField player2Field;
	/** Radio button for 1-player mode */
	JRadioButton singlePlayerButton;
	/** Radio button for 2-player mode */
	JRadioButton twoPlayerButton;

	/**
	 * Constructs the game frame with buttons and game panel.
	 * Sets up the UI layout and event handlers.
	 */
	GameFrame(){
		// Create the game panel
		panel = new GamePanel();
		
		// Create mode selection panel
		JPanel modePanel = new JPanel();
		modePanel.setBorder(BorderFactory.createTitledBorder("Game Mode"));
		ButtonGroup modeGroup = new ButtonGroup();
		
		singlePlayerButton = new JRadioButton("1 Player (vs Computer)", true);
		twoPlayerButton = new JRadioButton("2 Players");
		modeGroup.add(singlePlayerButton);
		modeGroup.add(twoPlayerButton);
		
		modePanel.add(singlePlayerButton);
		modePanel.add(twoPlayerButton);
		
		// Create input panel for player names
		JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		inputPanel.setBorder(BorderFactory.createTitledBorder("Player Names"));
		
		inputPanel.add(new JLabel("Player 1:"));
		player1Field = new JTextField("Player 1", 10);
		inputPanel.add(player1Field);
		
		inputPanel.add(new JLabel("Player 2:"));
		player2Field = new JTextField("Computer", 10);
		player2Field.setEnabled(false); // Disabled for single player
		inputPanel.add(player2Field);
		
		// Create control buttons
		JPanel buttonPanel = new JPanel();
		startButton = new JButton("Start Game");
		stopButton = new JButton("Stop Game");
		stopButton.setEnabled(false); // Initially disabled
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		
		// Combine all control panels
		JPanel controlPanel = new JPanel(new BorderLayout());
		controlPanel.add(modePanel, BorderLayout.NORTH);
		controlPanel.add(inputPanel, BorderLayout.CENTER);
		controlPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		// Set up layout: controls on top, game panel in center
		this.setLayout(new BorderLayout());
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(panel, BorderLayout.CENTER);
		
		// Add radio button listeners
		singlePlayerButton.addActionListener(e -> {
			player2Field.setText("Computer");
			player2Field.setEnabled(false);
			panel.setGameMode(true);
		});
		twoPlayerButton.addActionListener(e -> {
			player2Field.setText("Player 2");
			player2Field.setEnabled(true);
			panel.setGameMode(false);
		});
		
		// Add button event listeners
		startButton.addActionListener(e -> {
			String p1Name = player1Field.getText().trim();
			String p2Name = player2Field.getText().trim();
			if (p1Name.isEmpty() || p2Name.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please enter names for both players!", "Missing Names", JOptionPane.WARNING_MESSAGE);
				return;
			}
			panel.setPlayerNames(p1Name, p2Name);
			panel.startGame();
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			player1Field.setEnabled(false);
			player2Field.setEnabled(false);
			singlePlayerButton.setEnabled(false);
			twoPlayerButton.setEnabled(false);
		});
		stopButton.addActionListener(e -> {
			panel.stopGame();
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			player1Field.setEnabled(true);
			if (twoPlayerButton.isSelected()) {
				player2Field.setEnabled(true);
			}
			singlePlayerButton.setEnabled(true);
			twoPlayerButton.setEnabled(true);
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
