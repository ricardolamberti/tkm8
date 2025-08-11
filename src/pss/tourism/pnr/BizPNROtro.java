package pss.tourism.pnr;

import java.io.File;
import java.util.Date;

import pss.JPath;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.hoteles.BizHotel;
import pss.bsp.hoteles.detalle.BizHotelDetail;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.mailing.BizMailingPersona;
import pss.common.regions.company.BizCompany;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.tourism.interfaceGDS.FileProcessor;
import pss.tourism.interfaceGDS.amadeus.AmadeusFileProcessor;
import pss.tourism.interfaceGDS.sabre.SabreFileProcessor;
import pss.tourism.interfaceGDS.travelport.TravelPortFileProcessor;

public class BizPNROtro extends JRecord {

  private JLong pSecuenceId = new JLong();
  
	private JString pNroIata = new JString();
	private JString pOfficeId = new JString();
	private JString pCityCode = new JString();
	private JDate pPNRDate = new JDate();
	private JDate pCreationDate = new JDate();
	private JDate pCreationDateAir = new JDate();
	private JString pDirectory = new JString();
	private JDate pFechaProcesamiento = new JDate();
	private JString pTransactionType = new JString();
	private JString pPais = new JString();
	private JString pArchivo = new JString();
	private JString pVersion = new JString();
	private JString pGDS = new JString();
	private JString pOrigen = new JString();
	private JString pCustomerId = new JString();
	private JString pCustomerIdReducido = new JString();
	private JString pTipoPrestacion = new JString();
	private JBoolean pVoid = new JBoolean();
	private JLong pGrupo = new JLong();

	
	private JString pCodigopnr = new JString();
	private JString pNombrePasajero = new JString();
	private JLong   pCodigoSegmento = new JLong();
  private JString pProductCode = new JString();
  private JString pProductDescr = new JString();
  private JString pControlData = new JString();
  private JString pAction = new JString();
  private JDate pFechaReserva = new JDate();
  private JString pProductoSecundario = new JString();
  private JString pServiceCode = new JString();
  private JString pServiceType = new JString();
  private JLong pPasajeros = new JLong();
  private JLong pHabitaciones = new JLong();
  private JLong pNroAutos = new JLong();
  private JString pProductType = new JString();
  private JString pConfirmacion = new JString();
  private JString pAeropuerto = new JString();
  private JString pHotelCodigo = new JString();
  private JString pHotelFName = new JString();
  private JDate pFechaSalida = new JDate();
  private JLong pNoches = new JLong();
  private JString pTourName = new JString();
  private JString pHotelName = new JString();
  private JString pCodigoProducto = new JString();
  
  private JString pMonedaBase = new JString();
  private JString pMonedaLocal = new JString();
  private JString pMonedaImporte = new JString();
	
	private JCurrency pImporteBase=new JCurrency(){
		public void preset()throws Exception {
			setSimbolo(true);
			setMoneda(pMonedaBase.getValue());
		};
	};
	
  private JCurrency pImporteBaseUsd=new JCurrency(){
  	public void preset()throws Exception{
  		setSimbolo(true);setMoneda("USD");
  	};
  };
  
  private JCurrency pImporteBaseLocal=new JCurrency(){
  	public void preset()throws Exception{
  		setSimbolo(true);setMoneda(pMonedaLocal.getValue());
  	};
  };
  

