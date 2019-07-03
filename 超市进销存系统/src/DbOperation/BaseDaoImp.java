package DbOperation;

import java.lang.reflect.*;
import java.sql.ResultSet;

public class BaseDaoImp<T> implements BaseDao<T>{
	private DbOperation db;
	private Class<T> EntityClass;
	@SuppressWarnings("unchecked")
	public BaseDaoImp() throws Exception{
		db=DbOperation.getInstance();
		if(!db.isLinked())
			db.linkDb();
		
		ParameterizedType type = (ParameterizedType) (getClass().getGenericSuperclass());  
        EntityClass = (Class<T>) type.getActualTypeArguments()[0];  
	}
	
	
	@Override
	public int insert(T t) throws Exception {
		String sql=getSQL("SQL_INSERT");
		Object[] o=setArgs(t,"SQL_INSERT");
		
		return db.updateOne(sql, o);
	}

	@Override
	public int delete(T t) throws Exception {
		
		String sql=getSQL("SQL_DELETE");
		Object[] o=setArgs(t,"SQL_DELETE");
		
		return db.updateOne(sql, o);
	}

	@Override
	public int update(T t) throws Exception {
		String sql=getSQL("SQL_UPDATE");
		Object[] o=setArgs(t,"SQL_UPDATE");
		
		return db.updateOne(sql, o);
	}

	@Override
	public ResultSet queryAll() throws Exception {
		String sql=getSQL("SQL_SELECT");
		return db.query(sql, new Object[0]);
	}
	
	public int getCount() {
		return EntityClass.getDeclaredFields().length;
	}
	public Object[] getName() {
		Field[] fields=EntityClass.getDeclaredFields();
		Object[] name=new Object[fields.length];
		for(int i=0;i<fields.length;i++) {
			fields[i].setAccessible(true);
			name[i]=fields[i].getName();
		}
		return name;
	}
	
	
	private String getSQL(String op) {
		StringBuffer sql=new StringBuffer();
		Field[] fields=EntityClass.getDeclaredFields();
		
		if(op.equals("SQL_INSERT")) {
			sql.append("insert into ");
			sql.append(EntityClass.getSimpleName());
			sql.append(" values(");
			for(int i=0;i<fields.length;i++) {
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		else if(op.equals("SQL_DELETE")) {
			sql.append("delete from ");
			sql.append(EntityClass.getSimpleName());
			sql.append(" where "+fields[0].getName()+" =?");
			
		}
		else if(op.equals("SQL_UPDATE")) {
			sql.append("update "+EntityClass.getSimpleName());
			sql.append(" set ");
			for(int i=0;i<fields.length;i++) {
				fields[i].setAccessible(true);
				sql.append(" "+fields[i].getName()+"=?,");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append("where "+fields[0].getName()+"=?");
		}
		else if(op.equals("SQL_SELECT")) {
			sql.append("select * from "+EntityClass.getSimpleName());
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
		else if(op.equals("SQL_DELETE")) {
			o=new Object[1];
			fields[0].setAccessible(true);
			o[0]=fields[0].get(entity);
		}
		else if(op.equals("SQL_UPDATE")) {
			int n=(fields).length;
			o=new Object[n+1];
			for(int i=0;i<n;i++) {
				fields[i].setAccessible(true);
				o[i]=fields[i].get(entity);
			}
			o[n]=fields[0].get(entity);
		}
		return o;
	}
	
}
