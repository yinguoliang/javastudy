import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;


public class TestMain {
	public static void main(String rags[]) throws Exception{
		File file = new File("f:/work/Program/CCIC/ProductSystemLog/xgsrv/out.log");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		String packno = null;
		HashMap<String,HashSet<String>> map = new HashMap<String,HashSet<String>>();
		while((line=br.readLine())!=null){
			if(line.startsWith("T")){
				packno = line;
			}
			else if(line.startsWith("B")){
				if(map.get(line)==null) map.put(line, new HashSet<String>());
				map.get(line).add(packno);
			}
		}
		br.close();
		for(String key : map.keySet()){
			if(map.get(key).size()>1)
			   System.out.println(key+"   "+map.get(key));
		}
	}
}
