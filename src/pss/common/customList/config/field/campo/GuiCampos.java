package pss.common.customList.config.field.campo;

import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.relation.GuiCampoGallery;
import pss.common.customList.config.relation.GuiCamposGallery;
import pss.core.services.records.JRecords;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.lists.JWinList;

public class GuiCampos extends JWins {


	private GuiCustomList customList;
	private GuiCampo filtroParent;

	public void setObjFiltroParent(GuiCampo value) {
		this.filtroParent=value;
	}

	public void setObjCustomList(GuiCustomList value) {
		this.customList=value;
	}
	
	public GuiCustomList getCustomList() throws Exception {
		return customList;
	}
  /**
   * Constructor de la Clase
   */
  public GuiCampos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10064; } 
  public String  GetTitle()    throws Exception  { return "Campos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiCampo.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
		this.addAction(10, "Nuevo Campo", null, 10064, true, true);
		this.addAction(20, "Nuevo Filtro", null, 10064, true, true).setModal(true);
		this.addAction(30, "Nuevo Filtro OR", null, 10064, true, true).setModal(true);
	 }

	public boolean acceptDrop(String zone) throws Exception {
		return true;
	}
	
	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==10) return !GetVision().equals("FILTROS");
		if (a.getId()==20) return GetVision().equals("FILTROS");
		if (a.getId()==30) return GetVision().equals("FILTROS");
		return super.OkAction(a);
	}

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
//		if (a.getId()==10) return new JActWinsSelect(this.getCamposGallery(a), this.getNewCampo());
		if (a.getId()==10) return new JActNew( this.getNewCampo(),0);
		if (a.getId()==20) return new JActNew( this.getNewFiltro(),4);
		if (a.getId()==30) return new JActNew( this.getNewFiltroOR(),4);
		return this.getSubmitFor(a);
	}

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    if (GetVision().equals("FILTROS"))
      zLista.AddColumnaLista("descr_filtro");
    else
    	zLista.AddColumnaLista("descr_campo");
  }

  public boolean hasNavigationBar() throws Exception {
		return false;
	}
  
  public GuiCamposGallery getCamposGallery(BizAction a) throws Exception {
  	GuiCamposGallery g = new GuiCamposGallery();
  	g.setRecords(this.customList.GetcDato().getAllCampos(a, true));
  	g.setObjCustomList(this.customList);
  	return g;
  }
  
  public GuiCampo getNewCampo() throws Exception {
  	GuiCampo e = (GuiCampo)this.createJoinWin();
   	e.getRecord().addFilter("orden_padre", "0");
   	e.GetcDato().setOrdenPadre("0");
   	e.setDropListener(this.customList);
  	
    return e;
  }

  public GuiCampo getNewFiltro() throws Exception {
  	GuiCampo e = (GuiCampo)this.createJoinWin();
//  	e.GetcDato().addFilter("orden_padre", zValor);
  	e.GetcDato().setObjFiltroParent(this.filtroParent.GetcDato());
  	e.GetcDato().setObjCustomList(customList.GetcDato());
   	e.getRecord().addFilter("orden_padre", "1");
    e.SetVision("NEWFILTROS");
   	e.GetcDato().setOrdenPadre(""+this.filtroParent.GetcDato().getOrden());
    e.GetcDato().setHasFiltro(true);
  	e.GetcDato().setVisible(false);
    e.setDropListener(this.customList);
  	return e;
  }
  public GuiCampo getNewFiltroOR() throws Exception {
  	GuiCampo e = (GuiCampo)this.createJoinWin();
  	String oper = this.filtroParent.GetcDato().getOperacion().equals(BizCampo.OPER_OR)?BizCampo.OPER_AND_INTERNO:BizCampo.OPER_OR;
  	e.GetcDato().setObjFiltroParent(this.filtroParent.GetcDato());
  	e.GetcDato().setObjCustomList(customList.GetcDato());
  	e.GetcDato().addFilter("operacion", oper);
   	e.GetcDato().setOrdenPadre(""+this.filtroParent.GetcDato().getOrden());
  	e.GetcDato().setOperacion(oper);
  	e.GetcDato().setVisible(false);
  	e.SetVision("NEWFILTROS");
  	e.setDropListener(this.customList);
  	
  	return e;
  }
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		// TODO Auto-generated method stub
		if (zBaseWin instanceof GuiCampoGallery ) {
			GuiCampoGallery gal = (GuiCampoGallery) zBaseWin;
			if (gal.GetcDato().getFolder()) {
				throw new Exception("Disculpe. No se pueden tirar carpetas.");
			}
			gal.GetcDato().execProcessAddCampo(((GuiCustomList)getParent()).GetcDato());
			return null;
		}

				
		return super.Drop(zBaseWin);
	}

	@Override
	public void setRecords(JRecords zDatos) throws Exception {
		if (zDatos==null)	return;
		if (((BizCampos)zDatos).getCustomList()!=null) {
			GuiCustomList w = new GuiCustomList();
			w.setRecord(((BizCampos)zDatos).getCustomList());
			setObjCustomList(w);
			GuiCampo wf = new GuiCampo();
			wf.setRecord(((BizCampos)zDatos).getFiltroParent());
			wf.SetVision(GetVision());
	  	setObjFiltroParent(wf);

		}
		super.setRecords(zDatos);
	}
	public JWin createWin() throws Exception {
		GuiCampo c = (GuiCampo) GetClassWin().newInstance();
		if (this.customList!=null)
			c.GetcDato().setObjCustomList(this.customList.GetcDato());
		if (this.filtroParent!=null)
			c.GetcDato().setObjFiltroParent(this.filtroParent.GetcDato());
		return c;
	}
	
	@Override
	public JWin createJoinWin() throws Exception {
		
		GuiCampo win = (GuiCampo)super.createJoinWin();
		if (customList!=null)
			win.GetcDato().setObjCustomList(customList.GetcDato());
		return win;
	}
	
}
