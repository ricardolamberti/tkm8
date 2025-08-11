package pss.common.documentos.docHtmlBase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.CharEncoding;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.BookmarkElement;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;
import org.xml.sax.InputSource;

import com.lowagie.text.Image;

import pss.JPath;
import pss.common.documentos.BizDocumento;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;

public abstract class BizDocHtmlBase extends BizDocumento {

	
	public BizDocHtmlBase() throws Exception {
	}

	public void clean() throws Exception {
//		objTipo=null;
		super.clean();
  }
	
  
	public String extractText(String src) throws Exception {
		String str = (new URLCodec()).decode(src, CharEncoding.ISO_8859_1);
		str = str.replaceAll("<br>", "\n");
		str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("\\<p.*?\\>", "\n\n");
		str = str.replaceAll("\\<.*?\\>", "");
		return str;
	}
	
	public long getAnchoEditor() throws Exception {
		if (!this.hasPlantilla()) return 720;
		return this.getObjPlantilla().getAnchoEditor();
	}

	public long getMargenIzqEditor() throws Exception {
		if (!this.hasPlantilla()) return 80;
		return this.getObjPlantilla().getMargenIzqEditor();
	}

	public long getMargenImgSupEditor() throws Exception {
		if (!this.hasPlantilla()) return 108;
		return this.getObjPlantilla().getMargenImgSupEditor();
	}
	public long getMargenImgLeftEditor() throws Exception {
		if (!this.hasPlantilla()) return 0;
		return this.getObjPlantilla().getMargenImgLeftEditor();
	}

	public long getMargenImgSizeEditor() throws Exception {
		if (!this.hasPlantilla()) return 860;
		return this.getObjPlantilla().getMargenImgSizeEditor();
	}
	
	public String getFondo() throws Exception {
		if (!this.hasPlantilla()) return null;
		return this.getObjPlantilla().getFondo();
	} 
	
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected Document generarXml() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();

