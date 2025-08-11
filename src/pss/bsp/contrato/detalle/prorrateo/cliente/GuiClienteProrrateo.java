package pss.bsp.contrato.detalle.prorrateo.cliente;

import pss.bsp.contrato.detalle.prorrateo.tickets.GuiTicketProrrateos;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiClienteProrrateo extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiClienteProrrateo() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizClienteProrrateo(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Liquidación "+GetcDato().getClienteDescripcion(); }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormClienteProrrateo.class; }
  public String  getKeyField() throws Exception { return "cliente"; }
  public String  getDescripField() { return "descripcion"; }
  public BizClienteProrrateo GetcDato() throws Exception { return (BizClienteProrrateo) this.getRecord(); }
 
  JFilterMap filter;


  public JFilterMap getFilterMap() {
		return filter;
	}


	public void setFilterMap(JFilterMap action) {
		this.filter = action;
	}

  
  @Override
  public void createActionMap() throws Exception {
   	this.addAction(50, "detalle", null, 5012, true, true, true, "Group" ).setAccessToDetail(true);

  }
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 50)  return new JActWins(getDetalle());

	  return super.getSubmitFor(a);
  }

  public GuiTicketProrrateos getDetalle()   throws Exception {
  	GuiTicketProrrateos tkts =new GuiTicketProrrateos();
  	tkts.getRecords().addFilter("fecha", GetcDato().getDetalle().getFilterValue("fecha"));
  	tkts.setRecords(GetcDato().getDetalle());
  	return tkts;
  }



 }
