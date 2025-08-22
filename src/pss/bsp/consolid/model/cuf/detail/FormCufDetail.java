package pss.bsp.consolid.model.cuf.detail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormCufDetail extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormCufDetail() throws Exception {

	}

	public GuiCufDetail GetWin() {
		return (GuiCufDetail) GetBaseWin();
	}
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null, CHAR, OPT, "company").setHide(true);
		AddItemEdit(null, UINT, OPT, "id").setHide(true);
		AddItemEdit(null, UINT, OPT, "id_cuf").setHide(true);

		JFormPanelResponsive row = this.AddItemRow();
		row.AddItemEdit("TACN", CHAR, OPT, "tacn").setSizeColumns(4);

		row = this.AddItemRow();
		row.AddItemEdit("EFCO", FLOAT, OPT, "efco").setSizeColumns(4);
		row.AddItemEdit("Agente", CHAR, OPT, "agente").setSizeColumns(6);

		row = this.AddItemRow();
		row.AddItemEdit("DK", CHAR, OPT, "dk").setSizeColumns(4);
		row.AddItemEdit("AL", CHAR, OPT, "al").setSizeColumns(4);

		row = this.AddItemRow();
		row.AddItemEdit("Ruta", CHAR, OPT, "ruta").setSizeColumns(6);

		row = this.AddItemRow();
		row.AddItemEdit("Correo", CHAR, OPT, "correo").setSizeColumns(6);

		row = this.AddItemRow();
		row.AddItemEdit("Uso CFDI", CHAR, OPT, "uso_cfdi").setSizeColumns(4);
		row.AddItemEdit("Forma de Pago", CHAR, OPT, "forma_pago").setSizeColumns(4);
	}


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
