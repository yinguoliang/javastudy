package stu.fa.nfa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

class State2{
	public State2(int state){
		this.id = state;
	}
	public int id = -1;
	public Map<String,State2> mapping = new HashMap<String,State2>();
	public HashSet<String> accessChars = new HashSet<String>();
	public State2 clone(){
		State2 state = new State2(this.id);
		state.mapping = this.mapping;
		state.accessChars = new HashSet<String>();
		return state;
	}
}
class NFA2{
	public final static String EPSILON = "#";
	public HashMap<Integer,State2> states = new HashMap<Integer,State2>();
	public int startState;
	public int finalState;
	public void putStates(State2 ...state2s){
		for(State2 state : state2s){
			states.put(state.id, state);
		}
	}
	ArrayList<String> buffer = new ArrayList<String>();
	int bufferPos = -1;
	Stack<State2> path = new Stack<State2>();
	public void printPath(){
		Iterator<State2> it = path.iterator();
		while(it.hasNext()){
			System.out.print(it.next().id);
		}
		System.out.println();
	}
	public boolean forward(String input){
		State2 state = path.peek();
		if(state.accessChars.contains(input)) return false;
		State2 nextState = state.mapping.get(input);
		if(nextState==null){
			nextState = state.mapping.get(EPSILON);
			input = EPSILON;
		}
		if(nextState!=null){
			path.push(nextState.clone());
			state.accessChars.add(input);
			if(!input.equals(EPSILON)) ++bufferPos;
			return true;
		}
		return false;
	}
	public boolean back1(){
		State2 topState = path.pop();
		if(topState.id==startState) return false;
		State2 curState = path.peek();
		String curChar = buffer.get(bufferPos);
		if(curState.mapping.get(curChar)!=null){
			if(curState.mapping.get(curChar).id==topState.id)
				--bufferPos;
		}
		return true;
	}
	public void accept(String str){
		String[] strs = str.split("");
		for(String s : strs) if(s!=null&&!s.equals("")) buffer.add(s);
		bufferPos = 0;
		path.push(states.get(0).clone());
		while(bufferPos<buffer.size() || path.peek().id != finalState){
			printPath();
			if(!forward(buffer.get(bufferPos)))
				if(!back1()) break;
		}
		System.out.println("accept............");
	}
	
}
public class NFATest2 {
	public NFA2 generateNFA(){
		NFA2 nfa = new NFA2();
		State2 s0 = new State2(0);
		State2 s1 = new State2(1);
		State2 s2 = new State2(2);
		State2 s3 = new State2(3);
		State2 s4 = new State2(4);
		State2 s5 = new State2(5);
		
		s0.mapping.put("a", s1);
		s0.mapping.put("b", s2);
		s1.mapping.put("a", s3);
		s2.mapping.put("a", s4);
		s2.mapping.put("#", s3);
		s3.mapping.put("a", s3);
		s3.mapping.put("b", s5);
		s4.mapping.put("b", s2);
		s4.mapping.put("b", s5);
		
		nfa.putStates(s0,s1,s2,s3,s4,s5);
		nfa.startState=0;
		nfa.finalState=5;
		
		return nfa;
	}
	public static void main(String args[]){
		NFATest2 NFATest2 = new NFATest2();
		NFA2 nfa = NFATest2.generateNFA();
		nfa.accept("b");
	}
	
}
