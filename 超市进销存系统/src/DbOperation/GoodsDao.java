package DbOperation;

import java.sql.ResultSet;
import java.util.LinkedList;

import ast.Goods;

public class GoodsDao {
	private DbOperation db;
	
	public GoodsDao() throws Exception{
		db=new DbOperation();
		db.linkDb();
	}
	
	public LinkedList queryAll( ) throws Exception{
		LinkedList<Goods> o=new LinkedList<Goods>();
		ResultSet rs=db.query("select * from goods", new Object[0]);
		while(rs.next()) {
			o.add(new Goods(rs.getString("gno"),rs.getString("sno"),rs.getString("gname"),rs.getString("simply"),rs.getDouble("price")));	
		}
		return o;
	}
	
	
}
