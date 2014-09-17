package test.ehcache;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Vector;

public class Main {
//	static CacheManager manager = null;
//	
//	static{
//		String tmpDir = System.getProperty("java.io.tmpdir");
//		System.setProperty("ehcache.disk.store.dir", tmpDir+"/ehcache/budget/diskstorage");
//		manager = CacheManager.newInstance(Main.class.getResourceAsStream("ehcache.xml"));
//	}
//	public void display(){
//		String[] names = manager.getCacheNames();
//		for(String name:names)System.out.println("CacheName:"+name);
//	}
//	public void test(){
//		display();
//		manager.addCache(Main.class.getName());
//		display();
//		Cache testCache = manager.getCache(Main.class.getName());
//		System.out.println(testCache.getMemoryStoreSize()+","+testCache.getDiskStoreSize()
//				+","+testCache.getStatistics().getLocalHeapSize());
//		for(int i=0;i<20000;i++)
//		{
//			testCache.put(new Element("K"+i,"VVVVVVVV"+i));
//		}
//		System.out.println(testCache.getMemoryStoreSize()+","+testCache.getDiskStoreSize()
//				+","+testCache.getStatistics().getLocalHeapSize());
////		manager.shutdown();
//	}
//	public void testStackTrace(){
//		long timer = 0;
//		String name= Thread.currentThread().getStackTrace()[1].getMethodName();
//		System.out.println(name);
//		for(int i=0;i<100000;i++){
//			timer -= System.currentTimeMillis();
//			StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
//			name= ste.getClassName()+"."+ste.getMethodName()+ste.getLineNumber();
//			System.currentTimeMillis();
//			timer += System.currentTimeMillis();
//		}
//		System.out.println(name);
//		System.out.println(timer);
//	}
	public static void main(String args[]) throws Exception{
		TTT tt = new TTT();
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		Class cls  = cl.getClass();
		System.out.println(cls);
//		while (cls!=ClassLoader.class) cls = cls.getSuperclass();
		System.out.println(cls);
		Field field = cls.getDeclaredField("classes");
		field.setAccessible(true);
		Vector vector = (Vector) field.get(cl);
		for(Object v : vector){
			System.out.println(v);
		}
	}
}
class TTT{
	
}