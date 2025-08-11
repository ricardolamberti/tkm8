package pss.common.layout;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class JFonts {
	
	public static final String COURIER = "COURIER";
	public static final String TIMES_ROMAN = "TIMES_ROMAN";
	public static final String HELVETICA = "HELVETICA";
	public static final String EAN8 = "EAN8";
	public static final String EAN13 = "EAN13";
	public static final String CODE39 = "CODE39";
	public static final String CODE128 = "CODE128";

	public static final JMap<String, String> getFonts() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(JFonts.COURIER, "Courrier");
		map.addElement(JFonts.TIMES_ROMAN, "Times Roman");
		map.addElement(JFonts.HELVETICA, "Helvetica");
		map.addElement(JFonts.EAN8, "Barcode - EAN8");
		map.addElement(JFonts.EAN13, "Barcode - EAN13");
		map.addElement(JFonts.CODE39, "Barcode - CODE39");
		map.addElement(JFonts.CODE128, "Barcode - CODE128");
		return map;
	}

}
