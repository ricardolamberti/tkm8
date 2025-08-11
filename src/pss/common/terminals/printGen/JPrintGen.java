package pss.common.terminals.printGen;

import pss.common.layout.BizLayout;
import pss.common.layout.BizLayoutCampo;
import pss.common.layout.JFieldInterface;
import pss.common.terminals.config.BizTerminal;
import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.common.terminals.messages.answer.AwrPrintDoc;
import pss.common.terminals.messages.requires.print.JPrintCurrentNum;
import pss.common.terminals.messages.requires.print.JPrintDoc;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JStringTokenizer;

public class JPrintGen {
	
	private BizTerminal terminal=null;
	private BizLayout layout=null;
	private JFieldInterface fieldInterface=null;
	private String sDocType=null;
	private String sMode=null;
	private String sSize=null;
	private String sCustomSize=null;
	private String sImageSize=null;
	private String sFont=null;
	private int iFontSize=12;
	private int iCopias=1;
	private long lDocNumber=0L;
	private boolean bDirect=false;
  private JPrinterAdapter printAdapter = null;
  private boolean bSupportDoc = false;

	
	public JPrintGen()  {
	}
	
	public void setLayout(BizLayout value) {
		this.layout=value;
	}

	public void setDirect(boolean value) {
		this.bDirect=value;
	}
	public void setTerminal(BizTerminal value) {
		this.terminal=value;
	}
	public void setMode(String value) {
		this.sMode=value;
	}
	public void setSize(String value) {
		this.sSize=value;
	}
	public void setCustomSize(String value) {
		this.sCustomSize=value;
	}
	public void setImageSize(String value) {
		this.sImageSize=value;
	}
	public void setDocType(String value) {
		this.sDocType=value;
	}
	public void setSupportDoc(boolean value) {
		this.bSupportDoc=value;
	}
	public void setDocNumber(long value) {
		this.lDocNumber=value;
	}
	public void setFont(String value) {
		this.sFont=value;
	}
	public void setFontSize(int value) {
		this.iFontSize=value;
	}
	public void setCopias(int value) {
		this.iCopias=value;
	}
	
	public void setFieldInterface(JFieldInterface value) {
		this.fieldInterface=value;
	}

	public void setPageSector(String header, Object source) throws Exception {
		this.setPageSector(header, null, source);
	}
	public void setPageSector(String header, String footer, Object source) throws Exception {
		if (this.supportDocument()) return;
		this.layout.setPageSector(header, footer, source);
	}
	
	
	public BizLayout getLayout() {
		return this.layout;
	}
	
	
	public void startRecorder() throws Exception {
		this.getPrinterAdapter().startRecord();
		if (this.layout!=null) this.layout.startRecord();
		if (this.bDirect) this.getPrinterAdapter().openDocument();
	}

	public void endRecorder() throws Exception {
		if (this.bDirect) this.getPrinterAdapter().closeDocument();
	}

	public String preview() throws Exception {
		return getLayout().getLinesRecordedString();
	}
	
	public boolean hasSector(String sector) throws Exception {
		if (!this.hasLayout()) return false;
		JIterator<BizLayoutCampo> iter = this.getLayout().getObjCampos().getStaticIterator();
		while(iter.hasMoreElements()) {
			BizLayoutCampo c = iter.nextElement();
			if (c.getSector().equals(sector)) 
				return true;
		}
		return false;
	}
	
	public void generate(String sector, Object source) throws Exception {
		this.getPrinterAdapter().setFieldInterface(this.fieldInterface);
		if (this.supportDocument()) {
			this.getPrinterAdapter().generate(sector, source);
		  this.layout=null;
		} else {
			if (layout==null) JExcepcion.SendError("No exite layout definido");
			this.layout.setFieldInterface(this.fieldInterface);
			this.layout.generate(sector, source);
		}
		this.printDirect();
	}
	
	private void printDirect() throws Exception {
		if (!this.bDirect) return;
		if (this.supportDocument()) {
			return;
		} else {
			JPrinterAdapter p = this.getPrinterAdapter();
			JIterator<String> iter = this.layout.getLinesRecorded().getIterator();
			while (iter.hasMoreElements()) {
				p.printLine(iter.nextElement());
			}
			this.layout.cleanRecords();
		}
	}
	
	public int getCopias() throws Exception {
		if (!this.hasLayout()) return 0;
		return this.getLayout().getCopias();
	}

	public int getCopiasXPag() throws Exception {
		if (!this.hasLayout()) return 1;
		int c = this.getLayout().getCopiasXPag();
		if (c==0) return 1;
		return c;
	}


	public void newPage() throws Exception {
		if (!this.hasLayout()) return;
		this.layout.setFieldInterface(this.fieldInterface);
		this.layout.newPage();
	}
	
	public void newHeader() throws Exception {
		if (!this.hasLayout()) return;
		this.layout.setFieldInterface(this.fieldInterface);
		this.layout.newHeader();
	}

