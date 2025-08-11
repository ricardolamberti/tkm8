package  pss.bsp.bspBusiness;

import java.util.Date;
import java.util.TreeMap;

import pss.bsp.clase.BizClase;
import pss.bsp.config.airportGroups.BizAirportGroup;
import pss.bsp.config.carrierGroups.BizCarrierGroup;
import pss.bsp.consola.config.GuiBSPConfig;
import pss.bsp.market.BizMarket;
import pss.bsp.regions.BizRegion;
import pss.common.customList.config.carpetas.BizCarpeta;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.event.sql.BizSqlEvent;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.BizCompanyCountry;
import pss.common.regions.company.BizNewCompany;
import pss.common.regions.company.JCompanyBusinessModules;
import pss.common.security.BizOperacion;
import pss.common.security.BizOperacionRol;
import pss.common.security.BizRol;
import pss.common.security.BizRolVinculado;
import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.common.security.BizWebUserProfile;
import pss.core.data.BizPssConfig;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class BizNewBSPCompany extends BizNewCompany {

	protected JString pPais=new JString();
	protected JString pModelo=new JString();
  private JLong pLicencias = new JLong();
  private JDate pFechaIncio = new JDate();
  private JDate pFechaHasta = new JDate();
  private JLong pRenovaciones = new JLong();
  private JString pTipoLicencia = new JString();

	

	public void setPais(String value) throws Exception {pPais.setValue(value);}
	protected String getPais() throws Exception {return pPais.getValue();}
	public void setModelo(String value) throws Exception {pModelo.setValue(value);}
	protected String getModelo() throws Exception {return pModelo.getValue();}
	public void setTipoLicencia(String value) throws Exception {pTipoLicencia.setValue(value);}
	public void setFechaInicio(Date value) throws Exception {pFechaIncio.setValue(value);}
	public void setFechaHasta(Date value) throws Exception {pFechaHasta.setValue(value);}
	public void setRenovaciones(long value) throws Exception {pRenovaciones.setValue(value);}
	public void setLicencias(long value) throws Exception {pLicencias.setValue(value);}
	
	public BizNewBSPCompany() throws Exception {
	}

	protected String getBusiness() throws Exception {
		return JCompanyBusinessModules.BSP;
	}
	

	private JString pEmail=new JString();
	public void setEmail(String value) throws Exception {
		pEmail.setValue(value);
	}



	public String getEmail() throws Exception {
		return pEmail.getValue();
	}


	
	@Override
	public void createProperties() throws Exception {
		super.createProperties();
		this.addItem("email", pEmail);
		this.addItem("pais", pPais);
		this.addItem("modelo", pModelo);
    this.addItem( "licencias", pLicencias );
    this.addItem( "fecha_incio", pFechaIncio );
    this.addItem( "fecha_hasta", pFechaHasta );
    this.addItem( "renovaciones", pRenovaciones );
    this.addItem( "tipo_licencia", pTipoLicencia );
	}

	@Override
	public void createFixedProperties() throws Exception {
		super.createFixedProperties();
		this.addFixedItem(VIRTUAL, "email", "Email", true, false, 250);
		this.addFixedItem(FIELD, "pais", "Pais", true, true, 15);
		this.addFixedItem(FIELD, "modelo", "Modelo", true, true, 15);
    this.addFixedItem( FIELD, "licencias", "Licencias", true, true, 18 );
    this.addFixedItem( FIELD, "fecha_incio", "Fecha incio", true, true, 10 );
    this.addFixedItem( FIELD, "fecha_hasta", "Fecha hasta", true, true, 10 );
    this.addFixedItem( FIELD, "renovaciones", "Renovaciones", true, true, 18 );
    this.addFixedItem( FIELD, "tipo_licencia", "Tipo licencia", true, true, 50 );
	}
	BizCompanyExtraData objExtraData;
	public BizCompanyExtraData getObjExtraData() {
		return objExtraData;
	}
	public void setObjExtraData(BizCompanyExtraData objExtraData) {
		this.objExtraData = objExtraData;
	}
	@Override
	public void processInsert() throws Exception {
		pCompany.setValue(pCompany.getValue().toUpperCase());
		pParentCompany.setValue(pModelo.getValue());
		super.processInsert();
		crearCompanyCountry();
		objExtraData=createExtraData();
		createUsuariosTipos();
		createAdminUser();
		createSegConfig();
		createRoles();
		TreeMap<Long,Long> mapaCarpetas=createCarpetas();
		createDataMining(mapaCarpetas);
//		createIndicadores();
		createRegiones();
		createCarriers();
		createAirports();
		createClases();
		createMarkets();
		createConfig();
	}
	public void migrar() throws Exception {

//		createReports();
		TreeMap<Long,Long> mapaCarpetas=createCarpetas();
		createDataMining(mapaCarpetas);
		createIndicadores();
//		createRegiones();
//		createCarriers();
//		createAirports();
//		createClases();
//		createMarkets();
//		createConfig();
	}
	public void crearCompanyCountry() throws Exception {
		BizCompanyCountry extra = new BizCompanyCountry();
		extra.setCompany(pCompany.getValue());
		extra.setCountry(pPais.getValue().equals("1")?"AR":pPais.getValue());
		extra.processInsert();
	}
	public BizCompanyExtraData createExtraData() throws Exception {
		BizCompanyExtraData extra = new BizCompanyExtraData();
		extra.setCompany(pCompany.getValue());
		extra.setPais(pPais.getValue());
		extra.setLicencias(pLicencias.getValue());
		extra.setFechaHasta(pFechaHasta.getValue());
		extra.setFechaIncio(pFechaIncio.getValue());
		extra.setRenovaciones(pRenovaciones.getValue());
		extra.setTipoLicencia(pTipoLicencia.getValue());
		extra.setVersion(6);
		extra.setInactiva(false); 
		extra.setSuspender(false);
		extra.processInsert();
		return extra;
	
	}
	public void createConfig() throws Exception {
		GuiBSPConfig cc = new GuiBSPConfig();
		cc.GetcDato().dontThrowException(true);
		if (cc.GetcDato().read(pCompany.getValue(),"*")) return;
		cc.GetcDato().processGenerateUser(pCompany.getValue(),"*",pEmail.getValue() );
	}
	
	BizCompany objModelo;
	public BizCompany getObjModelCompany() throws Exception {
		if (objModelo!=null) return objModelo;
		if (getModelo().equals("")) {
			setModelo(BizUsuario.getUsr().getCompany());
		}
		BizCompany oCompany=new BizCompany();
		oCompany.Read(getModelo());
		return objModelo=oCompany;
		
	}
	private void createRegiones() throws Exception {
		JRecords<BizRegion> oData= new JRecords<BizRegion>(BizRegion.class);
		oData.addFilter("company",getObjModelCompany().getCompany());
		JIterator<BizRegion> it = oData.getStaticIterator();
		while (it.hasMoreElements()) {
			BizRegion oCategory=it.nextElement();
			BizRegion oNewCategory=new BizRegion();
			oNewCategory.copyProperties(oCategory);
			oNewCategory.setCompany(pCompany.getValue());
			oCategory.processClon(oNewCategory);
		}
	}

	private void createCarriers() throws Exception {
		JRecords<BizCarrierGroup> oData= new JRecords<BizCarrierGroup>(BizCarrierGroup.class);
		oData.addFilter("company",getObjModelCompany().getCompany());
		JIterator<BizCarrierGroup> it = oData.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCarrierGroup oCategory=it.nextElement();
			BizCarrierGroup oNewCategory=new BizCarrierGroup();
			oNewCategory.copyProperties(oCategory);
			oNewCategory.setCompany(pCompany.getValue());
			oCategory.processClon(oNewCategory);
		}
	}

	private void createAirports() throws Exception {
		JRecords<BizAirportGroup> oData= new JRecords<BizAirportGroup>(BizAirportGroup.class);
		oData.addFilter("company",getObjModelCompany().getCompany());
		JIterator<BizAirportGroup> it = oData.getStaticIterator();
		while (it.hasMoreElements()) {
			BizAirportGroup oCategory=it.nextElement();
			BizAirportGroup oNewCategory=new BizAirportGroup();
			oNewCategory.copyProperties(oCategory);
			oNewCategory.setCompany(pCompany.getValue());
			oCategory.processClon(oNewCategory);
		}
	}
	private void createClases() throws Exception {
		JRecords<BizClase> oData= new JRecords<BizClase>(BizClase.class);
		oData.addFilter("company",getObjModelCompany().getCompany());
		JIterator<BizClase> it = oData.getStaticIterator();
		while (it.hasMoreElements()) {
			BizClase oCategory=it.nextElement();
			BizClase oNewCategory=new BizClase();
			oNewCategory.copyProperties(oCategory);
			oNewCategory.setCompany(pCompany.getValue());
			oCategory.processClon(oNewCategory);
		}
	}
	private void createMarkets() throws Exception {
		JRecords<BizMarket> oData= new JRecords<BizMarket>(BizMarket.class);
		oData.addFilter("company",getObjModelCompany().getCompany());
		JIterator<BizMarket> it = oData.getStaticIterator();
		while (it.hasMoreElements()) {
			BizMarket oCategory=it.nextElement();
			BizMarket oNewCategory=new BizMarket();
			oNewCategory.copyProperties(oCategory);
			oNewCategory.setCompany(pCompany.getValue());
			oCategory.processClon(oNewCategory);
		}
	}


	private void createIndicadores() throws Exception {
		JRecords<BizSqlEvent> oData= new JRecords<BizSqlEvent>(BizSqlEvent.class);
		oData.addFilter("company",getObjModelCompany().getCompany());
		JIterator<BizSqlEvent> it = oData.getStaticIterator();
		while (it.hasMoreElements()) {
			BizSqlEvent oCategory=it.nextElement();
			oCategory.processClon(pCompany.getValue(),true);
		}
	}
	private void createUsuariosTipos() throws Exception {
//		JRecords<BizUsuarioTipo> oData= new JRecords<BizUsuarioTipo>(BizUsuarioTipo.class);
//		oData.addFilter("company",getObjModelCompany().getCompany());
//		JIterator<BizUsuarioTipo> it = oData.getStaticIterator();
//		while (it.hasMoreElements()) {
//			BizUsuarioTipo oCategory=it.nextElement();
//			BizUsuarioTipo oNewCategory=new BizUsuarioTipo();
//			oNewCategory.copyProperties(oCategory);
//			oNewCategory.setCompany(pCompany.getValue());
//			oCategory.processClon(oNewCategory);
//		}
	}
	private void createReports() throws Exception {
		JRecords<BizPlantilla> oData= new JRecords<BizPlantilla>(BizPlantilla.class);
		oData.addFilter("company",getObjModelCompany().getCompany());
		JIterator<BizPlantilla> it = oData.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPlantilla oCategory=it.nextElement();
			BizPlantilla oNewCategory=new BizPlantilla();
			oNewCategory.copyProperties(oCategory);
			oNewCategory.setCompany(pCompany.getValue());
			oCategory.processClon(oNewCategory);
		}
	}
	private void createDataMining(TreeMap<Long,Long> mapa) throws Exception {
		JRecords<BizCustomList> oData= new JRecords<BizCustomList>(BizCustomList.class);
		oData.addFilter("company",getObjModelCompany().getCompany());
		JIterator<BizCustomList> it = oData.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCustomList oCustomList=it.nextElement();
			if (oCustomList.isInvisible()) continue;
			oCustomList.readRecords();
			BizCustomList copy=oCustomList.processClon(pCompany.getValue(),true);
			BizCarpeta carp = new BizCarpeta();
			carp.dontThrowException(true);
			if (carp.read(oCustomList.getCompany(), oCustomList.getListId())) {
				Long carpDest = mapa.get(carp.getPadre());
				if (carpDest!=null) {
					BizCarpeta newCarp = new BizCarpeta();
					newCarp.setCompany(copy.getCompany());
					newCarp.setListado(copy.getListId());
					newCarp.setPadre(carpDest);
					newCarp.processInsert();
				}
			}
		}
	}
	private TreeMap<Long,Long> createCarpetas() throws Exception {
		TreeMap<Long,Long> map = new TreeMap<Long,Long>();
		JRecords<BizCarpeta> oData= new JRecords<BizCarpeta>(BizCarpeta.class);
		oData.addFilter("company",getObjModelCompany().getCompany());
		oData.addFilter("customlist", "null");
		JIterator<BizCarpeta> it = oData.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCarpeta oCarpeta=it.nextElement();
			BizCarpeta newCarp = oCarpeta.processClon(pCompany.getValue(),true);
			map.put(oCarpeta.getSecuencia(), newCarp.getSecuencia());
		}
		return map;
	}

	protected void createAdminUser() throws Exception {
		BizUsuario user=new BizUsuario();
		this.assingAdminData(user);
		user.processInsert();
	
 
		BizWebUserProfile oWebUserProfile=new BizWebUserProfile();
		this.assingAdminWedData(oWebUserProfile, user);
		oWebUserProfile.processInsert();
		
	}

	protected void createSuperUser() throws Exception {
		BizUsuario user=new BizUsuario();
		this.assingSuperData(user);
		user.processInsert();
	
 
		BizWebUserProfile oWebUserProfile=new BizWebUserProfile();
		this.assingSuperWedData(oWebUserProfile, user);
		oWebUserProfile.processInsert();
		
	}

	private void createSegConfig() throws Exception {
		BizSegConfiguracion config = getObjModelCompany().getObjSegConfig();
		if (config==null) return;
		config.setCompany(this.pCompany.getValue());
		config.insert();
	}

	protected void assingAdminWedData(BizWebUserProfile oWebUserProfile, BizUsuario user) throws Exception {
		oWebUserProfile.setUsuario(user.GetUsuario());
		oWebUserProfile.setHomePage("pss.bsp.GuiModuloBSP_10");
		oWebUserProfile.setMaxMatrix(10000);
		oWebUserProfile.setDefaultPagesize(10);
	}
	
	protected void assingSuperWedData(BizWebUserProfile oWebUserProfile, BizUsuario user) throws Exception {
		oWebUserProfile.setUsuario(user.GetUsuario());
		oWebUserProfile.setHomePage("pss.bsp.GuiModuloBSP_10");
		oWebUserProfile.setMaxMatrix(10000);
		oWebUserProfile.setDefaultPagesize(10);
	}
	
	protected void assingAdminData(BizUsuario user) throws Exception {
		user.SetUsuario("ADMIN."+pCompany.getValue());
		user.SetDescrip("Admin - "+pDescription.getValue());
		user.setCompany(this.pCompany.getValue());
		user.setLenguaje(BizPssConfig.GetDefaultLanguage());
		user.setBirthCountryId(pPais.getValue());
		user.setPais(pPais.getValue());
		user.setCustomMenu("COMPANY_ADMIN");
	}
	protected void assingSuperData(BizUsuario user) throws Exception {
		user.SetUsuario("SUPER."+pCompany.getValue());
		user.SetDescrip("Supervisor - "+pDescription.getValue());
		user.setCompany(this.pCompany.getValue());
		user.setLenguaje(BizPssConfig.GetDefaultLanguage());
		user.setBirthCountryId(pPais.getValue());
		user.setPais(pPais.getValue());
		user.setCustomMenu(null);
	}
	
	private void createRoles() throws Exception {
		if (getObjModelCompany().getObjSegConfig()==null) return;
		JRecords<BizRol> r1=getObjModelCompany().getObjSegConfig().getRolesJerarquicos();
		while (r1.nextRecord()) {
			BizRol r=r1.getRecord();
			r.setCompany(pCompany.getValue());
			r.insert();
		}

		JRecords<BizRol> r2=getObjModelCompany().getObjSegConfig().getRolesAplicacion();
		while (r2.nextRecord()) {
			BizRol r=r2.getRecord();
			r.setCompany(pCompany.getValue());
			r.insert();
		}

		JRecords<BizRol> r3=getObjModelCompany().getObjSegConfig().getRolesJerarquicos();
		while (r3.nextRecord()) {
			BizRol r=r3.getRecord();
			JRecords<BizRolVinculado> r4=r.getRolesVinculados1();
			while (r4.nextRecord()) {
				BizRolVinculado v = r4.getRecord();
				v.setCompany(pCompany.getValue());
				v.insert();
			}
		}

		JRecords<BizOperacion> r5=getObjModelCompany().getObjSegConfig().getOperaciones();
		while (r5.nextRecord()) {
			BizOperacion r=r5.getRecord();
			r.setCompany(pCompany.getValue());
			r.insert();
		}

		JRecords<BizRol> r6=getObjModelCompany().getObjSegConfig().getRolesAplicacion();
		while (r6.nextRecord()) {
			BizRol r=r6.getRecord();
			JRecords<BizOperacionRol> r7=r.getOperacionRoles();
			while (r7.nextRecord()) {
				BizOperacionRol o = r7.getRecord();
				o.setCompany(pCompany.getValue());
				o.insert();
			}
		}
	}
	

}
