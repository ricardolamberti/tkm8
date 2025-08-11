package pss.bsp.consolid.model.trx;

import java.util.Date;

import pss.bsp.consolidador.IConciliable;
import pss.bsp.parseo.IFormato;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;

public class BizArTrxDK extends JRecord implements IConciliable {

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Declaración de variables
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private JLong pTransaccionId = new JLong();
	private JLong pTrxLineId = new JLong();
	private JLong pTrxLinkId = new JLong();
	private JDate pFechaCreacion = new JDate();
	private JString pTransaccionNum = new JString();
	private JString pImprimir = new JString();
	private JString pMoneda = new JString();
	private JString pSolicitante = new JString();
	private JString pFormaPago = new JString();
	private JString pTcNum = new JString();
	private JString pCorreo = new JString();
	private JString pTipoCambio = new JString();
	private JString pUnidadOperativa = new JString();
	private JString pFechaCupon = new JString();
	private JString pFinalizar = new JString();
	private JString pReservaId = new JString();
	private JString pIata = new JString();
	private JString pGds = new JString();
	private JString pPccEmite = new JString();
	private JString pAgenteEmite = new JString();
	private JString pArchivo = new JString();
	private JString pOrganizacion = new JString();
	private JString pTipoServicio = new JString();
	private JLong pCantidad = new JLong();
	private JFloat pPrecioUnitario = new JFloat();
	private JString pPasajero = new JString();
	private JString pRuta = new JString();
	private JString pAerolinea = new JString();
	private JString pBoleto = new JString();
	private JString pFechaHoraSalida = new JString();
	private JString pClase = new JString();
	private JString pTarifaBase = new JString();
	private JString pIva = new JString();
	private JString pTua = new JString();
	private JFloat pMonto = new JFloat();
	private JString pBoletoRevisado = new JString();
	private JString pDocumentoTipo = new JString();
	private JString pFechaHoraRegreso = new JString();
	private JString pCdOrigen = new JString();
	private JString pCdDestino = new JString();
	private JString pBoletoConjunto = new JString();
	private JFloat pTasaIva = new JFloat();
	private JString pClasIva = new JString();
	private JString pFormaPagoLinea = new JString();
	private JString pTcLinea = new JString();
	private JString pDk = new JString();
	private JString pNombreCliente = new JString();
	private JString pPccReserva = new JString();
	private JString pIdBranch = new JString();
	private JString pComentarios = new JString();
	private JLong pUltimaste = new JLong();
	private JString pDkAgente = new JString();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Constructor de la clase
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BizArTrxDK() throws Exception {
	}

	
	public void setTransaccionId(long zValue) throws Exception { pTransaccionId.setValue(zValue); }
	public long getTransaccionId() throws Exception { return pTransaccionId.getValue(); }

	public void setTrxLineId(long zValue) throws Exception { pTrxLineId.setValue(zValue); }
	public long getTrxLineId() throws Exception { return pTrxLineId.getValue(); }

	public void setTrxLinkId(long zValue) throws Exception { pTrxLinkId.setValue(zValue); }
	public long getTrxLinkId() throws Exception { return pTrxLinkId.getValue(); }

	public void setFechaCreacion(Date zValue) throws Exception { pFechaCreacion.setValue(zValue); }
	public Date getFechaCreacion() throws Exception { return pFechaCreacion.getValue(); }

	public void setTransaccionNum(String zValue) throws Exception { pTransaccionNum.setValue(zValue); }
	public String getTransaccionNum() throws Exception { return pTransaccionNum.getValue(); }

	public void setImprimir(String zValue) throws Exception { pImprimir.setValue(zValue); }
	public String getImprimir() throws Exception { return pImprimir.getValue(); }

	public void setMoneda(String zValue) throws Exception { pMoneda.setValue(zValue); }
	public String getMoneda() throws Exception { return pMoneda.getValue(); }

	public void setSolicitante(String zValue) throws Exception { pSolicitante.setValue(zValue); }
	public String getSolicitante() throws Exception { return pSolicitante.getValue(); }

	public void setFormaPago(String zValue) throws Exception { pFormaPago.setValue(zValue); }
	public String getFormaPago() throws Exception { return pFormaPago.getValue(); }

	public void setTcNum(String zValue) throws Exception { pTcNum.setValue(zValue); }
	public String getTcNum() throws Exception { return pTcNum.getValue(); }

	public void setCorreo(String zValue) throws Exception { pCorreo.setValue(zValue); }
	public String getCorreo() throws Exception { return pCorreo.getValue(); }

