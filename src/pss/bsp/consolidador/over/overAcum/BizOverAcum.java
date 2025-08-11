package pss.bsp.consolidador.over.overAcum;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizOverAcum extends JRecord {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JString pIdpdf = new JString();
  private JString pIdaerolinea = new JString();
  private JFloat pCountBoletos = new JFloat();
  private JFloat pSumOverped = new JFloat();
  private JFloat pSumOverrec = new JFloat();
  private JFloat pSumDif = new JFloat();


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
  public void setIdaerolinea(String zValue) throws Exception {    pIdaerolinea.setValue(zValue);  }
  public String getIdaerolinea() throws Exception {     return pIdaerolinea.getValue();  }
  public boolean isNullIdaerolinea() throws Exception { return  pIdaerolinea.isNull(); } 
  public void setNullToIdaerolinea() throws Exception {  pIdaerolinea.setNull(); } 
  public void setCountBoletos(double zValue) throws Exception {    pCountBoletos.setValue(zValue);  }
  public double getCountBoletos() throws Exception {     return pCountBoletos.getValue();  }
  public boolean isNullCountBoletos() throws Exception { return  pCountBoletos.isNull(); } 
  public void setNullToCountBoletos() throws Exception {  pCountBoletos.setNull(); } 
  public void setSumOverped(double zValue) throws Exception {    pSumOverped.setValue(zValue);  }
  public double getSumOverped() throws Exception {     return pSumOverped.getValue();  }
  public boolean isNullSumOverped() throws Exception { return  pSumOverped.isNull(); } 
  public void setNullToSumOverped() throws Exception {  pSumOverped.setNull(); } 
  public void setSumOverrec(double zValue) throws Exception {    pSumOverrec.setValue(zValue);  }
  public double getSumOverrec() throws Exception {     return pSumOverrec.getValue();  }
  public boolean isNullSumOverrec() throws Exception { return  pSumOverrec.isNull(); } 
  public void setNullToSumOverrec() throws Exception {  pSumOverrec.setNull(); } 
  public void setSumDif(double zValue) throws Exception {    pSumDif.setValue(zValue);  }
  public double getSumDif() throws Exception {     return pSumDif.getValue();  }
  public boolean isNullSumDif() throws Exception { return  pSumDif.isNull(); } 
  public void setNullToSumDif() throws Exception {  pSumDif.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizOverAcum() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
//    this.addItem( "owner", pOwner );
    this.addItem( "idpdf", pIdpdf );
    this.addItem( "id_aerolinea", pIdaerolinea );
    this.addItem( "count_boletos", pCountBoletos );
    this.addItem( "sum_over_ped", pSumOverped );
    this.addItem( "sum_over_rec", pSumOverrec );
    this.addItem( "sum_dif", pSumDif );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "idpdf", "Idpdf", true, true, 50 );
    this.addFixedItem( KEY, "id_aerolinea", "Id aerolinea", true, true, 50 );
//    this.addFixedItem( FIELD, "owner", "Owner", true, true, 50 );
    this.addFixedItem( FIELD, "count_boletos", "Cantidad boletos", true, true, 18,2 );
    this.addFixedItem( FIELD, "sum_over_ped", "Suma over ped", true, true, 18,2 );
    this.addFixedItem( FIELD, "sum_over_rec", "Suma over rec", true, true, 18,2 );
    this.addFixedItem( FIELD, "sum_dif", "Sum diferencia", true, true, 18,2 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void buildSql() throws Exception {
		this.clearFields();
		this.addField("company");
//		this.addField("owner");
		this.addField("idpdf");
		this.addField("id_aerolinea");
		
		this.addFuntion("count(boleto) count_boletos");
		this.addFuntion("sum(over_ped) sum_over_ped");
		this.addFuntion("sum(over_rec) sum_over_rec");
		this.addFuntion("sum(dif) sum_dif");
		
		this.addGroupBy("company");
//		this.addGroupBy("owner");
		this.addGroupBy("idpdf");
		this.addGroupBy("id_aerolinea");
	}
	
	@Override
	public boolean read() throws Exception {
		buildSql();
		return super.read();
	}
  /**
   * Default read() method
   */
  public boolean read( String zCompany,  String zIdpdf, String zIdaerolinea ) throws Exception { 
    addFilter( "company",  zCompany ); 
//    addFilter( "owner",  zOwner ); 
    addFilter( "idpdf",  zIdpdf ); 
    addFilter( "id_aerolinea",  zIdaerolinea ); 
    return read(); 
  } 
  
  public String getFieldForeground(String zColName) throws Exception {
  	if (zColName.toLowerCase().indexOf("dif")==-1) return null;
  	String valor = getPropAsString(zColName);
  	if (!valor.equals("") &&  Double.parseDouble(valor)!=0) return "FF0000";
  	return "000000";
  }
	@Override
	public void processInsert() throws Exception {
		super.insert();
	}

}
