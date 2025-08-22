package  pss.common.event.sql;

import pss.common.event.manager.BizEventCode;
import pss.core.tools.JTools;
import pss.core.tools.collections.JMap;

public class BizSqlEventCode extends BizEventCode {

	
	public static String EVT_SQLEVENT = "1";

	protected JMap<String, String> map;
	
  public BizSqlEventCode() throws Exception {
  	
  }

  @Override
	public void generateModuleEventCodeList() throws Exception {
    create(EVT_SQLEVENT, "Alarma programada");
  }
  
	public void create(String codigo, String descrip) throws Exception {
		map.addElement(JTools.LPad(codigo+"", 3, "0"), JTools.LPad(codigo+"", 3, "0")+"-"+descrip);
		super.create(codigo, descrip);
	}

	public String getEventModule() { return BizEventCode.MODULE_CORE; } 
	
//	public JMap<String,String> getEventsMap() {return map;}
//	
//
//	public JMap<String,String> generateMapModuleEvents() throws Exception {
//		if (map!=null) return map;
//		map = JCollectionFactory.createOrderedMap();
//		generateModuleEventCodeList();
//		return map;
//	}
	
//	public JWins getIntalledModuleEvents() throws Exception {
//		return JWins.createVirtualWinsFromMap(generateMapModuleEvents());
//	}

	

	

}

