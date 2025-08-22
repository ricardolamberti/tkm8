package pss.bsp.consolid.model.repoDK;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiRepoDKs extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiRepoDKs() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiRepoDK.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 5052;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Analisis x DK";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Registro");
	}

	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("date_from");
		zLista.AddColumnaLista("date_to");
		}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addCDateTimeResponsive("Fecha", "date_to");
	}
	@Override
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("date_to") && control.hasValue()) {
			this.getRecords().addFilter("date_from", control.getValue(), "<=").setDynamic(true);
			this.getRecords().addFilter("date_to", control.getValue(), ">=").setDynamic(true);
			return;
		}
		super.asignFilterByControl(control);
	}
}
