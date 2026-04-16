package Pong;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Represents the ball in the Pong game.
 * Handles movement, direction changes, and rendering.
 */
public class Ball  extends Rectangle {
	
	/** Random number generator for initial direction */
	Random random;
	/** Horizontal velocity */
	int xVelocity;
	/** Vertical velocity */
	int yVelocity;
	/** Initial speed of the ball */
	int initialSpeed = 3;

	/**
	 * Constructs a ball with random initial direction.
	 * @param x X position
	 * @param y Y position
	 * @param width Ball width
	 * @param height Ball height
	 */
	Ball(int x, int y, int width, int height){
		super(x,y,width,height);
		random = new Random();
		int randomXDirection = random.nextInt(2);
		if(randomXDirection == 0)
			randomXDirection--;
		setXDirection(randomXDirection*initialSpeed);
		
		int randomYDirection = random.nextInt(2);
		if(randomYDirection == 0)
			randomYDirection--;
		setYDirection(randomYDirection*initialSpeed);

		
	}
	
	/**
	 * Sets the horizontal movement direction.
	 * @param randomXDirection New X velocity
	 */
	public void setXDirection(int randomXDirection) {
		xVelocity = randomXDirection;

		
	}
	
	/**
	 * Sets the vertical movement direction.
	 * @param randomYDirection New Y velocity
	 */
	public void setYDirection(int randomYDirection) {
		yVelocity = randomYDirection;

		
	}
	
	/**
	 * Updates the ball position based on current velocity.
	 */
	public void move () {
		x += xVelocity;
		y += yVelocity;

		
	} 
	
	/**
	 * Draws the ball as a filled oval.
	 * @param g Graphics context
	 */
	public void draw(Graphics g ) {
		g.setColor(Color.white);
		g.fillOval(x, y, height, width);

		
	}

}
