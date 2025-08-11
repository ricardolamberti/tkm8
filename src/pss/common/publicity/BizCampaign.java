package pss.common.publicity;

import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCampaign extends JRecord {

  private JLong pId = new JLong();
  private JString pDescription = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public void setDescription(String zValue) throws Exception {    pDescription.setValue(zValue);  }
  public String getDescription() throws Exception {     return pDescription.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizCampaign() throws Exception {
  }


  @Override
	public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "description", pDescription );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", true, true, 18 );
    this.addFixedItem( FIELD, "description", "descripcion", true, true, 256 );

  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "PUB_PUBLICITY_CAMPAIGN"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static BizCampaign getDefaultCampaign() {
	  BizCampaign defa = null;
	  try {
	  	long idCampaign = BizPssConfig.getPssConfig().getPublicityCampaign();
	  	if (idCampaign==-1) return null;
		  defa = new BizCampaign();
		  defa.Read(idCampaign);
		  JAplicacion.GetApp().setPublicityCampaign(defa);
	} catch (Exception e) {
		defa = null;
	} 
	return defa;
  }

  /**
   * Default Read() method
   */
  public boolean Read( long zId ) throws Exception { 
    addFilter( "id",  zId ); 
    return Read(); 
  }
  
  public void setCampaign() { 
	  JAplicacion.GetApp().setPublicityCampaign(this); 
  }

  public static void clearCampaign() { 
	  JAplicacion.GetApp().setPublicityCampaign(null); 
  }

}
