package  pss.bsp.consolid.model.gruposDK;

import pss.bsp.consolid.model.gruposDK.detail.BizGrupoDKDetail;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizGrupoDK extends JRecord {

  private JLong pIdGrupo = new JLong();
  private JString pCompany = new JString();
  private JString pDescripcion = new JString();
  private JString pIdentificador = new JString();
  private JBoolean pAgrupLiq = new JBoolean();
  private JBoolean pAgrupRepoDK = new JBoolean();
  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdGrupo(long zValue) throws Exception {    pIdGrupo.setValue(zValue);  }
  public long getIdGrupo() throws Exception {     return pIdGrupo.getValue();  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public void setIdentificador(String zValue) throws Exception {    pIdentificador.setValue(zValue);  }
  public String getIdentificador() throws Exception {     return pIdentificador.getValue();  }
  public void setAgrupLiq(boolean zValue) throws Exception {    pAgrupLiq.setValue(zValue);  }
  public boolean getAgrupLiq() throws Exception {     return pAgrupLiq.getValue();  }
  public void setAgrupRepoDK(boolean zValue) throws Exception {    pAgrupRepoDK.setValue(zValue);  }
  public boolean getAgrupRepoDK() throws Exception {     return pAgrupRepoDK.getValue();  }


  public BizGrupoDK() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany);
    this.addItem( "id_grupo", pIdGrupo);
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "identificador", pIdentificador );
    this.addItem( "use_liq", pAgrupLiq );
    this.addItem( "use_repo_dk", pAgrupRepoDK );
       
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_grupo", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, false, 250 );
    this.addFixedItem( FIELD, "identificador", "Identificador", true, false, 50 );
    this.addFixedItem( FIELD, "use_liq", "Uso en Liq.", true, false, 1 );
    this.addFixedItem( FIELD, "use_repo_dk", "Uso en repo DK", true, false, 1 );
 }
  
  
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_GRUPO_DK"; }

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
    this.addFilter( "id_grupo",  zIdtipo ); 
    return this.read(); 
  } 
  
	public JRecords<BizGrupoDKDetail> getObjDetalle() throws Exception {
		JRecords<BizGrupoDKDetail> dets= new JRecords<BizGrupoDKDetail>(BizGrupoDKDetail.class);
		dets.addFilter("company", getCompany());
		dets.addFilter("id_grupo", getIdGrupo());
		return dets;
	}
	public String getDetalleViewAll() throws Exception {
		JRecords<BizGrupoDKDetail> dets= new JRecords<BizGrupoDKDetail>(BizGrupoDKDetail.class);
		dets.addFilter("company", getCompany());
		dets.addFilter("id_grupo", getIdGrupo());
		dets.addFilter("view_all", true);
		dets.setTop(1);
		dets.readAll();
		return dets.nextRecord()?dets.getRecord().getDK():null;
	}
 }
