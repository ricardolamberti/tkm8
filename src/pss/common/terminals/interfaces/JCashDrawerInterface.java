package pss.common.terminals.interfaces;

import pss.common.terminals.messages.answer.Answer;

public interface JCashDrawerInterface {

	 public Answer openCashDrawer() throws Exception;
	 public Answer isCashDrawerOpen() throws Exception;

}
