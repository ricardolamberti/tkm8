package pss;

import pss.core.data.BizPssConfig;

public class JPssVersion {
// el generador de versiones tocaba aca, y actualizaba estos datos, ahora deberia ir ha hacerlo en el ini, segun la aplicacion
  public static String sVersion = "";
  public static String sTitle = "";
  public static String sDate = "";

  public JPssVersion() {

  }

  public static String getPssVersion()  {
  	try {
			if (!sVersion.equals("")) return sVersion;
			return BizPssConfig.getPssConfig().getVersion();
		} catch (Exception e) {
			return "Desconocida";
		}
  }

  public static String getPssDate()  {
  	try {
			if (!sDate.equals("")) return sVersion;
			return  BizPssConfig.getPssConfig().getDateVersion();
		} catch (Exception e) {
			return "Desconocida";
		}
  }

  public static String getPssTitle()  {
  	try {
			if (!sTitle.equals("")) return sTitle;
			return BizPssConfig.getPssConfig().getTitle();
		} catch (Exception e) {
			return "Desconocido";
		}
  }
  
  public static void setPssVersion(String version) throws Exception {
	  sVersion = version;
  }
  
  public static void setPssTitle(String title) throws Exception {
	  sTitle = title;
  }
  
}
