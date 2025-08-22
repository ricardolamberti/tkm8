package pss.bsp.consola.config;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.GuiBSPCompany;
import pss.bsp.bspBusiness.GuiBSPUsuarios;
import pss.bsp.clase.GuiClases;
import pss.bsp.config.airportGroups.GuiAirportGroups;
import pss.bsp.config.carrierGroups.GuiCarrierGroups;
import pss.bsp.consolid.model.clientesView.GuiClienteViews;
import pss.bsp.consolid.model.gruposDK.GuiGrupoDKs;
import pss.bsp.familia.GuiFamilias;
import pss.bsp.hoteles.GuiHotels;
import pss.bsp.market.GuiMarkets;
import pss.bsp.organization.GuiOrganizations;
import pss.bsp.regions.GuiRegiones;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.GuiSqlEventActions;
import pss.common.event.mailing.BizMailingPersona;
import pss.common.event.mailing.GuiMailingPersonas;
import pss.common.event.mailing.type.GuiMailingPersonaTypes;
import pss.common.layoutWysiwyg.GuiPlantillas;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.carrier.GuiCarriers;
import pss.tourism.dks.GuiDks;

public class GuiBSPConfig extends JWin {

//	public static final String FUENTEDATOS = "1";
//	public static final String DASHBOARD = "s";


