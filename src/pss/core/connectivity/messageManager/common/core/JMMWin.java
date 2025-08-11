package pss.core.connectivity.messageManager.common.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import pss.common.version.IEmpaquetable;
import pss.common.version.IVersionGenerator;
import pss.common.version.IVersionable;
import pss.common.version.JPacketable;
import pss.common.version.generator.BizVersionGenerators;
import pss.common.version.pack.detail.BizPackageDetail;
import pss.core.connectivity.messageManager.client.connection.IModuleClient;
import pss.core.connectivity.messageManager.client.connection.MsgConnectionInterface;
import pss.core.connectivity.messageManager.common.core.loader.IConfigurationLoader;
import pss.core.connectivity.messageManager.common.message.EvtError;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MsgResponse;
import pss.core.connectivity.messageManager.common.message.MsgResponse.TypeResponseCode;
import pss.core.connectivity.messageManager.common.message.events.SysEvtListenerOnConnect;
import pss.core.connectivity.messageManager.common.message.events.SysEvtListenerOnDisconnect;
import pss.core.connectivity.messageManager.common.message.events.SysEvtOnConnect;
import pss.core.connectivity.messageManager.common.message.events.SysEvtOnDisconnect;
import pss.core.connectivity.messageManager.common.server.confMngr.requests.subscribeConfigUnit.ReqSubscribeConfigUnit;
import pss.core.connectivity.messageManager.common.server.confMngr.responses.RespListenerSubscribeConfigUnit;
import pss.core.connectivity.messageManager.server.confMngr.IConfigurationServer;
import pss.core.connectivity.messageManager.server.confMngr.IMMConfigurationSubscriptor;
import pss.core.connectivity.messageManager.server.confMngr.IModuleLoadeableConfMgr;
import pss.core.tools.PssLogger;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;
import pss.www.platform.actions.requestBundle.JWebActionData;

public class JMMWin extends JWin implements IModuleClient, IModuleLoadeableConfMgr, IMMWinDropped, IMMConfigurationSubscriptor, IEmpaquetable {
	Map<IMMConfigurationSubscriptor, JMMWin> childsWins = null;
	IConfigurationLoader configLoader;
	
	@Override
	public void setConfiguration(IConfigurationLoader config) {
		configLoader = config;
		
	}
	protected Map<IMMConfigurationSubscriptor, JMMWin> getChildsWins() throws Exception {
		if (childsWins != null) return childsWins;
		HashMap<IMMConfigurationSubscriptor, JMMWin> c = new HashMap<IMMConfigurationSubscriptor, JMMWin>();
		return childsWins = c;
	}

	public JMMWin getChildWin(IMMConfigurationSubscriptor key) throws Exception {
		JMMWin mmwin = getChildsWins().get(key);
		if (mmwin != null) return mmwin;
		mmwin = key.getNewMMGui();
		mmwin.SetVision(GetVision());
		mmwin.getMMBaseDato().fillKeyParent(getMMBaseDato());
		if (mmwin.isFullIntegred(this)) getMMBaseDato().addChildsRecords(key, mmwin.getMMBaseDato());
		getChildsWins().put(key, mmwin);
		return mmwin;
	}

	public boolean isFullIntegred(JMMWin parent) throws Exception {
		return false;
	}

	public boolean fillExtendedPanel(JMMBaseForm form) throws Exception {
		boolean used = false;
		for (IMMConfigurationSubscriptor u : getMMBaseDato().getChilds().values()) {
			JMMWin w =u.getNewMMGui();
			w.SetVision(GetVision());
			used |= w.fillExtendedPanel(this, form);
		}
		return used;
	}

	public Class<? extends JBaseForm> getFormPanel() throws Exception {
		return getFormBase();
	}

	public boolean fillExtendedPanel(JMMWin parent, JMMBaseForm form) throws Exception {
		return false;
	}

	public JMMRecord getMMBaseDato() throws Exception {
		return (JMMRecord) super.GetBaseDato();
	}

