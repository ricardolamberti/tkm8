package pss.www.platform.content.generators;

import java.util.ArrayDeque;
import java.util.Deque;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import pss.www.platform.actions.JWebRequest;

/**
 * Minimal in-memory {@link JBasicXMLContentGenerator} implementation able to
 * collect the XML produced by web views into a DOM {@link Document}.
 */
public class InMemoryGenerator extends JBasicXMLContentGenerator {

	private final Document document;
	private final Deque<Element> stack = new ArrayDeque<>();

	public InMemoryGenerator(JWebRequest request) throws Exception {
		this.oRequest = request;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		this.document = db.newDocument();
		this.oXMLContent = new JXMLContent(this);
	}

	public Document getDocument() {
		return document;
	}

	public JXMLContent getContent() {
		return this.oXMLContent;
	}

	@Override
	protected void doGenerate() throws Exception {
		// Generation is driven externally through JXMLContent
	}

	@Override
	protected String getBaseContentName() {
		return "internal-report";
	}

	@Override
	protected void startNode(String name) throws SAXException {
		Element e = document.createElement(name);
		if (stack.isEmpty()) {
			document.appendChild(e);
		} else {
			stack.peek().appendChild(e);
		}
		stack.push(e);
	}

	@Override
	protected void endNode(String name) throws SAXException {
		if (!stack.isEmpty()) {
			stack.pop();
		}
	}

	@Override
	protected void setAttribute(String attr, String value) {
		if (!stack.isEmpty()) {
			stack.peek().setAttribute(attr, value);
		}
	}

	@Override
	protected void setAttribute(String attr, boolean value) {
		setAttribute(attr, String.valueOf(value));
	}

	@Override
	protected void setAttribute(String attr, int value) {
		setAttribute(attr, String.valueOf(value));
	}

	@Override
	protected void setAttribute(String attr, long value) {
		setAttribute(attr, String.valueOf(value));
	}

	@Override
	protected void setAttribute(String attr, double value) {
		setAttribute(attr, String.valueOf(value));
	}

	@Override
	protected void setAttribute(String attr, float value) {
		setAttribute(attr, String.valueOf(value));
	}

	@Override
	protected void setNodeText(String value) throws SAXException {
		if (!stack.isEmpty() && value != null) {
			stack.peek().appendChild(document.createTextNode(value));
		}
	}

	@Override
	protected void addTextNode(String node, String value) throws SAXException {
		startNode(node);
		try {
			setNodeText(value);
		} finally {
			endNode(node);
		}
	}

	@Override
	protected void addTextNodeNLS(String node, String value) throws Exception {
		addTextNode(node, value);
	}
}