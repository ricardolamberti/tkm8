package  pss.bsp.consolid.model.cotizacionDK;

import java.util.Date;

import pss.bsp.consolid.model.gruposDK.detail.BizGrupoDKDetail;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizCotizacionDK extends JRecord {

  private JLong pIdCot = new JLong();
  private JString pCompany = new JString();
  private JLong pDk = new JLong();
  private JDate pFecha = new JDate();
  private JCurrency pCotizacion = new JCurrency();
   
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdCotizacion(long zValue) throws Exception {    pIdCot.setValue(zValue);  }
  public long getIdCotizacion() throws Exception {     return pIdCot.getValue();  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setDK(long zValue) throws Exception {    pDk.setValue(zValue);  }
  public long getDK() throws Exception {     return pDk.getValue();  }
  public void setFecha(Date zValue) throws Exception {    pFecha.setValue(zValue);  }
  public Date getFecha() throws Exception {     return pFecha.getValue();  }
  public void setCotizacion(double zValue) throws Exception {    pCotizacion.setValue(zValue);  }
  public double getCotizacion() throws Exception {     return pCotizacion.getValue();  }


  public BizCotizacionDK() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany);
    this.addItem( "id_cot", pIdCot);
    this.addItem( "id_dk", pDk);
    this.addItem( "fecha", pFecha );
    this.addItem( "cotizacion", pCotizacion );
    
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_cot", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 50 );
    this.addFixedItem( FIELD, "id_dk", "Dk", true, false, 64 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, false, 50 );
    this.addFixedItem( FIELD, "cotizacion", "Cotización", true, false, 18,2 );
 }
  
  
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_COTIZACION_DK"; }

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
    this.addFilter( "id_cot",  zIdtipo ); 
    return this.read(); 
  } 
  
  public boolean ReadByDk( String company, String dk, Date date) throws Exception {
  
    this.addFilter( "company",  company ); 
    this.addJoin(JRelations.JOIN, "BSP_DK", "BSP_DK.id=BSP_COTIZACION_DK.id_dk");
    this.addFilter( "BSP_DK",  "dk", dk,"=" ); 
    this.addFilter("fecha", date,"<=");
    this.addOrderBy("fecha","desc");
   
		
    return this.read(); 
  } 
  
 }
