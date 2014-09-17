package stu.fa.nfa;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

class Edge{
	public static int WHITE = 0;
	public static int RED = 1;
	public List<String> accChars = new ArrayList<String>();
	public int color = WHITE;
	public Edge(String ...strings ){
		for(String s : strings){
			accChars.add(s);
		}
	}
}
class NFA{
	public int startStates[] ;
	public int finalStates[] ;
	public int size = 0;
	public Edge[][] edges ;
	public static Edge[] cloneEdges(Edge[] elems){
		Edge[] rets = new Edge[elems.length];
		for(int i=0;i<elems.length;i++){
			if(elems[i]!=null){
				rets[i]=new Edge(elems[i].accChars.toArray(new String[0]));
			}
		}
		return rets;
	}
	public void addEdge(int start,int to,String str){
		edges[start][to] = new Edge(str);
	}
	public boolean inStartStates(int state){
		for(int startState : startStates) if(startState==state) return true;
		return false;
	}
	public boolean inFinalStates(int state){
		for(int startState : finalStates) if(startState==state) return true;
		return false;
	}
}
public class NFAMain {
	public static String genString(String[] buffer,int s,int e){
		String tmp = buffer[s];
		for(int i=s+1;i<=e;i++) tmp+=buffer[i];
		return tmp;
	}
	
	public static NFA genNFA(){
		NFA nfa = new NFA();
		int size = 10;
		nfa.size = size;
		//NFA
		nfa = new NFA();
		nfa.edges = new Edge[size][size];
		nfa.addEdge(0, 1, "#");
		nfa.addEdge(0, 1, "#");
		nfa.addEdge(0, 3, "#");
		nfa.addEdge(1, 2, "a");
		nfa.addEdge(2, 5, "#");
		nfa.addEdge(3, 4, "b");
		nfa.addEdge(4, 5, "#");
		nfa.addEdge(7, 0, "#");
		nfa.addEdge(5, 0, "#");
		nfa.addEdge(5, 6, "#");
		nfa.startStates=new int[]{7};
		nfa.finalStates=new int[]{6};
		//DFA
		nfa.edges = new Edge[size][size];
		nfa.addEdge(0, 1, "a");
		nfa.addEdge(1, 1, "a");
		nfa.addEdge(1, 2, "b");
		nfa.addEdge(2, 1, "a");
		nfa.addEdge(0, 2, "b");
		nfa.addEdge(2, 2, "b");
		nfa.startStates=new int[]{0};
		nfa.finalStates=new int[]{1,2};
		nfa.edges = new Edge[size][size];
		nfa.addEdge(0,1,"a");
		nfa.addEdge(1,2,"b");
		nfa.addEdge(1,3,"c");
		nfa.addEdge(3,3,"c");
		nfa.addEdge(2,4,"b");
		nfa.addEdge(3,4,"d");
		nfa.addEdge(4,5,"e");
		nfa.addEdge(2, 3, "c");
		nfa.startStates=new int[]{0};
		nfa.finalStates=new int[]{5};
		return nfa;
	}
	public static void testNFA(NFA nfa,String input[]) throws InterruptedException{
		Stack<Integer> path = new Stack<Integer>();
		Stack<Edge[]> pathEdge = new Stack<Edge[]>();
		Stack<Boolean> pathPush = new Stack<Boolean>();
		path.push(nfa.startStates[0]);
		pathEdge.push(NFA.cloneEdges(nfa.edges[path.peek()]));
		pathPush.push(true);
		int index = 0;
		String curChar = input[index];
		int curPos = index;
		label:
		while(index < input.length || !nfa.inFinalStates(path.peek())){
			for(Enumeration<Integer> it = path.elements();it.hasMoreElements();){
				System.out.print(it.nextElement()+",");
			}
			System.out.println("\t"+index+"["+genString(input,0,curPos)+"]");
			Thread.sleep(50);
			Edge[] toEdges = pathEdge.peek();
			boolean moveForward = false;
			for(int to =0;to<toEdges.length;to++){
				moveForward = false;
				if(toEdges[to]==null) continue;
				boolean acceptLetter = toEdges[to].accChars.contains(curChar);
				boolean acceptEmpty =  toEdges[to].accChars.contains("#");
				boolean isNotAccessed = toEdges[to].color==Edge.WHITE;
				if((acceptLetter ||acceptEmpty) && isNotAccessed){
					//move forward
					path.push(to);
					pathEdge.push(NFA.cloneEdges(nfa.edges[path.peek()]));
					moveForward = true;
					
					if(acceptLetter){
						index++;
						if(index<input.length){
							curChar = input[index];
							curPos = index;
						}
						else curChar = "#";
						pathPush.push(true);
					}else{
						pathPush.push(false);
					}
					toEdges[to].color = Edge.RED;
					break;
				}
			}
			if(moveForward==false){
				path.pop();
				pathEdge.pop();
				if(path.isEmpty()) break label;
				if(pathPush.pop()){
					index--;
					if(index<input.length){
						curChar = input[index];
						curPos = index;
					}
				}
			}
		}
		for(Enumeration<Integer> it = path.elements();it.hasMoreElements();){
			System.out.print(it.nextElement()+",");
		}
		System.out.println("\t"+index+"["+curChar+"]");
		if(index>=input.length && nfa.inFinalStates(path.peek())){
			System.out.println("accept..");
		}else{
			System.out.println("not accept..");
		}
	}
	public static void main(String args[]) throws Exception{
		NFA nfa = genNFA();
		String input[] = "a,b,c,c,cx,c,c,c,d,e".split(",");
		testNFA(nfa,input);
	}
}
