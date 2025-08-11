package pss.common.terminals.core;

import pss.common.layout.JFieldInterface;
import pss.common.layout.JFieldReq;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

public class JPrinterAdapter {

	public static final String OFICIO = "OFICIO";
	public static final String OFICIO_LANDSCAPE = "OFICIO_LANDSCAPE";
	public static final String LETTER = "LETTER";
	public static final String LETTER_LANDSCAPE = "LETTER_LANDSCAPE";
	public static final String A4 = "A4";
	public static final String SOBRE = "SOBRE";
	

//------------------------------------------------------------------------------
// Object Variables
//------------------------------------------------------------------------------
	private String sSize;
	private String sCustomSize;
	private String sImageSize;
  private String sModalidad;
  private String sFont;
  private int iFontSize;
  private int iCopias;
  private double fLineSpacing;
  private String sBackground;
  private String sPrinterDocType;
  private long lPrinterDocNumber;
  private boolean isOpen;
  protected JTerminal terminal;
  private JFieldInterface fieldInterface;

  protected int linesToSkip;
  private JList<String> linesRecorded;
  private JMap<String, String> supportedDocs;

  
//------------------------------------------------------------------------------
// Class Constants
//------------------------------------------------------------------------------
  public void setSize(String value) { sSize= value; }
  public void setCustomSize(String value) { sCustomSize= value; }
  public void setImageSize(String value) { sImageSize= value; }
  public void setModalidad(String value) { sModalidad = value; }
  public void setFuente(String value) { sFont = value; }
  public void setFontSize(int value) { iFontSize = value; }
  public void setCopias(int value) { iCopias = value; }
  public void setLineSpacing(double value) { fLineSpacing = value; }
  public void setBackground(String value) { sBackground = value; }  
  public void setPrinterDocType(String value) { sPrinterDocType = value; }
  public void setPrinterDocNumber(long value) { lPrinterDocNumber = value; }
//  public void setNroRollo(String value) { sNroRollo = value; }
//  public boolean hasNroRollo() throws Exception { return sNroRollo!=null; }
  public void setFieldInterface(JFieldInterface value) { this.fieldInterface = value; }
  public String getSize() { return sSize; }
  public String getCustomSize() { return sCustomSize; }
  public String getImageSize() { return sImageSize; }
  public boolean hasSize() { return sSize!=null && !sSize.equals(""); }
  public boolean hasImageSize() { return sImageSize!=null && !sImageSize.equals(""); }
  public String getModalidad() { return sModalidad; }
  public String getFuente() { return sFont; }
  public int getFontSize() { return iFontSize; }
  public double getLineSpacing() { return fLineSpacing; }
  public String getBackground() { return sBackground; }
//  public String getNroRollo() { return sNroRollo; }
  public JPrinterInterface getPrinter() throws Exception { return (JPrinterInterface)this.getTerminal(); }
  public JFieldInterface getFieldInterface() throws Exception { return this.fieldInterface; }

//------------------------------------------------------------------------------
// Class Constructors
//------------------------------------------------------------------------------
  public JPrinterAdapter(JTerminal terminal) throws Exception {
  	this.terminal=terminal;
  }

//  public boolean isDocumentOpen() throws Exception {
//  	return isOpen; 
//  }
  public boolean checkDocOpen() throws Exception {
  	return false; 
  }
  
  public long getCurrentNum() throws Exception { 
  	return 0L; 
  }

  public String getTipoCuitFiscal(String idSystem) throws Exception { 
  	return idSystem; 
  }

  public String getTipoCuitPasaporte() throws Exception { 
  	return ""; 
  }

  public String getResponIdFiscal(String idSystem) throws Exception { 
  	return idSystem; 
  }

  public synchronized JMap<String, String> getSupportedDocuments() throws Exception {
  	if (this.supportedDocs!=null) return this.supportedDocs;
  	this.loadSupportedDocuments();
  	return this.supportedDocs;
  }
  protected void loadSupportedDocuments() throws Exception {
  }
  public void addSupportedDocs(String id, String descr) throws Exception {
  	if (this.supportedDocs==null) this.supportedDocs = JCollectionFactory.createMap();
  	supportedDocs.addElement(id, descr);
  }

  public JTerminal getTerminal() throws Exception {
  	return this.terminal;
  }
  
//------------------------------------------------------------------------------
// General Document Managment Methods
//------------------------------------------------------------------------------
  public Answer openDocument() throws Exception {
    if( this.isDocOpen() )  JExcepcion.SendError("Ya existe un comprobante abierto");
    Answer awr = this.getPrinter().openDoc();
    isOpen = true;
    return awr;
  }
  public Answer cancelDocument() throws Exception {
    if( !this.isDocOpen() ) { return new AwrOk(); }
    Answer awr = this.getPrinter().cancelDoc();
    isOpen = false;
    return awr;
  }
  public Answer closeDocument() throws Exception {
    if( !this.isDocOpen() ) { return new AwrOk(); }
    Answer awr = this.getPrinter().closeDoc();
    isOpen = false;
//    this.printCopies();
    return awr;
  }
  
