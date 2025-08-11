package pss.common.version.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pss.common.version.IStoreAndForwardeable;
import pss.common.version.protocol.ReqNewTransaction;
import pss.common.version.protocol.ReqNewTransactions;
import pss.common.version.storeAndForward.BizStoreForwardHeader;
import pss.common.version.storeAndForward.detail.BizStoreForward;
import pss.core.connectivity.messageManager.client.connection.MessageClient;
import pss.core.connectivity.messageManager.client.connection.MsgConnectionInterface;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MsgResponse;
import pss.core.connectivity.messageManager.common.message.MsgResponse.TypeResponseCode;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IProcessMsg;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.tools.PssLogger;

public class JVersionClient  extends MessageClient implements IProcessMsg {
 
	private TcpVersionClient tcpMsgClt = null;
	private IVersionClientProcess process = null;
	private String connection;
  
	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}


	public JVersionClient(IVersionClientProcess p) throws Exception {
		process=p;
		setClientSide(true);
	}

	private TcpVersionClient getTcpVersionClient() throws Exception {
		if (tcpMsgClt!=null) return tcpMsgClt;
		TcpVersionClient t = new TcpVersionClient(process);
	  return tcpMsgClt=t;
	}
	

	// @Override
	public IMessageContent onConnect(IMessageContent zMsg) throws Exception {
		getTcpVersionClient().start(getConnection());
		return null;
	}

	// @Override
	public IMessageContent onDisconnect(IMessageContent zMsg) throws Exception {
		getTcpVersionClient().stop();
		return null;
	}
	
	

	@Override
	public String getConnectionString() {
		return "local";
	}


	@Override
	public String getModuleName() throws Exception {
		return this.getClass().getSimpleName();
	}



	@Override
	public boolean initializeModuleClient(MsgConnectionInterface zClient) throws Exception {
		super.initializeModuleClient(zClient);
		subscribeToRequestMsg(new ReqNewTransaction(), this);
		return true;
	}

	
	@Override
	public IMessageContent processMessageContent(IMessageContent zMessage) throws Exception {
		if (zMessage instanceof ReqNewTransactions) return execProcess((ReqNewTransactions) zMessage);
		return null;
	}
	// metodo 1, sin concommitancia
	
	static long processStoreAndForwardNewPack() throws Exception {
		BizStoreForwardHeader sf = new BizStoreForwardHeader();
		long id = sf.SelectMaxLong("id_pack")+1;
		sf.setId(id);
		sf.setStore("local");
		sf.processInsert();
		return id;
	}


	IMessageContent execProcess(ReqNewTransactions trxs) throws Exception {
		MsgResponse oMR = new MsgResponse(null);
		try {
			JBDatos.GetBases().beginTransaction();
			long idPack = processStoreAndForwardBegin();
			for (ReqNewTransaction trx : trxs.trxs) {
				storeAndForward(idPack, trx);
			}
			JBDatos.GetBases().commit();

			oMR.setResponseCode(TypeResponseCode.OK);
		} catch (Exception e) {
			PssLogger.logError(e);
			JBDatos.GetBases().rollback();
			oMR.setResponseCode(TypeResponseCode.ERROR);
			oMR.setResponseDescription(e.getMessage());
		}
		return oMR;
	}


	static void storeAndForward(long idPack,ReqNewTransaction trx) throws Exception {
		BizStoreForward sf = new BizStoreForward();
		sf.setKeyMessage(trx.keyMessage);
		sf.setMessage(trx.message.toString());
		sf.setIdPack(idPack);
		sf.setId(sf.SelectMaxLong("id")+1);
		sf.setStore("local");
		sf.processInsert();
	}
	
	
	// metodo 2, con concommitancia
	
	static Map <Long, Long> commits;
	static boolean clientSide;
	
	public static boolean isClientSide() {
		return clientSide;
	}

	public static void setClientSide(boolean clientSide) {
		JVersionClient.clientSide = clientSide;
	}

	static Map <Long, Long> getCommits() {
		if (commits!=null) return commits;
		commits = new HashMap<Long, Long>();
		return commits;
	}
	static public long processStoreAndForwardBegin() throws Exception {
		long idThread = Thread.currentThread().getId();
		Long lidPack = getCommits().get(idThread);
		if (lidPack!=null) return lidPack.longValue();
		
		BizStoreForwardHeader sf = new BizStoreForwardHeader();
		long id = sf.SelectMaxLong("id_pack")+1;
		sf.setId(id);
		sf.setDescription("Envio "+new Date());
		sf.setStore("local");
		sf.processInsert();
		getCommits().put(idThread, id);
		return id;
	}
	static public void processStoreAndForwardEnd() throws Exception {
		long idThread = Thread.currentThread().getId();
		commits.remove(idThread);
	}
	
	static public void storeAndForward(IStoreAndForwardeable trx) throws Exception {
		long idThread = Thread.currentThread().getId();
		Long lidPack = getCommits().get(idThread);
		if (lidPack==null) 
			getCommits().put(idThread, processStoreAndForwardBegin());
		long idPack = getCommits().get(idThread);
		BizStoreForward sf = new BizStoreForward();
		sf.setKeyMessage(trx.getKeyMessage());
		sf.setMessage(trx.serialize());
		sf.setIdPack(idPack);
		sf.setId(sf.SelectMaxLong("id")+1);
		sf.setStore("local");
		sf.processInsert();
	}
	
}