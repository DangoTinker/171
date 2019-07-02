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
	
	private JLabel lnoLabel=new JLabel("���");
	private JLabel stnoLabel=new JLabel("Ա�����");
	private JLabel lcountLabel=new JLabel("����");
	private JLabel totalLabel=new JLabel("�ܼ�");
	private JLabel timeLabel=new JLabel("ʱ��");

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
		
		JButton insertButton=new JButton("���");
		JButton deleteButton=new JButton("ɾ��");
		JButton updateButton=new JButton("�޸�");
		JButton exportButton=new JButton("����");
		JButton purchaseButton=new JButton("��ϸ");
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
			new NoticeFrame("���Ȩ��ʧ��"+e.getMessage());
		}
		
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
		/*	case "���ļ�":{
				try {
					path=AstMethod.openFile(FileDialog.LOAD);
					FileInputStream in=new FileInputStream(path);
					iconFile=in.readAllBytes();
					iconFileLabel.setText(path);
				}catch(Exception ex) {
					new NoticeFrame("���ļ�ʧ��"+ex.getMessage());
				}
				break;
			}
			
			case "�鿴ͷ��":{
				int n=table.getSelectedRow();
				new PicFrame((byte[])tableModel.getValueAt(n, 5));
				break;
			}*/
			
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
			case "��ϸ":{
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
