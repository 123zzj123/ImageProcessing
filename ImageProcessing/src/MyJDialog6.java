import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyJDialog6 extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyPanel1 p1;
	MyPanel1 p2;
	JLabel p3;
	JLabel p4;
	JLabel j1;
	JLabel j2;
	public MyJDialog6() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		setSize(600,400);
		Container container = getContentPane();
		p1 = new MyPanel1();
		p2 = new MyPanel1();
		p3 = new JLabel("原始图像");
		p4 = new JLabel("对比图像");
		j1 = new JLabel();
		container.add(p1, new GBC(0, 0, 2, 1).setFill(GBC.BOTH).setIpad(200, 200).setInsets(5, 5, 5, 5));
		container.add(p2, new GBC(2, 0, 2, 1).setFill(GBC.BOTH).setIpad(200, 200).setInsets(5, 5, 5, 5));
		container.add(p3, new GBC(0, 1, 2, 1).setFill(GBC.BOTH).setIpad(200, 40));
		container.add(p4, new GBC(2, 1, 2, 1).setFill(GBC.BOTH).setIpad(200, 40));
		container.add(j1, new GBC(1, 2, 2, 1).setFill(GBC.BOTH).setIpad(100, 40));
	}
	
	public void SetIniImage(BufferedImage img) {
		p1.SetImage(img);
	}
	public void SetAnoImage(BufferedImage img) {
		p2.SetImage(img);
	}
	public void SetLabel(String str) {
		j1.setText(str);
	}
	public void Show() {
		p1.paint(p1.getGraphics());
		p2.paint(p2.getGraphics());
	}
	
	private class MyPanel1 extends JPanel{
		private BufferedImage image;
		public MyPanel1() {
			// TODO Auto-generated constructor stub
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
