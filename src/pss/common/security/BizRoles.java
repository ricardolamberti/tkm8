package  pss.common.security;

import pss.JPss;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class BizRoles extends JRecords<BizRol> {

	// boolean bIgualNivel = false;
	// public void SetIgualNivel( boolean zValue ) { bIgualNivel = zValue ; }

	public static final String JERARQUIA="J";
	public static final String NORMAL="N";

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRoles() throws Exception {
	}

	@Override
	public Class<BizRol> getBasedClass() {
		return BizRol.class;
	}

	@Override
	public String GetTable() {
		return "seg_rol";
	}

	@Override
	public boolean readAll() throws Exception {
		// BizCompInstalados.ifInstallControlJerarquias();
		// cambiar por la linea de arriba, no lo puede hacer bien porque estaba checkouteado
		if (JPss.class.getResource("core/Seguridad/controlJerarquias")==null) return super.readAll();

		this.endStatic();
		// boolean bIgualNivel = this.GetFiltroValue("igual_nivel").equals("S") ? true : false;
		String sCompany=this.getFilterValue("company");
		String sUsuario=this.getFilterValue("usuario");
		if (sUsuario.equals("")) sUsuario=BizUsuario.getUsr().GetUsuario();

		if (BizUsuario.IsAdminUser()) {
			this.clearFilters();
			if (isJerarquia()) this.addFilter("tipo", JERARQUIA);
			if (isNormal()) this.addFilter("tipo", NORMAL);
			super.readAll();
			this.toStatic();
			return true;
		}

		JRecords<BizUsuarioRol> oUsuRoles=new JRecords<BizUsuarioRol>(BizUsuarioRol.class);
		oUsuRoles.addFilter("company", sCompany);
		oUsuRoles.addFilter("usuario", sUsuario);
		oUsuRoles.readAll();
		this.setStatic(true);
		JIterator<BizUsuarioRol> iter = oUsuRoles.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizUsuarioRol usuRol=iter.nextElement();
			// if ( bIgualNivel ) AgregarRol(oUsuRol.pRol.GetValor());
			this.loadRolesVinculados(usuRol.getCompany(), usuRol.getRol());
		}
		this.firstRecord();
		return true;
	}

	public void loadRolesVinculados(String company, int rol) throws Exception {
		JRecords<BizRolVinculado> oRolesVinc=new JRecords<BizRolVinculado>(BizRolVinculado.class);
		oRolesVinc.addFilter("company", company);
		oRolesVinc.addFilter("rol", rol);
		oRolesVinc.readAll();
		JIterator<BizRolVinculado> iter = oRolesVinc.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizRolVinculado vinc=iter.nextElement();
			AgregarRol(vinc);
			// if ( ! oRol.pRol.GetValor().equals(zRol) )
			this.loadRolesVinculados(vinc.getCompany(), vinc.getRolVinculado());
		}

	}

	public JRecord AddItem(BizRol zRol) throws Exception {
		this.firstRecord();
		while (this.nextRecord()) {
			BizRol oRol=this.getRecord();
			if (oRol.pRol.getValue()==zRol.pRol.getValue()) 
				return zRol;
		}
		return super.addItem(zRol);
	}

	private void AgregarRol(BizRolVinculado vinc) throws Exception {
		BizRol oRol=vinc.getObjRolVinc();
		if (isJerarquia()&&!oRol.ifRolJerarquico()) return;
		if (isNormal()&&!oRol.ifRolNormal()) return;
		AddItem(oRol);
	}

	private boolean isJerarquia() throws Exception {
		return GetVision().equals(BizRoles.JERARQUIA);
	}

	private boolean isNormal() throws Exception {
		return GetVision().equals(BizRoles.NORMAL);
	}

}
