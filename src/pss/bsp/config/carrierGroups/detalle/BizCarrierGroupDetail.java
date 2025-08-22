package  pss.bsp.config.carrierGroups.detalle;

import pss.bsp.config.carrierGroups.BizCarrierGroup;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.tourism.carrier.BizCarrier;

public class BizCarrierGroupDetail extends JRecord {

  private JString pCompany = new JString();
  private JLong pIdGroup = new JLong();
  private JString pCarrier = new JString();
  private JString pDescrCarrier = new JString() {
  	public void preset() throws Exception {
  		setValue(getDescrCarrier());
  	};
  };


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdGroup(long zValue) throws Exception {    pIdGroup.setValue(zValue);  }
  public long getIdGroup() throws Exception {     return pIdGroup.getValue();  }
  public void setCarrier(String zValue) throws Exception {    pCarrier.setValue(zValue);  }
  public String getCarrier() throws Exception {     return pCarrier.getValue();  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizCarrierGroupDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id_group", pIdGroup );
    this.addItem( "carrier", pCarrier);
    this.addItem( "descr_carrier", pDescrCarrier );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_group", "Grupo Aerolineas", true, false, 18 );
    this.addFixedItem( KEY, "carrier", "Aerolinea", true, true, 2 );
    this.addFixedItem( FIELD, "company", "Compañia", true, true, 15 );
    this.addFixedItem( VIRTUAL, "descr_carrier", "Aerolinea", true, true, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_CARRIER_GROUP_DETAIL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.config.carrierGroups.detalle.GuiCarrierGroupDetails");
  
  	JRelation r;
  	r = rels.addRelationForce(10, "restriccion usuario");
   	r.addFilter(" (bsp_carrier_group_detail.company in (COMPANY_CUSTOMLIST)) ");

  	r=rels.addRelationParent(70, "Grupo", BizCarrierGroup.class, "id_group", "id_group");
  r.setTypeJoin(JRelations.JOIN_LEFT);
  	r.addJoin("BSP_CARRIER_GROUP_DETAIL.id_group", "carrier_group.id_group");
  	r.setAlias("carrier_group");

  	//rels.hideField("company");
  	
	}

  BizCarrier carrier;
  public BizCarrier getObjCarrier() throws Exception {
  	BizCarrier c = new BizCarrier();
  	c.Read(this.getCarrier());
  	return (carrier=c);
  }
  
  public String getDescrCarrier() throws Exception {
  	return this.getObjCarrier().getDescription();
  }

  public boolean read( String company, long zId , String carrier) throws Exception { 
  	addFilter( "company",  company); 
  	addFilter( "id_group",  zId ); 
  	addFilter( "carrier",  carrier ); 
    return read(); 
  }
}
