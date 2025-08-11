package pss.common.terminals.drivers.PDFFile;

import pss.common.layout.JFonts;
import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class TPDFPrinterAdapter extends JPrinterAdapter {
	

  public TPDFPrinterAdapter(JTerminal terminal) throws Exception {
  	super(terminal);
  }
  

	
//	private TPDFPrinter getWindowPrinter() throws Exception {
//		return (TPDFPrinter) this.getTerminal();
//	}
  
//  @Override
//	public JMap<String, String> getModalidades() throws Exception {
//    JMap<String, String> map = JCollectionFactory.createMap();
//    map.addElement(TPDFPrinter.LETTER,  "Letter" );
//    map.addElement(TPDFPrinter.LETTER_LANDSCAPE, "Letter Landscape" );
//    return map;
//  }
  
  @Override
	public JMap<String, String> getFontTypes() throws Exception {
    JMap<String, String> map = JCollectionFactory.createOrderedMap();
    map.addElement(JFonts.COURIER, "Courier" );
    map.addElement(JFonts.TIMES_ROMAN, "Times Roman" );
    return map;
  }
  
//
////----------------------------------------------------------------------------------------------------------------------
//  @Override
//	public Answer flush() throws Exception {
//    int iSpacing = this.getLineSpacing();
//    if( iSpacing > 30 ) 
//    	iSpacing = 30;
//     else if( iSpacing <= 0 ) 
//    	 iSpacing = 1;
//
//    this.getWindowPrinter().sendDoc(this.getFuente(), this.getModalidad(), iSpacing);
//    return this.getPrinter().flush();
//  }
//

    
}

