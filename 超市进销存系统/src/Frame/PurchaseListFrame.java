package Frame;

import java.awt.FileDialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import ast.PurchaseList;
import ast.Tranable;

public class PurchaseListFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private PurchaseListDaoImp dao;
	private DefaultTableModel tableModel;
	private LinkedList<Tranable> list;
	private String username;
	private JTable table;
	private String path=null;
	
	private JLabel lnoLabel=new JLabel("编号");
	private JLabel stnoLabel=new JLabel("员工编号");
	private JLabel lcountLabel=new JLabel("数量");
	private JLabel totalLabel=new JLabel("总计");
	private JLabel timeLabel=new JLabel("时间");

	private JTextField lnoText=new JTextField(10);
	private JTextField stnoText=new JTextField(10);
	private JTextField countText=new JTextField(10);
	private JTextField totalText=new JTextField(10);
	private JTextField timeText=new JTextField(10);

	
	
	public PurchaseListFrame(String u) {
		this.setSize(500, 300);
		username=u;
		try {
			list=new LinkedList<Tranable>();
			dao=new PurchaseListDaoImp();
			ResultSet rs=dao.queryAll();
			while(rs.next()) {
				list.add(new PurchaseList(rs.getString("lno"),rs.getString("stno"),rs.getInt("lcount"),rs.getDouble("total"),rs.getString("time")));	
			}
			
			
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
			e.printStackTrace();
		}
		Object[] o=dao.getName();
		tableModel=AstMethod.makeTableModel(o,list);
		
		JPanel panel=new JPanel();
		this.add(panel);
		
		table=new JTable(tableModel);
		panel.add(table.getTableHeader());
		panel.add(table);
		
		panel.add(lnoLabel);panel.add(lnoText);
		panel.add(stnoLabel);panel.add(stnoText);
		panel.add(lcountLabel);panel.add(countText);
		panel.add(totalLabel);panel.add(totalText);
		panel.add(timeLabel);panel.add(timeText);
		
		JButton insertButton=new JButton("添加");
		JButton deleteButton=new JButton("删除");
		JButton updateButton=new JButton("修改");
		JButton exportButton=new JButton("导出");
		JButton purchaseButton=new JButton("明细");
		panel.add(insertButton);
		panel.add(deleteButton);
		panel.add(updateButton);
		panel.add(exportButton);
		panel.add(purchaseButton);
		try {
			if(AstMethod.isRoot(username)) {
				insertButton.addMouseListener(new ButtonListener());
				deleteButton.addMouseListener(new ButtonListener());
				updateButton.addMouseListener(new ButtonListener());
				exportButton.addMouseListener(new ButtonListener());
				purchaseButton.addMouseListener(new ButtonListener());
			}
			else {
				insertButton.setEnabled(false);
				deleteButton.setEnabled(false);
				updateButton.setEnabled(false);
				exportButton.addMouseListener(new ButtonListener());
				purchaseButton.addMouseListener(new ButtonListener());
			}
		} catch (Exception e) {
			new NoticeFrame("检测权限失败"+e.getMessage());
		}
		
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
					ex.printStackTrace();
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
		/*	case "打开文件":{
				try {
					path=AstMethod.openFile(FileDialog.LOAD);
					FileInputStream in=new FileInputStream(path);
					iconFile=in.readAllBytes();
					iconFileLabel.setText(path);
				}catch(Exception ex) {
					new NoticeFrame("打开文件失败"+ex.getMessage());
				}
				break;
			}
			
			case "查看头像":{
				int n=table.getSelectedRow();
				new PicFrame((byte[])tableModel.getValueAt(n, 5));
				break;
			}*/
			
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
			case "明细":{
				new PurchaseFrame(username,(String)tableModel.getValueAt(table.getSelectedRow(), 0));
				break;
			}
			}
		}
	}
	
	private int delete() throws Exception{
		int n=table.getSelectedRow();
		PurchaseList Plist=new PurchaseList((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(int)tableModel.getValueAt(n, 2),(double)tableModel.getValueAt(n, 3),(String)tableModel.getValueAt(n, 4));
		int i=dao.delete(Plist);
		if(i==0) {
			return i;
		}
		tableModel.removeRow(n);
		return i;
	}
	private int insert() throws Exception{
		PurchaseList Plist=new PurchaseList(lnoText.getText(),stnoText.getText(),Integer.valueOf(countText.getText()),Double.valueOf(totalText.getText()),timeText.getText());
		int n=dao.insert(Plist);
		if(n==0) {
			return n;
		}
		
		tableModel.addRow(Plist.tran());
		lnoText.setText(""); 
		stnoText.setText("");
		countText.setText("");
		totalText.setText("");
		timeText.setText("");
		return n;
	}
	
	private int update() throws Exception{
		int n=table.getSelectedRow();
		PurchaseList newPlist=new PurchaseList(lnoText.getText(),stnoText.getText(),Integer.valueOf(countText.getText()),Double.valueOf(totalText.getText()),timeText.getText());
		int temp= dao.update(newPlist);
		tableModel.removeRow(n);
		tableModel.addRow(newPlist.tran());
		if(temp==0) {
			return 0;
		}
		return temp;
	}
	
}
