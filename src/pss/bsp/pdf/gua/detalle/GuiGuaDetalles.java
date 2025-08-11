package pss.bsp.pdf.gua.detalle;

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

public class GuiGuaDetalles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiGuaDetalles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10020; } 
  public String  GetTitle()    throws Exception  { return "Detalles parseo"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiGuaDetalle.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
  }

  @Override
  public boolean hasSubDetail() {
  	return true;
  }


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista( "fecha" );
    zLista.AddColumnaLista( "tipo_ruta" );
    zLista.AddColumnaLista( "aerolinea" );
    zLista.AddColumnaLista( "operacion" );
    zLista.AddColumnaLista( "boleto" );
    zLista.AddColumnaLista( "tarifa" );
    zLista.AddColumnaLista( "contado" );
    zLista.AddColumnaLista( "tarjeta" );
    zLista.AddColumnaLista( "base_imponible" );
    zLista.AddColumnaLista( "neto" );
    zLista.AddColumnaLista( "impuesto1" );
    zLista.AddColumnaLista( "impuesto2" );
    zLista.AddColumnaLista( "comision_porc" );
    zLista.AddColumnaLista( "comision" );
    zLista.AddColumnaLista( "comision_over" );
    zLista.AddColumnaLista( "imp_sobre_com" );
    zLista.AddColumnaLista( "total" );
    zLista.AddColumnaLista( "contado_bruto" );
    zLista.AddColumnaLista( "tarjeta_bruto" );
    zLista.AddColumnaLista( "tipo_tarjeta" );
    zLista.AddColumnaLista( "numero_tarjeta" );
    zLista.AddColumnaLista( "observaciones" );
    
  }

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addCDateResponsive("emisión", "fecha");
		zFiltros.addComboResponsive("LA",  "id_aerolinea",new GuiCarriers().addVisionAdHoc("BSP").addOrderAdHoc("description", "asc"));
		zFiltros.addEditResponsive("Boleto", JObject.JSTRING, "boleto");

		
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
