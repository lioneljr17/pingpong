package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Main entry point for the Pong game application.
 * Initializes the game on the Event Dispatch Thread for proper Swing handling.
 */
public class PongGame1 {

	/**
	 * Launches the Pong game by creating the game frame on the EDT.
	 * @param args Command line arguments (not used)
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameFrame();
			}
		});
	}

}
