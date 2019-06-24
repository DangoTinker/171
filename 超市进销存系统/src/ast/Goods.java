package ast;

public class Goods implements Tranable{
	private String  gno;
	private String sno;
	private String gname;
	private String simply;
	private double price;
	public Goods(String a,String b,String c,String d,double e) {
		setGno(a);setSno(b);setGname(c);setSimply(d);setPrice(e);
	}
	
	@Override
	public Object[] tran() {
		Object[] o=new Object[5];
		o[0]=gno;o[1]=sno;o[2]=gname;o[3]=simply;o[4]=price;
		return o;
	}
	
	public String getGno() {
		return gno;
	}
	public void setGno(String gno) {
		this.gno = gno;
	}
	
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getSimply() {
		return simply;
	}
	public void setSimply(String simply) {
		this.simply = simply;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
