package pss.bsp.contrato.detalleAvianca.grilla;

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

public class BizObjetivosGrilla extends JRecord {
	public static final String SOLO_CUMPLE = "1";
	public static final String SOLO_NO_CUMPLE = "2";
	public static final String FALTA_CANTIDAD = "3";
	
  public JString pCompany = new JString();
  public JLong pLinea = new JLong();
  public JLong pId = new JLong();
  public JLong pIdContrato = new JLong();
  public JLong pIdDetalle = new JLong();
  public JString pRuta = new JString();
  public JLong pCantidad = new JLong();
  public JLong pTotal = new JLong();
  public JLong pFaltante = new JLong();
  public JLong pPuntos = new JLong();
  public JHtml pLabelZonas1 = new JHtml();
  public JString pMS1 = new JString();
  public JString pPuntos1 = new JString();
  public JHtml pLabelZonas2 = new JHtml();
  public JString pMS2 = new JString();
  public JString pPuntos2 = new JString();
  public JHtml pLabelZonas3 = new JHtml();
  public JString pMS3 = new JString();
  public JString pPuntos3 = new JString();
  public JHtml pLabelZonas4 = new JHtml();
  public JString pMS4 = new JString();
  public JString pPuntos4 = new JString();
  public JHtml pLabelZonas5 = new JHtml();
  public JString pMS5 = new JString();
  public JString pPuntos5 = new JString();
  public JHtml pLabelZonas6 = new JHtml();
  public JString pMS6 = new JString();
  public JString pPuntos6 = new JString();
  public JHtml pLabelZonas7 = new JHtml();
  public JString pMS7 = new JString();
  public JString pPuntos7 = new JString();
  public JHtml pLabelZonas8 = new JHtml();
  public JString pMS8 = new JString();
  public JString pPuntos8 = new JString();
  public JHtml pLabelZonas9 = new JHtml();
  public JString pMS9 = new JString();
  public JString pPuntos9 = new JString();
  public JString pGrafico = new JString();
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
  public JLong pCumple = new JLong();
  public JBoolean pCumplible = new JBoolean();
  public JString pMensaje = new JString();
  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizObjetivosGrilla() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "linea", pLinea );
    this.addItem( "id_contrato", pIdContrato );
    this.addItem( "id_detalle", pIdDetalle );
    this.addItem( "ruta", pRuta);
    this.addItem( "cantidad", pCantidad );
    this.addItem( "faltante", pFaltante );
    this.addItem( "total", pTotal );
    this.addItem( "puntos", pPuntos );
    this.addItem( "ms", pMS );
    this.addItem( "fms", pFMS );
    this.addItem( "ms1", pMS1 );
    this.addItem( "puntos1", pPuntos1 );
    this.addItem( "labelzonas1", pLabelZonas1 );
    this.addItem( "ms2", pMS2 );
    this.addItem( "puntos2", pPuntos2 );
    this.addItem( "labelzonas2", pLabelZonas2 );
    this.addItem( "ms3", pMS3 );
    this.addItem( "puntos3", pPuntos3 );
    this.addItem( "labelzonas3", pLabelZonas3 );
    this.addItem( "ms4", pMS4 );
    this.addItem( "puntos4", pPuntos4 );
    this.addItem( "labelzonas4", pLabelZonas4 );
    this.addItem( "ms5", pMS5 );
    this.addItem( "puntos5", pPuntos5 );
    this.addItem( "labelzonas5", pLabelZonas5 );
    this.addItem( "ms6", pMS6 );
    this.addItem( "puntos6", pPuntos6 );
    this.addItem( "labelzonas6", pLabelZonas6 );
    this.addItem( "ms7", pMS7 );
    this.addItem( "puntos7", pPuntos7 );
    this.addItem( "labelzonas7", pLabelZonas7 );
    this.addItem( "ms8", pMS8 );
    this.addItem( "puntos8", pPuntos8 );
    this.addItem( "labelzonas8", pLabelZonas8 );
    this.addItem( "ms9", pMS9 );
    this.addItem( "puntos9", pPuntos9 );
    this.addItem( "labelzonas9", pLabelZonas9 );
    this.addItem( "grafico", pGrafico );
    this.addItem( "ingreso", pIngreso );
    this.addItem( "ingreso_hoy", pIngresoAHoy );
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
    this.addFixedItem( FIELD, "ruta", "Ruta", true, true, 250 );
    this.addFixedItem( FIELD, "cantidad", "Cantidad tkt", true, true, 18 );
    this.addFixedItem( FIELD, "faltante", "Cantidad Faltante", true, true, 18 );
    this.addFixedItem( FIELD, "total", "Total", true, true, 18 );
    this.addFixedItem( FIELD, "puntos", "Puntos", true, true, 18 );
    this.addFixedItem( FIELD, "ms", "MS", true, true, 18,2 );
    this.addFixedItem( FIELD, "fms", "MS min tkt", true, true, 18,2 );
    this.addFixedItem( FIELD, "labelzonas1", "Meta 1", true, false, 100 );
    this.addFixedItem( FIELD, "ms1", "MS 1", true, false, 18,2 );
    this.addFixedItem( FIELD, "puntos1", "Puntos 1", true, false, 18,2 );
    this.addFixedItem( FIELD, "labelzonas2", "Meta 2", true, false, 100 );
    this.addFixedItem( FIELD, "ms2", "MS 2", true, false, 18,2 );
    this.addFixedItem( FIELD, "puntos2", "Puntos 2", true, false, 18,2 );
    this.addFixedItem( FIELD, "labelzonas3", "Meta 3", true, false, 100 );
    this.addFixedItem( FIELD, "ms3", "MS 3", true, false, 18,2 );
    this.addFixedItem( FIELD, "puntos3", "Puntos 3", true, false, 18,2 );
    this.addFixedItem( FIELD, "labelzonas4", "Meta 4", true, false, 100 );
    this.addFixedItem( FIELD, "ms4", "MS 4", true, false, 18,2 );
    this.addFixedItem( FIELD, "puntos4", "Puntos 4", true, false, 18,2 );
    this.addFixedItem( FIELD, "labelzonas5", "Meta 5", true, false, 100 );
    this.addFixedItem( FIELD, "ms5", "MS 5", true, false, 18,2 );
    this.addFixedItem( FIELD, "puntos5", "Puntos 5", true, false, 18,2 );
    this.addFixedItem( FIELD, "labelzonas6", "Meta 6", true, false, 100 );
    this.addFixedItem( FIELD, "ms6", "MS 6", true, false, 18,2 );
    this.addFixedItem( FIELD, "puntos6", "Puntos 6", true, false, 18,2 );
    this.addFixedItem( FIELD, "labelzonas7", "Meta 7", true, false, 100 );
    this.addFixedItem( FIELD, "ms7", "MS 7", true, false, 18,2 );
    this.addFixedItem( FIELD, "puntos7", "Puntos 7", true, false, 18,2 );
    this.addFixedItem( FIELD, "labelzonas8", "Meta 8", true, false, 100 );
    this.addFixedItem( FIELD, "ms8", "MS 8", true, false, 18,2 );
    this.addFixedItem( FIELD, "puntos8", "Puntos 8", true, false, 18,2 );
    this.addFixedItem( FIELD, "labelzonas9", "Meta 9", true, false, 100 );
    this.addFixedItem( FIELD, "ms9", "MS 9", true, false, 18,2 );
    this.addFixedItem( FIELD, "puntos9", "Puntos 9", true, false, 18,2 );
    this.addFixedItem( FIELD, "grafico", "Objetivo", true, false, 18,2 );
    this.addFixedItem( FIELD, "ingreso", "Ganancia", true, true, 18,2 );
    this.addFixedItem( FIELD, "ingreso_hoy", "Ingreso Volado a hoy", true, true, 18,2 );
    this.addFixedItem( FIELD, "ticket_prom", "Ticket prom.", true, true, 18,2 );
    this.addFixedItem( FIELD, "cumple", "Cumple", true, true, 18 );
    this.addFixedItem( FIELD, "mensaje", "Mensaje", true, true, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return null; }

  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public long getCumple() throws Exception {
  	return pCumple.getValue();
  }
  
  public void setCumple(long zValue) throws Exception {
  	pCumple.setValue(zValue);
  }
  public boolean isCumplible() throws Exception {
  	if (pCumplible.getValue()) return true;
  	return false;
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
		regs.SetSQL(sql);
		return regs;
	}  


}
