package pss.common.terminals.messages.requires.print;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pss.common.layout.JFieldInterface;
import pss.common.layout.JFieldReq;
import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrPrintDoc;
import pss.common.terminals.messages.requires.JRequire;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.fields.JXMLString;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class JPrintDoc extends JRequire implements JFieldInterface {

	private JString pFont = new JString();
	private JInteger pFontSize = new JInteger();
	private JString pMode = new JString();
	private JString pSize = new JString();
	private JString pCustomSize = new JString();
	private JString pImageSize = new JString();
	private JFloat pLineSpacing = new JFloat();
	private JString pBackground = new JString();
	private JString pPrinterDocType = new JString();
	private JLong pDocNumber = new JLong();
	private JInteger pMaxItems = new JInteger();
	private JInteger pCopias = new JInteger();
	private JXMLString pXml = new JXMLString();

	private NodeList sections = null;
	
  public JPrintDoc() {
  }
  
  @Override
	public void loadFieldMap() throws Exception {
  	super.loadFieldMap();
  	this.addValue("font", pFont);
  	this.addValue("font_size", pFontSize);
  	this.addValue("mode", pMode);
  	this.addValue("size", pSize);
  	this.addValue("custom_size", pCustomSize);
  	this.addValue("line_spacing", pLineSpacing);
  	this.addValue("printer_doc_type", pPrinterDocType);
  	this.addValue("background", pBackground);
  	this.addValue("max_items", pMaxItems);
  	this.addValue("doc_number", pDocNumber);
  	this.addValue("xml", pXml);
  }
  
  @Override
	public Answer execute(JTerminal terminal) throws Exception {
  	return this.printLines(terminal);
  }
  
  public Element getXML() throws Exception {
  	return this.pXml.getValue();
  }
  public void setXML(String value) throws Exception {
  	this.pXml.setValue(value);
  }
  public void setMode(String value) {
  	this.pMode.setValue(value);
  }
  public void setSize(String value) {
  	this.pSize.setValue(value);
  }
  public void setCustomSize(String value) {
  	this.pCustomSize.setValue(value);
  }
  public void setImageSize(String value) {
  	this.pImageSize.setValue(value);
  }
  public void setFont(String value) {
  	this.pFont.setValue(value);
  }
  public void setFontSize(int value) {
  	this.pFontSize.setValue(value);
  }
  public void setCopias(int value) {
  	this.pCopias.setValue(value);
  }
  public void setLineSpacing(double value) {
  	this.pLineSpacing.setValue(value);
  }
  public void setPrinterDocType(String value) {
  	this.pPrinterDocType.setValue(value);
  }
  public void setDocNumber(long value) {
  	this.pDocNumber.setValue(value);
  }
  public int getMaxItems() throws Exception {
  	return this.pMaxItems.getValue();
  }
  public void setBackground(String value) {
  	this.pBackground.setValue(value);
  }
  
  private Answer printLines(JTerminal terminal) throws Exception {
  	JPrinterAdapter printer = terminal.getPrintAdapter();
  	try {
  		Element element = pXml.getValue();
  		printer.setSize(this.pSize.getValue());
  		printer.setCustomSize(this.pCustomSize.getValue());
  		printer.setImageSize(this.pImageSize.getValue());
  		printer.setModalidad(this.pMode.getValue());
  		printer.setFuente(this.pFont.getValue());
  		printer.setFontSize(this.pFontSize.getValue());
  		printer.setLineSpacing(this.pLineSpacing.getValue());
  		printer.setBackground(this.pBackground.getValue());
  		printer.setPrinterDocType(this.pPrinterDocType.getValue());
  		this.verifyNumeration(printer);
  		printer.setFieldInterface(this);
    	this.sections = element.getElementsByTagName("section");
    	printer.openDocument();
    	int len = sections.getLength();
	  	for(int i=0; i<len;i++) {
	  		Element section = (Element)sections.item(i);
	  		printer.print(section.getAttribute("id"),  String.valueOf(i));
	  	}
	  	printer.flush();
	  	printer.closeDocument();
	
	  	if (printer.checkDocOpen())
	  		JExcepcion.SendError("Error en Documento");
	  	
	  	return new AwrPrintDoc(true, printer.getPrinterDocNumber(), null);
	  	
  	} catch (Exception e) {
  		PssLogger.logError(e);
  		AwrPrintDoc awr = new AwrPrintDoc(printer.checkDocOpen(), printer.getPrinterDocNumber(), this.getMessageError(e));
  		printer.cancelDocument();
  		return awr;
  		//throw e;
    }
  }
  
	
	private String getMessageError(Exception e) throws Exception {
		if (e.getMessage()==null) return "Error puntero nulo";
		return e.getMessage();
	}
	
	private void verifyNumeration(JPrinterAdapter printer) throws Exception {
  	if (!printer.checkNumeration()) return;
  	long curr = printer.getCurrentNum(); 
  	if (this.pDocNumber.getValue()==0L) {
  		this.pDocNumber.setValue(curr);
  	}
		if (curr!=this.pDocNumber.getValue()) 
			JExcepcion.SendError("Desincronización del número de comprobante ["+curr+"]");
		printer.setPrinterDocNumber(curr);
  }

  public Object getField(JFieldReq req) throws Exception {
  	int i = Integer.parseInt((String)req.getSource1());
  	Element element = (Element)this.sections.item(i);
  	String value = element.getAttribute(req.id);
  	return value;
//  	String type = element.getAttribute("type_"+req.id);
//  	if (type.equals("S")) return value;
//  	if (type.equals("L")) return new Long(value);
//  	if (type.equals("I")) return new Integer(value);
//  	if (type.equals("F")) return new Double(value);
//  	if (type.equals("D")) return new Date(value);
//  	if (type.equals("B")) return new Boolean(value);
//  	return null;
  }

}
