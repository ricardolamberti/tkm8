package pss.common.customList.config.customlist;

import pss.common.customList.config.BizCustomListModules;
import pss.common.customList.config.field.campo.GuiCampo;
import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActWinsSelect;

public class GuiCustomListInforme extends GuiCustomList {

	public GuiCustomListInforme() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void createActionMap() throws Exception {
		addAction(20000 , "Agregar consulta Existente", null, 10080, false, false);
		addAction(20001 , "Crear consulta", null, 10079, false, false);
//		addAction(20002 , "Crear Filtro", null, 10076, true, true);
//		addAction(20003 , "Grabar", null, 10076, true, true);
		addAction(21    , "Informes", null, 10063, false, false).setRefreshAction(false);
		super.createActionMap();
	}
  
	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==20000) return true;
		if (a.getId()==20001) return true;
//		if (a.getId()==20002) return true;
//		if (a.getId()==20003) return true;
		if (a.getId()==18)    return true;
		if (a.getId()==21)    return true;
		if (a.getId()==21)    return true;
		if (a.getId()==21)    return true;
		if (a.getId()==4)     return true;
		if (a.getId()==2)     return true;
		return super.OkAction(a);
	}
	 public boolean isEdit() throws Exception {
	  	return false;
	  }
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==20000) return new JActWinsSelect(getConsultas(),this);
		if (a.getId()==20001) return new JActNew(getNewConsulta(),4);
//		if (a.getId()==20002) return new JActNew(getNewFiltro(),4);
//		if (a.getId()==20003) return new JActSubmit(this) {
//			@Override
//			public void Do() throws Exception {
//				//guardar posiciones
//				super.Do();
//			}
//		};
		return super.getSubmitFor(a);
	}
	
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiCustomList) {
			GuiCustomList l = (GuiCustomList) zBaseWin;
			BizCustomList newDoc = l.GetcDato().getClon();
			newDoc.setInvisible(true);
			GetcDato().execProcessAgregarInforme(newDoc);
			return null;
		}
//		if (zBaseWin instanceof GuiCampoGallery) {
//			GuiCampoGallery l = (GuiCampoGallery) zBaseWin;
////			GetcDato().execProcessAgregarFiltroInforme(l.GetcDato());
//			return null;
//		}
		if (zBaseWin instanceof GuiCampo) {
			GuiCampo l = (GuiCampo) zBaseWin;
			GetcDato().execProcessAgregarFiltroInforme(l.GetcDato());
			return null;
		}
		return super.Drop(zBaseWin);
	}
	public GuiCampo getNewFiltro() throws Exception {
		GuiCampo l = new GuiCampo();
		l.GetcDato().addFilter("company", GetcDato().getCompany());
  	l.GetcDato().addFilter("list_id", GetcDato().getListId());
  	l.GetcDato().addFilter("record_owner", BizCustomListModules.class.getName());
  	l.GetcDato().addFilter("rel_id", GetcDato().getRelId());
  	l.GetcDato().setObjCustomList(this.GetcDato());
  	l.GetcDato().setRecordOwner(GetcDato().getRecordOwner());
  	l.GetcDato().setRelId(GetcDato().getRelId());
  	l.SetVision("NEWFILTROS");
		l.setDropListener(this);
		return l;
	}	public GuiCustomList getNewConsulta() throws Exception {
		GuiCustomList l = new GuiCustomList();
		l.GetcDato().addFilter("company", GetcDato().getCompany());
  	l.GetcDato().addFilter("record_owner", BizCustomListModules.class.getName());
  	l.GetcDato().addFilter("rel_id", GetcDato().getRelId());
  	l.GetcDato().addFilter("invisible", true);
		l.setDropListener(this);
		return l;
	}

}
