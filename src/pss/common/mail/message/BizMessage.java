package pss.common.mail.message;

import java.util.Date;

import org.apache.commons.codec.net.URLCodec;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizMessage extends JRecord {

  private JLong pIdmessage = new JLong();
  private JString pSender = new JString();
  private JDateTime pDateCreation = new JDateTime(true);
  private JString pTitle = new JString();
  private JHtml pMessage = new JHtml();
  private JLong pPriority = new JLong();
  private JBoolean pEmergency = new JBoolean();
  private JBoolean pUrgent = new JBoolean();
  private JString company = new JString();
  
  private JString pSenderDesc = new JString();
//		@Override
//		public void preset() throws Exception {
//			if (pSenderDesc.getRawValue().equals("")) {
//				BizUsuario usr = new BizUsuario();
//				usr.dontThrowException(true);
//				if ( usr.Read(pSender.getValue()) )
//				  pSenderDesc.setValue(usr.getDescrUsuario());
//				else
//					pSenderDesc.setValue(pSender.getValue());
//			}
//		}
//	};


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void setCompany(String zValue) throws Exception {    company.setValue(zValue);  }
  public String getCompany() throws Exception {     return company.getValue();  }


  public void setIdmessage(long zValue) throws Exception {    pIdmessage.setValue(zValue);  }
  public long getIdmessage() throws Exception {     return pIdmessage.getValue();  }
  public boolean isNullIdmessage() throws Exception { return  pIdmessage.isNull(); } 
  public void setNullToIdmessage() throws Exception {  pIdmessage.setNull(); } 
  public void setSender(String zValue) throws Exception {    pSender.setValue(zValue);  }
  public String getSender() throws Exception {     return pSender.getValue();  }
  public void setSenderDesc(String zValue) throws Exception {    pSenderDesc.setValue(zValue);  }
  public String getSenderDesc() throws Exception {     return pSenderDesc.getValue();  }
  public boolean isNullSender() throws Exception { return  pSender.isNull(); } 
  public void setNullToSender() throws Exception {  pSender.setNull(); } 
  public void setDateCreation(Date zValue) throws Exception {    pDateCreation.setValue(zValue);  }
  public Date getDateCreation() throws Exception {     return pDateCreation.getValue();  }
  public boolean isNullDateCreation() throws Exception { return  pDateCreation.isNull(); } 
  public void setNullToDateCreation() throws Exception {  pDateCreation.setNull(); } 
  public void setTitle(String zValue) throws Exception {    pTitle.setValue(zValue);  }
  public String getTitle() throws Exception {     return pTitle.getValue();  }
  public boolean isNullTitle() throws Exception { return  pTitle.isNull(); } 
  public void setNullToTitle() throws Exception {  pTitle.setNull(); } 
  public void setMessage(String zValue) throws Exception {    pMessage.setValue(zValue);  }
  public String getMessage() throws Exception {     return pMessage.getValue();  }
  public boolean isNullMessage() throws Exception { return  pMessage.isNull(); } 
  public void setNullToMessage() throws Exception {  pMessage.setNull(); } 
  public void setPriority(long zValue) throws Exception {    pPriority.setValue(zValue);  }
  public long getPriority() throws Exception {     return pPriority.getValue();  }
  public boolean isEmergency() throws Exception {     return pEmergency.getValue();  }
  public void setEmergency(boolean val) { pEmergency.setValue(val); }
  public boolean isUrgent() throws Exception {     return pUrgent.getValue();  }
  public void setUrgent(boolean val) { pUrgent.setValue(val); }
  public String getMessageStrip() throws Exception {     return (new URLCodec()).decode(pMessage.getValue());  }

  public String getUrgentMessage() throws Exception {     return getTitle()+": "+getMessage();  }

  /**
   * Constructor de la Clase
   */
  public BizMessage() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_message", pIdmessage );
    this.addItem( "sender", pSender );
    this.addItem( "date_creation", pDateCreation );
    this.addItem( "title", pTitle );
    this.addItem( "priority", pPriority );
    this.addItem( "emergency", pEmergency);
    this.addItem( "urgent", pUrgent);
    this.addItem( "message", pMessage );
    this.addItem( "sender_desc", pSenderDesc );
    this.addItem( "company", company );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_message", "Id mensaje", false, false, 18 );
    this.addFixedItem( FIELD, "sender", "Emisor", true, false, 15 );
       this.addFixedItem( FIELD, "company", "Empresa", true, false, 50 );
    this.addFixedItem( FIELD, "date_creation", "Fecha Envio", true, true, 10 );
    this.addFixedItem( FIELD, "title", "Titulo", true, true, 250 );
    this.addFixedItem( FIELD, "priority", "Prioridad", true, true, 250 );
    this.addFixedItem( FIELD, "emergency", "Emergencia", true, false, 1 );
    this.addFixedItem( FIELD, "urgent", "Urgente", true, false, 1 );
    this.addFixedItem( FIELD, "message", "Mensaje", true, false, 1000 );
    this.addFixedItem( VIRTUAL, "sender_desc", "Emisor", true, false, 15 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "MSG_MESSAGE"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zIdmessage ) throws Exception { 
    this.addFilter( "id_message",  zIdmessage ); 
    return read(); 
  }

  public void processInsert() throws Exception {
//  	if (this.pIdmessage.isNull()) {
//  		BizMessage max = new BizMessage();
//			this.setIdmessage(max.SelectMaxLong("id_message")+1);
//  	}
  	super.processInsert();
  	this.setIdmessage(this.getIdentity("id_message"));
  }

}
