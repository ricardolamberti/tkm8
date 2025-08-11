package pss.common.documentos.docElectronico;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import pss.common.documentos.docEmail.BizDocEmail;
import pss.core.tools.JTools;


public class BizDocAdjunto extends BizDocElectronico {

	
	private BizDocEmail mail;
	
	public void setObjDocEmail(BizDocEmail v) {
		this.mail=v;
	}
	
	public BizDocEmail getObjDocEmail() {
		return this.mail;
	}

  /**
   * Constructor de la Clase
   */
  public BizDocAdjunto() throws Exception {
  }
  
  
	@Override
	public void processDelete() throws Exception {
		this.mail.getObjAdjuntos().removeStaticItem(this);
		if (!this.hasIdDoc()) return;
		super.processDelete();
//		this.notifyPadre();
	}
	
	public void embeddedImage() throws Exception {
		if (this.isTypePdf()) this.embeddedPdf();
		this.mail.getObjAdjuntos().removeStaticItem(this);
	}
	
	public void embeddedPdf() throws Exception {
		String fileName = this.getAbsoluteFileName();
		PDDocument pdf = PDDocument.load(new FileInputStream(fileName));
		PDFRenderer pdfRenderer = new PDFRenderer(pdf);
		int len=pdf.getDocumentCatalog().getPages().getCount();
		for (int i=0; i<len; i++) {
			BufferedImage image = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);
			this.getObjDocEmail().addBodyStart(this.getHtmlImage(image, "png"));
		}
		pdf.close();
	}
	
	public String getHtmlImage(BufferedImage image, String type) throws Exception {
		return "<img style=\" display: block; max-width:500px; max-height:700px; width:auto; height:auto; \"  " +
				" src=\"data:image/"+type+";base64,"+JTools.convertImageToBase24(image)+"\">";
	}



}


