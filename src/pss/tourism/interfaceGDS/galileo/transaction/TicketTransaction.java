package pss.tourism.interfaceGDS.galileo.transaction;

import java.util.Date;

import com.ibm.icu.util.Calendar;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.clase.BizClase;
import pss.bsp.clase.detalle.BizClaseDetail;
import pss.common.regions.company.BizCompany;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.tourism.airports.BizAirport;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.galileo.record.GalileoRecord;
import pss.tourism.interfaceGDS.galileo.record.RecordA02;
import pss.tourism.interfaceGDS.galileo.record.RecordA04;
import pss.tourism.interfaceGDS.galileo.record.RecordA07;
import pss.tourism.interfaceGDS.galileo.record.RecordA08;
import pss.tourism.interfaceGDS.galileo.record.RecordA10;
import pss.tourism.interfaceGDS.galileo.record.RecordA11;
import pss.tourism.interfaceGDS.galileo.record.RecordA14;
import pss.tourism.interfaceGDS.galileo.record.RecordA29;
import pss.tourism.interfaceGDS.galileo.record.RecordHeader;
import pss.tourism.pnr.BizPNRSegmentoAereo;
import pss.tourism.pnr.BizPNRTax;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.PNRCache;

public class TicketTransaction extends BaseTransaction {

	protected RecordHeader r1;

	int segments = 0;

	private boolean isInternational = false;

	public void save(JMap<String, Object> mRecords) throws Exception {
		this.mRecords = mRecords;
		r1 = ((RecordHeader) mRecords.getElement(GalileoRecord.HEADER));
		this.setPnrLocator(r1.getFieldValue(RecordHeader.T50RCL));
		PssLogger.logDebug("checking if already exists, GALILEO PNR: " + this.getPnrLocator());

		savePNR();

	}

	long fareItem = 0;
	long cantTickets = 0;
	String emdid;
	
	
	protected EMD getEMDbyID() {
		
		EMD e = null; 

		int i = 0;
		RecordA29 emd = (RecordA29) mRecords.getElement(GalileoRecord.A29RECORD + (i+1) );
		

		while (emd!=null) {
			//emd = (RecordA30) mRecords.getElement(GalileoRecord.A30RECORD + i);
			
			if (emdid.equals(emd.getEMDTicket())) {
				e = new EMD();
				e.baseFare = emd.getBaseAmount();
				e.netFare = emd.getEquivAmount();
				if (e.netFare==0) e.netFare = e.baseFare;
				e.fare = emd.getEquivAmount();
				if (e.fare==0) e.fare = e.netFare;
				
				e.netCurrency = emd.getBaseCurrency();
				e.totalCurrency = emd.getTotalCurrency();
				e.fareWithTax = emd.getTotalAmount();
				e.taxAmount = emd.getTaxAmount1();
				e.taxCode = emd.getTaxCode1();
				e.taxCurrency = emd.getTaxCurrency();
			

				break;
			}
			 i++;
			 emd = (RecordA29) mRecords.getElement(GalileoRecord.A29RECORD + (i+1) );
				
			 if (emd==null)
				 break;
			
		}
		
		return e;
	}

	@Override
	protected boolean savePNR() throws Exception {
		BizPNRTicket tk;
		String tickettype="";
		String ticket =null;
		double sumTarifa = 0;
		JRecords<BizPNRSegmentoAereo> segments;
		JIterator<RecordA02> iter = this.getRecordsA02().getIterator();
		int t=0;
		while (iter.hasMoreElements()) {
			RecordA02 a02 = iter.nextElement();
			RecordA10 tkorig = getTicketOriginal(getCompany(), a02.getFieldValue(RecordA02.A02EIN));
			String emdticket = null;
			setCustId(getDK());
			setCustIdReduced(getDK());
			if (RecordA29.isEMD()) {
				tickettype="D";
				isEMD = true;
			}
			emdid = null;
			if (isEMD) {
				Object[] ids = RecordA29.getIds();
				for (int idx = 0; idx < RecordA29.countEMDs(); idx++) {
					emdid = ids[idx].toString();
					setCompany(getCompany());
					setPnrLocator(r1.getFieldValue(RecordHeader.T50RCL));
					setCreationDate(r1.getFieldValueAsDate(RecordHeader.T50DTE));
					setCreationDateAIR(r1.getFieldValueAsDate(RecordHeader.T50DTE));
					setPNRDate(r1.getFieldValueAsDate(RecordHeader.T50PNR));
					setIATA(JTools.replace(r1.getFieldValue(RecordHeader.T50AAN), " ", ""));
					setOfficeId(r1.getFieldValue(RecordHeader.T50AGS));
					setGds("GALILEO");

					emdticket = RecordA29.getEMDTicket(emdid);
					String aircode = RecordA29.getEMDAirline(emdid);
					Passenger pass = new Passenger();
					pass.passengerName = a02.getFieldValue(RecordA02.A02NME);
					pass.passengerType = a02.getFieldValue(RecordA02.A02PIC).trim();
					mPass.addElement(emdticket, pass);
					this.saveTicket(emdticket, isInternational, aircode, (t + 1) + "", tickettype, "0", null);

				}
			} else {
				tk = crearTicket(a02, tkorig);
				if (!tk.acceptTicket())
					break;
				leerTarifa(tk, a02, fareItem, tkorig);
				pagos(tk, tkorig, sumTarifa);
				sumTarifa += tk.getTarifaTotalFactura();
				segments = generarSegmentos(tk, a02, fareItem, tkorig);
				finalyzeSegments(tk, segments);
			}
			t++;

		}

		return true;
	}
	
