package pss.common.documentos.docLocal;

import pss.common.layoutWysiwyg.GuiPlantillas;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormHtmlTextAreaResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDocLocal extends JBaseForm {


	private static final long serialVersionUID = 12218220792562L;

	  /**
	   * Constructor de la Clase
	   */
	  public FormDocLocal() throws Exception {

	  }

	  public GuiDocLocal GetWin() { 
	  	return (GuiDocLocal) getBaseWin(); 
	  }


	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
		  JFormPanelResponsive panel = this.AddItemColumn(12);
//		  panel.setLabelLeft(3);
		  panel.AddItemEdit("company", CHAR, OPT, "doc_company").hide();
		  panel.AddItemEdit("id_doc", ULONG, OPT, "id_doc").hide();
		  panel.AddItemEdit("Tipo Doc", CHAR, OPT, "doc_tipo_doc").hide();
		  panel.AddItemEdit("Fecha", CHAR, OPT, "doc_fecha").hide();
		  panel.AddItemEdit("usuario", CHAR, OPT, "doc_usuario").hide();
		  panel.AddItemEdit("Doc Padre", CHAR, OPT, "doc_id_doc_padre").hide();
		  panel.AddItemEdit("Source", CHAR, OPT, "doc_source_tipo").hide();
		  panel.AddItemEdit("sCod", CHAR, OPT, "doc_source_codigo").hide();

	  	panel.AddItemCombo("Plantilla",CHAR,OPT,"doc_plantilla", new JControlCombo() {
	  		 @Override
	  		public JWins getRecords(boolean one) throws Exception {
	  			return getPlantillas(one);
	  		}
	  	 }).setRefreshForm(true);
	  	 
	    
		  panel.AddItemEdit("Título", CHAR, OPT, "doc_titulo");
		  JFormHtmlTextAreaResponsive ctrl= panel.AddItemHtml("Nota", CHAR, OPT, "body");
		  ctrl.setHeight(800);

	  }

	  public JWins getPlantillas(boolean one) throws Exception {
	  	GuiPlantillas pls = new GuiPlantillas();
	  	pls.getRecords().addFilter("company", GetWin().GetcDato().getCompany());
	  	pls.getRecords().addFilter("origen", "D");
	  	if (one) {
	    	pls.getRecords().addFilter("id_plantilla", GetWin().GetcDato().getIdPlantilla());
	  		
	  	}
	  	return pls;
	  }

	  @Override
	  public void checkControls(String sControlId) throws Exception {
	  	if (sControlId.equals("doc_plantilla")) {
	  		GetWin().GetcDato().setBody(GetWin().GetcDato().getObjPlantilla().generateDocSimple(GetWin().GetcDato()));
	  		
	  	}
	  	super.checkControls(sControlId);
	  }
}  //  @jve:decl-index=0:visual-constraint="10,10" 
