package DbOperation;

import java.sql.ResultSet;

public interface BaseDao<T> {
	public int insert(T t) throws Exception;
	public int delete(T t) throws Exception;
	public int update(T t) throws Exception;
	public ResultSet queryAll() throws Exception;
	
}
