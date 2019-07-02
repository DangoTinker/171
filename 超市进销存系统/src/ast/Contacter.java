package ast;

public class Contacter implements Tranable{
	private String cno;
	private String sno;
	private String cname;
	private String phone;

	public Contacter(String a,String b,String c,String d) {
		setCno(a);setSno(b);setCname(c);setPhone(d);
	}

	@Override
	public Object[] tran() {
		Object[] o=new Object[4];
		o[0]=cno;o[1]=sno;o[2]=cname;o[3]=phone;
		return o;
	}
	
	public String getCno() {
		return cno;
	}
	public void setCno(String cno) {
		this.cno = cno;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}



}
