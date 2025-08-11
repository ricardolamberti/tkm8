package pss.common.regions.divitions;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiCiudades extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiCiudades() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 73; }
  @Override
	public String  GetTitle()    throws Exception  { return "Sub-Divisiones"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiCiudad.class; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }
/*  public boolean OkAction(BizAction zAct) throws Exception {
    if (zAct.GetId() == 1 ) if (this.getParentJBD()==null || !(this.getParentJBD() instanceof BizProvincia)) return false;
    return true;
  }
*/

	public JWin createJoinWin(JRecord zBD) throws Exception {
		JWin oWin=createWin(zBD);
		this.joinData(oWin);
		if (this.getRecords().getFilterValue("departamento_id")!=null)
			oWin.getRecord().addFilter("departamento_id",this.getRecords().getFilterValue("departamento_id"));
		return oWin;
	}
  //-------------------------------------------------------------------------//
  // Configuro las columnas que quiero mostrar en la grilla
  //-------------------------------------------------------------------------//
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
//    zLista.AddColumnaLista("descr_pais");
//    zLista.AddColumnaLista("descr_provincia");
    zLista.AddColumnaLista("ciudad");
//    zLista.SetColumnaBusqueda("ciudad");
    this.getRecords().addOrderBy("ciudad");
  }

  @Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Descripcion", "ciudad" ).setOperator("ilike");
		zFiltros.addCheckResponsive("Sin Dpto/Partido", "sin_dpto" );
	}

  @Override
  protected void asignFilterByControl(JFormControl control) throws Exception {
  	if (control.getName().equals("sin_dpto")) {
  		if (!control.hasValue()||control.getValue().equals("N")) return ;
  		this.getRecords().addFixedFilter("where ciudad_id not in (select r.ciudad_id from reg_ciudad_departamento r where " +
  				" r.pais=reg_ciudad.pais and " +
  				" r.provincia=reg_ciudad.provincia and " +
  				" r.ciudad_id=reg_ciudad.ciudad_id  " +
  				" )");
  	}
  	super.asignFilterByControl(control);
  }
}
