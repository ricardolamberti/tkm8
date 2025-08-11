package pss.common.customList.config.customlist;

import pss.common.customList.config.field.campo.GuiCampos;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormCustomListResultLista extends JBaseForm {

	private static final long serialVersionUID = 1226426905817L;

	/**
	 * Constructor de la Clase
	 */
	public FormCustomListResultLista() throws Exception {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GuiCustomList getWin() {
		return (GuiCustomList) getBaseWin();
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
		AddItemEdit(null, CHAR, OPT, "company").setVisible(false);
		AddItemEdit(null, UINT, OPT, "list_id").setVisible(false);
		AddItemEdit(null, CHAR, OPT, "description").setVisible(false);
		AddItemEdit(null, CHAR, OPT, "record_owner").setVisible(false);
		AddItemEdit(null, CHAR, OPT, "rel_id").setVisible(false);
		AddItemEdit(null, CHAR, OPT, "agrupado").setVisible(false);
		AddItemEdit(null, CHAR, OPT, "modelo").setVisible(false);
		AddItemEdit(null, CHAR, OPT, "view_table").setVisible(false);
    AddItemEdit(null, CHAR, OPT, "invisible" ).setVisible(false);
	
		if (getWin().GetVision().equals("PREVIEW"))
  		AddItemLabel("Vista parcial, 'ejecutar' para ver completo");

		// AddItemTab( 100);
    if (getWin().showFilter()) { //ejecutar
			JFormPanelResponsive row1 = AddItemColumn(2);
			JFormPanelResponsive row2 = AddItemColumn(10).setZoomtofit(1500);

			row1.AddItemTableExpand("Refinar busqueda", "campos", GuiCampos.class, true, "FILTROS").setRowToolbarPos(JBaseWin.TOOLBAR_IN).setSizeRow("col-sm-12");
			row1.AddItemButton("Aplicar Filtros", -1, true).setRefreshForm(true);
			row2.AddItemList(100).setMode(JFormLista.MODE_CUSTOMLIST);//.setForceHidePaginationBar(true);
		} else if (getWin().showZoom()) { //preview
			JFormPanelResponsive row1 = AddItemColumn(12)/*.setZoomtofit(1500)*/;
			row1.AddItemList(100).setMode(JFormLista.MODE_CUSTOMLIST).setShowFooterBar(false).setForceHidePaginationBar(true);
		} else { //imprimir
			JFormPanelResponsive row1 = AddItemColumn().setZoomtofit(2500);
			row1.AddItemList(100).setMode(JFormLista.MODE_CUSTOMLIST).setShowFooterBar(false).setAllowSortedColumns(false).setForceHidePaginationBar(true);
//			row1.setWidth(1500);
//			row1.AddItemList(100).setZoomtofit(1280).setAllowSortedColumns(false).setForceHidePaginationBar(true);
//			AddItemList(100).setForceHidePaginationBar(true);
		}
	}

} // @jve:decl-index=0:visual-constraint="36,5"
