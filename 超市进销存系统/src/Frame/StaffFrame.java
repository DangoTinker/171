package Frame;
import java.awt.FileDialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DbOperation.*;
import ast.AstMethod;
import ast.Staff;
import ast.Tranable;

public class StaffFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private StaffDaoImp dao;
	private DefaultTableModel tableModel;
	private LinkedList<Tranable> list;
	private JTable table;
	private byte[] iconFile;
	private String path=null;
	private JLabel stnoLabel=new JLabel("���");
	private JLabel stnameLabel=new JLabel("����");
	private JLabel stlevelLabel=new JLabel("����");
	private JLabel phoneLabel=new JLabel("�绰");
	private JLabel salaryLabel=new JLabel("����");
	private JLabel iconFileLabel=new JLabel("ͷ��");
	
	private JTextField stnoText=new JTextField(10);
	private JTextField stnameText=new JTextField(10);
	private JTextField stlevelText=new JTextField(10);
	private JTextField phoneText=new JTextField(10);
	private JTextField salaryText=new JTextField(10);
	private JButton iconFileButton=new JButton("���ļ�");
	
	
	public StaffFrame() {
		this.setSize(500, 300);
		try {
			list=new LinkedList<Tranable>();
			dao=new StaffDaoImp();
			ResultSet rs=dao.queryAll();
			while(rs.next()) {
				list.add(new Staff(rs.getString("stno"),rs.getString("stname"),rs.getString("stlevel"),rs.getString("phone"),rs.getDouble("salary"),(((java.sql.Blob)rs.getBlob("icon")).getBinaryStream()).readAllBytes()));	
			}
			
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
		}
		Object[] o=dao.getName();
		tableModel=AstMethod.makeTableModel(o,list);
		
		JPanel panel=new JPanel();
		this.add(panel);
		
		table=new JTable(tableModel);
		panel.add(table.getTableHeader());
		panel.add(table);
		
		panel.add(stnoLabel);panel.add(stnoText);
		panel.add(stnameLabel);panel.add(stnameText);
		panel.add(stlevelLabel);panel.add(stlevelText);
		panel.add(phoneLabel);panel.add(phoneText);
		panel.add(salaryLabel);panel.add(salaryText);
		panel.add(iconFileLabel);panel.add(iconFileButton);
		
		
		JButton insertButton=new JButton("���");
		JButton deleteButton=new JButton("ɾ��");
		JButton updateButton=new JButton("�޸�");
		JButton picButton=new JButton("�鿴ͷ��");
		JButton exportButton=new JButton("����");
		
		panel.add(insertButton);
		panel.add(deleteButton);
		panel.add(updateButton);
		panel.add(picButton);
		panel.add(exportButton);
		iconFileButton.addMouseListener(new ButtonListener());
		insertButton.addMouseListener(new ButtonListener());
		deleteButton.addMouseListener(new ButtonListener());
		updateButton.addMouseListener(new ButtonListener());
		exportButton.addMouseListener(new ButtonListener());
		picButton.addMouseListener(new ButtonListener());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private class ButtonListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			switch (((JButton)e.getSource()).getText()) {
			case "���":{
				
				try {
					insert();
				}catch(Exception ex) {
					new NoticeFrame("���ʧ��"+ex.getMessage());
					ex.printStackTrace();
				}
				break;
			}
			
			case "ɾ��":{
				try {
					delete();
				}catch(Exception ex) {
					new NoticeFrame("ɾ��ʧ��"+ex.getMessage());
					ex.printStackTrace();
				}
				break;
			}
			case "�޸�":{
				try {
					update();
				}catch(Exception ex) {
					new NoticeFrame("�޸�ʧ��"+ex.getMessage());
					
				}
				break;
			}
			case "���ļ�":{
				try {
					path=AstMethod.openFile(FileDialog.LOAD);
					FileInputStream in=new FileInputStream(path);
					iconFile=in.readAllBytes();
					iconFileLabel.setText(path);
					in.close();
				}catch(Exception ex) {
					new NoticeFrame("���ļ�ʧ��"+ex.getMessage());
				}
				break;
			}
			
			case "�鿴ͷ��":{
				int n=table.getSelectedRow();
				new PicFrame((byte[])tableModel.getValueAt(n, 5));
				break;
			}
			
			case "����":{
				try {
					path=AstMethod.openFile(FileDialog.SAVE);
					@SuppressWarnings("unchecked")
					LinkedList<Tranable> ls=(LinkedList<Tranable>)dao.queryAll();
					AstMethod.exportCSV(ls, path);
					new NoticeFrame("�����ɹ�");
				}catch(Exception ex) {
					if(path==null) {
						new NoticeFrame("δ����·��");
					}
					else
						new NoticeFrame("��������"+ex.getMessage());
				}
				break;
			}
			
			}
		}
	}
	
	private int delete() throws Exception{
		int n=table.getSelectedRow();
		Staff staff=new Staff((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3),(double)tableModel.getValueAt(n, 4),(byte[])tableModel.getValueAt(n, 5));
		int i=dao.delete(staff);
		if(i==0) {
			return i;
		}
		tableModel.removeRow(n);
		return i;
	}
	

	
	
	private int insert() throws Exception{
		Staff staff=new Staff(stnoText.getText(),stnameText.getText(),stlevelText.getText(),phoneText.getText(),Double.valueOf(salaryText.getText()),iconFile);
		int n=dao.insert(staff);
		if(n==0) {
			return n;
		}
		
		tableModel.addRow(staff.tran());
		stnoText.setText("");
		stnameText.setText(""); 
		stlevelText.setText(""); 
		phoneText.setText(""); 
		salaryText.setText(""); 
		iconFileLabel.setText("");
		iconFile=null;
		return n;
	}
	
	private int update() throws Exception{
		int n=table.getSelectedRow();
		Staff newStaff=new Staff(stnoText.getText(),stnameText.getText(),stlevelText.getText(),phoneText.getText(),Double.valueOf(salaryText.getText()),iconFile);
		int temp= dao.update(newStaff);
		tableModel.removeRow(n);
		
		
		tableModel.addRow(newStaff.tran());
		if(temp==0) {
			return 0;
		}
		return temp;
	}
	
}
