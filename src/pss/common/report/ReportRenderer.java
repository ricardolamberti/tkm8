package pss.common.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

/**
 * Renders HTML or generates PDF output from an {@link HtmlPayload}.
 */
public class ReportRenderer {

    public String renderHtml(HtmlPayload payload) {
        return payload.getHtml();
    }

    public byte[] renderPdf(HtmlPayload payload) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(payload.getHtml(), payload.getBaseUrl());

        renderer.getFontResolver().addFont("/fonts/Roboto-Regular.ttf",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        renderer.layout();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        renderer.createPDF(baos);
        return baos.toByteArray();
    }
}
