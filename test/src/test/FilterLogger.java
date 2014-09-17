package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FilterLogger {
	public static boolean filter(String line){
		return !line.equals("999999999999999++++0") 
			&& !line.startsWith("vv-->中支公司")
			&& !line.startsWith("userCode：")
			&& !line.startsWith("getUserName")
			&& !line.startsWith("getLoginComCode")
			&& !line.startsWith("getLoginGradeCodes")
			&& !line.startsWith("getLoginSystemCode")
			&& !line.startsWith("INSERT INTO BgtPsFeeAccountLog")
			&& !line.startsWith("***** UserException occurred")
			&& !line.startsWith("ErrorCatalog")
			&& !line.startsWith("ErrorNo")
			&& !line.startsWith("ErrorModule")
			&& !line.startsWith("ErrorMessage")
			&& !line.startsWith("The stack trace is:")
			&& !line.startsWith("com.sinosoft.sysframework.exceptionlog.UserException")
			&& !line.startsWith("	at ")
			&& !line.equals("-------->11111111")
			&& !line.equals("")
			&& !line.startsWith("T")
			&& !line.startsWith("S")
			&& line.indexOf("37020000")>0
			&& line.indexOf("140513152229709923")>-1
			&& line.indexOf("-ACCOUNT2")>0
			;
	}
	public static String formatDate(String s){
		String date="";
		s = s.replace("-", "").replace("J", "");
		date="2014-"+s.substring(2,4)+"-"+s.substring(4,6)+" "+s.substring(6,8)+":"+s.substring(8,10);
		
		return date;
	}
	public static String formatArr(String s){
		String str = "";
		for(String ss : s.split("-")){
			str+=ss+",";
		}
		return str;
	}
	public static void fileter() throws Exception{

		File file = new File("f:\\文档\\CCIC\\日常工作\\20140711分析实扣费控日志\\");
		File newFile = new File(file.getAbsolutePath(),"tofeikong.log");
		if(newFile.getParentFile().exists()==false) newFile.getParentFile().mkdirs();
		BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
		File files[] = file.listFiles();
		for(File f : files){
			if(f.getName().startsWith("fyglsrv2.log") && !f.getName().endsWith(".gz")){
				BufferedReader reader = new BufferedReader(new FileReader(f));
				
				String line = null;
				while((line=reader.readLine())!=null){
					if(filter(line)){
						int index = line.indexOf("ACC");
						writer.write(formatArr(line.substring(index))+","+formatDate(line.substring(0,index))+","+line.substring(0,index-1));
						writer.newLine();
					}
				}
				reader.close();
			}
		}
		writer.flush();
		writer.close();
	
	}
	public static String[] analyze(String line){
		String[] ret = new String[6];
		int idx = line.indexOf("ACCOUNT");
		ret[0] = line.substring(0,idx-1);
		ret[1] = line.substring(idx,idx+8);
		ret[2] = line.substring(idx+9,idx+17);
		ret[5] = line;
		if(ret[1].equals("ACCOUNT8")||ret[1].equals("ACCOUNT9")){
			ret[3] = "";
			ret[4] = line.substring(idx+19);
		}else{
			ret[3] = line.substring(idx+18,idx+28);
			ret[4] = line.substring(idx+29);
		}
		return ret;
	}
	public static void tofeikong()throws Exception{
		File file = new File("f:/fyglservlog/tofeikong.log");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<String> list = new ArrayList<String>();
		String line = null;
		while((line=reader.readLine())!=null){
			if(line.indexOf("ACCOUNT")>0){
				list.add(line);
			}
		}
		System.out.println(list.size());
		Collections.sort(list, new Comparator<String>() {
			public int compare(String o1, String o2) {
				String com1 =analyze(o1)[1]+ analyze(o1)[2]+analyze(o1)[3]+analyze(o1)[4];
				String com2 =analyze(o2)[1] + analyze(o2)[2]+analyze(o2)[3]+analyze(o2)[4];
				return com1.compareTo(com2);
			}
		});
		BufferedWriter writer = new BufferedWriter(new FileWriter("f:/fyglservlog/tofeikong.csv"));
		writer.write("batch_line_id,accounttype,comcode,personcode,amount,string");
		writer.newLine();
		for(String str : list){
			String s[] = analyze(str);
			String tmp = s[1]+","+s[2]+","+s[3]+","+s[4]+","+s[0];
			System.out.println(tmp);
			writer.write(tmp);
			writer.newLine();
		}
		writer.close();
		System.out.println(list.size());
		reader.close();
	}
	public static void main(String args[]) throws Exception{
		fileter();
	}
}
