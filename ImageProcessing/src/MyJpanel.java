import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class MyJpanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 400;
	int properWidth = 400;
	int properHeight = 400;
	public void paint(Graphics g) {
		//super.paint(g);
		try {
			g.drawImage(image, 0, 0, getWidth(), getHeight(),null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void setImage(BufferedImage img) {
		image = img;
		properWidth = img.getWidth() > DEFAULT_WIDTH ? DEFAULT_WIDTH : img.getWidth();
		properHeight = img.getHeight() > DEFAULT_HEIGHT ? DEFAULT_HEIGHT : img.getHeight();
		int x = (getParent().getWidth() - properWidth)/2;
		int y = (getParent().getHeight() - properHeight)/2;
		setBounds(x,y,properWidth,properHeight);
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
