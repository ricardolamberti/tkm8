package pss.bsp.bspBusiness;

import java.util.Date;

import pss.common.regions.company.BizCompany;
import pss.common.regions.company.BizCompanyCountry;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizCompanyExtraData extends JRecord {

  private JString pCompany = new JString();
  private JLong pLicencias = new JLong();
  private JDate pFechaIncio = new JDate();
  private JDate pFechaHasta = new JDate();
  private JString pPais = new JString();
  private JLong pRenovaciones = new JLong();
  private JString pTipoLicencia = new JString();
  private JLong pVersion = new JLong();
  private JBoolean pInactiva = new JBoolean();
  
  private JBoolean pSuspender = new JBoolean();
  private JBoolean pDependant = new JBoolean();
  private JString pCodigoCliente = new JString();
   
  private JBoolean pCNS = new JBoolean();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setLicencias(long zValue) throws Exception {    pLicencias.setValue(zValue);  }
  public long getLicencias() throws Exception {     return pLicencias.getValue();  }
  public boolean isNullLicencias() throws Exception { return  pLicencias.isNull(); } 
  public void setNullToLicencias() throws Exception {  pLicencias.setNull(); } 
  public void setFechaIncio(Date zValue) throws Exception {    pFechaIncio.setValue(zValue);  }
  public Date getFechaIncio() throws Exception {     return pFechaIncio.getValue();  }
  public boolean isNullFechaIncio() throws Exception { return  pFechaIncio.isNull(); } 
  public void setNullToFechaIncio() throws Exception {  pFechaIncio.setNull(); } 
  public void setFechaHasta(Date zValue) throws Exception {    pFechaHasta.setValue(zValue);  }
  public Date getFechaHasta() throws Exception {     return pFechaHasta.getValue();  }
  public boolean isNullFechaHasta() throws Exception { return  pFechaHasta.isNull(); } 
  public void setNullToFechaHasta() throws Exception {  pFechaHasta.setNull(); } 
  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
  public boolean isNullPais() throws Exception { return  pPais.isNull(); } 
  public void setNullToPais() throws Exception {  pPais.setNull(); } 
  public void setRenovaciones(long zValue) throws Exception {    pRenovaciones.setValue(zValue);  }
  public long getRenovaciones() throws Exception {     return pRenovaciones.getValue();  }
  public boolean isNullRenovaciones() throws Exception { return  pRenovaciones.isNull(); } 
  public void setNullToRenovaciones() throws Exception {  pRenovaciones.setNull(); } 
  public void setTipoLicencia(String zValue) throws Exception {    pTipoLicencia.setValue(zValue);  }
  public String getTipoLicencia() throws Exception {     return pTipoLicencia.getValue();  }
  public boolean isNullTipoLicencia() throws Exception { return  pTipoLicencia.isNull(); } 
  public void setNullToTipoLicencia() throws Exception {  pTipoLicencia.setNull(); } 
  public void setVersion(long zValue) throws Exception {    pVersion.setValue(zValue);  }
  public long getVersion() throws Exception {     return pVersion.getValue();  }
  public boolean isNullVersion() throws Exception { return  pVersion.isNull(); } 
  public void setNullToVersion() throws Exception {  pVersion.setNull(); } 
  public void setInactiva(boolean zValue) throws Exception {    pInactiva.setValue(zValue);  }
  public boolean getInactiva() throws Exception {     return pInactiva.getValue();  }
  public void setSuspender(boolean zValue) throws Exception {    pSuspender.setValue(zValue);  }
  public boolean getSuspender() throws Exception {     return pSuspender.getValue();  }
  public void setDependant(boolean zValue) throws Exception {    pDependant.setValue(zValue);  }
  public boolean getDependant() throws Exception {     return pDependant.getValue();  }
  public void setCNS(boolean zValue) throws Exception {    pCNS.setValue(zValue);  }
  public boolean getCNS() throws Exception {     return pCNS.getValue();  }

  public void setCodigoCliente(String zValue) throws Exception {    pCodigoCliente.setValue(zValue);  }
  public String getCodigoCliente() throws Exception {     return pCodigoCliente.getValue();  }
 
  /**
   * Constructor de la Clase
   */
  public BizCompanyExtraData() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "licencias", pLicencias );
    this.addItem( "fecha_incio", pFechaIncio );
    this.addItem( "fecha_hasta", pFechaHasta );
    this.addItem( "pais", pPais );
    this.addItem( "renovaciones", pRenovaciones );
    this.addItem( "tipo_licencia", pTipoLicencia );
    this.addItem( "tkmversion", pVersion );
    this.addItem( "inactiva", pInactiva );
    this.addItem( "suspender", pSuspender);
    this.addItem( "dependant", pDependant);
    this.addItem( "codigo_cliente", pCodigoCliente);
    this.addItem( "cns", pCNS);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "licencias", "Licencias", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_incio", "Fecha incio", true, false, 10 );
    this.addFixedItem( FIELD, "fecha_hasta", "Fecha hasta", true, false, 10 );
    this.addFixedItem( FIELD, "pais", "Pais", true, true, 15 );
    this.addFixedItem( FIELD, "renovaciones", "Renovaciones", true, false, 18 );
    this.addFixedItem( FIELD, "tipo_licencia", "Tipo licencia", true, true, 50 );
    this.addFixedItem( FIELD, "tkmversion", "Version", true, true, 18 );
    this.addFixedItem( FIELD, "inactiva", "Inactiva", true, true, 1 );
    this.addFixedItem( FIELD, "suspender", "Suspender", true, true, 1 );
    this.addFixedItem( FIELD, "dependant", "Dependiente", true, false, 1 );
    this.addFixedItem( FIELD, "codigo_cliente", "Código cliente", true, false, 50 );
    this.addFixedItem( FIELD, "cns", "CNS", true, false, 1 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_company"; }

  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static JMap<String, BizCompanyExtraData> hCache=null;
	public static synchronized JMap<String, BizCompanyExtraData> getCompanyCacheEmpty() throws Exception {
		if (hCache != null)
			return hCache;
		return hCache = JCollectionFactory.createMap();
	}

	public static synchronized JMap<String, BizCompanyExtraData> getCompanyCache() throws Exception {
		if (hCache != null)
			return hCache;
		JMap<String, BizCompanyExtraData> map = JCollectionFactory.createMap();
		JRecords<BizCompanyExtraData> recs = new JRecords<BizCompanyExtraData>(BizCompanyExtraData.class);
		recs.readAll();
		recs.firstRecord();
		while (recs.nextRecord()) {
			BizCompanyExtraData c = recs.getRecord();
			map.addElement(c.getCompany(), c);
		}
		return (hCache = map);
	}
  /**
   * Default read() method
   */
  public boolean read( String zCompany ) throws Exception { 
//    addFilter( "company",  zCompany ); 
//    return read(); 
    BizCompanyExtraData data = getCompanyCache().getElement(zCompany);
    if (data==null) return false;
    this.copyProperties(data);
    return true;
  } 
  
  
  @Override
  public void processUpdate() throws Exception {
  	// TODO Auto-generated method stub
  	super.processUpdate();
  	
  	BizCompanyCountry cc = new BizCompanyCountry();
  	cc.dontThrowException(true);
  	if (cc.read(getCompany())) {
  		cc.setCountry(getPais());
//  		cc.processDelete();
  		cc.processUpdateOrInsertWithCheck();
  	}
  	getCompanyCache().removeElement(getCompany());
  	getCompanyCache().addElement(getCompany(),this);

  }
}