		Element oRoot = document.createElement("root");
		document.appendChild(oRoot);
		this.exportarAXml(oRoot);
		return document;
	}
	
	public void exportarAXml(Element elem) throws Exception {
		Element mat = serializeContent("documento", elem, true);
		elem.appendChild(mat);
	}



	/*
	 * public String verDocumento(String texto ) throws Exception { String
	 * fullfilename =
	 * JPath.PssPathTempFiles()+"/"+this.getObjDocFisico().getHtmlFilename();
	 * String filename = this.getObjDocFisico().getHtmlFilename();
	 * FileOutputStream f = new FileOutputStream(fullfilename); String str
	 * =decode(texto,CharEncoding.ISO_8859_1); f.write("<html><head>".getBytes());
	 * String s="body:before, body:after {"; s+="content: \"\";";
	 * s+="position: fixed;"; s+="background: #FFFFFF;"; s+="left: 0;";
	 * s+="right: 0;"; s+="height: 10px;"; s+="}"; s+="body:before {";
	 * s+="top: 0;"; s+="}"; s+="body:after {"; s+="bottom: 0;"; s+="}";
	 * s+="body {"; s+="width: 650px;"; s+="border-left: 40px solid #FFFFFF;";
	 * s+="border-right: 20px solid #FFFFFF;"; s+="}"; s+="p {";
	 * s+="margin-top: 0px;"; s+="margin-bottom: 0px;"; s+="}";
	 * f.write(("<style type=\"text/css\">"+s+"</style>").getBytes());
	 * f.write("</head><body>".getBytes());
	 * f.write(str.replaceAll("border=\"1\" bordercolor=\"GRAY\"",
	 * "border=\"0\"").getBytes()); f.write("</body></html>".getBytes());
	 * f.close();
	 * 
	 * 
	 * // getObjLayout().imprimir(filename,path+"/"+tempfile,generarXml()); return
	 * filename; }
	 */
	protected BizPlantilla plantilla;
	public void setObjPlantilla(BizPlantilla p) throws Exception {
		this.plantilla = p;
	}
	public String getPlantillaDefault() throws Exception {
		return "";
	}
	public BizPlantilla getObjPlantilla() throws Exception {
		if (this.plantilla != null) return this.plantilla;
		BizPlantilla d = new BizPlantilla();
		if (this.isNullIdPlantilla()||getIdPlantilla()==0) {
			if ( getPlantillaDefault().equals("")) return null;
			d = BizCompany.getObjPlantilla(getCompany(), getPlantillaDefault());
			return (this.plantilla = d);
		}
		d.Read(getIdPlantilla());
		return (this.plantilla = d);
	}
	
	public boolean hasPlantilla() throws Exception {
		if (!this.hasIdDoc())return false;
		if (!this.getObjDocum().hasPlantilla()) return false;
		return true;
	}
	

	public long getMargenIzq() throws Exception {
		if (!this.hasPlantilla()) return 10;
		return this.getObjPlantilla().getMargenIzq();
	}

	public long getMargenDer() throws Exception {
		if (!this.hasPlantilla()) return 10;
		return this.getObjPlantilla().getMargenDer();
	}

	public long getMargenTop() throws Exception {
		if (!this.hasPlantilla()) return 10;
		return this.getObjPlantilla().getMargenTop();
	}

	public long getMargenBottom() throws Exception {
		if (!this.hasPlantilla()) return 10;
		return this.getObjPlantilla().getMargenBottom();
	}

	public String getTipoPagina() throws Exception {
		if (!this.hasPlantilla()) return "A4";
		return this.getObjPlantilla().getTipoPagina();
	}

	public String getBorde() throws Exception {
		if (!this.hasPlantilla()) return "";
		return this.getObjPlantilla().getBorde();
	}

	public long getPadding() throws Exception {
		if (!this.hasPlantilla()) return 0;
		return this.getObjPlantilla().getPadding();
	}

	public String getTipoPaginaCSS3() throws Exception {
		return BizPlantilla.getTipoPaginaCSS3(getTipoPagina());
	}
	public long getLargoPaginaCSS3() throws Exception {
		return BizPlantilla.getLargoPagina(getTipoPagina());
	}
	public String generateDoc() throws Exception {
		return this.getObjPlantilla().generateDocSimple(this);
	}
	
	
	public String verDocumento(boolean paraImpresion) throws Exception {
		return verDocumento(paraImpresion, generateDoc());
	}
	public String verDocumento(boolean paraImpresion, String texto) throws Exception {
		String imgFile = null;
		double ajuste=2;
		if (this.hasPlantilla()) {
			if (!paraImpresion || this.getObjPlantilla().getImprimeFondo())
				imgFile = JPath.PssPathData() + "/Fondos/" + this.getObjPlantilla().getFondo();
		}
		String filename=this.getObjDocum().getPdfFilename();
		
		if (imgFile ==null) {
			ajuste=0;
		}
		String fullfilename= JPath.PssPathTempFiles() + "/" + filename;
		ByteArrayOutputStream f = new ByteArrayOutputStream();
		texto=JTools.replace(texto,"+", "%2B");
		texto=JTools.replace(texto,"%u2026", "%22");
		texto=JTools.replace(texto,"%u201D", "%22");
		texto=JTools.replace(texto,"%u201C", "%22");
		
		
		String str = decode(texto, CharEncoding.ISO_8859_1);
		
		f.write("<html><head>".getBytes());
		String s ="";
//		s+= "body:before, body:after {";
//		s += "	left: 0;";
//		s += "	right: 0;";
//		s += "	height: 10px;";
//		s += "}";
		s += "body:before {";
		s += "	top: 0;";
		s += "}";
		s += "body:after {";
		s += "	bottom: 0;";
		s += "}";
		s += ".r90 {";
		s += "  display:inline-block;";
		s += "  filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=3);";
		s += "  -webkit-transform: rotate(270deg);";
		s += "  -ms-transform: rotate(270deg);";
		s += "  transform: rotate(270deg);";
		s += "}";
		s += "table{ ";
		s += "	counter-reset: tablepage;";
		s += "	-fs-table-paginate: paginate;";
		s += "	border-spacing: 0;";
		s += "	border-collapse:collapse;";
		s += "	-moz-border-radius: 5px;"; 
		s += "	border-radius: 5px;"; 
		s += "}";
		s += "th { counter-increment: tablepage }";
		s += "table tr{";
		s += "  page-break-inside:avoid;";
		s += "}";
		s += "body {";
		s += "	border-left: 0px solid #FFFFFF;";
		s += "	border-right: 0px solid #FFFFFF;";
	  s += "}";
		s += "p {";
		s += "	margin-top: 0px;";
		s += "	margin-bottom: 0px;";
	//	s += "	letter-spacing: -0.05em;";
		s += "}";
		s += "@page { ";
		s += "	size: " + getTipoPaginaCSS3() + ";";
		s += "	margin-left: " + getMargenIzq() + "mm; ";
		s += "	margin-right: " + getMargenDer() + "mm; ";
		s += "	margin-top: " + (getMargenTop()-ajuste) + "mm; ";
		s += "	margin-bottom: " + getMargenBottom() + "mm; ";
	//	s += "  background-image: url(\"/regtur/pss_data/Fondos/fondo_a4.jpg\"); ";
		if (!getBorde().equals("")) {
			s += "	border: " + getBorde() + ";";// thin solid black;";
			s += "	padding: " + getPadding() + "em;";
		}
		s += "}";
		
		s += "@page land { ";
		s += "  size: " + getTipoPaginaCSS3() + " landscape; ";
		s += "	margin-left: " + getMargenIzq() + "mm; ";
		s += "	margin-right: " + getMargenDer() + "mm; ";
		s += "	margin-top: " + getMargenTop() + "mm; ";
		s += "	margin-bottom: " + getMargenBottom() + "mm; ";
    s += "}";
		s += ".landscape { ";
		s += "   page:land; ";
		s += "   page-break-before: always;"; 
	  s += "   width: "+(getLargoPaginaCSS3()-getMargenIzq()-getMargenDer()-10)+"mm; ";
	  s += "} ";
	  s += "#tablenumber:before {  content: counter(tablepage); } ";
	  s += "#pagenumber:before {  content: counter(page); } ";
	  s += "#pagecount:before {  content: counter(pages); } ";
	  f.write(("<style type=\"text/css\">" + s + "</style>").getBytes());
		f.write("</head><body>".getBytes());
		str = JTools.replace(str,"border=\"1\" bordercolor=\"GRAY\"", "border=\"0\"");
		str = JTools.replace(str,"border=\"1\" bordercolor=\"gray\"", "border=\"0\"");
		str = JTools.replace(str,"border=\"1\" cellSpacing=\"0\" borderColor=\"gray\"", "border=\"0\" cellSpacing=\"0\"");
		str = JTools.replace(str,"border=\"1\" cellspacing=\"0\" bordercolor=\"gray\"", "border=\"0\" cellSpacing=\"0\"");
		// str=str.replaceAll("\n", "<br/>");
		f.write(str.getBytes());
		f.write("</body></html>".getBytes());
		f.close();

		String convertOut = convertToPrinteable(JTools.byteVectorToString(f.toByteArray()));
	//	convertOut=JTools.replace(convertOut,"[[mas]]","+");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream input = new ByteArrayInputStream(convertOut.getBytes());

		// Clean up the HTML to be well formed
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		TagNode node = cleaner.clean(input);
		// Instead of writing to System.out we now write to the ByteArray buffer
		new PrettyXmlSerializer(props).writeToStream(node, out);

		// Create the PDF
		ITextRenderer renderer = new ITextRenderer();
           
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\arial.ttf", true);
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\ariblk.ttf", true);
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\ariali.ttf", true);
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\arialbd.ttf", true);
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\arialbi.ttf", true);
		
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\times.ttf", true);
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\timesbd.ttf", true);
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\timesbi.ttf", true);
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\timesi.ttf", true);
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\impact.ttf", true);
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\comic.ttf", true);
		renderer.getFontResolver().addFont("c:\\windows\\fonts\\comicbd.ttf", true);
	//	renderer.getFontResolver().addFont("c:\\windows\\fonts\\barcode39ext.ttf", true);
    SharedContext sharedContext = renderer.getSharedContext();
    sharedContext.setPrint(true);
    sharedContext.setInteractive(false);
    sharedContext.setReplacedElementFactory(new ProfileImageReplacedElementFactory());
    sharedContext.getTextRenderer().setSmoothingThreshold(0);
   	renderer.setDocumentFromString(JTools.byteVectorToString(out.toByteArray()));
		renderer.layout();
		OutputStream outputStream = new FileOutputStream(fullfilename);
		renderer.createPDF(outputStream, true, 0);
		
		// Finishing up
		renderer.finishPDF();
		out.flush();
		out.close();
		return filename;
	}
	
	/* print como html*/
	
	public String verDocumentoHtml(boolean paraImpresion, String texto) throws Exception {
		String imgFile = null;
		if (this.hasPlantilla()) {
			if (!paraImpresion || this.getObjPlantilla().getImprimeFondo())
				imgFile = JPath.PssPathData() + "/Fondos/" + this.getObjPlantilla().getFondo();
		}
		String filename = this.getObjDocum().getHtmlFilename();

		String fullfilename= JPath.PssPathTempFiles() + "/" + filename;
		FileOutputStream f = new FileOutputStream(fullfilename);
		texto=JTools.replace(texto,"+", "%2B");
		String str = decode(texto, CharEncoding.ISO_8859_1);
		f.write("<html><head>".getBytes());
		String s = "body:before, body:after {";
//		s += "content: \"\";";
//		
//  	s += "background: #FFFFFF;";
		s += "position: fixed;";
		s += "height: 10px;";
		s += "}";
		s += "body:before {";
		s += "top: 0;";
		s += "}";
		s += "body:after {";
		s += "bottom: 0;";
		s += "}";
		s += ".r90 {";
		s += "  display:inline-block;";
		s += "  filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=3);";
		s += "  -webkit-transform: rotate(270deg);";
		s += "  -ms-transform: rotate(270deg);";
		s += "  transform: rotate(270deg);";
		s += "}";
		s += "table{ ";
		s += "-fs-table-paginate: paginate;";
		s += "border-spacing: 0;";
		s += "border-collapse:collapse;";
		s += "-moz-border-radius: 5px;"; 
		s += "border-radius: 5px;"; 
		s += "}";
		s += "table tr{";
		s += "  page-break-inside:avoid;";
		s += "}";
		s += "body {";
		s += "  background-image: url('/regtur/pss_data/Fondos/"+ this.getObjPlantilla().getFondo()+"'); ";
		s += "  background-size: 170mm 227mm; ";
		s += "  background-repeat: no-repeat; ";
		s += "	size: 210mm 297mm;";
		s += "	margin-left: 10 mm; ";
		s += "	margin-right: 10 mm; ";
		s += "	margin-top: " + getMargenTop() + "mm; ";
		s += "	margin-bottom: " + getMargenBottom() + "mm; ";
	  s += "}";
		s += "p {";
		s += "margin-top: 0px;";
		s += "margin-bottom: 0px;";
		s += "letter-spacing: -0.05em;";
		s += "}";
		s += "@page { ";
		s += "	size: " + getTipoPaginaCSS3() + ";";
		s += "	margin-left: " + getMargenIzq() + "mm; ";
		s += "	margin-right: " + getMargenDer() + "mm; ";
		s += "	margin-top: " + getMargenTop() + "mm; ";
		s += "	margin-bottom: " + getMargenBottom() + "mm; ";
		s += "  background-image: url('/regtur/pss_data/Fondos/"+ this.getObjPlantilla().getFondo()+"'); ";
		s += "  background-size: 210mm 297mm; ";
		s += "  background-repeat: no-repeat; ";
		 s+="	-fs-flow-top: \"header\";";
		 s+="	-fs-flow-bottom: \"footer\";";
		 s+="	-fs-flow-left: \"left\";";
		 s+="	-fs-flow-right: \"right\";";
		if (!getBorde().equals(""))
			s += "	border: " + getBorde() + ";";// thin solid black;";
		s += "	padding: " + getPadding() + "em;";
		s += "}";
		
		s += "@page land { ";
		s += "  size: " + getTipoPaginaCSS3() + " landscape; ";
		s += "	margin-left: " + getMargenIzq() + "mm; ";
		s += "	margin-right: " + getMargenDer() + "mm; ";
		s += "	margin-top: " + getMargenTop() + "mm; ";
		s += "	margin-bottom: " + getMargenBottom() + "mm; ";
    s += "}";
		s += ".landscape { ";
		s += "   page:land; ";
		s += "   page-break-before: always;"; 
	  s += "   width: "+getLargoPaginaCSS3()+"mm; ";
	  s += "} ";
	  s += "@media print {";
	  s += " body:before { ";
	  s += "  content:url(\"regtur/pss_data/Fondos/"+ this.getObjPlantilla().getFondo()+"\");";
		s += "  background-size: 180mm 297mm; ";
		s += "  background-repeat: no-repeat; ";
	  s += "}";
	  s += "}";

	  s += "#pagenumber:before {  content: counter(page); } ";
	  s += "#pagecount:before {  content: counter(pages); } ";
	  f.write(("<style type=\"text/css\">" + s + "</style>").getBytes());
		f.write("</head><body>".getBytes());
		str = str.replaceAll("border=\"1\" bordercolor=\"GRAY\"", "border=\"0\"");
		str = str.replaceAll("border=\"1\" bordercolor=\"gray\"", "border=\"0\"");
		str = str.replaceAll("border=\"1\" cellSpacing=\"0\" borderColor=\"gray\"", "border=\"0\" cellSpacing=\"0\"");
		str = str.replaceAll("border=\"1\" cellspacing=\"0\" bordercolor=\"gray\"", "border=\"0\" cellSpacing=\"0\"");
		// str=str.replaceAll("\n", "<br/>");
		f.write(str.getBytes());
		f.write("</body></html>".getBytes());
		f.close();

		return filename;

	}
	
	public class ProfileImageReplacedElementFactory implements ReplacedElementFactory {

    public ProfileImageReplacedElementFactory() {
    }

    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox,
            UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {

        Element element = blockBox.getElement();
        if (element == null) {
            return null;
        }

        String nodeName = element.getNodeName();
        if ("hr".equals(nodeName)) {
          String style = element.getAttribute("style");
          if (style.toLowerCase().indexOf("dashed")!=-1) {
	        	try {
	              return new BookmarkElement();
	            } catch (Exception e) {
	            	PssLogger.logError(e);
	            }
          }
        }
        if ("img".equals(nodeName)) {
          String src = element.getAttribute("src");
          if (src.startsWith("data:image")) {
	        	try {
	               	String tex = src.substring(src.indexOf(",")+1);
	              	tex=tex.replaceAll(" ", "+");
	            	  Image image = Image.getInstance(Base64.getDecoder().decode(tex));
	 
	                FSImage fsImage = new ITextFSImage(image);
	
	                if (fsImage != null) {
	                    if ((cssWidth != -1) || (cssHeight != -1)) {
	                        fsImage.scale(cssWidth, cssHeight);
	                    }
	                    else
	                    	fsImage.scale(fsImage.getWidth()*20, fsImage.getHeight()*20);
	
	                    return new ITextImageElement(fsImage);
	                }
	            } catch (Exception e) {
	            	PssLogger.logError(e);
	            }
          } else if  (src.indexOf("pss_data")!=-1) {
	        	try {
             	
              FSImage fsImage = userAgentCallback.getImageResource(src).getImage();

              if (fsImage != null) {
                  if ((cssWidth != -1) || (cssHeight != -1)) {
                      fsImage.scale(cssWidth, cssHeight);
                  }
                  else
                  	fsImage.scale(fsImage.getWidth()*20, fsImage.getHeight()*20);

                  return new ITextImageElement(fsImage);
              }
          } catch (Exception e) {
          	PssLogger.logError(e);
          }
          	
          }
        }

        return null;
    }
    
 	public String getFileName(String tipo) {
  		try {
    		return BizUsuario.getUsr().getCompany() + "/" + JTools.getValidFilename(this.toString()) + "." + tipo;
			} catch (Exception e) {
				return "imagen.jpg";
			}
		}

    public String getFullFileName(String tipo)  {
  		try {
				return JPath.PssPathTempFiles() + "/" + getFileName(tipo);
			} catch (Exception e) {
				return "imagen.jpg";
			}
  	}

    @Override
    public void reset() {
    }

    @Override
    public void remove(Element e) {
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
    }
	}
	public static String decode(String s, String enc) throws UnsupportedEncodingException {

		boolean needToChange = false;
		int numChars = s.length();
		StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
		int i = 0;

		if (enc.length() == 0) {
			throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
		}

		char c;
		byte[] bytes = null;
		while (i < numChars) {
			c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				i++;
				needToChange = true;
				break;
			case '%':
				/*
				 * Starting with this instance of %, process all consecutive substrings
				 * of the form %xy. Each substring %xy will yield a byte. Convert all
				 * consecutive bytes obtained this way to whatever character(s) they
				 * represent in the provided encoding.
				 */

				try {

					// (numChars-i)/3 is an upper bound for the number
					// of remaining bytes
					if (bytes == null)
						bytes = new byte[(numChars - i) / 3];
					int pos = 0;

					while (((i + 2) < numChars) && (c == '%')) {
						if (!JTools.IsHexa(s.substring(i + 1, i + 3))) {
							i += 3;
							break;
						}
						int v = Integer.parseInt(s.substring(i + 1, i + 3), 16);
						if (v < 0) {
							i += 3;
							break;
						}
						bytes[pos++] = (byte) v;
						i += 3;
						if (i < numChars)
							c = s.charAt(i);
					}

					// A trailing, incomplete byte encoding such as
					// "%x" will cause an exception to be thrown

					if ((i < numChars) && (c == '%')) {
						i += 2;
						continue;
					}
					sb.append(new String(bytes, 0, pos, enc));
				} catch (NumberFormatException e) {
					throw e;
				}
				needToChange = true;
				break;
			default:
				sb.append(c);
				i++;
				break;
			}
		}

		return (needToChange ? sb.toString() : s);
	}

	public String exportar(String path) throws Exception {
		String filename = this.getObjDocum().getXmlFilename();
		FileWriter output = new FileWriter(path + "/" + filename);
		String out = JTools.generarString(generarXml());
		System.out.println("Export: " + out);
		output.write(out);
		output.close();
		return filename;
	}