	public void setTipoCambio(String zValue) throws Exception { pTipoCambio.setValue(zValue); }
	public String getTipoCambio() throws Exception { return pTipoCambio.getValue(); }

	public void setUnidadOperativa(String zValue) throws Exception { pUnidadOperativa.setValue(zValue); }
	public String getUnidadOperativa() throws Exception { return pUnidadOperativa.getValue(); }

	public void setFechaCupon(String zValue) throws Exception { pFechaCupon.setValue(zValue); }
	public String getFechaCupon() throws Exception { return pFechaCupon.getValue(); }

	public void setFinalizar(String zValue) throws Exception { pFinalizar.setValue(zValue); }
	public String getFinalizar() throws Exception { return pFinalizar.getValue(); }

	public void setReservaId(String zValue) throws Exception { pReservaId.setValue(zValue); }
	public String getReservaId() throws Exception { return pReservaId.getValue(); }

	public void setIata(String zValue) throws Exception { pIata.setValue(zValue); }
	public String getIata() throws Exception { return pIata.getValue(); }

	public void setGds(String zValue) throws Exception { pGds.setValue(zValue); }
	public String getGds() throws Exception { return pGds.getValue(); }

	public void setPccEmite(String zValue) throws Exception { pPccEmite.setValue(zValue); }
	public String getPccEmite() throws Exception { return pPccEmite.getValue(); }

	public void setAgenteEmite(String zValue) throws Exception { pAgenteEmite.setValue(zValue); }
	public String getAgenteEmite() throws Exception { return pAgenteEmite.getValue(); }

	public void setArchivo(String zValue) throws Exception { pArchivo.setValue(zValue); }
	public String getArchivo() throws Exception { return pArchivo.getValue(); }

	public void setOrganizacion(String zValue) throws Exception { pOrganizacion.setValue(zValue); }
	public String getOrganizacion() throws Exception { return pOrganizacion.getValue(); }

	public void setTipoServicio(String zValue) throws Exception { pTipoServicio.setValue(zValue); }
	public String getTipoServicio() throws Exception { return pTipoServicio.getValue(); }

	public void setCantidad(long zValue) throws Exception { pCantidad.setValue(zValue); }
	public long getCantidad() throws Exception { return pCantidad.getValue(); }

	public void setPrecioUnitario(double zValue) throws Exception { pPrecioUnitario.setValue(zValue); }
	public double getPrecioUnitario() throws Exception { return pPrecioUnitario.getValue(); }

	public void setPasajero(String zValue) throws Exception { pPasajero.setValue(zValue); }
	public String getPasajero() throws Exception { return pPasajero.getValue(); }

	public void setRuta(String zValue) throws Exception { pRuta.setValue(zValue); }
	public String getRuta() throws Exception { return pRuta.getValue(); }

	public void setAerolinea(String zValue) throws Exception { pAerolinea.setValue(zValue); }
	public String getAerolinea() throws Exception { return pAerolinea.getValue(); }

	public void setBoleto(String zValue) throws Exception { pBoleto.setValue(zValue); }
	public String getBoleto() throws Exception { return pBoleto.getValue(); }

	public void setFechaHoraSalida(String zValue) throws Exception { pFechaHoraSalida.setValue(zValue); }
	public String getFechaHoraSalida() throws Exception { return pFechaHoraSalida.getValue(); }

	public void setClase(String zValue) throws Exception { pClase.setValue(zValue); }
	public String getClase() throws Exception { return pClase.getValue(); }

	public void setTarifaBase(String zValue) throws Exception { pTarifaBase.setValue(zValue); }
	public String getTarifaBase() throws Exception { return pTarifaBase.getValue(); }

	public void setIva(String zValue) throws Exception { pIva.setValue(zValue); }
	public String getIva() throws Exception { return pIva.getValue(); }

	public void setTua(String zValue) throws Exception { pTua.setValue(zValue); }
	public String getTua() throws Exception { return pTua.getValue(); }

	public void setMonto(double zValue) throws Exception { pMonto.setValue(zValue); }
	public double getMonto() throws Exception { return pMonto.getValue(); }

	public void setBoletoRevisado(String zValue) throws Exception { pBoletoRevisado.setValue(zValue); }
	public String getBoletoRevisado() throws Exception { return pBoletoRevisado.getValue(); }

	public void setDocumentoTipo(String zValue) throws Exception { pDocumentoTipo.setValue(zValue); }
	public String getDocumentoTipo() throws Exception { return pDocumentoTipo.getValue(); }

