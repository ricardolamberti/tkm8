package pss.bsp.contrato.rutas;

import java.util.Calendar;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;
import pss.tourism.pnr.BizPNRTicket;

public class BizObjetivosRutas extends JRecords<BizObjetivosRuta> {
	;
	public Class<BizObjetivosRuta> getBasedClass() {
		return BizObjetivosRuta.class;
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
		String conclusion = "";
		long factor=0;
		this.endStatic();
		this.setStatic(true);

		String idcompany = getFilterValue("company");
		long idcontrato = Long.parseLong(getFilterValue("contrato"));
		long iddetalle = Long.parseLong(getFilterValue("detalle"));
 		JOrderedMap<String, Double> acumPremios = JCollectionFactory.createOrderedMap();
		BizDetalle detalle = new BizDetalle();
		detalle.dontThrowException(true);
		if (!detalle.read(iddetalle))
			return false;

		int id = 0;

		// grabar fecha generacion
		// fecha vigencia

		Calendar periodo = Calendar.getInstance();
		periodo.setTime(detalle.getFDesde());

		String rutaFilter = getFilterValue("ruta");
		String cumpleFilter = getFilterValue("mensaje");

//		if (cumpleFilter.equals(BizObjetivosRuta.MAS_10)) factor=10;
//		else if (cumpleFilter.equals(BizObjetivosRuta.MAS_20)) factor=20;
//		else if (cumpleFilter.equals(BizObjetivosRuta.MAS_50)) factor=50;
//		else if (cumpleFilter.equals(BizObjetivosRuta.MAS_100)) factor=100;
		// cuantos tickets de una ruta tengo que vender, para pasar al siguiente
		// objetivo?
		// y cuanto gano si llego?

		double objetivo = detalle.getValorActual();
		double ganancia = detalle.getValorTotal();
		double premio = getPremio(detalle, objetivo, ganancia);

		JBaseRegistro regs = JBaseRegistro.recordsetFactory();
		String sql = "select  TUR_PNR_BOLETO.company as company, ROW_NUMBER() OVER() as linea, max(codigoAEROLINEA) as aerolinea,BSP_DETALLE_MERCADO.marketing_id as ruta, count( distinct TUR_PNR_BOLETO.*) as cantidad,  ";
		sql += " ( select count( distinct b.*)   from TUR_PNR_BOLETO b WHERE (b.company=  TUR_PNR_BOLETO.company)   AND  b.void = 'N'   AND  ";
		sql += " date_part('year',b.creation_date) = " + periodo.get(Calendar.YEAR) + "    AND  b.market like '%BOK-'||BSP_DETALLE_MERCADO.marketing_id ||'%'  ";
		if (rutaFilter != null) {
			sql += " AND BSP_DETALLE_MERCADO.marketing_id ilike '%" + rutaFilter + "%'";
		}
		sql += " AND  b.creation_date between '" + JDateTools.DateToString(detalle.getFDesde()) + "' and '" + JDateTools.DateToString(detalle.getFHasta()) + "' ) as total,MAX(BSP_DETALLE_MERCADO.fms) as fms ";
		sql += " from TUR_PNR_BOLETO JOIN BSP_DETALLE_MERCADO ON BSP_DETALLE_MERCADO.company='" + detalle.getCompany() + "' AND id_contrato='" + detalle.getId() + "' AND id_detalle='" + detalle.getLinea() + "' AND  TUR_PNR_BOLETO.market like '%BOK-'||BSP_DETALLE_MERCADO.marketing_id||'%' ";
		if (rutaFilter != null) {
			sql += " AND BSP_DETALLE_MERCADO.marketing_id ilike  '%" + rutaFilter + "%'";

		}
		sql += " WHERE (TUR_PNR_BOLETO.company= '" + idcompany + "')   ";
		sql += " AND  TUR_PNR_BOLETO.void = 'N'   AND  date_part('year',TUR_PNR_BOLETO.creation_date) = " + periodo.get(Calendar.YEAR) + "   AND ";
		sql += " TUR_PNR_BOLETO.codigoAEROLINEA in (" + (detalle.getIdAerolinea().startsWith("'") ? detalle.getIdAerolinea() : "'" + detalle.getIdAerolinea() + "'") + ") AND  ";
		sql += " TUR_PNR_BOLETO.creation_date between '" + JDateTools.DateToString(detalle.getFDesde()) + "' and '" + JDateTools.DateToString(detalle.getFHasta()) + "'  ";
		sql += " group by TUR_PNR_BOLETO.company,BSP_DETALLE_MERCADO.marketing_id";
		regs.ExecuteQuery(sql);

		JOrderedMap<String, Double> treeZonas = JCollectionFactory.createOrderedMap();
		int i = 1;
		String zonas = "";
		String zonaLabels = "";
		JIterator<BizNivel> itN = detalle.getObjNiveles().getStaticIterator();
		while (itN.hasMoreElements()) {
			BizNivel nivel = itN.nextElement();
			nivel.getValor();
			String label = nivel.getValorPerc((double) nivel.getValor(), (double) i);
			treeZonas.addElement(JTools.LPad("" + i, 3, "0") + (label.equals("") ? "nivel " + i : label), nivel.getValor());
			i++;
		}

		long tkt = 0;
		double volado = 0;
		double voladoAHoy = 0;
		double acum = 0;
		double premioExtra = 0;
		while (regs.next()) {
			BizObjetivosRuta obj = new BizObjetivosRuta();
			obj.pCompany.setValue(regs.CampoAsStr("company"));
			obj.pId.setValue(detalle.getLinea());
			obj.pLinea.setValue(regs.CampoAsLong("linea"));
			obj.pIdAerolinea.setValue(detalle.getIdAerolinea());
			obj.pRuta.setValue(regs.CampoAsStr("ruta"));
			obj.pCantidad.setValue(regs.CampoAsLong("cantidad")+factor);
			obj.pTotal.setValue(regs.CampoAsLong("total")+factor);
			obj.pFMS.setValue(regs.CampoAsFloat("fms"));
			obj.pFactor.setValue(factor);
			obj.pMS.setValue(getMS(obj.pCantidad.getValue(), obj.pTotal.getValue()));

			zonas = "";
			zonaLabels = "";
			double lastVal = 0;
			JIterator<String> itz = treeZonas.getKeyIterator();
			while (itz.hasMoreElements()) {
				String keyZona = itz.nextElement();
				Double valZona = treeZonas.getElement(keyZona);
				if (valZona == 0)
					continue;
				lastVal= valZona;
				zonas += (zonas.equals("") ? "" : ", ") + lastVal;
				zonaLabels = "'" + keyZona.substring(3) + "'" + (zonaLabels.equals("") ? "" : ", " + zonaLabels);
			}
			zonas += (zonas.equals("") ? "" : ", ") + (((((long) lastVal) / 100) + 1) * 100);
			zonaLabels += ",'Sin premio'";
			
			obj.pZonas.setValue("[" + zonas + "]");
			obj.pLabelZonas.setValue("[" + zonaLabels + "]");

//			obj.pObjetivo.setValue(getObjetivo(detalle, obj.pRuta.getValue(),factor));
//			obj.pIngreso.setValue(getGanancia(detalle, obj.pRuta.getValue(),factor));

//			obj.pPremio.setValue(premio);
//			obj.pPremioPorc.setValue(getPorcPremio(detalle, obj.pIngreso.getValue(), obj.pObjetivo.getValue()));
//			obj.pPremioEst.setValue(getPremio(detalle, obj.pIngreso.getValue(), obj.pObjetivo.getValue()));

//			obj.pMensaje.setValue(getAnalisis(detalle, obj, regs.CampoAsLong("cantidad"), obj.pFMS.getValue()));
//			obj.pGrafico.setValue(getHtmlData(detalle,obj));

			addItem(obj);
			acum += obj.pPremio.getValue();
		}

		setTotalPremios(acum);
		setTotalPremiosExtra(premioExtra);
		setVolado(volado);
		setVoladoAHoy(voladoAHoy);
		double porc = JTools.rd((acum * 100.0) / (acum + premioExtra), 2);
		conclusion = "Premios posibles al " + porc + "%, necesita vender " + tkt + " en las rutas específicas tickets para maximizar la ganancia";
		setConclusion(conclusion);
		
		this.Ordenar("-premio_estimado");
		return true;

	}

