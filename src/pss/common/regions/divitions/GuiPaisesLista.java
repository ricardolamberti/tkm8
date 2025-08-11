package  pss.common.regions.divitions;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiPaisesLista extends JWins {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPaisesLista() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 1; }
  @Override
	public String  GetTitle()    throws Exception  { return "Paises"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiPaisLista.class; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo País" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
     zLista.AddIcono("");
     zLista.AddColumnaLista("pais");
     zLista.AddColumnaLista("descripcion");
     zLista.AddColumnaLista("continente");
     zLista.AddColumnaLista("gentilicio");
  }

	@Override
	public void readAll() throws Exception {
		if (this.getRecords().getOrderBy()==null || this.getRecords().getOrderBy().size()==0)
			this.getRecords().addOrderBy("descripcion");
		super.readAll();
	}
  
  @Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
    zFiltros.addEditResponsive( "Código"  ,"CHAR", "pais").setOperator("ilike");
    zFiltros.addEditResponsive( "Descripción"  ,"CHAR", "descripcion").setOperator("ilike");
    zFiltros.addEditResponsive( "Continente"  ,"CHAR", "continente").setOperator("ilike");
  }

}
