package Frame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class NoticeFrame extends JFrame{
	private JButton button;
	public NoticeFrame(String a) {
		JLabel tempLabel=new JLabel(a);
		this.setLayout(new BorderLayout());
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		button=new JButton("È·¶¨");
		button.addMouseListener(new ButtonListener());
		panel1.add(tempLabel);
		panel2.add(button);
		button.addKeyListener(new Enter());
		this.add(panel1,BorderLayout.NORTH);
		this.add(panel2,BorderLayout.CENTER);
		this.setSize(100,100);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	class ButtonListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			dispose();
		}
	}
	
	class Enter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				if(e.getSource()==button) {
					dispose();
				}
			}
		}
	}
	
}
