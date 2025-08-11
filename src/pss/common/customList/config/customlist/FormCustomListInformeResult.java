package pss.common.customList.config.customlist;

import pss.common.customList.config.field.campo.GuiCampos;
import pss.common.customList.config.informe.GuiInformes;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JWinGridExpand;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormCustomListInformeResult   extends JBaseForm {


	private static final long serialVersionUID = 1226426905817L;

	
	  /**
	   * Constructor de la Clase
	   */
	  public FormCustomListInformeResult() throws Exception {
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
	  	AddItemEdit(null, CHAR,OPT, "company").setVisible(false).SetValorDefault(BizUsuario.getUsr().getCompany());
	  	AddItemEdit(null, UINT,OPT, "list_id").setVisible(false);
	  	AddItemEdit(null, UINT,OPT, "rel_id").setVisible(false);
	  	AddItemEdit(null, UINT,OPT, "description").setVisible(false);
	    AddItemEdit( null, CHAR,OPT, "record_owner" ).setVisible(false);
	    AddItemEdit( null, CHAR,OPT, "agrupado" ).setVisible(false);
	    AddItemEdit( null, CHAR,OPT, "modelo" ).setVisible(false);
			AddItemEdit(null, CHAR, OPT, "view_table").setVisible(false);
	    AddItemEdit( null, CHAR, OPT, "invisible" ).setVisible(false);
 
	    String tipo = getWin().GetcDato().getInformeDistribution()==null?JWinGridExpand.GRID2PAR1IMPAR:getWin().GetcDato().getInformeDistribution();
	    if (getWin().showFilter()) {
		    JFormPanelResponsive col1=AddItemColumn(3);
		    JFormPanelResponsive col2=AddItemColumn(9);
		    col1.AddItemTableExpand("Refinar busqueda", "filtrosrep", GuiCampos.class,true,"FILTROS").setSizeRow("col-sm-12");
		    col1.AddItemButton("Aplicar Filtros", -1, true).setRefreshForm(true);
		    col2.AddItemTableExpand(getLabel(), "informes", GuiInformes.class,1,20001,20000,null,isEditable(),getVision(),true,tipo).setZoomtofit(1500).setSizeRow("col-sm-12");
	    } else {
	    	getInternalPanel().AddItemTableExpand(getLabel(), "informes", GuiInformes.class,1,20001,20000,null,isEditable(),getVision(),true,tipo).setZoomtofit(1500).setSizeRow("col-sm-12");
	    }
	  }
	  
		 private String getVision() throws Exception {
			 if (getWin().showFilter())
				 return getWin().getMode()==GuiCustomList.EMBEDDED || getWin().getMode()==GuiCustomList.INFOEMBEDDED?"EMBEDDED":"";
			 else
				 return getWin().getMode()==GuiCustomList.EMBEDDED || getWin().getMode()==GuiCustomList.INFOEMBEDDED ?"EMBEDDED":"PREVIEW";
		 }
		 private String getLabel() throws Exception {
			 return getWin().getMode()==GuiCustomList.EMBEDDED || getWin().getMode()==GuiCustomList.INFOEMBEDDED?"":"Informe";
		 }	
	
	 private boolean isEditable() throws Exception {
		 return getWin().getMode()==GuiCustomList.EDIT_MODE;
	 }
	 
}  //  @jve:decl-index=0:visual-constraint="36,5"