	private double getLineSpacing() throws Exception {
		if (!this.hasLayout()) return 1;
		return this.layout.getLineSpacing();
	}
	private String getBackground() throws Exception {
		if (!this.hasLayout()) return "";
		return this.layout.getBackgroundFile();
	}
	
	public boolean hasLayout() {
		return this.layout!=null;
	}
	
	public Answer print() throws Exception {
		if (this.bDirect) {
			this.endRecorder();
			return new AwrOk();
		}
		JPrintDoc printDoc = new JPrintDoc();
		printDoc.setSize(this.sSize);
		printDoc.setCustomSize(this.sCustomSize);
		printDoc.setImageSize(this.sImageSize);
		printDoc.setMode(this.sMode);
		printDoc.setFont(this.sFont);
		printDoc.setFontSize(this.iFontSize);
		printDoc.setCopias(this.iCopias);
		printDoc.setLineSpacing(this.getLineSpacing());
		printDoc.setBackground(this.getBackground());
		printDoc.setPrinterDocType(this.getPrinterDocType());
		printDoc.setDocNumber(this.lDocNumber);
		String xml = (layout==null) ? this.getPrinterAdapter().getLinesRecordedAsXML() : layout.getLinesRecordedAsXML(); 
		printDoc.setXML(xml);
		return this.getTerminalPrinter().process(printDoc);
	}

	public long getCurrentNum() throws Exception {
		JPrintCurrentNum currNum = new JPrintCurrentNum();
		this.getPrinterAdapter().setPrinterDocType(this.getPrinterDocType());
		currNum.setTipoDocumento(this.getPrinterDocType());
		AwrPrintDoc answer = (AwrPrintDoc)this.getTerminalPrinter().process(currNum);
		return answer.getDocumentNumber();
	}
	
	public JPrinterAdapter getPrinterAdapter() throws Exception {
		if (this.printAdapter!=null) return this.printAdapter;
		JPrinterAdapter p = this.getTerminalPrinter().getPrintAdapter();
		p.setSize(this.sSize);
		p.setCustomSize(this.sCustomSize);
		p.setImageSize(this.sImageSize);
		p.setModalidad(this.sMode);
		p.setFuente(this.sFont);
		p.setFontSize(this.iFontSize);
		p.setCopias(this.iCopias);
		p.setLineSpacing(this.getLineSpacing());
		p.setBackground(this.getBackground());
		p.setPrinterDocType(this.getPrinterDocType());
		return (this.printAdapter=p);
	}
	
	public String getFileName() throws Exception {
		return this.getTerminalPrinter().getConnectinString();
	}
	public JTerminal getTerminalPrinter() throws Exception {
		return terminal.getTerminalPointer();
	}
	public BizTerminal getObjTerminal() throws Exception {
		return terminal;
	}
	
	private boolean supportDocument() throws Exception {
		if (this.sDocType!=null) return true;
		return this.bSupportDoc;
	}

	public String getResponIdFiscal(String idSystem) throws Exception {
		return this.getObjTerminal().getTerminal().getPrintAdapter().getResponIdFiscal(idSystem);
	}

	public String getTipoCuitFiscal(String idSystem) throws Exception {
		return this.getObjTerminal().getTerminal().getPrintAdapter().getTipoCuitFiscal(idSystem);
	}
	
	public String getTipoCuitPasaporte() throws Exception {
		return this.getObjTerminal().getTerminal().getPrintAdapter().getTipoCuitPasaporte();
	}

	public String getPrinterDocType() throws Exception {
//		if (this.supportDocument())
//			return this.getObjFiscalDocument().getDocumentoFiscal();
//		else
		return this.sDocType;
	}
	

	public void takeAttributesFrom(BizLayout layout) throws Exception {
		this.setSize(layout.getSize());
		this.setCustomSize(layout.getCustomSize());
		this.setImageSize(layout.getImageSize());
		this.setFont(layout.getFontType());
		this.setFontSize(layout.getFontSize());
		this.setCopias(layout.getCopias());
		this.setLayout(layout);
	}
//  private BizFiscalDocument getObjFiscalDocument() throws Exception {
//  	if (this.fiscalDoc!=null) return this.fiscalDoc;
//  	BizFiscalDocument record = new BizFiscalDocument();
//  	record.dontThrowException(true);
//  	record.Read(this.sCompany, this.sCountry, this.getTerminalPrinter().getType(), this.sDocType);
//  	return (this.fiscalDoc=record);
//  }

	public JList<String> getOrderSections() throws Exception {
		JList<String> list = JCollectionFactory.createList();
		JStringTokenizer tk = JCollectionFactory.createStringTokenizer(this.findOrderSections(), ';');
		while (tk.hasMoreTokens()) {
			String t= tk.nextToken();
			list.addElement(t);
		}
		return list;
	}

	public String findOrderSections() throws Exception {
		if (this.hasLayout()) {
			String orden = this.getLayout().getSectionOrden();
			if (!orden.isEmpty()) return orden;
		}
		return "T;B;P;"; // taxes, Body plus, taxes, 3ros, payments
	}

}
