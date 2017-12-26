import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MyJDialog4 extends JDialog{
	private double Size;
	private double Q;
	public MyJDialog4(String P1, String P2) {
		// TODO Auto-generated constructor stub
		setSize(280,120);
		setResizable(false);
		setLayout(new FlowLayout());
		JLabel f_Label = new JLabel(P1);
		add(f_Label);
		JTextField f_Filed = new JTextField(5);
		add(f_Filed);
		JLabel p_Label = new JLabel(P2);
		add(p_Label);
		JTextField p_Field = new JTextField(5);
		add(p_Field);
		JButton confirm = new JButton("х╥хо");
		add(confirm);
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Size = Double.parseDouble(f_Filed.getText());
					Q = Double.parseDouble(p_Field.getText());
				} catch (Exception e2) {
					// TODO: handle exception
					Size = 0.0;
					Q = 0.0;
				}
				dispose();
			}
		});
	}
	
	public double GetSize() {
		return Size;
	}
	
	public Double GetQ() {
		return Q;
	}
}
