package anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
public @interface MyAnnotation {
	String color();
	String value() default "MyAnnotation";
	String[] array();
	Gender gender() ;
	MetaAnnotation[] metas();
}
