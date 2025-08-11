package  pss.common.personalData;

import pss.common.diccionario.GuiDiccionarios;
import pss.common.personalData.newPerson.BizNewPerson;
import pss.common.personalData.newPerson.GuiNewPerson;
import pss.common.regions.cities.GuiCities;
import pss.common.regions.divitions.BizCiudadCP;
import pss.common.regions.divitions.BizPais;
import pss.common.regions.divitions.GuiCiudadCPes;
import pss.common.regions.divitions.GuiCiudades;
import pss.common.regions.divitions.GuiPaises;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.regions.divitions.GuiProvincias;
import pss.common.security.BizUsuario;
import pss.core.connectivity.messageManager.common.core.JMMBaseForm;
import pss.core.tools.JTools;
import pss.core.win.JControlWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public abstract class FormBaseDomicilio extends JMMBaseForm {

	public FormBaseDomicilio() {
	}

//	protected int getPanelDomicilioTop();
//	protected int getPanelTelefonoTop();

	public boolean map = true;
	
	/**
	 * @return the map
	 */
	public boolean isMap() {
		return map;
	}
	/**
	 * @param map the map to set
	 */
	public void setMap(boolean map) {
		this.map = map;
	}

//	public int panelOffset=3;
//	public int label_left=10;
//	// domicilio
//	protected JPssPanel panelDomicilio=null;
//	JPssPanel panelTelPerJuridica=null;
//	JPssPanel panelTelPerFisica=null;
//	JTabbedPane tabPanel=null;
//	protected JPssEdit Calle=new JPssEdit();
//	protected JPssEdit Numero=new JPssEdit();
//	protected JPssEdit Piso=new JPssEdit();
//	protected JPssEdit Departamento=new JPssEdit();
//	protected JComboBox pais=new JComboBox();
//	protected JPssLabelFormLov city=new JPssLabelFormLov();
//	protected JComboBox provincia=new JComboBox();
//	protected JPssLabel jLabel7=new JPssLabel();
//	protected JPssLabel jLabel8=new JPssLabel();
//	protected JPssLabel jLabel9=new JPssLabel();
//	protected JPssLabel lCiudad=new JPssLabel();
//	protected JPssLabel lPais=new JPssLabel();
//	protected JPssLabel lCity=new JPssLabel();
//	protected JPssLabel lProvincia=new JPssLabel();
//	protected JPssLabel jLabel13=new JPssLabel();
//	protected JPssLabel jLabel14=new JPssLabel();
//	protected JPssLabel jLabel15=new JPssLabel();
//	protected JPssLabel lOtros=new JPssLabel();
//	
//	protected JPssLabel lLocalidad=new JPssLabel();
//	protected JPssEdit CP=new JPssEdit();
//	protected JPssEdit jOtros=new JPssEdit();
//	protected JPssLabelWinLov jCiudadId=new JPssLabelWinLov();
//	protected JPssEdit jCiudad=new JPssEdit();
//	protected JPssEdit jLocalidad=new JPssEdit();
//	
//	protected TitledBorder borderDomicilio;
////	JPssGoogleMap gmap=new JPssGoogleMap();
//
//	JPssLabel lTelComercial=new JPssLabel();
//	JPssLabel lTelComercial2=new JPssLabel();
//	JPssLabel lTelParticular=new JPssLabel();
//	JPssLabel lTelCelular=new JPssLabel();
//	JPssLabel lTelFax=new JPssLabel();
//	JPssEdit jTelComercial=new JPssEdit();
//	JPssEdit jTelFax=new JPssEdit();
//	JPssEdit jTelParticular=new JPssEdit();
//	JPssEdit jTelCelular=new JPssEdit();
	public void InicializarPanelDomicilio() throws Exception {
		InicializarPanelDomicilio(true);
	}
	public void setValorDefault(JFormControl c) throws Exception {
		
	}
	public void InicializarPanelDomicilio(boolean hasReq) throws Exception {
		// usar el inicializar2
		JFormControl c;
		JFormPanelResponsive panel = this.getMainPanel().AddItemFieldset("Domicilio");
		// Domicilio
		panel.AddItemEdit("Calle", CHAR, OPT, "calle");
		panel.AddItemEdit("Numero", CHAR, OPT, "numero");
		panel.AddItemEdit("Piso", CHAR, OPT, "piso");
		panel.AddItemEdit("Departamento", CHAR, OPT, "depto");
		c=panel.AddItemEdit("CP", CHAR, OPT, "cod_postal");
		c.setRefreshForm(true);
		panel.AddItemEdit("Otros", CHAR, OPT, "otros");

		/*c=AddItem(pais, CHAR, (hasReq?REQ:OPT), "pais", new JControlWin() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return new GuiPaisesLista();
			}
		});*/
		c=panel.AddItemCombo("País", CHAR, (hasReq?REQ:OPT), "pais", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return new GuiPaisesLista();
			}
		});
		c.SetValorDefault(BizUsuario.getUsr().getCountry());
		c.setRefreshForm(true);	

		c=panel.AddItemCombo("Provincia", CHAR, (hasReq?REQ:OPT), "provincia", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return ObtenerProvincia();
			}
		});
		c.SetValorDefault("AA");
		c.setRefreshForm(true);
