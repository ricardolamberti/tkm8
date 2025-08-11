package pss.common.terminals.messages.answer;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JString;


public class AwrPrintDoc extends Answer {

	private JBoolean pWasOpen = new JBoolean();
	private JString pDocumentNumber = new JString();
	private JString pMessageError = new JString();
	
	public AwrPrintDoc() {
	}
  public AwrPrintDoc(boolean wasOpen, long docNum, String msg) {
  	this.pWasOpen.setValue(wasOpen);
  	this.pDocumentNumber.setValue(""+docNum);
  	this.pMessageError.setValue(msg);
  }
  
  @Override
	public void loadFieldMap() throws Exception {
  	this.addValue("doc_was_open", pWasOpen);
  	this.addValue("document_number", pDocumentNumber);
  	this.addValue("message_error", pMessageError);
  }
  
  public boolean isDocWasOpen() throws Exception {
  	return this.pWasOpen.getValue();
  }
  public long getDocumentNumber() throws Exception {
  	return Long.parseLong(this.pDocumentNumber.getValue());
  }

  public boolean hasDocumentNumber() throws Exception {
  	return !this.pDocumentNumber.isEmpty() && this.getDocumentNumber()!=0L;
  }

  public boolean hasMessageError() throws Exception {
  	return !this.pMessageError.isEmpty();
  }

  public String getMessageError() throws Exception {
  	return this.pMessageError.getValue();
  }

  @Override
	public boolean matches( Answer zEvent ) {
  	return false;
  }

}
