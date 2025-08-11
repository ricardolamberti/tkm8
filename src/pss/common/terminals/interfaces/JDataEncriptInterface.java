package pss.common.terminals.interfaces;

import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.requires.cripto.JDes;

public interface JDataEncriptInterface {
	
	public Answer dataEncrypt(JDes des) throws Exception;

}
