package DbOperation;

public interface BaseDao<T> {
	public int insert(T t) throws Exception;
	public int delete(T t) throws Exception;
	public int update(T t) throws Exception;
	public int queryAll() throws Exception;
	
}
