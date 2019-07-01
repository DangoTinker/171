package Frame;

import java.awt.FileDialog;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DbOperation.DbOperation;
import DbOperation.ContacterDao;

import ast.AstMethod;
import ast.Contacter;

import ast.Tranable;

public class ContacterFrame extends JFrame{
	private ContacterDao dao;
	private String username;
	private DefaultTableModel tableModel;
	private LinkedList<Tranable> list;
	private JTable table;

	private String path;
	private JLabel cnoLabel;
	private JLabel snoLabel;
	private JLabel cnameLabel;
	private JLabel phoneLabel;

	
	private JTextField cnoText;
	private JTextField snoText;
	private JTextField cnameText;
	private JTextField phoneText;

	
	
	
	public ContacterFrame(String u) {
		this.setSize(300, 300);
		username=u;
		try {
			dao=ContacterDao.getInstance();
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
		
		cnoLabel=new JLabel("联系人编号");
		snoLabel=new JLabel("供应商编号");
		cnameLabel=new JLabel("联系人名");
		phoneLabel=new JLabel("电话");

		
		cnoText=new JTextField(10);
		snoText=new JTextField(10);
		cnameText=new JTextField(10);
		phoneText=new JTextField(10);
		
		panel.add(cnoLabel);panel.add(cnoText);
		panel.add(snoLabel);panel.add(snoText);
		panel.add(cnameLabel);panel.add(cnameText);
		panel.add(phoneLabel);panel.add(phoneText);
		
		
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	private int delete() throws Exception{
		int n=table.getSelectedRow();
		Contacter Goods=new Contacter((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3));
		int i=dao.deleteOne(Goods);
		if(i==0) {
			return i;
		}
		tableModel.removeRow(n);
		return i;
	}
	private int insert() throws Exception{
		Contacter temp=new Contacter(cnoText.getText(),snoText.getText(),cnameText.getText(),phoneText.getText());
		int n=dao.insertOne(temp);
		if(n==0) {
			return n;
		}
		
		tableModel.addRow(temp.tran());
		return n;
	}
	
	private int update() throws Exception{
		int n=table.getSelectedRow();
		Contacter oldSupplier=new Contacter((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3));
		Contacter newSupplier=new Contacter(cnoText.getText(),snoText.getText(),cnameText.getText(),phoneText.getText());
		int temp= dao.updateOne(oldSupplier, newSupplier);
		tableModel.removeRow(n);
		
		
		tableModel.addRow(newSupplier.tran());
		if(temp==0) {
			return 0;
		}
		return temp;
	}
	
	
	
}
