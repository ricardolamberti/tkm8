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
import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.resolvers.JDoInternalRequestResolver;
import pss.www.platform.content.generators.JBasicXMLContentGenerator;
import pss.www.platform.content.generators.JWinListInternalRequestPageGenerator;
import pss.www.platform.content.generators.JXMLContent;

/**
 * Builds the XML for a report and transforms it into HTML using an XSLT
 * template. The resulting HTML together with its base URL is returned inside an
 * {@link HtmlPayload}.
 */
public class InternalReportService {

	private final TransformerFactory factory = TransformerFactory.newInstance();

	public HtmlPayload buildHtmlForOwner(JBaseWin owner, String reportName, String xslVariant, UserContext userContext, Map<String, Object> businessParams, Map<String, Object> xsltParams, String transformerOverrideOpt) throws Exception {

		Document xml = buildXmlForOwner(owner, reportName, businessParams, userContext);

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

	@SuppressWarnings("unchecked")
	private Document buildXmlForOwner(JBaseWin owner, String reportName, Map<String, Object> params, UserContext user) throws Exception {

		JWebRequest req = JWebActionFactory.getCurrentRequest();
		if (req == null)
			throw new IllegalStateException("No current request available");
		if (BizUsuario.getUsr() == null && user != null) {
			// BizUsuario.setFromCertificado(user.getUserId());
		}

		final String serializer = "html";
		String arguments;
		if ("htmlfull".equalsIgnoreCase(String.valueOf(params.get("serializer")))) {
			arguments = "dg_export=serializer=htmlfull,object=" + reportName + ",range=all,preserve=T,history=N" + "&dg_win_list_nav=name_op1=export,with_preview_op1=N,embedded_op1=false,toolbar_op1=none,&" + "&dg_client_conf=pw_op1=1000,ph_op1=2500,sw_op1=1000,sh_op1=2500";
		} else {
			arguments = "dg_export=serializer=" + serializer + ",object=" + reportName + ",range=all,preserve=T,history=N" + "&dg_win_list_nav=name_op1=export,with_preview_op1=N,embedded_op1=false,toolbar_op1=none,&" + "&dg_client_conf=pw_op1=1500,ph_op1=2500,sw_op1=1500,sh_op1=2500";
		}

		if (params != null) {
			for (Map.Entry<String, Object> e : params.entrySet()) {
				if (e.getValue() == null)
					continue;
				String k = "dgf_filter_pane_fd-" + e.getKey();
				String v = String.valueOf(e.getValue());
				arguments += "&" + k + "=" + urlEncodeISO(v);
			}
		}

		final String internalId = java.util.UUID.randomUUID().toString();
		final int actionInt = params != null && params.get("action") != null ? Integer.parseInt(String.valueOf(params.get("action"))) : 0;

		String extraArguments = JDoInternalRequestResolver.addInternaRequest(internalId, owner, actionInt, BizUsuario.getUsr() != null ? BizUsuario.getUsr().GetCertificado() : user.getUserId());

		pushQueryParamsToRequest(req, arguments);
		if (extraArguments != null && !extraArguments.isEmpty()) {
			pushQueryParamsToRequest(req, extraArguments);
		}

		JXMLContent zContent = createContentWithOwnerContext(owner, req);

		Object generator = new pss.www.platform.content.generators.JWinListInternalRequestPageGenerator();

		boolean produced = false;
		String[] methodCandidates = { "generate", "generateTo", "produce", "build", "componentToXML" };
		for (String m : methodCandidates) {
			try {
				Method method = generator.getClass().getMethod(m, zContent.getClass());
				method.invoke(generator, zContent);
				produced = true;
				break;
			} catch (NoSuchMethodException ignore) {
			}
		}

		if (!produced) {
			BizAction act = new BizAction();
			act.pAccion.setValue(reportName);
			JWinListInternalRequestPageGenerator view = new JWinListInternalRequestPageGenerator();
			view.generate();

		}

		return extractDocument(zContent);
	}

	/* ====================== Helpers ====================== */

	private static void pushQueryParamsToRequest(JWebRequest req, String qs) {
		if (qs == null || qs.isEmpty())
			return;
		for (String pair : qs.split("&")) {
			if (pair.isEmpty())
				continue;
			int p = pair.indexOf('=');
			String k = (p >= 0) ? pair.substring(0, p) : pair;
			String v = (p >= 0) ? pair.substring(p + 1) : "";
			try {
				// mismo mecanismo que usabas para “data bundles” del request
				req.addDataBundle(k, urlDecodeISO(v));
			} catch (Exception ignore) {
				/* non-critical */ }
		}
	}

	private static String urlEncodeISO(String s) {
		try {
			return new org.apache.commons.codec.net.URLCodec().encode(s, "ISO-8859-1");
		} catch (Exception e) {
			return s;
		}
	}

	private static String urlDecodeISO(String s) {
		try {
			return new org.apache.commons.codec.net.URLCodec().decode(s, "ISO-8859-1");
		} catch (Exception e) {
			return s;
		}
	}

	/**
	 * Crea un JXMLContent “válido” para que .addProviderHistory / HistoryManager y
	 * otros accesos a sesión no fallen durante la generación.
	 */
	private static JXMLContent createContentWithOwnerContext(JBaseWin owner, JWebRequest req) throws Exception {
		JXMLContent z = createJXMLContentWithSessionContext(owner,req);
		try {
			java.lang.reflect.Method setOwner = z.getClass().getMethod("setOwner", JBaseWin.class);
			setOwner.invoke(z, owner);
		} catch (NoSuchMethodException ignore) {
		}
		return z;
	}


private static JXMLContent createJXMLContentWithSessionContext(JBaseWin owner, JWebRequest req) throws Exception {
    // 1) Instanciar el GENERATOR concreto (subclase de JBasicXMLContentGenerator)
    JWinListInternalRequestPageGenerator gen = new JWinListInternalRequestPageGenerator();

    // 2) Inyectar contexto (si existen estos setters en tu base; por eso uso reflexión segura)
    callIfPresent(gen, "setRequest", JWebRequest.class, req);
    callIfPresent(gen, "setOwner",   JBaseWin.class,    owner);
    // si tu generator necesita usuario/sesión/historial, agregá aquí más setters:
    // callIfPresent(gen, "setUser", BizUsuario.class, BizUsuario.getUsr());

    // 3) Construir el JXMLContent con EXACTAMENTE el ctor requerido
    java.lang.reflect.Constructor<JXMLContent> c =
            JXMLContent.class.getDeclaredConstructor(JBasicXMLContentGenerator.class);
    c.setAccessible(true);
    JXMLContent z = c.newInstance(gen);

    // 4) (Opcional) redundar el contexto en el content si tu implementación lo expone
    trySet(z, "setOwner",   JBaseWin.class,    owner);
    trySet(z, "setRequest", JWebRequest.class, req);

    return z;
}


	/* ===== helpers de reflexión ===== */

	private static java.lang.reflect.Method findZeroArgMethod(Class<?> cls, String name) {
		try {
			return cls.getMethod(name);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	private static <T> void callIfPresent(Object target, String method, Class<T> type, T value) {
		try {
			java.lang.reflect.Method m = target.getClass().getMethod(method, type);
			m.setAccessible(true);
			m.invoke(target, value);
		} catch (NoSuchMethodException ignore) {
		} catch (Exception e) {
			// si el método existe pero falla, dejar trazabilidad
			throw new RuntimeException("Error invocando " + method + " en " + target.getClass().getName(), e);
		}
	}

	private static <T> void trySet(Object target, String method, Class<T> type, T value) {
		try {
			java.lang.reflect.Method m = target.getClass().getMethod(method, type);
			m.setAccessible(true);
			m.invoke(target, value);
		} catch (Exception ignore) {
		}
	}

	/**
	 * Extrae un org.w3c.dom.Document desde JXMLContent. Intenta
	 * getDocument()/toDOM()/asDocument(), y si no, parsea el XML string.
	 */
	private static Document extractDocument(JXMLContent zContent) throws Exception {
		// Intentos por reflexión comunes
		String[] getters = { "getDocument", "toDOM", "asDocument" };
		for (String g : getters) {
			try {
				java.lang.reflect.Method m = zContent.getClass().getMethod(g);
				Object dom = m.invoke(zContent);
				if (dom instanceof org.w3c.dom.Document) {
					return (org.w3c.dom.Document) dom;
				}
			} catch (NoSuchMethodException ignore) {
			}
		}
		// Si sólo hay string:
		try {
			java.lang.reflect.Method m = zContent.getClass().getMethod("toString");
			String xml = String.valueOf(m.invoke(zContent));
			javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(new org.xml.sax.InputSource(new java.io.StringReader(xml)));
		} catch (Exception e) {
			throw new IllegalStateException("No pude extraer Document desde JXMLContent", e);
		}
	}

}
