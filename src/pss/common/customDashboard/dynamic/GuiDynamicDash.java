package pss.common.customDashboard.dynamic;

import pss.common.customDashboard.DashBoardInfo;
import pss.common.customDashboard.DashBoardModule;
import pss.common.customDashboard.component.GuiDashComponent;
import pss.common.customDashboard.config.BizDashBoardConfig;
import pss.common.customDashboard.config.GuiDashBoardConfig;
import pss.common.customDashboard.config.GuiDashBoardConfigs;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiDynamicDash extends JWin {

  
  public GuiDynamicDash() throws Exception {
  }
  
  
  @Override
  public String GetTitle() throws Exception {
  	return "DashBoard";
  }
  
  public JRecord ObtenerDato()   throws Exception { return new BizDynamicDash(); }
  public int GetNroIcono()   throws Exception { return 5012; }
  @Override
  public Class<? extends JBaseForm> getFormBase() throws Exception {
  	return FormDynamicDash.class;
  }

  public BizDynamicDash GetcDato() throws Exception {
  	return (BizDynamicDash) this.getRecord();
  }

  public boolean forceCleanHistory() throws Exception {
		return false;
	}

  public void createActionMap() throws Exception {
  	this.addAction(10, "Configurar", null, 10035, true, true);

  	// se ponene todos para que se pueda configurar por seguridad
  	JIterator<String> iter = DashBoardModule.getModuloMap().getKeyIterator();
  	while (iter.hasMoreElements()) {
  		String modulo = iter.nextElement();
    	this.addAction("MOD-"+modulo, DashBoardModule.findModulo(modulo).getModuleName(), null, 6078, true, true);
  	}
  	
  	JIterator<BizDashBoardConfig> iter1 = this.GetcDato().getConfigs().getStaticIterator();
  	while (iter1.hasMoreElements()) {
  		BizDashBoardConfig cfg = iter1.nextElement();
    	this.addAction("FORM-"+cfg.getId(), cfg.findInfo().getDescrip(), null, 1, false, false);
  	}
  	
  	JIterator<DashBoardInfo> iter2 = DashBoardModule.getDashMap().getValueIterator();
  	while (iter2.hasMoreElements()) {
  		DashBoardInfo data = iter2.nextElement();
    	this.addAction("DASH-"+data.getId(), data.getDescrip(), null, data.isImage()?5012:6028, true, true);
  	}
//  	
//  	JIterator<BizDashBoardConfig> iter3 = this.GetcDato().getConfigs().getStaticIterator();
//  	while (iter3.hasMoreElements()) {
//  		BizDashBoardConfig cf = iter3.nextElement();
//  		JIterator<BizAction> iter4 = DashBoardModule.findInfo(cf.getDashCode()).getObjModule().getObjWinDash().getActionMap().getStaticIterator();
//  		while (iter4.hasMoreElements()) {
//  			BizAction a = iter4.nextElement();
//  			this.addAction("DETAIL-"+cf.getId()+"-"+a.getId(), a.GetDescr(), null, a.GetNroIcono(), false, false);
//  		}
//  	}

  	this.addAction(20, "Volver", null, GuiIcon.RETURN_ICON, true, true);
  }
  
  @Override
  public boolean hasBackAction() throws Exception {
  	return this.currentModule==null;
  }
  
  private String currentModule=null;
  public boolean isInModule() throws Exception {
  	return this.currentModule!=null;
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getIdAction().startsWith("DASH-")) 
  		return DashBoardModule.findInfo(Integer.parseInt(a.getIdAction().substring(5))).getModuleId().equals(this.currentModule);
  	if (a.getIdAction().startsWith("MOD-")) 
  		return this.currentModule==null;
  	if (a.getIdAction().startsWith("FORM-")) 
  		return true;
//  	if (a.getIdAction().startsWith("DETAIL-")) 
//  		return true;
  	if (a.getId()==10) return this.currentModule==null;
  	if (a.getId()==20) return this.currentModule!=null;
  	return super.OkAction(a);
  }
  
  @Override
  public JAct getSubmitFor(final BizAction a) throws Exception {

  	if (a.getIdAction().startsWith("MOD-")) return new JActSubmit(this) {
  		public void submit() throws Exception {
  			currentModule=a.getIdAction().substring(4);
  		}
		};
  
  	if (a.getIdAction().startsWith("FORM-"))
  		return this.getFormAction(a.getIdAction());

  	if (a.getIdAction().startsWith("DASH-"))
  		return this.getDashAction(a.getIdAction());
//
//  	if (a.getIdAction().startsWith("DETAIL-"))
//  		return this.getDetailAction(a.getIdAction());

  	if (a.getId()==10) return new JActWins(this.getDashBoardConfigs());
  	if (a.getId()==20) return new JActSubmit(this) {
  		public void submit() throws Exception {
  			currentModule=null;
  		}
  	};

  	return super.getSubmitFor(a);
  }
  
  private JActQuery getFormAction(String sDash) throws Exception {
  	int id = Integer.parseInt(sDash.substring(5));
  	BizDashBoardConfig cfg = new BizDashBoardConfig();
  	cfg.read(id);
  	GuiDashComponent c = new GuiDashComponent();
  	c.GetcDato().setConfig(cfg);
  	return new JActQuery(c);
  }

  private JAct getDashAction(String sDash) throws Exception {
  	int dash = Integer.parseInt(sDash.substring(5));
//  	DashBoardModule.findData(dash);
  	GuiDashBoardConfig cf = new GuiDashBoardConfig();
  	cf.GetcDato().setCompany(this.GetcDato().getCompany());
  	cf.GetcDato().setPais(this.GetcDato().getPais());
  	cf.GetcDato().setUserid(this.GetcDato().getUserId());
  	cf.GetcDato().setDashCode(dash);
  	cf.setDropListener(this);
  	return new JActNew(cf, 4);
  }

  
  @Override
  public JAct Drop(JBaseWin baseWin) throws Exception {
  	if (baseWin instanceof GuiDashBoardConfig) {
  		((GuiDashBoardConfig) baseWin).GetcDato().execProcessInsert();
  		this.currentModule=null;
  	}
  	return null;
  }
  

