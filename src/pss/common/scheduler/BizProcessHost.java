package  pss.common.scheduler;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;

public class BizProcessHost extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JLong uid=new JLong();
	JLong pid=new JLong();
	JString host=new JString();
	JLong idParam=new JLong();
	JString param=new JString();
	JString status=new JString();
	JBoolean stop=new JBoolean();
	JString desc_status=new JString() {
		public void preset() throws Exception {
			if ( status.getValue().equals(RUNNING) == true ) {
				if ( stop.getValue() )
				  desc_status.setValue("Ejecutando(a detener)");
				else
				  desc_status.setValue("Ejecutando");
			} else
				desc_status.setValue("Detenido");
		}
	};
	
	public static String RUNNING = "R";
	public static String STOPPED = "S";


	public String getIdParam() throws Exception {
		return idParam.toString();
	}

	public String getParams() throws Exception {
		return param.toString();
	}

	public String getHost() throws Exception {
		return host.toString();
	}
	
	public void setHost(String zValue) {
		host.setValue(zValue);
	}

	public String getStatus() throws Exception {
		return status.toString();
	}
	
	public void setStatus(String zValue) {
		status.setValue(zValue);
	}

	public boolean hasToStop() throws Exception {
		return stop.getValue();
	}
	
	public void setStop(boolean zValue) {
		stop.setValue(zValue);
	}

	public void setUID(long zValue) {
		uid.setValue(zValue);
	}

	public long getUID() throws Exception {
		return uid.getValue();
	}

	public void setPID(long zValue) {
		pid.setValue(zValue);
	}

	public long getPID() throws Exception {
		return pid.getValue();
	}
	
	public void setIdParam(long zValue) {
		idParam.setValue(zValue);
	}

	public void setParam(String zValue) {
		param.setValue(zValue);
	}

	public BizProcessHost() throws Exception {
		addItem("uniqueid", uid);
		addItem("pid", pid);
		addItem("host", host);
		addItem("id_params", idParam);
		addItem("params", param);
		addItem("status", status);
		addItem("stop", stop);
		addItem("desc_status", desc_status);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "uniqueid", "unique id", false, false, 18);
		addFixedItem(FIELD, "pid", "Proceso", true, false, 18);
		addFixedItem(FIELD, "host", "Servidor", true, true, 80);
		addFixedItem(FIELD, "id_params", "ID Param", true, false, 20);
		addFixedItem(FIELD, "params", "Parametros", true, false, 300);
		addFixedItem(FIELD, "status", "Status", true, false, 1);
		addFixedItem(FIELD, "stop", "Stop?", true, false, 1);
		addFixedItem(VIRTUAL, "desc_status", "Status", false, false, 50);
	}

	@Override
	public String GetTable() {
		return "SCH_PROCESS_HOST";
	}
	
	public boolean read(long uid) throws Exception {
		addFilter("uniqueid", uid);
		return this.read();
	}

	public boolean read(long pid, String host) throws Exception {
		addFilter("pid", pid);
		addFilter("host", host.toLowerCase());
		return this.read();
	}

	public boolean read(String pid, String host, String param) throws Exception {
		addFilter("pid", pid);
		addFilter("host", host.toLowerCase());
		addFilter("id_params", param);
		return this.read();
	}

	@Override
	public void processInsert() throws Exception {
		JRecords<BizProcessHost> oBDs=new JRecords<BizProcessHost>(BizProcessHost.class);
		oBDs.addFilter("pid", pid.getValue());
		oBDs.addFixedFilter("(host='"+ host.getValue().toLowerCase()+"' or host='*')");
		oBDs.addFilter("params", param.getValue().trim());
		if (oBDs.exists()) JExcepcion.SendError("Ya existe registro con el mismo servidor/parametro");
		
		BizProcessHost oMax=new BizProcessHost();
		oMax.addFilter("pid", pid.getValue());
		oMax.addFilter("host", host.getValue());
		idParam.setValue(oMax.SelectMaxLong("id_params")+1);
		host.setValue(host.getValue().toLowerCase());
		this.insertRecord();
	}

	
	public void setStatusToRunning() throws Exception {
		setStop(false);
		setStatus(BizProcessHost.RUNNING);
		this.execProcessUpdate();
	}
	
	public void setStatusToStopped() throws Exception {
		setStop(false);
		setStatus(BizProcessHost.STOPPED);
		this.execProcessUpdate();
	}

	public void stopProcess() throws Exception {
		this.setStop(true);
		this.execProcessUpdate();
	}

}
