package pss.bsp.consolid.model.trxseg;

import java.util.Date;

import pss.bsp.consolidador.IConciliable;
import pss.bsp.parseo.IFormato;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;

public class BizArTrxDKSeg extends JRecord implements IConciliable {


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Declaración de variables
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private JLong pIdSegment = new JLong();
	private JString pPeriodoEmi = new JString();
	private JString pPeriodoFac = new JString();
	private JString pOriginCityCode = new JString();
	private JString pCiudadOrigen = new JString();
	private JString pArrivalCityCode = new JString();
	private JString pCiudadDestino = new JString();
	private JString pFechaRegreso = new JString();
	private JString pLineaAerea = new JString();
	private JString pClase = new JString();
	private JString pElapsedFlyingTime = new JString();
	private JString pFareBasisCodeExp = new JString();
	private JString pFlightNumber = new JString();
	private JString pProductCode = new JString();
	private JString pSecondaryProductCode = new JString();
	private JString pTerminalIdDeparture = new JString();
	private JString pFechaSalida = new JString();
	private JDate pFechaFactura = new JDate();
	private JLong pOrgId = new JLong();
	private JString pOrg = new JString();
	private JString pTipoServicio = new JString();
	private JString pTipoDocumento = new JString();
	private JString pFactura = new JString();
	private JString pMoneda = new JString();
	private JString pSolicito = new JString();
	private JString pFormaPago = new JString();
	private JFloat pTipoCambio = new JFloat();
	private JString pUnidadOperativa = new JString();
	private JString pFechaCreacionPnr = new JString();
	private JString pPnr = new JString();
	private JString pIata = new JString();
	private JString pGds = new JString();
	private JString pPccEmite = new JString();
	private JString pAgenteEmite = new JString();
	private JString pServicio = new JString();
	private JFloat pPrecioUnitario = new JFloat();
	private JFloat pImporte = new JFloat();
	private JFloat pTotalFactura = new JFloat();
	private JString pPasajero = new JString();
	private JString pRuta = new JString();
	private JString pBoleto = new JString();
	private JFloat pTarifa = new JFloat();
	private JFloat pIva = new JFloat();
	private JFloat pTua = new JFloat();
	private JFloat pTotal = new JFloat();
	private JString pBoletoRevisado = new JString();
	private JString pBoletoConjunto = new JString();
	private JString pProveedor = new JString();
	private JString pCliente = new JString();
	private JString pDk = new JString();
	private JLong pIdCliente = new JLong();
	private JString pTerminoPago = new JString();
	private JString pCci = new JString();
	private JString pElaboradoPor = new JString();
	private JString pDescripcion = new JString();
	private JDate pFechaEmision = new JDate();
	private JDate pCreacionFactura = new JDate();
	private JString pArchivoOrigen = new JString();
	private JString pReferencia = new JString();

	public void setIdSegment(Long zValue) throws Exception { pIdSegment.setValue(zValue); }
	public Long getIdSegment() throws Exception { return pIdSegment.getValue(); }

	public void setPeriodoEmi(String zValue) throws Exception { pPeriodoEmi.setValue(zValue); }
	public String getPeriodoEmi() throws Exception { return pPeriodoEmi.getValue(); }

	public void setPeriodoFac(String zValue) throws Exception { pPeriodoFac.setValue(zValue); }
	public String getPeriodoFac() throws Exception { return pPeriodoFac.getValue(); }

	public void setOriginCityCode(String zValue) throws Exception { pOriginCityCode.setValue(zValue); }
	public String getOriginCityCode() throws Exception { return pOriginCityCode.getValue(); }

	public void setCiudadOrigen(String zValue) throws Exception { pCiudadOrigen.setValue(zValue); }
	public String getCiudadOrigen() throws Exception { return pCiudadOrigen.getValue(); }

	public void setArrivalCityCode(String zValue) throws Exception { pArrivalCityCode.setValue(zValue); }
	public String getArrivalCityCode() throws Exception { return pArrivalCityCode.getValue(); }

	public void setCiudadDestino(String zValue) throws Exception { pCiudadDestino.setValue(zValue); }
	public String getCiudadDestino() throws Exception { return pCiudadDestino.getValue(); }

	public void setFechaRegreso(String zValue) throws Exception { pFechaRegreso.setValue(zValue); }
	public String getFechaRegreso() throws Exception { return pFechaRegreso.getValue(); }