  private JCurrency pImporte=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda(pMonedaBase.getValue());};};
  private JCurrency pImporteUsd=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda("USD");};};
  private JCurrency pImporteLocal=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda(pMonedaLocal.getValue());};};

  private JCurrency pTasas=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda(pMonedaBase.getValue());};};
  private JCurrency pTasasUsd=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda("USD");};};
  private JCurrency pTasasLocal=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda(pMonedaLocal.getValue());};};
  private JString pMonedaTasas = new JString();

  private JCurrency pFee=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda(pMonedaBase.getValue());};};
  private JCurrency pFeeUsd=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda("USD");};};
  private JCurrency pFeeLocal=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda(pMonedaLocal.getValue());};};
  private JString pMonedaFee = new JString();

  private JCurrency pPrecioTotal=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda(pMonedaBase.getValue());};};
  private JCurrency pPrecioTotalUsd=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda("USD");};};
  private JCurrency pPrecioTotalLocal=new JCurrency(){public void preset()throws Exception{setSimbolo(true);setMoneda(pMonedaLocal.getValue());};};
	private JString pMonedaPrecioTotal = new JString();
    
	private JString pDomicilio = new JString();
  private JString pTelefono = new JString();
  private JString pFax = new JString();
  private JString pCodigoConfirmacion = new JString();
  private JString pComisionCodigo = new JString();
  private JString pComisionTexto = new JString();
  private JFloat pComisionMonto = new JFloat();
  private JString pTipoTarjeta = new JString();
  private JString pTarjeta = new JString();
  private JString pTaxBreakdown1 = new JString();
  private JString pTaxBreakdown2 = new JString();
  private JString pTaxBreakdown3 = new JString();
  private JString pTaxBreakdown4 = new JString();
  private JString pSurchange1 = new JString();
  private JString pSurchange2 = new JString();
  private JString pSurchange3 = new JString();
  private JString pSurchange4 = new JString();
  private JString pDisclaimer1 = new JString();
  private JString pDisclaimer2 = new JString();
  private JString pPickupPoint = new JString();
  private JDate pDropOffdate = new JDate();
  private JString pCarType = new JString();
  private JString pUpgrade = new JString();
  private JString pArriboHora = new JString();
  private JString pRetorno = new JString();
  private JString pDopOfflocation = new JString();
  private JString pDropOffcharge = new JString();
  private JString pGarantia = new JString();
  private JString pCorporateDiscount = new JString();
  private JString pTourcode = new JString();
  private JString pEquipamiento = new JString();
  private JString pRemarks = new JString();
  private JString pPasajeroFrecuente = new JString();
  private JString pNombreCliente = new JString();
  private JString pCupon = new JString();
  private JString pTasaGarantia = new JString();
  private JString pBookingSource = new JString();
  private JString pRateCode = new JString();
  private JString pVehiculoProveedor = new JString();
  private JString pVoucherTipo = new JString();
  private JString pVoucherNumero = new JString();
  private JString pVoucherBiling = new JString();
  private JString pVoucherFormat = new JString();
  private JString pPaisOrigen = new JString();
  private JString pPaisDestino = new JString();
  private JString pInfo = new JString();
  private JString pEquipamientoConfirmado = new JString();
  private JString pGarantiaInfo = new JString();
  private JString pVendedor = new JString();
  private JString pCompany = new JString();

	JString pOrder = new JString();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setSecuenceId(long zValue) throws Exception {    pSecuenceId.setValue(zValue);  }
  public long getSecuenceId() throws Exception {     return pSecuenceId.getValue();  }
  public boolean isNullSecuenceId() throws Exception { return  pSecuenceId.isNull(); } 
  public void setNullToSecuenceId() throws Exception {  pSecuenceId.setNull(); } 
  public void setProductDescr(String zValue) throws Exception {    pProductDescr.setValue(zValue);  }
  public String getProductDescr() throws Exception {     return pProductDescr.getValue();  }
  public void setProductCode(String zValue) throws Exception {    pProductCode.setValue(zValue);  }
  public String getProductCode() throws Exception {     return pProductCode.getValue();  }
  public boolean isNullProductCode() throws Exception { return  pProductCode.isNull(); } 
  public void setNullToProductCode() throws Exception {  pProductCode.setNull(); } 
  public void setControlData(String zValue) throws Exception {    pControlData.setValue(zValue);  }
  public String getControlData() throws Exception {     return pControlData.getValue();  }
  public boolean isNullControlData() throws Exception { return  pControlData.isNull(); } 
  public void setNullToControlData() throws Exception {  pControlData.setNull(); } 
  public void setAction(String zValue) throws Exception {    pAction.setValue(zValue);  }
  public String getAction() throws Exception {     return pAction.getValue();  }
  public boolean isNullAction() throws Exception { return  pAction.isNull(); } 
  public void setNullToAction() throws Exception {  pAction.setNull(); } 
  public void setFechaReserva(Date zValue) throws Exception {    pFechaReserva.setValue(zValue);  }
  public Date getFechaReserva() throws Exception {     return pFechaReserva.getValue();  }
  public boolean isNullFechaReserva() throws Exception { return  pFechaReserva.isNull(); } 
  public void setNullToFechaReserva() throws Exception {  pFechaReserva.setNull(); } 
  public void setProductoSecundario(String zValue) throws Exception {    pProductoSecundario.setValue(zValue);  }
  public String getProductoSecundario() throws Exception {     return pProductoSecundario.getValue();  }
  public boolean isNullProductoSecundario() throws Exception { return  pProductoSecundario.isNull(); } 
  public void setNullToProductoSecundario() throws Exception {  pProductoSecundario.setNull(); } 
  public void setServiceCode(String zValue) throws Exception {    pServiceCode.setValue(zValue);  }
  public String getServiceCode() throws Exception {     return pServiceCode.getValue();  }
  public void setServiceType(String zValue) throws Exception {    pServiceType.setValue(zValue);  }
  public String getServiceType() throws Exception {     return pServiceType.getValue();  }
  public void setPasajeros(long zValue) throws Exception {    pPasajeros.setValue(zValue);  }
  public long getPasajeros() throws Exception {     return pPasajeros.getValue();  }
  public boolean isNullPasajeros() throws Exception { return  pPasajeros.isNull(); } 
  public void setNullToPasajeros() throws Exception {  pPasajeros.setNull(); } 
  public void setHabitaciones(long zValue) throws Exception {    pHabitaciones.setValue(zValue);  }
  public long getHabitaciones() throws Exception {     return pHabitaciones.getValue();  }
  public boolean isNullHabitaciones() throws Exception { return  pHabitaciones.isNull(); } 
  public void setNullToHabitaciones() throws Exception {  pHabitaciones.setNull(); } 
  public void setNroAutos(long zValue) throws Exception {    pNroAutos.setValue(zValue);  }
  public long getNroAutos() throws Exception {     return pNroAutos.getValue();  }
  public boolean isNullNroAutos() throws Exception { return  pNroAutos.isNull(); } 
  public void setNullToNroAutos() throws Exception {  pNroAutos.setNull(); } 
  public void setProductType(String zValue) throws Exception {    pProductType.setValue(zValue);  }
  public String getProductType() throws Exception {     return pProductType.getValue();  }
  public boolean isNullProductType() throws Exception { return  pProductType.isNull(); } 
  public void setNullToProductType() throws Exception {  pProductType.setNull(); } 
  public void setConfirmacion(String zValue) throws Exception {    pConfirmacion.setValue(zValue);  }
  public String getConfirmacion() throws Exception {     return pConfirmacion.getValue();  }
  public boolean isNullConfirmacion() throws Exception { return  pConfirmacion.isNull(); } 
  public void setNullToConfirmacion() throws Exception {  pConfirmacion.setNull(); } 
  public void setAeropuerto(String zValue) throws Exception {    pAeropuerto.setValue(zValue);  }
  public String getAeropuerto() throws Exception {     return pAeropuerto.getValue();  }
  public boolean isNullAeropuerto() throws Exception { return  pAeropuerto.isNull(); } 
  public void setNullToAeropuerto() throws Exception {  pAeropuerto.setNull(); } 
  public void setHotelFName(String zValue) throws Exception {    pHotelFName.setValue(zValue);  }
  public String getHotelFName() throws Exception {     return pHotelFName.getValue();  }
  public void setHotelCodigo(String zValue) throws Exception {    pHotelCodigo.setValue(zValue);  }
  public String getHotelCodigo() throws Exception {     return pHotelCodigo.getValue();  }
  public boolean isNullHotelCodigo() throws Exception { return  pHotelCodigo.isNull(); } 
  public void setNullToHotelCodigo() throws Exception {  pHotelCodigo.setNull(); } 
  public void setFechaSalida(Date zValue) throws Exception {    pFechaSalida.setValue(zValue);  }
  public Date getFechaSalida() throws Exception {     return pFechaSalida.getValue();  }
  public boolean isNullFechaSalida() throws Exception { return  pFechaSalida.isNull(); } 
  public void setNullToFechaSalida() throws Exception {  pFechaSalida.setNull(); } 
  public void setNoches(long zValue) throws Exception {    pNoches.setValue(zValue);  }
  public long getNoches() throws Exception {     return pNoches.getValue();  }
  public boolean isNullNoches() throws Exception { return  pNoches.isNull(); } 
  public void setNullToNoches() throws Exception {  pNoches.setNull(); } 
  public void setTourName(String zValue) throws Exception {    pTourName.setValue(zValue);  }
  public String getTourName() throws Exception {     return pTourName.getValue();  }
  public boolean isNullTourName() throws Exception { return  pTourName.isNull(); } 
  public void setNullToTourName() throws Exception {  pTourName.setNull(); } 
  public void setHotelName(String zValue) throws Exception {    pHotelName.setValue(zValue);  }
  public String getHotelName() throws Exception {     return pHotelName.getValue();  }
  public boolean isNullHotelName() throws Exception { return  pHotelName.isNull(); } 
  public void setNullToHotelName() throws Exception {  pHotelName.setNull(); } 
  public void setCodigoProducto(String zValue) throws Exception {    pCodigoProducto.setValue(zValue);  }
  public String getCodigoProducto() throws Exception {     return pCodigoProducto.getValue();  }
  public boolean isNullCodigoProducto() throws Exception { return  pCodigoProducto.isNull(); } 
  public void setNullToCodigoProducto() throws Exception {  pCodigoProducto.setNull(); } 
 
  public void setGrupo(long zValue) throws Exception {    pGrupo.setValue(zValue);  }
  public long getGrupo() throws Exception {     return pGrupo.getValue();  }
  public boolean isNullGrupo() throws Exception { return  pGrupo.isNull(); } 
  public void setNullToGrupo() throws Exception {  pGrupo.setNull(); } 

  public void setImporteBase(double zValue) throws Exception {    pImporteBase.setValue(zValue);  }
  public double getImporteBase() throws Exception {     return pImporteBase.getValue();  }
  public boolean isNullImporteBase() throws Exception { return  pImporteBase.isNull(); } 
  public void setNullToImporteBase() throws Exception {  pImporteBase.setNull(); } 

  public void setImporteBaseUsd(double zValue) throws Exception {    pImporteBaseUsd.setValue(zValue);  }
  public double getImporteBaseUsd() throws Exception {     return pImporteBaseUsd.getValue();  }
  public boolean isNullImporteBaseUsd() throws Exception { return  pImporteBaseUsd.isNull(); } 
  public void setNullToImporteBaseUsd() throws Exception {  pImporteBaseUsd.setNull(); } 

  public void setImporteBaseLocal(double zValue) throws Exception {    pImporteBaseLocal.setValue(zValue);  }
  public double getImporteBaseLocal() throws Exception {     return pImporteBaseLocal.getValue();  }
  public boolean isNullImporteBaseLocal() throws Exception { return  pImporteBaseLocal.isNull(); } 
  public void setNullToImporteBaseLocal() throws Exception {  pImporteBaseLocal.setNull(); } 

  public void setMonedaBase(String zValue) throws Exception {    pMonedaBase.setValue(zValue);  }
  public String getMonedaBase() throws Exception {     return pMonedaBase.getValue();  }
  public boolean isNullMonedaBase() throws Exception { return  pMonedaBase.isNull(); } 
  public void setNullToMonedaBase() throws Exception {  pMonedaBase.setNull(); } 

  public void setMonedaLocal(String zValue) throws Exception {    pMonedaLocal.setValue(zValue);  }
  public String getMonedaLocal() throws Exception {     return pMonedaLocal.getValue();  }
  public boolean isNullMonedaLocal() throws Exception { return  pMonedaLocal.isNull(); } 
  public void setNullToMonedaLocal() throws Exception {  pMonedaLocal.setNull(); } 

  public void setMonedaImporte(String zValue) throws Exception {    pMonedaImporte.setValue(zValue);  }
  public String getMonedaImporte() throws Exception {     return pMonedaImporte.getValue();  }
  public boolean isNullMonedaImporte() throws Exception { return  pMonedaImporte.isNull(); } 
  public void setNullToMonedaImporte() throws Exception {  pMonedaImporte.setNull(); } 

  public void setImporte(double zValue) throws Exception {    pImporte.setValue(zValue);  }
  public double getImporte() throws Exception {     return pImporte.getValue();  }
  public boolean isNullImporte() throws Exception { return  pImporte.isNull(); } 
  public void setNullToImporte() throws Exception {  pImporte.setNull(); } 
  
  public void setImporteUsd(double zValue) throws Exception {    pImporteUsd.setValue(zValue);  }
  public double getImporteUsd() throws Exception {     return pImporteUsd.getValue();  }
  public boolean isNullImporteUsd() throws Exception { return  pImporteUsd.isNull(); } 
  public void setNullToImporteUsd() throws Exception {  pImporteUsd.setNull(); } 

  public void setImporteLocal(double zValue) throws Exception {    pImporteLocal.setValue(zValue);  }
  public double getImporteLocal() throws Exception {     return pImporteLocal.getValue();  }
  public boolean isNullImporteLocal() throws Exception { return  pImporteLocal.isNull(); } 
  public void setNullToImporteLocal() throws Exception {  pImporteLocal.setNull(); } 

  public void setTasas(double zValue) throws Exception {    pTasas.setValue(zValue);  }
  public double getTasas() throws Exception {     return pTasas.getValue();  }
  public boolean isNullTasas() throws Exception { return  pTasas.isNull(); } 
  public void setNullToTasas() throws Exception {  pTasas.setNull(); } 

  public void setTasasUsd(double zValue) throws Exception {    pTasasUsd.setValue(zValue);  }
  public double getTasasUsd() throws Exception {     return pTasasUsd.getValue();  }
  public boolean isNullTasasUsd() throws Exception { return  pTasasUsd.isNull(); } 
  public void setNullToTasasUsd() throws Exception {  pTasasUsd.setNull(); } 

  public void setTasasLocal(double zValue) throws Exception {    pTasasLocal.setValue(zValue);  }
  public double getTasasLocal() throws Exception {     return pTasasLocal.getValue();  }
  public boolean isNullTasasLocal() throws Exception { return  pTasasLocal.isNull(); } 
  public void setNullToTasasLocal() throws Exception {  pTasasLocal.setNull(); } 

  public void setMonedaTasas(String zValue) throws Exception {    pMonedaTasas.setValue(zValue);  }
  public String getMonedaTasas() throws Exception {     return pMonedaTasas.getValue();  }
  public boolean isNullMonedaTasas() throws Exception { return  pMonedaTasas.isNull(); } 
  public void setNullToMonedaTasas() throws Exception {  pMonedaTasas.setNull(); } 

  public void setMonedaFee(String zValue) throws Exception {    pMonedaFee.setValue(zValue);  }
  public String getMonedaFee() throws Exception {     return pMonedaFee.getValue();  }
  public boolean isNullMonedaFee() throws Exception { return  pMonedaFee.isNull(); } 
  public void setNullToMonedaFee() throws Exception {  pMonedaFee.setNull(); } 


  public void setFee(double zValue) throws Exception {    pFee.setValue(zValue);  }
  public double getFee() throws Exception {     return pFee.getValue();  }
  public boolean isNullFee() throws Exception { return  pFee.isNull(); } 
  public void setNullToFee() throws Exception {  pFee.setNull(); } 

  public void setFeeUsd(double zValue) throws Exception {    pFeeUsd.setValue(zValue);  }
  public double getFeeUsd() throws Exception {     return pFeeUsd.getValue();  }
  public boolean isNullFeeUsd() throws Exception { return  pFeeUsd.isNull(); } 
  public void setNullToFeeUsd() throws Exception {  pFeeUsd.setNull(); } 

  public void setFeeLocal(double zValue) throws Exception {    pFeeLocal.setValue(zValue);  }
  public double getFeeLocal() throws Exception {     return pFeeLocal.getValue();  }
  public boolean isNullFeeLocal() throws Exception { return  pFeeLocal.isNull(); } 
  public void setNullToFeeLocal() throws Exception {  pFeeLocal.setNull(); } 

  public void setPrecioTotal(double zValue) throws Exception {    pPrecioTotal.setValue(zValue);  }
  public double getPrecioTotal() throws Exception {     return pPrecioTotal.getValue();  }
  public boolean isNullPrecioTotal() throws Exception { return  pPrecioTotal.isNull(); } 
  public void setNullToPrecioTotal() throws Exception {  pPrecioTotal.setNull(); } 
  
  public void setMonedaPrecioTotal(String zValue) throws Exception {    pMonedaPrecioTotal.setValue(zValue);  }
  public String getMonedaPrecioTotal() throws Exception {     return pMonedaPrecioTotal.getValue();  }
  public boolean isNullMonedaPrecioTotal() throws Exception { return  pMonedaPrecioTotal.isNull(); } 
  public void setNullToMonedaPrecioTotal() throws Exception {  pMonedaPrecioTotal.setNull(); } 
  
  public void setPrecioTotalUsd(double zValue) throws Exception {    pPrecioTotalUsd.setValue(zValue);  }
  public double getPrecioTotalUsd() throws Exception {     return pPrecioTotalUsd.getValue();  }
  public boolean isNullPrecioTotalUsd() throws Exception { return  pPrecioTotalUsd.isNull(); } 
  public void setNullToPrecioTotalUsd() throws Exception {  pPrecioTotalUsd.setNull(); } 

  public void setPrecioTotalLocal(double zValue) throws Exception {    pPrecioTotalLocal.setValue(zValue);  }
  public double getPrecioTotalLocal() throws Exception {     return pPrecioTotalLocal.getValue();  }
  public boolean isNullPrecioTotalLocal() throws Exception { return  pPrecioTotalLocal.isNull(); } 
  public void setNullToPrecioTotalLocal() throws Exception {  pPrecioTotalLocal.setNull(); } 
  
  public void setDomicilio(String zValue) throws Exception {    pDomicilio.setValue(zValue);  }
  public String getDomicilio() throws Exception {     return pDomicilio.getValue();  }
  public boolean isNullDomicilio() throws Exception { return  pDomicilio.isNull(); } 
  public void setNullToDomicilio() throws Exception {  pDomicilio.setNull(); } 
  public void setTelefono(String zValue) throws Exception {    pTelefono.setValue(zValue);  }
  public String getTelefono() throws Exception {     return pTelefono.getValue();  }
  public boolean isNullTelefono() throws Exception { return  pTelefono.isNull(); } 
  public void setNullToTelefono() throws Exception {  pTelefono.setNull(); } 
  public void setFax(String zValue) throws Exception {    pFax.setValue(zValue);  }
  public String getFax() throws Exception {     return pFax.getValue();  }
  public boolean isNullFax() throws Exception { return  pFax.isNull(); } 
  public void setNullToFax() throws Exception {  pFax.setNull(); } 
  public void setCodigoConfirmacion(String zValue) throws Exception {    pCodigoConfirmacion.setValue(zValue);  }
  public String getCodigoConfirmacion() throws Exception {     return pCodigoConfirmacion.getValue();  }
  public boolean isNullCodigoConfirmacion() throws Exception { return  pCodigoConfirmacion.isNull(); } 
  public void setNullToCodigoConfirmacion() throws Exception {  pCodigoConfirmacion.setNull(); } 
  public void setComisionCodigo(String zValue) throws Exception {    pComisionCodigo.setValue(zValue);  }
  public String getComisionCodigo() throws Exception {     return pComisionCodigo.getValue();  }
  public boolean isNullComisionCodigo() throws Exception { return  pComisionCodigo.isNull(); } 
  public void setNullToComisionCodigo() throws Exception {  pComisionCodigo.setNull(); } 
  public void setComisionTexto(String zValue) throws Exception {    pComisionTexto.setValue(zValue);  }
  public String getComisionTexto() throws Exception {     return pComisionTexto.getValue();  }
  public boolean isNullComisionTexto() throws Exception { return  pComisionTexto.isNull(); } 
  public void setNullToComisionTexto() throws Exception {  pComisionTexto.setNull(); } 
  public void setComisionMonto(double zValue) throws Exception {    pComisionMonto.setValue(zValue);  }
  public double getComisionMonto() throws Exception {     return pComisionMonto.getValue();  }
  public boolean isNullComisionMonto() throws Exception { return  pComisionMonto.isNull(); } 
  public void setNullToComisionMonto() throws Exception {  pComisionMonto.setNull(); } 
  public void setTipoTarjeta(String zValue) throws Exception {    pTipoTarjeta.setValue(zValue);  }
  public String getTipoTarjeta() throws Exception {     return pTipoTarjeta.getValue();  }
  public boolean isNullTipoTarjeta() throws Exception { return  pTipoTarjeta.isNull(); } 
  public void setNullToTipoTarjeta() throws Exception {  pTipoTarjeta.setNull(); } 
  public void setTarjeta(String zValue) throws Exception {    pTarjeta.setValue(zValue);  }
  public String getTarjeta() throws Exception {     return pTarjeta.getValue();  }
  public boolean isNullTarjeta() throws Exception { return  pTarjeta.isNull(); } 
  public void setNullToTarjeta() throws Exception {  pTarjeta.setNull(); } 
  public void setTaxBreakdown1(String zValue) throws Exception {    pTaxBreakdown1.setValue(zValue);  }
  public String getTaxBreakdown1() throws Exception {     return pTaxBreakdown1.getValue();  }
  public boolean isNullTaxBreakdown1() throws Exception { return  pTaxBreakdown1.isNull(); } 
  public void setNullToTaxBreakdown1() throws Exception {  pTaxBreakdown1.setNull(); } 
  public void setTaxBreakdown2(String zValue) throws Exception {    pTaxBreakdown2.setValue(zValue);  }
  public String getTaxBreakdown2() throws Exception {     return pTaxBreakdown2.getValue();  }
  public boolean isNullTaxBreakdown2() throws Exception { return  pTaxBreakdown2.isNull(); } 
  public void setNullToTaxBreakdown2() throws Exception {  pTaxBreakdown2.setNull(); } 
  public void setTaxBreakdown3(String zValue) throws Exception {    pTaxBreakdown3.setValue(zValue);  }
  public String getTaxBreakdown3() throws Exception {     return pTaxBreakdown3.getValue();  }
  public boolean isNullTaxBreakdown3() throws Exception { return  pTaxBreakdown3.isNull(); } 
  public void setNullToTaxBreakdown3() throws Exception {  pTaxBreakdown3.setNull(); } 
  public void setTaxBreakdown4(String zValue) throws Exception {    pTaxBreakdown4.setValue(zValue);  }
  public String getTaxBreakdown4() throws Exception {     return pTaxBreakdown4.getValue();  }
  public boolean isNullTaxBreakdown4() throws Exception { return  pTaxBreakdown4.isNull(); } 
  public void setNullToTaxBreakdown4() throws Exception {  pTaxBreakdown4.setNull(); } 
  public void setSurchange1(String zValue) throws Exception {    pSurchange1.setValue(zValue);  }
  public String getSurchange1() throws Exception {     return pSurchange1.getValue();  }
  public boolean isNullSurchange1() throws Exception { return  pSurchange1.isNull(); } 
  public void setNullToSurchange1() throws Exception {  pSurchange1.setNull(); } 
  public void setSurchange2(String zValue) throws Exception {    pSurchange2.setValue(zValue);  }
  public String getSurchange2() throws Exception {     return pSurchange2.getValue();  }
  public boolean isNullSurchange2() throws Exception { return  pSurchange2.isNull(); } 
  public void setNullToSurchange2() throws Exception {  pSurchange2.setNull(); } 
  public void setSurchange3(String zValue) throws Exception {    pSurchange3.setValue(zValue);  }
  public String getSurchange3() throws Exception {     return pSurchange3.getValue();  }
  public boolean isNullSurchange3() throws Exception { return  pSurchange3.isNull(); } 
  public void setNullToSurchange3() throws Exception {  pSurchange3.setNull(); } 
  public void setSurchange4(String zValue) throws Exception {    pSurchange4.setValue(zValue);  }
  public String getSurchange4() throws Exception {     return pSurchange4.getValue();  }
  public boolean isNullSurchange4() throws Exception { return  pSurchange4.isNull(); } 
  public void setNullToSurchange4() throws Exception {  pSurchange4.setNull(); } 
  public void setDisclaimer1(String zValue) throws Exception {    pDisclaimer1.setValue(zValue);  }
  public String getDisclaimer1() throws Exception {     return pDisclaimer1.getValue();  }
  public boolean isNullDisclaimer1() throws Exception { return  pDisclaimer1.isNull(); } 
  public void setNullToDisclaimer1() throws Exception {  pDisclaimer1.setNull(); } 
  public void setDisclaimer2(String zValue) throws Exception {    pDisclaimer2.setValue(zValue);  }
  public String getDisclaimer2() throws Exception {     return pDisclaimer2.getValue();  }
  public boolean isNullDisclaimer2() throws Exception { return  pDisclaimer2.isNull(); } 
  public void setNullToDisclaimer2() throws Exception {  pDisclaimer2.setNull(); } 
  public void setPickupPoint(String zValue) throws Exception {    pPickupPoint.setValue(zValue);  }
  public String getPickupPoint() throws Exception {     return pPickupPoint.getValue();  }
  public boolean isNullPickupPoint() throws Exception { return  pPickupPoint.isNull(); } 
  public void setNullToPickupPoint() throws Exception {  pPickupPoint.setNull(); } 
  public void setDropOffdate(Date zValue) throws Exception {    pDropOffdate.setValue(zValue);  }
  public Date getDropOffdate() throws Exception {     return pDropOffdate.getValue();  }
  public boolean isNullDropOffdate() throws Exception { return  pDropOffdate.isNull(); } 
  public void setNullToDropOffdate() throws Exception {  pDropOffdate.setNull(); } 
  public void setCarType(String zValue) throws Exception {    pCarType.setValue(zValue);  }
  public String getCarType() throws Exception {     return pCarType.getValue();  }
  public boolean isNullCarType() throws Exception { return  pCarType.isNull(); } 
  public void setNullToCarType() throws Exception {  pCarType.setNull(); } 
  public void setUpgrade(String zValue) throws Exception {    pUpgrade.setValue(zValue);  }
  public String getUpgrade() throws Exception {     return pUpgrade.getValue();  }
  public boolean isNullUpgrade() throws Exception { return  pUpgrade.isNull(); } 
  public void setNullToUpgrade() throws Exception {  pUpgrade.setNull(); } 
  public void setArriboHora(String zValue) throws Exception {    pArriboHora.setValue(zValue);  }
  public String getArriboHora() throws Exception {     return pArriboHora.getValue();  }
  public boolean isNullArriboHora() throws Exception { return  pArriboHora.isNull(); } 
  public void setNullToArriboHora() throws Exception {  pArriboHora.setNull(); } 
  public void setRetorno(String zValue) throws Exception {    pRetorno.setValue(zValue);  }
  public String getRetorno() throws Exception {     return pRetorno.getValue();  }
  public boolean isNullRetorno() throws Exception { return  pRetorno.isNull(); } 
  public void setNullToRetorno() throws Exception {  pRetorno.setNull(); } 
  public void setDopOfflocation(String zValue) throws Exception {    pDopOfflocation.setValue(zValue);  }
  public String getDopOfflocation() throws Exception {     return pDopOfflocation.getValue();  }
  public boolean isNullDopOfflocation() throws Exception { return  pDopOfflocation.isNull(); } 
  public void setNullToDopOfflocation() throws Exception {  pDopOfflocation.setNull(); } 
  public void setDropOffcharge(String zValue) throws Exception {    pDropOffcharge.setValue(zValue);  }
  public String getDropOffcharge() throws Exception {     return pDropOffcharge.getValue();  }
  public boolean isNullDropOffcharge() throws Exception { return  pDropOffcharge.isNull(); } 
  public void setNullToDropOffcharge() throws Exception {  pDropOffcharge.setNull(); } 
  public void setGarantia(String zValue) throws Exception {    pGarantia.setValue(zValue);  }
  public String getGarantia() throws Exception {     return pGarantia.getValue();  }
  public boolean isNullGarantia() throws Exception { return  pGarantia.isNull(); } 
  public void setNullToGarantia() throws Exception {  pGarantia.setNull(); } 
  public void setCorporateDiscount(String zValue) throws Exception {    pCorporateDiscount.setValue(zValue);  }
  public String getCorporateDiscount() throws Exception {     return pCorporateDiscount.getValue();  }
  public boolean isNullCorporateDiscount() throws Exception { return  pCorporateDiscount.isNull(); } 
  public void setNullToCorporateDiscount() throws Exception {  pCorporateDiscount.setNull(); } 
  public void setTourcode(String zValue) throws Exception {    pTourcode.setValue(zValue);  }
  public String getTourcode() throws Exception {     return pTourcode.getValue();  }
  public boolean isNullTourcode() throws Exception { return  pTourcode.isNull(); } 
  public void setNullToTourcode() throws Exception {  pTourcode.setNull(); } 
  public void setEquipamiento(String zValue) throws Exception {    pEquipamiento.setValue(zValue);  }
  public String getEquipamiento() throws Exception {     return pEquipamiento.getValue();  }
  public boolean isNullEquipamiento() throws Exception { return  pEquipamiento.isNull(); } 
  public void setNullToEquipamiento() throws Exception {  pEquipamiento.setNull(); } 
  public void setRemarks(String zValue) throws Exception {    pRemarks.setValue(zValue);  }
  public String getRemarks() throws Exception {     return pRemarks.getValue();  }
  public boolean isNullRemarks() throws Exception { return  pRemarks.isNull(); } 
  public void setNullToRemarks() throws Exception {  pRemarks.setNull(); } 
  public void setPasajeroFrecuente(String zValue) throws Exception {    pPasajeroFrecuente.setValue(zValue);  }
  public String getPasajeroFrecuente() throws Exception {     return pPasajeroFrecuente.getValue();  }
  public boolean isNullPasajeroFrecuente() throws Exception { return  pPasajeroFrecuente.isNull(); } 
  public void setNullToPasajeroFrecuente() throws Exception {  pPasajeroFrecuente.setNull(); } 
  public void setNombreCliente(String zValue) throws Exception {    pNombreCliente.setValue(zValue);  }
  public String getNombreCliente() throws Exception {     return pNombreCliente.getValue();  }
  public boolean isNullNombreCliente() throws Exception { return  pNombreCliente.isNull(); } 
  public void setNullToNombreCliente() throws Exception {  pNombreCliente.setNull(); } 
  public void setCupon(String zValue) throws Exception {    pCupon.setValue(zValue);  }
  public String getCupon() throws Exception {     return pCupon.getValue();  }
  public boolean isNullCupon() throws Exception { return  pCupon.isNull(); } 
  public void setNullToCupon() throws Exception {  pCupon.setNull(); } 
  public void setTasaGarantia(String zValue) throws Exception {    pTasaGarantia.setValue(zValue);  }
  public String getTasaGarantia() throws Exception {     return pTasaGarantia.getValue();  }
  public boolean isNullTasaGarantia() throws Exception { return  pTasaGarantia.isNull(); } 
  public void setNullToTasaGarantia() throws Exception {  pTasaGarantia.setNull(); } 
  public void setBookingSource(String zValue) throws Exception {    pBookingSource.setValue(zValue);  }
  public String getBookingSource() throws Exception {     return pBookingSource.getValue();  }
  public boolean isNullBookingSource() throws Exception { return  pBookingSource.isNull(); } 
  public void setNullToBookingSource() throws Exception {  pBookingSource.setNull(); } 
  public void setRateCode(String zValue) throws Exception {    pRateCode.setValue(zValue);  }
  public String getRateCode() throws Exception {     return pRateCode.getValue();  }
  public boolean isNullRateCode() throws Exception { return  pRateCode.isNull(); } 
  public void setNullToRateCode() throws Exception {  pRateCode.setNull(); } 


  
  public void setProveedor(String zValue) throws Exception {    pVehiculoProveedor.setValue(zValue);  }
  public String getProveedor() throws Exception {     return pVehiculoProveedor.getValue();  }
  public boolean isNullProveedor() throws Exception { return  pVehiculoProveedor.isNull(); } 
  public void setNullToProveedor() throws Exception {  pVehiculoProveedor.setNull(); } 
  public void setVoucherTipo(String zValue) throws Exception {    pVoucherTipo.setValue(zValue);  }
  public String getVoucherTipo() throws Exception {     return pVoucherTipo.getValue();  }
  public boolean isNullVoucherTipo() throws Exception { return  pVoucherTipo.isNull(); } 
  public void setNullToVoucherTipo() throws Exception {  pVoucherTipo.setNull(); } 
  public void setVoucherNumero(String zValue) throws Exception {    pVoucherNumero.setValue(zValue);  }
  public String getVoucherNumero() throws Exception {     return pVoucherNumero.getValue();  }
  public boolean isNullVoucherNumero() throws Exception { return  pVoucherNumero.isNull(); } 
  public void setNullToVoucherNumero() throws Exception {  pVoucherNumero.setNull(); } 
  public void setVoucherBiling(String zValue) throws Exception {    pVoucherBiling.setValue(zValue);  }
  public String getVoucherBiling() throws Exception {     return pVoucherBiling.getValue();  }
  public boolean isNullVoucherBiling() throws Exception { return  pVoucherBiling.isNull(); } 
  public void setNullToVoucherBiling() throws Exception {  pVoucherBiling.setNull(); } 
  public void setVoucherFormat(String zValue) throws Exception {    pVoucherFormat.setValue(zValue);  }
  public String getVoucherFormat() throws Exception {     return pVoucherFormat.getValue();  }
  public boolean isNullVoucherFormat() throws Exception { return  pVoucherFormat.isNull(); } 
  public void setNullToVoucherFormat() throws Exception {  pVoucherFormat.setNull(); } 

  public void setPaisDestino(String zValue) throws Exception {    pPaisDestino.setValue(zValue);  }
  public String getPaisDestiono() throws Exception {     return pPaisDestino.getValue();  }
  public void setPaisOrigen(String zValue) throws Exception {    pPaisOrigen.setValue(zValue);  }
  public String getPaisOrigen() throws Exception {     return pPaisOrigen.getValue();  }

  public void setInfo(String zValue) throws Exception {    pInfo.setValue(zValue);  }
  public String getInfo() throws Exception {     return pInfo.getValue();  }
  public boolean isNullInfo() throws Exception { return  pInfo.isNull(); } 
  public void setNullToInfo() throws Exception {  pInfo.setNull(); } 
  public void setEquipamientoConfirmado(String zValue) throws Exception {    pEquipamientoConfirmado.setValue(zValue);  }
  public String getEquipamientoConfirmado() throws Exception {     return pEquipamientoConfirmado.getValue();  }
  public boolean isNullEquipamientoConfirmado() throws Exception { return  pEquipamientoConfirmado.isNull(); } 
  public void setNullToEquipamientoConfirmado() throws Exception {  pEquipamientoConfirmado.setNull(); } 
  public void setGarantiaInfo(String zValue) throws Exception {    pGarantiaInfo.setValue(zValue);  }
  public String getGarantiaInfo() throws Exception {     return pGarantiaInfo.getValue();  }
  public boolean isNullGarantiaInfo() throws Exception { return  pGarantiaInfo.isNull(); } 
  public void setNullToGarantiaInfo() throws Exception {  pGarantiaInfo.setNull(); } 
  public void setVendedor(String zValue) throws Exception {    pVendedor.setValue(zValue);  }
  public String getVendedor() throws Exception {     return pVendedor.getValue();  }
  public boolean isNullVendedor() throws Exception { return  pVendedor.isNull(); } 
  public void setNullToVendedor() throws Exception {  pVendedor.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
	public void setArchivo(String value) throws Exception {this.pArchivo.setValue(value);}
	public void setVersion(String value) throws Exception {this.pVersion.setValue(value);}
	public void setGDS(String value) throws Exception {this.pGDS.setValue(value);}
	public void setOrigen(String value) throws Exception {		this.pOrigen.setValue(value);}
	public void setNroIata(String value) throws Exception {		this.pNroIata.setValue(value);}
	public void setOfficeId(String value) throws Exception {		this.pOfficeId.setValue(value);}
	public void setCityCode(String value) throws Exception {		this.pCityCode.setValue(value);}
	public void setPNRDate(Date value) throws Exception {		this.pPNRDate.setValue(value);}
	public void setCreationDate(Date value) throws Exception {		this.pCreationDate.setValue(value);}
	public void setCreationDateAir(Date value) throws Exception {		this.pCreationDateAir.setValue(value);}
	public void setDirectory(String value) throws Exception {		this.pDirectory.setValue(value);}
	public void setFechaProcesamiento(Date value) throws Exception {		this.pFechaProcesamiento.setValue(value);}
	public void setTransactionType(String value) throws Exception {		this.pTransactionType.setValue(value);}
	public void setPais(String value) throws Exception {		this.pPais.setValue(value);}
	public void setCustomerId(String value) throws Exception {		this.pCustomerId.setValue(value);}
	public void setCustomerIdReducido(String value) throws Exception {
		this.pCustomerIdReducido.setValue(value);
	}

	public void setCustomerIdReducidoFromCustomer(String value) throws Exception {
		String cod = value;
		if (!(cod == null || cod.length() < 10)) {
			cod = value.substring(1, 3) + value.substring(6, 10);
		}
		this.pCustomerIdReducido.setValue(cod);
	}
	public void setTipoPrestacion(String value) throws Exception {		this.pTipoPrestacion.setValue(value);}
	public void setVoid(boolean value) throws Exception {		this.pVoid.setValue(value);}
	public void setCodigoPNR(String value) throws Exception {		this.pCodigopnr.setValue(value);}
  public String getCodigoPNR() throws Exception {     return pCodigopnr.getValue();  }
	public void setCodigoSegmento(long value) throws Exception {		this.pCodigoSegmento.setValue(value);}
	public void setNombrePasajero(String zValue) throws Exception {pNombrePasajero.setValue(zValue);	}

	

  /**
   * Constructor de la Clase
   */
  public BizPNROtro() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "secuence_id", pSecuenceId );
    this.addItem( "archivo", pArchivo );
    this.addItem( "version", pVersion );
    this.addItem( "gds", pGDS );
    this.addItem( "origen", pOrigen );
    this.addItem( "office_id", pOfficeId );
    this.addItem( "nro_iata", pNroIata );
    this.addItem( "city_code", pCityCode );
    this.addItem( "fecha_proc", pFechaProcesamiento );
    this.addItem( "pnr_date", pPNRDate);
    this.addItem( "creation_date", pCreationDate );
    this.addItem( "creation_dateiar", pCreationDateAir );
    this.addItem( "directory", pDirectory );
    this.addItem( "transaction_type", pTransactionType );
    this.addItem( "pais", pPais );
    this.addItem( "codigo_pnr", pCodigopnr );
    this.addItem( "nombre_pasajero", pNombrePasajero );
    this.addItem( "codigo_seg", pCodigoSegmento );
    this.addItem( "customer_id", pCustomerId );
    this.addItem( "customer_id_reducido", pCustomerIdReducido );
     this.addItem( "tipo_prestacion", pTipoPrestacion );
    this.addItem( "void", pVoid );
    this.addItem( "product_code", pProductCode );
    this.addItem( "product_desc", pProductDescr );
    this.addItem( "control_data", pControlData );
    this.addItem( "action", pAction );
    this.addItem( "fecha_reserva", pFechaReserva );
    this.addItem( "producto_secundario", pProductoSecundario );
    this.addItem( "service_type", pServiceType );
    this.addItem( "service_code", pServiceCode );
    this.addItem( "pasajeros", pPasajeros );
    this.addItem( "habitaciones", pHabitaciones );
    this.addItem( "nro_autos", pNroAutos );
    this.addItem( "car_code", pProductType );
    this.addItem( "confirmacion", pConfirmacion );
    this.addItem( "aeropuerto", pAeropuerto );
    this.addItem( "hotel_codigo", pHotelCodigo );
    this.addItem( "hotel_fname", pHotelFName );
    this.addItem( "fecha_salida", pFechaSalida );
    this.addItem( "noches", pNoches );
    this.addItem( "tour_name", pTourName );
    this.addItem( "hotel_name", pHotelName );
    this.addItem( "codigo_producto", pCodigoProducto );
   
    this.addItem( "importe_base", pImporteBase );
    this.addItem( "importe_base_usd", pImporteBaseUsd );
    this.addItem( "importe_base_local", pImporteBaseLocal );
    
    this.addItem( "importe", pImporte );
    this.addItem( "importe_usd", pImporteUsd );
    this.addItem( "importe_local", pImporteLocal );
    
    this.addItem( "moneda_base", pMonedaBase );
    this.addItem( "moneda_local", pMonedaLocal );
    
    this.addItem( "moneda_tasas", pMonedaTasas );
    this.addItem( "tasas", pTasas );
    this.addItem( "tasas_usd", pTasasUsd );
    this.addItem( "tasas_local", pTasasLocal );
    
    this.addItem( "moneda_fee", pMonedaFee );
    this.addItem( "fee", pFee );
    this.addItem( "fee_usd", pFeeUsd );
    this.addItem( "fee_local", pFeeLocal );

    this.addItem( "moneda_precio_total", pMonedaPrecioTotal);
    this.addItem( "precio_total", pPrecioTotal);
    this.addItem( "precio_total_usd", pPrecioTotalUsd);
    this.addItem( "precio_total_local", pPrecioTotalLocal);

    this.addItem( "grupo", pGrupo);
    
    
    this.addItem( "domicilio", pDomicilio );
    this.addItem( "telefono", pTelefono );
    this.addItem( "fax", pFax );
    this.addItem( "codigo_confirmacion", pCodigoConfirmacion );
    this.addItem( "comision_codigo", pComisionCodigo );
    this.addItem( "comision_texto", pComisionTexto );
    this.addItem( "comision_monto", pComisionMonto );
    this.addItem( "tipo_tarjeta", pTipoTarjeta );
    this.addItem( "tarjeta", pTarjeta );
    this.addItem( "tax_breakdown1", pTaxBreakdown1 );
    this.addItem( "tax_breakdown2", pTaxBreakdown2 );
    this.addItem( "tax_breakdown3", pTaxBreakdown3 );
    this.addItem( "tax_breakdown4", pTaxBreakdown4 );
    this.addItem( "surchange1", pSurchange1 );
    this.addItem( "surchange2", pSurchange2 );
    this.addItem( "surchange3", pSurchange3 );
    this.addItem( "surchange4", pSurchange4 );
    this.addItem( "disclaimer1", pDisclaimer1 );
    this.addItem( "disclaimer2", pDisclaimer2 );
    this.addItem( "pickup_point", pPickupPoint );
    this.addItem( "drop_off_date", pDropOffdate );
    this.addItem( "car_type", pCarType );
    this.addItem( "upgrade", pUpgrade );
    this.addItem( "arribo_hora", pArriboHora );
    this.addItem( "retorno", pRetorno );
    this.addItem( "dop_off_location", pDopOfflocation );
    this.addItem( "drop_off_charge", pDropOffcharge );
    this.addItem( "garantia", pGarantia );
    this.addItem( "corporate_discount", pCorporateDiscount );
    this.addItem( "tourcode", pTourcode );
    this.addItem( "equipamiento", pEquipamiento );
    this.addItem( "remarks", pRemarks );
    this.addItem( "pasajero_frecuente", pPasajeroFrecuente );
    this.addItem( "nombre_cliente", pNombreCliente );
    this.addItem( "cupon", pCupon );
    this.addItem( "tasa_garantia", pTasaGarantia );
    this.addItem( "booking_source", pBookingSource );
    this.addItem( "rate_code", pRateCode );
    this.addItem( "vehiculo_proveedor", pVehiculoProveedor );
    this.addItem( "voucher_tipo", pVoucherTipo );
    this.addItem( "voucher_numero", pVoucherNumero );
    this.addItem( "voucher_biling", pVoucherBiling );
    this.addItem( "voucher_format", pVoucherFormat );
    this.addItem( "pais_origen", pPaisOrigen );
    this.addItem( "pais_destino", pPaisDestino );
    this.addItem( "info", pInfo );
    this.addItem( "equipamiento_confirmado", pEquipamientoConfirmado );
    this.addItem( "garantia_info", pGarantiaInfo );
    this.addItem( "vendedor", pVendedor );
    this.addItem( "company", pCompany );
  	this.addItem("order_str", pOrder);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "secuence_id", "Secuence id", false, false, 64 );

    this.addFixedItem( FIELD, "archivo", "Archivo", true, false, 500 );
    this.addFixedItem( FIELD, "version", "Version", true, false, 50 );
    this.addFixedItem( FIELD, "gds", "GDS", true, false, 50 );
    this.addFixedItem( FIELD, "origen", "Origen pnr", true, false, 50 );
    this.addFixedItem( FIELD, "office_id", "Oficina", true, false, 50 );
    this.addFixedItem( FIELD, "nro_iata", "Iata", true, false, 50 );
    this.addFixedItem( FIELD, "city_code", "Ciudad pnr", true, false, 50 );
    this.addFixedItem( FIELD, "fecha_proc", "fecha_proc", true, false, 18 );
    this.addFixedItem( FIELD, "pnr_date", "pnr_date", true, false, 18 );
    this.addFixedItem( FIELD, "creation_dateiar", "Fecha modif pnr", true, false, 18 );
    this.addFixedItem( FIELD, "creation_date", "Fecha emisión", true, false, 18 );
    this.addFixedItem( FIELD, "directory", "directory", true, false, 500 );
    this.addFixedItem( FIELD, "transaction_type", "Tipo transAcción", true, false, 50 );
    this.addFixedItem( FIELD, "nombre_pasajero", "Nombre pasajero", true, false, 250 );
    this.addFixedItem( FIELD, "codigo_pnr", "codigo pnr", true, false, 50 );
    this.addFixedItem( FIELD, "codigo_seg", "codigo segmento", true, false, 18 );
    this.addFixedItem( FIELD, "customer_id", "Cliente", true, false, 50 );
    this.addFixedItem( FIELD, "customer_id_reducido", "DK", true, false, 50 );
    this.addFixedItem( FIELD, "tipo_prestacion", "Tipo prestacion", true, false, 50 );
    this.addFixedItem( FIELD, "void", "Anulado?", true, false, 1 );
    this.addFixedItem( FIELD, "pais", "pais pnr", true, false, 50 );

    this.addFixedItem( FIELD, "grupo", "Grupo", true, false, 18 );

    this.addFixedItem( FIELD, "product_code", "Código producto", true, false, 50 );
    this.addFixedItem( FIELD, "product_desc", "Producto", true, false, 250 );
    this.addFixedItem( FIELD, "control_data", "Control data", true, false, 10 );
    this.addFixedItem( FIELD, "action", "Action", true, false, 60 );
    this.addFixedItem( FIELD, "fecha_reserva", "Fecha reserva", true, false, 10 );
    this.addFixedItem( FIELD, "producto_secundario", "Producto secundario", true, false, 50 );
    this.addFixedItem( FIELD, "service_type", "Tipo servicio", true, false, 50 );
    this.addFixedItem( FIELD, "service_code", "Codigo servicio", true, false, 50 );
    this.addFixedItem( FIELD, "pasajeros", "Pasajeros", true, false, 18 );
    this.addFixedItem( FIELD, "habitaciones", "Habitaciones", true, false, 18 );
    this.addFixedItem( FIELD, "nro_autos", "Nro autos", true, false, 18 );
    this.addFixedItem( FIELD, "car_code", "Tipo producto", true, false, 50 );
    this.addFixedItem( FIELD, "confirmacion", "Confirmacion", true, false, 50 );
    this.addFixedItem( FIELD, "aeropuerto", "Aeropuerto", true, false, 10 );
    this.addFixedItem( FIELD, "hotel_codigo", "Hotel codigo", true, false, 50 );
    this.addFixedItem( FIELD, "hotel_fname", "Hotel nombre completo", true, false, 500 );
    this.addFixedItem( FIELD, "fecha_salida", "Fecha salida", true, false, 10 );
    this.addFixedItem( FIELD, "noches", "Noches", true, false, 18 );
    this.addFixedItem( FIELD, "tour_name", "Tour nombre", true, false, 50 );
    this.addFixedItem( FIELD, "hotel_name", "Hotel nombre", true, false, 200 );
    this.addFixedItem( FIELD, "codigo_producto", "Codigo producto", true, false, 100 );
    
    this.addFixedItem( FIELD, "importe_base", "Importe base", true, false, 18,2 );
    this.addFixedItem( FIELD, "importe_base_usd", "Importe base (USD)", true, false, 18,2 );
    this.addFixedItem( FIELD, "importe_base_local", "Importe base (local)", true, false, 18,2 );

    this.addFixedItem( FIELD, "importe", "Importe", true, false, 18,2 );
    this.addFixedItem( FIELD, "importe_usd", "Importe (USD)", true, false, 18,2 );
    this.addFixedItem( FIELD, "importe_local", "Importe (local)", true, false, 18,2 );

    this.addFixedItem( FIELD, "moneda_base", "Moneda base", true, false, 10 );
    this.addFixedItem( FIELD, "moneda_local", "Moneda local", true, false, 10 );

    this.addFixedItem( FIELD, "moneda_tasas", "Moneda tasas", true, false, 10 );
    this.addFixedItem( FIELD, "tasas", "Tasas", true, false, 18,2 );
    this.addFixedItem( FIELD, "tasas_usd", "Tasas (USD)", true, false, 18,2 );
    this.addFixedItem( FIELD, "tasas_local", "Tasas (local)", true, false, 18,2 );

    this.addFixedItem( FIELD, "moneda_fee", "Moneda fee", true, false, 10 );
    this.addFixedItem( FIELD, "fee", "Fee", true, false, 18,2 );
    this.addFixedItem( FIELD, "fee_usd", "Fee (USD)", true, false, 18,2 );
    this.addFixedItem( FIELD, "fee_local", "Fee (local)", true, false, 18,2 );

    this.addFixedItem( FIELD, "moneda_precio_total", "Moneda precio total", true, false, 10 );
    this.addFixedItem( FIELD, "precio_total", "Precio total Aprox", true, false, 18,2 );
    this.addFixedItem( FIELD, "precio_total_usd", "Precio total (USD)", true, false, 18,2 );
    this.addFixedItem( FIELD, "precio_total_local", "Precio total (local)", true, false, 18,2 );
    
    this.addFixedItem( FIELD, "domicilio", "Domicilio", true, false, 400 );
    this.addFixedItem( FIELD, "telefono", "Telefono", true, false, 50 );
    this.addFixedItem( FIELD, "fax", "Fax", true, false, 50 );
    this.addFixedItem( FIELD, "codigo_confirmacion", "Codigo confirmacion", true, false, 100 );
    this.addFixedItem( FIELD, "comision_codigo", "Comision codigo", true, false, 50 );
    this.addFixedItem( FIELD, "comision_texto", "Comision texto", true, false, 200 );
    this.addFixedItem( FIELD, "comision_monto", "Comision monto", true, false, 18,2 );
    this.addFixedItem( FIELD, "tipo_tarjeta", "Tipo tarjeta", true, false, 10 );
    this.addFixedItem( FIELD, "tarjeta", "Tarjeta", true, false, 50 );
    this.addFixedItem( FIELD, "tax_breakdown1", "Tax breakdown1", true, false, 50 );
    this.addFixedItem( FIELD, "tax_breakdown2", "Tax breakdown2", true, false, 50 );
    this.addFixedItem( FIELD, "tax_breakdown3", "Tax breakdown3", true, false, 59 );
    this.addFixedItem( FIELD, "tax_breakdown4", "Tax breakdown4", true, false, 50 );
    this.addFixedItem( FIELD, "surchange1", "Surchange1", true, false, 200 );
    this.addFixedItem( FIELD, "surchange2", "Surchange2", true, false, 200 );
    this.addFixedItem( FIELD, "surchange3", "Surchange3", true, false, 200 );
    this.addFixedItem( FIELD, "surchange4", "Surchange4", true, false, 200 );
    this.addFixedItem( FIELD, "disclaimer1", "Disclaimer1", true, false, 200 );
    this.addFixedItem( FIELD, "disclaimer2", "Disclaimer2", true, false, 200 );
    this.addFixedItem( FIELD, "pickup_point", "Pickup point", true, false, 100 );
    this.addFixedItem( FIELD, "drop_off_date", "Drop off date", true, false, 10 );
    this.addFixedItem( FIELD, "car_type", "Car type", true, false, 200 );
    this.addFixedItem( FIELD, "upgrade", "Upgrade", true, false, 200 );
    this.addFixedItem( FIELD, "arribo_hora", "Arribo hora", true, false, 10 );
    this.addFixedItem( FIELD, "retorno", "Retorno", true, false, 200 );
    this.addFixedItem( FIELD, "dop_off_location", "Dop off location", true, false, 200 );
    this.addFixedItem( FIELD, "drop_off_charge", "Drop off charge", true, false, 200 );
    this.addFixedItem( FIELD, "garantia", "Garantia", true, false, 200 );
    this.addFixedItem( FIELD, "corporate_discount", "Corporate discount", true, false, 200 );
    this.addFixedItem( FIELD, "tourcode", "Tourcode", true, false, 200 );
    this.addFixedItem( FIELD, "equipamiento", "Equipamiento", true, false, 200 );
    this.addFixedItem( FIELD, "remarks", "Remarks", true, false, 200 );
    this.addFixedItem( FIELD, "pasajero_frecuente", "Pasajero frecuente", true, false, 200 );
    this.addFixedItem( FIELD, "nombre_cliente", "Nombre cliente", true, false, 200 );
    this.addFixedItem( FIELD, "cupon", "Cupon", true, false, 50 );
    this.addFixedItem( FIELD, "tasa_garantia", "Tasa garantia", true, false, 50 );
    this.addFixedItem( FIELD, "booking_source", "Booking source", true, false, 200 );
    this.addFixedItem( FIELD, "rate_code", "Rate code", true, false, 50 );
    this.addFixedItem( FIELD, "vehiculo_proveedor", "Proveedor", true, false, 200 );
    this.addFixedItem( FIELD, "voucher_tipo", "Voucher tipo", true, false, 50 );
    this.addFixedItem( FIELD, "voucher_numero", "Voucher numero", true, false, 50 );
    this.addFixedItem( FIELD, "voucher_biling", "Voucher biling", true, false, 100 );
    this.addFixedItem( FIELD, "voucher_format", "Voucher format", true, false, 200 );
    this.addFixedItem( FIELD, "pais_origen", "País Origen", true, false, 50 );
    this.addFixedItem( FIELD, "pais_destino", "País Destino", true, false, 50 );
    this.addFixedItem( FIELD, "info", "Info", true, false, 200 );
    this.addFixedItem( FIELD, "equipamiento_confirmado", "Equipamiento confirmado", true, false, 400 );
    this.addFixedItem( FIELD, "garantia_info", "Garantia info", true, false, 200 );
    this.addFixedItem( FIELD, "vendedor", "Vendedor", true, false, 200 );
    this.addFixedItem( FIELD, "company", "Company", true, false, 15 );
  	this.addFixedItem(VIRTUAL, "order_str", "Orden", true, false, 10);
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "tur_pnr_otros"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	BizBSPCompany objCompany;

	public BizBSPCompany getObjCompany() throws Exception {
		if (objCompany != null)
			return objCompany;
		return objCompany=(BizBSPCompany)BizCompany.getCompany(pCompany.getValue());
	}


  /**
   * Default read() method
   */
  public boolean read( long zSecuenceId ) throws Exception { 
    addFilter( "secuence_id",  zSecuenceId ); 
    return read(); 
  } 
  public boolean readByPNR( String zCompany,String zIata,String zPnr,long zcodigo ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "nro_iata",  zIata ); 
    addFilter( "codigo_pnr",  zPnr ); 
    addFilter( "codigo_seg",  zcodigo ); 
    return read(); 
  } 
  
	public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass("pss.tourism.pnr.GuiPNROtros");

		JRelation rel;
		rel = rels.addRelationForce(10, "restriccion usuario");
