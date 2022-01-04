package Pong;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class score   extends Rectangle{
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	int player1;
	int player2;
	
	score(int GAME_WIDTH, int GAME_HEIGHT){
		score.GAME_WIDTH = GAME_WIDTH;
		score.GAME_HEIGHT = GAME_HEIGHT;

		
	}
	
	

	score(){
		
	}
	public void draw(Graphics g ) {
		g.setColor(Color.white);
		g.setFont(new Font("Consolas",Font.PLAIN,60));
		
		g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
		
		g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), 
				(GAME_WIDTH/2)-85, 50);
		g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), 
				(GAME_WIDTH/2)+20, 50);

		
	}

}
