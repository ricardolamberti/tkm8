package pss.common.issueTracker.issueHandlerUsers;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.lists.JWinList;

public class GuiIssueHandlerUsers extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiIssueHandlerUsers() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiIssueHandlerUser.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 89;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Usuarios";
	}
	

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
		//this.addAction(10, "Nueva Incidencia", null, GuiIcon.MAS_ICON, true, true);
	}

	public JAct getSubmitFor(BizAction a) throws Exception {

		return super.getSubmitFor(a);
	}
	
	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("usuario");
		zLista.AddColumnaLista("usuario_map_sistema");
		zLista.AddColumnaLista("descr_usuario");
		zLista.AddColumnaLista("email");
		zLista.AddColumnaLista("active");
	}

}