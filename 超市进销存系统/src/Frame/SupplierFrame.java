package Frame;
import java.awt.*;

import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

import DbOperation.DbOperation;
import DbOperation.SupplierDao;
import ast.AstMethod;
import ast.Supplier;
import ast.Tranable;

public class SupplierFrame extends JFrame{
	private SupplierDao dao;
	
	private DefaultTableModel tableModel;
	private LinkedList<Tranable> list;
	private JTable table;
	private int oldRowCount;
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
		
		try {
			dao=new SupplierDao();
			list=(LinkedList<Tranable>)dao.queryAll();
			
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
		}
		
		Object[] o=dao.getName();
		tableModel=AstMethod.makeTableModel(o,list);
		System.out.println(list.size());
		
		table=new JTable(tableModel);
		JPanel panel=new JPanel();
		this.add(panel);
		panel.add(table.getTableHeader());
		panel.add(table);
		oldRowCount=table.getRowCount();
		
		snoLabel=new JLabel("±àºÅ");
		snameLabel=new JLabel("Ãû×Ö");
		simplyLabel=new JLabel("¼ò³Æ");
		addressLabel=new JLabel("µØÖ·");
		sphoneLabel=new JLabel("µç»°");
		mailLabel=new JLabel("ÓÊ¼þ");
		
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
		
		
		JButton insertButton=new JButton("Ìí¼Ó");
		JButton deleteButton=new JButton("É¾³ý");
		JButton updateButton=new JButton("ÐÞ¸Ä");
		panel.add(insertButton);
		panel.add(deleteButton);
		panel.add(updateButton);
		
		insertButton.addMouseListener(new ButtonListener());
		deleteButton.addMouseListener(new ButtonListener());
		updateButton.addMouseListener(new ButtonListener());
		this.setVisible(true);
	}
	
	private class ButtonListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			switch (((JButton)e.getSource()).getText()) {
			case "Ìí¼Ó":{
				Supplier temp=new Supplier(snoText.getText(),snameText.getText(),simplyText.getText(),addressText.getText(),sphoneText.getText(),mailText.getText());
				try {
					insert(temp);
				}catch(Exception ex) {
					new NoticeFrame("Ìí¼ÓÊ§°Ü"+ex.getMessage());
				}
				break;
			}
			
			case "É¾³ý":{
				try {
					delete();
				}catch(Exception ex) {
					new NoticeFrame("É¾³ýÊ§°Ü"+ex.getMessage());
				}
				break;
			}
			case "ÐÞ¸Ä":{
				try {
					update();
				}catch(Exception ex) {
					new NoticeFrame("ÐÞ¸ÄÊ§°Ü"+ex.getMessage());
					
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
		int i=dao.deleteOne(supplier);
		if(i==0) {
			return i;
		}
		tableModel.removeRow(n);
		return i;
	}
	private int insert(Supplier temp) throws Exception{
		int n=dao.insertOne(temp);
		if(n==0) {
			return n;
		}
		
		tableModel.addRow(temp.tran());
		return n;
	}
	
	private int update() throws Exception{
		int n=table.getSelectedRow();
		Supplier oldSupplier=new Supplier((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3),(String)tableModel.getValueAt(n, 4),(String)tableModel.getValueAt(n, 5));
		Supplier newSupplier=new Supplier(snoText.getText(),snameText.getText(),simplyText.getText(),addressText.getText(),sphoneText.getText(),mailText.getText());
		int temp= dao.updateOne(oldSupplier, newSupplier);
		tableModel.removeRow(n);
		
		
		tableModel.addRow(newSupplier.tran());
		if(temp==0) {
			return 0;
		}
		return temp;
	}
	
	
}
