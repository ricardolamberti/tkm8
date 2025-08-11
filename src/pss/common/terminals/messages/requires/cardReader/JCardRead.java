package pss.common.terminals.messages.requires.cardReader;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JCardReadInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.requires.JRequire;

public class JCardRead extends JRequire {

	public JCardRead() {
	}
	
  @Override
	public Answer execute(JTerminal terminal) throws Exception {
  	JCardReadInterface cardReader = (JCardReadInterface)terminal;
  	return cardReader.readCard();
  }
  
}