	public String getHtmlData(BizDetalle detalle,BizObjetivosRuta obj) throws Exception {
		String defin = "{\"title\":'" + obj.pRuta.getValue() + "',\"subtitle\":'',\"ranges\":" + obj.pZonas.getValue() + ",\"rangeLabels\":" +
				obj.pLabelZonas.getValue() + ",\"measures\":[" + obj.pObjetivo.getValue() +
				"],\"measureLabels\":['"+detalle.getObjEvent().getDescripcion()+"'],\"markers\":[" + obj.pObjetivo.getValue() +
				"],\"markerLabels\":['Objetivo']}";

		String s = "";
		s += "<div style=\"display:block;width:400px\">";
		s += "<div id=\"span_bullet_" + obj.hashCode() + "\"><svg style='height:40px'> </svg></div>";
		s += "<script>addBullet('span_bullet_" + obj.hashCode() + "'," + defin + ",400,40);</script>";
		s += "</div>";
		return s;
	}


	public double getMS(double cantidad, double total) throws Exception {
		return (cantidad * 100.0) / total;
	}

	public double calculoPorcentaje(double ms, double fms) throws Exception {
		return fms == 0 ? 0 : (ms * 100.0) / fms;
	}

	public String getPorcPremio(BizDetalle detalle, double ingreso, double objetivo) throws Exception {
		BizNivel nivel = detalle.getObjNivelAlcanzado(objetivo);
		if (nivel == null)
			return "0%";
		return nivel.getValorPerc(ingreso, objetivo);
	}

