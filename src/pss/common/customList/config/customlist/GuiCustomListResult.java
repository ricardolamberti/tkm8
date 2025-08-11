package pss.common.customList.config.customlist;

import java.awt.event.KeyEvent;

import pss.common.customList.config.dynamic.GuiDynamics;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiCustomListResult extends GuiCustomList {


	private boolean bInForm;

	public void setInForm(boolean v) {
		this.bInForm=v;
	}
  /**
   * Constructor de la Clase
   */
  public GuiCustomListResult() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCustomList(); }
  
  public int GetNroIcono()   throws Exception {
  	return 10027; 
  }
  public String GetTitle()   throws Exception { 
   	return GetcDato().getDescription();
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
  	if (GetcDato().isDato())
  		return FormCustomListResultDato.class;
  	if (GetcDato().isLista())
  		return FormCustomListResultLista.class;
  	if (GetcDato().isAgrupado())
  		return FormCustomListResultLista.class;
  	if (GetcDato().isMatriz())
  		return FormCustomListResultMatriz.class;
  	if (GetcDato().isGrafico())
  		return FormCustomListResultGraph.class;
  	if (GetcDato().isInforme())
  		return FormCustomListInformeResult.class;
  	 return FormCustomListResult.class; 
  }
  @Override
  public Class<? extends JBaseForm> getFormUpdate() throws Exception {
  	return getFormBase();
  }
  
  public BizCustomList GetcDato() throws Exception { return (BizCustomList) this.getRecord(); }


	public void createActionMap() throws Exception {
		this.addAction(10, "Configurar", null, 10062, true, true);
		BizAction a = addAction(80, "Imprimir HTML",KeyEvent.VK_F7, null, 10050, true, true);
		a.setImprimir(true);
		a.setImportance(1);
		a = addAction(85, "Imprimir PDF",KeyEvent.VK_F8, null, 10050, true, true);
		a.setNuevaVentana(true);
		a.setImportance(1);
		addAction(50, "Compartir", null, 10078, true, true);
		addAction(500, "Guardar" ,null, 84, true, true);
		a = addAction(100, "Datos", null, 10059, false, false);
		a.setFilterAction(true);
		a.setRefreshAction(false);
		addAction(516 , "Recalcule" ,null, 84, true, true);

	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
//		if (a.getId()==10) return !this.GetcDato().isSystemProtect();
		if (a.getId()==85) return true;
		if (a.getId()==517) return BizUsuario.IsAdminCompanyUser();

		return true;
	}
	 public boolean isEdit() throws Exception {
	  	return false;
	  }

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActQuery(this.getCustomList()); 
		if (a.getId()==100) return new JActWins(this.getDynamicWins(a),false,false);
		if (a.getId()==50) return new JActNew(this.getCompartir(a), 4);
//		if (a.getId()>100 &&a.getId()<200) 
//			return new JActWins(this.getDynamicWins(a.getId()-100));
		if (a.getId() == 80)
			return new JActFileGenerate(this, a.getId()) {
				public String generate() throws Exception {
					return imprimir(this.getActionSource(),true);
				}
			};
		if (a.getId() == 85)
				return new JActFileGenerate(this, a.getId()) {
					public String generate() throws Exception {
						return imprimir(this.getActionSource(),false);
					}
				};
			if (a.getId()==100) return new JActWins(this.getDynamicWins(a),false,false);

			if (a.getId()==516) return new JActSubmit(this){
				public void submit() throws Exception {
					GetcDato().execProcessRecalcule();
				}
			};
	
		return super.getSubmitFor(a);
	}



	public JWin getCustomList() throws Exception {
		if (GetcDato().isInforme())
			return getEditInforme(null);

		GuiCustomList l = new GuiCustomList();
		l.GetcDato().read(this.GetcDato().getCompany(), this.GetcDato().getListId());
		return l;
	}

	public JWins getDynamicWins(BizAction a) throws Exception {
		GuiDynamics d = new GuiDynamics();
		this.GetcDato().clean();
		if (getFilterMap()!=null)
			GetcDato().fillFilters(getFilterMap());
//		else
//			GetcDato().fillFilters(d.getRecords());
		if (getMode()==GuiCustomList.INFOEMBEDDED || getMode()==GuiCustomList.INFOONLY_TABLE || GetVision().equals("PREVIEW")) {
		  d.setForceHideActions(true);
			d.setToolbarForced(JBaseWin.TOOLBAR_NONE);
			d.setEditable(true);
		}
		d.SetVision(GetVision());
		d.setCustomList(this);
//		if (this.bInForm) 
		d.setShowFilters(true);
		return d;
	}

	
  public JWins getReporte() throws Exception {
		GuiDynamics d = new GuiDynamics();
//		d.setFilterMap(getFilterMap());
		d.setCustomList(this);
		d.SetVision(GetVision());
	//	BizCampos f=this.GetcDato().getObjFiltros();
	//	this.GetcDato().clean();
		//this.GetcDato().setObjFiltros(f);// no quiero que borre los filtros porque hay dejo el filtro para hacer el mailing
//		GetcDato().fillFilters(d.getRecords());
	//	d.setShowFilters(true);
		return d;
  }
	
}
