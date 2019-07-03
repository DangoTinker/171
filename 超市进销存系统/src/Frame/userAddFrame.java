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
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import DbOperation.UserMessageImp;
import ast.UserMessage;

public class userAddFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private UserMessageImp dao;
	private JRadioButton jradio;
	private JTextField text1,text2;
	public userAddFrame() {
		this.setTitle("添加用户");
		try {
			dao=new UserMessageImp();
		}catch(Exception e) {
			new NoticeFrame("连接失败"+e.getMessage());
		}
		this.setTitle("注册");
		JPanel panel=new JPanel();
		this.add(panel);
		JLabel label1=new JLabel("账号:");
		JLabel label2=new JLabel("密码:");
		jradio = new JRadioButton("管理员");
		
		JButton button=new JButton("注册");
		text1=new JTextField(5);
		text2=new JPasswordField(5);
		
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(jradio);
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
			if(!jradio.isSelected())
				dao.insert(new UserMessage(text1.getText(), text2.getText(),"common"));
			else 
				dao.insert(new UserMessage(text1.getText(), text2.getText(),"root"));
			dispose();
			new NoticeFrame("注册成功");
		}catch(Exception ex) {
			text1.setText("");
			text2.setText("");
			new NoticeFrame("注册失败");
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
