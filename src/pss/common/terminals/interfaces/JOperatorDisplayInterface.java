package pss.common.terminals.interfaces;

import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.requires.display.JMenu;
import pss.common.terminals.messages.requires.display.JPage;


public interface JOperatorDisplayInterface {

	public Answer clearOperatorDisplay() throws Exception;
	public Answer operatorDisplay(JPage page) throws Exception;
	public int getlineLenOperatorDisplay() throws Exception;
	public int getLinesOperatorDisplay() throws Exception;
	public Answer displayMenu(JMenu menu) throws Exception;
	
}
