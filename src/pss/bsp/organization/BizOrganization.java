package  pss.bsp.organization;

import java.io.File;

import pss.JPath;
import pss.bsp.organization.detalle.BizOrganizationDetail;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizOrganization extends JRecord {

  private JLong  pId = new JLong();
  private JString pCompany = new JString();
  private JString pCodeOrganization = new JString();
  private JString pOrganization = new JString();
	private JString pLogo=new JString();
  private JHtml pLogoIncr = new JHtml() {
  	public void preset() throws Exception {
  		setValue(JPath.PssPathLogos()+getLogo());
  	};
  };
  


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setOrganization(String zValue) throws Exception {    pOrganization.setValue(zValue);  }
  public String getOrganization() throws Exception {     return pOrganization.getValue();  }
  public boolean isNullOrganization() throws Exception { return  pOrganization.isNull(); } 
  public void setNullOrganization() throws Exception {  pOrganization.setNull(); } 
  public void setCodeOrganization(String zValue) throws Exception {    pCodeOrganization.setValue(zValue);  }
  public String getCodeOrganization() throws Exception {     return pCodeOrganization.getValue();  }
	public void setLogo(String value) throws Exception {
		pLogo.setValue(value);
	}

	public String getLogo() throws Exception {
		return pLogo.getValue();
	}	
	

  /**
   * Constructor de la Clase
   */
  public BizOrganization() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "company", pCompany );
    this.addItem( "code", pCodeOrganization );
    this.addItem( "descripcion", pOrganization );
    this.addItem( "logo_inc", pLogoIncr );
    this.addItem("logo", pLogo);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "ID", false, false, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Organizacion", true, true, 200 );
    this.addFixedItem( FIELD, "code", "code", true, true, 50 );
		this.addFixedItem( FIELD, "logo", "Logo", true, false, 250);
 		this.addFixedItem( VIRTUAL, "logo_inc", "Logo Incrustado", true, false, 4000);
 }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_ORGANIZATION"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "id",  zId ); 
    return read(); 
  } 
  public boolean read( String company,long zId ) throws Exception { 
    addFilter( "company", company ); 
    addFilter( "id",  zId ); 
    return read(); 
  } 
  public boolean read( String company,String zCode ) throws Exception { 
    addFilter( "company", company ); 
    addFilter( "code",  zCode ); 
    return read(); 
  } 
 
  
  private static JMap<String,String> iatas = null; 
  public static void clearIatas() {
  	iatas=null;
  }
  public static String readByIata( String company,String  zIata ) throws Exception { 
    if (iatas==null) {
    	iatas = JCollectionFactory.createMap();
    	JRecords<BizOrganizationDetail> details = new JRecords<BizOrganizationDetail>(BizOrganizationDetail.class);
    	JIterator<BizOrganizationDetail> it = details.getStaticIterator();
    	while (it.hasMoreElements()) {
    		BizOrganizationDetail det = it.nextElement();
    		BizOrganization org = new BizOrganization();
    		org.dontThrowException(true);
    		if (!org.read(det.getCompany(), det.getIdOrganization())) 
    			continue;
    		iatas.addElement(det.getCompany()+"_"+det.getIata(), org.getCodeOrganization());
    	}
    }
  	return iatas.getElement(company+"_"+zIata) ;
  } 
//	public void execProcessAddMiembro(final String iata) throws Exception {
//		JExec oExec = new JExec(this, "processAddMiembro") {
//
//			@Override
//			public void Do() throws Exception {
//				processAddMiembro(iata);
//			}
//		};
//		oExec.execute();
//	}
  
//  public void processAddMiembro(String iata ) throws Exception {
//  	BizOrganizationDetail d = new BizOrganizationDetail();
//  	d.dontThrowException(true);
//  	if (d.read(getCompany(),getId(),iata))	return;
//  	d.setCompany(getCompany());
//  	d.setIdOrganization(getId());
//  	d.setIata(iata);
//  	d.processInsert();
//  }
//  

	@Override
	public void processDelete() throws Exception {

		JRecords<BizOrganizationDetail> oOrganizations=new JRecords<BizOrganizationDetail>(BizOrganizationDetail.class);
		oOrganizations.addFilter("id_org", getId());
		oOrganizations.processDeleteAll();

		super.processDelete();
	}

//	public BizOrganization processClon(BizOrganization newDoc) throws Exception {
//		newDoc.insert();
//		newDoc.setId(newDoc.getIdentity("id"));
//		JRecords<BizOrganizationDetail> ampls = new JRecords<BizOrganizationDetail>(BizOrganizationDetail.class);
//		ampls.addFilter("company", getCompany());
//		ampls.addFilter("id_org", getId());
//		ampls.toStatic();
//		while (ampls.nextRecord()) {
//			BizOrganizationDetail ampl = ampls.getRecord();
//			BizOrganizationDetail na = new BizOrganizationDetail();
//			na.copyProperties(ampl);
//			na.setCompany(newDoc.getCompany());
//			na.setIdOrganization(newDoc.getId());
//			na.processInsert();
//		}
//
//		return newDoc;
//	}
	
	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.organization.GuiOrganization");
  	
		JRelation r=rels.addRelationChild(50, "Miembros", BizOrganizationDetail.class);
  	r.addJoin("BSP_ORGANIZATION.id", "BSP_ORGANIZATION_DETAIL.id_org");
  	r.setAlias("members");

		super.attachRelationMap(rels);
	}
	
	@Override
	public void processInsert() throws Exception {
		processLogoFile();
		super.processInsert();
	}
	
	@Override
	public void processUpdate() throws Exception {
		processLogoFile();
		super.processUpdate();
	}
	public void processLogoFile() throws Exception {
		if ( this.pLogo.isEstablecida() == false ) return ;
		if (this.pLogo.isEmpty())	{			
			return;		
		}				
		String strSourceFileName = this.pLogo.getValue().replace("%3A", ":");				
		File oSourceFile = new File(strSourceFileName);				
		// Get source File Name		
		String strDestFileName = oSourceFile.getName();		
		int iLastPointIndex = strDestFileName.lastIndexOf('.');		
		strDestFileName = strDestFileName.substring(0, iLastPointIndex);				
		// Build destination Dir		
		String strClassDestDir = BizCompany.getLogoPath();				
		String strFullDestFileName = strClassDestDir + strDestFileName;				
		JTools.copyFile(strSourceFileName, strFullDestFileName);				
		this.pLogo.setValue(BizUsuario.getUsr().getCompany()+"/"+strDestFileName);	
	} 

}
