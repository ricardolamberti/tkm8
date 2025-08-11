package  pss.common.customList.config.field.campo;

import java.awt.Dimension;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormCampoOrEmbbeded extends JBaseForm {


private static final long serialVersionUID = 1226426966039L;


/**
   * Constructor de la Clase
   */
  public FormCampoOrEmbbeded() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCampo getWin() { return (GuiCampo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(493, 298));


  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "list_id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "secuencia" ).setHide(true);
		AddItemEdit( null, CHAR, OPT, "serial_deep").setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_source" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "rel_id").setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_gallery").setHide(true);
    AddItemEdit( null, UINT, OPT, "orden_padre" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "orden" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "operacion" ).setHide(true);
    AddItemCombo( "Tipo", CHAR, REQ, "negado", JWins.createVirtualWinsFromMap(BizCampo.getTipoNegaciones()) );
    AddItemCheck( "No incluir en totalizador de porcentajes", CHAR,"no_incluir" );
    AddItemCheck( "Solo incluir en totalizador de porcentajes", CHAR,"solo_tot" );
    AddItemEdit("Condiciones", CHAR,OPT, "descr_campo").SetReadOnly(true);;
  }
  

	
}  //  @jve:decl-index=0:visual-constraint="20,9" 
