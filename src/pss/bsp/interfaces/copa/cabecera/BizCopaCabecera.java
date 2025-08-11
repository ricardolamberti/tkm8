package  pss.bsp.interfaces.copa.cabecera;

import java.util.Date;

import pss.bsp.pdf.IHeaderPDF;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCopaCabecera extends JRecord implements IHeaderPDF {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JString pIdpdf = new JString();
  private JString pDescripcion = new JString();
  private JString pAerolinea = new JString();
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
  public void setAerolinea(String zValue) throws Exception {    pAerolinea.setValue(zValue);  }
  public String getAerolinea() throws Exception {     return pAerolinea.getValue();  }
  public boolean isNullAerolinea() throws Exception { return  pAerolinea.isNull(); } 
  public void setNullToAerolinea() throws Exception {  pAerolinea.setNull(); } 
  public void setPeriodoDesde(Date zValue) throws Exception {    pPeriodoDesde.setValue(zValue);  }
  public Date getPeriodoDesde() throws Exception {     return pPeriodoDesde.getValue();  }
  public void setPeriodoHasta(Date zValue) throws Exception {    pPeriodoHasta.setValue(zValue);  }
  public Date getPeriodoHasta() throws Exception {     return pPeriodoHasta.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizCopaCabecera() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "idPDF", pIdpdf );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "aerolinea", pAerolinea );
    this.addItem( "periodoDesde", pPeriodoDesde );
    this.addItem( "periodoHasta", pPeriodoHasta );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "idPDF", "Idpdf", true, true, 300 );
    this.addFixedItem( FIELD, "owner", "Owner", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, false, 300 );
    this.addFixedItem( FIELD, "aerolinea", "aerolinea", true, false, 50 );
    this.addFixedItem( FIELD, "periodoDesde", "Periodo Desde", true, false, 50 );
    this.addFixedItem( FIELD, "periodoHasta", "Periodo Hasta", true, false, 50 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_COPA_CABECERA"; }


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
