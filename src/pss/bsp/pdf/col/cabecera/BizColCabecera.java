package  pss.bsp.pdf.col.cabecera;

import java.util.Date;

import pss.bsp.pdf.IHeaderPDF;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizColCabecera extends JRecord implements IHeaderPDF {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JString pIdpdf = new JString();
  private JString pDescripcion = new JString();
  private JString pDireccion = new JString();
  private JString pCodigoPostal = new JString();
  private JString pIata = new JString();
  private JString pCif = new JString();
  private JString pLocalidad = new JString();
  private JString pMoneda = new JString();
  private JDate   pPeriodoDesde = new JDate();
  private JDate   pPeriodoHasta = new JDate();


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
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setDireccion(String zValue) throws Exception {    pDireccion.setValue(zValue);  }
  public String getDireccion() throws Exception {     return pDireccion.getValue();  }
  public boolean isNullDireccion() throws Exception { return  pDireccion.isNull(); } 
  public void setNullToDireccion() throws Exception {  pDireccion.setNull(); } 
  public void setCodigoPostal(String zValue) throws Exception {    pCodigoPostal.setValue(zValue);  }
  public String getCodigoPostal() throws Exception {     return pCodigoPostal.getValue();  }
  public boolean isNullCodigoPostal() throws Exception { return  pCodigoPostal.isNull(); } 
  public void setNullToCodigoPostal() throws Exception {  pCodigoPostal.setNull(); } 
  public void setIata(String zValue) throws Exception {    pIata.setValue(zValue);  }
  public String getIata() throws Exception {     return pIata.getValue();  }
  public boolean isNullIata() throws Exception { return  pIata.isNull(); } 
  public void setNullToIata() throws Exception {  pIata.setNull(); } 
  public void setCif(String zValue) throws Exception {    pCif.setValue(zValue);  }
  public String getCif() throws Exception {     return pCif.getValue();  }
  public boolean isNullCif() throws Exception { return  pCif.isNull(); } 
  public void setNullToCif() throws Exception {  pCif.setNull(); } 
  public void setLocalidad(String zValue) throws Exception {    pLocalidad.setValue(zValue);  }
  public String getLocalidad() throws Exception {     return pLocalidad.getValue();  }
  public boolean isNullLocalidad() throws Exception { return  pLocalidad.isNull(); } 
  public void setNullToLocalidad() throws Exception {  pLocalidad.setNull(); } 
  public void setMoneda(String zValue) throws Exception {    pMoneda.setValue(zValue);  }
  public String getMoneda() throws Exception {     return pMoneda.getValue();  }
  public boolean isNullMoneda() throws Exception { return  pMoneda.isNull(); } 
  public void setNullToMoneda() throws Exception {  pMoneda.setNull(); } 
  public void setPeriodoDesde(Date zValue) throws Exception {    pPeriodoDesde.setValue(zValue);  }
  public Date getPeriodoDesde() throws Exception {     return pPeriodoDesde.getValue();  }
  public void setPeriodoHasta(Date zValue) throws Exception {    pPeriodoHasta.setValue(zValue);  }
  public Date getPeriodoHasta() throws Exception {     return pPeriodoHasta.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizColCabecera() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "idPDF", pIdpdf );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "direccion", pDireccion );
    this.addItem( "codigo_postal", pCodigoPostal );
    this.addItem( "IATA", pIata );
    this.addItem( "CIF", pCif );
    this.addItem( "localidad", pLocalidad );
    this.addItem( "moneda", pMoneda );
    this.addItem( "periodoHasta", pPeriodoHasta );
    this.addItem( "periodoDesde", pPeriodoDesde );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "idPDF", "Idpdf", true, true, 300 );
    this.addFixedItem( FIELD, "owner", "Owner", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, false, 300 );
    this.addFixedItem( FIELD, "direccion", "Direccion", true, false, 300 );
    this.addFixedItem( FIELD, "codigo_postal", "Codigo postal", true, false, 50 );
    this.addFixedItem( FIELD, "IATA", "Iata", true, false, 50 );
    this.addFixedItem( FIELD, "CIF", "Cif", true, false, 50 );
    this.addFixedItem( FIELD, "localidad", "Localidad", true, false, 300 );
    this.addFixedItem( FIELD, "moneda", "Moneda", true, false, 50 );
    this.addFixedItem( FIELD, "periodoHasta", "Periodo Desde", true, false, 50 );
    this.addFixedItem( FIELD, "periodoHasta", "Periodo Hasta", true, false, 50 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_PDF_COL_CABECERA"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void attachRelationMap(JRelations rels) throws Exception {
  	//rels.hideField("company");
  	rels.hideField("owner");
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
	
  /**
   * Default read() method
   */
  public boolean read( String zCompany, String zIdpdf ) throws Exception { 
    addFilter( "company",  zCompany ); 
//    addFilter( "owner",  zOwner ); 
    addFilter( "idPDF",  zIdpdf ); 
    return read(); 
  } 
}
