package pss.bsp.consolidador;

import java.util.Date;
import java.util.List;

import pss.bsp.bo.BizInterfazBO;
import pss.bsp.bo.GuiInterfazBO;
import pss.bsp.bo.formato.BizFormato;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.bsp.consolidador.diferencia.BizDiferencia;
import pss.bsp.consolidador.iva.BizImpositivo;
import pss.bsp.consolidador.over.BizOver;
import pss.bsp.pdf.GuiPDF;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWin;
import pss.tourism.pnr.BizPNRTicket;

public class JConsolidador implements IConsolidador {

	GuiPDF pdf;

	JRecords<BizInterfazBO> boInterfaces;

	long linea = 0;

	long lineaOver = 0;

	long lineaIva = 0;

	long lineaDife = 0;

	BizFormato formatoActual;

	boolean ignoreFormato = false;
	
	JList<String> boletos;

	public JWin getJWin() {
		return null;
	}

	private boolean checkExits() throws Exception {
		BizConsolidacion cons = new BizConsolidacion();
		cons.addFilter("company", pdf.GetcDato().getCompany());
	//	cons.addFilter("owner", pdf.GetcDato().getOwner());
		cons.addFilter("idpdf", pdf.GetcDato().getIdpdf());
		return cons.selectCount() > 0;
	}

	public void clearAll() throws Exception {
		BizConsolidacion cons = new BizConsolidacion();
		cons.addFilter("company", pdf.GetcDato().getCompany());
	//	cons.addFilter("owner", pdf.GetcDato().getOwner());
		cons.addFilter("idpdf", pdf.GetcDato().getIdpdf());
		cons.deleteMultiple();
		BizOver over = new BizOver();
		over.addFilter("company", pdf.GetcDato().getCompany());
	//	over.addFilter("owner", pdf.GetcDato().getOwner());
		over.addFilter("idpdf", pdf.GetcDato().getIdpdf());
		over.deleteMultiple();
		BizDiferencia dif = new BizDiferencia();
		dif.addFilter("company", pdf.GetcDato().getCompany());
	//	dif.addFilter("owner", pdf.GetcDato().getOwner());
		dif.addFilter("idpdf", pdf.GetcDato().getIdpdf());
		dif.deleteMultiple();
		BizImpositivo iva = new BizImpositivo();
		iva.addFilter("company", pdf.GetcDato().getCompany());
	//	iva.addFilter("owner", pdf.GetcDato().getOwner());
		iva.addFilter("idpdf", pdf.GetcDato().getIdpdf());
		iva.deleteMultiple();
	}

	private double getPrecision() throws Exception {
		return BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getPrecision();
	}
	private double getFactorBO() throws Exception {
		return BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getFactorBo();
	}


	private String getFieldKey() throws Exception {
		return formatoActual.getConciliableKey();
	//		return BizBSPUser.getUsrBSP().getBspConsola().getConciliableKey();
	}

	public void consolidar(boolean force) throws Exception {
		try {
			if (checkExits()) {
				if (!force) return;
			}
			boletos = JCollectionFactory.createList();
			JBDatos.GetBases().beginTransaction();
			clearAll();
			Date fechaDesde = pdf.GetcDato().getPeriodoDesde();
			Date fechaHasta = pdf.GetcDato().getPeriodoHasta();
			generateBOInterfaces(pdf.GetcDato().getIata(), fechaDesde, fechaHasta);
			JIterator<IConciliable> it = pdf.getDetail().getRecords().getStaticIterator();
			while (it.hasMoreElements()) {
				IConciliable regBSP = (IConciliable) it.nextElement();
				IConciliable regPNR = obtenerPNR(regBSP);
				IConciliable regBO = obtenerBO(regBSP);
		//		if (regPNR!=null) {
					comparePNR(regBSP, regPNR);
		//		}
				compareBoleto(regBSP, regBO);
				if (regBSP == null || regBO == null) continue;
				compareIva(regBSP, regBO);
				compareOver(regBSP, regBO);
				compareDiferencia(regBSP, regBO);
			}
			buscarBoletosFaltantesEnBSP(fechaDesde, fechaHasta);
			buscarBoletosFaltantesEnPNR(fechaDesde, fechaHasta, pdf.GetcDato().getIata());
			pdf.GetcDato().setEstado("CONCILIADO");
			pdf.GetcDato().processUpdate();
			JBDatos.GetBases().commit();
		} catch (RuntimeException e) {
			JBDatos.GetBases().rollback();
			throw e;
		}
	}

