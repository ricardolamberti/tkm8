package pss.bsp.contrato.detalleAvianca.objetivos;

import java.util.Calendar;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.tourism.pnr.BizPNRTicket;

public class BizObjetivosAvianca extends JRecord {
	public static final String FULL_PUNTOS = "1";
	public static final String CON_ALGUNOS_PUNTOS = "2";
	public static final String SIN_PUNTOS = "3";
	
  public JString pCompany = new JString();
  public JLong pLinea = new JLong();
  public JLong pId = new JLong();
  public JLong pIdContrato = new JLong();
  public JLong pIdDetalle = new JLong();
  public JLong pIdDetalleRuta = new JLong();
  public JHtml pRuta = new JHtml();
  public JLong pCantidad = new JLong();
  public JLong pTotal = new JLong();
  public JLong pFaltante = new JLong();
  public JLong pPuntos = new JLong();
  public JString pLabelZonas = new JString();
  public JHtml pZonas = new JHtml();
  public JHtml pGrafico = new JHtml() {
  	public String asPrintealbleObject() throws Exception { return pRuta.getValue();};
  };
  public JFloat pMS = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pFMS = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pNextObj = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
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
  public JFloat pPremio = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JBoolean pCumple = new JBoolean();
  public JBoolean pConPuntos = new JBoolean();
  public JString pMensaje = new JString();
  

