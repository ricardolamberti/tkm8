package pss.bsp.consolid.model.liquidacion.errors;

import java.util.Date;

import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.common.agenda.evento.BizEvento;
import pss.common.agenda.evento.tipo.GuiTipoEventos;
import pss.common.agenda.turno.GuiTurnos;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JPair;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class BizLiqError extends JRecord {

  public final static String CR_FALTANTETKM = "FTKM";
  public final static String CR_FALTANTEORCL = "FORCL";
  public final static String CR_FALTANTEHIST = "FHIST";
  public final static String CR_WARNING = "WARNING";
  public final static String CR_DIFERENCIA = "DIFF";
  public final static String CR_VOID = "VOID";
  public final static String CR_OK = "OK";
  public final static String CR_ALLERRORS = "ALLERROR";
  
  public BizLiqError() throws Exception {
      super();
  }

  private JString pCompany = new JString();
  private JLong pLiquidacionId = new JLong();
  private JLong pLinea = new JLong();
	private JString pOrganization = new JString();
	private JString pType = new JString();
  private JString pError = new JString();
  private JLong pInterfaceId = new JLong();
  private JString pDk = new JString();
  private JString pTipoDoc = new JString();
  private JString pNroDoc = new JString();
  private JDate pFechaDoc = new JDate();
  private JString pNroBoleto = new JString();
  private JString pGDSTkm = new JString();
  private JString pEmisionTkm = new JString();
  private JString pNroTarjetaTkm = new JString();
  private JDate pFechaSalidaTkm = new JDate();
  private JDate pFechaFinTkm = new JDate();
  private JString pOrigenTkm = new JString();
  private JString pDestinoTkm = new JString();
  private JString pPrestador = new JString();
  private JString pPrestadorTkm = new JString();
  private JString pRuta = new JString();
  private JString pRutaTkm = new JString();
  private JString pPasajero = new JString();
  private JString pReserva = new JString();
  private JString pIata = new JString();
  private JString pIataTkm = new JString();
  private JFloat pTarifa = new JFloat();
  private JFloat pTarifaTkm = new JFloat();
  private JFloat pIva = new JFloat();
  private JFloat pIvaTkm = new JFloat();
  private JFloat pTua = new JFloat();
  private JFloat pTuaTkm = new JFloat();
  private JFloat pImporte = new JFloat();
  private JFloat pImporteTkm = new JFloat();
  private JFloat pSaldo = new JFloat();
  private JFloat pSaldoTkm = new JFloat();
  private JFloat pComision = new JFloat();
  private JFloat pPorcComision = new JFloat();
  private JString pTipoServicio = new JString();
  private JString pTipoServicioTkm = new JString();
  private JString pOrigen = new JString();
  private JString pClase = new JString();
  private JString pClaseTkm = new JString();
  private JString pClases = new JString();
  private JString pClasesTkm = new JString();
  private JString pLineas = new JString();
  private JString pLineasTkm = new JString();
  private JString pFormaPago = new JString();
  private JString pFormaPagoTkm = new JString();
  private JFloat pPorcIncentivo = new JFloat();
  private JFloat pIncentivo = new JFloat();
  private JString pTipo = new JString();
  private JFloat pContado = new JFloat();
  private JFloat pTarjeta = new JFloat();
  private JFloat pFiscal = new JFloat();
  private JFloat pNoFiscal = new JFloat();
  private JFloat pNoDevengado = new JFloat();

  private JString pCriticidadDescr = new JString() {
    public void preset() throws Exception {
    	pCriticidadDescr.setValue(getCriticidadDescr());
    }
};
  private JString pControlIata = new JString() {
      public void preset() throws Exception {
          pControlIata.setValue(getControlIata());
      }
  };
  public String getControlIata() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pIataTkm.getValue();
		if (!pType.getValue().equals(CR_DIFERENCIA) ) return pIata.getValue();
      return pIataTkm.getValue().equals(pIata.getValue()) ? ""+pIata.getValue() : "TKM:" + pIataTkm.getValue() + " ORA:" + pIata.getValue();
  }

  private JString pControlPrestador = new JString() {
      public void preset() throws Exception {
          pControlPrestador.setValue(getControlPrestador());
      }
  };
  public String getControlPrestador() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pPrestadorTkm.getValue();
	if (!pType.getValue().equals(CR_DIFERENCIA) ) return pPrestador.getValue();
      return pPrestadorTkm.getValue().equals(pPrestador.getValue()) ? ""+pPrestador.getValue() : "TKM:" + pPrestadorTkm.getValue() + " ORA:" + pPrestador.getValue();
  }

  private JString pControlTarifa = new JString() {
      public void preset() throws Exception {
          pControlTarifa.setValue(getControlTarifa());
      }
  };
  public String getControlTarifa() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pTarifaTkm.getValue();
		if (!pType.getValue().equals(CR_DIFERENCIA) ) return ""+pTarifa.getValue();
      return Math.abs(pTarifaTkm.getValue())==Math.abs(pTarifa.getValue()) ? ""+pTarifa.getValue() : "TKM:" + pTarifaTkm.getValue() + " ORA:" + pTarifa.getValue();
  }

  private JString pControlIva = new JString() {
      public void preset() throws Exception {
          pControlIva.setValue(getControlIva());
      }
  };
  public String getControlIva() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pIvaTkm.getValue();
		if (!pType.getValue().equals(CR_DIFERENCIA) ) return ""+pIva.getValue();
      return Math.abs( pIvaTkm.getValue())==Math.abs(pIva.getValue()) ? ""+pIva.getValue() : "TKM:" + pIvaTkm.getValue() + " ORA:" + pIva.getValue();
  }

  private JString pControlTua = new JString() {
      public void preset() throws Exception {
          pControlTua.setValue(getControlTua());
      }
  };
  public String getControlTua() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pTuaTkm.getValue();
		if (!pType.getValue().equals(CR_DIFERENCIA) ) return ""+pTua.getValue();
      return Math.abs(pTuaTkm.getValue())==Math.abs(pTua.getValue()) ? ""+pTua.getValue() : "TKM:" + pTuaTkm.getValue() + " ORA:" + pTua.getValue();
  }

  private JString pControlImporte = new JString() {
      public void preset() throws Exception {
          pControlImporte.setValue(getControlImporte());
      }
  };
  public String getControlImporte() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pImporteTkm.getValue();
		if (!pType.getValue().equals(CR_DIFERENCIA) ) return ""+pImporte.getValue();
      return Math.abs(pImporteTkm.getValue())==Math.abs(pImporte.getValue()) ? ""+pImporte.getValue() : "TKM:" + pImporteTkm.getValue() + " ORA:" + pImporte.getValue();
  }

  private JString pControlSaldo = new JString() {
      public void preset() throws Exception {
          pControlSaldo.setValue(getControlSaldo());
      }
  };
  public String getControlSaldo() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pSaldoTkm.getValue();
    if (!pType.getValue().equals(CR_DIFERENCIA) ) return ""+pSaldo.getValue();
      return Math.abs(pSaldoTkm.getValue())==Math.abs(pSaldo.getValue()) ? ""+pSaldo.getValue() : "TKM:" + pSaldoTkm.getValue() + " ORA:" + pSaldo.getValue();
  }

  private JString pControlTipoServicio = new JString() {
      public void preset() throws Exception {
          pControlTipoServicio.setValue(getControlTipoServicio());
      }
  };
  public String getControlTipoServicio() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pTipoServicioTkm.getValue();
		if (!pType.getValue().equals(CR_DIFERENCIA) ) return pTipoServicio.getValue();
      return pTipoServicioTkm.getValue().equals(pTipoServicio.getValue()) ? pTipoServicio.getValue() : "TKM:" + pTipoServicioTkm.getValue() + " ORA:" + pTipoServicio.getValue();
  }

  private JString pControlClase = new JString() {
      public void preset() throws Exception {
          pControlClase.setValue(getControlClase());
      }
  };
  public String getControlClase() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pClaseTkm.getValue();
	if (!pType.getValue().equals(CR_DIFERENCIA) ) return pClase.getValue();
      return pClaseTkm.getValue().equals(pClase.getValue()) ? pClase.getValue() : "TKM:" + pClaseTkm.getValue() + " ORA:" + pClase.getValue();
  }

  private JString pControlClases = new JString() {
      public void preset() throws Exception {
          pControlClases.setValue(getControlClases());
      }
  };
  public String getControlClases() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pClasesTkm.getValue();
		if (!pType.getValue().equals(CR_DIFERENCIA) ) return pClases.getValue();
      return pClasesTkm.getValue().equals(pClases.getValue()) ? pClases.getValue() : "TKM:" + pClasesTkm.getValue() + " ORA:" + pClases.getValue();
  }

  private JString pControlLineas = new JString() {
      public void preset() throws Exception {
          pControlLineas.setValue(getControlLineas());
      }
  };
  public String getControlLineas() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pLineasTkm.getValue();
		if (!pType.getValue().equals(CR_DIFERENCIA) ) return pLineas.getValue();
    return pLineasTkm.getValue().equals(pLineas.getValue()) ? pLineas.getValue() : "TKM:" + pLineasTkm.getValue() + " ORA:" + pLineas.getValue();
  }

  private JString pControlFormaPago = new JString() {
      public void preset() throws Exception {
          pControlFormaPago.setValue(getControlFormaPago());
      }
  };
  
  public String getControlFormaPago() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pFormaPagoTkm.getValue();
		if (!pType.getValue().equals(CR_DIFERENCIA) ) return pFormaPago.getValue();
		return BizLiqHeader.isContado( pFormaPagoTkm.getValue())==(BizLiqHeader.isContado(pFormaPago.getValue())) ? pFormaPago.getValue() : "TKM:" + pFormaPagoTkm.getValue() + " ORA:" + pFormaPago.getValue();
  }
  
  private JString pControlRuta = new JString() {
    public void preset() throws Exception {
    	pControlRuta.setValue(getControlRuta());
    }
	};
	
	public String getControlRuta() throws Exception {
  	if (pType.getValue().equals(CR_FALTANTEORCL) ) return ""+pRutaTkm.getValue();
			if (!pType.getValue().equals(CR_DIFERENCIA) ) return pRuta.getValue();
	    return pRutaTkm.getValue().equals(pRuta.getValue()) ? pRuta.getValue() : "TKM:" + pRutaTkm.getValue() + " ORA:" + pRuta.getValue();
	}

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Getter & Setter methods (tkm added where applicable)
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void setPrestadorTkm(String zValue) throws Exception { pPrestadorTkm.setValue(zValue); }
  public String getPrestadorTkm() throws Exception { return pPrestadorTkm.getValue(); }

  public void setIataTkm(String zValue) throws Exception { pIataTkm.setValue(zValue); }
  public String getIataTkm() throws Exception { return pIataTkm.getValue(); }


	public void setOrganization(String zValue) throws Exception { 
		if (zValue == null || zValue.equals(""))
			PssLogger.logDebug("log point");
		pOrganization.setValue(zValue);
		}
	public String getOrganization() throws Exception { return pOrganization.getValue(); }
	public boolean isNullOrganization() throws Exception { return pOrganization.isNull(); }
	 
  public void setIata(String zValue) throws Exception { pIata.setValue(zValue); }
  public String getIata() throws Exception { return pIata.getValue(); }

  public void setTarifaTkm(double zValue) throws Exception { pTarifaTkm.setValue(zValue); }
  public double getTarifaTkm() throws Exception { return pTarifaTkm.getValue(); }

  public void setIvaTkm(double zValue) throws Exception { pIvaTkm.setValue(zValue); }
  public double getIvaTkm() throws Exception { return pIvaTkm.getValue(); }

  public void setTuaTkm(double zValue) throws Exception { pTuaTkm.setValue(zValue); }
  public double getTuaTkm() throws Exception { return pTuaTkm.getValue(); }

  public void setImporteTkm(double zValue) throws Exception { pImporteTkm.setValue(zValue); }
  public double getImporteTkm() throws Exception { return pImporteTkm.getValue(); }

  public void setSaldoTkm(double zValue) throws Exception { pSaldoTkm.setValue(zValue); }
  public double getSaldoTkm() throws Exception { return pSaldoTkm.getValue(); }

  public void setTipoServicioTkm(String zValue) throws Exception { pTipoServicioTkm.setValue(zValue); }
  public String getTipoServicioTkm() throws Exception { return pTipoServicioTkm.getValue(); }

  public void setClaseTkm(String zValue) throws Exception { pClaseTkm.setValue(zValue); }
  public String getClaseTkm() throws Exception { return pClaseTkm.getValue(); }

  public void setClasesTkm(String zValue) throws Exception { pClasesTkm.setValue(zValue); }
  public String getClasesTkm() throws Exception { return pClasesTkm.getValue(); }

  public void setGDSTkm(String zValue) throws Exception { pGDSTkm.setValue(zValue); }
  public String getGDSTkm() throws Exception { return pGDSTkm.getValue(); }

  public void setEmisionTkm(String zValue) throws Exception { pEmisionTkm.setValue(zValue); }
  public String getEmisionTkm() throws Exception { return pEmisionTkm.getValue(); }
  
  public void setNroTarjetaTkm(String zValue) throws Exception { pNroTarjetaTkm.setValue(zValue); }
  public String getNroTarjetaTkm() throws Exception { return pNroTarjetaTkm.getValue(); }

  public void setOrigenTkm(String zValue) throws Exception { pOrigenTkm.setValue(zValue); }
  public String getOrigenTkm() throws Exception { return pOrigenTkm.getValue(); }

  public void setDestinoTkm(String zValue) throws Exception { pDestinoTkm.setValue(zValue); }
  public String getDestinoTkm() throws Exception { return pDestinoTkm.getValue(); }
 
  public void setFechaSalidaTkm(Date zValue) throws Exception { pFechaSalidaTkm.setValue(zValue); }
  public Date gettFechaSalidaTkm() throws Exception { return pFechaSalidaTkm.getValue(); }

  public void setFechaFinTkm(Date zValue) throws Exception { pFechaFinTkm.setValue(zValue); }
  public Date gettFechaFinTkm() throws Exception { return pFechaFinTkm.getValue(); }

  public void setLineasTkm(String zValue) throws Exception { pLineasTkm.setValue(zValue); }
  public String getLineasTkm() throws Exception { return pLineasTkm.getValue(); }

  public void setFormaPagoTkm(String zValue) throws Exception { pFormaPagoTkm.setValue(zValue); }
  public String getFormaPagoTkm() throws Exception { return pFormaPagoTkm.getValue(); }

	public void setCompany(String zValue) throws Exception { pCompany.setValue(zValue); }
	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setLiquidacionId(long zValue) throws Exception { pLiquidacionId.setValue(zValue); }
	public long getLiquidacionId() throws Exception { return pLiquidacionId.getValue(); }

	public void setLinea(long zValue) throws Exception { pLinea.setValue(zValue); }
	public long getLinea() throws Exception { return pLinea.getValue(); }

	public void setError(String zValue) throws Exception { pError.setValue(zValue); }
	public String getError() throws Exception { return pError.getValue(); }

	public void setInterfaceId(long zValue) throws Exception { pInterfaceId.setValue(zValue); }
	public long getInterfaceId() throws Exception { return pInterfaceId.getValue(); }

	public void setType(String zValue) throws Exception { pType.setValue(zValue); }
	public String getType() throws Exception { return pType.getValue(); }

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

	public void setPrestador(String zValue) throws Exception { pPrestador.setValue(zValue); }
	public String getPrestador() throws Exception { return pPrestador.getValue(); }

	public void setRuta(String zValue) throws Exception { pRuta.setValue(zValue); }
	public String getRuta() throws Exception { return pRuta.getValue(); }
	
	public void setRutaTkm(String zValue) throws Exception { pRutaTkm.setValue(zValue); }
	public String getRutaTkm() throws Exception { return pRutaTkm.getValue(); }

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

	public void setTipoError(String zValue) throws Exception { pType.setValue(zValue); }
	public String getTipoError() throws Exception { return pType.getValue(); }

	public String  getCriticidadDescr() throws Exception {
		return getTiposErrores().getElement(pType.getValue());
	}
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Property creation methods
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected static JMap<String,String> getTiposErrores() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizLiqError.CR_FALTANTETKM, JLanguage.translate("Faltante TKM"));
		map.addElement(BizLiqError.CR_FALTANTEORCL, JLanguage.translate("Faltante Oracle"));
		map.addElement(BizLiqError.CR_FALTANTEHIST, JLanguage.translate("Faltante Histórico"));
		map.addElement(BizLiqError.CR_WARNING, JLanguage.translate("Faltante DXE"));
		map.addElement(BizLiqError.CR_DIFERENCIA, JLanguage.translate("Diferencias"));
		map.addElement(BizLiqError.CR_VOID, JLanguage.translate("Faltante Void"));
			map.addElement(BizLiqError.CR_OK, JLanguage.translate("Ok"));
		map.addElement(BizLiqError.CR_ALLERRORS, JLanguage.translate("Solo problemas"));
		return map;
	}
	protected static JMap<String,String> getEditTiposErrores() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizLiqError.CR_FALTANTETKM, JLanguage.translate("Faltante TKM"));
		map.addElement(BizLiqError.CR_FALTANTEORCL, JLanguage.translate("Faltante Oracle"));
		map.addElement(BizLiqError.CR_FALTANTEHIST, JLanguage.translate("Faltante Histórico"));
		map.addElement(BizLiqError.CR_WARNING, JLanguage.translate("Faltante DXE"));
		map.addElement(BizLiqError.CR_DIFERENCIA, JLanguage.translate("Diferencias"));
		map.addElement(BizLiqError.CR_VOID, JLanguage.translate("Faltante Void"));
		return map;
	}
  public void createProperties() throws Exception {
      this.addItem("company", pCompany);
      this.addItem("liquidacion_id", pLiquidacionId);
      this.addItem("linea", pLinea);
      this.addItem("organization", pOrganization);
      this.addItem("type_error", pType);
      this.addItem("type_descr", pCriticidadDescr);
      this.addItem("error", pError);
      this.addItem("interface_id", pInterfaceId);
      this.addItem("dk", pDk);
      this.addItem("tipo_doc", pTipoDoc);
      this.addItem("nro_doc", pNroDoc);
      this.addItem("fecha_doc", pFechaDoc);
      this.addItem("nro_boleto", pNroBoleto);
      this.addItem("prestador", pPrestador);
      this.addItem("prestador_tkm", pPrestadorTkm);
      this.addItem("ruta", pRuta);
      this.addItem("ruta_tkm", pRutaTkm);
      this.addItem("pasajero", pPasajero);
      this.addItem("reserva", pReserva);
      this.addItem("iata", pIata);
      this.addItem("iata_tkm", pIataTkm);
      this.addItem("tarifa", pTarifa);
      this.addItem("tarifa_tkm", pTarifaTkm);
      this.addItem("iva", pIva);
      this.addItem("iva_tkm", pIvaTkm);
      this.addItem("tua", pTua);
      this.addItem("tua_tkm", pTuaTkm);
      this.addItem("importe", pImporte);
      this.addItem("importe_tkm", pImporteTkm);
      this.addItem("saldo", pSaldo);
      this.addItem("saldo_tkm", pSaldoTkm);
      this.addItem("comision", pComision);
      this.addItem("porc_comision", pPorcComision);
      this.addItem("tipo_servicio", pTipoServicio);
      this.addItem("tipo_servicio_tkm", pTipoServicioTkm);
      this.addItem("origen", pOrigen);
      this.addItem("clase", pClase);
      this.addItem("clase_tkm", pClaseTkm);
      this.addItem("clases", pClases);
      this.addItem("clases_tkm", pClasesTkm);
      this.addItem("lineas", pLineas);
      this.addItem("lineas_tkm", pLineasTkm);
      this.addItem("forma_pago", pFormaPago);
      this.addItem("forma_pago_tkm", pFormaPagoTkm);
      this.addItem("gds_tkm", pGDSTkm);
      this.addItem("emision_tkm", pEmisionTkm);
      this.addItem("tarjeta_tkm", pNroTarjetaTkm);
      this.addItem("fecha_salida_tkm", pFechaSalidaTkm);
      this.addItem("fecha_fin_tkm", pFechaFinTkm);
      this.addItem("origen_tkm", pOrigenTkm);
      this.addItem("destino_tkm", pDestinoTkm);
      this.addItem("porc_incentivo", pPorcIncentivo);
      this.addItem("incentivo", pIncentivo);
      this.addItem("tipo", pTipo);
      this.addItem("contado", pContado);
      this.addItem("tarjeta", pTarjeta);
      this.addItem("fiscal", pFiscal);
      this.addItem("nofiscal", pNoFiscal);
      this.addItem("noDevengado", pNoDevengado);
      this.addItem("control_iata", pControlIata);
      this.addItem("control_prestador", pControlPrestador);
      this.addItem("control_tarifa", pControlTarifa);
      this.addItem("control_iva", pControlIva);
      this.addItem("control_tua", pControlTua);
      this.addItem("control_importe", pControlImporte);
      this.addItem("control_saldo", pControlSaldo);
      this.addItem("control_tipo_servicio", pControlTipoServicio);
      this.addItem("control_clase", pControlClase);
      this.addItem("control_clases", pControlClases);
      this.addItem("control_lineas", pControlLineas);
      this.addItem("control_forma_pago", pControlFormaPago);
      this.addItem("control_ruta", pControlRuta);
  }

  public void createFixedProperties() throws Exception {
      this.addFixedItem(KEY, "linea", "Linea", false, false, 64);
      this.addFixedItem(FIELD, "company", "Company", true, true, 18);
      this.addFixedItem(FIELD, "liquidacion_id", "Liquidacion ID", true, true, 18);
      this.addFixedItem(FIELD, "organization", "Organización", true, false, 50);
      this.addFixedItem(FIELD, "type_error", "Criticidad", true, false, 50);
      this.addFixedItem(VIRTUAL, "type_descr", "Criticidad", true, false, 50);
      this.addFixedItem(FIELD, "error", "Error", true, false, 2000);
      this.addFixedItem(FIELD, "interface_id", "Interface ID", true, false, 18);
      this.addFixedItem(FIELD, "dk", "DK", true, false, 20);
      this.addFixedItem(FIELD, "tipo_doc", "Tipo Doc", true, false, 40);
      this.addFixedItem(FIELD, "nro_doc", "Nro Doc", true, false, 40);
      this.addFixedItem(FIELD, "fecha_doc", "Fecha Doc", true, false, 18);
      this.addFixedItem(FIELD, "nro_boleto", "Nro Boleto", true, false, 40);
      this.addFixedItem(FIELD, "prestador", "Prestador", true, false, 400);
      this.addFixedItem(FIELD, "prestador_tkm", "Prestador TKM", true, false, 400);
      this.addFixedItem(FIELD, "ruta", "Ruta", true, false, 400);
      this.addFixedItem(FIELD, "ruta_tkm", "Ruta TKM", true, false, 400);
      this.addFixedItem(FIELD, "pasajero", "Pasajero", true, false, 400);
      this.addFixedItem(FIELD, "reserva", "Reserva", true, false, 400);
      this.addFixedItem(FIELD, "iata", "IATA", true, false, 40);
      this.addFixedItem(FIELD, "iata_tkm", "IATA TKM", true, false, 40);
      this.addFixedItem(FIELD, "tarifa", "Tarifa", true, false, 18, 2);
      this.addFixedItem(FIELD, "tarifa_tkm", "Tarifa TKM", true, false, 18, 2);
      this.addFixedItem(FIELD, "iva", "IVA", true, false, 18, 2);
      this.addFixedItem(FIELD, "iva_tkm", "IVA TKM", true, false, 18, 2);
      this.addFixedItem(FIELD, "tua", "TUA", true, false, 18, 2);
      this.addFixedItem(FIELD, "tua_tkm", "TUA TKM", true, false, 18, 2);
      this.addFixedItem(FIELD, "importe", "Importe", true, false, 18, 2);
      this.addFixedItem(FIELD, "importe_tkm", "Importe TKM", true, false, 18, 2);
      this.addFixedItem(FIELD, "saldo", "Saldo", true, false, 18, 2);
      this.addFixedItem(FIELD, "saldo_tkm", "Saldo TKM", true, false, 18, 2);
      this.addFixedItem(FIELD, "tipo_servicio", "Tipo Servicio", true, false, 40);
      this.addFixedItem(FIELD, "tipo_servicio_tkm", "Tipo Servicio TKM", true, false, 40);
      this.addFixedItem(FIELD, "clase", "Clase", true, false, 40);
      this.addFixedItem(FIELD, "clase_tkm", "Clase TKM", true, false, 40);
      this.addFixedItem(FIELD, "clases", "Clases", true, false, 40);
      this.addFixedItem(FIELD, "clases_tkm", "Clases TKM", true, false, 40);
      this.addFixedItem(FIELD, "lineas", "Lineas", true, false, 40);
      this.addFixedItem(FIELD, "lineas_tkm", "Lineas TKM", true, false, 40);
      this.addFixedItem(FIELD, "forma_pago", "Forma Pago", true, false, 40);
      this.addFixedItem(FIELD, "forma_pago_tkm", "Forma Pago TKM", true, false, 40);
      
      this.addFixedItem(FIELD, "gds_tkm", "GDS TKM", true, false, 40);
      this.addFixedItem(FIELD, "emision_tkm", "Emision TKM", true, false, 50);
      this.addFixedItem(FIELD, "tarjeta_tkm", "Nro Tarjeta TKM", true, false, 100);
      this.addFixedItem(FIELD, "fecha_salida_tkm", "Fecha Salida TKM", true, false, 18);
      this.addFixedItem(FIELD, "fecha_fin_tkm", "Fecha fin TKM", true, false, 18);
      this.addFixedItem(FIELD, "origen_tkm", "Origen TKM", true, false, 40);
      this.addFixedItem(FIELD, "destino_tkm", "Destino TKM", true, false, 40);
   
      this.addFixedItem(FIELD, "porc_incentivo", "Porc. Incentivo", true, false, 18, 2);
      this.addFixedItem(FIELD, "incentivo", "Incentivo", true, false, 18, 2);
      this.addFixedItem(FIELD, "tipo", "Tipo", true, false, 20);
      this.addFixedItem(FIELD, "contado", "Contado", true, false, 18, 2);
      this.addFixedItem(FIELD, "tarjeta", "Tarjeta", true, false, 18, 2);
      this.addFixedItem(FIELD, "fiscal", "Fiscal", true, false, 18, 2);
      this.addFixedItem(FIELD, "nofiscal", "No Fiscal", true, false, 18, 2);
      this.addFixedItem(FIELD, "noDevengado", "No Devengado", true, false, 18, 2);
      this.addFixedItem(VIRTUAL, "control_iata", "Iata", true, false, 100);
      this.addFixedItem(VIRTUAL, "control_tarifa", "Tarifa", true, false, 100);
      this.addFixedItem(VIRTUAL, "control_iva", "IVA", true, false, 100);
      this.addFixedItem(VIRTUAL, "control_tua", "TUA", true, false, 100);
      this.addFixedItem(VIRTUAL, "control_importe", "Importe", true, false, 100);
      this.addFixedItem(VIRTUAL, "control_saldo", "Saldo", true, false, 100);
      this.addFixedItem(VIRTUAL, "control_tipo_servicio", "Tipo Servicio", true, false, 1000);
      this.addFixedItem(VIRTUAL, "control_clase", "Clase", true, false, 100);
      this.addFixedItem(VIRTUAL, "control_clases", "Clases", true, false, 1000);
      this.addFixedItem(VIRTUAL, "control_lineas", "Lineas", true, false, 1000);
      this.addFixedItem(VIRTUAL, "control_forma_pago", "Forma Pago", true, false, 100);
      this.addFixedItem(VIRTUAL, "control_ruta", "Ruta", true, false, 100);
      this.addFixedItem(VIRTUAL, "control_prestador", "Prestador", true, false, 100);
  }
  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("type_error", createControlCombo(JWins.createVirtualWinsFromMap(BizLiqError.getEditTiposErrores()),null, null) );
  	super.createControlProperties();
  }
  public String GetTable() {
      return "bsp_liquidation_error";
  }

  public boolean read(long zId) throws Exception {
      addFilter("linea", zId);
      return read();
  }
  
  public void execProcessNoProblem() throws Exception {
		JExec oExec = new JExec(this, "processNoProblem") {

			@Override
			public void Do() throws Exception {
				processNoProblem();
			}
		};
		oExec.execute();
	}
	public void processNoProblem() throws Exception {
		setTipoError(CR_OK);
		setError(BizUsuario.getUsr().GetUsuario()+" marco como NO error");
		update();
	}
	
}
