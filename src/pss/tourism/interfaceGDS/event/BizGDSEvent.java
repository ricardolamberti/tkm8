package pss.tourism.interfaceGDS.event;

import pss.common.event.manager.BizEventCode;

public class BizGDSEvent extends BizEventCode {

	
	public static long EVT_PROCESS_ERROR = 1;
	
  public BizGDSEvent() throws Exception {  }

  @Override
	public void generateModuleEventCodeList() throws Exception {
    create(""+EVT_PROCESS_ERROR, "Interfaz Procesada con Error");
  }
  
	public String getEventModule() { return "MODULE_GDS"; } 

}

