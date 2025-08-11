package  pss.bsp.consolid.model.gruposDK.detail;

import pss.bsp.consolid.model.gruposDK.BizGrupoDK;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizGrupoDKDetail extends JRecord {

	private JLong pIdGrupo = new JLong();
	private JLong pIdDetail = new JLong();
  private JString pCompany = new JString();
  private JString pDK = new JString();
  private JBoolean pViewAll = new JBoolean();
   
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdtipo(long zValue) throws Exception {    pIdGrupo.setValue(zValue);  }
  public long getIdtipo() throws Exception {     return pIdGrupo.getValue();  }
  public void setIdDetail(long zValue) throws Exception {    pIdDetail.setValue(zValue);  }
  public long getIdDetail() throws Exception {     return pIdDetail.getValue();  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setDK(String zValue) throws Exception {    pDK.setValue(zValue);  }
  public String getDK() throws Exception {     return pDK.getValue();  }
  public void setViewAll(boolean zValue) throws Exception {    pViewAll.setValue(zValue);  }
  public boolean getViewAll() throws Exception {     return pViewAll.getValue();  }
 

  public BizGrupoDKDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany);
    this.addItem( "id_grupo", pIdGrupo);
    this.addItem( "id_detail", pIdDetail );
    this.addItem( "dk", pDK );
    this.addItem( "view_all", pViewAll );

  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_detail", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 50 );
    this.addFixedItem( FIELD, "id_grupo", "id_tipo", true, false, 64 );
    this.addFixedItem( FIELD, "dk", "dk", true, false, 250 );
    this.addFixedItem( FIELD, "view_all", "Ver todos", true, false, 250 );
 }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_GRUPO_DK_DETAIL"; }

  public void processInsert() throws Exception {
   	super.processInsert();
  }
  public void processUpdate() throws Exception {
   	super.processUpdate();
  };

  /**
   * Default Read() method
   */
  public boolean Read( long zIdtipoDetail) throws Exception { 
    this.addFilter( "id_detail",  zIdtipoDetail ); 
    return this.read(); 
  } 
  BizGrupoDK objGrupo;
  public BizGrupoDK getObjGrupoDk() throws Exception {
  	if (objGrupo!=null) return objGrupo;
  	BizGrupoDK g = new BizGrupoDK();
  	g.dontThrowException(true);
  	if (!g.Read(getIdtipo())) return null;
  	return objGrupo=g;
  	
  }
 }
