package pss.bsp.bspBusiness;

import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.codec.net.URLCodec;

import com.ibm.icu.util.Calendar;

import pss.JPath;
import pss.bsp.GuiModuloBSP;
import pss.bsp.bo.BizInterfazBO;
import pss.bsp.clase.BizClase;
import pss.bsp.consola.config.BizBSPConfig;
import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.prorrateo.BizProrrateo;
import pss.bsp.contrato.logica.ILogicaContrato;
import pss.bsp.familia.BizFamilia;
import pss.bsp.gds.BizInterfazNew;
import pss.bsp.market.BizMarket;
import pss.bsp.pdf.BizPDF;
import pss.bsp.regions.BizRegion;
import pss.common.customList.config.carpetas.BizCarpeta;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.IActionData;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.sql.BizSqlEvent;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.BizCompanyCountry;
import pss.common.regions.currency.history.BizMonedaCotizacion;
import pss.common.regions.divitions.BizPais;
import pss.common.regions.divitions.BizPaisLista;
import pss.common.security.BizOperacion;
import pss.common.security.BizOperacionRol;
import pss.common.security.BizRol;
import pss.common.security.BizRolVinculado;
import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.common.security.BizUsuarioRol;
import pss.common.security.BizWebUserProfile;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRFare;
import pss.tourism.pnr.BizPNRFilename;
import pss.tourism.pnr.BizPNROtro;
import pss.tourism.pnr.BizPNRReserva;
import pss.tourism.pnr.BizPNRSegmentoAereo;
import pss.tourism.pnr.BizPNRTicket;

public class BizBSPCompany extends BizCompany implements IActionData {

	private JBoolean pConcentrador = new JBoolean() {
		public void preset() throws Exception {
  		setValue(isConcentrador());
  	};
	};
  private JLong pLicenciasCalculadas = new JLong() {
  	public void preset() throws Exception {
  		setValue(getLicenciasCalculadas());
  	};
  };
  private JLong pLicencias = new JLong() {
  	public void preset() throws Exception {
  		BizCompanyExtraData extra =getObjExtraData();
  		if (extra==null) return;
  		setValue(extra.getLicencias());
  	};
  };
  private JDate pFechaIncio = new JDate() {
  	public void preset() throws Exception {
  		BizCompanyExtraData extra =getObjExtraData();
  		if (extra==null) return;
  		setValue(extra.getFechaIncio());
  	};
  };
  private JDate pFechaHasta = new JDate() {
  	public void preset() throws Exception {
  		BizCompanyExtraData extra =getObjExtraData();
  		if (extra==null) return;
  		setValue(extra.getFechaHasta());
  	};
  };
  private JString pEmail = new JString() {
  	public void preset() throws Exception {
  		setValue(getEmail());
  	};
  };
  private JString pPais = new JString() {
  	public void preset() throws Exception {
  		BizCompanyExtraData extra =getObjExtraData();
  		if (extra==null) return;
  		setValue(extra.getPais());
  	};
  };
  private JLong pRenovaciones = new JLong() {
  	public void preset() throws Exception {
  		BizCompanyExtraData extra =getObjExtraData();
  		if (extra==null) return;
  		setValue(extra.getRenovaciones());
  	};
  };
  private JString pTipoLicencia = new JString() {
  	public void preset() throws Exception {
  		BizCompanyExtraData extra =getObjExtraData();
  		if (extra==null) return;
  		setValue(extra.getTipoLicencia());
  	};
  };
  private JLong pVersion = new JLong() {
  	public void preset() throws Exception {
  		BizCompanyExtraData extra =getObjExtraData();
  		if (extra==null) return;
  		setValue(extra.getVersion());
  	};
  };
  private JBoolean pInactiva = new JBoolean() {
  	public void preset() throws Exception {
  		BizCompanyExtraData extra =getObjExtraData();
  		if (extra==null) return;
  		setValue(extra.getInactiva());
  	};
  };
  private JString pPaisDescr = new JString() {
  	public void preset() throws Exception {
  		setValue(getDescrPais());
  	};
  };
  private JLong pCantidadTickets = new JLong() {
  	public void preset() throws Exception {
  		setValue(getCantidadTickets());
  	};
  };
  private JLong pCantidadBookings = new JLong() {
  	public void preset() throws Exception {
  		setValue(getCantidadBookings());
  	};
  };
  private JFloat pTicketsPromedio = new JFloat() {
  	public void preset() throws Exception {
  		setValue(getTicketsPromedio());
  	};
  	public int getPrecision() {return 2;};
  };
  private JFloat pTicketsPromedioBooks = new JFloat() {
  	public void preset() throws Exception {
  		setValue(getTicketsPromedioBooks());
  	};
  	public int getPrecision() {return 2;};
  };

