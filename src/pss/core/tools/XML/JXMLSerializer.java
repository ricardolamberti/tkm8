package pss.core.tools.XML;

import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import pss.core.tools.JExcepcion;

public class JXMLSerializer {

  private Transformer serializer = null;
  @SuppressWarnings("unused")
	private Writer output = null;
  private String xml = "";
  private boolean canonical;
  @SuppressWarnings("unused")
	private String encoding;

  public JXMLSerializer() throws Exception {}

  /**
   * This is the current method to use
   * @param zWriter
   * @param zNode
   * @throws Exception
   */
  public void serialize(Writer zWriter, Element zNode) throws Exception {
    this.serialize(zWriter, (Node)zNode);
  }


  public void serialize(Writer zWriter, Node zNode) throws Exception {
    System.setProperty(
      "javax.xml.transform.TransformerFactory",
      "org.apache.xalan.processor.TransformerFactoryImpl"
    );
    this.serializer = TransformerFactory.newInstance().newTransformer();
    this.serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    this.serializer.setOutputProperty(OutputKeys.ENCODING, "yes");
    this.serializer.setOutputProperty(OutputKeys.INDENT, "yes");
    this.serializer.transform(new DOMSource(zNode),new StreamResult(zWriter));
  }



  public String toString(Element zNode) throws Exception {
    if (!(zNode instanceof Element)) JExcepcion.SendError("La implementación de xml no es la deseada.");
//    return ((Element)zNode).toString();
		Document document = zNode.getOwnerDocument();

		DOMImplementationLS domImplementation = (DOMImplementationLS) document.getImplementation();
	  LSSerializer lsSerializer = domImplementation.createLSSerializer();
	  return lsSerializer.writeToString(zNode);   

 }


  public void getNodeAsString(Node node) throws Exception {
    if (node == null) {
      return;
    }

    int type = node.getNodeType();
    switch (type) {
      // print document
      case Node.DOCUMENT_NODE :
        {
          /*if (!canonical) {
            out.println("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>");
          }*/
          getNodeAsString(((Document) node).getDocumentElement());
          //out.flush();
          break;
        }

        // print element with attributes
      case Node.ELEMENT_NODE :
        {
          //out.print('<');
          xml += '<';
          xml += node.getNodeName();
          Attr attrs[] = sortAttributes(node.getAttributes());
          for (int i = 0; i < attrs.length; i++) {
            Attr attr = attrs[i];
            xml += ' ';
            xml += attr.getNodeName();
            xml += "=\"";
            xml += normalize(attr.getNodeValue());
            xml += '"';
          }
          xml += '>';
          NodeList children = node.getChildNodes();
          if (children != null) {
            int len = children.getLength();
            for (int i = 0; i < len; i++) {
              getNodeAsString(children.item(i));
            }
          }
          break;
        }

        // handle entity reference nodes
      case Node.ENTITY_REFERENCE_NODE :
        {
          if (canonical) {
            NodeList children = node.getChildNodes();
            if (children != null) {
              int len = children.getLength();
              for (int i = 0; i < len; i++) {
                getNodeAsString(children.item(i));
              }
            }
          } else {
            xml += '&';
            xml += node.getNodeName();
            xml += ';';
          }
          break;
        }

        // print cdata sections
      case Node.CDATA_SECTION_NODE :
        {
          if (canonical) {
            xml += normalize(node.getNodeValue());
          } else {
            xml += "<![CDATA[";
            xml += node.getNodeValue();
            xml += "]]>";
          }
          break;
        }

        // print text
      case Node.TEXT_NODE :
        {
          xml += normalize(node.getNodeValue());
          break;
        }

        // print processing instruction
      case Node.PROCESSING_INSTRUCTION_NODE :
        {
          xml += "<?";
          xml += node.getNodeName();
          String data = node.getNodeValue();
          if (data != null && data.length() > 0) {
            xml += ' ';
            xml += data;
          }
          xml += "?>";
          break;
        }
    }
    if (type == Node.ELEMENT_NODE) {
      xml += "</";
      xml += node.getNodeName();
      xml += '>';
    }
    //xml += "\n";
}

protected Attr[] sortAttributes(NamedNodeMap attrs) {
  int len = (attrs != null) ? attrs.getLength() : 0;
  Attr array[] = new Attr[len];
  for (int i = 0; i < len; i++) {
    array[i] = (Attr) attrs.item(i);
  }
  for (int i = 0; i < len - 1; i++) {
    String name = array[i].getNodeName();
    int index = i;
    for (int j = i + 1; j < len; j++) {
      String curName = array[j].getNodeName();
      if (curName.compareTo(name) < 0) {
        name = curName;
        index = j;
      }
    }
    if (index != i) {
      Attr temp = array[i];
      array[i] = array[index];
      array[index] = temp;
    }
  }
  return (array);
}

protected String normalize(String s) {
  StringBuffer str = new StringBuffer();
  int len = (s != null) ? s.length() : 0;
  for (int i = 0; i < len; i++) {
    char ch = s.charAt(i);
    switch (ch) {
      case '<' :
        {
          str.append("&lt;");
          break;
        }
      case '>' :
        {
          str.append("&gt;");
          break;
        }
      case '&' :
        {
          str.append("&amp;");
          break;
        }
      case '"' :
        {
          str.append("&quot;");
          break;
        }
      case '\r' :
      case '\n' :
        {
          if (canonical) {
            str.append("&#");
            str.append(Integer.toString(ch));
            str.append(';');
            break;
          }
          // else, default append char
        }
      default :
        {
          str.append(ch);
        }
    }
  }
  return (str.toString());
}



