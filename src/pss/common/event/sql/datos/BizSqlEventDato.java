package  pss.common.event.sql.datos;

import java.util.Date;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JImage;
import pss.core.services.fields.JLong;
import pss.core.services.records.JRecord;

public class BizSqlEventDato extends JRecord {

  private JLong pId = new JLong();
  private JFloat pValue = new JFloat();
  private JFloat pVarValor = new JFloat();
  private JFloat pVarPorc = new JFloat();
  private JFloat pTendencia = new JFloat();
  private JDate pFecha = new JDate();
  private JLong pIdevento = new JLong();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setValue(double zValue) throws Exception {    pValue.setValue(zValue);  }
  public double getValue() throws Exception {     return pValue.getValue();  }
  public void setVarValue(double zValue) throws Exception {    pVarValor.setValue(zValue);  }
  public double getVarValue() throws Exception {     return pVarValor.getValue();  }
  public void setVarPorc(double zValue) throws Exception {    pVarPorc.setValue(zValue);  }
  public double getVarPorc() throws Exception {     return pVarPorc.getValue();  }
  public void setTendencia(double zValue) throws Exception {    pTendencia.setValue(zValue);  }
  public double getTendencia() throws Exception {     return pTendencia.getValue();  }
  public boolean isNullValue() throws Exception { return  pValue.isNull(); } 
  public void setNullToValue() throws Exception {  pValue.setNull(); } 
  public void setFecha(Date zValue) throws Exception {    pFecha.setValue(zValue);  }
  public Date getFecha() throws Exception {     return pFecha.getValue();  }
  public boolean isNullFecha() throws Exception { return  pFecha.isNull(); } 
  public void setNullToFecha() throws Exception {  pFecha.setNull(); } 
  public void setIdevento(long zValue) throws Exception {    pIdevento.setValue(zValue);  }
  public long getIdevento() throws Exception {     return pIdevento.getValue();  }
  public boolean isNullIdevento() throws Exception { return  pIdevento.isNull(); } 
  public void setNullToIdevento() throws Exception {  pIdevento.setNull(); } 
  private JImage pTendenciaView = new JImage() {
  	public void preset() throws Exception {
  		pTendenciaView.setValue(getTendenciaView());
  	};
  };
  
  /**
   * Constructor de la Clase
   */
  public BizSqlEventDato() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "value", pValue );
    this.addItem( "var_val", pVarValor );
    this.addItem( "var_porc", pVarPorc );
    this.addItem( "tendencia", pTendencia );
    this.addItem( "tendenciaview", pTendenciaView);
    this.addItem( "fecha", pFecha );
    this.addItem( "id_evento", pIdevento );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "value", "Value", true, true, 18,2 );
    this.addFixedItem( FIELD, "var_val", "Variación", true, false, 18,2 );
    this.addFixedItem( FIELD, "var_porc", "Variación(%)", true, false, 18,2 );
    this.addFixedItem( FIELD, "tendencia", "Tendencia", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "tendenciaview", "T.", true, false, 50 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, true, 18 );
    this.addFixedItem( FIELD, "id_evento", "Id evento", true, true, 64 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "evt_sqleventos_datos"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public String getTendenciaView() throws Exception {
  	if (getTendencia()>0) return "/images/fsube2.gif";
  	if (getTendencia()<0) return "/images/fbaja2.gif";
  	return "/images/figual2.gif";
  	
  	
  }
  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception {
  	clean();
    addFilter( "id",  zId ); 
    return read(); 
  } 
  public boolean read( long zEvento,Date zFecha ) throws Exception { 
  	clean();
    addFilter( "id_evento",  zEvento ); 
    addFilter( "fecha",  zFecha ); 
    return read(); 
  } 
  
 
}
