//Arimaa Project - Nathan Purwosumarto - 5/6/2016
//Plays a game of Arimaa in a pop up GUI

//Imports
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JViewport;



public class Arimaa implements ActionListener, MouseListener{
	
	JFrame frame = new JFrame("Arimaa Board Game");
	BoardPanel board = new BoardPanel();
	Container right = new Container();
	JLabel playerLabel = new JLabel("   Current Player: Gold");
	//0 for player gold, 1 for player silver
	int currentPlayer = 0;
	String goldPlayer = "Gold";
	String silverPlayer = "Silver";
	JButton changeName = new JButton("Change Player Name");
	JTextField changeNameText = new JTextField();
	JLabel movesLabel = new JLabel("   Moves Left: 4");
	int movesLeft = 4;
	JButton endTurn = new JButton("End Turn");
	JButton resign = new JButton("Resign");
	JLabel gameStatus = new JLabel("");
	JButton reset = new JButton("Reset Board");
	JButton start = new JButton("Start Game");
	ArimaaPiece selectedPiece;
	boolean gameOver = false;
	boolean gameRunning = false;
	boolean gameSetup = true;
	boolean pushMove = false;
	int pushedPower;
	int pushedX;
	int pushedY;
	int pullPower;
	int pullX;
	int pullY;
	
	public Arimaa(){ 
		//Frame setup
		frame.setSize(1000, 800);
		frame.setLayout(new BorderLayout());
		frame.add(board, BorderLayout.CENTER);
		board.addMouseListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		//Set content panel to 1000 by 800
		Insets insets = frame.getInsets();
		frame.setSize(950 + insets.left + insets.right, 800 + insets.top + insets.bottom);
		
		//Set right container
		//right.setLayout(new GridLayout(2,1));
		
		//Top right container
		right.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridy = 0;
		c.ipady = 40;
		c.fill = GridBagConstraints.HORIZONTAL;
		right.add(playerLabel, c);
		c.gridy = 1;
		c.ipady = 0;
		right.add(changeName, c);
		changeName.addActionListener(this);
		c.gridy = 2;
		right.add(changeNameText, c);
		c.gridy = 3;
		c.ipady = 20;
		right.add(movesLabel, c);
		c.gridy = 4;
		c.ipady = 10;
		right.add(endTurn, c);
		endTurn.addActionListener(this);
		c.gridy = 5;
		right.add(resign, c);
		resign.addActionListener(this);
		c.weighty = 1;
		c.gridy = 6;
		c.ipady = 40;
		right.add(gameStatus, c);
		c.weighty = 0;
		c.gridy = 7;
		c.ipady = 10;
		right.add(start, c);
		start.addActionListener(this);
		c.gridy = 8;
		right.add(reset, c);
		reset.addActionListener(this);
		right.setPreferredSize(new Dimension(152, 152));
		frame.add(right, BorderLayout.EAST);
//		board.addPiece(new ArimaaPiece(5,4,1,0));
//		board.addPiece(new ArimaaPiece(5,5,1,0));
//		board.addPiece(new ArimaaPiece(3,5,1,1));
//		board.addPiece(new ArimaaPiece(5,6,8,1));
//		board.addPiece(new ArimaaPiece(5,7,9,1));
//		board.addPiece(new ArimaaPiece(4,6,6,0));
//		board.addPiece(new ArimaaPiece(3,6,5,1));
		endTurn.setEnabled(false);
		resign.setEnabled(false);
		board.resetBoard();	
	}

