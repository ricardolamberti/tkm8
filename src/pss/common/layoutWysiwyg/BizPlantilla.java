package pss.common.layoutWysiwyg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Vector;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.CharEncoding;
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
import org.xhtmlrenderer.pdf.BookmarkElement;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;
import org.xml.sax.InputSource;

import com.lowagie.text.Image;

import pss.JPath;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.dynamic.BizDynamic;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.layout.JFieldSetWins;
import pss.common.layoutWysiwyg.permisos.BizOwnerPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.core.winUI.lists.JPlantilla;
import pss.www.platform.actions.resolvers.JDoLayoutActionResolver;
import pss.www.platform.applications.JWebApplication;
import pss.www.platform.applications.JWebServer;

public class BizPlantilla extends JRecord implements IPlantilla{
	protected JString pPais=new JString();
	protected JString pCompany=new JString();

  private JLong pId = new JLong();
  private JString pIdtipo = new JString();
  private JString pDescripcion = new JString();
  private JLong pMargenIzq = new JLong();
  private JLong pMargenDer = new JLong();
  private JLong pMargenTop = new JLong();
  private JLong pMargenBottom = new JLong();
  private JString pBorde = new JString();
  private JLong pPadding = new JLong();
  private JString pTipoPagina = new JString();
  private JString pOrigen = new JString();
  private JString pPlantilla = new JString();
  private JString pFondo = new JString();
  private JBoolean pImprimeFondo = new JBoolean();
  private JString pUsuario = new JString();
  private JString pNivel = new JString();
  
  
 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public long getId() throws Exception {     return pId.getValue();  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public void setIdtipo(String zValue) throws Exception {    pIdtipo.setValue(zValue);  }
  public String getIdtipo() throws Exception {     return pIdtipo.getValue();  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public void setMargenIzq(long zValue) throws Exception {    pMargenIzq.setValue(zValue);  }
  public long getMargenIzq() throws Exception {     return pMargenIzq.getValue();  }
  public void setMargenDer(long zValue) throws Exception {    pMargenDer.setValue(zValue);  }
  public long getMargenDer() throws Exception {     return pMargenDer.getValue();  }
  public void setMargenTop(long zValue) throws Exception {    pMargenTop.setValue(zValue);  }
  public long getMargenTop() throws Exception {     return pMargenTop.getValue();  }
  public void setMargenBottom(long zValue) throws Exception {    pMargenBottom.setValue(zValue);  }
  public long getMargenBottom() throws Exception {     return pMargenBottom.getValue();  }
  public void setTipoPagina(String zValue) throws Exception {    pTipoPagina.setValue(zValue);  }
  public String getTipoPagina() throws Exception {     return pTipoPagina.getValue();  }
  public void setBorde(String zValue) throws Exception {    pBorde.setValue(zValue);  }
  public String getBorde() throws Exception {     return pBorde.getValue();  }
  public void setPadding(long zValue) throws Exception {    pPadding.setValue(zValue);  }
  public long getPadding() throws Exception {     return pPadding.getValue();  }
  public void setOrigen(String zValue) throws Exception {    pOrigen.setValue(zValue);  }
  public String getOrigen() throws Exception {     return pOrigen.getValue();  }
  public void setNivel(String zValue) throws Exception {    pNivel.setValue(zValue);  }
  public String getNivel() throws Exception {     return pNivel.getValue();  }
  public void setPlantilla(String zValue) throws Exception {    pPlantilla.setValue(zValue);  }
  public String getPlantilla() throws Exception {     
  	if (pPlantilla.isNull()) {
  		pPlantilla.setValue(getPlantillaBase());
  	}
  	return pPlantilla.getValue();  
  	
  }
  public void setFondo(String zValue) throws Exception {    pFondo.setValue(zValue);  }
  public String getFondo() throws Exception {     return pFondo.getValue();  }
  public void setImprimeFondo(boolean zValue) throws Exception {    pImprimeFondo.setValue(zValue);  }
  public boolean getImprimeFondo() throws Exception {     return pImprimeFondo.getValue();  }
 
  public String getPathFondo() throws Exception {     
  	return "Fondos/"+this.pCompany.getValue()+"/"+pFondo.getValue(); 
  }

  /**
   * Constructor de la Clase
   */
  public BizPlantilla() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId);
    this.addItem( "pais", pPais);
    this.addItem( "company", pCompany);
    this.addItem( "id_tipo", pIdtipo);
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "margen_der", pMargenDer );
    this.addItem( "margen_izq", pMargenIzq );
    this.addItem( "margen_top", pMargenTop );
    this.addItem( "margen_bottom", pMargenBottom );
    this.addItem( "borde", pBorde );
    this.addItem( "separacion", pPadding );
    this.addItem( "tipo_pagina", pTipoPagina );
    this.addItem( "origen", pOrigen );
    this.addItem( "fondo", pFondo );
    this.addItem( "imprime_fondo", pImprimeFondo );
    this.addItem( "plantilla", pPlantilla );
    this.addItem( "usuario", pUsuario );
    this.addItem( "nivel", pNivel );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 18 );
    this.addFixedItem( KEY, "company", "Empresa", true, true, 15);
    this.addFixedItem( KEY, "pais", "Pais", true, true, 3);
    this.addFixedItem( FIELD, "id_tipo", "Id tipo", true, false, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 250 );
    this.addFixedItem( FIELD, "margen_der", "Margen Derecho", true, false, 18 );
    this.addFixedItem( FIELD, "margen_izq", "Margen Izquierdo", true, false, 18 );
    this.addFixedItem( FIELD, "margen_top", "Margen Superior", true, false, 18 );
    this.addFixedItem( FIELD, "margen_bottom", "Margen Inferior", true, false, 18 );
    this.addFixedItem( FIELD, "borde", "Borde", true, false, 50 );
    this.addFixedItem( FIELD, "separacion", "Separacion borde", true, false, 18 );
    this.addFixedItem( FIELD, "tipo_pagina", "Tipo Pagina", true, false, 250 );
    this.addFixedItem( FIELD, "origen", "Origen", true, false, 250 );
    this.addFixedItem( FIELD, "plantilla", "plantilla", true, false, 10000 );
    this.addFixedItem( FIELD, "fondo", "fondo", true, false, 500 );
    this.addFixedItem( FIELD, "imprime_fondo", "Imprime fondo?", true, false, 1 );
    this.addFixedItem( VIRTUAL, "usuario", "Usuario", true, false, 200 );
    this.addFixedItem( VIRTUAL, "nivel", "Nivel", true, false, 200 );
      }
  /**
   * Returns the table name
   */
  public String GetTable() { return "LYT_PLANTILLA"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void execProcessATodos() throws Exception {
		JExec oExec = new JExec() {
			public void Do() throws Exception {
				processATodos();
			}
		};
		oExec.execute();
	}

	
	private void processATodos() throws Exception {
		BizOwnerPlantilla pl = new BizOwnerPlantilla();
		pl.dontThrowException(true);
		if (pl.Read(getIdtipo(), "TODOS")) return;
		pl.setIdtipo(getIdtipo());
		pl.setBorrable(false);
		pl.setModificable(false);
		pl.setOwner("TODOS");
		pl.processInsert();
	}

	private void darPermisosAUsuarioLocal() throws Exception {
		BizOwnerPlantilla pl = new BizOwnerPlantilla();
		pl.dontThrowException(true);
		if (pl.Read(getIdtipo(), BizUsuario.getUsr().GetUsuario())) return;
		pl.setIdtipo(getIdtipo());
		pl.setBorrable(true);
		pl.setModificable(true);
		pl.setOwner(BizUsuario.getUsr().GetUsuario());
		pl.processInsert();
	}
	
	public boolean canModificar() throws Exception {
		BizOwnerPlantilla pl = new BizOwnerPlantilla();
		pl.dontThrowException(true);
		if (pl.Read(getIdtipo(), "TODOS")) {
			if (pl.isModificable()) return true;
		}
		pl.clearFilters();
		if (pl.Read(getIdtipo(), BizUsuario.getUsr().GetUsuario())) {
			if (pl.isModificable()) return true;
		}
		return false;
	}
	public boolean canBorrar() throws Exception {
		BizOwnerPlantilla pl = new BizOwnerPlantilla();
		pl.dontThrowException(true);
		if (pl.Read(getIdtipo(), "TODOS")) {
			if (pl.isBorrable()) return true;
		}
		pl.clearFilters();
		if (pl.Read(getIdtipo(), BizUsuario.getUsr().GetUsuario())) {
			if (pl.isBorrable()) return true;
		}
		return false;
	}

  /**
   * Default Read() method
   */

  public boolean ReadByDescripcion( String company,String zDescr) throws Exception { 
    this.addFilter( "company",  company ); 
    this.addFilter( "descripcion",  zDescr ); 
    return this.read(); 
  } 
  public boolean ReadByIdTipo( String company,String zIdtipo) throws Exception { 
    this.addFilter( "company",  company ); 
    this.addFilter( "id_tipo",  zIdtipo ); 
    return this.read(); 
  } 
  public boolean ReadById( String company,long zId) throws Exception { 
    this.addFilter( "company",  company ); 
    this.addFilter( "id",  zId ); 
    return this.read(); 
  } 
  public boolean Read( long zId) throws Exception { 
    this.addFilter( "id",  zId ); 
    return this.read(); 
  } 
  public boolean Read( String zIdtipo) throws Exception { 
    this.addFilter( "id_tipo",  zIdtipo ); 
    return this.read(); 
  }
  public JPlantilla getDatosPlantilla() throws Exception {  
  	return JDoLayoutActionResolver.getDatosPlantilla(getClaseOrigenDatos(), JFieldSetWins.SECTOR_MAIN);
  }

  
  public long getAnchoEditor() throws Exception {
  	double mmtopx = 3.8;
  	long mi =(long)((getMargenIzq()+(getPadding()*10))*mmtopx);
  	long ap =(long)(getAnchoPagina(getTipoPagina())*mmtopx);
  	return ap-mi;
  }

  public long getMargenIzqEditor() throws Exception {
  	double mmtopx = 3.8;
  	long mi =(long)((getMargenIzq()+(getPadding()*10))*mmtopx);
  	return mi/2;
 	
  }
  public long getMargenImgLeftEditor() throws Exception {
  	double mmtopx = 1.5;
  	long mi =(long)((getMargenIzq())*mmtopx);
  	return mi;
 	
  }
  public long getMargenImgRightEditor() throws Exception {
  	double mmtopx = 3.8;
  	long mi =(long)((getMargenDer())*mmtopx);
  	return mi;
 	
  }
  public long getMargenImgSupEditor() throws Exception {
  	double mmtopx = 3.8;
  	long mi =(long)((getMargenTop())*mmtopx);
  	return mi;
 	
  }
  public long getMargenImgSizeEditor() throws Exception {
  	double mmtopx = 3.9;
  	long ap =(long)((getAnchoPagina(getTipoPagina()) )*mmtopx);
  	return ap;
 	
  }
  public String getPlantillaBase() throws Exception {
  	String colors[] = {
  			"56c3e8","e5b7bf","e5b7e1","ccb7e5","b7e5e4",
  			"b7e5c6","e5e1b7","e5ceb7","e5b7b7","aaff90",
  			"4c5dff","c2b2aa","8caa03","349998","7fa67d",
  			"d20ac2","cfcdcf","8995a3","53723f","f9e21b",
  			"bb6858","029e3d","f3f0c2","fffc02","ff02af"};
  	if (getOrigen().equals("")) throw new Exception("Especifique la fuente de datos");

  	String base="";
		base +="<br/><br/><br/><br/><br/>";
  	return base;
  }
  
  public static String getTipoPaginaCSS3(String tipo) throws Exception {
  	if (tipo.equals("A4")) return "A4";
  	if (tipo.equals("A4A")) return "A4 landscape";
  	if (tipo.equals("O")) return "Legal";
  	if (tipo.equals("OA")) return "Legal landscape";
  	if (tipo.equals("S")) return "230mm 145mm";
  	return "A4";
  }
	public long getLargoPaginaCSS3() throws Exception {
		return BizPlantilla.getLargoPagina(getTipoPagina());
	}

  public static long getAnchoPagina(String tipo) throws Exception {
  	if (tipo.equals("A4")) return 210;
  	if (tipo.equals("A4A")) return 297;
  	if (tipo.equals("O")) return 215;
  	if (tipo.equals("OA")) return 355;
  	if (tipo.equals("S")) return 230;
  	return 210;
  }
  public static long getLargoPagina(String tipo) throws Exception {
  	if (tipo.equals("A4")) return 297;
  	if (tipo.equals("A4A")) return 210;
   	if (tipo.equals("O")) return 355;
   	if (tipo.equals("OA")) return 215;
  	if (tipo.equals("S")) return 145;
  	     	return 210;
  } 
	private static JMap<String, String> hCacheBorde = null; 
  public static JMap<String, String> getBordes() throws Exception {
  	if (hCacheBorde==null) {
  		JMap<String, String> map = JCollectionFactory.createOrderedMap();
  		map.addElement("thin solid black", "Linea negra");
  		map.addElement("thin solid red", "Linea roja");
  		map.addElement("thin solid blue", "Linea azul");
  		map.addElement("thin dotted black", "Linea punteada negra");
  		map.addElement("thin dotted red", "Linea punteada roja");
  		map.addElement("thin dotted blue", "Linea punteada azul");
  		map.addElement("thin double black", "Linea doble negra");
  		map.addElement("thin double red", "Linea doble roja");
  		map.addElement("thin double blue", "Linea doble azul");
  		map.addElement("thick solid black", "Linea Gruesa negra");
  		map.addElement("thick solid red", "Linea Gruesa roja");
  		map.addElement("thick solid blue", "Linea Gruesa azul");
  		
  		
  		hCacheBorde=map;
  	}
  	return hCacheBorde;
  }
	private static JMap<String, String> hCacheTiposPagina = null; 
  public static JMap<String, String> getTiposPagina() throws Exception {
  	if (hCacheTiposPagina==null) {
  		JMap<String, String> map = JCollectionFactory.createOrderedMap();
  		map.addElement("A4", "A4");
  		map.addElement("A4A", "A4 Apaisado");
  		map.addElement("O", "Oficio");
  		map.addElement("OA", "Oficio Apaisado");
  		map.addElement("S", "Sobre");
  		hCacheTiposPagina=map;
  	}
  	return hCacheTiposPagina;
  }
 
  public static JMap<String, String> getNivelDatos(String origen) throws Exception {
  	return BizUsuario.getUsr().getObjBusiness().getNivelDatos(origen);
  }
  public static JMap<String, String> getOrigenDatos() throws Exception {
  	return BizUsuario.getUsr().getObjBusiness().getOrigenDatos();
  }
  
  public static JMap<String, String> getClassOrigenDatos() throws Exception {
  	return BizUsuario.getUsr().getObjBusiness().getClassOrigenDatos();
  }
    
  public String getClaseOrigenDatos() throws Exception {
  	if (getNivel().equals("")) setNivel(getOrigen());
  	return getClassOrigenDatos().getElement(getNivel());
  }
	private static JMap<String, String> hCache = null; 
  public static JMap<String, String> getTipos() throws Exception {
  	if (hCache==null) {
  		JMap<String, String> map = JCollectionFactory.createOrderedMap();
  		JRecords<BizPlantilla> recs = new JRecords<BizPlantilla>(BizPlantilla.class);
  		recs.readAll();
  		while (recs.nextRecord()) {
  			BizPlantilla t = recs.getRecord();
  			map.addElement(t.getIdtipo(), t.getDescripcion());
  		}
  		hCache=map;
  	}
  	return hCache;
  }
  
  public static String getDescrTipo(String tipo) throws Exception {
  	try {
  		return BizPlantilla.getTipos().getElement(tipo);
  	} catch (Exception e) {
  		return "Desconocido";
  	}
  }

	@Override
	public void processInsert() throws Exception {
		if (pPais.isNull()) pPais.setValue(BizUsuario.getUsr().getCountry());
		if (pPlantilla.isNull()) {
			pPlantilla.setValue(getPlantillaBase());
		}
		super.processInsert();
		pId.setValue(getIdentity("id"));
		this.setIdtipo(""+pId.getValue());
		processUpdate();
		
		darPermisosAUsuarioLocal();
	}
  
  @Override
  public void processUpdate() throws Exception {
  	String texto=getPlantilla();
  	if (texto.equals("")) {
  		texto = getPlantillaBase();
  	}
  	pPlantilla.setValue(texto);
  	super.processUpdate();
  }

  transient DocumentBuilder objDb;
	public DocumentBuilder getDocumentBuilder() throws Exception {
		 if (objDb!=null) return objDb;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	   	
	  	dbf.setValidating(false);
	  	dbf.setNamespaceAware(true);
	  	dbf.setIgnoringComments(false);
	  	dbf.setIgnoringElementContentWhitespace(false);
	  	dbf.setExpandEntityReferences(false);
	  	
	  	DocumentBuilder db = dbf.newDocumentBuilder();
	  	return objDb=db;
	}
	
  public String render(String plantilla, JRecord[] origen, int posOrigen, boolean preserveTags) throws Exception {
   	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
   	
  	dbf.setValidating(false);
  	dbf.setNamespaceAware(true);
  	dbf.setIgnoringComments(false);
  	dbf.setIgnoringElementContentWhitespace(false);
  	dbf.setExpandEntityReferences(false);
  	
  	DocumentBuilder db = getDocumentBuilder();
  	String source = plantilla;
  	source = source.replaceAll("<br>", "<br/>");
  	source = source.replaceAll("<hr>", "<hr/>");
  	source = source.replaceAll("<BR>", "<br/>");
  	source = source.replaceAll("<HR>", "<hr/>");
  	source = source.replaceAll("&nbsp;", " ");
//<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML//EN">
  	Document doc;
  	Node n;
  	Node p;
  	Document newdoc;
//  	System.out.println(source);
  	try {
  		doc = db.parse(new InputSource(new StringReader(source)));
  		p=doc.getDocumentElement();
  	} catch (Exception e) {
    	doc = db.parse(new InputSource(new StringReader("<div>"+source+"</div>")));
  		p=doc.getFirstChild();
  	}
  	newdoc = db.newDocument();
  	n = newdoc.appendChild(newdoc.createElement("DIV"));
  	
   	doRender(newdoc,n,p,origen,posOrigen,preserveTags);
   	
   	return JTools.generarString(newdoc);
  }
  
  private String trimHTML(String htlm) {
  	String out = "";
  	int in = 0;
  	for (int i = 0; i < htlm.length(); i++) {
  		if (htlm.charAt(i)=='<') in++;
  		else if (htlm.charAt(i)=='>') in--;
  		else if (in==0) out+=htlm.charAt(i);
  	}
  	return out;
  }
  
	public String getImage(String zImage) throws Exception {
		String image = zImage;
		if (image==null || image.equals(""))
			image = "refresh.jpg";
		else if (image.startsWith("script:")) {
			return image;
		}
		byte[] content = JTools.readFileAsArrayByte(JPath.PssImages() +"\\" + image);
		return "data:image/jpg;base64,"+(java.util.Base64.getEncoder().encodeToString(content).replaceAll("\r\n", ""));
	}

  private String doRenderBase(String texto,JRecord[] origen,int posOrigen, int width, int heigth,boolean preserveTags) throws Exception {
  	String s ="";
		boolean used = false;
		int startTest=0;
  	while (true) {
  		int posI = texto.indexOf("{*",startTest);
  		if (posI==-1) break;
  		int posF = texto.indexOf("}",posI+1);
  		if (posF==-1) break;
  		String name = texto.substring(posI+2,posF);
  		name = trimHTML(name);
  		s+=texto.substring(startTest,posI);
  		if (name.startsWith("$")) {
  			if (name.equals("$_hoy_larga")) {
  				s+=JDateTools.formatFechaLarga(new Date());
  			}else if (name.equals("$_hoy_corta")) {
  				s+=JDateTools.formatFechaMediana(new Date());
	  		}	else if (name.equals("$_nombre_usuario")) {
	  			s+=BizUsuario.getUsr().GetDescrip();
	  		}	else if (name.equals("$_email_usuario")) {
	  			s+=BizUsuario.getUsr().getEMailAddress();
	  		}	else if (name.equals("$_usuario")) {
	  			s+=BizUsuario.getUsr().getDescrUsuario();
	  		} else if (name.equals("$_dniusuario")) {
	  			s+=BizUsuario.getUsr().getIdPersona()==0?"":BizUsuario.getUsr().getObjPersona().GetNroDoc();
	  		}	else if (name.equals("$_ident_adicional")) {
	  			s+=BizUsuario.getUsr().getIdPersona()==0?"":BizUsuario.getUsr().getObjPersona().getMatricula();
  			} 
  			used=true;
  		}else {
  			int newPosOrigen= posOrigen;
  			if (name.startsWith("n0_")) {
  				newPosOrigen = 0;
  				name = name.substring(3);
  			}
  			if (name.startsWith("n1_") && posOrigen>=1) {
  				newPosOrigen = 1;
  				name = name.substring(3);
  			}
  			if (name.startsWith("n2_") && posOrigen>=2) {
  				newPosOrigen = 2;
  				name = name.substring(3);
  			}
  			if (name.startsWith("n3_") && posOrigen>=3) {
  				newPosOrigen = 3;
  				name = name.substring(3);
  			}
  			for (int pos=newPosOrigen;pos>=0;pos--) {
  				used = false;
					if (origen[pos] == null) continue;
					if (origen[pos] instanceof BizDynamic) {
						if (name.indexOf("campo_alineado_")!=-1) {
							JObject obj = origen[pos].getProperties().getElement(name.substring(15).toLowerCase());
							if (obj == null)
								continue;
							if (Number.class.isAssignableFrom(obj.getObjectClass())) 
								s += "html:<div><p align=\"right\">"+obj.toFormattedString()+"</p></div>";
							else
								s += obj.toFormattedString();							
						  used=true;
						} else if (name.indexOf("campo_")!=-1) {
							int number = Integer.parseInt(JTools.getNumberEmbedded(name));
							Object claves[] = origen[pos].getProperties().getKeys();
							if (number-1>=claves.length) 
								continue;
							String clave = (String)claves[number-1];
							JObject obj = origen[pos].getProperties().getElement(clave);
							if (obj == null)
								continue;
							s += obj.toFormattedString();
							used=true;
						} else if (name.indexOf("eje_")!=-1) {
							int number = Integer.parseInt(JTools.getNumberEmbedded(name));
							Object claves[] = origen[pos].getProperties().getKeys();
							if (number-1>=claves.length) 
								continue;
							
							String clave = (String)claves[number-1];
							JObject obj = origen[pos].getProperties().getElement(clave);
							if (obj == null)
								continue;
							s += obj.toFormattedString();
							used=true;
						} else if (name.indexOf("x_corte")!=-1) {
							s+= (((BizDynamic)origen[pos]).getValorCorte()!=null)?"S":"N";
							used=true;
						}  else if (name.indexOf("x_total_")!=-1) {
							String campo = name.substring(8);
							if (((BizDynamic)origen[pos]).getValorCorte()!=null)
								s += "html:<div><p align=\"right\">"+(((BizDynamic)origen[pos]).getValorCorte().getValor(campo))+"</p></div>";
							used=true;
						}  else {
							JObject obj = origen[pos].getProperties().getElement(name);
							if (obj == null)
								continue;
							s += obj.toFormattedString();
							used=true;
						}

					}
					else if (origen[pos] instanceof BizCustomList) {
						if (name.indexOf("titulo_listado")!=-1) {
							s += ((BizCustomList)origen[pos]).getDescription().toUpperCase();
							used=true;
						}  else if (name.indexOf("titulo_alineado_")!=-1) {
							int number = Integer.parseInt(JTools.getNumberEmbedded(name));
							BizCampo campo = ((BizCustomList)origen[pos]).getCampo(number);
							if (campo == null)
								continue;
							String ss = campo.getNombreColumna().equals("")?(campo.getCampo().equals("")?(campo.isPorcentaje()?"Porcentaje":campo.getFuncion()):campo.getDescrCampo()):campo.getNombreColumna();
							if (campo.getCampo().equals("COUNT") ||  campo.getCampo().equals("FORMULA") ||Number.class.isAssignableFrom(campo.getObjRecordOwner().getProp(campo.getCampo()).getObjectClass()))
								s += "html:<div><p align=\"right\">"+ss+"</p></div>";
							else
								s += ss;
							used=true;
						} else if (name.indexOf("titulo")!=-1 && !JTools.getNumberEmbedded(name).equals("")) {
							int number = Integer.parseInt(JTools.getNumberEmbedded(name));
							BizCampo campo = ((BizCustomList)origen[pos]).getCampo(number);
							if (campo == null)
								continue;
							s += campo.getNombreColumna().equals("")?(campo.getCampo().equals("")?(campo.isPorcentaje()?"Porcentaje":campo.getFuncion()):campo.getFixedProp().GetDescripcion()):campo.getNombreColumna();
							used=true;
						} else if (name.indexOf("grafico")!=-1) {
							int number = Integer.parseInt(JTools.getNumberEmbedded(name));
//							BizCampo campo = ((BizDocListado)origen[pos]).getObjCustomList().getCampo(number);
//							if (campo == null)
//								continue;
							BizCustomList custom = ((BizCustomList)origen[pos]);
							if (custom!=null) {
								String img = custom.getImageInterno();
								if (img!=null) {
									if (img.startsWith("data:")) s+= "html:<img src=\""+img+"\"/>";
									if (img.startsWith("script:")) s+="html:<img src=\""+JTools.convertScript2Image(img.substring(7),600,600)+"\" width=\"100%\" height=\"100%\"/>";
									else s+=img;
									used=true;
								}
							}
						}
					}
					if (!used) {
						JObject obj = origen[pos].getPropDeep(name, false);
						if (obj == null)
							continue;
						used=true;
						String decoded =  obj.toFormattedString();
						if (decoded==null) {
							s+="";
						} else if (decoded.indexOf("graph_title")!=-1) {
							s += "html:<div><div>"+JTools.replaceForeignCharsForWeb(decoded)+"</div></div>";
									
							
						} else {
//							try {
//								decoded = obj.toFormattedString()==null?"":obj.toFormattedString().startsWith("data:image/")?obj.toFormattedString():decoded;
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
							if (decoded.toUpperCase().indexOf("<DIV")!=-1)
								s += JTools.replaceForeignCharsForWeb(obj.toFormattedString());
							else
								s += decoded;
						}
					}
					break;
				}
			}
  		if(preserveTags && !used) {
  			s+="{*"+name+"}";
  		}
			startTest = posF+1;
  	}
		s+=texto.substring(startTest);
		
  	return s;
  }
  int lastWidth =0;
  int lastHeight=0;
	private void doRender(Document outDoc, Node output,Node nodoPadre, JRecord[] origen, int posOrigen,boolean preserveTags) throws Exception {
		Node newNode=null;
		if (nodoPadre instanceof Element) {
			Element padre = (Element) nodoPadre;
		  if (padre.getTagName().equalsIgnoreCase("div")) {
			//	String name = padre.getAttribute("id");
				newNode = output.appendChild(outDoc.createElement("div"));
				lastWidth=(int)JTools.getLongFirstNumberEmbedded(padre.getAttribute("width"));
				lastHeight=(int)JTools.getLongFirstNumberEmbedded(padre.getAttribute("height"));
//				if (name.equals("")) ((Element)newNode).setAttribute("style", padre.getAttribute("style"));
				for (int i=0;i<padre.getAttributes().getLength();i++) {
					((Element)newNode).setAttribute(padre.getAttributes().item(i).getNodeName(), padre.getAttributes().item(i).getNodeValue());
				}

			} else if (padre.getTagName().equalsIgnoreCase("img")) {
				String name = padre.getAttribute("src");
				int w=(int)JTools.getLongFirstNumberEmbedded(padre.getAttribute("width"));
				int h=(int)JTools.getLongFirstNumberEmbedded(padre.getAttribute("height"));
				if (w==0) w =200;
				if (h==0) h =200;
				if (name.startsWith("{")) {
					newNode = output.appendChild(outDoc.createElement("img"));
					String valor = doRenderBase( name, origen, posOrigen,w,h,preserveTags);
					if (!valor.startsWith("data:image")) valor = getImage(valor);
					((Element)newNode).setAttribute("src", valor );
				} else {
					newNode = output.appendChild(outDoc.importNode(padre, false));
					
				}

			}	else if (padre.getTagName().equalsIgnoreCase("a")) {
				String name = padre.getAttribute("href");
				String type = padre.getAttribute("target");
				if (name.startsWith("{")) {
					newNode = output.appendChild(outDoc.createElement("a"));
					String valor = doRenderBase( name, origen, posOrigen,0,0,preserveTags);
					((Element)newNode).setAttribute("href", valor );
					((Element)newNode).setAttribute("target", type );
				} else {
					newNode = output.appendChild(outDoc.importNode(padre, false));
					
				}

			}	else if (padre.getTagName().equalsIgnoreCase("body"))
				newNode = output.appendChild(outDoc.createElement("div"));
			else
				newNode = output.appendChild(outDoc.importNode(padre, false));
			if (padre.getNodeValue() != null)
				newNode.setNodeValue(doRenderBase(padre.getNodeValue(), origen, posOrigen,lastWidth,lastHeight,preserveTags));
		} else {
			if (nodoPadre.getNodeValue() != null) {
				String out = doRenderBase(nodoPadre.getNodeValue(), origen, posOrigen,lastWidth,lastHeight,preserveTags);
				if (out.startsWith("html:")) {
					newNode = output.appendChild(outDoc.importNode(String2XML(out.substring(5)).getFirstChild(), true));
				} else if (out.indexOf("html:<")!=-1) {
					newNode = output.appendChild(outDoc.importNode(String2XML(JTools.replace(out, "html:<","<")).getFirstChild(), true));

				}else if (out.startsWith("script:")) {
					newNode = output.appendChild(outDoc.importNode(String2XML("<div>"+out.substring(7)+"</div>").getFirstChild(), true));
				}
				else {
						if (out.indexOf("<div")!=-1 || out.indexOf("<br")!=-1) {
							newNode = output.appendChild(outDoc.importNode(String2XML(out).getFirstChild(), true));
						}	else {
							newNode = output.appendChild(outDoc.importNode(nodoPadre, false));
							newNode.setNodeValue(out);
						}

				}
			}
		}
		NodeList nodeList = nodoPadre.getChildNodes();
		if (newNode==null || nodeList == null || nodeList.getLength() == 0)
			return;

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node n = (Node) nodeList.item(i);
			doRender( outDoc, newNode, n, origen, posOrigen, preserveTags);
		}

		return;
	}
	
	public Document String2XML(String fragment) throws Exception {
		DocumentBuilder db=getDocumentBuilder();
  	String source = fragment;
  	source = source.replaceAll("<br>", "<br/>");
  	source = source.replaceAll("<hr>", "<hr/>");
  	source = source.replaceAll("<BR>", "<br/>");
   	source = source.replaceAll("<HR>", "<hr/>");
    	source = source.replaceAll("&nbsp;", " ");
  	source = JTools.replaceWebForForeignChars(source);
//  	source = source.replaceAll("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">", "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
//  	source = source.replaceAll("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">", "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
//  	source = source.replaceAll("<meta name=\"Generator\" content=\"Microsoft Exchange Server\">", "<meta name=\"Generator\" content=\"Microsoft Exchange Server\"/>");
//  	
//	 	source = source.replaceAll("<hr id=\"stopSpelling\">","<hr id=\"stopSpelling\"/>");
	 	source=reparacionBugsDeExplorer(source);
  	Document doc;
  	try {
  		doc = db.parse(new InputSource(new StringReader(source)));
  	} catch (Exception e) {
  		PssLogger.logInfo(source);
    	doc = db.parse(new InputSource(new StringReader("<div>"+source+"</div>")));
  	}
 	
   	return doc;
  
}
  public String preRender(JRecord[] origenes, int posOrigen, String forcePlantilla) throws Exception {
  	String plantilla = forcePlantilla;
  	if (plantilla==null) plantilla = getPlantilla();
  	String quitaru = plantilla.replaceAll("%u2013", "-");
  	quitaru = quitaru.replaceAll("%u201C", "\"");
  	quitaru = quitaru.replaceAll("%u201D", "\"");
  	//quitaru.substring(quitaru.indexOf("%u"));
  	
  	quitaru = quitaru.replaceAll("%u....", "*");
  	String source = (quitaru.indexOf("%20")==-1)?quitaru:(new URLCodec()).decode(quitaru,"utf-8");
  	if (source.indexOf("%20")!=-1) try {source = (new URLCodec()).decode(source,"utf-8");} catch (Exception e) {}
  	if (source.indexOf("%20")!=-1) try {source = (new URLCodec()).decode(source,"utf-8");} catch (Exception e) {}
  	source = source.startsWith("<body")?source:"<body>"+source+"</body>";
  	source = source.replaceAll("<br>", "<br/>");
  	source = source.replaceAll("<BR>", "<br/>");
  	source = replaceAllmenormayor(source);
  	source = source.replaceAll("&nbsp;", " ");
  	String out = reparacionBugsDeExplorer(source);
  	if (origenes!=null) out = doPreRender(out, origenes, posOrigen,false);
   	return out;
  }
  
  
  public String posRender(String source) throws Exception {
  	int posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("[[",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf("]]",posI+1);
  		String name = source.substring(posI+2,posIF);
  		if (name.toLowerCase().startsWith("si ")) {
    		int posIEND = source.indexOf("[[",posIF+1);
    		if (posIEND==-1) break;
    		int posIFEND = source.indexOf("]]",posIEND+1);
    		String nameEnd = source.substring(posIEND+2,posIFEND);
    		if (nameEnd.toLowerCase().startsWith("finsi")) {
    			source = evaluarSI(source,name.substring(3),source.substring(posIF+2,posIEND),quitarTextos(source.substring(posIF+2,posIEND)),source.substring(posI,posIFEND+2));
    			posInicial=0;
    		} else {
    			posInicial=posIEND;
    		}
  		} else
  			posInicial=posIF+1;

//  		posInicial= posIF+1;
  	}
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("[[",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf("]]",posI+1);
  		String name = source.substring(posI+2,posIF);
  		String contenido = source.substring(posI,posIF+2);
  		if (name.toLowerCase().startsWith("calcular ")) {
   			source = source.replace(contenido, evaluarCalculate(name.substring(8)));
   			posInicial=0;
   		} else {
   			posInicial=posIF+2;
   		}
 		}

  	// quitar comentarios
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<!--",posInicial);
  		if (posI==-1) break;
  		int l = "<!--".length();
  		int posIF = source.indexOf("-->",posI+1);
  		String contenido = source.substring(posI,posIF+3);
  		source = JTools.replace(source,contenido, "");
  		posInicial= posI+1;
  	}

  	return source;
  }
  public String replaceAllmenormayor(String texto) {
  	String out ="";
  	boolean dentro = false;
  	for (int i=0;i<texto.length();i++) {
  		if (i+1<texto.length() && texto.charAt(i)=='[' && texto.charAt(i+1)=='[') {
  			dentro = true;
  		}
  		if (i+1<texto.length() && texto.charAt(i)==']' && texto.charAt(i+1)==']') {
  			dentro = false;
  		}
 			if (i+3<texto.length() && texto.charAt(i)=='&' && texto.charAt(i+1)=='g'&& texto.charAt(i+2)=='t'&& texto.charAt(i+3)==';' &&!dentro) {
 				out+=">";
 				i+=3;
 			}
 			else if (i+3<texto.length() && texto.charAt(i)=='&' && texto.charAt(i+1)=='l'&& texto.charAt(i+2)=='t'&& texto.charAt(i+3)==';' &&!dentro) {
 				out+="<";
 				i+=3;
 			}
 			else out+=texto.charAt(i);
  	}
  	return out;
  }
  
  public String quitarTextos(String texto) {
  	String out ="";
  	boolean dentro = false;
  	for (int i=0;i<texto.length();i++) {
  		if (texto.charAt(i)=='<' && !dentro) {
  			dentro = true;
  			out+=texto.charAt(i);
  		}
  		else if (texto.charAt(i)=='>' && dentro) {
  			dentro = false;
  			out+=texto.charAt(i);
  		}
  		else {
  			if (dentro) out+=texto.charAt(i);
  		}
  	}
  	out=JTools.replace(out,"<hr/>", "");
  	return out;
  }
  
  public String evaluarSI(String texto,String condicion,String siOk,String siFalse,String area) {
  	if (evaluar(trimHTML(condicion))) 
  		texto = texto.replace(area, siOk);
  	else 
  		texto = texto.replace(area, siFalse);
  	return texto;
  }
  public String evaluarCalculate(String c) throws Exception {
  	String condicion = trimHTML(c);
    try {
    	condicion = condicion.replaceAll("&gt;", ">");
    	condicion = condicion.replaceAll("&lt;", "<");
    	condicion = condicion.replaceAll("&amp;", "&");
    	
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");        
//			String result = (String) engine.eval(condicion).toString();
    // USO: [[calcular return Fecha("01/03/2014").addMonth(6).ddmmyyyy(); ]]

	    String  script;
	    script = "function Fecha(str){var parts = str.split('/');var dt = new Date(parseInt(parts[2], 10), parseInt(parts[1], 10) - 1,parseInt(parts[0], 10)); return dt;}";
	    engine.eval(script);
      script = "Date.prototype.ddmmyyyy=function(){var dd=this.getDate();if(dd<10)dd='0'+dd;var mm=this.getMonth()+1;if(mm<10)mm='0'+mm;var yyyy=this.getFullYear();return String(dd+'/'+mm+'/'+yyyy) }";
      engine.eval(script);
      script = "Date.prototype.addMonth=function(mes){this.setMonth(this.getMonth()+mes); return this }";
      engine.eval(script);
	    script = "function ejecutar() {  "+condicion+" };";
      engine.eval(script);
       

       script = "ejecutar();";
       // evaluate script
       String result;
       Object o = engine.eval(script);
       result = o.toString();
  
			return result;
		} catch (ScriptException e) {
			PssLogger.logError(e);
	  	return e.getMessage();
		}
  }
  public boolean evaluar(String condicion) {
    try {
    	condicion = condicion.replaceAll("&gt;", ">");
    	condicion = condicion.replaceAll("&lt;", "<");
    	condicion = condicion.replaceAll("&amp;", "&");
    	
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");        
			Boolean result = (Boolean) engine.eval(condicion);
			return result;
		} catch (ScriptException e) {
			PssLogger.logError(e);
	  	return false;
		}
  }
  private Vector<String> generateMapCC(String source, Vector<String> vector) throws Exception {
  	if (vector==null) vector = new Vector<String>();
  	String last="";
  	int posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<!--CC:",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf("-->",posI+1);
  		String name = source.substring(posI+7,posIF);
  		
  		int posEnd = source.indexOf("<!--CCEND:"+name,posInicial);
  		if (posEnd==-1) break;
  		generateMapCC(source.substring(posI+7+name.length()+3, posEnd-1),vector);
  		if (!last.equals(name))
  			vector.add(name);
  		last=name;
  		posInicial= posEnd+1;
  		
  	}
  	return vector;
  }
  private Vector<String> generateMap(String source, Vector<String> vector) throws Exception {
  	if (vector==null) vector = new Vector<String>();
  	String last="";
  	int posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<!--DATA:",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf("-->",posI+1);
  		String name = source.substring(posI+9,posIF);
  		
  		if (name.startsWith("!")) 
    		if (!last.equals(name))
    			vector.add(name);
  			
  		int posEnd = source.indexOf("<!--DATAEND:"+name,posInicial);
  		generateMap(source.substring(posI+9+name.length()+3, posEnd-1),vector);
   		if (!name.startsWith("!")) 
   		  	if (!last.equals(name))
   		  			vector.add(name);
  		last=name;
  		posInicial= posEnd+1;
  		
  	}
  	return vector;
  }
  
  public static String reparacionBugsDeExplorer(String source) throws Exception {
  	// asegura que los parametros tengan comillas
  	int posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf(">",posI+1);
  		String div = source.substring(posI,posIF+1);
    	if (!div.startsWith("<!") && !div.startsWith("<img") && !div.startsWith("<meta")) {
	  		int posInicial2 = posI;
		    while (true) {
		  		int posIgual = source.indexOf("=",posInicial2);
		    	int posEndIgualF=posIgual;
		  		if (posIgual==-1 || posIgual>posIF) break;
		  	  if (source.charAt(posIgual+1)=='\'') {
		    		int posEndIgual = source.indexOf("'",posIgual+2);
		    		int posEndIgual2 = source.indexOf(">",posIgual+1);
		    		if (posEndIgual!=-1) {
			     		String param = source.substring(posIgual+2,posEndIgual);
			    		if (posEndIgual<posEndIgual2) 
			     	 		source = source.substring(0,posIgual+1)+"\""+param+"\""+source.substring(posEndIgual+1);
		    		}
		  	  }
		  	  else if (source.charAt(posIgual+1)!='\"') {
		    		int posEndIgual = source.indexOf(" ",posIgual+1);
		    		int posEndIgual2 = source.indexOf(">",posIgual+1);
		    		posEndIgualF = posEndIgual2<posEndIgual || posEndIgual==-1?posEndIgual2:posEndIgual;
		     		String param = source.substring(posIgual+1,posEndIgualF);
		     		if (!param.equals("us-ascii\"") && !param.equals("UTF-8\"" ))
		     			source = source.substring(0,posIgual+1)+"\""+param+"\""+source.substring(posEndIgualF);
		  	 }
		  
		  	  posInicial2= posEndIgualF+1;
		    }
    	}
    	posInicial= posIF+1;
    }  		
  
		
  	// agrega las secciones, que el explorer puede llegar a eliminar
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<DIV style=\"BACKGROUND-COLOR: #dddddd\" id=\"zone_",posInicial);
  		if (posI==-1) break;
  		int l = "<DIV style=\"BACKGROUND-COLOR: #dddddd\" id=\"zone_".length();
  		int posIF = source.indexOf(">",posI+1);
  		int posIF2 = source.indexOf("\"",posI+l);
  		String id = source.substring(posI+l,posIF2);
  		String name = source.substring(posI,posIF+1);
  		String continuacion = source.substring(posIF+1,(posIF+16+id.length())>source.length()-1?source.length()-1:posIF+16+id.length());
  		if (continuacion.indexOf("<!--DATA:"+id)==-1) {
  			source = source.replaceAll(name, name+"<!--DATA:"+id+"-->");
  		}
  		posInicial= posIF+1;
  		
  	}
  	// elimina raros comentarios
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<!--[",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF+1);
  			source = source.replace(contenido,"");
  		posInicial= posI+1;
  		
  	}
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<![",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF+1);
  			source = source.replace(contenido,"");
  		posInicial= posI+1;
  		
  	}
  	// elimina st1:personname
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<st1:",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF+1);
  			source = source.replace(contenido,"");
  		posInicial= posI+1;
  		
  	}
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("</st1:",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF+1);
  			source = source.replace(contenido,"");
  		posInicial= posI+1;
  		
  	}
  	// elimina v:
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<v:",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF+1);
  			source = source.replace(contenido,"");
  		posInicial= posI+1;
  		
  	}
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("</v:",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF+1);
  			source = source.replace(contenido,"");
  		posInicial= posI+1;
  		
  	}

  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<o:",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF+1);
  			source = source.replace(contenido,"");
  		posInicial= posI+1;
  		
  	}
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("</o:",posInicial);
  		if (posI==-1) break;
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF+1);
  			source = source.replace(contenido,"");
  		posInicial= posI+1;
  		
  	}
  	// pone fin a los img
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<img",posInicial);
  		if (posI==-1) break;
  		int l = "<img".length();
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF);
  		String control = source.substring(posI,posIF+1);
  		if (!control.endsWith("/>"))
  			source = source.replace(control, contenido+" />");
  		posInicial= posI+1;
  		
  	}
   	// pone fin a los meta
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<meta",posInicial);
  		if (posI==-1) break;
  		int l = "<meta".length();
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF);
  		String control = source.substring(posI,posIF+1);
  		if (!control.endsWith("/>"))
  			source = source.replace(control, contenido+" />");
  		posInicial= posI+1;
  		
  	}
  	// pone fin a los hr
  	posInicial = 0;
  	while (true) {
  		int posI = source.toLowerCase().indexOf("<hr",posInicial);
  		if (posI==-1) break;
  		int l = "<hr".length();
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF);
  		String control = source.substring(posI,posIF+1);
  		if (!control.endsWith("/>"))
  			source = source.replace(control, contenido+" />");
  		posInicial= posI+1;
  		
  	}   	
  	// pone fin a los br
  	posInicial = 0;
  	while (true) {
  		int posI = source.toLowerCase().indexOf("<br",posInicial);
  		if (posI==-1) break;
  		int l = "<br".length();
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF);
  		String control = source.substring(posI,posIF+1);
  		if (!control.endsWith("/>"))
  			source = source.replace(control, contenido+" />");
  		posInicial= posI+1;
  		
  	}  	/*
   	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<img",posInicial);
  		if (posI==-1) break;
  		int l = "<img".length();
  		int posIF = source.indexOf(">",posI+1);
  		String contenido = source.substring(posI,posIF);
  		String control = source.substring(posI,posIF+1);
  		int posIS = contenido.indexOf("src=\"data:image/jpg;base64,",0);
  		if (posIS!=-1) {
  			int posFS = contenido.indexOf("\"",posIS+("src=\"data:image/jpg;base64,".length())+1);
    		String contenidoS = contenido.substring(posIS+("src=\"data:image/jpg;base64,".length()),posFS);
    		if (contenidoS.indexOf("%0D")==-1) {
	    	  contenidoS= URLEncoder.encode(contenidoS,"UTF-8");
	    	  if (!contenido.substring(posIS+5,posFS).equals(contenidoS))
	    	  	contenido = contenido.replace(contenido.substring(posIS+("src=\"data:image/jpg;base64,".length()),posFS), contenidoS);
    		}
  		}
  		if (!control.endsWith("/>")) {
  			contenido+=" />";
  		}
  		else {
   			contenido+=">";
  		}

  		source = source.replace(control, contenido);
  		
  		
  		posInicial= posI+1;
  		
  	}
  */	
  	// elimina de las construcciones {* xxx } los html que pueden existir, ej: <*xxx<b>xxx</b>xxx}
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("{*",posInicial);
  		if (posI==-1) break;
  		int l = "{*".length();
  		int posIF = source.indexOf("}",posI+1);
  		if (posIF==-1) break;
  		String cadenaOriginal=source.substring(posI,posIF+1);
  		String cadena1="";
  		String cadena2="";
  		int in=0;
  		for (int i=posI;i<posIF+1;i++) {
  			if (source.charAt(i)=='<') {
  				in++;
  				cadena2+=source.charAt(i);
  			}
  			else if (source.charAt(i)=='>') {
  				in--;
  				cadena2+=source.charAt(i);
  			}
  			else {
  				if (in==0) cadena1+=source.charAt(i);
    			if (in>0) cadena2+=source.charAt(i);
  			}
  		}
  		if (!cadenaOriginal.equals(cadena1+cadena2))
  			source = source.replace(cadenaOriginal, cadena1+cadena2);
 		
  		posInicial= posIF+1;
  		
  	}
  
  	
  	posInicial = 0;
  	while (true) {
  		int posI = source.indexOf("<div style=\"",posInicial);
  		if (posI==-1) break;
  		int l = "<span class=\"Apple-tab-span\"".length();
  		int l2 = "<div style=\"".length();
   		int l3 = "</span>".length();
   	 	int posIF = source.indexOf("<span class=\"Apple-tab-span\"",posI+1);
  		if (posIF==-1) break;
  		int posIF2 = source.indexOf("</div>",posI+1);
  		int posIF4 = source.indexOf("<div",posI+1);
  		int posIF5 = source.indexOf("<p ",posI+1);
  		int posIF3 = source.indexOf("</span>",posIF+1);
  		if ((posIF2>0 && posIF2<posIF) || (posIF4>0 && posIF4<posIF)  || (posIF5>0 && posIF5<posIF)) {
  		 	posInicial= posI+1;
  			continue;
  		}
  		String cadenaOriginal=source.substring(posI,posIF3+l3);
  		String cadena = "<div style=\"text-indent: 1.2cm;"+cadenaOriginal.substring(l2,posIF-posI);
  		source = source.replace(cadenaOriginal, cadena);
		 	posInicial= posI+1;
  		
  	}
  	posInicial = 0;
  	while (true) {//                /--------------\->esta zona deberia ser mas flexible
  		int posI = source.indexOf("<p",posInicial);
  		if (posI==-1) break;
  		int posI1 = source.indexOf(">",posI);
  		int posI2 = source.indexOf("style=\"",posI);
  		if (posI1<posI2) {
  		 	posInicial= posI+1;
  			continue;
  		}
  		int l = "<span class=\"Apple-tab-span\"".length();
  		int l2 = posI2-posI+"style=\"".length();
   		int l3 = "</span>".length();
   	 	int posIF = source.indexOf("<span class=\"Apple-tab-span\"",posI+1);
  		if (posIF==-1) break;
  		int posIF2 = source.indexOf("</p>",posI+1);
  		int posIF4 = source.indexOf("<div",posI+1);
  		int posIF5 = source.indexOf("<p ",posI+1);
  		int posIF3 = source.indexOf("</span>",posIF+1);
  		if ((posIF2>0 && posIF2<posIF) || (posIF4>0 && posIF4<posIF)  || (posIF5>0 && posIF5<posIF)) {
  		 	posInicial= posI+1;
  			continue;
  		}
  		String cadenaOriginal=source.substring(posI,posIF3+l3);
  		String cadena = cadenaOriginal.substring(0,l2)+"text-indent: 1.2cm;"+cadenaOriginal.substring(l2,posIF-posI);
  		source = source.replace(cadenaOriginal, cadena);
		 	posInicial= posI+1;
  		
  	}
  
  	return source;
	}
	private String searchDataDeep(String name,String source, String zone, JRecord[] origenes, int posOrigen,boolean preserveTags) throws Exception {
		StringBuffer out = new StringBuffer("");
		int pos = name.indexOf(".");
		if (pos==-1) {
			out = new StringBuffer(searchData(name, source, zone, origenes,posOrigen,preserveTags));
			return out.toString();
		}
		
		JRecord origen =  origenes[posOrigen];
		
		String base = name.substring(0,pos);
		String resto = name.substring(pos+1,name.length());
		if (origen.existProperty(base)) {
			JProperty p = origen.getFixedProp(base);
			String tipo = p.getType();
			if (tipo.equals(JProperty.TIPO_RECORDS) || tipo.equals(JProperty.TIPO_RECORD)) {
				JBaseRecord record = null;
				JObject obj = origen.getProp(base,true);
				if (obj instanceof JObjBD) {
					JObjBD recs = (JObjBD) obj;
					record = recs.getValue();
				} else if (obj instanceof JObjBDs) {
					JObjBDs recs = (JObjBDs) obj;
					record = recs.getValue();
				}
				if (record != null) {
					if (record instanceof JRecords) {
						JRecords records = (JRecords) record;
						String aux = "";
						JIterator<JRecord> it = records.getStaticIterator();
						while (it.hasMoreElements()) {
							JRecord r = it.nextElement();
							origenes[posOrigen+1]=r;
							aux+=searchDataDeep(resto,source,zone,origenes,posOrigen+1,preserveTags);
						}
						out = new StringBuffer(aux);
//						source=source.replace(zone, aux);
					} else {
						JRecord r = (JRecord) record;
						origenes[posOrigen+1]=r;
						out= new StringBuffer(searchDataDeep(resto,source,zone,origenes,posOrigen+1,preserveTags));
					}
				} 
			}
		}
		
		return out.toString();
	}
	
	private String searchData(String name,String source, String zone, JRecord[] origenes,int posOrigen, boolean preserveTags) throws Exception {
		StringBuffer out= new StringBuffer("");
		if (name.equals("SECTOR_MAIN")) {
			out =new StringBuffer( render(zone,origenes, posOrigen,preserveTags).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", ""));
			return out.toString();
		} 
		int i=posOrigen;
		if (name.startsWith("n0_")) {
			i = 0;
			name = name.substring(3);
		}
		if (name.startsWith("n1_") && posOrigen>=1) {
			i = 1;
			name = name.substring(3);
		}
		if (name.startsWith("n2_") && posOrigen>=2) {
			i = 2;
			name = name.substring(3);
		}
		if (name.startsWith("n3_") && posOrigen>=3) {
			i = 3;
			name = name.substring(3);
		} else {
			i=posOrigen;//-1;
		}

		JRecord origen= origenes[i];
		while (origen!=null) {
			if (origen.existProperty(name)) {
				JProperty p = origen.getFixedProp(name);
				String tipo = p.getType();
				if (tipo.equals(JProperty.TIPO_RECORDS) || tipo.equals(JProperty.TIPO_RECORD)) {
					JBaseRecord record = null;
					JObject obj = origen.getProp(name,true);
					if (obj instanceof JObjBD) {
						JObjBD recs = (JObjBD) obj;
						record = recs.getValue();
					} else if (obj instanceof JObjBDs) {
						JObjBDs recs = (JObjBDs) obj;
						record = recs.getValue();
					}
					if (record != null) {
						if (record instanceof JRecords) {
							JRecords records = (JRecords) record;
							String aux = "";
							JIterator<JRecord> it = records.getStaticIterator();
							while (it.hasMoreElements()) {
								JRecord r = it.nextElement();
								origenes[posOrigen+1]=r;
								String salida = doPreRender(zone,origenes, posOrigen+1,preserveTags);
//								String salida=render(zone,origenes, posOrigen+1,preserveTags);
								salida=salida.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?><DIV>", "");
							//	salida=salida.replace("<div style=\"\">", "");
								salida=salida.startsWith("<body>")?salida.substring(6):salida;
								salida=salida.startsWith("<BODY>")?salida.substring(6):salida;
								salida=salida.startsWith("<DIV>")?salida.substring(5):salida;
								salida=salida.startsWith("<div>")?salida.substring(5):salida;
								salida=salida.endsWith("</BODY>")?salida.substring(0, salida.length()-7):salida;
								salida=salida.endsWith("</body>")?salida.substring(0, salida.length()-7):salida;
								salida=salida.endsWith("</DIV>")?salida.substring(0, salida.length()-6):salida;
								salida=salida.endsWith("</div>")?salida.substring(0, salida.length()-6):salida;
								out.append(salida);
							}
//							source=source.replace(zone, aux);
						} else {
							JRecord r = (JRecord) record;
//							source=source.replace(zone, render(zone,r)).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
							origenes[posOrigen+1]=r;
							String salida = doPreRender(zone,origenes, posOrigen+1,preserveTags);
							salida=salida.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?><DIV>", "");
							salida=salida.startsWith("<body>")?salida.substring(6):salida;
							salida=salida.startsWith("<BODY>")?salida.substring(6):salida;
							salida=salida.startsWith("<DIV>")?salida.substring(5):salida;
							salida=salida.startsWith("<div>")?salida.substring(5):salida;
							salida=salida.endsWith("</BODY>")?salida.substring(0, salida.length()-7):salida;
							salida=salida.endsWith("</body>")?salida.substring(0, salida.length()-7):salida;
							salida=salida.endsWith("</DIV>")?salida.substring(0, salida.length()-6):salida;
							salida=salida.endsWith("</div>")?salida.substring(0, salida.length()-6):salida;
							out= new StringBuffer(salida);
//							out= render(zone,origenes, posOrigen+1,preserveTags).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
						}
					} 
				}
				return out.toString();
			}			
			--i;
			if (i<0) break;
			origen= origenes[i];
		}
		return out.toString();

	}
	
	String oldZone=null;
	private String doPreRender(String source, JRecord[] origenes, int posOrigen,boolean preserveTags) throws Exception {
		//Vector<String> mapa = generateMap(source,null);
		Vector<String> mapaCC = generateMapCC(source,null);
		Vector<String> useds = new Vector<String>();
		Vector<String> usedsLevels ;
		String last="";
		boolean exit=true;
		
		while (true) {
			exit=true;
			usedsLevels = new Vector<String>();
			Vector<String> mapa = generateMap(source,null);
			for( String entrada:mapa) {
			
				String tag = "<!--DATA:"+entrada+"-->";
				int posI = source.indexOf(tag);
				int posF = source.indexOf("<!--DATAEND:"+entrada+"-->",posI+1);
				if (posF==-1) {
					continue;
				}
				usedsLevels.add(entrada);
				if (useds.contains(entrada)) continue;
				exit=false;
				String zone = source.substring(posI,posF+("<!--DATAEND:"+entrada+"-->").length());
				String subzone = source.substring(posI+("<!--DATA:"+entrada+"-->").length(),posF);
				String realEntrada =entrada.startsWith("!")?entrada.substring(1):entrada;
				source=source.replace(zone,searchDataDeep(realEntrada,source,subzone,origenes,posOrigen,true)).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
				last=entrada;
			}
			useds.addAll(usedsLevels);
			
			if (exit) break;
		}

    // la zona demarcada por CC si se repite, borrarlas.
		source = "<body>"+(render(source, origenes, posOrigen, preserveTags).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", ""))+"</body>";
		for (String entrada:mapaCC) {
			while (true) {
				String tag = "<!--CC:"+entrada+"-->";
				int posI = source.indexOf(tag);
				int posF = source.indexOf("<!--CCEND:"+entrada+"-->",posI+1);
				if (posF==-1) {
					break;
				}
				String zone = source.substring(posI,posF+("<!--CCEND:"+entrada+"-->").length());
				//String zone2 = source.substring(posI+tag.length(),posF);
				if (oldZone!=null && zone.equals(oldZone)) {
					source=JTools.DelSubStr(source,posI,zone.length());
				} else {
					source=JTools.DelSubStr(source, posF,("<!--CCEND:"+entrada+"-->").length());
					source=JTools.DelSubStr(source, posI,tag.length());
					oldZone=zone;
				}
			}
		}
		return source;
	}
	
	public BizPlantilla execProcessClon(final BizPlantilla clon) throws Exception {
  	JExec oExec = new JExec(this, "processClon") {
 		@Override
			public void Do() throws Exception {
				 processClon(clon);
			}
		};
		oExec.execute();
		return clon;
	}
	public BizPlantilla getClon() throws Exception {
    final BizPlantilla clon = new BizPlantilla();
    clon.copyProperties(this);
    clon.pIdtipo.setNull();
    clon.pDescripcion.setValue(this.getDescripcion()+" (bis)");
		return clon;
	}

	public BizPlantilla processClon(BizPlantilla newDoc) throws Exception {
//		newDoc.copyProperties(this);
		newDoc.processInsert();
		
		//permisos al clonador
		BizOwnerPlantilla ow = new BizOwnerPlantilla();
		ow.dontThrowException(true);
		if (ow.Read(newDoc.getIdtipo(), BizUsuario.getUsr().GetUsuario())) {
			ow.setBorrable(true);
			ow.setModificable(true);
			ow.processUpdate();
		} else {
			ow.setIdtipo(newDoc.getIdtipo());
			ow.setOwner(BizUsuario.getUsr().GetUsuario());
			ow.setBorrable(true);
			ow.setModificable(true);
			ow.processInsert();
		}
		return newDoc;
	}
@Override
public void processDelete() throws Exception {
	JRecords<BizOwnerPlantilla> rels= new JRecords<BizOwnerPlantilla>(BizOwnerPlantilla.class);
	rels.addFilter("id_plantilla", getIdtipo());
	rels.toStatic();
	rels.processDeleteAll();
	super.processDelete();
}
	public static JWins getFondos() throws Exception {
		return BizPlantilla.getFondos(null);
	}
	public static JWins getFondos(String company) throws Exception {
		JMap<String,String> map = JCollectionFactory.createOrderedMap();
		File dir = new File(JPath.PssPathData()+"/Fondos/"+company);
//		if (!dir.exists()) dir = new File(JPath.PssPathData()+"/Fondos");
		String [] names = dir.list(new SuffixFileFilter(".png"));
		if (names==null) return JWins.createVirtualWinsFromMap(map);
		for (String name:names) {
			map.addElement(name, name);
		}
		return JWins.createVirtualWinsFromMap(map);
	}
	

	public String getTipoPaginaCSS3() throws Exception {
		return getTipoPaginaCSS3(getTipoPagina());
	}

	public String htmlToPdf(String filename, JRecord rec) throws Exception {
		String html = new URLCodec().decode(this.generateDocSimple(rec), CharEncoding.ISO_8859_1);
		return this.htmlToPdf(filename, true, html);
	}
	public String htmlToPdf(String filename, boolean paraImpresion, String html) throws Exception {
		String dirName;
		String fullfilename;
		String pathToResources= "file:///"+JPath.PssPathResourceHtml();
		fullfilename= JPath.PssPathTempFiles() + "/" + filename;
		ByteArrayOutputStream f = new ByteArrayOutputStream();
		String str = decode(html, CharEncoding.ISO_8859_1);
		f.write("<html><head>".getBytes());
		String s = "";
		String i = "";
		i += " <link rel=\"stylesheet\" href=\""+pathToResources+"/vendor/bootstrap/css/bootstrap.min.css\"></link>";
		i += " <link href=\""+pathToResources+"/css/nv.d3.css\" media=\"all\" type=\"text/css\" rel=\"stylesheet\"></link>";
		i += " <link href=\""+pathToResources+"/vendor/jqueryui/jquery-ui.min.css\" media=\"all\" type=\"text/css\" rel=\"stylesheet\"></link>";
		i += " <link type=\"text/css\" rel=\"stylesheet\" href=\""+pathToResources+"/vendor/font-awesome/js/all.min.js\"></link>";
		i += " <script type=\"text/javascript\" src=\"https://maps.googleapis.com/maps/api/js?v=3.exp&amp;sensor=false&amp;language=es\"></script>";
		i += " <script type=\"text/javascript\" src=\""+pathToResources+"/js/map.js\"></script>";
		i += " <script src=\""+pathToResources+"/vendor/jquery/jquery.min.js\"></script>";
		i += " <script src=\""+pathToResources+"/vendor/raphael/raphael.min.js\"></script>";
		i += " <script src=\""+pathToResources+"/vendor/moment/moment-with-locale.js\"></script>";
		i += " <script type=\"text/javascript\" src=\""+pathToResources+"/vendor/d3/d3.min.js\"></script>";
		i += " <script type=\"text/javascript\" src=\""+pathToResources+"/vendor/d3/d3.v5.min.js\"></script>";
		i += " <script type=\"text/javascript\" src=\""+pathToResources+"/vendor/d3/nv.d3.js\"></script>";
		i += " <script type=\"text/javascript\" src=\""+pathToResources+"/vendor/d3/d3.tip.v0.6.3.js\"></script>";
		i += " <script type=\"text/javascript\" src=\""+pathToResources+"/js/three.min.js\"></script>";
		i += " <script type=\"text/javascript\" src=\""+pathToResources+"/js/three-text2d.js\"></script>";
		i += " <script src=\""+pathToResources+"/js/gauge.js\"></script>";
		i += " <script src=\""+pathToResources+"/js/gaugev5.js\"></script>";
		s += " body:before, body:after {";
		s += "	content: \"\";";
		s += "	position: fixed;";
		s += "	background: #FFFFFF;";
		s += "	left: 0;";
		s += "	right: 0;";
		s += "	height: 10px;";
		s += "}";
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
		s += "	letter-spacing: -0.05em;";
		s += "}";
		s += "@page { ";
		s += "	size: " + getTipoPaginaCSS3() + ";";
		s += "	margin-left: " + getMargenIzq() + "mm; ";
		s += "	margin-right: " + getMargenDer() + "mm; ";
		s += "	margin-top: " + getMargenTop() + "mm; ";
		s += "	margin-bottom: " + getMargenBottom() + "mm; ";
	//	s += "  background-image: url(\"/regtur/pss_data/Fondos/fondo_a4.jpg\"); ";
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
	  s += "   width: "+(getLargoPaginaCSS3()-getMargenIzq()-getMargenDer()-10)+"mm; ";
	  s += "} ";
	  s += "#tablenumber:before {  content: counter(tablepage); } ";
	  s += "#pagenumber:before {  content: counter(page); } ";
	  s += "#pagecount:before {  content: counter(pages); } ";
	  f.write( i.getBytes());
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

//		String convertOut = convertToPrinteable(JTools.byteVectorToString(f.toByteArray()));

		ByteArrayInputStream input = new ByteArrayInputStream(f.toByteArray());

		JTools.convertHtml2JPGorPDF(input,fullfilename);
		
		return filename;

	}
	
	/*	
	public String htmlToPdf(String filename, boolean paraImpresion, String html) throws Exception {
		String dirName, imgFile = null;
		dirName = JPath.PssPathData()+"/Fondos/"+this.pCompany.getValue();
//		File dir = new File(dirName);
//		if (!dir.exists()) dirName = "/Fondos";

		if (!paraImpresion)
				imgFile = dirName+ "/" +this.getFondo();
			else if (getImprimeFondo())
				imgFile = dirName+ "/" +this.getFondo();
		String fullfilename;
		
		fullfilename= JPath.PssPathTempFiles() + "/" + filename;
		ByteArrayOutputStream f = new ByteArrayOutputStream();
		String str = decode(html, CharEncoding.ISO_8859_1);
		f.write("<html><head>".getBytes());
		String s = "body:before, body:after {";
		s += "	content: \"\";";
		s += "	position: fixed;";
		s += "	background: #FFFFFF;";
		s += "	left: 0;";
		s += "	right: 0;";
		s += "	height: 10px;";
		s += "}";
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
		s += "	letter-spacing: -0.05em;";
		s += "}";
		s += "@page { ";
		s += "	size: " + getTipoPaginaCSS3() + ";";
		s += "	margin-left: " + getMargenIzq() + "mm; ";
		s += "	margin-right: " + getMargenDer() + "mm; ";
		s += "	margin-top: " + getMargenTop() + "mm; ";
		s += "	margin-bottom: " + getMargenBottom() + "mm; ";
	//	s += "  background-image: url(\"/regtur/pss_data/Fondos/fondo_a4.jpg\"); ";
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
	  s += "   width: "+(getLargoPaginaCSS3()-getMargenIzq()-getMargenDer()-10)+"mm; ";
	  s += "} ";
	  s += "#tablenumber:before {  content: counter(tablepage); } ";
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
           
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//arial.ttf", true);
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//ariblk.ttf", true);
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//ariali.ttf", true);
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//arialbd.ttf", true);
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//arialbi.ttf", true);
		
		try {
			renderer.getFontResolver().addFont(JPath.pssFonts()+"//helr65w.ttf", true);
			renderer.getFontResolver().addFont(JPath.pssFonts()+"//helr45w.ttf", true);
			renderer.getFontResolver().addFont(JPath.pssFonts()+"//helr66w.ttf", true);
			renderer.getFontResolver().addFont(JPath.pssFonts()+"//helr47w.ttf", true);
			renderer.getFontResolver().addFont(JPath.pssFonts()+"//helr67w.ttf", true);
			renderer.getFontResolver().addFont(JPath.pssFonts()+"//helr68w.ttf", true);
			renderer.getFontResolver().addFont(JPath.pssFonts()+"//helr48w.ttf", true);
			renderer.getFontResolver().addFont(JPath.pssFonts()+"//helr46w.ttf", true);
			renderer.getFontResolver().addFont(JPath.pssFonts()+"//barcode39ext.ttf", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//times.ttf", true);
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//timesbd.ttf", true);
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//timesbi.ttf", true);
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//timesi.ttf", true);
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//impact.ttf", true);
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//comic.ttf", true);
		renderer.getFontResolver().addFont(JPath.pssFonts()+"//comicbd.ttf", true);
    SharedContext sharedContext = renderer.getSharedContext();
    sharedContext.setPrint(true);
    sharedContext.setInteractive(false);
    sharedContext.setReplacedElementFactory(new ProfileImageReplacedElementFactory());
    sharedContext.getTextRenderer().setSmoothingThreshold(0);
   // System.out.println(JTools.byteVectorToString(out.toByteArray()));
   	renderer.setDocumentFromString(JTools.byteVectorToString(out.toByteArray()));
		renderer.layout();
		OutputStream outputStream = new FileOutputStream(fullfilename);
		renderer.createPDF(outputStream, true, 0);//, imgFile); // se quito la imagen de fondo forzada, hay que recompilar
		
		// Finishing up
		renderer.finishPDF();
		out.flush();
		out.close();
		return filename;

	}
	*/
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

	public static BizPlantilla find(String buscador) throws Exception {
		BizPlantilla pl = new BizPlantilla();
		pl.dontThrowException(true);
		pl.addFilter("descripcion", buscador,"ilike");
		if(!pl.read())return null;
		return pl;
	}


	public String fillDatos(JRecord[] datos,int cantDatos) throws Exception {
		String texto;
	
		texto = preRender(datos,cantDatos,null);
		texto = posRender(texto);
		texto = (new URLCodec()).encode(JTools.replaceForeignCharsForWeb(texto), "UTF-8").replaceAll("\\+", " ");
		return texto;
	}


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
		source=JTools.replaceWebForForeignChars(source);
		
		DocumentBuilder db = dbf.newDocumentBuilder();
  	String in = reparacionBugsDeExplorer(source);
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
					s = "13px";
				else if (s.equals("3"))
					s = "17px";
				else if (s.equals("4"))
					s = "19px";
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
					s = "41px";
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
//				} else if (padre.getTagName().equalsIgnoreCase("svg")) {
//	        
//          TranscoderInput input_svg_image = new TranscoderInput(JTools.generarString(padre));        
//          OutputStream png_ostream = new ByteArrayOutputStream();
//          TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);              
//          PNGTranscoder my_converter = new PNGTranscoder();        
//          my_converter.transcode(input_svg_image, output_png_image);
//          png_ostream.flush();
//          png_ostream.close();
//          newNode = outDoc.createElement("img");
//          ((Element)newNode).setAttribute("src", png_ostream.toString());
//        
				}	else {
					newNode = output.appendChild(outDoc.importNode(nodoPadre, false));
				}          output.appendChild(newNode);

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


  public String generateListadoTemp(boolean impresion, BizCustomList docRelacionado,JFilterMap action,String company, String tipoDocLocal, boolean html ) throws Exception {
  	if (html)
  		return generateListadoTemporarioHTML(impresion,docRelacionado,action,company,tipoDocLocal);
		return generateListadoTemporarioPDF(impresion,docRelacionado,action,company,tipoDocLocal);
  }
  public static String generateListadoTemporarioPDF(boolean impresion, BizCustomList docRelacionado,JFilterMap action,String company, String tipoDocLocal ) throws Exception {
  	BizPlantilla l = BizCompany.getObjPlantillaByIdTipo(company, tipoDocLocal);
  	if (l==null) l=BizCompany.getObjPlantilla(company, "sys_reporte");
  	String s =  l.generateDoc(company,docRelacionado,action);
		String tempFile = company+"/doc"+docRelacionado.getListId()+".pdf";
  	JTools.createDirectories(JPath.PssPathTempFiles(),  tempFile);
		l.htmlToPdf(tempFile, impresion, s);
		return tempFile;
  }  
  public static String generateListadoTemporarioHTML(boolean impresion, BizCustomList docRelacionado,JFilterMap action, String company,String tipoDocLocal ) throws Exception {
  	BizPlantilla l = BizCompany.getObjPlantillaByIdTipo(company, tipoDocLocal);
  	if (l==null) l=BizCompany.getObjPlantilla(company, "sys_reporte");
  	String s =  new URLCodec().decode(l.generateDoc(company,docRelacionado,action));
		String tempFile = company+"/doc"+(docRelacionado==null?l.hashCode():docRelacionado.hashCode())+".html";
  	JTools.createDirectories(JPath.PssPathTempFiles(),  tempFile);
		return l.htmlToHtml(tempFile,impresion,s);
  }  
  public String generateDoc(String company,BizCustomList docRelacionado,JFilterMap filters) throws Exception {
    docRelacionado.fillFilters(filters);
		JRecord[] datos = new JRecord[10];
		datos[0]=BizCompany.getObjCompany(company);
  	datos[1]=docRelacionado;
		return fillDatos(datos, 1);
  }  
  public String generateDocSimple( JRecord docRelacionado) throws Exception {
		JRecord[] datos = new JRecord[10];
		datos[0]=BizCompany.getObjCompany(BizUsuario.getUsr().getCompany());
		datos[1]=docRelacionado;
		return fillDatos(datos, 1);
  }  
  public String generateDocSimple( JRecord docRelacionado, JRecord docDatos) throws Exception {
		JRecord[] datos = new JRecord[10];
		datos[0]=BizCompany.getObjCompany(BizUsuario.getUsr().getCompany());
		datos[1]=docRelacionado;
		datos[2]=docDatos;
		return fillDatos(datos, 2);
  }  
  
  public JMap<String,String> recoveryVariables(String doc) throws Exception {
  	JMap<String,String> map = JCollectionFactory.createMap();
  	int pos = doc.indexOf("variable__");
  	while (pos!=-1) {
  		int varNameIni = doc.lastIndexOf("\"", pos );
  		int varNameFin = doc.indexOf("\"", pos );
  		int posIni = doc.lastIndexOf("<", pos );
  		int posFin = doc.indexOf("</div>", pos );
  		if (varNameFin==-1||varNameIni==-1||posIni==-1||posFin==-1) {
  			pos=pos+11;
  			continue;//descartar
  		}
  		String varName = doc.substring(varNameIni+11,varNameFin);
  		String varHtml=doc.substring(posIni,posFin+6);
  		map.addElement(varName, JTools.trimHTML(varHtml));

  		pos = doc.indexOf("variable__",pos+10);
  		  
  	}
		return map;
  }  
  
 	public String htmlToHtml(String filename, boolean paraImpresion, String html) throws Exception {
		String imgFile = null;
		if (!paraImpresion)
				imgFile = JPath.PssPathData() + "/Fondos/" + this.getFondo();
			else if (getImprimeFondo())
				imgFile = JPath.PssPathData() + "/Fondos/" + this.getFondo();
		String fullfilename;
		
		fullfilename= filename==null?null:JPath.PssPathTempFiles() + "/" + filename;
		if (fullfilename!=null)
			JTools.DeleteFile(fullfilename);
		
String str = html;
//System.out.println(str);

//		String str = decode(html, CharEncoding.ISO_8859_1);
		String s ="";
		String h ="";
		JWebApplication app =JWebServer.getInstance().getWebApplication(null);
		if (app!=null) {
			
			h+="<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/resources/styles[xxxx].css\"></link>\n";
		}

		h+="<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/css/asterplot.css\"></link>\n";
		h+="<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/css/nv.d3.css\"></link>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/vendor/jquery/jquery.min.js\" type=\"text/javascript\"></script>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/vendor/d3/d3.min.js\" type=\"text/javascript\"></script>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/vendor/d3/d3.v5.min.js\" type=\"text/javascript\"></script>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/vendor/d3/nv.d3.js\" type=\"text/javascript\"></script>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/vendor/d3/d3.tip.v0.6.3.js\" type=\"text/javascript\"></script>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/js/three.min.js\" type=\"text/javascript\"></script>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/js/three-text2d.js\" type=\"text/javascript\"></script>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/js/TrackballControls.js\" type=\"text/javascript\"></script>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/js/queue.v1.min.js\" type=\"text/javascript\"></script>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/js/gauge.js\" type=\"text/javascript\"></script>\n";
		h+="<script src=\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/js/gaugev5.js\" type=\"text/javascript\"></script>\n";

		s += "body:before, body:after {";
		s += "	content: \"\";";
		s += "	position: fixed;";
		s += "	background: #FFFFFF;";
		s += "	left: 0;";
		s += "	right: 0;";
		s += "	height: 10px;";
		s += "}";
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
		s += "	letter-spacing: -0.05em;";
		s += "}";
		s += "@page { ";
		s += "	size: " + getTipoPaginaCSS3() + ";";
		s += "	margin-left: " + getMargenIzq() + "mm; ";
		s += "	margin-right: " + getMargenDer() + "mm; ";
		s += "	margin-top: " + getMargenTop() + "mm; ";
		s += "	margin-bottom: " + getMargenBottom() + "mm; ";
	//	s += "  background-image: url(\"/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/pss_data/Fondos/fondo_a4.jpg\"); ";
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
	  s += "   width: "+(getLargoPaginaCSS3()-getMargenIzq()-getMargenDer()-10)+"mm; ";
	  s += "} ";
	  s += "#tablenumber:before {  content: counter(tablepage); } ";
	  s += "#pagenumber:before {  content: counter(page); } ";
	  s += "#pagecount:before {  content: counter(pages); } ";
		str = str.replaceAll("border=\"1\" bordercolor=\"GRAY\"", "border=\"0\"");
		str = str.replaceAll("border=\"1\" bordercolor=\"gray\"", "border=\"0\"");
		str = str.replaceAll("border=\"1\" cellSpacing=\"0\" borderColor=\"gray\"", "border=\"0\" cellSpacing=\"0\"");
		str = str.replaceAll("border=\"1\" cellspacing=\"0\" bordercolor=\"gray\"", "border=\"0\" cellSpacing=\"0\"");
		
		String output = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><html><head>";
		output+=h;
		output+="<style type=\"text/css\">" + s + "</style>";
		output+="</head><body>";
		output+=str;
		output+="</body></html>";
		
		if (fullfilename==null) return output;
		
		File ff= new File(fullfilename);
		FileOutputStream f = new FileOutputStream(ff);
		f.write(output.getBytes());
		f.close();

		
		return filename;

	}
  public static String generateListadoTemporario(String company, boolean impresion, JRecord[] datos, String tipoDocLocal ) throws Exception {
  	BizPlantilla l = BizCompany.getObjPlantilla(company, tipoDocLocal);
		String tempFile = company+"/doc"+datos.hashCode()+".html";
		return l.htmlToHtml(tempFile,true,new URLCodec().decode(l.fillDatos(datos, datos.length-2),CharEncoding.ISO_8859_1));
  }  

}