	public void setFechaHoraRegreso(String zValue) throws Exception { pFechaHoraRegreso.setValue(zValue); }
	public String getFechaHoraRegreso() throws Exception { return pFechaHoraRegreso.getValue(); }

	public void setCdOrigen(String zValue) throws Exception { pCdOrigen.setValue(zValue); }
	public String getCdOrigen() throws Exception { return pCdOrigen.getValue(); }

	public void setCdDestino(String zValue) throws Exception { pCdDestino.setValue(zValue); }
	public String getCdDestino() throws Exception { return pCdDestino.getValue(); }

	public void setBoletoConjunto(String zValue) throws Exception { pBoletoConjunto.setValue(zValue); }
	public String getBoletoConjunto() throws Exception { return pBoletoConjunto.getValue(); }

	public void setTasaIva(double zValue) throws Exception { pTasaIva.setValue(zValue); }
	public double getTasaIva() throws Exception { return pTasaIva.getValue(); }

	public void setClasIva(String zValue) throws Exception { pClasIva.setValue(zValue); }
	public String getClasIva() throws Exception { return pClasIva.getValue(); }

	public void setFormaPagoLinea(String zValue) throws Exception { pFormaPagoLinea.setValue(zValue); }
	public String getFormaPagoLinea() throws Exception { return pFormaPagoLinea.getValue(); }

	public void setTcLinea(String zValue) throws Exception { pTcLinea.setValue(zValue); }
	public String getTcLinea() throws Exception { return pTcLinea.getValue(); }

	public void setDk(String zValue) throws Exception { pDk.setValue(zValue); }
	public String getDk() throws Exception { return pDk.getValue(); }

	public void setNombreCliente(String zValue) throws Exception { pNombreCliente.setValue(zValue); }
	public String getNombreCliente() throws Exception { return pNombreCliente.getValue(); }

	public void setPccReserva(String zValue) throws Exception { pPccReserva.setValue(zValue); }
	public String getPccReserva() throws Exception { return pPccReserva.getValue(); }

	public void setIdBranch(String zValue) throws Exception { pIdBranch.setValue(zValue); }
	public String getIdBranch() throws Exception { return pIdBranch.getValue(); }

	public void setComentarios(String zValue) throws Exception { pComentarios.setValue(zValue); }
	public String getComentarios() throws Exception { return pComentarios.getValue(); }

	public void setUltimaste(long zValue) throws Exception { pUltimaste.setValue(zValue); }
	public long getUltimaste() throws Exception { return pUltimaste.getValue(); }