//		c.SetValorDefault(BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioLegal().GetProvincia());
		c=panel.AddItemCombo("Municipio", CHAR, (hasReq?REQ:OPT), "ciudad_id", new JControlWin() {
			public JWins getRecords(boolean one) throws Exception {
				return getCiudades(one);
			}
		});
		c.setRefreshForm(true);
//		setValorDefault(c);

		c=panel.AddItemEdit("Localidad", CHAR, OPT, "localidad");
//		setValorDefault(c);
	}	
	
	private JFormPanelResponsive mainPanel;
	public JFormPanelResponsive getMainPanel() throws Exception {
		if (this.mainPanel!=null) return mainPanel;
		JFormPanelResponsive panel=this.addPanelHalf();
//		panel.setResponsiveClass(panel.getResponsiveHalf(12));
		panel.setId("mail-panel");
//		this.mainPanel.setMaxWidth(800);
//		this.mainPanel.setWidth(600);
		return (this.mainPanel=panel);
	}
	
	public JFormPanelResponsive createPanelDomicilio() throws Exception {
		JFormControlResponsive c;
		JFormPanelResponsive panel = this.getMainPanel().AddItemFieldset("Domicilio");
		panel.AddItemEdit("Calle", CHAR, OPT, "calle", 4);
		panel.AddItemEdit("Nro", CHAR, OPT, "numero", 1);
		panel.AddItemEdit("Piso", CHAR, OPT, "piso", 1);
		panel.AddItemEdit("Dpto", CHAR, OPT, "depto", 1);
		panel.AddItemEdit("CP", CHAR, OPT, "cod_postal", 1);
		c=panel.AddItemCombo("Ciudad", CHAR, OPT, "city", new JControlWin() {
			public JWins getRecords(boolean one) throws Exception {
				return getCities(one);
			}
		});
		c.setSizeColumns(4);
		c=panel.AddItemCombo("País", CHAR, REQ, "pais", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return GuiPaises.getPaises();
			}
		});
		c.SetValorDefault(this.findPaisDefault());
		c.setRefreshForm(true);
		c.setSizeColumns(3);
		c=panel.AddItemCombo("Provincia", CHAR, OPT, "provincia", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return ObtenerProvincia();
			}
		});
		c.setSizeColumns(3);
