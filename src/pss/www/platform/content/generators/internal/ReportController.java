package pss.www.platform.content.generators.internal;

import java.util.HashMap;
import java.util.Map;

import pss.core.services.records.JFilterMap;
import pss.core.tools.collections.JIterator;

/**
 * Builds the XML for a report and transforms it into HTML using an XSLT
 * template. The resulting HTML together with its base URL is returned inside an
 * {@link HtmlPayload}.
 */
public class ReportController {

	private final InternalReportService reportService;
	private final ReportRenderer renderer;

	public ReportController(InternalReportService reportService, ReportRenderer renderer) {
		this.reportService = reportService;
		this.renderer = renderer;
	}

	private Map<String, Object> toMap(JFilterMap params) throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (params != null && params.getMap() != null) {
			JIterator<String> it = params.getMap().getKeyIterator();
			while (it.hasMoreElements()) {
				String key = it.nextElement();
				map.put(key, params.getMap().getElement(key));
			}
		}
		return map;
	}

	private UserContext buildUserContext() throws Exception {
		return UserContext.fromCurrentUser();
	}

	

}
