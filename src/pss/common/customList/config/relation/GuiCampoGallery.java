package pss.common.customList.config.relation;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.field.BizField;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.campo.GuiCampo;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActOptions;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActUpdate;

public class GuiCampoGallery extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCampoGallery() throws Exception {
  }

  

  public JRecord ObtenerDato()   throws Exception { return new BizCampoGallery(); }
  public int GetNroIcono()   throws Exception { 
  	if (GetcDato().getCampo().equals("")) return 15051;
  	if (!GetcDato().getFunction().equals("")) return 15055;
  	return 15054; 
  }
  public int GetNroIconoOpen()   throws Exception { 
  	if (GetcDato().getCampo().equals("")) return 15052;
  	return GetNroIcono(); 
  }
  public String GetTitle()   throws Exception { return "Campo"; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descr_completa"; }
  public BizCampoGallery GetcDato() throws Exception { return (BizCampoGallery) this.getRecord(); }

  
  
	public String GetValorTreeItemClave() throws Exception {
		if (GetcDato().getFolder()) {
			return GetcDato().getDescripcion();
		}
		if (GetcDato().getCampo().equals("")) 
			return this.GetcDato().getDescripcion();
		if (GetcDato().getFunction().equals("")) 
			return this.GetcDato().getGrupo()+"_"+GetcDato().getCampo();
		return this.GetcDato().getGrupo()+"_"+GetcDato().getCampo()+"_"+GetcDato().getFunction();
	}

	@Override
	public String getSwapKeyField() throws Exception {
		return null;
	}
	public String GetValorTreeParentClave() throws Exception {
		if (GetcDato().getFolder()) {
			return GetcDato().getGrupo();
		}
		if (GetcDato().getCampo().equals("")) 
			return GetcDato().getGrupo();
		if (GetcDato().getCampo().equals(BizField.FUNTION_COUNT) && GetcDato().getFunction().equals(BizField.FUNTION_COUNT)) 
			return "root_";
		if (!GetcDato().getFunction().equals("") && !GetcDato().getFunction().equals(BizCampo.FORMULA)) 
			return GetcDato().getGrupo()+"_"+GetcDato().getCampo();
		if (GetcDato().getGrupo().equals("")) 
			return "root_";
		return GetcDato().getGrupo();
	}

	@Override
	public boolean GetValorTreeSelectionable() throws Exception {
		if (GetcDato().getCampo().equals("")) 
			return false;
		return true;
	}
  @Override
  public void createActionMap() throws Exception {
//		addAction(10, "A Columna", null, 15059, true, true);
//		addAction(11, "Quitar Columna", null, 15058, true, true);
//		addAction(20, "A Fila", null, 15059, true, true);
//		addAction(21, "Quitar Fila", null, 15058, true, true);
		addAction(30, "A Campo", null, 15059, true, true);
		addAction(31, "Quitar Campo", null, 15058, true, true);
		addAction(40, "A Filtro", null, 15059, true, true);
		addAction(41, "Quitar Filtro", null, 15058, true, true);
//		addAction(50, "A Filtro SQL", null, 15059, true, true);
//		addAction(60, "A Orden", null, 15059, true, true);
//		addAction(61, "Quitar Orden", null, 15058, true, true);
//		addAction(62, "Con Ranking", null, 15059, true, true);
//		addAction(63, "Quitar Ranking", null, 15058, true, true);
//		addAction(70, "Editar Eje", null, 10064, true, true);
		addAction(72, "Editar Campo", null, 10064, true, true);
//		addAction(73, "Editar Filtro", null, 10064, true, true);
  }
  
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
//  	if (a.getId()==50) return isSqlAdvanced();
//  	if (a.getId()==10) return !GetcDato().isColumna() && isMatriz();
//  	if (a.getId()==11) return GetcDato().isColumna() && isMatriz();
//  	if (a.getId()==20) return !GetcDato().isFila() && isMatriz();
//  	if (a.getId()==21) return GetcDato().isFila() && isMatriz();
  	if (a.getId()==31) return GetcDato().isCampo();
  	if (a.getId()==41) return GetcDato().isFiltro();
//  	if (a.getId()==60) return !GetcDato().isOrden();
//  	if (a.getId()==61) return GetcDato().isOrden();
//  	if (a.getId()==62) return (GetcDato().isColumna() || GetcDato().isFila()) && isMatriz();
//  	if (a.getId()==63) return GetcDato().isOrden()  && isMatriz();
//   	if (a.getId()==70) return (GetcDato().isColumna() || GetcDato().isFila()) && isMatriz();
   	if (a.getId()==72) return GetcDato().isCampo();
//   	if (a.getId()==73) return GetcDato().isFiltro();
    return super.OkAction(a);
  }
  
  public boolean isSqlAdvanced() throws Exception {
  	if ((GuiCamposGallery)getParent()==null) return false;
  	if (((GuiCamposGallery)getParent()).getObjCustomList()==null) return false;
  	return ((GuiCamposGallery)getParent()).getObjCustomList().GetcDato().isSqlAdvanced();
  }
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
//  	if (a.getId()==10) return processAction(a.getId(),((GuiCamposGallery)getParent()).getObjCustomList(),"COLUMNA");
//  	if (a.getId()==20) return processAction(a.getId(),((GuiCamposGallery)getParent()).getObjCustomList(),"FILA");
  	if (a.getId()==30) return processAction(a.getId(),((GuiCamposGallery)getParent()).getObjCustomList(),"CAMPOS");
		if (a.getId()==40) return new JActNew(getNewFiltro(),4);
