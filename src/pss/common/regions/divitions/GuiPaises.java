package  pss.common.regions.divitions;

import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiPaises extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPaises() throws Exception {
  }

  @Override
  public JRecords<? extends JRecord> ObtenerDatos() throws Exception {
  	return new BizPaises();
  }
  @Override
	public int     GetNroIcono() throws Exception  { return 1; }
  @Override
	public String  GetTitle()    throws Exception  { return "Paises Operativos"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiPais.class; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
     zLista.AddIcono("");
     zLista.AddColumnaLista("pais");
     zLista.AddColumnaLista("descripcion").setActionOnClick(1);
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addEditResponsive("País", "descripcion").setFilterNeverHide(true).setOperator("like");
  }

  
  
  public static GuiPaises getPaises() throws Exception {
  	GuiPaises w = new GuiPaises();
  	w.setRecords(BizPais.getPaises());
  	w.toStatic();
  	return w;
  }
  


//  @Override
//	public void readAll() throws Exception {
//		if (this.getRecords().getOrderBy()==null)
//			this.getRecords().addOrderBy("pais");
//		super.readAll();
//	}
//
//  public static GuiPaises getPaises() throws Exception {
//  	GuiPaises p = new GuiPaises();
//  	p.setRecords(BizPais.getPaises());
//  	p.toStatic();
//  	return p;
//  }

}