	public void buscarBoletosFaltantesEnBSP(Date fechaDesde, Date fechaHasta) throws Exception {
		JIterator<BizInterfazBO> it = boInterfaces.getStaticIterator();
		while (it.hasMoreElements()) {
			BizInterfazBO IB = it.nextElement();
			GuiInterfazBO gib = new GuiInterfazBO();
			JRecords details = IB.getDetail().getRecords();
			details.addFilter("company", IB.getCompany());
		//	details.addFilter("owner", IB.getOwner());
			details.addFilter("idInterfaz", IB.getIdinterfaz());
			String campoFecha = formatoActual.getColumn(IConciliable.FECHA);
			if (campoFecha != null) {
				details.addFilter(campoFecha, fechaDesde, ">=");
				details.addFilter(campoFecha, fechaHasta, "<=");
			}
			details.readAll();
			BizFormato formato = IB.getObjFormato();
			while (details.nextRecord()) {
				IConciliable conc = (IConciliable) details.getRecord();
				String boleto = conc.getStringValue(formato.getConciliableKey(), true);
				if (boletos.containsElement(boleto)) continue;
				crearEntradaPorFaltaEnBSP(conc,IConsolidador.BO);
			}

		}

	}
	public void buscarBoletosFaltantesEnPNR(Date fechaDesde, Date fechaHasta,String iata) throws Exception {
			ignoreFormato=true;
			JRecords<BizPNRTicket> details = new JRecords<BizPNRTicket>(BizPNRTicket.class);
			details.addFilter("company", pdf.GetcDato().getCompany());
			details.addFilter("nro_iata", iata);
			details.addFilter("creation_date", fechaDesde, ">=");
			details.addFilter("creation_date", fechaHasta, "<=");
			details.readAll();
			while (details.nextRecord()) {
				IConciliable conc = (IConciliable) details.getRecord();
				String boleto = conc.getStringValue(IConciliable.BOLETOS, true);
				if (boletos.containsElement(boleto)) continue;
				crearEntradaPorFaltaEnBSP(conc,IConsolidador.PNR);
			}


	}

	private void generateBOInterfaces(String iata, Date fechaDesde, Date fechaHasta) throws Exception {
		boInterfaces =new JRecords<BizInterfazBO>(BizInterfazBO.class);
		boInterfaces.addFilter("company", pdf.GetcDato().getCompany());
		//boInterfaces.addFilter("owner", pdf.GetcDato().getOwner());
		boInterfaces.addFilter("fecha_desde", fechaHasta, "<=");
		boInterfaces.addFilter("fecha_hasta", fechaDesde, ">=");
//		if (!iata.startsWith("-"))boInterfaces.addFilter("iata", iata.replaceAll(" ", ""));
		boInterfaces.toStatic();
	}
	private IConciliable obtenerBO(IConciliable bsp) throws Exception {
		JIterator<BizInterfazBO> it = boInterfaces.getStaticIterator();
		while (it.hasMoreElements()) {
			BizInterfazBO IB =  it.nextElement();
			BizFormato formato = IB.getObjFormato();
			formatoActual = formato;
			String boleto = bsp.getStringValue(formato.getConciliableKey(), true);
			String column = formato.getColumn(formato.getConciliableKey());
			if (column == null) continue;

			JRecords detail = IB.getDetail().getRecords();
			detail.addFilter("company", IB.getCompany());
	//		detail.addFilter("owner", IB.getOwner());
			detail.addFilter("idInterfaz", IB.getIdinterfaz());
//			if (boleto.indexOf("3781447112")!=-1)
//				PssLogger.logInfo("check point");
				
			detail.addFilter(column, boleto);
			JIterator<IConciliable> itc=detail.getStaticIterator();
			
			if (!itc.hasMoreElements()) continue;
			boletos.addElement(boleto);
			IConciliable res = (IConciliable) itc.nextElement();
			res.setFormato(formato);
			detail.closeRecord();
			return res;
		}
		return null;
	}
	private IConciliable obtenerPNR(IConciliable bsp) throws Exception {
		String boleto = bsp.getStringValue(IConciliable.BOLETOS, true);
		
			JRecords<BizPNRTicket> detail = new JRecords<BizPNRTicket>(  BizPNRTicket.class);
			detail.addFilter("company", pdf.GetcDato().getCompany());
			//detail.addFilter("owner", IB.getOwner());
			detail.addFilter("NumeroBoleto", boleto);
			JIterator<BizPNRTicket> itc=detail.getStaticIterator();
			if (!itc.hasMoreElements()) return null;
			boletos.addElement(boleto);
			IConciliable res = (IConciliable) itc.nextElement();
			detail.closeRecord();
			return res;
		
	}

	private void crearEntradaPorFaltaEnBO(IConciliable regBSP,String tipo) throws Exception {
		BizConsolidacion cons = new BizConsolidacion();
		cons.setCompany(pdf.GetcDato().getCompany());
		cons.setOwner(pdf.GetcDato().getOwner());
		cons.setTipo(tipo);
		cons.setIdpdf(pdf.GetcDato().getIdpdf());
		cons.setLinea(linea++);
		cons.setStatus(IConsolidador.ONLY_BSP);
		cons.setNroBoleto(regBSP.getStringValue(IConciliable.BOLETOS, false));

		Date fecha = regBSP.getDateValue(IConciliable.FECHA, false);
		if (fecha==null) {
			fecha = pdf.GetcDato().getPeriodoDesde();
		}
		cons.setFecha(fecha);
		for (int d = 1; d <= 40; d++) {
			List<String> campos = cons.getFieldName(d);
			for(String campo:campos) {
				String valueBSP = regBSP.getStringValue(campo, false);
				if (valueBSP == null) continue;
				cons.setD(campo, valueBSP);
				break;
			}
		}
		cons.setObservaciones("");
		cons.processInsert();
	}

