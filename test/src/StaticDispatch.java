
public class StaticDispatch {
//	public void p(char c){
//		
//	}
	public void p(int c){
		
	}
//	static abstract class Human{
//		
//	}
//	static class Man extends Human{
//		
//	}
//	static class Woman extends Human{
//		
//	}
//	public void say(Human human){
//		System.out.println("Hello,guy");
//	}
//	public void say(Man man){
//		System.out.println("Hello,man");
//	}
//	public void say(Woman woman){
//		System.out.println("Hello,woman");
//	}
	public static void main(String args[]){
		StaticDispatch sd = new StaticDispatch();
		sd.p('a');
	}
}
