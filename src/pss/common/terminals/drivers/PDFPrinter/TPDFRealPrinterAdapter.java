package pss.common.terminals.drivers.PDFPrinter;

import pss.common.layout.JFonts;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.drivers.PDFFile.TPDFPrinterAdapter;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class TPDFRealPrinterAdapter extends TPDFPrinterAdapter {
	

  public TPDFRealPrinterAdapter(JTerminal terminal) throws Exception {
  	super(terminal);
  }
  

  
  @Override
	public JMap<String, String> getFontTypes() throws Exception {
    JMap<String, String> map = JCollectionFactory.createOrderedMap();
    map.addElement(JFonts.COURIER, "Courier" );
    map.addElement(JFonts.TIMES_ROMAN, "Times Roman" );
    return map;
  }
  


    
}

