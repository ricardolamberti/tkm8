package pss.bsp.contrato.evaluate.detail;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizEvaluateDetail extends JRecord {

  private JString pOk = new JString();
  private JString pDetalle = new JString();
  private JString pResult = new JString();
   private JLong pNumber = new JLong();
  private JLong pIdLinea = new JLong();
   //private JObjBDs<JRecord> pIdLinea = new JLong();
  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void setDetalle(String zValue) throws Exception {    pDetalle.setValue(zValue);  }
  public String getDetalle() throws Exception {     return pDetalle.getValue();  }
  public boolean isNullDetalle() throws Exception { return  pDetalle.isNull(); } 
  public void setNullToDetalle() throws Exception {  pDetalle.setNull(); } 
  public void setNumero(long zValue) throws Exception {    pNumber.setValue(zValue);  }
  public long getNumero() throws Exception {     return pNumber.getValue();  }
  public void setOk(String zValue) throws Exception {    pOk.setValue(zValue);  }
  public String getOk() throws Exception {     return pOk.getValue();  }
  public void setId(long zValue) throws Exception {    pIdLinea.setValue(zValue);  }
  public long getId() throws Exception {     return pIdLinea.getValue();  }
  public boolean isNullId() throws Exception { return  pIdLinea.isNull(); } 
  public void setNullToId() throws Exception {  pIdLinea.setNull(); } 
  public void setResult(String zValue) throws Exception {    pResult.setValue(zValue);  }
  public String getResult() throws Exception {     return pResult.getValue();  }
  public boolean isNullResult() throws Exception { return  pResult.isNull(); } 
  public void setNullToResult() throws Exception {  pResult.setNull(); } 
 
  
  
  BizDetalle objDetalle;

  public BizDetalle getObjDetalle() throws Exception {
  	if (objDetalle==null) {
  		BizDetalle det =new  BizDetalle();
  		det.dontThrowException(true);
  		if (!det.read(getId())) return null;
  		BizDetalle detFinal =det.getObjLogicaInstance().getBiz();
  		objDetalle=detFinal;
   	}
  	
		return objDetalle;
	}
	
  /**
   * Constructor de la Clase
   */
  public BizEvaluateDetail() throws Exception {
  }

  public BizEvaluateDetail(long number, String description) throws Exception {
  	setNumero(number);
  	setDetalle(description);
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pIdLinea );
    this.addItem( "ok", pOk );
    this.addItem( "detail", pDetalle );
    this.addItem( "number", pNumber );
    this.addItem( "result", pResult );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 32 );
    this.addFixedItem( FIELD, "ok", "ok", true, false, 32 );
    this.addFixedItem( FIELD, "detail", "detail", true, false, 300 );
    this.addFixedItem( FIELD, "number", "number", true, false, 32 );
    this.addFixedItem( FIELD, "result", "result", true, false, 300 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return null; }



}
