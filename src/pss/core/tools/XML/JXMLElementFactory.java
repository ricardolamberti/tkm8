package pss.core.tools.XML;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import pss.core.tools.JExcepcion;

/**
 * Esta clase es la fabrica de documentos , nodos y elementos XML.
 * Modo de uso.
 *
 * JXMLElementFactory.getInstance();
 * este metodo de clase retorna una única instancia de
 * la fabrica. a partir de aqui podrá operar con métodos
 * de instancia
 *
 * @author: BUG][V][AN
 * @description:
 */

public class JXMLElementFactory {

  private static JXMLElementFactory oFactory = null;
  private Document oDocument = null;
  private DocumentBuilder oBuilder = null;


  /**
   * Return the single instance
   */
  public static JXMLElementFactory getInstance() throws Exception {
    if (oFactory == null) {
      System.setProperty(
        "javax.xml.parsers.DocumentBuilderFactory",
//        "org.apache.crimson.jaxp.DocumentBuilderFactoryImpl"
       "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl"
      );
      oFactory = new JXMLElementFactory(DocumentBuilderFactory.newInstance());
    }
    return oFactory;
  }


  private JXMLElementFactory(DocumentBuilderFactory zFactory) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    this.oBuilder = dbf.newDocumentBuilder();
    this.oDocument = oBuilder.newDocument();
  }


  /**
   * CREATES A ROOT ELEMENT
   */
  public Element createRootElement() throws Exception {
    Element e = this.oDocument.createElement("root");
    return e;
  }

  /**
   * CREATES A DOCUMENT ELEMENT
   */
  public synchronized Element createElement(String zNodeName) throws Exception {
    return this.oDocument.createElement(zNodeName);
  }


  /**
   * CREATES AN ELEMENT DOCUMENT FROM A XML STRING
   */
  public synchronized Element createElementFromString(String zXMLString) throws Exception {
    if ("".equals(zXMLString)) 
    	JExcepcion.SendError("Fuente inválida para crear elemento XML");
    StringReader stringReader = new StringReader(zXMLString);
    InputSource inputSource = new InputSource(stringReader);
    

    Element result =this.oBuilder.parse(inputSource).getDocumentElement();
    stringReader.close();
    return result;

  }
  


  /**
   * MERGE A ELEMENT INTO ANOTHER ONE
   */
  public void adoptNode(Element zParent, Element zChild) throws Exception {
    Element oChild = (Element)zParent.getOwnerDocument().importNode(zChild,true);
    zParent.appendChild(oChild);
  }


  /**
   * PARSES A XML URI TO A ELEMENT
   */
  public synchronized Element parseURIToElement(String zURI) throws Exception {
    return this.oBuilder.parse(zURI).getDocumentElement();
  }



  /**
   * Parses the given <code>InputStream</code> to XML.
   */
//  public synchronized Element parseURIToElement(InputStream zStream) throws Exception {
//  	
//    InputSource inputSource = new InputSource(getStringFromInputStream(zStream));
////  	this.oBuilder.parse(inputSource);
//  	Document doc = this.oBuilder.parse(inputSource);
//  	return doc.getDocumentElement();
//  }
  /**
   * Parses the given <code>InputStream</code> to XML.
   */
  public synchronized Element parseURIToElement(InputStream zStream) throws Exception {
    return createElementFromString(getStringFromInputStream(zStream));
  }
  
  

	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}


  /**
   * PARSES A XML URI TO A DOCUMENT
   */
  public Document parseURIToDocument(String zURI) throws Exception {
    URL url = new URL(zURI);
    InputStream stream = url.openStream();
    InputSource inputSource = new InputSource(stream);
  	return this.oBuilder.parse(inputSource);
  }

  /**
   * CREATES A DOCUMENT
   */
  public Document createDocument() throws Exception {
    return oBuilder.newDocument();
  }
  
	public String serialize(Element oRoot) throws Exception {
//		JXMLElementFactory oFactory = JXMLElementFactory.getInstance();
		Document document = oRoot.getOwnerDocument();

		DOMImplementationLS domImplementation = (DOMImplementationLS) document.getImplementation();
	  LSSerializer lsSerializer = domImplementation.createLSSerializer();
	  lsSerializer.getDomConfig().setParameter("xml-declaration", false);
	  return lsSerializer.writeToString(oRoot);   

//		return oRoot.toString();
	}
	

