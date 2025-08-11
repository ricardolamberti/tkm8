package pss.common.restJason.apiReqHistory;

import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormApiRequestHistory extends JBaseForm {



//-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormApiRequestHistory() throws Exception {

  }

  public GuiApiRequestHistory getWin() { 
  	return (GuiApiRequestHistory) getBaseWin(); 
  }

  public BizApiRequestHistory getHist() throws Exception { 
  	return this.getWin().GetcDato(); 
  }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormControlResponsive c;	
  	JFormPanelResponsive panel = this.AddItemColumn(10);
//  	panel.AddItemEdit("Empresa", CHAR, OPT, "company").hide();
//  	panel.AddItemEdit("id", ULONG, OPT, "id").hide();
  	panel.AddItemLabel("Fecha: "+JDateTools.DateToString(this.getHist().getCreationDate(), "dd/MM/yyyy HH:mm:ss"), 2).italic().color("green");
  	panel.AddItemLabel("URL: " + this.getHist().getUrlTrunc	(), 10).italic();
  	panel.AddItemLabel("Rta: " + this.getHist().getStatusMessage()).color(this.getHist().isStatusError()?"red":"green").italic();
  	panel.addVerticalGap(10);

  	panel.AddItemHtml(null, CHAR, OPT, "request_html" ).setSizeColumns(6);
  	panel.AddItemHtml(null, CHAR, OPT, "response_html" ).setSizeColumns(6);
  	panel.AddItemLabel("Id: " + this.getHist().getId()).italic();

  	
  }
  
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
