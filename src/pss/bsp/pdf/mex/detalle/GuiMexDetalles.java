package pss.bsp.pdf.mex.detalle;

import com.f1j.util.o;

import pss.bsp.pdf.mex.cabecera.GuiMexCabecera;
import pss.core.services.fields.JObject;
import pss.core.tools.JTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.tourism.carrier.GuiCarriers;

public class GuiMexDetalles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiMexDetalles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10020; } 
  public String  GetTitle()    throws Exception  { return "Detalles parseo"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiMexDetalle.class; }
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
   	if (GetVision().equals("CUF")) {
   		zLista.AddColumnaLista("TACN");
  	}
   	if (GetVision().equals("INTERFAZ_6")) {
   		zLista.AddColumnaLista("DET");
   		zLista.AddColumnaLista("TACN");
   		zLista.AddColumnaLista("AGTN");
   		zLista.AddColumnaLista("RPSI");
   		zLista.AddColumnaLista("CAT");
  		zLista.AddColumnaLista("ACLASS");
  		zLista.AddColumnaLista("TRNC");
  		zLista.AddColumnaLista("TDNR");
  		zLista.AddColumnaLista("CDGT");
   		zLista.AddColumnaLista("DAIS");
   	  zLista.AddColumnaLista("CPUI");
  		zLista.AddColumnaLista("CUTP");
  		zLista.AddColumnaLista("COBL");
  		zLista.AddColumnaLista("CORT");
  		zLista.AddColumnaLista("COAM");
  		zLista.AddColumnaLista("SPRT");
  		zLista.AddColumnaLista("SPAN");
  		zLista.AddColumnaLista("EFRT");
  		zLista.AddColumnaLista("EFCO");
  		zLista.AddColumnaLista("REMT");
  		zLista.AddColumnaLista("TOCA");
  		zLista.AddColumnaLista("TAX");
  		zLista.AddColumnaLista("FEES");
  		zLista.AddColumnaLista("NRID");
  		zLista.AddColumnaLista("NRMETH");
  		zLista.AddColumnaLista("STAT");
  		zLista.AddColumnaLista("TOUR");
  		zLista.AddColumnaLista("WAVR");
  		zLista.AddColumnaLista("CASH");
  		zLista.AddColumnaLista("CRED");
  		zLista.AddColumnaLista("RELT1");
  		zLista.AddColumnaLista("RTDN1");
  		zLista.AddColumnaLista("RTCP1");
  		zLista.AddColumnaLista("RELT2");
  		zLista.AddColumnaLista("rtdn2");
   		zLista.AddColumnaLista("RTCP2");
   		zLista.AddColumnaLista("ICDN");
   		zLista.AddColumnaLista("ESAC");
   		zLista.AddColumnaLista("TXCALC");
   		zLista.AddColumnaLista("TRANSID");
   		zLista.AddColumnaLista("CCVR");

  	 	return;
 

  	}
  	if (isFullData()) {
  		zLista.AddColumnaLista("tipo_ruta");        // STAT
  		zLista.AddColumnaLista("boleto");           // TDNR
  		zLista.AddColumnaLista("boleto_largo");     // TDNR
//  		zLista.AddColumnaLista("aerolinea_boleto"); // TACN + " " + TDNR
  		zLista.AddColumnaLista("operacion");        // TRNC o nombreHoja
  		zLista.AddColumnaLista("tarifa");           // COBL
//  		zLista.AddColumnaLista("base_imponible");   // COBL
//  		zLista.AddColumnaLista("total");            // COBL + TAX + SPAN + COAM
  		zLista.AddColumnaLista("comision");         // EFCO
  		zLista.AddColumnaLista("comision_over");    // 0
  		zLista.AddColumnaLista("comision_porc");    // EFRT
//  		zLista.AddColumnaLista("impuesto1");        // TAX si CASH ≠ 0
//  		zLista.AddColumnaLista("impuesto2");        // TAX si CASH = 0
  		zLista.AddColumnaLista("impuesto_acum");    // TAX
  		zLista.AddColumnaLista("imp_sobre_com");    // TACO
 		zLista.AddColumnaLista("contado_bruto");    // CASH + impuesto1
//  		zLista.AddColumnaLista("contado");          // CASH
  		zLista.AddColumnaLista("tarjeta_bruto");    // CRED + impuesto2
//  		zLista.AddColumnaLista("tarjeta");          // CRED
  		zLista.AddColumnaLista("tipo_tarjeta");     // PCIN.substring(2, 4)
  		zLista.AddColumnaLista("numero_tarjeta");   // últimos 4 de PCIN
  		zLista.AddColumnaLista("observaciones");    // ""
  		zLista.AddColumnaLista("fecha");            // DAIS
  		zLista.AddColumnaLista("id_aerolinea");     // TACN
  		zLista.AddColumnaLista("aerolinea");        // TACN
//  		zLista.AddColumnaLista("idpdf");            // clave padre
//  		zLista.AddColumnaLista("linea");            // número de línea
//  		zLista.AddColumnaLista("company");
//  		zLista.AddColumnaLista("owner");
 // 		zLista.AddColumnaLista("anulado");          // S/N según TRNC

  		// Campos adicionales del Excel
  		zLista.AddColumnaLista("AGTN");
  		zLista.AddColumnaLista("RPSI");
  		zLista.AddColumnaLista("CAT");
  		zLista.AddColumnaLista("ACLASS");
  		zLista.AddColumnaLista("CDGT");
  		zLista.AddColumnaLista("CPUI");
  		zLista.AddColumnaLista("CUTP");
  		zLista.AddColumnaLista("SPRT");
  		zLista.AddColumnaLista("COAM");
  		zLista.AddColumnaLista("CORT");
  		zLista.AddColumnaLista("EFRT");
  		zLista.AddColumnaLista("REMT");
  		zLista.AddColumnaLista("SPAN");
  		zLista.AddColumnaLista("FEES");
  		zLista.AddColumnaLista("PEN");
  		zLista.AddColumnaLista("NRID");
  		zLista.AddColumnaLista("NRMETH");
  		zLista.AddColumnaLista("TOUR");
  		zLista.AddColumnaLista("WAVR");
  		zLista.AddColumnaLista("RELT1");
  		zLista.AddColumnaLista("RTDN1");
  		zLista.AddColumnaLista("RTCP1");
  		zLista.AddColumnaLista("RELT2");
  		zLista.AddColumnaLista("RTDN2");
  		zLista.AddColumnaLista("RTCP2");
  		zLista.AddColumnaLista("ICDN");
  		zLista.AddColumnaLista("ESAC");
  		zLista.AddColumnaLista("TXCALC");
  		zLista.AddColumnaLista("TRANSID");
  		zLista.AddColumnaLista("CCVR");
  		return;
  	}
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

	public boolean isFullData() throws Exception {
		GuiMexCabecera cab = new GuiMexCabecera();
		cab.GetcDato().dontThrowException(true);
		if (cab.GetcDato().read(getFilterValue("company"), getFilterValue("idPDF"))) {
			return cab.GetcDato().getFullData();
		}
		return false;
	}


	
	
}
