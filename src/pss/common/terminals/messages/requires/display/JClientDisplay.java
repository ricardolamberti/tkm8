package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JClientDisplayInterface;
import pss.common.terminals.messages.answer.Answer;

public class JClientDisplay extends JInformation {
	
	
  public JClientDisplay() {
  }
  

  @Override
	public Answer execute(JTerminal terminal) throws Exception {
  	JClientDisplayInterface clientDisplay = (JClientDisplayInterface) terminal;
  	return clientDisplay.clientDisplay(this);
  }

//--

}
