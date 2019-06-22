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
		JButton supplierButton=new JButton("��Ӧ�̹���");
		JButton goodsButton=new JButton("��Ʒ����");
		this.add(panel);
		panel.add(supplierButton);
		panel.add(goodsButton);
		supplierButton.addMouseListener(new ButtonListener());
		goodsButton.addMouseListener(new ButtonListener());
		this.setVisible(true);
	}
	
	private class ButtonListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			switch (((JButton)e.getSource()).getText()) {
				case "��Ӧ�̹���":{
					if(root) {
						new SupplierFrame();
					}
					else {
						new NoticeFrame("Ȩ�޲���");
					}
					break;
				}
				
				case "��Ʒ����":{
					new GoodsFrame(username);
					break;
				}
			
			}
		}
	}
	
	
}
