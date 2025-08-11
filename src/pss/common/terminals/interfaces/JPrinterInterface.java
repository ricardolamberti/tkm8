package pss.common.terminals.interfaces;

import pss.common.terminals.messages.answer.Answer;

public interface JPrinterInterface {

	public Answer printLine(String line) throws Exception;
	public Answer closeShift() throws Exception;
	public Answer closeDay() throws Exception;
	
	public Answer cancelDoc() throws Exception;
	public Answer openDoc() throws Exception;
	public Answer closeDoc() throws Exception;
	public Answer flush() throws Exception;
	public Answer skeepLines(int lines) throws Exception;

	
}
