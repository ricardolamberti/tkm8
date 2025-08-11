package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JOperatorDisplayInterface;
import pss.common.terminals.messages.answer.Answer;

public class JOperDisplay extends JInformation {
	
  public JOperDisplay() {
  }

  @Override
	public Answer execute(JTerminal terminal) throws Exception {
  	JOperatorDisplayInterface operDisplay = (JOperatorDisplayInterface) terminal;
    return operDisplay.operatorDisplay(this);
  }

//--

}
