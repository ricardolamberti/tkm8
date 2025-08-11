package  pss.bsp.gds;

import java.util.Date;

import pss.bsp.pdf.arg.detalle.BizArgDetalle;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizGds extends JRecord {

  private JLong pId = new JLong();
  private JString pTarifabase = new JString();
  private JString pTarifaCodmoneda = new JString();
  private JString pCodigobasemoneda = new JString();
  private JLong pTarifaSec = new JLong();
  private JString pTipodecambio = new JString();
  private JString pTarifaconimpuestos = new JString();
  private JString pTarifa = new JString();
  private JString pFeeCodmoneda = new JString();
  private JString pFeeImporte = new JString();
  private JString pFeeDescripcion = new JString();
  private JString pCodigoentretenimiento = new JString();
  private JString pHoraarrivo = new JString();
  private JString pTipoSegmento = new JString();
  private JString pHoradespegue = new JString();
  private JString pDespegue = new JString();
  private JString pDuracionvuelo = new JString();
  private JString pArrivo = new JString();
  private JString pCarrier = new JString();
  private JString pCodigosegmento = new JString();
  private JString pFechaarrivo = new JString();
  private JString pEstado = new JString();
  private JString pCodigocomida = new JString();
  private JString pClase = new JString();
  private JString pTipoequipo = new JString();
  private JString pSegmentoIni = new JString();
  private JString pNumerovuelo = new JString();
  private JString pFechadespegue = new JString();
  private JString pNumero = new JString();
  private JString pDetalle = new JString();
  private JString pCodigoaerolineafqt = new JString();
  private JString pNombrepasajero = new JString();
  private JString pNumeropasajerofqt = new JString();
  private JString pTipopasajero = new JString();
  private JString pPasNumeropasajero = new JString();
  private JString pPagoCodmoneda = new JString();
  private JString pFormapago = new JString();
  private JString pCodigoaprobacion = new JString();
  private JString pPagoImporte = new JString();
  private JString pNumerotarjeta = new JString();
  private JLong pPagoSec = new JLong();
  private JString pFechavencimiento = new JString();
  private JString pComImporte = new JString();
  private JLong pSecuencia = new JLong();
  private JFloat pComisionIva = new JFloat();
  private JString pPorcentaje = new JString();
  private JString pImpCodmoneda = new JString();
  private JString pVirtual = new JString();
  private JString pImpImporte = new JString();
  private JLong pImpSec = new JLong();
  private JString pCodigoimpuesto = new JString();
  private JString pImportecedido = new JString();
  private JString pOffLine = new JString();
  private JString pTarifanormal = new JString();
  private JString pNeto = new JString();
  private JString pAdditionalFee = new JString();
  private JLong pPorcentajeover = new JLong();
  private JString pVoid = new JString();
  private JString pDevolucion = new JString();
  private JString pBoletocambio = new JString();
  private JFloat pIvaExpense = new JFloat();
  private JString pCodigoaerolinea = new JString();
  private JString pIt = new JString();
  private JString pVenta = new JString();
  private JString pOverCedidoiva_retenido = new JString();
  private JString pReemision = new JString();
  private JString pNumeroboleto = new JString();
  private JString pCantidadconectados = new JString();
  private JString pAplicacentro = new JString();
  private JString pAjuste = new JString();
  private JLong pBoletoSec = new JLong();
  private JString pInternacional = new JString();
  private JString pReferencia = new JString();
  private JString pBoletoNropasajero = new JString();
  private JString pTarifaeconomica = new JString();
  private JString pImporteover = new JString();
  private JFloat pIvaover = new JFloat();
  private JString pBoletoDescripcion = new JString();
  private JString pConsolidador = new JString();
  private JString pNumeroempleado = new JString();
  private JFloat pExpense = new JFloat();
  private JFloat pPorcExpense = new JFloat();
  private JString pTipoboleto = new JString();
  private JString pOversobre = new JString();
  private JString pAplicacomparativo = new JString();
  private JString pConsumed = new JString();
  private JDate pFechacreacion = new JDate();
  private JString pOrigen = new JString();
  private JString pCentrocosto = new JString();
  private JString pFechasalida = new JString();
  private JString pCodigopnr = new JString();
  private JString pObservation = new JString();
  private JString pTipoair = new JString();
  private JString pRetransmitido = new JString();
  private JString pCantidadboletos = new JString();
  private JString pVendedor = new JString();
  private JString pCompany = new JString();
  private JString pRegistrolocalizador = new JString();
  private JString pTransactiontype = new JString();
  private JDate pFechacreacionair = new JDate();
  private JString pOfficeid = new JString();
  private JString pRuta = new JString();
  private JString pComandocrs = new JString();
  private JString pCodigocliente = new JString();
  private JString pNombrecliente = new JString();
  private JString pVersion = new JString();
  private JDate pFechamodificacion = new JDate();
  private JString pGds = new JString();
  private JString pArchivo = new JString();
  private JLong pInterfaceId = new JLong();
  private JString pIndicadorventa = new JString();
  private JString pTipoPrestacion = new JString();
  private JString pIata = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setTarifabase(String zValue) throws Exception {    pTarifabase.setValue(zValue);  }
  public String getTarifabase() throws Exception {     return pTarifabase.getValue();  }
  public boolean isNullTarifabase() throws Exception { return  pTarifabase.isNull(); } 
  public void setNullToTarifabase() throws Exception {  pTarifabase.setNull(); } 
  public void setTarifaCodmoneda(String zValue) throws Exception {    pTarifaCodmoneda.setValue(zValue);  }
  public String getTarifaCodmoneda() throws Exception {     return pTarifaCodmoneda.getValue();  }
  public boolean isNullTarifaCodmoneda() throws Exception { return  pTarifaCodmoneda.isNull(); } 
  public void setNullToTarifaCodmoneda() throws Exception {  pTarifaCodmoneda.setNull(); } 
  public void setCodigobasemoneda(String zValue) throws Exception {    pCodigobasemoneda.setValue(zValue);  }
  public String getCodigobasemoneda() throws Exception {     return pCodigobasemoneda.getValue();  }
  public boolean isNullCodigobasemoneda() throws Exception { return  pCodigobasemoneda.isNull(); } 
  public void setNullToCodigobasemoneda() throws Exception {  pCodigobasemoneda.setNull(); } 
  public void setTarifaSec(long zValue) throws Exception {    pTarifaSec.setValue(zValue);  }
  public long getTarifaSec() throws Exception {     return pTarifaSec.getValue();  }
  public boolean isNullTarifaSec() throws Exception { return  pTarifaSec.isNull(); } 
  public void setNullToTarifaSec() throws Exception {  pTarifaSec.setNull(); } 
  public void setTipodecambio(String zValue) throws Exception {    pTipodecambio.setValue(zValue);  }
  public String getTipodecambio() throws Exception {     return pTipodecambio.getValue();  }
  public boolean isNullTipodecambio() throws Exception { return  pTipodecambio.isNull(); } 
  public void setNullToTipodecambio() throws Exception {  pTipodecambio.setNull(); } 
  public void setTarifaconimpuestos(String zValue) throws Exception {    pTarifaconimpuestos.setValue(zValue);  }
  public String getTarifaconimpuestos() throws Exception {     return pTarifaconimpuestos.getValue();  }
  public boolean isNullTarifaconimpuestos() throws Exception { return  pTarifaconimpuestos.isNull(); } 
  public void setNullToTarifaconimpuestos() throws Exception {  pTarifaconimpuestos.setNull(); } 
  public void setTarifa(String zValue) throws Exception {    pTarifa.setValue(zValue);  }
  public String getTarifa() throws Exception {     return pTarifa.getValue();  }
  public boolean isNullTarifa() throws Exception { return  pTarifa.isNull(); } 
  public void setNullToTarifa() throws Exception {  pTarifa.setNull(); } 
  public void setFeeCodmoneda(String zValue) throws Exception {    pFeeCodmoneda.setValue(zValue);  }
  public String getFeeCodmoneda() throws Exception {     return pFeeCodmoneda.getValue();  }
  public boolean isNullFeeCodmoneda() throws Exception { return  pFeeCodmoneda.isNull(); } 
  public void setNullToFeeCodmoneda() throws Exception {  pFeeCodmoneda.setNull(); } 
  public void setFeeImporte(String zValue) throws Exception {    pFeeImporte.setValue(zValue);  }
  public String getFeeImporte() throws Exception {     return pFeeImporte.getValue();  }
  public boolean isNullFeeImporte() throws Exception { return  pFeeImporte.isNull(); } 
  public void setNullToFeeImporte() throws Exception {  pFeeImporte.setNull(); } 
  public void setFeeDescripcion(String zValue) throws Exception {    pFeeDescripcion.setValue(zValue);  }
  public String getFeeDescripcion() throws Exception {     return pFeeDescripcion.getValue();  }
  public boolean isNullFeeDescripcion() throws Exception { return  pFeeDescripcion.isNull(); } 
  public void setNullToFeeDescripcion() throws Exception {  pFeeDescripcion.setNull(); } 
  public void setCodigoentretenimiento(String zValue) throws Exception {    pCodigoentretenimiento.setValue(zValue);  }
  public String getCodigoentretenimiento() throws Exception {     return pCodigoentretenimiento.getValue();  }
  public boolean isNullCodigoentretenimiento() throws Exception { return  pCodigoentretenimiento.isNull(); } 
  public void setNullToCodigoentretenimiento() throws Exception {  pCodigoentretenimiento.setNull(); } 
  public void setHoraarrivo(String zValue) throws Exception {    pHoraarrivo.setValue(zValue);  }
  public String getHoraarrivo() throws Exception {     return pHoraarrivo.getValue();  }
  public boolean isNullHoraarrivo() throws Exception { return  pHoraarrivo.isNull(); } 
  public void setNullToHoraarrivo() throws Exception {  pHoraarrivo.setNull(); } 
  public void setTipoSegmento(String zValue) throws Exception {    pTipoSegmento.setValue(zValue);  }
  public String getTipoSegmento() throws Exception {     return pTipoSegmento.getValue();  }
  public boolean isNullTipoSegmento() throws Exception { return  pTipoSegmento.isNull(); } 
  public void setNullToTipoSegmento() throws Exception {  pTipoSegmento.setNull(); } 
  public void setHoradespegue(String zValue) throws Exception {    pHoradespegue.setValue(zValue);  }
  public String getHoradespegue() throws Exception {     return pHoradespegue.getValue();  }
  public boolean isNullHoradespegue() throws Exception { return  pHoradespegue.isNull(); } 
  public void setNullToHoradespegue() throws Exception {  pHoradespegue.setNull(); } 
  public void setDespegue(String zValue) throws Exception {    pDespegue.setValue(zValue);  }
  public String getDespegue() throws Exception {     return pDespegue.getValue();  }
  public boolean isNullDespegue() throws Exception { return  pDespegue.isNull(); } 
  public void setNullToDespegue() throws Exception {  pDespegue.setNull(); } 
  public void setDuracionvuelo(String zValue) throws Exception {    pDuracionvuelo.setValue(zValue);  }
  public String getDuracionvuelo() throws Exception {     return pDuracionvuelo.getValue();  }
  public boolean isNullDuracionvuelo() throws Exception { return  pDuracionvuelo.isNull(); } 
  public void setNullToDuracionvuelo() throws Exception {  pDuracionvuelo.setNull(); } 
  public void setArrivo(String zValue) throws Exception {    pArrivo.setValue(zValue);  }
  public String getArrivo() throws Exception {     return pArrivo.getValue();  }
  public boolean isNullArrivo() throws Exception { return  pArrivo.isNull(); } 
  public void setNullToArrivo() throws Exception {  pArrivo.setNull(); } 
  public void setCarrier(String zValue) throws Exception {    pCarrier.setValue(zValue);  }
  public String getCarrier() throws Exception {     return pCarrier.getValue();  }
  public boolean isNullCarrier() throws Exception { return  pCarrier.isNull(); } 
  public void setNullToCarrier() throws Exception {  pCarrier.setNull(); } 
  public void setCodigosegmento(String zValue) throws Exception {    pCodigosegmento.setValue(zValue);  }
  public String getCodigosegmento() throws Exception {     return pCodigosegmento.getValue();  }
  public boolean isNullCodigosegmento() throws Exception { return  pCodigosegmento.isNull(); } 
  public void setNullToCodigosegmento() throws Exception {  pCodigosegmento.setNull(); } 
  public void setFechaarrivo(String zValue) throws Exception {    pFechaarrivo.setValue(zValue);  }
  public String getFechaarrivo() throws Exception {     return pFechaarrivo.getValue();  }
  public boolean isNullFechaarrivo() throws Exception { return  pFechaarrivo.isNull(); } 
  public void setNullToFechaarrivo() throws Exception {  pFechaarrivo.setNull(); } 
  public void setEstado(String zValue) throws Exception {    pEstado.setValue(zValue);  }
  public String getEstado() throws Exception {     return pEstado.getValue();  }
  public boolean isNullEstado() throws Exception { return  pEstado.isNull(); } 
  public void setNullToEstado() throws Exception {  pEstado.setNull(); } 
  public void setCodigocomida(String zValue) throws Exception {    pCodigocomida.setValue(zValue);  }
  public String getCodigocomida() throws Exception {     return pCodigocomida.getValue();  }
  public boolean isNullCodigocomida() throws Exception { return  pCodigocomida.isNull(); } 
  public void setNullToCodigocomida() throws Exception {  pCodigocomida.setNull(); } 
  public void setClase(String zValue) throws Exception {    pClase.setValue(zValue);  }
  public String getClase() throws Exception {     return pClase.getValue();  }
  public boolean isNullClase() throws Exception { return  pClase.isNull(); } 
  public void setNullToClase() throws Exception {  pClase.setNull(); } 
  public void setTipoequipo(String zValue) throws Exception {    pTipoequipo.setValue(zValue);  }
  public String getTipoequipo() throws Exception {     return pTipoequipo.getValue();  }
  public boolean isNullTipoequipo() throws Exception { return  pTipoequipo.isNull(); } 
  public void setNullToTipoequipo() throws Exception {  pTipoequipo.setNull(); } 
  public void setSegmentoIni(String zValue) throws Exception {    pSegmentoIni.setValue(zValue);  }
  public String getSegmentoIni() throws Exception {     return pSegmentoIni.getValue();  }
  public boolean isNullSegmentoIni() throws Exception { return  pSegmentoIni.isNull(); } 
  public void setNullToSegmentoIni() throws Exception {  pSegmentoIni.setNull(); } 
  public void setNumerovuelo(String zValue) throws Exception {    pNumerovuelo.setValue(zValue);  }
  public String getNumerovuelo() throws Exception {     return pNumerovuelo.getValue();  }
  public boolean isNullNumerovuelo() throws Exception { return  pNumerovuelo.isNull(); } 
  public void setNullToNumerovuelo() throws Exception {  pNumerovuelo.setNull(); } 
  public void setFechadespegue(String zValue) throws Exception {    pFechadespegue.setValue(zValue);  }
  public String getFechadespegue() throws Exception {     return pFechadespegue.getValue();  }
  public boolean isNullFechadespegue() throws Exception { return  pFechadespegue.isNull(); } 
  public void setNullToFechadespegue() throws Exception {  pFechadespegue.setNull(); } 
  public void setNumero(String zValue) throws Exception {    pNumero.setValue(zValue);  }
  public String getNumero() throws Exception {     return pNumero.getValue();  }
  public boolean isNullNumero() throws Exception { return  pNumero.isNull(); } 
  public void setNullToNumero() throws Exception {  pNumero.setNull(); } 
  public void setDetalle(String zValue) throws Exception {    pDetalle.setValue(zValue);  }
  public String getDetalle() throws Exception {     return pDetalle.getValue();  }
  public boolean isNullDetalle() throws Exception { return  pDetalle.isNull(); } 
  public void setNullToDetalle() throws Exception {  pDetalle.setNull(); } 
  public void setCodigoaerolineafqt(String zValue) throws Exception {    pCodigoaerolineafqt.setValue(zValue);  }
  public String getCodigoaerolineafqt() throws Exception {     return pCodigoaerolineafqt.getValue();  }
  public boolean isNullCodigoaerolineafqt() throws Exception { return  pCodigoaerolineafqt.isNull(); } 
  public void setNullToCodigoaerolineafqt() throws Exception {  pCodigoaerolineafqt.setNull(); } 
  public void setNombrepasajero(String zValue) throws Exception {    pNombrepasajero.setValue(zValue);  }
  public String getNombrepasajero() throws Exception {     return pNombrepasajero.getValue();  }
  public boolean isNullNombrepasajero() throws Exception { return  pNombrepasajero.isNull(); } 
  public void setNullToNombrepasajero() throws Exception {  pNombrepasajero.setNull(); } 
  public void setNumeropasajerofqt(String zValue) throws Exception {    pNumeropasajerofqt.setValue(zValue);  }
  public String getNumeropasajerofqt() throws Exception {     return pNumeropasajerofqt.getValue();  }
  public boolean isNullNumeropasajerofqt() throws Exception { return  pNumeropasajerofqt.isNull(); } 
  public void setNullToNumeropasajerofqt() throws Exception {  pNumeropasajerofqt.setNull(); } 
  public void setTipopasajero(String zValue) throws Exception {    pTipopasajero.setValue(zValue);  }
  public String getTipopasajero() throws Exception {     return pTipopasajero.getValue();  }
  public boolean isNullTipopasajero() throws Exception { return  pTipopasajero.isNull(); } 
  public void setNullToTipopasajero() throws Exception {  pTipopasajero.setNull(); } 
  public void setPasNumeropasajero(String zValue) throws Exception {    pPasNumeropasajero.setValue(zValue);  }
  public String getPasNumeropasajero() throws Exception {     return pPasNumeropasajero.getValue();  }
  public boolean isNullPasNumeropasajero() throws Exception { return  pPasNumeropasajero.isNull(); } 
  public void setNullToPasNumeropasajero() throws Exception {  pPasNumeropasajero.setNull(); } 
  public void setPagoCodmoneda(String zValue) throws Exception {    pPagoCodmoneda.setValue(zValue);  }
  public String getPagoCodmoneda() throws Exception {     return pPagoCodmoneda.getValue();  }
  public boolean isNullPagoCodmoneda() throws Exception { return  pPagoCodmoneda.isNull(); } 
  public void setNullToPagoCodmoneda() throws Exception {  pPagoCodmoneda.setNull(); } 
  public void setFormapago(String zValue) throws Exception {    pFormapago.setValue(zValue);  }
  public String getFormapago() throws Exception {     return pFormapago.getValue();  }
  public boolean isNullFormapago() throws Exception { return  pFormapago.isNull(); } 
  public void setNullToFormapago() throws Exception {  pFormapago.setNull(); } 
  public void setCodigoaprobacion(String zValue) throws Exception {    pCodigoaprobacion.setValue(zValue);  }
  public String getCodigoaprobacion() throws Exception {     return pCodigoaprobacion.getValue();  }
  public boolean isNullCodigoaprobacion() throws Exception { return  pCodigoaprobacion.isNull(); } 
  public void setNullToCodigoaprobacion() throws Exception {  pCodigoaprobacion.setNull(); } 
  public void setPagoImporte(String zValue) throws Exception {    pPagoImporte.setValue(zValue);  }
  public String getPagoImporte() throws Exception {     return pPagoImporte.getValue();  }
  public boolean isNullPagoImporte() throws Exception { return  pPagoImporte.isNull(); } 
  public void setNullToPagoImporte() throws Exception {  pPagoImporte.setNull(); } 
  public void setNumerotarjeta(String zValue) throws Exception {    pNumerotarjeta.setValue(zValue);  }
  public String getNumerotarjeta() throws Exception {     return pNumerotarjeta.getValue();  }
  public boolean isNullNumerotarjeta() throws Exception { return  pNumerotarjeta.isNull(); } 
  public void setNullToNumerotarjeta() throws Exception {  pNumerotarjeta.setNull(); } 
  public void setPagoSec(long zValue) throws Exception {    pPagoSec.setValue(zValue);  }
  public long getPagoSec() throws Exception {     return pPagoSec.getValue();  }
  public boolean isNullPagoSec() throws Exception { return  pPagoSec.isNull(); } 
  public void setNullToPagoSec() throws Exception {  pPagoSec.setNull(); } 
  public void setFechavencimiento(String zValue) throws Exception {    pFechavencimiento.setValue(zValue);  }
  public String getFechavencimiento() throws Exception {     return pFechavencimiento.getValue();  }
  public boolean isNullFechavencimiento() throws Exception { return  pFechavencimiento.isNull(); } 
  public void setNullToFechavencimiento() throws Exception {  pFechavencimiento.setNull(); } 
  public void setComImporte(String zValue) throws Exception {    pComImporte.setValue(zValue);  }
  public String getComImporte() throws Exception {     return pComImporte.getValue();  }
  public boolean isNullComImporte() throws Exception { return  pComImporte.isNull(); } 
  public void setNullToComImporte() throws Exception {  pComImporte.setNull(); } 
  public void setSecuencia(long zValue) throws Exception {    pSecuencia.setValue(zValue);  }
  public long getSecuencia() throws Exception {     return pSecuencia.getValue();  }
  public boolean isNullSecuencia() throws Exception { return  pSecuencia.isNull(); } 
  public void setNullToSecuencia() throws Exception {  pSecuencia.setNull(); } 
  public void setComisionIva(double zValue) throws Exception {    pComisionIva.setValue(zValue);  }
  public double getComisionIva() throws Exception {     return pComisionIva.getValue();  }
  public boolean isNullComisionIva() throws Exception { return  pComisionIva.isNull(); } 
  public void setNullToComisionIva() throws Exception {  pComisionIva.setNull(); } 
  public void setPorcentaje(String zValue) throws Exception {    pPorcentaje.setValue(zValue);  }
  public String getPorcentaje() throws Exception {     return pPorcentaje.getValue();  }
  public boolean isNullPorcentaje() throws Exception { return  pPorcentaje.isNull(); } 
  public void setNullToPorcentaje() throws Exception {  pPorcentaje.setNull(); } 
  public void setImpCodmoneda(String zValue) throws Exception {    pImpCodmoneda.setValue(zValue);  }
  public String getImpCodmoneda() throws Exception {     return pImpCodmoneda.getValue();  }
  public boolean isNullImpCodmoneda() throws Exception { return  pImpCodmoneda.isNull(); } 
  public void setNullToImpCodmoneda() throws Exception {  pImpCodmoneda.setNull(); } 
  public void setVirtual(String zValue) throws Exception {    pVirtual.setValue(zValue);  }
  public String getVirtual() throws Exception {     return pVirtual.getValue();  }
  public boolean isNullVirtual() throws Exception { return  pVirtual.isNull(); } 
  public void setNullToVirtual() throws Exception {  pVirtual.setNull(); } 
  public void setImpImporte(String zValue) throws Exception {    pImpImporte.setValue(zValue);  }
  public String getImpImporte() throws Exception {     return pImpImporte.getValue();  }
  public boolean isNullImpImporte() throws Exception { return  pImpImporte.isNull(); } 
  public void setNullToImpImporte() throws Exception {  pImpImporte.setNull(); } 
  public void setImpSec(long zValue) throws Exception {    pImpSec.setValue(zValue);  }
  public long getImpSec() throws Exception {     return pImpSec.getValue();  }
  public boolean isNullImpSec() throws Exception { return  pImpSec.isNull(); } 
  public void setNullToImpSec() throws Exception {  pImpSec.setNull(); } 
  public void setCodigoimpuesto(String zValue) throws Exception {    pCodigoimpuesto.setValue(zValue);  }
  public String getCodigoimpuesto() throws Exception {     return pCodigoimpuesto.getValue();  }
  public boolean isNullCodigoimpuesto() throws Exception { return  pCodigoimpuesto.isNull(); } 
  public void setNullToCodigoimpuesto() throws Exception {  pCodigoimpuesto.setNull(); } 
  public void setImportecedido(String zValue) throws Exception {    pImportecedido.setValue(zValue);  }
  public String getImportecedido() throws Exception {     return pImportecedido.getValue();  }
  public boolean isNullImportecedido() throws Exception { return  pImportecedido.isNull(); } 
  public void setNullToImportecedido() throws Exception {  pImportecedido.setNull(); } 
  public void setOffLine(String zValue) throws Exception {    pOffLine.setValue(zValue);  }
  public String getOffLine() throws Exception {     return pOffLine.getValue();  }
  public boolean isNullOffLine() throws Exception { return  pOffLine.isNull(); } 
  public void setNullToOffLine() throws Exception {  pOffLine.setNull(); } 
  public void setTarifanormal(String zValue) throws Exception {    pTarifanormal.setValue(zValue);  }
  public String getTarifanormal() throws Exception {     return pTarifanormal.getValue();  }
  public boolean isNullTarifanormal() throws Exception { return  pTarifanormal.isNull(); } 
  public void setNullToTarifanormal() throws Exception {  pTarifanormal.setNull(); } 
  public void setNeto(String zValue) throws Exception {    pNeto.setValue(zValue);  }
  public String getNeto() throws Exception {     return pNeto.getValue();  }
  public boolean isNullNeto() throws Exception { return  pNeto.isNull(); } 
  public void setNullToNeto() throws Exception {  pNeto.setNull(); } 
  public void setAdditionalFee(String zValue) throws Exception {    pAdditionalFee.setValue(zValue);  }
  public String getAdditionalFee() throws Exception {     return pAdditionalFee.getValue();  }
  public boolean isNullAdditionalFee() throws Exception { return  pAdditionalFee.isNull(); } 
  public void setNullToAdditionalFee() throws Exception {  pAdditionalFee.setNull(); } 
  public void setPorcentajeover(long zValue) throws Exception {    pPorcentajeover.setValue(zValue);  }
  public long getPorcentajeover() throws Exception {     return pPorcentajeover.getValue();  }
  public boolean isNullPorcentajeover() throws Exception { return  pPorcentajeover.isNull(); } 
  public void setNullToPorcentajeover() throws Exception {  pPorcentajeover.setNull(); } 
  public void setVoid(String zValue) throws Exception {    pVoid.setValue(zValue);  }
  public String getVoid() throws Exception {     return pVoid.getValue();  }
  public boolean isNullVoid() throws Exception { return  pVoid.isNull(); } 
  public void setNullToVoid() throws Exception {  pVoid.setNull(); } 
  public void setDevolucion(String zValue) throws Exception {    pDevolucion.setValue(zValue);  }
  public String getDevolucion() throws Exception {     return pDevolucion.getValue();  }
  public boolean isNullDevolucion() throws Exception { return  pDevolucion.isNull(); } 
  public void setNullToDevolucion() throws Exception {  pDevolucion.setNull(); } 
  public void setBoletocambio(String zValue) throws Exception {    pBoletocambio.setValue(zValue);  }
  public String getBoletocambio() throws Exception {     return pBoletocambio.getValue();  }
  public boolean isNullBoletocambio() throws Exception { return  pBoletocambio.isNull(); } 
  public void setNullToBoletocambio() throws Exception {  pBoletocambio.setNull(); } 
  public void setIvaExpense(double zValue) throws Exception {    pIvaExpense.setValue(zValue);  }
  public double getIvaExpense() throws Exception {     return pIvaExpense.getValue();  }
  public boolean isNullIvaExpense() throws Exception { return  pIvaExpense.isNull(); } 
  public void setNullToIvaExpense() throws Exception {  pIvaExpense.setNull(); } 
  public void setCodigoaerolinea(String zValue) throws Exception {    pCodigoaerolinea.setValue(zValue);  }
  public String getCodigoaerolinea() throws Exception {     return pCodigoaerolinea.getValue();  }
  public boolean isNullCodigoaerolinea() throws Exception { return  pCodigoaerolinea.isNull(); } 
  public void setNullToCodigoaerolinea() throws Exception {  pCodigoaerolinea.setNull(); } 
  public void setIt(String zValue) throws Exception {    pIt.setValue(zValue);  }
  public String getIt() throws Exception {     return pIt.getValue();  }
  public boolean isNullIt() throws Exception { return  pIt.isNull(); } 
  public void setNullToIt() throws Exception {  pIt.setNull(); } 
  public void setVenta(String zValue) throws Exception {    pVenta.setValue(zValue);  }
  public String getVenta() throws Exception {     return pVenta.getValue();  }
  public boolean isNullVenta() throws Exception { return  pVenta.isNull(); } 
  public void setNullToVenta() throws Exception {  pVenta.setNull(); } 
  public void setOverCedidoiva_retenido(String zValue) throws Exception {    pOverCedidoiva_retenido.setValue(zValue);  }
  public String getOverCedidoiva_retenido() throws Exception {     return pOverCedidoiva_retenido.getValue();  }
  public boolean isNullOverCedidoiva_retenido() throws Exception { return  pOverCedidoiva_retenido.isNull(); } 
  public void setNullToOverCedidoiva_retenido() throws Exception {  pOverCedidoiva_retenido.setNull(); } 
  public void setReemision(String zValue) throws Exception {    pReemision.setValue(zValue);  }
  public String getReemision() throws Exception {     return pReemision.getValue();  }
  public boolean isNullReemision() throws Exception { return  pReemision.isNull(); } 
  public void setNullToReemision() throws Exception {  pReemision.setNull(); } 
  public void setNumeroboleto(String zValue) throws Exception {    pNumeroboleto.setValue(zValue);  }
  public String getNumeroboleto() throws Exception {     return pNumeroboleto.getValue();  }
  public boolean isNullNumeroboleto() throws Exception { return  pNumeroboleto.isNull(); } 
  public void setNullToNumeroboleto() throws Exception {  pNumeroboleto.setNull(); } 
  public void setCantidadconectados(String zValue) throws Exception {    pCantidadconectados.setValue(zValue);  }
  public String getCantidadconectados() throws Exception {     return pCantidadconectados.getValue();  }
  public boolean isNullCantidadconectados() throws Exception { return  pCantidadconectados.isNull(); } 
  public void setNullToCantidadconectados() throws Exception {  pCantidadconectados.setNull(); } 
  public void setAplicacentro(String zValue) throws Exception {    pAplicacentro.setValue(zValue);  }
  public String getAplicacentro() throws Exception {     return pAplicacentro.getValue();  }
  public boolean isNullAplicacentro() throws Exception { return  pAplicacentro.isNull(); } 
  public void setNullToAplicacentro() throws Exception {  pAplicacentro.setNull(); } 
  public void setAjuste(String zValue) throws Exception {    pAjuste.setValue(zValue);  }
  public String getAjuste() throws Exception {     return pAjuste.getValue();  }
  public boolean isNullAjuste() throws Exception { return  pAjuste.isNull(); } 
  public void setNullToAjuste() throws Exception {  pAjuste.setNull(); } 
  public void setBoletoSec(long zValue) throws Exception {    pBoletoSec.setValue(zValue);  }
  public long getBoletoSec() throws Exception {     return pBoletoSec.getValue();  }
  public boolean isNullBoletoSec() throws Exception { return  pBoletoSec.isNull(); } 
  public void setNullToBoletoSec() throws Exception {  pBoletoSec.setNull(); } 
  public void setInternacional(String zValue) throws Exception {    pInternacional.setValue(zValue);  }
  public String getInternacional() throws Exception {     return pInternacional.getValue();  }
  public boolean isNullInternacional() throws Exception { return  pInternacional.isNull(); } 
  public void setNullToInternacional() throws Exception {  pInternacional.setNull(); } 
  public void setReferencia(String zValue) throws Exception {    pReferencia.setValue(zValue);  }
  public String getReferencia() throws Exception {     return pReferencia.getValue();  }
  public boolean isNullReferencia() throws Exception { return  pReferencia.isNull(); } 
  public void setNullToReferencia() throws Exception {  pReferencia.setNull(); } 
  public void setBoletoNropasajero(String zValue) throws Exception {    pBoletoNropasajero.setValue(zValue);  }
  public String getBoletoNropasajero() throws Exception {     return pBoletoNropasajero.getValue();  }
  public boolean isNullBoletoNropasajero() throws Exception { return  pBoletoNropasajero.isNull(); } 
  public void setNullToBoletoNropasajero() throws Exception {  pBoletoNropasajero.setNull(); } 
  public void setTarifaeconomica(String zValue) throws Exception {    pTarifaeconomica.setValue(zValue);  }
  public String getTarifaeconomica() throws Exception {     return pTarifaeconomica.getValue();  }
  public boolean isNullTarifaeconomica() throws Exception { return  pTarifaeconomica.isNull(); } 
  public void setNullToTarifaeconomica() throws Exception {  pTarifaeconomica.setNull(); } 
  public void setImporteover(String zValue) throws Exception {    pImporteover.setValue(zValue);  }
  public String getImporteover() throws Exception {     return pImporteover.getValue();  }
  public boolean isNullImporteover() throws Exception { return  pImporteover.isNull(); } 
  public void setNullToImporteover() throws Exception {  pImporteover.setNull(); } 
  public void setIvaover(double zValue) throws Exception {    pIvaover.setValue(zValue);  }
  public double getIvaover() throws Exception {     return pIvaover.getValue();  }
  public boolean isNullIvaover() throws Exception { return  pIvaover.isNull(); } 
  public void setNullToIvaover() throws Exception {  pIvaover.setNull(); } 
  public void setBoletoDescripcion(String zValue) throws Exception {    pBoletoDescripcion.setValue(zValue);  }
  public String getBoletoDescripcion() throws Exception {     return pBoletoDescripcion.getValue();  }
  public boolean isNullBoletoDescripcion() throws Exception { return  pBoletoDescripcion.isNull(); } 
  public void setNullToBoletoDescripcion() throws Exception {  pBoletoDescripcion.setNull(); } 
  public void setConsolidador(String zValue) throws Exception {    pConsolidador.setValue(zValue);  }
  public String getConsolidador() throws Exception {     return pConsolidador.getValue();  }
  public boolean isNullConsolidador() throws Exception { return  pConsolidador.isNull(); } 
  public void setNullToConsolidador() throws Exception {  pConsolidador.setNull(); } 
  public void setNumeroempleado(String zValue) throws Exception {    pNumeroempleado.setValue(zValue);  }
  public String getNumeroempleado() throws Exception {     return pNumeroempleado.getValue();  }
  public boolean isNullNumeroempleado() throws Exception { return  pNumeroempleado.isNull(); } 
  public void setNullToNumeroempleado() throws Exception {  pNumeroempleado.setNull(); } 
  public void setExpense(double zValue) throws Exception {    pExpense.setValue(zValue);  }
  public double getExpense() throws Exception {     return pExpense.getValue();  }
  public boolean isNullExpense() throws Exception { return  pExpense.isNull(); } 
  public void setNullToExpense() throws Exception {  pExpense.setNull(); } 
  public void setPorcExpense(double zValue) throws Exception {    pPorcExpense.setValue(zValue);  }
  public double getPorcExpense() throws Exception {     return pPorcExpense.getValue();  }
  public boolean isNullPorcExpense() throws Exception { return  pPorcExpense.isNull(); } 
  public void setNullToPorcExpense() throws Exception {  pPorcExpense.setNull(); } 
  public void setTipoboleto(String zValue) throws Exception {    pTipoboleto.setValue(zValue);  }
  public String getTipoboleto() throws Exception {     return pTipoboleto.getValue();  }
  public boolean isNullTipoboleto() throws Exception { return  pTipoboleto.isNull(); } 
  public void setNullToTipoboleto() throws Exception {  pTipoboleto.setNull(); } 
  public void setOversobre(String zValue) throws Exception {    pOversobre.setValue(zValue);  }
  public String getOversobre() throws Exception {     return pOversobre.getValue();  }
  public boolean isNullOversobre() throws Exception { return  pOversobre.isNull(); } 
  public void setNullToOversobre() throws Exception {  pOversobre.setNull(); } 
  public void setAplicacomparativo(String zValue) throws Exception {    pAplicacomparativo.setValue(zValue);  }
  public String getAplicacomparativo() throws Exception {     return pAplicacomparativo.getValue();  }
  public boolean isNullAplicacomparativo() throws Exception { return  pAplicacomparativo.isNull(); } 
  public void setNullToAplicacomparativo() throws Exception {  pAplicacomparativo.setNull(); } 
  public void setConsumed(String zValue) throws Exception {    pConsumed.setValue(zValue);  }
  public String getConsumed() throws Exception {     return pConsumed.getValue();  }
  public boolean isNullConsumed() throws Exception { return  pConsumed.isNull(); } 
  public void setNullToConsumed() throws Exception {  pConsumed.setNull(); } 
  public void setFechacreacion(Date zValue) throws Exception {    pFechacreacion.setValue(zValue);  }
  public Date getFechacreacion() throws Exception {     return pFechacreacion.getValue();  }
  public boolean isNullFechacreacion() throws Exception { return  pFechacreacion.isNull(); } 
  public void setNullToFechacreacion() throws Exception {  pFechacreacion.setNull(); } 
  public void setOrigen(String zValue) throws Exception {    pOrigen.setValue(zValue);  }
  public String getOrigen() throws Exception {     return pOrigen.getValue();  }
  public boolean isNullOrigen() throws Exception { return  pOrigen.isNull(); } 
  public void setNullToOrigen() throws Exception {  pOrigen.setNull(); } 
  public void setCentrocosto(String zValue) throws Exception {    pCentrocosto.setValue(zValue);  }
  public String getCentrocosto() throws Exception {     return pCentrocosto.getValue();  }
  public boolean isNullCentrocosto() throws Exception { return  pCentrocosto.isNull(); } 
  public void setNullToCentrocosto() throws Exception {  pCentrocosto.setNull(); } 
  public void setFechasalida(String zValue) throws Exception {    pFechasalida.setValue(zValue);  }
  public String getFechasalida() throws Exception {     return pFechasalida.getValue();  }
  public boolean isNullFechasalida() throws Exception { return  pFechasalida.isNull(); } 
  public void setNullToFechasalida() throws Exception {  pFechasalida.setNull(); } 
  public void setCodigopnr(String zValue) throws Exception {    pCodigopnr.setValue(zValue);  }
  public String getCodigopnr() throws Exception {     return pCodigopnr.getValue();  }
  public boolean isNullCodigopnr() throws Exception { return  pCodigopnr.isNull(); } 
  public void setNullToCodigopnr() throws Exception {  pCodigopnr.setNull(); } 
  public void setObservation(String zValue) throws Exception {    pObservation.setValue(zValue);  }
  public String getObservation() throws Exception {     return pObservation.getValue();  }
  public boolean isNullObservation() throws Exception { return  pObservation.isNull(); } 
  public void setNullToObservation() throws Exception {  pObservation.setNull(); } 
  public void setTipoair(String zValue) throws Exception {    pTipoair.setValue(zValue);  }
  public String getTipoair() throws Exception {     return pTipoair.getValue();  }
  public boolean isNullTipoair() throws Exception { return  pTipoair.isNull(); } 
  public void setNullToTipoair() throws Exception {  pTipoair.setNull(); } 
  public void setRetransmitido(String zValue) throws Exception {    pRetransmitido.setValue(zValue);  }
  public String getRetransmitido() throws Exception {     return pRetransmitido.getValue();  }
  public boolean isNullRetransmitido() throws Exception { return  pRetransmitido.isNull(); } 
  public void setNullToRetransmitido() throws Exception {  pRetransmitido.setNull(); } 
  public void setCantidadboletos(String zValue) throws Exception {    pCantidadboletos.setValue(zValue);  }
  public String getCantidadboletos() throws Exception {     return pCantidadboletos.getValue();  }
  public boolean isNullCantidadboletos() throws Exception { return  pCantidadboletos.isNull(); } 
  public void setNullToCantidadboletos() throws Exception {  pCantidadboletos.setNull(); } 
  public void setVendedor(String zValue) throws Exception {    pVendedor.setValue(zValue);  }
  public String getVendedor() throws Exception {     return pVendedor.getValue();  }
  public boolean isNullVendedor() throws Exception { return  pVendedor.isNull(); } 
  public void setNullToVendedor() throws Exception {  pVendedor.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setRegistrolocalizador(String zValue) throws Exception {    pRegistrolocalizador.setValue(zValue);  }
  public String getRegistrolocalizador() throws Exception {     return pRegistrolocalizador.getValue();  }
  public boolean isNullRegistrolocalizador() throws Exception { return  pRegistrolocalizador.isNull(); } 
  public void setNullToRegistrolocalizador() throws Exception {  pRegistrolocalizador.setNull(); } 
  public void setTransactiontype(String zValue) throws Exception {    pTransactiontype.setValue(zValue);  }
  public String getTransactiontype() throws Exception {     return pTransactiontype.getValue();  }
  public boolean isNullTransactiontype() throws Exception { return  pTransactiontype.isNull(); } 
  public void setNullToTransactiontype() throws Exception {  pTransactiontype.setNull(); } 
  public void setFechacreacionair(Date zValue) throws Exception {    pFechacreacionair.setValue(zValue);  }
  public Date getFechacreacionair() throws Exception {     return pFechacreacionair.getValue();  }
  public boolean isNullFechacreacionair() throws Exception { return  pFechacreacionair.isNull(); } 
  public void setNullToFechacreacionair() throws Exception {  pFechacreacionair.setNull(); } 
  public void setOfficeid(String zValue) throws Exception {    pOfficeid.setValue(zValue);  }
  public String getOfficeid() throws Exception {     return pOfficeid.getValue();  }
  public boolean isNullOfficeid() throws Exception { return  pOfficeid.isNull(); } 
  public void setNullToOfficeid() throws Exception {  pOfficeid.setNull(); } 
  public void setRuta(String zValue) throws Exception {    pRuta.setValue(zValue);  }
  public String getRuta() throws Exception {     return pRuta.getValue();  }
  public boolean isNullRuta() throws Exception { return  pRuta.isNull(); } 
  public void setNullToRuta() throws Exception {  pRuta.setNull(); } 
  public void setComandocrs(String zValue) throws Exception {    pComandocrs.setValue(zValue);  }
  public String getComandocrs() throws Exception {     return pComandocrs.getValue();  }
  public boolean isNullComandocrs() throws Exception { return  pComandocrs.isNull(); } 
  public void setNullToComandocrs() throws Exception {  pComandocrs.setNull(); } 
  public void setCodigocliente(String zValue) throws Exception {    pCodigocliente.setValue(zValue);  }
  public String getCodigocliente() throws Exception {     return pCodigocliente.getValue();  }
  public boolean isNullCodigocliente() throws Exception { return  pCodigocliente.isNull(); } 
  public void setNullToCodigocliente() throws Exception {  pCodigocliente.setNull(); } 
  public void setNombrecliente(String zValue) throws Exception {    pNombrecliente.setValue(zValue);  }
  public String getNombrecliente() throws Exception {     return pNombrecliente.getValue();  }
  public boolean isNullNombrecliente() throws Exception { return  pNombrecliente.isNull(); } 
  public void setNullToNombrecliente() throws Exception {  pNombrecliente.setNull(); } 
  public void setVersion(String zValue) throws Exception {    pVersion.setValue(zValue);  }
  public String getVersion() throws Exception {     return pVersion.getValue();  }
  public boolean isNullVersion() throws Exception { return  pVersion.isNull(); } 
  public void setNullToVersion() throws Exception {  pVersion.setNull(); } 
  public void setFechamodificacion(Date zValue) throws Exception {    pFechamodificacion.setValue(zValue);  }
  public Date getFechamodificacion() throws Exception {     return pFechamodificacion.getValue();  }
  public boolean isNullFechamodificacion() throws Exception { return  pFechamodificacion.isNull(); } 
  public void setNullToFechamodificacion() throws Exception {  pFechamodificacion.setNull(); } 
  public void setGds(String zValue) throws Exception {    pGds.setValue(zValue);  }
  public String getGds() throws Exception {     return pGds.getValue();  }
  public boolean isNullGds() throws Exception { return  pGds.isNull(); } 
  public void setNullToGds() throws Exception {  pGds.setNull(); } 
  public void setArchivo(String zValue) throws Exception {    pArchivo.setValue(zValue);  }
  public String getArchivo() throws Exception {     return pArchivo.getValue();  }
  public boolean isNullArchivo() throws Exception { return  pArchivo.isNull(); } 
  public void setNullToArchivo() throws Exception {  pArchivo.setNull(); } 
  public void setInterfaceId(long zValue) throws Exception {    pInterfaceId.setValue(zValue);  }
  public long getInterfaceId() throws Exception {     return pInterfaceId.getValue();  }
  public boolean isNullInterfaceId() throws Exception { return  pInterfaceId.isNull(); } 
  public void setNullToInterfaceId() throws Exception {  pInterfaceId.setNull(); } 
  public void setIndicadorventa(String zValue) throws Exception {    pIndicadorventa.setValue(zValue);  }
  public String getIndicadorventa() throws Exception {     return pIndicadorventa.getValue();  }
  public boolean isNullIndicadorventa() throws Exception { return  pIndicadorventa.isNull(); } 
  public void setNullToIndicadorventa() throws Exception {  pIndicadorventa.setNull(); } 
  public void setTipoPrestacion(String zValue) throws Exception {    pTipoPrestacion.setValue(zValue);  }
  public String getTipoPrestacion() throws Exception {     return pTipoPrestacion.getValue();  }
  public boolean isNullTipoPrestacion() throws Exception { return  pTipoPrestacion.isNull(); } 
  public void setNullToTipoPrestacion() throws Exception {  pTipoPrestacion.setNull(); } 
  public void setIata(String zValue) throws Exception {    pIata.setValue(zValue);  }
  public String getIata() throws Exception {     return pIata.getValue();  }
  public boolean isNullIata() throws Exception { return  pIata.isNull(); } 
  public void setNullToIata() throws Exception {  pIata.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizGds() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "tarifabase", pTarifabase );
    this.addItem( "tarifa_codmoneda", pTarifaCodmoneda );
    this.addItem( "codigobasemoneda", pCodigobasemoneda );
    this.addItem( "tarifa_sec", pTarifaSec );
    this.addItem( "tipodecambio", pTipodecambio );
    this.addItem( "tarifaconimpuestos", pTarifaconimpuestos );
    this.addItem( "tarifa", pTarifa );
    this.addItem( "fee_codmoneda", pFeeCodmoneda );
    this.addItem( "fee_importe", pFeeImporte );
    this.addItem( "fee_descripcion", pFeeDescripcion );
    this.addItem( "codigoentretenimiento", pCodigoentretenimiento );
    this.addItem( "horaarrivo", pHoraarrivo );
    this.addItem( "tipo_segmento", pTipoSegmento );
    this.addItem( "horadespegue", pHoradespegue );
    this.addItem( "despegue", pDespegue );
    this.addItem( "duracionvuelo", pDuracionvuelo );
    this.addItem( "arrivo", pArrivo );
    this.addItem( "carrier", pCarrier );
    this.addItem( "codigosegmento", pCodigosegmento );
    this.addItem( "fechaarrivo", pFechaarrivo );
    this.addItem( "estado", pEstado );
    this.addItem( "codigocomida", pCodigocomida );
    this.addItem( "clase", pClase );
    this.addItem( "tipoequipo", pTipoequipo );
    this.addItem( "segmento_ini", pSegmentoIni );
    this.addItem( "numerovuelo", pNumerovuelo );
    this.addItem( "fechadespegue", pFechadespegue );
    this.addItem( "numero", pNumero );
    this.addItem( "detalle", pDetalle );
    this.addItem( "codigoaerolineafqt", pCodigoaerolineafqt );
    this.addItem( "nombrepasajero", pNombrepasajero );
    this.addItem( "numeropasajerofqt", pNumeropasajerofqt );
    this.addItem( "tipopasajero", pTipopasajero );
    this.addItem( "pas_numeropasajero", pPasNumeropasajero );
    this.addItem( "pago_codmoneda", pPagoCodmoneda );
    this.addItem( "formapago", pFormapago );
    this.addItem( "codigoaprobacion", pCodigoaprobacion );
    this.addItem( "pago_importe", pPagoImporte );
    this.addItem( "numerotarjeta", pNumerotarjeta );
    this.addItem( "pago_sec", pPagoSec );
    this.addItem( "fechavencimiento", pFechavencimiento );
    this.addItem( "com_importe", pComImporte );
    this.addItem( "secuencia", pSecuencia );
    this.addItem( "comision_iva", pComisionIva );
    this.addItem( "porcentaje", pPorcentaje );
    this.addItem( "imp_codmoneda", pImpCodmoneda );
    this.addItem( "virtual", pVirtual );
    this.addItem( "imp_importe", pImpImporte );
    this.addItem( "imp_sec", pImpSec );
    this.addItem( "codigoimpuesto", pCodigoimpuesto );
    this.addItem( "importecedido", pImportecedido );
    this.addItem( "off_line", pOffLine );
    this.addItem( "tarifanormal", pTarifanormal );
    this.addItem( "neto", pNeto );
    this.addItem( "additional_fee", pAdditionalFee );
    this.addItem( "porcentajeover", pPorcentajeover );
    this.addItem( "void", pVoid );
    this.addItem( "devolucion", pDevolucion );
    this.addItem( "boletocambio", pBoletocambio );
    this.addItem( "iva_expense", pIvaExpense );
    this.addItem( "codigoaerolinea", pCodigoaerolinea );
    this.addItem( "it", pIt );
    this.addItem( "venta", pVenta );
    this.addItem( "over_cedido_iva_retenido", pOverCedidoiva_retenido );
    this.addItem( "reemision", pReemision );
    this.addItem( "numeroboleto", pNumeroboleto );
    this.addItem( "cantidadconectados", pCantidadconectados );
    this.addItem( "aplicacentro", pAplicacentro );
    this.addItem( "ajuste", pAjuste );
    this.addItem( "boleto_sec", pBoletoSec );
    this.addItem( "internacional", pInternacional );
    this.addItem( "referencia", pReferencia );
    this.addItem( "boleto_nropasajero", pBoletoNropasajero );
    this.addItem( "tarifaeconomica", pTarifaeconomica );
    this.addItem( "importeover", pImporteover );
    this.addItem( "ivaover", pIvaover );
    this.addItem( "boleto_descripcion", pBoletoDescripcion );
    this.addItem( "consolidador", pConsolidador );
    this.addItem( "numeroempleado", pNumeroempleado );
    this.addItem( "expense", pExpense );
    this.addItem( "porc_expense", pPorcExpense );
    this.addItem( "tipoboleto", pTipoboleto );
    this.addItem( "oversobre", pOversobre );
    this.addItem( "aplicacomparativo", pAplicacomparativo );
    this.addItem( "consumed", pConsumed );
    this.addItem( "fechacreacion", pFechacreacion );
    this.addItem( "origen", pOrigen );
    this.addItem( "centrocosto", pCentrocosto );
    this.addItem( "fechasalida", pFechasalida );
    this.addItem( "codigopnr", pCodigopnr );
    this.addItem( "observation", pObservation );
    this.addItem( "tipoair", pTipoair );
    this.addItem( "retransmitido", pRetransmitido );
    this.addItem( "cantidadboletos", pCantidadboletos );
    this.addItem( "vendedor", pVendedor );
    this.addItem( "company", pCompany );
    this.addItem( "registrolocalizador", pRegistrolocalizador );
    this.addItem( "transactiontype", pTransactiontype );
    this.addItem( "fechacreacionair", pFechacreacionair );
    this.addItem( "officeid", pOfficeid );
    this.addItem( "ruta", pRuta );
    this.addItem( "comandocrs", pComandocrs );
    this.addItem( "codigocliente", pCodigocliente );
    this.addItem( "nombrecliente", pNombrecliente );
    this.addItem( "version", pVersion );
    this.addItem( "fechamodificacion", pFechamodificacion );
    this.addItem( "gds", pGds );
    this.addItem( "archivo", pArchivo );
    this.addItem( "interface_id", pInterfaceId );
    this.addItem( "indicadorventa", pIndicadorventa );
    this.addItem( "tipo_prestacion", pTipoPrestacion );
    this.addItem( "iata", pIata );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "id sec", true, true, 18 );
    this.addFixedItem( FIELD, "tarifabase", "Tarifabase", true, true, 50 );
    this.addFixedItem( FIELD, "tarifa_codmoneda", "Tarifa codmoneda", true, true, 50 );
    this.addFixedItem( FIELD, "codigobasemoneda", "Codigo base moneda", true, true, 50 );
    this.addFixedItem( FIELD, "tarifa_sec", "Tarifa sec", true, true, 5 );
    this.addFixedItem( FIELD, "tipodecambio", "Tipo de cambio", true, true, 50 );
    this.addFixedItem( FIELD, "tarifaconimpuestos", "Tarifa con impuestos", true, true, 50 );
    this.addFixedItem( FIELD, "tarifa", "Tarifa", true, true, 50 );
    this.addFixedItem( FIELD, "fee_codmoneda", "Fee codmoneda", true, true, 50 );
    this.addFixedItem( FIELD, "fee_importe", "Fee importe", true, true, 50 );
    this.addFixedItem( FIELD, "fee_descripcion", "Fee descripcion", true, true, 50 );
    this.addFixedItem( FIELD, "codigoentretenimiento", "Codigo entre tenimiento", true, true, 100 );
    this.addFixedItem( FIELD, "horaarrivo", "Hora arrivo", true, true, 50 );
    this.addFixedItem( FIELD, "tipo_segmento", "Tipo segmento", true, true, 10 );
    this.addFixedItem( FIELD, "horadespegue", "Hora despegue", true, true, 50 );
    this.addFixedItem( FIELD, "despegue", "Despegue", true, true, 50 );
    this.addFixedItem( FIELD, "duracionvuelo", "Duracion vuelo", true, true, 100 );
    this.addFixedItem( FIELD, "arrivo", "Arrivo", true, true, 50 );
    this.addFixedItem( FIELD, "carrier", "Carrier", true, true, 50 );
    this.addFixedItem( FIELD, "codigosegmento", "Codigo segmento", true, true, 1 );
    this.addFixedItem( FIELD, "fechaarrivo", "Fecha arrivo", true, true, 50 );
    this.addFixedItem( FIELD, "estado", "Estado", true, true, 50 );
    this.addFixedItem( FIELD, "codigocomida", "Codigo comida", true, true, 100 );
    this.addFixedItem( FIELD, "clase", "Clase", true, true, 50 );
    this.addFixedItem( FIELD, "tipoequipo", "Tipo equipo", true, true, 50 );
    this.addFixedItem( FIELD, "segmento_ini", "Segmento inicial", true, true, 1 );
    this.addFixedItem( FIELD, "numerovuelo", "Numero vuelo", true, true, 50 );
    this.addFixedItem( FIELD, "fechadespegue", "Fecha despegue", true, true, 50 );
    this.addFixedItem( FIELD, "numero", "Numero", true, true, 50 );
    this.addFixedItem( FIELD, "detalle", "Detalle", true, true, 250 );
    this.addFixedItem( FIELD, "codigoaerolineafqt", "Codigo aerolinea fqt", true, true, 50 );
    this.addFixedItem( FIELD, "nombrepasajero", "Nombre pasajero", true, true, 50 );
    this.addFixedItem( FIELD, "numeropasajerofqt", "Numero pasajero fqt", true, true, 50 );
    this.addFixedItem( FIELD, "tipopasajero", "Tipo pasajero", true, true, 50 );
    this.addFixedItem( FIELD, "pas_numeropasajero", "numero pasajero", true, true, 50 );
    this.addFixedItem( FIELD, "pago_codmoneda", "Pago cod moneda", true, true, 50 );
    this.addFixedItem( FIELD, "formapago", "Forma pago", true, true, 50 );
    this.addFixedItem( FIELD, "codigoaprobacion", "Codigo aprobacion", true, true, 50 );
    this.addFixedItem( FIELD, "pago_importe", "Pago importe", true, true, 50 );
    this.addFixedItem( FIELD, "numerotarjeta", "Numero tarjeta", true, true, 50 );
    this.addFixedItem( FIELD, "pago_sec", "Pago id sec", true, true, 5 );
    this.addFixedItem( FIELD, "fechavencimiento", "Fecha vencimiento", true, true, 4 );
    this.addFixedItem( FIELD, "com_importe", "Comision importe", true, true, 50 );
    this.addFixedItem( FIELD, "secuencia", "Secuencia", true, true, 5 );
    this.addFixedItem( FIELD, "comision_iva", "Comision iva", true, true, 7,2 );
    this.addFixedItem( FIELD, "porcentaje", "Porcentaje", true, true, 50 );
    this.addFixedItem( FIELD, "imp_codmoneda", "Imp codmoneda", true, true, 50 );
    this.addFixedItem( FIELD, "virtual", "Virtual", true, true, 1 );
    this.addFixedItem( FIELD, "imp_importe", "Impuestos importe", true, true, 50 );
    this.addFixedItem( FIELD, "imp_sec", "Impuestos id sec", true, true, 5 );
    this.addFixedItem( FIELD, "codigoimpuesto", "Codigo impuesto", true, true, 50 );
    this.addFixedItem( FIELD, "importecedido", "Importe cedido", true, true, 50 );
    this.addFixedItem( FIELD, "off_line", "Off line", true, true, 50 );
    this.addFixedItem( FIELD, "tarifanormal", "Tarifa normal", true, true, 50 );
    this.addFixedItem( FIELD, "neto", "Neto", true, true, 50 );
    this.addFixedItem( FIELD, "additional_fee", "Additional fee", true, true, 50 );
    this.addFixedItem( FIELD, "porcentajeover", "Porcentaje over", true, true, 50 );
    this.addFixedItem( FIELD, "void", "Void", true, true, 1 );
    this.addFixedItem( FIELD, "devolucion", "Devolucion", true, true, 1 );
    this.addFixedItem( FIELD, "boletocambio", "Boleto cambio", true, true, 50 );
    this.addFixedItem( FIELD, "iva_expense", "Iva expense", true, true, 7,2 );
    this.addFixedItem( FIELD, "codigoaerolinea", "Codigo aerolinea", true, true, 50 );
    this.addFixedItem( FIELD, "it", "Boleto It", true, true, 50 );
    this.addFixedItem( FIELD, "venta", "Venta", true, true, 50 );
    this.addFixedItem( FIELD, "over_cedido_iva_retenido", "Over cedido iva retenido", true, true, 1 );
    this.addFixedItem( FIELD, "reemision", "Reemision", true, true, 1 );
    this.addFixedItem( FIELD, "numeroboleto", "Numero boleto", true, true, 50 );
    this.addFixedItem( FIELD, "cantidadconectados", "Cantidad conectados", true, true, 50 );
    this.addFixedItem( FIELD, "aplicacentro", "Aplica centro", true, true, 50 );
    this.addFixedItem( FIELD, "ajuste", "Ajuste", true, true, 1 );
    this.addFixedItem( FIELD, "boleto_sec", "Boleto sec", true, true, 5 );
    this.addFixedItem( FIELD, "internacional", "Internacional", true, true, 50 );
    this.addFixedItem( FIELD, "referencia", "Referencia", true, true, 50 );
    this.addFixedItem( FIELD, "boleto_nropasajero", "Boleto nropasajero", true, true, 50 );
    this.addFixedItem( FIELD, "tarifaeconomica", "Tarifa economica", true, true, 50 );
    this.addFixedItem( FIELD, "importeover", "Importe over", true, true, 50 );
    this.addFixedItem( FIELD, "ivaover", "Iva over", true, true, 7,2 );
    this.addFixedItem( FIELD, "boleto_descripcion", "Boleto descripcion", true, true, 50 );
    this.addFixedItem( FIELD, "consolidador", "Consolidador", true, true, 15 );
    this.addFixedItem( FIELD, "numeroempleado", "Numero empleado", true, true, 50 );
    this.addFixedItem( FIELD, "expense", "Expense", true, true, 10,2 );
    this.addFixedItem( FIELD, "porc_expense", "Porc expense", true, true, 7,2 );
    this.addFixedItem( FIELD, "tipoboleto", "Tipo boleto", true, true, 50 );
    this.addFixedItem( FIELD, "oversobre", "Over sobre", true, true, 50 );
    this.addFixedItem( FIELD, "aplicacomparativo", "Aplica comparativo", true, true, 50 );
    this.addFixedItem( FIELD, "consumed", "Consumed", true, true, 50 );
    this.addFixedItem( FIELD, "fechacreacion", "Fecha creacion", true, true, 10 );
    this.addFixedItem( FIELD, "origen", "Origen", true, true, 50 );
    this.addFixedItem( FIELD, "centrocosto", "Centro costo", true, true, 50 );
    this.addFixedItem( FIELD, "fechasalida", "Fecha salida", true, true, 50 );
    this.addFixedItem( FIELD, "codigopnr", "Codigo pnr", true, true, 50 );
    this.addFixedItem( FIELD, "observation", "Observation", true, true, 50 );
    this.addFixedItem( FIELD, "tipoair", "Tipoair", true, true, 50 );
    this.addFixedItem( FIELD, "retransmitido", "Retransmitido", true, true, 50 );
    this.addFixedItem( FIELD, "cantidadboletos", "Cantidad boletos", true, true, 50 );
    this.addFixedItem( FIELD, "vendedor", "Vendedor", true, true, 50 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 50 );
    this.addFixedItem( FIELD, "registrolocalizador", "Registro localizador", true, true, 50 );
    this.addFixedItem( FIELD, "transactiontype", "Transaction type", true, true, 1 );
    this.addFixedItem( FIELD, "fechacreacionair", "Fecha creacion air", true, true, 10 );
    this.addFixedItem( FIELD, "officeid", "Officeid", true, true, 50 );
    this.addFixedItem( FIELD, "ruta", "Ruta", true, true, 100 );
    this.addFixedItem( FIELD, "comandocrs", "Comandocrs", true, true, 50 );
    this.addFixedItem( FIELD, "codigocliente", "Codigo cliente", true, true, 50 );
    this.addFixedItem( FIELD, "nombrecliente", "Nombre cliente", true, true, 50 );
    this.addFixedItem( FIELD, "version", "Version", true, true, 50 );
    this.addFixedItem( FIELD, "fechamodificacion", "Fecha modificacion", true, true, 10 );
    this.addFixedItem( FIELD, "gds", "Gds", true, true, 50 );
    this.addFixedItem( FIELD, "archivo", "Archivo", true, true, 50 );
    this.addFixedItem( FIELD, "interface_id", "Interface id", true, true, 64 );
    this.addFixedItem( FIELD, "indicadorventa", "Indicador venta", true, true, 50 );
    this.addFixedItem( FIELD, "tipo_prestacion", "Tipo prestacion", true, true, 50 );
    this.addFixedItem( FIELD, "iata", "Iata", true, true, 50 );
  }
  
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.gds.GuiGdss");
		rels.addRelationParent(1, "BSP", BizArgDetalle.class, "BSP_PDF_ARG_DETALLE").addJoin("bsp_gds", "BSP_PDF_ARG_DETALLE.aerolinea_boleto");

