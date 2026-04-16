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
	 * Draws the score display and center line.
	 * @param g Graphics context
	 */
	public void draw(Graphics g ) {
		g.setColor(Color.white);
		g.setFont(new Font("Consolas",Font.PLAIN,60));
		
		// Draw center line
		g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
		
		// Draw player scores
		g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), 
				(GAME_WIDTH/2)-85, 50);
		g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), 
				(GAME_WIDTH/2)+20, 50);

		
	}

}