	public void setLineaAerea(String zValue) throws Exception { pLineaAerea.setValue(zValue); }
	public String getLineaAerea() throws Exception { return pLineaAerea.getValue(); }

	public void setClase(String zValue) throws Exception { pClase.setValue(zValue); }
	public String getClase() throws Exception { return pClase.getValue(); }

	public void setElapsedFlyingTime(String zValue) throws Exception { pElapsedFlyingTime.setValue(zValue); }
	public String getElapsedFlyingTime() throws Exception { return pElapsedFlyingTime.getValue(); }

	public void setFareBasisCodeExp(String zValue) throws Exception { pFareBasisCodeExp.setValue(zValue); }
	public String getFareBasisCodeExp() throws Exception { return pFareBasisCodeExp.getValue(); }

	public void setFlightNumber(String zValue) throws Exception { pFlightNumber.setValue(zValue); }
	public String getFlightNumber() throws Exception { return pFlightNumber.getValue(); }

	public void setProductCode(String zValue) throws Exception { pProductCode.setValue(zValue); }
	public String getProductCode() throws Exception { return pProductCode.getValue(); }

	public void setSecondaryProductCode(String zValue) throws Exception { pSecondaryProductCode.setValue(zValue); }
	public String getSecondaryProductCode() throws Exception { return pSecondaryProductCode.getValue(); }

	public void setTerminalIdDeparture(String zValue) throws Exception { pTerminalIdDeparture.setValue(zValue); }
	public String getTerminalIdDeparture() throws Exception { return pTerminalIdDeparture.getValue(); }

	public void setFechaSalida(String zValue) throws Exception { pFechaSalida.setValue(zValue); }
	public String getFechaSalida() throws Exception { return pFechaSalida.getValue(); }

	public void setFechaFactura(Date zValue) throws Exception { pFechaFactura.setValue(zValue); }
	public Date getFechaFactura() throws Exception { return pFechaFactura.getValue(); }

	public void setOrgId(Long zValue) throws Exception { pOrgId.setValue(zValue); }
	public Long getOrgId() throws Exception { return pOrgId.getValue(); }

	public void setOrg(String zValue) throws Exception { pOrg.setValue(zValue); }
	public String getOrg() throws Exception { return pOrg.getValue(); }

	public void setTipoServicio(String zValue) throws Exception { pTipoServicio.setValue(zValue); }
	public String getTipoServicio() throws Exception { return pTipoServicio.getValue(); }

	public void setTipoDocumento(String zValue) throws Exception { pTipoDocumento.setValue(zValue); }
	public String getTipoDocumento() throws Exception { return pTipoDocumento.getValue(); }

	public void setFactura(String zValue) throws Exception { pFactura.setValue(zValue); }
	public String getFactura() throws Exception { return pFactura.getValue(); }

	public void setMoneda(String zValue) throws Exception { pMoneda.setValue(zValue); }
	public String getMoneda() throws Exception { return pMoneda.getValue(); }

	public void setSolicito(String zValue) throws Exception { pSolicito.setValue(zValue); }
	public String getSolicito() throws Exception { return pSolicito.getValue(); }

	public void setFormaPago(String zValue) throws Exception { pFormaPago.setValue(zValue); }
	public String getFormaPago() throws Exception { return pFormaPago.getValue(); }

	public void setTipoCambio(double zValue) throws Exception { pTipoCambio.setValue(zValue); }
	public double getTipoCambio() throws Exception { return pTipoCambio.getValue(); }

	public void setUnidadOperativa(String zValue) throws Exception { pUnidadOperativa.setValue(zValue); }
	public String getUnidadOperativa() throws Exception { return pUnidadOperativa.getValue(); }

	public void setFechaCreacionPnr(String zValue) throws Exception { pFechaCreacionPnr.setValue(zValue); }
	public String getFechaCreacionPnr() throws Exception { return pFechaCreacionPnr.getValue(); }

	public void setPnr(String zValue) throws Exception { pPnr.setValue(zValue); }
	public String getPnr() throws Exception { return pPnr.getValue(); }

	public void setIata(String zValue) throws Exception { pIata.setValue(zValue); }
	public String getIata() throws Exception { return pIata.getValue(); }

	public void setGds(String zValue) throws Exception { pGds.setValue(zValue); }
	public String getGds() throws Exception { return pGds.getValue(); }

