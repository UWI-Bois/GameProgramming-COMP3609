import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Image;

public abstract class Sprite {

	protected int x;
	protected int y;

	protected int dx;
	protected int dy;

	protected int width;
	protected int height;

	protected int xSize;
	protected int ySize;

	protected boolean active;

	protected Image spriteImage;

	protected JFrame window;
	protected Dimension dimension;

	public Sprite (JFrame frame, 
		       int x, int y, int dx, int dy, 
		       int xSize, int ySize, 
		       String filename) {
		window = frame;
		dimension = window.getSize();
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.xSize = xSize;
		this.ySize = ySize;
		setImage(filename);
		active = true;
	}

	public Image getImage() {
		return spriteImage;
	}

	public void setImage (String filename) {
		spriteImage = loadImage (filename);
		width = spriteImage.getWidth(null);
		height = spriteImage.getHeight(null);
	}

	public Image loadImage (String filename) {
		return new ImageIcon(filename).getImage();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getX() {
		return x;
	}

	public void setX(int newX) {
		x = newX;
	}

	public int getY() {
		return y;
	}

	public void setY(int newY) {
		y = newY;
	}

	public int getDX() {
		return dx;
	}

	public void setDX(int newDX) {
		dx = newDX;
	}

	public int getDY() {
		return dy;
	}

	public void setDY(int newDY) {
		dy = newDY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public abstract void update(); 

	public void draw (Graphics g) {
		g.drawImage(spriteImage, x, y, xSize, ySize, null);
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, xSize, ySize);
	}

}