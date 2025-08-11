package  pss.common.regions.nodes;

import pss.common.regions.divitions.GuiPaises;
import pss.common.regions.propagation.GuiPropagates;
import pss.core.connectivity.messageManager.common.core.JMMBaseForm;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;

public class FormNodo extends JMMBaseForm {

  public FormNodo() throws Exception {
  }


  public GuiNodo GetWin() { return (GuiNodo) getBaseWin(); }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Nodo", CHAR, REQ, "NODO" );
    AddItemEdit( "codigoIdent", CHAR, REQ, "codigo_ident" );
    AddItemEdit( "DESCRIPCION", CHAR, REQ, "DESCRIPCION" );
    AddItemEdit( "Company", CHAR, REQ, "company");
    AddItemCombo( "Pais", CHAR, REQ, "pais", new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return ObtenerPaises();}} );
    AddItemCombo( "NodoPadre", CHAR, OPT, "nodo_padre", new GuiNodos() );
//    AddItem( ImpresoraReporte, CHAR, OPT, "impresora_reportes", ObtenerImpresoras() );
    AddItemCombo( "ModeloPropagar", CHAR, OPT, "modelo_propagar", new GuiPropagates() );
    AddItemCheck( "Propagar", REQ, "propagar");
    AddItemCheck( "ModuloEventosHabilitado" , OPT , "modulo_eventos").SetValorDefault(true);
    AddItemEdit( "reportExportDir" , CHAR, OPT , "report_dir");
    AddItemEdit("RepCabecera" , CHAR, OPT, "reporte_cabecera");
    AddItemEdit( "ipServidorLicencias", CHAR, OPT, "servidor_licencia" );

  }




  private JWins ObtenerPaises() throws Exception {
    return new GuiPaises();
  }



}  //  @jve:decl-index=0:visual-constraint="6,6"
