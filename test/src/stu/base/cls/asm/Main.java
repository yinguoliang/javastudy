package stu.base.cls.asm;

import java.io.File;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Main {
	public void generateAclass() throws Exception{
		ClassWriter cw = new ClassWriter(0);
		cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT+Opcodes.ACC_INTERFACE,
				"test/asm/Comparable",null, "java/lang/Object", new String[]{"com/asm3/Mesurable"});
		cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC, 
				"LESS", "I", null, new Integer(-1))
		  .visitEnd();
		cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC, 
				"EQUAL", "I", null, new Integer(0))
		  .visitEnd();
		cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC, 
				"GREATER", "I", null, new Integer(1))
		  .visitEnd();
		cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT,
				"compareTo", "(Ljava/lang/Object;)I",null, null).visitEnd();
		cw.visitEnd();
		byte data[] = cw.toByteArray();
		File file = new File("F:/work/Program/CCIC/XG/workspace/test/src/test/asm/Comparable.class");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(data);
		fos.close();
		
	}
	public void modifyClass() throws Exception {
		ClassReader reader = new ClassReader("test/asm/Foo");
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassAdapter adapter = new ClassAdapter(writer){
			private String owner;
			private boolean isInterface;
			
			@Override
			public void visit(int version,int access,String name,String signature,
					String superName,String[] interfaces){
				cv.visit(version, access, name, signature, superName, interfaces);
				owner = name;
				isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
			}
			@Override
			public MethodVisitor visitMethod(int access,String name,String desc,
					String signature,String[] exceptions){
				MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
				if(!name.equals("<init>") && !isInterface && mv!=null){
					mv = new MethodAdapter(mv){
						@Override
						public void visitCode(){
							mv.visitCode();
							System.out.println("insert instruction.....11111");
							mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "timer", "J");
							mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
							mv.visitInsn(Opcodes.LSUB);
							mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, "timer", "J");
						}
						@Override
						public void visitInsn(int opcode){
							System.out.println("opcode:"+opcode);
							if((opcode>=Opcodes.IRETURN && opcode<=Opcodes.RETURN)||
									opcode==Opcodes.ATHROW){
								System.out.println("insert instruction.....2222");
								mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "timer", "J");
								mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
								mv.visitInsn(Opcodes.LADD);
								mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, "timer", "J");
							}
							mv.visitInsn(opcode);
						}
						@Override
						public void visitMaxs(int maxStack,int maxLocal){
							mv.visitMaxs(maxStack+4, maxLocal);
						}
					};
				}
				return mv;
			}
			@Override
			public void visitEnd(){
				if(!isInterface){
					FieldVisitor fv = cv.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC,
							"timer", "J", null,null);
					if(fv!=null) fv.visitEnd();
				}
				cv.visitEnd();
			}
		};
		reader.accept(adapter, ClassReader.SKIP_DEBUG);
		byte data[] = writer.toByteArray();
		System.out.println(data);
		File file = new File("F:/work/Program/CCIC/XG/workspace/test/src/test/asm/Foo.class");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(data);
		fos.close();
	}
	
	public static void main(String args[]) throws Exception{
		new Main().modifyClass();
		Foo foo = new Foo();
		foo.say("NNN"); 
		Class cc = foo.getClass();
		System.out.println(cc.getField("timer").get(cc));
	}
}
