package pss.www.platform.actions.resolvers;

import java.net.URLEncoder;
import java.util.Date;

import pss.common.layout.JFieldSet;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.winUI.lists.JPlantilla;
import pss.www.platform.actions.results.JWebActionResult;

public class JDoLayoutActionResolver extends JBasicWebActionResolver {


	@Override
	protected String getBaseActionName() {
		return "layout-menu";
	}
	
  static public JPlantilla getDatosPlantilla(String origen,String key) throws Exception {  
  	JPlantilla p = new JPlantilla();
  	if (origen==null) return null;
  	if (origen.equals("")) return null;

  	String prefix ="";
  	if (key.equals("SECTOR_MAIN") && origen.indexOf("BizTramite")!=-1) prefix = "n0_";
  	if (key.equals("SECTOR_MAIN") && origen.indexOf("BizDocLegajo")!=-1) prefix = "n1_";
  	if (key.equals("SECTOR_MAIN") && origen.indexOf("BizAsiento")!=-1) prefix = "n2_";
  	
  	
  	int i=0;
  	JFieldSet fieldset =JFieldSet.getSet(JFieldSet.WINS_SET,origen);
  	fieldset.includeRecords=true;
  	fieldset.includeHeaders=false;
  	fieldset.setFilter(key);
  	fieldset.setIdent(origen);
  	JMap<String,String> sections = fieldset.getAllSections();
  	JIterator<String> it = sections.getKeyIterator();
  	while(it.hasMoreElements()) {
  		String value = it.nextElement();
      if (!key.equals(value)) continue;
// 		if (value.toLowerCase().indexOf("header")!=-1) continue;
    	String data = sections.getElement(value);
    	JMap<String,String> campos = fieldset.getAllFields(value);
    	JIterator<String> itc = campos.getKeyIterator();
    	while(itc.hasMoreElements()) {
    		String keyField = itc.nextElement();
    		String valueField = campos.getElement(keyField);
    	
    		if (valueField.startsWith("Zona "))
    			p.addTag(value, keyField, valueField);
    		else 
    			p.addTag(value, "{*"+prefix+keyField+"}", valueField);
    	}
	  	p.addTag(value, "{*$_hoy_anio}", "Año actual");
	  	p.addTag(value, "{*$_hoy_larga}", "Fecha larga de hoy");
			p.addTag(value, "{*$_hoy_corta}", "Fecha corta de hoy");
			p.addTag(value, "{*$_fecha_larga}", "Fecha larga de documento");
			p.addTag(value, "{*$_fecha_corta}", "Fecha corta de documento");
			p.addTag(value, "{*$_protocolo}", "Protocolo");
			p.addTag(value, "{*$_protocolo_medio}", "Protocolo Medio");
			p.addTag(value, "{*$_protocolo_largo}", "Protocolo Largo");
			
			p.addTag(value, "{*$_usuario}", "Autor");
			p.addTag(value, "{*$_nombre_usuario}", "Autor (Nom. y Apell.)");
			p.addTag(value, "{*$_email_usuario}", "e-mail Autor");
			p.addTag(value, "{*$_dniusuario}", "DNI autor");
			p.addTag(value, "{*$_ident_adicional}", "Ident Adicional Autor");
    
			p.addZone("ROOT", value, "Zona "+data);
    	i++;
  	}
  	return p;
  }

  static public JPlantilla getInfoDatosPlantilla(JPlantilla p,String origen,JRecord object,JRecord docOrigen,String key) throws Exception {  
  	if (p==null) p = new JPlantilla();
  	if (origen==null) return null;
  	if (origen.equals("")) return null;
  	if (object==null) return null;

  	String prefix ="";
  
  	int i=0;
  	JFieldSet fieldset =JFieldSet.getSet(JFieldSet.WINS_SET,origen);
  	fieldset.includeRecords=false;
  	fieldset.includeHeaders=false;
  	fieldset.setFilter(key);
  	fieldset.setIdent(origen);
  	JMap<String,String> sections = fieldset.getAllSections();
  	JIterator<String> it = sections.getKeyIterator();
  	while(it.hasMoreElements()) {
  		String value = it.nextElement();
      if (!key.equals(value)) continue;
    	String data = sections.getElement(value);
    	JMap<String,String> campos = fieldset.getAllFields(value);
    	JIterator<String> itc = campos.getKeyIterator();
    	while(itc.hasMoreElements()) {
    		String keyField = itc.nextElement();
    		String valueField = campos.getElement(keyField);
    	
    		String descr =object.visualizaInEditor(keyField);
    		if (descr!=null) {
    			JObject obj =object.getPropDeep(keyField,false);
    			if (obj!=null) p.addTag(value, URLEncoder.encode(obj.toString()), descr.equals("")?valueField:descr);
    		}
    	}
	  	p.addTag(value, ""+JDateTools.getAnioActual(), "Año actual");
	  	p.addTag(value, JDateTools.formatFechaLarga(new Date()), "Fecha larga de hoy");
			p.addTag(value, JDateTools.formatFechaMediana(new Date()), "Fecha corta de hoy");
//			p.addTag(value, docOrigen.getFecha()==null?JDateTools.formatFechaLarga(new Date()):JDateTools.formatFechaLarga(docOrigen.getFecha()), "Fecha larga de documento");
//			p.addTag(value, docOrigen.getFecha()==null?JDateTools.formatFechaMediana(new Date()):JDateTools.formatFechaMediana(docOrigen.getFecha()), "Fecha corta de documento");
//			p.addTag(value, docOrigen.getProtocoloCorto(), "Protocolo");
//			p.addTag(value, docOrigen.getProtocoloMedio(), "Protocolo Medio");
//			p.addTag(value, docOrigen.getProtocoloLargo(), "Protocolo Largo");
			
			p.addTag(value, BizUsuario.getUsr().getDescrUsuario(), "Autor");
			p.addTag(value, BizUsuario.getUsr().GetDescrip(), "Autor (Nom. y Apell.)");
			p.addTag(value, BizUsuario.getUsr().getEMailAddress(), "e-mail Autor");
			p.addTag(value, BizUsuario.getUsr().getIdPersona()==0?"":BizUsuario.getUsr().getObjPersona().GetNroDoc(), "DNI autor");
			p.addTag(value, BizUsuario.getUsr().getIdPersona()==0?"":BizUsuario.getUsr().getObjPersona().getMatricula(), "Ident Adicional Autor");
    
//			p.addZone("ROOT", value, "Zona "+data);
    	i++;
  	}
  	return p;
  }
	@Override
	protected boolean isAjax() {return true;}

	@Override
	protected JWebActionResult perform() throws Throwable {
    this.addObjectToResult("ajax", "ajax");
		JPlantilla p = getDatosPlantilla((String)getRequest().get("source"),(String)getRequest().get("key"));

		this.getRequest().addModelObject("key", (String)getRequest().get("key"));
		this.getRequest().addModelObject("source", (String)getRequest().get("source"));
		this.getRequest().addModelObject("plantilla", p);
	//	this.getRequest().addModelObject("source", getRequest().get("source"));
		return super.perform();
	}
}
