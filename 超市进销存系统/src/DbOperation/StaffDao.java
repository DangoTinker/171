package DbOperation;

import java.sql.ResultSet;
import java.util.*;

import ast.Staff;

public class StaffDao {
	private DbOperation db;
	private Object[] name;
	private int count;
	private static StaffDao s=null;
	
	public static StaffDao getInstance() throws Exception{
		if(s==null) {
			return new StaffDao();
		}
		return s;
	}
	
	public Object[] getName() {
		
		return name;
	}
	
	public int getCount(){
		return count;
	}
	
	
	public StaffDao() throws Exception{
		name=new Object[6];
		name[0]="编号";
		name[1]="姓名";
		name[2]="级别";
		name[3]="电话";
		name[4]="工资";
		name[5]="头像";
		count=6;
		db=DbOperation.getInstance();
		if(!db.isLinked())
			db.linkDb();
		
	}
	
	public List queryAll() throws Exception{
		Object[] o=new Object[0];
		ResultSet rs=db.query("select * from staff", o);
		LinkedList<Staff> ls=new LinkedList<Staff>();
		while(rs.next()) {
			ls.add(new Staff(rs.getString("stno"),rs.getString("stname"),rs.getString("stlevel"),rs.getString("phone"),rs.getDouble("salary"),(((java.sql.Blob)rs.getBlob("icon")).getBinaryStream()).readAllBytes()));
			
		}
		return ls;
		
		
	}
	public List queryOne(String username) throws Exception{
		Object[] o=new Object[1];
		o[0]=username;
		LinkedList<Staff> ls=new LinkedList<Staff>();
		ResultSet rs=db.query("select * from staff where stno=?", o);
		if(!rs.next()) {
			return ls;
		}
		String stno=rs.getString("stno");
		String stname=rs.getString("stname");
		String stlevel=rs.getString("stlevel");
		String phone=rs.getString("phone");
		double salary=rs.getDouble("salary");
		byte[] icon=(((java.sql.Blob)rs.getBlob("icon")).getBinaryStream()).readAllBytes();
		
		ls.add(new Staff(stno,stname,stlevel,phone,salary,icon));
		return ls;
	}
	
	public int insertOne(Staff staff) throws Exception{
		Object[] o=staff.tran();
		return db.updateOne("insert into staff values(?,?,?,?,?,?)", o);
	}
	
	public int deleteOne(Staff staff) throws Exception{
		Object[] o=staff.tran();
		
		Object[] test=new Object[count-1];
		
		for(int i=0;i<count-1;i++) {
			test[i]=o[i];
		}
		
		return db.updateOne("delete from staff where stno=? and stname=? and stlevel=? and phone=? and salary=? ", test);
		
	}
	
	public int updateOne(Staff o,Staff n) throws Exception{
		Object[] temp=new Object[count];
		temp[0]=n.getStno();
		temp[1]=n.getStname();
		temp[2]=n.getStlevel();
		temp[3]=n.getPhone();
		temp[4]=n.getSalary();
		temp[5]=o.getStno();
		return db.updateOne("update staff set stno=?,stname=?,stlevel=?,phone=?,salary=? where stno=?", temp);
		
	}
	
	
	
	
	
	
	
	
}
