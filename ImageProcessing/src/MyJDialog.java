import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyJDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Scale scale;
	private int[] RGBArray;
	private int[] Result;
	private int SrcWidth;
	private int SrcHeight;
	private int DesWidth;
	private int DesHeight;
	private int ID;
	private boolean Correct = true;
	public MyJDialog(int[] result, int w, int h) {
		// TODO Auto-generated constructor stub
		RGBArray = result;
		SrcWidth = w;
		SrcHeight = h;
		setSize(300,150);
		setLayout(new BorderLayout());
		Container container = getContentPane();
		scale = new Scale();
		JButton confirm = new JButton("确定");
		container.add("South", confirm);
		JLabel jLabel = new JLabel("请输入缩放后的图片的宽和高");
		container.add("North", jLabel);
		JPanel panel = new JPanel();
		panel.setSize(120,40);
		JTextField width = new JTextField(10);
		JTextField height = new JTextField(10);
		JLabel tips = new JLabel();
		panel.add(width);
		panel.add(height);
		panel.add(tips);
		container.add("Center", panel);
		
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					DesWidth = Integer.parseInt(width.getText());
					DesHeight = Integer.parseInt(height.getText());
					Correct = true;
				} catch (NumberFormatException exception) {
					// TODO: handle exception
					Correct = false;
					tips.setText("请输入正整数");
					tips.setForeground(Color.red);
					//exception.printStackTrace();
				}
				
				if(DesWidth <= 0 || DesHeight <= 0) {
					Correct = false;
				}
				if(Correct) {
					switch (ID) {
					case 1:
						Result = scale.CopyScale(RGBArray, SrcWidth, SrcHeight, DesWidth, DesHeight);
						break;
					case 2:
						Result = scale.BilineInterpolationScale(RGBArray, SrcWidth, SrcHeight, DesWidth, DesHeight);
						break;
					case 3:
						Result = scale.bicubicScale(RGBArray, SrcWidth, SrcHeight, DesWidth, DesHeight);
					default:
						break;
					}
					dispose();
				}
			}
		});
	}
	
	public int[] GetResult() {
		return Result;
	}
	
	public int GetDesWidth() {
		return DesWidth;
	}
	
	public int GetDesHeight() {
		return DesHeight;
	}
	
	public void SetID(int id) {
		ID = id;
	}
}
