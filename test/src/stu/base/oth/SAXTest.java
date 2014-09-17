package stu.base.oth;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SAXTest {
	public static void main(String args[]) throws Exception{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(SAXTest.class.getResourceAsStream("test.xml"),
				new DefaultHandler(){
			@Override
			public void startElement(String uri,String localName,String qName,Attributes attributes)
			throws SAXException{
				System.out.print(qName);
			}
			@Override
			public void endElement(String uri,String localName,String qName){
				System.out.println(qName);
			}
			public void characters(char[] ch, int start, int length){
				char c[] = new char[length];
				System.arraycopy(ch,start, c, 0, length);
				String str = new String (c);
				if(str.trim().equals("")) return;
				System.out.print(str);
			}
		});
	}
}
