package  pss.common.security;

import pss.common.customMenu.GuiMenu;
import pss.common.regions.company.GuiCompany;
import pss.common.security.type.GuiUsuarioTipos;
import pss.core.connectivity.messageManager.common.core.usuario.GuiMMUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.GuiActions;
import pss.core.win.security.GuiWinPropiedad;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiSegConfiguracion extends JWin {



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiSegConfiguracion() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizSegConfiguracion(); }
  @Override
	public int GetNroIcono()       throws Exception { return 909; }
  @Override
	public String GetTitle()       throws Exception { return "Configuración"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormSegConfiguracion.class; }
  @Override
	public String getKeyField()   throws Exception { return "1"; }
  @Override
	public String getDescripField()                  { return "desc"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
//    addActionDelete ( 3, "Eliminar" );
  	this.addAction(10, "Perfiles", null, 83, false, false);
  	this.addAction(20, "Grupos Funciones", null, 43, false, false);
  	this.addAction(30, "Usuarios", null, 45, false, false);
  	this.addAction(40, "Matriz Funciones", null, 44, false, false);
  	this.addAction(50, "Ingresos", null, 53, false, false);
  	this.addAction(60, "Seguridad Menú", null, 10035, true, true);
  	this.addAction(70, "Tipo Usuarios", null, 44, false, false);
  }


  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==10) return new JActWins(this.getRolesJerarquicos());
  	if ( a.getId()==20) return new JActWins(this.getRolesAplicacion());
  	if ( a.getId()==30) return new JActWins(this.getCompanyUsers());
  	if ( a.getId()==40) return new JActWins(this.getMatrizOperaciones());
  	if ( a.getId()==50) return new JActWins(this.getActividades());
  	if ( a.getId()==60) return new JActQuery(this.getSeguridadMenu());
  	if ( a.getId()==70) return new JActWins(this.getUsuarioTipos());
  	return super.getSubmitFor(a);
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==3) return BizUsuario.getUsr().isAnyAdminUser();
  	if (a.getId()==20) return this.GetcDato().getObjCompany().getObjBusiness().hasRolesAplicacion();
//  	if (a.getId()==60) return BizUsuario.getUsr().isAnyAdminUser();
  	return true;
  }
  

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizSegConfiguracion GetcDato() throws Exception {
    return (BizSegConfiguracion) this.getRecord();
  }

	public JWins getRolesJerarquicos() throws Exception {
		GuiRoles g=new GuiRoles();
		g.setRecords(this.GetcDato().getRolesJerarquicos());
		g.SetVision(BizRoles.JERARQUIA);
		return g;
	}
	
	public JWins getRolesAplicacion() throws Exception {
		GuiRoles g=new GuiRoles();
		g.setRecords(this.GetcDato().getRolesAplicacion());
		g.SetVision(BizRoles.NORMAL);
		return g;
	}

	public JWins getOperaciones() throws Exception {
		GuiOperaciones g=new GuiOperaciones();
		g.setRecords(this.GetcDato().getOperaciones());
		return g;
	}
	
	public JWins getMatrizOperaciones() throws Exception {
		GuiListOpers g=new GuiListOpers();
		g.getRecords().addFilter("company", this.GetcDato().getCompany());
		return g;
	}

	public JWins getActividades() throws Exception {
		GuiLogTraces g=new GuiLogTraces();
		g.getRecords().addFilter("company", this.GetcDato().getCompany());
		return g;
	}

	public JWins getUsuarioTipos() throws Exception {
		GuiUsuarioTipos g= new GuiUsuarioTipos();
		g.getRecords().addFilter("company", this.GetcDato().getCompany());
		return g;
	}

	public JWin getSeguridadMenu() throws Exception {
		GuiMenu g=new GuiMenu();
		g.GetcDato().Read(BizUsuario.getUsr().getCustomMenu());
		g.setActionMap(g.generateActionMenu(false, 0).GetSubAcciones());
		return g.getObjWinProperty();
		
//		if (BizUsuario.getUsr().getCustomMenu().equals("")) throw new Exception("No se ha definido un menú");
//		g.GetcDato().Read(BizUsuario.getUsr().getCustomMenu());
//		GuiActions a = new GuiActions();
//		a.setRecords(g.generateActionMenu(false, 0).GetSubAcciones());
//		return a;
	}

	public JWins getCompanyUsers() throws Exception {
		GuiUsuarios wins = BizUsuario.getUsr().getObjBusiness().getCompanyWinUsersClass();
		wins.getRecords().addFilter("company", this.GetcDato().getCompany());
		wins.setPreviewFlag(JWins.PREVIEW_MAX);
		return wins;
	}
	
	public GuiCompany getObjCompany() throws Exception {
		GuiCompany c = GuiCompany.VirtualCreateType(this.GetcDato().getObjCompany().getBusiness());
		c.GetcDato().Read(this.GetcDato().getCompany());
		return c;
	}

}
