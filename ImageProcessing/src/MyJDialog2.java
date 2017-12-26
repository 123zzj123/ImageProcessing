import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MyJDialog2 extends JDialog{
	private int Size;
	public MyJDialog2(String labelText) {
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
					Size = Integer.parseInt(textField.getText());
				} catch (Exception e2) {
					// TODO: handle exception
					Size = 0;
				}
				dispose();
			}
		});
	}
	
	public int GetSize() {
		return Size;
	}
}
