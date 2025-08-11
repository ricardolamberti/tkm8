package pss.bsp.carpeta;

import pss.common.customList.config.carpetas.BizCarpeta;
import pss.common.customList.config.carpetas.GuiCarpetas;
import pss.common.customList.config.carpetas.IContenidoCarpeta;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.regions.company.GuiCompany;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;

public class GuiBSPCarpetas extends GuiCarpetas {

	public GuiBSPCarpetas() throws Exception {
		super();
	}
  public Class<? extends JWin>  GetClassWin()                   { return GuiBSPCarpeta.class; }
  public String getHelpTag() { return "datamining";}
  
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	super.ConfigurarFiltros(zFiltros);
  	zFiltros.addComboResponsive("Tipo", "nav", new JControlCombo() {
  		@Override
  		public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
  			return getTiposCarpetas();
  		}
  	},true).setRefreshForm(true);
  }
  

	private JWins getTiposCarpetas() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("Carpetas", JLanguage.translate("Carpetas"));
		map.addElement("Datamining", JLanguage.translate("Datamining"));
		map.addElement("Informes", JLanguage.translate("Informes"));
		map.addElement("Graficos", JLanguage.translate("Graficos"));
		map.addElement("Matrices", JLanguage.translate("Matrices"));
		map.addElement("Agrupaciones", JLanguage.translate("Agrupaciones"));
		map.addElement("Listas", JLanguage.translate("Listas"));
		map.addElement("Dato", JLanguage.translate("Dato"));
		map.addElement("Predefinidos", JLanguage.translate("Predefinidos"));
		map.addElement("Propios", JLanguage.translate("Propios"));
		return JWins.createVirtualWinsFromMap(map);
	}
  protected void asignFilterByControl(JFormControl control) throws Exception {
//  	if (isCarpetaEspecial()) {
//  		if (control.getIdControl().equals("invisible")){
//  			if (control.getValue().equals("N")) {
//  				getRecords().addFixedFilter("(invisible is null or invisible='N')");
//  			}
//  		}
//
//			return;
//  		
//  	}
		if (control.getIdControl().equals("nav")&&control.getValue()!=null) {
			if (control.getValue().equals("Carpetas")) {
				getRecords().addFixedFilter("(customlist is null)");
			} else if (control.getValue().equals("Informes")) {
				getRecords().addFixedFilter("where lst_carpetasv2.customlist is not null and customlist in (select list_id from LST_CUSTOM_LISTV2 where LST_CUSTOM_LISTV2.agrupado='I' and LST_CUSTOM_LISTV2.list_id=lst_carpetasv2.customlist and LST_CUSTOM_LISTV2.company='"+BizUsuario.getUsr().getCompany()+"') ");
			} else if (control.getValue().equals("Datamining")) {
				getRecords().addFixedFilter("where lst_carpetasv2.customlist is not null and customlist in (select list_id from LST_CUSTOM_LISTV2 where LST_CUSTOM_LISTV2.agrupado<>'I' and LST_CUSTOM_LISTV2.list_id=lst_carpetasv2.customlist and LST_CUSTOM_LISTV2.company='"+BizUsuario.getUsr().getCompany()+"') ");
			} else if (control.getValue().equals("Graficos")) {
				getRecords().addFixedFilter("where lst_carpetasv2.customlist is not null and customlist in (select list_id from LST_CUSTOM_LISTV2 where LST_CUSTOM_LISTV2.agrupado like 'G%' and LST_CUSTOM_LISTV2.list_id=lst_carpetasv2.customlist and LST_CUSTOM_LISTV2.company='"+BizUsuario.getUsr().getCompany()+"') ");
			} else if (control.getValue().equals("Matrices")) {
				getRecords().addFixedFilter("where lst_carpetasv2.customlist is not null and customlist in (select list_id from LST_CUSTOM_LISTV2 where (LST_CUSTOM_LISTV2.agrupado='M' or LST_CUSTOM_LISTV2.agrupado='PresentationMatrix') and LST_CUSTOM_LISTV2.list_id=lst_carpetasv2.customlist and LST_CUSTOM_LISTV2.company='"+BizUsuario.getUsr().getCompany()+"') ");
			} else if (control.getValue().equals("Agrupaciones")) {
				getRecords().addFixedFilter("where lst_carpetasv2.customlist is not null and customlist in (select list_id from LST_CUSTOM_LISTV2 where (LST_CUSTOM_LISTV2.agrupado='S' or LST_CUSTOM_LISTV2.agrupado='PresentationGroup') and LST_CUSTOM_LISTV2.list_id=lst_carpetasv2.customlist and LST_CUSTOM_LISTV2.company='"+BizUsuario.getUsr().getCompany()+"') ");
			} else if (control.getValue().equals("Listas")) {
				getRecords().addFixedFilter("where lst_carpetasv2.customlist is not null and customlist in (select list_id from LST_CUSTOM_LISTV2 where (LST_CUSTOM_LISTV2.agrupado='N' or LST_CUSTOM_LISTV2.agrupado='PresentationList') and LST_CUSTOM_LISTV2.list_id=lst_carpetasv2.customlist and LST_CUSTOM_LISTV2.company='"+BizUsuario.getUsr().getCompany()+"') ");
			} else if (control.getValue().equals("Dato")) {
				getRecords().addFixedFilter("where lst_carpetasv2.customlist is not null and customlist in (select list_id from LST_CUSTOM_LISTV2 where LST_CUSTOM_LISTV2.agrupado='PresentationDato' and LST_CUSTOM_LISTV2.list_id=lst_carpetasv2.customlist and LST_CUSTOM_LISTV2.company='"+BizUsuario.getUsr().getCompany()+"') ");
			} else if (control.getValue().equals("Predefinidos")) {
				getRecords().addFixedFilter("where lst_carpetasv2.customlist is not null and customlist in (select list_id from LST_CUSTOM_LISTV2 where (LST_CUSTOM_LISTV2.system_protect is not null and LST_CUSTOM_LISTV2.system_protect<>0) and LST_CUSTOM_LISTV2.list_id=lst_carpetasv2.customlist and LST_CUSTOM_LISTV2.company='"+BizUsuario.getUsr().getCompany()+"') ");
			} else if (control.getValue().equals("Propios")) {
				getRecords().addFixedFilter("where lst_carpetasv2.customlist is not null and customlist in (select list_id from LST_CUSTOM_LISTV2 where (LST_CUSTOM_LISTV2.system_protect is null or LST_CUSTOM_LISTV2.system_protect=0)and LST_CUSTOM_LISTV2.list_id=lst_carpetasv2.customlist and LST_CUSTOM_LISTV2.company='"+BizUsuario.getUsr().getCompany()+"') ");
			}
			

		}
  	
  	super.asignFilterByControl(control);
  }
  
  boolean isCarpetaEspecial() throws Exception {
  	return 
  			getObjCarpetaFav().getSecuencia()==getCarpetaActual() ||
  			getObjCarpetaSemanal().getSecuencia()==getCarpetaActual() || 
  			getObjCarpetaMensual().getSecuencia()==getCarpetaActual();
  }
  
  BizCarpeta objCarpetaSemanal;
  public BizCarpeta  getObjCarpetaSemanal() throws Exception {
  	if (objCarpetaSemanal!=null) return objCarpetaSemanal;
  	BizCarpeta carp = new BizCarpeta();
  	carp.dontThrowException(true);
  	carp.addFilter("company", BizUsuario.getUsr().getCompany());
  	carp.addFilter("especial",BizBSPCarpeta.SEMANAL);
  	if (!carp.read()) return null;
  	return carp;
  }
  BizCarpeta objCarpetaMensual;
  public BizCarpeta  getObjCarpetaMensual() throws Exception {
  	if (objCarpetaMensual!=null) return objCarpetaMensual;
  	BizCarpeta carp = new BizCarpeta();
  	carp.dontThrowException(true);
  	carp.addFilter("company", BizUsuario.getUsr().getCompany());
  	carp.addFilter("especial",BizBSPCarpeta.MENSUAL);
  	if (!carp.read()) return null;
  	return carp;
  }
  BizCarpeta objCarpetaFav;
  public BizCarpeta  getObjCarpetaFav() throws Exception {
  	if (objCarpetaFav!=null) return objCarpetaFav;
  	BizCarpeta carp = new BizCarpeta();
  	carp.dontThrowException(true);
  	carp.addFilter("company", BizUsuario.getUsr().getCompany());
  	carp.addFilter("especial","FAV");
  	if (!carp.read()) return null;
  	return carp;
  }
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiCompany) {
			JIterator<JWin> iter = this.getStaticIterator();
			while (iter.hasMoreElements()) {
				GuiBSPCarpeta tr = (GuiBSPCarpeta) iter.nextElement();
			  IContenidoCarpeta contenido=  tr.getContenidoCarpeta();
			  if (contenido instanceof GuiCustomList)
			    ((GuiCustomList)contenido).Drop(zBaseWin);	
			}
		}
		return null;
	}
}
