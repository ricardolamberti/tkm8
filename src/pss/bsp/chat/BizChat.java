package pss.bsp.chat;

import pss.bsp.chat.conversation.BizConversation;
import pss.common.agenda.evento.BizEvento;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizChat extends JRecord {

	
  public JString pCompany = new JString();

  public JString pResponse  = new JString();
  public JString pQuestion = new JString();
  public JBoolean pClearHistory = new JBoolean() {
  	public void preset() throws Exception {
  		if (isRawNull()) pClearHistory.setValue(true);
  	};
  };

  public JObjBDs<BizConversation> pConversation= new JObjBDs<BizConversation>();
  
  
  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public String getCompany() throws Exception {
  	return pCompany.getValue();
  }
  public String getQuestion() throws Exception {
  	return pQuestion.getValue();
  }
  public String getResponse() throws Exception {
  	return pResponse.getValue();
  }
  public boolean getClearHistory() throws Exception {
  	return pClearHistory.getValue();
  }
  public JRecords<BizConversation> getConversation() throws Exception {
  	if (pConversation.isRawNull()) {
  		JRecords<BizConversation> convs = new JRecords<BizConversation>(BizConversation.class);
  		convs.setStatic(true);
  		pConversation.setValue(convs);
  	}
  	return pConversation.getValue();
  }
  public void setCompany(String company) throws Exception {
  	pCompany.setValue(company);
  }
  public void setQuestion(String question) throws Exception {
  	pQuestion.setValue(question);
  }
  public void setResponse(String response) throws Exception {
  	pResponse.setValue(response);
  }
  public void setClearHistory(boolean clearHistory) throws Exception {
  	pClearHistory.setValue(clearHistory);
  }
  /**
   * Constructor de la Clase
   */
  public BizChat() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "question", pQuestion );
    this.addItem( "respuesta", pResponse );
    this.addItem( "conversation", pConversation);
    this.addItem( "clear_history", pClearHistory);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "question", "question", true, false, 4000 );
    this.addFixedItem( FIELD, "respuesta", "response", true, false, 4000 );
    this.addFixedItem( FIELD, "conversation", "conversation", true, false, 4000 ).setClase(BizConversation.class);
    this.addFixedItem( FIELD, "clear_history", "clear history", true, false, 1 );
   }
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }
  public void limpiarConversation() throws Exception {
  	setClearHistory(true);
  	getConversation().getStaticItems().removeAllElements();
  }
  public void addConversation() throws Exception {
  	BizConversation conv = new BizConversation();
  	conv.setCompany(getCompany());
  	conv.setQuestion(getQuestion());
  	conv.setResponse(getResponse());
  	getConversation().addItem(conv,0);
  	setClearHistory(false);
  	setQuestion("");
  	setResponse("");
  	
  }
}