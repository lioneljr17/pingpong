package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Represents a paddle in the Pong game.
 * Handles movement and rendering of the paddle.
 */
public class Paddle  extends Rectangle{
	
	/** Player identifier (1 for left, 2 for right) */
	int id;
	/** Current vertical velocity */
	int yVelocity;
	/** Movement speed in pixels per frame */
	int speed =10;
	
	/**
	 * Constructs a paddle with specified position and dimensions.
	 * @param x X position
	 * @param y Y position  
	 * @param PADDLE_WIDTH Width of paddle
	 * @param PADDLE_HEIGHT Height of paddle
	 * @param id Player identifier
	 */
	Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){
		super(x,y, PADDLE_WIDTH, PADDLE_HEIGHT);
		this.id=id;
		
	}
	
	/**
	 * Handles key press events (legacy method, now using key bindings).
	 * @param r Key event
	 */
	public void KeyPressed(KeyEvent r) {
		switch(id) {
		case 1 :
			if(r.getKeyCode()==KeyEvent.VK_W) {
				setYDirection(-10);
				move();
			}
			if(r.getKeyCode()==KeyEvent.VK_S) {
				setYDirection(10);
				move();
			}
			break;
		case 2 :
			if(r.getKeyCode()==KeyEvent.VK_UP) {
				setYDirection(-speed);
				move();
			}
			if(r.getKeyCode()==KeyEvent.VK_DOWN) {
				setYDirection(speed);
				move();
			}
			break;
		}
		
	}
	
	/**
	 * Handles key release events (legacy method, now using key bindings).
	 * @param e Key event
	 */
	public void KeyReleased(KeyEvent e) {
		switch(id) {
		case 1 :
			if(e.getKeyCode()==KeyEvent.VK_W) {
				setYDirection(0);
				move();
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				setYDirection(0);
				move();
			}
			break;
		case 2 :
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				setYDirection(0);
				move();
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				setYDirection(0);
				move();
			}
			break;
		}
		
	}
	
	/**
	 * Sets the vertical movement direction.
	 * @param yDirection Velocity in Y direction (negative = up, positive = down)
	 */
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
		
	}
	
	/**
	 * Updates the paddle position based on current velocity.
	 */
	public void move() {
		y = y + yVelocity;
		
	}
	
	/**
	 * Draws the paddle on the screen.
	 * @param g Graphics context
	 */
	public void draw(Graphics g ) {
		if (id ==1 )
			g.setColor(Color.blue);
		else
			g.setColor(Color.red);
		g.fillRect(x,y,width,height);
		
	}

}