//	public static void main(String[] args) {
//		try {
//			Element el= JXMLElementFactory.getInstance().createElementFromString("<xml>"
//					+ "<section id=\"REPORT_HEADER\"  TIPO_COMPROB_D=\"Factura B\"  NRO_COMPROBANTE=\"10230\" DIR_EMPRESA=\"Sgo. del Estero Nº 376  (4400) provincia de Salta\"  EMPRESA_TELEFONO=\"4313116\"  EMPRESA_CPOSTAL=\"4400\"  EMPRESA_LOCALIDAD=\"\" EMPRESA_IDENT_ADICIONAL=\"EVT Leg. 7906\"  CLIENTE_NOMBRE=\"FRENCH, Santiago\"  CLIENTE_TIPO_CUIT_FISCAL=\"C\"  CLIENTE_CUIT=\"20257049680\" CLIENTE_DOMICILIO=\"provincia de Salta\"  CLIENTE_RESPON_ID_FISCAL=\"C\"  NRO_COMPUESTO_ORI=\"\"  NRO_COMPROB_ORI=\"null\"  />"
//					+ "<section id=\"BODY\" ITEM_CODIGO=\"\"  ITEM_DESCR=\"Iva Exento\"  ITEM_CANT=\"1.0\"  ITEM_P_UNIT=\"64830.6\"  ITEM_RATENATTAX=\"0.0\"  ITEM_P_UNIT_ORIG=\"-1.0\" ITEM_PRICE_PRESITION=\"2\"  ITEM_NEGATIVE=\"false\"  />"
//					+ "<section id=\"BODY\"  ITEM_CODIGO=\"\"  ITEM_DESCR=\"No Gravado\"  ITEM_CANT=\"1.0\" ITEM_P_UNIT=\"11616.4\"  ITEM_RATENATTAX=\"0.0\"  ITEM_P_UNIT_ORIG=\"-1.0\"  ITEM_PRICE_PRESITION=\"2\"  ITEM_NEGATIVE=\"false\"  />"
//					+ "<section id=\"PAY_BODY\"  PAY_FORMA_PAGO_PADRE=\"Cuenta Corriente\"  PAY_DESCRIP=\"Cuenta Corriente\"  PAY_MONTO_ORIGINAL=\"76447\"  />"
//					+ "<section id=\"REPOR&quot; T_FOOTER\"  VUELTO_CONSUMIDO=\"0.0\"  VUELTO_CONSUMIDO_DESCR=\"null\"  COMENTARIO=\"LA - 4944794498 - 03/09 - FRENCH/BERNARDO->SLA/LIM/LIM/MIA/MIA/...\nLA - 4944794497 - 03/09 - FRENCH/MANUELA->SLA/LIM/LIM/MIA/MIA/...\nLA - 4944794496 - 03/09 - ESCUDERO/AGUSTINA->SLA/LIM/LIM/MIA/MIA/...\nLA - 4944794495 - 03/09 - FRENCH/SANTIAGO->SLA/LIM/LIM/MIA/MIA/...\n\"  />"
//					+ "</xml>");
//			
//			System.out.println("OK");
//			
//			System.out.println(JXMLElementFactory.getInstance().serialize(el));
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			System.out.println("Error");
//			e.printStackTrace();
//		}
//	}

	public static void main(String[] args) {
	try {
		
		byte[]  a = {60, 63, 120, 109, 108, 32, 118, 101, 114, 115, 105, 111, 110, 61, 34, 49, 46, 48, 34, 32, 101, 110, 99, 111, 100, 105, 110, 103, 61, 34, 85, 84, 70, 45, 56, 34, 32, 115, 116, 97, 110, 100, 97, 108, 111, 110, 101, 61, 34, 121, 101, 115, 34, 63, 62, 10, 60, 108, 111, 103, 105, 110, 84, 105, 99, 107, 101, 116, 82, 101, 115, 112, 111, 110, 115, 101, 32, 118, 101, 114, 115, 105, 111, 110, 61, 34, 49, 34, 62, 10, 32, 32, 32, 32, 60, 104, 101, 97, 100, 101, 114, 62, 10, 32, 32, 32, 32, 32, 32, 32, 32, 60, 115, 111, 117, 114, 99, 101, 62, 67, 78, 61, 119, 115, 97, 97, 44, 32, 79, 61, 65, 70, 73, 80, 44, 32, 67, 61, 65, 82, 44, 32, 83, 69, 82, 73, 65, 76, 78, 85, 77, 66, 69, 82, 61, 67, 85, 73, 84, 32, 51, 51, 54, 57, 51, 52, 53, 48, 50, 51, 57, 60, 47, 115, 111, 117, 114, 99, 101, 62, 10, 32, 32, 32, 32, 32, 32, 32, 32, 60, 100, 101, 115, 116, 105, 110, 97, 116, 105, 111, 110, 62, 67, 61, 97, 114, 44, 32, 79, 61, 112, 101, 110, 116, 97, 119, 97, 114, 101, 32, 115, 97, 44, 32, 83, 69, 82, 73, 65, 76, 78, 85, 77, 66, 69, 82, 61, 67, 85, 73, 84, 32, 51, 48, 55, 49, 48, 55, 57, 51, 57, 57, 53, 44, 32, 67, 78, 61, 112, 101, 110, 116, 97, 60, 47, 100, 101, 115, 116, 105, 110, 97, 116, 105, 111, 110, 62, 10, 32, 32, 32, 32, 32, 32, 32, 32, 60, 117, 110, 105, 113, 117, 101, 73, 100, 62, 51, 57, 53, 57, 53, 55, 55, 50, 49, 50, 60, 47, 117, 110, 105, 113, 117, 101, 73, 100, 62, 10, 32, 32, 32, 32, 32, 32, 32, 32, 60, 103, 101, 110, 101, 114, 97, 116, 105, 111, 110, 84, 105, 109, 101, 62, 50, 48, 49, 55, 45, 48, 54, 45, 48, 54, 84, 49, 49, 58, 49, 50, 58, 51, 52, 46, 56, 49, 48, 45, 48, 51, 58, 48, 48, 60, 47, 103, 101, 110, 101, 114, 97, 116, 105, 111, 110, 84, 105, 109, 101, 62, 10, 32, 32, 32, 32, 32, 32, 32, 32, 60, 101, 120, 112, 105, 114, 97, 116, 105, 111, 110, 84, 105, 109, 101, 62, 50, 48, 49, 55, 45, 48, 54, 45, 48, 54, 84, 50, 51, 58, 49, 50, 58, 51, 52, 46, 56, 49, 48, 45, 48, 51, 58, 48, 48, 60, 47, 101, 120, 112, 105, 114, 97, 116, 105, 111, 110, 84, 105, 109, 101, 62, 10, 32, 32, 32, 32, 60, 47, 104, 101, 97, 100, 101, 114, 62, 10, 32, 32, 32, 32, 60, 99, 114, 101, 100, 101, 110, 116, 105, 97, 108, 115, 62, 10, 32, 32, 32, 32, 32, 32, 32, 32, 60, 116, 111, 107, 101, 110, 62, 80, 68, 57, 52, 98, 87, 119, 103, 100, 109, 86, 121, 99, 50, 108, 118, 98, 106, 48, 105, 77, 83, 52, 119, 73, 105, 66, 108, 98, 109, 78, 118, 90, 71, 108, 117, 90, 122, 48, 105, 86, 86, 82, 71, 76, 84, 103, 105, 73, 72, 78, 48, 89, 87, 53, 107, 89, 87, 120, 118, 98, 109, 85, 57, 73, 110, 108, 108, 99, 121, 73, 47, 80, 103, 111, 56, 99, 51, 78, 118, 73, 72, 90, 108, 99, 110, 78, 112, 98, 50, 52, 57, 73, 106, 73, 117, 77, 67, 73, 43, 67, 105, 65, 103, 73, 67, 65, 56, 97, 87, 81, 103, 100, 87, 53, 112, 99, 88, 86, 108, 88, 50, 108, 107, 80, 83, 73, 51, 78, 122, 65, 122, 79, 68, 103, 119, 79, 84, 77, 105, 73, 72, 78, 121, 89, 122, 48, 105, 81, 48, 52, 57, 100, 51, 78, 104, 89, 83, 119, 103, 84, 122, 49, 66, 82, 107, 108, 81, 76, 67, 66, 68, 80, 85, 70, 83, 76, 67, 66, 84, 82, 86, 74, 74, 81, 85, 120, 79, 86, 85, 49, 67, 82, 86, 73, 57, 81, 49, 86, 74, 86, 67, 65, 122, 77, 122, 89, 53, 77, 122, 81, 49, 77, 68, 73, 122, 79, 83, 73, 103, 90, 50, 86, 117, 88, 51, 82, 112, 98, 87, 85, 57, 73, 106, 69, 48, 79, 84, 89, 51, 78, 84, 103, 121, 79, 84, 81, 105, 73, 71, 86, 52, 99, 70, 57, 48, 97, 87, 49, 108, 80, 83, 73, 120, 78, 68, 107, 50, 79, 68, 65, 120, 78, 84, 85, 48, 73, 105, 66, 107, 99, 51, 81, 57, 73, 107, 78, 79, 80, 88, 100, 122, 90, 109, 85, 115, 73, 69, 56, 57, 81, 85, 90, 74, 85, 67, 119, 103, 81, 122, 49, 66, 85, 105, 73, 118, 80, 103, 111, 103, 73, 67, 65, 103, 80, 71, 57, 119, 90, 88, 74, 104, 100, 71, 108, 118, 98, 105, 66, 50, 89, 87, 120, 49, 90, 84, 48, 105, 90, 51, 74, 104, 98, 110, 82, 108, 90, 67, 73, 103, 100, 72, 108, 119, 90, 84, 48, 105, 98, 71, 57, 110, 97, 87, 52, 105, 80, 103, 111, 103, 73, 67, 65, 103, 73, 67, 65, 103, 73, 68, 120, 115, 98, 50, 100, 112, 98, 105, 66, 49, 97, 87, 81, 57, 73, 107, 77, 57, 89, 88, 73, 115, 73, 69, 56, 57, 99, 71, 86, 117, 100, 71, 70, 51, 89, 88, 74, 108, 73, 72, 78, 104, 76, 67, 66, 84, 82, 86, 74, 74, 81, 85, 120, 79, 86, 85, 49, 67, 82, 86, 73, 57, 81, 49, 86, 74, 86, 67, 65, 122, 77, 68, 99, 120, 77, 68, 99, 53, 77, 122, 107, 53, 78, 83, 119, 103, 81, 48, 52, 57, 99, 71, 86, 117, 100, 71, 69, 105, 73, 72, 78, 108, 99, 110, 90, 112, 89, 50, 85, 57, 73, 110, 100, 122, 90, 109, 85, 105, 73, 72, 74, 108, 90, 50, 49, 108, 100, 71, 104, 118, 90, 68, 48, 105, 77, 106, 73, 105, 73, 71, 86, 117, 100, 71, 108, 48, 101, 84, 48, 105, 77, 122, 77, 50, 79, 84, 77, 48, 78, 84, 65, 121, 77, 122, 107, 105, 73, 71, 70, 49, 100, 71, 104, 116, 90, 88, 82, 111, 98, 50, 81, 57, 73, 109, 78, 116, 99, 121, 73, 43, 67, 105, 65, 103, 73, 67, 65, 103, 73, 67, 65, 103, 73, 67, 65, 103, 73, 68, 120, 121, 90, 87, 120, 104, 100, 71, 108, 118, 98, 110, 77, 43, 67, 105, 65, 103, 73, 67, 65, 103, 73, 67, 65, 103, 73, 67, 65, 103, 73, 67, 65, 103, 73, 67, 65, 56, 99, 109, 86, 115, 89, 88, 82, 112, 98, 50, 52, 103, 99, 109, 86, 115, 100, 72, 108, 119, 90, 84, 48, 105, 78, 67, 73, 103, 97, 50, 86, 53, 80, 83, 73, 122, 77, 68, 99, 120, 77, 68, 99, 53, 77, 122, 107, 53, 78, 83, 73, 118, 80, 103, 111, 103, 73, 67, 65, 103, 73, 67, 65, 103, 73, 67, 65, 103, 73, 67, 65, 56, 76, 51, 74, 108, 98, 71, 70, 48, 97, 87, 57, 117, 99, 122, 52, 75, 73, 67, 65, 103, 73, 67, 65, 103, 73, 67, 65, 56, 76, 50, 120, 118, 90, 50, 108, 117, 80, 103, 111, 103, 73, 67, 65, 103, 80, 67, 57, 118, 99, 71, 86, 121, 89, 88, 82, 112, 98, 50, 52, 43, 67, 106, 119, 118, 99, 51, 78, 118, 80, 103, 111, 75, 60, 47, 116, 111, 107, 101, 110, 62, 10, 32, 32, 32, 32, 32, 32, 32, 32, 60, 115, 105, 103, 110, 62, 87, 115, 104, 110, 79, 122, 97, 69, 50, 89, 97, 43, 82, 112, 79, 110, 43, 53, 90, 85, 81, 116, 118, 57, 56, 79, 99, 88, 114, 105, 74, 76, 88, 99, 56, 84, 111, 87, 76, 108, 48, 66, 81, 80, 74, 47, 52, 97, 85, 114, 43, 122, 116, 99, 89, 67, 86, 122, 47, 52, 98, 99, 71, 73, 90, 57, 72, 65, 79, 57, 81, 100, 74, 69, 122, 74, 83, 112, 113, 104, 57, 56, 50, 105, 72, 98, 114, 49, 105, 97, 71, 48, 83, 115, 121, 110, 65, 109, 107, 69, 55, 100, 85, 109, 110, 106, 104, 52, 121, 43, 48, 83, 110, 87, 79, 71, 116, 83, 100, 116, 101, 106, 55, 112, 50, 68, 57, 101, 111, 69, 80, 122, 112, 90, 83, 122, 110, 82, 118, 109, 48, 50, 56, 121, 119, 57, 109, 55, 90, 70, 100, 50, 87, 105, 99, 88, 47, 109, 79, 98, 82, 74, 90, 78, 107, 65, 104, 86, 98, 98, 89, 61, 60, 47, 115, 105, 103, 110, 62, 10, 32, 32, 32, 32, 60, 47, 99, 114, 101, 100, 101, 110, 116, 105, 97, 108, 115, 62, 10, 60, 47, 108, 111, 103, 105, 110, 84, 105, 99, 107, 101, 116, 82, 101, 115, 112, 111, 110, 115, 101, 62, 10, 10};
		InputStream token = new ByteArrayInputStream(a);
		Element e = JXMLElementFactory.getInstance().parseURIToElement(token);
		
		System.out.println("OK");
		
		System.out.println(JXMLElementFactory.getInstance().serialize(e));
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("Error");
		e.printStackTrace();
	}
	}

}

