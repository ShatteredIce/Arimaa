import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	
	ArrayList<ArimaaPiece> pieces = new ArrayList<ArimaaPiece>();
	
	public BoardPanel(){
		super();
	}
	
	//add a piece to the gameboard
	public void addPiece(ArimaaPiece a){
		pieces.add(a);
	}
	
	//remove a piece from the gameboard
	public void removePiece(ArimaaPiece a){
		pieces.remove(a);
	}
	
	public ArimaaPiece atPosition(int x, int y){
		for (int a = 0; a < pieces.size(); a++) {
			if(pieces.get(a).getX() == x && pieces.get(a).getY() == y){
				return pieces.get(a);
			}
		}
		return null;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Image background;
			try {
				background = ImageIO.read(new File("arimaabackground.png"));
				 g.drawImage(background, 0, 0, null);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		Image trap;
		try {
			background = ImageIO.read(new File("trap.jpg"));
			 g.drawImage(background, 200, 200, null);
			 g.drawImage(background, 500, 200, null);
			 g.drawImage(background, 200, 500, null);
			 g.drawImage(background, 500, 500, null);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		for (int a = 0; a < pieces.size(); a++) {
			pieces.get(a).paintMe(g);
		}
	}
	
	//Remove all the pieces
	public void resetBoard(){
		while (pieces.size() > 0) {
			pieces.remove(0);
		}
		int row = 0;
		int color = 1;
		int column = 0;
		for (int i = 0; i < 16; i++) {
			this.addPiece(new ArimaaPiece(row, column, 1, color));
			row++;
			if(row == 8){
				row = 0;
				column = 7;
				color = 0;
			}
		}
		column = 6;
		for(int i = 0; i < 2; i++){
			this.addPiece(new ArimaaPiece(0, column, 5, i));
			this.addPiece(new ArimaaPiece(1, column, 6, i));
			this.addPiece(new ArimaaPiece(2, column, 7, i));
			this.addPiece(new ArimaaPiece(3, column, 8, i));
			this.addPiece(new ArimaaPiece(4, column, 9, i));
			this.addPiece(new ArimaaPiece(5, column, 7, i));
			this.addPiece(new ArimaaPiece(6, column, 6, i));
			this.addPiece(new ArimaaPiece(7, column, 5, i));
			column = 1;
		}
		this.repaint();
	}

	//Checks to see if a player has won the game
	public int checkWin(int currentPlayer) {
		boolean goldEliminated = true;
		boolean silverEliminated = true;
		if(currentPlayer == 0){
			for (int i = 0; i < pieces.size(); i++) {
				if(pieces.get(i).getPower() == 1 && pieces.get(i).getY() == 0 && pieces.get(i).getColor() == 0){
					return 1;
				}
				else if(pieces.get(i).getPower() == 1 && pieces.get(i).getY() == 7 && pieces.get(i).getColor() == 1){
					return 2;
				}
				else if(pieces.get(i).getPower() == 1 && pieces.get(i).getColor() == 1){
					silverEliminated = false;
				}
				else if(pieces.get(i).getPower() == 1 && pieces.get(i).getColor() == 0){
					goldEliminated = false;
				}
			}
		}
	}
}