//		if (BizUsuario.getUsr().hasNodo()) c.SetValorDefault(BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioLegal().getProvincia());
		panel.AddItemEdit("Municipio", CHAR, OPT, "ciudad", 3);
		panel.AddItemEdit("Locallidad", CHAR, OPT, "localidad", 3);
		panel.AddItemArea("Comentarios", CHAR, OPT, "Comentarios");
		return panel;
	}
	public void InicializarPanelDomicilioSimple(JFormPanelResponsive panel) throws Exception {
		JFormControlResponsive c;
		panel.AddItemEdit(null, CHAR, OPT, "persona").setHide(true);
		panel.AddItemEdit(null, CHAR, OPT, "secuencia").setHide(true);
		panel.AddItemEdit("Calle", CHAR, OPT, "calle", 6);
		panel.AddItemEdit("Nro", CHAR, OPT, "numero", 4);
		panel.AddItemEdit("Piso", CHAR, OPT, "piso", 1);
		panel.AddItemEdit("Dpto", CHAR, OPT, "depto", 1);
	//	panel.AddItemEdit("CP", CHAR, OPT, "cod_postal", 1);
//		c=panel.AddItemCombo("Ciudad", CHAR, OPT, "city", new JControlWin() {
//			public JWins getRecords(boolean one) throws Exception {
//				return getCities(one);
//			}
//		});
//		c.setSizeColumns(4);
		c=panel.AddItemEdit("País", CHAR, OPT, "pais");
		c.SetValorDefault(this.findPaisDefault());
		c.setHide(true);
		c=panel.AddItemDropDownCombo("Provincia", CHAR, OPT, "localidad_id", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return ObtenerProvinciaSimple();
			}
		});
		c.setSizeColumns(6);
		c=panel.AddItemDropDownCombo("Ciudad", CHAR, OPT, "ciudad_id", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return ObtenerCiudadSimple();
			}
		});
		c.setSizeColumns(6);
		
	}
	@Override
	public void OnPostShow() throws Exception {
		this.verifyLabels(false);
	}
	
	private void verifyLabels(boolean clear) throws Exception {
//		BizPais p = BizPais.findPais(this.valueControl("pais"));
//		if (p==null) return;
//		if (this.hasControl("provincia")) 
//			this.findControlResponsive("provincia").setLabel(p.GetDivision());
//		if (this.hasControl("ciudad")) 
//			this.findControlResponsive("ciudad").setLabel(p.GetCiudad());
//		if (this.hasControl("localidad")) 
//			this.findControlResponsive("localidad").setLabel(p.GetLocalidad());
//		if (clear) {
//			this.clearControl("city");
//			this.clearControl("provincia");
//			this.clearControl("ciudad");
//			this.clearControl("localidad");
//		}		
	}
	
	
	public void InicializarPanelTelPerJuridica() throws Exception {
		this.InicializarPanelTelPerJuridica(false, false);
	}
	public void InicializarPanelTelPerJuridica(boolean req_com,boolean req_cel) throws Exception {
		JFormPanelResponsive panel = this.getMainPanel().AddItemFieldset("Teléfono");
		panel.setLabelLeft(6);
		panel.AddItemEdit("Comercial", CHAR, req_com?REQ:OPT, "tel_comercial", 3);
		panel.AddItemEdit("Fax/Cel", CHAR, req_cel?REQ:OPT, "tel_fax", 3);
		panel.AddItemEdit("Alt.", CHAR, req_cel?REQ:OPT, "tel_particular", 3);
	}
	public void InicializarPanelTelPerFisica() throws Exception {
		this.InicializarPanelTelPerFisica(false, false, false);
	}
	public void InicializarPanelTelPerFisica(boolean req_com,boolean req_cel, boolean req_par) throws Exception {
		JFormPanelResponsive panel = this.getMainPanel().AddItemFieldset("Teléfono");
		panel.setLabelLeft(6);
		panel.AddItemEdit("Particular", CHAR, req_par?REQ:OPT, "tel_particular", 3);
		panel.AddItemEdit("Comercial", CHAR, req_com?REQ:OPT, "tel_comercial", 3);
		panel.AddItemEdit("Celular", CHAR, req_cel?REQ:OPT, "tel_celular", 3);
	}

	
//	protected void addCountryDependencies(JFormLOV pais) throws Exception {
//		pais.addDependencies("provincia");
//	}

