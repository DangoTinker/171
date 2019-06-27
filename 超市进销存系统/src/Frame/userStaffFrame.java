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
import DbOperation.GoodsDao;
import DbOperation.StaffDao;
import DbOperation.SupplierDao;
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
	
	private JLabel stnoLabel=new JLabel("编号");
	private JLabel stnameLabel=new JLabel("姓名");
	private JLabel stlevelLabel=new JLabel("级别");
	private JLabel phoneLabel=new JLabel("电话");
	private JLabel salaryLabel=new JLabel("工资");
	private JLabel iconFileLabel=new JLabel("头像");
	
	
	
	public userStaffFrame(String u) {
		username=u;
		try {
			dao=StaffDao.getInstance();
			list=(LinkedList<Tranable>)dao.queryOne(username);
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
		}
		if(list.size()==0) {
			dispose();
			new NoticeFrame("无此员工");
			
		}
		
		
		Object[] o=dao.getName();
		tableModel=AstMethod.makeTableModel(o,list);
		
		JPanel panel=new JPanel();
		this.add(panel);
		
		table=table=new JTable(tableModel);
		panel.add(table.getTableHeader());
		panel.add(table);
		
		panel.add(stnoLabel);
		panel.add(stnameLabel);
		panel.add(stlevelLabel);
		panel.add(phoneLabel);
		panel.add(salaryLabel);
		panel.add(iconFileLabel);
		
		
		JButton picButton=new JButton("查看头像");
		
		
		panel.add(picButton);
		
		picButton.addMouseListener(new ButtonListener());
		
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
	
	private int delete() throws Exception{
		int n=table.getSelectedRow();
		Staff staff=new Staff((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3),(double)tableModel.getValueAt(n, 4),(byte[])tableModel.getValueAt(n, 5));
		int i=dao.deleteOne(staff);
		if(i==0) {
			return i;
		}
		tableModel.removeRow(n);
		return i;
	}
	
	public void openFile() {
		try {
			FileDialog openFile=new FileDialog(frame,"打开文件",FileDialog.LOAD);
			openFile.setVisible(true);
			if(openFile.getFile()!=null) {
				InputStream input=new FileInputStream(openFile.getDirectory()+openFile.getFile());
				iconFile=input.readAllBytes();
			iconFileLabel.setText(openFile.getFile());
			}
		}catch(Exception ex) {
			new NoticeFrame("打开错误"+ex.getMessage());
		}
	}
	
	
	
}
