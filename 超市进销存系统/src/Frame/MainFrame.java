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
	public MainFrame(String u) {
		this.setSize(300,200);
		username=u;
		JPanel panel=new JPanel();
		JButton supplierButton=new JButton("供应商管理");
		JButton goodsButton=new JButton("商品管理");
		JButton staffButton=new JButton("员工管理");
		JButton contacterButton=new JButton("联系人管理");
		JButton userAddButton=new JButton("添加用户");
		JButton userStaffButton=new JButton("个人信息");
		JButton purchaseListButton=new JButton("采购表管理");
		this.add(panel);
		panel.add(supplierButton);
		panel.add(goodsButton);
		panel.add(staffButton);
		panel.add(userAddButton);
		panel.add(userStaffButton);
		panel.add(contacterButton);
		panel.add(purchaseListButton);
		try {
			if(AstMethod.isRoot(username)) {
				supplierButton.addMouseListener(new ButtonListener());
				staffButton.addMouseListener(new ButtonListener());
				userAddButton.addMouseListener(new ButtonListener());
				contacterButton.addMouseListener(new ButtonListener());
				
				goodsButton.addMouseListener(new ButtonListener());
				userStaffButton.addMouseListener(new ButtonListener());
				purchaseListButton.addMouseListener(new ButtonListener());
				
			}
			else {
				goodsButton.addMouseListener(new ButtonListener());
				userStaffButton.addMouseListener(new ButtonListener());
				
				supplierButton.setEnabled(false);
				staffButton.setEnabled(false);
				userAddButton.setEnabled(false);
				contacterButton.setEnabled(false);
				purchaseListButton.addMouseListener(new ButtonListener());
			}
		} catch (Exception e) {
			new NoticeFrame("权限检测失败"+e.getMessage());
		}
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private class ButtonListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			switch (((JButton)e.getSource()).getText()) {
				case "供应商管理":{
					
					new SupplierFrame(username);
					
					break;
				}
				
				case "商品管理":{
					new GoodsFrame(username);
					break;
				}
				
				case "员工管理":{
					new StaffFrame(username);
					break;
				}
				
				case "添加用户":{
					
					new NoticeFrame("权限不足");
					
					break;
				}
				/*
				case "个人信息":{
					new userStaffFrame(username);
					break;
				}
				*/
				case "联系人管理":{
					new ContacterFrame(username);
					break;
				}
				case "采购表管理":{
					new PurchaseListFrame(username);
					break;
				}
			}
		}
	}
	
	
}
