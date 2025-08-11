package  pss.common.regions.divitions;

import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiCiudadCPes extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiCiudadCPes() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 73; }
  @Override
	public String  GetTitle()    throws Exception  { return "Sub-Divisiones CP"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiCiudadCP.class; }

	@Override
	public JRecords<? extends JRecord> ObtenerDatos() throws Exception {
		return new BizCiudadCPes();
	}

  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }
  //-------------------------------------------------------------------------//
  // Configuro las columnas que quiero mostrar en la grilla
  //-------------------------------------------------------------------------//
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("ciudad");
    zLista.AddColumnaLista("cod_postal");
    this.getRecords().addOrderBy("ciudad");
    this.getRecords().addOrderBy("cod_postal");
  }

  @Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	if (GetVision().equals(BizCiudadCP.ABM_CIUDAD)) {
  		zFiltros.addComboResponsive("Pais", "pais" ,new GuiPaises());
  		zFiltros.addComboResponsive("Provincia", "provincia" ,new GuiProvincias());
			zFiltros.addEditResponsive("Descripcion", "ciudad" ).setOperator("ilike");
  	}
		zFiltros.addEditResponsive("Codigo postal", "cod_postal" );
	}
	public void hidePreasignedFilters(JFormControl c,RFilter filter) throws Exception {
		if (!filter.isDynamic()) c.SetReadOnly(true); // si es dinamico no se oculta
	}
	
  @Override
  protected void asignFilterByControl(JFormControl control) throws Exception {
  	if (control.getName().equals("ciudad") && control.hasValue()) {
//  		if (!this.getRecords().hasJoin("REG_CIUDAD")) {
//  			this.getRecords().addJoin("REG_CIUDAD");
//    		this.getRecords().addFixedFilter("where REG_CIUDAD_CP.pais=REG_CIUDAD.pais and REG_CIUDAD_CP.provincia=REG_CIUDAD.provincia and REG_CIUDAD_CP.ciudad_id=REG_CIUDAD.ciudad_id ");
//  		}
  		this.getRecords().addFilter("REG_CIUDAD","ciudad",control.getValue(),"ilike").setDynamic(true);
  		return;
  	}
  	super.asignFilterByControl(control);
  }
}
