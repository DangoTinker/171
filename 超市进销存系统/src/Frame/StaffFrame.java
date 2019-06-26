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
import DbOperation.StaffDao;
import DbOperation.SupplierDao;
import ast.AstMethod;
import ast.Goods;
import ast.Supplier;
import ast.Tranable;

public class StaffFrame extends JFrame{
	private StaffDao dao;
	private DefaultTableModel tableModel;
	
	private JLabel stnoLabel=new JLabel("编号");
	private JLabel stnameLabel=new JLabel("姓名");
	private JLabel stlevelLabel=new JLabel("级别");
	private JLabel phoneLabel=new JLabel("电话");
	private JLabel salaryLabel=new JLabel("工资");
	private JLabel iconFileLabel=new JLabel("头像");
	
	private JTextField stnoText=new JTextField(10);
	private JTextField stnameText=new JTextField(10);
	private JTextField stlevelText=new JTextField(10);
	private JTextField phoneText=new JTextField(10);
	private JTextField salaryText=new JTextField(10);
	private JButton iconFileButton=new JButton("打开文件");
	
	
	public StaffFrame() {
		
		try {
			dao=StaffDao.getInstance();
		}catch(Exception e) {
			new NoticeFrame(e.getMessage());
		}
		
		JPanel panel=new JPanel();
		this.add(panel);
		
		
		
		
		
		this.setVisible(true);
	}
	
}
