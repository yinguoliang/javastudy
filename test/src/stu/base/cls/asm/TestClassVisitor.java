package stu.base.cls.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class TestClassVisitor implements ClassVisitor{
	public static void main(String args[]) throws Exception{
		ClassReader reader = new ClassReader("test.asm.foo");
		TestClassVisitor visitor = new TestClassVisitor();
		reader.accept(visitor, 0);
	}
	@Override
	public void visit(int arg0, int arg1, String arg2, String arg3,
			String arg4, String[] arg5) {
		System.out.println(arg2 + " extends "  + arg4 +  " {" );
	}

	@Override
	public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
		return null;
	}

	@Override
	public void visitAttribute(Attribute arg0) {
		
	}

	@Override
	public void visitEnd() {
		 System.out.println("}" );  
	}

	@Override
	public FieldVisitor visitField(int arg0, String arg1, String arg2,
			String arg3, Object arg4) {
		System.out.println(" "  + arg2 +  " "  + arg1); 
		return null;
	}

	@Override
	public void visitInnerClass(String arg0, String arg1, String arg2, int arg3) {
		
	}

	@Override
	public MethodVisitor visitMethod(int arg0, String arg1, String arg2,
			String arg3, String[] arg4) {
		System.out.println(" "  + arg2 +  " "  + arg1); 
		return null;
	}

	@Override
	public void visitOuterClass(String arg0, String arg1, String arg2) {
		
	}

	@Override
	public void visitSource(String arg0, String arg1) {
		
	}

}
