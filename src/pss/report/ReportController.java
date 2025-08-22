package pss.report;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * Example controller showcasing how to use {@link InternalReportService} and
 * {@link ReportRenderer} to deliver HTML or PDF reports without performing
 * internal HTTP requests.
 */
public class ReportController {

    private final InternalReportService reportService;
    private final ReportRenderer renderer;

    public ReportController(InternalReportService reportService, ReportRenderer renderer) {
        this.reportService = reportService;
        this.renderer = renderer;
    }

    public void getHtmlView(String reportName,
                            String xslVariant,
                            boolean pdf,
                            UserContext user,
                            Map<String, Object> params,
                            HttpServletResponse resp) throws Exception {

        HtmlPayload payload = reportService.buildHtml(reportName, xslVariant, user, params);

        if (!pdf) {
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().write(renderer.renderHtml(payload));
        } else {
            resp.setContentType("application/pdf");
            resp.getOutputStream().write(renderer.renderPdf(payload));
        }
    }
}
