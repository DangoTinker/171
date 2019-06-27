package Frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.Reader;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import DbOperation.LoginDao;


public class LoginFrame extends JFrame{
	private LoginDao dao;
	private JTextField text1,text2;
	public LoginFrame() {
		try {
			dao=LoginDao.getInstance();
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
		}
		this.setTitle("µÇÂ½");
		JPanel panel=new JPanel();
		this.add(panel);
		
		JMenuBar menuBar=new JMenuBar();
		JMenu menu=new JMenu("°ïÖú");
		JMenuItem menuItem1=new JMenuItem("°ïÖúÎÄ¼þ");
		
		menuItem1.addMouseListener(new MenuItemListener());
		this.setJMenuBar(menuBar);
		menuBar.add(menu);
		menu.add(menuItem1);
		
		
		text1=new JTextField(5);
		text2=new JPasswordField(5);
		JButton button1=new JButton("µÇÂ½");
		JButton button2=new JButton("×¢²á");
		JLabel label1=new JLabel("ÕËºÅ:");
		JLabel label2=new JLabel("ÃÜÂë:");
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);

		panel.add(button1);
		panel.add(button2);
		
		text1.addKeyListener(new Enter());
		text2.addKeyListener(new Enter());
		
		button1.addMouseListener(new Login());
		button2.addMouseListener(new Register());
	
		
		this.setSize(250,125);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void loginMethod() {
		try {
		if(dao.Login(text1.getText(), text2.getText())==1) {
			new MainFrame(text1.getText());
			dispose();
		}else {
			new NoticeFrame("ÃÜÂë´íÎó");
		}
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
		}
	}
	
	
	
	
	class Login extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			loginMethod();
		}
	}
	
	
	class Register extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			new LogupFrame();
		}
	}
	
	class MenuItemListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			try {
			Reader input=new FileReader("README.txt");
			
			int length=0;
			String b="";
			while(length!=-1) {
				char[] a=new char[1024];
				length=input.read(a);
				b=b+String.valueOf(a);
			}
			JFrame tempFrame=new JFrame();
			tempFrame.setSize(500,500);
			JTextArea text=new JTextArea(70,70);
			text.setText(b);
			text.setLineWrap(true);
			JScrollPane ScrollPane = new JScrollPane(text);
			tempFrame.add(ScrollPane);
			tempFrame.setLocationRelativeTo(null);
			tempFrame.setVisible(true);
			}catch(Exception ex) {
				new NoticeFrame("´ò¿ªÊ§°Ü");
			}
		}
	}
	
	class Enter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				if(e.getSource()==text1) {
					text2.requestFocus();
				}
				if(e.getSource()==text2) {
					loginMethod();
				}
			}
		}
	}
}
