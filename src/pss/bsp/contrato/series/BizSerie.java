package pss.bsp.contrato.series;

import java.util.Calendar;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;

public class BizSerie extends JRecord {

  private JLong pVariable = new JLong();
  private JLong pId = new JLong();
  private JLong pMes = new JLong();
  private JLong pDia= new JLong();
  private JFloat pValor = new JFloat();
  private JFloat pAcumulado = new JFloat();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setValor(double zValue) throws Exception {    pValor.setValue(zValue);  }
  public double getValor() throws Exception {     return pValor.getValue();  }
  public boolean isNullValor() throws Exception { return  pValor.isNull(); } 
  public void setNullToValor() throws Exception {  pValor.setNull(); } 
  public void setAcumulado(double zValue) throws Exception {    pAcumulado.setValue(zValue);  }
  public double getAcumulado() throws Exception {     return pAcumulado.getValue();  }
  public boolean isNullAcumulado() throws Exception { return  pAcumulado.isNull(); } 
  public void setNullToAcumulado() throws Exception {  pAcumulado.setNull(); } 
  public void setVariable(long zValue) throws Exception {    pVariable.setValue(zValue);  }
  public long getVariable() throws Exception {     return pVariable.getValue();  }
  public boolean isNullVariable() throws Exception { return  pVariable.isNull(); } 
  public void setNullToVariable() throws Exception {  pVariable.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setDia(long zValue) throws Exception {    pDia.setValue(zValue);  }
  public long getDia() throws Exception {     return pDia.getValue();  }
  public boolean isNullDia() throws Exception { return  pDia.isNull(); } 
  public void setNullToDia() throws Exception {  pDia.setNull(); } 
  public void setMes(long zValue) throws Exception {    pMes.setValue(zValue);  }
  public long getMes() throws Exception {     return pMes.getValue();  }
  public boolean isNullMes() throws Exception { return  pMes.isNull(); } 
  public void setNullToMes() throws Exception {  pMes.setNull(); } 



  /**
   * Constructor de la Clase
   */
  public BizSerie() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "valor", pValor );
    this.addItem( "acumulado", pAcumulado );
    this.addItem( "mes", pMes );
    this.addItem( "dia", pDia );
    this.addItem( "variable", pVariable );
    this.addItem( "id", pId );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "valor", "Valor", true, true, 18,2 );
    this.addFixedItem( FIELD, "acumulado", "Acumulado", true, true, 18,2 );
    this.addFixedItem( FIELD, "dia", "dia", true, true, 18 );
    this.addFixedItem( FIELD, "mes", "mes", true, true, 18 );
    this.addFixedItem( FIELD, "variable", "Variable", true, true, 18 );
    this.addFixedItem( KEY, "id", "Id", false, false, 32 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_series"; }


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
  public boolean read( long zMes,long zDia,long zVar ) throws Exception { 
   // addFilter( "mes",  zMes ); 
    addFilter( "dia",  zDia ); 
    addFilter( "variable",  zVar ); 
    return read(); 
  } 
  
  public static void processPopulate() throws Exception {
  	JRecords<BizSerie> erase = new JRecords<BizSerie>(BizSerie.class);
  	erase.ExecprocessDeleteAll();
  	
  	Calendar cal = Calendar.getInstance();
  	int meses[] = {900,900,700,50,10,10,800,800,500,50,10,200};
  	cal.set(2014,0,1);
  	double acumul1 = 0;
  	for (int day=1;cal.get(Calendar.YEAR)!=JDateTools.getAnioActual();day++) {
  		BizSerie serie = new BizSerie();
  		serie.setMes(cal.get(Calendar.MONTH));
  		serie.setDia(cal.get(Calendar.DAY_OF_YEAR));
  		double valor =((Math.random()*meses[cal.get(Calendar.MONTH)])+2000);
  		acumul1+=valor;
  		serie.setVariable(0);
  		serie.setValor(valor);
  		serie.setAcumulado(acumul1);
  		serie.execProcessInsert();
  		
  		
  		cal.add(Calendar.DAY_OF_YEAR, 1);
  	}
  }
  
}
