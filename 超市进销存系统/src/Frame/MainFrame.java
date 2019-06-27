package Frame;
import java.awt.*;

import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

import DbOperation.DbOperation;
import ast.AstMethod;
public class MainFrame extends JFrame{
	private String username;
	private boolean root;
	public MainFrame(String u) throws Exception{
		username=u;
		root=AstMethod.isRoot(username);
		JPanel panel=new JPanel();
		JButton supplierButton=new JButton("供应商管理");
		JButton goodsButton=new JButton("商品管理");
		JButton staffButton=new JButton("员工管理");
		JButton userAddButton=new JButton("添加用户");
		JButton userStaffButton=new JButton("个人信息");
		this.add(panel);
		panel.add(supplierButton);
		panel.add(goodsButton);
		panel.add(staffButton);
		panel.add(userAddButton);
		panel.add(userStaffButton);
		supplierButton.addMouseListener(new ButtonListener());
		goodsButton.addMouseListener(new ButtonListener());
		staffButton.addMouseListener(new ButtonListener());
		userAddButton.addMouseListener(new ButtonListener());
		userStaffButton.addMouseListener(new ButtonListener());
		this.setVisible(true);
	}
	
	private class ButtonListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			switch (((JButton)e.getSource()).getText()) {
				case "供应商管理":{
					if(root) {
						new SupplierFrame();
					}
					else {
						new NoticeFrame("权限不足");
					}
					break;
				}
				
				case "商品管理":{
					new GoodsFrame(username);
					break;
				}
				
				case "员工管理":{
					if(root)
						new StaffFrame(username);
					else
						new userStaffFrame(username);
					break;
				}
				
				case "添加用户":{
					if(root) {
						new userAddFrame();
					}
					else {
						new NoticeFrame("权限不足");
					}
					break;
				}
				
				case "个人信息":{
					new userStaffFrame(username);
					break;
				}
				
			}
		}
	}
	
	
}