	@Override
	public void createActionMap() throws Exception {
		super.createActionMap();
		loadChildrenActionMap();
	}

	public void loadChildrenActionMap() throws Exception {
		for (IMMConfigurationSubscriptor u : getMMBaseDato().getChilds().values()) {
			PssLogger.logDebug("Adding [" + u.getNewMMGui().getModuleName() + "] children action map for module [" + this.getModuleName() + "]");
			JMMWin win = u.getNewMMGui();
			win.SetVision(GetVision());
			win.createActionMap(this);
		}
	}

	public enum OkActionResult {
		UNUSED, SHOW, HIDE
	};

	@Override
	public boolean OkAction(BizAction zAct) throws Exception {

		for (IMMConfigurationSubscriptor u : getMMBaseDato().getChilds().values()) {
			JMMWin w =u.getNewMMGui();
			w.SetVision(GetVision());
			OkActionResult result = w.OkAction(this, zAct);
			if (result != OkActionResult.UNUSED) return result == OkActionResult.SHOW;
		}

		return super.OkAction(zAct);
	}

	public OkActionResult OkAction(JMMWin parent, BizAction zAct) throws Exception {
		return OkActionResult.UNUSED;
	}

	@Override
	public IModuleClient getNewInstance() throws Exception {
		return this;
	}

