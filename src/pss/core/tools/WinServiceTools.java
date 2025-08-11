package pss.core.tools;

import java.io.File;

import pss.JPath;

public class WinServiceTools {

	String servicename="";
	String className="";
	String stopClassName="";

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setStopClassName(String className) {
		this.stopClassName = className;
	}

	/**
	 * @return the className
	 */
	public String getStopClassName() {
		return this.stopClassName;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	
	public WinServiceTools() {
	}

	static String sJvmDllFile;

	public boolean ifJvmDllFileExits(String zFile) throws Exception {
		File oFile = new File(zFile);
		if (oFile.exists()) {
			sJvmDllFile = "\"" + zFile + "\"";
			return true;
		} else
			return false;
	}

	public String getJvmDllPath() throws Exception {
		if (sJvmDllFile != null)
			return sJvmDllFile;
		String sJavaBin = System.getProperty("java.home") + "/bin";
		String sFile = "";

		sFile = "/client/jvm.dll";
		if (ifJvmDllFileExits(sJavaBin + sFile))
			return sJvmDllFile;

		sFile = "/server/jvm.dll";
		if (ifJvmDllFileExits(sJavaBin + sFile))
			return sJvmDllFile;

		sFile = "/classic/jvm.dll";
		if (ifJvmDllFileExits(sJavaBin + sFile))
			return sJvmDllFile;

		sFile = "/hotspot/jvm.dll";
		if (ifJvmDllFileExits(sJavaBin + sFile))
			return sJvmDllFile;

		return sJvmDllFile;
	}

	private String getJavaServiceProgram() throws Exception {
		String sPssRootPath = JPath.PssMainPath().replaceFirst("file:","");
		String sPath = sPssRootPath + "../../bin/";
		return sPath + "javaservice";
	}

	public void setServiceName(String name) {
		this.servicename = name;
	}

	public String getServiceName() {
		return "\""+this.servicename+"\"";
	}

	public void install() throws Exception {
		String sClassPath = System.getProperty("java.class.path", ".");

		String sComando = getJavaServiceProgram() + " -install " + getServiceName() + " " + getJvmDllPath();
		sComando +=	" -Djava.class.path=\"" + sClassPath + "\""; // classpath
		// sComando +=	" -Xms128M -Xmx512M -Dcom.sun.management.jmxremote.port=9200 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"; 
		sComando +=	" -Xms128M -Xmx2048M"; 
		sComando +=	" -start " + this.getClassName(); // clase arrancar el servicio
		if (this.getStopClassName().isEmpty() == false)
			sComando +=	" -stop " + this.getStopClassName(); // clase Java para bajar el servicio
		// sComando +=	" -out \"" + JPath.PssPathData() + "/serviceerror.out\""; 
		sComando +=	" -err \"" + JPath.PssPathData() + "/serviceerror.err\""; 

		// execute the command with feedback
		executeCommand(sComando, this.getServiceName()+ " OK", true);
	}

	private void executeCommand(String zCommand, String zText, boolean zNoMsg) throws Exception {
		JOSCommand oCommand = new JOSCommand(zCommand, System.out, System.out);
		oCommand.executeWaiting(60000, 1);

		if (zNoMsg == false) {
			if (oCommand.hasTerminatedNormally()) {
			} else if (oCommand.hasTerminatedWithError()) {
				JExcepcion.SendError("command aborted by error.");
			} else if (oCommand.hasBeenInterrupted()) {
				JExcepcion.SendError("command aborted by user.");
			} else if (oCommand.hasTimedOut()) {
				JExcepcion.SendError("command aborted by timeout.");
			}
		}
	}

}
