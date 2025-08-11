package pss.common.event;

import pss.common.event.device.GuiChannels;
import pss.common.event.device.GuiDevices;
import pss.common.event.device.GuiTypeDevices;
import pss.common.event.mail.GuiMailCasillas;
import pss.common.event.mail.GuiMailConfs;
import pss.common.event.manager.GuiEvents;
import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;

public class GuiModuloEvent extends GuiModulo {

	public GuiModuloEvent() throws Exception {
		super();
		SetModuleName("Eventos");
		SetNroIcono(5072);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return this.addAction(1, "Eventos", null, 5072, true, true);
	}

	@Override
	public void createActionMap() throws Exception {
		addAction(10, "Histórico", null, 5074, true, false, true, "Group");
		this.addAction(20, "Servidores de Mail", null, 63, true, false);
		this.addAction(30, "Casillas de Mails", null, 63, true, false);
		addAction(40, "Canales", null, 10006, true, false, true, "Group");
		addAction(50, "Dispositivos", null, 900, true, false, true, "Group");
		addAction(60, "Tipos de Dispositivos", null, 900, true, false, true, "Group");
		this.loadDynamicActions(null);

	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActWins(getEvents());
		if (a.getId() == 20)
			return new JActWins(new GuiMailConfs());
		if (a.getId() == 30)
			return new JActWins(this.getMailSenders());
		if (a.getId() == 40)
			return new JActWins(this.getChannels());
		if (a.getId() == 50)
			return new JActWins(this.getDevices());
		if (a.getId() == 60)
			return new JActWins(this.getTypeDevices());

	
		return null;
	}

	private JBaseWin getEvents() throws Exception {
		GuiEvents t = new GuiEvents();
		t.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		t.getRecords().addOrderBy("event_datetime", "desc");
		return t;
	}
	
	public JWins getMailSenders() throws Exception {
		GuiMailCasillas nodos = new GuiMailCasillas();
		nodos.getRecords().addFilter("company",	BizUsuario.getUsr().getCompany());
		return nodos;
	}
	public GuiChannels getChannels() throws Exception {
		GuiChannels channels = new GuiChannels();
		if (!BizUsuario.getUsr().isAdminUser())
			channels.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		channels.getRecords().addOrderBy("usuario", "asc");
		channels.getRecords().addOrderBy("id_channel", "asc");
		return channels;
	}
	public GuiDevices getDevices() throws Exception {
		GuiDevices devices = new GuiDevices();
		if (!BizUsuario.getUsr().isAdminUser())
			devices.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		devices.getRecords().addOrderBy("id_device", "asc");
		return devices;
	}
	public GuiTypeDevices getTypeDevices() throws Exception {
		GuiTypeDevices devices = new GuiTypeDevices();
		if (!BizUsuario.getUsr().isAdminUser())
			devices.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		devices.getRecords().addOrderBy("description", "asc");
		return devices;
	}
}
