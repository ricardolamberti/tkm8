package  pss.common.customList.config.field.filtro;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormFiltroSQL extends JBaseForm {


private static final long serialVersionUID = 1226426966039L;


/**
   * Constructor de la Clase
   */
  public FormFiltroSQL() throws Exception {
   }

  public GuiFiltroSQL getWin() { return (GuiFiltroSQL) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "list_id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "secuencia" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "orden" ).setHide(true);

    AddItemEdit( "Filtro", CHAR, REQ, "filtro");
    AddItemEdit( "Nombre", CHAR, OPT, "nombre_filtro" );
    AddItemCombo( "Tipo", CHAR, OPT, "tipo_filtro", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return JWins.createVirtualWinsFromMap(BizFiltroSQL.getTipos());
    	}
    });
    AddItemCombo( "Valor default", CHAR, OPT, "campo_key", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return JWins.createVirtualWinsFromMap(BizUsuario.getUsr().getObjBusiness().getCamposClavesParaCustomList());
    	}
    });
    }
	
    
  public void checkControls(String campo) throws Exception {
	}
  
}  //  @jve:decl-index=0:visual-constraint="20,9" 
