package pss.common.customList.config.customlist;

import pss.common.customList.config.field.campo.GuiCampos;
import pss.common.customList.config.informe.GuiInformes;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssEdit;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JWinGridExpand;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormCustomListInforme  extends JBaseForm {


	private static final long serialVersionUID = 1226426905817L;

	JPssEdit company = new JPssEdit  ();
	JPssEdit listId = new JPssEdit  ();
	JPssEdit secuencia = new JPssEdit  ();

	  /**
	   * Constructor de la Clase
	   */
	  public FormCustomListInforme() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public  GuiCustomListInforme getWin() { return (GuiCustomListInforme) getBaseWin(); }
	  public  GuiCustomList getWinCL() { return (GuiCustomList) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
	  }
	  
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	  	AddItemEdit(null, CHAR,OPT, "company").setVisible(false).SetValorDefault(BizUsuario.getUsr().getCompany());
	  	AddItemEdit(null, UINT,OPT, "list_id").setVisible(false);
	    AddItemEdit( null, CHAR,OPT, "record_owner" ).setVisible(false);
	    AddItemEdit( null, CHAR,OPT, "modelo" ).setVisible(false);
			AddItemEdit(null, CHAR, OPT, "agrupado").setVisible(false);
			AddItemEdit(null, CHAR, OPT, "view_table").setVisible(false);
	    AddItemEdit( null, CHAR, OPT, "invisible" ).setVisible(false);

			String tipo = getWinCL().GetcDato().getInformeDistribution() == null ? JWinGridExpand.GRID2PAR1IMPAR : getWinCL().GetcDato().getInformeDistribution();
			JFormControl c = AddItemCombo("Fuente de datos", CHAR, REQ, "rel_id", new JControlCombo() {
				public JWins getRecords(boolean one) throws Exception {
					return getRelationGalery(one);
				}
			}).setFirstOcur(true).setSizeColumns(2).setSizeColumns(1).setRefreshForm(true);
			AddItemEdit("Descripción", CHAR, REQ, "description").setSizeColumns(11).SetValorDefault("Informe");
			AddItemMultiple("Distribución", CHAR, REQ, "distribution", new JControlCombo() {
				public JWins getRecords(boolean one) throws Exception {
					return getTypes(one);
				}
			}).setSizeColumns(12).setRefreshForm(true).SetValorDefault(BizCustomList.PRESENTATION_INFO_GRID2PAR1IMPAR);
	
			JFormPanelResponsive row = AddItemRow();
			JFormColumnResponsive col3 = row.AddItemColumn("col-sm-3");
			JFormColumnResponsive col4 = row.AddItemColumn("col-sm-9");
			col3.setBackground("#008b8b");
			col4.setBackground("#e6e6fa");
	
			JFormPanelResponsive rowA = col3.AddItemRow();
			JFormPanelResponsive rowB = col3.AddItemRow();
			rowB.AddItemTree("Arrastrar a campos", 25);
			rowA.AddItemTableExpand("Refinar busqueda", "campos", GuiCampos.class, true, "FILTROS").addDropManager("campos").setSizeRow("col-sm-12").setHeight(400);
			col4.AddItemTableExpand("Informe", "informes", GuiInformes.class, 1, 20001,20000, null, true, "PREVIEW", true, tipo).setZoomtofit(2000).setSizeRow("col-sm-12").setHeight(1000);

	  }
	  
    private JWins getRelationGalery(boolean one) throws Exception {
    	if (one) {
    		return JWins.createVirtualWinsFromMap(this.getWinCL().GetcDato().getRelationGallery(false, true));
    	} else {
  			return JWins.createVirtualWinsFromMap(this.getWinCL().GetcDato().getRelationGallery(false, true));
    	}
    }

    private JWins getTypes(boolean one) throws Exception {
    	return JWins.CreateVirtualWins(BizCustomList.getPresentationInformeTypes());
    }
	}  //  @jve:decl-index=0:visual-constraint="36,5"