//  	JRelation rel; 
//  	rel = rels.addRelationChild(12, "restriccion usuario");
//  	rel.addJoin("BSP_PDF_ARG_DETALLE.company", BizUsuario.getUsr().getCompany());
//  	rel.addJoin("BSP_PDF_ARG_DETALLE.owner", BizUsuario.getUsr().GetUsuario());
	  
//  	//rels.hideField("company");
//  	rels.hideField("owner");
  }

  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_gds"; }


//  @Override
//  public String GetTableTemporal() throws Exception {
//  	String s = "SELECT (ROW_NUMBER() OVER (order by tur_pnr_boleto.numeroboleto)) as id, iata,  tur_pnr.tipo_prestacion, indicadorventa, tur_pnr.interface_id,  archivo, gds, fechamodificacion, version, nombrecliente, codigocliente,  comandocrs, ruta, officeid, fechacreacionair, transactiontype,  registrolocalizador, tur_pnr.company, vendedor, cantidadboletos, retransmitido,  tipoair, observation, tur_pnr.codigopnr, fechasalida, centrocosto, origen,  fechacreacion, tur_pnr.consumed, aplicacomparativo, oversobre, tipoboleto, porc_expense,  expense,  numeroempleado, consolidador,  tur_pnr_boleto.descripcion as boleto_descripcion, ivaover, importeover, tarifaeconomica, tur_pnr_boleto.numeropasajero as boleto_nropasajero,  referencia, internacional, tur_pnr_boleto.secuencia as boleto_sec, ajuste, aplicacentro, cantidadconectados,  tur_pnr_boleto.numeroboleto,  reemision, over_cedido_iva_retenido, venta,  it, codigoaerolinea, iva_expense, boletocambio, devolucion,   void, porcentajeover, additional_fee, neto, tarifanormal, off_line,  importecedido, codigoimpuesto,  tur_pnr_impuestos.secuencia as imp_sec,  tur_pnr_impuestos.importe as imp_importe, virtual,  tur_pnr_impuestos.codigomoneda as imp_codmoneda, tur_pnr_comision.porcentaje, tur_pnr_comision.comision_iva,  tur_pnr_comision.secuencia,  tur_pnr_comision.importe as com_importe, 	tur_pnr_pagos.fechavencimiento, tur_pnr_pagos.secuencia as pago_sec,  numerotarjeta, tur_pnr_pagos.importe as pago_importe, codigoaprobacion, formapago,  tur_pnr_pagos.codigomoneda as pago_codmoneda, tur_pnr_pasajeros.numeropasajero as pas_numeropasajero, tipopasajero, numeropasajerofqt,  nombrepasajero,  codigoaerolineafqt, tur_pnr_remarks.detalle,  tur_pnr_remarks.numero, fechadespegue, numerovuelo, segmento_ini,  tipoequipo, clase, codigocomida,  estado, fechaarrivo,  codigosegmento, carrier, arrivo, duracionvuelo, despegue, horadespegue,  tipo_segmento, horaarrivo, codigoentretenimiento, tur_pnr_serviciosfee.descripcion as fee_descripcion, tur_pnr_serviciosfee.importe as fee_importe, tur_pnr_serviciosfee.codigomoneda as fee_codmoneda, tarifa, tarifaconimpuestos, tipodecambio,  tur_pnr_tarifas.secuencia as tarifa_sec, tur_pnr_tarifas.codigobasemoneda, tur_pnr_tarifas.codigomoneda as tarifa_codmoneda,  tur_pnr_tarifas.tarifabase FROM tur_pnr  LEFT JOIN tur_pnr_boleto on tur_pnr.interface_id=tur_pnr_boleto.interface_id and tur_pnr.codigopnr=tur_pnr_boleto.codigopnr LEFT JOIN tur_pnr_comision on tur_pnr.company=tur_pnr_comision.company and tur_pnr.codigopnr=tur_pnr_comision.codigopnr and tur_pnr.interface_id=tur_pnr_comision.interface_id and tur_pnr_boleto.numeroboleto=tur_pnr_comision.numeroboleto LEFT JOIN tur_pnr_impuestos on tur_pnr.company=tur_pnr_impuestos.company and tur_pnr.codigopnr=tur_pnr_impuestos.codigopnr and tur_pnr.interface_id=tur_pnr_impuestos.interface_id and tur_pnr_boleto.numeroboleto=tur_pnr_impuestos.numeroboleto LEFT JOIN tur_pnr_pagos on tur_pnr.company=tur_pnr_pagos.company and tur_pnr.codigopnr=tur_pnr_pagos.codigopnr and tur_pnr.interface_id=tur_pnr_pagos.interface_id and tur_pnr_boleto.numeroboleto=tur_pnr_pagos.numeroboleto LEFT JOIN tur_pnr_tarifas on tur_pnr.company=tur_pnr_tarifas.company and tur_pnr.codigopnr=tur_pnr_tarifas.codigopnr and tur_pnr.interface_id=tur_pnr_tarifas.interface_id and tur_pnr_boleto.numeroboleto=tur_pnr_tarifas.numeroboleto LEFT JOIN tur_pnr_serviciosfee on tur_pnr.company=tur_pnr_serviciosfee.company and tur_pnr.codigopnr=tur_pnr_serviciosfee.codigopnr and tur_pnr.interface_id=tur_pnr_serviciosfee.interface_id LEFT JOIN tur_pnr_pasajeros on tur_pnr.company=tur_pnr_pasajeros.company and tur_pnr.codigopnr=tur_pnr_pasajeros.codigopnr and tur_pnr.interface_id=tur_pnr_pasajeros.interface_id LEFT JOIN tur_pnr_remarks on tur_pnr.company=tur_pnr_remarks.company and tur_pnr.codigopnr=tur_pnr_remarks.codigopnr and tur_pnr.interface_id=tur_pnr_remarks.interface_id LEFT JOIN tur_pnr_segmentoaereo on tur_pnr.company=tur_pnr_segmentoaereo.company and tur_pnr.codigopnr=tur_pnr_segmentoaereo.codigopnr and tur_pnr.interface_id=tur_pnr_segmentoaereo.interface_id";
//  	return "("+s+") as "+GetTable() ;
//  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "id",  zId ); 
    return read(); 
  } 
}
