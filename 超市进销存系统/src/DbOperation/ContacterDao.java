package DbOperation;

import java.sql.ResultSet;
import java.util.*;

import ast.Contacter;

public class ContacterDao {
	private DbOperation db;
	private Object[] name;
	private int count;
	private static ContacterDao g=null;
	public static ContacterDao getInstance() throws Exception{
		if(g==null) {
			g=new ContacterDao();
		}
		return g;
		
	}
	public ContacterDao() throws Exception{
		count=4;
		name=new Object[count];
		name[0]="联系人编号";
		name[1]="供应商编号";
		name[2]="联系人名";
		name[3]="电话";
		
		db=DbOperation.getInstance();
		if(!db.isLinked())	
			db.linkDb();
	}
	
	public Object[] getName() {
		return name;
	}
	public int getCount() {
		return count;
	}
	
	public List queryAll( ) throws Exception{
		LinkedList<Contacter> o=new LinkedList<Contacter>();
		ResultSet rs=db.query("select * from contacter", new Object[0]);
		while(rs.next()) {
			o.add(new Contacter(rs.getString("cno"),rs.getString("sno"),rs.getString("cname"),rs.getString("phone")));	
		}
		return o;
	}
	public  int insertOne(Contacter e) throws Exception{
		Object[] o=e.tran();
		return db.updateOne("insert into contacter values(?,?,?,?)", o);
	}
	public  int deleteOne(Contacter goods) throws Exception{
		Object[] o=goods.tran();
		Object[] purchase=new Object[1];
		purchase[0]=o[0];
		db.updateOne("delete from contacter where cno=?", purchase);
		return db.updateOne("delete from contacter where cno=? and sno=? and cname=? and phone=?", o);
		
	}
	
	public  int updateOne(Contacter o,Contacter n) throws Exception{
		Object[] temp=new Object[count+1];
		temp[0]=n.getCno();
		temp[1]=n.getSno();
		temp[2]=n.getCname();
		temp[3]=n.getPhone();
		
		temp[4]=o.getCno();
		
		return db.updateOne("update goods set cno=?,sno=?,cname=?,phone=? where cno=?", temp);
	}
	
}
