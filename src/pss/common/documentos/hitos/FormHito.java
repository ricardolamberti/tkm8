package pss.common.documentos.hitos;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormHito extends JBaseForm {


private static final long serialVersionUID = 1218227728984L;


  /**
   * Constructor de la Clase
   */
  public FormHito() throws Exception {
  }

  public GuiHito GetWin() { return (GuiHito) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, UINT, REQ, "id_doc" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "id_movim" ).setHide(true);
    AddItemCombo( "Operador", CHAR, REQ, "id_operador" , new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return null;
    	}
    });
    AddItemDateTime( "Fecha", DATE, REQ, "fecha" );
    AddItemEdit( "Observaciones", CHAR, REQ, "observaciones" );
    AddItemCombo( "Hito", CHAR, REQ, "id_tipo_hito" , new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getTiposHitos();
    	}
    });

  } 
  
  public JWins getTiposHitos() throws Exception {
  	return JWins.createVirtualWinsFromMap(BizHito.getTipoHitos());
  }
}  //  @jve:decl-index=0:visual-constraint="10,10" 