  private JString pMes12 = new JString() {
  	public void preset() throws Exception {
  		setValue(getMes(12));
  	};
   };
   private JString pMes11 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(11));
   	};
  };
  private JString pMes10 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(10));
   	};
  };
  private JString pMes9 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(9));
   	};
  };
  private JString pMes8 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(8));
   	};
  };
  private JString pMes7 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(7));
   	};
  };
  
  private JString pMes6 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(6));
   	};
  };
  private JString pMes5 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(5));
   	};
  };
  private JString pMes4 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(4));
   	};
  };
  private JString pMes3 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(3));
   	};
  };
  private JString pMes2 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(2));
   	};
  };
  private JString pMes1 = new JString() {
   	public void preset() throws Exception {
   		setValue(getMes(1));
   	};
  };
  
  private JDateTime pFechaUltimoPNR = new JDateTime() {
  	public void preset() throws Exception {
  		setValue(getFechaUltimoPNR());
  	};
  };
  private JBoolean pDependant = new JBoolean() {
	  	public void preset() throws Exception {
	  		BizCompanyExtraData extra =getObjExtraData();
	  		if (extra==null) return;
	  		setValue(extra.getDependant());
	  	};
	  };
	private JBoolean pCNS = new JBoolean() {
	  	public void preset() throws Exception {
	  		BizCompanyExtraData extra =getObjExtraData();
	  		if (extra==null) return;
	  		setValue(extra.getCNS());
	  	};
	  };

  private JString pCodigoCliente = new JString() {
  	public void preset() throws Exception {
  		BizCompanyExtraData extra =getObjExtraData();
  		if (extra==null) return;
  		setValue(extra.getCodigoCliente());
  	};
  };

  public long getLicencias() throws Exception {
   	return pLicencias.getValue();
  }
	Long liccalc;
  public long getLicenciasCalculadas() throws Exception {
  	if (liccalc!=null) return liccalc;
   	return liccalc=(long)Math.ceil(getTicketsPromedio()/500.0);
  }
	String sMes[]=new String[13];
  public String getMes(int mes) throws Exception {
  	if (sMes[mes]!=null) return sMes[mes];
   	BizInterfazNew news = new BizInterfazNew();
  	news.dontThrowException(true);
  	if (!news.read(getCompany())) return "";
  	return sMes[mes]=(String)news.getMes(mes);
  }
	Long cantidad;
  public long getCantidadTickets() throws Exception {
  	if (cantidad!=null) return cantidad;
   	BizInterfazNew news = new BizInterfazNew();
  	news.dontThrowException(true);
  	if (!news.read(getCompany())) return 0;
  	return cantidad=(long)news.getTicketTotal();
  }
  Long cantidadbook;
  public long getCantidadBookings() throws Exception {
  	if (cantidadbook!=null) return cantidadbook;
   	BizInterfazNew news = new BizInterfazNew();
  	news.dontThrowException(true);
  	if (!news.read(getCompany())) return 0;
  	return cantidadbook=(long)news.getBookingTotal();
  }
  
  Long promedio;
  public long getTicketsPromedio() throws Exception {
  	if (promedio!=null) return promedio;
   	BizInterfazNew news = new BizInterfazNew();
  	news.dontThrowException(true);
  	if (!news.read(getCompany())) return 0;
  	return promedio=(long)news.getTicketMensual();
  }
  Long promedioBooks;
  public long getTicketsPromedioBooks() throws Exception {
  	if (promedioBooks!=null) return promedioBooks;
   	BizInterfazNew news = new BizInterfazNew();
  	news.dontThrowException(true);
  	if (!news.read(getCompany())) return 0;
  	return promedioBooks=(long)news.getBookingMensual();
  }
  
  Date ultimoPNR;
  public Date getFechaUltimoPNR() throws Exception {
  	if (ultimoPNR!=null) return ultimoPNR;
  	BizInterfazNew news = new BizInterfazNew();
  	news.dontThrowException(true);
  	if (!news.read(getCompany())) return null;
  	return ultimoPNR = news.getLastPnr();
 }
  
  
  @Override
  public void clean() throws Exception {
  	cantidad=null;
  	promedio=null;
  	cantidadbook=null;
  	promedioBooks=null;
  	ultimoPNR=null;
  	super.clean();
  }
  
	public BizBSPCompany() throws Exception {
		super();
	}
	
	public static BizBSPConfig getConfigView(String company)  throws Exception {
		BizBSPConfig other = new  BizBSPConfig();
		other.read(company,"*");
		return other;
	}

	String monedaDefa;
	public String  getMonedaDefa() throws Exception {
		if (monedaDefa!=null) return monedaDefa;
		BizPais c =  BizPais.findPais(getCountry());
		return monedaDefa=c.GetMonedaLocal();
	}

	String country;
	public String  getCountry() throws Exception {
		if (country!=null) return country;
		BizCompanyCountry c = new BizCompanyCountry();
		c.addFilter("company", getCompany());
		c.read();
		return country=c.getCountry();
	}
	@Override
	public void processDelete() throws Exception {
		if (!getObjExtraData().getInactiva())
			throw new Exception("Solo se pueden eliminar empresas inactivas");
		super.processDelete();
		deleteAdminUser();
		deleteSegConfig();
		deleteRoles();
		deleteReports();
		deleteDataMining();
		deleteIndicadores();
		deleteRegiones();
		deleteCompanyCountry();
		deleteTickets();
		deleteContratos();
		deleteBsp();
		JTools.EliminarDirectoryFiles(JPath.PssPathInputProcessed()+"/"+getCompany(), null, null, null);
		JTools.EliminarDirectoryFiles(JPath.PssPathInputError()+"/"+getCompany(), null, null, null);
		
		
	}
	private void deleteCompanyCountry() throws Exception {
		JRecords<BizCompanyCountry> oData= new JRecords<BizCompanyCountry>(BizCompanyCountry.class);
		oData.addFilter("company",getCompany());
		oData.processDeleteAll();
		JRecords<BizCompanyExtraData> oData2= new JRecords<BizCompanyExtraData>(BizCompanyExtraData.class);
		oData2.addFilter("company",getCompany());
		oData2.processDeleteAll();
		
	}

	private void deleteRegiones() throws Exception {
		JRecords<BizRegion> oData= new JRecords<BizRegion>(BizRegion.class);
		oData.addFilter("company",getCompany());
		oData.processDeleteAll();
		JRecords<BizClase> oData2= new JRecords<BizClase>(BizClase.class);
		oData2.addFilter("company",getCompany());
		oData2.processDeleteAll();
		JRecords<BizFamilia> oData3= new JRecords<BizFamilia>(BizFamilia.class);
		oData3.addFilter("company",getCompany());
		oData3.processDeleteAll();
		JRecords<BizMarket> oData4= new JRecords<BizMarket>(BizMarket.class);
		oData4.addFilter("company",getCompany());
		oData4.processDeleteAll();
	}

	private void deleteIndicadores() throws Exception {
		JRecords<BizSqlEvent> oData= new JRecords<BizSqlEvent>(BizSqlEvent.class);
		oData.addFilter("company",getCompany());
		oData.processDeleteAll();
		JRecords<BizSqlEventAction> oData2= new JRecords<BizSqlEventAction>(BizSqlEventAction.class);
		oData2.addFilter("company",getCompany());
		oData2.processDeleteAll();
		JRecords<BizSqlEventHistory> oData3= new JRecords<BizSqlEventHistory>(BizSqlEventHistory.class);
		oData3.addFilter("company",getCompany());
		oData3.processDeleteAll();
	}

	private void deleteTickets() throws Exception {
		JRecords<BizPNRTicket> oData= new JRecords<BizPNRTicket>(BizPNRTicket.class);
		oData.addFilter("company",getCompany());
		oData.processDeleteAll();
		JRecords<BizPNRReserva> oData2= new JRecords<BizPNRReserva>(BizPNRReserva.class);
		oData2.addFilter("company",getCompany());
		oData2.processDeleteAll();
		JRecords<BizPNROtro> oData3= new JRecords<BizPNROtro>(BizPNROtro.class);
		oData3.addFilter("company",getCompany());
		oData3.processDeleteAll();
		JRecords<BizBooking> oData4= new JRecords<BizBooking>(BizBooking.class);
		oData4.addFilter("company",getCompany());
		oData4.processDeleteAll();
		JRecords<BizProrrateo> oDataE= new JRecords<BizProrrateo>(BizProrrateo.class);
		oDataE.addFilter("company",getCompany());
		oDataE.delete();
		JRecords<BizMonedaCotizacion> oDataM= new JRecords<BizMonedaCotizacion>(BizMonedaCotizacion.class);
		oDataM.addFilter("company",getCompany());
		oDataM.delete();
	}
	
	private void deleteContratos() throws Exception {
		JRecords<BizContrato> oData= new JRecords<BizContrato>(BizContrato.class);
		oData.addFilter("company",getCompany());
		oData.processDeleteAll();
	}
	
	private void deleteBsp() throws Exception {
		JRecords<BizPDF> oData= new JRecords<BizPDF>(BizPDF.class);
		oData.addFilter("company",getCompany());
		oData.processDeleteAll();
		JRecords<BizInterfazBO> oData2= new JRecords<BizInterfazBO>(BizInterfazBO.class);
		oData2.addFilter("company",getCompany());
		oData2.processDeleteAll();
		
	}

	private void deleteReports() throws Exception {
		JRecords<BizPlantilla> oData= new JRecords<BizPlantilla>(BizPlantilla.class);
		oData.addFilter("company",getCompany());
		oData.processDeleteAll();

	}
	private void deleteDataMining() throws Exception {
		JRecords<BizCustomList> oData= new JRecords<BizCustomList>(BizCustomList.class);
		oData.addFilter("company",getCompany());
		oData.processDeleteAll();
		JRecords<BizCarpeta> oDataC= new JRecords<BizCarpeta>(BizCarpeta.class);
		oDataC.addFilter("company",getCompany());
		oDataC.delete();

	}

	protected void deleteAdminUser() throws Exception {
		JRecords<BizUsuario> oData= new JRecords<BizUsuario>(BizUsuario.class);
		oData.addFilter("company",getCompany());
		oData.processDeleteAll();


	}
	
	private void deleteSegConfig() throws Exception {
		JRecords<BizSegConfiguracion> oData= new JRecords<BizSegConfiguracion>(BizSegConfiguracion.class);
		oData.addFilter("company",getCompany());
		oData.processDeleteAll();
	}

	protected void assingAdminWedData(BizWebUserProfile oWebUserProfile, BizUsuario user) throws Exception {
		oWebUserProfile.setUsuario(user.GetUsuario());
		oWebUserProfile.setHomePage("pss.bsp.GuiModuloBSP_10");
	}
	
		
	private void deleteRoles() throws Exception {

		JRecords<BizRol> recs31= new JRecords<BizRol>(BizRol.class);
		recs31.addFilter("company", getCompany());
		recs31.processDeleteAll();
	
		JRecords<BizOperacion> recs32= new JRecords<BizOperacion>(BizOperacion.class);
		recs32.addFilter("company", getCompany());
		recs32.processDeleteAll();

		JRecords<BizOperacionRol> recs33= new JRecords<BizOperacionRol>(BizOperacionRol.class);
		recs33.addFilter("company", getCompany());
		recs33.processDeleteAll();


		JRecords<BizRolVinculado> recs36= new JRecords<BizRolVinculado>(BizRolVinculado.class);
		recs36.addFilter("company", getCompany());
		recs36.processDeleteAll();	
	}

	protected JObjBDs<BizContrato> pContratos= new JObjBDs<BizContrato>() {
		public void preset() throws Exception {
			pContratos.setValue(getObjContratos());
		}
	};

	public JRecords<BizContrato> getObjContratos() throws Exception {
	  JRecords<BizContrato> records = new JRecords<BizContrato>(BizContrato.class);
		records.addFilter("company", this.getCompany());
		records.addFilter("active", true);
		return records;
	}
	
	public synchronized static boolean isBSPUser() {
		return BizUsuario.getUsr() instanceof BizBSPUser;
	}
	@Override
	public void createProperties() throws Exception {
    this.addItem( "licencias", pLicencias );
    this.addItem( "licencias_calc", pLicenciasCalculadas );
    this.addItem( "fecha_incio", pFechaIncio );
    this.addItem( "fecha_hasta", pFechaHasta );
    this.addItem( "pais", pPais );
    this.addItem( "email", pEmail );
    this.addItem( "renovaciones", pRenovaciones );
    this.addItem( "tipo_licencia", pTipoLicencia );
    this.addItem( "cantidad", pCantidadTickets );
    this.addItem( "cantidad_books", pCantidadBookings );
    this.addItem( "promedio", pTicketsPromedio );
    this.addItem( "promedio_books", pTicketsPromedioBooks );
    this.addItem( "mes12", pMes12 );
    this.addItem( "mes11", pMes11 );
    this.addItem( "mes10", pMes10 );
    this.addItem( "mes9", pMes9 );
    this.addItem( "mes8", pMes8 );
    this.addItem( "mes7", pMes7 );
    this.addItem( "mes6", pMes6 );
    this.addItem( "mes5", pMes5 );
    this.addItem( "mes4", pMes4 );
    this.addItem( "mes3", pMes3 );
    this.addItem( "mes2", pMes2 );
    this.addItem( "mes1", pMes1 );
    this.addItem( "ultimo", pFechaUltimoPNR );
    this.addItem( "pais_descr", pPaisDescr );
    this.addItem( "contratos", pContratos );
    this.addItem( "tkmversion", pVersion );
    this.addItem( "inactiva", pInactiva );
    this.addItem( "concentrador", pConcentrador );
    this.addItem( "dependant", pDependant );
    this.addItem( "cns", pCNS );
    this.addItem( "codigo_cliente", pCodigoCliente );
    
    
    super.createProperties();
  }

	@Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( VIRTUAL, "licencias_calc", "Licencias Calc.", true, false, 18 );
    this.addFixedItem( VIRTUAL, "licencias", "Licencias", true, false, 18 );
    this.addFixedItem( VIRTUAL, "fecha_incio", "Fecha incio", true, false, 10 );
    this.addFixedItem( VIRTUAL, "fecha_hasta", "Fecha hasta", true, false, 10 );
    this.addFixedItem( VIRTUAL, "pais", "Pais", true, false, 15 );
    this.addFixedItem( VIRTUAL, "email", "Email", true, false, 500 );
    this.addFixedItem( VIRTUAL, "pais_descr", "Pais descripcion", true, false, 150 );
    this.addFixedItem( VIRTUAL, "renovaciones", "Renovaciones", true, false, 18 );
    this.addFixedItem( VIRTUAL, "tipo_licencia", "Tipo licencia", true, false, 50 );
    this.addFixedItem( VIRTUAL, "cantidad", "Cantidad tickets", true, false, 18 );
    this.addFixedItem( VIRTUAL, "cantidad_books", "Cant.de Bookings", true, false, 18 );
    this.addFixedItem( VIRTUAL, "promedio", "Promedio mensual tickets", true, false, 18 );
    this.addFixedItem( VIRTUAL, "promedio_books", "Prom.mensual Books", true, false, 18 );
    this.addFixedItem( VIRTUAL, "ultimo", "Fecha Ultimo PNR", true, false, 18 );
    this.addFixedItem( VIRTUAL, "mes12", "-12 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes11", "-11 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes10", "-10 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes9", "-9 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes8", "-8 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes7", "-7 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes6", "-6 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes5", "-5 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes4", "-4 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes3", "-3 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes2", "-2 meses", true, false, 100 );
    this.addFixedItem( VIRTUAL, "mes1", "-1 mes", true, false, 100 );
    this.addFixedItem( VIRTUAL, "tkmversion", "Version", true, false, 18 );
    this.addFixedItem( VIRTUAL, "inactiva", "Inactiva", true, false, 1 );
    this.addFixedItem( VIRTUAL, "concentrador", "concentrador", true, false, 1 );
    this.addFixedItem( VIRTUAL, "dependant", "dependencia", true, false, 1 );
    this.addFixedItem( VIRTUAL, "codigo_cliente", "Codigo cliente", true, false, 50 );
    this.addFixedItem( VIRTUAL, "cns", "CNS", true, false, 1 );
    this.addFixedItem( RECORDS, "contratos", "Contratos", true, false, 18).setClase(BizContrato.class);
		super.createFixedProperties();
	}
	
	BizPaisLista objPais;
	public BizPaisLista getObjPaisLista() throws Exception {
  	if (objPais!=null) return objPais;
  	BizPaisLista p = new BizPaisLista();
  	p.dontThrowException(true);
  	if (!p.Read(pPais.getValue())) return null;
		return objPais = p;
  
  }
	public String getPais() throws Exception {
		return pPais.getValue();
	}	
	public String getDescrPais() throws Exception {
		if (getObjPaisLista()==null) return "";
		return getObjPaisLista().GetDescrip();
	}
	public String getEmail() throws Exception {
		BizBSPConfig c = BizBSPCompany.getConfigView(this.getCompany());
		if (c==null) return "";
		return c.getEmail();
	}
  
	
  BizPlantilla objPlantilla;
  public BizPlantilla getObjPlantilla() throws Exception {
  	if (objPlantilla!=null) return objPlantilla;
		BizPlantilla p = BizCompany.getObjPlantilla(getCompany(), "informe");
		if (p==null) return null;
		return objPlantilla = p;
  
  }
  
  BizCompanyExtraData objExtraData;
  public void setObjExtraData(BizCompanyExtraData objExtraData) {
		this.objExtraData = objExtraData;
	}


	public BizCompanyExtraData getObjExtraData() throws Exception {
  	if (objExtraData!=null) return objExtraData;
  	if (getCompany().equals("")) 
  		return null;
  	BizCompanyExtraData p = new BizCompanyExtraData();
  	p.dontThrowException(true);
  	if (p.read(getCompany())) return objExtraData = p;
  	p.setCompany(getCompany());
  	p.setLicencias(0);
  	p.setRenovaciones(0);
  	p.setPais(BizUsuario.getUsr().getCountry());
  	p.setTipoLicencia("TKM");
  	p.setVersion(7);
  	p.setInactiva(false);
  	p.setSuspender(false);
  	if (JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress())
    	p.processInsert();
  	else
  		p.execProcessInsert();
  	return objExtraData = p;
  
  }

  public BizSqlEventHistory  generarAviso(JFilterMap a,BizSqlEventAction action,boolean vistaPrevia) throws Exception {
		BizSqlEventHistory hist= new BizSqlEventHistory();
  	hist.setFecha(new Date());
  	hist.setIdevento(""+action.getIdevento());
  	hist.setCompany(getCompany());
  	hist.setFundamento(action.getDescripcion());
  	hist.setMensajeUsuario(action.getMensajeUsuario());
  	hist.setIdaction(action.getIdaction());
  	hist.setAccion(action.getAction());
  	hist.setDestinatario(action.getDestinatario());
 		hist.setMensaje(action.getMensajeAviso(a,hist));
  	if (!vistaPrevia) hist.setArchivoAsociado(action.getArchivoAsociado(a,hist));
  	return hist;
  }
  
	public String getNotificacionAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
  	return "";
  }
	public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
		if (!action.isAdjunto() && !action.isAccionURL()) {
			if (action.isOutEXCEL()) {
				return URLEncoder.encode(GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(70,"excel",a),"ISO-8859-1").replace("+", "%20");
			}
			if (action.isOutCSV()) {
				return URLEncoder.encode(GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(70,"csv",a),"ISO-8859-1").replace("+", "%20");
			}
			if (action.isOutPDF()) {
				BizPlantilla l =BizCompany.getObjPlantillaById(getCompany(), action.getIdplantilla());
		  	return l.generateDocSimple(this,action);
			}
			return action.getMensajeUsuario()+"<br/>"+ URLEncoder.encode(GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(70,"html",a),"ISO-8859-1").replace("+", "%20");
		}
		// mando un correo
		return action.getMensajeUsuario()+"<br/>"+ action.getObjPlantilla().generateDocSimple(hist,this);
  }
	
	public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception {
		return getCorreoAviso(a,action, hist);
	}
	public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception {
		return getCorreoAviso(a,action, hist,campo,valor);
	}	
	public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
		if (action.isOutPantalla()) {
			GuiBSPCompany l = new GuiBSPCompany();
			l.setRecord(this);
			String file = l.hashCode()+".html";
			String content = GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(70,"html",a);
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isOutEXCEL()) {
			GuiBSPCompany l = new GuiBSPCompany();
			l.setRecord(this);
			String file = l.hashCode()+".csv";
			String content = GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(70,"excel",a);
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isOutCSV()) {
			GuiBSPCompany l = new GuiBSPCompany();
			l.setRecord(this);
			String file = l.hashCode()+".csv";
			String content = GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(70,"csv",a);
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isOutPDF()) {
			BizPlantilla l =BizCompany.getObjPlantillaById(getCompany(), action.getIdplantilla());
	  	String s =  new URLCodec().decode(l.generateDocSimple(this,action));
			String file = "doc"+this.hashCode()+".html";
			String content = l.htmlToHtml(null,true,s);
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			
			return file;
		}
		
		return null;
  }


	@Override
	public String getDescripcion() throws Exception {
		return "Informe contratos";
	}



	@Override
	public String hasGenerateAviso(BizSqlEventAction action) throws Exception {
		return null;
	}


	@Override
	public void mensajeEnviado(String marca) throws Exception {
	}

	@Override
	public boolean read(String company, String id, JFilterMap param) throws Exception {
  	dontThrowException(true);
		addFilter("company",id);
		dontThrowException(true);
		return read();
	}
	
	
	public static BizBSPCompany getObjBSPCompany(String company) throws Exception {
		BizBSPCompany val = new BizBSPCompany();
		BizCompany comp = BizCompany.getCompanyCache().getElement(company);
		//val.Read(company);
		if (comp==null) return null;
		val.copyProperties(comp);
		return val;
	}
	
	public void execRenovarLicencia() throws Exception {
		JExec oExec = new JExec(null, "processRenovarLicencia") {

			@Override
			public void Do() throws Exception {
				processRenovarLicencia();
			}
		};
		oExec.execute();
	}
	
	public void processRenovarLicencia() throws Exception {
		BizCompanyExtraData extra = getObjExtraData();
		extra.setRenovaciones(extra.getRenovaciones()+1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(extra.getFechaHasta());
		c2.add(Calendar.YEAR, 1);
		extra.setFechaHasta(c2.getTime());
		extra.processUpdate();
		
	}
	
	public void execBorrarTickets() throws Exception {
		JExec oExec = new JExec(null, "processRenovarLicencia") {

			@Override
			public void Do() throws Exception {
				processBorrarTickets();
			}
		};
		oExec.execute();
	}
	public void execProcessRegenerar() throws Exception {
		JExec oExec = new JExec(null, "execProcessRegenerar") {

			@Override
			public void Do() throws Exception {
				processRegenerar();
			}
		};
		oExec.execute();
	}
	public void processRegenerar() throws Exception {
		JRecords<BizDetalle> detalles = new JRecords<BizDetalle>(BizDetalle.class);
	//	detalles.addFilter("logica", JContratoUpfrontRuta.class.getName());
		if (!getCompany().equalsIgnoreCase("DEFAULT"))
			detalles.addFilter("company", getCompany());
		JIterator<BizDetalle> it=detalles.getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle det = it.nextElement();
		  ILogicaContrato logica = det.getObjLogicaInstance();
		  BizDetalle clon = logica.getBiz();
	    clon.copyProperties(det);
	    clon.processUpdate();
		}
	}	
	public void execProcessRemigrar() throws Exception {
		JExec oExec = new JExec(null, "processRenovarLicencia") {

			@Override
			public void Do() throws Exception {
				processRemigrar();
			}
		};
		oExec.execute();
	}
	public void processRemigrar() throws Exception {
		BizNewBSPCompany newc = new BizNewBSPCompany();
		newc.setPais("MX");
		newc.setModelo("MEXICO");
		newc.setCompany(getCompany());

		newc.migrar();
	}	
	public void execBorrarIataTickets() throws Exception {
	
				processBorrarIataTickets();
		
	}
	public void processBorrarIataTickets() throws Exception {
		JRecords<BizPNRTicket> recs = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		recs.addFilter("company", getCompany());
		recs.addFilter("nro_iata", "77500152");//77500152 //86515656
		recs.addOrderBy("CodigoPNR");
		int paginado=0;
		int size=5000;
		String last ="";
		while (true) {
			recs.setOffset(paginado);
			recs.setTop(size);
			recs.setStatic(false);
			JIterator<BizPNRTicket> it = recs.getStaticIterator();
			int i =0;
			while (it.hasMoreElements()) {
				BizPNRTicket pnr = it.nextElement();
				i++;
				if (pnr.getCodigopnr().equals(last)) continue;
				last=pnr.getCodigopnr();
				try {
					pnr.execProcessDelete();;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (i<size) break;
		}
	}

	public void processBorrarTickets() throws Exception {
		/*JRecords<BizPNRTicket> recs = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		recs.addFilter("company", getCompany());
		int paginado=0;
		int size=5000;
		String last ="";
		while (true) {
			recs.setOffset(paginado);
			recs.setTop(size);
			recs.setStatic(false);
			JIterator<BizPNRTicket> it = recs.getStaticIterator();
			int i =0;
			while (it.hasMoreElements()) {
				BizPNRTicket pnr = it.nextElement();
				i++;
				if (pnr.getCodigopnr().equals(last)) continue;
				last=pnr.getCodigopnr();
				try {
					//pnr.execSaveCotizacion();
					//if (pnr.getTarifaUsa()==0)
					pnr.execProcessDelete();;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (i<size) break;
//			paginado+=size;
		}	*/
		JRecords<BizPNRSegmentoAereo> recs1 = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		recs1.addFilter("company", getCompany());
		recs1.delete();

		JRecords<BizBooking> recs11 = new JRecords<BizBooking>(BizBooking.class);
		recs11.addFilter("company", getCompany());
		recs11.delete();
	
		JRecords<BizPNRFare> recs12 = new JRecords<BizPNRFare>(BizPNRFare.class);
		recs12.addFilter("company", getCompany());
		recs12.delete();
	
		JRecords<BizPNRFilename> recs13 = new JRecords<BizPNRFilename>(BizPNRFilename.class);
		recs13.addFilter("company", getCompany());
		recs13.delete();
	
		JRecords<BizPNRTicket> recs14 = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		recs14.addFilter("company", getCompany());
		recs14.delete();
	
		JRecords<BizSqlEvent> recs2 = new JRecords<BizSqlEvent>(BizSqlEvent.class);
		recs2.addFilter("company", getCompany());
		recs2.processDeleteAll();
	
		JRecords<BizContrato> recs3= new JRecords<BizContrato>(BizContrato.class);
		recs3.addFilter("company", getCompany());
		recs3.processDeleteAll();


}
	
	public void execCopiarPermisos() throws Exception {
		JExec oExec = new JExec(this, "processPopulate") {

			@Override
			public void Do() throws Exception {
				copiarPermisos();
			}
		};
		oExec.execute();
	}
	
	
	public void copiarPermisos() throws Exception {
		JRecords<BizBSPCompany> recs = new JRecords<BizBSPCompany>(BizBSPCompany.class);
		JIterator<BizBSPCompany> it=recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizBSPCompany cmp=it.nextElement();
			if (cmp.getCompany().equals("DEFAULT"))
				continue;
			cmp.createRoles(this);
		}
		
		JRecords<BizUsuario> users = new JRecords<BizUsuario>(BizUsuario.class);
		JIterator<BizUsuario> itu=users.getStaticIterator();
		while (itu.hasMoreElements()) {
			BizUsuario usr=itu.nextElement();
			if (usr.GetUsuario().equals("ADMIN"))
				continue;
			BizUsuarioRol ur = new BizUsuarioRol();
			ur.dontThrowException(true);
			ur.addFilter("company", usr.getCompany());
			ur.addFilter("usuario", usr.GetUsuario());
			if (ur.read()) continue;
			ur.setCompany(usr.getCompany());
			ur.setRol(3);
			ur.SetUsuario(usr.GetUsuario());
			ur.processInsert();
		}
		
	}
	private void createRoles(BizBSPCompany original) throws Exception {
		if (original.getObjSegConfig()==null) return;
		deleteSegConfig();
		deleteRoles();
		BizSegConfiguracion rsc=original.getObjSegConfig();
		rsc.setCompany(getCompany());
		rsc.insert();
		
		JRecords<BizRol> r1=original.getObjSegConfig().getRolesJerarquicos();
		while (r1.nextRecord()) {
			BizRol r=r1.getRecord();
			r.setCompany(getCompany());
			r.insert();
		}

		JRecords<BizRol> r2=original.getObjSegConfig().getRolesAplicacion();
		while (r2.nextRecord()) {
			BizRol r=r2.getRecord();
			r.setCompany(getCompany());
			r.insert();
		}

		JRecords<BizRol> r3=original.getObjSegConfig().getRolesJerarquicos();
		while (r3.nextRecord()) {
			BizRol r=r3.getRecord();
			JRecords<BizRolVinculado> r4=r.getRolesVinculados1();
			while (r4.nextRecord()) {
				BizRolVinculado v = r4.getRecord();
				v.setCompany(getCompany());
				v.insert();
			}
		}

		JRecords<BizOperacion> r5=original.getObjSegConfig().getOperaciones();
		while (r5.nextRecord()) {
			BizOperacion r=r5.getRecord();
			r.setCompany(getCompany());
			r.insert();
		}

		JRecords<BizRol> r6=original.getObjSegConfig().getRolesAplicacion();
		while (r6.nextRecord()) {
			BizRol r=r6.getRecord();
			JRecords<BizOperacionRol> r7=r.getOperacionRoles();
			while (r7.nextRecord()) {
				BizOperacionRol o = r7.getRecord();
				o.setCompany(getCompany());
				o.insert();
			}
		}
	}
	
	Boolean isConcetrador=null;
	public boolean isConcentrador() throws Exception {
		if (isConcetrador!=null) return isConcetrador;
		BizBSPChildCompany child = new BizBSPChildCompany();
		child.addFilter("company", getCompany());
		return isConcetrador=child.exists();
	}
	
	public boolean isDependant() throws Exception {
		return pDependant.getValue();
	}
	public boolean isCNS() throws Exception {
		return pCNS.getValue();
	}

	public String getCodigoCliente() throws Exception {
		return pCodigoCliente.getValue();
	}

	public String getTicketCompany() throws Exception {
		if (isDependant()) 
			return "'"+getParentCompany()+"'";
		else
			return getCustomListCompany();
	}
	
	public String getCustomListCompany() throws Exception {
		if (!isConcentrador()) return "'"+getCompany()+"'";
		String out="";
		JRecords<BizBSPChildCompany> recs = new JRecords<BizBSPChildCompany>(BizBSPChildCompany.class);
		recs.addFilter("company", getCompany());
		JIterator<BizBSPChildCompany> it =recs.getStaticIterator();
		while (it.hasMoreElements()) {
			out += (out.equals("")?"":",") + "'"+it.nextElement().getChildCompany()+"'";
		}
		return out;
	}
	
}
