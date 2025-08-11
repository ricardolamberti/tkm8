package pss.bsp.contrato.series.calculado;

import java.util.Date;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizSerieCalculada extends JRecord {

  private JLong pModelo = new JLong();
  private JFloat pValor = new JFloat();
  private JFloat pValorAnt = new JFloat();
  private JFloat pValorEstimado = new JFloat();
  private JFloat pAcumulado = new JFloat();
  private JLong pAnio = new JLong();
  private JLong pMes = new JLong();
  private JLong pDia = new JLong();
  private JDate pFecha = new JDate();
  private JLong pVariable = new JLong();
  private JString pCompany = new JString();
  private JLong pId = new JLong();



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setModelo(long zValue) throws Exception {    pModelo.setValue(zValue);  }
  public long getModelo() throws Exception {     return pModelo.getValue();  }
  public boolean isNullModelo() throws Exception { return  pModelo.isNull(); } 
  public void setNullToModelo() throws Exception {  pModelo.setNull(); } 
  public void setValor(double zValue) throws Exception {    pValor.setValue(zValue);  }
  public double getValor() throws Exception {     return pValor.getValue();  }
  public void setValorAnt(double zValue) throws Exception {    pValorAnt.setValue(zValue);  }
  public double getValorAnt() throws Exception {     return pValorAnt.getValue();  }
  public boolean isNullValor() throws Exception { return  pValor.isNull(); } 
  public void setNullToValor() throws Exception {  pValor.setNull(); } 
  public void setValorEstimado(double zValue) throws Exception {    pValorEstimado.setValue(zValue);  }
  public double getValorEstimado() throws Exception {     return pValorEstimado.getValue();  }
  public boolean isNullValorEstimado() throws Exception { return  pValorEstimado.isNull(); } 
  public void setNullToValorEstimado() throws Exception {  pValorEstimado.setNull(); } 
  public void setAcumulado(double zValue) throws Exception {    pAcumulado.setValue(zValue);  }
  public double getAcumulado() throws Exception {     return pAcumulado.getValue();  }
  public boolean isNullAcumulado() throws Exception { return  pAcumulado.isNull(); } 
  public void setNullToAcumulado() throws Exception {  pAcumulado.setNull(); } 
  public void setAnio(long zValue) throws Exception {    pAnio.setValue(zValue);  }
  public long getAnio() throws Exception {     return pAnio.getValue();  }
  public boolean isNullAnio() throws Exception { return  pAnio.isNull(); } 
  public void setNullToAnio() throws Exception {  pAnio.setNull(); } 
  public void setMes(long zValue) throws Exception {    pMes.setValue(zValue);  }
  public long getMes() throws Exception {     return pMes.getValue();  }
  public boolean isNullMes() throws Exception { return  pMes.isNull(); } 
  public void setNullToMes() throws Exception {  pMes.setNull(); } 
  public void setDia(long zValue) throws Exception {    pDia.setValue(zValue);  }
  public long getDia() throws Exception {     return pDia.getValue();  }
  public void setFecha(Date zValue) throws Exception {    pFecha.setValue(zValue);  }
  public Date getFecha() throws Exception {     return pFecha.getValue();  }
  public boolean isNullDia() throws Exception { return  pDia.isNull(); } 
  public void setNullToDia() throws Exception {  pDia.setNull(); } 
  public void setVariable(long zValue) throws Exception {    pVariable.setValue(zValue);  }
  public long getVariable() throws Exception {     return pVariable.getValue();  }
  public boolean isNullVariable() throws Exception { return  pVariable.isNull(); } 
  public void setNullToVariable() throws Exception {  pVariable.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizSerieCalculada() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "modelo", pModelo );
    this.addItem( "valor", pValor );
    this.addItem( "valor_ant", pValorAnt );
    this.addItem( "valor_estimado", pValorEstimado );
    this.addItem( "acumulado", pAcumulado );
    this.addItem( "anio", pAnio );
    this.addItem( "mes", pMes );
    this.addItem( "dia", pDia );
    this.addItem( "fecha", pFecha );
    this.addItem( "variable", pVariable );
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "modelo", "Modelo", true, true, 18 );
    this.addFixedItem( FIELD, "valor", "año actual", true, false, 18,2 );
    this.addFixedItem( FIELD, "valor_ant", "año anterior", true, false, 18,2 );
    this.addFixedItem( FIELD, "valor_estimado", "Valor estimado", true, false, 18,2 );
    this.addFixedItem( FIELD, "acumulado", "Acumulado", true, false, 18,2 );
    this.addFixedItem( FIELD, "anio", "Anio", true, true, 18 );
    this.addFixedItem( FIELD, "mes", "Mes", true, true, 18 );
    this.addFixedItem( FIELD, "dia", "dia", true, true, 18 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, true, 18 );
    this.addFixedItem( FIELD, "variable", "Variable", true, true, 18 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id", "Id", false, false, 64 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_serie_calculada"; }


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
  public boolean read(String company,long variable, Date fecha) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "variable",  variable ); 
    addFilter( "fecha",  fecha ); 
    return read(); 
  } 
  public boolean read( Date fecha ) throws Exception { 
    addFilter( "fecha",  fecha ); 
    return read(); 
  } 
}