	public void setPccEmite(String zValue) throws Exception { pPccEmite.setValue(zValue); }
	public String getPccEmite() throws Exception { return pPccEmite.getValue(); }

	public void setAgenteEmite(String zValue) throws Exception { pAgenteEmite.setValue(zValue); }
	public String getAgenteEmite() throws Exception { return pAgenteEmite.getValue(); }

	public void setServicio(String zValue) throws Exception { pServicio.setValue(zValue); }
	public String getServicio() throws Exception { return pServicio.getValue(); }

	public void setPrecioUnitario(double zValue) throws Exception { pPrecioUnitario.setValue(zValue); }
	public double getPrecioUnitario() throws Exception { return pPrecioUnitario.getValue(); }

	public void setImporte(double zValue) throws Exception { pImporte.setValue(zValue); }
	public double getImporte() throws Exception { return pImporte.getValue(); }

	public void setTotalFactura(double zValue) throws Exception { pTotalFactura.setValue(zValue); }
	public double getTotalFactura() throws Exception { return pTotalFactura.getValue(); }

	public void setPasajero(String zValue) throws Exception { pPasajero.setValue(zValue); }
	public String getPasajero() throws Exception { return pPasajero.getValue(); }

	public void setRuta(String zValue) throws Exception { pRuta.setValue(zValue); }
	public String getRuta() throws Exception { return pRuta.getValue(); }

	public void setBoleto(String zValue) throws Exception { pBoleto.setValue(zValue); }
	public String getBoleto() throws Exception { return pBoleto.getValue(); }

	public void setTarifa(double zValue) throws Exception { pTarifa.setValue(zValue); }
	public double getTarifa() throws Exception { return pTarifa.getValue(); }

	public void setIva(double zValue) throws Exception { pIva.setValue(zValue); }
	public double getIva() throws Exception { return pIva.getValue(); }

	public void setTua(double zValue) throws Exception { pTua.setValue(zValue); }
	public double getTua() throws Exception { return pTua.getValue(); }

	public void setTotal(double zValue) throws Exception { pTotal.setValue(zValue); }
	public double getTotal() throws Exception { return pTotal.getValue(); }

	public void setBoletoRevisado(String zValue) throws Exception { pBoletoRevisado.setValue(zValue); }
	public String getBoletoRevisado() throws Exception { return pBoletoRevisado.getValue(); }

	public void setBoletoConjunto(String zValue) throws Exception { pBoletoConjunto.setValue(zValue); }
	public String getBoletoConjunto() throws Exception { return pBoletoConjunto.getValue(); }

	public void setProveedor(String zValue) throws Exception { pProveedor.setValue(zValue); }
	public String getProveedor() throws Exception { return pProveedor.getValue(); }

	public void setCliente(String zValue) throws Exception { pCliente.setValue(zValue); }
	public String getCliente() throws Exception { return pCliente.getValue(); }

	public void setDk(String zValue) throws Exception { pDk.setValue(zValue); }
	public String getDk() throws Exception { return pDk.getValue(); }

	public void setIdCliente(Long zValue) throws Exception { pIdCliente.setValue(zValue); }
	public Long getIdCliente() throws Exception { return pIdCliente.getValue(); }

	public void setTerminoPago(String zValue) throws Exception { pTerminoPago.setValue(zValue); }
	public String getTerminoPago() throws Exception { return pTerminoPago.getValue(); }

	public void setCci(String zValue) throws Exception { pCci.setValue(zValue); }
	public String getCci() throws Exception { return pCci.getValue(); }

	public void setElaboradoPor(String zValue) throws Exception { pElaboradoPor.setValue(zValue); }
	public String getElaboradoPor() throws Exception { return pElaboradoPor.getValue(); }

	public void setDescripcion(String zValue) throws Exception { pDescripcion.setValue(zValue); }
	public String getDescripcion() throws Exception { return pDescripcion.getValue(); }

	public void setFechaEmision(Date zValue) throws Exception { pFechaEmision.setValue(zValue); }
	public Date getFechaEmision() throws Exception { return pFechaEmision.getValue(); }

	public void setCreacionFactura(Date zValue) throws Exception { pCreacionFactura.setValue(zValue); }
	public Date getCreacionFactura() throws Exception { return pCreacionFactura.getValue(); }

	public void setArchivoOrigen(String zValue) throws Exception { pArchivoOrigen.setValue(zValue); }
	public String getArchivoOrigen() throws Exception { return pArchivoOrigen.getValue(); }

