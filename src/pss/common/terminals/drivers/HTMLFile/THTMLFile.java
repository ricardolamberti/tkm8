package pss.common.terminals.drivers.HTMLFile;

import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode;

import pss.JPath;
import pss.JPssVersion;
import pss.common.layout.JFonts;
import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.drivers.PDFFile.JRectangulo;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.data.BizPssConfig;
import pss.core.data.files.JStreamFile;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class THTMLFile extends JTerminal implements JPrinterInterface {

	private JStreamFile pFile;
	protected float lineHeight = 9.5f;
	protected int header=0;
	protected float lineX;
	protected float lineY = 10f; // tuve que poner un offset en el header

	protected String defaultFont = null;
	protected int defaultSize=0;
	protected String defaultFore="000000";
	protected String defaultBack=null;
	

	public THTMLFile() throws Exception {
	}


	@Override
	public void loadDrivers() throws Exception {
		this.addDriver(JTerminal.D_PRINTER);
	}
	
	@Override
	public Answer open() throws Exception {
		return new AwrOk();
	}
  
	@Override
	public Answer close() throws Exception {
		return new AwrOk();
	}
	
	public Answer closeDay() throws Exception {
		return new AwrOk();
	}

	public Answer closeShift() throws Exception {
		return new AwrOk();
	}
	
	public Answer closeDoc() throws Exception {
		if (pFile==null) return new AwrOk();
//		pFile.altaln("</div>");
    pFile.altaln("</div></body></html>");
		pFile.Close();
		return new AwrOk();
	}

	public Answer cancelDoc() throws Exception {
		if (pFile==null) return new AwrOk();
		pFile.Close();
		return new AwrOk();
	}
  

	public Answer openDoc() throws Exception {
	  JTools.createDirectories(JPath.PssPathTempFiles(), this.getConnectinString());
	  String sFileName = JPath.PssPathTempFiles()+"/"+this.getConnectinString();
	  pFile = new JStreamFile();
    pFile.CreateNewFile(sFileName);    
    pFile.altaln("<html>");
    pFile.altaln("<head>");
    pFile.altaln("<title>"+JPssVersion.getPssTitle()+" - Impresión </title>");
    pFile.altaln("</head>");
//    pFile.altaln("<body onload='window.print();window.close();'>");
//    pFile.altaln("<body onload='window.print();'>");
    pFile.altaln("<body>");
    pFile.altaln("<div style=\"");
    pFile.altaln("background-image:url('/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/layout/"+this.getPrintAdapter().getBackground()+"');");
    pFile.altaln("background-repeat:no-repeat;");
    pFile.altaln("font-family:"+this.findFontFamily(this.getPrintAdapter().getFuente())+";");
    pFile.altaln("font-size:"+this.findFontSize()+";");
    pFile.altaln("width: "+this.getPageSize().getWidth()+"px; height: "+this.getPageSize().getHeight()+"px");
    pFile.altaln("\">");

//    pFile.altaln("<div style=\"" + "border:0;background-color: transparent; overflow: hidden\"" + "rows=100 cols=100>");
    return new AwrOk();
	}
	
	private int findFontSize() throws Exception {
		if (this.getPrintAdapter().getFontSize()==0) 
			return 10;
		return this.getPrintAdapter().getFontSize();
	}
	
	public Answer printLine(String line) throws Exception {
		if (pFile==null) return new AwrOk();
		this.lineX=this.getMargenIzq();
		if (line.equals("^n")) { 
			line="";
		}
		if (line.equals("^p")) {
			this.newPage(false);
			return new AwrOk();
		}
		if (line.equals("^h")) {
			this.addHeader();
			return new AwrOk();
		}
		line=this.reemplazarControles(line);
		
//		cb.beginText();
		this.showStyleText(line);
//		cb.endText();
		lineY=lineY+this.lineHeight;
		
		return new AwrOk();
	}
	
	public float getMargenIzq() throws Exception {
		return (3f*this.getSizeLetra());
	}
	
	public float getSizeLetra() throws Exception {
		return 5f;
	}

	public void newLine() throws Exception {
		if (pFile==null) return;
		pFile.altaln("<br></br>");
	}

	public Answer flush() throws Exception {
    return new AwrOk();
  }
	
  public Answer skeepLines(int lines) throws Exception {
    this.newLine(lines);
    return new AwrOk();
  }
  private void newLine( int howManyLines ) throws Exception {
    for( int i = 0; i < howManyLines; i++ ) {
    	this.newLine();
    }
  }

	private void newPage(boolean first) throws Exception {
		this.header=0;
		this.addHeader();
		lineX=3;
		lineY=12f; //830
	}
	
	private void addHeader() throws Exception {
		header += 1;//imageSize.getHeight();
//		this.addPageImage();
	}

	private String reemplazarControles(String line) throws Exception {
		return line.replace("^t", "    ");
	}

	private void showStyleText(String line) throws Exception {
			if (!line.startsWith("~"))
					line="~ ~"+line;
		
		// con style
		JStringTokenizer tks = JCollectionFactory.createStringTokenizer(line, '~');
		while (tks.hasMoreTokens()) {
			String style=tks.nextToken();
			String text=tks.nextToken();
			this.showText(style, text);
		}		
	}
	
//	private JMap<String, Object> getDefaultStyle() throws Exception {
//		JMap<String, Object> map = JCollectionFactory.createMap();
//		map.addElement("font", this.defaultFont); 
//		map.addElement("size", this.defaultSize+"");
//		map.addElement("bg", this.defaultBack);
//		map.addElement("fg", this.defaultFore);
//		return map;
//	}

	private void showText(String style, String line) throws Exception {
		if (line==null) line = "";
		JMap<String, Object> map = this.findStyle(style);

		if (this.showEspecialStyle(map, line)) 
			return;
		
		this.showImageStyle(map, line); 
		
		this.showText(map, line);
	}
	
	private JMap<String, Object> findStyle(String style) throws Exception {
		if (style==null) return null;
		if (style.trim().isEmpty()) return null;

		JMap<String, Object> map = JCollectionFactory.createMap();
		JStringTokenizer tk = JCollectionFactory.createStringTokenizer(style, ' ');
		while (tk.hasMoreTokens()) {
			String f = tk.nextToken();
			if (f.startsWith("f="))	{ 
				String font = f.substring(2);
				map.addElement("font", this.findFontFamily(font)); 
				if (this.findFontBold(font)) map.addElement("bold", true);
				if (this.findFontItalic(font)) map.addElement("italic", true);
			}
			if (f.startsWith("s=")) map.addElement("size", f.substring(2));
			if (f.startsWith("bg=")) map.addElement("bg",  f.substring(3));
			if (f.startsWith("fg=")) map.addElement("fg",  f.substring(3));
//			if (f.startsWith("img=")) map.addElement("img",  this.createImage(f.substring(4)));
			if (f.startsWith("pos=")) map.addElement("pos",  f.substring(4));
			if (f.startsWith("a=")) map.addElement("align",  f.substring(2));
		}
		return map;
	}


	private boolean showEspecialStyle(JMap<String, Object> styleMap, String line) throws Exception {
		if (styleMap==null) return false;
		Barcode barcode = (Barcode)styleMap.getElement("barcode"); 
		if (barcode==null) return false;
		if (line.isEmpty()) return true;
//		this.showBarCodeText(styleMap, barcode, line); // falla en el acrobaat reader windows
		return true;
	}
	
	private void showImageStyle(JMap<String, Object> map, String line) throws Exception {
		if (map==null) return;
		if (!map.containsKey("img")) return;
		Image i = (Image)map.getElement("img");
		if (i==null) return;
//		this.addImage(i);
	}

	private void showText(JMap<String, Object> smap, String line) throws Exception {
		if (line.isEmpty()) return;
//		line=this.decode(line);
		String font=null, size=null, pos=null;
		boolean bold=false, italic=false;
		if (smap!=null) {
			font=(String)smap.getElement("font");
			size=(String)smap.getElement("size");
			pos=(String)smap.getElement("pos");
			bold=smap.containsKey("bold");
			italic=smap.containsKey("italic");
		}

//		String fore = (String)smap.getElement("fg");
//		String back = smap.containsKey("bg")?(String)smap.getElement("bg"):null;
//		String align = (String)smap.getElement("align");
		if (pos!=null) line=line.trim();
		
//		font-family: 'Roboto', Arial, sans-serif;
//    font-size: 11px;
//    font-style: normal;
//    font-weight: normal;
//    text-decoration: none;
//    text-align: center;
		
		JRectangulo r = this.parseRectangle(pos);

		StringBuffer html = new StringBuffer();
		html.append("<div style= \" ");
		html.append("position:absolute;");
		html.append("top:"+r.getTop()+"px; ");
		html.append("left:"+(this.getMargenIzq()+this.findSpaceOffset(line)+r.getLeft())+"px; ");
		if (font!=null) html.append("font-family:"+font+";");
		if (size!=null) html.append("font-size:"+size+"; ");
		if (bold) html.append("font-weight:bold; ");
		if (italic) html.append("font-style:italic; ");
		
		html.append("\">");
		html.append(line.trim());
		html.append("</div>");
		
		pFile.Write(html.toString());
		lineX+=(line.length()*this.getSizeLetra()); // ancho de lo que se imprimio
	}
	
	private float findSpaceOffset(String line) throws Exception {
		long len = line.length();
		int s=0; 
		for (int i=0;i<len;i++) {
			if (line.charAt(i)==' ') s++;
			if (line.charAt(i)!=' ') break;
		}
		return s*this.getSizeLetra();
	}

//	private String htmlLine(String line) throws Exception {
//		StringBuffer str = new StringBuffer();
//		long len = line.length();
//		char c; boolean spaces=true;
//		for (int i=0;i<len;i++) {
//			c=line.charAt(i);
//			if (c!=' ') spaces=false; 
//			if (c==' ' && spaces) str.append("&nbsp;"); 
//			else str.append(c);
//		}
//		return str.toString();
//	}
	
	private JRectangulo parseRectangle(String position) throws Exception {
		float x = this.lineX; 
		float y = this.lineY; 
		float w=1200f; 
		float h=16f;
		String p="";
		if (position!=null) { 
			JStringTokenizer tk = JCollectionFactory.createStringTokenizer(position, ';');
			if (tk.hasMoreTokens()) {
				p=(String)tk.nextToken();
				if (!p.equals("-")) x=Float.parseFloat(p);
			}
			if (tk.hasMoreTokens()) {
				p=(String)tk.nextToken();
				if (!p.equals("-")) y=Float.parseFloat(p);
				y +=this.findHeaderOffset();
			}
			if (tk.hasMoreTokens()) {
				p=(String)tk.nextToken();
				if (!p.equals("-")) w=Float.parseFloat(p);
			} if (tk.hasMoreTokens()) { 
				p=(String)tk.nextToken();
				if (!p.equals("-")) h=Float.parseFloat(p);
			}
		}
//		y=this.getPageSize().getHeight()-y; // deberia restarle h pero habria que arreglar todos los layouts
		return new JRectangulo(x, y, x+w, y+h);
	}

	private float findHeaderOffset() throws Exception {
		return (this.header-1)*this.getImagePageSize().getHeight();
	}
	
	private Rectangle imageSize;
  private Rectangle getImagePageSize() throws Exception {
  	if (this.imageSize!=null) return this.imageSize;
  	JPrinterAdapter p = this.getPrintAdapter();
  	if (p.hasImageSize()) {
  		String size = p.getImageSize();
  		float w = Float.parseFloat(size.substring(0, size.indexOf(";")));
  		float h = Float.parseFloat(size.substring(size.indexOf(";")+1));
  		return new Rectangle(w, h);
  	}
  	return (this.imageSize=this.getPageSize());
  }
 
  private Rectangle pageSize;
  private Rectangle getPageSize() throws Exception {
  	if (this.pageSize!=null) return this.pageSize;
  	return this.pageSize=this.findPageSize();
  }
  
  private Rectangle findPageSize() throws Exception {
  	JPrinterAdapter p = this.getPrintAdapter();
  	if (!p.hasSize()) {
  		String size = p.getCustomSize();
  		float w = Float.parseFloat(size.substring(0, size.indexOf(";")));
  		float h = Float.parseFloat(size.substring(size.indexOf(";")+1));
  		return new Rectangle(w, h);
  	}
  	if (p.getSize().equals(JPrinterAdapter.LETTER)) return PageSize.LETTER;
  	if (p.getSize().equals(JPrinterAdapter.LETTER_LANDSCAPE)) return PageSize.LETTER.rotate();
  	if (p.getSize().equals(JPrinterAdapter.A4)) return PageSize.A4;
  	if (p.getSize().equals(JPrinterAdapter.OFICIO)) return PageSize.LEGAL;
  	if (p.getSize().equals(JPrinterAdapter.OFICIO_LANDSCAPE)) return PageSize.LEGAL.rotate();
  	return null;
  }

  public String findFontFamily(String font) throws Exception {
  	if (font==null || font.isEmpty()) 
  		return "courier";
  	
  	if (font.indexOf(';')!=-1) {
  		font=font.substring(0, font.indexOf(';'));
  	}
  	if (font.equals(JFonts.COURIER)) return "courier";
  	if (font.equals(JFonts.TIMES_ROMAN)) return "times_roman";
  	if (font.equals(JFonts.HELVETICA)) return "helvetica";
  	return null;
  }
  
  public boolean findFontBold(String font) throws Exception {
  	if (font==null || font.isEmpty()) return false;
  	if (font.indexOf(';')==-1) return false;
		String type=font.substring(font.indexOf(';')+1);
		return type.indexOf("b")!=-1;
  }

  public boolean findFontItalic(String font) throws Exception {
  	if (font==null || font.isEmpty())  return false;
  	if (font.indexOf(';')==-1) return false;
		String type=font.substring(font.indexOf(';')+1);
		return type.indexOf("i")!=-1;
  }

}
