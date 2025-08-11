package pss.common.issueTracker.issue.note;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiIssueNote extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiIssueNote() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizIssueNote(); }
  @Override
	public int GetNroIcono()       throws Exception { return 721; }
  @Override
	public String GetTitle()       throws Exception { return "Nota"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormIssueNote.class; }
  @Override
	public String getKeyField()   throws Exception { return "note_id"; }
  @Override
	public String getDescripField()                  { return "issue_id"; }


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
  public BizIssueNote GetcDato() throws Exception {
    return (BizIssueNote) this.getRecord();
  }



}
