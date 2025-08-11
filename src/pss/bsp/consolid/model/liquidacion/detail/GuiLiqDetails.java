package pss.bsp.consolid.model.liquidacion.detail;

import pss.bsp.consolid.model.liquidacion.acumulado.GuiLiqAcum;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiLiqDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiLiqDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Clientes Consolid"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiLiqDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Registro" );
  }

  public String getExcelName() throws Exception {
    if (GetVision().equals("INTERFAZ_1")) {
  			GuiLiqAcum liqAcum = (GuiLiqAcum)getParent();
  		  String desde = JDateTools.DateToString(liqAcum.GetcDato().getFechaDesde(), "dd").toUpperCase();
        String hasta = JDateTools.DateToString(liqAcum.GetcDato().getFechaHasta(), "ddMMMyy").toUpperCase();
        return "EDREAMS_" + desde + "-" + hasta;
    }
    return null;
}
	public String getModelExcel() throws Exception {
	 	if (GetVision().equals("INTERFAZ_1")) {
	 		return "XSL";
	 	}
	 	return null;
	}
  public String[] getExcelHeader() throws Exception {
   	if (GetVision().equals("INTERFAZ_1")) {
  		GuiLiqAcum liqAcum = (GuiLiqAcum)getParent();
  		
   		String[] header = new String[5];
//   		Estado de Cuenta TravelFan				
//   		Fecha del Reporte		25 de MARZO de 2025		
//   		EDM917	VACACIONES EDREAMS			
//   		Período de Facturación del:		16/03/2025	al:	23.03.2025
   		header[0] = "Estado de Cuenta TravelFan";
   		header[1] = "Fecha del Reporte "+JDateTools.DateToString( liqAcum.GetcDato().getFechaLiquidacion(),"dd 'de' MMMM 'de' yyyy");
   		header[2] = liqAcum.GetcDato().getDK() + "  "+ liqAcum.GetcDato().getDescripcion();
   		header[3] = "Período de Facturación del: "+JDateTools.DateToString(liqAcum.GetcDato().getFechaDesde(),"dd/MM/yyyy")+" al "+JDateTools.DateToString(liqAcum.GetcDato().getFechaHasta(),"dd/MM/yyyy");
   		header[4] = "";
   		return header;
   				
   	}
   	if (GetVision().equals("INTERFAZ_6")) {
  		GuiLiqAcum liqAcum = (GuiLiqAcum)getParent();
  		
   		String[] header = new String[5];
//   		Estado de Cuenta TravelFan				
//   		Fecha del Reporte		25 de MARZO de 2025		
//   		EDM917	VACACIONES EDREAMS			
//   		Período de Facturación del:		16/03/2025	al:	23.03.2025
   		header[0] = "Estado de Cuenta TravelFan";
   		header[1] = "Fecha del Reporte "+JDateTools.DateToString( liqAcum.GetcDato().getFechaLiquidacion(),"dd 'de' MMMM 'de' yyyy");
   		header[2] = liqAcum.GetcDato().getDK() + "  "+ liqAcum.GetcDato().getDescripcion();
   		header[3] = "Período de Facturación del: "+JDateTools.DateToString(liqAcum.GetcDato().getFechaDesde(),"dd/MM/yyyy")+" al "+JDateTools.DateToString(liqAcum.GetcDato().getFechaHasta(),"dd/MM/yyyy");
   		header[4] = "";
   		return header;
   				
   	}
   	if (GetVision().equals("INTERFAZ_8_A")) {
  		GuiLiqAcum liqAcum = (GuiLiqAcum)getParent();
//  		Consolid Account Statement			
//  		Report date	18/4/2025	
//  		TRP917			
//  		Billing Period of: 	9/4/2025	to:	15/4/2025
		
   		String[] header = new String[5];
   		header[0] = "Consolid Account Statement";
   		header[1] = "Report date "+JDateTools.DateToString( liqAcum.GetcDato().getFechaLiquidacion(),"dd/MM/yyyy");
   		header[2] = liqAcum.GetcDato().getDK() + "  "+ liqAcum.GetcDato().getDescripcion();
   		header[3] = "Billing Period of:  "+JDateTools.DateToString(liqAcum.GetcDato().getFechaDesde(),"dd/MM/yyyy")+" al "+JDateTools.DateToString(liqAcum.GetcDato().getFechaHasta(),"dd/MM/yyyy");
   		header[4] = "";
   		return header;
   				
   	}
		return null;
	}
	
	public String[] getExcelFooter() throws Exception {
  	if (GetVision().equals("INTERFAZ_1")) {
  		String[] totales = new String[6];
  		totales[0]="tarifa";
  		totales[1]="iva";
  		totales[2]="tua";
  		totales[3]="importe";
  		totales[4]="saldo";
  		totales[5]="comision";
  		return totales;
  	}
  	if (GetVision().equals("INTERFAZ_2")) {
  		String[] totales = new String[8];
  		totales[0]="tarifa_yq";
  		totales[1]="iva_tua";
  		totales[2]="fuel";
  		totales[3]="base_tax_fuel";
  		totales[4]="comision";
  		totales[5]="price_comision";
  		totales[6]="fee";
  		totales[7]="total_fee";
  		
  		return totales;
  	}
  	if (GetVision().equals("INTERFAZ_3")) {
  		String[] totales = new String[7];
  		totales[0]="importe";
  		totales[1]="comision";
  		totales[2]="net_price";
  		totales[3]="iva_tua";
  		totales[4]="i3_total_local";
  		totales[5]="i3_total";
  		totales[6]="i3_total_usd";
    	return totales;
  	}
  	return null;
	}
	

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	if (GetVision().equals("INTERFAZ_1")) {
    	zLista.AddColumnaLista("Serie","tipo_doc_punto");
    	zLista.AddColumnaLista("No.Documento","nro_doc");
    	zLista.AddColumnaLista("No.Boleto","nro_boleto");
      zLista.AddColumnaLista("Aerolinea","prestador");
    	zLista.AddColumnaLista("Fecha","fecha_doc_arabian");
     	zLista.AddColumnaLista("Ruta","ruta");
     	zLista.AddColumnaLista("Nombre Pasajero","pasajero");
     	zLista.AddColumnaLista("Cve.Resrv.","reserva");
     	zLista.AddColumnaLista("Tarifa Base","tarifa");
    	zLista.AddColumnaLista("IVA","iva");
    	zLista.AddColumnaLista("TUA","tua");
    	zLista.AddColumnaLista("Importe","importe");
    	zLista.AddColumnaLista("F.P","forma_pago");
    	zLista.AddColumnaLista("Saldo a Pagar","saldo");
    	zLista.AddColumnaLista("Comisión","comision");
    	zLista.AddColumnaLista("% Comisión","porc_comision");
    	zLista.AddColumnaLista("Agente","agente");
   
  		return;
  	}
  	if (GetVision().equals("INTERFAZ_2")) {
    	zLista.AddColumnaLista("SERIE","tipo_doc");
      zLista.AddColumnaLista("DOCUMENT NUMBER","nro_doc");
      zLista.AddColumnaLista("Ticket #","aereo_ticket");
     	zLista.AddColumnaLista("PNR","reserva");
      zLista.AddColumnaLista("Form of Payment","forma_pago_show");
     	zLista.AddColumnaLista("Type of ticket","type_serv");
     	zLista.AddColumnaLista("Type of Passanger","type_pasajero");
     	zLista.AddColumnaLista("Base Fare","tarifa_yq");
    	zLista.AddColumnaLista("Tax","iva_tua");
    	zLista.AddColumnaLista("Fuel","fuel");
    	zLista.AddColumnaLista("Total ticket price","base_tax_fuel");
    	zLista.AddColumnaLista("Airline Commission","comision");
    	zLista.AddColumnaLista("Settlement price in BSP","price_comision");
    	zLista.AddColumnaLista("Consoldiator fee","fee");
    	zLista.AddColumnaLista("Settlment price for Consolidator","total_fee");
    	zLista.AddColumnaLista("Issuing date","fecha_pnr");
    	zLista.AddColumnaLista("Departure date","departure");
    	zLista.AddColumnaLista("Carrier","prestador");
      
  		return;
  	}
  	if (GetVision().equals("INTERFAZ_3")) {
      zLista.AddColumnaLista("DOCUMENT","tipo_doc");
    	zLista.AddColumnaLista("ISSUE DATE","fecha_doc");
      zLista.AddColumnaLista("INVOICE No ","nro_doc");
      zLista.AddColumnaLista("PNR","reserva");
      zLista.AddColumnaLista("PAX NAME","pasajero");
    	zLista.AddColumnaLista("AIRLINE","prestador");
      zLista.AddColumnaLista("TICKET","nro_boleto");
      zLista.AddColumnaLista("FORM OF PAYMENT","forma_pago");
     	zLista.AddColumnaLista("UNITE PRICE","tarifa");
     	zLista.AddColumnaLista("%","porc_comision");
      zLista.AddColumnaLista("COMMISSION","comision");
      zLista.AddColumnaLista("NET PRICE","net_price");
      zLista.AddColumnaLista("TAX","iva_tua");
      zLista.AddColumnaLista("TOTAL MXN","i3_total_local");
      zLista.AddColumnaLista("USD EXCHANGE RATE","i3_cotiz");
      zLista.AddColumnaLista("TOTAL","i3_total");
      zLista.AddColumnaLista("FEE","i3_fee");
      zLista.AddColumnaLista("TOTAL USD","i3_total_usd");
  		return;
  	} 
  	if (GetVision().equals("INTERFAZ_6")) {
      zLista.AddColumnaLista("Serie","tipo_doc");
      zLista.AddColumnaLista("No.Documento ","nro_doc");
     	zLista.AddColumnaLista("Aerolinea","prestador");
      zLista.AddColumnaLista("No.Boleto","nro_boleto");
      zLista.AddColumnaLista("Fecha","fecha_doc");
    	zLista.AddColumnaLista("Ruta","ruta");
      zLista.AddColumnaLista("Cve.Resrv.","reserva");
     	zLista.AddColumnaLista("Tarifa Base","tarifa");
    	zLista.AddColumnaLista("IVA","iva");
    	zLista.AddColumnaLista("TUA","tua");
     	zLista.AddColumnaLista("Importe","importe");
      zLista.AddColumnaLista("F.P.","forma_pago");
     	zLista.AddColumnaLista("Saldo a Pagar","saldo");
    	zLista.AddColumnaLista("Comisión BSP","bsp_comision");
     	zLista.AddColumnaLista("% Comisión BSP","bsp_comision_porc");
     	zLista.AddColumnaLista("Iva de Comisión BSP","bsp_comision_iva");

  		return;
  	} 
  	if (GetVision().equals("INTERFAZ_8_A")) {
      zLista.AddColumnaLista("ETICKET_NO","nro_boleto");
      zLista.AddColumnaLista("OTA_NAME","dk");
      zLista.AddColumnaLista("AL","prestador");
      zLista.AddColumnaLista("DOC_NUMBER","nro_boleto");
    	zLista.AddColumnaLista("TOTAL_DOC","importe");
     	zLista.AddColumnaLista("NET_FARE","tarifa");
    	zLista.AddColumnaLista("TAX","iva_tua");
    	zLista.AddColumnaLista("FEE","fee");
    	zLista.AddColumnaLista("COMM_PCT","bsp_comision_porc");
    	zLista.AddColumnaLista("COMM","bsp_comision");
    	zLista.AddColumnaLista("INCENTIVE_PCT","porc_incentivo");
    	zLista.AddColumnaLista("INCENTIVE","incentivo");
    	zLista.AddColumnaLista("REISSUE","reissue");
      zLista.AddColumnaLista("FP","forma_pago");
    	zLista.AddColumnaLista("PAX_NAME","pasajero");
    	zLista.AddColumnaLista("AS","as");
     	zLista.AddColumnaLista("TRNC","trnc");
     	zLista.AddColumnaLista("SVC","svc");
    	zLista.AddColumnaLista("GRAND_TOTAL","grand_total");
    	zLista.AddColumnaLista("GDS","gds");
     	zLista.AddColumnaLista("NOTE","note");
     	zLista.AddColumnaLista("ISSUE_DATE","fecha_doc");
     	zLista.AddColumnaLista("DEPARTUR_DATE","departure");

  		return;
  	} 
  	if (GetVision().equals("INTERFAZ_8_B")) {
      zLista.AddColumnaLista("Transactions","sale_fl");
      zLista.AddColumnaLista("Types","agencia_fl");
      zLista.AddColumnaLista("ETICKET_NO","nro_boleto");
      zLista.AddColumnaLista("Credit Fare","bsp_credit");
      zLista.AddColumnaLista("Credit Tax","bsp_credit_tax");
      zLista.AddColumnaLista("Cash Fare","bsp_cash");
      zLista.AddColumnaLista("Cash Tax","bsp_cash_tax");
			zLista.AddColumnaLista("Comm","bsp_comm");
 		  return;
  	} 
  	zLista.AddColumnaLista("Org.","organization");
  	zLista.AddColumnaLista("dk");
  	zLista.AddColumnaLista("tipo_doc");
  	zLista.AddColumnaLista("nro_doc");
  	zLista.AddColumnaLista("fecha_doc");
  	zLista.AddColumnaLista("nro_boleto");
  	zLista.AddColumnaLista("prestador");
  	zLista.AddColumnaLista("ruta");
  	zLista.AddColumnaLista("pasajero");
  	zLista.AddColumnaLista("reserva");
  	zLista.AddColumnaLista("tarifa");
  	zLista.AddColumnaLista("iva");
  	zLista.AddColumnaLista("tua");
  	zLista.AddColumnaLista("importe");
  	zLista.AddColumnaLista("saldo");
  	zLista.AddColumnaLista("comision");
  	zLista.AddColumnaLista("porc_comision");
  	zLista.AddColumnaLista("tipo_servicio");
  	zLista.AddColumnaLista("origen");
  	zLista.AddColumnaLista("clase");
  	zLista.AddColumnaLista("clases");
  	zLista.AddColumnaLista("lineas");
  	zLista.AddColumnaLista("forma_pago");
  	zLista.AddColumnaLista("porc_incentivo");
  	zLista.AddColumnaLista("incentivo");
  	zLista.AddColumnaLista("tipo");
  	zLista.AddColumnaLista("agente");

  
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addEditResponsive("DK", "dk").setOperator("=");
  	zFiltros.addCDateResponsive("Fecha Doc.", "fecha_doc");
   	zFiltros.addEditResponsive("Nro.doc.", "nro_doc").setOperator("=");
  	zFiltros.addEditResponsive("Nro.tkt.", "nro_boleto").setOperator("=");
   	zFiltros.addComboResponsive("Tipo doc", "tipo_doc", this.getDocs(), true);
   	zFiltros.addComboResponsive("Origen", "origen", this.getOrigenes(), true);
  	zFiltros.addEditResponsive("Reserva", "reserva").setOperator("ilike");
  	zFiltros.addEditResponsive("Forma de pago", "forma_pago").setOperator("=");
  	zFiltros.addEditResponsive("Clase", "clase").setOperator("ilike");
  	zFiltros.addEditResponsive("Prestador", "prestador").setOperator("ilike");
  	zFiltros.addEditResponsive("Pasajero", "pasajero").setOperator("ilike");
   	zFiltros.addEditResponsive("Ruta", "ruta").setOperator("ilike");
  	zFiltros.addEditResponsive("Tipo", "tipo").setOperator("=");
   	    
  	super.ConfigurarFiltros(zFiltros);
  }	

	private JWins getOrigenes() throws Exception {
		return JWins.CreateVirtualWins(ObtenerOrigenes());
	}
	public static JRecords<BizVirtual> ObtenerOrigenes() throws Exception {
		JRecords<BizVirtual> oBDs = JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD("TKM", "Tkm", 943));
		oBDs.addItem(JRecord.virtualBD("XSLX", "Excel", 941));
		return oBDs;
	}
	

	private JWins getDocs() throws Exception {
		JRecords<BizVirtual> oBDs = JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD("STE", "STE", 943));
		oBDs.addItem(JRecord.virtualBD("RFN", "RFN", 943));
		oBDs.addItem(JRecord.virtualBD("FCE", "FCE", 943));
	//	oBDs.addItem(JRecord.virtualBD("NCE", "NCE", 943));
		oBDs.addItem(JRecord.virtualBD("DXE", "DXE", 943));
		return JWins.CreateVirtualWins(ObtenerOrigenes());
	}
	public static JRecords<BizVirtual> ObtenerDocs() throws Exception {
		JRecords<BizVirtual> oBDs = JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD("TKM", "Tkm", 943));
		oBDs.addItem(JRecord.virtualBD("XLSX", "Excel", 941));
		return oBDs;
	}
}
