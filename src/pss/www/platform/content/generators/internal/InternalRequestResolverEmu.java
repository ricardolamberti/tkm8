package pss.www.platform.content.generators.internal;

import java.util.HashMap;
import java.util.Map;

/**
 * Replaces the old Cocoon pipeline composed by the act/transform/select steps
 * of the sitemap. It resolves reports entirely in memory.
 */
public class InternalRequestResolverEmu {

	private final InternalReportService reportService = new InternalReportService();
	private final ReportRenderer renderer = new ReportRenderer();

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
