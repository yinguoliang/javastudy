import java.util.regex.Pattern;


public class RegexTest {
	public static void main(String args[]){
		Pattern p = Pattern.compile("");
		p.matcher("").matches();
	}
}
