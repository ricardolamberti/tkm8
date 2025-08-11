package pss.common.customList.config.customlist;

import pss.common.customList.config.field.campo.GuiCampos;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormCustomListResultDato  extends JBaseForm {


	private static final long serialVersionUID = 1226426905817L;

	
	  /**
	   * Constructor de la Clase
	   */
	  public FormCustomListResultDato() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public  GuiCustomList getWin() { return (GuiCustomList) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
	  }
	  
	 
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	  	AddItemEdit( null, CHAR,OPT, "company").setVisible(false);
	  	AddItemEdit( null, UINT,OPT, "list_id").setVisible(false);
	    AddItemEdit( null, CHAR,OPT, "description" ).setVisible(false);
	    AddItemEdit( null, CHAR,OPT, "record_owner" ).setVisible(false);
	    AddItemEdit( null, CHAR,OPT, "rel_id" ).setVisible(false);
	    AddItemEdit( null, CHAR,OPT, "agrupado" ).setVisible(false);
	    AddItemEdit( null, CHAR,OPT, "modelo" ).setVisible(false);
			AddItemEdit(null, CHAR, OPT, "view_table").setVisible(false);
	    AddItemEdit( null, CHAR, OPT, "invisible" ).setVisible(false);
		
	  	
	  	if (getWin().GetVision().equals("PREVIEW"))
	  		AddItemLabel("Vista parcial, 'ejecutar' para ver completo");
	    
	    if (getWin().showFilter()) {
		    JFormPanelResponsive row1=AddItemColumn(2);
		    JFormPanelResponsive row2=AddItemColumn(10).setZoomtofit(1500);
	    	row1.AddItemTableExpand("Refinar busqueda", "campos", GuiCampos.class,true,"FILTROS").setRowToolbarPos(JBaseWin.TOOLBAR_IN).setSizeRow("col-sm-12");
	    	row1.AddItemButton("Aplicar Filtros", -1, true).setRefreshForm(true);
		    row2.AddInfoCard(getWin().GetcDato().getDescripcion(), CHAR, "dato", JWebIcon.getResponsiveIcon("fa fa-database fa-5x"),"Fuente de datos", -1).setResponsiveClass("panel-primary");
			} else if (getWin().showZoom()) {
				JFormPanelResponsive row1 = AddItemColumn(12).setZoomtofit(1500);
				row1.AddInfoCard(getWin().GetcDato().getDescripcion(), CHAR, "dato", JWebIcon.getResponsiveIcon("fa fa-database fa-5x"),"Fuente de datos", -1).setResponsiveClass("panel-primary");
	    } else {
	    	AddInfoCard(getWin().GetcDato().getDescripcion(), CHAR, "dato", JWebIcon.getResponsiveIcon("fa fa-database fa-5x"),"Fuente de datos", -1).setResponsiveClass("panel-primary");
	    }

	  
	  }
		
	}  //  @jve:decl-index=0:visual-constraint="36,5"
