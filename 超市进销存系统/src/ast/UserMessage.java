package ast;

public class UserMessage implements Tranable{
	private String username;
	private String password;
	private String level;
	@Override
	public Object[] tran() {
		Object[] o=new Object[3];
		o[0]=username;
		o[1]=password;
		o[2]=level;
		return o;
	}

	public UserMessage(String a,String b,String c) {
		// TODO �Զ����ɵķ������
		
		username=a;
		password=b;
		level=c;
	}

	
	
}
