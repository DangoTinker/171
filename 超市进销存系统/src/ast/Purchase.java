package ast;

public class Purchase implements Tranable{
	private String lno;
	private String gno;
	private int    count;

	public Purchase(String a,String b,int c) {
		setLno(a);setGno(b);setCount(c);
	}

	@Override
	public Object[] tran() {
		Object[] o=new Object[3];
		o[0]=lno;o[1]=gno;o[2]=count;
		return o;
	}

	public String getLno() {
		return lno;
	}

	public void setLno(String lno) {
		this.lno = lno;
	}

	public String getGno() {
		return gno;
	}

	public void setGno(String gno) {
		this.gno = gno;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}



}
