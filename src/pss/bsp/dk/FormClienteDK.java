package  pss.bsp.dk;

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

public class FormClienteDK extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

/**
   * Constructor de la Clase
   */
  public FormClienteDK() throws Exception {
  }

  public GuiClienteDK GetWin() { return (GuiClienteDK) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
  	
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_cot" ).setHide(true);
    AddItemRow().AddItemEdit( "DK", CHAR, REQ, "dk" ).setSizeColumns(4);
    AddItemRow().AddItemDateTime("fecha", DATE, REQ, "fecha" ).setSizeColumns(4);
    AddItemRow().AddItemEdit( "Cotización", FLOAT, REQ, "cotizacion" ).setSizeColumns(4);
    
  }
}  //  @jve:decl-index=0:visual-constraint="80,14"
