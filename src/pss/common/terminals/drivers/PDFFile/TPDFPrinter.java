package pss.common.terminals.drivers.PDFFile;


import java.io.File;
import java.io.FileOutputStream;

//import com.lowagie.text.BaseColor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import pss.JPath;
import pss.common.layout.JFonts;
import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.data.files.JStreamFile;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class TPDFPrinter extends JTerminal implements JPrinterInterface {

	protected String sFileName;
	protected JStreamFile pFile;
	protected Document document;
	protected PdfContentByte cb;
	protected BaseFont defaultFont = null;
	protected int defaultSize=0;
	protected String defaultFore="000000";
	protected String defaultBack=null;
	protected float lineHeight = 9.5f;
	
	//	private PdfTemplate template;
//	private float line;
	protected int header=0;
//	private float footer;
	protected float lineX;
	protected float lineY;
	PdfWriter writer;

	public TPDFPrinter() throws Exception {
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
		/*
		 * if (pFile==null) return new AwrOk(); pFile.altaln("</textarea>"); pFile.altaln("</div></body></html>"); pFile.Close();
		 */
//		template.endText();
//		cb.endText();
//
//		cb.addTemplate(template, .5f, 0, 0, .5f, 10, line);
//		line=line-10;

		document.close();


		return new AwrOk();
	}

	public Answer cancelDoc() throws Exception {
		if (pFile==null) return new AwrOk();
		pFile.Close();
		return new AwrOk();
	}
	
	public JPrinterAdapter createPrintAdapter() throws Exception {
  	return new TPDFPrinterAdapter(this);
  }

  private Rectangle getPageSize() throws Exception {
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
  
  
  public BaseFont createFont(String font) throws Exception {
  	if (font==null || font.isEmpty()) 
  		font=JFonts.COURIER;
  	
  	String type = "";
  	if (font.indexOf(';')!=-1) {
  		type=font.substring(font.indexOf(';')+1);
  		font=font.substring(0, font.indexOf(';'));
  	}
  	if (font.equals(JFonts.COURIER)) {
  		if (type.equals("")) return this.makeFont(BaseFont.COURIER);
  		if (type.equals("b")) return this.makeFont(BaseFont.COURIER_BOLD);
  		if (type.equals("i")) return this.makeFont(BaseFont.COURIER_OBLIQUE);
  		if (type.equals("bi")) return this.makeFont(BaseFont.COURIER_BOLDOBLIQUE);
  	}
  	if (font.equals(JFonts.TIMES_ROMAN)) {
  		if (type.equals("")) return this.makeFont(BaseFont.TIMES_ROMAN);
  		if (type.equals("b")) return this.makeFont(BaseFont.TIMES_BOLD);
  		if (type.equals("i")) return this.makeFont(BaseFont.TIMES_ITALIC);
  		if (type.equals("bi")) return this.makeFont(BaseFont.TIMES_BOLDITALIC);
  	}
  	if (font.equals(JFonts.HELVETICA)) {
  		if (type.equals("")) return this.makeFont(BaseFont.HELVETICA);
  		if (type.equals("b")) return this.makeFont(BaseFont.HELVETICA_BOLD);
  		if (type.equals("i")) return this.makeFont(BaseFont.HELVETICA_OBLIQUE);
  		if (type.equals("bi")) return this.makeFont(BaseFont.HELVETICA_BOLDOBLIQUE);
  	}
//		JExcepcion.SendError("No existe font="+font);
  	return null;
  }
  
  private BaseFont makeFont(String font) throws Exception {
		return BaseFont.createFont(font, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
  }
  
  public String getFilename() throws Exception {
  	
		return this.getConnectinString()+(this.getConnectinString().toLowerCase().endsWith(".pdf")?"":".pdf");
  }

	public Answer openDoc() throws Exception {
		document=new Document();
		document.setPageSize(this.getPageSize());
		String name=this.getFilename();
		JTools.createDirectories(JPath.PssPathTempFiles(),name);
		sFileName=JPath.PssPathTempFiles()+"/"+name;
		pFile=new JStreamFile();
		FileOutputStream newFile=new FileOutputStream(sFileName);
		writer=PdfWriter.getInstance(document, newFile);

		document.open();
		this.newPage(true);
		
		defaultFont = this.createFont(this.getPrintAdapter().getFuente());
		defaultSize = this.getPrintAdapter().getFontSize();
		if (defaultSize==0) defaultSize=10;
		lineHeight = (float)this.getPrintAdapter().getLineSpacing();
		if (lineHeight==0) lineHeight=9.5f;
		cb=writer.getDirectContent();
	
		return new AwrOk();
	}

	private void newPage(boolean first) throws Exception {
		if (!first)	this.document.newPage();
		this.header=0;
		this.addHeader();
		lineX=3;
		lineY=12f; //830
	}
	
	private void addHeader() throws Exception {
//		Rectangle imageSize = 
		header += 1;//imageSize.getHeight();
		this.addPageImage();
//		if (imageSize==null) return;
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
  	return (this.imageSize=this.document.getPageSize());
  }
  
	
	private Rectangle addPageImage() throws Exception {
		String imgFile=JPath.PssPath()+"/pss/common/layout/backgrounds"+this.getPrintAdapter().getBackground();
		if (imgFile.endsWith("/")) return null;
		File oFile=new File(imgFile);
		if (!oFile.exists()) return null;
		
		Image image=Image.getInstance(imgFile);
		Rectangle imageSize=this.getImagePageSize();
		image.scaleToFit(imageSize.getWidth(), imageSize.getHeight());
		image.setAlignment(Element.ALIGN_TOP);
		image.setAbsolutePosition(0f, document.getPageSize().getHeight()-(header*imageSize.getHeight()));
		document.add(image);
		return imageSize;
	}

	private Image createImage(String simage) throws Exception {
		String imgFile=simage;//JPath.PssPath()+"/pss/common/layout/backgrounds/"+simage;
		if (imgFile.endsWith("/")) return null;
		File oFile=new File(imgFile);
		if (!oFile.exists()) return null;
		return Image.getInstance(imgFile);
	}
		
	private void addImage(JRectangulo r, Image image) throws Exception {
//		cb.endText();
		image.setAlignment(Element.ALIGN_TOP);
//		image.setAbsolutePosition(this.lineX, document.getPageSize().getHeight()-this.lineY-image.getHeight()+this.lineHeight);
		image.setAbsolutePosition(r.getLeft(), r.getTop());
		if (r.getWidth()!=1200) // no es default, tiene valores, re truch 
			image.scaleToFit(r.getWidth(), r.getHeight());
		document.add(image);
//		cb.beginText();
	}
	
	private String reemplazarControles(String line) throws Exception {
		return line.replace("^t", "    ");
	}
	
	public Answer printLine(String line) throws Exception {
		if (line.startsWith("^p")) {
			this.newPage(false);
			return new AwrOk();
		}
		if (line.startsWith("^h")) {
			this.addHeader();
			return new AwrOk();
		}

		if (line.startsWith("^n")) { 
			if (line.indexOf('-')!=-1) {
				float interline = Float.parseFloat(line.substring(line.indexOf('-')+1));
				lineY+=interline;
			}
			line="";
		}
		line=this.reemplazarControles(line);
//		cb.beginText();
		this.showStyleText(line);
//		cb.endText();
		lineY+=this.lineHeight;
		lineX=3;

		return new AwrOk();
	}
	
	
	private void showStyleText(String line) throws Exception {
		// sin style
//		if (line.indexOf('~')==-1) {
			if (!line.startsWith("~"))
					line="~ ~"+line;//this.showStyleText(null, line);
//			return;
//		}
		
		// con style
		JStringTokenizer tks = JCollectionFactory.createStringTokenizer(line, '~');
//		boolean firstConf = line.charAt(0)=='~';
		while (tks.hasMoreTokens()) {
//			if (!firstConf) {
//				this.showStyleText(null, tks.nextToken());
//				firstConf=true;
//				continue;
//			}
			String style=tks.nextToken();
			String text=tks.nextToken();
			this.showText(style, text);
		}		
	}
	
	private JMap<String, Object> getDefaultStyle() throws Exception {
		JMap<String, Object> map = JCollectionFactory.createMap();
		map.addElement("font", this.defaultFont); 
		map.addElement("size", this.defaultSize+"");
		map.addElement("bg", this.defaultBack);
		map.addElement("fg", this.defaultFore);
		map.addElement("lh", this.lineHeight);
		return map;
	}

	private JMap<String, Object> findStyle(String style) throws Exception {
		JMap<String, Object> map = this.getDefaultStyle();
		if (style==null) return map;
		if (style.trim().isEmpty()) return map;

		JStringTokenizer tk = JCollectionFactory.createStringTokenizer(style, ' ');
		while (tk.hasMoreTokens()) {
			String f = tk.nextToken();
			if (f.startsWith("f="))	map.addElement("font", this.createFont(f.substring(2))); 
			if (f.startsWith("f="))	map.addElement("barcode", this.createBarCode(f.substring(2))); 
			if (f.startsWith("s=")) map.addElement("size", f.substring(2));
			if (f.startsWith("bg=")) map.addElement("bg",  f.substring(3));
			if (f.startsWith("fg=")) map.addElement("fg",  f.substring(3));
			if (f.startsWith("img=")) map.addElement("img",  this.createImage(f.substring(4)));
			if (f.startsWith("pos=")) map.addElement("pos",  f.substring(4));
			if (f.startsWith("a=")) map.addElement("align",  f.substring(2));
		}
		return map;
	}
	
	private void showText(String style, String line) throws Exception {
		if (line==null) line = "";
		JMap<String, Object> map = this.findStyle(style);
		if (this.showEspecialStyle(map, line)) 
			return;
		
		this.showImageStyle(map, line); 
		
		this.showText(map, line);
	}
	
	private void showImageStyle(JMap<String, Object> map, String line) throws Exception {
		if (map==null) return;
		if (!map.containsKey("img")) return;
		Image i = (Image)map.getElement("img");
		if (i==null) return;
		String pos = (String)map.getElement("pos");
		JRectangulo r = this.parseRectangle(pos);
		this.addImage(r, i);
	}


//	private void showDefaultStyleText(String line) throws Exception {
//		this.showText(line, this.defaultFont, this.defaultSize, this.defaultFore, this.defaultBack);
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
		y=document.getPageSize().getHeight()-y; // deberia restarle h pero habria que arreglar todos los layouts
		return new JRectangulo(x, y, w, h);
	}
	
//	private float parsePosX(String position) throws Exception {
//		if (position==null) return this.lineX;
//		if (position.indexOf(';')==-1) 
//			return Float.parseFloat(position);
//		return Float.parseFloat(position.substring(0, position.indexOf(';')));
//	}
//
//	private float parsePosY(String position) throws Exception {
//		if (position==null) return this.lineY;
//		if (position.indexOf(';')==-1) return this.lineY;  
//		int ix1 = position.indexOf(';');
//		int ix2 = position.lastIndexOf(';');
//		String s = (ix1!=ix2)? position.substring(ix1+1, ix2):position.substring(ix1+1);
//		if (s.equals("-")) return this.lineY;
//		float y = Float.parseFloat(s);
//		y +=this.findHeaderOffset();
//		return y;
//	}


	private int getAlign(String a) throws Exception {
		if (a==null)
			return PdfContentByte.ALIGN_LEFT;
		if (a.equals("Left"))
			return PdfContentByte.ALIGN_LEFT;
		if (a.equals("Right"))
			return PdfContentByte.ALIGN_RIGHT;
		if (a.equals("Center"))
			return PdfContentByte.ALIGN_CENTER;
		return PdfContentByte.ALIGN_LEFT;
	}
	
	private float findHeaderOffset() throws Exception {
		return (this.header-1)*this.getImagePageSize().getHeight();
	}
	
	private String decode(String line) throws Exception {
		return line.replace("\\n", "\n"); // recupero los enters
	}
	
	private void showText(JMap<String, Object> smap, String line) throws Exception {
		if (line.isEmpty()) return;
		line=this.decode(line);
		BaseFont bf =(BaseFont)smap.getElement("font");
		int size = Integer.parseInt((String)smap.getElement("size"));
		String fore = (String)smap.getElement("fg");
		String back = smap.containsKey("bg")?(String)smap.getElement("bg"):null;
		String pos = (String)smap.getElement("pos");
		String align = (String)smap.getElement("align");
		if (pos!=null) line=line.trim();
//		float posX = this.parsePosX(pos);
//		float posY = this.parsePosY(pos);
//		float antesY = document.getPageSize().getHeight()-posY;
//		Rectangle j = this.parseRectangle(pos);
//		float despuesY= j.getTop();
		
		JRectangulo r = this.parseRectangle(pos);

		cb.setFontAndSize(bf, size);
		cb.setColorFill(JTools.StringToColor(fore));
		
		int alignment=this.getAlign(align);		
//		line=r.getLeft()+"/"+r.getTop()+"<>"+posX+"/"+(document.getPageSize().getHeight()-posY)+"/"+line;
//		line=r.getLeft()+"/"+r.getTop()+"<>"+r.getWidth()+"/"+r.getHeight()+"/"+line;
		Chunk chunk = new Chunk(line, new Font(bf, size));
		if (back!=null) chunk.setBackground(JTools.StringToColor(back));
//		Rectangle rec = new Rectangle(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight());
//    rec.setBorder(Rectangle.BOX);
//    rec.setBorderWidth(1);
//    cb.rectangle(rec);
		ColumnText c = new ColumnText(cb);
		c.addText(new Phrase(chunk));
		c.setSimpleColumn(r.getLeft(), r.getTop(), r.getAllWidth(), r.getAllHeight());
//		c.setSimpleColumn(0,0,100,20);
		c.setAlignment(alignment);
		c.go();
    //.getLeft(), r.getTop(), r.getWidth(), r.getHeight());
//		ColumnText.showTextAligned(cb, alignment, new Phrase(chunk), posX, document.getPageSize().getHeight()-posY, 0f);
//		ColumnText.showTextAligned(cb, alignment, new Phrase(chunk), r.getLeft(), r.getTop(), 0f);
		lineX+=cb.getEffectiveStringWidth(line, true);
	}
	
	private Barcode createBarCode(String font) throws Exception {
//		String font = (String)style.getElement("barcode");
		if (font==null) return null;
		if (font.equals(JFonts.EAN8)) {
			Barcode b = new BarcodeEAN();
			b.setCodeType(BarcodeEAN.EAN8);
			return b;
		}
		if (font.equals(JFonts.EAN13)) {
			Barcode b = new BarcodeEAN();
			b.setCodeType(BarcodeEAN.EAN13);
			return b;
		}
		if (font.equals(JFonts.CODE39)) {
			Barcode b = new Barcode39();
			b.setSize(10);
			return b;
		}
		if (font.equals(JFonts.CODE128)) {
			Barcode b = new Barcode128();
			b.setSize(10);
			return b;
		}
		return null;
	}

	private boolean showEspecialStyle(JMap<String, Object> styleMap, String line) throws Exception {
		Barcode barcode = (Barcode)styleMap.getElement("barcode"); 
		if (barcode==null) return false;
		if (line.isEmpty()) return true;
		this.showBarCodeText(styleMap, barcode, line); // falla en el acrobaat reader windows
		return true;
	}
	
	private float showBarCodeText(JMap<String, Object> styleMap, Barcode barcode, String line) throws Exception {
		String size=(String)styleMap.getElement("size");
		JStringTokenizer tks = JCollectionFactory.createStringTokenizer(size, ';');
		float isize = Float.parseFloat(tks.nextToken());
		float X = Float.parseFloat(tks.nextToken());
		float N = Float.parseFloat(tks.nextToken());
		float ink = Float.parseFloat(tks.nextToken());
    barcode.setCode(line.trim());
    barcode.setBarHeight(isize);
    if(X!=0)  barcode.setX(X);
    if(N!=0)  barcode.setN(N);
    if(ink!=0)  barcode.setInkSpreading(ink);
		String pos = (String)styleMap.getElement("pos");
//		float posX = this.parsePosX(pos);
//		float posY = this.parsePosY(pos);
		JRectangulo r = this.parseRectangle(pos);

  	Image img = barcode.createImageWithBarcode(this.cb, null, null);
  	img.setAbsolutePosition(r.getLeft(), r.getTop()-isize);
//  	img.setAbsolutePosition(r.getLeft(), r.getHeight());
//  	img.setAbsolutePosition(posX, document.getPageSize().getHeight()-(posY+isize));
//  	img.setAbsolutePosition(this.lineX, this.lineY);
  	this.lineX+=img.getPlainWidth();
  	document.add(img);
  	return isize;
	}


//	public void newLine() throws Exception {
//		if (pFile==null) return;
//		pFile.altaln("<br></br>");
//	}

	public Answer flush() throws Exception {
		return new AwrOk();
	}

	public Answer skeepLines(int lines) throws Exception {
//		this.newLine(lines);
		return new AwrOk();
	}

//	private void newLine(int howManyLines) throws Exception {
//		for(int i=0; i<howManyLines; i++) {
//			this.newLine();
//		}
//	}

}
