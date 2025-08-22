package pss.www.platform.content.generators.internal;

import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.environment.Context;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.Response;
import org.apache.cocoon.environment.http.HttpContext;
import org.apache.cocoon.environment.http.HttpRequest;
import org.apache.cocoon.environment.http.HttpResponse;
import org.apache.cocoon.environment.http.HttpEnvironment;
import org.apache.excalibur.source.Source;
import org.apache.excalibur.source.SourceException;
import org.apache.excalibur.source.SourceResolver;
import org.apache.commons.codec.net.URLCodec;
import org.w3c.dom.Document;

import pss.common.actions.BizAction;
import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.resolvers.JDoInternalRequestResolver;
import pss.www.platform.content.generators.JBasicXMLContentGenerator;
import pss.www.platform.content.generators.JWinListInternalRequestPageGenerator;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.JWebWinMatrixResponsive;

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
                if (req == null) {
                        throw new IllegalStateException("No current request available");
                }

                String serializer = params != null ? String.valueOf(params.get("serializer")) : null;
                boolean full = "htmlfull".equalsIgnoreCase(serializer);

                String arguments;
                if (full) {
                        arguments = "dg_export=serializer=htmlfull,object=" + reportName
                                        + ",range=all,preserve=T,history=N" + "&dg_win_list_nav=name_op1=export,with_preview_op1=N,embedded_op1=false,toolbar_op1=none,&"
                                        + "&dg_client_conf=pw_op1=1000,ph_op1=2500,sw_op1=1000,sh_op1=2500";
                } else {
                        arguments = "dg_export=serializer=html,object=" + reportName
                                        + ",range=all,preserve=T,history=N" + "&dg_win_list_nav=name_op1=export,with_preview_op1=N,embedded_op1=false,toolbar_op1=none,&"
                                        + "&dg_client_conf=pw_op1=1500,ph_op1=2500,sw_op1=1500,sh_op1=2500";
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

                String internalId = java.util.UUID.randomUUID().toString();
                int actionInt = params != null && params.get("action") != null
                                ? Integer.parseInt(String.valueOf(params.get("action"))) : 0;

                String userId = BizUsuario.getUsr() != null ? BizUsuario.getUsr().GetCertificado()
                                : (user != null ? user.getUserId() : "anonymous");
                String extraArguments = JDoInternalRequestResolver.addInternaRequest(internalId, owner, actionInt, userId);

                pushQueryParamsToRequest(req, arguments);
                pushQueryParamsToRequest(req, extraArguments);

                JXMLContent content = createJXMLContentWithSessionContext(owner, req);

                boolean generated = false;
                try {
                        Method m = owner.getClass().getMethod("componentToXML", JXMLContent.class);
                        m.invoke(owner, content);
                        generated = true;
                } catch (NoSuchMethodException ignore) {
                }

                if (!generated) {
                        BizAction act = new BizAction();
                        act.pAccion.setValue(reportName);
                        JWebWinMatrixResponsive view = new JWebWinMatrixResponsive(act, true);
                        trySet(view, "setOwner", JBaseWin.class, owner);
                        trySet(view, "setParent", JBaseWin.class, owner);
                        trySet(view, "setEmbedded", boolean.class, Boolean.TRUE);
                        Method m = view.getClass().getMethod("componentToXML", JXMLContent.class);
                        m.invoke(view, content);
                }

                return extractDocument(content);
        }

        /* ====================== Helpers ====================== */

        private static HttpServletRequest extractHttpServletRequest(JWebRequest req) {
                try {
                        Request cocoonReq = req.getServletRequest();
                        if (cocoonReq instanceof HttpRequest) {
                                java.lang.reflect.Field f = cocoonReq.getClass().getDeclaredField("req");
                                f.setAccessible(true);
                                return (HttpServletRequest) f.get(cocoonReq);
                        }
                } catch (Exception ignore) {
                }
                return null;
        }

        private static HttpServletResponse extractHttpServletResponse(JWebRequest req) {
                try {
                        Request cocoonReq = req.getServletRequest();
                        if (cocoonReq instanceof HttpRequest) {
                                java.lang.reflect.Field f = cocoonReq.getClass().getDeclaredField("env");
                                f.setAccessible(true);
                                Object env = f.get(cocoonReq);
                                java.lang.reflect.Field rf = env.getClass().getDeclaredField("res");
                                rf.setAccessible(true);
                                Object resp = rf.get(env);
                                if (resp instanceof HttpServletResponse) {
                                        return (HttpServletResponse) resp;
                                }
                        }
                } catch (Exception ignore) {
                }
                return null;
        }

        private static ServletContext extractServletContext(HttpServletRequest httpReq) {
                try {
                        return httpReq.getSession().getServletContext();
                } catch (Exception e) {
                        return null;
                }
        }

        private static Map<String, Object> makeCocoonObjectModel(HttpServletRequest httpReq,
                        HttpServletResponse httpRes, ServletContext servletCtx) {
                Map<String, Object> objectModel = new HashMap<>();
                try {
                        HttpContext ctx = new HttpContext(servletCtx);
                        HttpResponse res = new HttpResponse(httpRes);
                        HttpEnvironment env = new HttpEnvironment("", httpReq, httpRes, servletCtx, ctx, "", "");
                        Constructor<HttpRequest> c = HttpRequest.class.getDeclaredConstructor(HttpServletRequest.class, HttpEnvironment.class);
                        c.setAccessible(true);
                        HttpRequest req = c.newInstance(httpReq, env);
                        objectModel.put(ObjectModelHelper.REQUEST_OBJECT, req);
                        objectModel.put(ObjectModelHelper.RESPONSE_OBJECT, res);
                        objectModel.put(ObjectModelHelper.CONTEXT_OBJECT, ctx);
                } catch (Exception e) {
                        throw new RuntimeException("Unable to build Cocoon object model", e);
                }
                return objectModel;
        }

        private static final class NoopResolver implements SourceResolver {
                private final ServletContext servletContext;

                NoopResolver(ServletContext servletContext) {
                        this.servletContext = servletContext;
                }

                @Override
                public Source resolveURI(String location) throws MalformedURLException, IOException, SourceException {
                        return null;
                }

                @Override
                public Source resolveURI(String location, String baseURI, Map parameters)
                                throws MalformedURLException, IOException, SourceException {
                        return null;
                }

                @Override
                public void release(Source source) {
                }
        }

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

        private static JXMLContent createJXMLContentWithSessionContext(JBaseWin owner, JWebRequest req) throws Exception {
                JWinListInternalRequestPageGenerator gen = new JWinListInternalRequestPageGenerator();

                HttpServletRequest httpReq = extractHttpServletRequest(req);
                HttpServletResponse httpRes = extractHttpServletResponse(req);
                ServletContext servletCtx = extractServletContext(httpReq);

                Map<String, Object> objectModel = makeCocoonObjectModel(httpReq, httpRes, servletCtx);
                SourceResolver resolver = new NoopResolver(servletCtx);

                gen.setup(resolver, objectModel, null, Parameters.EMPTY_PARAMETERS);

                callIfPresent(gen, "setRequest", JWebRequest.class, req);
                callIfPresent(gen, "setOwner", JBaseWin.class, owner);

                Constructor<JXMLContent> c = JXMLContent.class.getDeclaredConstructor(JBasicXMLContentGenerator.class);
                c.setAccessible(true);
                JXMLContent z = c.newInstance(gen);

                trySet(z, "setOwner", JBaseWin.class, owner);
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
