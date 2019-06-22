package Frame;

import java.awt.Label;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DbOperation.DbOperation;
import DbOperation.GoodsDao;
import DbOperation.SupplierDao;
import ast.AstMethod;
import ast.Tranable;

public class GoodsFrame extends JFrame{
	private GoodsDao dao;
	private String username;
	private DefaultTableModel tableModel;
	private LinkedList<Tranable> list;
	private JTable table;
	public GoodsFrame(String u) {
		username=u;
		try {
			dao=new GoodsDao();
			list=(LinkedList<Tranable>)dao.queryAll();
			
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
		}
		Object[] o=new Object[5];
		o[0]="商品编号";
		o[1]="供应商编号";
		o[2]="商品名";
		o[3]="简称";
		o[4]="单价";
		tableModel=AstMethod.makeTableModel(o,list);
		System.out.println(list.size());
		
		table=new JTable(tableModel);
		JPanel panel=new JPanel();
		this.add(panel);
		panel.add(table.getTableHeader());
		panel.add(table);
		panel.add(new Label("test"));
		this.setVisible(true);
	}
}