	public void setReferencia(String zValue) throws Exception { pReferencia.setValue(zValue); }
	public String getReferencia() throws Exception { return pReferencia.getValue(); }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Constructor de la clase
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BizArTrxDKSeg() throws Exception {
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Métodos para crear propiedades
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void createProperties() throws Exception {
		this.addItem("ID_SEGMENT", pIdSegment);
		this.addItem("periodo_emi", pPeriodoEmi);
		this.addItem("periodo_fac", pPeriodoFac);
		this.addItem("origin_city_code", pOriginCityCode);
		this.addItem("ciudad_origen", pCiudadOrigen);
		this.addItem("arrival_city_code", pArrivalCityCode);
		this.addItem("ciudad_destino", pCiudadDestino);
		this.addItem("fecha_regreso", pFechaRegreso);
		this.addItem("linea_aerea", pLineaAerea);
		this.addItem("clase", pClase);
		this.addItem("elapsed_flying_time", pElapsedFlyingTime);
		this.addItem("fare_basis_code_exp", pFareBasisCodeExp);
		this.addItem("flight_number", pFlightNumber);
		this.addItem("product_code", pProductCode);
		this.addItem("secondary_product_code", pSecondaryProductCode);
		this.addItem("terminal_id_departure", pTerminalIdDeparture);
		this.addItem("fecha_salida", pFechaSalida);
		this.addItem("fecha_factura", pFechaFactura);
		this.addItem("org_id", pOrgId);
		this.addItem("org", pOrg);
		this.addItem("tipo_servicio", pTipoServicio);
		this.addItem("tipo_documento", pTipoDocumento);
		this.addItem("factura", pFactura);
		this.addItem("moneda", pMoneda);
		this.addItem("solicito", pSolicito);
		this.addItem("forma_pago", pFormaPago);
		this.addItem("tipo_cambio", pTipoCambio);
		this.addItem("unidad_operativa", pUnidadOperativa);
		this.addItem("fecha_creacion_pnr", pFechaCreacionPnr);
		this.addItem("pnr", pPnr);
		this.addItem("iata", pIata);
		this.addItem("gds", pGds);
		this.addItem("pcc_emite", pPccEmite);
		this.addItem("agente_emite", pAgenteEmite);
		this.addItem("servicio", pServicio);
		this.addItem("precio_unitario", pPrecioUnitario);
		this.addItem("importe", pImporte);
		this.addItem("total_factura", pTotalFactura);
		this.addItem("pasajero", pPasajero);
		this.addItem("ruta", pRuta);
		this.addItem("boleto", pBoleto);
		this.addItem("tarifa", pTarifa);
		this.addItem("iva", pIva);
		this.addItem("tua", pTua);
		this.addItem("total", pTotal);
		this.addItem("boleto_revisado", pBoletoRevisado);
		this.addItem("boleto_conjunto", pBoletoConjunto);
		this.addItem("proveedor", pProveedor);
		this.addItem("cliente", pCliente);
		this.addItem("dk", pDk);
		this.addItem("id_cliente", pIdCliente);
		this.addItem("termino_pago", pTerminoPago);
		this.addItem("cci", pCci);
		this.addItem("elaborado_por", pElaboradoPor);
		this.addItem("descripcion", pDescripcion);
		this.addItem("fecha_emision", pFechaEmision);
		this.addItem("creacion_factura", pCreacionFactura);
		this.addItem("archivo_origen", pArchivoOrigen);
		this.addItem("referencia", pReferencia);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Métodos para crear propiedades fijas
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_segment", "segmento", true, false, 150);
		this.addFixedItem(KEY, "boleto", "Boleto", true, false, 150);
		this.addFixedItem(FIELD, "periodo_emi", "Periodo Emision", true, false, 150);
		this.addFixedItem(FIELD, "periodo_fac", "Periodo Facturacion", true, false, 150);
		this.addFixedItem(FIELD, "origin_city_code", "Codigo Ciudad Origen", true, false, 50);
		this.addFixedItem(FIELD, "ciudad_origen", "Ciudad Origen", true, false, 500);
		this.addFixedItem(FIELD, "arrival_city_code", "Codigo Ciudad Destino", true, false, 50);
		this.addFixedItem(FIELD, "ciudad_destino", "Ciudad Destino", true, false, 500);
		this.addFixedItem(FIELD, "fecha_regreso", "Fecha Regreso", true, false, 50);
		this.addFixedItem(FIELD, "linea_aerea", "Linea Aerea", true, false, 150);
		this.addFixedItem(FIELD, "clase", "Clase", true, false, 150);
		this.addFixedItem(FIELD, "elapsed_flying_time", "Tiempo Vuelo", true, false, 80);
		this.addFixedItem(FIELD, "fare_basis_code_exp", "Descripcion Tarifa", true, false, 120);
		this.addFixedItem(FIELD, "flight_number", "Numero Vuelo", true, false, 70);
		this.addFixedItem(FIELD, "product_code", "Codigo Producto", true, false, 30);
		this.addFixedItem(FIELD, "secondary_product_code", "Codigo Secundario Producto", true, false, 30);
		this.addFixedItem(FIELD, "terminal_id_departure", "Terminal Salida", true, false, 200);
		this.addFixedItem(FIELD, "fecha_salida", "Fecha Salida", true, false, 50);
		this.addFixedItem(FIELD, "fecha_factura", "Fecha Factura", true, false, 7);
		this.addFixedItem(FIELD, "org_id", "ID Organizacion", true, false, 22);
		this.addFixedItem(FIELD, "org", "Organizacion", true, false, 150);
		this.addFixedItem(FIELD, "tipo_servicio", "Tipo Servicio", true, false, 50);
		this.addFixedItem(FIELD, "tipo_documento", "Tipo Documento", true, false, 50);
		this.addFixedItem(FIELD, "factura", "Factura", true, false, 20);
		this.addFixedItem(FIELD, "moneda", "Moneda", true, false, 15);
		this.addFixedItem(FIELD, "solicito", "Solicito", true, false, 150);
		this.addFixedItem(FIELD, "forma_pago", "Forma Pago", true, false, 150);
		this.addFixedItem(FIELD, "tipo_cambio", "Tipo Cambio", true, false, 22);
		this.addFixedItem(FIELD, "unidad_operativa", "Unidad Operativa", true, false, 150);
		this.addFixedItem(FIELD, "fecha_creacion_pnr", "Fecha Creacion PNR", true, false, 50);
		this.addFixedItem(FIELD, "pnr", "PNR", true, false, 150);
		this.addFixedItem(FIELD, "iata", "IATA", true, false, 150);
		this.addFixedItem(FIELD, "gds", "GDS", true, false, 150);
		this.addFixedItem(FIELD, "pcc_emite", "PCC Emite", true, false, 150);
		this.addFixedItem(FIELD, "agente_emite", "Agente Emite", true, false, 150);
		this.addFixedItem(FIELD, "servicio", "Servicio", true, false, 240);
		this.addFixedItem(FIELD, "precio_unitario", "Precio Unitario", true, false, 22);
		this.addFixedItem(FIELD, "importe", "Importe", true, false, 22);
		this.addFixedItem(FIELD, "total_factura", "Total Factura", true, false, 22);
		this.addFixedItem(FIELD, "pasajero", "Pasajero", true, false, 150);
		this.addFixedItem(FIELD, "ruta", "Ruta", true, false, 150);
		this.addFixedItem(FIELD, "tarifa", "Tarifa", true, false, 22);
		this.addFixedItem(FIELD, "iva", "IVA", true, false, 22);
		this.addFixedItem(FIELD, "tua", "TUA", true, false, 22);
		this.addFixedItem(FIELD, "total", "Total", true, false, 22);
		this.addFixedItem(FIELD, "boleto_revisado", "Boleto Revisado", true, false, 150);
		this.addFixedItem(FIELD, "boleto_conjunto", "Boleto Conjunto", true, false, 150);
		this.addFixedItem(FIELD, "proveedor", "Proveedor", true, false, 150);
		this.addFixedItem(FIELD, "cliente", "Cliente", true, false, 150);
		this.addFixedItem(FIELD, "dk", "DK", true, false, 30);
		this.addFixedItem(FIELD, "id_cliente", "ID Cliente", true, false, 22);
		this.addFixedItem(FIELD, "termino_pago", "Termino Pago", true, false, 15);
		this.addFixedItem(FIELD, "cci", "CCI", true, false, 25);
		this.addFixedItem(FIELD, "elaborado_por", "Elaborado Por", true, false, 150);
		this.addFixedItem(FIELD, "descripcion", "Descripcion", true, false, 2000);
		this.addFixedItem(FIELD, "fecha_emision", "Fecha Emision", true, false, 7);
		this.addFixedItem(FIELD, "creacion_factura", "Creacion Factura", true, false, 7);
		this.addFixedItem(FIELD, "archivo_origen", "Archivo Origen", true, false, 150);
		this.addFixedItem(FIELD, "referencia", "Referencia", true, false, 300);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "apps.CNS_TICKET_MINING_V";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long zId) throws Exception {
		addFilter("transaccion_id", zId);
		return read();
	}

	public Date getDateValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.FECHA))
