package pss.bsp.contrato.rutas.ms;

import java.util.Calendar;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;

public class BizMSs extends JRecords<BizMS> {
;
	public Class<BizMS> getBasedClass() {
		return BizMS.class;
	}
	
	
	String conclusion;

	Double voladoAHoy;



	Double volado;
	Double totalPremiosAHoy;
	Double totalPremios;
	Double totalPremiosExtra;
	public Double getTotalPremiosAHoy() {
		return totalPremiosAHoy;
	}

	public void setTotalPremiosAHoy(Double totalPremiosAHoy) {
		this.totalPremiosAHoy = totalPremiosAHoy;
	}
	
	public Double getTotalPremiosExtra() {
		return totalPremiosExtra;
	}

	public void setTotalPremiosExtra(Double totalPremiosExtra) {
		this.totalPremiosExtra = totalPremiosExtra;
	}

	public Double getTotalPremios() {
		return totalPremios;
	}

	public void setTotalPremios(Double totalPremios) {
		this.totalPremios = totalPremios;
	}
	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public Double getVoladoAHoy() {
		return voladoAHoy;
	}

	public void setVoladoAHoy(Double voladoAHoy) {
		this.voladoAHoy = voladoAHoy;
	}

	public Double getVolado() {
		return volado;
	}

	public void setVolado(Double volado) {
		this.volado = volado;
	}

	// market share
	// ms por ruta
	// a un boleto de recibir premio
	
	
	// importe
	// rutas que mas redituan
	// rutas que menos redituan
	
	
	// volado
	// cuando se compro
	
	

	@Override
	public boolean readAll() throws Exception {
		this.endStatic();
    this.setStatic(true);
	
    String idcompany = getFilterValue("company");
    long iddetalle = Long.parseLong(getFilterValue("detalle"));
    String rutaFilter = getFilterValue("ruta");
    String factor = getFilterValue("factor");
    String aerolinea = getFilterValue("aerolinea");
    
    BizDetalle detalle = new BizDetalle();
    detalle.dontThrowException(true);
    if (!detalle.read(iddetalle)) 
    	return false;
 
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());
    
		JBaseRegistro regs=JBaseRegistro.recordsetFactory();
		String sql="select  TUR_PNR_BOLETO.company as company, ROW_NUMBER() OVER() as linea,'"+rutaFilter+"' as ruta,  codigoAEROLINEA as aerolinea, count( distinct TUR_PNR_BOLETO.*) as cantidad ";
		sql+= " ,(select count( distinct TUR_PNR_BOLETO.*) ";
		sql+= " from TUR_PNR_BOLETO WHERE TUR_PNR_BOLETO.market like '%BOK-"+rutaFilter+"%' ";
		sql+= " AND (TUR_PNR_BOLETO.company= '"+idcompany+"')   ";
		sql+= " AND  TUR_PNR_BOLETO.void = 'N'   AND  date_part('year',TUR_PNR_BOLETO.creation_date) = "+periodo.get(Calendar.YEAR)+"   AND ";
		sql+= " TUR_PNR_BOLETO.creation_date between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"'  ";
		sql+= ") as total ";
		sql+= " from TUR_PNR_BOLETO WHERE TUR_PNR_BOLETO.market like '%BOK-"+rutaFilter+"%' ";
		sql+= " AND (TUR_PNR_BOLETO.company= '"+idcompany+"')   ";
		sql+= " AND  TUR_PNR_BOLETO.void = 'N'   AND  date_part('year',TUR_PNR_BOLETO.creation_date) = "+periodo.get(Calendar.YEAR)+"   AND ";
		sql+= " TUR_PNR_BOLETO.creation_date between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"'  ";
		sql+= " group by TUR_PNR_BOLETO.company,TUR_PNR_BOLETO.codigoAEROLINEA";
		regs.ExecuteQuery(sql);
		boolean unaVez=true;
  	while (regs.next()) {
			BizMS obj = new BizMS();
			obj.pCompany.setValue(regs.CampoAsStr("company"));
			obj.pLinea.setValue(regs.CampoAsStr("linea"));
			obj.pAerolinea.setValue(regs.CampoAsStr("aerolinea"));
			if (aerolinea!=null && aerolinea.indexOf(obj.pAerolinea.getValue())!=-1 && unaVez) {
				obj.pCantidad.setValue(regs.CampoAsLong("cantidad")+Long.parseLong(factor));
				unaVez=false;
			} else
				obj.pCantidad.setValue(regs.CampoAsLong("cantidad"));

			
			obj.pRuta.setValue(regs.CampoAsStr("ruta"));
			double total = regs.CampoAsLong("total")+(float)Long.parseLong(factor);
			
			obj.pPorcentaje.setValue(JTools.rd(total==0?0:(obj.pCantidad.getValue()/total)*100,2));
			addItem(obj);
  	}

		return true;
		
	}

  
}