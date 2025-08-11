package  pss.common.security;

import pss.common.regions.company.BizCompany;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.lists.JWinList;

public class GuiOperacionRoles extends JWins {

	private GuiRol rolParent;
	private GuiOperacion operParent;
	
	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public GuiOperacionRoles() throws Exception {
	}

  public void setRolParent(GuiRol value) {
  	rolParent=value;
  }

  public void setOperParent(GuiOperacion value) {
  	operParent=value;
  }
  
  @Override
	public Class<? extends JWin> GetClassWin() { return GuiOperacionRol.class; }
  @Override
	public int GetNroIcono() throws Exception { return 44; }
  @Override
	public String GetTitle() throws Exception { return "Operaciones"; }
  @Override
	public boolean canExpandTree() throws Exception { return false; }

	@Override
	public void createActionMap() throws Exception {
		this.addAction(10, "Agregar", null, GuiIcon.MULTI_MAS_ICON, true, true).setModal(true);
	}
	
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) {
  		if (GetVision().equals(BizOperacionRol.VISION_ROL)) 
  			return new JActWinsSelect(this.getRoles(), this.getOperParent(), true);
  		else
  			return new JActWinsSelect(this.getOperaciones(), this.getRolParent(), true);
  	}
  	return null;
  }

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		if (GetVision().equals(BizOperacionRol.VISION_ROL)) {
      zLista.AddColumnaLista("descr_rol");
		} else {
      zLista.AddColumnaLista("descr_oper");
		}
		zLista.AddColumnaLista("clave_supervisor");
	}
	
	private JWins getOperaciones() throws Exception {
		GuiOperaciones r = new GuiOperaciones();
		r.getRecords().addFilter("company", this.getRecords().getFilterValue("company"));
		r.getRecords().addOrderBy("descripcion", "desc");
		return r;
	}

	private JWins getRoles() throws Exception {
		BizCompany company = new BizCompany();
		company.Read(this.getRecords().getFilterValue("company"));
		GuiRoles r = new GuiRoles();
		r.getRecords().addFilter("company", company.getCompany());
		r.getRecords().addFilter("tipo", company.getObjBusiness().hasRolesAplicacion() ? BizRol.NORMAL: BizRol.JERARQUICO);
		r.getRecords().addOrderBy("descripcion", "desc");
		return r;
	}
	
  private GuiRol getRolParent() throws Exception {
  	if (this.rolParent!=null) return this.rolParent;
  	GuiRol rol = new GuiRol();
  	rol.GetcDato().Read(this.getRecords().getFilterValue("company"), Integer.parseInt(this.getRecords().getFilterValue("rol")));
  	return (this.rolParent=rol);
  }
  
  private GuiOperacion getOperParent() throws Exception {
  	if (this.operParent!=null) return this.operParent;
  	GuiOperacion ope = new GuiOperacion();
  	ope.GetcDato().Read(this.getRecords().getFilterValue("company"), this.getRecords().getFilterValue("operacion"));
  	return (this.operParent=ope);
  }
  
}
