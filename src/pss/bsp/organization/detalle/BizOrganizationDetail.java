package  pss.bsp.organization.detalle;

import java.util.StringTokenizer;

import pss.bsp.organization.BizOrganization;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizOrganizationDetail extends JRecord {

  private JLong pId = new JLong();
  private JLong pIdOrganization = new JLong();
  private JString pCompany = new JString();
  private JString pIata = new JString();
 

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setIdOrganization(long zValue) throws Exception {    pIdOrganization.setValue(zValue);  }
  public long getIdOrganization() throws Exception {     return pIdOrganization.getValue();  }
  public boolean isNullIdOrganization() throws Exception { return  pIdOrganization.isNull(); } 
  public void setNullToIdOrganization() throws Exception {  pIdOrganization.setNull(); } 
  public void setIata(String zValue) throws Exception {    pIata.setValue(zValue);  }
  public String getIata() throws Exception {     return pIata.getValue();  }
  public boolean isNullIata() throws Exception { return  pIata.isNull(); } 
  public void setNullToIata() throws Exception {  pIata.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 

  /**
   * Constructor de la Clase
   */
  public BizOrganizationDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "id_org", pIdOrganization );
    this.addItem( "company", pCompany );
    this.addItem( "iata", pIata );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "id", false, false, 18 );
    this.addFixedItem( KEY, "id_org", "Organization id", true, true, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50);
    this.addFixedItem( FIELD, "iata", "Iata", true, true, 4000 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_ORGANIZATION_DETAIL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.regions.detalle.GuiOrganizationDetails");
  
  	JRelation r=rels.addRelationParent(49, "Organization", BizOrganization.class, "id_org", "id");
  	r.addJoin("BSP_ORGANIZATION_DETAIL.id_org", "BSP_ORGANIZATION.id");
  	r.addJoin("BSP_ORGANIZATION_DETAIL.company", "BSP_ORGANIZATION.company");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);
		super.attachRelationMap(rels);
	}
  /**
   * Default read() method
   */
  
  public boolean read( String company,long idOrganization,long idIata ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "id",  idIata ); 
    addFilter( "id_org",  idOrganization ); 
    return read(); 
  } 
  public boolean read( String company,long idOrganization,String iata ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "iata",  iata ); 
    addFilter( "id_org",  idOrganization ); 
    return read(); 
  } 

	@Override
	public void processInsert() throws Exception {
		if (getIata().indexOf(",")!=-1) {
			StringTokenizer iatas = new StringTokenizer(getIata(), ",");
			while (iatas.hasMoreTokens()) {
				String iata = iatas.nextToken();
				BizOrganizationDetail detail = new BizOrganizationDetail();
				detail.dontThrowException(true);
				if (detail.read(getCompany(), getIdOrganization(), getIata())) 
					continue;
				detail.setCompany(getCompany());
				detail.setIata(iata.trim());
				detail.setIdOrganization(getIdOrganization());
				detail.processInsert();
			}
			return;
		}

		
		
		super.processInsert();
	}
  
}
