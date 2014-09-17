package stu.base.oth;


public class MinMovements {
	public static int minmoves(String args[]){
		int a = Integer.parseInt(args[0]);
		int b = Integer.parseInt(args[1]);
		int c = Integer.parseInt(args[2]);
		int d = Integer.parseInt(args[3]);
		int total = a+2*b+3*c+4*d;
		if(total%3!=0) {
			System.out.println("not impossible");
			return -1;
		}
		int minMoves = 0;
		if(a>b) minMoves = 2*a+b;
		else if(a==b) minMoves = d / 3;
		else minMoves = b + (d-b)/3;
		System.out.println("Min Movements is "+minMoves);
		return minMoves;
	}
	public static void main(String args[]){
		args=new String[]{"1","1","4","5"};
		minmoves(args);
	}
}
