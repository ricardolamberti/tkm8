package pss.bsp.consolidador;

import java.util.Date;
import java.util.List;

import pss.bsp.bo.BizInterfazBO;
import pss.bsp.bo.GuiInterfazBO;
import pss.bsp.bo.formato.BizFormato;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.bsp.consolidador.consolidacionOracle.BizConsolidacionOracle;
import pss.bsp.consolidador.diferencia.BizDiferencia;
import pss.bsp.consolidador.iva.BizImpositivo;
import pss.bsp.consolidador.over.BizOver;
import pss.bsp.pdf.GuiPDF;
import pss.bsp.ticketsOracle.GuiTicketOracle;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWin;
import pss.tourism.pnr.BizPNRTicket;

public class JConsolidadorOracle implements IConsolidador {

  GuiTicketOracle pdf;


	long linea = 0;


	boolean ignoreFormato = false;
	
	JList<String> boletos;

	public JWin getJWin() {
		return null;
	}

	private boolean checkExits() throws Exception {
		BizConsolidacionOracle cons = new BizConsolidacionOracle();
		cons.addFilter("company", pdf.GetcDato().getCompany());
	//	cons.addFilter("owner", pdf.GetcDato().getOwner());
		cons.addFilter("idpdf", pdf.GetcDato().getId());
		return cons.selectCount() > 0;
	}

	private void clearAll() throws Exception {
		BizConsolidacionOracle cons = new BizConsolidacionOracle();
		cons.addFilter("company", pdf.GetcDato().getCompany());
	//	cons.addFilter("owner", pdf.GetcDato().getOwner());
		cons.addFilter("idpdf", pdf.GetcDato().getId());
		cons.deleteMultiple();

	}

	private double getPrecision() throws Exception {
		return BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getPrecision();
	}
	private double getFactorBO() throws Exception {
		return BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getFactorBo();
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
			JIterator<IConciliable> it = pdf.getDetail().getRecords().getStaticIterator();
			while (it.hasMoreElements()) {
				IConciliable regBSP = (IConciliable) it.nextElement();
				IConciliable regPNR = obtenerPNR(regBSP);
				comparePNR(regBSP, regPNR);
				if (regBSP == null ) continue;
			}
			buscarBoletosFaltantesEnPNR(fechaDesde, fechaHasta);
			pdf.GetcDato().setEstado("CONCILIADO");
			pdf.GetcDato().processUpdate();
			JBDatos.GetBases().commit();
		} catch (RuntimeException e) {
			JBDatos.GetBases().rollback();
			throw e;
		}
	}
	private void crearEntradaComparativa(IConciliable regBSP, IConciliable regBO,String tipo) throws Exception {
		BizConsolidacionOracle cons = new BizConsolidacionOracle();
		boolean equals = true;
		cons.setCompany(pdf.GetcDato().getCompany());
		cons.setOwner(pdf.GetcDato().getOwner());
		cons.setIdpdf(pdf.GetcDato().getId());
		cons.setTipo(tipo);
		cons.setLinea(linea++);
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
			
			
			boolean esReembolso = regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("REEMBOLSO")!=-1 || regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("ACM CONCEPTO")!=-1;
			boolean esAnulacion = regBSP.getStringValue(IConciliable.OPERACION, true).indexOf("REFUND")!=-1;
			
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
					if (Math.abs(valueBSP - valueBO) < 0.01 + getPrecision()) cons.setD(campo, String.valueOf(valueBSP.doubleValue()));
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
					if (valueStringBSP.equalsIgnoreCase(valueBO)) cons.setD(campo, valueStringBSP);
					else {
						cons.setD(campo, "BSP:" + valueStringBSP + " <> "+tipo+":" + valueBO);
						equals = false;
					}
					continue;
				}

			}
		}
		cons.setObservaciones("");
		cons.setStatus(equals ? IConsolidador.OK : IConsolidador.DISTINCT);
		cons.processInsert();
	}
	private void crearEntradaPorFaltaEnBO(IConciliable regBSP,String tipo) throws Exception {
		BizConsolidacionOracle cons = new BizConsolidacionOracle();
		cons.setCompany(pdf.GetcDato().getCompany());
		cons.setOwner(pdf.GetcDato().getOwner());
		cons.setTipo(tipo);
		cons.setIdpdf(pdf.GetcDato().getId());
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

	public void comparePNR(IConciliable regBSP, IConciliable regPNR) throws Exception {
		ignoreFormato=true;
		if (regPNR == null) {// el Boleto esta en falta
			crearEntradaPorFaltaEnBO(regBSP,IConsolidador.PNR);
			return;
		}
		crearEntradaComparativa(regBSP, regPNR,IConsolidador.PNR);
	}

	private void buscarBoletosFaltantesEnPNR(Date fechaDesde, Date fechaHasta) throws Exception {
			ignoreFormato=true;
			JRecords<BizPNRTicket> details = new JRecords<BizPNRTicket>(BizPNRTicket.class);
			details.addFilter("company", pdf.GetcDato().getCompany());
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


	private void crearEntradaPorFaltaEnBSP(IConciliable regBO,String tipo) throws Exception {
		BizConsolidacionOracle cons = new BizConsolidacionOracle();
		cons.setCompany(pdf.GetcDato().getCompany());
		cons.setOwner(pdf.GetcDato().getOwner());
		cons.setIdpdf(pdf.GetcDato().getId());
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


  public void setOracle(GuiTicketOracle pdf) {
		this.pdf = pdf;
	}

}
