package pss.core.connectivity.messageManager.common.core;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pss.common.version.IEmpaquetable;
import pss.common.version.IStoreAndForwardeable;
import pss.common.version.IVersionGenerator;
import pss.common.version.IVersionable;
import pss.common.version.client.JVersionClient;
import pss.common.version.pack.detail.BizPackageDetail;
import pss.core.connectivity.messageManager.client.connection.MsgConnectionInterface;
import pss.core.connectivity.messageManager.common.message.IMessageComponent;
import pss.core.connectivity.messageManager.server.confMngr.ConfigurationManager;
import pss.core.connectivity.messageManager.server.confMngr.ConfigurationUnit;
import pss.core.connectivity.messageManager.server.confMngr.IConfigurationServer;
import pss.core.connectivity.messageManager.server.confMngr.IMMConfigurationSubscriptor;
import pss.core.services.JExec;
import pss.core.services.fields.JObject;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JMap;

public class JMMRecord extends JRecord implements IConfigurationServer, IEmpaquetable, IStoreAndForwardeable {

	Map<String, IMMConfigurationSubscriptor> childs = null;
	Map<IMMConfigurationSubscriptor, JMMRecord> childsRecords = null;

	protected Map<IMMConfigurationSubscriptor, JMMRecord> getChildsRecords() throws Exception {
		if (childsRecords != null) return childsRecords;
		HashMap<IMMConfigurationSubscriptor, JMMRecord> c = new HashMap<IMMConfigurationSubscriptor, JMMRecord>();

		return childsRecords = c;
	}
	
	public void addChildsRecords(IMMConfigurationSubscriptor key , JMMRecord r) throws Exception {
		if (getChildsRecords().get(key)!=null)
			getChildsRecords().remove(key);
		getChildsRecords().put(key,r);
	}

	public JMMRecord getChildsRecord(IMMConfigurationSubscriptor key) throws Exception {
		JMMRecord rec = getChildsRecords().get(key);
		if (rec!=null) return rec;
		rec =key.getNewMMRecord();
		rec.fillKeyParent(this);
		rec.read();
		getChildsRecords().put(key,rec);
		return rec;
	}
	
	protected Map<String, IMMConfigurationSubscriptor> getChilds() throws Exception {
		if (childs != null) return childs;
		HashMap<String, IMMConfigurationSubscriptor> c = new HashMap<String, IMMConfigurationSubscriptor>();
		Collection<ConfigurationUnit> coll = ConfigurationManager.getConfigurationManagerInstance().getConfUnitsSubscribedToMe(getConfigurationName());
		if (coll != null) for (ConfigurationUnit u : coll) {
			c.put(u.getConfigUnitClass().getClass().getName(), u.getConfigUnitClass());
		}
		if (c.isEmpty()) return c;
		return childs = c;
	}
	
	public void execProcessInsertFromMsg(final IMessageComponent msg) throws Exception {
 		JExec oExec = new JExec(this, "processInsert") {
	
			@Override
			public void Do() throws Exception {
				processInsertFromMsg(msg);
			}
		};
		oExec.execute();
	}
	
	protected void processInsertFromMsg(JMMRecord parent,IMessageComponent msg) throws Exception {
		processInsertFromMsg(msg);
	}

	public void processInsertFromMsg(IMessageComponent msg) throws Exception {
		for (IMMConfigurationSubscriptor ics : getChilds().values()) {
			ics.getNewMMRecord().processInsertFromMsg(this,msg);
		}
		processInsert();
	}

	public JMMRecord() throws Exception {
		super();
	}
	public void processInsert(JMMRecord parent) throws Exception {
		processInsert();
	}
	public void processUpdateOrInsertWithCheck(JMMRecord parent) throws Exception {
		processUpdateOrInsertWithCheck();
	}


	@Override
	public void processInsert() throws Exception {
		processInsertChild();
		processInsertVersion();
		super.processInsert();
	}
	
	public void processInsertChild() throws Exception {
		for (JMMRecord rec : getChildsRecords().values()) {
			rec.fillKeyParent(this);
			rec.processInsert(this);
		}
	}