//		if (a.getId()==50) return new JActNew(getNewFiltroSQL(),0);
//		if (a.getId()==60) return new JActNew(this.getNewOrder(), 4);
//		if (a.getId()==62) return new JActUpdate(this.getRanking(), BizAction.DROP);
//		if (a.getId()==70) return new JActUpdate(getEje(),BizAction.UPDATE);
		if (a.getId()==72) return new JActUpdate(getCampo(),BizAction.UPDATE);
//		if (a.getId()==73) return new JActUpdate(getFiltro(),BizAction.UPDATE);
//	 	if (a.getId()==11) return new JActSubmit(this, a.getId()) {
//  		public void submit() throws Exception {
//  			GetcDato().execProcessDeleteColumna(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
//  		}
//  	};
//	 	if (a.getId()==21) return new JActSubmit(this, a.getId()) {
//  		public void submit() throws Exception {
//  			GetcDato().execProcessDeleteFila(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
//  		}
//  	};
	 	if (a.getId()==31) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execProcessDeleteCampo(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
  		}
  	};

//	 	if (a.getId()==61) return new JActSubmit(this, a.getId()) {
//  		public void submit() throws Exception {
//  			GetcDato().execProcessDeleteOrden(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
//  		}
//  	};
//	 	if (a.getId()==63) return new JActSubmit(this, a.getId()) {
//  		public void submit() throws Exception {
//  			GetcDato().execProcessDeleteOrden(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
//  		}
//  	};

	  return super.getSubmitFor(a);
  }

  @Override
	public JBaseWin getObjectDrageable(String zone) throws Exception {

		return this;
	}

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }

  public boolean isLista() throws Exception {
  	if (((GuiCamposGallery)getParent()).getObjCustomList()==null) return false;
  	return (((GuiCamposGallery)getParent()).getObjCustomList().GetcDato()).isLista();
  }
  public boolean isAgrupado() throws Exception {
  	if (((GuiCamposGallery)getParent()).getObjCustomList()==null) return false;
  	return (((GuiCamposGallery)getParent()).getObjCustomList().GetcDato()).isAgrupado();
  }
  public boolean isInforme() throws Exception {
  	if (((GuiCamposGallery)getParent()).getObjCustomList()==null) return false;
  	return (((GuiCamposGallery)getParent()).getObjCustomList().GetcDato()).isInforme();
  }
  public boolean isMatriz() throws Exception {
  	if (((GuiCamposGallery)getParent()).getObjCustomList()==null) return false;
  	return (((GuiCamposGallery)getParent()).getObjCustomList().GetcDato()).isMatriz();
  }

  public GuiCampo getEje() throws Exception {
  	GuiCampo eje = new GuiCampo();
  	eje.setRecord(GetcDato().getCampo(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato()));
  	return eje;
  }
  public GuiCampo getCampo() throws Exception {
  	GuiCampo campo = new GuiCampo();
  	campo.setRecord(GetcDato().getCampo(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato()));
  	return campo;
  }