//	public JWins ObtenerPaises() throws Exception {
//		return new GuiPaises();
//	}

	//
	// public void blanquearCiudad() throws Exception {
	// JFormControl oControl = GetControles().ControlByName("ciudad_id");
	// oControl.Blanquear();
	// }
	public void blanquearProvincia() throws Exception {
		JFormControl oControl=getControles().findControl("provincia");
		oControl.setValue("");
	}

	public void blanquearLocalidad() throws Exception {
		JFormControl oControl=getControles().findControl("localidad_id");
		oControl.clear();
	}

	public JWins ObtenerProvincia() throws Exception {
		GuiProvincias oProvincias=new GuiProvincias();
		oProvincias.getRecords().addFilter("pais", getControles().findControl("pais").getValue());
		oProvincias.addOrderAdHoc("descripcion", "ASC");
		return oProvincias;
	}
	
	public JWins ObtenerProvinciaSimple() throws Exception {
		GuiDiccionarios oProvincias=new GuiDiccionarios();
		oProvincias.getRecords().addFilter("company",  BizUsuario.getUsr().getCompany());
		oProvincias.getRecords().addFilter("grupo", "PROVINCIA");
		oProvincias.addOrderAdHoc("descripcion", "ASC");
		return oProvincias;
	}
	public JWins ObtenerCiudadSimple() throws Exception {
		GuiDiccionarios oProvincias=new GuiDiccionarios();
		oProvincias.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		oProvincias.getRecords().addFilter("grupo", "CIUDAD");
		oProvincias.addOrderAdHoc("descripcion", "ASC");
		return oProvincias;
	}

//	void pais_itemStateChanged(ItemEvent e) {
//		try {
//			if (!isIdle()) return;
//			if (e.getStateChange()==ItemEvent.SELECTED) {
//				onCambioPais();
//			}
//		} catch (Exception ex) {
//		}
//	}
//
//	void provincia_itemStateChanged(ItemEvent e) {
//		try {
//			if (e.getStateChange()==ItemEvent.SELECTED) {
//				JFormControl oControl=this.GetControles().findControl("ciudad_id");
//				oControl.clear();
//				blanquearLocalidad();
//			}
//		} catch (Exception ex) {
//		}
//	}

	protected int getFormWidth() {
		return getPanelWidth()+30;
	}
	protected int getPanelWidth() {
		return 600;
	}

	public JFormTabPanelResponsive tabPanel;
	public JFormTabPanelResponsive getTabPanel() throws Exception {
		if (this.tabPanel!=null) return this.tabPanel;
		JFormTabPanelResponsive t = this.addTabHalf();
		t.setId("tab-panel");
		return (this.tabPanel = t);
	}
	
	
	private JWins getCities(boolean one) throws Exception {
		GuiCities g = new GuiCities();
		if (one) {
			g.getRecords().addFilter("city", this.findControl("city").getProp().toString());
		} else {
			g.getRecords().addFilter("country", this.valueControl("pais"));
		}
		return g;
	}


