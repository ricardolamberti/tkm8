package pss.common.terminals.drivers.WindowPrinter;

import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.messages.answer.Answer;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class TWindowPrinterAdapter extends JPrinterAdapter {
	

  public TWindowPrinterAdapter(JTerminal terminal) throws Exception {
  	super(terminal);
  }


	
	private TWindowPrinter getWindowPrinter() throws Exception {
		return (TWindowPrinter) this.getTerminal();
	}
  
  @Override
	public JMap<String, String> getModalidades() throws Exception {
    JMap<String, String> map = JCollectionFactory.createMap();
    map.addElement(TWindowPrinter.PORTRAIT,  "Portrait" );
    map.addElement(TWindowPrinter.LANDSCAPE, "Landscape" );
    return map;
  }
  
  @Override
	public JMap<String, String> getFontTypes() throws Exception {
    JMap<String, String> map = JCollectionFactory.createOrderedMap();
    map.addElement(TWindowPrinter.COURIER_12, "Courier 12 DPI" );
    map.addElement(TWindowPrinter.COURIER_10, "Courier 10 DPI" );
    map.addElement(TWindowPrinter.COURIER_8, "Courier 8 DPI" );
    map.addElement(TWindowPrinter.COURIER_6, "Courier 6 DPI" );
    map.addElement(TWindowPrinter.LUCIDA_12, "Lucida Console 12 DPI" );
    map.addElement(TWindowPrinter.LUCIDA_10, "Lucida Console 10 DPI" );
    map.addElement(TWindowPrinter.LUCIDA_8, "Lucida Console 8 DPI" );
    map.addElement(TWindowPrinter.LUCIDA_6, "Lucida Console 6 DPI" );
    return map;
  }

//----------------------------------------------------------------------------------------------------------------------
  @Override
	public Answer flush() throws Exception {
    double iSpacing = this.getLineSpacing();
    if( iSpacing > 30 ) 
    	iSpacing = 30;
     else if( iSpacing <= 0 ) 
    	 iSpacing = 1;

    this.getWindowPrinter().sendDoc(this.getFuente(), this.getFontSize(), this.getModalidad(), (int)iSpacing);
    return this.getPrinter().flush();
  }


    
}

