package DbOperation;

import java.sql.ResultSet;

public class LoginDao {
	private static DbOperation db;
	private static LoginDao l=null;
	public static LoginDao getInstance() throws Exception{
		db=DbOperation.getInstance();
		if(!db.isLinked())
			db.linkDb();
		if(l==null) {
			l=new LoginDao();
		}
		return l;
	}
	public int Login(String username,String password) throws Exception{
		Object[] o=new Object[1];
		o[0]=username;
		ResultSet rs=db.query("select password from userMessage where username=?", o);
		rs.next();
		if(rs.getString("password").equals(password)) {
			return 1;
		}
		return 0;
	}
	public int Logup(String username,String password,String level) throws Exception{
		Object[] o=new Object[3];
		o[0]=username;
		o[1]=password;
		o[2]=level;
		return db.updateOne("insert into userMessage values(?,?,?)", o);
	}
}
