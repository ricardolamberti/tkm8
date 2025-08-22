package pss.report;

/**
 * Simple value object containing the rendered HTML and its base URL for
 * resource resolution (images, CSS, fonts).
 */
public final class HtmlPayload {
    private final String html;
    private final String baseUrl;

    public HtmlPayload(String html, String baseUrl) {
        this.html = html;
        this.baseUrl = baseUrl;
    }

    public String getHtml() {
        return html;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
