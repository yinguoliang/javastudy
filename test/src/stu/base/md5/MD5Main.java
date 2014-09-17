package stu.base.md5;

import java.util.ArrayList;
import java.util.List;

public class MD5Main {
	static int A=0x01234567;
	static int B=0x89ABCDEF;
	static int C=0xFEDCBA98;
	static int D=0x76543210;
	//F(X,Y,Z) =(X&Y)|((~X)&Z)
	public static int F(int x,int y,int z){
		return (x&y)|((~x)&z);
	}
	//G(X,Y,Z) =(X&Z)|(Y&(~Z))
	public static int G(int x,int y,int z){
		return (x&z)|(y&(~z));
	}
	//H(X,Y,Z) =X^Y^Z
	public static int H(int x,int y,int z){
		return x^y^z;
	}
	//I(X,Y,Z)=Y^(X|(~Z))
	public static int I(int x,int y,int z){
		return y^(x|(~z));
	}
	//FF(a,b,c,d,Mj,s,ti);表示 a = b + ((a + F(b,c,d) + Mj + ti) << s)
	public static int FF(int a,int b,int c,int d,int  m,int s,int t){
		return b+((a+F(b,c,d)+m+t)<<s);
	}
	//GG(a,b,c,d,Mj,s,ti);表示 a = b + ((a + G(b,c,d) + Mj + ti) << s)
	public static int GG(int a,int b,int c,int d,int  m,int s,int t){
		return b+((a+G(b,c,d)+m+t)<<s);
	}
	//HH(a,b,c,d,Mj,s,ti);表示 a = b + ((a + H(b,c,d) + Mj + ti) << s)
	public static int HH(int a,int b,int c,int d,int  m,int s,int t){
		return b+((a+H(b,c,d)+m+t)<<s);
	}
	//Ⅱ（a,b,c,d,Mj,s,ti);表示 a = b + ((a + I(b,c,d) + Mj + ti) << s)
	public static int II(int a,int b,int c,int d,int  m,int s,int t){
		return b+((a+I(b,c,d)+m+t)<<s);
	}
	public static String str2bin(String str){
		StringBuffer buffer = new StringBuffer();
		for(byte b : str.getBytes()){
			buffer.append(Integer.toBinaryString(b));
		}
		return buffer.toString();
	}
	public static String[] group(String binStr){
		List<String> list = new ArrayList<String>();
		if(binStr.length()!=512)
		for(int i=binStr.length();i<512;i++) binStr+="1";
		list.add(binStr)
		;
		return list.toArray(new String[0]);
	}
	public static int[] message(String str){
		return new int[]{
		 12,32,32,43,
		 12,33,4533,12,
		 55,23,22,4,
		 123,44,90,32
		};
	}
	public static String int2bin(int i){
		String s = Integer.toBinaryString(i);
		return s+" "+i+" "+s.length()+"\n";
	}
	public static String md5_rotatition(String str){
		int a = A,b=B,c=C,d=D;
		int m[]=message(str);
		//round 1
		a = FF(a,b,c,d,m[0],7,0xd76aa478);
		d = FF(d,a,b,c,m[1],12,0xe8c7b756);
		c = FF(c,d,a,b,m[2],17,0x242070db);
		b = FF(b,c,d,a,m[3],22,0xc1bdceee);
		a = FF(a,b,c,d,m[4],7,0xf57c0faf);
		d = FF(d,a,b,c,m[5],12,0x4787c62a);
		c = FF(c,d,a,b,m[6],17,0xa8304613);
		b = FF(b,c,d,a,m[7],22,0xfd469501);
		a = FF(a,b,c,d,m[8],7,0x698098d8);
		d = FF(d,a,b,c,m[9],12,0x8b44f7af);
		c = FF(c,d,a,b,m[10],17,0xffff5bb1);
		b = FF(b,c,d,a,m[11],22,0x895cd7be);
		a = FF(a,b,c,d,m[12],7,0x6b901122);
		d = FF(d,a,b,c,m[13],12,0xfd987193);
		c = FF(c,d,a,b,m[14],17,0xa679438e);
		b = FF(b,c,d,a,m[15],22,0x49b40821);
		//round 2
		a = GG(a,b,c,d,m[1],5,0xf61e2562);
		d = GG(d,a,b,c,m[6],9,0xc040b340);
		c = GG(c,d,a,b,m[11],14,0x265e5a51);
		b = GG(b,c,d,a,m[0],20,0xe9b6c7aa);
		a = GG(a,b,c,d,m[5],5,0xd62f105d);
		d = GG(d,a,b,c,m[10],9,0x02441453);
		c = GG(c,d,a,b,m[15],14,0xd8a1e681);
		b = GG(b,c,d,a,m[4],20,0xe7d3fbc8);
		a = GG(a,b,c,d,m[9],5,0x21e1cde6);
		d = GG(d,a,b,c,m[14],9,0xc33707d6);
		c = GG(c,d,a,b,m[3],14,0xf4d50d87);
		b = GG(b,c,d,a,m[8],20,0x455a14ed);
		a = GG(a,b,c,d,m[13],5,0xa9e3e905);
		d = GG(d,a,b,c,m[2],9,0xfcefa3f8);
		c = GG(c,d,a,b,m[7],14,0x676f02d9);
		b = GG(b,c,d,a,m[12],20,0x8d2a4c8a);
		//round 3
		a = HH(a,b,c,d,m[5],4,0xfffa3942);
		d = HH(d,a,b,c,m[8],11,0x8771f681);
		c = HH(c,d,a,b,m[11],16,0x6d9d6122);
		b = HH(b,c,d,a,m[14],23,0xfde5380c);
		a = HH(a,b,c,d,m[1],4,0xa4beea44);
		d = HH(d,a,b,c,m[4],11,0x4bdecfa9);
		c = HH(c,d,a,b,m[7],16,0xf6bb4b60);
		b = HH(b,c,d,a,m[10],23,0xbebfbc70);
		a = HH(a,b,c,d,m[13],4,0x289b7ec6);
		d = HH(d,a,b,c,m[0],11,0xeaa127fa);
		c = HH(c,d,a,b,m[3],16,0xd4ef3085);
		b = HH(b,c,d,a,m[6],23,0x04881d05);
		a = HH(a,b,c,d,m[9],4,0xd9d4d039);
		d = HH(d,a,b,c,m[12],11,0xe6db99e5);
		c = HH(c,d,a,b,m[15],16,0x1fa27cf8);
		b = HH(b,c,d,a,m[2],23,0xc4ac5665);
		return int2bin(a)+int2bin(b)+int2bin(c)+int2bin(d);
	}
	public static void main(String args[]){
		System.out.println(md5_rotatition(""));
	}
}
