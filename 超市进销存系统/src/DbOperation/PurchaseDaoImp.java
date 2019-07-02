package DbOperation;

import java.sql.ResultSet;

import ast.Purchase;

public class PurchaseDaoImp extends BaseDaoImp<Purchase>{
	private DbOperation db;
	public PurchaseDaoImp() throws Exception {
		super();
		db=DbOperation.getInstance();
		// TODO 自动生成的构造函数存根
	}
	
	public ResultSet queryAll(String l) throws Exception{
		Object[] temp=new Object[1];
		temp[0]=l;
		ResultSet rs=db.query("select * from purchase where lno=?", temp);
		return rs;
	}

	public  int delete(Purchase goods) throws Exception{
		Object[] o=goods.tran();
		return db.updateOne("delete from purchase where lno=? and gno=? and count=?",o);
	}
	
	public  int update(Purchase n) throws Exception{
		Object[] temp=new Object[getCount()+2];
		temp[0]=n.getLno();
		temp[1]=n.getGno();
		temp[2]=n.getCount();
		
		temp[3]=n.getLno();
		temp[4]=n.getGno();
		return db.updateOne("update purchase set lno=?,gno=?,count=? where lno=? and gno=?", temp);
	}

}
