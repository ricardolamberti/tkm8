package  pss.bsp.config.airportGroups.detalle;

import pss.bsp.config.airportGroups.BizAirportGroup;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.tourism.airports.BizAirport;

public class BizAirportGroupDetail extends JRecord {

  private JString pCompany = new JString();
  private JLong pIdGroup = new JLong();
  private JString pAirport = new JString();
  private JString pDescrAirport = new JString() {
  	public void preset() throws Exception {
  		setValue(getDescrAirport());
  	};
  };


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdGroup(long zValue) throws Exception {    pIdGroup.setValue(zValue);  }
  public long getIdGroup() throws Exception {     return pIdGroup.getValue();  }
  public void setAirport(String zValue) throws Exception {    pAirport.setValue(zValue);  }
  public String getAirport() throws Exception {     return pAirport.getValue();  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizAirportGroupDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id_group", pIdGroup );
    this.addItem( "airport", pAirport);
    this.addItem( "descr_airport", pDescrAirport );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_group", "Grupo Aeropuerto", true, false, 18 );
    this.addFixedItem( KEY, "airport", "Aeropuerto", true, true, 10 );
    this.addFixedItem( FIELD, "company", "Compañia", true, true, 15 );
    this.addFixedItem( VIRTUAL, "descr_airport", "Aeropuerto", true, true, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_AIRPORT_GROUP_DETAIL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.config.airportGroups.detalle.GuiAirportGroupDetails");
  
  	JRelation r;
  	r = rels.addRelationForce(10, "restriccion usuario");
   	r.addFilter(" (bsp_airport_group_detail.company in (COMPANY_CUSTOMLIST)) ");

  	r=rels.addRelationParent(70, "Grupo", BizAirportGroup.class, "id_group", "id_group");
  	r.addJoin("BSP_AIRPORT_GROUP_DETAIL.id_group", "airport_group.id_group");
  	r.setAlias("airport_group");

  	//rels.hideField("company");
  	
	}

  BizAirport airport;
  public BizAirport getObjAirport() throws Exception {
  	BizAirport c = new BizAirport();
  	c.Read(this.getAirport());
  	return (airport=c);
  }
  
  public String getDescrAirport() throws Exception {
  	return this.getObjAirport().getDescription();
  }
  
  public boolean read( String company, long zId , String airport) throws Exception { 
  	addFilter( "company",  company); 
  	addFilter( "id_group",  zId ); 
  	addFilter( "airport",  airport ); 
    return read(); 
  }

  
}
