package pss.bsp.contrato.detalleCopaWS.objetivos;

import java.util.Calendar;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRTicket;

public class BizObjetivosCopaWS extends JRecord {
	public static final String SOLO_CUMPLE = "1";
	public static final String SOLO_NO_CUMPLE = "2";
	public static final String FALTA_CANTIDAD = "3";
	public static final String DESTACADOS = "4";
	
  public JString pCompany = new JString();
  public JLong pLinea = new JLong();
  public JLong pId = new JLong();
  public JLong pIdContrato = new JLong();
  public JLong pIdDetalle = new JLong();
  public JHtml pRuta = new JHtml();
  public JFloat pCantidadTkt = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pTotalTkt = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pCantidad = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pTotal = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pMS = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JString pLabelZonas = new JString();
  public JString pZonas = new JString();
  public JString pCompanyPDF = new JString();
  public JString pIdPDF = new JString();
  public JString pGrafico = new JString() {
  	public String asPrintealbleObject() throws Exception { return pRuta.getValue();};
  };
  public JFloat pIngreso = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pIngresoAHoy = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pPorcentaje = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pTicketPromedio = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JString pMensaje = new JString();
  public JIntervalDate pFecha = new JIntervalDate();
  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizObjetivosCopaWS() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "linea", pLinea );
    this.addItem( "id_contrato", pIdContrato );
    this.addItem( "id_detalle", pIdDetalle );
    this.addItem( "ruta", pRuta);
    this.addItem( "cantidad", pCantidad );
    this.addItem( "total", pTotal );
    this.addItem( "cantidad_tkt", pCantidadTkt );
    this.addItem( "total_tkt", pTotalTkt );
    this.addItem( "ms", pMS );
    this.addItem( "zonas", pZonas );
    this.addItem( "labelzonas", pLabelZonas );
    this.addItem( "grafico", pGrafico );
    this.addItem( "porcentaje", pPorcentaje );
    this.addItem( "ingreso", pIngreso );
    this.addItem( "ingreso_hoy", pIngresoAHoy );
    this.addItem( "ticket_prom", pTicketPromedio );
    this.addItem( "mensaje", pMensaje );
    this.addItem( "fecha", pFecha );
    this.addItem( "pdf_company", pCompanyPDF );
    this.addItem( "pdf_id", pIdPDF );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id", "Id", true, false, 32 );
    this.addFixedItem( KEY, "linea", "Linea", false, false, 32 );
    this.addFixedItem( FIELD, "pdf_company", "COmpany del pdf", true, true, 250 );
    this.addFixedItem( FIELD, "pdf_id", "Id del pdf", true, true, 250 );
    this.addFixedItem( FIELD, "id_contrato", "Contrato", true, true, 18 );
    this.addFixedItem( FIELD, "id_detalle", "Detalle", true, true, 18 );
    this.addFixedItem( FIELD, "ruta", "Ruta", true, true, 250 );
    this.addFixedItem( FIELD, "cantidad", "Cantidad imp", true, true, 18 );
    this.addFixedItem( FIELD, "total", "Total imp", true, true, 18 );
    this.addFixedItem( FIELD, "cantidad_tkt", "Cantidad tkt", true, true, 18 );
    this.addFixedItem( FIELD, "total_tkt", "Total tkt", true, true, 18 );
    this.addFixedItem( FIELD, "ms", "MS", true, true, 18,2 );
    this.addFixedItem( FIELD, "zonas", "Zonas", true, true, 18,2 );
    this.addFixedItem( FIELD, "labelzonas", "Label Zonas", true, true, 100 );
    this.addFixedItem( FIELD, "grafico", "Objetivo", true, true, 18,2 );
    this.addFixedItem( FIELD, "porcentaje", "Porc.del fms", true, true, 18,2 );
    this.addFixedItem( FIELD, "ingreso", "Ingreso Volado", true, true, 18,2 );
    this.addFixedItem( FIELD, "ingreso_hoy", "Ingreso Volado a hoy", true, true, 18,2 );
    this.addFixedItem( FIELD, "ticket_prom", "Ticket prom.", true, true, 18,2 );
    this.addFixedItem( FIELD, "mensaje", "Mensaje", true, true, 250 );
    this.addFixedItem( VIRTUAL, "fecha", "fecha", true, false, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return null; }
  BizDetalle objDetalle=null;
  public BizDetalle getObjDetalle() throws Exception {
  	if (objDetalle!=null) return objDetalle;
		BizDetalle detalle = new BizDetalle();
    detalle.dontThrowException(true);
    if (!detalle.read(pIdDetalle.getValue())) return null;
    return objDetalle=detalle;
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public JRecords<BizBooking> getDetalleTotal() throws Exception {
		JRecords<BizBooking> regs= new JRecords<BizBooking>(BizBooking.class);

		BizDetalle detalle = getObjDetalle();
     if (detalle==null) return null;
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());

		String sql="select *";
		sql+= " from TUR_PNR_BOOKING ";
		sql+= " WHERE (TUR_PNR_BOOKING.company= '"+pCompany.getValue()+"')  AND ";
//		sql+= " TUR_PNR_BOLETO.market like '%BOK-"+pRuta.getValue()+"%'  ";
		sql+= " (TUR_PNR_BOOKING.mercado like '%BOK-"+pRuta.getValue()+"%' )  ";
		sql+= "  AND  date_part('year',TUR_PNR_BOOKING.fecha_emision) = "+periodo.get(Calendar.YEAR)+"   AND ";
		sql+= " TUR_PNR_BOOKING.fecha_emision between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"'  ";
		regs.SetSQL(sql);
		return regs;
	}
  public JRecords<BizBooking> getDetalle() throws Exception {
		JRecords<BizBooking> regs= new JRecords<BizBooking>(BizBooking.class);

		BizDetalle detalle = new BizDetalle();
    detalle.dontThrowException(true);
    if (!detalle.read(pIdDetalle.getValue())) return null;
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());

		String sql="select *";
		sql+= " from TUR_PNR_BOOKING ";
		sql+= " WHERE (TUR_PNR_BOOKING.company= '"+pCompany.getValue()+"')  AND  TUR_PNR_BOOKING.mercado like '%BOK-"+pRuta.getValue()+"%'  ";
		sql+= " AND  date_part('year',TUR_PNR_BOOKING.fecha_emision) = "+periodo.get(Calendar.YEAR)+"   AND ";
		sql+= " TUR_PNR_BOOKING.carrier = '"+detalle.getIdAerolinea()+"' AND  ";
		sql+= " TUR_PNR_BOOKING.fecha_emision between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"'  ";
		regs.SetSQL(sql);
		return regs;
	}
  public JRecords<BizPNRTicket> getDetalleTotalTickets() throws Exception {
		JRecords<BizPNRTicket> regs= new JRecords<BizPNRTicket>(BizPNRTicket.class);

		BizDetalle detalle = getObjDetalle();
    if (detalle==null) return null;
     Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());
    String fechaFilter = getFilterValue("fecha");
    String fdesde = JDateTools.DateToString(detalle.getFDesde());
    String fhasta = JDateTools.DateToString(detalle.getFHasta());
    if (fechaFilter!=null && !fechaFilter.equals("")) {
    	JIntervalDate idf = new JIntervalDate();
    	idf.setValueFormUI(fechaFilter);
      fdesde = JDateTools.DateToString(idf.getStartDateValue());
      fhasta = JDateTools.DateToString(idf.getEndDateValue());
    }
    String ruta = pRuta.getValue().substring(0,3)+"/"+pRuta.getValue().substring(3,6);
		String sql="select *";
		sql+= " from TUR_PNR_BOLETO ";
		sql+= " WHERE (TUR_PNR_BOLETO.company= '"+pCompany.getValue()+"')  AND ";
		sql+= " TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' AND ";
		sql+= " (TUR_PNR_BOLETO.mini_route = '"+ruta+"' )  AND ";
		sql+= " TUR_PNR_BOLETO.creation_date between '"+fdesde+"' and '"+fhasta+"'  ";
		regs.SetSQL(sql);
		return regs;
	}
  public JRecords<BizPNRTicket> getDetalleTickets() throws Exception {
		JRecords<BizPNRTicket> regs= new JRecords<BizPNRTicket>(BizPNRTicket.class);

		BizDetalle detalle = getObjDetalle();
    if (detalle==null) return null;
     Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());
    String fechaFilter = getFilterValue("fecha");
    String fdesde = JDateTools.DateToString(detalle.getFDesde());
    String fhasta = JDateTools.DateToString(detalle.getFHasta());
    if (fechaFilter!=null && !fechaFilter.equals("")) {
    	JIntervalDate idf = new JIntervalDate();
    	idf.setValueFormUI(fechaFilter);
      fdesde = JDateTools.DateToString(idf.getStartDateValue());
      fhasta = JDateTools.DateToString(idf.getEndDateValue());
    }
    String ruta = pRuta.getValue().substring(0,3)+"/"+pRuta.getValue().substring(3,6);
    String sql="select *";
		sql+= " from TUR_PNR_BOLETO ";
		sql+= " WHERE (TUR_PNR_BOLETO.company= '"+pCompany.getValue()+"')  AND  ";
		sql+= " TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' AND ";
		sql+= " (TUR_PNR_BOLETO.mini_route = '"+ruta+"' )  AND ";
		sql+= " TUR_PNR_BOLETO.codigoaerolinea = '"+detalle.getIdAerolinea()+"' AND  ";
		sql+= " TUR_PNR_BOLETO.creation_date between '"+fdesde+"' and '"+fhasta+"'  ";
		regs.SetSQL(sql);
		return regs;
	}
  
  public JRecords<BizPNRTicket> getDetalleGanancia() throws Exception {
		JRecords<BizPNRTicket> regs= new JRecords<BizPNRTicket>(BizPNRTicket.class);

		BizDetalle detalle = getObjDetalle();
    if (detalle==null) return null;
     Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());

		String sql="select *";
		sql+= " from TUR_PNR_BOLETO   ";
		sql+= " WHERE (TUR_PNR_BOLETO.company= '"+pCompany.getValue()+"')  AND  TUR_PNR_BOLETO.market like '%BOK-"+pRuta.getValue()+"%' ";
		sql+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S'  AND  date_part('year',TUR_PNR_BOLETO.creation_date) = "+periodo.get(Calendar.YEAR)+"   AND ";
		sql+= " TUR_PNR_BOLETO.codigoAEROLINEA = '"+detalle.getIdAerolinea()+"' AND  ";
		sql+= " TUR_PNR_BOLETO.departure_date between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"'  ";
		regs.SetSQL(sql);
		return regs;
	}  


}
