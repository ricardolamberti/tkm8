package pss.common.customList.config.customlist;

import pss.common.customList.config.field.campo.GuiCampos;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormCustomListResultMatriz extends JBaseForm {

	private static final long serialVersionUID = 1226426905817L;

	/**
	 * Constructor de la Clase
	 */
	public FormCustomListResultMatriz() throws Exception {
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
    AddItemEdit( null, CHAR, OPT, "invisible" ).setVisible(false);
  	if (getWin().GetVision().equals("PREVIEW")||getWin().GetVision().equals("ONLYVIEW"))
  		AddItemLabel("Vista parcial, 'ejecutar' para ver completo");

    if (getWin().showFilter()) {

			JFormPanelResponsive row1 = AddItemColumn(2);
			JFormPanelResponsive row2 = AddItemColumn(10).setZoomtofit(1500);

			row1.AddItemTableExpand("Refinar busqueda", "campos", GuiCampos.class, true, "FILTROS").setRowToolbarPos(JBaseWin.TOOLBAR_IN).setSizeRow("col-sm-12");
			row1.AddItemButton("Aplicar Filtros", -1, true).setRefreshForm(true);
			row2.AddItemMatrix(100, "Informe").setMode(JFormLista.MODE_CUSTOMLIST).setForceHidePaginationBar(true);
		} else if (getWin().showZoom()) {
			JFormPanelResponsive row1 = AddDivPanel();//.setZoomtofit(1500);
			row1.AddItemMatrix(100, "Informe").setMode(JFormLista.MODE_CUSTOMLIST).setShowFooterBar(false).setAllowSortedColumns(false).setForceHidePaginationBar(true).setToolBar(JBaseWin.TOOLBAR_NONE);
		} else {
			JFormPanelResponsive row1 = AddItemColumn(12).setZoomtofit(1500);
			row1.AddItemMatrix(100).setMode(JFormLista.MODE_CUSTOMLIST).setShowFooterBar(false).setAllowSortedColumns(false).setForceHidePaginationBar(true).setToolBar(JBaseWin.TOOLBAR_NONE);
//			AddItemMatrix(100, "Informe").setForceHidePaginationBar(true);
		}
    
    
	}

} // @jve:decl-index=0:visual-constraint="36,5"