	public String getIATA() throws Exception {
		return IATA;
	}

	private RecordA10 getTicketOriginal(String company, String refa10) throws Exception {
		if (refa10 == null)
			return null;
		if (refa10.equals(""))
			return null;
		RecordA10 a10 = getRecordsA10(refa10);
		isReissued = true;

		return a10;

	}

	private BizPNRTicket getRealTicketOriginal(String company, RecordA10 a10) throws Exception {
		if (a10 == null)
			return null;
		String nroBol = a10.getOriginalTicket();
		// nroBol = nroBol.substring(3);
		BizPNRTicket tkt = new BizPNRTicket();
		tkt.dontThrowException(true);
		tkt.addFilter("company", company);
		tkt.addFilter("numeroboleto", nroBol);
		if (!tkt.read())
			return null;

		return tkt;

	}

	private JRecords<BizPNRTax> fillTax(RecordA07 a07, BizPNRTicket tk ) throws Exception {
		JRecords<BizPNRTax> taxs = new JRecords<BizPNRTax>(BizPNRTax.class);
		taxs.setStatic(true);

		RecordA10 tktOrig = null;
		
		double t1Ant = tktOrig == null ? 0 : tktOrig.getFieldValueDouble(RecordA10.A10TT1);
		String tax1Ant = tktOrig == null ? "" : tktOrig.getFieldValue(RecordA10.A10TC1);
		double t2Ant = tktOrig == null ? 0 : tktOrig.getFieldValueDouble(RecordA10.A10TT2);
		String tax2Ant = tktOrig == null ? "" : tktOrig.getFieldValue(RecordA10.A10TC2);
		double t3Ant = tktOrig == null ? 0 : tktOrig.getFieldValueDouble(RecordA10.A10TT3);
		String tax3Ant = tktOrig == null ? "" : tktOrig.getFieldValue(RecordA10.A10TC3);
		double taxAnts = t1Ant + t2Ant + t3Ant;

		double t1 = a07.getFieldValueDouble(RecordA07.A07TT1);
		String tax1 = a07.getFieldValue(RecordA07.A07TC1);
		double t2 = a07.getFieldValueDouble(RecordA07.A07TT2);
		String tax2 = a07.getFieldValue(RecordA07.A07TC2);
		double t3 = a07.getFieldValueDouble(RecordA07.A07TT3);
		String tax3 = a07.getFieldValue(RecordA07.A07TC3);
		String imp = (BizBSPCompany.getConfigView(tk.getCompany()).getTipoImpuesto());
	  Double firstTax = null;
		double taxtot = 0.0f;
		double taxImp = 0.0f;
		double taxIva = 0.0f;
		double taxYQ = 0.0f;
		taxtot += t1;
		taxtot += t2;
		taxtot += t3;
		boolean ivaInXT=false;
		if (tax1 != null) {
			if ((","+imp+",").indexOf(","+tax1+",")==-1)
				taxImp += t1;
			else
				taxIva += t1;
		//	if (firstTax==null) firstTax=t1;
		}
		if (tax2 != null) {
			if ((","+imp+",").indexOf(","+tax2+",")==-1)
				taxImp += t2;
			else
				taxIva += t2;
		//	if (firstTax==null) firstTax=t1;
		}
		if (tax3 != null) {
			if ((","+imp+",").indexOf(","+tax3+",")==-1)
				taxImp += t3;
			else
				taxIva += t3;
		//	if (firstTax==null) firstTax=t1;
		}
		ivaInXT = taxIva==0;
		if (tax1 != null && tax1.equals("YQ"))
			taxYQ += t1;
		if (tax2 != null && tax2.equals("YQ"))
			taxYQ += t2;
		if (tax3 != null && tax3.equals("YQ"))
			taxYQ += t3;
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT1C), a07.getFieldValueDouble(RecordA07.A07IT1), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT2C), a07.getFieldValueDouble(RecordA07.A07IT2), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT3C), a07.getFieldValueDouble(RecordA07.A07IT3), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT4C), a07.getFieldValueDouble(RecordA07.A07IT4), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT5C), a07.getFieldValueDouble(RecordA07.A07IT5), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT6C), a07.getFieldValueDouble(RecordA07.A07IT6), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT7C), a07.getFieldValueDouble(RecordA07.A07IT7), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT8C), a07.getFieldValueDouble(RecordA07.A07IT8), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT9C), a07.getFieldValueDouble(RecordA07.A07IT9), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT10C), a07.getFieldValueDouble(RecordA07.A07IT10), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT11C), a07.getFieldValueDouble(RecordA07.A07IT11), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT12C), a07.getFieldValueDouble(RecordA07.A07IT12), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT13C), a07.getFieldValueDouble(RecordA07.A07IT13), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT14C), a07.getFieldValueDouble(RecordA07.A07IT14), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT15C), a07.getFieldValueDouble(RecordA07.A07IT15), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT16C), a07.getFieldValueDouble(RecordA07.A07IT16), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT17C), a07.getFieldValueDouble(RecordA07.A07IT17), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT18C), a07.getFieldValueDouble(RecordA07.A07IT18), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT19C), a07.getFieldValueDouble(RecordA07.A07IT19), tk);
		this.createTax(taxs, a07.getFieldValue(RecordA07.A07IT20C), a07.getFieldValueDouble(RecordA07.A07IT20), tk);
		if (taxIva==0) {
			JIterator<BizPNRTax> iter = taxs.getStaticIterator();
			while (iter.hasMoreElements()) {
				BizPNRTax t = iter.nextElement();
				if (!t.getNumeroBoleto().equals(tk.getNumeroboleto()))
					continue;

				if ((","+imp+",").indexOf(","+t.getCodigoimpuesto()+",")!=-1) {
					taxIva += t.getImporte();
					break;
				}
			}	
			if (ivaInXT)
				taxImp-=taxIva;
		}
