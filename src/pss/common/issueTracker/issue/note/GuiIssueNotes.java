package pss.common.issueTracker.issue.note;

import pss.common.issueTracker.setting.BizIssueTrackerSetting;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.lists.JWinList;

public class GuiIssueNotes extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiIssueNotes() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiIssueNote.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 721;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Notas";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nueva Nota");
	}


  @Override
  public boolean OkAction(BizAction a) throws Exception {
    	if ( a.getId()==1 ) return !this.GetVision().equals(BizIssueTrackerSetting.STATUS_CLOSED);
    	return super.OkAction(a);
  }
	
	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
			zLista.AddColumnaLista("note_id");
		zLista.AddColumnaLista("date_submitted");
		zLista.AddColumnaLista("descr_usuario");
		zLista.AddColumnaLista("handler_user");
	}


	
}
