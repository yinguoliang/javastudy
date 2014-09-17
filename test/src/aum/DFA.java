package aum;

import java.util.ArrayList;

class State{
	public int stateid;
	public boolean isFinal=false;
	public State(int stateid){
		this.stateid=stateid;
	}
	public String toString(){
		return "("+stateid+","+isFinal+")";
	}
}
class Transition{
	public State start;
	public State end;
	public String input;
	public Transition(State start,State end,String input){
		this.start=start;
		this.end=end;
		this.input=input;
	}
}
class Input{
	public String[] chars;
	private int currentIndex = 0;
	public Input(String inputStr){
		this.chars=inputStr.split("");
	}
	public boolean isEnd(){
//		System.out.println(chars.length+","+currentIndex);
		if(chars==null) return true;
		else if(chars.length<=currentIndex) return true;
		return false;
	}
	public String nextInput(){
		currentIndex++;
		return chars[currentIndex-1];
	}
}
public class DFA {
	public State currentState = new State(0);;
	public ArrayList<Transition> transitions = new ArrayList<Transition>();
	public State startState = new State(0);
	public void addTransition(int s,int e,String input){
		transitions.add(new Transition(new State(s),new State(e),input));
	}
	public boolean isEndState(State state){
//		System.out.println("CurrentState:"+state);
		if(state.stateid==6) return true;
		return false;
	}
	public DFA(){
		//10(01)*10
		addTransition(0,1,"1");
		addTransition(1,2,"0");
		addTransition(2,3,"1");
		addTransition(2,4,"0");
		addTransition(4,5,"1");
		addTransition(5,4,"0");
		addTransition(5,3,"1");
		addTransition(3,6,"0");
		addTransition(0,0,"");
		addTransition(1,1,"");
		addTransition(2,2,"");
		addTransition(3,3,"");
		addTransition(4,4,"");
		addTransition(5,6,"");
		addTransition(6,6,"");
	}
	public boolean transite(String input){
		for(Transition t : transitions){
			if(t.start.stateid==currentState.stateid){
				if(t.input.equals(input)){
					this.currentState=t.end;
					return true;
				}
			}
		}
		return false;
	}
	public boolean accept(Input input){
		while(!input.isEnd()){
			System.out.print(currentState.stateid+"->");
			if(!this.transite(input.nextInput())){
				return false;
			}
		}
		System.out.println(currentState.stateid);
		if(isEndState(currentState)) return true;
		else return false;
	}
	public static void main(String args[]){
//		System.out.println(new DFA().accept(new Input("1010")));
//		System.out.println(new DFA().accept(new Input("100110")));
//		System.out.println(new DFA().accept(new Input("10011010")));
		System.out.println(new DFA().accept(new Input("10010110")));
	}
}