	private void crearEntradaPorFaltaEnBSP(IConciliable regBO,String tipo) throws Exception {
		BizConsolidacion cons = new BizConsolidacion();
		cons.setCompany(pdf.GetcDato().getCompany());
		cons.setOwner(pdf.GetcDato().getOwner());
		cons.setIdpdf(pdf.GetcDato().getIdpdf());
		cons.setTipo(tipo);
		cons.setLinea(linea++);
		cons.setStatus(IConsolidador.ONLY_BO);
		cons.setNroBoleto(regBO.getStringValue(IConciliable.BOLETOS, false));
	

		Date fecha = regBO.getDateValue(IConciliable.FECHA, false);
		if (fecha==null)
				fecha = pdf.GetcDato().getPeriodoDesde();
		String moneda = null;

		cons.setFecha(fecha);		
		for (int d = 1; d <= 40; d++) {
			List<String> campos = cons.getFieldName(d);
			for(String campo:campos) {
				if (formatoActual!=null && !ignoreFormato) {
					String boColumn = formatoActual.getColumn(campo);
					if (boColumn == null) continue;
				} else {
					boolean find=false;
					for (String c : campos) {
						campo = c;
						if (regBO.getLongValue(campo, false) != null ||
								regBO.getDoubleValue(campo, moneda, false)!=null || 
								 regBO.getDateValue(campo, false)!=null ||
								 regBO.getStringValue(campo, false)!=null
								) {
							find = true;
							break; // encontre un campo
							}
						}
						if (!find) {
							continue;
						}
					
				}
				{
					String valueBO = regBO.getStringValue(campo, false);
					if (valueBO != null) {
						cons.setD(campo, valueBO);
						continue;
					}
				}
				{
					Long valueBO = regBO.getLongValue(campo, false);
					if (valueBO != null) {
						cons.setD(campo, valueBO.toString());
						continue;
					}
				}
				{
					Double valueBO = regBO.getDoubleValue(campo, moneda, false);
					if (valueBO != null) {
						cons.setD(campo, valueBO.toString());
						continue;
					}
				}
				{
					Date valueBO = regBO.getDateValue(campo, false);
					if (valueBO != null) {
						cons.setD(campo, JDateTools.DateToString(valueBO));
						continue;
					}
				}
				break;
			}
		}
		cons.setObservaciones("");
		cons.processInsert();
	}