//
//  public GuiOrder getRanking() throws Exception {
//  	GuiOrder orden = new GuiOrder();
//  	BizCampo eje = getEje().GetccDato();
//  	BizOrder borden =eje.getObjRanking();
//  	if (borden==null) {
//  		borden = new BizOrder();
//  		borden.setObjCustomList(eje.getObjCustomList());
//  		borden.setCompany(eje.getCompany());
//  		borden.setListId(eje.getListId());
//  	}
//  	orden.setRecord(borden);
//  	orden.setDropListener(this);
//  	return orden;
//  }

  public JAct processAction(int idAction,GuiCustomList list,String mode) throws Exception {
//		if (!mode.equals("CAMPOS") && GuiCustomList.isCampoClave(this)) {
//			this.SetVision(mode);
//			return new JActWinsSelect(GuiCustomList.getOpcionesCampoClave(this),this);
//		}
//  	if (mode.equals("COLUMNA")) {
//  		GuiCamposGallery op = getOperationsEjes();
//    	if (op==null) {
//    		 return new JActSubmit(this, idAction) {
//  	  		public void submit() throws Exception {
//  	  			GetcDato().execProcessAddColumna(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
//  	  		}
//  	  	};
//    	}
//    	op.setObjCustomList(list);
//    	op.SetVision(mode);
//    	return new JActOptions(op,this);
//    } else if (mode.equals("FILA")) {
//  		GuiCamposGallery op = getOperationsEjes();
//    	if (op==null) {
//    		 return new JActSubmit(this, idAction) {
//  	  		public void submit() throws Exception {
//  	  			GetcDato().execProcessAddFila(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
//  	  		}
//  	  	};
//    	}
//    	op.setObjCustomList(list);
//    	op.SetVision(mode);
//    	return new JActOptions(op,this);
//    }	else 
    if (mode.equals("CAMPOS")) {
  		GuiCamposGallery op = getOperationsCampos();
    	if (op==null) {
    		 return new JActSubmit(this, idAction) {
  	  		public void submit() throws Exception {
  	  			GetcDato().execProcessAddCampo(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
  	  		}
  	  	};
    	}
    	op.setObjCustomList(list);
    	op.SetVision(mode);
    	return new JActOptions(op,this);
  	}
  	return null;
  }
  public GuiCamposGallery getOperationsCampos() throws Exception {
  	JRecords<BizCampoGallery> gal = GetcDato().getOperacionesCampos();
  	if (gal==null || gal.getStaticItems().size()==1) return null;
  	GuiCamposGallery campos = new GuiCamposGallery();
  	campos.setRecords(gal);
		return campos;
  }
//  public GuiCamposGallery getOperationsEjes() throws Exception {
//  	JRecords<BizCampoGallery> gal = GetcDato().getOperacionesEjes();
//  	if (gal==null || gal.getStaticItems().size()==1) return null;
//  	GuiCamposGallery campos = new GuiCamposGallery();
//  	campos.setRecords(gal);
//		return campos;
//  }
  public GuiCamposGallery getOperationsFiltros() throws Exception {
  	JRecords<BizCampoGallery> gal = GetcDato().getOperacionesFiltros();
  	if (gal==null || gal.getStaticItems().size()==1) return null;
  	GuiCamposGallery campos = new GuiCamposGallery();
  	campos.setRecords(gal);
		return campos;
  }

  public GuiCampo getNewFiltro() throws Exception {
  	GuiCampo c = new GuiCampo();
  	BizCustomList list=((GuiCamposGallery)getParent()).getObjCustomList().GetcDato();
  	c.GetcDato().setObjCustomList(list);
  	c.GetcDato().setCompany(list.getCompany());
  	c.GetcDato().setListId(list.getListId());
  	c.GetcDato().setVisible(false);
  	c.GetcDato().setHasFiltro(true);
  	c.GetcDato().setRelId(this.GetcDato().getRelId());
  	c.GetcDato().setOrigenDatos(list.getRelId());
  	c.GetcDato().setRecordOwner(this.GetcDato().getRecordOwner());
  	c.GetcDato().setRecordSource(this.GetcDato().getRecordSource());
  	c.GetcDato().setSerialDeep(this.GetcDato().getSerialDeep());
  	c.GetcDato().setCampo(this.GetcDato().getCampo());
  	c.GetcDato().calculeIdGallery();
  	c.setDropListener(((GuiCamposGallery)getParent()).getObjCustomList());
  	return c;
  }

  @Override
  public JAct Drop(JBaseWin zBaseWin) throws Exception {
   	boolean ignorar=false;
//  	if (zBaseWin instanceof GuiVirtual) {
//  		GuiVirtual virtual = (GuiVirtual)zBaseWin;
//  		if (virtual.GetcDato().getValor().equals("Descartar")) return null;
//  		if (virtual.GetcDato().getValor().equals("Mantener")) {
//  			zBaseWin = this;
//  			ignorar=true;
//  		}
//  		if (virtual.GetcDato().getValor().equals("Convertir")) {
//    		GuiCampoGallery campo = this;
//    		BizCustomList list =	(((GuiCamposGallery)getParent()).getObjCustomList()).GetcDato();
//    		list.execConvertirAListado();
//  			campo.GetcDato().execProcessAddCampo(list);
//  			return null;
//  		}
//  	}

  	if (zBaseWin instanceof GuiCampoGallery ) {
  		GuiCampoGallery gal = (GuiCampoGallery) zBaseWin;
//  		if (GuiCustomList.isCampoClave(this)&&!ignorar&&!gal.GetVision().equals("CAMPOS")) {
//  			return new JActWinsSelect(GuiCustomList.getOpcionesCampoClave(this),this);
//  		}
// 		if (gal.GetVision().equals("CAMPOS"))
  			gal.GetcDato().execProcessAddCampo(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
//  		if (gal.GetVision().equals("COLUMNA"))
//  			gal.GetcDato().execProcessAddColumna(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
//  		if (gal.GetVision().equals("FILA"))
//  			gal.GetcDato().execProcessAddFila(((GuiCamposGallery)getParent()).getObjCustomList().GetcDato());
 		
  	} 
  	if (zBaseWin instanceof GuiCampo ) {
  		GuiCampo campo = (GuiCampo) zBaseWin; 
  		((GuiCamposGallery)getParent()).getObjCustomList().GetcDato().removeCampo(campo.GetcDato());
  		return null;
  	}
//  	if (zBaseWin instanceof GuiCampo ) {
//  		GuiCampo campo = (GuiCampo) zBaseWin;
//  		campo.GetccDato().execProcessDelete();
//  		return null;
//  	}
//  	if (zBaseWin instanceof GuiFiltro ) {
//  		GuiFiltro campo = (GuiFiltro) zBaseWin;
//  		campo.GetcDato().execProcessDelete();
//  		return null;
//  	}  	

  	return super.Drop(zBaseWin);
  }
  
  @Override
  public String getFieldBackground(String zColName) throws Exception {
  	if (GetcDato().isFila()) return "c4c601";
  	if (GetcDato().isColumna()) return "04a76f";
  	if (GetcDato().isCampo()) return "e99d4b";
  	if (GetcDato().isFiltro()) return "b7da7f";
  	if (GetcDato().isOrden()) return "f0f185";
  	return super.getFieldBackground(zColName);
  }
  


	public boolean acceptDrop(String zone) throws Exception {
		return true;
	}
  
	public int GetDobleClick(String zColName, boolean isEje) throws Exception {
		return 30;
	}
  
 }
