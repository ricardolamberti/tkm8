package pss.common.terminals.config;

import pss.common.terminals.core.JTerminal;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizType extends JRecord {


	

	public static final String SERIAL = "SERIAL";
	public static final String PARALLEL = "PARALLEL";
	public static final String EPSON_SERIAL = "EPSON_SERIAL";
	public static final String EPSON_T88III = "EPSON_T88III";
	public static final String VERIFONESC5000 = "VERIFONESC5000";
	public static final String WINDOW_PRINTER = "WINDOW_PRINTER";
	public static final String HASAR_PL8_100 = "HASAR_PL8_100";
	public static final String HASAR_PL8_201 = "HASAR_PL8_201";
	public static final String HASAR_P320_100 = "HASAR_P320_100";
	public static final String HASAR_P330_100 = "HASAR_P330_100";
	public static final String HASAR_P330_201 = "HASAR_P330_201";
	public static final String HASAR_P330_202 = "HASAR_P330_202";
	public static final String PDF = "PDF";
	public static final String EPSON_LX300F = "EPSON_LX300F";
	public static final String PDFPRINTER = "PDFPRINTER";
	public static final String ZEBRA = "ZEBRA";
	
//	private JString pType = new JString();
//	private JString pClase = new JString();
//	private JString pDescription = new JString();
	
	private static JMap<String, String> types; 
	
//	public void setType(String value) {
//		pType.setValue(value);
//	}
//	public void setClase(String value) {
//		pClase.setValue(value);
//	}
//	public void setDescription(String value) {
//		pDescription.setValue(value);
//	}
	
//	public String getClase() throws Exception {
//		return this.pClase.getValue();
//	}

//  private JTerminal terminalDummy=null;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////



  /**
   * Constructor de la Clase
   */
  public BizType() throws Exception {
  }
  
//  @Override
//  public void createProperties() throws Exception {
//  	this.addItem("type", pType);
//  	this.addItem("descrption", pDescription);
//  	this.addItem("clase", pClase);
//  }
//  
//  @Override
//  public void createFixedProperties() throws Exception {
//    this.addFixedItem( FIELD, "type", "Tipo", true, true, 15 );
//    this.addFixedItem( FIELD, "description", "Descripción", true, true, 50 );
//    this.addFixedItem( FIELD, "clase", "Clase", true, true, 150 );
//
//  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return ""; }



  public static String getClassType(String type) throws Exception {
  	if (type.equals(SERIAL)) return "pss.common.terminals.drivers.Serial.TSerial";
  	if (type.equals(PARALLEL)) return "pss.common.terminals.drivers.Parallel.TParallel";
  	if (type.equals(EPSON_SERIAL)) return "pss.common.terminals.drivers.EpsonSerial.TEpsonSerial";
  	if (type.equals(EPSON_T88III)) return "pss.common.terminals.drivers.EpsonTM88III.TEpsonT88III";
  	if (type.equals(VERIFONESC5000)) return "pss.common.terminals.drivers.VerifoneSC5000.TVerifoneSC5000";
  	if (type.equals(WINDOW_PRINTER)) return "pss.common.terminals.drivers.WindowPrinter.TWindowPrinter";
  	if (type.equals(HASAR_PL8_100)) return "pss.common.terminals.drivers.Hasar.THasarPL8_201";
  	if (type.equals(HASAR_PL8_201)) return "pss.common.terminals.drivers.Hasar.THasarPL8_100";
  	if (type.equals(HASAR_P320_100)) return "pss.common.terminals.drivers.Hasar.THasarP320_100";
  	if (type.equals(HASAR_P330_100)) return "pss.common.terminals.drivers.Hasar.THasarP330_100";
  	if (type.equals(HASAR_P330_201)) return "pss.common.terminals.drivers.Hasar.THasarP330_201";
  	if (type.equals(HASAR_P330_202)) return "pss.common.terminals.drivers.Hasar.THasarP330_202";
  	if (type.equals(EPSON_LX300F)) return "pss.common.terminals.drivers.EpsonFiscal.TEpsonLX300F";
  	if (type.equals(PDF)) return "pss.common.terminals.drivers.PDFFile.TPDFPrinter";
  	if (type.equals(PDFPRINTER)) return "pss.common.terminals.drivers.PDFPrinter.TPDFRealPrinter";
  	if (type.equals(ZEBRA)) return "pss.common.terminals.drivers.Zebra.TZebra";
  	JExcepcion.SendError("Terminal no contemplada");
  	return null;
  }

//  public static BizType createType(String type, String descr, String clase) throws Exception {
//  	BizType record = (BizType)Class.forName(clase).newInstance();
//  	record.setType(clase);
//  	record.setClase(clase);
//  	record.setDescription(clase);
//		return record;
//  }

  
  public static synchronized JMap<String, String> getTypes() throws Exception {
  	if (types!=null) return types;
  	JMap<String, String> map = JCollectionFactory.createOrderedMap();
  	if (JTools.isInstalled(getClassType(SERIAL))) 
  		map.addElement(SERIAL, "Serial");
  	if (JTools.isInstalled(getClassType(PARALLEL))) 
  		map.addElement(PARALLEL, "Paralela");
  	if (JTools.isInstalled(getClassType(EPSON_SERIAL))) 
  		map.addElement(EPSON_SERIAL, "Epson Serial");
  	if (JTools.isInstalled(getClassType(EPSON_T88III))) 
  		map.addElement(EPSON_T88III, "Epson T88III");
  	if (JTools.isInstalled(getClassType(VERIFONESC5000))) 
  		map.addElement(VERIFONESC5000, "Verifone SC5000");
  	if (JTools.isInstalled(getClassType(WINDOW_PRINTER))) 
  		map.addElement(WINDOW_PRINTER, "Window Printer");
  	if (JTools.isInstalled(getClassType(HASAR_PL8_100))) 
  		map.addElement(HASAR_PL8_100, "Hasar PL8 v 1.00");
  	if (JTools.isInstalled(getClassType(HASAR_PL8_201))) 
  		map.addElement(HASAR_PL8_201, "Hasar PL8 v 2.01");
  	if (JTools.isInstalled(getClassType(HASAR_P320_100))) 
  		map.addElement(HASAR_P320_100, "Hasar P320 v 1.00");
  	if (JTools.isInstalled(getClassType(HASAR_P330_100))) 
  		map.addElement(HASAR_P330_100, "Hasar P330 v 1.00");
  	if (JTools.isInstalled(getClassType(HASAR_P330_201))) 
  		map.addElement(HASAR_P330_201, "Hasar P330 v 2.01");
  	if (JTools.isInstalled(getClassType(HASAR_P330_202))) 
  		map.addElement(HASAR_P330_202, "Hasar P330 v 2.02");
  	if (JTools.isInstalled(getClassType(EPSON_LX300F))) 
  		map.addElement(EPSON_LX300F, "Epson LX300F");
  	if (JTools.isInstalled(getClassType(PDF))) 
  		map.addElement(PDF, "PDF");
  	if (JTools.isInstalled(getClassType(PDFPRINTER))) 
  		map.addElement(PDFPRINTER, "PDF a impresora");
  	if (JTools.isInstalled(getClassType(ZEBRA))) 
  		map.addElement(ZEBRA, "Zebra");
		return (types=map);
  }
//  public static BizType getObjType(String type) throws Exception {
//  	return (BizType)BizType.getTypes().getElement(key);
//  }
  
//  public JTerminal getTerminalDummy() throws Exception {
//  	if (this.terminalDummy!=null) return this.terminalDummy;
//  	JTerminal terminal = (JTerminal)Class.forName(pClase.getValue()).newInstance();
//  	return (this.terminalDummy=terminal);
//  }
  
  public static JTerminal createTerminal(String type) throws Exception {
  	return (JTerminal)Class.forName(getClassType(type)).newInstance();
  }
  
  
  
  
}
