package Pong;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
	
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH*(0.55555)) ;
	static final Dimension  SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	static final int MAX_SCORE = 5;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	score score;
	boolean gameRunning = false;
	
	
	GamePanel(){
		NewPaddles();
		NewBall();
		score = new score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		this.setPreferredSize(SCREEN_SIZE);
		this.setBackground(Color.black);
		setupKeyBindings();
		
		// Don't start thread here, wait for startGame()
		
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
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
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
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		// to stop paddle at the edge of the window
		if(paddle1.y<=0)
			paddle1.y=0;
		if (paddle1.y>= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;

		if(paddle2.y<= 0)
			paddle2.y=0;
		if (paddle2.y>= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle2.y = GAME_HEIGHT- PADDLE_HEIGHT;
		// GIVE A PLAYER 1 POINT AND CREATES A NEW PADDLES BALL
		
		if (ball.x<=0) {
			score.player2++;
			NewPaddles();
			NewBall();
			System.out.print("Player 2:"+ score.player2);
			if(score.player2 >= MAX_SCORE) {
				System.out.println("Player 2 wins!");
				stopGame();
			}
		}
		if (ball.x>= GAME_WIDTH-BALL_DIAMETER ) {
			score.player1++;
			NewPaddles();
			NewBall();
			System.out.println("Player 1:"+ score.player1);
			if(score.player1 >= MAX_SCORE) {
				System.out.println("Player 1 wins!");
				stopGame();
			}
			
		}
		
	} 
	
	public void startGame() {
		gameRunning = true;
		if(gameThread == null || !gameThread.isAlive()) {
			gameThread = new Thread(this);
			gameThread.start();
		}
		this.requestFocusInWindow();
	}
	
	public void stopGame() {
		gameRunning = false;
	}
	
	public void run() {
		// game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(gameRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				move();
				checkcollision();
				repaint();
				delta--;       
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}   
		}
		
		
	}
	
	private void setupKeyBindings() {
		InputMap im = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = this.getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "p1UpPress");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "p1UpRelease");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "p1DownPress");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "p1DownRelease");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "p2UpPress");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "p2UpRelease");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "p2DownPress");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "p2DownRelease");

		am.put("p1UpPress", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paddle1.setYDirection(-paddle1.speed);
			}
		});
		am.put("p1UpRelease", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paddle1.setYDirection(0);
			}
		});
		am.put("p1DownPress", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paddle1.setYDirection(paddle1.speed);
			}
		});
		am.put("p1DownRelease", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paddle1.setYDirection(0);
			}
		});
		am.put("p2UpPress", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paddle2.setYDirection(-paddle2.speed);
			}
		});
		am.put("p2UpRelease", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paddle2.setYDirection(0);
			}
		});
		am.put("p2DownPress", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paddle2.setYDirection(paddle2.speed);
			}
		});
		am.put("p2DownRelease", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paddle2.setYDirection(0);
			}
		});
	}
}
