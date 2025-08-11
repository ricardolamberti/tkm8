package pss.bsp.carpeta;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.common.customList.config.carpetas.BizCarpeta;
import pss.common.customList.config.carpetas.GuiCarpeta;
import pss.common.customList.config.carpetas.GuiCarpetas;
import pss.common.event.action.GuiSqlEventAction;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.action.history.GuiSqlEventHistory;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActBack;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;

public class GuiBSPCarpeta extends GuiCarpeta {

	public GuiBSPCarpeta() throws Exception {
		super();
		}

  public JRecord ObtenerDato()   throws Exception { return new BizBSPCarpeta(); }
  public BizBSPCarpeta GetccDato() throws Exception { return (BizBSPCarpeta) this.getRecord(); }

  public GuiCarpetas getCustomListsActual() throws Exception {
  	JRecords<BizCarpeta> carps = getEspecialCarpeta();
  	if (carps!=null) {
  		addFolderToNav();
  	 	GuiBSPCarpetas winCarp = new GuiBSPCarpetas();
  	 	carps.readAll();
  	  winCarp.setRecords(carps);
  	 	return winCarp;
  	}
  	return super.getCustomListsActual();
  }
  
	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==50) return true;
		if (a.getId()==55) return true;
		return super.OkAction(a);
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==515)return super.getSubmitFor(a);
		if (a.getId()==516)return super.getSubmitFor(a);

		
		if (!GetcDato().isCarpeta())
			return getContenidoCarpeta().getSubmitFor(a);
			
		
		if (a.getId()==50) return new JActNew(this.getCompartir(a), 4);
		if (a.getId()==55) return new JActFileGenerate(this) { 
			public String generate() throws Exception {
				return GetccDato().generateInforme();
			};
		};
		return super.getSubmitFor(a);
	}
	
	public JWin getCompartir(BizAction a) throws Exception {
		GuiSqlEventAction sql = new GuiSqlEventAction();
		sql.getRecord().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
		sql.getRecord().addFilter("class_evento", BizBSPCarpeta.class.getCanonicalName());
		sql.getRecord().addFilter("id_evento", this.GetcDato().getSecuencia());
		sql.getRecord().addFilter("descripcion", "Informe mensual");
  	BizPlantilla pl = BizCompany.getObjPlantilla(BizBSPUser.getUsrBSP().getCompany(), "sys_informe");
		if (pl!=null) sql.getRecord().addFilter("id_plantilla",pl.getId());
		sql.setDropListener(this);
		this.setFilterMap(a.getFilterMap());
		return sql;
	}
  JFilterMap filter;


  public JFilterMap getFilterMap() {
		return filter;
	}


	public void setFilterMap(JFilterMap action) {
		this.filter = action;
	}

  @Override
  public JAct Drop(JBaseWin zBaseWin) throws Exception {
   	if (zBaseWin instanceof GuiSqlEventAction) {
  		GuiSqlEventAction action = (GuiSqlEventAction)zBaseWin;
  		if (action.GetcDato().isAccionDOWNLOAD()) {
  			return new JActFileGenerate(action) { 
  				public String generate() throws Exception {
  		  		BizSqlEventHistory hist=((GuiSqlEventAction)getResult()).GetcDato().getObjSqlEvent().generarAviso(getFilterMap(),((GuiSqlEventAction)getResult()).GetcDato(),false);
  					return hist.getArchivoAsociado();
  				};
  			};
  		}
  		BizSqlEventHistory hist = action.GetcDato().execProcessExecute(this.getFilterMap());
  		if (hist==null) return new JActBack(this);
  		GuiSqlEventHistory ghist = new GuiSqlEventHistory();
  		ghist.setRecord(hist);
  		return new JActQuery(ghist);
  	}
		else if (zBaseWin instanceof GuiBSPCarpeta) {
			GuiBSPCarpeta l = (GuiBSPCarpeta) zBaseWin;
			if (l.GetcDato().isEspecial()) {
				l.GetcDato().execMoveToFolder(this.GetcDato());
				return null;
			}
		}
   	return super.Drop(zBaseWin);
  }

	public GuiCarpetas getContenido() throws Exception {
		JRecords<BizCarpeta> carp = getEspecialCarpeta();
		if (carp!=null) {
  	 	GuiBSPCarpetas winCarp = new GuiBSPCarpetas();
  	  winCarp.setRecords(carp);
  	  winCarp.SetVision(GuiCarpetas.VISION_FOLDER);
  	 	return winCarp;
		}
		return super.getContenido();
	}
	

	
	public JRecords<BizCarpeta> getEspecialCarpeta() throws Exception {
//  	if (GetcDato().getEspecial().equals("FAV")) {
//  		JRecords<BizCarpeta> carpetas = new JRecords<BizCarpeta>(BizCarpeta.class);
//  		carpetas.addFilter("company", BizUsuario.getUsr().getCompany());
//  		carpetas.addFixedFilter(
//  				"where lst_carpetas.customlist is not null and customlist  in "
//  			+ "(select list_id from LST_CUSTOM_LISTV2_FAV where LST_CUSTOM_LISTV2_FAV.list_id=lst_carpetas.customlist and LST_CUSTOM_LISTV2_FAV.company='"+BizUsuario.getUsr().getCompany()+"' and LST_CUSTOM_LISTV2_FAV.usuario='"+BizUsuario.getUsr().GetUsuario()+"') ");
//
//  		return carpetas;
//  	}
//  	if (GetcDato().getEspecial().equals(BizCustomListReporte.MENSUAL)) {
//  		JRecords<BizCarpeta> carpetas = new JRecords<BizCarpeta>(BizCarpeta.class);
//  		carpetas.addFilter("company", BizUsuario.getUsr().getCompany());
//  		carpetas.addFixedFilter(
//  				"where lst_carpetas.customlist is not null and customlist  in "
//  			+ "(select list_id from LST_CUSTOM_LISTV2_REP where LST_CUSTOM_LISTV2_REP.list_id=lst_carpetas.customlist and LST_CUSTOM_LISTV2_REP.periodo='"+BizCustomListReporte.MENSUAL+"' and LST_CUSTOM_LISTV2_REP.company='"+BizUsuario.getUsr().getCompany()+"' and LST_CUSTOM_LISTV2_REP.usuario='"+BizUsuario.getUsr().GetUsuario()+"') ");
//
//  		return carpetas;
//  	}
//  	if (GetcDato().getEspecial().equals(BizCustomListReporte.SEMANAL)) {
//  		JRecords<BizCarpeta> carpetas = new JRecords<BizCarpeta>(BizCarpeta.class);
//  		carpetas.addFilter("company", BizUsuario.getUsr().getCompany());
//  		carpetas.addFixedFilter(
//  				"where lst_carpetas.customlist is not null and customlist  in "
//  			+ "(select list_id from LST_CUSTOM_LISTV2_REP where LST_CUSTOM_LISTV2_REP.list_id=lst_carpetas.customlist and LST_CUSTOM_LISTV2_REP.periodo='"+BizCustomListReporte.SEMANAL+"' and LST_CUSTOM_LISTV2_REP.company='"+BizUsuario.getUsr().getCompany()+"' and LST_CUSTOM_LISTV2_REP.usuario='"+BizUsuario.getUsr().GetUsuario()+"') ");
//
//  		return carpetas;
//  	}
  	return null;
	}

}
