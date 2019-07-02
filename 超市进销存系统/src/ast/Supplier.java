package ast;

public class Supplier implements Tranable{
	private String sno;
	private String sname;
	private String simply;
	private String address;
	private String sphone;
	private String mail;
	
	
	public Supplier(String a,String b,String c,String d,String e,String f) {
		sno=a;sname=b;simply=c;address=d;sphone=e;mail=f;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSimply() {
		return simply;
	}
	public void setSimply(String simply) {
		this.simply = simply;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return sphone;
	}
	public void setPhone(String phone) {
		this.sphone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public Object[] tran() {
		Object[] o=new Object[6];
		o[0]=sno;o[1]=sname;o[2]=simply;o[3]=address;o[4]=sphone;o[5]=mail;
		return o;
	}

}
