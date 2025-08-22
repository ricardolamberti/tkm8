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

        public HtmlPayload buildHtmlForOwner(
                JBaseWin owner,
                String reportName,
                String xslVariant,
                UserContext userContext,
                Map<String, Object> businessParams,
                Map<String, Object> xsltParams,
                String transformerOverrideOpt) throws Exception {

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

	/**
	 * Builds the report XML by executing the same in-memory pipeline used by the
	 * classic Cocoon based implementation. The method initialises the request and
	 * user context, builds a {@link JXMLContent} backed by a minimal
	 * {@link JBasicXMLContentGenerator} and delegates the XML creation to the
	 * {@link JWebWinMatrixResponsive} view associated to the report.
	 */
	@SuppressWarnings("unchecked")
        private Document buildXml(String reportName, Map<String, Object> params, UserContext user) throws Exception {

	    // ------------------------------------------------------------------
	    // 0) Preconditions: request y usuario actual
	    // ------------------------------------------------------------------
	    JWebRequest req = JWebActionFactory.getCurrentRequest();
	    if (req == null) throw new IllegalStateException("No current request available");
	    if (BizUsuario.getUsr() == null && user != null) {
	        // Si tu framework lo permite, setea el usuario de sesión acá
	        // BizUsuario.setFromCertificado(user.getUserId()); // <-- si existe
	    }

	    // ------------------------------------------------------------------
	    // 1) Simular JDoInternalRequestResolver: armar arguments y registrar
	    // ------------------------------------------------------------------
	    final String serializer = "html"; // o tomalo de params.get("serializer")
	    String arguments;
	    if ("htmlfull".equalsIgnoreCase(String.valueOf(params.get("serializer")))) {
	        arguments = "dg_export=serializer=htmlfull,object=" + reportName + ",range=all,preserve=T,history=N"
	                  + "&dg_win_list_nav=name_op1=export,with_preview_op1=N,embedded_op1=false,toolbar_op1=none,&"
	                  + "&dg_client_conf=pw_op1=1000,ph_op1=2500,sw_op1=1000,sh_op1=2500";
	    } else {
	        arguments = "dg_export=serializer=" + serializer + ",object=" + reportName + ",range=all,preserve=T,history=N"
	                  + "&dg_win_list_nav=name_op1=export,with_preview_op1=N,embedded_op1=false,toolbar_op1=none,&"
	                  + "&dg_client_conf=pw_op1=1500,ph_op1=2500,sw_op1=1500,sh_op1=2500";
	    }

	    // Filtros como hacía tu viejo getHtmlView (dgf_filter_pane_fd-<key>=<val>)
	    if (params != null) {
	        for (Map.Entry<String, Object> e : params.entrySet()) {
	            if (e.getValue() == null) continue;
	            String k = "dgf_filter_pane_fd-" + e.getKey();
	            String v = String.valueOf(e.getValue());
	            arguments += "&" + k + "=" + urlEncodeISO(v); // mismo encoding que usabas
	        }
	    }

	    // Registrar internal request (esto te devuelve __dictionary y __requestid, etc.)
	    final String internalId = java.util.UUID.randomUUID().toString();
	    // action: si lo necesitás, obtenelo desde params ("action") o usa 0
	    final int actionInt = params != null && params.get("action") != null
	            ? Integer.parseInt(String.valueOf(params.get("action"))) : 0;

	    String extraArguments = JDoInternalRequestResolver.addInternaRequest(
	            internalId,
	            this,                                // el "caller" igual que antes
	            actionInt,
	            BizUsuario.getUsr() != null ? BizUsuario.getUsr().GetCertificado() : user.getUserId()
	    );

	    // Inyectar TODOS los parámetros (arguments + extraArguments) en el request
	    pushQueryParamsToRequest(req, arguments);
	    if (extraArguments != null && !extraArguments.isEmpty()) {
	        pushQueryParamsToRequest(req, extraArguments);
	    }

	    // ------------------------------------------------------------------
	    // 2) Ejecutar JWinListInternalRequestPageGenerator in-process
	    // ------------------------------------------------------------------
	    // Armá el content con el “generator context” apropiado
	    JXMLContent zContent = createJXMLContentWithSessionContext(req);

	    // Instanciar el generator real
	    Object generator = new pss.www.platform.content.generators.JWinListInternalRequestPageGenerator();

	    // Intentar llamar por reflexión al método que produzca el XML dentro de zContent
	    boolean produced = false;
	    // Candidatos comunes: generate(JXMLContent), generateTo(JXMLContent),
	    // produce(JXMLContent), build(JXMLContent), componentToXML(JXMLContent)
	    String[] methodCandidates = {
	            "generate", "generateTo", "produce", "build", "componentToXML"
	    };
	    for (String m : methodCandidates) {
	        try {
	            java.lang.reflect.Method method = generator.getClass().getMethod(m, zContent.getClass());
	            method.invoke(generator, zContent);
	            produced = true;
	            break;
	        } catch (NoSuchMethodException ignore) {
	            // probar siguiente
	        }
	    }

	    // Fallback: hay proyectos donde el generator sólo prepara contexto
	    // y la vista (JWebWinMatrixResponsive) es quien emite el XML:
	    if (!produced) {
	        BizAction act = new BizAction();
	        act.pAccion.setValue(reportName);
	        JWebWinMatrixResponsive view = new JWebWinMatrixResponsive(act, true);
	        view.setEmbedded(true);
	        view.componentToXML(zContent);
	    }

	    // ------------------------------------------------------------------
	    // 3) Entregar el Document resultante
	    // ------------------------------------------------------------------
            return extractDocument(zContent);
        }

        @SuppressWarnings("unchecked")
        private Document buildXmlForOwner(JBaseWin owner, String reportName, Map<String, Object> params, UserContext user) throws Exception {

            JWebRequest req = JWebActionFactory.getCurrentRequest();
            if (req == null) throw new IllegalStateException("No current request available");
            if (BizUsuario.getUsr() == null && user != null) {
                // BizUsuario.setFromCertificado(user.getUserId());
            }

            final String serializer = "html";
            String arguments;
            if ("htmlfull".equalsIgnoreCase(String.valueOf(params.get("serializer")))) {
                arguments = "dg_export=serializer=htmlfull,object=" + reportName + ",range=all,preserve=T,history=N"
                          + "&dg_win_list_nav=name_op1=export,with_preview_op1=N,embedded_op1=false,toolbar_op1=none,&"
                          + "&dg_client_conf=pw_op1=1000,ph_op1=2500,sw_op1=1000,sh_op1=2500";
            } else {
                arguments = "dg_export=serializer=" + serializer + ",object=" + reportName + ",range=all,preserve=T,history=N"
                          + "&dg_win_list_nav=name_op1=export,with_preview_op1=N,embedded_op1=false,toolbar_op1=none,&"
                          + "&dg_client_conf=pw_op1=1500,ph_op1=2500,sw_op1=1500,sh_op1=2500";
            }

            if (params != null) {
                for (Map.Entry<String, Object> e : params.entrySet()) {
                    if (e.getValue() == null) continue;
                    String k = "dgf_filter_pane_fd-" + e.getKey();
                    String v = String.valueOf(e.getValue());
                    arguments += "&" + k + "=" + urlEncodeISO(v);
                }
            }

            final String internalId = java.util.UUID.randomUUID().toString();
            final int actionInt = params != null && params.get("action") != null
                    ? Integer.parseInt(String.valueOf(params.get("action"))) : 0;

            String extraArguments = JDoInternalRequestResolver.addInternaRequest(
                    internalId,
                    owner,
                    actionInt,
                    BizUsuario.getUsr() != null ? BizUsuario.getUsr().GetCertificado() : user.getUserId()
            );

            pushQueryParamsToRequest(req, arguments);
            if (extraArguments != null && !extraArguments.isEmpty()) {
                pushQueryParamsToRequest(req, extraArguments);
            }

            JXMLContent zContent = createContentWithOwnerContext(owner, req);

            Object generator = new pss.www.platform.content.generators.JWinListInternalRequestPageGenerator();

            boolean produced = false;
            String[] methodCandidates = {
                    "generate", "generateTo", "produce", "build", "componentToXML"
            };
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
                JWebWinMatrixResponsive view = new JWebWinMatrixResponsive(act, true);
                view.setEmbedded(true);
                try {
                    Method setOwner = view.getClass().getMethod("setOwner", JBaseWin.class);
                    setOwner.invoke(view, owner);
                } catch (NoSuchMethodException ignore) {
                }
                view.componentToXML(zContent);
            }

            return extractDocument(zContent);
        }

	/* ====================== Helpers ====================== */

	private static void pushQueryParamsToRequest(JWebRequest req, String qs) {
	    if (qs == null || qs.isEmpty()) return;
	    for (String pair : qs.split("&")) {
	        if (pair.isEmpty()) continue;
	        int p = pair.indexOf('=');
	        String k = (p >= 0) ? pair.substring(0, p) : pair;
	        String v = (p >= 0) ? pair.substring(p + 1) : "";
	        try {
	            // mismo mecanismo que usabas para “data bundles” del request
	            req.addDataBundle(k, urlDecodeISO(v));
	        } catch (Exception ignore) { /* non-critical */ }
	    }
	}

	private static String urlEncodeISO(String s) {
	    try { return new org.apache.commons.codec.net.URLCodec().encode(s, "ISO-8859-1"); }
	    catch (Exception e) { return s; }
	}

        private static String urlDecodeISO(String s) {
            try { return new org.apache.commons.codec.net.URLCodec().decode(s, "ISO-8859-1"); }
            catch (Exception e) { return s; }
        }

        /**
         * Crea un JXMLContent “válido” para que .addProviderHistory / HistoryManager
         * y otros accesos a sesión no fallen durante la generación.
         */
        private static JXMLContent createContentWithOwnerContext(JBaseWin owner, JWebRequest req) throws Exception {
            JXMLContent z = createJXMLContentWithSessionContext(req);
            try {
                java.lang.reflect.Method setOwner = z.getClass().getMethod("setOwner", JBaseWin.class);
                setOwner.invoke(z, owner);
            } catch (NoSuchMethodException ignore) {
            }
            return z;
        }

        /**
         * Crea un JXMLContent “válido” para que .addProviderHistory / HistoryManager
         * y otros accesos a sesión no fallen durante la generación.
         */
        private static JXMLContent createJXMLContentWithSessionContext(JWebRequest req) throws Exception {
            // Si tu proyecto tiene una factoría/constructor específico, usalo acá.
            // Muchas bases tienen JXMLContent(JWebRequest) o con un “generator ctx”.
            try {
	        // Opción 1: constructor JXMLContent(JWebRequest)
	        java.lang.reflect.Constructor<JXMLContent> c1 =
	                JXMLContent.class.getConstructor(JWebRequest.class);
	        return c1.newInstance(req);
	    } catch (NoSuchMethodException ignore) {
	        // Opción 2: constructor por defecto + setGeneratorContext(...)
	        JXMLContent z = JXMLContent.class.newInstance();
	        try {
	            java.lang.reflect.Method setGen =
	                    JXMLContent.class.getMethod("setGeneratorContext", JWebRequest.class);
	            setGen.invoke(z, req);
	        } catch (NoSuchMethodException ignored) {
	            // Opción 3: si existe setRequest(...)
	            try {
	                java.lang.reflect.Method setReq =
	                        JXMLContent.class.getMethod("setRequest", JWebRequest.class);
	                setReq.invoke(z, req);
	            } catch (NoSuchMethodException ignored2) {
	                // última opción: simplemente devolver el objeto y confiar
	            }
	        }
	        return z;
	    }
	}

	/**
	 * Extrae un org.w3c.dom.Document desde JXMLContent.
	 * Intenta getDocument()/toDOM()/asDocument(), y si no, parsea el XML string.
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
	        } catch (NoSuchMethodException ignore) {}
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
