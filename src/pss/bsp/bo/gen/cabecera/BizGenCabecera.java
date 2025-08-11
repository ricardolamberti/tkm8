package  pss.bsp.bo.gen.cabecera;

import pss.bsp.bo.formato.BizFormato;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizGenCabecera extends JRecord {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JLong pIdinterfaz = new JLong();
  private JString pIdformato = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setOwner(String zValue) throws Exception {    pOwner.setValue(zValue);  }
  public String getOwner() throws Exception {     return pOwner.getValue();  }
  public boolean isNullOwner() throws Exception { return  pOwner.isNull(); } 
  public void setNullToOwner() throws Exception {  pOwner.setNull(); } 
  public void setIdinterfaz(long zValue) throws Exception {    pIdinterfaz.setValue(zValue);  }
  public long getIdinterfaz() throws Exception {     return pIdinterfaz.getValue();  }
  public boolean isNullIdinterfaz() throws Exception { return  pIdinterfaz.isNull(); } 
  public void setNullToIdinterfaz() throws Exception {  pIdinterfaz.setNull(); } 
  public void setIdformato(String zValue) throws Exception {    pIdformato.setValue(zValue);  }
  public String getIdformato() throws Exception {     return pIdformato.getValue();  }
  public boolean isNullIdformato() throws Exception { return  pIdformato.isNull(); } 
  public void setNullToIdformato() throws Exception {  pIdformato.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizGenCabecera() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "idInterfaz", pIdinterfaz );
    this.addItem( "id_formato", pIdformato );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "idInterfaz", "Idinterfaz", true, true, 18 );
    this.addFixedItem( FIELD, "id_formato", "Id formato", true, true, 50 );
    this.addFixedItem( FIELD, "owner", "Owner", true, true, 50 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_INTERFAZ_BO_GEN_CABECERA"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  BizFormato  objFormato;
  
  public BizFormato getObjFormato() throws Exception {
  	if (objFormato!=null) return objFormato;
  	BizFormato o = new BizFormato();
  	o.dontThrowException(true);
  	if(!o.read(getCompany(), getIdformato())) return o;
  	return (objFormato=o);
  }

  /**
   * Default read() method
   */
  public boolean read( String zCompany,  long zIdinterfaz ) throws Exception { 
    addFilter( "company",  zCompany ); 
  //  addFilter( "owner",  zOwner ); 
    addFilter( "idInterfaz",  zIdinterfaz ); 
    return read(); 
  } 
	@Override
	public void processInsert() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
	}

	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processUpdate();
	}

  public String getTitulo(long campo)  throws Exception {
  	BizFormato formato = getObjFormato();
  	if (campo==1) return formato.getD1();
  	if (campo==2) return formato.getD2();
  	if (campo==3) return formato.getD3();
  	if (campo==4) return formato.getD4();
  	if (campo==5) return formato.getD5();
  	if (campo==6) return formato.getD6();
  	if (campo==7) return formato.getD7();
  	if (campo==8) return formato.getD8();
  	if (campo==9) return formato.getD9();
  	if (campo==10) return formato.getD10();
  	if (campo==11) return formato.getD11();
  	if (campo==12) return formato.getD12();
  	if (campo==13) return formato.getD13();
  	if (campo==14) return formato.getD14();
  	if (campo==15) return formato.getD15();
  	if (campo==16) return formato.getD16();
  	if (campo==17) return formato.getD17();
  	if (campo==18) return formato.getD18();
  	if (campo==19) return formato.getD19();
  	if (campo==20) return formato.getD20();
   	if (campo==21) return formato.getD21();
  	if (campo==22) return formato.getD22();
  	if (campo==23) return formato.getD23();
  	if (campo==24) return formato.getD24();
  	if (campo==25) return formato.getD25();
  	if (campo==26) return formato.getD26();
  	if (campo==27) return formato.getD27();
  	if (campo==28) return formato.getD28();
  	if (campo==29) return formato.getD29();
  	if (campo==30) return formato.getD30();
  	if (campo==31) return formato.getD31();
  	if (campo==32) return formato.getD32();
  	if (campo==33) return formato.getD33();
  	if (campo==34) return formato.getD34();
  	if (campo==35) return formato.getD35();
  	if (campo==36) return formato.getD36();
  	if (campo==37) return formato.getD37();
  	if (campo==38) return formato.getD38();
  	if (campo==39) return formato.getD39();
  	if (campo==40) return formato.getD40();
  	return "*";
  }
}
