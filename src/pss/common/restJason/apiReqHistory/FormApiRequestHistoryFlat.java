package pss.common.restJason.apiReqHistory;

import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormApiRequestHistoryFlat extends JBaseForm {



//-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormApiRequestHistoryFlat() throws Exception {

  }

  public GuiApiRequestHistory getWin() { 
  	return (GuiApiRequestHistory) getBaseWin(); 
  }

  public BizApiRequestHistory getHist() throws Exception { 
  	return this.getWin().GetcDato(); 
  }
  
  @Override
	public void InicializarPanelHeader(JWin zBase) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn();
  	col.AddItemLabel("Id", 1);
  	JFormPanelResponsive col1= col.AddItemColumn(3);
  	col1.AddItemLabel("Usuario", 4);
  	col1.AddItemLabel("Fecha/Hora", 5);
  	col1.AddItemLabel("Entidad", 3);
  	
  	JFormPanelResponsive col2= col.AddItemColumn(4);
  	col2.AddItemLabel("Type", 2);
  	col2.AddItemLabel("URL", 10);
  	col.AddItemLabel("Respuesta", 4);
  	super.InicializarPanelHeader(zBase);
	}

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormControlResponsive c;	
  	JFormPanelResponsive col = this.AddItemColumn();
  	JFormPanelResponsive line = col.AddItemColumn(1).AddInLinePanel();
  	line.addButton(this.getHist().isEntrante()?6106:6107, null, false, 0).color(this.findColor());
  	line.addGap(6);
//  	line.addButton(this.getWin().GetNroIcono(), null, false, 0).color(this.getHist().isStatusOK()?"green":"red");
  	line.addGap(6);
  	line.addButton(this.getHist().getId()+"", 1, 0);
    line.addButton(17, 10, 1);
  	
  	JFormPanelResponsive col1=col.AddItemColumn(3);
  	col1.AddItemLabel(this.getHist().getUser(), 4).italic();
  	col1.AddItemLabel(JDateTools.DateToString(this.getHist().getCreationDate(), "dd/MM/yyyy HH:mm:ss"), 5).italic().color("green");
  	col1.AddItemLabel(this.getHist().findOther(), 3).italic().color("Brown");

  	
  	JFormPanelResponsive col2=col.AddItemColumn(4);
  	col2.AddItemLabel(this.getHist().getType(), 2).italic();
  	col2.addButton(this.getHist().getUrlTrunc(), 1, 10).italic();
//  	col.AddItemLabel(this.getHist().getMethod(), 2).italic();
//   	JFormPanelResponsive req = col.AddItemColumn(5);
//   	req.overHidden();
//    req.addButton(JTools.trunc(this.getHist().getRequest(), 150	), 1, false).italic().noWrap();
  	
    col.addButton(JTools.trunc(this.getHist().getStatusMessage(), 200), 1, 4).italic();
//  	panel.AddItemHtml("request_html", CHAR, OPT, "request_html" );
  	
  }
  
  private String findColor() throws Exception {
  	if (this.getHist().isStatusOK()) return "green";
  	if (this.getHist().isStatusError()) return "red";
  	if (this.getHist().isStatusPendiente()) return "blue";
  	if (this.getHist().isStatusVencido()) return "orange";
  	return "black";
  }
  
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
