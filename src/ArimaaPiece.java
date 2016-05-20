import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ArimaaPiece {
	
	int x;
	int y;
	int power;
	int color;
	boolean selected;
	
	public ArimaaPiece(int newx, int newy, int newpower, int newcolor) {
		x = newx;
		y = newy;
		power = newpower;
		//0 for gold, 1 for silver
		color = newcolor;
		selected = false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int newx) {
		x = newx;
	}
	
	public void setY(int newy) {
		y = newy;
	}
	
	public int getPower() {
		return power;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setPower(int newpower) {
		power = newpower;
	}
	
	public void setSelected(boolean b) {
		selected = b;
	}

	public void paintMe(Graphics g) {
		Image image;
		String imageFile = null;
		if(this.power == 1){
			imageFile = "rabbit";
		}
		else if (this.power == 5){
			imageFile = "cat";
		}
		else if (this.power == 6){
			imageFile = "dog";
		}
		else if (this.power == 7){
			imageFile = "horse";
		}
		else if (this.power == 8){
			imageFile = "camel";
		}
		else if (this.power == 9){
			imageFile = "elephant";
		}
		else{
			imageFile = "error";
		}
		if(this.color == 0){
			imageFile = "gold"+imageFile;
		}
		else if(this.color == 1){
			imageFile = "silver"+imageFile;
		}
		if(this.selected == true){
			imageFile += "selected";
		}
		try {
			image = ImageIO.read(new File(imageFile+".png"));
			 g.drawImage(image, (this.x*100)+13, (this.y*100)+13, null);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
