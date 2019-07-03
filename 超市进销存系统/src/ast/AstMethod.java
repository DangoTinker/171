package ast;

import java.awt.FileDialog;
import java.io.*;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.*;

import DbOperation.DbOperation;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class AstMethod {
	/*
	public static DefaultTableModel makeTableModel(Object[] name,List<Tranable> list) {
		Object[][] o=new Object[list.size()][name.length];
		int i=0;
		
		for(Tranable t:list) {
			o[i]=t.tran();
			i++;
		}
		return new DefaultTableModel(o,name);
	}
	*/
	public static DefaultTableModel makeTableModel(Object[] name,List list) throws Exception {
		Object[][] o=new Object[list.size()][name.length];
		if(list.size()!=0) {
			
		
		
		int i=0;
		
		Class clazz=list.get(0).getClass();
		Field[] fields=clazz.getDeclaredFields();
		for(Object t:list) {
			for(int j=0;j<fields.length;j++) {
				fields[j].setAccessible(true);
				o[i][j]=fields[j].get(t);
			}
			
			i++;
		}
		}
		return new DefaultTableModel(o,name);
	}
	
	
	
	public static boolean isRoot(String username) throws Exception{
		DbOperation db=DbOperation.getInstance();
		db.linkDb();
		Object[] o=new Object[1];
		o[0]=username;
		ResultSet rs=db.query("select role from userMessage where username=?", o);
		rs.next();
		if(rs.getString("role").equals("root")) {
			return true;
		}
		return false;
	}
	
	public static void exportCSV(List ls,String path)throws Exception{
		FileOutputStream fileOut=new FileOutputStream(path);
		BufferedOutputStream buffer=new BufferedOutputStream(fileOut);
		Class clazz=ls.get(0).getClass();
		
		Field[] fields=clazz.getDeclaredFields();
		
		
		for(int i=0;i<ls.size();i++) {
			Object[] o=new Object[fields.length];
			
			for(int j=0;j<fields.length;j++) {
				fields[j].setAccessible(true);
				o[j]=fields[j].get(ls.get(i));
			}
				
			
			
			for(int j=0;j<o.length;j++) {
				if(!(o[j] instanceof byte[])) {
					String s=o[j].toString()+",";
					byte[] b=s.getBytes();
					buffer.write(b);
				}
			}
			buffer.write("\n".getBytes());
		}
		buffer.flush();
		buffer.close();
	}
	
	public static String openFile(int o) throws Exception{
		FileDialog file=new FileDialog(new JFrame(),"打开文件",o);
	
			file.setVisible(true);
			String path=null;
			if(file.getFile()!=null) {
				path=file.getDirectory()+file.getFile();
	
			}
			return path;
	}
	
	
	
}