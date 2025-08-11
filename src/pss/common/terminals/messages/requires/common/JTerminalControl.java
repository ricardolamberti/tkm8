package pss.common.terminals.messages.requires.common;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.common.terminals.messages.requires.JRequire;
import pss.core.services.fields.JString;
import pss.core.tools.JExcepcion;

public class JTerminalControl extends JRequire {

	public static final String OPEN = "OPEN";
	public static final String CLOSE = "CLOSE";
	
	private JString pOperation = new JString();
	
  public JTerminalControl(String oper) {
  	this.pOperation.setValue(oper);
  }
  public JTerminalControl() {
  }
  
  @Override
	public void loadFieldMap() throws Exception {
  	super.loadFieldMap();
  	this.addValue("operation", pOperation);
  }

  
  public void setOperation(String value) {
  	this.pOperation.setValue(value);
  }
  
  @Override
	public Answer execute(JTerminal terminal) throws Exception {
		if (pOperation.getValue().equals(OPEN)) {
			terminal.open();
			return new AwrOk();
		}
		if (pOperation.getValue().equals(CLOSE)) {
			terminal.close();
			return new AwrOk();
		}
		JExcepcion.SendError("Operación inválida");
  	return null;
  }
  
}
