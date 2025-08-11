package pss.www.platform.users.online;

import java.lang.ref.WeakReference;
import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.data.connectionPool.JDBConnectionPools;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.applications.JWebServer;
import pss.www.platform.users.history.BizUserHistory;

public class BizOnlineUser extends JRecord {

	static final int MAX_HISTORY = 20;
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  private JString pSubsession = new JString();
  private JString pUser = new JString();
  private JBoolean pPersistente = new JBoolean();
  private JBoolean pCached = new JBoolean();
  private JLong pTImeout = new JLong();
  private JString pApp = new JString();
  private JString pBase = new JString();
  private JLong pReqCount = new JLong();
  private JFloat pReqPromedio = new JFloat();
  private JDateTime pLastReqTime = new JDateTime();
  private JLong pObjs = new JLong();
  private JLong pHistory = new JLong();
  private JString pDB = new JString();
  private JLong pDBFree = new JLong();
  private JFloat pIdleTime = new JFloat();
  private JBoolean pAgrupado = new JBoolean();
  private JString pIp = new JString();
  
//  	public void preset() throws Exception {
//  		pReqCount.setValue(getReqCount());
//  	}
//  };
  private JObjBDs<BizUserHistory> pStadistics = new JObjBDs<BizUserHistory>();
  private JLong pMemory = new JLong();
  private JString pTipoUsuario = new JString(){
		public void preset() throws Exception {
			pTipoUsuario.setValue(getUsuarioTipo());
		};
	};
  private JString pUserDescription = new JString(){
		public void preset() throws Exception {
			pUserDescription.setValue(getObjUsuario().getDescrUsuario());
		};
	};
  
//  private BizUsuario user;
 
  public void setUser(String user) throws Exception { pUser.setValue(user);}
  public String getUser() throws Exception {return pUser.getValue();}
  public String getApp() throws Exception {return pApp.getValue();}
  public String getBase() throws Exception {return pBase.getValue();}
  public JRecords<BizUserHistory> getStadistics() throws Exception {return pStadistics.getValue();}

  
  

   //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizOnlineUser() throws Exception {}

  @Override
	public void createProperties() throws Exception {
    addItem("user", pUser);
    addItem("subsession", pSubsession);
    addItem("app", pApp);
    addItem("base", pBase);
    addItem("persistent", pPersistente);
    addItem("timeout", pTImeout);
    addItem("cached", pCached);
    addItem("req_count", pReqCount);
    addItem("req_obj", pObjs);
    addItem("req_his", pHistory);
    addItem("req_db", pDB);
    addItem("req_dbfree", pDBFree);
    addItem("req_promedio", pReqPromedio);
    addItem("last_reqtime", pLastReqTime);
    addItem("idle_time", pIdleTime);
    addItem("user_description", pUserDescription);
    addItem("tipo_usuario", pTipoUsuario);
    addItem("memory", pMemory);
    addItem("agrupado", pAgrupado);
    addItem("ip", pIp);
    addItem("stad", pStadistics);
  }
  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem(KEY, "user", "Usuario", true, true, 250);
    addFixedItem(FIELD, "subsession", "Tipo", true, true, 250);
    addFixedItem(FIELD, "user_description", "Descripcion", true, true, 250);
    addFixedItem(FIELD, "tipo_usuario", "Tipo", true, true, 250);
    addFixedItem(FIELD, "persistent", "Persist.", true, false, 1);
    addFixedItem(FIELD, "cached", "Cached", true, false, 1);
    addFixedItem(FIELD, "timeout", "Expire(minutes)", true, false, 18);
    addFixedItem(FIELD, "app", "Aplicacion", true, true, 250);
    addFixedItem(FIELD, "base", "Base", true, true, 250);
    addFixedItem(FIELD, "req_count", "#Req", true, true, 10);
    addFixedItem(FIELD, "req_obj", "Objetos", true, true, 10);
    addFixedItem(FIELD, "req_his", "History size", true, true, 10);
    addFixedItem(FIELD, "req_db", "DB ", true, true, 200);
    addFixedItem(FIELD, "req_dbfree", "Pool DB Free", true, true, 10);
    addFixedItem(FIELD, "req_promedio", "Promedio", true, true, 10);
    addFixedItem(FIELD, "last_reqtime", "Ultimo acceso", true, true, 10);
    addFixedItem(FIELD, "idle_time", "Tiempo Idle", true, true, 10,4);
    addFixedItem(FIELD, "memory", "Memoria usada", true, true, 18);
    addFixedItem(FIELD, "ip", "Ip", true, true, 100);
    addFixedItem(VIRTUAL, "agrupado", "Agrupado", true, false, 1);
    addFixedItem(RECORDS, "stad", "Estadistica", true, false, 250);
  }

  @Override
	public String GetTable() {
    return "";
  }

  public void fill(String subsession, JWebApplicationSession s) throws Exception {
//  	user = session.getUser();
  		pSubsession.setValue(subsession);
  	pUser.setValue(s.getUser().GetUsuario());
  	pApp.setValue(s.getOldAplicacionObject().sAplicacionID);
  	pPersistente.setValue(s.getPersistentKey()!=null);
  	pCached.setValue(s.isCacheInDisk());
  	pTImeout.setValue(s.getTimeout());
  	pBase.setValue(s.getDatabase().getCurrentDatabase());
  	pObjs.setValue(JWebActionFactory.getCurrentRequest().getRegisteredObjectsOld().size());
  	pHistory.setValue(s.getHistoryManager().getActionHistory().size());
   	pDB.setValue(s.getDatabase().getCurrentDatabase());
    pDBFree.setValue(JDBConnectionPools.getInstance().howManyConnection(s.getDatabase().getCurrentDatabase()));
  	pReqCount.setValue(this.getReqCount());
  	pReqPromedio.setValue(this.getReqPromedio());
  	pIp.setValue(s.getIpAddress());
//   	pMemory.setValue(ObjectSizeCalculator.getObjectSize(s.getRegisteredObjects()));
   	pStadistics.setValue(convertToHistory(s.getStadistics()));
     	
  	BizUserHistory h = this.getStadistics().getLastStaticElement();
  	if (h!=null) {
  		pLastReqTime.setValue(h.getFechaHora());
  	
  		Date today = BizUsuario.getUsr().todayGMT(true);
    	long diff = today.getTime()-this.pLastReqTime.getValue().getTime();
    	pIdleTime.setValue(JTools.rd((double)((diff/1000d)/60d),2));
  	}
  }
  
  public JRecords<BizUserHistory> convertToHistory(JMap<Long, BizUserHistory> map) throws Exception {
  	JStadistics stadistics =  JWebServer.getInstance().getWebApplication(null).getStadistics();
  	JIterator<Long> iter = map.getKeyIterator();
    JRecords<BizUserHistory> recs = pStadistics.getRawValue();
    if (recs==null) 
    	recs=new JRecords<BizUserHistory>(BizUserHistory.class);
    int parcial = recs.getStaticItems().size();
    int total =parcial+map.size();
    int eliminar = total - MAX_HISTORY<0?0:(parcial<total - MAX_HISTORY?parcial:total-MAX_HISTORY);
  	recs.setStatic(true);
  	JIterator<BizUserHistory> it = recs.getStaticIterator();
  	while (it.hasMoreElements() && eliminar>0) {
  		BizUserHistory uh=it.nextElement();
  		it.remove();
  		eliminar--;
  	}
  	while(iter.hasMoreElements()) {
  		Long key = iter.nextElement();
  		BizUserHistory hist = map.getElement(key);
  		recs.addItem(hist);
  	}
//  	recs.Ordenar("fecha_hora");
//  	recs.invertirOrden();
  	return recs;
  
  }
  
