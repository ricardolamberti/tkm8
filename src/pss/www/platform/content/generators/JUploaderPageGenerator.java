package pss.www.platform.content.generators;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.cocoon.environment.Response;
import org.apache.cocoon.servlet.multipart.Part;
import org.apache.cocoon.servlet.multipart.PartOnDisk;
import org.apache.cocoon.servlet.multipart.RejectedPart;

import pss.www.ui.controller.JWebClientConfiguration;


public class JUploaderPageGenerator extends JXMLComponentGenerator {


		@Override
		protected String getBaseContentName() {
			return "upload";
		}

		static long count =(new Random()).nextLong();
		String getNewFilename(String filename) {
			String fn = filename;
			String ext = "";
			int posType = filename.lastIndexOf(".");
			if (posType!=-1) {
				fn = filename.substring(0,posType);
				ext = filename.substring(posType+1);
			}
			String fne = fn+".";
			fne+= ""+(count++)+"_";
			Calendar c = new GregorianCalendar();
			c.setTime(new Date());
			fne+= c.get(Calendar.MILLISECOND);
			fne+="."+ext;
			if (count>10000000) count =0;
			return fne;
		}
	
		
  	public Serializable getKey() {
			if (this.getSession()==null) return null;

			return this.getSession().getId()+"_"+(count++);
		}
		@Override
		protected void doGenerate() throws Exception {
			String filename = "";
			String field = (String)getRequest().get("field");
			String progreso = (String)getRequest().get("progreso");
      Part filePart = (Part) getRequest().get("uploaded_file");

      if (filePart!=null && !(filePart  instanceof RejectedPart)) {
	      File file = ((PartOnDisk)filePart).getFile();
//	      PssLogger.logDebug("Uploaded file = " + file.getCanonicalPath());
	      String realFilename = getNewFilename(file.getCanonicalPath());
	      ((PartOnDisk)filePart).copyToFile(realFilename);	
	           // Usar archivo y borrarlo      
	      filename = realFilename.replaceAll("\\\\", "/");
      }
      
      this.startNode("message");
			this.setAttribute("type", "upload");
      if (field!=null && !field.equals("")) this.setAttribute("field", field);
      if (progreso!=null && !progreso.equals("")) this.setAttribute("progreso", progreso);
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
