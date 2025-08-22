package pss.www.platform.content.generators.internal;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Replaces the old Cocoon pipeline composed by the act/transform/select steps
 * of the sitemap. It resolves reports entirely in memory.
 */
public class InternalRequestResolverEmu {

	private final InternalReportService reportService = new InternalReportService();
	private final ReportRenderer renderer = new ReportRenderer();

	public HtmlPayload resolveHtml(String reportName, String serializerName, String transformerOpt, String basedir, String dictionary, String requestid, UserContext user, Map<String, Object> filters) throws Exception {

		// Validate that the serializer exists even if we only use HTML-like ones
		CocoonSerializer.from(serializerName);

		String xslVariant = "html";
		if ("htmlfull".equalsIgnoreCase(serializerName)) {
			xslVariant = "htmlfull";
		} else if ("graph".equalsIgnoreCase(serializerName)) {
			xslVariant = "graph";
		} else if ("map".equalsIgnoreCase(serializerName)) {
			xslVariant = "map";
		}

		Map<String, Object> xsltParams = buildXsltParams(basedir, dictionary, requestid);
		return reportService.buildHtml(reportName, xslVariant, user, filters, xsltParams, transformerOpt);
	}

	public byte[] resolvePdf(String reportName, String serializerName, String transformerOpt, String basedir, String dictionary, String requestid, UserContext user, Map<String, Object> filters) throws Exception {
		String htmlVariant = "htmlfull".equalsIgnoreCase(serializerName) ? "htmlfull" : "html";
		HtmlPayload payload = resolveHtml(reportName, htmlVariant, transformerOpt, basedir, dictionary, requestid, user, filters);
		return renderer.renderPdf(payload);
	}

	public byte[] resolveExcel(String reportName, String transformerOpt, String basedir, String dictionary, String requestid, UserContext user, Map<String, Object> filters) throws Exception {
		Map<String, Object> xsltParams = buildXsltParams(basedir, dictionary, requestid);
		HtmlPayload payload = reportService.buildHtml(reportName, "excel", user, filters, xsltParams, transformerOpt);
		return payload.getHtml().getBytes(StandardCharsets.UTF_8);
	}

	public byte[] resolveCsv(String reportName, String transformerOpt, String basedir, String dictionary, String requestid, UserContext user, Map<String, Object> filters) throws Exception {
		Map<String, Object> xsltParams = buildXsltParams(basedir, dictionary, requestid);
		HtmlPayload payload = reportService.buildHtml(reportName, "csv", user, filters, xsltParams, transformerOpt);
		return payload.getHtml().getBytes(StandardCharsets.UTF_8);
	}

	public String resolveJson(String reportName, String transformerOpt, String basedir, String dictionary, String requestid, UserContext user, Map<String, Object> filters) throws Exception {
		Map<String, Object> xsltParams = buildXsltParams(basedir, dictionary, requestid);
		HtmlPayload payload = reportService.buildHtml(reportName, "json", user, filters, xsltParams, transformerOpt);
		return payload.getHtml();
	}

	private Map<String, Object> buildXsltParams(String basedir, String dictionary, String requestid) {
		Map<String, Object> xsltParams = new HashMap<>();
		if (basedir != null) {
			xsltParams.put("basedir", basedir);
		}
		if (dictionary != null) {
			xsltParams.put("dictionary", dictionary);
		}
		if (requestid != null) {
			xsltParams.put("requestid", requestid);
		}
		return xsltParams;
	}
}
