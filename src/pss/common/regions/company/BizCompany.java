package pss.common.regions.company;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;

import pss.JPath;
import pss.common.components.JSetupParameters;
import pss.common.customList.config.carpetas.BizCarpeta;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.divitions.BizPais;
import pss.common.regions.nodes.BizNodo;
import pss.common.security.BizOperacion;
import pss.common.security.BizOperacionRol;
import pss.common.security.BizRol;
import pss.common.security.BizRolVinculado;
import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.common.security.BizUsuarioRol;
import pss.core.connectivity.messageManager.common.core.JMMRecord;
import pss.core.data.BizPssConfig;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizCompany extends JMMRecord {

	public static final String DEFAULT_COMPANY="DEFAULT";
	public static final String DEFAULT_USER="DEFAULT";
	

	private JString pCompany=new JString();
	private JString pDescription=new JString();
	private JString pBusiness=new JString();
	private JString pTaxId=new JString();
	private JBoolean pMultinacional=new JBoolean();
	private JBoolean pUseWebService = new JBoolean();
	private JBoolean pUseMobile = new JBoolean();
	private JBoolean pUseWhiteList = new JBoolean();
	protected JBoolean pIsInProduction = new JBoolean();
	protected JDate pStartDate = new JDate();
	protected JLong pPaymentPeriod = new JLong();
	private JString pCurrency = new JString();
	protected JFloat pAmount = new JFloat();
	protected JLong pQuantity = new JLong();

	public JMap<String, BizNodo> hNodos=null;
	private JString pParentCompany=new JString();
  private JString pLogoImg = new JString() {
  	public void preset() throws Exception {
  		setValue(JPath.PssPathLogos()+getLogo());
  	};
  };
  
  private JHtml pLogoIncr = new JHtml() {
  	public void preset() throws Exception {
  		setValue(getLogoIncrustado());
  	};
  };
  private JString pLogoProd = new JString() {
  	public void preset() throws Exception {
  		setValue(getLogoProductoIncrustado());
  	};
  };
  private JString pLogoManuf = new JString() {
  	public void preset() throws Exception {
  		setValue(getLogoFabricanteIncrustado());
  	};
  };
  
	public boolean isInProduction() throws Exception {
		return pIsInProduction.getValue();
	}

	public void setUseMobile(boolean val) {
		this.pUseMobile.setValue(val);
	}

	public boolean isUseMobile() throws Exception {
		return pUseMobile.getValue();
	}
	
	public void setUseWhiteList(boolean val) {
		this.pUseWhiteList.setValue(val);
	}

	public boolean hasWhiteList() throws Exception {
		return pUseWhiteList.getValue();
	}


	public void setUseWebService(boolean val) {
		this.pUseWebService.setValue(val);
	}

	public boolean isUseWebService() throws Exception {
		return pUseWebService.getValue();
	}

	public void setIsInProduction(boolean pIsInProduction) {
		this.pIsInProduction.setValue(pIsInProduction);
	}

	public Date getStartDate() throws Exception {
		return pStartDate.getValue();
	}

	public void setStartDate(Date pStartDate) {
		this.pStartDate.setValue(pStartDate);
	}

	public long getPaymentPeriod() throws Exception {
		return pPaymentPeriod.getValue();
	}

	public void setPaymentPeriod(long pPaymentPeriod) {
		this.pPaymentPeriod.setValue(pPaymentPeriod);
	}

	public long getMaxEvents() throws Exception {
		return maxevents.getValue();
	}

	public void setMaxEvents(long val) {
		this.maxevents.setValue(val);
	}

	public double getAmount() throws Exception {
		return pAmount.getValue();
	}

	public void setAmount(double pAmount) {
		this.pAmount.setValue(pAmount);
	}

	public long getQuantity() throws Exception {
		return pQuantity.getValue();
	}

	public void setQuantity(long pQuantity) {
		this.pQuantity.setValue(pQuantity);
	}

	public void setCurrency(String value) throws Exception {
		pCurrency.setValue(value);
	}

	public String getCurrency() throws Exception {
		return pCurrency.getValue();
	}



	protected JCurrency pTotalAmount = new JCurrency() {
		public void preset() throws Exception {
			pTotalAmount.setValue(pQuantity.getValue() * pAmount.getValue());
		}

	};

	protected JString pDescrip2 = new JString() {
		public void preset() throws Exception {
			pDescrip2.setValue(pCompany.getValue() + "-"
					+ pDescription.getValue());
		}

	};
	

  
	public String getLogoIncrustado() throws Exception {
		
    if (getLogo().equals("")) return "";
    File file = new File(JPath.PssPathData()+"/"+pLogoImg.getValue());
    if (!file.exists()) return "";
    BufferedImage bufferedImage = ImageIO.read(file);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage,"jpg", baos);
    return "html:<img src=\"data:image/jpg;base64,"+(java.util.Base64.getEncoder().encodeToString(baos.toByteArray()).replaceAll("\r\n", "")+"\"/>");
	}
	public String getLogoProductoIncrustado() throws Exception {
	   File file = new File(JPath.PssPathData()+"/files/producto.jpg");
	    if (!file.exists()) return "";
	  BufferedImage bufferedImage = ImageIO.read(file);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage,"jpg", baos);
    return "html:<img src=\"data:image/jpg;base64,"+(java.util.Base64.getEncoder().encodeToString(baos.toByteArray()).replaceAll("\r\n", "")+"\"/>");
	}

	public String getLogoFabricanteIncrustado() throws Exception {
		File file = new File(JPath.PssPathData()+"/files/manufacturer.jpg");
		if (!file.exists()) return"";
	  BufferedImage bufferedImage = ImageIO.read(file);
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
	  ImageIO.write(bufferedImage,"jpg", baos);
	  return "html:<img src=\"data:image/jpg;base64,"+(java.util.Base64.getEncoder().encodeToString(baos.toByteArray()).replaceAll("\r\n", "")+"\"/>");
	}
	public void setParentCompany(String value) throws Exception {
		pParentCompany.setValue(value);
	}

	public String getParentCompany() throws Exception {
		return pParentCompany.getValue();
	}
	public boolean hasParentCompany() throws Exception {
		return pParentCompany.isNotNull();
	}
	private static JMap<String, BizCompany> mCompany = JCollectionFactory.createMap(10);

	public static BizCompany getObjCompany(String company) throws Exception {
		return getObjCompany(null,company);
	}
	public static void cleanCompany(String company) throws Exception {
	mCompany.removeElement(company);
	}
	
	public static BizCompany getObjCompany(BizCompany cpy, String company) throws Exception {
		BizCompany val = mCompany.getElement(company);
		if (val == null) {
			if ( cpy == null)
			  val = new BizCompany();
			else
				val = cpy;
			val.Read(company);
			mCompany.addElement(company, val);
		}
		return val;
	}
	
	public static String getCompanyDesc(JString val) throws Exception {
		if (val.isEmpty())
			return "sin definir";
		return BizCompany.getObjCompany(val.getValue()).getDescription();
	}
	
	/**
	 * @return the pTaxId
	 */
	public String getTaxId() throws Exception {
		return pTaxId.getValue();
	}
	/**
	 * @param taxId the pTaxId to set
	 */
	public void setTaxId(String taxId) {
		pTaxId.setValue(taxId);
	}
	
	public static JMap<String, BizCompany> getCompanyMap() {
		return mCompany;
	}

	private JString pLogo=new JString();
	private JString pLink=new JString();
	private JString pManufacturerLogo=new JString();
	
	
	private transient JCompanyBusiness oBusiness=null;
	private JLong maxevents = new JLong();
	private JBoolean limitedHistory = new JBoolean();

	public boolean getLimitedHistory() throws Exception {
		return limitedHistory.getValue();
	}

	public void setLimitedHistory(boolean limitedHistory) {
		this.limitedHistory.setValue( limitedHistory );
	}

	public void setCompany(String value) throws Exception {
		pCompany.setValue(value);
	}
	public void setManufacturerLogo(String value) throws Exception {
		pManufacturerLogo.setValue(value);
	}

	public String getManufacturerLogo() throws Exception {
		return pManufacturerLogo.getValue();
	}
	
	public void setLogo(String value) throws Exception {
		pLogo.setValue(value);
	}

	public String getLogo() throws Exception {
		return pLogo.getValue();
	}
	
	public void setLink(String value) throws Exception {
		pLink.setValue(value);
	}

	public String getLink() throws Exception {
		return pLink.getValue();
	}

	public void setBusiness(String value) throws Exception {
		pBusiness.setValue(value);
	}

	public String getBusiness() throws Exception {
		return pBusiness.getValue();
	}

	public void setDescription(String value) throws Exception {
		pDescription.setValue(value);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public String getDescription() throws Exception {
		return pDescription.getValue();
	}

	public boolean isMultinacional() throws Exception {
		return pMultinacional.getValue();
	}

	public BizCompany() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("description", pDescription);
		this.addItem("business", pBusiness);
		this.addItem("taxid", pTaxId);
		this.addItem("logo", pLogo);
		this.addItem("manufacturer_logo", pManufacturerLogo);
		this.addItem("link", pLink);
		this.addItem("multinacional", pMultinacional);
		this.addItem("parent_company", pParentCompany);
	    this.addItem( "logo_img", pLogoImg );
	    this.addItem( "logo_inc", pLogoIncr );
	    this.addItem( "logo_mnf", pLogoManuf );
	    this.addItem( "logo_prd", pLogoProd );
		this.addItem("is_production", pIsInProduction);
		this.addItem("start_date", pStartDate);
		this.addItem("payment_period", pPaymentPeriod);
		this.addItem("payment_currency", pCurrency);
		this.addItem("amount", pAmount);
		this.addItem("quantity", pQuantity);
		this.addItem("descrip_completa", pDescrip2);

	    
		this.addItem("max_events", maxevents );
		this.addItem("use_webservice", pUseWebService );
		this.addItem("use_mobile", pUseMobile );
		this.addItem("use_whitelist", pUseWhiteList );
		
		this.addItem("total_amount", pTotalAmount);
		this.addItem("limited_history", limitedHistory);


	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Empresa", true, true, 15);
		this.addFixedItem(FIELD, "description", "Descripción", true, true, 150);
		this.addFixedItem(FIELD, "business", "Negocio", true, true, 15);
		this.addFixedItem(FIELD, "taxid", "Id Tributario", true, false, 30);
		this.addFixedItem(FIELD, "logo", "Logo", true, false, 250);
		this.addFixedItem(FIELD, "manufacturer_logo", "Logo Desarrollador", true, false, 250);
		this.addFixedItem(FIELD, "link", "link", true, false, 250);
		this.addFixedItem(FIELD, "multinacional", "Multinacional", true, false, 1);
		this.addFixedItem(FIELD, "parent_company", "Compania generadora", true, false, 15);
		this.addFixedItem(FIELD, "use_mobile", "Usa Movil?", true, false, 1);
		this.addFixedItem(FIELD, "use_webservice", "Usa WebService?", true, false, 1);
		this.addFixedItem(FIELD, "use_whitelist", "Lista Blanca?", true, false, 1);

		this.addFixedItem(FIELD, "is_production", "Produccion?", true, false, 250);
		this.addFixedItem(FIELD, "start_date", "Fecha Inicio", true, false, 250);
		this.addFixedItem(FIELD, "payment_period", "Periodo Pago", true, false, 250);
		this.addFixedItem(FIELD, "payment_currency", "Moneda Pago", true, false, 250);
		this.addFixedItem(FIELD, "amount", "Monto Servicio", true, false, 18, 2);
		this.addFixedItem(FIELD, "quantity", "Cantidad", true, false, 4);
		this.addFixedItem(FIELD, "max_events", "Max.Eventos", true, false, 4);
		this.addFixedItem(FIELD, "limited_history", "limited_history", true, false, 1);
		this.addFixedItem(VIRTUAL, "total_amount", "Monto Total", true, false, 18, 2);
		this.addFixedItem(VIRTUAL, "descrip_completa", "Descripcion", true, false, 300);
		this.addFixedItem(VIRTUAL, "logo_img", "Logo", true, false, 150);
		this.addFixedItem(VIRTUAL, "logo_inc", "Logo Incrustado", true, false, 4000);
		this.addFixedItem(VIRTUAL, "logo_mnf", "Logo manufactura", true, false, 4000);
		this.addFixedItem(VIRTUAL, "logo_prd", "Logo producto", true, false, 4000);

	}

	@Override
	public String GetTable() {
		return "NOD_COMPANY";
	}

	public boolean Read(String zCompany) throws Exception {
		this.addFilter("company", zCompany);
		return this.read();
	}

	public static String GetDescripcionReporte(String zCompany) throws Exception {
		String sDesc="";
		sDesc="Compañía: ";
		if (zCompany.equals("")) sDesc+="< Todas >";
		else {
			BizCompany oCompany=new BizCompany();
			oCompany.Read(zCompany);
			sDesc+=zCompany+" "+oCompany.getDescription();
		}
		return sDesc;
	}
	
	@Override
	public void processInsert() throws Exception {
		super.processInsert();
	}

	@Override
	public void processDelete() throws Exception {
		if (getNodos().nextRecord()) 
			JExcepcion.SendError("La empresa tiene nodos asociados");

		this.getUsers().processDeleteAll();
		BizSegConfiguracion c = this.getObjSegConfig();
		if (c!=null) c.processDelete();
		super.processDelete();
		this.refreshCache();
	}
	
	public BizSegConfiguracion getObjSegConfig() throws Exception {
		BizSegConfiguracion c = new BizSegConfiguracion();
		c.dontThrowException(true);
		if (!c.Read(this.pCompany.getValue()))
			return null;
		return c;
	}
	

	public JRecords<BizNodo> getNodos() throws Exception {
		JRecords<BizNodo> oNodos=new JRecords<BizNodo>(BizNodo.class);
		oNodos.addFilter("company", getCompany());
		oNodos.readAll();
		return oNodos;
	}

	public JRecords<BizUsuario> getUsers() throws Exception {
		JRecords<BizUsuario> oUsers=new JRecords<BizUsuario>(BizUsuario.class);
		oUsers.addFilter("company", getCompany());
		oUsers.readAll();
		return oUsers;
	}


	@Override
	public void validateConstraints() throws Exception {
		if (pCompany.getValue().indexOf(" ")>-1) JExcepcion.SendError("El ID de la empresa no puede tener espacios en blanco");
	}

	public JCompanyBusiness getObjBusiness() throws Exception {
		if (oBusiness!=null) return this.oBusiness;
		JCompanyBusiness oBusiness=JCompanyBusinessModules.getInstanceFor(pBusiness.getValue());
		return (this.oBusiness=oBusiness);
	}

	public static BizCompany VirtualCreateType(String zTipo) throws Exception {
		return JCompanyBusinessModules.getInstanceFor(zTipo).getInstance();
	}

  @Override
  protected void setupConfig(JSetupParameters params) throws Exception {
  	if (params.getDataModel().equals("jud")) {
  		params.setExportData(true);
  	}
  	super.setupConfig(params);
  }

