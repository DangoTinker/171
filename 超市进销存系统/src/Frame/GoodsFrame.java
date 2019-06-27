package Frame;

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
import DbOperation.GoodsDao;
import DbOperation.SupplierDao;
import ast.AstMethod;
import ast.Goods;
import ast.Supplier;
import ast.Tranable;

public class GoodsFrame extends JFrame{
	private GoodsDao dao;
	private String username;
	private DefaultTableModel tableModel;
	private LinkedList<Tranable> list;
	private JTable table;

	
	private JLabel gnoLabel;
	private JLabel gnameLabel;
	private JLabel simplyLabel;
	private JLabel snoLabel;
	private JLabel priceLabel;
	
	private JTextField gnoText;
	private JTextField gnameText;
	private JTextField simplyText;
	private JTextField snoText;
	private JTextField priceText;
	
	
	
	public GoodsFrame(String u) {
		this.setSize(500, 300);
		username=u;
		try {
			dao=GoodsDao.getInstance();
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
		
		gnoLabel=new JLabel("商品编号");
		snoLabel=new JLabel("供应商编号");
		gnameLabel=new JLabel("商品名");
		simplyLabel=new JLabel("简介");
		priceLabel=new JLabel("单价");
		
		gnoText=new JTextField(10);
		snoText=new JTextField(10);
		gnameText=new JTextField(10);
		simplyText=new JTextField(10);
		priceText=new JTextField(10);
		
		panel.add(gnoLabel);panel.add(gnoText);
		panel.add(snoLabel);panel.add(snoText);
		panel.add(gnameLabel);panel.add(gnameText);
		panel.add(simplyLabel);panel.add(simplyText);
		panel.add(priceLabel);panel.add(priceText);
		
		
		JButton insertButton=new JButton("添加");
		JButton deleteButton=new JButton("删除");
		JButton updateButton=new JButton("修改");
		
		panel.add(insertButton);
		panel.add(deleteButton);
		panel.add(updateButton);
		
		insertButton.addMouseListener(new ButtonListener());
		deleteButton.addMouseListener(new ButtonListener());
		updateButton.addMouseListener(new ButtonListener());
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
				
			}
		}
	}
	
	private int delete() throws Exception{
		int n=table.getSelectedRow();
		Goods Goods=new Goods((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3),Double.valueOf((String)tableModel.getValueAt(n, 4)));
		int i=dao.deleteOne(Goods);
		if(i==0) {
			return i;
		}
		tableModel.removeRow(n);
		return i;
	}
	private int insert() throws Exception{
		Goods temp=new Goods(gnoText.getText(),snoText.getText(),gnameText.getText(),simplyText.getText(),Double.valueOf(priceText.getText()));
		int n=dao.insertOne(temp);
		if(n==0) {
			return n;
		}
		
		tableModel.addRow(temp.tran());
		return n;
	}
	
	private int update() throws Exception{
		int n=table.getSelectedRow();
		Goods oldSupplier=new Goods((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3),Double.valueOf((String)tableModel.getValueAt(n, 4)));
		Goods newSupplier=new Goods(gnoText.getText(),snoText.getText(),gnameText.getText(),simplyText.getText(),Double.valueOf(priceText.getText()));
		int temp= dao.updateOne(oldSupplier, newSupplier);
		tableModel.removeRow(n);
		
		
		tableModel.addRow(newSupplier.tran());
		if(temp==0) {
			return 0;
		}
		return temp;
	}
	
	
	
}