	private void crearEntradaComparativa(IConciliable regBSP, IConciliable regBO,String tipo) throws Exception {
		if (regBSP.getStringValue(IConciliable.OPERACION,false).startsWith("ADM")) return;
		if (regBSP.getStringValue(IConciliable.OPERACION,false).startsWith("ACM")) return;
		if (regBSP.getStringValue(IConciliable.OPERACION,false).startsWith("SPDR")) return;
		if (regBSP.getStringValue(IConciliable.OPERACION,false).startsWith("CANN")) return;
		BizConsolidacion cons = new BizConsolidacion();
		boolean equals = true;
		cons.setCompany(pdf.GetcDato().getCompany());
		cons.setOwner(pdf.GetcDato().getOwner());
		cons.setIdpdf(pdf.GetcDato().getIdpdf());
		cons.setTipo(tipo);
		cons.setLinea(linea++);
		boolean esReembolso =regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("RFND")!=-1 ||  regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("REEMBOLSO")!=-1 || regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("ACM CONCEPTO")!=-1;
		boolean esAnulacion = regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("REFUND")!=-1 || regBSP.getStringValue(IConciliable.OPERACION, true).startsWith("CAN");

		cons.setNroBoleto(regBSP.getStringValue(IConciliable.BOLETOS, true));
		
		Date fecha = regBSP.getDateValue(IConciliable.FECHA, true);
		if (fecha==null) {
			fecha = regBO.getDateValue(IConciliable.FECHA, true);
			if (fecha==null)
				fecha = pdf.GetcDato().getPeriodoDesde();
		}
		cons.setFecha(fecha);
		String moneda = regBSP.getStringValue(IConciliable.MONEDA, true);
		for (int d = 1; d <= 40; d++) {
			List<String> campos = cons.getFieldName(d);
			if (campos.isEmpty()) continue;
			String campo = null;
			String firstcampo = null;
			if (formatoActual!=null && !ignoreFormato) {
				for (String c : campos) {
					campo = c;
					if (firstcampo==null) firstcampo = c;
					if (formatoActual.getColumn(campo) != null) break; // encontre un campo
				}
				if (formatoActual.getColumn(campo) == null) {
					String valueBSP = regBSP.getStringValue(firstcampo, true);
					if (valueBSP != null) cons.setD(firstcampo, "[" + valueBSP + "]");
					continue;
				}
			} else {
				boolean find=false;
				for (String c : campos) {
					campo = c;
					if (regBO.getLongValue(campo, true) != null ||
							regBO.getDoubleValue(campo, moneda, true)!=null || 
							 regBO.getDateValue(campo, true)!=null ||
							 regBO.getStringValue(campo, true)!=null
							) {
						find = true;
						break; // encontre un campo
						}
					}
					if (!find) {
						String valueBSP = regBSP.getStringValue(campo, true);
						if (valueBSP != null) cons.setD(campo, "[" + valueBSP + "]");
						continue;
					}
				
			}
			
			Long valueLongBSP = regBSP.getLongValue(campo, true);
			if (valueLongBSP != null) {
				Long valueBO = regBO.getLongValue(campo, true);
				if (valueBO != null) {
					if (Math.abs(valueLongBSP - valueBO) == 0) cons.setD(campo, String.valueOf(valueLongBSP.longValue()));
					else {
						cons.setD(campo, "BSP:" + valueLongBSP.longValue() + " <> "+tipo+":" + valueBO.longValue());
						equals = false;
					}
					continue;
				}
			}
			Double valueBSP = regBSP.getDoubleValue(campo, moneda, true);
			if (valueBSP != null) {
				Double valueBO = regBO.getDoubleValue(campo, moneda, true);
				if (valueBO != null) {
					if (Math.abs(valueBSP - valueBO) < 0.01 + getPrecision()) 
						cons.setD(campo, String.valueOf(valueBSP.doubleValue()));
					else {
						if (esReembolso || esAnulacion) {
							if (Math.abs(Math.abs(valueBSP) - Math.abs(valueBSP)) < 0.01 + getPrecision()) cons.setD(campo, String.valueOf(valueBSP.doubleValue()));
						} else {
							cons.setD(campo, "BSP:" + valueBSP.doubleValue() + " <> "+tipo+":" + valueBO.doubleValue());
							equals = false;
						}
					} 
						
				
					continue;
				}
			}
			Date valueDateBSP = regBSP.getDateValue(campo, true);
			if (valueDateBSP != null) {
				Date valueBO = regBO.getDateValue(campo, true);
				if (valueBO != null) {
					if (valueDateBSP.compareTo(valueBO) == 0) cons.setD(campo, JDateTools.DateToString(valueDateBSP));
					else {
						cons.setD(campo, "BSP:" + JDateTools.DateToString(valueDateBSP) + " <> "+tipo+":" + JDateTools.DateToString(valueBO));
						equals = false;
					}
					continue;
				}
			}
			String valueStringBSP = regBSP.getStringValue(campo, true);
			if (valueStringBSP != null) {
				String valueBO = regBO.getStringValue(campo, true);
				if (valueBO != null) {
					if ((campo.equals(IConciliable.OPERACION)  && !valueBO.startsWith("RFND") && valueStringBSP.startsWith(valueBO))||
							(!campo.equals(IConciliable.OPERACION) && valueStringBSP.equalsIgnoreCase(valueBO))) cons.setD(campo, valueStringBSP);
					else {
						cons.setD(campo, "BSP:" + valueStringBSP + " <> "+tipo+":" + valueBO);
						equals = false;
					}
					continue;
				}

			}
		}
		if (regBO!=null) cons.setSituation(regBO.getSituation(regBSP, getPrecision()));
		if (regBO!=null) cons.setContrato(regBO.getContrato());
		cons.setObservaciones("");
		cons.setStatus(equals ? IConsolidador.OK : IConsolidador.DISTINCT);
		cons.processInsert();
	}

	public void compareBoleto(IConciliable regBSP, IConciliable regBO) throws Exception {
		ignoreFormato=false;
		if (regBO == null) {// el Boleto esta en falta
			crearEntradaPorFaltaEnBO(regBSP,IConsolidador.BO);
			return;
		}
		crearEntradaComparativa(regBSP, regBO,IConsolidador.BO);
	}
	public void comparePNR(IConciliable regBSP, IConciliable regPNR) throws Exception {
		ignoreFormato=true;
		if (regBSP.getStringValue(IConciliable.OPERACION,false).startsWith("ADM")) return;
		if (regBSP.getStringValue(IConciliable.OPERACION,false).startsWith("ACM")) return;
		if (regBSP.getStringValue(IConciliable.OPERACION,false).startsWith("SPDR")) return;
		if (regBSP.getStringValue(IConciliable.OPERACION,false).startsWith("CANN")) return;
		if (regPNR == null) {// el Boleto esta en falta
			crearEntradaPorFaltaEnBO(regBSP,IConsolidador.PNR);
			return;
		}
		crearEntradaComparativa(regBSP, regPNR,IConsolidador.PNR);
	}

