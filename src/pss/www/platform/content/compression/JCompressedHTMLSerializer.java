/*
 * Created on 27-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.compression;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.avalon.framework.CascadingRuntimeException;
import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Response;
import org.apache.cocoon.environment.SourceResolver;
import org.apache.cocoon.serialization.HTMLSerializer;
import org.xml.sax.SAXException;

import pss.www.ui.controller.JWebClientConfiguration;


public class JCompressedHTMLSerializer extends HTMLSerializer {

  public JCompressedHTMLSerializer() {
  }
  
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		 try {
			if (JWebClientConfiguration.getCurrent().acceptsGZip()) {
			   Response zResponse=ObjectModelHelper.getResponse(objectModel);
			   zResponse.addHeader("Content-Encoding", "gzip"); // RJL no llega nunca aqui, por eso esta esparcido por todos lados :(
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



  @Override
	public void setOutputStream(OutputStream out) {
    try {
      if (!JWebClientConfiguration.getCurrent().acceptsGZip()) {
        super.setOutputStream(out);
        return;
      }
      JSerializerGZipOutputStream oGZipStream = new JSerializerGZipOutputStream(out);
      super.setOutputStream(oGZipStream);
    } catch (Exception e) {
      throw new CascadingRuntimeException("JCompressedHTMLSerializer.setOutputStream()", e);
    }
  }


}
