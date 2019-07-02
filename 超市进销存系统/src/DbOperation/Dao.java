package DbOperation;

import ast.Tranable;

public class Dao {
	private static Dao d=null;
	private DbOperation db;
	private SqlFactory sql;
	public static Dao getInstance() throws Exception{
		if(d==null) {
			d=new Dao();
		}
		return d;
	}
	private Dao() throws Exception{
		db=DbOperation.getInstance();
		sql=SqlFactory.getInstance();
		if(!db.isLinked())
			db.linkDb();
	}
	public int insert(Tranable tran) throws Exception{
		Class clazz=tran.getClass();
		String s=(sql.getMap()).get(clazz.getName()+"_insert");
	//	System.out.println(s+" "+clazz.getName()+" "+clazz.toString());
		return db.updateOne(s, tran.tran());
	}
}
