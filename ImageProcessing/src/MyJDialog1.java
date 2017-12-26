import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class MyJDialog1 extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyPanel1 p1;
	MyPanel1 p2;
	MyPanel1 p3;
	MyPanel1 p4;
	public MyJDialog1() {
		setSize(430, 430);
		setLayout(new GridLayout(2, 2, 10, 10));
		setResizable(false);
		Container container = getContentPane();
		p1 = new MyPanel1();
		p2 = new MyPanel1();
		p3 = new MyPanel1();
		p4 = new MyPanel1();
		container.add(p1);
		container.add(p2);
		container.add(p3);
		container.add(p4);
	}
	
	public void SetIniImage(BufferedImage img) {
		p1.SetImage(img);
	}
	public void SetIniHistogram(BufferedImage img) {
		p2.SetImage(img);
	}
	public void SetProImage(BufferedImage img) {
		p3.SetImage(img);
	}
	public void SetProHistogram(BufferedImage img) {
		p4.SetImage(img);
	}
	public void Show() {
		p1.paint(p1.getGraphics());
		p2.paint(p2.getGraphics());
		p3.paint(p3.getGraphics());
		p4.paint(p4.getGraphics());
		File output = new File("D:/333.jpg");
        try {
            ImageIO.write(p2.image, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        File output1 = new File("D:/444.jpg");
        try {
            ImageIO.write(p4.image, "jpg", output1);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private class MyPanel1 extends JPanel{
		private BufferedImage image;
		public MyPanel1() {
			// TODO Auto-generated constructor stub
			setSize(200, 200);
		}
		public void paint(Graphics g) {
			//super.paint(g);
			try {
				g.drawImage(image, 0, 0, getWidth(), getHeight(),null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		public void SetImage(BufferedImage img) {
			image = img;
		}
	}
}
