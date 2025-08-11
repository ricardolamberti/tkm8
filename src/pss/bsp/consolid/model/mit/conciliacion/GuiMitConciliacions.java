package pss.bsp.consolid.model.mit.conciliacion;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiMitConciliacions extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiMitConciliacions() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiMitConciliacion.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 5052;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Reporte";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
	//	addActionNew(1, "Nuevo Registro");
	}

	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("fecha");
		zLista.AddColumnaLista("nor_op");
		zLista.AddColumnaLista("tipo_op");
		zLista.AddColumnaLista("pnr");
		zLista.AddColumnaLista("importe_mit");
		zLista.AddColumnaLista("importe_pnr");
		zLista.AddColumnaLista("diferencia");
		zLista.AddColumnaLista("estado_conciliacion");
		zLista.AddColumnaLista("detalle");
	}


	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Nro Operación", "nor_op").setOperator("=");
		zFiltros.addEditResponsive("Tipo Operación", "tipo_op").setOperator("ilike");
		zFiltros.addEditResponsive("PNR", "pnr").setOperator("ilike");
		zFiltros.addComboResponsive("Estado", "estado_conciliacion").setPermiteVacio(true);
		zFiltros.addIntervalCDateResponsive("Fecha", "fecha");
	}



}
