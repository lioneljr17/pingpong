package Pong;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Main game panel that handles gameplay, rendering, and input.
 * Implements Runnable for the game loop thread.
 */
public class GamePanel extends JPanel implements Runnable {
	
	// Game dimensions and constants
	/** Width of the game area */
	static final int GAME_WIDTH = 1000;
	/** Height of the game area (calculated as 55.555% of width for classic aspect ratio) */
	static final int GAME_HEIGHT = (int)(GAME_WIDTH*(0.55555)) ;
	/** Screen size dimension */
	static final Dimension  SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	/** Diameter of the ball */
	static final int BALL_DIAMETER = 20;
	/** Width of each paddle */
	static final int PADDLE_WIDTH = 25;
	/** Height of each paddle */
	static final int PADDLE_HEIGHT = 100;
	/** Maximum score to win the game */
	static final int MAX_SCORE = 5;
	
	// Game components
	/** Thread for running the game loop */
	Thread gameThread;
	/** Off-screen image for double buffering */
	Image image;
	/** Graphics object for drawing to the off-screen image */
	Graphics graphics;
	/** Random number generator for ball positioning */
	Random random;
	/** Left paddle (player 1) */
	Paddle paddle1;
	/** Right paddle (player 2) */
	Paddle paddle2;
	/** The game ball */
	Ball ball;
	/** Score display and tracking */
	score score;
	/** Flag indicating if the game loop is running */
	boolean gameRunning = false;
	/** Flag indicating if game is in single-player mode (true) or two-player mode (false) */
	boolean singlePlayerMode = true;
	/** Player 1 name */
	String player1Name = "Player 1";
	/** Player 2 name */
	String player2Name = "Player 2";
	
	/**
	 * Constructs the game panel and initializes game objects.
	 * Sets up key bindings and UI properties.
	 */
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
	
	/**
	 * Sets the names for both players.
	 * @param p1Name Name for player 1
	 * @param p2Name Name for player 2
	 */
	public void setPlayerNames(String p1Name, String p2Name) {
		this.player1Name = p1Name;
		this.player2Name = p2Name;
		score.setPlayerNames(p1Name, p2Name);
	}
	
	/**
	 * Sets the game mode.
	 * @param singlePlayer true for single-player mode (vs computer), false for two-player mode
	 */
	public void setGameMode(boolean singlePlayer) {
		this.singlePlayerMode = singlePlayer;
	}
	
	/**
	 * Creates a new ball at the center of the screen with random Y position.
	 */
	public void NewBall() {
		random = new Random();
		ball = new Ball ((GAME_WIDTH/2)- (BALL_DIAMETER/2),
				random.nextInt(GAME_HEIGHT-BALL_DIAMETER),
				BALL_DIAMETER,BALL_DIAMETER);
		
	}

	/**
	 * Creates new paddles positioned at the left and right sides of the screen.
	 */
	public void NewPaddles() {
		paddle1 = new Paddle (0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),
				PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2 = new Paddle (GAME_WIDTH-PADDLE_WIDTH, (GAME_HEIGHT/2)-
				(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);	
	}
	
	/**
	 * Paints the game panel by calling the draw method.
	 * @param g Graphics context for painting
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	/**
	 * Draws all game objects (paddles, ball, score) to the screen.
	 * @param g Graphics context for drawing
	 */
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
	}
	
	/**
	 * Updates the positions of all moving game objects (paddles and ball).
	 * In single-player mode, implements AI control for the right paddle.
	 */
	public void move() {
		paddle1.move();
		
		if (singlePlayerMode) {
			// AI control for right paddle in single-player mode
			int paddleCenter = paddle2.y + PADDLE_HEIGHT / 2;
			int ballCenter = ball.y + BALL_DIAMETER / 2;
			
			// Simple AI: move paddle towards ball's Y position
			if (ballCenter < paddleCenter - 10) {
				paddle2.setYDirection(-paddle2.speed);
			} else if (ballCenter > paddleCenter + 10) {
				paddle2.setYDirection(paddle2.speed);
			} else {
				paddle2.setYDirection(0);
			}
		}
		
		paddle2.move();
		ball.move();
		
	}
	
	/**
	 * Checks for collisions between game objects and handles scoring.
	 * Includes ball-wall collisions, ball-paddle collisions, and paddle boundary limits.
	 */
	public void checkcollision() {
		// Ball bouncing off top and bottom walls
		if (ball.y<= 0 ) {
			ball.setYDirection(-ball.yVelocity);
		}
		if (ball.y>= GAME_HEIGHT-BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}
		
		// Ball bouncing off paddles
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
		
		// Prevent paddles from moving outside the game area
		if(paddle1.y<=0)
			paddle1.y=0;
		if (paddle1.y>= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;

		if(paddle2.y<= 0)
			paddle2.y=0;
		if (paddle2.y>= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle2.y = GAME_HEIGHT- PADDLE_HEIGHT;
		// Handle scoring when ball goes off screen
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
	
	/**
	 * Starts the game by setting the running flag and starting the game thread.
	 * Also requests focus for keyboard input.
	 */
	public void startGame() {
		gameRunning = true;
		if(gameThread == null || !gameThread.isAlive()) {
			gameThread = new Thread(this);
			gameThread.start();
		}
		this.requestFocusInWindow();
	}
	
	/**
	 * Stops the game by clearing the running flag.
	 */
	public void stopGame() {
		gameRunning = false;
	}
	
	/**
	 * Main game loop that runs at 60 FPS.
	 * Updates game state and renders when running.
	 */
	public void run() {
		// Game loop timing variables
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(gameRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				// Update game state and render
				move();
				checkcollision();
				repaint();
				delta--;       
			} else {
				// Sleep to prevent busy waiting
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}   
		}
		
		
	}
	
	/**
	 * Sets up keyboard input mappings for paddle control.
	 * W/S keys control left paddle, Up/Down arrows control right paddle.
	 */
	private void setupKeyBindings() {
		InputMap im = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = this.getActionMap();

		// Map key presses and releases to action names
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "p1UpPress");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "p1UpRelease");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "p1DownPress");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "p1DownRelease");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "p2UpPress");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "p2UpRelease");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "p2DownPress");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "p2DownRelease");

		// Define actions for each key event
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
