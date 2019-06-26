package DbOperation;

import java.sql.ResultSet;
import java.util.LinkedList;

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
	
	public LinkedList<Staff> queryAll() throws Exception{
		Object[] o=new Object[0];
		ResultSet rs=db.query("select * from staff", o);
		LinkedList<Staff> ls=new LinkedList<Staff>();
		while(rs.next()) {
			ls.add(new Staff(rs.getString("stno"),rs.getString("stname"),rs.getString("stlevel"),rs.getString("phone"),rs.getDouble("salary"),(((java.sql.Blob)rs.getBlob("pic")).getBinaryStream()).readAllBytes()));
			
		}
		return ls;
		
		
	}
	
	
	public int insertOne(Staff staff) throws Exception{
		Object[] o=staff.tran();
		return db.updateOne("insert into staff values(?,?,?,?,?)", o);
	}
	
	public int deleteOne(Staff staff) throws Exception{
		Object[] o=staff.tran();
		Object[] temp=new Object[1];
		temp[0]=o[0];
		db.updateOne("delete from purchaseList where stno=?",temp);
		return db.updateOne("delete from staff where stno=? and stname=? and stlevel=? and phone=? and salary=?", o);
		
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
