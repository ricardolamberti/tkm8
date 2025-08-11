package pss.common.event.action;

import pss.common.event.action.history.GuiSqlEventHistories;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiSqlEventAction extends JWin {

	


	/**
   * Constructor de la Clase
   */
  public GuiSqlEventAction() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizSqlEventAction(); }
  public int GetNroIcono()   throws Exception { return 15010; }
  public String GetTitle()   throws Exception { return "Accion"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
  	if (GetVision().equals("ONLYMSG"))
  		return FormSqlEventActionMessage.class;
  	return FormSqlEventAction.class; 
  	
  }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormSqlEventActionNew.class; }
  public String  getKeyField() throws Exception { return "id_action"; }
  public String  getDescripField() { return "correo"; }
  public BizSqlEventAction GetcDato() throws Exception { return (BizSqlEventAction) this.getRecord(); }

  @Override
	public void createActionMap() throws Exception {
   	this.addAction(10, "Historico Avisos", null, 63, false, false, false, "Group" );
   	this.addAction(20, "Ver Mensaje", null, 63, false, false, false, "Group" );
   	super.createActionMap();
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10 ) return new JActWins(this.getAcciones());
  	if (a.getId()==20 ) return new JActQuery(this.getVerMensaje());
		return null;
	}
	
	GuiSqlEventAction actionCopy;
	
  public GuiSqlEventAction getVerMensaje() throws Exception {
  	//if (actionCopy!=null) return actionCopy;
//  	GuiSqlEventAction copy = new GuiSqlEventAction();
//  	copy.GetcDato().copyProperties(this.getRecord());
//  	copy.GetcDato().setMensaje(GetcDato().getPreviewMensaje());
//  	copy.SetVision("ONLYMSG");
//  	return actionCopy=copy;
  	this.GetcDato().clearObjSqlEvent();
  	this.GetcDato().setMensaje(GetcDato().getPreviewMensaje());
  	this.SetVision("ONLYMSG");
  	return this;
  }
  public JWins getAcciones() throws Exception {
  	GuiSqlEventHistories	c = new GuiSqlEventHistories();
  	c.getRecords().addFilter("company", this.GetcDato().getCompany());
   	c.getRecords().addFilter("id_action", this.GetcDato().getIdaction());
  	return c;
  }
  
	
 }
