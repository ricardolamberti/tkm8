package pss.bsp.interfaces.copa.detalle;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.fields.JObject;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.tourism.carrier.GuiCarriers;

public class GuiCopaDetalles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiCopaDetalles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10020; } 
  public String  GetTitle()    throws Exception  { return "Detalles parseo"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiCopaDetalle.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
  }

 

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
    zLista.AddColumnaLista( "origen" );
    zLista.AddColumnaLista( "destino" );
    zLista.AddColumnaLista( "id_aerolinea" );
    zLista.AddColumnaLista( "marketing_id" );
    zLista.AddColumnaLista( "fms" );
    zLista.AddColumnaLista( "minimo" );
    
  }

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addComboResponsive("LA",  "id_aerolinea",new GuiCarriers().addVisionAdHoc("BSP").addOrderAdHoc("description", "asc"));
		zFiltros.addEditResponsive("Origen", JObject.JSTRING, "origen");
		zFiltros.addEditResponsive("Destino", JObject.JSTRING, "destino");
		zFiltros.addEditResponsive("Marketing id", JObject.JSTRING, "marketing_id");


		
	}

	@Override
	protected void asignFilterByControl(JFormControl control) throws Exception {
		// a la aerolinea hay que agrgarle un 0
		if (control.getName().equals("id_aerolinea") && control.hasValue()) {
			getRecords().addFilter("id_aerolinea", JTools.LPad(control.findValue(), 3, "0"));
			return;
		}
		super.asignFilterByControl(control);
	}



	
	
}
