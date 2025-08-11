package  pss.bsp.dk;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizClienteDK extends JRecord {

  private JLong pId = new JLong();
  private JString pCompany = new JString();
  private JString pDk = new JString();
  private JString pDescripcion = new JString();
  private JString pMail = new JString();
  private JString pNumero = new JString();
  private JFloat pIva = new JFloat();
  private JString pCode = new JString();
  private JString pCtaBancaria = new JString();
  private JString pCtaClave = new JString();
  
  private JString pComision = new JString();
  private JString pFormato = new JString();
  
  private JBoolean pReporte1 = new JBoolean();
  private JBoolean pReporte2 = new JBoolean();
  private JBoolean pReporte3 = new JBoolean();
  private JBoolean pReporte4 = new JBoolean();
  private JBoolean pReporte5 = new JBoolean();
  private JBoolean pReporte6 = new JBoolean();
  private JBoolean pReporte7 = new JBoolean();
  private JBoolean pReporte8 = new JBoolean();

  
  private JString pDescrip = new JString() {
  	public void preset() throws Exception {
  		pDescrip.setValue(getDescripOperativa());
  	};
  };
  
	public final static String OFRECIDO = "O";
	public final static String CEDIDO = "C";
	public final static String SIN_COMISION = "S";
	 public String getDescripOperativa() throws Exception {     
	  	return pDk.getValue()+"-"+pDescripcion.getValue();  
	  }
	  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  static JMap<String,String> gComision;
  public static JMap<String,String> getTipoComision() throws Exception {
  	if (gComision!=null) return gComision;
  	gComision=JCollectionFactory.createMap();
  	gComision.addElement(OFRECIDO, "Ofrecidos");
  	gComision.addElement(CEDIDO, "Cedidos");
  	gComision.addElement(SIN_COMISION, "Sin comisión");
   	return gComision;
  }

  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public void setDK(String zValue) throws Exception {    pDk.setValue(zValue);  }
  public String getDK() throws Exception {     return pDk.getValue();  }
  public void setFormato(String zValue) throws Exception {    pFormato.setValue(zValue);  }
  public String getFormato() throws Exception {     return pFormato.getValue();  }
  public void setReporte1(boolean zValue) throws Exception {    pReporte1.setValue(zValue);  }
  public boolean getReporte1() throws Exception {     return pReporte1.getValue();  }
  public void setReporte2(boolean zValue) throws Exception {    pReporte2.setValue(zValue);  }
  public boolean getReporte2() throws Exception {     return pReporte2.getValue();  }
  public void setReporte3(boolean zValue) throws Exception {    pReporte3.setValue(zValue);  }
  public boolean getReporte3() throws Exception {     return pReporte3.getValue();  }
  public void setReporte4(boolean zValue) throws Exception {    pReporte4.setValue(zValue);  }
  public boolean getReporte4() throws Exception {     return pReporte4.getValue();  }
  public void setReporte5(boolean zValue) throws Exception {    pReporte5.setValue(zValue);  }
  public boolean getReporte5() throws Exception {     return pReporte5.getValue();  }
  public void setReporte6(boolean zValue) throws Exception {    pReporte6.setValue(zValue);  }
  public boolean getReporte6() throws Exception {     return pReporte6.getValue();  }
  public void setReporte7(boolean zValue) throws Exception {    pReporte7.setValue(zValue);  }
  public boolean getReporte7() throws Exception {     return pReporte7.getValue();  }
  public void setReporte8(boolean zValue) throws Exception {    pReporte8.setValue(zValue);  }
  public boolean getReporte8() throws Exception {     return pReporte8.getValue();  }
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 


  public void setNumero(String zValue) throws Exception {    pNumero.setValue(zValue);  }
  public String getNumero() throws Exception {     return pNumero.getValue();  }
  public boolean isNullNumero() throws Exception { return  pNumero.isNull(); } 
  public void setNullToNumero() throws Exception {  pNumero.setNull(); } 

  public void setCode(String zValue) throws Exception {    pCode.setValue(zValue);  }
  public String getCode() throws Exception {     return pCode.getValue();  }
  public boolean isNullCode() throws Exception { return  pCode.isNull(); } 
  public void setNullToCode() throws Exception {  pCode.setNull(); } 

  public void setCtaBancaria(String zValue) throws Exception {    pCtaBancaria.setValue(zValue);  }
  public String getCtaBancaria() throws Exception {     return pCtaBancaria.getValue();  }
  public boolean isNullCtaBancaria() throws Exception { return  pCtaBancaria.isNull(); } 
  public void setNullToCtaBancaria() throws Exception {  pCtaBancaria.setNull(); } 

  public void setCtaClave(String zValue) throws Exception {    pCtaClave.setValue(zValue);  }
  public String getCtaClave() throws Exception {     return pCtaClave.getValue();  }
  public boolean isNullCtaClave() throws Exception { return  pCtaClave.isNull(); } 
  public void setNullToCtaClave() throws Exception {  pCtaClave.setNull(); } 

  public void setIva(double zValue) throws Exception {    pIva.setValue(zValue);  }
  public double getIva() throws Exception {     return pIva.getValue();  }
  public boolean isNullIva() throws Exception { return  pIva.isNull(); } 
  public void setNullToIva() throws Exception {  pIva.setNull(); } 

  public void setMail(String zValue) throws Exception {    pMail.setValue(zValue);  }
  public String getMail() throws Exception {     return pMail.getValue();  }
  public boolean isNullMail() throws Exception { return  pMail.isNull(); } 
  public void setNullToMail() throws Exception {  pMail.setNull(); } 

  public void setComision(String zValue) throws Exception {    pComision.setValue(zValue);  }
  public String getComision() throws Exception {     return pComision.getValue();  }
  public boolean isNullComision() throws Exception { return  pComision.isNull(); } 
  public void setNullToComision() throws Exception {  pComision.setNull(); } 



  public BizClienteDK() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId);
    this.addItem( "company", pCompany);
    this.addItem( "dk", pDk);
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "formato", pFormato );
    this.addItem( "numero", pNumero );
    this.addItem( "mail", pMail );
    this.addItem( "special_code", pCode );
    this.addItem( "cta_bancaria", pCtaBancaria );
    this.addItem( "cta_clave", pCtaClave );
    this.addItem( "iva", pIva );
    this.addItem( "reporte", pReporte1 );
    this.addItem( "reporte2", pReporte2 );
    this.addItem( "reporte3", pReporte3 );
    this.addItem( "reporte4", pReporte4 );
    this.addItem( "reporte5", pReporte5 );
    this.addItem( "reporte6", pReporte6 );
    this.addItem( "reporte7", pReporte7 );
    this.addItem( "reporte8", pReporte8 );
    this.addItem( "comision", pComision );
    this.addItem( "descripcion_op", pDescrip);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 50 );
    this.addFixedItem( FIELD, "dk", "Dk", true, false, 50 );
    this.addFixedItem( FIELD, "formato", "Formato", true, false, 50 );
     this.addFixedItem( FIELD, "descripcion", "Descripcion mailing", true, false, 250 );
    this.addFixedItem( FIELD, "mail", "Mail", true, false, 250 );
    this.addFixedItem( FIELD, "numero", "Código unificado", true, false, 50 );
    this.addFixedItem( FIELD, "special_code", "code", true, false, 50 );
    this.addFixedItem( FIELD, "cta_bancaria", "Cta bancaria", true, false, 50 );
    this.addFixedItem( FIELD, "cta_clave", "Cta Clave", true, false, 50 );
    this.addFixedItem( FIELD, "iva", "iva", true, false, 18,2 );
    this.addFixedItem( FIELD, "reporte", "Reporte 1", true, false, 1 );
    this.addFixedItem( FIELD, "reporte2", "Reporte 2", true, false, 1 );
    this.addFixedItem( FIELD, "reporte3", "Reporte 3", true, false, 1 );
    this.addFixedItem( FIELD, "reporte4", "Reporte 4", true, false, 1 );
    this.addFixedItem( FIELD, "reporte5", "Reporte 5", true, false, 1 );
    this.addFixedItem( FIELD, "reporte6", "Reporte 6", true, false, 1 );
    this.addFixedItem( FIELD, "reporte7", "Reporte 7", true, false, 1 );
    this.addFixedItem( FIELD, "reporte8", "Reporte 8", true, false, 1 );
    this.addFixedItem( FIELD, "comision", "Tipo comisión", true, false, 1 );
    this.addFixedItem( VIRTUAL, "descripcion_op", "Descripcion mailing", true, false, 250 );
  }
  
  
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_DK"; }

  public void processInsert() throws Exception {
    	super.processInsert();
  }
  public void processUpdate() throws Exception {
   	super.processUpdate();
  };

  /**
   * Default Read() method
   */
  public boolean Read( long zIdtipo) throws Exception { 
    this.addFilter( "id",  zIdtipo ); 
    return this.read(); 
  } 
  public boolean ReadByDK(String zcompany, String zDK) throws Exception { 
  	 this.addFilter( "company",  zcompany ); 
  	 this.addFilter( "dk",  zDK ); 
  	 return this.read(); 
  } 
  

 }
