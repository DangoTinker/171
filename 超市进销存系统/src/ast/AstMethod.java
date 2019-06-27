package ast;

import java.sql.ResultSet;
import java.util.*;

import DbOperation.DbOperation;
import javax.swing.table.DefaultTableModel;

public class AstMethod {
	
	public static DefaultTableModel makeTableModel(Object[] name,List<Tranable> list) {
		Object[][] o=new Object[list.size()][name.length];
		int i=0;
		for(Tranable t:list) {
			o[i]=t.tran();
			i++;
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
}
