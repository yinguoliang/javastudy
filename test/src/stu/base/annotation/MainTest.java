package stu.base.annotation;

import java.lang.reflect.Field;
import java.util.Date;

@MyAnnotation(
		array = { "" }, color = "",
		gender = Gender.MAN, 
		metas = { @MetaAnnotation(info = "", value = "") })
public class MainTest {
	@MyAnnotation(array = { "Array" }, color = "", gender = Gender.MAN, metas = { @MetaAnnotation(info = "", value = "") })
	public String name;
	public String age;
	public Date birthday;
	@SuppressWarnings({"unused"})
	public static void main(String r[]){
		System.out.println(MainTest.class.isAnnotationPresent(MyAnnotation.class));
		MyAnnotation a = MainTest.class.getAnnotation(MyAnnotation.class);
		
		Field[] fields = MainTest.class.getDeclaredFields();
		for(Field f : fields){
			System.out.println(f.getName()+":"+f.isAnnotationPresent(MyAnnotation.class));
		}
	}
}