//  public static BizCompany readCompany(String company) throws Exception {//		BizCompany c = new BizCompany();//		c.dontThrowException(true);//		if (c.Read(company)) //			return c;//		if (company.equals(DEFAULT_COMPANY)) //			return BizCompany.createDefaultCompany();////		JExcepcion.SendError("Empresa inexistente:^"+company);//		return null;//  }  
//  private static BizCompany createDefaultCompany() throws Exception {
//  	// ver si recrear siempre con los datos del ini
//  	BizCompany c = new BizCompany();
//  	c.setCompany(DEFAULT_COMPANY);
//  	c.setDescription("Default");
//  	c.setBusiness(BizPssConfig.getPssConfig().getBusinessDefault());
//  	c.setLogo(BizPssConfig.getPssConfig().getLogo());
//  	c.setLink(BizPssConfig.getPssConfig().getLink());
//  	c.processInsert();
//  	return c;
//  }

	private static JMap<String, BizCompany> hCache=null;

  public static BizCompany getCompany(String company) throws Exception {
		if (hCache == null) hCache = JCollectionFactory.createMap();
		BizCompany c = hCache.getElement(company);
		if (c!=null) return c;
		c = new BizCompany();
		c.Read(company);
		BizCompany cc = BizCompany.VirtualCreateType(c.getBusiness());
		cc.Read(c.getCompany());
		hCache.addElement(cc.getCompany(), cc);
		return cc;
  }
  
