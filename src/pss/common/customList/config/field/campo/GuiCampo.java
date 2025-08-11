package pss.common.customList.config.field.campo;

import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.relation.GuiCampoGallery;
import pss.common.customList.config.relation.GuiCamposGallery;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFieldSwapWins;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiCampo extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCampo() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCampo(); }
  public int GetNroIcono()   throws Exception { return 5060; }
  public String GetTitle()   throws Exception { return GetcDato().getDescrCampoWithOpAndOrder(); }
 // public String getTitleRight() throws Exception { return GetcDato().getRolDescription(); }
 public Class<? extends JBaseForm> getFormBase() throws Exception { 
	  if (GetcDato().isGroup())
	  	return FormCampoOR.class;
	  if (GetVision().equals("NEWFILTROS")||GetcDato().isDependiente())
	  	return FormCampoNewFiltro.class;
	  if (GetcDato().getFuncion().equals(BizCampo.FUNTION_FORMULA)||GetcDato().getCampo().equals(BizCampo.FUNTION_FORMULA)) 
	  	return FormCampoFormula.class;
	  if (GetVision().equals("FILTROS"))
	  	return FormCampoFilter.class;
	  return FormCampo.class; 
	  }
  @Override
  public Class<? extends JBaseForm> getFormEmbedded() throws Exception {
	  if (GetcDato().isCampoFormula()) 
	  	return FormCampoFormulaEmbedded.class;
	  if (GetcDato().isGroup())
	  	return FormCampoOrEmbbeded.class;
	  if (GetVision().equals("FILTROS") && GetcDato().hasStadistic())
	  	return FormCampoStadistic.class;
	  if (GetVision().equals("FILTROS") && GetcDato().isDateInput())
	  	return FormCampoDate.class;
	  if (GetVision().equals("FILTROS") && GetcDato().isDateTimeInput())
	  	return FormCampoDate.class;
	  if (GetVision().equals("FILTROS")||GetcDato().isDependiente())
	  	return FormCampoFilter.class;
  	return FormCampoEmbedded.class;
  }
  public String  getKeyField() throws Exception {
   	return "secuencia"; 
  	
  }
  public String  getDescripField() { return "descr_campo"; }
  public BizCampo GetcDato() throws Exception { return (BizCampo) this.getRecord(); }
  public BizCampo GetccDato() throws Exception { return (BizCampo) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate().setModal(true);
		createActionDelete();
		addAction(10, "Subir", null, 15056, true, true);
		addAction(12, "Bajar", null, 15057, true, true);
		addAction(14, "Top Orden", null, 15056, true, true);
		addAction(15, "Duplicar", null, 5137, true, true);
		addAction(20, "Detalle", null, 10000, false, false);
		addAction(30, "Ocultar", null, 10000, false, false);
		addAction(40, "Mostrar", null, 10000, false, false);
		addAction(530, "Agregar Filtro OR", null, 206, false, false).setModal(true);
		addAction(531, "Agregar Filtro AND", null, 206, false, false).setModal(true);
	  
  }
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  //	if (a.getId()==BizAction.UPDATE && GetVision().equals("FILTROS")) return false;
  	if (a.getId()==BizAction.REMOVE && GetVision().equals("FILTROS")) return false;
  	if (a.getId()==10 /*&& GetVision().equals("FILTROS")*/) return false;
  	if (a.getId()==12 /*&& GetVision().equals("FILTROS")*/) return false;
  	if (a.getId()==14 && !GetcDato().hasOrdenAscDesc()&& GetVision().equals("FILTROS")) return false;
  	if (a.getId()==15 && GetVision().equals("FILTROS")) return false;
  	if (a.getId()==30 && !GetcDato().isOcultarReporte()) return false;
  	if (a.getId()==30 && !GetcDato().isOcultarReporte()) return false;
  	if (a.getId()==40 && GetcDato().isOcultarReporte()) return false;
		if (a.getId() == 530 && GetcDato().isGroup() ) return true;
		if (a.getId() == 531 && GetcDato().isGroup() ) return true;
  	return super.OkAction(a);
  }
	
	@Override
	protected boolean checkActionOnDrop(BizAction a) throws Exception {
		if (a.getId()==530) return true;
		if (a.getId()==531) return true;
		return super.checkActionOnDrop(a);
	}
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execSubir(-1);
  		}
  	};
  	if (a.getId()==12) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execBajar(-1);
  		}
  	};
  	if (a.getId()==14) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execTop();
  		}
  	};
  	if (a.getId()==15) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execDuplicar();
  		}
  	};
  	if (a.getId()==20) return new JActWins(this.getCamposHijos());
  	if (a.getId()==30) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execOcultar();
  		}
  	};

  	if (a.getId()==40) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execMostrar();
  		}
  	};
		if (a.getId()==530) return new JActFieldSwapWins(getDetalles(),getCustomList().getGallery(),this,"campo_serial","campo_serial");
		if (a.getId()==531) return new JActNew(getFiltroOR(),4);
    return super.getSubmitFor(a);
  }
  
	public GuiCustomList getCustomList() throws Exception {
		if (getParent() instanceof GuiCustomList)
			return (GuiCustomList) getParent();
		else 		if (getParent() instanceof GuiCampos)
			return ((GuiCampos) getParent()).getCustomList();
		return ((GuiCampo) getParent()).getCustomList();
	}
  public JWins getCamposHijos() throws Exception {
  	GuiCampos c = new GuiCampos();
  	c.getRecords().addFilter("company", this.GetcDato().getCompany());
  	c.getRecords().addFilter("list_id", this.GetcDato().getListId());
  	c.getRecords().addFilter("orden_padre", ""+this.GetcDato().getOrden());
  	c.SetVision("FILTROS");
  	GuiCustomList w = new GuiCustomList();
  	w.setRecord(GetcDato().getObjCustomList());
  	c.setObjCustomList(w);
  	c.setObjFiltroParent(this);

//  	c.getRecords().addFilter("record_owner", this.GetcDato().getRecordOwner());
  	return c;
  }
	public GuiCampo getFiltroOR() throws Exception {
  	GuiCampo e = new GuiCampo();
  	String oper = this.GetcDato().getOperacion().equals(BizCampo.OPER_OR)?BizCampo.OPER_AND_INTERNO:BizCampo.OPER_OR;
  	e.GetcDato().setObjFiltroParent(this.GetcDato());
  	e.GetcDato().setObjCustomList(getCustomList().GetcDato());
  	e.GetcDato().setParent(getCustomList().GetcDato());
   	e.GetcDato().addFilter("operacion", oper);
   	e.GetcDato().setOrdenPadre(""+this.GetcDato().getOrden());
  	e.GetcDato().setOperacion(oper);
  	e.GetcDato().setVisible(false);
  	e.SetVision("NEWFILTROS");
  	e.setParent(this);
  	e.setDropListener(this);
  	
  	return e;
  }
  public JWins getDetalles() throws Exception {
  	GuiCampos c = new GuiCampos();
  	c.setRecords(GetcDato().getObjDetalle());
  	c.setObjCustomList(getCustomList());
  	c.setObjFiltroParent(this);
  	return c;
  }
  public boolean acceptDrop(String zone) throws Exception {
		return this.GetcDato().getObjCustomList().GetVision().equalsIgnoreCase("preview");
	}
  @Override
	public JBaseWin getObjectDrageable(String zone) throws Exception {

		return this;
	}

  
  @Override
  public JAct Drop(JBaseWin zBaseWin) throws Exception {
  	if (zBaseWin instanceof GuiCampo) {
  		if (!GetcDato().isGroup() && !GetcDato().isDependiente()) {
	  		GuiCampo eje = (GuiCampo)zBaseWin;
	 			if (eje.GetccDato().getOrden()>this.GetccDato().getOrden()) {
	 				eje.GetccDato().execSubir(this.GetccDato().getOrden());
	 			} else {
	 				eje.GetccDato().execBajar(this.GetccDato().getOrden());
	 			}
  		}
  		return null;
  	}
//  	if (zBaseWin instanceof GuiCampoGallery) {
//    		GuiCampoGallery campo = (GuiCampoGallery)zBaseWin;
//  	}
		if (zBaseWin instanceof GuiCamposGallery) {
			GuiCamposGallery campos = (GuiCamposGallery)zBaseWin;
			GetcDato().getObjDetalle().execProcessFillRecords(this.GetcDato(),campos.getRecords(),"campo_serial","campo_serial");
			return null;
		}
		if (zBaseWin instanceof GuiCampo) {
			GuiCampo filtro = (GuiCampo)zBaseWin;
			filtro.GetcDato().processDrop(this.getCustomList().GetcDato());
			filtro.setDropListener(null);

				return null;
		}
  	return null;
  }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  
  @Override
  public String getRowGridMode() throws Exception {
		if (GetcDato().isFiltroActivo())
			return "primary";
		else
			return "info";
  }
  @Override
  public boolean isHide() throws Exception {
  	return !GetVision().equals("FILTROS") && GetcDato().isDependiente();
  }

 }