	private double calculeDif(IConciliable regBSP, IConciliable regBO,String field,boolean reembolso) throws Exception {
		String moneda = regBSP.getStringValue(IConciliable.MONEDA, true);
		 Double valbsp=regBSP.getDoubleValue(field, moneda, true); 
		 Double valbo=regBO.getDoubleValue(field, moneda, true);
		 if (valbsp==null||valbo==null) return 0;
		 if (Math.abs(valbsp+valbo)<0.1 && reembolso) 
			 return 0;
		 else
			 return ( Math.abs(valbsp-valbo)<0.001)?0:valbsp-valbo;
	}
	public void compareIva(IConciliable regBSP, IConciliable regBO) throws Exception {
		BizImpositivo cons = new BizImpositivo();
		cons.setCompany(pdf.GetcDato().getCompany());
		cons.setOwner(pdf.GetcDato().getOwner());
		cons.setIdpdf(pdf.GetcDato().getIdpdf());
		cons.setLinea(lineaIva++);
		double diferenciaTarifa = 0;
		double diferenciaIva = 0;
		double diferenciaCom = 0;
		double diferenciaIvaCom = 0;
		boolean esReembolso = regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("RFND")!=-1 ||regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("REEMBOLSO")!=-1 || regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("ACM CONCEPTO")!=-1;
		String moneda = regBSP.getStringValue(IConciliable.MONEDA, true);
		
		if (!regBO.getStringValue(IConciliable.IMP_SOBRE_COMISION, true).equals("")) diferenciaIvaCom = calculeDif(regBSP,regBO,IConciliable.IMP_SOBRE_COMISION,esReembolso);
		else if (!regBO.getStringValue(IConciliable.IMP_SOBRE_COMISION_INV, true).equals("")) diferenciaIvaCom = calculeDif(regBSP,regBO,IConciliable.IMP_SOBRE_COMISION_INV,esReembolso);
		if (!regBO.getStringValue(IConciliable.COMISION, true).equals("")) diferenciaCom = calculeDif(regBSP,regBO,IConciliable.COMISION,esReembolso);
		else if (!regBO.getStringValue(IConciliable.COMISION_INV, true).equals("")) diferenciaCom = calculeDif(regBSP,regBO,IConciliable.COMISION_INV,esReembolso);
		if (!regBO.getStringValue(IConciliable.TARIFA, true).equals("")) diferenciaTarifa = calculeDif(regBSP,regBO,IConciliable.TARIFA,esReembolso);
		else if (!regBO.getStringValue(IConciliable.TARIFA_SIN_COMISION, true).equals("")) diferenciaTarifa = calculeDif(regBSP,regBO,IConciliable.TARIFA_SIN_COMISION,esReembolso);
		if (!regBO.getStringValue(IConciliable.IMPUESTO_1, true).equals("")) diferenciaIva =calculeDif(regBSP,regBO,IConciliable.IMPUESTO_1,esReembolso);
 
		
		boolean hayDiferencia = !(diferenciaTarifa == 0 && diferenciaIva == 0 && diferenciaCom == 0 && diferenciaIvaCom == 0);
		cons.setStatus(hayDiferencia ? IConsolidador.DISTINCT : IConsolidador.OK);
		cons.setBoleto(regBSP.getStringValue(getFieldKey(), true));
		cons.setTipoRuta(regBSP.getStringValue(IConciliable.TIPO_RUTA, true));
		cons.setIdaerolinea(regBSP.getStringValue(IConciliable.AEROLINEA, true));

		cons.setTarifaBsp(regBSP.getDoubleValue(IConciliable.TARIFA, moneda, true));
		if (!regBO.getStringValue(IConciliable.TARIFA, true).equals("")) cons.setTarifaBo(regBO.getDoubleValue(IConciliable.TARIFA, moneda, true));
		else if (!regBO.getStringValue(IConciliable.TARIFA_SIN_COMISION, true).equals("")) cons.setTarifaBo(regBO.getDoubleValue(IConciliable.TARIFA_SIN_COMISION, moneda, true));
		cons.setTarifaDif(diferenciaTarifa);

		cons.setIvaBsp(regBSP.getDoubleValue(IConciliable.IMPUESTO_1, moneda, true));
		if (!regBO.getStringValue(IConciliable.IMPUESTO_1, true).equals("")) cons.setIvaBo(regBO.getDoubleValue(IConciliable.IMPUESTO_1, moneda, true));
		cons.setIvaDif(diferenciaIva);

		if (!regBO.getStringValue(IConciliable.COMISION, true).equals("")) {
			cons.setComisionBsp(regBSP.getDoubleValue(IConciliable.COMISION, moneda, true));
			cons.setComisionBo(regBO.getDoubleValue(IConciliable.COMISION, moneda, true));
		}
		else if (!regBO.getStringValue(IConciliable.COMISION_INV, true).equals("")) {
			cons.setComisionBsp(regBSP.getDoubleValue(IConciliable.COMISION_INV, moneda, true));
			cons.setComisionBo(regBO.getDoubleValue(IConciliable.COMISION_INV, moneda, true));
		}
		else {
			cons.setComisionBsp(regBSP.getDoubleValue(IConciliable.COMISION, moneda, true));
		}
		cons.setComisionDif(diferenciaCom);

		if (!regBO.getStringValue(IConciliable.IMP_SOBRE_COMISION, true).equals("")) {
			cons.setIvacomisionBsp(regBSP.getDoubleValue(IConciliable.IMP_SOBRE_COMISION, moneda, true));
			cons.setIvacomisionBo(regBO.getDoubleValue(IConciliable.IMP_SOBRE_COMISION, moneda, true));
		}
		else if (!regBO.getStringValue(IConciliable.IMP_SOBRE_COMISION_INV, true).equals("")) {
			cons.setIvacomisionBsp(regBSP.getDoubleValue(IConciliable.IMP_SOBRE_COMISION_INV, moneda, true));
			cons.setIvacomisionBo(regBO.getDoubleValue(IConciliable.IMP_SOBRE_COMISION_INV, moneda, true));
		} else {
			cons.setIvacomisionBsp(regBSP.getDoubleValue(IConciliable.IMP_SOBRE_COMISION, moneda, true));
		}
		cons.setIvacomisionDif(diferenciaIvaCom);

		cons.setObservacion("");
		cons.processInsert();
	}