//  private JMap<Long, BizDashBoardConfig> configFilled;
//  public JMap<Long, BizDashBoardConfig> getConfigMap() throws Exception {
//  	if (this.configFilled==null) this.configFilled=JCollectionFactory.createOrderedMap();
//  	return this.configFilled;
//  }
  
//  private boolean hasModule(String module) throws Exception {
//  	JIterator<BizDashBoardConfig> iter = this.getConfigMap().getValueIterator();
//  	while (iter.hasMoreElements()) {
//  		BizDashBoardConfig cf = iter.nextElement();
//  		if (cf.findData().getObjModule().getModuleId().equals(module))
//  			return true;
//  	}
//  	return false;
//  }
  	
//	public void makeDinamyc() throws Exception {
//		JIterator<DashBoardModule> iter = DashBoardModule.getModuloMap().getValueIterator();
//		while (iter.hasMoreElements()) {
//			DashBoardModule mod = iter.nextElement();
//			this.appendModulo(mod);
//		}
//	}
  
  	
//  public void appendModulo(DashBoardModule mod) throws Exception {
//  	
//  	GuiDashBoard db = mod.getObjWinDash();
//  	// meto todas las properties
//  	JIterator<JProperty> iter = db.getRecord().getFixedProperties().getValueIterator();
//  	while (iter.hasMoreElements()) {
//  		JProperty prop = iter.nextElement();
//  		String campo = mod.getModuleId()+"-"+prop.GetCampo();
//  		this.getRecord().addFixedItem(prop.getTipo(), campo, prop.GetDescripcion(), false, false, prop.getSize());
//  	}
//
//  }

  public JWins getDashBoardConfigs() throws Exception {
  	GuiDashBoardConfigs wins = new GuiDashBoardConfigs();
  	wins.getRecords().addFilter("company", this.GetcDato().getCompany());
  	wins.getRecords().addFilter("pais", this.GetcDato().getPais());
  	wins.getRecords().addFilter("userid", this.GetcDato().getUserId());
  	wins.getRecords().addOrderBy("dash_order");
  	return wins;
  }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  
}