	// @Override
	// public boolean canConvertToURL() throws Exception {
	// return false;
	// }
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		for (IMMConfigurationSubscriptor u : getMMBaseDato().getChilds().values()) {
			JMMWin w =u.getNewMMGui();
			w.SetVision(GetVision());
			JAct act = w.getSubmitFor(this, a);
			
			if (act != null) return act;
		}
		return super.getSubmit(a);
	}

	public void createActionMap(JMMWin parent) throws Exception {
	}

	public String getConfigurationName() throws Exception {
		return getMMBaseDato().getConfigurationName();
	}



	public JMMWin getNewInstance(JMMWin parent) throws Exception {
		JMMWin mmwin = getClass().newInstance();
		mmwin.getMMBaseDato().fillKeyParent(parent.getMMBaseDato());
		mmwin.getMMBaseDato().keysToFilters();
		mmwin.getRecord().dontThrowException(true);
		mmwin.getRecord().read();
		mmwin.SetVision(GetVision());
		return mmwin;
	}

	protected MsgConnectionInterface client;

	@Override
	public boolean ifOpenDBConnection() {
	  return false;
	}
	
	@Override
	public boolean initializeModuleClient(MsgConnectionInterface zClient) throws Exception {
		Queue<String> col = new LinkedList<String>();
		subscribeClassList(col);
		if (col.isEmpty()) return false;

		client = zClient;
		client.setOpenDatabaseConnection(this.ifOpenDBConnection());
		client.subscribeToEvent(new SysEvtListenerOnConnect(new SysEvtOnConnect().setModuleName(this.getClass().getSimpleName()), this, "onConnect"));
		client.subscribeToEvent(new SysEvtListenerOnDisconnect(new SysEvtOnDisconnect().setModuleName(this.getClass().getSimpleName()), this, "onDisconnect"));
		client.subscribeToAnswer(new RespListenerSubscribeConfigUnit(this, "onResponseConfigUnit", "onResponseConfigUnit"));
		return true;
	}

	@Override
	public String getConnectionString() {
		return "local";
	}

	public IMessageContent onResponseConfigUnit(IMessageContent zMsg) throws Exception {
		if (zMsg instanceof EvtError) {
			((EvtError)zMsg).dumpMessage();
			PssLogger.logError("Reintentando envio de ConfigUnit");
			this.sendConfigUnitReq();
			return null;
		}
		
		MsgResponse resp = (MsgResponse) zMsg;
		if (resp.getResponseCode() == TypeResponseCode.ERROR_RETRY) {
			PssLogger.logError("ERROR:" + resp.getResponseDescription() + " reintentando envio de ConfigUnit");
			this.sendConfigUnitReq();
			return null;
		}
		
		if (resp.getResponseCode() == TypeResponseCode.ERROR) {
			PssLogger.logError("ERROR:" + resp.getResponseDescription() + ", no se reintenta");
			return null;
		}
	
		PssLogger.logDebug("Desconectadome porque ya me anote como Configuration Unit");
		client.disconnect();
		return null;
	}

	public IConfigurationServer getConfigurationServer() throws Exception {
		return (IConfigurationServer) getMMBaseDato();
	}

	protected void sendConfigUnitReq () throws Exception {
		Queue<String> col = new LinkedList<String>();
		subscribeClassList(col);

		ReqSubscribeConfigUnit req = new ReqSubscribeConfigUnit();
		req.setConfigurationUnitName(getConfigurationServer().getConfigurationName());
		req.setSubscriberClassName(this.getClass().getName());
		req.setSubscriptionsList(col);

		client.sendMessage(req);
	}
	
	public IMessageContent onConnect(IMessageContent zMsg) throws Exception {

		this.sendConfigUnitReq();
		return null;
	}

	public void subscribeClassList(Collection<String> col) {

	}

	public IMessageContent onDisconnect(IMessageContent zMsg) throws Exception {
		return null;
	}

	@Override
	public JMMBaseForm getNewMMForm() throws Exception {
		return (JMMBaseForm) getFormBase().newInstance();
	}

	@Override
	public JMMWin getNewMMGui() throws Exception {
		JMMWin w = (JMMWin) Class.forName(this.getClass().getName()).newInstance();
		w.SetVision(GetVision());
		return w;
	}
	@Override
	public JMMRecord getNewMMRecord() throws Exception {
		return getNewMMGui().getMMBaseDato();
	}

	@Override
	public String getModuleName() throws Exception {
		return this.getClass().getSimpleName();
	}

	public JAct getSubmitFor(JMMWin parent, BizAction a) throws Exception {
		return null;
	}

	public void controlsToBD(JWebActionData data) throws Exception {
		for (IMMConfigurationSubscriptor u : getMMBaseDato().getChilds().values()) {
			getChildWin(u).controlsToBD(data);
		}
		super.controlsToBD(data);
	}
	
	protected List<IVersionable> getChildVersion()  throws Exception {
		return null;
	}
	
	public void configurarPackFor( Map<String,JPacketable> list) throws Exception {
		if (this.GetBaseDato() instanceof IVersionable) addPack(list, (IVersionable) this.GetBaseDato(),((IVersionable) this.GetBaseDato()).getEmpaquetable(), getChildVersion());
	}
		
	public void configurarPack( Map<String,JPacketable> list) throws Exception {
		configurarPackFor(list);
		for (IMMConfigurationSubscriptor u : getMMBaseDato().getChilds().values()) {
			JMMWin win = getChildWin(u);
			win.configurarPack(this, list);
		}
	}
	
	// para que los miembros agregados se autoagreguen
	
	public void configurarPackSelf(JMMWin parent, Map<String,JPacketable> listparent) throws Exception {
		
	}
	
	public void configurarPack(JMMWin parent, Map<String,JPacketable> list) throws Exception {
		configurarPackSelf(parent,list);
		for (IMMConfigurationSubscriptor u : getMMBaseDato().getChilds().values()) {
			JMMWin win = getChildWin(u);
			win.configurarPack(parent, list);
		}
	}
	public void addPack(Map<String,JPacketable> list, int act) throws Exception {
		addPack(list,act,null);
	}
	public void addPack(Map<String,JPacketable> list, int act, List<IVersionable> childVers) throws Exception {
		JBaseWin pack = this.findAction(act).getObjSubmit().getWinsResult();
		if (pack.GetBaseDato() instanceof IVersionable) addPack(list,(IVersionable)pack.GetBaseDato(),((IVersionable) pack).getEmpaquetable(),childVers);
		else if (pack instanceof JMMWins) addPack(list, (JMMWins) pack);
	}
	
	public void addPack(Map<String,JPacketable> list, JMMWin version, int act, List<IVersionable> childVers) throws Exception {
		JBaseWin pack = this.findAction(act).getObjSubmit().getWinsResult();
		if (version !=null && version instanceof IVersionable && pack.GetBaseDato() instanceof IEmpaquetable) addPack(list,(IVersionable)version,((IVersionable) pack).getEmpaquetable(),childVers);
		else if (pack.GetBaseDato() instanceof IVersionable) addPack(list,(IVersionable)pack.GetBaseDato(),((IVersionable) pack).getEmpaquetable(),childVers);
		else if (pack instanceof JMMWins) addPack(list,  version, (JMMWins) pack);
	}
	
	public void addPack(Map<String,JPacketable> list, JMMWins pack) throws Exception {
		pack.ReRead();
		while (pack.nextRecord()) {
			JWin o = pack.getRecord();
			if (!(o instanceof JMMWin)) continue;
			JMMWin w = (JMMWin) o;
			w.configurarPack(list);
		}
	}
	public void addPack(Map<String,JPacketable> list,JMMWin version, JMMWins pack) throws Exception {
		pack.ReRead();
		while (pack.nextRecord()) {
			JWin o = pack.getRecord();
			if (!(o instanceof JMMWin)) continue;
			JMMWin w = (JMMWin) o;
			w.configurarPack(version,list);
		}
	}	
	
	public void addPack(Map<String,JPacketable> list, IVersionable pack) throws Exception {
		addPack(list, (IVersionable) pack, pack.getEmpaquetable(), null );
	}	
	
	public void addPack(Map<String,JPacketable> list, IVersionable version, IEmpaquetable pack, List<IVersionable> childList) throws Exception {
		if (list.get(pack.uniqueId())!=null) return;
		list.put(pack.uniqueId(),new JPacketable(version,pack,childList));
	}
	
	@Override
	public void pack(IVersionGenerator idVersion, List<BizPackageDetail> list, Map<String, IVersionGenerator> versionGenerators, boolean full, boolean deleted) throws Exception {


		Map<String,JPacketable> listChildPack = new HashMap<String,JPacketable>();
		configurarPack(this, listChildPack);
		for (JPacketable p : listChildPack.values()) {
			if (p.version != null) {
				IVersionGenerator versObj = p.version.getVersionGenerator();

				long valueObjVers = versObj.getIdVersionGenerator(idVersion);
				if (full || p.version.getIdVersion() == -1 || 
						(p.version.getIdVersion() >= valueObjVers || isAnyChildChange(idVersion,p))) {
					
						p.pack.pack(versObj, list, versionGenerators, full, p.version.getDeleted());
						versionGenerators.put(versObj.getKeyVersion(),versObj);
						if (p.version.getIdVersion() != -1)
							BizVersionGenerators.setIDVersion(idVersion, versObj, p.version.getIdVersion() );
						if (p.version.getDeleted()) {
							p.version.deleteAfterPack();
						}
				}
			}
			else {
				p.pack.pack(idVersion, list, versionGenerators, full,  false);
				versionGenerators.put(idVersion.getKeyVersion(),idVersion);
			}
		}
	}


	public void deleteAfterPack() throws Exception {
  }
	
	boolean isAnyChildChange(IVersionGenerator idVersion,JPacketable p) throws Exception {
		if (p.childVersions==null) return false;
		for (IVersionable v :p.childVersions) {
			long valueObjVers = v.getVersionGenerator().getIdVersionGenerator(idVersion);
			if (v.getIdVersion() >= valueObjVers) return true;
		}
		return false;
	}
	
	@Override
	public String uniqueId() throws Exception {
		return this.getClass().getCanonicalName()+"||"+this.GetBaseDato().toString();
	}

	@Override
	public void unpack(String xmlContent, boolean isDelete) throws Exception {
		return;
	}

	@Override
	public void unpackTruncate() throws Exception {
		return;
	}
}
