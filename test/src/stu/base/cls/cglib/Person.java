package stu.base.cls.cglib;

public class Person {
	public void printName(String name){
		System.out.println("My name is "+name);
	}
	public int add(String a,String b){
		System.out.println(a+"+"+b+"="+(Integer.parseInt(a)+Integer.parseInt(b)));
		return   Integer.parseInt(a)+Integer.parseInt(b);
	}
}
