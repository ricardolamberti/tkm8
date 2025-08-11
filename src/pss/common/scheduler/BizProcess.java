package  pss.common.scheduler;

import java.lang.reflect.Method;

import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class BizProcess extends JRecord {

	JLong pid=new JLong();
	JString description=new JString();
	JString className=new JString();
  static JMap<String,Thread> running = JCollectionFactory.createMap();
  long uid = 0;
	
	public String getClassName() throws Exception {
		return className.getValue();
	}

	public long getPID() throws Exception {
		return pid.getValue();
	}

	public void setPID(long zValue) {
		pid.setValue(zValue);
	}

	public void setDescription(String zValue) {
		description.setValue(zValue);
	}

	public void setClassName(String zValue) {
		className.setValue(zValue);
	}

	public BizProcess() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("pid", pid);
		addItem("description", description);
		addItem("classname", className);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "pid", "Id", false, false, 20);
		addFixedItem(FIELD, "description", "Descripción", true, true, 100);
		addFixedItem(FIELD, "classname", "Clase", true, true, 300);
	}

	@Override
	public String GetTable() {
		return "SCH_PROCESS";
	}


	public boolean read(String zProceso) throws Exception {
		addFilter("pid", zProceso);
		return this.read();
	}

	public boolean readByClassname(String zProceso) throws Exception {
		addFilter("classname", zProceso);
		return this.read();
	}

	public boolean hasToExecuteProcess(BizProcessHost h) throws Exception {
		if ( h.hasToStop() ) tryTopStop(h);
		if ( running.getElement(h.getUID()+"") != null ) return false;
		return true;
	}


	private void tryTopStop(BizProcessHost h) throws Exception {
		Thread t = running.getElement(h.getUID()+"");
		if (t == null) return;
		PssLogger.logDebug("Interrupting process: " + this.description + " " + h.getParams() ); 
		t.interrupt();
	}

	@Override
	public void processDelete() throws Exception {
		deleteHostProcess();
		super.processDelete();
	}

	@Override
	public void processInsert() throws Exception {
		super.processInsert();
		setPID(getIdentity("pid"));
	}

	public void deleteHostProcess() throws Exception {
		JRecords<BizProcessHost> oProcesosHost=new JRecords<BizProcessHost>(BizProcessHost.class);
		oProcesosHost.addFilter("pid", this.pid.getValue());
		oProcesosHost.deleteAll();
	}

	public void executeInThread(BizProcessHost h) throws Exception {
		uid = h.getUID();
    Thread oth3 = new Thread() {
      public void run() {
      	try {
          execute();
      	} finally {
          registerProcessStop();    		
      		running.removeElement(uid+"");
      	}
      }
    };
    oth3.start();
    running.addElement(uid+"", oth3);
		h.setStatusToRunning();
	}
	
	private void registerProcessStop() {
		try {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("scheduler_stopprocess", JAplicacion.AppTipoThread(), true);
			BizProcessHost h = new BizProcessHost();
		  h.dontThrowException(true);
		  if ( h.read(uid) )
		    h.setStatusToStopped();
		  PssLogger.logDebug("Process stopped : " + this.className + " " + h.getParams() );
		} catch ( Exception ee ) {
			PssLogger.logError(ee);
		}	finally {
			try {
				JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();
			} catch (Exception ignored) {}
		}
	}

	public void execute() {
		try {
			String sParametros="";
			String sComando=className.getValue();
			JStringTokenizer st=JCollectionFactory.createStringTokenizer(sComando, '|');
			int iCantToks=st.countTokens();
			if (iCantToks<2) JExcepcion.SendError("Sintaxis Incorrecta");
			String sClase=st.nextToken();
			String sMetodo=st.nextToken();
			Class<?>[] aParamClas=null;
			Object[] aParamArgs=null;

			if (iCantToks-2>0) {
				aParamClas=new Class[iCantToks-2];
				aParamArgs=new Object[iCantToks-2];
				for(int i=0; st.hasMoreTokens(); i++) {
					String param=st.nextToken();
					aParamClas[i]=String.class;
					aParamArgs[i]=param;
					sParametros+=param+" ";
				}
			}

			PssLogger.logInfo("Class + Method to process : "+sClase+"."+sMetodo+"( "+sParametros+")");

			Object oClase=Class.forName(sClase).newInstance();
			Method oMethod=oClase.getClass().getMethod(sMetodo, aParamClas);
			PssLogger.logInfo("Invoking method");
			if ( aParamClas == null )
			  oMethod.invoke(sMetodo);
			else 
				oMethod.invoke(sMetodo, aParamArgs);
		} catch (Exception e) {
			PssLogger.logError(e);
		}
	}


	public JRecords<BizProcessHost> getProcesosHost(String nodo, String host) throws Exception {
		if (!nodo.equals(BizUsuario.getUsr().getNodo())) return null;
		JRecords<BizProcessHost> oProcesos=new JRecords<BizProcessHost>(BizProcessHost.class);
		oProcesos.addFilter("pid", pid.getValue());
		oProcesos.addFilter("host", host);
		oProcesos.readAll();
		oProcesos.toStatic();
		return oProcesos;
	}

}
