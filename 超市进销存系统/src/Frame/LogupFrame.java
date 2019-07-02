package Frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DbOperation.LoginDaoImp;

public class LogupFrame extends JFrame{
	private LoginDaoImp dao;
	private JTextField text1,text2;
	public LogupFrame() {
		try {
			dao=new LoginDaoImp();
		}catch(Exception e) {
			new NoticeFrame("Á¬½ÓÊ§°Ü"+e.getMessage());
		}
		this.setTitle("×¢²á");
		JPanel panel=new JPanel();
		this.add(panel);
		JLabel label1=new JLabel("ÕËºÅ:");
		JLabel label2=new JLabel("ÃÜÂë:");
		JButton button=new JButton("×¢²á");
		text1=new JTextField(5);
		text2=new JPasswordField(5);
		
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(button);
		
		text1.addKeyListener(new Enter());
		text2.addKeyListener(new Enter());
		button.addMouseListener(new ButtonListener());
		this.setSize(250, 125);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public void register() {
		try {
			dao.Logup(text1.getText(), text2.getText(),"common");
			dispose();
			new NoticeFrame("×¢²á³É¹¦");
		}catch(Exception ex) {
			text1.setText("");
			text2.setText("");
			new NoticeFrame("×¢²áÊ§°Ü");
		}
	}
	
	class ButtonListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			register();
		}
	}
	
	class Enter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				if(e.getSource()==text1) {
					text2.requestFocus();
				}
				if(e.getSource()==text2) {
					register();
				}
			}
		}
	}
	
}
