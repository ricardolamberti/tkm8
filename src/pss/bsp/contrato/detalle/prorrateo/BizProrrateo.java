package pss.bsp.contrato.detalle.prorrateo;

import pss.bsp.dk.GuiClienteDKs;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.mailing.GuiMailingPersona;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JList;
import pss.core.win.IControl;
import pss.core.win.JControlWin;
import pss.core.win.JWins;

public class BizProrrateo extends JRecord {

  protected JString pCompany = new JString();
  
  protected JLong pContrato = new JLong();
  protected JLong pIdDetalle = new JLong();
  protected JLong pId = new JLong();
  
  protected JMultiple pCliente = new JMultiple();
  protected JFloat pPorcentaje = new JFloat();
	  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 

  public void setContrato(long zValue) throws Exception {    pContrato.setValue(zValue);  }
  public long getContrato() throws Exception {     return pContrato.getValue();  }
  public boolean isNullContrato() throws Exception { return  pContrato.isNull(); } 
  public void setNullToContrato() throws Exception {  pContrato.setNull(); } 

  public void setIdDetalle(long zValue) throws Exception {    pIdDetalle.setValue(zValue);  }
  public long getIdDetalle() throws Exception {     return pIdDetalle.getValue();  }
  public boolean isNullIdDetalle() throws Exception { return  pIdDetalle.isNull(); } 
  public void setNullToIdDetalle() throws Exception {  pIdDetalle.setNull(); } 

  public void setIdProrrateo(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getIdProrrateo() throws Exception {     return pId.getValue();  }
  public boolean isNullIdProrrateo() throws Exception { return  pId.isNull(); } 
  public void setNullToIdProrrateo() throws Exception {  pId.setNull(); } 

  public void setCliente(JList<String> zValue) throws Exception {    pCliente.setValue(zValue);  }
  public JList<String> getCliente() throws Exception {     return pCliente.getValue();  }
  public boolean isNullCliente() throws Exception { return  pCliente.isNull(); } 
  public void setNullToCliente() throws Exception {  pCliente.setNull(); } 

  public void setComision(double zValue) throws Exception {    pPorcentaje.setValue(zValue);  }
  public double getComision() throws Exception {     return pPorcentaje.getValue();  }
  public boolean isNullComision() throws Exception { return  pPorcentaje.isNull(); } 
  public void setNullToComision() throws Exception {  pPorcentaje.setNull(); } 

  /**
   * Constructor de la Clase
   */
  public BizProrrateo() throws Exception {
  }

  public void processInsertClon() throws Exception {
		super.processInsert();
	}


  public void createProperties() throws Exception {
    this.addItem( "id_prorrateo", pId );
    this.addItem( "company", pCompany );
    this.addItem( "contrato", pContrato );
    this.addItem( "detalle", pIdDetalle );
    this.addItem( "cliente", pCliente );
    this.addItem( "valor", pPorcentaje );
    
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_prorrateo", "Id", false, false, 64 ).setHide(true);
    this.addFixedItem( KEY, "company", "Company", true, true, 15 ).setHide(true);
    this.addFixedItem( FIELD, "contrato", "contrato", true, true, 32 ).setHide(true);
    this.addFixedItem( FIELD, "detalle", "id detalle", true, true, 32 ).setHide(true);
    this.addFixedItem( FIELD, "cliente", "Cliente", true, false, 4000 ).setClase(GuiMailingPersona.class).setControl(getClientesIdCorto());
    this.addFixedItem( FIELD, "valor", "Comision", true, true, 18,2 );

  }
  
	public IControl getClientesIdCorto() throws Exception {

		return new JControlWin() {
			@Override
			public JWins getRecords(boolean bOneRow) throws Exception {
				GuiClienteDKs wins = new GuiClienteDKs();
				wins.getRecords().addFilter("company", getRecord().getProp("company"));
				wins.getRecords().addOrderBy("dk");
				return wins;
			}
		};
	}
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_prorrateo"; }


  public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass(GuiProrrateos.class.getName());
  	rels.hideField("detalle");
  	//rels.hideField("company");
  	rels.hideField("id_prorrateo");
  	rels.hideField("contrato");

  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zLinea ) throws Exception { 
    addFilter( "id_prorrateo",  zLinea ); 
    return read(); 
  } 
  

}
