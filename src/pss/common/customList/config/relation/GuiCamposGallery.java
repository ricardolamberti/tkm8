package pss.common.customList.config.relation;

import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.customlist.GuiCustomListResult;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.campo.GuiCampo;
import pss.common.customList.config.field.campo.GuiCampos;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFieldSwapWins;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiCamposGallery extends JWins {


	private GuiCustomList customList;

	public void setObjCustomList(GuiCustomList value) {
		this.customList=value;
	}

	public GuiCustomList getObjCustomList() {
		return this.customList;
	}

  /**
   * Constructor de la Clase
   */
  public GuiCamposGallery() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10064; } 
  public String  GetTitle()    throws Exception  { return "Campos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiCampoGallery.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
  public void createActionMap() throws Exception {
		this.addAction(100, "Preview", null, 206, false, false);
		this.addAction(105, "Agregar Campos", null, 206, true, true).setModal(true);
		this.addAction(110, "Agregar Filtro OR", null, 206, true, true);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==100) return new JActQuery(this.getResultPreview(true,GuiCustomList.ONLY_TABLE));
		if (a.getId()==105) return new JActFieldSwapWins(getCampos(),getGallery(),getObjCustomList(),"campo_serial","campo_serial");
		if (a.getId()==110) return new JActNew(getFiltroOR(),4);
  	return super.getSubmitFor(a);
  }

	public GuiCustomListResult getResultPreview(boolean inform, long mode) throws Exception {
		GuiCustomListResult c = new GuiCustomListResult();
		c.setRecord(this.customList.GetBaseDato());
		c.setToolbarForced(JBaseWin.TOOLBAR_NONE);
		c.setInForm(inform);
		if (inform) {
			c.SetVision("PREVIEW");
			c.setMode(mode);
		}
		return c;
	}
	public GuiCamposGallery getGallery() throws Exception {
  	GuiCamposGallery g = new GuiCamposGallery();
 	  g.setRecords(this.getObjCustomList().GetcDato().getOnlyCampos(this.getObjCustomList().GetcDato(), null, true,false));
  	g.setObjCustomList(this.getObjCustomList());
		g.setPreviewFlag(JWins.PREVIEW_SI);
		g.setForceActionPreview(100);
		g.setPreviewSplitPos(150);
		g.setModeView(JBaseWin.MODE_TREE);
		g.SetVision("SWAP");
	//	g.setToolbarForced(JWins.TOOLBAR_LEFT);
		g.toStatic();
  	return g;
	}
	public GuiCampos getCampos() throws Exception {
		GuiCampos campos = new GuiCampos();
		campos.setRecords(getObjCustomList().GetcDato().getObjAllCampos());
		return campos;
	}
	public GuiCampo getFiltroOR() throws Exception {
		GuiCampo c = new GuiCampo();
		c.GetcDato().addFilter("company", getObjCustomList().GetcDato().getCompany());
		c.GetcDato().addFilter("list_id", getObjCustomList().GetcDato().getListId());
		c.GetcDato().addFilter("rel_id", getObjCustomList().GetcDato().getRelId());
		c.GetcDato().addFilter("record_owner", getObjCustomList().GetcDato().getRecordOwner());
		c.GetcDato().addFilter("operacion", BizCampo.OPER_OR);
    c.GetcDato().addFilter("orden_padre", "0");
    c.GetcDato().setRelId(getObjCustomList().GetcDato().getRelId());
    c.GetcDato().setOrigenDatos(getObjCustomList().GetcDato().getRelId());
		c.GetcDato().setOperacion(BizCampo.OPER_OR);
  	c.GetcDato().setObjFiltroParent(null);
   	c.GetcDato().setOrdenPadre("0");
		c.GetcDato().setObjCustomList(getObjCustomList().GetcDato());
		c.setDropListener(getObjCustomList());
		return c;
	}

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
//    zLista.AddColumnaLista("rel_id_descr");
    zLista.AddColumnaLista("descr_completa");
  }
  
  @Override
  public int getWebPageSize() {
  	return 25;
  }

  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	if (GetVision().equals("SWAP")) return;
  	zFiltros.addComboResponsive("Grupo", "grupo", new JControlCombo() {
  		@Override
  		public JWins getRecords(boolean one) throws Exception {
  			return getFuentes();
  		}
  	}, true).setRefreshForm(true);
 // 	zFiltros.NuevoCheck("Destacadas", "simple").SetValorDefault(true);
  	zFiltros.addEditResponsive("Descripción", "descripcion"); 
  }
  
  public JWins getFuentes() throws Exception {
  	if (getObjCustomList().GetcDato().getObjRelation()==null) {
  		return   JWins.createVirtualWinsFromMap((JMap)JCollectionFactory.createOrderedMap());
  	}
  	return  JWins.createVirtualWinsFromMap(getObjCustomList().GetcDato().getObjRelation().getTargetRelationMap().getAllFolderGroup());
  }
  

	@Override
	public void asignFiltersFromFilterBar(JFormFiltro filterBar) throws Exception {
		super.asignFiltersFromFilterBar(filterBar);
	}
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }


}
