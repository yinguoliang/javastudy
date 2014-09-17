package stu.base.cls.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class Main {
	public static void main(String args[]){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Person.class);
		enhancer.setCallback(new MethodInterceptor() {
			public Object intercept(Object arg0, Method arg1, Object[] arg2,
					MethodProxy arg3) throws Throwable {
				Person p = new Person();
				System.out.println("start............"+arg0.getClass());
				Object obj = arg1.invoke(p, arg2);
				System.out.println("end..............");
				return obj;
			}
		});
		
		Person person = (Person)enhancer.create();
		person.printName("YGL");
		person.add("1", "2");
	}
}
