
public class DES_IP{
	public void ip(){
		String[] m = "0000000100100011010001010110011110001001101010111100110111101111".split("");
		int ip_table[] = new int[]{
				58,50,42,34,26,18,10,2,
				60,52,44,36,28,20,12,4,
				62,54,46,38,30,22,14,6,
				64,56,48,40,32,24,16,8,
				57,49,41,33,25,17,9,1,
				59,51,43,35,27,19,11,3,
				61,53,45,37,29,21,13,5,
				63,55,47,39,31,23,15,7
		};
		String ip_str = "";
		for(int i=0;i<64;i++){
			if(i%4==0) ip_str+=" ";
			ip_str += m[ip_table[i]];
		}
		System.out.println(ip_str);
	}
	public static void main(String args[]){
		DES_IP des=new DES_IP();
		des.ip();
	}
}
