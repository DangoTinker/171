package DbOperation;
import java.io.*;
import java.util.*;
import java.sql.*;
public class DbOperation {
	private Connection conn;
	PreparedStatement pstmt;
	
	private static DbOperation db=new DbOperation();
	public static DbOperation getInstance() {
		return db;
	}
	
	
	public DbOperation() {
		
	}
	
	public void linkDb(InputStream input) throws Exception{
		Properties props=new Properties();
		props.load(input);
		String driver=props.getProperty("driver");
		String url=props.getProperty("url");
		String username=props.getProperty("username");
		String password=props.getProperty("password");
		Class.forName(driver);
		conn=DriverManager.getConnection(url,username,password);
	}
	
	public void linkDb(String driver,String url,String username,String password) throws Exception{
		
		Class.forName(driver);
		conn=DriverManager.getConnection(url,username,password);
	}
	public void linkDb() throws Exception{
		this.linkDb("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@localhost:1521:orcl","system","123456");
	}
	public int updateLots(String sql,Object[][] o) throws Exception{
		pstmt=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		for(int i=0;i<o.length;i++) {
			for(int j=0;j<o[i].length;j++) {
				pstmt.setObject(j+1, o[i][j]);
			}
			pstmt.addBatch();
		}
		int count=pstmt.executeUpdate();
		conn.commit();
		return count;
	}
	public int updateOne(String sql,Object[] o) throws Exception{
		pstmt=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		for(int i=0;i<o.length;i++) {
			pstmt.setObject(i+1, o[i]);
		}
		int count=pstmt.executeUpdate();
		conn.commit();
		return count;
	}
	public ResultSet query(String sql,Object[] o) throws Exception{
		pstmt=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		for(int i=0;i<o.length;i++) {
			pstmt.setObject(i+1, o[i]);
		}
		ResultSet rs=pstmt.executeQuery();
		return rs;
	}
	
	
	public void closePstmt() throws Exception{
		pstmt.close();
	}
	public void closeConn() throws Exception{
		conn.close();
	}
}