  // @author IRA
  // Perdón por esta funcion horrible y mal hecha.
  // Hay que buscar una auxiliar en algún paquete de XML que encodeé un String a un String en UTF-8
  public static String m_table[] = {
    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,   // 0x00 0x0f
    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,   // 0x10 0x1f
    "&#032;", "&#033;", "&#034;", "&#035;", "&#036;", "&#037;", "&#038;", "&#039;", "&#040;", "&#041;", "&#042;", "&#043;", "&#044;", "&#045;", "&#046;", "&#047;",   // 0x20 0x2f
    null, null, null, null, null, null, null, null, null, null, "&#058;", "&#059;", "&#060;", "&#061;", "&#062;", "&#063;",   // 0x30 0x3f
    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,   // 0x40 0x4f
    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,   // 0x50 0x5f
    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,   // 0x60 0x6f
    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,   // 0x70 0x7f
    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,   // 0x80 0x8f
    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,   // 0x90 0x9f
    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,   // 0xa0 0xaf
    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&#191;",   // 0xb0 0xbf
    null, "&#193;", null, null, null, null, null, null, null, "&#201;", null, null, null, "&#205;", null, null,   // 0xc0 0xcf
    null, "&#209;", null, "&#211;", null, null, null, null, null, null, "&#218;", "&#219;", null, null, null, null,   // 0xd0 0xdf
    null, "&#225;", null, null, null, null, null, null, null, "&#233;", null, null, null, "&#237;", null, null,   // 0xe0 0xef
    null, "&#241;", null, "&#243;", null, null, null, null, null, null, "&#250;", null, null, null, null, null };   // 0xf0 0xff

  // Hay que hacer bien esta cagada para usarla
  public static String encodeUTF8( String s ) {
    if( s == null ) {
      return null;
    }

    byte[]  bytes = s.getBytes();
    String  ret = "";
    int     i = 0;
    int     c;

    while( i < bytes.length ) {
      c = bytes[i];
      if( c < 0 )
        c = 256 + c;

      if( m_table[ c ] == null ) {
        ret += (char)bytes[i];
      } else {
        ret += m_table[ c ];
      }
      i++;
    }

    return ret;
  }
  
	public static String encodeString(String input) {
		String output = "";
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c > 0x7F) {
				output += encodeUTF8(String.valueOf(c));	
			} else {
				output += c;
			}
		}
		return output;
	}


}
