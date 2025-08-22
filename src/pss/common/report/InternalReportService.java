package pss.common.report;

import java.io.StringWriter;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

/**
 * Builds the XML for a report and transforms it into HTML using an XSLT
 * template. The resulting HTML together with its base URL is returned inside an
 * {@link HtmlPayload}.
 */
public class InternalReportService {

    private final TransformerFactory factory = TransformerFactory.newInstance();

    public HtmlPayload buildHtml(String reportName,
                                 String xslVariant,
                                 UserContext userContext,
                                 Map<String, Object> params) throws Exception {

        Document xml = buildXml(reportName, params, userContext);

        String xslPath = "/xsl/" + reportName + '_' + xslVariant + ".xsl";
        Source xsl = new StreamSource(getClass().getResourceAsStream(xslPath));

        Transformer transformer = factory.newTransformer(xsl);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(xml), new StreamResult(writer));

        String baseUrl = getClass().getResource("/public/").toExternalForm();
        return new HtmlPayload(writer.toString(), baseUrl);
    }

    /**
     * Builds the report XML in memory. The actual implementation depends on the
     * application's data model and is therefore represented here as a stub.
     */
    private Document buildXml(String reportName,
                              Map<String, Object> params,
                              UserContext  user) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        doc.appendChild(doc.createElement("report"));
        return doc;
    }
}
