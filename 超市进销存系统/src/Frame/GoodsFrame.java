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

import DbOperation.GoodsDaoImp;
import ast.AstMethod;
import ast.Goods;
import ast.Tranable;

public class GoodsFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private GoodsDaoImp dao;
	private String username;
	private DefaultTableModel tableModel;
	private LinkedList<Tranable> list;
	private JTable table;
	private String path;
	
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
			list=new LinkedList<Tranable>();
			dao=new GoodsDaoImp();
			ResultSet rs=dao.queryAll();
			while(rs.next()) {
				list.add(new Goods(rs.getString("gno"),rs.getString("sno"),rs.getString("gname"),rs.getString("simply"),rs.getDouble("price")));	
			}
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
			e.printStackTrace();
		}
		Object[] o=dao.getName();
		tableModel=AstMethod.makeTableModel(o,list);
		
		table=new JTable(tableModel);
		JPanel panel=new JPanel();
		this.add(panel);
		panel.add(table.getTableHeader());
		panel.add(table);
		
		gnoLabel=new JLabel("��Ʒ���");
		snoLabel=new JLabel("��Ӧ�̱��");
		gnameLabel=new JLabel("��Ʒ��");
		simplyLabel=new JLabel("���");
		priceLabel=new JLabel("����");
		
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
					ex.printStackTrace();
				}
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
		Goods Goods=new Goods((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3),(double)(tableModel.getValueAt(n, 4)));
		int i=dao.delete(Goods);
		if(i==0) {
			return i;
		}
		tableModel.removeRow(n);
		return i;
	}
	private int insert() throws Exception{

		Goods temp=new Goods(gnoText.getText(),snoText.getText(),gnameText.getText(),simplyText.getText(),Double.valueOf(priceText.getText()));
		int n=dao.insert(temp);
		if(n==0) {
			return n;
		}
		
		tableModel.addRow(temp.tran());
		return n;
	}
	
	private int update() throws Exception{
		int n=table.getSelectedRow();
//		Goods oldSupplier=new Goods((String)tableModel.getValueAt(n, 0),(String)tableModel.getValueAt(n, 1),(String)tableModel.getValueAt(n, 2),(String)tableModel.getValueAt(n, 3),((double)tableModel.getValueAt(n, 4)));
		Goods newSupplier=new Goods(gnoText.getText(),snoText.getText(),gnameText.getText(),simplyText.getText(),Double.valueOf(priceText.getText()));
		int temp= dao.update(newSupplier);
		tableModel.removeRow(n);
		
		
		tableModel.addRow(newSupplier.tran());
		if(temp==0) {
			return 0;
		}
		return temp;
	}
	
	
	
	
}