	public double getPremio(BizDetalle detalle, double ingreso, double objetivo) throws Exception {
		BizNivel nivel = detalle.getObjNivelAlcanzado(objetivo);
		if (nivel == null)
			return 0;
		return nivel.getValorPremio(ingreso, objetivo);
	}

//	public String getAnalisis(BizDetalle detalle, BizObjetivosRuta obj, long factor) throws Exception {
//		String error = "";
//		if (factor==0) return "";
//		
//		return "";
//	}

	public String generateSQL(BizDetalle detalle, String ruta, long factor) throws Exception {
		if (factor==0) return null;
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(detalle.getFHasta());
		String columns = "";
		String values = "";
		String valuesRecord = "";
		String sql = null;
		int cant=0;
		BizPNRTicket ticketModelo = null;
		JRecords<BizPNRTicket> ticketModelos = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		ticketModelos.dontThrowException(true);
		ticketModelos.addFilter("company", detalle.getCompany());
		ticketModelos.addFilter("creation_date", detalle.getFDesde(), ">=");
		ticketModelos.addFilter("creation_date", detalle.getFHasta(), "<=");
		ticketModelos.addFilter("market", "BOK-" + ruta, "like");
		JIterator<BizPNRTicket> it = ticketModelos.getStaticIterator();
		boolean firstTime=true;
		while (it.hasMoreElements()) {
			ticketModelo =it.nextElement();
				JIterator<String> itp = ticketModelo.getFixedProperties().getKeyIterator();
				valuesRecord="";
				while (itp.hasMoreElements()) {
					String ss = itp.nextElement();
					JProperty prop = ticketModelo.getFixedProperties().getElement(ss);
					String colName = prop.GetCampo();
					if (prop.isVirtual()) continue;
					if (prop.ifForeign()) continue;
					if (prop.isRecord()) continue;
					if (prop.isRecords()) continue;
					if (firstTime) 
						columns += (columns.equals("") ? "" : ", ") + colName;
					if (ticketModelo.getProp(colName).isDate()) {
						if (ticketModelo.getProp(colName).isNull())
							valuesRecord += (valuesRecord.equals("") ? "" : ", ") + "null::date";
						else
							valuesRecord += (valuesRecord.equals("") ? "" : ", ") + "'" +  ticketModelo.getPropAsString(colName) + "'::date";
					} else if (ticketModelo.getProp(colName).isString()||ticketModelo.getProp(colName).isBoolean()) {
						if (ticketModelo.getProp(colName).isNull())
							valuesRecord += (valuesRecord.equals("") ? "" : ", ") + "''";
						else
							valuesRecord += (valuesRecord.equals("") ? "" : ", ") + "'"+ticketModelo.getPropAsString(colName) + "'";
					} else {
						if (ticketModelo.getProp(colName).isNull())
							valuesRecord += (valuesRecord.equals("") ? "" : ", ") + "0";
						else
							valuesRecord += (valuesRecord.equals("") ? "" : ", ") + ticketModelo.getPropAsString(colName);
					}
				}
				firstTime=false;
				values+=(values.equals("")?"":", ")+"("+valuesRecord+")";
				cant++;
				if (cant>=factor) break;
		}
		
		if (columns.equals("")) 
			return null;
		
		sql = "with ";
		sql += "TUR_PNR_BOLETO as (";
		sql += " Select " + columns + " from TUR_PNR_BOLETO where creation_date<= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
		sql += " and company= '" + detalle.getCompany() + "' ";
		sql += "  union all ";
		sql += " (VALUES";
		String sql2 = "";
		sql2 += (sql2.equals("") ? "" : ", ")  + values ;
		for (int j = 0; j < ((factor-cant)/cant); j++)
			sql2 += (sql2.equals("") ? "" : ", ")  + values ;
		sql += sql2;
		sql += " )";
		sql += ")";// ,";
		return sql;
	}

	
	public double getGanancia(BizDetalle detalle, String ruta,long factor) throws Exception {
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(detalle.getFHasta());
		return detalle.getObjEventGanancia().getValor(null,fecha, generateSQL(detalle, ruta,factor));
	}

	public double getObjetivo(BizDetalle detalle, String ruta,long factor) throws Exception {
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(detalle.getFHasta());
		return detalle.getObjEvent().getValor(null,fecha, generateSQL(detalle, ruta,factor));
	}

}