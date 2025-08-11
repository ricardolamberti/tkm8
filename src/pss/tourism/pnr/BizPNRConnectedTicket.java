package pss.tourism.pnr;

import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPNRConnectedTicket extends JRecord {
  private JString pTicket = new JString();
  private JString pConnectedTicket = new JString();

	protected JString pCompany=new JString();


	public void setCompany(String zValue)  {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setTicket(String zValue) throws Exception {    pTicket.setValue(zValue);  }
  public String getTicket() throws Exception {     return pTicket.getValue();  }
  public void setConnectedTicket(String zValue) throws Exception {    pConnectedTicket.setValue(zValue);  }
  public String getConnectedTicket() throws Exception {     return pConnectedTicket.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizPNRConnectedTicket() throws Exception {
    this.addItem( "numeroboleto", pTicket );
    this.addItem( "NUMEROBOLETOCONECTADO", pConnectedTicket );
		addItem("company", pCompany);
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "empresa", true, false, 50);
    this.addFixedItem( KEY, "numeroboleto", "numeroboleto", true, true, 50 );
    this.addFixedItem( KEY, "NUMEROBOLETOCONECTADO", "NUMEROBOLETOCONECTADO", true, true, 50 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "TUR_PNR_BOLETOSCONECTADOS"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.tourism.pnr.GuiPNRCnnectedTickets");
  }

  /**
   * Default Read() method
   */
  public boolean read( String company, String ticket, String ticketconectado ) throws Exception { 
    clearFilters(); 
    addFilter( "company",  company ); 
    addFilter( "numeroboleto",  ticket ); 
    addFilter( "numeroboletoconectado",  ticketconectado ); 
    return read(); 
  } 
}
