package pss.bsp.contrato.quevender.ms;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMS extends JBaseForm {


private static final long serialVersionUID = 1446860154249L;

  /**
   * Constructor de la Clase
   */
  public FormMS() throws Exception {
  }

  
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "linea" ).setHide(true);

    
  } 
} 