	public void compareOver(IConciliable regBSP, IConciliable regBO) throws Exception {
		BizOver cons = new BizOver();
		cons.setCompany(pdf.GetcDato().getCompany());
		cons.setOwner(pdf.GetcDato().getOwner());
		cons.setIdpdf(pdf.GetcDato().getIdpdf());
		cons.setLinea(lineaOver++);
		double overBO = 0;
		double overBSP = 0;
		String moneda = regBSP.getStringValue(IConciliable.MONEDA, true);

		boolean esReembolso = regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("RFND")!=-1 ||regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("REEMBOLSO")!=-1 || regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("ACM CONCEPTO")!=-1;
		if (!regBO.getStringValue(IConciliable.COMISION_OVER, true).equals("")) {
			if (esReembolso && Math.abs(regBO.getDoubleValue(IConciliable.COMISION_OVER, moneda, true)+regBSP.getDoubleValue(IConciliable.COMISION_OVER, moneda, true))<0.1) 
				overBO =-regBO.getDoubleValue(IConciliable.COMISION_OVER, moneda, true);
			else
				overBO =regBO.getDoubleValue(IConciliable.COMISION_OVER, moneda, true);
			overBSP = regBSP.getDoubleValue(IConciliable.COMISION_OVER, moneda, true);
		}
		else if (!regBO.getStringValue(IConciliable.COMISION_OVER_INV, true).equals("")) {
			if (esReembolso && Math.abs(regBO.getDoubleValue(IConciliable.COMISION_OVER_INV, moneda, true)+regBSP.getDoubleValue(IConciliable.COMISION_OVER_INV, moneda, true))<0.1) 
				overBO =-regBO.getDoubleValue(IConciliable.COMISION_OVER_INV, moneda, true);
			else
				overBO =regBO.getDoubleValue(IConciliable.COMISION_OVER_INV, moneda, true);
			overBSP = regBSP.getDoubleValue(IConciliable.COMISION_OVER_INV, moneda, true);
		}
		else if (!regBO.getStringValue(IConciliable.COMISION_ACUM, true).equals("")) {
			if (esReembolso && Math.abs(regBO.getDoubleValue(IConciliable.COMISION_ACUM, null, true)+regBSP.getDoubleValue(IConciliable.COMISION_ACUM, moneda, true))<0.1) 
				overBO =(-regBO.getDoubleValue(IConciliable.COMISION_ACUM, moneda, true))-regBSP.getDoubleValue(IConciliable.COMISION, moneda, true);
			else
				overBO =regBO.getDoubleValue(IConciliable.COMISION_ACUM, moneda, true)-regBSP.getDoubleValue(IConciliable.COMISION, moneda, true);
			overBSP = regBSP.getDoubleValue(IConciliable.COMISION_ACUM, moneda, true)-regBSP.getDoubleValue(IConciliable.COMISION, moneda, true);
		}
		else if (!regBO.getStringValue(IConciliable.COMISION_ACUM_INV, true).equals("")) {
			if (esReembolso && Math.abs(regBO.getDoubleValue(IConciliable.COMISION_ACUM_INV, moneda, true)+regBSP.getDoubleValue(IConciliable.COMISION_ACUM_INV, moneda, true))<0.1) 
				overBO =(-regBO.getDoubleValue(IConciliable.COMISION_ACUM_INV, moneda, true))-regBSP.getDoubleValue(IConciliable.COMISION_INV, moneda, true);
			else
			  overBO =regBO.getDoubleValue(IConciliable.COMISION_ACUM_INV, moneda, true)-regBSP.getDoubleValue(IConciliable.COMISION_INV, moneda, true);
			overBSP = regBSP.getDoubleValue(IConciliable.COMISION_ACUM_INV, moneda, true)-regBSP.getDoubleValue(IConciliable.COMISION_INV, moneda, true);
		}
		else return;
		
		double diferenciaOver =  overBSP - overBO;
		boolean hayDiferencia = !(diferenciaOver == 0);
		cons.setStatus(hayDiferencia ? IConsolidador.DISTINCT : IConsolidador.OK);
		cons.setBoleto(regBSP.getStringValue(getFieldKey(), true));
		cons.setIdaerolinea(regBSP.getStringValue(IConciliable.AEROLINEA, true));
		cons.setFecha(regBSP.getDateValue(IConciliable.FECHA, true));
		cons.setOverRec(overBSP);
		cons.setOverPed(overBO);
		cons.setDif(diferenciaOver);
		cons.setObservaciones("");
		cons.processInsert();
	}

