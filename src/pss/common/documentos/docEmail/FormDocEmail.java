package pss.common.documentos.docEmail;

import pss.common.documentos.docElectronico.GuiDocAdjunto;
import pss.common.layoutWysiwyg.GuiPlantillas;
import pss.common.security.BizUsuario;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDocEmail extends JBaseForm {

	public FormDocEmail() throws Exception {
  }

  public GuiDocEmail getWin() { 
  	return (GuiDocEmail) getBaseWin(); 
  }

  public BizDocEmail getMail() throws Exception { 
  	return this.getWin().GetcDato(); 
  }

  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormButtonResponsive b;
  	JFormPanelResponsive all = this.AddItemColumn(10);
  	JFormPanelResponsive col = all.AddItemColumn();
  	col.AddItemLabel(this.getMail().getTitulo(), 8).size(16);
  	col.AddItemLabel(JDateTools.DateToString(this.getMail().getFecha(), "dd/MM/yyyy dd:mm:ss"), 3).right();
  	b=col.addButton(6103, 200, 1);
  	if (b!=null) b.size(14);

   	
  	col.AddItemLabel(this.getMail().findAnyMail()).bold().size(12);
  	col.AddItemHtml( "", CHAR, OPT, "body_biblo");
  	
    JFormPanelResponsive panel2 = col.AddItemColumn();
    JFormPanelResponsive line; int i=0;
    JIterator<GuiDocAdjunto> iter = this.getWin().getAdjuntos().getStaticIterator();
    while (iter.hasMoreElements()) {
    	GuiDocAdjunto a = iter.nextElement();
    	line=panel2.AddItemColumn().AddInLinePanel();
    	line.addButton(a.GetNroIcono(), null, false, 0).size(16);
    	line.addButton(a.GetcDato().getTitulo(), 701, 0).setRow(a.GetcDato().getIddoc()).size(14);
//	  	line.addButton(7, 702, true, 0);
    }
    if (BizUsuario.getUsr().isAnyAdminUser())
    	col.AddItemLabel("id: " + this.getMail().getIddoc());
  }

}
