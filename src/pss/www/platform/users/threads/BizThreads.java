package pss.www.platform.users.threads;

import java.util.Set;

import pss.core.services.records.JRecords;

public class BizThreads extends JRecords<BizThread> {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizThreads() throws Exception {
	}

	@Override
	public Class<BizThread> getBasedClass() {
		return BizThread.class;
	}

	@Override
	public String GetTable() {
		return "";
	}


	@Override
	public boolean readAll() throws Exception {
		this.clearStaticItems();
		
		setStatic(true);
		Set<Thread> threads = Thread.getAllStackTraces().keySet();
		 
		for (Thread t : threads) {
		    BizThread record = new BizThread();
		    record.fill(t);
		    addItem(record);
		  			    
		}
		Ordenar("id");
		firstRecord();
		return true;
	}
	


}