//	private static BizCompany getCompanyObj(String company) throws Exception {
//		BizCompany cpy = new BizCompany();
//		cpy.Read(company);
//		getCompanyCache().addElement(company, cpy);
//		return cpy;
//	}
//	public static synchronized JMap<String, BizCompany> getCompanyCacheEmpty() throws Exception {
//		if (hCache != null)
//			return hCache;
//		return hCache=JCollectionFactory.createMap();
//	}


	protected void setDefaultLogo() throws Exception {	  
		setLogo(BizPssConfig.getPssConfig().getLogo());	
	}  

  public BizNodo findNodo(String nodo) throws Exception {  	
  	return this.getNodosMap().getElement(nodo);	
  }
		
	public JMap<String, BizNodo> getNodosMap() throws Exception {
  	if (this.hNodos!=null) return this.hNodos;
  	JMap<String, BizNodo> h = JCollectionFactory.createMap();
  	JIterator<BizNodo> iter = this.getNodos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizNodo n = iter.nextElement();
  		h.addElement(n.GetNodo(), n);
  	}
  	return (this.hNodos=h);
  }
  
	public void validateRecord() throws Exception {
//		processLogoFile();
		super.validateRecord();
	}
	
	public void processUpdate() throws Exception {
		super.processUpdate();
		this.refreshCache();
	}

	
	private void refreshCache() throws Exception {
	  if (hCache==null) return;
	  hCache.removeElement(this.getCompany());
	 }
	
	
	public static String getLogoPath() throws Exception {		
		String strDestRootDir = JPath.PssPathData();
		if (!strDestRootDir.endsWith("/")) strDestRootDir+="/";		
		String strClassDestDir = strDestRootDir + JPath.PssPathLogos()+BizUsuario.getUsr().getCompany()+"/";
		File oDestFile = new File(strClassDestDir);
		if (oDestFile.exists() == false) {
			oDestFile.mkdirs();
		}
		return strClassDestDir;
	}
	
	public void processLogoFile() throws Exception {
		if ( this.pLogo.isEstablecida() == false ) return ;
		if (this.pLogo.isEmpty())	{			
			setDefaultLogo();			
			return;		
		}				
		String strSourceFileName = this.pLogo.getValue().replace("%3A", ":");				
		File oSourceFile = new File(strSourceFileName);				
		// Get source File Name		
		String strDestFileName = oSourceFile.getName();		
		int iLastPointIndex = strDestFileName.lastIndexOf('.');		
		strDestFileName = strDestFileName.substring(0, iLastPointIndex);				
		// Build destination Dir		
		String strClassDestDir = BizCompany.getLogoPath();				
		String strFullDestFileName = strClassDestDir + strDestFileName;				
		JTools.copyFile(strSourceFileName, strFullDestFileName);				
		this.pLogo.setValue(BizUsuario.getUsr().getCompany()+"/"+strDestFileName);	
	}  

  public static BizPlantilla getObjPlantilla(String company,String id) throws Exception {
  	if (company==null || company.equals("")) return null;
  	BizCompany cmp = BizCompany.getObjCompany(company);
  	if (cmp==null) return null;
		BizPlantilla p = new BizPlantilla();
		p.dontThrowException(true);
		if (!p.ReadByDescripcion(company, id))
			return  BizCompany.getObjPlantilla(cmp.getParentCompany(),id);
		return p;
  
  }
  public static BizPlantilla getObjPlantillaById(String company,long id) throws Exception {
  	if (company==null || company.equals("")) return null;
  	BizCompany cmp = BizCompany.getObjCompany(company);
  	if (cmp==null) return null;
		BizPlantilla p = new BizPlantilla();
		p.dontThrowException(true);
		if (!p.ReadById(company, id))
			return  BizCompany.getObjPlantillaById(cmp.getParentCompany(),id);
		return p;
  
  }
  public static BizPlantilla getObjPlantillaByIdTipo(String company,String id) throws Exception {
  	if (company==null || company.equals("")) return null;
  	BizCompany cmp = BizCompany.getObjCompany(company);
  	if (cmp==null) return null;
		BizPlantilla p = new BizPlantilla();
		p.dontThrowException(true);
		if (!p.ReadByIdTipo(company, id))
			return  BizCompany.getObjPlantillaByIdTipo(cmp.getParentCompany(),id);
		return p;
  
  }
  
  public BizPais getPaisFromFirstNodo() throws Exception {
  	BizNodo nodo = this.getFirstNodo();
  	if (nodo!=null)
  		return this.getFirstNodo().ObtenerPais();
  	return null;
  }
  
  public BizNodo getFirstNodo() throws Exception {
  	JRecords<BizNodo> nodos = this.getNodos();
  	if (nodos.nextRecord()) {
  		return ((BizNodo)nodos.getRecord());
  	}
  	return null;
  }

  public void  registerNewCompany(JCompanyBusiness business, String sUsername,String sCompany,String sPassword,String sEmail,String pais) throws Exception {
   	BizUsuario.checkEmail(null,sEmail,true);
    
   	BizUsuario provisorio = new BizUsuario();
   	provisorio.setCompany(getCompany());
   	provisorio.SetUsuario("PROVISORIO");
   	provisorio.setLoginSource(sCompany);
   	provisorio.SetDescrip(sUsername);
   	provisorio.setMailUser(sEmail);
   	provisorio.setBirthCountryId(pais);
   	provisorio.SetClave(sPassword);
   	
   	BizUsuario admin = new BizUsuario();
   	admin.Read(BizUsuario.C_ADMIN_USER);
   	
   	BizUsuario.SetGlobal(admin);
   	JTools.sendMail(provisorio.getUsrMailSender(),getCompany(),"sys_email_verify","Verificación de correo",sEmail,provisorio);
   	BizUsuario.SetGlobal(null);
  }
  
  public BizUsuario getAdminUser() throws Exception {
  	BizUsuario u = new BizUsuario();
  	u.Read("ADMIN."+this.getCompany());
  	return u;
  }
  
  public BizUsuario  buildNewCompany(JCompanyBusiness business, String sUsername,String sCompany,String sPassword,String sEmail,String pais) throws Exception {
  
  	BizUsuario.checkEmail(null,sEmail,true);
 
  	BizCompany companyCount = new BizCompany();
  	long cantCompany = companyCount.selectCount();
  	BizUsuario usuarioCount = new BizUsuario();
  	long cantUser = usuarioCount.selectCount();

  	String newCompanyName = "RC_"+cantCompany;
  	String newUserName = "RU_"+cantUser+"."+newCompanyName;
  	
  	BizCompany newCompany = business.getInstance();
  	newCompany.setCompany(newCompanyName);
  	newCompany.setParentCompany(this.getCompany());
  	newCompany.setBusiness(this.getBusiness());
  	newCompany.setDescription(sCompany);
  	newCompany.processInsert();
  	
  	cloneTableCompany(newCompanyName,newUserName);
  	BizUsuario usrToLogin = cloneUsers(business, newCompanyName, newUserName, sUsername, sCompany, sPassword, sEmail, pais);
  	
  	return usrToLogin;

  }
  public BizUsuario  cloneUsers(JCompanyBusiness business,String newCompanyName,String newUserName,String sUsername,String sCompany,String sPassword,String sEmail,String pais) throws Exception {
  	addUser("admin"+"."+newCompanyName,"Admin - "+sCompany,null,"Nhrm7167",pais,newCompanyName,null);
  	return addUser(newUserName,sUsername,sEmail,sPassword,pais,newCompanyName,business.getTipoUsuarioDefault());
  }
  public void  cloneTableCompany(String newCompanyName,String newUserName) throws Exception {
  	cloneTableForNewCompany(BizSegConfiguracion.class,newCompanyName,null,null);
  	cloneTableForNewCompany(BizRol.class,newCompanyName,null,null);
  	cloneTableForNewCompany(BizOperacionRol.class,newCompanyName,null,null);
  	cloneTableForNewCompany(BizRolVinculado.class,newCompanyName,null,null);
  	cloneTableForNewCompany(BizOperacion.class,newCompanyName,null,null);
//  	cloneTableForNewCompany(BizUsuarioTipo.class,newCompanyName,null,null);
  	cloneTableForNewCompany(BizCarpeta.class,newCompanyName,null,null);
  	cloneTableForNewCompany(BizCustomList.class,newCompanyName,null,null);
//  	cloneTableForNewCompany(BizPlantilla.class,newCompanyName,null,null);
   	cloneTableForNewCompany(BizCampo.class,newCompanyName,null,null);
  }
  
  public void cloneTableForNewCompany(Class record,String newCompanyName,String fieldkey, String value) throws Exception {
  	JRecords roles = new JRecords(record);
  	roles.addFilter("company", getCompany());
  	JIterator<JRecord> it = roles.getStaticIterator();
  	while (it.hasMoreElements()) {
  		JRecord rec = it.nextElement();
  		JRecord newrec = rec.createCopyClone();
  		JObject comp = newrec.getProp("company");
  		if (comp==null) continue;
  		comp.setValue(newCompanyName);
  		if (fieldkey!=null && value==null)
  			newrec.getProp(fieldkey).setNull();
  		if (fieldkey!=null && value!=null)
  			newrec.getProp(fieldkey).setValue(value);
  		newrec.processInsert();
  	}
  }


  public long getDefaultRol() throws Exception {
  	return -1;
  }
	
	protected BizUsuario addUser(String zUser, String name,String email, String password,String pais,String newCompanyName,String tipo) throws Exception {
		BizUsuario user=new BizUsuario();
		user.SetUsuario(zUser.toUpperCase());
		user.SetDescrip(name);
		user.setCompany(newCompanyName);
		user.setLenguaje(BizPssConfig.GetDefaultLanguage());
		user.setMailUser(email);
		user.SetClave(JTools.StringToPassword(user.GetUsuario(),	password));
		user.setBirthCountryId(pais);
		user.setPais(pais);
		user.setCaducidad(0);
		user.SetFechaCambioClave(new Date());
		if (tipo!=null)
			user.SetUsuarioTipo(tipo);
		user.processInsert();
		
		long rol = getDefaultRol();
		if (rol!=-1) {
		BizUsuarioRol usurol = new BizUsuarioRol();
			usurol.SetUsuario( user.GetUsuario());
			usurol.setCompany(user.getCompany());
			usurol.setRol(rol);
			usurol.processInsert();
		}
		return user;
	}
	
	private JMap<String, BizCompanyCountry> paisMap;
	public BizCompanyCountry findPais(String pais) throws Exception {
		if (paisMap==null) paisMap=JCollectionFactory.createMap();
		BizCompanyCountry cc = paisMap.getElement(pais);
		if (cc!=null) return cc;
		cc = new BizCompanyCountry();
		cc.dontThrowException(true);
		if(!cc.read(this.getCompany(), pais)) {
			// --legacy -- pueden no existir los registros 
			cc.setCompany(this.getCompany());
			cc.setCountry(pais); 
		}
		paisMap.addElement(pais, cc);
		return cc;
	}

	public BizCompanyCountry findFirstPais() throws Exception {
		return this.findPais(this.getFirstNodo().GetPais());
	}
	
}
