package  pss.bsp.consolid.model.gruposDK.detail;

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

public class FormGrupoDKDetail extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

 

/**
   * Constructor de la Clase
   */
  public FormGrupoDKDetail() throws Exception {
   }

  public GuiGrupoDKDetail GetWin() { return (GuiGrupoDKDetail) getBaseWin(); }

 
  public void InicializarPanel( JWin zWin ) throws Exception {
  	
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_grupo" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_detail" ).setHide(true);
    AddItemEdit( "DK", CHAR, OPT, "dk" );
    AddItemCheck("Ver todos",OPT,"view_all"); 
  }


 
}  //  @jve:decl-index=0:visual-constraint="80,14"
