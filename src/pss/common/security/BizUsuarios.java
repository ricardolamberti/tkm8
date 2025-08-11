package  pss.common.security;

import pss.core.services.records.JRecords;

public class BizUsuarios extends JRecords<BizUsuario> {

	/**
	 * Constructor
	 */
	public BizUsuarios() throws Exception {
	}

	@Override
	public Class getBasedClass() {
		return BizUsuario.class;
	}

	@Override
	public String GetTable() {
		return "seg_usuario";
	}

//	@Override
//	public boolean readAll() throws Exception {
////		if (!BizUsuario.IsAdminUser()) this.setFilterValue("company", BizUsuario.getUsr().getCompany());
//		// BizCompInstalados.ifInstallControlJerarquias();
//		// cambiar por la linea de arriba, no lo puede hacer bien porque estaba checkouteado
//		if (JPss.class.getResource("core/Seguridad/controlJerarquias")==null) return super.readAll();
//
//		this.clearFilters();
//		this.endStatic();
//		if (BizUsuario.IsAdminUser()) {
//			super.readAll();
//			this.toStatic();
//			return true;
//		}
//
//		BizRoles oRoles=new BizRoles();
//		oRoles.SetVision(BizRoles.JERARQUIA);
//		oRoles.readAll();
//		oRoles.firstRecord();
//		while (oRoles.nextRecord()) {
//			BizRol oRol=oRoles.getRecord();
//			JRecords<BizUsuario> oUsuarios=new JRecords<BizUsuario>(BizUsuario.class);
//			oUsuarios.addJoin("seg_usuario_rol");
//			oUsuarios.addFixedFilter(" where seg_usuario_rol.rol = '"+oRol.pRol.getValue()+"'");
//			oUsuarios.addFixedFilter(" and   seg_usuario.usuario = seg_usuario_rol.usuario");
//			// oUsuarios.SetFiltroFijo(" and seg_usuario.usuario <> 'ADMIN'");
//			oUsuarios.addFixedFilter(" and   seg_usuario.company ='"+BizUsuario.getUsr().getCompany()+"'");
//			oUsuarios.addFixedFilter(" order by seg_usuario.usuario");
//			oUsuarios.readAll();
//			this.appendRecords(oUsuarios);
//		}
//		/*
//		 * // Agrego los usuarios públicos (sin rol asociado) JBDs oUsuarios = new JBDs(BizUsuario.class); //oUsuarios.SetJoin("seg_usuario_rol"); oUsuarios.SetFiltroFijo(" where not exists (select * from seg_usuario_rol where seg_usuario.usuario = seg_usuario_rol.usuario) "); oUsuarios.SetFiltros("usuario","ADMIN","<>"); oUsuarios.ReadAll(); this.AppendDatos(oUsuarios);
//		 * 
//		 * 
//		 */
//		this.setStatic(true);
//
//		String sCriterio="";
//		sCriterio="descripcion";
//		JIterator<ROrderBy> oCampos=this.getOrderBy().getIterator();
//		if (oCampos.hasMoreElements()) {
//			sCriterio="";
//			while (oCampos.hasMoreElements()) {
//				if (!sCriterio.equals("")) sCriterio+=";";
//				sCriterio+=(oCampos.nextElement()).GetCampo();
//			}
//		}
//		this.Ordenar(sCriterio);
//
//		return true;
//	}
}
