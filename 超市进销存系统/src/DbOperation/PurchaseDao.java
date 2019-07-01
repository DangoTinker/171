package DbOperation;

import java.sql.ResultSet;
import java.util.*;


import ast.Purchase;

public class PurchaseDao {
	private DbOperation db;
	private Object[] name;
	private int count;
	private static PurchaseDao g=null;
	public static PurchaseDao getInstance() throws Exception{
		if(g==null) {
			g=new PurchaseDao();
		}
		return g;
		
	}
	public PurchaseDao() throws Exception{
		count=3;
		name=new Object[count];
		name[0]="清单编号";
		name[1]="商品编号";
		name[2]="数量";
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
	
	public List queryAll(String l) throws Exception{
		LinkedList<Purchase> o=new LinkedList<Purchase>();
		Object[] temp=new Object[1];
		temp[0]=l;
		ResultSet rs=db.query("select * from purchase where lno=?", temp);
		while(rs.next()) {
			o.add(new Purchase(rs.getString("lno"),rs.getString("gno"),rs.getInt("count")));	
		}
		return o;
	}
	public  int insertOne(Purchase e) throws Exception{
		Object[] o=e.tran();
		return db.updateOne("insert into purchase values(?,?,?)", o);
	}
	public  int deleteOne(Purchase goods) throws Exception{
		Object[] o=goods.tran();
		return db.updateOne("delete from purchase where lno=? and gno=? and count=?",o);
	}
	
	public  int updateOne(Purchase o,Purchase n) throws Exception{
		Object[] temp=new Object[count+2];
		temp[0]=n.getLno();
		temp[1]=n.getGno();
		temp[2]=n.getCount();
		
		temp[3]=o.getLno();
		temp[4]=o.getGno();
		return db.updateOne("update purchase set lno=?,gno=?,count=? where lno=? and gno=?", temp);
	}
	
}
