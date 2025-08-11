package  pss.common.moduleConfigMngr;

import java.util.Calendar;
import java.util.Date;

import pss.common.moduleConfigMngr.actions.BizModuleConfigurationAction;
import pss.common.moduleConfigMngr.actions.ModuleConfAction;
import pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.lastConfigurationId.ReqConfigurationId;
import pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.lastConfigurationId.ReqLastConfigurationId;
import pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.lastConfigurationId.ReqListenerLastConfigurationId;
import pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.newConfigurationApplied.ReqListenerNewConfigurationApplied;
import pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.newConfigurationApplied.ReqNewConfigurationApplied;
import pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.subscribeMdlConfigMngr.ReqListenerSubscribeMdlConfigMngr;
import pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.subscribeMdlConfigMngr.ReqSubscribeMdlConfigMngr;
import pss.common.moduleConfigMngr.common.ModuleConfigMngr.responses.lastConfigurationId.RespConfigurationId;
import pss.common.moduleConfigMngr.common.ModuleConfigMngr.responses.lastConfigurationId.RespLastConfigurationId;
import pss.common.moduleConfigMngr.common.ModuleConfigMngr.responses.newConfigurationApplied.RespNewConfigurationApplied;
import pss.common.moduleConfigMngr.transactions.BizModuleConfigTransaction;
import pss.common.moduleConfigMngr.transactions.ModuleConfTransaction;
import pss.core.connectivity.messageManager.client.connection.IModuleClient;
import pss.core.connectivity.messageManager.client.connection.MessageClient;
import pss.core.connectivity.messageManager.client.connection.MsgConnectionInterface;
import pss.core.connectivity.messageManager.common.core.loader.IConfigurationLoader;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MsgResponse;
import pss.core.connectivity.messageManager.server.confMngr.IModuleLoadeableConfMgr;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;

public class JMMModuleConfigMngr extends MessageClient implements IModuleLoadeableConfMgr {
	private static JMMModuleConfigMngr gMdlConfigMngr = null;
	private JRecords<BizModuleConfigMngr> pMdlsConfigs = null;
	IConfigurationLoader configLoader;

	@Override
	public String getConnectionString() {
		return "local";
	}

	@Override
	public IMessageContent onConnect(IMessageContent zMsg) throws Exception {
		// SysEvtListenerOnConnect evt = (SysEvtListenerOnConnect) zMsg;
		//		
		// if (evt)
		return null;
	}

	@Override
	public IMessageContent onDisconnect(IMessageContent zMsg) throws Exception {
		return null;
	}

	@Override
	public boolean initializeModuleClient(MsgConnectionInterface zClient) throws Exception {
		super.initializeModuleClient(zClient);
		this.subscribeToRequestMsg(new ReqListenerSubscribeMdlConfigMngr(this, "processSubscribeMdlConfigMngr"));
		this.subscribeToRequestMsg(new ReqListenerNewConfigurationApplied(this, "processNewConfigurationApplied"));
		this.subscribeToRequestMsg(new ReqListenerLastConfigurationId(this, "processLastConfigurationId"));
		return true;
	}

	@Override
	public IModuleClient getNewInstance() throws Exception {
		if (gMdlConfigMngr == null) {
			gMdlConfigMngr = new JMMModuleConfigMngr();
		}
		return gMdlConfigMngr;
	}

	public JRecords<BizModuleConfigMngr> getMdlsConfigs() throws Exception {
		if (this.pMdlsConfigs == null) {
			this.pMdlsConfigs = new JRecords<BizModuleConfigMngr>();
			this.pMdlsConfigs.readAll();
		}

		return pMdlsConfigs;
	}

