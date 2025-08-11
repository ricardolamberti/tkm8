package  pss.common.layoutWysiwyg.permisos;

import pss.common.security.GuiUsuarios;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormComboResponsive;

public class FormOwnerPlantilla extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

/**
   * Constructor de la Clase
   */
  public FormOwnerPlantilla() throws Exception {
  }

  public GuiOwnerPlantilla GetWin() { return (GuiOwnerPlantilla) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
   AddItemEdit( null, CHAR, REQ, "id_plantilla" ).setHide(true);
    
    JFormComboResponsive f = AddItemCombo( "Usuario", CHAR, OPT, "id_owner" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getUsuarios(one);
    	}
    });
    f.SetPermitirTodo(true);
//    f.setWinTodos(JWin.createVirtualWin("TODOS", "Todos", 380));
    f.SetPermitirTodo(true);
    f.setFirstOcur(true);
    AddItemCheck( "Modificar", CHAR, OPT, "modificar", "S", "N" );
    AddItemCheck( "Borrar", CHAR, OPT, "borrar", "S", "N" );
    
  }


  public JWins getUsuarios(boolean one) throws Exception {
  	JWins w = new GuiUsuarios();
  	
  	return w;
  }

}  //  @jve:decl-index=0:visual-constraint="80,14"
