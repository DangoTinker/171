package DbOperation;

import java.sql.ResultSet;
import java.util.*;

import ast.Supplier;

public class SupplierDao {
	private DbOperation db;
	private Object[] name;
	private int count;
	private static SupplierDao s;
	public static SupplierDao getInstance() throws Exception{
		if(s==null) {
			s=new SupplierDao();
		}
		return s;
	}
	
	public Object[] getName() {
		return name;
	}
	public int getCount() {
		return count;
	}
	
	
	public SupplierDao() throws Exception{
		count=6;
		name=new Object[count];
		
		name[0]="供应商编号";
		name[1]="供应商名";
		name[2]="简称";
		name[3]="地址";
		name[4]="电话";
		name[5]="邮件";
		
		db=DbOperation.getInstance();
		db.linkDb();
		
	}
	
	public  List queryAll() throws Exception{
		LinkedList<Supplier> supplier=new LinkedList<Supplier>();
		ResultSet rs=db.query("select * from supplier", new Object[0]);
		while(rs.next()) {
			supplier.add(new Supplier(rs.getString("sno"),rs.getString("sname"),rs.getString("simply"),rs.getString("address"),rs.getString("sphone"),rs.getString("mail")));	
		}
		return supplier;
	}
	public  int insertOne(Supplier supplier) throws Exception{
		Object[] o=supplier.tran();
		return db.updateOne("insert into supplier values(?,?,?,?,?,?)", o);
	}
	public  int insert(List<Supplier> s) throws Exception{
		Object[][] o=new Object[s.size()][count];
		for(int i=0;i<s.size();i++) {
			o[i]=s.get(i).tran();
		}
		db.updateLots("insert into supplier values(?,?,?,?,?,?)", o);
		
		
		
		return db.updateOne("insert into supplier values(?,?,?,?,?,?)", o);
	}
	public  int deleteOne(Supplier supplier) throws Exception{
		Object[] o=supplier.tran();
		Object[] goods=new Object[1];
		goods[0]=o[0];
		Object[] contacter=new Object[1];
		contacter[0]=o[0];
		db.updateOne("delete from contacter where sno=?",contacter );
		db.updateOne("delete from goods where sno=?", goods);
		return db.updateOne("delete from supplier where sno=? and sname=? and simply=? and address=? and sphone=? and mail=?", o);
		
	}
	
	public  int deleteLots(List<Supplier> s) throws Exception{
		Object[][] o=new Object[s.size()][count];
		Object[][] contacter=new Object[s.size()][1];
		Object[][] goods=new Object[s.size()][1];
		for(int i=0;i<s.size();i++) {
			o[i]=s.get(i).tran();
			contacter[i][0]=o[i][0];
			goods[i][0]=o[i][0];
		}
		db.updateLots("delete from contacter where sno=?", contacter);
		db.updateLots("delete from goods where sno=?", goods);
		return db.updateLots("delete from supplier where sno=? and sname=? and simply=? and address=? and sphone=? and mail=?", o);
	}
	
	public  int updateOne(Supplier o,Supplier n) throws Exception{
		Object[] temp=new Object[count+1];
		temp[0]=n.getSno();
		temp[1]=n.getSname();
		temp[2]=n.getSimply();
		temp[3]=n.getAddress();
		temp[4]=n.getPhone();
		temp[5]=n.getMail();
		temp[6]=o.getSno();
		return db.updateOne("update supplier set sno=?,sname=?,simply=?,address=?,sphone=?,mail=? where sno=?", temp);
	}
	
	
	
	
	
}
