package pss.www.platform.content.generators.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * Utility that writes different serializer outputs emulating Cocoon's
 * <map:serializer> step. It works entirely in memory and writes the result to
 * the provided {@link HttpServletResponse}.
 */
public class SerializerDispatcher {

	private final TransformerFactory transformerFactory = TransformerFactory.newInstance();

	public void write(HttpServletResponse resp, CocoonSerializer type, HtmlPayload htmlPayload, String textBodyOpt, Document xmlOpt) throws IOException, TransformerException {

		resp.setCharacterEncoding(type.getEncoding());
		resp.setContentType(type.getMimeType());

		byte[] body;
		switch (type) {
		case XML:
			body = serializeXml(xmlOpt, type.getEncoding());
			break;
		case HTML:
		case HTML_DECODED:
		case HTML_NOCOMPRESS:
		case GRAPH:
		case MAP:
			body = toBytes(htmlPayload.getHtml(), type.getEncoding());
			break;
		default:
			body = toBytes(textBodyOpt == null ? "" : textBodyOpt, type.getEncoding());
			break;
		}

		if (type.isGzip()) {
			resp.setHeader("Content-Encoding", "gzip");
			try (GZIPOutputStream gzip = new GZIPOutputStream(resp.getOutputStream())) {
				gzip.write(body);
			}
		} else {
			resp.setContentLength(body.length);
			OutputStream os = resp.getOutputStream();
			os.write(body);
			os.flush();
		}
	}

	private byte[] toBytes(String text, String encoding) {
		return text.getBytes(Charset.forName(encoding));
	}

	private byte[] serializeXml(Document doc, String encoding) throws TransformerException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
		transformer.transform(new DOMSource(doc), new StreamResult(baos));
		return baos.toByteArray();
	}
}
