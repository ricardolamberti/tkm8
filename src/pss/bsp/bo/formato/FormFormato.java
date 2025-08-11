package  pss.bsp.bo.formato;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormFormato extends JBaseForm {


private static final long serialVersionUID = 1245698898912L;



  /**
   * Constructor de la Clase
   */
  public FormFormato() throws Exception {
   }

  public GuiFormato getWin() { return (GuiFormato) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo(null, CHAR, REQ, "company" ).setHide(false);
    AddItemCombo(null, CHAR, REQ, "owner" ).setHide(false);
    AddItemCombo(null, CHAR, REQ, "id_formato" ).setHide(false);
    AddItemCombo("d1", CHAR, REQ, "d1" , getCamposPosibles());
    AddItemCombo("d2", CHAR, REQ, "d2" , getCamposPosibles());
    AddItemCombo("d3", CHAR, REQ, "d3" , getCamposPosibles());
    AddItemCombo("d4", CHAR, REQ, "d4" , getCamposPosibles());
    AddItemCombo("d5", CHAR, REQ, "d5" , getCamposPosibles());
    AddItemCombo("d6", CHAR, REQ, "d6" , getCamposPosibles());
    AddItemCombo("d7", CHAR, REQ, "d7" , getCamposPosibles());
    AddItemCombo("d8", CHAR, REQ, "d8" , getCamposPosibles());
    AddItemCombo("d9", CHAR, REQ, "d9" , getCamposPosibles());
    AddItemCombo("d10", CHAR, REQ, "d10" , getCamposPosibles());
    AddItemCombo("d11", CHAR, REQ, "d11" , getCamposPosibles());
    AddItemCombo("d12", CHAR, REQ, "d12" , getCamposPosibles());
    AddItemCombo("d13", CHAR, REQ, "d13" , getCamposPosibles());
    AddItemCombo("d14", CHAR, REQ, "d14" , getCamposPosibles());
    AddItemCombo("d15", CHAR, REQ, "d15" , getCamposPosibles());
    AddItemCombo("d16", CHAR, REQ, "d16" , getCamposPosibles());
    AddItemCombo("d17", CHAR, REQ, "d17" , getCamposPosibles());
    AddItemCombo("d18", CHAR, REQ, "d18" , getCamposPosibles());
    AddItemCombo("d19", CHAR, REQ, "d19" , getCamposPosibles());
    AddItemCombo("d20", CHAR, REQ, "d20" , getCamposPosibles());
    AddItemCombo("d21", CHAR, REQ, "d21" , getCamposPosibles());
    AddItemCombo("d22", CHAR, REQ, "d22" , getCamposPosibles());
    AddItemCombo("d23", CHAR, REQ, "d23" , getCamposPosibles());
    AddItemCombo("d24", CHAR, REQ, "d24" , getCamposPosibles());
    AddItemCombo("d25", CHAR, REQ, "d25" , getCamposPosibles());
    AddItemCombo("d26", CHAR, REQ, "d26" , getCamposPosibles());
    AddItemCombo("d27", CHAR, REQ, "d27" , getCamposPosibles());
    AddItemCombo("d28", CHAR, REQ, "d28" , getCamposPosibles());
    AddItemCombo("d29", CHAR, REQ, "d29" , getCamposPosibles());
    AddItemCombo("d30", CHAR, REQ, "d30" , getCamposPosibles());
    AddItemCombo("d31", CHAR, REQ, "d31" , getCamposPosibles());
    AddItemCombo("d32", CHAR, REQ, "d32" , getCamposPosibles());
    AddItemCombo("d33", CHAR, REQ, "d33" , getCamposPosibles());
    AddItemCombo("d34", CHAR, REQ, "d34" , getCamposPosibles());
    AddItemCombo("d35", CHAR, REQ, "d35" , getCamposPosibles());
    AddItemCombo("d36", CHAR, REQ, "d36" , getCamposPosibles());
    AddItemCombo("d37", CHAR, REQ, "d37" , getCamposPosibles());
    AddItemCombo("d38", CHAR, REQ, "d38" , getCamposPosibles());
    AddItemCombo("d39", CHAR, REQ, "d39" , getCamposPosibles());
    AddItemCombo("d40", CHAR, REQ, "d40" , getCamposPosibles());   
    AddItemCombo("descripcion", CHAR, REQ, "descripcion" );
    AddItemTabPanel().AddItemTab(20);
  } 
  
  public JWins getCamposPosibles() throws Exception {
  	return JWins.createVirtualWinsFromMap(BizFormato.getCamposPosibles());
  }

	
  
}  //  @jve:decl-index=0:visual-constraint="10,10" 