	public IMessageContent processSubscribeMdlConfigMngr(IMessageContent zMsg) throws Exception {
		ReqSubscribeMdlConfigMngr rSMCM = (ReqSubscribeMdlConfigMngr) zMsg;
		MsgResponse oMR = new MsgResponse(zMsg);

		if (rSMCM.getCompany() == null || rSMCM.getCompany().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo compania esta vacio o nulo");
			return oMR;
		}

		if (rSMCM.getCountry() == null || rSMCM.getCountry().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo country esta vacio o nulo");
			return oMR;
		}

		if (rSMCM.getNode() == null || rSMCM.getNode().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo node esta vacio o nulo");
			return oMR;
		}

		if (rSMCM.getModuleId() == null || rSMCM.getModuleId().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo ModuleId esta vacio o nulo");
			return oMR;
		}

		if (rSMCM.getConfigType() == null || rSMCM.getConfigType().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo configType esta vacio o nulo");
			return oMR;
		}

		BizModuleConfigMngr bizMngr = new BizModuleConfigMngr();
		bizMngr.dontThrowException(true);

		if (bizMngr.read(rSMCM.getCompany(), rSMCM.getNode(), rSMCM.getCountry(), rSMCM.getModuleId(), rSMCM.getConfigType())) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.OK);
			oMR.setResponseDescription("El modulo [" + rSMCM.getModuleId() + "] ya esta registrado");
			return oMR;
		}

		bizMngr.setCompany(rSMCM.getCompany());
		bizMngr.setCountry(rSMCM.getCountry());
		bizMngr.setNode(rSMCM.getNode());
		bizMngr.setModuleId(rSMCM.getModuleId());
		bizMngr.setConfigType(rSMCM.getConfigType());
		bizMngr.setConfigId(0);
		bizMngr.setConfigDate(Calendar.getInstance().getTime());
		bizMngr.execProcessInsert();

		return oMR;
	}

	public IMessageContent processLastConfigurationId(IMessageContent zMsg) throws Exception {
		ReqLastConfigurationId reqLCI = (ReqLastConfigurationId) zMsg;
		MsgResponse oMR = new MsgResponse(zMsg);
		RespLastConfigurationId respLCI = new RespLastConfigurationId();

		// Chequeos
		if (reqLCI.getCompany() == null || reqLCI.getCompany().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo compania esta vacio o nulo");
			return oMR;
		}

		if (reqLCI.getCountry() == null || reqLCI.getCountry().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo country esta vacio o nulo");
			return oMR;
		}

		if (reqLCI.getNode() == null || reqLCI.getNode().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo node esta vacio o nulo");
			return oMR;
		}

		BizModuleConfigMngr bizMngr;

		if (reqLCI.ifHaveReqConfigId()) {
			bizMngr = new BizModuleConfigMngr();
			bizMngr.dontThrowException(true);
			for (ReqConfigurationId reqCI : reqLCI.getReqConfigIdList()) {
				if (bizMngr.read(reqLCI.getCompany(), reqLCI.getNode(), reqLCI.getCountry(), reqCI.getModuleId(), reqCI.getConfigType())) {
					respLCI.addLastConfigurationId(bizMngr.getModuleId(), bizMngr.getConfigType(), bizMngr.getConfigId(), bizMngr.getConfigDate());
				} else {
					respLCI.addLastConfigurationId(reqCI.getModuleId(), reqCI.getConfigType(), -1, null);
				}
			}
		} else {
			JRecords<BizModuleConfigMngr> bizMngrs = new JRecords<BizModuleConfigMngr>();
			bizMngrs.addFilter("company", reqLCI.getCompany());
			bizMngrs.addFilter("node", reqLCI.getNode());
			bizMngrs.addFilter("country", reqLCI.getCountry());
			bizMngrs.readAll();

			while (bizMngrs.nextRecord()) {
				bizMngr = bizMngrs.getRecord();
				respLCI.addLastConfigurationId(bizMngr.getModuleId(), bizMngr.getConfigType(), bizMngr.getConfigId(), bizMngr.getConfigDate());
			}
		}

		oMR.setResponseCode(MsgResponse.TypeResponseCode.OK);
		oMR.addMessageComponent(respLCI);
		return oMR;
	}

	public IMessageContent processNewConfigurationApplied(IMessageContent zMsg) throws Exception {
		ReqNewConfigurationApplied rNCA = (ReqNewConfigurationApplied) zMsg;
		MsgResponse oMR = new MsgResponse(zMsg);

		// Chequeos
		if (rNCA.getCompany() == null || rNCA.getCompany().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo compania esta vacio o nulo");
			return oMR;
		}

		if (rNCA.getCountry() == null || rNCA.getCountry().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo country esta vacio o nulo");
			return oMR;
		}

		if (rNCA.getNode() == null || rNCA.getNode().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo node esta vacio o nulo");
			return oMR;
		}

		if (rNCA.getModuleId() == null || rNCA.getModuleId().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo ModuleId esta vacio o nulo");
			return oMR;
		}

		if (rNCA.getConfigType() == null || rNCA.getConfigType().isEmpty()) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El campo configType esta vacio o nulo");
			return oMR;
		}

		if (rNCA.ifHaveTransactions() == false) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("No hay transacciones");
			return oMR;
		}

		BizModuleConfigMngr bizMngr = new BizModuleConfigMngr();
		bizMngr.dontThrowException(true);

		if (bizMngr.read(rNCA.getCompany(), rNCA.getNode(), rNCA.getCountry(), rNCA.getModuleId(), rNCA.getConfigType()) == false) {
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription("El modulo [" + rNCA.getModuleId() + "] no esta registrado con los siguiente parametros: Company [" + rNCA.getCompany() + "] - Country [" + rNCA.getCountry() + "] - Nodo [" + rNCA.getNode() + "] - Conf.Type [" + rNCA.getConfigType() + "] ");
			return oMR;
		}

		Date trxDateTime = Calendar.getInstance().getTime();
		long configId;

		// Procesamiento
		try {
			JBDatos.GetBases().beginTransaction();

			// Incremento el Config Id y updateo
			configId = bizMngr.getConfigId() + 1;
			bizMngr.setConfigId(configId);
			bizMngr.processUpdate();

			// Proceso las transacciones
			BizModuleConfigTransaction bizTranConfig = new BizModuleConfigTransaction();
			bizTranConfig.setCompany(rNCA.getCompany());
			bizTranConfig.setCountry(rNCA.getCountry());
			bizTranConfig.setNode(rNCA.getNode());
			bizTranConfig.setModuleId(rNCA.getModuleId());
			bizTranConfig.setConfigType(rNCA.getConfigType());
			bizTranConfig.setConfId(configId);
			// proceso las transacciones
			for (ModuleConfTransaction trx : rNCA.getTransactionList()) {
				bizTranConfig.setCreationDatetime(trxDateTime);
				bizTranConfig.setDescription(trx.getTrxDescription());
				bizTranConfig.setTransactionMsg(trx.getTransaction());
				bizTranConfig.processInsert();
			} // end for

			// Proceso las acciones
			BizModuleConfigurationAction bizConfAction = new BizModuleConfigurationAction();
			bizConfAction.setCompany(rNCA.getCompany());
			bizConfAction.setCountry(rNCA.getCountry());
			bizConfAction.setNode(rNCA.getNode());
			bizConfAction.setModuleId(rNCA.getModuleId());
			bizConfAction.setConfigType(rNCA.getConfigType());

			String actionDataByConfig = new String("conf_id=" + configId);

			if (rNCA.ifHaveActions()) {
				for (ModuleConfAction act : rNCA.getActionList()) {
					bizConfAction.setActionId(act.getActionId());
					if (act.getActionData().indexOf(actionDataByConfig) == -1)
						bizConfAction.setActionData(actionDataByConfig + "#+" + act.getActionData());
					else
						bizConfAction.setActionData(act.getActionData());
					bizConfAction.setActionCreationdatetime(trxDateTime);
					bizConfAction.setActionState(BizModuleConfigurationAction.ACT_STATE_NEW);
					bizConfAction.setActionStatedatetime(trxDateTime);
					bizConfAction.processInsert();
				} // end for
			} else {
				bizConfAction.setActionId(ModuleConfAction.NONE_ACTION_ID);
				bizConfAction.setActionData(actionDataByConfig);
				bizConfAction.setActionCreationdatetime(trxDateTime);
				bizConfAction.setActionState(BizModuleConfigurationAction.ACT_STATE_DONE);
				bizConfAction.setActionStatedatetime(trxDateTime);
				bizConfAction.setActionResult(BizModuleConfigurationAction.ACT_RESULT_OK);
				bizConfAction.setActionResultdatetime(trxDateTime);
				bizConfAction.processInsert();
			} // end if

			JBDatos.GetBases().commit();
		} catch (Exception e) {
			JBDatos.GetBases().rollback();
			oMR.setResponseCode(MsgResponse.TypeResponseCode.ERROR);
			oMR.setResponseDescription(e.getMessage());
			return oMR;

		}

		oMR.setResponseCode(MsgResponse.TypeResponseCode.OK);
		RespNewConfigurationApplied respNCA = new RespNewConfigurationApplied();
		respNCA.setRespConfigId(new RespConfigurationId(rNCA.getModuleId(), rNCA.getConfigType(), configId, trxDateTime));
		return oMR;
	}

	@Override
	public void setConfiguration(IConfigurationLoader config) {
		configLoader= config;
		
	}
}
