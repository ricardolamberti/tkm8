package pss.bsp.consolid.model.liquidacion.detail;

import java.util.Date;

import pss.bsp.consolid.model.cotizacionDK.BizCotizacionDK;
import pss.bsp.consolid.model.feeDK.BizFeeDK;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.parseo.IFormato;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.tourism.pnr.BizPNRTicket;

public class BizLiqDetail extends JRecord implements IConciliable {

	
		public BizLiqDetail() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}


		private JString pCompany = new JString();
		private JLong pLiquidacionId = new JLong();
		private JLong pLinea = new JLong();
		private JLong pCustomerTrxId = new JLong();
		private JString pDk = new JString();
		private JString pOrganization = new JString();
		private JString pGrupoIata = new JString();
		private JString pIata = new JString();
		private JString pTipoDoc = new JString();
		private JString pTipoDocPunto = new JString() {
			public void preset() throws Exception {
				pTipoDocPunto.setValue(getTipoDoc()+".");
			}
		};
		private JString pNroDoc = new JString();
		private JDate pFechaDoc = new JDate();
		private JString pFechaDocArabian = new JString() {
			public void preset() throws Exception {
				pFechaDocArabian.setValue(JDateTools.DateToString(getFechaDoc(),"dd.MM.yyyy"));
			};
		};
		private JString pNroBoleto = new JString();
		private JString pPrestador = new JString();
		private JString pAgente = new JString();
		private JString pRuta = new JString();
		private JString pPasajero = new JString();
		private JString pReserva = new JString();
		private JFloat pTarifa = new JFloat();
		private JFloat pIva = new JFloat();
		private JFloat pTua = new JFloat();
		private JFloat pImporte = new JFloat();
		private JFloat pSaldo = new JFloat();
		private JFloat pComision = new JFloat();
		private JFloat pPorcComision = new JFloat();
		private JString pTipoServicio = new JString();
		private JString pOrigen = new JString();
		private JString pClase = new JString();
		private JString pClases = new JString();
		private JString pLineas = new JString();
		private JString pFormaPago = new JString();
		private JFloat pPorcIncentivo = new JFloat();
		private JFloat pIncentivo = new JFloat();
		private JString pTipo = new JString();
		private JLong pInterfaceId = new JLong();
		private JFloat pContado = new JFloat();
		private JFloat pTarjeta = new JFloat();
		private JFloat pFiscal = new JFloat();
		private JFloat pNoFiscal = new JFloat();
		private JFloat pNoDevengado = new JFloat();

		private JString pAereoTicket = new JString() {
			public void preset() throws Exception {
				pAereoTicket.setValue(getAereoTicket());
			};
		};
		private JString pFormaPagoShow = new JString() {
			public void preset() throws Exception {
				pFormaPagoShow.setValue(getFormaPagoShow());
			};
		};
		private JString pTipoServ = new JString() {
			public void preset() throws Exception {
				pTipoServ.setValue(getTipoServ());
			};
		};
		private JString pTipoPasajero = new JString() {
			public void preset() throws Exception {
				pTipoPasajero.setValue(getTipoPasajero());
			};
		};
		private JFloat pTarifaYq = new JFloat() {
			public void preset() throws Exception {
				pTarifaYq.setValue(getTarifaYq());
			};
		};
		private JFloat pIvaTua = new JFloat() {
			public void preset() throws Exception {
				pIvaTua.setValue(getIvaTua());
			};
		};
		private JFloat pFuel = new JFloat() {
			public void preset() throws Exception {
				pFuel.setValue(getFuel());
			};
		};
		private JFloat pBaseTaxFuel = new JFloat() {
			public void preset() throws Exception {
				pBaseTaxFuel.setValue(getBaseTaxFuel());
			};
		};
		private JFloat pTotalFee = new JFloat() {
			public void preset() throws Exception {
				pTotalFee.setValue(getTotalFee());
			};
		};
		private JFloat pFee = new JFloat() {
			public void preset() throws Exception {
				pFee.setValue(getFee());
			};
		};
		private JFloat pPriceComision = new JFloat() {
			public void preset() throws Exception {
				pPriceComision.setValue(getPriceComision());
			};
		};

    private JDate pFechaPnr = new JDate() {
			public void preset() throws Exception {
				pFechaPnr.setValue(getFechaPnr());
			};
		};
		private JDate pFechaDeparture = new JDate() {
			public void preset() throws Exception {
				pFechaDeparture.setValue(getFechaDeparture());
			};
		};
		private JFloat pNetPrice = new JFloat() {
			public void preset() throws Exception {
				pNetPrice.setValue(getNetPrice());
			};
		};	
		private JFloat pImpuestos = new JFloat() {
			public void preset() throws Exception {
				pImpuestos.setValue(getImpuestos());
			};
		};
		
		private JFloat pTotalLocal = new JFloat() {
			public void preset() throws Exception {
				pTotalLocal.setValue(getI3TotalLocal());
			};
		};
		
		private JFloat pCotiz = new JFloat() {
			public void preset() throws Exception {
				pCotiz.setValue(getI3Cotiz());
			};
		};
		private JFloat pI3Fee = new JFloat() {
			public void preset() throws Exception {
				pI3Fee.setValue(getI3Fee());
			};
		};
		private JFloat pTotalUsd = new JFloat() {
			public void preset() throws Exception {
				pTotalUsd.setValue(getI3TotalUsd());
			};
		};
		private JFloat pTotal = new JFloat() {
			public void preset() throws Exception {
				pTotal.setValue(getI3Total());
			};
		};
		private JFloat pComisionBSP = new JFloat() {
			public void preset() throws Exception {
				pComisionBSP.setValue(getComisionBSP());
			};
		};
		private JFloat pComisionPorcBSP = new JFloat() {
			public void preset() throws Exception {
				pComisionPorcBSP.setValue(getComisionPorcBSP());
			};
		};
		private JFloat pComisionIvaBSP = new JFloat() {
			public void preset() throws Exception {
				pComisionIvaBSP.setValue(getComisionIvaBSP());
			};
		};
		private JFloat pCreditBsp = new JFloat() {
			public void preset() throws Exception {
				pCreditBsp.setValue(getCreditBSP());
			};
		};
		private JFloat pCreditTaxBsp = new JFloat() {
			public void preset() throws Exception {
				pCreditTaxBsp.setValue(getCreditTaxBSP());
			};
		};
		private JFloat pCashBsp = new JFloat() {
			public void preset() throws Exception {
				pCashBsp.setValue(getCashBSP());
			};
		};
		private JFloat pCashTaxBsp = new JFloat() {
			public void preset() throws Exception {
				pCashTaxBsp.setValue(getCashTaxBSP());
			};
		};
		private JFloat pCommBsp = new JFloat() {
			public void preset() throws Exception {
				pCommBsp.setValue(getCommBSP());
			};
		};
  	private JFloat pReissue = new JFloat() {
			public void preset() throws Exception {
				pReissue.setValue(0);
			};
		};
		private JString pAs = new JString() {
			public void preset() throws Exception {
				pAs.setValue("");
			};
		};
		private JString pNote = new JString() {
			public void preset() throws Exception {
				pNote.setValue("");
			};
		};
		private JString pSvc = new JString() {
			public void preset() throws Exception {
				pSvc.setValue("");
			};
		};
		private JString pTrnc = new JString() {
			public void preset() throws Exception {
				pTrnc.setValue(getBSPTrnc());
			};
		};
		private JString pGrandTotal = new JString() {
			public void preset() throws Exception {
				pGrandTotal.setValue(getGrandTotal());
			};
		};		
		private JString pGds = new JString() {
			public void preset() throws Exception {
				pGds.setValue(getGDS());
			};
		};
		private JString pSaleFl = new JString() {
			public void preset() throws Exception {
				pSaleFl.setValue("");
			};
		};
		private JString pAgenciaFl = new JString() {
			public void preset() throws Exception {
				pAgenciaFl.setValue("");
			};
		};
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Getter & Setters methods
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		public void setCompany(String zValue) throws Exception { pCompany.setValue(zValue); }
		public String getCompany() throws Exception { return pCompany.getValue(); }

		public void setOrganization(String zValue) throws Exception {
			if (zValue == null || zValue.equals(""))
				PssLogger.logDebug("log point");
			pOrganization.setValue(zValue); 
			}
		public String getOrganization() throws Exception { return pOrganization.getValue(); }
		public boolean isNullOrganization() throws Exception { return pOrganization.isNull(); }

		public void setLiquidacionId(long zValue) throws Exception { pLiquidacionId.setValue(zValue); }
		public long getLiquidacionId() throws Exception { return pLiquidacionId.getValue(); }

		public void setLinea(long zValue) throws Exception { pLinea.setValue(zValue); }
		public long getLinea() throws Exception { return pLinea.getValue(); }
		
		public void setCustomerTrxId(long zValue) throws Exception { pCustomerTrxId.setValue(zValue); }
		public long getCustomerTrxId() throws Exception { return pCustomerTrxId.getValue(); }

		public void setDk(String zValue) throws Exception { pDk.setValue(zValue); }
		public String getDk() throws Exception { return pDk.getValue(); }

		public void setTipoDoc(String zValue) throws Exception { pTipoDoc.setValue(zValue); }
		public String getTipoDoc() throws Exception { return pTipoDoc.getValue(); }

		public void setNroDoc(String zValue) throws Exception { pNroDoc.setValue(zValue); }
		public String getNroDoc() throws Exception { return pNroDoc.getValue(); }

		public void setFechaDoc(Date zValue) throws Exception { pFechaDoc.setValue(zValue); }
		public Date getFechaDoc() throws Exception { return pFechaDoc.getValue(); }

		public void setNroBoleto(String zValue) throws Exception { pNroBoleto.setValue(zValue); }
		public String getNroBoleto() throws Exception { return pNroBoleto.getValue(); }
 
		public void setAgente(String zValue) throws Exception { pAgente.setValue(zValue); }
		public String getAgente() throws Exception { return pAgente.getValue(); }

		public void setPrestador(String zValue) throws Exception { pPrestador.setValue(zValue); }
		public String getPrestador() throws Exception { return pPrestador.getValue(); }

		public void setRuta(String zValue) throws Exception { pRuta.setValue(zValue); }
		public String getRuta() throws Exception { return pRuta.getValue(); }

		public void setPasajero(String zValue) throws Exception { pPasajero.setValue(zValue); }
		public String getPasajero() throws Exception { return pPasajero.getValue(); }

		public void setReserva(String zValue) throws Exception { pReserva.setValue(zValue); }
		public String getReserva() throws Exception { return pReserva.getValue(); }

		public void setTarifa(double zValue) throws Exception { pTarifa.setValue(zValue); }
		public double getTarifa() throws Exception { return pTarifa.getValue(); }

		public void setIva(double zValue) throws Exception { pIva.setValue(zValue); }
		public double getIva() throws Exception { return pIva.getValue(); }

		public void setTua(double zValue) throws Exception { pTua.setValue(zValue); }
		public double getTua() throws Exception { return pTua.getValue(); }

		public void setImporte(double zValue) throws Exception { pImporte.setValue(zValue); }
		public double getImporte() throws Exception { return pImporte.getValue(); }

		public void setSaldo(double zValue) throws Exception { pSaldo.setValue(zValue); }
		public double getSaldo() throws Exception { return pSaldo.getValue(); }

		public void setComision(double zValue) throws Exception { pComision.setValue(zValue); }
		public double getComision() throws Exception { return pComision.getValue(); }

		public void setPorcComision(double zValue) throws Exception { pPorcComision.setValue(zValue); }
		public double getPorcComision() throws Exception { return pPorcComision.getValue(); }

		public void setTipoServicio(String zValue) throws Exception { pTipoServicio.setValue(zValue); }
		public String getTipoServicio() throws Exception { return pTipoServicio.getValue(); }

		public void setOrigen(String zValue) throws Exception { pOrigen.setValue(zValue); }
		public String getOrigen() throws Exception { return pOrigen.getValue(); }

		public void setClase(String zValue) throws Exception { pClase.setValue(zValue); }
		public String getClase() throws Exception { return pClase.getValue(); }

		public void setClases(String zValue) throws Exception { pClases.setValue(zValue); }
		public String getClases() throws Exception { return pClases.getValue(); }

		public void setLineas(String zValue) throws Exception { pLineas.setValue(zValue); }
		public String getLineas() throws Exception { return pLineas.getValue(); }

		public void setFormaPago(String zValue) throws Exception { pFormaPago.setValue(zValue); }
		public String getFormaPago() throws Exception { return pFormaPago.getValue(); }

		public void setPorcIncentivo(double zValue) throws Exception { pPorcIncentivo.setValue(zValue); }
		public double getPorcIncentivo() throws Exception { return pPorcIncentivo.getValue(); }

		public void setIncentivo(double zValue) throws Exception { pIncentivo.setValue(zValue); }
		public double getIncentivo() throws Exception { return pIncentivo.getValue(); }

		public void setContado(double zValue) throws Exception { pContado.setValue(zValue); }
		public double getContado() throws Exception { return pContado.getValue(); }
		public void setTarjeta(double zValue) throws Exception { pTarjeta.setValue(zValue); }
		public double getTarjeta() throws Exception { return pTarjeta.getValue(); }
		public void setFiscal(double zValue) throws Exception { pFiscal.setValue(zValue); }
		public double getFiscal() throws Exception { return pFiscal.getValue(); }
		public void setNoFiscal(double zValue) throws Exception { pNoFiscal.setValue(zValue); }
		public double getNoFiscal() throws Exception { return pNoFiscal.getValue(); }
		public void setNoDevengado(double zValue) throws Exception { pNoDevengado.setValue(zValue); }
		public double getNoDevengado() throws Exception { return pNoDevengado.getValue(); }


		public void setTipo(String zValue) throws Exception { pTipo.setValue(zValue); }
		public String getTipo() throws Exception { return pTipo.getValue(); }

		public void setInterfaceId(long zValue) throws Exception { pInterfaceId.setValue(zValue); }
		public long getInterfaceId() throws Exception { return pInterfaceId.getValue(); }

		
		public void createProperties() throws Exception {
		    this.addItem("company", pCompany);
		    this.addItem("liquidacion_id", pLiquidacionId);
		    this.addItem("linea", pLinea);
		    this.addItem("organization", pOrganization);
			  this.addItem("customer_trx_id", pCustomerTrxId);
		    this.addItem("dk", pDk);
		    this.addItem("tipo_doc", pTipoDoc);
		    this.addItem("tipo_doc_punto", pTipoDocPunto);
		    this.addItem("nro_doc", pNroDoc);
		    this.addItem("fecha_doc", pFechaDoc);
		    this.addItem("fecha_doc_arabian", pFechaDocArabian);
		    this.addItem("nro_boleto", pNroBoleto);
		    this.addItem("agente", pAgente);
		    this.addItem("prestador", pPrestador);
		    this.addItem("ruta", pRuta);
		    this.addItem("pasajero", pPasajero);
		    this.addItem("reserva", pReserva);
		    this.addItem("tarifa", pTarifa);
		    this.addItem("iva", pIva);
		    this.addItem("tua", pTua);
		    this.addItem("importe", pImporte);
		    this.addItem("saldo", pSaldo);
		    this.addItem("comision", pComision);
		    this.addItem("porc_comision", pPorcComision);
		    this.addItem("tipo_servicio", pTipoServicio);
		    this.addItem("origen", pOrigen);
		    this.addItem("clase", pClase);
		    this.addItem("clases", pClases);
		    this.addItem("lineas", pLineas);
		    this.addItem("forma_pago", pFormaPago);
		    this.addItem("porc_incentivo", pPorcIncentivo);
		    this.addItem("incentivo", pIncentivo);
		    this.addItem("tipo", pTipo);
		    this.addItem("interface_id", pInterfaceId);
		    this.addItem("contado", pContado);
		    this.addItem("tarjeta", pTarjeta);
		    this.addItem("fiscal", pFiscal);
		    this.addItem("nofiscal", pNoFiscal);
		    this.addItem("noDevengado", pNoDevengado);
		    this.addItem("aereo_ticket", pAereoTicket);
		    this.addItem("forma_pago_show", pFormaPagoShow);
		    this.addItem("type_serv", pTipoServ);
		    this.addItem("type_pasajero", pTipoPasajero);
		    this.addItem("tarifa_yq", pTarifaYq);
		    this.addItem("iva_tua", pIvaTua);
		    this.addItem("fuel", pFuel);
		    this.addItem("base_tax_fuel", pBaseTaxFuel);
		    this.addItem("total_fee", pTotalFee);
		    this.addItem("fee", pFee);
		    this.addItem("price_comision", pPriceComision);
		    this.addItem("fecha_pnr", pFechaPnr);
		    this.addItem("departure", pFechaDeparture);
		    this.addItem("net_price", pNetPrice);
		    this.addItem("impuestos", pImpuestos);
		    this.addItem("i3_total_local", pTotalLocal);
		    this.addItem("i3_cotiz", pCotiz);
		    this.addItem("i3_total", pTotal);
		  	this.addItem("i3_fee", pI3Fee);
		    this.addItem("i3_total_usd", pTotalUsd);
		    this.addItem("bsp_comision", pComisionBSP);
		    this.addItem("bsp_comision_porc", pComisionPorcBSP);
		    this.addItem("bsp_comision_iva", pComisionIvaBSP);
		    this.addItem("bsp_credit", pCreditBsp);
		    this.addItem("bsp_cash", pCashBsp);
		    this.addItem("bsp_credit_tax", pCreditTaxBsp);
		    this.addItem("bsp_cash_tax", pCashTaxBsp);
		    this.addItem("bsp_comm", pCommBsp);
				this.addItem("reissue", pReissue);
		    this.addItem("trnc", pTrnc);
		    this.addItem("as", pAs);
		    this.addItem("svc", pSvc);
		    this.addItem("note", pNote);
		    this.addItem("grand_total", pGrandTotal);
		    this.addItem("gds", pGds);
		    this.addItem("sale_fl", pSaleFl);
		    this.addItem("agencia_fl", pAgenciaFl);
		    
	}
		
 

		/**
		 * Adds the fixed object properties
		 */
		public void createFixedProperties() throws Exception {
	    this.addFixedItem(FIELD, "company", "Company", true, true, 18);
	    this.addFixedItem(FIELD, "liquidacion_id", "Liquidacion ID", true, true, 18);
	    this.addFixedItem(KEY, "linea", "Linea", false, false, 64);
	    this.addFixedItem(FIELD, "dk", "DK", true, false, 20);
	    this.addFixedItem(FIELD, "organization", "Organización", true, false, 50);
		  this.addFixedItem(FIELD, "customer_trx_id", "Trx Id", true, false, 64);
	    
	    
	    this.addFixedItem(FIELD, "tipo_doc", "Tipo Doc", true, false, 40);
	    this.addFixedItem(VIRTUAL, "tipo_doc_punto", "Tipo Doc", true, false, 40);
	    this.addFixedItem(FIELD, "nro_doc", "Nro Doc", true, false, 40);
	    this.addFixedItem(FIELD, "fecha_doc", "Fecha Doc", true, false, 18);
	    this.addFixedItem(VIRTUAL, "fecha_doc_arabian", "Fecha Doc", true, false, 18);
	    this.addFixedItem(FIELD, "nro_boleto", "Nro Boleto", true, false, 40);
	    this.addFixedItem(FIELD, "agente", "Agente", true, false, 40);
	    this.addFixedItem(FIELD, "prestador", "Prestador", true, false, 400);
	    this.addFixedItem(FIELD, "ruta", "Ruta", true, false, 400);
	    this.addFixedItem(FIELD, "pasajero", "Pasajero", true, false, 400);
	    this.addFixedItem(FIELD, "reserva", "Reserva", true, false, 400);
	    this.addFixedItem(FIELD, "tarifa", "Tarifa", true, false, 18, 2);
	    this.addFixedItem(FIELD, "iva", "IVA", true, false, 18, 2);
	    this.addFixedItem(FIELD, "tua", "TUA", true, false, 18, 2);
	    this.addFixedItem(FIELD, "importe", "Importe", true, false, 18, 2);
	    this.addFixedItem(FIELD, "saldo", "Saldo", true, false, 18, 2);
	    this.addFixedItem(FIELD, "comision", "Comisión", true, false, 18, 2);
	    this.addFixedItem(FIELD, "porc_comision", "Porc. Comisión", true, false, 18, 2);
	    this.addFixedItem(FIELD, "tipo_servicio", "Tipo Servicio", true, false, 40);
	    this.addFixedItem(FIELD, "origen", "Origen", true, false, 20);
	    this.addFixedItem(FIELD, "clase", "Clase", true, false, 40);
	    this.addFixedItem(FIELD, "clases", "Clases", true, false, 40);
	    this.addFixedItem(FIELD, "lineas", "Líneas", true, false, 40);
	    this.addFixedItem(FIELD, "forma_pago", "Forma Pago", true, false, 40);
	    this.addFixedItem(FIELD, "porc_incentivo", "Porc. Incentivo", true, false, 18, 2);
	    this.addFixedItem(FIELD, "incentivo", "Incentivo", true, false, 18, 2);
	    this.addFixedItem(FIELD, "tipo", "Tipo", true, false, 20);
	    this.addFixedItem(FIELD, "interface_id", "Interface ID", true, false, 18);
	    this.addFixedItem(FIELD, "contado", "contado", true, false, 18, 2);
	    this.addFixedItem(FIELD, "tarjeta", "tarjeta", true, false, 18, 2);
	    this.addFixedItem(FIELD, "fiscal", "fiscal", true, false, 18, 2);
	    this.addFixedItem(FIELD, "nofiscal", "nofiscal", true, false, 18, 2);
	    this.addFixedItem(FIELD, "noDevengado", "noDevengado", true, false, 18, 2);
	    this.addFixedItem(VIRTUAL, "aereo_ticket", "Ticket", true, false, 50);
	    this.addFixedItem(VIRTUAL, "forma_pago_show", "Forma de pago", true, false, 50);
	    this.addFixedItem(VIRTUAL, "type_serv", "Tipo servicio", true, false, 50);
	    this.addFixedItem(VIRTUAL, "type_pasajero", "Pasajero", true, false, 50);
	    this.addFixedItem(VIRTUAL, "tarifa_yq", "Tarifa", true, false, 18, 2);
	    this.addFixedItem(VIRTUAL, "iva_tua", "Impuestos", true, false, 18, 2);
	    this.addFixedItem(VIRTUAL, "fuel", "Fuel", true, false, 18, 2);
	    this.addFixedItem(VIRTUAL, "base_tax_fuel", "base y fuel", true, false, 18, 2);
	    this.addFixedItem(VIRTUAL, "total_fee", "Total fee", true, false, 18, 2);
	    this.addFixedItem(VIRTUAL, "fee", "fee", true, false, 18, 2);
	    this.addFixedItem(VIRTUAL, "price_comision", "price_comision", true, false, 18, 2);
	    this.addFixedItem(VIRTUAL, "fecha_pnr", "Fecha", true, false, 18);
	    this.addFixedItem(VIRTUAL, "departure", "Departure", true, false, 18);
	    this.addFixedItem(VIRTUAL, "impuestos", "impuestos", true, false, 18,2);
	    this.addFixedItem(VIRTUAL, "net_price", "Neto", true, false, 18,2);
	    this.addFixedItem(VIRTUAL, "i3_total_local", "total local", true, false, 18,2);
	    this.addFixedItem(VIRTUAL, "i3_cotiz", "cotiz", true, false, 18,2);
	    this.addFixedItem(VIRTUAL, "i3_total", "Total", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "i3_fee", "fee", true, false, 18,2);
	    this.addFixedItem(VIRTUAL, "i3_total_usd", "Total usd", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "bsp_comision", "Comision bsp", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "bsp_comision_porc", "% Comision bsp", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "bsp_comision_iva", "Iva Comision bsp", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "bsp_credit", "Credit bsp", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "bsp_credit_tax", "Credit tax bsp", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "bsp_cash", "Cash bsp", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "bsp_cash_tax", "Cash tax bsp", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "bsp_comm", "Comm bsp", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "reissue", "Reissue", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "trnc", "TRNC", true, false, 18);	  	
	    this.addFixedItem(VIRTUAL, "as", "AS", true, false, 18);	  	
	    this.addFixedItem(VIRTUAL, "svc", "Svc", true, false, 18);	  	
	    this.addFixedItem(VIRTUAL, "note", "Note", true, false, 18);	  	
	    this.addFixedItem(VIRTUAL, "grand_total", "Grand total", true, false, 18,2);	  	
	    this.addFixedItem(VIRTUAL, "gds", "GDS", true, false, 20);	  	
	    this.addFixedItem(VIRTUAL, "sale_fl", "sale fl", true, false, 20);	  	
	    this.addFixedItem(VIRTUAL, "agencia_fl", "agencia_fl", true, false, 20);	  	
		}
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_liquidation_detail"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "linea",  zId ); 
    return read(); 
  } 
  public boolean readByBoleto(String company, String zBoleto ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "nro_boleto",  zBoleto ); 
      return read(); 
  } 
  BizLiqHeader objAcum;
  public BizLiqHeader getObjHeader() throws Exception {
  	if (objAcum!=null) return objAcum;
  	BizLiqHeader obj = new BizLiqHeader();
  	obj.dontThrowException(true);
  	if (!obj.read(getLiquidacionId()))
  		return null;
  	return objAcum=obj;
  }
  BizPNRTicket objTicket;
  public BizPNRTicket getObjTicket() throws Exception {
  	if (objTicket!=null) return objTicket;
  	BizPNRTicket obj = new BizPNRTicket();
  	obj.dontThrowException(true);
  	if (!obj.read(getInterfaceId())) {
  		obj.clearFilters();
  		obj.addFilter("company", getCompany());
  		obj.addFilter("NumeroBoleto", getNroBoleto());
  		if (!obj.read()) 
  	  	return null;
  	}
  	return objTicket=obj;
  }
  BizCotizacionDK objCotiz;
  public BizCotizacionDK getObjCotizacionDK() throws Exception {
  	if (objCotiz!=null) return objCotiz;
  	BizCotizacionDK obj = new BizCotizacionDK();
  	obj.dontThrowException(true);
  	if (!obj.ReadByDk(getCompany(),getDk(),getFechaDoc()))
  		return null;
  	return objCotiz=obj;
  }
  BizMexDetalle objBSP;
  public BizMexDetalle getObjBSP() throws Exception {
  	if (objBSP!=null) return objBSP;
  	BizMexDetalle obj = new BizMexDetalle();
  	obj.dontThrowException(true);
  	if (!obj.read(getCompany(),getNroBoleto()))
  		return null;
  	return objBSP=obj;
  }

	public String getAereoTicket() throws Exception {
		if (getObjTicket()==null) return "";
		return getObjTicket().getObjCarrier()==null?"0": getObjTicket().getObjCarrier().getCodIata()+"-"+getNroBoleto();
	};
	public String getFormaPagoShow() throws Exception {
		if (getFormaPago().equals("TR")) return "Cash";
		if (getFormaPago().startsWith("CC")) return "CC";
			return getFormaPago();
	};
	public String getTipoServ() throws Exception {
		if (getObjTicket()==null) return "";
		if (getTipoDoc().equals("RFN")) return "RFND";
		if (getObjTicket().getTipoOperacion().equals("ETR")) return "TKTT";
		if (getObjTicket().getTipoOperacion().equals("EMD")) return "EMD";
		return "ADM";
	};
	public String getTipoPasajero() throws Exception {
		if (getObjTicket()==null) return "";
		if (getObjTicket().getTipoPasajero().equals("ADT")) return "ADULT";
		if (getObjTicket().getTipoPasajero().equals("CHD")) return "CHILD";
		if (getObjTicket().getTipoPasajero().equals("INF")) return "INF";
		return getObjTicket().getTipoPasajero();
	};
	public double getTarifaYq() throws Exception {
		if (getObjTicket()==null) return 0.0;
		return getObjTicket().getTarifaFacturadaYQLocal();
	};
	public double getIvaTua() throws Exception {
		if (getObjTicket()==null) return 0.0;
		return getIva()+getTua()-getObjTicket().getYQ();
	};
	public double getFuel() throws Exception {
		if (getObjTicket()==null) return 0.0;
		return getObjTicket().getYQ();
	};
	public double getBaseTaxFuel() throws Exception {
		return getTarifaYq()+getIvaTua()+getFuel();
	};
	public double getPriceComision() throws Exception {
		return getBaseTaxFuel()-getComision();
	};
	public double getFee() throws Exception {
		BizFeeDK fee = new BizFeeDK();
		fee.dontThrowException(true);
		if (!fee.ReadByDk(getCompany(), this)) 
			return 0;
		return fee.getRealFee(getTarifa());
	};
	public double getTotalFee() throws Exception {
		if (getFormaPago().equals("TR")) return getBaseTaxFuel()+getComision()+getFee();
		return getComision()+getFee();
	};
	public Date getFechaPnr() throws Exception {
		if (getObjTicket()==null) return null;
		return getObjTicket().getCreationDate();
	};
	
	public Date getFechaDeparture() throws Exception {
		if (getObjTicket()==null) return null;
		return getObjTicket().getDepartureDate();
	};
	
	public double getNetPrice() throws Exception {
		return getImporte()-getComision() ;
	};
	public double getImpuestos() throws Exception {
		if (getObjTicket()==null) return 0.0;
		return getObjTicket().getImpuestoFactura() ;
	};
	public String getGDS() throws Exception {
		if (getObjTicket()==null) return "";
		return getObjTicket().getGDS() ;
	};
	
	public double getI3TotalLocal() throws Exception {
		if (getObjTicket()==null) return 0.0;
		return getImporte()+ getIvaTua() ;
	};
	
	public double getI3Cotiz() throws Exception {
		if (getObjCotizacionDK()==null) throw new Exception("No hay cotizacion para "+getDk()+" al "+getFechaDoc());
		return getObjCotizacionDK().getCotizacion();
	};
	
	public double getI3Fee() throws Exception {
		BizFeeDK fee = new BizFeeDK();
		fee.dontThrowException(true);
		if (!fee.ReadByDk(getCompany(), this)) 
			return 0;
		return fee.getRealFee(getI3Total());
	};
	
	public double getI3Total() throws Exception {
		return JTools.rd(getI3TotalLocal()/getI3Cotiz(),2);
	};
	public double getI3TotalUsd() throws Exception {
		return  getI3Total() + getI3Fee();
	};
	public double getComisionBSP() throws Exception {
		if (getObjBSP()==null) return 0;//throw new Exception("No hay BSP para la "+getNroBoleto());
		return getObjBSP().getEFCO();
	};
	public double getComisionPorcBSP() throws Exception {
		if (getObjBSP()==null) return 0;//throw new Exception("No hay BSP para la "+getNroBoleto());
		return getObjBSP().getEFRT();
	};
	public double getComisionIvaBSP() throws Exception {
		if (getObjBSP()==null) return 0;//throw new Exception("No hay BSP para la "+getNroBoleto());
		return getObjBSP().getTOCA();
	};
	public String getBSPTrnc() throws Exception {
		if (getObjBSP()==null) return "No hay BSP";//throw new Exception("No hay BSP para la "+getNroBoleto());
		return getObjBSP().getTRNC();
	};
	public double getGrandTotal() throws Exception {
		return getComisionBSP()-getFee();
	};
	public double getCreditBSP() throws Exception {
		if (getObjBSP()==null) return 0;//throw new Exception("No hay BSP para la "+getNroBoleto());
		return getObjBSP().getCRED();
	};
	public double getCashBSP() throws Exception {
		if (getObjBSP()==null) return 0;//throw new Exception("No hay BSP para la "+getNroBoleto());
		return getObjBSP().getCASH();
	};
	public double getCommBSP() throws Exception {
		if (getObjBSP()==null) return 0;//throw new Exception("No hay BSP para la "+getNroBoleto());
		return getObjBSP().getEFCO();
	};
	public double getCreditTaxBSP() throws Exception {
		if (getObjBSP()==null) return 0;//throw new Exception("No hay BSP para la "+getNroBoleto());
		return getObjBSP().getCRED()==0?0:getObjBSP().getTAX();
	};
	public double getCashTaxBSP() throws Exception {
		if (getObjBSP()==null) return 0;//throw new Exception("No hay BSP para la "+getNroBoleto());
		return  getObjBSP().getCRED()==0?getObjBSP().getTAX():0;
	}
	@Override
	public void setFormato(IFormato formato) {
		
	}
	public Date getDateValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.FECHA))
			return pFechaDoc.getValue();
		return null;
	}

	public Long getLongValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.ID_AEROLINEA))
			return null;
		return null;
	}

	public Double getDoubleValue(String field, String moneda, boolean check) throws Exception {
		if (moneda!=null&&moneda.equals("USD"))
			return getDoubleValueUSD(field, check);
		return getDoubleValueLocal(field, check);
			
	}
	
	public Double getDoubleValueUSD(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.COMISION))
			return null;
		if (field.equals(IConciliable.COMISION_OVER))
			return null;
		if (field.equals(IConciliable.COMISION_PORC))
			return null;
		if (field.equals(IConciliable.COMISION_INV))
			return null;
		if (field.equals(IConciliable.COMISION_OVER_INV))
			return null;
		if (field.equals(IConciliable.IMP_SOBRE_COMISION_INV))
			return null;
		if (field.equals(IConciliable.TOTAL))
			return null;
		if (field.equals(IConciliable.TOTALAPAGAR))
			return null;
		if (field.equals(IConciliable.BASE_IMPONIBLE))
			return null;
		if (field.equals(IConciliable.CONTADO))
			return null;
		if (field.equals(IConciliable.IMP_SOBRE_COMISION))
			return null;
		if (field.equals(IConciliable.IMPUESTO_1))
			return null;
		if (field.equals(IConciliable.IMPUESTO_2))
			return null;
		if (field.equals(IConciliable.IMPUESTO_ACUM))
			return null;
		if (field.equals(IConciliable.TARIFA))
			return null;
		if (field.equals(IConciliable.TARJETA))
			return null;
		if (field.equals(IConciliable.TARJETA_BRUTO))
			return null;
		if (field.equals(IConciliable.CONTADO_BRUTO))
			return null;
		if (field.equals(IConciliable.NETO))
			return null;
		if (field.equals(IConciliable.COMISION_ACUM))
			return null;
		// if (field.equals(IConciliable.COMISION_ACUM_INV)) return null;
		if (field.equals(IConciliable.TARIFA_SIN_COMISION))
			return null;
		return null;
	}	
	public Double getDoubleValueLocal(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.COMISION))
			return pComision.getValue();
		if (field.equals(IConciliable.COMISION_OVER))
			return null;
		if (field.equals(IConciliable.COMISION_PORC))
			return null;
		if (field.equals(IConciliable.COMISION_INV))
			return null;
		if (field.equals(IConciliable.COMISION_OVER_INV))
			return null;
		if (field.equals(IConciliable.IMP_SOBRE_COMISION_INV))
			return null;
		if (field.equals(IConciliable.TOTAL))
			return pImporte.getValue();
		if (field.equals(IConciliable.TOTALAPAGAR))
			return null;
		if (field.equals(IConciliable.BASE_IMPONIBLE))
			return null;
		if (field.equals(IConciliable.CONTADO))
			return pContado.getValue()-  (pContado.getValue()!=0?pIvaTua.getValue():0);
		if (field.equals(IConciliable.IMP_SOBRE_COMISION))
			return null;
		if (field.equals(IConciliable.IMPUESTO_1))
			return null;
		if (field.equals(IConciliable.IMPUESTO_2))
			return null;
		if (field.equals(IConciliable.IMPUESTO_ACUM))
			return pIvaTua.getValue();
		if (field.equals(IConciliable.TARIFA))
			return pTarifa.getValue() ;
		if (field.equals(IConciliable.TARJETA))
			return pTarjeta.getValue()-  (pTarjeta.getValue()!=0?pIvaTua.getValue():0);
		if (field.equals(IConciliable.TARJETA_BRUTO))
			return pTarjeta.getValue();;
		if (field.equals(IConciliable.CONTADO_BRUTO))
			return pContado.getValue();
		if (field.equals(IConciliable.NETO))
			return null;
		if (field.equals(IConciliable.COMISION_ACUM))
			return null;
		if (field.equals(IConciliable.TARIFA_SIN_COMISION))
			return null;
		return null;
	}

	public String getStringValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.BOLETOS))
			return pNroBoleto.getValue();
		if (field.equals(IConciliable.AEROLINEA_BOLETOS))
			return null;
		if (field.equals(IConciliable.BOLETOS_LARGO))
			return null;
		if (field.equals(IConciliable.AEROLINEA))
			return null;
		if (field.equals(IConciliable.ID_AEROLINEA))
			return pPrestador.getValue();
		if (field.equals(IConciliable.NUMERO_TARJETA))
			return null;
		if (field.equals(IConciliable.OBSERVACION))
			return null;
		if (field.equals(IConciliable.OPERACION))
			return pTipoDoc.getValue();
		if (field.equals(IConciliable.TIPO_RUTA))
			return pTipoServicio.getValue();
		if (field.equals(IConciliable.TIPO_TARJETA))
			return pFormaPago.getValue();
			
		return null;
	}
	@Override
	public String getSituation(IConciliable other, double precision) throws Exception {
		return null;
	}
	@Override
	public String getContrato() throws Exception {
		return null;
	};
}
