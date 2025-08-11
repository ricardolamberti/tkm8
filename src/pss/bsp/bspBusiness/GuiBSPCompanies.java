package pss.bsp.bspBusiness;

import pss.bsp.pais.GuiPaises;
import pss.common.regions.company.BizCompanies;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.license.typeLicense.GuiTypeLicenses;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;

public class GuiBSPCompanies extends GuiCompanies {

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiBSPCompany.class;
	}

	public GuiBSPCompanies() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void createActionMap() throws Exception {
		this.addAction(30, "Paises", null, 14, true, false, true, "Group");
		this.addAction(40, "Tipo Licencias", null, 972, true, false, true, "Group");
		this.addAction(50, "Copiar permisos", null, 972, true, true, true, "Group");
			super.createActionMap();
	}
	

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActNew(this.getNewBSPCompany(), 0);
		if (a.getId()==30) return new JActWins(new GuiPaises());
		if (a.getId()==40) return new JActWins(getTypeLicenses());
		if (a.getId()==50) return new JActSubmit(this) {
			public void submit() throws Exception {
				copiarPermisos();
			};
		};
		return this.getSubmitFor(a);
	}

	public GuiNewBSPCompany getNewBSPCompany() throws Exception {
		GuiNewBSPCompany newCompany=new GuiNewBSPCompany();
		this.joinData(newCompany);
		return newCompany;
	}
	public JWins getTypeLicenses() throws Exception {
		GuiTypeLicenses lic = new GuiTypeLicenses();
		lic.getRecords().addFilter("company", "DEFAULT");
		return lic;
	}
	@Override
	public JRecords<BizCompany> ObtenerDatos() throws Exception {
		return new BizCompanies();
	}
	
	public void copiarPermisos() throws Exception {
		GuiBSPCompany cmp = new GuiBSPCompany();
		cmp.GetcDato().addFilter("company", "DEFAULT");
		cmp.GetcDato().read();
		cmp.GetccDato().execCopiarPermisos();
	}
	
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		JGrupoColumnaLista g1=zLista.AddGrupoColumnaLista("Datos");
		JGrupoColumnaLista g2=zLista.AddGrupoColumnaLista("Licencia");
		JGrupoColumnaLista g4=zLista.AddGrupoColumnaLista("Bookings");
		JGrupoColumnaLista g3=zLista.AddGrupoColumnaLista("Tickets");
		JGrupoColumnaLista g5=zLista.AddGrupoColumnaLista("Evolucion Meses");
		zLista.AddColumnaLista("logo_inc").setGrupo(g1);
		zLista.AddColumnaLista("company").setGrupo(g1);
		zLista.AddColumnaLista("description").setGrupo(g1);
		zLista.AddColumnaLista("tkmversion").setGrupo(g1);
		zLista.AddColumnaLista("inactiva").setGrupo(g1);
		zLista.AddColumnaLista("email").setGrupo(g1);
		zLista.AddColumnaLista("pais_descr").setGrupo(g1);
		
		zLista.AddColumnaLista("tipo_licencia").setGrupo(g2);
		zLista.AddColumnaLista("licencias").setGrupo(g2);
		zLista.AddColumnaLista("licencias_calc").setGrupo(g2);
		
		zLista.AddColumnaLista("fecha_incio").setGrupo(g2);
		zLista.AddColumnaLista("fecha_hasta").setGrupo(g2);
		zLista.AddColumnaLista("renovaciones").setGrupo(g2);
		zLista.AddColumnaLista("cantidad_books").setGrupo(g4);
		zLista.AddColumnaLista("promedio_books").setGrupo(g4);
		zLista.AddColumnaLista("cantidad").setGrupo(g3);
		zLista.AddColumnaLista("promedio").setGrupo(g3);
		zLista.AddColumnaLista("ultimo").setGrupo(g3);

		zLista.AddColumnaLista("mes12").setGrupo(g5);
		zLista.AddColumnaLista("mes11").setGrupo(g5);
		zLista.AddColumnaLista("mes10").setGrupo(g5);
		zLista.AddColumnaLista("mes9").setGrupo(g5);
		zLista.AddColumnaLista("mes8").setGrupo(g5);
		zLista.AddColumnaLista("mes7").setGrupo(g5);
		zLista.AddColumnaLista("mes6").setGrupo(g5);
		zLista.AddColumnaLista("mes5").setGrupo(g5);
		zLista.AddColumnaLista("mes4").setGrupo(g5);
		zLista.AddColumnaLista("mes3").setGrupo(g5);
		zLista.AddColumnaLista("mes2").setGrupo(g5);
		zLista.AddColumnaLista("mes1").setGrupo(g5);

  }
	
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("inactiva")) {
			if (control.hasValue()) {
				String value = control.getValue();
				this.getRecords().addJoin( "bsp_company");
				this.getRecords().addFixedFilter("bsp_company.company = NOD_COMPANY.company");
				this.getRecords().addFilter("bsp_company","inactiva", value, "=");
				if (value.equals("N")) {
					this.getRecords().addFilter("bsp_company","tkmversion", "4", "<>"); //TEST, y modelo de paises
				}
				return;
			}
		}
		super.asignFilterByControl(control);
	}
	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addCheckThreeResponsive("Inactivas","inactiva").SetValorDefault("N");
		super.ConfigurarFiltros(zFiltros);
	}

}