	public void processInsertVersion() throws Exception {
		if (this instanceof IVersionable) {
			IVersionable vs = (IVersionable) this;
			vs.setDeleted(false);
			vs.setIdVersion(vs.getVersionGenerator()==null?-1:vs.getVersionGenerator().getIdVersionGenerator());
		}
		
		if (this instanceof IStoreAndForwardeable && JVersionClient.isClientSide()) {
			IStoreAndForwardeable sf = (IStoreAndForwardeable) this;
			if (sf.hasStoreAndForward()) sf.storeAndForward(); 
		}	
	}
	
	
	@Override
	public void processUpdate() throws Exception {
		processUpdateChild();
		processUpdateVersion();
		super.processUpdate();
	}
	
	public void processUpdateChild() throws Exception {
		for (JMMRecord rec : getChildsRecords().values()) {
			rec.processUpdateOrInsertWithCheck(this);
		}
	}
	public void processUpdateVersion() throws Exception {
		if (this instanceof IVersionable) {
			IVersionable vs = (IVersionable) this;
			vs.setDeleted(false);
			vs.setIdVersion(vs.getVersionGenerator()==null?-1:vs.getVersionGenerator().getIdVersionGenerator());
		}
		if (this instanceof IStoreAndForwardeable && JVersionClient.isClientSide()) {
			IStoreAndForwardeable sf = (IStoreAndForwardeable) this;
			if (sf.hasStoreAndForward()) sf.storeAndForward(); 
		}	
	}
	
	public void fillKeyParent(JMMRecord parent) throws Exception {
		
	}

	public JMap<String, JObject<?>> getProperties() throws Exception {
		if (hProperties != null) return hProperties;
		this.createProperties();
		return hProperties;
	}

	@Override
	public JProperty getFixedProp(String zCampo) throws Exception {
		if (!this.getFixedProperties().containsKey(zCampo.toLowerCase())) return getFixedPropChild(zCampo);
		return getFixedProperties().getElement(zCampo.toLowerCase());
	}

	public JProperty getFixedPropChild(String zCampo) throws Exception {
		for (JMMRecord rec : getChildsRecords().values()) {
			try {
				return rec.getFixedProp(zCampo);
			} catch (Exception e) {
			}
		}
		JExcepcion.SendError("Campo inexistente: ^" + zCampo + " - " + this.getClass().getName());
		return null;
	}

	@Override
	public JObject<?> getProp(String zCampo) throws Exception {
		JMap<String, JObject<?>> props = this.getProperties();
		if (props == null) return getPropChild(zCampo);
		Object prop = props.getElement(zCampo.toLowerCase());
		return (JObject<?>) prop;
	}

	public void doChild(String method, Object ... args) throws Exception {
		for (JMMRecord rec : getChildsRecords().values()) {
			try {
				int i=0;
				Class<?>[] params = new Class<?>[args.length];
				for(Object o:args){
					params[i++]=o.getClass();
				}
				Method m = rec.getClass().getMethod(method, params);
				if (m!=null) m.invoke(rec, args);
			} catch (Exception e) {
			}
		}
	}

	
	public JObject<?> getPropChild(String zCampo) throws Exception {
		for (JMMRecord rec : getChildsRecords().values()) {
			try {
				return rec.getProp(zCampo);
			} catch (Exception e) {
			}
		}
		return null;
	}

	@Override
	public String getConfigurationName() {
		return getClass().getSimpleName();
	}

	public void processCascadeDelete(JMMRecord parent) throws Exception {
		fillKeyParent(parent);
		this.keysToFilters();
		dontThrowException(true);
		if (read()) processDelete();
	}
	
	public void processDelete(JMMRecord parent) throws Exception {
		processDelete();
	}
	
	@Override
	public void processDelete() throws Exception {
		processDeleteChild();
		if (processDeleteVersion()) return;

		super.processDelete();
	}
	
	public void processDeleteChild() throws Exception {
		for (IMMConfigurationSubscriptor ics : getChilds().values()) {
			ics.getNewMMRecord().processCascadeDelete(this);
		}
		for (JMMRecord rec : getChildsRecords().values()) {
			rec.processDelete(this);
		}
	}