//	private BizPais getObjPais() throws Exception {
//		String pais = this.valueControl("pais");
//		if (pais==null || pais.equals("")) return BizUsuario.getUsr().getObjCountry();
//		BizPais p = new BizPais();
//		p.dontThrowException(true);
//		if(!p.Read(pais))
//			JExcepcion.SendError("Pais sin configurar: "+p.GetDescrip());
//		return p;
//	}
	
	@Override
	public void checkControls(String sControlId) throws Exception {
		if (sControlId.equals("pais")) {
			this.verifyLabels(true);
		}
		if (sControlId.equals("cod_postal")) {
			String cp = getControles().findControl("cod_postal").getValue();
			if (!cp.trim().equals("")) {
				BizCiudadCP ciudad = new BizCiudadCP();
				ciudad.dontThrowException(true);
				if (ciudad.readCP(cp)) {
					getControles().findControl("provincia").getProp().setValue(ciudad.getProvincia());
//					GetControles().findControl("ciudad_id").getProp().setValue(ciudad.getCiudad());
					getControles().findControl("provincia").setValue(ciudad.getProvincia());
//					GetControles().findControl("ciudad_id").setValue(ciudad.getCiudad());
				}
			}
		}
		else if (sControlId.equals("ciudad_id")) {
			String cp = getControles().findControl("cod_postal").getValue();
			if (cp.trim().equals("")) {
				BizCiudadCP ciudad = new BizCiudadCP();
				ciudad.dontThrowException(true);
				if (ciudad.countCP(getControles().findControl("pais").getProp().toString(),getControles().findControl("provincia").getProp().toString(),getControles().findControl("ciudad_id").getProp().toString())==1) {
					if (ciudad.Read(getControles().findControl("pais").getProp().toString(),getControles().findControl("provincia").getProp().toString(),getControles().findControl("ciudad_id").getProp().toString())) {
						getControles().findControl("cod_postal").getProp().setValue(ciudad.getCodPostal());
						getControles().findControl("cod_postal").setValue(ciudad.getCodPostal());
					}
				}
			} else {
				BizCiudadCP ciudad = new BizCiudadCP();
				ciudad.dontThrowException(true);
				if (!ciudad.readCP(getControles().findControl("ciudad_id").getProp().toString(),cp)) {
					if (ciudad.countCP(getControles().findControl("pais").getProp().toString(),getControles().findControl("provincia").getProp().toString(),getControles().findControl("ciudad_id").getProp().toString())==1) {
						if (ciudad.Read(getControles().findControl("pais").getProp().toString(),getControles().findControl("provincia").getProp().toString(),getControles().findControl("ciudad_id").getProp().toString())) {
							getControles().findControl("cod_postal").getProp().setValue(ciudad.getCodPostal());
							getControles().findControl("cod_postal").setValue(ciudad.getCodPostal());
						}
					}
				}
			}
		}
		super.checkControls(sControlId);
	}
	
	public JWins getCiudades(boolean one) throws Exception {
		GuiCiudades g = new GuiCiudades();
		g.setModeWinLov(true);
		String cp = this.valueControl("cod_postal");
		if (cp.equals("")) cp = this.findControl("cod_postal").getProp().toString();
		String pais = this.valueControl("pais");
		if (pais==null) return g;
		if (pais.equals("")) pais = this.findControl("pais").getProp().toString();
		String provincia = this.valueControl("provincia");
		if (provincia.equals("")) provincia = this.findControl("provincia").getProp().toString();
		String ciudad = this.valueControl("ciudad_id");
		if (ciudad.equals("")) ciudad = this.findControl("ciudad_id").getProp().toString();
		if (one) {
			g.getRecords().addFilter("pais", pais);
			g.getRecords().addFilter("provincia", provincia);
			g.getRecords().addFilter("ciudad_id", ciudad);
		} else {
			if (!cp.equals("")){
//				g.getRecords().addJoin("reg_ciudad_cp");
//				g.getRecords().addFixedFilter("where reg_ciudad_cp.pais=reg_ciudad.pais" +
//						" and reg_ciudad_cp.provincia=reg_ciudad.provincia  " +
//						" and reg_ciudad_cp.ciudad_id=reg_ciudad.ciudad_id and reg_ciudad_cp.cod_postal='"+cp+"'");
				GuiCiudadCPes gcp = new GuiCiudadCPes();
				gcp.setModeWinLov(true);
				gcp.SetVision(BizCiudadCP.ABM_CIUDAD);
				gcp.getRecords().addFilter("pais", pais);
				gcp.getRecords().addFilter("provincia", provincia);
				gcp.getRecords().addFilter("cod_postal", cp);
				return gcp;
			} else {
				g.getRecords().addFilter("pais", pais);
				g.getRecords().addFilter("provincia", provincia);
				g.setRefreshOnlyOnUserRequest(true);
			}
		}
		g.addOrderAdHoc("ciudad", "ASC");
		return g;
	}

	public GuiNewPerson getWin() throws Exception {
		return (GuiNewPerson) this.getBaseWin();
	}

	public BizNewPerson getPerson() throws Exception {
		return this.getWin().GetcDato();
	}

	public String findPaisDefault() throws Exception {
		return BizUsuario.getUsr().getCountry();
	}
	
	public JFormPanelResponsive panelFlat;
	public JFormPanelResponsive getPanelFlat() throws Exception {
		if (this.panelFlat!=null) return this.panelFlat;
		return (this.panelFlat=this.AddItemColumn());
	}
	public JFormPanelResponsive datosFlat;
	public JFormPanelResponsive getDatosFlat() throws Exception {
		if (this.datosFlat!=null) return this.datosFlat;
		JFormPanelResponsive p =this.getPanelFlat().AddItemColumn(9);
		this.makeIconColumn(p.AddItemColumn(1));
		return (this.datosFlat=p.AddItemColumn(11));
	}
	
	public void makeIconColumn(JFormPanelResponsive panel) throws Exception {
	}	
	
	public void makeDomicilioFlat() throws Exception {
		JFormPanelResponsive all = this.getDatosFlat();
		JFormPanelResponsive line=all.AddItemColumn().AddInLinePanel();
		line.AddItemEdit("País", CHAR, REQ, "pais").hide();
//		line.addGap(48);
//		col.AddItemColumn(1);
//		col.AddItemColumn(1).addButton(20, null, 1);
//		JFormPanelResponsive line = col.AddItemColumn(11).AddInLinePanel();
//		line.addButton(20, null, 0);
//		line.addGap(3);
		StringBuffer s = new StringBuffer();
		s.append(JTools.capitalizeAll(this.getPerson().getCalle()));
		s.append(" "+this.getPerson().getNumero());
		s.append(" "+this.getPerson().getPiso());
		if (!this.getPerson().getDepto().isEmpty())
			s.append(" /"+this.getPerson().getDepto());
		if (!this.getPerson().getOtros().isEmpty())
			s.append(" /"+this.getPerson().getOtros());
		if (!this.getPerson().getCodPostal().isEmpty())
			s.append(" ("+this.getPerson().getCodPostal()+")");


		if (!this.getPerson().getLocalidad().isEmpty())
			s.append(", "+JTools.capitalizeAll(this.getPerson().getLocalidad().toLowerCase()));
		if (!this.getPerson().getCiudad().isEmpty())
			s.append(", "+JTools.capitalizeAll(this.getPerson().getCiudad().toLowerCase()));
		if (!this.getPerson().getDescrProvincia().isEmpty())
			s.append(", "+JTools.capitalizeAll(this.getPerson().getDescrProvincia().toLowerCase()));
		s.append(", "+JTools.capitalizeAll(this.getPerson().getDescrPais().toLowerCase()));
		if (!this.getPerson().getDescrCity().isEmpty())
			s.append(", "+JTools.capitalizeAll(this.getPerson().getDescrCity().toLowerCase()));
		line.AddItemLabel(s.toString(), 0);
		line.addGap(6);
//		col = line;//all.AddItemColumn(4);
//		line = col.AddItemColumn(11).AddInLinePanel();
		if (!this.getPerson().getTelParticular().isEmpty()) {
			line.addButton(57, null, 0);
			line.AddItemLabel(this.getPerson().getTelParticular(), 0);
			line.addGap(6);
		}
		if (!this.getPerson().getTelComercial().isEmpty()) {
			line.addButton(57, null, 0);
			line.AddItemLabel(this.getPerson().getTelComercial(), 0);
			line.addGap(6);
		}
		if (!this.getPerson().getTelCelular().isEmpty()) {
			line.addButton(57, null, 0);
			line.AddItemLabel(this.getPerson().getTelCelular(), 0);
			line.addGap(6);
		}
		if (!this.getPerson().getTelFax().isEmpty()) {
			line.addButton(57, null, 0);
			line.AddItemLabel(this.getPerson().getTelFax(), 0);
			line.addGap(6);
		}
		if (!this.getPerson().getEMail().isEmpty()) {
			line.addButton(63, null, 0);
			line.AddItemLabel(this.getPerson().getEMail(), 0);
			line.addGap(6);
		}
	}
	public void makeTelefonoFlat() throws Exception {
//		JFormPanelResponsive panel = this.getPanelFlat().AddItemColumn(3);
	}


	//	protected void onCambioPais() throws Exception {
//		// checkControls();
//		blanquearProvincia();
//		// blanquearCiudad();
//		// blanquearLocalidad();
//	}
}
