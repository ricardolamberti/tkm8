package pss.bsp.contrato.rutas;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormObjetivosRuta extends JBaseForm {


private static final long serialVersionUID = 1446860154249L;


  /**
   * Constructor de la Clase
   */
  public FormObjetivosRuta() throws Exception {
  }

  public GuiObjetivosRuta getWin() { return (GuiObjetivosRuta) getBaseWin(); }

  
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
  	AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
  	AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
  	AddItemEdit( null, CHAR, OPT, "linea" ).setHide(true);

  	JFormTabPanelResponsive tabs= AddItemTabPanel();
  	tabs.AddItemList(10);
 
  } 
} 
