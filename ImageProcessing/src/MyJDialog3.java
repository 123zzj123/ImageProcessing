import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MyJDialog3 extends JDialog{
	private Double Size;
	public MyJDialog3(String labelText) {
		// TODO Auto-generated constructor stub
		setSize(200, 120);
		setLayout(new BorderLayout());
		JLabel label = new JLabel(labelText);
		add(label,BorderLayout.NORTH);
		JTextField textField = new JTextField(5);
		add(textField,BorderLayout.CENTER);
		JButton button = new JButton("х╥хо");
		add(button, BorderLayout.SOUTH);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Size = Double.parseDouble(textField.getText());
				} catch (Exception e2) {
					// TODO: handle exception
					Size = 0.0d;
				}
				dispose();
			}
		});
	}
	
	public Double GetSize() {
		return Size;
	}
}
