package pss.bsp.consolid.model.clientes;

import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizNewBSPCompany;
import pss.bsp.dk.BizClienteDK;
import pss.common.event.mailing.BizMailingPersona;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.common.security.BizWebUserProfile;
import pss.core.data.BizPssConfig;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCliente extends JRecord {

  private JString pCustomerNumber = new JString();
  private JString pAttribute1 = new JString();
  private JString pSalesRepNumber = new JString();
  private JLong pSalesRepId = new JLong();
  private JString pCustomerName = new JString();
  private JString pRFC = new JString();
  private JString pAttribute2 = new JString();
  private JLong pCustomerId = new JLong();
  private JLong pSiteUseId = new JLong();
  private JLong pPartySiteId = new JLong();
  private JLong pPartyId = new JLong();
  private JString pLocation = new JString();
  private JString pStatus = new JString();
  private JString pStatusSite = new JString();
  private JString pCCI = new JString();
  private JString pAttribute5 = new JString();
  private JLong pOrgId = new JLong();
  private JString pDireccion = new JString();
  private JString pContacto = new JString();
  private JString pRegFiscal = new JString();
  private JString pRefBancaria = new JString();
  private JBoolean pExistCompany = new JBoolean() {
  	public void preset() throws Exception {
			pExistCompany.setValue(hasExistCompany());

		}
  };
  
  public boolean hasExistCompany()throws Exception {
  	return BizCompany.getCompany("C_"+pCustomerNumber.getValue())!=null;
  }
 


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCustomerNumber(String zValue) throws Exception {    pCustomerNumber.setValue(zValue);  }
  public String getCustomerNumber() throws Exception {     return pCustomerNumber.getValue();  }

  public void setAttribute1(String zValue) throws Exception { pAttribute1.setValue(zValue); }
  public String getAttribute1() throws Exception { return pAttribute1.getValue(); }

  public void setSalesRepNumber(String zValue) throws Exception { pSalesRepNumber.setValue(zValue); }
  public String getSalesRepNumber() throws Exception { return pSalesRepNumber.getValue(); }

  public void setSalesRepId(Long zValue) throws Exception { pSalesRepId.setValue(zValue); }
  public Long getSalesRepId() throws Exception { return pSalesRepId.getValue(); }

  public void setCustomerName(String zValue) throws Exception { pCustomerName.setValue(zValue); }
  public String getCustomerName() throws Exception { return pCustomerName.getValue(); }

  public void setRFC(String zValue) throws Exception { pRFC.setValue(zValue); }
  public String getRFC() throws Exception { return pRFC.getValue(); }

  public void setAttribute2(String zValue) throws Exception { pAttribute2.setValue(zValue); }
  public String getAttribute2() throws Exception { return pAttribute2.getValue(); }

  public void setCustomerId(Long zValue) throws Exception { pCustomerId.setValue(zValue); }
  public Long getCustomerId() throws Exception { return pCustomerId.getValue(); }

  public void setSiteUseId(Long zValue) throws Exception { pSiteUseId.setValue(zValue); }
  public Long getSiteUseId() throws Exception { return pSiteUseId.getValue(); }

  public void setPartySiteId(Long zValue) throws Exception { pPartySiteId.setValue(zValue); }
  public Long getPartySiteId() throws Exception { return pPartySiteId.getValue(); }

  public void setPartyId(Long zValue) throws Exception { pPartyId.setValue(zValue); }
  public Long getPartyId() throws Exception { return pPartyId.getValue(); }

  public void setLocation(String zValue) throws Exception { pLocation.setValue(zValue); }
  public String getLocation() throws Exception { return pLocation.getValue(); }

  public void setStatus(String zValue) throws Exception { pStatus.setValue(zValue); }
  public String getStatus() throws Exception { return pStatus.getValue(); }

  public void setStatusSite(String zValue) throws Exception { pStatusSite.setValue(zValue); }
  public String getStatusSite() throws Exception { return pStatusSite.getValue(); }

  public void setCCI(String zValue) throws Exception { pCCI.setValue(zValue); }
  public String getCCI() throws Exception { return pCCI.getValue(); }

  public void setAttribute5(String zValue) throws Exception { pAttribute5.setValue(zValue); }
  public String getAttribute5() throws Exception { return pAttribute5.getValue(); }

  public void setOrgId(Long zValue) throws Exception { pOrgId.setValue(zValue); }
  public Long getOrgId() throws Exception { return pOrgId.getValue(); }

  public void setDireccion(String zValue) throws Exception { pDireccion.setValue(zValue); }
  public String getDireccion() throws Exception { return pDireccion.getValue(); }

  public void setContacto(String zValue) throws Exception { pContacto.setValue(zValue); }
  public String getContacto() throws Exception { return pContacto.getValue(); }

  public void setRegFiscal(String zValue) throws Exception { pRegFiscal.setValue(zValue); }
  public String getRegFiscal() throws Exception { return pRegFiscal.getValue(); }

  public void setRefBancaria(String zValue) throws Exception { pRefBancaria.setValue(zValue); }
  public String getRefBancaria() throws Exception { return pRefBancaria.getValue(); }

  /**
   * Constructor de la Clase
   */
  public BizCliente() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "customer_number", pCustomerNumber );
    this.addItem("attribute1", pAttribute1);
    this.addItem("salesrep_number", pSalesRepNumber);
    this.addItem("salesrep_id", pSalesRepId);
    this.addItem("customer_name", pCustomerName);
    this.addItem("rfc", pRFC);
    this.addItem("attribute2", pAttribute2);
    this.addItem("customer_id", pCustomerId);
    this.addItem("site_use_id", pSiteUseId);
    this.addItem("party_site_id", pPartySiteId);
    this.addItem("party_id", pPartyId);
    this.addItem("location", pLocation);
    this.addItem("status", pStatus);
    this.addItem("status_site", pStatusSite);
    this.addItem("cci", pCCI);
    this.addItem("attribute5", pAttribute5);
    this.addItem("org_id", pOrgId);
    this.addItem("direccion", pDireccion);
    this.addItem("contacto", pContacto);
    this.addItem("reg_fiscal", pRegFiscal);
    this.addItem("ref_bancaria", pRefBancaria);
    this.addItem("has_company", pExistCompany);

  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
  	this.addFixedItem(KEY, "customer_number", "Customer Number", true, true, 20);
  	this.addFixedItem(FIELD, "attribute1", "Attribute 1", true, false, 50);
  	this.addFixedItem(FIELD, "salesrep_number", "Sales Rep Number", true, false, 10);
  	this.addFixedItem(FIELD, "salesrep_id", "Sales Rep ID", true, false, 10);
  	this.addFixedItem(FIELD, "customer_name", "Customer Name", true, false, 400);
  	this.addFixedItem(FIELD, "rfc", "RFC", true, false, 20);
  	this.addFixedItem(FIELD, "attribute2", "Attribute 2", true, false, 50);
  	this.addFixedItem(FIELD, "customer_id", "Customer ID", true, false, 10);
  	this.addFixedItem(FIELD, "site_use_id", "Site Use ID", true, false, 10);
  	this.addFixedItem(FIELD, "party_site_id", "Party Side ID", true, false, 10);
  	this.addFixedItem(FIELD, "party_id", "Party ID", true, false, 10);
  	this.addFixedItem(FIELD, "location", "Location", true, false, 50);
  	this.addFixedItem(FIELD, "status", "Status", true, false, 20);
  	this.addFixedItem(FIELD, "status_site", "Status Site", true, false, 20);
  	this.addFixedItem(FIELD, "cci", "CCI", true, false, 30);
  	this.addFixedItem(FIELD, "attribute5", "Attribute 5", true, false, 400);
  	this.addFixedItem(FIELD, "org_id", "Org ID", true, false, 10);
  	this.addFixedItem(FIELD, "direccion", "Direccion", true, false, 400);
  	this.addFixedItem(FIELD, "contacto", "Contacto", true, false, 50);
  	this.addFixedItem(FIELD, "reg_fiscal", "Reg Fiscal", true, false, 20);
  	this.addFixedItem(FIELD, "ref_bancaria", "Ref Bancaria", true, false, 20);
  	this.addFixedItem(VIRTUAL, "has_company", "Existe en Site TKM", true, false, 20);

  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "apps.CNS_CLIENTES_V"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zId ) throws Exception { 
    addFilter( "customer_number",  zId ); 
    return read(); 
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
		newComp.setCompany("C_"+getCustomerNumber());
		newComp.setBusiness(parent.getBusiness());
		newComp.setParentCompany(BizUsuario.getUsr().getCompany());
		newComp.setDesciption(getCustomerName());
		//newComp.setEmail(this.getm);
		newComp.setModelo("TV");
		newComp.setPais(parent.getPais());
		newComp.setTipoLicencia(parent.getObjExtraData().getTipoLicencia());
		newComp.setFechaInicio(new Date());
		newComp.setFechaHasta(new Date());
		newComp.setLicencias(1);
		newComp.setRenovaciones(0);
		newComp.processInsert();
		
		BizBSPCompany child = BizBSPCompany.getObjBSPCompany("C_"+getCustomerNumber());
		child.setObjExtraData(newComp.getObjExtraData());
		child.getObjExtraData().setCodigoCliente(getCustomerNumber());
		child.getObjExtraData().setDependant(true);
		child.getObjExtraData().processUpdate();
		createUser(child);
		
		procActualizar(BizUsuario.getUsr().getCompany());

		BizCompany comp = new BizCompany();
		comp.dontThrowException(true);
		if (comp.Read("C_"+getCustomerNumber())) {
			comp.setParentCompany(BizUsuario.getUsr().getCompany());
			comp.update();		
		}

	}
	protected void procActualizar(String company) throws Exception {
		BizClienteDK mailing = new BizClienteDK();
		mailing.dontThrowException(true);
		mailing.addFilter("company", company);
		mailing.addFilter("dk", getCustomerNumber());
		if (mailing.read()) {
			mailing.setDescripcion(getCustomerName());
			mailing.setCode(getRefBancaria());
			mailing.processUpdate();
		} else {
			mailing.filtersToProp();
			mailing.setDescripcion(getCustomerName());
			mailing.setCode(getRefBancaria());
			mailing.processInsert();
		}
	}
	
	
	protected void createUser(BizBSPCompany company) throws Exception {
		BizUsuario user=new BizUsuario();
		this.assingUniqueData(user,company);
		user.processInsert();
	
 
		BizWebUserProfile oWebUserProfile=new BizWebUserProfile();
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
		user.SetDescrip("Usuario - "+getCustomerName());
		user.setCompany(company.getCompany());
		user.setLenguaje(BizPssConfig.GetDefaultLanguage());
		user.setBirthCountryId(company.getPais());
		user.setPais(company.getPais());
		user.setCustomMenu(null);
	}
}
		
