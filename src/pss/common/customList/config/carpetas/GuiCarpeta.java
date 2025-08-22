package pss.common.customList.config.carpetas;

import pss.common.customList.config.BizCustomListModules;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.customlist.GuiCustomListInforme;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActDelete;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActUpdate;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;

public class GuiCarpeta extends JWin implements IContenidoCarpeta {



  /**
   * Constructor de la Clase
   */
  public GuiCarpeta() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizCarpeta(); }
  @Override
	public int GetNroIcono()   throws Exception { 
  	if (this.GetcDato().isInvisible())
  		return 15018;
  	if (GetcDato().isCarpeta())
  		return 116;
  	return getContenidoCarpeta().GetNroIcono(); 
  }
  @Override
	public String GetTitle()   throws Exception { return GetcDato().getDescripcion(); }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCarpetaView.class; }
  @Override
  public Class<? extends JBaseForm> getFormUpdate() throws Exception {return FormCarpeta.class;};
  @Override
  public Class<? extends JBaseForm> getFormNew() throws Exception {return FormCarpeta.class;};
  @Override
	public String  getKeyField() throws Exception { return "secuencia"; }
  @Override
	public String  getDescripField() { return "descripcion_view"; }
  public BizCarpeta GetcDato() throws Exception { return (BizCarpeta) this.getRecord(); }

  public String GetValorTreeItemClave() throws Exception {
		return ""+GetcDato().getSecuencia();
	}

	public String GetValorTreeParentClave() throws Exception {
		return ""+GetcDato().getPadre();
	}

  @Override
  public void createActionMap() throws Exception {
//		createActionQuery(); 
//		createActionUpdate();
//		createActionDelete().setMulti(true);
		this.addActionUpdate(1000, "Modificar").setHelp("Permite abrir el formulario de modificación de "+GetTitle());
		this.addActionDelete(1001, "Eliminar").setHelp("Permite eliminar un "+GetTitle()).setMulti(true);
		addAction(18,  "Ver", null, 10076, false, false);
		addAction(810, "Subir", null, 15056, true, true);
		addAction(812, "Bajar", null, 15057, true, true);
		addAction(900, "Contenidos", null, 116, false, false);
		addAction(901, "Incorporar", null, 10022, true, true);

		IContenidoCarpeta contenido = getContenidoCarpeta();
		if (contenido !=null && !(contenido instanceof GuiCarpeta)) {
			getContenidoCarpeta().createActionMap();
			addActions(getContenidoCarpeta().getRawActionMap());
		}
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==810) 
			return true;
		if (a.getId()==812) 
			return true;
		
		if (a.getId()==18) return !getContenidoCarpeta().isSystemProtect() ;
		
		if (!GetcDato().isCarpeta()) { //NO es carpeta
			if (a.getId()==901) return false;
			if (a.getId()==900) return false;
			if (a.getId()==1001) return false;
			if (a.getId()==1000) return false;
			return this.getContenidoCarpeta().OkAction(a);
		}
		if (a.getId()==18) return true;

		if (a.getId()==900) 
			return true;
		if (a.getId()==901) 
			return GetcDato().isCarpeta();
		if (a.getId()==1000) 
			return GetcDato().isCarpeta() && isOwner() && !getContenidoCarpeta().isSystemProtect() ;
			
		if (a.getId()==1001) 
			return GetcDato().isCarpeta()&& isOwner() && !getContenidoCarpeta().isSystemProtect() ;
			

		return a.getId()<5;
  }
  public boolean isOwner() throws Exception {
  	return  !GetcDato().hasOwner() || GetcDato().getOwner().equals(BizUsuario.getUsr().GetUsuario());
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==1000) return new JActUpdate(this.getRelativeWin(), a.getId());
		if (a.getId()==1001) return new JActDelete(this, a.getId());

  	if (a.getId()==810) return new JActSubmit(this, a.getId()) {
  		@Override
			public void submit() throws Exception {
  			GetcDato().execSubir();
  		}
  	};
  	if (a.getId()==812) return new JActSubmit(this, a.getId()) {
  		@Override
			public void submit() throws Exception {
  			GetcDato().execBajar();
  		}
  	};
		if (!GetcDato().isCarpeta())
			return getContenidoCarpeta().getSubmitFor(a);
		
		if (a.getId()==900) return new JActWins(this.getContenido());
		if (a.getId()==901) return new JActWinsSelect(this.getCarpetas(),this,true);
		if (a.getId()==18) return new JActQuery(this);
		
		return super.getSubmitFor(a);
  }
  
  public boolean isSystemProtect() throws Exception {
  	return GetcDato().isSystemProtect();
  }
  
	public static String getNav() throws Exception {
		String nav = (String)BizUsuario.getUsr().getObjectMap("NAV"); 
		return nav==null?"":nav;
	}
	public static void setNav(String nav) throws Exception {
		BizUsuario.getUsr().putObjectMap("NAV",nav);
	}	
	public void addFolderToNav() throws Exception {
		if (!getNav().endsWith(";" + GetcDato().getSecuencia()))
			setNav(getNav() + ";" + GetcDato().getSecuencia());
	}


	IContenidoCarpeta objContent;
	public IContenidoCarpeta getContenidoCarpeta() throws Exception {
		if (objContent!=null) return objContent;
		if (!GetcDato().hasClase()) { // por compatibilidad
			if (GetcDato().isCarpeta())
				return objContent=this;
			else
				GetcDato().setClase(GuiCustomList.class.getName());
		}
  	return objContent= GetcDato().getObjClass();
  	
  }
  public GuiCarpetas getCustomListsActual() throws Exception {
		addFolderToNav();
	
		BizUsuario.getUsr().getObjBusiness().getCarpeta().GetcDato().execProcessCompletarCarpetas();
		GuiCarpetas f = BizUsuario.getUsr().getObjBusiness().getCarpetas();
		f.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		if (!getNav().equals("")) {
			String str = getNav();
			String folder = str.substring(str.lastIndexOf(";")+1);
			f.getRecords().addFilter("padre", Long.parseLong(folder)).setDynamic(true);;
		}
		f.getRecords().addOrderBy("orden","DESC");
		f.setPreviewFlag(JWins.PREVIEW_SI);
		f.setForceSelected(true);
   	f.setPreviewSplitPos(430);
		return f;
	}
	public GuiCarpetas getCarpetas() throws Exception {
		GuiCarpetas l = BizUsuario.getUsr().getObjBusiness().getCarpetas();
		l.getRecords().addFilter("company", GetcDato().getCompany());
		l.getRecords().addFilter("customlist", "null", "<>");
		l.SetVision(GuiCarpetas.VISION_FOLDER);
		return l;
	}

	public JAct getActResultPreview(BizAction a,boolean inform) throws Exception {
		return new JActQuery(this);
	}
	
	@Override
	public int GetSimpleClick() {
		return 18;
	}
	
	@Override
	public int GetDobleClick() throws Exception {
		return 20;
	}
	
	public GuiCarpetas getContenido() throws Exception {
		GuiCarpetas carp = BizUsuario.getUsr().getObjBusiness().getCarpetas();
		carp.setRecords(GetcDato().getContenidos());
		carp.SetVision(GuiCarpetas.VISION_FOLDER);
		return carp;
	}
	
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiCarpetas) {
			GuiCarpetas ls = (GuiCarpetas) zBaseWin;
			JIterator<BizCarpeta> it= ls.getRecords().getStaticIterator();
			while (it.hasMoreElements()) {
				BizCarpeta c = it.nextElement();
				c.execAddToFolder(GetcDato());
			}
		}
		else if (zBaseWin instanceof GuiCarpeta) {
			GuiCarpeta l = (GuiCarpeta) zBaseWin;
			l.GetcDato().execAddToFolder(GetcDato());
		}
  	if (zBaseWin instanceof GuiCustomList) {
  		GuiCustomList c = (GuiCustomList)zBaseWin;
  		this.GetcDato().execProcessInsert(c.GetcDato());
  		c.setDropListener(null);
  		return null;
  	}

		return super.Drop(zBaseWin);
	}

  public GuiCustomList getNewCustomList() throws Exception {
  	GuiCustomList l = new GuiCustomList();
  	l.GetcDato().setCompany(GetcDato().getFilterValue("company")==null?BizUsuario.getUsr().getCompany():GetcDato().getFilterValue("company"));
  	l.GetcDato().addFilter("record_owner", BizCustomListModules.class.getName());
  	l.setDropListener(this);
  	return l;
  }
  public GuiCustomList getNewReporte() throws Exception {
  	GuiCustomListInforme l = new GuiCustomListInforme();
  	l.GetcDato().setCompany(GetcDato().getFilterValue("company")==null?BizUsuario.getUsr().getCompany():GetcDato().getFilterValue("company"));
  	l.GetcDato().addFilter("record_owner", BizCustomListModules.class.getName());
  	l.GetcDato().addFilter("agrupado", "I");
  	l.GetcDato().setAgrupado("I");
  	l.setDropListener(this);
  	return l;
  }
  
	@Override
	public JBaseWin getObjectDrageable(String zone) throws Exception {
			return this;
	}

	
	@Override
	public boolean acceptDrop(String zone) throws Exception {
		return true;
		
	}


	@Override
	public boolean read(String company, String id) throws Exception {
		return GetcDato().read(company, id, null);
	}


	@Override
	public String imprimir(BizAction a) throws Exception {
		return null;
	}
 }
