package pss.common.issueTracker.issue.history;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiIssueHistory extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiIssueHistory() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizIssueHistory(); }
  @Override
	public int GetNroIcono()       throws Exception { return 10082; }
  @Override
	public String GetTitle()       throws Exception { return "Nota"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormIssueHistory.class; }
  @Override
	public String getKeyField()   throws Exception { return "history_id"; }
  @Override
	public String getDescripField()                  { return "description"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	return super.getSubmitFor(a);
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }




  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizIssueHistory GetcDato() throws Exception {
    return (BizIssueHistory) this.getRecord();
  }



}
