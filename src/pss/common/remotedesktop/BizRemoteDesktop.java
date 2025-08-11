package  pss.common.remotedesktop;


import java.io.File;
import java.io.PrintStream;

import org.apache.axis.utils.ByteArrayOutputStream;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JOSCommand;
import pss.core.tools.JTools;

public class BizRemoteDesktop extends JRecord   {



	
	private JLong pTimeout = new JLong();
	private JString pUpload = new JString();
	private JString pwd = new JString();
	private JString pComando = new JString();
	private JString pResultado = new JString();
	private JBoolean pCmd = new JBoolean();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setComando(String zValue) throws Exception {
		pComando.setValue(zValue);
	}
	
	public String getComando() throws Exception {
		return pComando.getValue();
	}

	public void setResultado(String zValue) throws Exception {
		pResultado.setValue(zValue);
	}
	
	public String getResultado() throws Exception {
		return pResultado.getValue();
	}

	public Boolean isCommando() throws Exception {
		return pCmd.isNotNull() && pCmd.getValue();
	}



	/**
	 * Constructor de la Clase
	 */
	public BizRemoteDesktop() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("timeout", pTimeout);
		this.addItem("wd", pwd);
		this.addItem("upload", pUpload);
		this.addItem("comando", pComando);
		this.addItem("resultado", pResultado);
		this.addItem("cmd", pCmd);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "timeout", "timeout", true, false, 18);
		this.addFixedItem(FIELD, "wd", "Workingdirectory", true, false, 4000);
		this.addFixedItem(FIELD, "upload", "upload", true, false, 100000);
		this.addFixedItem(FIELD, "comando", "Comando", true, false, 100000);
		this.addFixedItem(FIELD, "resultado", "resutado", true, false, 100000);
		this.addFixedItem(FIELD, "cmd", "is cmd", true, false, 1);
	}
	

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String upload() throws Exception {
  	if (pUpload.isNull()) return "Archivo indefinido";
  	String arch = pUpload.getValue();
  	arch = arch.replace("%3A", ":");
  	arch = arch.replace("%20", " ");
  	
  	String f = arch.lastIndexOf("/")==-1?arch:arch.substring(arch.lastIndexOf("/")+1);
  	int pos1 = f.lastIndexOf(".");
  	if (pos1!=-1) {
  		int pos2 = f.lastIndexOf(".",pos1-1);
  		if (pos2!=-1)
  			f = f.substring(0, pos2)+f.substring(pos1);
  	}
  	JTools.copyFile(arch, pwd.isNull()?"\\"+f:pwd.getValue()+"/"+f);
  	
  	return "Se copió: "+arch+" a "+(pwd.isNull()?"\\"+f:pwd.getValue());

  }

  public void ejecutar() throws Exception {
  	executeCommand((pCmd.getValue()?(System.getProperty("os.name").startsWith("Windows")?"cmd.exe /C ":"ssh "):"")+getComando());
  }
 
	private void executeCommand(String zCommand) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		
		JOSCommand oCommand = new JOSCommand(zCommand, ps,ps);
		if (pwd.hasValue()) oCommand.setWorkingDirectory(new File(pwd.getValue()));
		oCommand.executeWaiting((int)pTimeout.getValue(), 0);
		pResultado.setValue(os.toString());
		
	}

}
