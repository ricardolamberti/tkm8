package  pss.bsp.pdf.mex.impuesto;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizMexImpuesto extends JRecord {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JString pIdpdf = new JString();
  private JLong pLinea = new JLong();
  private JString pIso = new JString();
  private JFloat pContado = new JFloat();
  private JFloat pTarjeta = new JFloat();


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
  public void setIdpdf(String zValue) throws Exception {    pIdpdf.setValue(zValue);  }
  public String getIdpdf() throws Exception {     return pIdpdf.getValue();  }
  public boolean isNullIdpdf() throws Exception { return  pIdpdf.isNull(); } 
  public void setNullToIdpdf() throws Exception {  pIdpdf.setNull(); } 
  public void setLinea(long zValue) throws Exception {    pLinea.setValue(zValue);  }
  public long getLinea() throws Exception {     return pLinea.getValue();  }
  public boolean isNullLinea() throws Exception { return  pLinea.isNull(); } 
  public void setNullToLinea() throws Exception {  pLinea.setNull(); } 
  public void setIso(String zValue) throws Exception {    pIso.setValue(zValue);  }
  public String getIso() throws Exception {     return pIso.getValue();  }
  public boolean isNullIso() throws Exception { return  pIso.isNull(); } 
  public void setNullToIso() throws Exception {  pIso.setNull(); } 
  public void setContado(double zValue) throws Exception {    pContado.setValue(zValue);  }
  public double getContado() throws Exception {     return pContado.getValue();  }
  public boolean isNullContado() throws Exception { return  pContado.isNull(); } 
  public void setNullToContado() throws Exception {  pContado.setNull(); } 
  public void setTarjeta(double zValue) throws Exception {    pTarjeta.setValue(zValue);  }
  public double getTarjeta() throws Exception {     return pTarjeta.getValue();  }
  public boolean isNullTarjeta() throws Exception { return  pTarjeta.isNull(); } 
  public void setNullToTarjeta() throws Exception {  pTarjeta.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizMexImpuesto() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "idPDF", pIdpdf );
    this.addItem( "linea", pLinea );
    this.addItem( "iso", pIso );
    this.addItem( "contado", pContado );
    this.addItem( "tarjeta", pTarjeta );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "idPDF", "Idpdf", true, true, 300 );
    this.addFixedItem( KEY, "linea", "Linea", true, true, 18 );
    this.addFixedItem( KEY, "iso", "Iso", true, false, 50 );
    this.addFixedItem( FIELD, "owner", "Owner", true, true, 50 );
    this.addFixedItem( FIELD, "contado", "Contado", true, false, 18,2 );
    this.addFixedItem( FIELD, "tarjeta", "Tarjeta", true, false, 18,2 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_PDF_MEX_DETALLE_IMPUESTO"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany,  String zIdpdf, long zLinea, String zIso ) throws Exception { 
    addFilter( "company",  zCompany ); 
//    addFilter( "owner",  zOwner ); 
    addFilter( "idPDF",  zIdpdf ); 
    addFilter( "linea",  zLinea ); 
    addFilter( "iso",  zIso ); 
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


}
