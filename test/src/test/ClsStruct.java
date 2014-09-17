package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClsStruct {
	ArrayList<Object> constantpool = new ArrayList<Object>();
	public static enum CP_TYPE{
		UTF8,INTEGER,FLOAT,DOUBLE,CLASS,STRING,FIELD,METHOD,INTFACE,NT,NONE,LONG
	};
	FileInputStream fis = null;
	public ClsStruct() {
		try{
			constantpool.add("");
			fis = new FileInputStream(new File("F:/work/Program/CCIC/XG/workspace/test/bin/test/A.class"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public  CP_TYPE cp_type(int type){
		switch(type){
		case 1:return CP_TYPE.UTF8;
		case 3:return CP_TYPE.INTEGER;
		case 4:return CP_TYPE.FLOAT;
		case 5:return CP_TYPE.LONG;
		case 6:return CP_TYPE.DOUBLE;
		case 7:return CP_TYPE.CLASS;
		case 8:return CP_TYPE.STRING;
		case 9:return CP_TYPE.FIELD;
		case 10:return CP_TYPE.METHOD;
		case 11:return CP_TYPE.INTFACE;
		case 12:return CP_TYPE.NT;
		}
		return CP_TYPE.NONE;
	}
	public  String strVal(byte ... bytes){
		String s = "";
		for(byte b : bytes){
			s+=Integer.toHexString((b & 0x000000FF) | 0xFFFFFF00).toUpperCase().substring(6);
		}
		return s;
	}
	public  String print(byte[] bytes){
		String s = strVal(bytes);
		System.out.println(s);
		return s;
	}
	
	public static int intVal(byte ... bytes){
		int sum = 0;
		for(int i=0;i<bytes.length;i++){
			sum += (int)(bytes[i]);
		}
		return sum;
	}
	public  int printInt(byte ... bytes){
		int v = intVal(bytes);
		System.out.println(v);
		return v;
	}
	public  byte[] read(int len) throws IOException{
		byte[] bs = new byte[len];
		fis.read(bs);
//		System.out.println(strVal(bs));
		return bs;
	}
	public  void dealCP(CP_TYPE type) throws Exception{
		byte[] bs = null;
		String val = null;
		switch(type){
		case CLASS:
			bs = read(2);
			System.out.println("CLASS index="+intVal(bs));
			constantpool.add(intVal(bs));
			break;
		case UTF8:
			bs = read(2);
			int len = intVal(bs);
			bs = read(len);
			val = new String(bs);
			System.out.println("UTF8 len="+len+",Val="+val);
			constantpool.add(val);
			break;
		case METHOD:
			bs = read(2);
			int d = intVal(bs);
			bs = read(2);
			int ntIndex = intVal(bs);
			System.out.println("METHOD ("+d+","+ntIndex+")");
			constantpool.add("#"+d+".#"+ntIndex);
			break;
		case NT:
			bs = read(2);
			int dNT = intVal(bs);
			bs = read(2);
			int ntIndexNT = intVal(bs);
			System.out.println("NT ("+dNT+","+ntIndexNT+")");
			constantpool.add("#"+dNT+".#"+ntIndexNT);
			break;
		case STRING:
			bs = read(2);
			System.out.println("String index="+intVal(bs));
			constantpool.add(intVal(bs));
			break;
		case FIELD:
			bs = read(2);
			int dFIELD = intVal(bs);
			bs = read(2);
			int ntIndexFIELD = intVal(bs);
			System.out.println("FIELD ("+dFIELD+","+ntIndexFIELD+")");
			constantpool.add("#"+dFIELD+".#"+ntIndexFIELD);
			break;
		default:
			System.out.println();
		}
	}
	public void dealCodeAttr(byte[] bs) throws IOException{
		int index = 0;
		byte[] b = new byte[2];
		System.arraycopy(bs,index, b, 0, b.length);  index+=b.length;
		System.out.print("    ====MaxStack:"+intVal(b));
		b = new byte[2];
		System.arraycopy(bs,index,b,0,b.length);  index+=b.length;
		System.out.print("  MaxLocals:"+intVal(b));
		b = new byte[4];
		System.arraycopy(bs,index,b,0,b.length);  index+=b.length;
		int codeLen = intVal(b);
		System.out.print("  CodeLen:"+codeLen);
		b = new byte[codeLen];
		System.arraycopy(bs,index,b,0,b.length);  index+=b.length;
		System.out.println();
		OP op = new OP();
		for(int i=0;i<b.length;i++){
			byte tmp = b[i];
			System.out.println("         "+strVal(tmp)+"\t: "+op.opmap.get(strVal(tmp).toLowerCase()));
			i+=op.opmap.get(strVal(tmp).toLowerCase()).paraLen;
		}
		b = new byte[2];//exception table length
		System.arraycopy(bs,index,b,0,b.length);  index+=b.length;
		int elen = intVal(b);
		System.out.println("    Exception Table Length:"+elen);
		
		b = new byte[2];//attribute count
		System.arraycopy(bs,index,b,0,b.length);  index+=b.length;
		int attrlen = intVal(b);
		System.out.println("    Attribute Count:"+attrlen);
		for(int j=0;j<attrlen;j++){
			byte[] bbs = new byte[2];//attribute name index
			System.arraycopy(bs,index,bbs,0,bbs.length);  index+=bbs.length;
			Object attrName = constantpool.get(intVal(bbs));
			System.out.print("    Attribute  NameIndex="+intVal(bbs)+"("+attrName+")");
			bbs = new byte[4];;//attribute length
			System.arraycopy(bs,index,bbs,0,bbs.length);  index+=bbs.length;
			int len = intVal(bbs);
			System.out.print("      AttrLen:"+len);
			bbs = new byte[len];
			System.arraycopy(bs,index,bbs,0,bbs.length);  index+=bbs.length;
			System.out.println("      AttrVal:"+strVal(bbs));
		}
		System.out.println();
	}
	public void dealAttr() throws IOException{
		byte[] bs = read(2);//attribute name index
		Object attrName = constantpool.get(intVal(bs));
		System.out.print("    Attribute  NameIndex="+intVal(bs)+"("+attrName+")");
		bs = read(4);//attribute length
		int len = intVal(bs);
		System.out.print("      AttrLen:"+len);
		bs = read(len);
		System.out.println("      AttrVal:"+strVal(bs));
		if("Code".equals(attrName)){
			dealCodeAttr(bs);
		}
	}
	public String cvAccFlg(byte[] bs){
		String str = "";
		int i=(bs[1]<<0)+(bs[0]<<8);
//		System.out.println(strVal(bs));
		if((i&0x0001)!=0) str+="public ";
		if((i&0x0002)!=0) str+="private ";
		if((i&0x0004)!=0) str+="protected ";
		if((i&0x0008)!=0) str+="static ";
		return str;
	}
	public void dealCS() throws Exception{
		//magic number
		byte[] bs = read(4);
		System.out.println("MagicNumber:"+strVal(bs));
		//minor version
		bs = read(2);
		System.out.println("MinorVersion="+intVal(bs));
		//major version
		bs = read(2);
		System.out.println("MajorVersion="+intVal(bs));
		//constant pool count
		bs = read(2);
		int cp_count = intVal(bs)-1;
		System.out.println("ConstantPoolCount="+cp_count);
		for(int i=0;i<cp_count;i++){
			System.out.print("#"+(i+1)+"\t");
			bs = read(1);//constant type
			dealCP(cp_type(intVal(bs)));
		}
		bs = read(2);//access flag
		bs = read(2);//this class
		bs = read(2);//super class
		bs = read(2);//interface count
		int intfCount = intVal(bs);
		System.out.println("Interface Count="+intVal(bs));
		for(int i=0;i<intfCount;i++){
			bs = read(2);
			System.out.println("  interface index="+intVal(bs)+"("+constantpool.get(new Integer(constantpool.get(intVal(bs)).toString()))+")");
		}
		bs = read(2);//fields count
		int fieldCount = intVal(bs);
		System.out.println("Field Count="+fieldCount);
		for(int i=0;i<fieldCount;i++){
			bs = read(2);//access flags
			System.out.print("Field  AccFlag:"+cvAccFlg(bs) +"\t");
			bs = read(2);//name index
			System.out.print("NameIndex:"+intVal(bs)+"("+constantpool.get(intVal(bs)) +")\t");
			bs = read(2);//descriptor index
			System.out.print("DIndex:"+intVal(bs) +"\t");
			bs = read(2);//attribute counts
			System.out.println("AccCount:"+intVal(bs));
			for(int j=0;j<intVal(bs);j++){
				dealAttr();
			}
		}
		bs = read(2);//method counts
		int methodCount = intVal(bs);
		System.out.println("Method Count="+methodCount);
//		System.out.println(constantpool);
		for(int i=0;i<methodCount;i++){
			bs = read(2);//access flags
			System.out.print("Method   AccFlg:"+cvAccFlg(bs)+"\t");
			bs = read(2);//name index
			System.out.print("NameIndex:"+intVal(bs)+"("+constantpool.get(intVal(bs)) +")\t");
			bs = read(2);//descriptor index
			System.out.print("DIndex:"+intVal(bs)+"\t");
			bs = read(2);//method attribute counts
			int attrCnt = intVal(bs);
			System.out.println("AttrCnt:"+attrCnt);
			for(int j=0;j<attrCnt;j++){
				dealAttr();
			}
		}
		bs = read(2);//attribute count
		int attrCnt = intVal(bs);
		System.out.println("Attribute Count = "+attrCnt);
		for(int j=0;j<attrCnt;j++){
			dealAttr();
		}
		fis.close();
	}
	public static void main(String args[]) throws Exception{
		new ClsStruct().dealCS();
	}
}
