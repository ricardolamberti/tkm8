package pss.common.terminals.messages.requires.print;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.requires.JRequire;
import pss.core.services.fields.JInteger;
import pss.core.tools.JExcepcion;

public class JPrintClose extends JRequire {

	public static final int SHIFT = 1;
	public static final int DAY = 2;
	
	private JInteger pType= new JInteger(SHIFT);
	
	public JPrintClose() {
	}
	
  public JPrintClose(int type) {
  	this.pType.setValue(type);
  }
  
  @Override
	public void loadFieldMap() throws Exception {
  	super.loadFieldMap();
  	this.addValue("type", pType);
  }
  
  
  public int getType() throws Exception {
  	return this.pType.getValue();
  }
  
  
  @Override
	public Answer execute(JTerminal terminal) throws Exception {
		JPrinterInterface printer = (JPrinterInterface)terminal;
		if (pType.getValue()==SHIFT) return printer.closeShift();
		if (pType.getValue()==DAY) return printer.closeDay();
		JExcepcion.SendError("Tipo inválido");
		return null;
  }
  
}
