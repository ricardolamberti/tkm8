package pss.www.platform.users.threads;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.ref.WeakReference;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizThread extends JRecord {

		
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  private JString pDescripcion = new JString();
  private JString pState = new JString();
  private JLong pPriority = new JLong();
  private JString pType = new JString();
  private JLong pId = new JLong();
  private JFloat pBlockedTime = new JFloat();
  private JFloat pWaitedTime = new JFloat();
  private JFloat pCpuTime = new JFloat();
  private JFloat pUserTime = new JFloat();
  
   
//  private BizUsuario user;
  private transient WeakReference<Thread> weakThread;
  
  Thread getThread() throws Exception {
  	if (weakThread==null) return null;
  	Thread thread = weakThread.get() ; 
  	return thread;
  }

  void setThread(Thread s ) throws Exception {
  	weakThread = new WeakReference<Thread>(s);
  }
   //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizThread() throws Exception {}

  @Override
	public void createProperties() throws Exception {
    addItem("id", pId);
    addItem("description", pDescripcion);
    addItem("state", pState);
    addItem("priority", pPriority);
    addItem("type", pType);
    addItem("blocked", pBlockedTime);
    addItem("wait", pWaitedTime);
    addItem("cputime", pCpuTime);
    addItem("usertime", pUserTime);
       
  }
  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem(KEY, "id", "Id", true, true, 18);
    addFixedItem(FIELD, "description", "description", true, true, 250);
    addFixedItem(FIELD, "state", "state", true, true, 250);
    addFixedItem(FIELD, "priority", "priority", true, true, 18);
    addFixedItem(FIELD, "type", "type", true, true, 250);
    addFixedItem(FIELD, "blocked", "Blocked Time (seg)", true, true, 18);
    addFixedItem(FIELD, "wait", "Wait Time (seg)", true, true, 18);
    addFixedItem(FIELD, "cputime", "CPU Time (seg)", true, true, 18);
    addFixedItem(FIELD, "usertime", "User Time (seg)", true, true, 18);
   }

  @Override
	public String GetTable() {
    return "";
  }

  
  @Override
  public void processDelete() throws Exception {
	 	Thread thread = getThread();
	  if (thread==null) return;
	  thread.interrupt();
  }
  transient ThreadMXBean threadMXBean;
  public ThreadMXBean getThreadMXBean() throws Exception {
  	if (threadMXBean!=null) return threadMXBean;
    threadMXBean = ManagementFactory.getThreadMXBean();
    if (!threadMXBean.isThreadCpuTimeSupported())  return threadMXBean;
    if (!threadMXBean.isCurrentThreadCpuTimeSupported())  return threadMXBean;
 
    threadMXBean.setThreadContentionMonitoringEnabled(true);
    threadMXBean.setThreadCpuTimeEnabled(true);
   return threadMXBean;
  } 
  public void fill(Thread t) throws Exception {
  	setThread(t);
    String type = t.isDaemon() ? "Daemon" : "Normal";
    pId.setValue(t.getId());
  	pDescripcion.setValue(t.getName());
  	pState.setValue(t.getState());
  	pPriority.setValue(t.getPriority());
  	pType.setValue(type);

	
    ThreadInfo threadInfo = getThreadMXBean().getThreadInfo(t.getId());
    double blockedTime = threadInfo.getBlockedTime()/1000;
    double waitedTime = threadInfo.getWaitedTime()/1000;
    double cpuTime = getThreadMXBean().getThreadCpuTime(threadInfo.getThreadId())/1000;
    double userTime = getThreadMXBean().getThreadUserTime(threadInfo.getThreadId())/1000;
    
   	pBlockedTime.setValue(blockedTime);
   	pWaitedTime.setValue(waitedTime);
   	pCpuTime.setValue(cpuTime);
   	pUserTime.setValue(userTime);
        

  }
  

  
}