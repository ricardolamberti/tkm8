package pss.www.ui;

import java.net.URLEncoder;

import pss.core.tools.JTools;
import pss.core.winUI.lists.JPlantilla;
import pss.core.winUI.responsiveControls.JFormHtmlTextAreaResponsive;
import pss.www.platform.content.generators.JXMLContent;

public class JWebHtmlTextAreaResponsive  extends JWebViewTextAreaResponsive {
	@Override
	public String getComponentType() {
		return "html_text_area_responsive";
	}
	
	boolean isweb;
	boolean formulario;
	long margenIzq;
	long margenImgSup;
	long margenImgLeft;
	long margenImgSize;
	long anchoPagina;
	String fondo;
	String mapaSource;
	JPlantilla mapaPlantilla;
	
	public static JWebHtmlTextAreaResponsive create(JWebViewComposite parent, JFormHtmlTextAreaResponsive zArea) throws Exception {
		JWebHtmlTextAreaResponsive webText=new JWebHtmlTextAreaResponsive();
		webText.takeAttributesFormControlResponsive(zArea);
		webText.setResponsive(zArea.isResponsive());
		webText.isweb = zArea.isWeb();
		webText.mapaSource = zArea.getMapaSource();
		webText.mapaPlantilla = zArea.getPlantilla();
		webText.margenIzq = zArea.getMargenIzq();
		webText.margenImgSup = zArea.getMargenImgSup();
		webText.margenImgLeft = zArea.getMargenImgLeft();
		webText.margenImgSize = zArea.getMargenImgSize();
		webText.fondo = zArea.getFondo();
		webText.formulario = zArea.isFormulario(); 
		webText.anchoPagina = zArea.getAnchoPagina();
		webText.setMinHeightResponsive(zArea.getHeight());
//		if (webText.getToolTip()==null) webText.setToolTip(zArea.getToolTipText());
		if (parent!=null) parent.addChild(zArea.getName(), webText);

		return webText;
	}
	
	
	protected String getTextForGeneration() throws Exception {
		String text = getText();
		int pos =text.indexOf("%3C");
		if (pos==-1 || pos>100)
			text = URLEncoder.encode(getText(),"ISO-8859-1").replaceAll("\\+", "%20");
		String source=JTools.replace(text, "contenteditable%3D%22true%22", "");
		return source;
	}


	public void setWeb(boolean isweb) {
		this.isweb = isweb;
	}
	
	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("isweb", isweb);
		if (!formulario) {
			zContent.setAttribute("formulario",formulario);
		} else {
			zContent.setAttribute("formulario",getText().indexOf("class%3D%22editable%22")!=-1);//autodetect
		}
		zContent.setAttribute("mapa_source",mapaSource);
		zContent.setAttribute("mapa_plantilla",(mapaPlantilla!=null)? mapaPlantilla.serialize():"[]");
		zContent.setAttribute("margen_izq",margenIzq);
		zContent.setAttribute("margen_imgsup",margenImgSup);
		zContent.setAttribute("margen_imgleft",margenImgLeft);
		zContent.setAttribute("margen_imgsize",margenImgSize);
		zContent.setAttribute("fondo",fondo==null?"":fondo);
		zContent.setAttribute("ancho_pagina",anchoPagina);
		super.componentToXML(zContent);
	}
}
