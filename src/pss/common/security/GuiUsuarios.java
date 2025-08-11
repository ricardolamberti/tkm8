package  pss.common.security;

import pss.common.regions.nodes.GuiNodos;
import pss.common.security.type.GuiUsuarioTipos;
import pss.core.connectivity.messageManager.common.core.JMMWins;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiUsuarios extends JMMWins {

	public GuiUsuarios() throws Exception {
		getRecords().addOrderBy("descripcion");
	}

//	@Override
//	public JRecords<? extends JRecord> ObtenerDatos() throws Exception {
//		return new BizUsuarios();
//	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiUsuario.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 45;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Usuarios";
	}

	@Override
	public void createActionMap() throws Exception {
		this.addActionNew( 1, "Nuevo Usuario" );
	}

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActNew(this.getNewUser(), 0);
		return this.getSubmitFor(a);
	}

	public JWin getNewUser() throws Exception {
		JWin user=this.createNewJoinWin();
		if (BizUsuario.IsAdminUser()) return user;
		user.getRecord().setFilterValue("company", BizUsuario.getUsr().getCompany(),null);
		return user;
	}
	
	  

	
  public void ConfigurarFiltros(JFormFiltro filter) throws Exception {
  	filter.addEditResponsive("company", "company");
  	filter.addComboResponsive("Sucursal", "nodo", new JControlCombo() {
  		public JWins getRecords(Object source, boolean one) throws Exception {
  			return getNodos(source, one);
  		}
  	}, true);
  	filter.addEditResponsive("Usuario", "usuario").setFilterNeverHide(true).setOperator("ilike");
  	filter.addEditResponsive("Descripción", "descripcion").setFilterNeverHide(true).setOperator("ilike");
  	filter.addComboResponsive("Tipo usuario", "tipo_usuario", new GuiUsuarioTipos(), true);
  	filter.addCheckResponsive("Ver Inactivos", "activo").SetValorDefault(false);
  }
  
  protected JWins getNodos(Object source, boolean one) throws Exception {
  	JFormControl c = (JFormControl) source;
  	GuiNodos g = new GuiNodos();
  	g.getRecords().addFilter("company", c.findControl("company").getValue());
  	return g;
  }


	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		if (zLista.isForExport()) {
			zLista.AddIcono("");
			zLista.AddColumnaLista("usuario").setActionOnClick(this.hasDropListener()?4:1);
			zLista.AddColumnaLista("descripcion");
	//		zLista.AddColumnaLista("descrip_nodo");
	//		zLista.AddColumnaLista("activo");
			zLista.AddColumnaLista("fecha_ultimo_ingreso");
			zLista.AddColumnaLista("origen_login");
			getRecords().clearOrderBy();
			getRecords().addOrderBy("usuario");
		} else {
			zLista.addColumnWinAction("Flat", 1);
//			zLista.setWithHeader(false);
		}
	}
	
	@Override
	public boolean needAutocomplete(JWinList zLista) throws Exception {
		return false;
	}
	
	@Override
	public boolean isForceHideActions() {
		return true;
	}
	
	/*
	 * public void ConfigurarReport(JReport zReport) throws Exception { this.CargarTabla(zReport); GuiUsuarioRoles oUsuRoles = new GuiUsuarioRoles(); oUsuRoles.ReadAllDatos(); oUsuRoles.CargarTabla(zReport); GuiRoles oRoles = new GuiRoles(); oRoles.ReadAllDatos(); oRoles.CargarTabla(zReport); }
	 */
	@Override
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getName().equals("activo")) {
			if (control.getValue().equals("N")) {
				this.getRecords().addFilter("activo", true).setDynamic(true);
			}
			return;
		}
		super.asignFilterByControl(control);
	}

}
