package DbOperation;

import java.sql.ResultSet;
import java.util.LinkedList;

import ast.Goods;
import ast.Supplier;

public class GoodsDao {
	private DbOperation db;
	private Object[] name;
	private int count;
	private static GoodsDao g=null;
	public static GoodsDao getInstance() throws Exception{
		if(g==null) {
			g=new GoodsDao();
		}
		return g;
		
	}
	
	public GoodsDao() throws Exception{
		count=5;
		name=new Object[count];
		name[0]="商品编号";
		name[1]="供应商编号";
		name[2]="商品名";
		name[3]="简称";
		name[4]="单价";
		
		
		db=DbOperation.getInstance();
		db.linkDb();
	}
	
	public Object[] getName() {
		return name;
	}
	public int getCount() {
		return count;
	}
	
	public LinkedList queryAll( ) throws Exception{
		LinkedList<Goods> o=new LinkedList<Goods>();
		ResultSet rs=db.query("select * from goods", new Object[0]);
		while(rs.next()) {
			o.add(new Goods(rs.getString("gno"),rs.getString("sno"),rs.getString("gname"),rs.getString("simply"),rs.getDouble("price")));	
		}
		return o;
	}
	public  int insertOne(Goods goods) throws Exception{
		Object[] o=goods.tran();
		return db.updateOne("insert into goods values(?,?,?,?,?)", o);
	}
	public  int deleteOne(Goods goods) throws Exception{
		Object[] o=goods.tran();
		Object[] purchase=new Object[1];
		purchase[0]=o[0];
		db.updateOne("delete from goods where sno=?", purchase);
		return db.updateOne("delete from goods where gno=? and sno=? and gname=? and simply=? and price=?", o);
		
	}
	
	public  int updateOne(Goods o,Goods n) throws Exception{
		Object[] temp=new Object[count+1];
		temp[0]=n.getGno();
		temp[1]=n.getSno();
		temp[2]=n.getGname();
		temp[3]=n.getSimply();
		temp[4]=n.getPrice();
		
		temp[6]=o.getGno();
		
		return db.updateOne("update goods set gno=?,sno=?,gname=?,simply=?,price=? where gno=?", temp);
	}
	
}
