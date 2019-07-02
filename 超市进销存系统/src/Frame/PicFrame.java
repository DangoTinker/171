package Frame;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


public class PicFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton button;
	public PicFrame(byte[] pic) {
		JLabel picLabel=new JLabel();
		this.setLayout(new BorderLayout());
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		button=new JButton("È·¶¨");
		button.addMouseListener(new ButtonListener());
		button.addKeyListener(new Enter());

		
		ImageIcon icon=new ImageIcon(pic);  
		icon.setImage(icon.getImage().getScaledInstance(100, 100,  
		        Image.SCALE_DEFAULT));  
		picLabel.setIcon(icon);
		
		panel1.add(picLabel);
		
		panel2.add(button);
		this.add(panel1,BorderLayout.NORTH);
		this.add(panel2,BorderLayout.CENTER);
		this.setSize(300,300);
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
