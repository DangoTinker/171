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
		Object[] o=new Object[count];
		o[0]="编号";
		o[1]="姓名";
		o[2]="级别";
		o[3]="电话";
		o[4]="工资";
		o[5]="头像";
		return o;
	}
	
	public int getCount(){
		return count;
	}
	
	
	public StaffDao() throws Exception{
		db=DbOperation.getInstance();
		db.linkDb();
		name=new Object[6];
		name[0]="编号";
		name[1]="姓名";
		name[2]="级别";
		name[3]="电话";
		name[4]="工资";
		name[5]="头像";
		count=6;
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
	
	
	public int insertOne(Staff staff) throws Exception{
		Object[] o=staff.tran();
		return db.updateOne("insert into staff values(?,?,?,?,?,?)", o);
	}
	
	public int deleteOne(Staff staff) throws Exception{
		Object[] o=staff.tran();
		Object[] temp=new Object[1];
		
		Object[] test=new Object[count-1];
		
		for(int i=0;i<count-1;i++) {
			test[i]=o[i];
		}
		
		temp[0]=o[0];
		db.updateOne("delete from purchaseList where stno=?",temp);
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
