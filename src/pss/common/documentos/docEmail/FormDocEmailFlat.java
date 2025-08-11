package pss.common.documentos.docEmail;

import pss.common.security.BizUsuario;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormDocEmailFlat extends JBaseForm {

  public FormDocEmailFlat() throws Exception {
  }

  public GuiDocEmail getWin() { 
  	return (GuiDocEmail) getBaseWin(); 
  }
  
  public BizDocEmail getMail() throws Exception { 
  	return this.getWin().GetcDato(); 
  }
  
  @Override
  public void InicializarPanelHeader(JWin zBase) throws Exception {
    JFormPanelResponsive col = this.AddItemColumn();
    col.AddItemLabel( "Para", 3);
    col.AddItemLabel( "Asunto", 8);
  }

  public String findColor(String color) throws Exception {
		if (!this.getMail().isLast()) return "#b1b1b1";
		return color;
	}

  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormControlResponsive c;
  	JFormPanelResponsive col = this.AddItemColumn();
  	JFormPanelResponsive col1 = col.AddItemColumn(3);
  	JFormPanelResponsive line = col1.AddInLinePanel();
  	line.addButton(this.getMail().isSaliente()?808:809, null, false, 0).color(this.findColor("#2f0e6f"));
  	line.addButton(this.getWin().GetNroIcono(), null, false, 0).color(this.findColor("#2f0e6f"));
  	line.addGap(4);
  	
  	// from
  	c=line.addButton( this.getMail().getGrupoEMail(), 100, 0).color("#2f0e6f");
  	if (this.getMail().canMarcarLeido()) c.bold();

    line  = col1.AddItemColumn();
  	line.AddItemLabel("", 1);
  	line.AddItemLabel(this.getMail().getUsuario() +" -- " +JDateTools.DateToString(this.getMail().getFechaEnviado(), "dd/MM/yyyy HH:mm:ss"), 11).italic().color("green");

  	// titulo y body
  	JFormPanelResponsive col2 = col.AddItemColumn(7);
  	line = col2.AddInLinePanel();
  	line.overHidden();
  	c=line.addButton(this.getMail().getTitulo(), 100, 0).noWrap();
  	if (this.getMail().canMarcarLeido()) c.bold();
  	line.AddItemLabel(" - "+this.getMail().getBodyTrunc(), 0).color("#808080").italic().noWrap();

  	// acciones
    JFormPanelResponsive col3 = col.AddItemColumn(2);
//  	last.AddItemColumn(2).addButton(6101, 330, true);
    col3.addButton(6100, 310, true, 2);
    col3.addButton(12, 3, true, 2);
    col3.addButton("Enviar", 301, 5);
    col3.addButton("Re-Enviar", 302, 5);
    col3.addButton("Responder", 200, 5);
  	
    this.completeCol2(col2);
    
  }
 
  public void completeCol2(JFormPanelResponsive col) throws Exception {
  }

}