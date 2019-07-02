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
		JButton supplierButton=new JButton("��Ӧ�̹���");
		JButton goodsButton=new JButton("��Ʒ����");
		JButton staffButton=new JButton("Ա������");
		JButton contacterButton=new JButton("��ϵ�˹���");
		JButton userAddButton=new JButton("����û�");
		JButton userStaffButton=new JButton("������Ϣ");
		JButton purchaseListButton=new JButton("�ɹ������");
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
			new NoticeFrame("Ȩ�޼��ʧ��"+e.getMessage());
		}
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private class ButtonListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			switch (((JButton)e.getSource()).getText()) {
				case "��Ӧ�̹���":{
					
					new SupplierFrame(username);
					
					break;
				}
				
				case "��Ʒ����":{
					new GoodsFrame(username);
					break;
				}
				
				case "Ա������":{
					new StaffFrame(username);
					break;
				}
				
				case "����û�":{
					
					new NoticeFrame("Ȩ�޲���");
					
					break;
				}
				/*
				case "������Ϣ":{
					new userStaffFrame(username);
					break;
				}
				*/
				case "��ϵ�˹���":{
					new ContacterFrame(username);
					break;
				}
				case "�ɹ������":{
					new PurchaseListFrame(username);
					break;
				}
			}
		}
	}
	
	
}
