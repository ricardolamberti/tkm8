package pss.core.connectivity.messageManager.server.confMngr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Queue;

import pss.core.connectivity.messageManager.client.connection.IModuleClient;
import pss.core.connectivity.messageManager.client.connection.MessageClient;
import pss.core.connectivity.messageManager.client.connection.MsgConnectionInterface;
import pss.core.connectivity.messageManager.common.core.loader.IConfigurationLoader;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MsgResponse;
import pss.core.connectivity.messageManager.common.server.confMngr.requests.subscribeConfigUnit.ReqListenerSubscribeConfigUnit;
import pss.core.connectivity.messageManager.common.server.confMngr.requests.subscribeConfigUnit.ReqSubscribeConfigUnit;
import pss.core.tools.PssLogger;

//public class ConfigurationManager extends MessageClientThread {
public class ConfigurationManager extends MessageClient implements IModuleLoadeableConfMgr {

	private HashMap<String, ConfigurationUnit> configurationUnitMap = null;
	private HashMap<String, ConfigurationActions> configurationActionsMap = null;

	static ConfigurationManager gConfManager = null;
	IConfigurationLoader configLoader;
	
	@Override
	public void setConfiguration(IConfigurationLoader config) {
		configLoader = config;
		
	}

	public static ConfigurationManager getConfigurationManagerInstance() throws Exception {
		if (gConfManager == null) {
			gConfManager = new ConfigurationManager();
		}
		return gConfManager;
	}

	public HashMap<String, ConfigurationUnit> getConfigUnitMap() {
		if (this.configurationUnitMap == null) {
			this.configurationUnitMap = new HashMap<String, ConfigurationUnit>();
		}
		return this.configurationUnitMap;
	}

	public HashMap<String, ConfigurationActions> getConfigActionsMap() {
		if (this.configurationActionsMap == null) {
			this.configurationActionsMap = new HashMap<String, ConfigurationActions>();
		}

		return this.configurationActionsMap;
	}

	public ConfigurationManager() throws Exception {
	}

	// @Override
	public IMessageContent onConnect(IMessageContent zMsg) throws Exception {
		return null;
	}

	// @Override
	public IMessageContent onDisconnect(IMessageContent zMsg) throws Exception {
		return null;
	}

	@Override
	public String getConnectionString() {
		return "local";
	}

	/**
	 * @param zMessage
	 * @return
	 * @throws Exception
	 */
	public IMessageContent processSubscribeConfigUnit(IMessageContent zMessage) throws Exception {
		ReqSubscribeConfigUnit rSCU = (ReqSubscribeConfigUnit) zMessage;
		MsgResponse oMR = new MsgResponse(zMessage);

		/*****************
		 * Validaciones **
		 *****************/
		if (rSCU.getConfigurationUnitName() == null || rSCU.getConfigurationUnitName().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			String errorTxt = "Se esta intentando subscribir una unidad de configuracion que tiene el nombre nulo o vacio.";
			if (rSCU.getSubscriberClassName().isEmpty() == false) {
				errorTxt += " ConfigUnitClass [" + rSCU.getSubscriberClassName() + "]";
			}
			oMR.setResponseDescription(errorTxt);
			return oMR;
		}

		if (rSCU.getSubscriberClassName() == null || rSCU.getSubscriberClassName().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("Se esta intentando subscribir una unidad de configuracion que no tiene configurado la clase configUnitName. ConfigUnitName [" + rSCU.getConfigurationUnitName() + "]");
			return oMR;
		}

		Queue<String> oSubscriptionList = rSCU.getSubscriptionsList();

		if (oSubscriptionList.isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("La unidad de configuracion se debe subscribir al menos a una clase");
			return oMR;
		}

		ConfigurationUnit oConfUnit = new ConfigurationUnit();

		try {
			oConfUnit.setConfigUnitName(rSCU.getConfigurationUnitName());
			oConfUnit.setConfigUnitClass(rSCU.getSubscriberClassName());

			for (String classNames : oSubscriptionList) {
				if (classNames == null || classNames.isEmpty()) {
					oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
					oMR.setResponseDescription("La unidad de configuracion [" + oConfUnit.getConfigUnitName() + "] intenta subscribirse a una clase con nombre nulo o vacio.");
					return oMR;
				}

				oConfUnit.addClassToSubscribe(classNames);
			}
		} catch (Exception e) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription(e.getMessage());
			return oMR;
		}

		if (this.getConfigUnitMap().containsKey(oConfUnit.getConfigUnitName())) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("Esta unidad de configuracion [" + oConfUnit.getConfigUnitName() + "] ya esta subscripta");
			return oMR;
		}

		/******************
		 * Procesamiento **
		 ******************/
		this.getConfigUnitMap().put(oConfUnit.getConfigUnitName(), oConfUnit);
		this.addSubscribeClassName(oConfUnit);

		oMR.setResponseCode(MsgResponse.TypeResponseCode.OK);
		return oMR;
	}

	public void addSubscribeClassName(ConfigurationUnit zConfUnit) throws Exception {
		ConfigurationActions oConfAction;

		for (String className : zConfUnit.getClassesToSubscribeMap()) {
			oConfAction = this.getConfigActionsMap().get(className);

			if (oConfAction == null) {
				oConfAction = new ConfigurationActions(className);
				this.getConfigActionsMap().put(className, oConfAction);
				PssLogger.logDebug("Subscribing ["+zConfUnit.getConfigUnitName()+"] to ["+className+"]");
			}

			oConfAction.addConfigurationUnit(zConfUnit);
		} // end for
	}

	public Collection<ConfigurationUnit> getConfUnitsSubscribedToMe(String zClassName) throws Exception {
		ConfigurationActions oConfActions = this.getConfigActionsMap().get(zClassName);

		if (oConfActions == null)
			return null;

		return oConfActions.getSubscriptedConfUnitsCollection();
	}

	@Override
	public IModuleClient getNewInstance() throws Exception {
		return getConfigurationManagerInstance();
	}

	@Override
	public String getModuleName() throws Exception {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean initializeModuleClient(MsgConnectionInterface zClient) throws Exception {
		super.initializeModuleClient(zClient);
		subscribeToRequestMsg(new ReqListenerSubscribeConfigUnit(this, "processSubscribeConfigUnit"));
		return true;
	}

}