//		rel.addFilter(" (TUR_PNR_OTROS.company in (COMPANY_CUSTOMLIST)) ");
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			rel.addFilter(" (TUR_PNR_OTROS.company in (COMPANY_TICKET) and TUR_PNR_OTROS.customer_id_reducido='"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"')  ");
		} else {
			rel.addFilter(" (TUR_PNR_OTROS.company in (COMPANY_CUSTOMLIST)) ");
		}
		JRelation r;
		r = rels.addRelationParent(91, "Vendedor", BizMailingPersona.class, "vendedor", "codigo");
		r.addJoin("TUR_PNR_OTROS.vendedor", "vendedor.codigo");
		r.addJoin("TUR_PNR_OTROS.company", "vendedor.company");
		r.addFilter("vendedor.tipo='" + BizMailingPersona.VENDEDOR + "'");
		r.setAlias("vendedor");
		
		r = rels.addRelationParent(92, "Sucursal", BizMailingPersona.class, "office_id", "codigo");
		r.addJoin("TUR_PNR_OTROS.office_id", "sucursal.codigo");
		r.addJoin("TUR_PNR_OTROS.company", "sucursal.company");
		r.addFilter("sucursal.tipo='" + BizMailingPersona.SUCURSAL + "'");
		r.setAlias("sucursal");
		
		r = rels.addRelationParent(94, "IATA", BizMailingPersona.class, "nro_iata", "codigo");
		r.addJoin("TUR_PNR_OTROS.nro_iata", "iata.codigo");
		r.addJoin("TUR_PNR_OTROS.company", "iata.company");
		r.addFilter("iata.tipo='" + BizMailingPersona.IATA + "'");
		r.setAlias("iata");

		r = rels.addRelationParent(98, "Grupo", BizHotel.class, "grupo", "id");
		r.addJoin("TUR_PNR_OTROS.grupo", "grupo.id");
		r.addJoin("TUR_PNR_OTROS.company", "grupo.company");
		r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("grupo");
		
		rels.addRelationParent(85, "Tipo Productos", getMapProductos(), "product_code");
		rels.addRelationParent(86, "Tipo Productos Secundario", getMapProductoSecundario(), "producto_secundario");

		
		rels.hideField("secuence_id");
		//rels.hideField("company");
		rels.hideField("archivo");
		rels.hideField("origen");
		rels.hideField("fecha_proc");
		rels.hideField("pais");
		rels.hideField("order_str");
		rels.hideField("version");
		
		rels.addFilter("void", "=", "N");

		rels.addFolderGroup("Voucher", null);
		rels.addFolderGroup("Fechas", "Voucher");
		rels.addFolderGroup("Pasajero", "Voucher");
		rels.addFolderGroup("Tarifas", "Voucher");
		rels.addFolderGroup("Sucursal", "Voucher");
		rels.addFolderGroup("Tarifa (Moneda PNR)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda USD)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda local)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda Base)", "Tarifas");
		rels.addFolderGroup("Comisiones", "Voucher");
		rels.addFolderGroup("Impuestos", "Voucher");
		rels.addFolderGroup("IATA", "Voucher");
		rels.addFolderGroup("Grupo hotel", "Voucher");
		rels.addFolderGroup("Otros Datos", "Voucher");
		rels.addFolderGroup("Cliente", "Voucher");
		rels.addFolderGroup("Hotel", "Voucher");
		rels.addFolderGroup("Autos", "Voucher");
			
		
		String s ="8";
		
	 	 
		
		rels.addFieldGroup(s, "gds", "*", "Voucher");
		rels.addFieldGroup(s, "company", "*", "Voucher");
		rels.addFieldGroup(s, "directory", "*", "Voucher");
		rels.addFieldGroup(s, "transaction_type", "*", "Voucher");
		rels.addFieldGroup(s, "codigo_pnr", "*", "Voucher");
		rels.addFieldGroup(s, "tipo_prestacion", "*", "Voucher");
		rels.addFieldGroup(s, "void", "*", "Voucher");
		rels.addFieldGroup(s, "product_code", "*", "Voucher");
		rels.addFieldGroup(s, "product_desc", "*", "Voucher");
		rels.addFieldGroup(s, "producto_secundario", "*", "Voucher");
		rels.addFieldGroup(s, "service_type", "*", "Voucher");
		rels.addFieldGroup(s, "service_code", "*", "Voucher");
		rels.addFieldGroup(s, "aeropuerto", "*", "Voucher");
		rels.addFieldGroup(s, "confirmacion", "*", "Voucher");
		
		rels.addFieldGroup(s, "habitaciones", "*", "Hotel");
		rels.addFieldGroup(s, "hotel_codigo", "*", "Hotel");
		rels.addFieldGroup(s, "hotel_fname", "*", "Hotel");
		rels.addFieldGroup(s, "fecha_salida", "*", "Hotel");
		rels.addFieldGroup(s, "noches", "*", "Hotel");
		rels.addFieldGroup(s, "tour_name", "*", "Hotel");
		rels.addFieldGroup(s, "hotel_name", "*", "Hotel");
		rels.addFieldGroup(s, "codigo_producto", "*", "Hotel");
		rels.addFieldGroup(s, "grupo", "*", "Hotel");
		rels.addFieldGroup(s, "domicilio", "*", "Hotel");
		rels.addFieldGroup(s, "telefono", "*", "Hotel");
		rels.addFieldGroup(s, "fax", "*", "Hotel");
		rels.addFieldGroup(s, "codigo_confirmacion", "*", "Hotel");

	 	rels.addFieldGroup(s, "nro_autos", "*", "Autos");
		rels.addFieldGroup(s, "car_code", "*", "Autos");
		rels.addFieldGroup(s, "pickup_point", "*", "Autos");
		rels.addFieldGroup(s, "drop_off_date", "*", "Autos");
		rels.addFieldGroup(s, "car_type", "*", "Autos");
		rels.addFieldGroup(s, "upgrade", "*", "Autos");
		rels.addFieldGroup(s, "arribo_hora", "*", "Autos");
		rels.addFieldGroup(s, "retorno", "*", "Autos");
		rels.addFieldGroup(s, "dop_off_location", "*", "Autos");
		rels.addFieldGroup(s, "drop_off_charge", "*", "Autos");
		rels.addFieldGroup(s, "garantia", "*", "Autos");
		rels.addFieldGroup(s, "corporate_discount", "*", "Autos");
		rels.addFieldGroup(s, "tourcode", "*", "Autos");
		rels.addFieldGroup(s, "equipamiento", "*", "Autos");
		rels.addFieldGroup(s, "voucher_tipo", "*", "Autos");
		rels.addFieldGroup(s, "voucher_numero", "*", "Autos");
		rels.addFieldGroup(s, "voucher_biling", "*", "Autos");
		rels.addFieldGroup(s, "voucher_format", "*", "Autos");
		rels.addFieldGroup(s, "cupon", "*", "Autos");
		rels.addFieldGroup(s, "tasa_garantia", "*", "Autos");
		rels.addFieldGroup(s, "booking_source", "*", "Autos");
		rels.addFieldGroup(s, "rate_code", "*", "Autos");
		rels.addFieldGroup(s, "vehiculo_proveedor", "*", "Autos");
		rels.addFieldGroup(s, "remarks", "*", "Autos");
		rels.addFieldGroup(s, "garantia_info", "*", "Autos");
		
	  
		rels.addFieldGroup(s, "nombre_pasajero", "*", "Pasajero");
		rels.addFieldGroup(s, "pasajeros", "*", "Pasajero");
		rels.addFieldGroup(s, "pasajero_frecuente", "*", "Pasajero");
			
		rels.addFieldGroup(s, "office_id", "*", "Sucursal");

		rels.addFieldGroup(s, "city_code", "*", "Otros Datos");
		rels.addFieldGroup(s, "codigo_seg", "*", "Otros Datos");
		rels.addFieldGroup(s, "control_data", "*", "Otros Datos");
		rels.addFieldGroup(s, "action", "*", "Otros Datos");
		rels.addFieldGroup(s, "tipo_tarjeta", "*", "Otros Datos");
		rels.addFieldGroup(s, "tarjeta", "*", "Otros Datos");
		rels.addFieldGroup(s, "confirmation", "*", "Otros Datos");

		rels.addFieldGroup(s, "pais_origen", "*", "Otros Datos");
		rels.addFieldGroup(s, "pais_destino", "*", "Otros Datos");
		rels.addFieldGroup(s, "equipamiento_confirmado", "*", "Otros Datos");
		rels.addFieldGroup(s, "info", "*", "Otros Datos");
		rels.addFieldGroup(s, "vendedor", "*", "Otros Datos");
		
		

		rels.addFieldGroup(s, "pnr_date", "*", "Fechas");
		rels.addFieldGroup(s, "creation_date", "*", "Fechas");
		rels.addFieldGroup(s, "creation_dateiar", "*", "Fechas");
		rels.addFieldGroup(s, "fecha_reserva", "*", "Fechas");

		rels.addFieldGroup(s, "customer_id", "*", "Cliente");
		rels.addFieldGroup(s, "customer_id_reducido", "*", "Cliente");
		rels.addFieldGroup(s, "nombre_cliente", "*", "Cliente");
		
		rels.addFieldGroup(s, "importe_base", "*", "Tarifa (Moneda Base)");
		rels.addFieldGroup(s, "moneda_base", "*", "Tarifa (Moneda Base)");

		rels.addFieldGroup(s, "importe_base_usd", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "importe_usd", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "precio_total_usd", "*", "Tarifa (Moneda USD)");
	   
		rels.addFieldGroup(s, "importe_base_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "importe_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "moneda_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "precio_total_local", "*", "Tarifa (Moneda local)");
	  
		rels.addFieldGroup(s, "importe", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "precio_total", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "moneda_precio_total", "*", "Tarifa (Moneda PNR)");
  
		rels.addFieldGroup(s, "moneda_tasas", "*", "Impuestos");
		rels.addFieldGroup(s, "tasas", "*", "Impuestos");
		rels.addFieldGroup(s, "tasas_usd", "*", "Impuestos");
		rels.addFieldGroup(s, "tasas_local", "*", "Impuestos");
	     
		rels.addFieldGroup(s, "moneda_fee", "*", "Impuestos");
		rels.addFieldGroup(s, "fee", "*", "Impuestos");
		rels.addFieldGroup(s, "fee_usd", "*", "Impuestos");
		rels.addFieldGroup(s, "fee_local", "*", "Impuestos");
		rels.addFieldGroup(s, "tax_breakdown1", "*", "Impuestos");
		rels.addFieldGroup(s, "tax_breakdown2", "*", "Impuestos");
		rels.addFieldGroup(s, "tax_breakdown3", "*", "Impuestos");
		rels.addFieldGroup(s, "tax_breakdown4", "*", "Impuestos");
		rels.addFieldGroup(s, "surchange1", "*", "Impuestos");
		rels.addFieldGroup(s, "surchange2", "*", "Impuestos");
		rels.addFieldGroup(s, "surchange3", "*", "Impuestos");
		rels.addFieldGroup(s, "surchange4", "*", "Impuestos");
		rels.addFieldGroup(s, "disclaimer1", "*", "Impuestos");
		rels.addFieldGroup(s, "disclaimer2", "*", "Impuestos");
	   
		rels.addFieldGroup(s, "comision_codigo", "*", "Comisiones");
		rels.addFieldGroup(s, "comision_texto", "*", "Comisiones");
		rels.addFieldGroup(s, "comision_monto", "*", "Comisiones");
	   
		


		rels.addFieldGroup(s + "_91", "*", "*", "");
		rels.addFieldGroup(s + "_91", "descripcion", "*", "Vendedor");
		rels.addFieldGroup(s + "_91", "mail", "*", "Vendedor");
		rels.addFieldGroup(s + "_91", "numero", "*", "Vendedor");

	  
		rels.addFieldGroup(s + "_94", "*", "*", "");
		rels.addFieldGroup(s + "_94", "descripcion", "*", "IATA");
		rels.addFieldGroup(s + "_94", "mail", "*", "IATA");
		rels.addFieldGroup(s + "_94", "numero", "*", "IATA");
		rels.addFieldGroup(s, "nro_iata", "*", "IATA");

		rels.addFieldGroup(s + "_92", "*", "*", "");
		rels.addFieldGroup(s + "_92", "descripcion", "*", "Sucursal");
		rels.addFieldGroup(s + "_92", "mail", "*", "Sucursal");
		rels.addFieldGroup(s + "_92", "numero", "*", "Sucursal");

		rels.addFieldGroup(s + "_98", "*", "*", "");
		rels.addFieldGroup(s + "_98", "descripcion", "*", "Grupo hotel");
		rels.addFieldGroup(s, "grupo", "*", "Grupo hotel");
	}

	public boolean isHotel() throws Exception {
		return getProductCode().equals("3");
	}

	public boolean isCar() throws Exception {
		return getProductCode().equals("A");
	}
	
	public boolean isRail() throws Exception {
		return getProductCode().equals("6");
	}
	
	public boolean isInsurance() throws Exception {
		return getProductCode().equals("7");
	}
	
	public boolean isAddSegment() throws Exception {
		return getProductoSecundario().equals("ADD");
	}
	public boolean isLimo() throws Exception {
		return getProductoSecundario().equals("LIM");
	}
	public boolean isTor() throws Exception {
		return getProductoSecundario().equals("TOR");
	}
		
	
	public boolean isElvaSea() throws Exception {
		return getProductCode().equals("F");
	}
	
	public boolean isElvaTor() throws Exception {
		return getProductCode().equals("G");
	}
	
	public boolean isCruiser() throws Exception {
		return getProductCode().equals("D");
	}
	
	public String generateProductDescripcion() throws Exception {
		String descr="";
		descr=getMapProductoSecundario().getElement(getProductoSecundario());
		if (descr==null||descr.trim().equals(""))
			descr=getMapProductos().getElement(getProductCode());
		return descr;
	}
	
	static JMap<String, String> cacheProduct;
	public static JMap<String, String> getMapProductos() throws Exception {
		if (cacheProduct!=null) return cacheProduct;
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("2", JLanguage.translate("Tour manual"));
		map.addElement("3", JLanguage.translate("Hotel"));
		map.addElement("4", JLanguage.translate("Maritimo"));
		map.addElement("5", JLanguage.translate("Autobus"));
		map.addElement("6", JLanguage.translate("Tren"));
		map.addElement("7", JLanguage.translate("Seguro"));
		map.addElement("8", JLanguage.translate("Taxi Aereo"));
		map.addElement("9", JLanguage.translate("Varios"));
		map.addElement("A", JLanguage.translate("Autos"));
		map.addElement("B", JLanguage.translate("Prepago "));
		map.addElement("C", JLanguage.translate("Tierra"));
		map.addElement("D", JLanguage.translate("Cruise Director"));
		map.addElement("E", JLanguage.translate("SNCB Rail,Elgar Rail,Eurostar Rail"));
		map.addElement("F", JLanguage.translate("Elva Sea"));
		map.addElement("G", JLanguage.translate("Elva Tour"));
		map.addElement("H", JLanguage.translate("CruiseMatch"));
		map.addElement("I", JLanguage.translate("LeisureNet"));
		map.addElement("J", JLanguage.translate("Guia tour"));
		map.addElement("K", JLanguage.translate("Tranporte por tierra sueco"));
		map.addElement("L", JLanguage.translate("Segemnto extra"));
		map.addElement("M", JLanguage.translate("Limo"));
		return cacheProduct=map;
	}
	
	static JMap<String, String> cacheProductSec;
	public static JMap<String, String> getMapProductoSecundario() throws Exception {
		if (cacheProductSec!=null) return cacheProductSec;
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("ADD", JLanguage.translate("Segmento"));
		map.addElement("ATX", JLanguage.translate("Aire"));
		map.addElement("BUS", JLanguage.translate("Bus"));
		map.addElement("CAR", JLanguage.translate("Autos"));
		map.addElement("HHT", JLanguage.translate("Hotel"));
		map.addElement("HHL", JLanguage.translate("Hotel"));
		map.addElement("HTL", JLanguage.translate("Hotel"));
		map.addElement("INS", JLanguage.translate("Seguros"));
		map.addElement("LAN", JLanguage.translate("Terrestre"));
		map.addElement("MCO", JLanguage.translate("Varios"));
		map.addElement("OTH", JLanguage.translate("Otros "));
		map.addElement("PTA", JLanguage.translate("Prepago"));
		map.addElement("RAL", JLanguage.translate("Tren"));
		map.addElement("SEA", JLanguage.translate("Maritimo"));
		map.addElement("TOR", JLanguage.translate("Tour"));
		map.addElement("SUR", JLanguage.translate("Transporte terrestre sueco"));
		map.addElement("LIM", JLanguage.translate("Limousine"));
		return cacheProductSec=map;
	}

	public void execReprocesar() throws Exception {
		JExec oExec = new JExec(this, "reprocesar") {

			@Override
			public void Do() throws Exception {
				reprocesar();
			}
		};
		oExec.execute();
	}
	public void reprocesar() throws Exception {
		boolean first = true;

		JRecords<BizPNRFile> files = getArchivos();
		JIterator<BizPNRFile> it = files.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPNRFile file = it.nextElement();
			String origen = JPath.PssPathInputProcessed() + "/" + file.getDirectory() + "/" + file.getArchivo();

	
			processFileInternal(origen, true);

		}

	}
	public void processFileInternal(String filename, boolean reprocess) throws Exception {

		FileProcessor fp = null;
		if (filename.indexOf("SABRE")!=-1)
			fp = new SabreFileProcessor();
		else if (filename.toUpperCase().indexOf("COPA.")!=-1)
			fp = new SabreFileProcessor();
		else if (filename.indexOf("AMADEUS")!=-1)
			fp = new AmadeusFileProcessor();
		else if (filename.indexOf("TRAVELPORT")!=-1)
			fp = new TravelPortFileProcessor();
		else
			throw new Exception("GDS desconocido");
		fp.processFileInternal(new File(filename), reprocess);
		
	}
	
	public JRecords<BizPNRFile> getArchivos() throws Exception {

		JRecords<BizPNRFile> files = new JRecords<BizPNRFile>(BizPNRFile.class);
		files.setStatic(true);
		files.clearStaticItems();

		JRecords<BizPNRFilename> fs = new JRecords<BizPNRFilename>(BizPNRFilename.class);
		fs.addFilter("company", this.getCompany());
		fs.addFilter("pnr", this.getCodigoPNR());
		fs.addOrderBy("arrive_order");
		fs.toStatic();

		while (fs.nextRecord()) {
			BizPNRFilename file = fs.getRecord();

			BizPNRFile f = new BizPNRFile();
			f.setPNR(file.getPNR());
			f.setCompany(file.getCompany());
			f.setArchivo(file.getArchivo());
			f.setDirectory(file.getDirectory());
			int pos = file.getArchivo().lastIndexOf(".");

			f.setDescripcion("Archivo version " + file.getArchivo());

			files.getStaticItems().addElement(f);
		}
		return files;
	}

	public static void processReprocessAllTicket(String company) throws Exception {
		JRecords<BizPNROtro> tickets = new JRecords<BizPNROtro>(BizPNROtro.class);
		tickets.addFilter("company", company);
//		tickets.addFilter("creation_date", JDateTools.getFirstDayOfYear(new Date()), ">=");
		tickets.readAll();
		while (tickets.nextRecord()) {
			BizPNROtro ticket = tickets.getRecord();
			ticket.postProcesar();
			ticket.processUpdate();
		}
	}
	
	public void postProcesar() throws Exception {
		JRecords<BizHotelDetail> details  = new JRecords<BizHotelDetail>(BizHotelDetail.class);
		details.addJoin("JOIN", "BSP_HOTEL", "H", "BSP_HOTEL_DETAIL.id_hotel=H.id");
		details.addFilter("company", getCompany());
		details.addOrderBy("H","orden","ASC");
		JIterator<BizHotelDetail> it = details.getStaticIterator();
		while (it.hasMoreElements()) {
			BizHotelDetail detail = it.nextElement();
			boolean find=false;
			if (!detail.getExacto()&&detail.getDescription().equals("*"))
				find=true;
			else if (detail.getExacto()) {
				if (detail.getDescription().toLowerCase().equals(getHotelName().toLowerCase()))
					find =true;
				if (detail.getDescription().toLowerCase().equals(getHotelFName().toLowerCase()))
					find =true;
				if (detail.getDescription().toLowerCase().equals(getHotelCodigo().toLowerCase()))
					find =true;
			} else {
				if ((getHotelName().toLowerCase().indexOf(detail.getDescription().toLowerCase().replaceAll("%",""))!=-1))
					find =true;
				if ((getHotelFName().toLowerCase().indexOf(detail.getDescription().toLowerCase().replaceAll("%",""))!=-1))
					find =true;
				if ((getHotelCodigo().toLowerCase().indexOf(detail.getDescription().toLowerCase().replaceAll("%",""))!=-1))
					find =true;
				
			}
			if (find) {
				setGrupo(detail.getIdHotel());
				break;
				
			}
		}
		if (getHotelFName().equals("") && !getHotelName().equals("")) {
			setHotelFName(getHotelName());
		} else if (getHotelName().equals("") && !getHotelFName().equals("")) {
			setHotelName(getHotelFName());
		}

	}

}
