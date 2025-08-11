package  pss.common.security;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.lists.JWinList;

public class GuiRolesVinculados extends JWins {

	private GuiRol rolParent;
	
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiRolesVinculados() throws Exception {
  }
  
  public void setRolParent(GuiRol value) {
  	rolParent=value;
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiRolVinculado.class; }
  @Override
	public int GetNroIcono() throws Exception { return 43; }
  @Override
	public String GetTitle() throws Exception { return "Roles Vinculados"; }

  @Override
	public void createActionMap() throws Exception {
//		this.addActionNew(1, "Vincular Rol");
		this.addAction(10, "Agregar", null, GuiIcon.MULTI_MAS_ICON, true, true);
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWinsSelect(this.getRoles(), this.getRolParent(), true);
  	return null;
  }
  
  private JWins getRoles() throws Exception {
  	GuiRoles roles = new GuiRoles();
  	roles.getRecords().addFilter("company", this.getRecords().getFilterValue("company"));
//  	roles.getRecords().addFilter("tipo", BizRol.NORMAL);
  	return roles;
  }
  

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
     zLista.AddIcono("");
     zLista.AddColumnaLista("Grupo Vinculado", "descr_rolvinc");
  }

  private GuiRol getRolParent() throws Exception {
  	if (this.rolParent!=null) return this.rolParent;
  	GuiRol rol = new GuiRol();
  	rol.GetcDato().Read(this.getRecords().getFilterValue("company"), Integer.parseInt(this.getRecords().getFilterValue("rol")));
  	return (this.rolParent=rol);
  }


}
