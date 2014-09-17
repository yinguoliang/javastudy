package stu.base.cls.struct;

public class A implements Aintf,Aintfx{
	public static final String ClassVersion = "1.0";
	public String name = "ClassA";
	private int age = 100;
	public <T> void doSth(T t){
		System.out.println(t);
	}
	public void sayHello(){
		System.out.println("Hi,I am "+name);
	}
	public static void main(String args[]) throws Exception{
		try{
		A a = new A();
		a.sayHello();
		}catch(Exception ex){
			throw ex;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void doooo(){
		{
		byte[] placeHolder = new byte[64*1024*1024];
		}
		int a = 0;
	}
}
