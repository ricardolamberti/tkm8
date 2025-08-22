package pss.bsp.chat.conversation;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;

public class BizConversation extends JRecord {

	
  public JString pCompany = new JString();

  public JString pResponse  = new JString();
  public JString pQuestion = new JString();
  public JString pDialog = new JString() {
  	public void preset() throws Exception {
  		pDialog.setValue(getQuestion()+"->"+getResponse());
  	};
  };

  public JString pResponseHtml = new JString() {
  	public void preset() throws Exception {
  		pResponseHtml.setValue(getResponseHtml());
  	};
  };

  
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
  public String getResponseHtml() throws Exception {
  	String orig = getResponse();
  	String newResp = "<html><body><div style=\"padding: 10px;\">"+orig+"</div></body></html>";
 		newResp = JTools.replace(newResp,"```sql", "<div style=\"background: black;color: white;width: 600px;border-radius: 10px;margin: 10px;padding: 10px;    font-family: sans-serif; font-size: larger;\"><code>");
 		newResp = JTools.replace(newResp,"```", "</code></div>");
 		newResp = JTools.replace(newResp,"\n", "<br>");
 		newResp = JTools.replace(newResp,"\r", "<br>");

 		return newResp;
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
  
  /**
   * Constructor de la Clase
   */
  public BizConversation() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "question", pQuestion );
    this.addItem( "respuesta", pResponse );
    this.addItem( "respuesta_html", pResponseHtml);
    this.addItem( "dialog", pDialog );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "question", "question", true, false, 4000 );
    this.addFixedItem( FIELD, "respuesta", "response", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "respuesta_html", "respuesta_html", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "dialog", "dialog", true, false, 4000 );
   }
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }

}