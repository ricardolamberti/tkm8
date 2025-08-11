package  pss.common.event2.mail;

import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizMailConf extends JRecord {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  private JLong pId = new JLong();
  private JString pCompany = new JString();
  private JString pDescription = new JString();
  private JString pTransport = new JString();
  private JString pSmtpServer = new JString();
  private JLong pSmtpPort = new JLong();
  private JString pMailFormat = new JString();

  public void   setId(long zVal ) throws Exception { pId.setValue( zVal ); }
  public long getId() throws Exception { return pId.getValue(); }
  public void   setDescription(String zVal ) throws Exception { pDescription.setValue( zVal ); }
  public String getDescription() throws Exception { return pDescription.getValue(); }
  public void   setCompany(String zVal ) throws Exception { pCompany.setValue( zVal ); }
  public String getCompany() throws Exception { return pCompany.getValue(); }
  public void   setTransport(String zVal ) throws Exception { pTransport.setValue( zVal ); }
  public String getTransport() throws Exception { return pTransport.getValue(); }
  public void   setSmtpServer(String zVal ) throws Exception { pSmtpServer.setValue( zVal ); }
  public String getSmtpServer() throws Exception { return pSmtpServer.getValue(); }
  public void   setSmtpPort(long zVal ) throws Exception { pSmtpPort.setValue( zVal ); }
  public long getSmtpPort() throws Exception { return pSmtpPort.getValue(); }
  public void   setMailFormat(String zVal ) throws Exception { pMailFormat.setValue( zVal ); }
  public String getMailFormat() throws Exception { return pMailFormat.getValue(); }

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizMailConf() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "ID", pId );
    addItem( "company", pCompany );
    addItem( "DESCRIPTION", pDescription );
    addItem( "TRANSPORT", pTransport );
    addItem( "SMTP_SERVER", pSmtpServer );
    addItem( "SMTP_PORT", pSmtpPort );
    addItem( "MAIL_FORMAT", pMailFormat );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "ID", "Id", false, false, 18, 0 );
    addFixedItem( FIELD, "COMPANY", "Empresa", true, false, 50 );
    addFixedItem( FIELD, "DESCRIPTION", "Descripción", true, true, 50 );
    addFixedItem( FIELD, "TRANSPORT", "Transporte", true, true, 50 );
    addFixedItem( FIELD, "SMTP_SERVER", "Smtp Servidor", true, true, 50 );
    addFixedItem( FIELD, "SMTP_PORT", "Smtp Puerto", true, true, 6 );
    addFixedItem( FIELD, "MAIL_FORMAT", "Formato Mail", true, false, 50 );
  }

  @Override
	public String GetTable() {  return "MAIL_CONF";  }

  public boolean read ( int iID ) throws Exception {
    clearFilters();
    addFilter("ID", iID);
    return read();
  }
  
	public void validateConstraints() throws Exception {
		if ( this.pCompany.isNull() ) {
			if (BizUsuario.getUsr()==null)
				this.pCompany.setValue(BizCompany.DEFAULT_COMPANY);
			else
				this.pCompany.setValue(BizUsuario.getUsr().getCompany());
		}
	}

	public boolean isSender() throws Exception{
		return getTransport().toLowerCase().indexOf("smtp")!=-1;
	}
	public boolean isReceiver() throws Exception{
		return !isSender();
	}

}