//		if (taxIva==0 && firstTax!=null) {
//			taxImp -= firstTax;
//			taxIva = firstTax;
//		}
		tk.setImpuestos(taxImp);
		tk.setIva(taxIva);
		tk.setTarifaYQ(tk.getTarifaFactura() + taxYQ);
		tk.setYQ(taxYQ);

		double antIva = 0;
		double taxYQAnt = 0;
		if (tax1Ant != null && tax1Ant.equals("YQ"))
			taxYQAnt += t1Ant;
		if (tax2Ant != null && tax2Ant.equals("YQ"))
			taxYQAnt += t2Ant;
		if (tax3Ant != null && tax3Ant.equals("YQ"))
			taxYQAnt += t3Ant;
		if (tax1Ant.equals(imp))
			antIva = t1Ant;
		if (tax2Ant.equals(imp))
			antIva = t2Ant;
		if (tax3Ant.equals(imp))
			antIva = t3Ant;

		tk.setImpuestoFactura(taxAnts + taxImp);
		tk.setIvaFactura(taxIva - antIva);
		tk.setTarifaFacturadaYQ(tk.getTarifaFactura() + (taxYQ == 0 ? 0 : taxYQ - taxYQAnt));
		tk.setYQ(taxYQ);


		saveTaxes(taxs, tk);
		return taxs;
	}

	private BizPNRTax createTax(JRecords<BizPNRTax> taxs, String taxid, double taxamount, BizPNRTicket tk) throws Exception {
		if (taxid == null)
			return null;
		if (taxid.equals(""))
			return null;
		BizPNRTax tax = new BizPNRTax();
		tax.setCompany(tk.getCompany());
		tax.setId(tk.getId());
		tax.setCodigopnr(tk.getCodigopnr());
		tax.setNumeroBoleto(tk.getNumeroboleto());
		tax.setCodigomoneda(tk.getCodigoMoneda());
		tax.setCodigomonedaLocal(tk.getCodigoMonedaLocal());

		tax.setCodigoimpuesto(taxid);
		tax.setImporte(taxamount);
		tax.setTarifasEnDolares(tk);
		taxs.addItem(tax);
		if (taxid.equals("YQ"))
			tk.setYQ(taxamount);
		return tax;
	}

	private void saveTaxes(JRecords<BizPNRTax> recs, BizPNRTicket tk) throws Exception {
		int i = 0;
		JIterator<BizPNRTax> iter = recs.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizPNRTax t = iter.nextElement();
			if (!t.getNumeroBoleto().equals(tk.getNumeroboleto()))
				continue;

			t.setId(tk.getId());
			t.setSecuencia(++i);
			t.processUpdateOrInsertWithCheck();
		}
	}

	public void leerTarifa(BizPNRTicket tk, RecordA02 a02, long fareItem, RecordA10 tktOrig) throws Exception {
		int j = 0;
		String originalTicket = tktOrig==null?null:tktOrig.getOriginalTicket();//RJL
		tktOrig = null;
		JIterator<RecordA07> iter = this.getRecordsA07().getIterator();
		while (iter.hasMoreElements()) {
			RecordA07 a07 = iter.nextElement();

			long fare = a07.getFieldValueNumber(RecordA07.A07FSI);
			if (fare != fareItem)
				continue;

			
			double tarifaBase = a07.getFieldValueDouble(RecordA07.A07TBF);
			double tarifaEqv = a07.getFieldValueDouble(RecordA07.A07EQV);
			double tarifaBaseAnt = tktOrig == null ? 0 : tktOrig.getFieldValueDouble(RecordA10.A10OTF);
			double impuestoAnt = tktOrig == null ? 0 : tktOrig.getFieldValueDouble(RecordA10.A10TT1) + tktOrig.getFieldValueDouble(RecordA10.A10TT2) + tktOrig.getFieldValueDouble(RecordA10.A10TT3);
			double tarifaAnt = tarifaBaseAnt;

			tarifaEqv = tarifaEqv == 0 ? tarifaBase : tarifaEqv;
			if (a07.getFieldValue(RecordA07.A07CRB)!=null)
				tk.setCodigoBaseMoneda(a07.getFieldValue(RecordA07.A07CRB));
			if (a07.getFieldValue(RecordA07.A07CUR)!=null)
				tk.setCodigoMoneda(a07.getFieldValue(RecordA07.A07CUR));
			tk.setTarifaBaseFactura(tarifaBase);
			tk.setTarifaFactura(tarifaEqv - tarifaAnt);
			tk.setTarifa(tarifaEqv);

			double cotiz = tk.getTarifaFactura() / tk.getTarifaBaseFactura();

			String monedaTotal = a07.getFieldValue(RecordA07.A07CRT);
			if (monedaTotal.equals(tk.getCodigoMoneda())) { // esta en moneda
															// nacional
				tk.setTarifaTotalFactura(a07.getFieldValueDouble(RecordA07.A07TTA));
			} else if (monedaTotal.equals(tk.getCodigoBaseMoneda())) { // esta
																		// en
																		// moneda
																		// base
				tk.setTarifaTotalFactura(a07.getFieldValueDouble(RecordA07.A07TTA) * cotiz);
			} else { // ?
				tk.setTarifaTotalFactura(0);
			}

			RecordHeader header = (RecordHeader) mRecords.getElement(GalileoRecord.HEADER);

			double comission = header.getFieldValueDouble(RecordHeader.T50COM);
			double comisRate = header.getFieldValueDouble(RecordHeader.T50RTE) / 100f;

			// los impuestos
			fillTax(a07, tk);

			// tarifas
			tk.setTarifaTotalFactura(tk.getTarifaFactura() + tk.getImpuestoFactura() + tk.getIvaFactura());
			tk.setTarifaFacturadaYQ(tk.getTarifaFactura() + tk.getYQ());
			if (comisRate == 0)
				tk.setComisionFactura(comission);
			else if (comisRate != 0) {
				tk.setComisionPerc(comisRate);
				tk.setComisionFactura((tk.getTarifaFactura() * comisRate) / 100);
			}

			tk.setNetoFactura(tk.getTarifaFactura() - tk.getComisionFactura());

			tk.setNeto(tk.getNetoFactura());
			tk.setImpuestos(tk.getImpuestoFactura());
			tk.setIva(tk.getIvaFactura());
			tk.setImpuestosTotal(tk.getIva());
			tk.setComisionAmount(tk.getComisionFactura());
			tk.setTarifaBaseConImpuestos(tk.getTarifaTotalFactura());
			tk.setTarifaBase(tk.getTarifaBaseFactura());

			// t.setNeto(t.getTarifa() - t.getComisionAmount());
			// t.setNetoFactura(t.getNeto());
			// t.setTarifaFactura(t.getTarifa());
			// t.setTarifaTotalFactura(t.getTarifaBaseConImpuestos());
			// t.setImpuestoFactura(t.getImpuestos());
			// t.setComisionFactura(t.getComisionAmount());

				int i = 1;

			// if (isReissued && tktOrig != null) { // esto deberia cambiar como
			// // esta en SABRE
			// // int i = 1;
			// BizPNRTicket pnrOriginal = findTicketFromBoleto(getCompany(),
			// tktOrig.getOriginalTicket());
			// if (pnrOriginal != null) {
			// pnrOriginal.processReemitir(true);
			// tk.setRefOriginal(pnrOriginal.getId());
			// tk.setNetoFactura(tk.getNeto());
			// tk.setNeto(tk.getNeto() + pnrOriginal.getNeto());
			// tk.setTarifaFactura(tk.getTarifa());
			// tk.setTarifa(tk.getTarifa() + pnrOriginal.getTarifa());
			// tk.setTarifaYQ(tk.getTarifa() + tk.getYQ());
			// tk.setTarifaFacturadaYQ(tk.getTarifaFactura() + tk.getYQ());
			// tk.setIva(tk.getIva() + pnrOriginal.getIva());
			// tk.setTarifaBaseConImpuestos(tk.getTarifaBaseConImpuestos() +
			// pnrOriginal.getTarifaBaseConImpuestos());
			// tk.setImpuestos(tk.getImpuestos() + pnrOriginal.getImpuestos());
			// tk.setComisionAmount(tk.getComisionAmount() +
			// pnrOriginal.getComisionAmount());
			// if (tk.getTarifaBase() > 0)
			// tk.setTipoCambio(tk.getNeto() / tk.getTarifaBase());
			// }
			// tk.setEmision(false);
			// tk.setOriginalNumeroboleto(tktOrig.getOriginalTicket());
			// }
			

			if (originalTicket != null) { // esto deberia cambiar como
				// esta en SABRE
				BizPNRTicket pnrOriginal = findTicketFromBoleto(getCompany(), originalTicket);
				if (pnrOriginal != null) {
					pnrOriginal.processReemitir(true);
					tk.setRefOriginal(pnrOriginal.getId());
					tk.setNeto(tk.getNeto()-pnrOriginal.getNeto()<0?0:tk.getNeto()-pnrOriginal.getNeto());
					tk.setNeto(tk.getNeto() + pnrOriginal.getNeto());
					tk.setTarifa(tk.getTarifa()-pnrOriginal.getTarifa()<0?0:tk.getTarifa()-pnrOriginal.getTarifa());
					tk.setTarifaFactura(tk.getTarifa());
					tk.setTarifaYQ(tk.getTarifa() + tk.getYQ());
					tk.setTarifaFacturadaYQ(tk.getTarifaFactura() + tk.getYQ());
					tk.setIva(tk.getIva() + pnrOriginal.getIva());
					
					tk.setTarifaTotalFactura(tk.getTarifaFactura() + tk.getImpuestoFactura() + tk.getIvaFactura());
					tk.setTarifaBaseConImpuestos(tk.getTarifaTotalFactura());

					tk.setTarifaBaseConImpuestos(tk.getTarifaBaseConImpuestos() + pnrOriginal.getTarifaBaseConImpuestos());
					tk.setImpuestos(tk.getImpuestos() + pnrOriginal.getImpuestos());
					
					tk.setComisionFactura((tk.getTarifaFactura() * comisRate) / 100);
					tk.setComisionAmount(tk.getComisionFactura());
					tk.setComisionAmount(tk.getComisionAmount() + pnrOriginal.getComisionAmount());
					tk.setNetoFactura(tk.getTarifaFactura()-tk.getComisionFactura());
					
					if (tk.getTarifaBase() > 0)
						tk.setTipoCambio(tk.getNeto() / tk.getTarifaBase());
						
				} else {
					addFaltante(getCompany(), originalTicket);

				}
				tk.setEmision(false);
				tk.setOriginalNumeroboleto(originalTicket);
			}
			setTarifasEnDolares(tk);

			// BizPNRTicket orig = getRealTicketOriginal(company, tktOrig);
			// if (orig != null) {
			// orig.processReemitir(true);
			// tk.setRefOriginal(orig.getId());
			// }

		}
	}

	public void pagos(BizPNRTicket tk, RecordA10 tktOrig, double consumido) throws Exception {

		double tarjeta = 0;
		double pagos = 0;
		String cc = "";
		// No se levantan completo los pagos, solo el total por tarjeta
		String typecc = "";
		JIterator<RecordA11> iter = this.getRecordsA11().getIterator();
		while (iter.hasMoreElements()) {
			RecordA11 a11 = iter.nextElement();

			String type = a11.getFieldValue(RecordA11.A11TYP);
			if (type.equals("CC")) {
				tarjeta += a11.getFieldValueDouble(RecordA11.A11AMT);
				typecc = a11.getFieldValue(RecordA11.A11CCC);
				cc = a11.getFieldValue(RecordA11.A11CCN);
			}
			pagos += a11.getFieldValueDouble(RecordA11.A11AMT);

		}
		if (tarjeta >= consumido + tk.getTarifaTotalFactura() && cc.trim().equals("") == false) {
			tk.setMontoTarjeta(tk.getTarifaTotalFactura());
			tk.setNombreTarjeta(typecc);
			tk.setNumeroTarjeta(cc);
		} else if (tarjeta >= consumido && cc.trim().equals("") == false) {
			tk.setMontoTarjeta(tk.getTarifaTotalFactura() - (tarjeta - consumido));
			tk.setNombreTarjeta(typecc);
			tk.setNumeroTarjeta(cc);
		} else {
			tk.setMontoTarjeta(0);
		}

	}

	public JRecords<BizPNRSegmentoAereo> generarSegmentos(BizPNRTicket tk, RecordA02 a02, long fareItem, RecordA10 tktOrig) throws Exception {
		JRecords<BizPNRSegmentoAereo> segments = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		segments.setStatic(true);

		JIterator<RecordA08> iter = this.getRecordsA08().getIterator();
		while (iter.hasMoreElements()) {
			RecordA08 a08 = iter.nextElement();

			long fare = a08.getFieldValueNumber(RecordA08.A08FSI);
			if (fare != fareItem)
				continue;

			BizPNRSegmentoAereo sa = new BizPNRSegmentoAereo();
			sa.setId(tk.getId());
			sa.setCompany(tk.getCompany());
			sa.setCodigoPNR(tk.getCodigopnr());
			sa.setCodigoSegmento(a08.getFieldValue(RecordA08.A08ITN));
			sa.setFareBasic(a08.getFieldValue(RecordA08.A08CFB));
			sa.setFareBasicExpanded(a08.getFieldValue(RecordA08.A08CFB));
			sa.setCodigoPNR(tk.getCodigopnr());

			long seg = a08.getFieldValueNumber(RecordA08.A08ITN);
			completarSegmento(tk, sa, a02, seg, tktOrig);

			segments.addItem(sa);
		}
		return segments;
	}

	private void checkAerolinea(String aerolinea, String descr, String iata) throws Exception {
		if (aerolinea == null)
			return;
		if (aerolinea.equals(""))
			return;
		BizCarrier carr = new BizCarrier();
		carr.dontThrowException(true);
		if (!carr.Read(aerolinea)) {
			BizCarrier carrNew = new BizCarrier();
			carrNew.setCarrier(aerolinea);
			carrNew.setDescription(descr == null ? "Codigo aerolinea " + aerolinea : descr);
			carrNew.setCodIata(iata);
			carrNew.setCodAnalisis(BizCarrier.SIN_LOGICA);
			carrNew.processInsert();
		}
		return;
	}

	public boolean isInternational() throws Exception {
		JIterator<RecordA04> iter = this.getRecordsA04().getIterator();
		while (iter.hasMoreElements()) {
			RecordA04 a04 = iter.nextElement();
			if (a04.isDomestic() == false) {
				return true;
			}
		}
		return false;
	}
	
	public long getYear() {
		try { 
		if (year==0)
			year = JDateTools.getAnioActual();
		} catch (Exception ee) {}
		return year;
	}


	public void completarSegmento(BizPNRTicket tk, BizPNRSegmentoAereo sa, RecordA02 a02, long segemento, RecordA10 tktOrig) throws Exception {
		String firstCarrier = null;
		JIterator<RecordA04> iter = this.getRecordsA04().getIterator();
		while (iter.hasMoreElements()) {
			RecordA04 a04 = iter.nextElement();

			long fare = a04.getFieldValueNumber(RecordA04.A04ITN);
			if (fare != segemento)
				continue;

			String carrier = a04.getFieldValue(RecordA04.A04CDE);
			String carrierOp = carrier;
			String descrCarrier = a04.getFieldValue(RecordA04.A04NME);
			String descrCarrierOp = a04.getFieldValue(RecordA04.A04NME);
			String numCarrier = a04.getFieldValue(RecordA04.A04NUM);
			String numCarrierOp = a04.getFieldValue(RecordA04.A04NUM);
			checkAerolinea(carrier, descrCarrier, numCarrier);
			checkAerolinea(carrierOp, descrCarrierOp, numCarrierOp);
			sa.setCarrier(carrier);
			sa.setCarrierOp(carrierOp);
			sa.setCarrierPlaca(tk.getCarrier());

			sa.setNumeroVuelo(a04.getFieldValue(RecordA04.A04FLT));
			sa.setClase(a04.getFieldValue(RecordA04.A04CLS));

			sa.setArrivo(a04.getFieldValue(RecordA04.A04DCC));
			sa.setDespegue(a04.getFieldValue(RecordA04.A04OCC));
			sa.setConnectionIndicator(a04.getFieldValue(RecordA04.A04STP));
			sa.setCodigoComida(a04.getFieldValue(RecordA04.A04SVC));
			sa.setCodigoEntreten(a04.getFieldValue(RecordA04.A04SVC));

			String ind = a04.getFieldValue(RecordA04.A04IND);
			Calendar arrivo = Calendar.getInstance();
			Calendar despegue = Calendar.getInstance();
			long year = getYear();
			Date desp=a04.getFieldValueAsDateShort(RecordA04.A04DTE, year);
			Date desp2=a04.getFieldValueAsDate(RecordA04.A04DDD);
			
			despegue.setTime(desp2==null?desp:desp2);
			despegue.set(Calendar.HOUR_OF_DAY, (int) JTools.getLongFirstNumberEmbedded(a04.getFieldValue(RecordA04.A04TME).substring(0, 2)));
			despegue.set(Calendar.MINUTE, (int) JTools.getLongFirstNumberEmbedded(a04.getFieldValue(RecordA04.A04TME).substring(2, 4)));
			arrivo.setTime(despegue.getTime());
			if (ind.equals("1"))
				arrivo.add(Calendar.DAY_OF_MONTH, -1);
			else if (ind.equals("3"))
				arrivo.add(Calendar.DAY_OF_MONTH, 1);
			else if (ind.equals("4"))
				arrivo.add(Calendar.DAY_OF_MONTH, 2);
			if (!a04.getFieldValue(RecordA04.A04ARV).equals("")) {
				arrivo.set(Calendar.HOUR_OF_DAY, (int) JTools.getLongFirstNumberEmbedded(a04.getFieldValue(RecordA04.A04ARV).substring(0, 2)));
				arrivo.set(Calendar.MINUTE, (int) JTools.getLongFirstNumberEmbedded(a04.getFieldValue(RecordA04.A04ARV).substring(2, 4)));
			}

			sa.setHoraDespegue(a04.getFieldValue(RecordA04.A04TME));
			sa.setHoraArrivo(a04.getFieldValue(RecordA04.A04ARV));
			sa.setNumeroVuelo(a04.getFieldValue(RecordA04.A04FLT));
			sa.setEstado(a04.getFieldValue(RecordA04.A04STS));
			
			BizAirport airportDespegue = sa.getObjAeropuertoDespegue();
			BizAirport airportArribo = sa.getObjAeropuertoArrivo();

			if (airportArribo.getCountry().equals(tk.getObjCompany().getCountry()))
				sa.setPaisDestino(airportDespegue.getCountry());
			else
				sa.setPaisDestino(airportArribo.getCountry());
			
			boolean segmentoInternacional = !airportDespegue.getCountry().equals(tk.getObjCompany().getCountry())
					|| !airportDespegue.getObjCountry().GetPais().equals(airportArribo.getObjCountry().GetPais());
			
			BizClaseDetail cd =  PNRCache.getTipoClaseCache(getCompany(), sa.getCarrierPlaca(), sa.getClase(),segmentoInternacional);
			if (cd == null)
				cd =  PNRCache.getTipoClaseCache(getCompany(), sa.getCarrierPlaca(), sa.getClase(),segmentoInternacional);
			if (cd == null) {
				sa.setTipoClase(BizClase.getTipoClaseDefault(sa.getClase()));
				sa.setPrioridad(BizClase.getPrioridadClaseDefault(sa.getClase()));
			} else {
				sa.setTipoClase(cd.getObjClase().getDescripcion());
				sa.setPrioridad(cd.getPrioridad());
			}

			long duracion = JDateTools.getMinutesBetween(despegue.getTime(), arrivo.getTime());
			if (duracion<0)
				duracion=-duracion;
			String strDur = JTools.LPad("" + (duracion / 60), 2, "0") + JTools.LPad("" + (duracion % 60), 2, "0");

			sa.setFechaDespegue(despegue.getTime());
			sa.setFechaArrivo(arrivo.getTime());
			sa.setDuracion(strDur);
			sa.setGeoArrivo(this.findGeoAirport(sa.getArrivo()));
			sa.setGeoDespegue(this.findGeoAirport(sa.getDespegue()));
			sa.setRuta(sa.getDespegue() + "/" + sa.getArrivo());
			sa.setLimitePeso(a04.getFieldValue(RecordA04.A04BAG));
			sa.setTipoEquipo(a04.getFieldValue(RecordA04.A04AIR));



			if (firstCarrier == null && segmentoInternacional) {
				firstCarrier = sa.getCarrier();
			}

			break;
		}
	}

	public BizPNRTicket crearTicket(RecordA02 a02, RecordA10 tktOrig) throws Exception {
		BizPNRTicket tk = new BizPNRTicket();
		tk.setCodigoaerolinea(r1.getFieldValue(RecordHeader.T50ISC));
		String modelo = r1.getFieldValue(RecordHeader.T50TRC);
		String bspcode = r1.getFieldValue(RecordHeader.T50ISA);
		String aerolinea = r1.getFieldValue(RecordHeader.T50ISC);
		String descr = r1.getFieldValue(RecordHeader.T50ISN);

		checkAerolinea(aerolinea, descr, bspcode);
		tk.setVoid(false);
		tk.setNullVoidDate();
		tk.setCompany(getCompany());
		tk.setFechaProcesamiento(new Date());
		tk.setCodigopnr(r1.getFieldValue(RecordHeader.T50RCL));
		tk.setCreationDate(a02.getFieldValueAsDate(RecordA02.A02NFC));
		tk.setCreationDateAir(r1.getFieldValueAsDate(RecordHeader.T50DTE));
		tk.setPNRDate(r1.getFieldValueAsDate(RecordHeader.T50PNR));
		tk.setNroIata(JTools.replace(r1.getFieldValue(RecordHeader.T50AAN), " ", ""));
		tk.setOfficeId(r1.getFieldValue(RecordHeader.T50TPC));
		tk.setVendedor(r1.getFieldValue(RecordHeader.T50AGT));
		tk.setCodigoMoneda(r1.getFieldValue(RecordHeader.T50CUR));
		tk.setCodigoMonedaLocal(BizCompany.getObjCompany(tk.getCompany()).getCurrency());
		tk.setGDS("GALILEO");
		tk.setCustomerIdReducido(getCustIdReduced());
		tk.setCustomerId(this.getCustId());
		tk.setTipoOperacion("ETR");
		tk.setArchivo(this.getPnrFile());

		tk.setTourCode(r1.getFieldValue(RecordHeader.T50ITC));
		tk.setInternacional(!r1.getFieldValue(RecordHeader.T50IN17).equals(" "));
		tk.setInternacionalDescr(isInternational() ? "International" : "Domestic");

		tk.setNombrePasajero(a02.getFieldValue(RecordA02.A02NME));
		tk.setTipoPasajero(a02.getFieldValue(RecordA02.A02PIC).trim());
		tk.setNumeroboleto(a02.getFieldValue(RecordA02.A02TKT));
		
		fareItem = a02.getFieldValueNumber(RecordA02.A02FIN);

		tk.setReemitted(false);

		// if (isReissued && tktOrig != null) { // esto deberia cambiar como
		// // esta en SABRE
		// int i = 1;
		// BizPNRTicket pnrOriginal = findTicketFromBoleto(getCompany(),
		// tktOrig.getOriginalTicket());
		// if (pnrOriginal != null) {
		// pnrOriginal.processReemitir(true);
		// tk.setRefOriginal(pnrOriginal.getId());
		// tk.setNetoFactura(tk.getNeto());
		// tk.setNeto(tk.getNeto() + pnrOriginal.getNeto());
		// tk.setTarifaFactura(tk.getTarifa());
		// tk.setTarifa(tk.getTarifa() + pnrOriginal.getTarifa());
		// tk.setTarifaYQ(tk.getTarifa() + tk.getYQ());
		// tk.setTarifaFacturadaYQ(tk.getTarifaFactura() + tk.getYQ());
		// tk.setIva(tk.getIva() + pnrOriginal.getIva());
		// tk.setTarifaBaseConImpuestos(tk.getTarifaBaseConImpuestos() +
		// pnrOriginal.getTarifaBaseConImpuestos());
		// tk.setImpuestos(tk.getImpuestos() + pnrOriginal.getImpuestos());
		// tk.setComisionAmount(tk.getComisionAmount() +
		// pnrOriginal.getComisionAmount());
		// if (tk.getTarifaBase() > 0)
		// tk.setTipoCambio(tk.getNeto() / tk.getTarifaBase());
		// }
		// tk.setEmision(false);
		// tk.setOriginalNumeroboleto(tktOrig.getOriginalTicket());
		// }

		if (tk.acceptTicket()) {
			tk.SetVision("UPDATE");
			tk.setCalculed(false);
			this.save(tk);
			
		}
		return tk;
	}

	private JList<RecordA02> getRecordsA02() throws Exception {
		JList<RecordA02> list = JCollectionFactory.createList();
		int i = 0;
		while (true) {
			i++;
			RecordA02 a02 = (RecordA02) (mRecords.getElement(GalileoRecord.A02RECORD + i));
			if (a02 == null)
				break;
			list.addElement(a02);
		}
		cantTickets = i;
		return list;
	}

	private JList<RecordA04> getRecordsA04() throws Exception {
		JList<RecordA04> list = JCollectionFactory.createList();
		int i = 0;
		while (true) {
			i += 1;
			RecordA04 a04 = (RecordA04) (mRecords.getElement(GalileoRecord.A04RECORD + i));
			if (a04 == null)
				break;
			list.addElement(a04);
		}
		return list;
	}
	private String getDK() throws Exception {
		int i = 0;
		while (true) {
			i += 1;
			RecordA14 a14 = (RecordA14) (mRecords.getElement(GalileoRecord.A14RECORD + i));
			if (a14 == null)
				break;
			if (a14.getFieldValue(RecordA14.A14SA)!=null)
				return a14.getFieldValue(RecordA14.A14SA);
		}
		return null;
	}

	private JList<RecordA07> getRecordsA07() throws Exception {
		JList<RecordA07> list = JCollectionFactory.createList();
		int i = 0;
		while (true) {
			i += 1;
			RecordA07 a07 = (RecordA07) (mRecords.getElement(GalileoRecord.A07RECORD + i));
			if (a07 == null)
				break;
			list.addElement(a07);
		}
		return list;
	}

	private JList<RecordA08> getRecordsA08() throws Exception {
		JList<RecordA08> list = JCollectionFactory.createList();
		int i = 0;
		while (true) {
			i += 1;
			RecordA08 a08 = (RecordA08) (mRecords.getElement(GalileoRecord.A08RECORD + i));
			if (a08 == null)
				break;
			list.addElement(a08);
		}
		return list;
	}

	private JList<RecordA11> getRecordsA11() throws Exception {
		JList<RecordA11> list = JCollectionFactory.createList();
		int i = 0;
		while (true) {
			i += 1;
			RecordA11 a11 = (RecordA11) (mRecords.getElement(GalileoRecord.A11RECORD + i));
			if (a11 == null)
				break;
			list.addElement(a11);
		}
		return list;
	}

	private RecordA10 getRecordsA10(String ref) throws Exception {
		int i = 0;
		while (true) {
			i += 1;
			RecordA10 a10 = (RecordA10) (mRecords.getElement(GalileoRecord.A10RECORD + i));
			if (a10 == null)
				break;
			if (!a10.getFieldValue(RecordA10.A10EXI).equals(ref))
				continue;
			return a10;

		}
		return null;
	}
}