  public String getRuta() throws Exception {
  	return pRuta.getValue();
  }  
  public String getLabelZonas() throws Exception {
  	return pLabelZonas.getValue();
  }  
  public String getZonas() throws Exception {
  	return pZonas.getValue();
  }
  public String getMensajes() throws Exception {
  	return pMensaje.getValue();
  }
  public long getCantidad() throws Exception {
  	return pCantidad.getValue();
  }
  public long geTotal() throws Exception {
  	return pTotal.getValue();
  }
  public long getFaltante() throws Exception {
  	return pFaltante.getValue();
  }
  public long getPuntos() throws Exception {
  	return pPuntos.getValue();
  }
  public long getIdDetalle() throws Exception {
  	return pIdDetalle.getValue();
  }
  public long getIdDetalleRuta() throws Exception {
  	return pIdDetalleRuta.getValue();
  }
  public double getMS() throws Exception {
  	return pMS.getValue();
  }
  public double getFMS() throws Exception {
  	return pFMS.getValue();
  }
  public double getIngreso() throws Exception {
  	return pIngreso.getValue();
  }  
  public double getPremio() throws Exception {
  	return pPremio.getValue();
  }
  public double getTotal() throws Exception {
  	return pTotal.getValue();
  }  
  public double getIngresoAHoy() throws Exception {
  	return pIngresoAHoy.getValue();
  } 
  public double getPorcentaje() throws Exception {
  	return pPorcentaje.getValue();
  } 
  public double getTicketPromedio() throws Exception {
  	return pTicketPromedio.getValue();
  } 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizObjetivosAvianca() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "linea", pLinea );
    this.addItem( "id_contrato", pIdContrato );
    this.addItem( "id_detalle", pIdDetalle );
    this.addItem( "id_detalle_ruta", pIdDetalleRuta );
    this.addItem( "ruta", pRuta);
    this.addItem( "cantidad", pCantidad );
    this.addItem( "faltante", pFaltante );
    this.addItem( "total", pTotal );
    this.addItem( "puntos", pPuntos );
    this.addItem( "ms", pMS );
    this.addItem( "fms", pFMS );
    this.addItem( "next_obj", pNextObj );
    this.addItem( "zonas", pZonas );
    this.addItem( "labelzonas", pLabelZonas );
    this.addItem( "grafico", pGrafico );
    this.addItem( "ingreso", pIngreso );
    this.addItem( "ingreso_hoy", pIngresoAHoy );
    this.addItem( "premio", pPremio );
    this.addItem( "ticket_prom", pTicketPromedio );
    this.addItem( "cumple", pCumple );
    this.addItem( "mensaje", pMensaje );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id", "Id", true, false, 32 );
    this.addFixedItem( KEY, "linea", "Linea", false, false, 32 );
    this.addFixedItem( FIELD, "id_contrato", "Contrato", true, true, 18 );
    this.addFixedItem( FIELD, "id_detalle", "Detalle", true, true, 18 );
    this.addFixedItem( FIELD, "id_detalle_ruta", "Detalle Ruta", true, true, 18 );
    this.addFixedItem( FIELD, "ruta", "Ruta", true, true, 250 );
    this.addFixedItem( FIELD, "cantidad", "Cantidad tkt", true, true, 18 );
    this.addFixedItem( FIELD, "faltante", "Cantidad Faltante", true, true, 18 );
    this.addFixedItem( FIELD, "total", "Total", true, true, 18 );
    this.addFixedItem( FIELD, "puntos", "Puntos", true, true, 18 );
    this.addFixedItem( FIELD, "ms", "MS", true, true, 18,2 );
    this.addFixedItem( FIELD, "fms", "MS min tkt", true, true, 18,2 );
    this.addFixedItem( FIELD, "next_obj", "Sig.obj.", true, true, 18,2 );
    this.addFixedItem( FIELD, "zonas", "Zonas", true, true, 18,2 );
    this.addFixedItem( FIELD, "labelzonas", "Label Zonas", true, true, 100 );
    this.addFixedItem( FIELD, "grafico", "Objetivo", true, false, 18,2 );
    this.addFixedItem( FIELD, "ingreso", "Ingreso Volado", true, true, 18,2 );
    this.addFixedItem( FIELD, "ingreso_hoy", "Ingreso Volado a hoy", true, true, 18,2 );
    this.addFixedItem( FIELD, "premio", "Premio", true, true, 18,2 );
    this.addFixedItem( FIELD, "ticket_prom", "Ticket prom.", true, true, 18,2 );
    this.addFixedItem( FIELD, "cumple", "Cumple", true, true, 1 );
    this.addFixedItem( FIELD, "mensaje", "Mensaje", true, true, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return null; }

  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public boolean isCumple() throws Exception {
  	if (pCumple.getValue()) return true;
  	return false;
  }
  public boolean isCumplible() throws Exception {
  	if (pConPuntos.getValue()) return true;
  	return false;
  }

  BizDetalle objDetalleRuta;
  public void setObjDetalleRuta(BizDetalle detRuta) throws Exception {
  	objDetalleRuta=detRuta;
  }
  public BizDetalle getObjDetalleRuta() throws Exception {
  	if (objDetalleRuta!=null) return objDetalleRuta;
  	BizDetalle det = new BizDetalle();
  	det.dontThrowException(true);
  	if (!det.read(pIdDetalleRuta.getValue())) return null;
  	return objDetalleRuta = det;
  }
  
  public JRecords<BizPNRTicket> getDetalle() throws Exception {
		JRecords<BizPNRTicket> regs= new JRecords<BizPNRTicket>(BizPNRTicket.class);

		BizDetalle detalle = new BizDetalle();
    detalle.dontThrowException(true);
    if (!detalle.read(pIdDetalle.getValue())) return null;
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());

		String sql="select *";
		sql+= " from TUR_PNR_BOLETO ";
		sql+= " WHERE (TUR_PNR_BOLETO.company= '"+pCompany.getValue()+"')  AND  TUR_PNR_BOLETO.market like '%"+pRuta.getValue()+"%'  ";
		sql+= " AND  TUR_PNR_BOLETO.void = 'N' AND    date_part('year',TUR_PNR_BOLETO.creation_date) = "+periodo.get(Calendar.YEAR)+"   AND ";
		sql+= " TUR_PNR_BOLETO.codigoAEROLINEA = '"+detalle.getIdAerolinea()+"' AND  ";
		sql+= " TUR_PNR_BOLETO.creation_date between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"'  ";
		if (detalle.hasIatas())
  		sql+= " AND TUR_PNR_BOLETO.nro_iata in ("+detalle.getIatas()+")  ";
		regs.SetSQL(sql);
		return regs;
	}

  public JRecords<BizPNRTicket> getDetalleGanancia() throws Exception {
		JRecords<BizPNRTicket> regs= new JRecords<BizPNRTicket>(BizPNRTicket.class);

		BizDetalle detalle = new BizDetalle();
    detalle.dontThrowException(true);
    if (!detalle.read(pIdDetalle.getValue())) return null;
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());

		String sql="select *";
		sql+= " from TUR_PNR_BOLETO   ";
		sql+= " WHERE (TUR_PNR_BOLETO.company= '"+pCompany.getValue()+"')  AND  TUR_PNR_BOLETO.market like '%"+pRuta.getValue()+"%'  ";
		sql+= " AND  TUR_PNR_BOLETO.void = 'N'  AND  date_part('year',TUR_PNR_BOLETO.creation_date) = "+periodo.get(Calendar.YEAR)+"   AND ";
		sql+= " TUR_PNR_BOLETO.codigoAEROLINEA = '"+detalle.getIdAerolinea()+"' AND  ";
		sql+= " TUR_PNR_BOLETO.departure_date between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"'  ";
		if (detalle.hasIatas())
  		sql+= " AND TUR_PNR_BOLETO.nro_iata in ("+detalle.getIatas()+")  ";
		regs.SetSQL(sql);
		return regs;
	}  
  public JRecords<BizPNRTicket> getDetalleTotal() throws Exception {
		JRecords<BizPNRTicket> regs= new JRecords<BizPNRTicket>(BizPNRTicket.class);

		BizDetalle detalle = new BizDetalle();
    detalle.dontThrowException(true);
    if (!detalle.read(pIdDetalle.getValue())) return null;
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());

		String sql="select *";
		sql+= " from TUR_PNR_BOLETO ";
		sql+= " WHERE (TUR_PNR_BOLETO.company= '"+pCompany.getValue()+"')  AND  TUR_PNR_BOLETO.market like '%"+pRuta.getValue()+"%'  ";
		sql+= " AND  TUR_PNR_BOLETO.void = 'N' AND    date_part('year',TUR_PNR_BOLETO.creation_date) = "+periodo.get(Calendar.YEAR)+"   AND ";
		sql+= " TUR_PNR_BOLETO.creation_date between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"'  ";
		if (detalle.hasIatas())
  		sql+= " AND TUR_PNR_BOLETO.nro_iata in ("+detalle.getIatas()+")  ";
		regs.SetSQL(sql);
		return regs;
	} 	

}