  public boolean isDocOpen() throws Exception {
  	return this.isOpen;
  }
  	
//	public void print(String zSeccion, Object zSource) throws Exception {
//		JFieldReq req = new JFieldReq(this.fieldInterface, zSeccion, zSource);
//		JList lines = (JList)this.fieldInterface.getField(req);
//		JIterator iter = lines.getIterator();
//		while (iter.hasMoreElements()) {
//			String line = (String)iter.nextElement();
//			this.getPrinter().printLine(line);
//		}
//	}
	
  public Answer print(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	this.getPrinter().printLine(JTools.unencodeString(req.getString("line")));
  	return new AwrOk();
  }
  public Answer printLine(String line) throws Exception {
  	return this.getPrinter().printLine(line);
  }
  public Answer flush() throws Exception {
  	return this.getPrinter().flush();
  }
  public Answer skeepLines(int lines) throws Exception {
  	return this.getPrinter().skeepLines(lines);
  }
	
//  protected void printCopies() throws Exception {
////    if( oLayout == null ) { return; }
////    int copias = oLayout.getCopias();
//  	int copias = 0; // ver de donde sacar las copias
//    for(int i=1; i<=copias; i++) {
//      try {
//      	this.openDocument();
//        //getLayout().printCopy(i, linesToSkip);
//      	this.closeDocument();
//      }
//      catch( Exception e ) {
//        isOpen = false;
//        PssLogger.logError("Error imprimiendo Copias. " + e.getMessage());
//        break;
//      }
//    }
//    linesToSkip = 0;
//  }

  public void generate(String sector, Object source) throws Exception {
  }
  
//  public boolean supportDocument() throws Exception {
//  	return false;
//  }
    
  public JMap<String, String> getModalidades() throws Exception {
    JMap<String, String> map = JCollectionFactory.createMap();
    map.addElement("DEFAULT", "Modo Unico" );
    return map;
  }
  public JMap<String, String> getFontTypes() throws Exception {
    JMap<String, String> map = JCollectionFactory.createMap();
    map.addElement("DEFAULT", "Fuente Unica" );
    return map;
  }
  
  public boolean hasCierreZ() throws Exception {
  	return false;
  }
  public boolean hasMontoMaximo(String zTipoDoc) throws Exception {
  	return this.getMontoMaximo(zTipoDoc)!=0;
  }
  public double getMontoMaximo(String zTipoDoc) throws Exception {
  	return 0;
  }


//  public void skipLines(int lines) throws Exception { }
//  public void setPos(Object zValue) {}
//  public void setObjDocumentType(Object zTipoComp) {}
//  public boolean isNumeracionUtilizada() throws Exception { return false;}
//  public boolean isNumeracionUtilizada(String zSection, Object zSource) throws Exception { return isNumeracionUtilizada();}

  public boolean ImprimeFecha() throws Exception {
    return false;
  }

  public boolean printerPrintDate() throws Exception { return false; }

//  public void controlPrinter() throws Exception {}

	public String getLinesRecordedAsXML() throws Exception {
		StringBuffer xml = new StringBuffer();
		xml.append("<xml>");
		JIterator<String> iter = this.linesRecorded.getIterator();
		while(iter.hasMoreElements()) {
			xml.append(iter.nextElement());
		}
		xml.append("</xml>");
		return xml.toString();
	}
	
	public void startRecord() throws Exception {
		this.linesRecorded=null;
	}
	public void recordLine(String line) throws Exception {
		if (linesRecorded==null) linesRecorded = JCollectionFactory.createList();
		linesRecorded.addElement(line);
	}
	
	protected boolean supportDocument() throws Exception {
		return this.getSupportedDocuments().containsKey(this.sPrinterDocType);
	}
	
	protected String getPrinterDocType() {
		return sPrinterDocType;
	}

	public long getPrinterDocNumber() {
		return lPrinterDocNumber;
	}
	
	public boolean checkNumeration() throws Exception {
		return false;
	}
	
	public static JMap<String, String> getSizeTypes() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(LETTER, "Letter");
		map.addElement(LETTER_LANDSCAPE, "Letter Landscape");
		map.addElement(A4, "A4");
		map.addElement(OFICIO, "Oficio");
		map.addElement(OFICIO_LANDSCAPE, "Oficio Landscape");
		map.addElement(SOBRE, "A5");
		return map;
	}
	
	public boolean isRequiereIdentFiscal() throws Exception {
		return false;
	}
	
}
