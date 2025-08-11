package pss.common.documentos.docElectronico;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDocAdjuntoFlat extends JBaseForm {

  public FormDocAdjuntoFlat() throws Exception {
  }

  public GuiDocAdjunto getWin() { 
  	return (GuiDocAdjunto) getBaseWin(); 
  }

  public BizDocAdjunto getAdjunto() throws Exception { 
  	return this.getWin().GetccDato(); 
  }

  @Override
  public void InicializarPanelHeader(JWin zBase) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn();
  	col.AddItemLabel("Archivo");
  }
  
  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn();
  	col.addButton(this.getAdjunto().getTitulo(), 10);
  }

}
