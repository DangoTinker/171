package ast;

public class PurchaseList implements Tranable{
	private String lno;
	private String stno;
	private int    count;
	private double total;
	private String time;

	public PurchaseList(String a,String b,int c,double d,String e) {
		setLno(a);setStno(b);setCount(c);setTotal(d);setTime(e);
	}

	@Override
	public Object[] tran() {
		Object[] o=new Object[5];
		o[0]=lno;o[1]=stno;o[2]=count;o[3]=total;o[4]=time;
		return o;
	}

	public String getLno() {
		return lno;
	}

	public void setLno(String lno) {
		this.lno = lno;
	}

	public String getStno() {
		return stno;
	}

	public void setStno(String stno) {
		this.stno = stno;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	

}
