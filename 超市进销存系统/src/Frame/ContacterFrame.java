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

import DbOperation.ContacterDaoImp;
import ast.AstMethod;
import ast.Contacter;
import ast.Supplier;

public class ContacterFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private ContacterDaoImp dao;
	private DefaultTableModel tableModel;
	private LinkedList<Contacter> list;
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

	
	
	
	public ContacterFrame() {
		this.setSize(300, 300);
		try {
			dao=new ContacterDaoImp();
			list=new LinkedList<Contacter>();
			ResultSet rs=dao.queryAll();
			while(rs.next()) {
				list.add(new Contacter(rs.getString("cno"),rs.getString("sno"),rs.getString("cname"),rs.getString("phone")));	
			}
			
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
		}
		Object[] o=dao.getName();
		try {
			tableModel=AstMethod.makeTableModel(o,list);
		} catch (Exception e) {
			new NoticeFrame(e.getMessage());
			e.printStackTrace();
		}
		System.out.println(list.size());
		
		table=new JTable(tableModel);
		JPanel panel=new JPanel();
		this.add(panel);
		panel.add(table.getTableHeader());
		panel.add(table);
		
		cnoLabel=new JLabel("��ϵ�˱��");
		snoLabel=new JLabel("��Ӧ�̱��");
		cnameLabel=new JLabel("��ϵ����");
		phoneLabel=new JLabel("�绰");

		
		cnoText=new JTextField(10);
		snoText=new JTextField(10);
		cnameText=new JTextField(10);
		phoneText=new JTextField(10);
		
		panel.add(cnoLabel);panel.add(cnoText);
		panel.add(snoLabel);panel.add(snoText);
		panel.add(cnameLabel);panel.add(cnameText);
		panel.add(phoneLabel);panel.add(phoneText);
		
		
		JButton insertButton=new JButton("���");
		JButton deleteButton=new JButton("ɾ��");
		JButton updateButton=new JButton("�޸�");
		JButton exportButton=new JButton("����");
		panel.add(insertButton);
		panel.add(deleteButton);
		panel.add(updateButton);
		panel.add(exportButton);
		insertButton.addMouseListener(new ButtonListener());
		deleteButton.addMouseListener(new ButtonListener());
		updateButton.addMouseListener(new ButtonListener());
		exportButton.addMouseListener(new ButtonListener());
		table.addMouseListener(new TableListener());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private class TableListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
				int n=table.getSelectedRow();
				cnoText.setText((String)tableModel.getValueAt(n, 0));
				snoText.setText((String)tableModel.getValueAt(n, 1));
				cnameText.setText((String)tableModel.getValueAt(n, 2));
				phoneText.setText((String)tableModel.getValueAt(n, 3));
				
		}
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
					LinkedList<Supplier> ls=new LinkedList <Supplier>();
					ResultSet rs=dao.queryAll();
					while(rs.next()) {
						
						list.add(new Contacter(rs.getString("cno"),rs.getString("sno"),rs.getString("cname"),rs.getString("phone")));	
					}
					AstMethod.exportCSV(ls, path);
					new NoticeFrame("�����ɹ�");
				}catch(Exception ex) {
					if(path==null) {
						new NoticeFrame("δ����·��");
					}
					else
						new NoticeFrame("��������"+ex.getMessage());
					ex.printStackTrace();
				}
				break;
			}
			}
			cnoText.setText("");
			snoText.setText("");
			cnameText.setText("");
			phoneText.setText("");
		}
	}
	
	private int delete() throws Exception{
		int n=table.getSelectedRow();
		Contacter Goods=new Contacter((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3));
		int i=dao.delete(Goods);
		if(i==0) {
			return i;
		}
		tableModel.removeRow(n);
		return i;
	}
	private int insert() throws Exception{
		Contacter temp=new Contacter(cnoText.getText(),snoText.getText(),cnameText.getText(),phoneText.getText());
		int n=dao.insert(temp);
		if(n==0) {
			return n;
		}
		
		tableModel.addRow(temp.tran());
		cnoText.setText("");
		snoText.setText("");
		cnameText.setText("");
		phoneText.setText("");
		return n;
	}
	
	private int update() throws Exception{
		int n=table.getSelectedRow();
//		Contacter oldSupplier=new Contacter((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3));
		Contacter newSupplier=new Contacter(cnoText.getText(),snoText.getText(),cnameText.getText(),phoneText.getText());
		int temp= dao.update(newSupplier);
		if(temp==0) {
			return 0;
		}
		tableModel.removeRow(n);
		
		
		tableModel.addRow(newSupplier.tran());
		
		return temp;
	}
	
	
	
}
