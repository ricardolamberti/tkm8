package pss.bsp.consolid.model.clientesView;

import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizNewBSPCompany;
import pss.bsp.consolid.model.liquidacion.acumulado.invoice.BizInvoice;
import pss.bsp.dk.BizClienteDK;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.common.security.BizWebUserProfile;
import pss.core.data.BizPssConfig;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.win.JWins;
import pss.tourism.dks.BizDk;

public class BizClienteView extends JRecord {

	private JString pCustomerNumber = new JString();
	private JString pCustomerName = new JString();
	private JString pRegFiscal = new JString();
	private JString pRefBancaria = new JString();
	private JBoolean pExistCompany = new JBoolean() {
		public void preset() throws Exception {
			pExistCompany.setValue(hasExistCompany());

		}
	};
	private JString pCtaBancaria = new JString() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pCtaBancaria.setValue(getCtaBancaria());
				first = false;
			}

		}
	};
	private JString pCtaClave = new JString() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pCtaClave.setValue(getCtaClave());
				first = false;
			}

		}
	};
	private JString pEmail = new JString() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pEmail.setValue(getEmail());
				first = false;
			}

		}
	};
	private JBoolean pReporte1 = new JBoolean() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pReporte1.setValue(getReporte1());
				first = false;
			}

		}
	};
	private JBoolean pReporte2 = new JBoolean() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pReporte2.setValue(getReporte2());
				first = false;
			}

		}
	};
	private JBoolean pReporte3 = new JBoolean() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pReporte3.setValue(getReporte3());
				first = false;
			}

		}
	};
	private JBoolean pReporte4 = new JBoolean() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pReporte4.setValue(getReporte4());
				first = false;
			}

		}
	};
	private JBoolean pReporte5 = new JBoolean() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pReporte5.setValue(getReporte5());
				first = false;
			}

		}
	};
	private JBoolean pReporte6 = new JBoolean() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pReporte6.setValue(getReporte6());
				first = false;
			}

		}
	};
	private JBoolean pReporte7 = new JBoolean() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pReporte7.setValue(getReporte7());
				first = false;
			}

		}
	};
	private JBoolean pReporte8 = new JBoolean() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pReporte8.setValue(getReporte8());
				first = false;
			}

		}
	};
	private JString pComision = new JString() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pComision.setValue(getComision());
				first = false;
			}

		}
	};
	private JString pFormato = new JString() {
		boolean first = true;

		public void preset() throws Exception {
			if (first) {
				pFormato.setValue(getFormato());
				first = false;
			}

		}
	};
	private JBoolean pHabilitado = new JBoolean() {
		public void preset() throws Exception {
			pHabilitado.setValue(getHabilitado());

		}
	};
	private JBoolean pUltimoAcceso = new JBoolean() {
		public void preset() throws Exception {
			pUltimoAcceso.setValue(getUltimoAcceso());

		}
	};

	public boolean hasExistCompany() throws Exception {
		return BizCompany.getCompany("C_" + pCustomerNumber.getValue()) != null;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCustomerNumber(String zValue) throws Exception {
		pCustomerNumber.setValue(zValue);
	}

	public String getCustomerNumber() throws Exception {
		return pCustomerNumber.getValue();
	}

	public void setCustomerName(String zValue) throws Exception {
		pCustomerName.setValue(zValue);
	}

	public String getCustomerName() throws Exception {
		return pCustomerName.getValue();
	}

	public void setRegFiscal(String zValue) throws Exception {
		pRegFiscal.setValue(zValue);
	}

	public String getRegFiscal() throws Exception {
		return pRegFiscal.getValue();
	}

	public void setCtaBancaria(String zValue) throws Exception {
		pCtaBancaria.setValue(zValue);
	}

	public String getCtaBancaria() throws Exception {
		return getObjClienteDK() == null ? "" : getObjClienteDK().getCtaBancaria();
	}

	public void setCtaClave(String zValue) throws Exception {
		pCtaClave.setValue(zValue);
	}

	public String getCtaClave() throws Exception {
		return getObjClienteDK() == null ? "" : getObjClienteDK().getCtaClave();
	}

	public void setRefBancaria(String zValue) throws Exception {
		pRefBancaria.setValue(zValue);
	}

	public String getRefBancaria() throws Exception {
		return pRefBancaria.getValue();
	}

	public String getEmail() throws Exception {
		return getObjClienteDK() == null ? "" : getObjClienteDK().getMail();
	}

	public boolean getHabilitado() throws Exception {
		return getObjUsuario() == null ? false : getObjUsuario().isActive();
	}

	public Date getUltimoAcceso() throws Exception {
		return getObjUsuario() == null ? null : getObjUsuario().getFechaUltimoIngreso();
	}

	public boolean getReporte1() throws Exception {
		return getObjClienteDK() == null ? false : getObjClienteDK().getReporte1();
	}

	public boolean getReporte2() throws Exception {
		return getObjClienteDK() == null ? false : getObjClienteDK().getReporte2();
	}

	public boolean getReporte3() throws Exception {
		return getObjClienteDK() == null ? false : getObjClienteDK().getReporte3();
	}

	public boolean getReporte4() throws Exception {
		return getObjClienteDK() == null ? false : getObjClienteDK().getReporte4();
	}

	public boolean getReporte5() throws Exception {
		return getObjClienteDK() == null ? false : getObjClienteDK().getReporte5();
	}

	public boolean getReporte6() throws Exception {
		return getObjClienteDK() == null ? false : getObjClienteDK().getReporte6();
	}

	public boolean getReporte7() throws Exception {
		return getObjClienteDK() == null ? false : getObjClienteDK().getReporte7();
	}

	public boolean getReporte8() throws Exception {
		return getObjClienteDK() == null ? false : getObjClienteDK().getReporte8();
	}

	public String getComision() throws Exception {
		return getObjClienteDK() == null ? "S" : getObjClienteDK().getComision();
	}

	public String getFormato() throws Exception {
		return getObjClienteDK() == null ? null : getObjClienteDK().getFormato();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizClienteView() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("customer_number", pCustomerNumber);
		this.addItem("customer_name", pCustomerName);
		this.addItem("reg_fiscal", pRegFiscal);
		this.addItem("ref_bancaria", pRefBancaria);
		this.addItem("cta_bancaria", pCtaBancaria);
		this.addItem("cta_clave", pCtaClave);
		this.addItem("has_company", pExistCompany);
		this.addItem("email", pEmail);
		this.addItem("formato", pFormato);
		this.addItem("reporte1", pReporte1);
		this.addItem("reporte2", pReporte2);
		this.addItem("reporte3", pReporte3);
		this.addItem("reporte4", pReporte4);
		this.addItem("reporte5", pReporte5);
		this.addItem("reporte6", pReporte6);
		this.addItem("reporte7", pReporte7);
		this.addItem("reporte8", pReporte8);
		this.addItem("comision", pComision);
		this.addItem("active", pHabilitado);
		this.addItem("ult_acceso", pUltimoAcceso);

	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "customer_number", "Customer Number", true, true, 20);
		this.addFixedItem(FIELD, "customer_name", "Customer Name", true, false, 400);
		this.addFixedItem(FIELD, "reg_fiscal", "Reg Fiscal", true, false, 20);
		this.addFixedItem(FIELD, "ref_bancaria", "Ref Bancaria", true, false, 20);
		this.addFixedItem(VIRTUAL, "cta_bancaria", "Cta Bancaria", true, false, 50);
		this.addFixedItem(VIRTUAL, "cta_clave", "Cta Clave", true, false, 50);
		this.addFixedItem(VIRTUAL, "has_company", "Existe en Site TKM", true, false, 20);
		this.addFixedItem(VIRTUAL, "email", "Email", true, false, 400);
		this.addFixedItem(VIRTUAL, "formato", "Formato Invoice", true, false, 400);
		this.addFixedItem(VIRTUAL, "reporte1", "reporte1", true, false, 1);
		this.addFixedItem(VIRTUAL, "reporte2", "reporte2", true, false, 1);
		this.addFixedItem(VIRTUAL, "reporte3", "reporte3", true, false, 1);
		this.addFixedItem(VIRTUAL, "reporte4", "reporte4", true, false, 1);
		this.addFixedItem(VIRTUAL, "reporte5", "reporte5", true, false, 1);
		this.addFixedItem(VIRTUAL, "reporte6", "reporte6", true, false, 1);
		this.addFixedItem(VIRTUAL, "reporte7", "reporte7", true, false, 1);
		this.addFixedItem(VIRTUAL, "reporte8", "reporte8", true, false, 1);
		this.addFixedItem(VIRTUAL, "comision", "Tipo Comisión", true, false, 1);
		this.addFixedItem(VIRTUAL, "active", "Activo", true, false, 1);
		this.addFixedItem(VIRTUAL, "ult_acceso", "Ult.Acceso", true, false, 18);

	}

	@Override
	public void createControlProperties() throws Exception {
		this.addControlsProperty("formato", createControlCombo(JWins.createVirtualWinsFromMap(BizInvoice.getTiposFormato()), null, null));
		this.addControlsProperty("comision", createControlCombo(JWins.createVirtualWinsFromMap(BizClienteDK.getTipoComision()), null, null));
		super.createControlProperties();
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return null;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	BizClienteDK objPersona;

	public BizClienteDK getObjClienteDK() throws Exception {
		if (objPersona != null)
			return objPersona;
		BizClienteDK biz = new BizClienteDK();
		biz.dontThrowException(true);
		biz.addFilter("company", BizUsuario.getUsr().getCompany());
		biz.addFilter("dk", this.getCustomerNumber());
		if (biz.read())
			return objPersona = biz;
		return null;
	}

	BizDk objDk;

	public BizDk getObjDK() throws Exception {
		if (objDk != null)
			return objDk;
		BizDk biz = new BizDk();
		biz.dontThrowException(true);
		biz.addFilter("company", BizUsuario.getUsr().getCompany());
		biz.addFilter("dk", this.getCustomerNumber());
		if (biz.read())
			return objDk = biz;
		return null;
	}

	BizUsuario objUsuario;

	public BizUsuario getObjUsuario() throws Exception {
		if (objUsuario != null)
			return objUsuario;
		BizUsuario biz = new BizUsuario();
		biz.dontThrowException(true);
		biz.addFilter("company", "C_" + getCustomerNumber());
		biz.addFilter("usuario", "" + getCustomerNumber());
		if (biz.read())
			return objUsuario = biz;
		return null;

	}

	@Override
	public void clean() throws Exception {
		objUsuario = null;
		objPersona = null;
		super.clean();
	}

	/**
	 * Default read() method
	 */
	public boolean read(String zId) throws Exception {
		addFilter("customer_number", zId);
		boolean r = read();
		return r;
	}

	public void execGenerar() throws Exception {
		JExec oExec = new JExec(null, "procGenerar") {

			@Override
			public void Do() throws Exception {
				procGenerar();
			}
		};
		oExec.execute();
	}

	public void execActualizar(String company) throws Exception {
		JExec oExec = new JExec(null, "procActualizar") {

			@Override
			public void Do() throws Exception {
				procActualizar(company);
			}
		};
		oExec.execute();
	}

	public void procGenerar() throws Exception {
		BizBSPCompany parent = BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany());

		BizNewBSPCompany newComp = new BizNewBSPCompany();
		newComp.setCompany("C_" + getCustomerNumber());
		newComp.setBusiness(parent.getBusiness());
		newComp.setParentCompany(BizUsuario.getUsr().getCompany());
		newComp.setDesciption(getCustomerName());
		// newComp.setEmail(this.getm);
		newComp.setModelo("TV");
		newComp.setPais(parent.getPais());
		newComp.setTipoLicencia(parent.getObjExtraData().getTipoLicencia());
		newComp.setFechaInicio(new Date());
		newComp.setFechaHasta(new Date());
		newComp.setLicencias(1);
		newComp.setRenovaciones(0);
		newComp.processInsert();

		BizBSPCompany child = BizBSPCompany.getObjBSPCompany("C_" + getCustomerNumber());
		child.setObjExtraData(newComp.getObjExtraData());
		child.getObjExtraData().setCodigoCliente(getCustomerNumber());
		child.getObjExtraData().setDependant(true);
		child.getObjExtraData().processUpdate();
		createUser(child);

		procActualizar(BizUsuario.getUsr().getCompany());

		BizCompany comp = new BizCompany();
		comp.dontThrowException(true);
		if (comp.Read("C_" + getCustomerNumber())) {
			comp.setParentCompany(BizUsuario.getUsr().getCompany());
			comp.update();
		}

	}

	protected void procActualizar(String company) throws Exception {
		BizClienteDK mailing = new BizClienteDK();
		mailing.dontThrowException(true);
		mailing.addFilter("company", company);
		mailing.addFilter("codigo", getCustomerNumber());
		if (mailing.read()) {
			mailing.setDescripcion(getCustomerName());
			mailing.setCode(getRefBancaria());
			mailing.setCtaBancaria(getCtaBancaria());
			mailing.setCtaClave(getCtaClave());
			mailing.processUpdate();
		} else {
			mailing.filtersToProp();
			mailing.setDescripcion(getCustomerName());
			mailing.setCode(getRefBancaria());
			mailing.setCtaBancaria(getCtaBancaria());
			mailing.setCtaClave(getCtaClave());
			mailing.processInsert();
		}
	}

	protected void createUser(BizBSPCompany company) throws Exception {
		BizUsuario user = new BizUsuario();
		this.assingUniqueData(user, company);
		user.processInsert();

		BizWebUserProfile oWebUserProfile = new BizWebUserProfile();
		this.assingUniqueWedData(oWebUserProfile, user);
		oWebUserProfile.processInsert();

	}

	protected void assingUniqueWedData(BizWebUserProfile oWebUserProfile, BizUsuario user) throws Exception {
		oWebUserProfile.setUsuario(user.GetUsuario());
		oWebUserProfile.setHomePage("pss.bsp.GuiModuloBSP_10");
		oWebUserProfile.setMaxMatrix(10000);
		oWebUserProfile.setDefaultPagesize(10);
	}

	protected void assingUniqueData(BizUsuario user, BizBSPCompany company) throws Exception {
		user.SetUsuario(getCustomerNumber());
		user.SetDescrip("Usuario - " + getCustomerName());
		user.setCompany(company.getCompany());
		user.setLenguaje(BizPssConfig.GetDefaultLanguage());
		user.setBirthCountryId(company.getPais());
		user.setPais(company.getPais());
		user.setCustomMenu("EMPTY");
	}

	@Override
	public void processUpdate() throws Exception {
		BizClienteDK pers = getObjClienteDK();
		pers.setMail(pEmail.getRawValue());
		pers.setReporte1(pReporte1.getRawValue());
		pers.setReporte2(pReporte2.getRawValue());
		pers.setReporte3(pReporte3.getRawValue());
		pers.setReporte4(pReporte4.getRawValue());
		pers.setReporte5(pReporte5.getRawValue());
		pers.setReporte6(pReporte6.getRawValue());
		pers.setReporte7(pReporte7.getRawValue());
		pers.setReporte8(pReporte8.getRawValue());
		pers.setComision(pComision.getRawValue());
		pers.setFormato(pFormato.getRawValue());
		pers.update();
	}
}
