package pss.common.terminals.interfaces;

import pss.common.terminals.messages.answer.Answer;


public interface JClientKeyboarInterface {

	public Answer readInputByKey(int timeout) throws Exception;
	
}