  /**
   * Constructor de la Clase
   */
  public GuiBSPConfig() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBSPConfig(); }
  public int GetNroIcono()   throws Exception { return 10057; }
  public String GetTitle()   throws Exception { return "Configuracion"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception {
  	return FormBSPConfig.class;
  	 
  }
  public String  getKeyField() throws Exception { return "usuario"; }
  public String  getDescripField() { return "usuario"; }
  public BizBSPConfig GetcDato() throws Exception { return (BizBSPConfig) this.getRecord(); }

  public void createActionMap() throws Exception {
		this.createActionQuery();
		this.createActionUpdate();
 		this.addAction(50, "Formatos", null, 10020, false, false);
 		this.addAction(10, "Regiones", null, 10020, false, false);
 		this.addAction(20, "Grupo Clases", null, 10020, false, false);
 		this.addAction(25, "Grupo Fam.Tarif", null, 10020, false, false);
 		this.addAction(26, "Grupo Hoteles", null, 10020, false, false);
 	 	this.addAction(30, "Grupo Aerolíneas", null, 10020, false, false);
 		this.addAction(40, "Grupo Aeropuertos", null, 10020, false, false);
 		this.addAction(60, "Acciones", null, 10020, false, false);
 		this.addAction(70, "Clientes", null, 10020, false, false);
 		this.addAction(71, "Clientes DKs", null, 10020, false, false);
 		this.addAction(75, "Clientes remark", null, 10020, false, false);
 		this.addAction(76, "Reglas DKs", null, 10020, false, false);
 		this.addAction(80, "Vendedor", null, 10020, false, false);
 		this.addAction(90, "Centro de Costo", null, 10020, false, false);
 		this.addAction(100,"Sucursal", null, 10020, false, false);
 		this.addAction(105,"IATAs", null, 10020, false, false);
		this.addAction(107,"Mercados", null, 10020, false, false);
		this.addAction(110,"Refrescar", null, 15013, true, true);
		this.addAction(115,"Usuarios", null, 10020, false, false);
		this.addAction(120,"Copiar hijos", null, 15013, true, true);
		this.addAction(130,"Empresa", null, 15013, true, true);
		this.addAction(200,"Clientes Consolid", null, 15013, true, true);
		this.addAction(210,"Grupos Clientes", null, 15013, true, true);
		this.addAction(220,"DK Agrupados", null, 15013, true, true);
		this.addAction(240,"Aerolineas", null, 15013, true, true);
		this.addAction(230,"Organizacion", null, 15013, true, true);
		  		// 		super.createActionMap();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  		if (a.getId()==10) return BizUsuario.IsAdminCompanyUser();
  		if (a.getId()==20) return BizUsuario.IsAdminCompanyUser();
  		if (a.getId()==25) return BizUsuario.IsAdminCompanyUser();
  		if (a.getId()==30) return BizUsuario.IsAdminCompanyUser();
//  		if (a.getId()==40) return BizUsuario.IsAdminCompanyUser();
  		if (a.getId()==50) return BizUsuario.IsAdminCompanyUser();
  		if (a.getId()==120) return BizUsuario.IsAdminCompanyUser();
  		if (a.getId()==130) return BizUsuario.IsAdminCompanyUser();
  		if (a.getId()==115) return BizUsuario.isSuperCompanyUser();
  		if (a.getId()==200) return BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isCNS();
  		if (a.getId()==220) return BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isCNS();
  		if (a.getId()==240) return BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isCNS();
      	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 50)	return new JActWins(this.getFormatos());
		if (a.getId() == 10)	return new JActWins(this.getRegiones());
		if (a.getId() == 20)	return new JActWins(this.getClases());
		if (a.getId() == 25)	return new JActWins(this.getFamilias());
		if (a.getId() == 26)	return new JActWins(this.getHoteles());
		if (a.getId() == 30)	return new JActWins(this.getCarrierGroups());
		if (a.getId() == 40)	return new JActWins(this.getAeropuertoGroups());
		if (a.getId() == 60)	return new JActWins(this.getAcciones());
		if (a.getId() == 70)	return new JActWins(this.getClientes());
		if (a.getId() == 71)	return new JActWins(this.getClientesIdCorto());
		if (a.getId() == 75)	return new JActWins(this.getClientesRMK());
		if (a.getId() == 76)	return new JActWins(this.getReglasDK());
		if (a.getId() == 80)	return new JActWins(this.getVendedores());
		if (a.getId() == 90)	return new JActWins(this.getCCostos());
		if (a.getId() == 100)	return new JActWins(this.getSucursales());
		if (a.getId() == 105)	return new JActWins(this.getIatas());
		if (a.getId() == 115)	return new JActWins(this.getUsuarios());
		if (a.getId() == 107)	return new JActWins(this.getMercados());
		if (a.getId() == 130)	return new JActQuery(this.getEmpresa());
		if (a.getId() == 200)	return new JActWins(this.getClientesConsolid());
		if (a.getId() == 210)	return new JActWins(this.getGruposClientes());
		if (a.getId() == 220)	return new JActWins(this.getGruposDks());
		if (a.getId() == 230)	return new JActWins(this.getOrganizadores());
		if (a.getId() == 240)	return new JActWins(this.getAerolineas());

  	if (a.getId()==110 ) return new JActSubmit(this) {
  		@Override
  		public void submit() throws Exception {
  			GetcDato().execProcessRefrescar();
  		}
  	};
  	if (a.getId()==120 ) return new JActSubmit(this) {
  		@Override
  		public void submit() throws Exception {
  			GetcDato().execProcessCopiarHijos();
  		}
  	};
		return super.getSubmitFor(a);
	}
	public JWins getAcciones() throws Exception {
		GuiSqlEventActions wins = new GuiSqlEventActions();
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("tipo_periodicidad", BizSqlEventAction.SOLOUNAVEZ, "<>");
		
		return wins;
	}
	public JWins getFormatos() throws Exception {
		GuiPlantillas wins = new GuiPlantillas();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		return wins;
	}
	public JWins getRegiones() throws Exception {
		GuiRegiones wins = new GuiRegiones();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		return wins;
	}
	public JWins getClientesConsolid() throws Exception {
		GuiClienteViews wins = new GuiClienteViews();
		//wins.getRecords().addFilter("status","A");
		
		wins.getRecords().addOrderBy("customer_number");
		return wins;
	}
	public JWins getGruposClientes() throws Exception {
		GuiMailingPersonaTypes wins = new GuiMailingPersonaTypes();
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addOrderBy("descripcion");
		return wins;
	}
	public JWins getGruposDks() throws Exception {
		GuiGrupoDKs wins = new GuiGrupoDKs();
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addOrderBy("descripcion");
		return wins;
	}
	public JWins getOrganizadores() throws Exception {
		GuiOrganizations wins = new GuiOrganizations();
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addOrderBy("descripcion");
		return wins;
	}
	public JWins getAerolineas() throws Exception {
		GuiCarriers wins = new GuiCarriers();
		wins.getRecords().addOrderBy("description");
		return wins;
	}
	public JWins getClientes() throws Exception {
		GuiMailingPersonas wins = new GuiMailingPersonas();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("tipo", BizMailingPersona.CLIENTE);
		wins.getRecords().addOrderBy("codigo");
		return wins;
	}
	public JWins getClientesIdCorto() throws Exception {
		GuiMailingPersonas wins = new GuiMailingPersonas();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("tipo", BizMailingPersona.CLIENTE_REDUCIDO);
		wins.getRecords().addOrderBy("codigo");
		return wins;
	}
	public JWins getClientesRMK() throws Exception {
		GuiMailingPersonas wins = new GuiMailingPersonas();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("tipo", BizMailingPersona.CLIENTERMK);
		wins.getRecords().addOrderBy("codigo");
		return wins;
	}
	public JWins getReglasDK() throws Exception {
		GuiDks wins = new GuiDks();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addOrderBy("dk");
		return wins;
	}
	public JWins getVendedores() throws Exception {
		GuiMailingPersonas wins = new GuiMailingPersonas();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("tipo", BizMailingPersona.VENDEDOR);
		wins.getRecords().addOrderBy("codigo");
		return wins;
	}
	public JWins getSucursales() throws Exception {
		GuiMailingPersonas wins = new GuiMailingPersonas();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("tipo", BizMailingPersona.SUCURSAL);
		wins.getRecords().addOrderBy("codigo");
		return wins;
	}
	public JWins getIatas() throws Exception {
		GuiMailingPersonas wins = new GuiMailingPersonas();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("tipo", BizMailingPersona.IATA);
		wins.getRecords().addOrderBy("codigo");
		return wins;
	}
	public JWins getUsuarios() throws Exception {
		GuiBSPUsuarios wins = new GuiBSPUsuarios();
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addOrderBy("usuario");
		return wins;
	}
	public JWins getMercados() throws Exception {
		GuiMarkets wins = new GuiMarkets();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addOrderBy("descripcion");
		return wins;
	}
	public JWins getCCostos() throws Exception {
		GuiMailingPersonas wins = new GuiMailingPersonas();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("tipo", BizMailingPersona.CCOSTO);
		wins.getRecords().addOrderBy("codigo");
		return wins;
	}
	public JWins getClases() throws Exception {
		GuiClases wins = new GuiClases();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		return wins;
	}
	public JWins getFamilias() throws Exception {
		GuiFamilias wins = new GuiFamilias();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		return wins;
	}
	public JWins getHoteles() throws Exception {
		GuiHotels wins = new GuiHotels();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		return wins;
	}
	public JWins getCarrierGroups() throws Exception {
		GuiCarrierGroups wins = new GuiCarrierGroups();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		return wins;
	}
	public GuiBSPCompany getEmpresa() throws Exception {
		GuiBSPCompany cpny = new GuiBSPCompany();
		cpny.setRecord(BizBSPCompany.getObjBSPCompany(GetcDato().getCompany()));
		return cpny;
	}
	
	public JWins getAeropuertoGroups() throws Exception {
		GuiAirportGroups wins = new GuiAirportGroups();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else wins.getRecords().addFilter("company", GetcDato().getCompany());
		return wins;
	}

	
}
