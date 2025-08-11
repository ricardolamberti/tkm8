package  pss.common.personalData;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTiposDoc extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiTiposDoc() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 10036; }
  @Override
	public String  GetTitle()    throws Exception  { return "Tipos de Documento"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiTipoDoc.class; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("tipo_doc");
    zLista.AddColumnaLista("descripcion");
  }

  public static JWins ObtenerDocLocales() throws Exception {
    GuiTiposDoc oTipos = new GuiTiposDoc();
    oTipos.getRecords().addFilter("pais", BizUsuario.getUsr().getCountry());
    return oTipos;
  }

}