	public boolean processDeleteVersion() throws Exception {
		if (!(this instanceof IVersionable)) return false;
		IVersionable vs=(IVersionable) this;
		vs.setDeleted(true);
		vs.setIdVersion(vs.getVersionGenerator()==null ? -1 : vs.getVersionGenerator().getIdVersionGenerator());
		super.processUpdate();
		return true;
	}
	
	public void processRealDelete(JMMRecord parent) throws Exception {
		processRealDelete();
	}
	public void processRealDelete() throws Exception {
		for (IMMConfigurationSubscriptor ics : getChilds().values()) {
			ics.getNewMMRecord().processCascadeDelete(this);
		}
		for (JMMRecord rec : getChildsRecords().values()) {
			rec.processRealDelete(this);
		}

		super.processDelete();
	}
	protected MsgConnectionInterface client;

	public void pack(IVersionGenerator idVersion, List<BizPackageDetail> list, Map<String, IVersionGenerator> versionGenerators, boolean full, boolean deleted ) throws Exception {
		if (deleted && full) return; // no mando borrados si  la version es full
		BizPackageDetail det = new BizPackageDetail();
		PssLogger.logDebug("Generator: "+idVersion.getKeyVersion()+"("+idVersion.getIdVersionGenerator()+") adding to pack: "+this.toString());
		det.setKeyPack(this.getClass().getCanonicalName());
		det.setDelete(deleted);
		det.setPackage(this.serialize());
		versionGenerators.put(idVersion.getKeyVersion(),idVersion);
		list.add(det);

	}

	public void deleteAfterPack() throws Exception {
		this.processRealDelete();
  }
	
	
	@Override
	public String uniqueId() {
		return this.toString();
	}

	@Override
	public void unpack(String xmlContent,boolean isDelete) throws Exception {
		unSerialize(xmlContent);
		if (isDelete) processDelete();
		else processUpdateOrInsertWithCheck();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void unpackTruncate() throws Exception {
		JRecords<JRecord> tabla = new JRecords<JRecord>(getBasedClass());
		tabla.truncate();
	}

	@Override
	public void beginStoreAndForward() throws Exception {
		JVersionClient.processStoreAndForwardBegin();
	}

	@Override
	public void endStoreAndForward() throws Exception {
		JVersionClient.processStoreAndForwardEnd();
	}

	@Override
	public String getKeyMessage() throws Exception {
		return getClass().getCanonicalName();
	}

	@Override
	public boolean hasStoreAndForward() throws Exception {
		return false;
	}

	@Override
	public void storeAndForward() throws Exception {
		JVersionClient.storeAndForward(this);
	}
	
	public void doBeginTransaction() throws Exception {
		if (!JVersionClient.isClientSide()) return;
		if (this instanceof IStoreAndForwardeable && ((IStoreAndForwardeable)this).hasStoreAndForward()) ((IStoreAndForwardeable)this).beginStoreAndForward();
	}
	
	public void doCommitTransaction() throws Exception {
		if (!JVersionClient.isClientSide()) return;
		if (this instanceof IStoreAndForwardeable && ((IStoreAndForwardeable)this).hasStoreAndForward()) ((IStoreAndForwardeable)this).endStoreAndForward();
	}
	
	public void doRollBackTransaction() throws Exception {
		if (!JVersionClient.isClientSide()) return;
		if (this instanceof IStoreAndForwardeable && ((IStoreAndForwardeable)this).hasStoreAndForward()) ((IStoreAndForwardeable)this).endStoreAndForward();
	}
	
	public void fillFromRecord(JRecord origen ) throws Exception {
		Iterator<JProperty> oEnum = origen.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();

			JObject<?> oPropOrig = origen.getProp(oProp.GetCampo());
			JObject<?> oPropDest = getProp(oProp.GetCampo());
			if (oPropDest==null) continue;
			oPropDest.setValue(oPropOrig.asRawObject());
		}
	}


}
