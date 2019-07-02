package DbOperation;

import java.lang.reflect.*;

public class BaseDaoImp<T> implements BaseDao<T>{
	private DbOperation db;
	private Class<T> EntityClass;
	public BaseDaoImp() throws Exception{
		db=DbOperation.getInstance();
		if(!db.isLinked())
			db.linkDb();
		
		ParameterizedType type = (ParameterizedType) (getClass().getGenericSuperclass());
                      
        
       
        EntityClass = (Class<T>) type.getActualTypeArguments()[0];  

		
		
	}
	
	
	@Override
	public int insert(T t) throws Exception {
		// TODO 自动生成的方法存根
		String sql=getSQL("SQL_INSERT");
		Object[] o=setArgs(t,"SQL_INSERT");
		System.out.println(sql);
		db.updateOne(sql, o);
		return 0;
	}

	@Override
	public int delete(T t) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int update(T t) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int queryAll() {
		// TODO 自动生成的方法存根
		return 0;
	}
	
	
	private String getSQL(String sqlType) {
		StringBuffer sql=new StringBuffer();
		Field[] field=EntityClass.getDeclaredFields();
		
		if(sqlType.equals("SQL_INSERT")) {
			sql.append("insert into ");
			sql.append(EntityClass.getSimpleName());
			sql.append(" values(");
			for(int i=0;i<field.length;i++) {
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		return sql.toString();
		
	}
	
	private Object[] setArgs(T entity,String op) throws IllegalArgumentException, IllegalAccessException {
		Object[] o=null;
		Field[] fields=EntityClass.getDeclaredFields();
		if(op.equals("SQL_INSERT")) {
			int n=(fields).length;
			o=new Object[n];
			for(int i=0;i<n;i++) {
				fields[i].setAccessible(true);
				o[i]=fields[i].get(entity);
			}
			
		}
		return o;
	}
	
}
