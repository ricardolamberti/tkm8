package pss.bsp.consolid.model.mit.detail;

import pss.common.regions.divitions.GuiPaises;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssGoogleMap;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFormLov;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

import java.awt.Rectangle;
import java.awt.Dimension;

public class FormMitDetail extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormMitDetail() throws Exception {

	}

	public GuiMitDetail GetWin() {
		return (GuiMitDetail) GetBaseWin();
	}
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
	    AddItemEdit(null, CHAR, OPT, "company").setHide(true);
	    AddItemEdit(null, UINT, OPT, "id").setHide(true);
	    AddItemEdit(null, CHAR, OPT, "id_report").setHide(true);

	    JFormPanelResponsive row = this.AddItemRow();
	    row.AddItemEdit("Fecha", DATE, OPT, "fecha").setSizeColumns(4).setReadOnly(true);
	    row.AddItemEdit("Nro Operación", CHAR, OPT, "nor_op").setSizeColumns(4).setReadOnly(true);
	    
	    row = this.AddItemRow();
	    row.AddItemEdit("Tipo Operación", CHAR, OPT, "tipo_op").setSizeColumns(4).setReadOnly(true);
	    row.AddItemEdit("PNR", CHAR, OPT, "pnr").setSizeColumns(4).setReadOnly(true);

	    row = this.AddItemRow();
	    row.AddItemEdit("Nro Tarjeta", CHAR, OPT, "nro_tarjeta").setSizeColumns(4).setReadOnly(true);
	    row.AddItemEdit("Agencia", CHAR, OPT, "agencia").setSizeColumns(4).setReadOnly(true);

	    row = this.AddItemRow();
	    row.AddItemEdit("Nombre Agencia", CHAR, OPT, "nombre_agencia").setSizeColumns(6).setReadOnly(true);

	    row = this.AddItemRow();
	    row.AddItemEdit("Aerolínea", CHAR, OPT, "aerolinea").setSizeColumns(4).setReadOnly(true);
	    row.AddItemEdit("Estado", CHAR, OPT, "status").setSizeColumns(4).setReadOnly(true);

	    row = this.AddItemRow();
	    row.AddItemEdit("Autorización", CHAR, OPT, "autorizacion").setSizeColumns(4).setReadOnly(true);
	    row.AddItemEdit("Globalizador", CHAR, OPT, "globalizador").setSizeColumns(4).setReadOnly(true);

	    row = this.AddItemRow();
	    row.AddItemEdit("Importe", FLOAT, OPT, "importe").setSizeColumns(4);

	    // Boletos
	    for (int i = 1; i <= 10; i++) {
	        String field = "boleto" + i;
	        if ((i - 1) % 3 == 0) row = this.AddItemRow();
	        row.AddItemEdit("Boleto " + i, CHAR, OPT, field).setSizeColumns(4).setReadOnly(true);
	    }
	}



	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
