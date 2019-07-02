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

import DbOperation.PurchaseDaoImp;
import ast.AstMethod;
import ast.Purchase;
import ast.Tranable;

public class PurchaseFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private PurchaseDaoImp dao;
	private DefaultTableModel tableModel;
	private LinkedList<Tranable> list;
	private String username;
	private JTable table;
	private String path=null;
	private String listLno;
	private JLabel lnoLabel=new JLabel("�嵥���");
	private JLabel gnoLabel=new JLabel("��Ʒ���");
	private JLabel countLabel=new JLabel("����");

	private JTextField lnoText=new JTextField(10);
	private JTextField gnoText=new JTextField(10);
	private JTextField countText=new JTextField(10);

	public PurchaseFrame(String u,String l) {
		this.setSize(250, 300);
		listLno=l;
		username=u;
		try {
			list=new LinkedList<Tranable>();
			dao=new PurchaseDaoImp();
			ResultSet rs=dao.queryAll();
			while(rs.next()) {
				list.add(new Purchase(rs.getString("lno"),rs.getString("gno"),rs.getInt("count")));	
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
		
		panel.add(lnoLabel);panel.add(lnoText);
		panel.add(gnoLabel);panel.add(gnoText);
		panel.add(countLabel);panel.add(countText);

		JButton insertButton=new JButton("���");
		JButton deleteButton=new JButton("ɾ��");
		JButton updateButton=new JButton("�޸�");
		JButton exportButton=new JButton("����");
		
		panel.add(insertButton);
		panel.add(deleteButton);
		panel.add(updateButton);
		panel.add(exportButton);

		try {
			if(AstMethod.isRoot(username)) {
				insertButton.addMouseListener(new ButtonListener());
				deleteButton.addMouseListener(new ButtonListener());
				updateButton.addMouseListener(new ButtonListener());
				exportButton.addMouseListener(new ButtonListener());
			}
			else {
				insertButton.setEnabled(false);
				deleteButton.setEnabled(false);
				updateButton.setEnabled(false);
				exportButton.addMouseListener(new ButtonListener());
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
			case "����":{
				try {
					path=AstMethod.openFile(FileDialog.SAVE);
					@SuppressWarnings("unchecked")
					LinkedList<Tranable> ls=(LinkedList<Tranable>)dao.queryAll(listLno);
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
		Purchase purch=new Purchase((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(int)tableModel.getValueAt(n, 2));
		int i=dao.delete(purch);
		if(i==0) {
			return i;
		}
		tableModel.removeRow(n);
		return i;
	}
	private int insert() throws Exception{
		Purchase purch=new Purchase(lnoText.getText(),gnoText.getText(),Integer.valueOf(countText.getText()));
		int n=dao.insert(purch);
		if(n==0) {
			return n;
		}
		
		tableModel.addRow(purch.tran());
		return n;
	}
	
	private int update() throws Exception{
		int n=table.getSelectedRow();
		Purchase newpurch=new Purchase(lnoText.getText(),gnoText.getText(),Integer.valueOf(countText.getText()));
		int temp= dao.update( newpurch);
		tableModel.removeRow(n);
		tableModel.addRow(newpurch.tran());
		if(temp==0) {
			return 0;
		}
		return temp;
	}
	
}
