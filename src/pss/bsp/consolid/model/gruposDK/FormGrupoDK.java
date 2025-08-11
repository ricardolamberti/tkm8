package  pss.bsp.consolid.model.gruposDK;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import pss.common.security.GuiUsuarios;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormSwingCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormGrupoDK extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

/**
   * Constructor de la Clase
   */
  public FormGrupoDK() throws Exception {
  }

  public GuiGrupoDK GetWin() { return (GuiGrupoDK) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
  	
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_grupo" ).setHide(true);
    AddItemEdit( "Denominacion", CHAR, REQ, "descripcion" );
    AddItemEdit( "Identificador", CHAR, REQ, "identificador" );
    AddItemCheck( "Uso en liquidacion", OPT, "use_liq" );
    AddItemCheck( "Uso en reporte dk", OPT, "use_repo_dk" );
 
    AddItemTabPanel().AddItemList(10);
  }
}  //  @jve:decl-index=0:visual-constraint="80,14"
