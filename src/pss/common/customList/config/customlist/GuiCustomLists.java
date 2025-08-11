package pss.common.customList.config.customlist;

import java.awt.event.KeyEvent;

import pss.common.customList.config.BizCustomListModules;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.company.GuiCompany;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiCustomLists extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiCustomLists() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10027; } 
  public String  GetTitle()    throws Exception  { return "Listados"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiCustomList.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addAction( 1, "Nuevo DM" , KeyEvent.VK_INSERT, null, 10077, true, true);
    addAction( 10, "Nueva Informe" , KeyEvent.VK_INSERT, null, 10077, true, true);
  }


	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		if (BizUsuario.getUsr().isAdminUser())
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());
		zFiltros.addEditResponsive("Descripcion",JBaseForm.CHAR, "description", "ilike").setPopupIcon(false);
  	JFormControl c = zFiltros.addCheckResponsive("favoritos","fav");
  	c.SetValorDefault(/*hasFav()?"S":*/"N");
  	c.setRefreshForm(true);
   	JFormControl c1 = zFiltros.addCheckResponsive("Solor mis DMs","repo_owner");
  	c1.SetValorDefault(/*hasFav()?"S":*/"S");
  	c1.setRefreshForm(true);

	}
  public boolean hasFav() throws Exception {
  	JRecords<BizCustomListFav> recs = new JRecords<BizCustomListFav>(BizCustomListFav.class);
  	recs.addFilter("company", BizUsuario.getUsr().getCompany());
  	recs.addFilter("usuario", BizUsuario.getUsr().GetUsuario());
  	return recs.exists();
  }
  
  @Override
  protected void asignFilterByControl(JFormControl control) throws Exception {
  	if (control.getIdControl().equals("fav") ) {
  		if (control.getValue().equals("") || control.getValue().equals("S")) {
	  		this.getRecords().addJoin("LST_CUSTOM_LISTV2_FAV");
	  		this.getRecords().addFixedFilter("where LST_CUSTOM_LISTV2_FAV.company=LST_CUSTOM_LISTV2.company"+
	  				" and LST_CUSTOM_LISTV2_FAV.list_id=LST_CUSTOM_LISTV2.list_id and " +
	  				" LST_CUSTOM_LISTV2_FAV.usuario = '"+BizUsuario.getUsr().GetUsuario()+"' ").setDynamic(true);
	  		return;
  		}  		
  	}
  	if (control.getIdControl().equals("repo_owner") ) {
  		if ( control.getValue().equals("S")) {
	  		this.getRecords().addFilter("repo_owner",BizUsuario.getUsr().GetUsuario());
	 		} 
  		return;
  	}
  	super.asignFilterByControl(control);
  }

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    if (GetVision().equals("SYSTEM")) 
      zLista.AddColumnaLista("company");
    zLista.AddColumnaLista("description");
//    zLista.AddColumnaLista("Favorito", "fav");
  }
  

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActNew(this.getNewCustomList(), 0);
		if (a.getId()==10) return new JActNew(this.getNewReport(), 0);
		return super.getSubmit(a);
	}
	
  public GuiCustomList getNewCustomList() throws Exception {
  	GuiCustomList l = new GuiCustomList();
  	l.GetcDato().addFilter("company", this.getFilterValue("company"));
  	l.GetcDato().addFilter("record_owner", BizCustomListModules.class.getName());
  	return l;
  }
  
  public GuiCustomList getNewReport() throws Exception {
  	GuiCustomListInforme l = new GuiCustomListInforme();
  	l.GetcDato().addFilter("company", this.getFilterValue("company"));
  	l.GetcDato().addFilter("record_owner", BizCustomListModules.class.getName());
  	l.GetcDato().addFilter("agrupado", "I");
  	return l;
  }
	
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiCompany) {
			JIterator<JWin> iter = this.getStaticIterator();
			while (iter.hasMoreElements()) {
				GuiCustomList tr = (GuiCustomList) iter.nextElement();
			    tr.Drop(zBaseWin);	
			}
		}
		return null;
	}

}
