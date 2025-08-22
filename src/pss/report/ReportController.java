package pss.report;

import java.util.HashMap;
import java.util.Map;

import pss.core.services.records.JFilterMap;
import pss.core.tools.collections.JIterator;

/**
 * Controller-like facade used to render reports without making internal HTTP
 * calls. It relies on {@link InternalReportService} to build the report's
 * HTML and on {@link ReportRenderer} to optionally convert it to PDF.
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

    /**
     * Builds and returns the HTML representation of the given report.
     */
    public String getHtmlView(String reportName, String xslVariant, JFilterMap params) throws Exception {
        HtmlPayload payload = reportService.buildHtml(reportName, xslVariant, buildUserContext(), toMap(params));
        return renderer.renderHtml(payload);
    }

    /**
     * Generates a PDF for the given report and returns it as a byte array.
     */
    public byte[] getPdfBytes(String reportName, String xslVariant, JFilterMap params) throws Exception {
        HtmlPayload payload = reportService.buildHtml(reportName, xslVariant, buildUserContext(), toMap(params));
        return renderer.renderPdf(payload);
    }

    /**
     * Returns the CSV output for the report using a dedicated XSLT variant.
     */
    public String getCsvView(String reportName, JFilterMap params) throws Exception {
        HtmlPayload payload = reportService.buildHtml(reportName, "csv", buildUserContext(), toMap(params));
        return payload.getHtml();
    }

    /**
     * Returns the Excel output for the report using a dedicated XSLT variant.
     */
    public String getExcelView(String reportName, JFilterMap params) throws Exception {
        HtmlPayload payload = reportService.buildHtml(reportName, "excel", buildUserContext(), toMap(params));
        return payload.getHtml();
    }
}
