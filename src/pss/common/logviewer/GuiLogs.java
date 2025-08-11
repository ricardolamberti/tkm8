package  pss.common.logviewer;

import pss.core.services.records.JBaseRecord;
import pss.core.tools.PssLogger;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.lists.JWinList;

public class GuiLogs extends JWins {

	public GuiLogs() throws Exception {
	}
	
  @Override
	public void createActionMap() throws Exception {
    addAction( 10, "Activar Log", null, 10033 , true, true, false, "Group" );
    addAction( 20, "DesActivar Log", null, 1085 , true, true, false, "Group" );
  }
  
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActSubmit(this, a.getId()) {
				public void submit() throws Exception {
					activar_desactivarLog(true);
				}
			};
			if (a.getId() == 20)
				return new JActSubmit(this, a.getId()) {
					public void submit() throws Exception {
						activar_desactivarLog(false);
					}
				};
  	return super.getSubmitFor(a);
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId() == 10) return PssLogger.getNoSQLLog();
  	if (a.getId() == 20)return !PssLogger.getNoSQLLog();
  	return super.OkAction(a);
  }
  

	public JBaseRecord ObtenerBaseDato() throws Exception {
		return new BizLogs();
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 1012;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Logs";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiLog.class;
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("file_name");
		// zLista.AddColumnaLista("is_current");
		zLista.AddColumnaLista("full_path");
	}
	
	private void activar_desactivarLog(Boolean activar) throws Exception {
		if (activar)
			PssLogger.habilitarSQLLog();
		else
			PssLogger.deshabilitarSQLLog();
		return;
	}

}
