package Frame;
import java.awt.*;

import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

import DbOperation.*;
import ast.AstMethod;
import ast.Supplier;
import ast.Tranable;

public class SupplierFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private SupplierDaoImp dao;
	private DefaultTableModel tableModel;
	private LinkedList<Tranable> list;
	private JTable table;
//	private int oldRowCount;
	private String path=null;
	private JLabel snoLabel;
	private JLabel snameLabel;
	private JLabel simplyLabel;
	private JLabel addressLabel;
	private JLabel sphoneLabel;
	private JLabel mailLabel;
	
	private JTextField snoText;
	private JTextField snameText;
	private JTextField simplyText;
	private JTextField addressText;
	private JTextField sphoneText;
	private JTextField mailText;
	
	
	public SupplierFrame() {
		this.setSize(500,300);
		list=new LinkedList<Tranable>();
		try {
			dao=new SupplierDaoImp();
			ResultSet rs=dao.queryAll();
			while(rs.next()) {
				
				list.add(new Supplier(rs.getString("sno"),rs.getString("sname"),rs.getString("simply"),rs.getString("address"),rs.getString("sphone"),rs.getString("mail")));	
			}
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
		}
		
		Object[] o=dao.getName();
		tableModel=AstMethod.makeTableModel(o,list);
		
		table=new JTable(tableModel);
		JPanel panel=new JPanel();
		this.add(panel);
		panel.add(table.getTableHeader());
		panel.add(table);
//		oldRowCount=table.getRowCount();
		
		snoLabel=new JLabel("编号");
		snameLabel=new JLabel("名字");
		simplyLabel=new JLabel("简称");
		addressLabel=new JLabel("地址");
		sphoneLabel=new JLabel("电话");
		mailLabel=new JLabel("邮件");
		
		snoText=new JTextField(10);
		snameText=new JTextField(10);
		simplyText=new JTextField(10);
		addressText=new JTextField(10);
		sphoneText=new JTextField(10);
		mailText=new JTextField(10);
		
		panel.add(snoLabel);panel.add(snoText);
		panel.add(snameLabel);panel.add(snameText);
		panel.add(simplyLabel);panel.add(simplyText);
		panel.add(addressLabel);panel.add(addressText);
		panel.add(sphoneLabel);panel.add(sphoneText);
		panel.add(mailLabel);panel.add(mailText);
		
		
		JButton insertButton=new JButton("添加");
		JButton deleteButton=new JButton("删除");
		JButton updateButton=new JButton("修改");
		JButton exportButton=new JButton("导出");
		panel.add(insertButton);
		panel.add(deleteButton);
		panel.add(updateButton);
		panel.add(exportButton);
		
	
		insertButton.addMouseListener(new ButtonListener());
		deleteButton.addMouseListener(new ButtonListener());
		updateButton.addMouseListener(new ButtonListener());
		exportButton.addMouseListener(new ButtonListener());
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	

	
	
	private class ButtonListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			switch (((JButton)e.getSource()).getText()) {
			case "添加":{
				
				try {
					insert();
				}catch(Exception ex) {
					new NoticeFrame("添加失败"+ex.getMessage());
					ex.printStackTrace();
				}
				break;
			}
			
			case "删除":{
				try {
					delete();
				}catch(Exception ex) {
					new NoticeFrame("删除失败"+ex.getMessage());
				}
				break;
			}
			case "修改":{
				try {
					update();
				}catch(Exception ex) {
					new NoticeFrame("修改失败"+ex.getMessage());
					
				}
				break;
			}
			
			case "导出":{
				try {
					path=AstMethod.openFile(FileDialog.SAVE);
					@SuppressWarnings("unchecked")
					LinkedList<Tranable> ls=(LinkedList<Tranable>)dao.queryAll();
					AstMethod.exportCSV(ls, path);
					new NoticeFrame("导出成功");
				}catch(Exception ex) {
					if(path==null) {
						new NoticeFrame("未设置路径");
					}
					else
						new NoticeFrame("导出错误"+ex.getMessage());
				}
				break;
			}
			
			
			
			}
		}
	}
	
	
//	private void confirm() throws Exception{
//		for(int i=0;i<oldRowCount;i++) {
//			LinkedList<Supplier> ls=new LinkedList<Supplier>();
//			for(int i=oldRowCount-1;i<=table.getRowCount()-1;i++) {
//				ls.add(new Supplier((String)tableModel.getValueAt(i, 0),(String)tableModel.getValueAt(i, 1),(String)tableModel.getValueAt(i, 2),(String)tableModel.getValueAt(i, 3),(String)tableModel.getValueAt(i, 4),(String)tableModel.getValueAt(i, 5)));
//				dao.update(ls);
//			}
//		}
//			
//		if(oldRowCount!=table.getRowCount()) {
//			LinkedList<Supplier> ls=new LinkedList<Supplier>();
//			for(int i=oldRowCount-1;i<=table.getRowCount()-1;i++) {
//				ls.add(new Supplier((String)tableModel.getValueAt(i, 0),(String)tableModel.getValueAt(i, 1),(String)tableModel.getValueAt(i, 2),(String)tableModel.getValueAt(i, 3),(String)tableModel.getValueAt(i, 4),(String)tableModel.getValueAt(i, 5)));
//				dao.insert(ls);
//			}
//			oldRowCount=table.getRowCount();
//			
//		}
//		
//		
//		
//		
//	}
	
	
	
	private int delete() throws Exception{
		int n=table.getSelectedRow();
		Supplier supplier=new Supplier((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3),(String)tableModel.getValueAt(n, 4),(String)tableModel.getValueAt(n, 5));
		int i=dao.delete(supplier);
		if(i==0) {
			return i;
		}
		tableModel.removeRow(n);
		return i;
	}
	private int insert() throws Exception{
		Supplier temp=new Supplier(snoText.getText(),snameText.getText(),simplyText.getText(),addressText.getText(),sphoneText.getText(),mailText.getText());
		/*int n=dao.insertOne(temp);
		if(n==0) {
			return n;
		}
		
		tableModel.addRow(temp.tran());
		return n;
		*/
		SupplierDaoImp d=new SupplierDaoImp();
		d.insert(temp);
		tableModel.addRow(temp.tran());
		snoText.setText("");
		snameText.setText("");
		simplyText.setText("");
		addressText.setText("");
		sphoneText.setText("");
		mailText.setText("");
		return 0;
	}
	
	private int update() throws Exception{
		int n=table.getSelectedRow();
		Supplier newSupplier=new Supplier(snoText.getText(),snameText.getText(),simplyText.getText(),addressText.getText(),sphoneText.getText(),mailText.getText());
		int temp= dao.update(newSupplier);
		tableModel.removeRow(n);
		
		
		tableModel.addRow(newSupplier.tran());
		if(temp==0) {
			return 0;
		}
		return temp;
	}
	
}
