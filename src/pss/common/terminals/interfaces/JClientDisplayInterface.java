package pss.common.terminals.interfaces;

import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.requires.display.JClientDisplay;


public interface JClientDisplayInterface {

	public Answer clearClientDisplay() throws Exception;
	public Answer clientDisplay(JClientDisplay clientDisplay) throws Exception;
	public int getLineLenClientDisplay() throws Exception;
	public int getLinesClientDisplay() throws Exception;
	
}