	public static void main(String[] args) {
		new Arimaa();
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(changeName) && gameOver == false){
			if(changeNameText.getText().length()<= 6){
				if(currentPlayer == 0 && !changeNameText.getText().equals(silverPlayer)){
					goldPlayer = changeNameText.getText();
					playerLabel.setText("   Current Player: "+goldPlayer);
				}
				else if(currentPlayer == 1 && !changeNameText.getText().equals(goldPlayer)){
					silverPlayer = changeNameText.getText();
					playerLabel.setText("   Current Player: "+silverPlayer);
				}
			}
		}
		else if(event.getSource().equals(resign) && gameOver == false){
			gameOver = true;
			if(currentPlayer == 0){
				gameStatus.setText(" "+goldPlayer + " has resigned!");
			}
			else{
				gameStatus.setText(" "+silverPlayer + " has resigned!");
			}
		}
		else if(event.getSource().equals(reset)){
			pullPower = 0;
			if(selectedPiece != null){
				selectedPiece = null;
			}
			gameOver = false;
			gameSetup = true;
			start.setEnabled(true);
			endTurn.setEnabled(false);
			resign.setEnabled(false);
			currentPlayer = 0;
			gameStatus.setText("");
			changeNameText.setText("");
			playerLabel.setText("   Current Player: "+goldPlayer);
			movesLeft = 4;
			movesLabel.setText("   Moves Left: "+movesLeft);
			board.resetBoard();			
		}
		else if(event.getSource().equals(endTurn) && gameOver == false){
			if(movesLeft == 4){
				return;
			}
			gameStatus.setText("");
			if(board.checkSameState(currentPlayer)){
				movesLeft = 4;
				movesLabel.setText("   Moves Left: "+movesLeft);
				gameStatus.setText("Board state is the same");
				return;
			}
			if(selectedPiece != null){
				selectedPiece.setSelected(false);
				selectedPiece = null;
			}
			movesLeft = 4;
			movesLabel.setText("   Moves Left: "+movesLeft);
			int winResult = board.checkWin(currentPlayer);
			if(winResult == 1){
				gameStatus.setText(goldPlayer + " has won!");
				gameOver = true;
			}
			else if(winResult == 3){
				gameStatus.setText(silverPlayer + " is eliminated!");
				gameOver = true;
			}
			if(winResult == 2){
				gameStatus.setText(silverPlayer + " has won!");
				gameOver = true;
			}
			else if(winResult == 4){
				gameStatus.setText(goldPlayer + " is eliminated!");
				gameOver = true;
			}
			if(currentPlayer == 0){
				currentPlayer = 1;
				playerLabel.setText("   Current Player: "+silverPlayer);
			}
			else{
				currentPlayer = 0;
				playerLabel.setText("   Current Player: "+goldPlayer);
			}
			frame.repaint();
		}
		else if(event.getSource().equals(start)){
			board.getStartPositions();
			gameSetup = false;
			start.setEnabled(false);
			endTurn.setEnabled(true);
			resign.setEnabled(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if(gameSetup == true){
			if(selectedPiece == null && board.atPosition(event.getX()/100, event.getY()/100) != null){
				selectedPiece = board.atPosition(event.getX()/100, event.getY()/100);
				selectedPiece.setSelected(true);
			}
			else if(board.atPosition(event.getX()/100, event.getY()/100) != null){
				if(selectedPiece != null && selectedPiece.equals(board.atPosition(event.getX()/100, event.getY()/100))){
					selectedPiece.setSelected(false);
					selectedPiece = null;
				}
				else if(selectedPiece != null && selectedPiece.getColor() == board.atPosition(event.getX()/100, event.getY()/100).getColor()){
					ArimaaPiece clicked = board.atPosition(event.getX()/100, event.getY()/100);
					clicked.setX(selectedPiece.getX());
					clicked.setY(selectedPiece.getY());
					selectedPiece.setX(event.getX()/100);
					selectedPiece.setY(event.getY()/100);
				}
				else if(selectedPiece != null && selectedPiece.getColor() != board.atPosition(event.getX()/100, event.getY()/100).getColor()){
					selectedPiece.setSelected(false);
					selectedPiece = null;
					selectedPiece = board.atPosition(event.getX()/100, event.getY()/100);
					selectedPiece.setSelected(true);
				}
			}
			board.repaint();
		}
		else if(gameOver == false){
			if(board.atPosition(event.getX()/100, event.getY()/100) != null){
				if(selectedPiece != null && selectedPiece.equals(board.atPosition(event.getX()/100, event.getY()/100))){
					selectedPiece.setSelected(false);
					selectedPiece = null;
				}
				else{
					if(selectedPiece != null){
						selectedPiece.setSelected(false);
					}
					selectedPiece = board.atPosition(event.getX()/100, event.getY()/100);
					selectedPiece.setSelected(true);
				}
			}
			else if(selectedPiece != null){
				if(selectedPiece.getColor() == currentPlayer && pushMove == false){
					if(checkValidMove(selectedPiece, event.getX()/100, event.getY()/100) == true){
						if(movesLeft > 0){
							pullPower = selectedPiece.getPower();
							pullX = selectedPiece.getX();
							pullY = selectedPiece.getY();
							selectedPiece.setX(event.getX()/100);
							selectedPiece.setY(event.getY()/100);
							movesLeft -= 1;
							movesLabel.setText("   Moves Left: "+movesLeft);
						}
					}
				}
				else if(selectedPiece.getColor() == currentPlayer && pushMove == true){
					if(selectedPiece.getPower() > pushedPower && event.getX()/100 == pushedX && event.getY()/100 == pushedY){
						if(checkValidMove(selectedPiece, event.getX()/100, event.getY()/100) == true){
							if(movesLeft > 0){
								selectedPiece.setX(event.getX()/100);
								selectedPiece.setY(event.getY()/100);
								movesLeft -= 1;
								movesLabel.setText("   Moves Left: "+movesLeft);
								pushMove = false;
							}
						}
					}
				}
				//Pull a piece of the opposite color
				else if(selectedPiece.getPower() < pullPower && event.getX()/100 == pullX && event.getY()/100 == pullY){
					if((selectedPiece.getX() - 1 == event.getX()/100 && selectedPiece.getY() == event.getY()/100) ||
						(selectedPiece.getX() + 1 == event.getX()/100 && selectedPiece.getY() == event.getY()/100) ||
						(selectedPiece.getX() == event.getX()/100 && selectedPiece.getY() == event.getY()/100 + 1) ||
						(selectedPiece.getX() == event.getX()/100 && selectedPiece.getY() == event.getY()/100 - 1)){
						selectedPiece.setX(event.getX()/100);
						selectedPiece.setY(event.getY()/100);
						movesLeft -= 1;
						movesLabel.setText("   Moves Left: "+movesLeft);
					}
				}
				else if(checkValidPush(selectedPiece, event.getX()/100, event.getY()/100) == true){
					if(movesLeft > 1){
						pushMove = true;
						pushedPower = selectedPiece.getPower();
						pushedX = selectedPiece.getX();
						pushedY = selectedPiece.getY();
						selectedPiece.setX(event.getX()/100);
						selectedPiece.setY(event.getY()/100);
						movesLeft -= 1;
						movesLabel.setText("   Moves Left: "+movesLeft);
					}
				}
			}
			checkTrap(2,2);
			checkTrap(5,2);
			checkTrap(2,5);
			checkTrap(5,5);
			board.repaint();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean checkValidMove(ArimaaPiece piece, int mouseX, int mouseY) {
		//piece is left of square clicked
		if(piece.getPower() == 1 && piece.getColor() == 0 && piece.getX() == mouseX && piece.getY()+1 == mouseY){
			return false;
		}
		if(piece.getPower() == 1 && piece.getColor() == 1 && piece.getX() == mouseX && piece.getY()-1 == mouseY){
			return false;
		}
		if(!(piece.getX() - 1 == mouseX && piece.getY() == mouseY) && !(piece.getX() + 1 == mouseX && piece.getY() == mouseY)
		&&!(piece.getX() == mouseX && piece.getY() == mouseY + 1)&&!(piece.getX() == mouseX && piece.getY() == mouseY - 1)){
			return false;
		}
		if(checkBlocked(piece) == true){
			return false;
		}
		return true;
	}
	
	//Checks to see if a piece is unable to move because it is next to a higher piece of the opposite color
	private boolean checkBlocked(ArimaaPiece piece) {
		Boolean blocked = false;
		if(isBlocked(piece, -1, 0) == 0){
			return false;
		}
		else if(isBlocked(piece, -1, 0) == 1){
			blocked = true;
		}
		if(isBlocked(piece, 1, 0) == 0){
			return false;
		}
		else if(isBlocked(piece, 1, 0) == 1){
			blocked = true;
		}
		if(isBlocked(piece, 0, -1) == 0){
			return false;
		}
		else if(isBlocked(piece, 0, -1) == 1){
			blocked = true;
		}
		if(isBlocked(piece, 0, 1) == 0){
			return false;
		}
		else if(isBlocked(piece, 0, 1) == 1){
			blocked = true;
		}
		return blocked;
	}
	
	//helper function of checkBlocked, checks one square
	private int isBlocked(ArimaaPiece piece, int xshift, int yshift){
		if(board.atPosition(piece.getX()+xshift, piece.getY()+yshift) != null){
			//if the piece next to it is the same color, selected piece is free to move
			if(piece.getColor() == board.atPosition(piece.getX()+xshift, piece.getY()+yshift).getColor()){
				return 0;
			}
			//if the piece is the opposite color and the higher rank, set blocked to true
			else if(piece.getPower() < board.atPosition(piece.getX()+xshift, piece.getY()+yshift).getPower()){
				return 1;
			}
		}
		return 2;
	}
	
	private boolean checkValidPush(ArimaaPiece piece, int mouseX, int mouseY) {
		if(!(piece.getX() - 1 == mouseX && piece.getY() == mouseY) && !(piece.getX() + 1 == mouseX && piece.getY() == mouseY)
			&&!(piece.getX() == mouseX && piece.getY() == mouseY + 1)&&!(piece.getX() == mouseX && piece.getY() == mouseY - 1)){
				return false;
			}
		ArimaaPiece pieceUsed = board.atPosition(piece.getX() - 1, piece.getY());
		if(pieceUsed != null && pieceUsed.getPower() > selectedPiece.getPower() && checkBlocked(pieceUsed) == false
				&& pieceUsed.getColor() == currentPlayer){
			return true;
		}
		pieceUsed = board.atPosition(piece.getX() + 1, piece.getY());
		if(pieceUsed != null && pieceUsed.getPower() > selectedPiece.getPower() && checkBlocked(pieceUsed) == false
				&& pieceUsed.getColor() == currentPlayer){
			return true;
		}
		pieceUsed = board.atPosition(piece.getX(), piece.getY() - 1);
		if(pieceUsed != null && pieceUsed.getPower() > selectedPiece.getPower() && checkBlocked(pieceUsed) == false
				&& pieceUsed.getColor() == currentPlayer){
			return true;
		}
		pieceUsed = board.atPosition(piece.getX(), piece.getY() + 1);
		if(pieceUsed != null && pieceUsed.getPower() > selectedPiece.getPower() && checkBlocked(pieceUsed) == false
				&& pieceUsed.getColor() == currentPlayer){
			return true;
		}
		return false;
	}
	
	private void checkTrap(int x, int y) {
		if(board.atPosition(x, y) != null){
			ArimaaPiece trappedPiece = board.atPosition(x, y);
			if(board.atPosition(trappedPiece.getX()-1, trappedPiece.getY()) != null){
				if(board.atPosition(trappedPiece.getX()-1, trappedPiece.getY()).getColor() == trappedPiece.getColor()){
					return;
				}
			}
			if(board.atPosition(trappedPiece.getX()+1, trappedPiece.getY()) != null){
				if(board.atPosition(trappedPiece.getX()+1, trappedPiece.getY()).getColor() == trappedPiece.getColor()){
					return;
				}
			}
			if(board.atPosition(trappedPiece.getX(), trappedPiece.getY()-1) != null){
				if(board.atPosition(trappedPiece.getX(), trappedPiece.getY()-1).getColor() == trappedPiece.getColor()){
					return;
				}
			}
			if(board.atPosition(trappedPiece.getX(), trappedPiece.getY()+1) != null){
				if(board.atPosition(trappedPiece.getX(), trappedPiece.getY()+1).getColor() == trappedPiece.getColor()){
					return;
				}
			}
			if(trappedPiece.equals(selectedPiece)){
				selectedPiece.setSelected(false);
				selectedPiece = null;
			}
			board.removePiece(trappedPiece);
			pullPower = 0;
		}
		
	}

}
