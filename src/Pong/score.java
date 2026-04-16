package Pong;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Handles scoring display and tracking for the Pong game.
 * Shows player scores and center line.
 */
public class score   extends Rectangle{
	/** Game width */
	static int GAME_WIDTH;
	/** Game height */
	static int GAME_HEIGHT;
	/** Player 1 score */
	int player1;
	/** Player 2 score */
	int player2;
	/** Player 1 name */
	String player1Name = "Player 1";
	/** Player 2 name */
	String player2Name = "Player 2";
	
	/**
	 * Constructs a score tracker with game dimensions.
	 * @param GAME_WIDTH Width of game area
	 * @param GAME_HEIGHT Height of game area
	 */
	score(int GAME_WIDTH, int GAME_HEIGHT){
		score.GAME_WIDTH = GAME_WIDTH;
		score.GAME_HEIGHT = GAME_HEIGHT;

		
	}
	
	

	score(){
		
	}
	
	/**
	 * Sets the names for both players.
	 * @param p1Name Name for player 1
	 * @param p2Name Name for player 2
	 */
	public void setPlayerNames(String p1Name, String p2Name) {
		this.player1Name = p1Name;
		this.player2Name = p2Name;
	}
	
	/**
	 * Draws the score display and center line.
	 * @param g Graphics context
	 */
	public void draw(Graphics g ) {
		g.setColor(Color.white);
		
		// Draw center line
		g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
		
		// Draw player names
		g.setFont(new Font("Consolas",Font.PLAIN,20));
		g.drawString(player1Name, (GAME_WIDTH/2)-85, 25);
		g.drawString(player2Name, (GAME_WIDTH/2)+20, 25);
		
		// Draw player scores
		g.setFont(new Font("Consolas",Font.PLAIN,60));
		g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), 
				(GAME_WIDTH/2)-85, 70);
		g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), 
				(GAME_WIDTH/2)+20, 70);

		
	}

}