//			return JDateTools.StringToDate(pFechaCreacionPnr.getValue(),"dd-MMM-YYYY");
			return pFechaEmision.getValue();
		return null;
	}

	public Long getLongValue(String field, boolean check) throws Exception {
//		if (field.equals(IConciliable.ID_AEROLINEA))
//			return pAerolinea.getValue();
		return null;
	}

	public Double getDoubleValue(String field, String moneda, boolean check) throws Exception {
		if (moneda != null && moneda.equals("USD"))
			return getDoubleValueUSD(field, check);
		return getDoubleValueLocal(field, check);

	}

	public Double getDoubleValueUSD(String field, boolean check) throws Exception {
//		if (field.equals(IConciliable.COMISION))
//			return null;
//		if (field.equals(IConciliable.COMISION_OVER))
//			return null;
//		if (field.equals(IConciliable.COMISION_PORC))
//			return null;
//		if (field.equals(IConciliable.COMISION_INV))
//			return null;
//		if (field.equals(IConciliable.COMISION_OVER_INV))
//			return null;
//		if (field.equals(IConciliable.IMP_SOBRE_COMISION_INV))
//			return null;
		if (field.equals(IConciliable.TOTAL))
			return new Double(pTotalFactura.getValue());
		if (field.equals(IConciliable.TOTALAPAGAR))
			return new Double(pTotal.getValue());;
		if (field.equals(IConciliable.BASE_IMPONIBLE))
			return new Double(pTarifa.getValue());;
//		if (field.equals(IConciliable.CONTADO))
//			return null;
//		if (field.equals(IConciliable.IMP_SOBRE_COMISION))
//			return null;
//		if (field.equals(IConciliable.IMPUESTO_1))
//			return null;
//		if (field.equals(IConciliable.IMPUESTO_2))
//			return null;
//		if (field.equals(IConciliable.IMPUESTO_ACUM))
//			return JTools.rd(pTarifaFacturaConImpuestosDolares.getValue() - pTarifaFacturaDolares.getValue(), 2);
//		if (field.equals(IConciliable.TARIFA))
//			return pTarifaFacturaDolares.getValue();
//		if (field.equals(IConciliable.TARJETA))
//			return null;
//		if (field.equals(IConciliable.TARJETA_BRUTO))
//			return null;
//		if (field.equals(IConciliable.CONTADO_BRUTO))
//			return null;
//		if (field.equals(IConciliable.NETO))
//			return null;
//		if (field.equals(IConciliable.COMISION_ACUM))
//			return null;
//		// if (field.equals(IConciliable.COMISION_ACUM_INV)) return null;
//		if (field.equals(IConciliable.TARIFA_SIN_COMISION))
//			return null;
		return null;
	}

	public Double getDoubleValueLocal(String field, boolean check) throws Exception {
//		if (field.equals(IConciliable.COMISION))
//			return pComisionFactura.getValue();
//		if (field.equals(IConciliable.COMISION_OVER))
//			return null;//pImporteover.getValue();
//		if (field.equals(IConciliable.COMISION_PORC))
//			return pComisionPerc.getValue();
//		if (field.equals(IConciliable.COMISION_INV))
//			return -pComisionFactura.getValue();
//		if (field.equals(IConciliable.COMISION_OVER_INV))
//			return null;//-pImporteover.getValue();
//		if (field.equals(IConciliable.IMP_SOBRE_COMISION_INV))
//			return null;
		if (field.equals(IConciliable.TOTAL))
			return new Double(pTotalFactura.getValue());
		if (field.equals(IConciliable.TOTALAPAGAR))
			return new Double(pTotal.getValue());;
		if (field.equals(IConciliable.BASE_IMPONIBLE))
			return new Double(pTarifa.getValue());;
//		if (field.equals(IConciliable.TOTALAPAGAR))
//			return null;
//		if (field.equals(IConciliable.BASE_IMPONIBLE))
//			return null;
//		if (field.equals(IConciliable.CONTADO))
//			return null;
//		if (field.equals(IConciliable.IMP_SOBRE_COMISION))
//			return null;
//		if (field.equals(IConciliable.IMPUESTO_1))
//			return null;
//		if (field.equals(IConciliable.IMPUESTO_2))
//			return null;
//		if (field.equals(IConciliable.IMPUESTO_ACUM))
//			return JTools.rd(pTarifaFacturaConImpuestos.getValue() - pTarifaFactura.getValue(), 2);
//		if (field.equals(IConciliable.TARIFA))
//			return pTarifaFactura.getValue();
//		if (field.equals(IConciliable.TARJETA))
//			return null;
//		if (field.equals(IConciliable.TARJETA_BRUTO))
//			return null;
//		if (field.equals(IConciliable.CONTADO_BRUTO))
//			return null;
//		if (field.equals(IConciliable.NETO))
//			return null;
//		if (field.equals(IConciliable.COMISION_ACUM))
//			return null;
//		// if (field.equals(IConciliable.COMISION_ACUM_INV)) return null;
//		if (field.equals(IConciliable.TARIFA_SIN_COMISION))
//			return null;
		return null;
	}

	public String getStringValue(String field, boolean check) throws Exception {
		if (field.equalsIgnoreCase(IConciliable.BOLETOS))
			return pBoleto.getValue();
		if (field.equalsIgnoreCase(IConciliable.BOLETOS_LARGO))
			return check ? null : pLineaAerea.getValue() + " " + pBoleto.getValue();
		;
		if (field.equalsIgnoreCase(IConciliable.AEROLINEA))
			return check ? null : pLineaAerea.getValue();
		if (field.equalsIgnoreCase(IConciliable.AEROLINEA_BOLETOS))
			return check ? null : pLineaAerea.getValue() + " " + pBoleto.getValue();
		if (field.equalsIgnoreCase(IConciliable.ID_AEROLINEA))
			return check ? null : pLineaAerea.getValue();
//		if (field.equalsIgnoreCase(IConciliable.NUMERO_TARJETA))
//			return check ? null : pTcNum.getValue();
//		if (field.equalsIgnoreCase(IConciliable.OBSERVACION))
//			return check ? null : pComentarios.getValue();
		
		if (field.equalsIgnoreCase(IConciliable.OPERACION))
			return check ? null : pTipoDocumento.getValue();
		if (field.equalsIgnoreCase(IConciliable.TIPO_RUTA))
			return null;
		if (field.equalsIgnoreCase(IConciliable.TIPO_TARJETA))
			return check ? null : pFormaPago.getValue();
		;
		if (field.equalsIgnoreCase(IConciliable.CLIENTE))
			return check ? null : pDk.getValue();
		;
		if (field.equalsIgnoreCase(IConciliable.FECHA))
			return check ? null : JDateTools.DateToString(pFechaEmision.getValue(), "dd/MM/YYYY");
		if (field.equalsIgnoreCase(IConciliable.TARIFA))
			return check ? null : "" + pTarifa.getValue();
		if (field.equalsIgnoreCase(IConciliable.BASE_IMPONIBLE))
			return check ? null : "" + pTarifa.getValue();
		if (field.equalsIgnoreCase(IConciliable.NETO))
			return check ? null : "" + pTarifa.getValue();
		if (field.equalsIgnoreCase(IConciliable.IMPUESTO_1))
			return check ? null : "" + pIva.getValue();
		if (field.equalsIgnoreCase(IConciliable.IMPUESTO_2))
			return check ? null : "" + pTua.getValue();
		if (field.equalsIgnoreCase(IConciliable.CONTADO))
			return check ? null : !pFormaPago.getValue().equals("CCPD") ? "0.00" : "" + pTotalFactura.getValue();
		if (field.equalsIgnoreCase(IConciliable.TARJETA))
			return check ? null : pFormaPago.getValue().equals("CCPD") ? "0.00" : "" + pTotalFactura.getValue();

		return null;
	}

	@Override
	public void setFormato(IFormato formato) {

	}
	@Override
	public String getSituation(IConciliable other, double precision) throws Exception {
			return "";
	}

	@Override
	public String getContrato() throws Exception {
		return "";
	}
}