//  private JWebApplicationSession findSession(String user) throws Exception {
//		JApplicationSessionManager sessionManager =  JWebServer.getInstance().getWebApplication(null).getSessionManager();
//		JMap<String, Object> sessions = sessionManager.getSessions();
//		JIterator<Object> it = sessions.getValueIterator();
//		while (it.hasMoreElements()) {
//			JWebApplicationSession session = (JWebApplicationSession)it.nextElement();
//			if (session.getUser().GetUsuario().equalsIgnoreCase(user)) return session;
//		}
//		return null;
//
//  }
  
  @Override
  public void processDelete() throws Exception {
// 	JWebApplicationSession session = s;//RJL esto dejo de funcionar
//  if (session==null) return;
//  	session.logoutAdmin();
  }
  
  public long getReqCount() throws Exception {
   	return getStadistics()==null?0:getStadistics().size();
  }
  public long getReqPromedio() throws Exception {
  	long suma=0L;
  	if (getStadistics()==null) return 0;
  
  	JIterator<BizUserHistory> iter = getStadistics().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizUserHistory h = iter.nextElement();
  		suma+=h.getTotal();
  	}
  	long cant = this.getReqCount();
  	if (cant==0) return suma;
  	return suma/cant;
  }

//  public BizUserHistory getLastHistory() throws Exception {
//   	JWebApplicationSession session = s;
//    if (session==null) throw new Exception("La session Expiró");
//
//    BizUserHistory last=null;
//  	JIterator<BizUserHistory> it = session.getStadistics().getValueIterator();
//  	while (it.hasMoreElements()) {
//  		last = it.nextElement();
//  	}
//  	return last;
//  }
  
  
  public JRecords<BizUserHistory> getUserHistories() throws Exception {
  

  	return getStadistics();
  }
 
	BizUsuario objUsuario;
	public BizUsuario getObjUsuario() throws Exception {
		if (objUsuario != null)
			return objUsuario;
		BizUsuario c = BizUsuario.getUserInstance(BizUsuario.getUsr());
	//	c.Read(this.getUser());
		return objUsuario = c;
	}
	
	public String getUsuarioTipo() throws Exception {
		if (getObjUsuario().getObjUsuarioTipo()!=null) 
			return getObjUsuario().getObjUsuarioTipo().getIdUsuarioTipo();
		else
			return "" ;
	}
	
	public void add(BizOnlineUser user) throws Exception {
		pReqCount.setValue(pReqCount.getValue()+user.getReqCount());
		pMemory.setValue(pMemory.getValue()+user.pMemory.getValue());
		pObjs.setValue(pObjs.getValue()+user.pObjs.getValue());
		pHistory.setValue(pHistory.getValue()+user.pHistory.getValue());
		pIdleTime.setValue(Math.min(pIdleTime.getValue(),user.pIdleTime.getValue()));
		pLastReqTime.setValue(JDateTools.dateEqualOrAfter(pLastReqTime.getValue(),user.pLastReqTime.getValue())?pLastReqTime.getValue():user.pLastReqTime.getValue());
	}
  
  
}