	public void compareDiferencia(IConciliable regBSP, IConciliable regBO) throws Exception {
		BizDiferencia cons = new BizDiferencia();
		cons.setCompany(pdf.GetcDato().getCompany());
		cons.setOwner(pdf.GetcDato().getOwner());
		cons.setIdpdf(pdf.GetcDato().getIdpdf());
		cons.setLinea(lineaDife++);
		double diferenciaTarifa = 0;
		double diferenciaContado = 0;
		double diferenciaCom = 0;
		double diferenciaCredito = 0;
		double diferenciaImpuesto = 0;
		boolean esReembolso = regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("RFND")!=-1 ||regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("REEMBOLSO")!=-1 || regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("ACM CONCEPTO")!=-1;

		if (!regBO.getStringValue(IConciliable.TARIFA, true).equals("")) diferenciaTarifa = calculeDif(regBSP,regBO,IConciliable.TARIFA,esReembolso);
		else if (!regBO.getStringValue(IConciliable.TARIFA_SIN_COMISION, true).equals("")) diferenciaTarifa = calculeDif(regBSP,regBO,IConciliable.TARIFA_SIN_COMISION,esReembolso);
		if (!regBO.getStringValue(IConciliable.CONTADO, true).equals("")) diferenciaContado = calculeDif(regBSP,regBO,IConciliable.CONTADO,esReembolso);
		else if (!regBO.getStringValue(IConciliable.CONTADO_BRUTO, true).equals("")) diferenciaContado = calculeDif(regBSP,regBO,IConciliable.CONTADO_BRUTO,esReembolso);
		if (!regBO.getStringValue(IConciliable.COMISION, true).equals("")) diferenciaCom = calculeDif(regBSP,regBO,IConciliable.COMISION,esReembolso);
		else if (!regBO.getStringValue(IConciliable.COMISION_INV, true).equals("")) diferenciaCom = calculeDif(regBSP,regBO,IConciliable.COMISION_INV,esReembolso);
		else if (!regBO.getStringValue(IConciliable.COMISION_ACUM, true).equals("")) diferenciaCom = calculeDif(regBSP,regBO,IConciliable.COMISION_ACUM,esReembolso);
		else if (!regBO.getStringValue(IConciliable.COMISION_ACUM_INV, true).equals("")) diferenciaCom = calculeDif(regBSP,regBO,IConciliable.COMISION_ACUM_INV,esReembolso);
		if (!regBO.getStringValue(IConciliable.TARJETA, true).equals("")) diferenciaCredito = calculeDif(regBSP,regBO,IConciliable.TARJETA,esReembolso);
		else if (!regBO.getStringValue(IConciliable.TARJETA_BRUTO, true).equals("")) diferenciaCredito = calculeDif(regBSP,regBO,IConciliable.TARJETA_BRUTO,esReembolso);
		if (!regBO.getStringValue(IConciliable.IMPUESTO_1, true).equals("")) diferenciaImpuesto = calculeDif(regBSP,regBO,IConciliable.IMPUESTO_1,esReembolso);
		boolean hayDiferencia = !(diferenciaTarifa == 0 && diferenciaContado == 0 && diferenciaCom == 0 && diferenciaCredito == 0 && diferenciaImpuesto == 0);
		String moneda = regBSP.getStringValue(IConciliable.MONEDA, true);

		cons.setStatus(hayDiferencia ? IConsolidador.DISTINCT : IConsolidador.OK);
		cons.setBoleto(regBSP.getStringValue(getFieldKey(), true));
		cons.setTipoRuta(regBSP.getStringValue(IConciliable.TIPO_RUTA, true));
		cons.setIdaerolinea(regBSP.getStringValue(IConciliable.AEROLINEA, true));
		cons.setOperacion(regBSP.getStringValue(IConciliable.OPERACION, true));

		cons.setTarifaBsp(regBSP.getDoubleValue(IConciliable.TARIFA, moneda, true));

		if (!regBO.getStringValue(IConciliable.TARIFA, true).equals("")) cons.setTarifaBo(regBO.getDoubleValue(IConciliable.TARIFA, moneda, true));
		else if (!regBO.getStringValue(IConciliable.TARIFA_SIN_COMISION, true).equals("")) cons.setTarifaBo(regBO.getDoubleValue(IConciliable.TARIFA_SIN_COMISION, moneda, true));
		cons.setTarifaDif(diferenciaTarifa);

		if (!regBO.getStringValue(IConciliable.CONTADO, true).equals("")) {
			Double val=regBSP.getDoubleValue(IConciliable.CONTADO, moneda, true);
			cons.setContadoBsp(val==null?0:val);
			cons.setContadoBo(regBO.getDoubleValue(IConciliable.CONTADO, moneda, true));
		}
		else if (!regBO.getStringValue(IConciliable.CONTADO_BRUTO, true).equals("")) {
			Double val=regBSP.getDoubleValue(IConciliable.CONTADO_BRUTO, moneda, true);
			cons.setContadoBsp(val==null?0:val);
			cons.setContadoBo(regBO.getDoubleValue(IConciliable.CONTADO_BRUTO, moneda, true));
		}else{
			Double val=regBSP.getDoubleValue(IConciliable.CONTADO, moneda, true);
			cons.setContadoBsp(val==null?0:val);
		}
		cons.setContadoDif(diferenciaContado);

		if (!regBO.getStringValue(IConciliable.TARJETA, true).equals("")) {
			Double val=regBSP.getDoubleValue(IConciliable.TARJETA, moneda, true);
			cons.setCreditoBsp(val==null?0:val);
			cons.setCreditoBo(regBO.getDoubleValue(IConciliable.TARJETA, moneda, true));
		}
		else if (!regBO.getStringValue(IConciliable.TARJETA_BRUTO, true).equals("")) {
			Double val=regBSP.getDoubleValue(IConciliable.TARJETA_BRUTO, moneda, true);
			cons.setCreditoBsp(val==null?0:val);
			cons.setCreditoBo(regBO.getDoubleValue(IConciliable.TARJETA_BRUTO, moneda, true));
		}
		else {
			Double tarj = regBSP.getDoubleValue(IConciliable.TARJETA, moneda, true);
			cons.setCreditoBsp(tarj==null?0:tarj);
		}
		cons.setCreditoDif(diferenciaCredito);

		if (!regBO.getStringValue(IConciliable.COMISION, true).equals("")) {
			cons.setComisionBsp(regBSP.getDoubleValue(IConciliable.COMISION, moneda, true));
			cons.setComisionBo(regBO.getDoubleValue(IConciliable.COMISION, moneda, true));
		}
		else if (!regBO.getStringValue(IConciliable.COMISION_INV, true).equals("")) {
			cons.setComisionBsp(regBSP.getDoubleValue(IConciliable.COMISION_INV, moneda, true));
			cons.setComisionBo(regBO.getDoubleValue(IConciliable.COMISION_INV, moneda, true));
		}
		else if (!regBO.getStringValue(IConciliable.COMISION_ACUM, true).equals("")) {
			cons.setComisionBsp(regBSP.getDoubleValue(IConciliable.COMISION_ACUM, moneda, true));
			cons.setComisionBo(regBO.getDoubleValue(IConciliable.COMISION_ACUM, moneda, true));
		}
		else if (!regBO.getStringValue(IConciliable.COMISION_ACUM_INV, true).equals("")) {
			cons.setComisionBsp(regBSP.getDoubleValue(IConciliable.COMISION_ACUM_INV, moneda, true));
			cons.setComisionBo(regBO.getDoubleValue(IConciliable.COMISION_ACUM_INV, moneda, true));
		}
		else {
			cons.setComisionBsp(regBSP.getDoubleValue(IConciliable.COMISION, moneda, true));
		}
		cons.setComisionDif(diferenciaCom);

		cons.setImpuestoBsp(regBSP.getDoubleValue(IConciliable.IMPUESTO_1, moneda, true));
		if (!regBO.getStringValue(IConciliable.IMPUESTO_1, true).equals("")) cons.setImpuestoBo(regBO.getDoubleValue(IConciliable.IMPUESTO_1, moneda, true));
		cons.setImpuestoDif(diferenciaImpuesto);

		cons.setObservacion("");
		cons.processInsert();
	}

public void setPdf(GuiPDF pdf) {
		this.pdf = pdf;
	}

}
