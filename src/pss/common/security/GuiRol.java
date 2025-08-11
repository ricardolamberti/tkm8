package  pss.common.security;

import pss.common.customList.config.relation.JRelations;
import pss.core.data.interfaces.structure.RJoins;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiRol extends JWin {

  public GuiRol() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizRol(); }
  @Override
	public String GetTitle()       throws Exception {
  	return "Perfil";
  }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormRol.class; }
  @Override
	public String getKeyField()   throws Exception { return "rol"; }
  @Override
	public int GetNroIcono() throws Exception {
    return GetcDato().ifRolJerarquico() ? 83 : 43 ;
  }
  @Override
	public String getDescripField() {
    if (GetVision().equals("rol")) return "rol";
    if (GetVision().equals("descripcion")) return "descripcion";
    return "descripcion";
  }

  @Override
	public void createActionMap() throws Exception {
    addActionQuery(1, "Consultar" );
    addActionUpdate(2, "Modificar" );
    addActionDelete (3, "Eliminar"  );

//    addAction( 15, "Vincular Rol", null, 43, true, true );
    addAction( 10, "Grupos Vinculados", null, 1, false, false);
    addAction( 11, "Funciones Vinculadas", null, 1, false, false);
    addAction( 20, "Usuarios", null, 45, false, false);

  }
  
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(getRolesVinculados());
  	if (a.getId()==11) return new JActWins(getOperaciones());
  	if (a.getId()==20) return new JActWins(getUsuarioRoles());
//  	if (a.getId()==15) return new JActNew(this.getNuevoRol(), 0);
  	return null;
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
    if (a.getId()==10) return GetcDato().ifRolJerarquico();
//    if (a.getId()==11) return !GetcDato().ifRolJerarquico();
    return super.OkAction(a);
  }

  public BizRol GetcDato() throws Exception {
    return (BizRol) this.getRecord();
  }

//  public JWin getNuevoRol() throws Exception {
//  	GuiRolVinculado oRolVinculado = new GuiRolVinculado();
// 		oRolVinculado.getRecord().addFilter("rol", GetcDato().pRol.getValue());
// 		return oRolVinculado;
//  }

  public JWins getUsuarioRoles() throws Exception {
    GuiUsuarioRoles g = new GuiUsuarioRoles();
    g.setParent(this);
		RJoins join = g.getRecords().addJoin(JRelations.JOIN, "seg_usuario", "");
		join.addCondicion("seg_usuario.usuario=seg_usuario_rol.usuario");
		join.addCondicion("seg_usuario.activo='S'");
    g.getRecords().addFilter("company", GetcDato().pCompany.getValue());
    g.getRecords().addFilter("rol", GetcDato().pRol.getValue());
    g.getRecords().addOrderBy("usuario");
    g.SetVision(BizUsuarioRol.VISION_USER);
    return g;
  }

  public JWins getRolesVinculados() throws Exception {
    GuiRolesVinculados g = new GuiRolesVinculados();
    g.setRolParent(this);
    g.getRecords().addFilter("company", GetcDato().pCompany.getValue());
    g.getRecords().addFilter("rol", GetcDato().pRol.getValue());
    g.getRecords().addOrderBy("rol_vinculado");
    return g;
  }

  public JWins getOperaciones() throws Exception {
    GuiOperacionRoles g = new GuiOperacionRoles();
    g.setRolParent(this);
    g.getRecords().addFilter("company", GetcDato().pCompany.getValue());
    g.getRecords().addFilter("rol", GetcDato().pRol.getValue());
    return g;
  }

  public JAct Drop(JBaseWin baseWin) throws Exception {
  	if (baseWin instanceof GuiRol) {
  		this.createRolVinculado(JRecords.createRecords(baseWin.GetBaseDato()));
  	}
  	if (baseWin instanceof GuiRoles) {
  		this.createRolVinculado(JRecords.createRecords(baseWin.GetBaseDato()));
  	}
  	if (baseWin instanceof GuiOperacion) {
  		this.createOperacionRol(JRecords.createRecords(baseWin.GetBaseDato()));
  	}
  	if (baseWin instanceof GuiOperaciones) {
  		this.createOperacionRol(JRecords.createRecords(baseWin.GetBaseDato()));
  	}
  	return null;
  }
  
  private void createRolVinculado(JRecords<?> vincs) throws Exception {
  	JIterator<?> iter = vincs.getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizRol vinc = (BizRol)iter.nextElement(); 
	  	BizRolVinculado v = new BizRolVinculado();
	  	v.setCompany(this.GetcDato().getCompany());
	  	v.setRol(this.GetcDato().getRol());
	  	v.setRolVinculado(vinc.getRol());
	  	v.execProcessInsert();
  	}
  }

  private void createOperacionRol(JRecords<?> opers) throws Exception {
  	JIterator<?> iter = opers.getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizOperacion ope = (BizOperacion)iter.nextElement(); 
  		BizOperacionRol v = new BizOperacionRol();
	  	v.setCompany(this.GetcDato().getCompany());
	  	v.setRol(this.GetcDato().getRol());
	  	v.SetOperacion(ope.GetOperacion());
	  	v.execProcessInsert();
  	}
  }

}



