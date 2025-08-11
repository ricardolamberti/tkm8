package  pss.common.security.license.typeLicense;

import pss.common.customMenu.BizMenu;
import pss.common.security.BizRol;
import pss.common.security.license.ILicense;
import pss.common.security.license.license.BizLicense;
import pss.common.security.license.typeLicense.detail.BizTypeLicenseDetail;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizTypeLicense extends JRecord {

  private JString pCompany = new JString();
  private JString pIdtypelicense = new JString();
  private JString pDescription = new JString();
  private JString pIdcustom_menu = new JString();
  private JLong pRol = new JLong();
  private JString pIdclase = new JString();
  private JBoolean pIsUpgradeable = new JBoolean();
  private JFloat pMonto = new JFloat();
  
	public static JMap<String, String> licenseClass=null;
	private static String[][] clasesConocidas = {
			{"pss.turnos.license.LicenseFree","Free"},
			{"pss.turnos.license.LicenseGold","Gold"},
			{"pss.bsp.consola.typesLicense.JTKMTest","TKM TEST"},
			{"pss.bsp.consola.typesLicense.JTKMStandart","TKM STANDARD"}
	};

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setIdtypelicense(String zValue) throws Exception {    pIdtypelicense.setValue(zValue);  }
  public String getIdtypelicense() throws Exception {     return pIdtypelicense.getValue();  }
  public boolean isNullIdtypelicense() throws Exception { return  pIdtypelicense.isNull(); } 
  public void setNullToIdtypelicense() throws Exception {  pIdtypelicense.setNull(); } 
  public void setDescription(String zValue) throws Exception {    pDescription.setValue(zValue);  }
  public String getDescription() throws Exception {     return pDescription.getValue();  }
  public boolean isNullDescription() throws Exception { return  pDescription.isNull(); } 
  public void setNullToDescription() throws Exception {  pDescription.setNull(); } 
  public void setIdcustom_menu(String zValue) throws Exception {    pIdcustom_menu.setValue(zValue);  }
  public String getIdcustom_menu() throws Exception {     return pIdcustom_menu.getValue();  }
  public boolean isNullIdcustom_menu() throws Exception { return  pIdcustom_menu.isNull(); } 
  public void setNullToIdcustom_menu() throws Exception {  pIdcustom_menu.setNull(); } 
  public void setRol(long zValue) throws Exception {    pRol.setValue(zValue);  }
  public long getRol() throws Exception {     return pRol.getValue();  }
  public boolean isNullRol() throws Exception { return  pRol.isNull(); } 
  public void setNullToRol() throws Exception {  pRol.setNull(); } 
  public void setIdclase(String zValue) throws Exception {    pIdclase.setValue(zValue);  }
  public String getIdclase() throws Exception {     return pIdclase.getValue();  }
  public boolean isNullIdclase() throws Exception { return  pIdclase.isNull(); } 
  public void setNullToIdclase() throws Exception {  pIdclase.setNull(); } 
  public boolean isUpgradeable() throws Exception {	return pIsUpgradeable.getValue();}

  BizMenu objMenu;
  BizRol objRol;
    
  public BizMenu getObjCustomMenu() throws Exception {
  	if (objMenu!=null) return objMenu;
  	BizMenu t = new BizMenu();
  	t.dontThrowException(true);
  	if (!t.Read(getIdcustom_menu())) return null;
  	return objMenu=t;
  }
  public BizRol getObjRol() throws Exception {
  	if (objRol!=null) return objRol;
  	BizRol t = new BizRol();
  	t.dontThrowException(true);
  	if (!t.Read(getCompany(),(int)getRol())) return null;
  	return objRol=t;
  }  
  /**
   * Constructor de la Clase
   */
  public BizTypeLicense() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id_typeLicense", pIdtypelicense );
    this.addItem( "description", pDescription );
    this.addItem( "id_custom_menu", pIdcustom_menu );
    this.addItem( "rol", pRol );
    this.addItem( "id_clase", pIdclase );
    this.addItem( "is_upgradeable", pIsUpgradeable );
    this.addItem( "monto", pMonto );
     }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id_typeLicense", "Id typelicense", true, true, 50 );
    this.addFixedItem( FIELD, "description", "Description", true, true, 150 );
    this.addFixedItem( FIELD, "id_custom_menu", "Id custom menu", true, false, 50 );
    this.addFixedItem( FIELD, "rol", "Rol", true, false, 5 );
    this.addFixedItem( FIELD, "id_clase", "Id clase", true, true, 250 );
    this.addFixedItem( FIELD, "is_upgradeable", "upgradeable", true, false, 1 );
    this.addFixedItem( FIELD, "monto", "monto", true, false, 18,2 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "SEG_TYPE_LICENSE"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static synchronized JMap<String, String> getLicenseClass() throws Exception {
		if (licenseClass!=null) return licenseClass;
		JMap<String, String> map=JCollectionFactory.createMap();
		for (String[] clase : clasesConocidas) {
			if (JTools.isInstalled(clase[0])) map.addElement(clase[0], clase[1]);
		}
		return (licenseClass=map);
	}  

  public ILicense getNewLicense() throws Exception {
  	if (pIdclase.isNull()) return null;
  	return (ILicense)Class.forName(getIdclase()).newInstance();
  }

  /**
   * Default read() method
   */
  public boolean read( String zCompany, String zIdtypelicense ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "id_typeLicense",  zIdtypelicense ); 
    return read(); 
  } 
  public boolean readByType( String zClase ) throws Exception { 
    addFilter( "id_clase",  zClase ); 
    return read(); 
  } 

  public String buildDetail(BizLicense zLicense) throws Exception {
  	String key = zLicense.getCompany()+"_"+zLicense.getIdlicense()+"_"+zLicense.getIdtypelicense();
  	JRecords<BizTypeLicenseDetail> detalles = new JRecords<BizTypeLicenseDetail>(BizTypeLicenseDetail.class);
  	detalles.addFilter( "company",  getCompany() ); 
  	detalles.addFilter( "id_typeLicense",  getIdtypelicense() ); 
  	detalles.readAll();
  	ILicense l = getNewLicense();
  	l.set(zLicense);
  	while (detalles.nextRecord()) {
  		BizTypeLicenseDetail detalle = detalles.getRecord();
  		l.addNewLicenseDetail(detalle);
  		key+="|"+detalle.getField()+"="+detalle.getValue();
  	}
  	
  	return JTools.StringToPassword(key);
  }
}
