package pss.www.platform.content.generators.internal;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

import pss.common.security.BizUsuario;
import pss.core.win.actions.BizAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.content.generators.InMemoryGenerator;
import pss.www.platform.content.generators.JBasicXMLContentGenerator;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.JWebWinMatrixResponsive;

/**
 * Builds the XML for a report and transforms it into HTML using an XSLT
 * template. The resulting HTML together with its base URL is returned inside an
 * {@link HtmlPayload}.
 */
public class InternalReportService {

	private final TransformerFactory factory = TransformerFactory.newInstance();

	public HtmlPayload buildHtml(String reportName, String xslVariant, UserContext userContext, Map<String, Object> businessParams, Map<String, Object> xsltParams, String transformerOverrideOpt) throws Exception {

		Document xml = buildXml(reportName, businessParams, userContext);

		String xslPath;
		if (transformerOverrideOpt != null) {
			if (transformerOverrideOpt.startsWith("/")) {
				xslPath = transformerOverrideOpt;
			} else {
				xslPath = "/xsl/" + transformerOverrideOpt;
			}
		} else {
			xslPath = "/xsl/" + reportName + '_' + xslVariant + ".xsl";
		}
		Source xsl = new StreamSource(getClass().getResourceAsStream(xslPath));

		Transformer transformer = factory.newTransformer(xsl);
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		if (xsltParams != null) {
			for (Map.Entry<String, Object> e : xsltParams.entrySet()) {
				transformer.setParameter(e.getKey(), e.getValue());
			}
		}

		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(xml), new StreamResult(writer));

		String baseUrl = getClass().getResource("/public/").toExternalForm();
		return new HtmlPayload(writer.toString(), baseUrl);
	}

	public HtmlPayload buildHtml(String reportName, String xslVariant, UserContext userContext, Map<String, Object> params) throws Exception {
		return buildHtml(reportName, xslVariant, userContext, params, null, null);
	}

	/**
	 * Builds the report XML by executing the same in-memory pipeline used by the
	 * classic Cocoon based implementation. The method initialises the request and
	 * user context, builds a {@link JXMLContent} backed by a minimal
	 * {@link JBasicXMLContentGenerator} and delegates the XML creation to the
	 * {@link JWebWinMatrixResponsive} view associated to the report.
	 */
	private Document buildXml(String reportName, Map<String, Object> params, UserContext user) throws Exception {

		// ------------------------------------------------------------------
		// 1) Ensure there is a current web request and user information
		// ------------------------------------------------------------------
		JWebRequest req = JWebActionFactory.getCurrentRequest();
		if (req == null) {
			throw new IllegalStateException("No current request available");
		}

		// If the thread local BizUsuario is not set, rebuild it from the
		// certificate contained in the UserContext.
		if (BizUsuario.getUsr() == null && user != null) {
			BizUsuario usr = new BizUsuario();
			usr.unSerialize(BizUsuario.readCertificado(user.getUserId()));
			BizUsuario.SetGlobal(usr);
			// Try to attach the user to the current session via reflection. The
			// API exposes only a protected setter.
			try {
				Method m = req.getSession().getClass().getDeclaredMethod("setUser", BizUsuario.class);
				m.setAccessible(true);
				m.invoke(req.getSession(), usr);
			} catch (Exception ignore) {
				// session user is optional for report rendering
			}
		}

		// ------------------------------------------------------------------
		// 2) Prepare a generator able to build DOM in memory
		// ------------------------------------------------------------------
		InMemoryGenerator generator = new InMemoryGenerator(req);
		JXMLContent zContent = generator.getContent();

		// ------------------------------------------------------------------
		// 3) Instantiate and configure the view responsible for the XML
		// ------------------------------------------------------------------
		BizAction action = new BizAction();
		action.pAccion.setValue(reportName);
		JWebWinMatrixResponsive view = new JWebWinMatrixResponsive(action, true);
		view.setEmbedded(true);

		// ------------------------------------------------------------------
		// 4) Map incoming parameters to the request. When the concrete report
		// expects filters, they are usually delivered via JWebActionData
		// structures. The detailed mapping is application specific, so for
		// now we simply expose the raw parameters to the request bundle.
		// ------------------------------------------------------------------
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, Object> e : params.entrySet()) {
				try {
					req.addDataBundle(e.getKey(), String.valueOf(e.getValue()));
				} catch (Exception ex) {
					// Non critical: continue processing other parameters
				}
			}
		}

		// ------------------------------------------------------------------
		// 5) Execute the view to populate the XML content
		// ------------------------------------------------------------------
		view.componentToXML(zContent);

		// ------------------------------------------------------------------
		// 6) Return the generated DOM document
		// ------------------------------------------------------------------
		return generator.getDocument();
	}

}
