package pss.bsp;

import pss.bsp.bspBusiness.GuiBSPCompanies;
import pss.bsp.consola.GuiBSPConsola;
import pss.bsp.consola.config.GuiBSPConfig;
import pss.bsp.contrato.conocidos.GuiContratoConocidos;
import pss.bsp.contrato.conocidos2.GuiContratoConocidosV2;
import pss.bsp.monitor.consola.GuiBspMonitorService;
import pss.bsp.persona.GuiPersonaBSPs;
import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActQueryActive;
import pss.core.win.submits.JActWins;
import pss.tourism.airports.GuiAirports;
import pss.tourism.carrier.GuiCarriers;
import pss.tourism.interfaceGDS.log.GuiInterfaceLogs;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiModuloBSP extends GuiModulo {

	public GuiModuloBSP() throws Exception {
		super();
		SetModuleName("BSP");
		SetNroIcono(10055);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return this.addAction(1, "BSP", null, 10055, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(20, "Configuración BSP", null, 10055, true, true).setBackAction(false);
		BizAction a = this.addAction(10, "Consola operacion", null, 10055, true, true);
		a.setBackAction(false);
	
		this.addAction(30, "Aeropuertos", null, 10055, true, true);//.setNuevaSession(true);;
		this.addAction(60, "Aerolineas", null, 10055, true, true);
		this.addAction(50, "Boletos", null, 10055, true, true);
		this.addAction(80, "Contratos conocidos", null, 10055, true, true);
		this.addAction(90, "Clientes", null, 10055, true, true);
		this.addAction(100, "Personas", null, 10055, true, true);
		this.addAction(110, "Monitor Back", null, 10055, true, true);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 20) return new JActWins(this.getBSPConfigurador());
		if (a.getId() == 10) return new JActQuery(getBSPConsolaUsuario());
		if (a.getId() == 30) return new JActWins(this.getAirports());
		if (a.getId() == 60) return new JActWins(new GuiCarriers());
		if (a.getId() == 50) return new JActWins(this.getBoletos());
		if (a.getId() == 80) return new JActWins(this.getContratosConocidosV2());
		if (a.getId() == 90) return new JActWins(this.getClientes());
		if (a.getId() == 100) return new JActWins(this.getPersonas());
		if (a.getId() == 110) return new JActQueryActive(this.getMonitor());
			
		return null;
	}
	public GuiInterfaceLogs getClientes() throws Exception {
		GuiInterfaceLogs c = new GuiInterfaceLogs();
		c.addOrderAdHoc("company", "DESC");
		c.addOrderAdHoc("id", "DESC");
		c.setPreviewFlag(JWins.PREVIEW_NO);
		return c;
	}
	
	public GuiPersonaBSPs getPersonas() throws Exception {
		GuiPersonaBSPs c = new GuiPersonaBSPs();
		if (!BizUsuario.getUsr().IsAdminUser())
			c.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		return c;
	}
	

	public GuiBSPCompanies getBSPConfigurador() throws Exception {
		GuiBSPCompanies c = new GuiBSPCompanies();
		c.getRecords().addOrderBy("description");
		return c;
	}
	public GuiContratoConocidos getContratosConocidos() throws Exception {
		GuiContratoConocidos c = new GuiContratoConocidos();
		c.addOrderAdHoc("pais", "DESC");
		c.addOrderAdHoc("aerolineas", "DESC");
		c.addOrderAdHoc("descripcion", "DESC");
		return c;
	}
	public GuiContratoConocidosV2 getContratosConocidosV2() throws Exception {
		GuiContratoConocidosV2 c = new GuiContratoConocidosV2();
		c.addOrderAdHoc("pais", "DESC");
		c.addOrderAdHoc("aerolineas", "DESC");
		c.addOrderAdHoc("descripcion", "DESC");
		return c;
	}
	
	static public GuiBSPConsola getBSPConsolaUsuario() throws Exception {
		GuiBSPConsola c = new GuiBSPConsola();
		c.setToolbarForced(JBaseWin.TOOLBAR_LEFT);


		GuiBSPConfig cc = new GuiBSPConfig();
		cc.GetcDato().dontThrowException(true);
		if (cc.GetcDato().read(BizUsuario.getUsr().getCompany(),"*")) return c;
		cc.GetcDato().ExecGenerateUser(BizUsuario.getUsr().getCompany(),"*","" );
		if (!cc.GetcDato().read(BizUsuario.getUsr().getCompany(),"*" )) throw new Exception ("usuario no inicializado");
		
	//	c.setConfigView(cc);
		return c;
	}
	
	static public GuiBspMonitorService getMonitor() throws Exception {
		GuiBspMonitorService c = new GuiBspMonitorService();
		c.setToolbarForced(JBaseWin.TOOLBAR_LEFT);
		return c;
	}
	
	private JWins getBoletos() throws Exception {
		GuiPNRTickets tkt = new GuiPNRTickets();
		tkt.SetVision("SINTOT");
		return tkt;
	}

	private JWins getAirports() throws Exception {
		GuiAirports a = new GuiAirports();
//		a.toStatic();
		return a;
	}

}
