package Frame;
import java.awt.FileDialog;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DbOperation.DbOperation;
import DbOperation.StaffDao;
import ast.AstMethod;
import ast.Goods;
import ast.Staff;
import ast.Supplier;
import ast.Tranable;

public class userStaffFrame extends JFrame{
	private StaffDao dao;
	private DefaultTableModel tableModel;
	private LinkedList<Tranable> list;
	private String username;
	private JTable table;
	private JFrame frame;
	private byte[] iconFile;
	
	
	
	
	public userStaffFrame(String u) {
		this.setSize(500,300);
		username=u;
		try {
			dao=StaffDao.getInstance();
			list=(LinkedList<Tranable>)dao.queryOne(username);
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
		}
		/*
		if(list.size()==0) {
			dispose();
			new NoticeFrame("无此员工");
			
		}
		*/
		
		Object[] o=dao.getName();
		tableModel=AstMethod.makeTableModel(o,list);
		
		JPanel panel=new JPanel();
		this.add(panel);
		
		table=table=new JTable(tableModel);
		panel.add(table.getTableHeader());
		panel.add(table);
		
		
		
		JButton picButton=new JButton("查看头像");
		
		
		panel.add(picButton);
		
		picButton.addMouseListener(new ButtonListener());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setVisible(true);
	}
	
	private class ButtonListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			switch (((JButton)e.getSource()).getText()) {
			
			
				case "查看头像":{
					int n=table.getSelectedRow();
					new PicFrame((byte[])tableModel.getValueAt(n, 5));
					break;
				}
			
			
			}
		}
	}
	
	
	
	
	
	
	
}
