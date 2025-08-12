package pss.bsp.contrato.detalleAvianca;

import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormDetalleAvianca extends JBaseForm {

	private static final long serialVersionUID = 1446860278358L;

	/**
	 * Constructor de la Clase
	 */
	public FormDetalleAvianca() throws Exception {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GuiDetalleAvianca getWin() {
		return (GuiDetalleAvianca) getBaseWin();
	}

	/**
	 * Inicializacion Grafica
	 */
	protected void jbInit() throws Exception {
	}

	/**
	 * Linkeo los campos con la variables del form
	 */
	public void InicializarPanel(JWin zWin) throws Exception {
		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItemEdit("company", CHAR, REQ, "company").SetValorDefault(BizUsuario.getUsr().getCompany());
		AddItemEdit("id", UINT, OPT, "id");
		AddItemEdit("linea", UINT, OPT, "linea");
		AddItemEdit("variable", CHAR, OPT, "variable");
		AddItemEdit("variable_ganancia", CHAR, OPT, "variable_ganancia");
		AddItemEdit("fms_max", CHAR, OPT, "fms_max");
		AddItemEdit("fms_min", CHAR, OPT, "fms_min");
		AddItemEdit("Limite", CHAR, OPT, "Limite");
		AddItemEdit("periodo_detalle", CHAR, OPT, "periodo_detalle");

		JFormControl c = AddItemCheck("acumulativo", OPT, "acumulativo");
		c.SetValorDefault(false);
		AddItemCombo("periodo", CHAR, OPT, "periodo", JWins.createVirtualWinsFromMap(BizDetalleAvianca.getPeriodos()));
//    AddItem( getValorActual(), FLOAT, OPT, "valor_actual" ).SetReadOnly(true);
		AddItemEdit("valor_fcontrato", FLOAT, OPT, "valor_fcontrato").SetReadOnly(true);
//    AddItem( getValorObjetivo(), FLOAT, OPT, "valor_total" ).setVisible(true);
		AddItemEdit("valor_totalcontrato", FLOAT, OPT, "valor_totalcontrato").setVisible(true);
		AddItemEdit("conclusion", CHAR, OPT, "conclusion").SetReadOnly(true);
		AddItemEdit("descripcion", CHAR, OPT, "descripcion").SetReadOnly(true);
//    AddItem( getNivel1(), CHAR, OPT, "nivel_alcanzado" ).SetReadOnly(true);
//    AddItem( getEvalua1(), FLOAT, OPT, "ganancia" ).SetReadOnly(true);
		AddItemEdit("nivel_alcanzado_estimada", CHAR, OPT, "nivel_alcanzado_estimada").SetReadOnly(true);
		AddItemEdit("ganancia_estimada", FLOAT, OPT, "ganancia_estimada").SetReadOnly(true);
		JFormImageResponsive i = AddItemImage("", "imagen1");
		i.setSource(JPssImage.SOURCE_SCRIPT);

		i = AddItemImage("", "imagen2");
		i.setSource(JPssImage.SOURCE_SCRIPT);

		AddItemCheck("kicker", OPT, "kicker");
		JFormTabPanelResponsive tabs = new JFormTabPanelResponsive();
		tabs.AddItemList(32);
		tabs.AddItemList(35);
		tabs.AddItemList(33);

	}

	@Override
	public void OnPostShow() throws Exception {
		checkControls("");
		super.OnPostShow();
	}

	private JWins getIndicador(boolean one) throws Exception {
		GuiSqlEvents g = new GuiBSPSqlEvents();
		if (one) {
			g.getRecords().addFilter("id", getWin().GetcDato().getVariable());
		} else {
			g.getRecords().addFilter("company", getControles().findControl("company").getValue());
		}
		return g;
	}

	private JWins getIndicadorGanancia(boolean one) throws Exception {
		GuiSqlEvents g = new GuiBSPSqlEvents();
		if (one) {
			g.getRecords().addFilter("id", getWin().GetcDato().getVariableGanancia());
		} else {
			g.getRecords().addFilter("company", getControles().findControl("company").getValue());
		}
		return g;
	}

	@Override
	public void checkControls(String sControlId) throws Exception {
		if (sControlId.equals("variable")) {
			getWin().GetcDato().clean();
		}
//  		if (GetControles().findControl("acumulativo").getValue().equals("S")) {
//  			GetControles().findControl("periodo").edit(GetModo());
//  			//GetControles().findControl("valor").edit(GetModo());
//  		} else {
//  			GetControles().findControl("periodo").disable();
//  			//GetControles().findControl("valor").disable();
//  			GetControles().findControl("periodo").setValue("");
//  			//GetControles().findControl("valor").setValue(""+getWin().GetcDato().getValor());
//  		}
//  		if (sControlId.equals("")) {
//  			getWin().GetcDato().calcule();
//  			this.GetControles().findControl("valor").setValue(""+getWin().GetcDato().getObjEvent().getValor());
//  		}
		super.checkControls(sControlId);
	}

} // @jve:decl-index=0:visual-constraint="7,4"
