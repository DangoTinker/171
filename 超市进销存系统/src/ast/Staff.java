package ast;

public class Staff implements Tranable{
	
	private String stno;
	private String stname;
	private String stlevel;
	private String phone;
	private double salary;
	
	public Staff(String a,String b,String c,String d,double e) {
		stno=a;
		stname=b;
		stlevel=c;
		phone=d;
		salary=e;
	}
	
	
	public String getStno() {
		return stno;
	}
	public void setStno(String stno) {
		this.stno = stno;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String sname) {
		this.stname = sname;
	}
	public String getStlevel() {
		return stlevel;
	}
	public void setStlevel(String stlevel) {
		this.stlevel = stlevel;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}


	@Override
	public Object[] tran() {
		// TODO 自动生成的方法存根
		Object[] o=new Object[5];
		o[0]=stno;
		o[1]=stname;
		o[2]=stlevel;
		o[3]=phone;
		o[4]=salary;

		return o;
	}
	
	
	
}
