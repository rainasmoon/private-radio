import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;


public class TestSomeSimple {

	Logger log = Logger.getLogger("wh:");

	@Test
	public void testAlot() {
//		log.info("lot:" + "aia 526".replaceFirst("aia", ""));
//		
//		String msgString = "2 aia,come on";
//		String[] msgArray = msgString.split(" ");
//		
//		log.info("size:"+msgArray.length);
//		log.info(msgArray[0]);
//		log.info(msgArray.toString());
		
		List<String> list = new ArrayList<String>();
//		list.add("1");
//		list.add("2");
		
//		String[] strArray2 = new String[list.size()];
//		strArray2 = list.toArray(strArray2);
//		strArray2= list.toArray(new String[0]);
//		
//		for(String str: strArray2) {
//			log.info(str);
//		}
//		
//		
//		
//		log.info(getPhone().substring(0, 3) + "****" + getPhone().substring(7, 11));
//		
		
		log.info(""+"".indexOf(' '));
		log.info(""+" ".indexOf(' '));
		log.info(""+"aia ".indexOf(' '));
		log.info(""+"aia a".indexOf(' '));
		log.info(""+" a ".indexOf(' '));
	}
	
	private static String getPhone() {
		String ph = "+8615811015803";
		ph = ph.substring(ph.length()-11, ph.length());
		return ph;
	}
	
}
