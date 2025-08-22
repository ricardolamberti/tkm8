package  pss.bsp.renderOver;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizRenderOver extends JRecord {

	private JLong pId = new JLong();
  private JString pCompany = new JString();
	private JLong pDetalle = new JLong();
  private JDate pUpdateVersion = new JDate();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
   * Constructor de la Clase
   */
  public BizRenderOver() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "company", pCompany );
    this.addItem( "detalle", pDetalle );
    this.addItem( "update_version", pUpdateVersion );
   }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "id", false, false, 64 );
    this.addFixedItem( FIELD, "company", "company", true, true, 50 );
    this.addFixedItem( FIELD, "detalle", "detalle", true, true, 50 );
    this.addFixedItem( FIELD, "update_version", "update_version", true, true, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_OVER_RENDER"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long id ) throws Exception { 
    addFilter( "id",  id ); 
    return read(); 
  } 
  public boolean readByDetalle( String company, long detalle ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "detalle",  detalle ); 
    return read(); 
  } 
  public static boolean hasFullRender(BizDetalle detalle) throws Exception {
  	BizRenderOver render = new BizRenderOver();
  	render.dontThrowException(true);
  	if (render.readByDetalle(detalle.getCompany(), detalle.getId())) {
  		return render.pUpdateVersion.getValue().equals(detalle.getUpdateVersion());
  	} 
  	return true;
  	
  
  }
  public static void updateRender(BizDetalle detalle) throws Exception {
  	BizRenderOver render = new BizRenderOver();
  	render.dontThrowException(true);
  	if (render.readByDetalle(detalle.getCompany(), detalle.getId())) {
  		render.pUpdateVersion.setValue(detalle.getUpdateVersion());
  		render.update();
  	} else {
  		render.pCompany.setValue(detalle.getCompany());
  		render.pDetalle.setValue(detalle.getId());
   		render.pUpdateVersion.setValue(detalle.getUpdateVersion());
  		render.update();
  	}
  }
}
