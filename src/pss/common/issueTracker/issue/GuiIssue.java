package pss.common.issueTracker.issue;

import pss.common.issueTracker.issue.attachment.GuiIssueAttachments;
import pss.common.issueTracker.issue.history.GuiIssueHistorys;
import pss.common.issueTracker.issue.note.BizIssueNote;
import pss.common.issueTracker.issue.note.GuiIssueNote;
import pss.common.issueTracker.issue.note.GuiIssueNotes;
import pss.common.issueTracker.issueHandlerUsers.GuiIssueHandlerUser;
import pss.common.issueTracker.issueHandlerUsers.GuiIssueHandlerUsers;
import pss.common.issueTracker.setting.BizIssueTrackerSetting;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;

public class GuiIssue extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiIssue() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizIssue(); }
  @Override
	public int GetNroIcono()       throws Exception { 
		if (this.GetcDato().isClosed()) return 26;
		return 89;
  }

  @Override
	public String GetTitle()       throws Exception { return "Incidencia"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormIssue.class; }
  @Override
	public String getKeyField()   throws Exception { return "issue_id"; }
  @Override
	public String getDescripField()                  { return "summary"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    this.addAction(100, "Asignar", null, 10013, true, true);
    this.addAction(110, "Resolver", null, 6027, true, true);
    this.addAction(120, "Pedir más Info", null, 15012, true, true);
    this.addAction(130, "Recibir", null, 15058, true, true);
    this.addAction(140, "Re-Abrir", null, 6027, true, true);    
    this.addAction(150, "Cerrar", null, 26, true, true);
    this.addAction(200, "Notas", null, 721, false, false);
    this.addAction(210, "Adjuntos", null, 10079, false, false);
    this.addAction(220, "Historia", null, 10082, false, false);
    
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if ( a.getId()==100 ) return BizIssueTrackerSetting.canAsignar(this.GetcDato().getStatus());
  	if ( a.getId()==110 ) return BizIssueTrackerSetting.canResolver(this.GetcDato().getStatus());
  	if ( a.getId()==120 ) return BizIssueTrackerSetting.canMoreData(this.GetcDato().getStatus());
  	if ( a.getId()==130 ) return BizIssueTrackerSetting.canRecibir(this.GetcDato().getStatus());
  	if ( a.getId()==140 ) return BizIssueTrackerSetting.canReOpen(this.GetcDato().getStatus());
  	if ( a.getId()==150 ) return BizIssueTrackerSetting.canClose(this.GetcDato().getStatus());
  	return super.OkAction(a);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==100) return new JActWinsSelect(getUsuarios(), this, false);	
		if (a.getId()==110) return new JActNew(this.getNewNote(BizIssueTrackerSetting.STATUS_RESOLVED), 4);
		if (a.getId()==120) return new JActNew(this.getNewNote(BizIssueTrackerSetting.STATUS_MOREDATA), 4);
		if (a.getId()==130) return new JActNew(this.getNewNote(BizIssueTrackerSetting.STATUS_ARRIVED), 4);
		if (a.getId()==140) return new JActNew(this.getNewNote(BizIssueTrackerSetting.STATUS_REOPENED), 4);
		if (a.getId()==150) return new JActSubmit(this, a.getId()) {
			public void submit() throws Exception {
				GetcDato().execChangeStatus(BizIssueTrackerSetting.STATUS_CLOSED, null);
			}
		};
  	if ( a.getId()==200 ) return new JActWins(this.getNotes());
  	if ( a.getId()==210 ) return new JActWins(this.getAttachments());
		if ( a.getId()==220 ) return new JActWins(this.getHistorys());
  	return null;
  }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizIssue GetcDato() throws Exception {
    return (BizIssue) this.getRecord();
  }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }

  public JWins getNotes() throws Exception {
  	GuiIssueNotes wins = new GuiIssueNotes();
  	wins.setRecords(this.GetcDato().getNotes());
  	wins.SetVision(this.GetcDato().getStatus());
  	return wins;
  }
  
  public JWins getAttachments() throws Exception {
  	GuiIssueAttachments wins = new GuiIssueAttachments();
  	wins.setRecords(this.GetcDato().getAttachments());
  	wins.SetVision(this.GetcDato().getStatus());
  	return wins;
  }
  
  public JWins getHistorys() throws Exception {
  	GuiIssueHistorys wins = new GuiIssueHistorys();
  	wins.setRecords(this.GetcDato().getHistorys());
  	return wins;
  }

  
	public JWins getUsuarios() throws Exception {
		GuiIssueHandlerUsers w = new GuiIssueHandlerUsers();
		w.getRecords().addFilter("active", true);
		return w;
	}
	
  public JWin getNewNote(String status) throws Exception {
  	GuiIssueNote win = new GuiIssueNote();
	   win.GetcDato().setCompany(this.GetcDato().getCompany());
	   win.GetcDato().setIssueId(this.GetcDato().getIssueId());
	   win.GetcDato().setStatusToApply(status);
	   win.GetcDato().setAvoidHistory(true);
	   if (status.equals(BizIssueTrackerSetting.STATUS_ARRIVED)) {
	  	 win.GetcDato().setDescription("Su incidencia ha sido recibida, y está siendo analizada");
	   }
	   
	   win.setDropListener(this);
	   return win;
 }
	
	
	@Override
	public JAct Drop(JBaseWin w) throws Exception {
		JBaseRecord record=w.GetBaseDato();
		record.notifyDropOK();
		if (w instanceof GuiIssueHandlerUser)
			this.GetcDato().execAsignarUsuario(((GuiIssueHandlerUser)w).GetcDato());
		if (w instanceof GuiIssueNote) {
			BizIssueNote note = ((GuiIssueNote)w).GetcDato();
			note.execProcessInsert();
			this.GetcDato().execChangeStatus(note.getStatusToApply(),note.getHandlerUser());
		}
		return null;
	}

	@Override
	public String getFieldForeground(String col) throws Exception {
		if (col.equals("ICONO"))
			if (this.GetcDato().isResolved()) 
				return "#5cb85c"; //verde
			else
				return "red";
		return null;
	}
  
}
