package pss.common.event.manager;

import pss.core.winUI.forms.JBaseForm;

public class FormEvent extends JBaseForm {


private static final long serialVersionUID = 1244688730075L;

  /**
   * Propiedades de la Clase
   */

  /**
   * Constructor de la Clase
   */
  public FormEvent() throws Exception {
  }

  public GuiEvent getWin() { return (GuiEvent) getBaseWin(); }

} 