//	public JMap<String, String> getSubtipos() throws Exception {
//		return BizDocHtmlTipo.getTipos();
//	}

	
	public String convertToPrinteable(String source) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setValidating(false);
		dbf.setNamespaceAware(true);
		dbf.setIgnoringComments(false);
		dbf.setIgnoringElementContentWhitespace(false);
		dbf.setExpandEntityReferences(false);
		source = source.replaceAll("<br>", "<br/>");
		source = source.replaceAll("<br clear=\"all\">", "<br clear=\"all\"/>");
		
		source = source.replaceAll("<hr>", "<hr/>");
		source = source.replaceAll("<BR>", "<br/>");
		source = source.replaceAll("<HR>", "<hr/>");
		source = source.replaceAll("&nbsp;", " ");
		source = source.replaceAll("“", "\"");
		source = source.replaceAll("”", "\"");
		source=JTools.replaceWebForForeignChars(source);
		
		DocumentBuilder db = dbf.newDocumentBuilder();
  	String in = BizPlantilla.reparacionBugsDeExplorer(source);
//  	in = BizDocHtmlTipo.convertPxToMM(in);
		Document doc = db.parse(new InputSource(new StringReader(in)));
		Document newdoc = db.newDocument();
		Node n = newdoc.appendChild(newdoc.createElement("html"));

		doRender(newdoc, n, doc.getDocumentElement());

		return JTools.generarString(newdoc);
	}

	private void doRender(Document outDoc, Node output, Node nodoPadre) throws Exception {
		Node newNode;
		if (nodoPadre instanceof Element) {
			Element padre = (Element) nodoPadre;
			if (padre.getTagName().equalsIgnoreCase("font")) {
				String name = padre.getAttribute("face");
				String s = padre.getAttribute("size");
				String fs = padre.getAttribute("style");
				if (fs!=null) {
					int pos = fs.indexOf("font-size:");
					if (pos!=-1) {
						int pos1 = fs.indexOf("\"",pos+1);
						int pos2 = fs.indexOf(";",pos+1);
						String sf="";
						if (pos1==-1 && pos2==-1) sf=fs.substring(pos+"font-size:".length());
						else if (pos1==-1) sf=fs.substring(pos+"font-size:".length(),pos2);
						else if (pos2==-1) sf=fs.substring(pos+"font-size:".length(),pos1);
						else sf=fs.substring(pos+"font-size:".length(),pos1<pos2?pos1:pos2);
						if (s==null || s.equals("")) 
							s =sf.trim();
					}
				}
				newNode = output.appendChild(outDoc.createElement("span"));
				if (name == null)
					name = "";
				else if (name.equalsIgnoreCase("cursive"))
					name = "Comic Sans MS";
				else if (name.equalsIgnoreCase("fantasy"))
					name = "Impact";
				else if (name.equalsIgnoreCase("courier"))
					name = "Courier";
				else if (name.equalsIgnoreCase("arial"))
					name = "Arial";
				else if (name.equalsIgnoreCase("helvetica"))
					name = "Helvetica";
				else if (name.equalsIgnoreCase("times new roman"))
					name = "Times New Roman";
				else if (name.equalsIgnoreCase("barcode39ext")) {
					if (s!=null) s+="+";
				}
					
				if (s == null)
					s = "";
				if (s.equals("1"))
					s = "9px";
				else if (s.equals("2"))
					s = "11px";
				else if (s.equals("3"))
					s = "14px";
				else if (s.equals("4"))
					s = "18px";
				else if (s.equals("5"))
					s = "21px";
				else if (s.equals("6"))
					s = "23px";
				else if (s.equals("7"))
					s = "25px";
				else if (s.equals("8"))
					s = "27px";
				else if (s.equals("9"))
					s = "29px";
				else if (s.equals("10"))
					s = "31px";
				else if (s.equals("11"))
					s = "33px";
				else if (s.equals("12"))
					s = "35px";
				else if (s.equals("13"))
					s = "37px";
				else if (s.equals("14"))
					s = "39px";
				else if (s.equals("15"))
					s = "41px";
				else 	if (s.equals("1+"))
					s = "30px";
				else if (s.equals("2+"))
					s = "51px";
				else if (s.equals("3+"))
					s = "61px";
				else if (s.equals("4+"))
					s = "71px";
				else if (s.equals("5+"))
					s = "81px";
				else if (s.equals("6+"))
					s = "91px";
				else if (s.equals("7+"))
					s = "101px";
				else if (s.equals("8+"))
					s = "111px";
				else if (s.equals("9+"))
					s = "121px";
				else if (s.equals("10+"))
					s = "131px";
				else if (s.equals("11+"))
					s = "141px";
				else if (s.equals("12+"))
					s = "151px";
				else if (s.equals("13+"))
					s = "161px";
				else if (s.equals("14+"))
					s = "171px";
				else if (s.equals("15+"))
					s = "181px";
				else if (s.indexOf("pt")!=-1)
					s = (Long.parseLong(s.replace("pt", ""))+5)+"px";

				String style =  "font-weight: 100;"+ (name.equals("") ? "" : "font-family:" + name + ";") + (s.equals("") ? "" : "font-size:" + s + ";");
				((Element) newNode).setAttribute("style", style);

			} else if (padre.getTagName().equalsIgnoreCase("span")) {
        //<span class="Apple-tab-span" style="white-space:pre">	</span>

				String style = padre.getAttribute("class");
				if (style.equals("Apple-tab-span")) {
					Text e = outDoc.createTextNode("\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0");
					
					newNode = output.appendChild(e);
					return;
				}
				else {
					newNode = output.appendChild(outDoc.importNode(nodoPadre, false));
				}
			} else {
				newNode = output.appendChild(outDoc.importNode(nodoPadre, false));
			}

		} else {
			newNode = output.appendChild(outDoc.importNode(nodoPadre, false));
		}
		if (nodoPadre.getNodeValue() != null)
			newNode.setNodeValue(nodoPadre.getNodeValue());

		NodeList nodeList = nodoPadre.getChildNodes();
		if (nodeList == null || nodeList.getLength() == 0)
			return;

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node n = (Node) nodeList.item(i);
			doRender(outDoc, newNode, n);
		}

		return;
	}


	
}
