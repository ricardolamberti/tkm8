package  pss.bsp.parseo;

import java.util.HashMap;
import java.util.Map;


public class JParseoFactory {
	static Map<String,String[]> parseosConocidosPdf = null;
	
	private static Map<String,String[]> getParseosConocidosPdf() {
		if (parseosConocidosPdf!=null) return parseosConocidosPdf;
		parseosConocidosPdf = new HashMap<String,String[]>();
		parseosConocidosPdf.put("PARSEO_COLOMBIA", new String[]{"pss.bsp.parseo.bspParseo.JParseoColombia"});
		parseosConocidosPdf.put("PARSEO_ARGENTINA", new String[]{"pss.bsp.parseo.bspParseo.JParseoArgentina2","pss.bsp.parseo.bspParseo.JParseoArgentina"});
		parseosConocidosPdf.put("PARSEO_MEXICO", new String[]{"pss.bsp.parseo.bspParseo.JParseoMexico2"});
		parseosConocidosPdf.put("PARSEO_GUATEMALA", new String[]{"pss.bsp.parseo.bspParseo.JParseoGuatemala2"});
		parseosConocidosPdf.put("PARSEO_BOLIVIA", new String[]{"pss.bsp.parseo.bspParseo.JParseoBolivia"});
		parseosConocidosPdf.put("PARSEO_BACKOFFICE", new String[]{"pss.bsp.parseo.bspParseo.JParseoGenericoBO"});
		parseosConocidosPdf.put("PARSEO_CRICA", new String[]{"pss.bsp.parseo.bspParseo.JParseoCostaRica"});
		parseosConocidosPdf.put("PARSEO_PANAMA", new String[]{"pss.bsp.parseo.bspParseo.JParseoPanama"});
		parseosConocidosPdf.put("PARSEO_CHILE", new String[]{"pss.bsp.parseo.bspParseo.JParseoChile"});
	return parseosConocidosPdf;
	}
	static Map<String,String[]> parseosConocidosExcel = null;
	
	private static Map<String,String[]> getParseosConocidosExcel() {
		if (parseosConocidosExcel!=null) return parseosConocidosExcel;
		parseosConocidosExcel = new HashMap<String,String[]>();
		parseosConocidosExcel.put("PARSEO_COLOMBIA", new String[]{"pss.bsp.parseo.bspParseo.JParseoExcel"});
		parseosConocidosExcel.put("PARSEO_ARGENTINA", new String[]{"pss.bsp.parseo.bspParseo.JParseoExcel"});
		parseosConocidosExcel.put("PARSEO_MEXICO", new String[]{"pss.bsp.parseo.bspParseo.JParseoExcel"});
		parseosConocidosExcel.put("PARSEO_ARGENTINA", new String[]{"pss.bsp.parseo.bspParseo.JParseoExcel"});
		parseosConocidosExcel.put("PARSEO_GUATEMALA", new String[]{"pss.bsp.parseo.bspParseo.JParseoExcel"});
		parseosConocidosExcel.put("PARSEO_BOLIVIA", new String[]{"pss.bsp.parseo.bspParseo.JParseoExcel"});
		parseosConocidosExcel.put("PARSEO_CRICA", new String[]{"pss.bsp.parseo.bspParseo.JParseoExcel"});
		parseosConocidosExcel.put("PARSEO_PANAMA", new String[]{"pss.bsp.parseo.bspParseo.JParseoExcel"});
		parseosConocidosExcel.put("PARSEO_CHILE", new String[]{"pss.bsp.parseo.bspParseo.JParseoExcel"});
		parseosConocidosExcel.put("PARSEO_BACKOFFICE", new String[]{"pss.bsp.parseo.bspParseo.JParseoGenericoBO"});
		parseosConocidosExcel.put("PARSEO_TERRESTRE", new String[]{"pss.bsp.parseo.bspParseo.JParseoInterfazTerrestre"});
		parseosConocidosExcel.put("PARSEO_DQB", new String[]{"pss.bsp.parseo.bspParseo.JParseoInterfazDQB"});
		parseosConocidosExcel.put("PARSEO_COPA", new String[]{"pss.bsp.parseo.bspParseo.JParseoInterfazCopa"});
		return parseosConocidosExcel;
	}
	public static IParseo[] getInstance(String parseador) throws Exception {
		return getInstance(parseador,"*","*");
	}	
	public static IParseo[] getInstance(String parseador,String typeFile) throws Exception {
		return getInstance(parseador,typeFile,"*");
	}
	public static IParseo[] getInstance(String parseador,String typeFile, String format) throws Exception {
		String name = parseador;
		if (typeFile.equals("XLSX") || typeFile.equals("XLS")) {
			String[] clases = getParseosConocidosExcel().get(name);
			if (clases==null) throw new Exception("Parseador no implementado"+parseador);
			IParseo[] parsers =new IParseo[clases.length];
			int i=0;
			for(String clase:clases) {
				IParseo parser = (IParseo)Class.forName(clase).newInstance();
				parser.setIdParseador(clase);
				parser.setTypeFile(typeFile);
				parser.setFormat(format);	
				parsers[i++]=parser;
			}	
			if (parsers.length!=0)
				return parsers; 
		}
		String[] clases = getParseosConocidosPdf().get(name);
		if (clases==null) throw new Exception("Parseador no implementado"+parseador);
		IParseo[] parsers =new IParseo[clases.length];
		int i=0;
		for(String clase:clases) {
			IParseo parser = (IParseo)Class.forName(clase).newInstance();
			parser.setIdParseador(clase);
			parser.setTypeFile(typeFile);
			parser.setFormat(format);	
			parsers[i++]=parser;
		}

		return parsers;
	}
	public static IParseo getInstanceFromClass(String clase) throws Exception {
		IParseo parser = (IParseo)Class.forName(clase).newInstance();
		parser.setIdParseador(clase);
		return parser;
	}
}


