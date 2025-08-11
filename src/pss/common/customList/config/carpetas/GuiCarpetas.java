package pss.common.customList.config.carpetas;

import java.awt.event.KeyEvent;

import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiCarpetas extends JWins {

	public static String VISION_FOLDER = "1";
	public static String VISION_NAV = "2";


  /**
   * Constructor de la Clase
   */
  public GuiCarpetas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10064; } 
  public String  GetTitle()    throws Exception  { return "Informes"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiCarpeta.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nueva carpeta" );
    addAction( 20, "Nueva DM" , KeyEvent.VK_INSERT, null, 10077, true, true);
    addAction( 30, "Nuevo Informe" , KeyEvent.VK_INSERT, null, 10077, true, true);
// 	  addAction(10, "Subir", null, 10059, false, false);
  }


  @Override
  public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==20) return new JActNew(this.createListado(), 4);
		if (a.getId()==30) return new JActNew(this.createReporte(), 4);
//		if (a.getId()==10) return new JActSubmit(this) {
//  		@Override
//  		public void submit() throws Exception {
//  			removeFolderToNav();
//  			super.submit();
//  		}
//  		
//  	
//  	};

  	return super.getSubmit(a);
  }

  
  public GuiCarpetas that() {
  	return this;
  }
//	public void removeFolderToNav() throws Exception {
//		int pos =GuiCarpeta.getNav().lastIndexOf(";");
//		if (pos!=-1)
//			GuiCarpeta.setNav(GuiCarpeta.getNav().substring(0,pos));
//	}
	
	@Override
	public void addOrderByFromUI(String columnOrden, String dirOrden) throws Exception {
		if (columnOrden.equals("descripcion_view")) 
			super.addOrderByFromUI("descripcion", dirOrden);
		else
			super.addOrderByFromUI(columnOrden, dirOrden);
	}

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
		if (BizUsuario.getUsr().isAdminUser())
			zLista.AddColumnaLista("company");
    zLista.AddColumnaLista("descripcion_view");
    zLista.AddColumnaLista("last_update");
 //   zLista.AddColumnaLista("orden");
		if (BizUsuario.getUsr().isAdminUser())
     zLista.AddColumnaLista("descripcion_protect");

 		

  }
  
  public boolean hasDocOwner() throws Exception {
  	JRecords<BizCarpeta> a = new JRecords<BizCarpeta> (BizCarpeta.class);
  	a.addFilter("company", BizUsuario.getUsr().getCompany());
  	a.addFilter("carp_owner", BizUsuario.getUsr().GetUsuario());
  	return a.exists();
  }
  
  public GuiCustomList createListado() throws Exception {
		GuiCarpeta l = BizUsuario.getUsr().getObjBusiness().getCarpeta();
  	joinData(l);
  	l.GetcDato().setCompany(getFilterValue("company")==null?BizUsuario.getUsr().getCompany():getFilterValue("company"));
  	if (getCarpetaActual()!=0)
  		l.GetcDato().setPadre(getCarpetaActual());
  	l.GetcDato().setInvisible(false);
  	return l.getNewCustomList();
  }
  
  public GuiCustomList createReporte() throws Exception {
		GuiCarpeta l = BizUsuario.getUsr().getObjBusiness().getCarpeta();
  	joinData(l);
  	l.GetcDato().setCompany(getFilterValue("company")==null?BizUsuario.getUsr().getCompany():getFilterValue("company"));
  	if (getCarpetaActual()!=0)
  		l.GetcDato().setPadre(getCarpetaActual());
  	l.GetcDato().setInvisible(false);
  	return l.getNewReporte();
  }
  
   @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
 		if (BizUsuario.getUsr().isAdminUser())
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());
		if (GetVision().equals(VISION_FOLDER)) {
			JFormControl c = zFiltros.addEditResponsive("Descripcion", JBaseForm.CHAR, "descripcion", "ilike");
			c.setRefreshForm(true);
			return;
		}
		JFormControl	c  ;
	
//		c= zFiltros.NuevoBoton("Subir",10,false);
//  	c.setIdControl("nav");
//  	c.setPreferredWidth(40);
  	
    c=zFiltros.addComboResponsive("Carpeta","padre",new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getCarpetas();
    	}
    },true);
//    c.clear();
//    if (getCarpetaActual()!=0) {
//    	c.SetValorDefault(""+getCarpetaActual());
//    } else {
//    	c.clearDefault();
//    }
    c.setRefreshForm(true);
  	c = zFiltros.addEditResponsive("Descripcion",JBaseForm.CHAR, "descripcion", "ilike");
  	c.setRefreshForm(true);
  	if (BizUsuario.getUsr().IsAdminCompanyUser()) {
  		c=zFiltros.addCheckThreeResponsive("Invisible","invisible");
    	c.SetValorDefault("N");
//   	c = zFiltros.addCheckResponsive("Invisible","invisible");
  		
  	}
//   	c = zFiltros.NuevoCheck("favoritos","descripcion_view");
//    c.setRefreshForm(true);
//   	c.SetValorDefault(/*hasFav()?"S":*/"N");
//   	c.setRefreshForm(true);
  	JFormControl c1 = zFiltros.addCheckResponsive("","only_owner","Mis DMs","Todos");
  	c1.SetValorDefault( hasDocOwner()?"S":"N");
  	c1.setPreferredWidth(100);
   	c1.setRefreshForm(true);

    
  }
  
  @Override
  protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("padre")&&control.hasValue()){
	  		getRecords().clearFilter("padre");
				getRecords().addFilter("padre",control.getValue());
				return;
		}
		else 
//		if (control.getIdControl().equals("nav")){
//			if (getCarpetaActual()!=0) {
//	  		getRecords().clearFilter("padre");
//				getRecords().addFilter("padre",getCarpetaActual());
//				return;
//			} else {
//	  		getRecords().clearFilter("padre");//control.getValue()
//				getRecords().addFilter("padre","null");
//				return;
//			}
//		}
		if (control.getIdControl().equals("invisible")){
			if (control.hasValue()) {
				if (control.getValue().equals("N")) {
					getRecords().addFixedFilter("(invisible is null or invisible='N')");
					return;
				}
			}
		}

  	if (control.getIdControl().equals("only_owner") ) {
  		if ( control.getValue().equals("S")) {
  			getRecords().addFixedFilter("where  carp_owner='"+BizUsuario.getUsr().GetUsuario()+"' ");
  			  	
	 		} 
  		return;
  	}
 
  	super.asignFilterByControl(control);
  }
  
 
   
   public long getCarpetaActual() throws Exception {
// 		if (!BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getNav().equals("")) {
//			String str = BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getNav();
//			String folder = str.substring(str.lastIndexOf(";")+1);
//			f.getRecords().addFilter("padre", Long.parseLong(folder)).setDynamic(true);
//		} else
//			f.getRecords().addFilter("padre","null").setDynamic(true);

  	 int pos =GuiCarpeta.getNav().lastIndexOf(";");
 			if (pos==-1) return 0;
 			return Long.parseLong(GuiCarpeta.getNav().substring(pos+1));
   }
   
  
   public GuiCarpetas getCarpetas() throws Exception {
 		GuiCarpetas carp = BizUsuario.getUsr().getObjBusiness().getCarpetas();
 		carp.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
 		carp.getRecords().addFilter("customlist","null");
 		carp.getRecords().addOrderBy("last_update","DESC");
 		carp.getRecords().addOrderBy("descripcion");
 		return carp;
 	}

}
