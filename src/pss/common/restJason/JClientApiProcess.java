package pss.common.restJason;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class JClientApiProcess {

	public static String GET ="GET";
	public static String POST ="POST";
	public static String PUT ="PUT";
	
	private String sCompany;
	public void setCompany(String v) { this.sCompany = v; }
	public String getCompany() { return this.sCompany ; }

	private String sBody;
	public void setBody(String v) { this.sBody = v; }
	public String getBody() { return this.sBody; }

	private String sType;
	public void setType(String v) { this.sType = v; }
	public String getType() { return this.sType; }


	public JClientApiProcess() {
	}

	private String url;
	private String method;
	private JMap<String, String> parameters = null;
	
	public String getURL() throws Exception {
		return url; 
	}
	public void setURL(String value) throws Exception { 
		this.url=value; 
	}

	public String getMethod() throws Exception {
		return method; 
	}
	public void setMethod(String value) throws Exception { 
		this.method=value; 
	}
	
	public void setParameters(JMap<String, String>  value) throws Exception { 
		this.parameters=value; 
	}
	public JMap<String, String> getParameters() throws Exception {	
		return parameters; 
	}

	public boolean hasParameters() throws Exception {	
		return parameters!=null; 
	}
		
	
	public List<NameValuePair> getListParameters() throws Exception {
		if (!this.hasParameters()) return null;
    List<NameValuePair> list = new ArrayList<NameValuePair>();
    JIterator<String> iter = this.getParameters().getKeyIterator();
    while (iter.hasMoreElements()) {
    	String key = iter.nextElement();
    	String value = this.getParameters().getElement(key);
    	list.add(new BasicNameValuePair(key, value));
    }
    return list;
	}
	
	public URI buildUri() throws Exception {
    URIBuilder builder = new URIBuilder(this.getURL() + this.getMethod());
    if (this.hasParameters()) builder.addParameters(this.getListParameters());
    return builder.build();
	}


	public HttpResponse executeGET() throws Exception {
		return this.execute(GET);
	}
	public HttpResponse executePOST() throws Exception {
		return this.execute(POST);
	}
	public HttpResponse executePUT() throws Exception {
		return this.execute(PUT);
	}
	
	public HttpResponse execute(String type) throws Exception {
		this.setType(type);
		if (type.equals(GET)) {
			return this.execute(new HttpGet(this.buildUri()));
		}
		if (type.equals(POST)) {
			HttpPost httpPost = new HttpPost(this.buildUri());
			httpPost.setEntity(new StringEntity(this.getBody()));
			return this.execute(httpPost);
		}
		if (type.equals(PUT)) {
			HttpPut httpPut = new HttpPut(this.buildUri());
			httpPut.setEntity(new StringEntity(this.getBody()));
			return this.execute(httpPut);
		}
		return null;
	}
	
	private HttpClient makeHttpClient() throws Exception {
		return HttpClientBuilder.create().build();
	}
	
//	private HttpResponse execute(HttpRequestBase request) throws Exception {
//		try {
//			HttpResponse r = this.doExecute(request);
//			this.historyOk();
//			return r;
//		} catch (Exception e) {
//			this.historyError(e);
//			return null;
//		}
//	}
	
	private HttpResponse execute(HttpRequestBase request) throws Exception {
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
    HttpResponse response = this.makeHttpClient().execute(request);
    this.checkStatus(response);
    return response;
	}

	private void checkStatus(HttpResponse response) throws Exception {
    int statusCode = response.getStatusLine().getStatusCode();
    if (statusCode != HttpStatus.SC_OK) {
    	String html = EntityUtils.toString(response.getEntity(), "UTF-8");
    	JExcepcion.SendError(html);
    }
	}
	
	public NodeList getHandlerResult(String searchNode) throws Exception {
		NodeList result = null;
    HttpResponse response = this.executeGET();
    String apiOutput = EntityUtils.toString(response.getEntity());
     
    Document doc = this.convertToXML(apiOutput);
    doc.getDocumentElement().normalize();
    result = doc.getElementsByTagName(searchNode);
    return result;
	}
	
	private Document convertToXML(String xmlString) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
		return doc;
  }
	
//	private boolean save=true;
//	public void setSave(boolean v) {
//		this.save=v;
//	}
//
//
//	private void historyOk() throws Exception {
//		if(!this.save) return;
//		this.doHistory(false, null);
//	}
//
//	private void historyError(Exception e) throws Exception {
//		PssLogger.logError(e);
//		if(!this.save) return;
//		this.doHistory(true, e.getMessage());
//	}
		
	
//	private void doHistory(boolean error, String msg) throws Exception {
//		String status = error ? BizApiRequestHistory.ERROR:BizApiRequestHistory.OK;
//		BizApiRequestHistory h = new BizApiRequestHistory();
//		h.setCompany(this.getCompany());
//		h.setOper(BizApiRequestHistory.SALIENTE);
//		h.setType(this.getType());
//		h.setRequest(this.getBody());
//		h.setUser(BizUsuario.getUsr().GetUsuario());
//		h.setUrl(this.getURL());
//		h.setStatus(status);
//		h.setStatusMessage(msg);
//		h.processInsert();
//	}


}
