package Pong;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends 
Panel implements Runnable {
	
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH*(0.55555)) ;
	static final Dimension  SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	score score;
	
	
	GamePanel(){
		NewPaddles();
		NewBall();
		score = new score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		this.setBackground(Color.black);
		
		gameThread = new Thread(this);
		gameThread.start();
		
		
		
	}
	
	public void NewBall() {
		random = new Random();
		ball = new Ball ((GAME_WIDTH/2)- (BALL_DIAMETER/2),
				random.nextInt(GAME_HEIGHT-BALL_DIAMETER),
				BALL_DIAMETER,BALL_DIAMETER);
		
	}

	public void NewPaddles() {
		paddle1 = new Paddle (0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),
				PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2 = new Paddle (GAME_WIDTH-PADDLE_WIDTH, (GAME_HEIGHT/2)-
				(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);	
	}
	
	public void paint (Graphics g ) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
		
	}
	
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
	//	Toolkit.getDefaultToolkit().sync();
		
	}
	
	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();
		
	}
	
	public void checkcollision() {
		// ball bouncing off top and bottom of the page
		
		if (ball.y<= 0 ) {
			ball.setYDirection(-ball.yVelocity);
		}
		if (ball.y>= GAME_HEIGHT-BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}
		// bounce of the paddle
		
		if (ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; // optional for more difficulty
			if (ball.yVelocity>0)
				ball.yVelocity++;// optional 
			else
				ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //optional
			if(ball.yVelocity>0)
				ball.yVelocity++; // more optional
			else
				ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		// to stop paddle at the edge of the window
		if(paddle1.y<=0)
			paddle2.y=0;
		if (paddle1.y>= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
	
		if(paddle2.y<= 0);
			paddle2.y=0;
		if (paddle2.y>= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle2.y = GAME_HEIGHT- PADDLE_HEIGHT;
		// GIVE A PLAYER 1 POINT AND CREATES A NEW PADDLES BALL
		
		if (ball.x<=0) {
			score.player2++;
			NewPaddles();
			NewBall();
			System.out.print("Player 2:"+ score.player2);
		}
		if (ball.x>= GAME_WIDTH-BALL_DIAMETER ) {
			score.player1++;
			NewPaddles();
			NewBall();
			System.out.println("Player 1:"+ score.player1);
			
		}
		
	} 
	
	public void run() {
		// game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns= 1000000000/ amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta+=(now -lastTime)/ns;
			if(delta >= 1) {
				move();
				checkcollision();
				repaint();
				delta--;       
				
				
			}   
		}
		
		
	}
	
	public class AL extends KeyAdapter{
		public void KeyPressed(KeyEvent r) {
			paddle1.KeyPressed(r);
			paddle2.KeyPressed(r);
			
		}
		public void KeyReleased(KeyEvent e) {
			paddle1.KeyReleased(e);
			paddle2.KeyReleased(e);
			
		}
	}
}
