package DbOperation;

import java.sql.ResultSet;
import java.util.*;

import ast.Contacter;
import ast.PurchaseList;

public class PurchaseListDao {
	private DbOperation db;
	private Object[] name;
	private int count;
	private static PurchaseListDao g=null;
	public static PurchaseListDao getInstance() throws Exception{
		if(g==null) {
			g=new PurchaseListDao();
		}
		return g;
		
	}
	public PurchaseListDao() throws Exception{
		count=5;
		name=new Object[count];
		name[0]="编号";
		name[1]="员工编号";
		name[2]="数量";
		name[3]="总计";
		name[4]="时间";
		
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
		LinkedList<PurchaseList> o=new LinkedList<PurchaseList>();
		ResultSet rs=db.query("select * from purchaseList", new Object[0]);
		while(rs.next()) {
			o.add(new PurchaseList(rs.getString("lno"),rs.getString("stno"),rs.getInt("lcount"),rs.getDouble("total"),rs.getString("time")));	
		}
		return o;
	}
	public  int insertOne(PurchaseList e) throws Exception{
		Object[] o=e.tran();
		return db.updateOne("insert into purchaseList values(?,?,?,?,?)", o);
	}
	public  int deleteOne(PurchaseList goods) throws Exception{
		Object[] o=new Object[1];
		o[0]=(goods.tran())[0];
		
		return db.updateOne("delete from purchaseList where lno=? ", o);
		
	}
	
	public  int updateOne(PurchaseList o,PurchaseList n) throws Exception{
		Object[] temp=new Object[count+1];
		temp[0]=n.getLno();
		temp[1]=n.getStno();
		temp[2]=n.getCount();
		temp[3]=n.getTotal();
		temp[4]=n.getTime();
		temp[5]=o.getLno();
		return db.updateOne("update purchaseList set lno=?,stno=?,lcount=?,total=?,time=? where lno=?", temp);
	}
	
}
