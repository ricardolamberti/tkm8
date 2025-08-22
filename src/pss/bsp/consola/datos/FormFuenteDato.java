package  pss.bsp.consola.datos;

import pss.core.win.JWin;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormFuenteDato extends JBaseForm {


private static final long serialVersionUID = 1245260233503L;

  /**
   * Constructor de la Clase
   */
  public FormFuenteDato() throws Exception {
  }

  public GuiFuenteDato getWin() { return (GuiFuenteDato) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
//    AddItem( usuario, CHAR, REQ, "usuario" );
//    AddItem( licencia, CHAR, REQ, "licencia" );
  	JFormLista l;
  	
  	JFormTabPanelResponsive tabs=AddItemTabPanel();
  	tabs.AddItemList(10);
  		tabs.AddItemListOnDemand(15);
  	tabs.AddItemListOnDemand(20);
   	tabs.AddItemListOnDemand(500);
   	tabs.AddItemListOnDemand(170);
   	tabs.AddItemListOnDemand(175);
     	
  	tabs.AddItemListOnDemand(30);
  	tabs.AddItemListOnDemand(35);
  	tabs.AddItemListOnDemand(45);
   	tabs.AddItemListOnDemand(36);
   	tabs.AddItemListOnDemand(37);
    tabs.AddItemListOnDemand(38);
  	tabs.AddItemListOnDemand(120);
  	tabs.AddItemListOnDemand(39);
  	tabs.AddItemListOnDemand(130);
} 





}  //  @jve:decl-index=0:visual-constraint="10,10" 
