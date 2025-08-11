package  pss.bsp.pais;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPais extends JRecord {

  private JString pPais = new JString();
  private JString pIdparser_pdf = new JString();
  private JString pIdparser_ho = new JString();
  private JString pIdparser_copa = new JString();
	private JLong pGmt = new JLong();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
  public boolean isNullPais() throws Exception { return  pPais.isNull(); } 
  public void setNullToPais() throws Exception {  pPais.setNull(); } 
  public void setIdparserPdf(String zValue) throws Exception {    pIdparser_pdf.setValue(zValue);  }
  public String getIdparserPdf() throws Exception {     return pIdparser_pdf.getValue();  }
  public boolean isNullIdparserPdf() throws Exception { return  pIdparser_pdf.isNull(); } 
  public void setNullToIdparserPdf() throws Exception {  pIdparser_pdf.setNull(); } 
  public void setIdparserHo(String zValue) throws Exception {    pIdparser_ho.setValue(zValue);  }
  public String getIdparserHo() throws Exception {     return pIdparser_ho.getValue();  }
  public boolean isNullIdparserHo() throws Exception { return  pIdparser_ho.isNull(); } 
  public void setNullToIdparserHo() throws Exception {  pIdparser_ho.setNull(); } 

  public void setIdparserCopa(String zValue) throws Exception {    pIdparser_copa.setValue(zValue);  }
  public String getIdparserCopa() throws Exception {     return pIdparser_copa.getValue();  }
  public boolean isNullIdparserCopa() throws Exception { return  pIdparser_copa.isNull(); } 
  public void setNullToIdparserCopa() throws Exception {  pIdparser_copa.setNull(); } 

  public void setGMT(long value) throws Exception {    pGmt.setValue(value);  }
  public long getGMT() throws Exception {     return pGmt.getValue();  }
 
  /**
   * Constructor de la Clase
   */
  public BizPais() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "pais", pPais );
    this.addItem( "id_parser_pdf", pIdparser_pdf );
    this.addItem( "id_parser_ho", pIdparser_ho );
    this.addItem( "id_parser_copa", pIdparser_copa );
    this.addItem( "gmt", pGmt );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "pais", "Pais", true, true, 2 );
    this.addFixedItem( FIELD, "id_parser_pdf", "Id parser pdf", true, true, 50 );
    this.addFixedItem( FIELD, "id_parser_ho", "Id parser ho", true, true, 50 );
    this.addFixedItem( FIELD, "id_parser_copa", "Id parser copa", true, true, 50 );
    this.addFixedItem( FIELD, "gmt", "GMT", true, false, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_PAIS"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zPais ) throws Exception { 
    addFilter( "pais",  zPais ); 
    return read(); 
  } 
}
