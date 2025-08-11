package pss.www.platform.content.generators;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Random;

import org.apache.cocoon.environment.Response;
import org.apache.cocoon.servlet.multipart.Part;
import org.apache.cocoon.servlet.multipart.PartOnDisk;
import org.apache.cocoon.servlet.multipart.RejectedPart;

import pss.core.data.BizPssConfig;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.www.ui.controller.JWebClientConfiguration;

public class JFTPPageGenerator extends JXMLComponentGenerator {

	@Override
	protected String getBaseContentName() {
		return "upload";
	}

	static long count = (new Random()).nextLong();

	String getNewFilename(String filename) throws Exception {
		String fn = BizPssConfig.getPssConfig().getFTPFiles();
		File f = new File(filename);
		fn += "/" + f.getName();

		try {
			String filenew = fn;
			if (fn.endsWith(".new"))
				filenew = filenew.substring(0, filenew.indexOf(".new"));
			else
				if (fn.endsWith(".new.ok"))
					filenew = filenew.substring(0, filenew.indexOf(".new.ok"));

			File oDestiny = new File(filenew);
			while (oDestiny.exists()) {
				String number = filenew.substring(filenew.length() - 6, filenew.length() - 4);
				long num = Long.parseLong(number);
				num++;
				filenew = filenew.substring(0, filenew.length() - 6) + JTools.LPad(num + "", 2, "0") + filenew.substring(filenew.length() - 4, filenew.length());
				oDestiny = new File(filenew);
			}
			if (fn.endsWith(".new"))
				fn = filenew + ".new";
			else
				if (fn.endsWith(".new.ok"))
					fn = filenew + ".new.ok";
				else
			    fn = filenew;
		} catch (Exception eee) {
		}

		return fn;
	}

	public Serializable getKey() {
		if (this.getSession() == null)
			return null;

		return this.getSession().getId() + "_" + (count++);
	}

	@Override
	protected void doGenerate() throws Exception {
		String filename = "";
		long origLastModified=new Date().getTime();
		String field = (String) getRequest().get("field");
		String progreso = (String) getRequest().get("progreso");
		Part filePart = (Part) getRequest().get("uploaded_file");

		if (filePart != null && !(filePart instanceof RejectedPart)) {
			File file = ((PartOnDisk) filePart).getFile();
			// PssLogger.logDebug("Uploaded file = " + file.getCanonicalPath());
			String realFilename = getNewFilename(file.getCanonicalPath());
			String tempName = realFilename + ".temp";
			boolean okfile = realFilename.endsWith(".ok");
			if (okfile == false || realFilename.endsWith(".new") == false) {
				if(okfile==false)
				  ((PartOnDisk) filePart).copyToFile(tempName);
			} 
			
			if (okfile ) {
				FileInputStream is = new FileInputStream(file);
				byte[] buf = new byte[500];
				if (is.read(buf)>10 ){
					try {
					origLastModified = Long.parseLong( new String(buf));
					} catch (Exception eee) {}
				}
			  is.close();
				file.delete();
				realFilename = realFilename.substring(0, realFilename.indexOf(".new.ok"));
				tempName = realFilename + ".new.temp";
				File newfile = new File(tempName);
				if (newfile.setLastModified(origLastModified)  ) {
					PssLogger.logDebug("Fecha cambiada: " + realFilename);
				}

			}

			if (okfile || realFilename.endsWith(".echo") || realFilename.endsWith(".new") == false) {
				Path movefrom = FileSystems.getDefault().getPath(tempName);
				Path target = FileSystems.getDefault().getPath(realFilename);
				Files.move(movefrom, target, StandardCopyOption.REPLACE_EXISTING);
			}

			// Usar archivo y borrarlo
			filename = realFilename.replaceAll("\\\\", "/");
		}

		this.startNode("message");
		this.setAttribute("type", "upload");
		if (field != null && !field.equals(""))
			this.setAttribute("field", field);
		if (progreso != null && !progreso.equals(""))
			this.setAttribute("progreso", progreso);
		this.addTextNode("text", filename);
		this.endNode("message");
	}

	@Override
	protected void setResponseHeaders(Response zResponse) throws Exception {
		if (JWebClientConfiguration.getCurrent().acceptsGZip()) {
			zResponse.addHeader("Content-Encoding", "gzip");
		}
		super.setResponseHeaders(zResponse);

	}

}