	public void setDkAgente(String zValue) throws Exception { pDkAgente.setValue(zValue); }
	public String getDkAgente() throws Exception { return pDkAgente.getValue(); }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Métodos para crear propiedades
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void createProperties() throws Exception {
		this.addItem("transaccion_id", pTransaccionId);
		this.addItem("trx_line_id", pTrxLineId);
		this.addItem("trx_link_id", pTrxLinkId);
		this.addItem("fecha_creacion", pFechaCreacion);
		this.addItem("transaccion_num", pTransaccionNum);
		this.addItem("imprimir", pImprimir);
		this.addItem("moneda", pMoneda);
		this.addItem("solicitante", pSolicitante);
		this.addItem("forma_pago", pFormaPago);
		this.addItem("tc_num", pTcNum);
		this.addItem("correo", pCorreo);
		this.addItem("tipo_cambio", pTipoCambio);
		this.addItem("unidad_operativa", pUnidadOperativa);
		this.addItem("fecha_cupon", pFechaCupon);
		this.addItem("finalizar", pFinalizar);
		this.addItem("reserva_id", pReservaId);
		this.addItem("iata", pIata);
		this.addItem("gds", pGds);
		this.addItem("pcc_emite", pPccEmite);
		this.addItem("agente_emite", pAgenteEmite);
		this.addItem("archivo", pArchivo);
		this.addItem("organizacion", pOrganizacion);
		this.addItem("tipo_servicio", pTipoServicio);
		this.addItem("cantidad", pCantidad);
		this.addItem("precio_unitario", pPrecioUnitario);
		this.addItem("pasajero", pPasajero);
		this.addItem("ruta", pRuta);
		this.addItem("aerolinea", pAerolinea);
		this.addItem("boleto", pBoleto);
		this.addItem("fecha_hora_salida", pFechaHoraSalida);
		this.addItem("clase", pClase);
		this.addItem("tarifa_base", pTarifaBase);
		this.addItem("iva", pIva);
		this.addItem("tua", pTua);
		this.addItem("monto", pMonto);
		this.addItem("boleto_revisado", pBoletoRevisado);
		this.addItem("documento_tipo", pDocumentoTipo);
		this.addItem("fecha_hora_regreso", pFechaHoraRegreso);
		this.addItem("cd_origen", pCdOrigen);
		this.addItem("cd_destino", pCdDestino);
		this.addItem("boleto_conjunto", pBoletoConjunto);
		this.addItem("tasa_iva", pTasaIva);
		this.addItem("clas_iva", pClasIva);
		this.addItem("forma_pago_linea", pFormaPagoLinea);
		this.addItem("tc_linea", pTcLinea);
		this.addItem("dk", pDk);
		this.addItem("nombre_cliente", pNombreCliente);
		this.addItem("pcc_reserva", pPccReserva);
		this.addItem("id_branch", pIdBranch);
		this.addItem("comentarios", pComentarios);
		this.addItem("ultimaste", pUltimaste);
		this.addItem("dk_agente", pDkAgente);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Métodos para crear propiedades fijas
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "transaccion_id", "Transaccion ID", true, true, 22);
		this.addFixedItem(FIELD, "trx_line_id", "TRX Line ID", true, false, 22);
		this.addFixedItem(FIELD, "trx_link_id", "TRX Link ID", true, false, 22);
		this.addFixedItem(FIELD, "fecha_creacion", "Fecha Creacion", true, false, 7);
		this.addFixedItem(FIELD, "transaccion_num", "Transaccion Num", true, false, 20);
		this.addFixedItem(FIELD, "imprimir", "Imprimir", true, false, 1);
		this.addFixedItem(FIELD, "moneda", "Moneda", true, false, 15);
		this.addFixedItem(FIELD, "solicitante", "Solicitante", true, false, 150);
		this.addFixedItem(FIELD, "forma_pago", "Forma Pago", true, false, 150);
		this.addFixedItem(FIELD, "tc_num", "TC Num", true, false, 150);
		this.addFixedItem(FIELD, "correo", "Correo", true, false, 150);
		this.addFixedItem(FIELD, "tipo_cambio", "Tipo Cambio", true, false, 150);
		this.addFixedItem(FIELD, "unidad_operativa", "Unidad Operativa", true, false, 150);
		this.addFixedItem(FIELD, "fecha_cupon", "Fecha Cupon", true, false, 150);
		this.addFixedItem(FIELD, "finalizar", "Finalizar", true, false, 1);
		this.addFixedItem(FIELD, "reserva_id", "Reserva ID", true, false, 150);
		this.addFixedItem(FIELD, "iata", "IATA", true, false, 150);
		this.addFixedItem(FIELD, "gds", "GDS", true, false, 150);
		this.addFixedItem(FIELD, "pcc_emite", "PCC Emite", true, false, 150);
		this.addFixedItem(FIELD, "agente_emite", "Agente Emite", true, false, 150);
		this.addFixedItem(FIELD, "archivo", "Archivo", true, false, 150);
		this.addFixedItem(FIELD, "organizacion", "Organizacion", true, false, 9);
		this.addFixedItem(FIELD, "tipo_servicio", "Tipo Servicio", true, false, 240);
		this.addFixedItem(FIELD, "cantidad", "Cantidad", true, false, 22);
		this.addFixedItem(FIELD, "precio_unitario", "Precio Unitario", true, false, 22);
		this.addFixedItem(FIELD, "pasajero", "Pasajero", true, false, 150);
		this.addFixedItem(FIELD, "ruta", "Ruta", true, false, 150);
		this.addFixedItem(FIELD, "aerolinea", "Aerolinea", true, false, 150);
		this.addFixedItem(FIELD, "boleto", "Boleto", true, false, 150);
		this.addFixedItem(FIELD, "fecha_hora_salida", "Fecha Hora Salida", true, false, 150);
		this.addFixedItem(FIELD, "clase", "Clase", true, false, 150);
		this.addFixedItem(FIELD, "tarifa_base", "Tarifa Base", true, false, 150);
		this.addFixedItem(FIELD, "iva", "IVA", true, false, 150);
		this.addFixedItem(FIELD, "tua", "TUA", true, false, 150);
		this.addFixedItem(FIELD, "monto", "Monto", true, false, 22);
		this.addFixedItem(FIELD, "boleto_revisado", "Boleto Revisado", true, false, 150);
		this.addFixedItem(FIELD, "documento_tipo", "Documento Tipo", true, false, 50);
		this.addFixedItem(FIELD, "fecha_hora_regreso", "Fecha Hora Regreso", true, false, 150);
		this.addFixedItem(FIELD, "cd_origen", "CD Origen", true, false, 150);
		this.addFixedItem(FIELD, "cd_destino", "CD Destino", true, false, 150);
		this.addFixedItem(FIELD, "boleto_conjunto", "Boleto Conjunto", true, false, 150);
		this.addFixedItem(FIELD, "tasa_iva", "Tasa IVA", true, false, 22);
		this.addFixedItem(FIELD, "clas_iva", "Clas IVA", true, false, 50);
		this.addFixedItem(FIELD, "forma_pago_linea", "Forma Pago Linea", true, false, 150);
		this.addFixedItem(FIELD, "tc_linea", "TC Linea", true, false, 150);
		this.addFixedItem(FIELD, "dk", "DK", true, false, 30);
		this.addFixedItem(FIELD, "nombre_cliente", "Nombre Cliente", true, false, 50);
		this.addFixedItem(FIELD, "pcc_reserva", "PCC Reserva", true, false, 100);
		this.addFixedItem(FIELD, "id_branch", "ID Branch", true, false, 20);
		this.addFixedItem(FIELD, "comentarios", "Comentarios", true, false, 1760);
		this.addFixedItem(FIELD, "ultimaste", "Ultimaste", true, false, 22);
		this.addFixedItem(FIELD, "dk_agente", "DK Agente", true, false, 70);
	}

  /**
   * Returns the table name
   */
  public String GetTable() { return "apps.CNS_ARTRX_DK_V"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "transaccion_id",  zId ); 
    return read(); 
  }


	public Date getDateValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.FECHA))
			return pFechaCreacion.getValue();
		return null;
	}

	public Long getLongValue(String field, boolean check) throws Exception {
//		if (field.equals(IConciliable.ID_AEROLINEA))
//			return pAerolinea.getValue();
		return null;
	}

	public Double getDoubleValue(String field, String moneda, boolean check) throws Exception {
		if (moneda!=null&&moneda.equals("USD"))
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
			return pMonto.getValue();
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
			return pMonto.getValue();
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
			return check?null:pAerolinea.getValue()+" "+pBoleto.getValue();;
		if (field.equalsIgnoreCase(IConciliable.AEROLINEA))
				return check?null:pAerolinea.getValue();
		if (field.equalsIgnoreCase(IConciliable.AEROLINEA_BOLETOS))
			return check?null:pAerolinea.getValue()+" "+pBoleto.getValue();
		if (field.equalsIgnoreCase(IConciliable.ID_AEROLINEA))
			return check?null:pAerolinea.getValue();
		if (field.equalsIgnoreCase(IConciliable.NUMERO_TARJETA))
			return check?null: pTcNum.getValue();
		if (field.equalsIgnoreCase(IConciliable.OBSERVACION))
			return check?null: pComentarios.getValue();;
		if (field.equalsIgnoreCase(IConciliable.OPERACION))
			return check?null:pDocumentoTipo.getValue();
		if (field.equalsIgnoreCase(IConciliable.TIPO_RUTA))
			return null;
		if (field.equalsIgnoreCase(IConciliable.TIPO_TARJETA))
			return check?null:pFormaPago.getValue();;
		if (field.equalsIgnoreCase(IConciliable.CLIENTE))
			return check?null:pDk.getValue();;
		if (field.equalsIgnoreCase(IConciliable.FECHA))
			return check?null:JDateTools.DateToString(pFechaCreacion.getValue(),"dd/MM/YYYY");
		if (field.equalsIgnoreCase(IConciliable.TARIFA))
			return check?null:""+pMonto.getValue();
		if (field.equalsIgnoreCase(IConciliable.BASE_IMPONIBLE))
			return check?null:""+pTarifaBase.getValue();
		if (field.equalsIgnoreCase(IConciliable.NETO))
			return check?null:""+pTarifaBase.getValue();
		if (field.equalsIgnoreCase(IConciliable.IMPUESTO_1))
			return check?null:""+pIva.getValue();
		if (field.equalsIgnoreCase(IConciliable.CONTADO))
			return check?null:!pFormaPago.getValue().equals("CCPD")?"0.00":""+pMonto.getValue();
		if (field.equalsIgnoreCase(IConciliable.TARJETA))
			return check?null:pFormaPago.getValue().equals("CCPD")?"0.00":""+pMonto.getValue();

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
