package pss.common.terminals.messages.requires.print;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.requires.JRequire;
import pss.core.services.fields.JString;

public class JPrintCurrentNum extends JRequire {
	public JString pTipoDocumento = new JString("");
	
  
  @Override
	public void loadFieldMap() throws Exception {
  	super.loadFieldMap();
  	this.addValue("tipoDocumento", pTipoDocumento);
  }
  
  

  public JPrintCurrentNum() {
  }
    
  
  @Override
	public Answer execute(JTerminal terminal) throws Exception {
		return this.getCurrentNum(terminal);
  }
  
  private Answer getCurrentNum(JTerminal terminal) throws Exception {
  	terminal.getPrintAdapter().setPrinterDocType(pTipoDocumento.getValue());
//  	return new AwrPrintDoc(false, terminal.getPrintAdapter().getCurrentNum(pTipoDocumento.getValue()));
  	return null;
  }


	
	public String getTipoDocumento() throws Exception {
		return pTipoDocumento.getValue();
	}


	
	public void setTipoDocumento(String tipoDocumento) {
		this.pTipoDocumento.setValue(tipoDocumento);
	}
  
  
  
}
