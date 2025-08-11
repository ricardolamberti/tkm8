package pss.common.terminals.messages.requires.cashDrawer;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JCashDrawerInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.requires.JRequire;

public class JCashDrawerOpen extends JRequire {

  public JCashDrawerOpen() {
  }
    
  @Override
	public Answer execute(JTerminal terminal) throws Exception {
  	JCashDrawerInterface cashDrawer = (JCashDrawerInterface)terminal;
  	return cashDrawer.openCashDrawer();
  }
  
}
