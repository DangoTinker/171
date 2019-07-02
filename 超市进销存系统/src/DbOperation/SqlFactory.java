package DbOperation;

import java.util.HashMap;

public class SqlFactory {
	private static HashMap<String,String> map=new HashMap<String,String>();
	private static SqlFactory s=new SqlFactory();
	public static SqlFactory getInstance() {
		init();
		return s;
	}
	public static HashMap<String,String> getMap(){
		return map;
	}
	private static void init() {
		map.put("ast.Supplier_insert", "insert into supplier values(?,?,?,?,?,?)");
		map.put("ast.Goods_insert", "insert into supplier values(?,?,?,?,?)");
		map.put("ast.Staff_insert", "insert into supplier values(?,?,?,?,?,?)");
		map.put("ast.Contacter_insert", "insert into supplier values(?,?,?,?)");
		map.put("ast.PurchaseList_insert", "insert into supplier values(?,?,?,?,?)");
		map.put("ast.Purchase_insert", "insert into supplier values(?,?,?)");
		
		map.put("ast.Supplier_delete", "delete from supplier where sno=?");
		map.put("ast.Goods_delete", "delete from goods where gno=?");
		map.put("ast.Staff_delete", "delete from staff where stno=?");
		map.put("ast.Contacter_delete", "delete from contacter where cno=?");
		map.put("ast.PurchaseList_delete", "delete from purchaseList where lno=?");
		map.put("ast.Purchase_delete", "delete from purchase where lno=? and gno=?");
		
		map.put("ast.Supplier_update", "update supplier set sno=?,sname=?,simply=?,address=?,sphone=?,mail=? where sno=?");
		
	}
	
}
