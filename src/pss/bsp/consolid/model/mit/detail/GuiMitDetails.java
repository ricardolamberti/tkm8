package pss.bsp.consolid.model.mit.detail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiMitDetails extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiMitDetails() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiMitDetail.class;
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
		zLista.AddColumnaLista("nro_tarjeta");
		zLista.AddColumnaLista("agencia");
		zLista.AddColumnaLista("nombre_agencia");
		zLista.AddColumnaLista("aerolinea");
		zLista.AddColumnaLista("status");
		zLista.AddColumnaLista("autorizacion");
		zLista.AddColumnaLista("globalizador");
		zLista.AddColumnaLista("importe");
		zLista.AddColumnaLista("boleto1");
		zLista.AddColumnaLista("boleto2");
		zLista.AddColumnaLista("boleto3");
		zLista.AddColumnaLista("boleto4");
		zLista.AddColumnaLista("boleto5");
		zLista.AddColumnaLista("boleto6");
		zLista.AddColumnaLista("boleto7");
		zLista.AddColumnaLista("boleto8");
		zLista.AddColumnaLista("boleto9");
		zLista.AddColumnaLista("boleto10");
	}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Nro Operación", "nor_op").setOperator("ilike");
		zFiltros.addEditResponsive("Tipo Operación", "tipo_op").setOperator("ilike");
		zFiltros.addEditResponsive("PNR", "pnr").setOperator("ilike");
		zFiltros.addEditResponsive("Agencia", "agencia").setOperator("ilike");
		zFiltros.addEditResponsive("Aerolínea", "aerolinea").setOperator("ilike");
		zFiltros.addEditResponsive("Autorización", "autorizacion").setOperator("ilike");
	}


}
