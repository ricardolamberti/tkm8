package pss.common.customDashboard;

import pss.common.customDashboard.dynamic.GuiDynamicDash;
import pss.common.security.BizUsuario;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public abstract class DashBoardModule {

	public static final String MODULO_VENTAS = "TVENTAS";
	public static final String MODULO_STOCK = "STOCK";
	
	private GuiDashBoard winDash;
	public GuiDashBoard getObjWinDash() throws Exception {
		if (this.winDash!=null) return this.winDash;
		return (this.winDash=this.getWinDashboard());
	}

	public abstract GuiDashBoard getWinDashboard() throws Exception;
	public abstract String getModuleId() throws Exception;
	public abstract String getModuleName() throws Exception;
	protected abstract void initDescrip() throws Exception;
	
	public static DashBoardModule findModulo(String mod) throws Exception {
		return DashBoardModule.getModuloMap().getElement(mod);
	}	
	private static JMap<String, DashBoardModule> moduleMap = null;
	public static JMap<String, DashBoardModule> getModuloMap() throws Exception {
		if (moduleMap!=null) return moduleMap;
		JMap<String, DashBoardModule> map = JCollectionFactory.createOrderedMap();
		JIterator<String> iter = DashBoardModule.getAllModulos().getValueIterator();
		while (iter.hasMoreElements()) {
			String module = iter.nextElement();
			try {
				DashBoardModule m = (DashBoardModule)Class.forName(module).newInstance();
				map.addElement(m.getModuleId(), m);
			} catch (Exception e) {
				continue;
			}
		}
		return (moduleMap=map);
	}
		
	public static JMap<String, String> getAllModulos() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(MODULO_STOCK, " pss.erp.stock.dashboard.DashBoardStockModule");
		map.addElement(MODULO_VENTAS, "pss.erp.stock.dashboard.DashBoardStockModule");
		return map;
	}
	
//	public static String findClass(String mod) throws Exception {
//		if (mod.equals(MODULO_VENTAS)) return "pss.erp.transactions.dashboard.DashBoardVentaModule";
//		if (mod.equals(MODULO_TOURISM)) return "pss.tourism.dashboard.DashBoardTourismModule";
//		if (mod.equals(MODULO_AIR)) return "pss.tourism.products.air.dashboard.DashBoardAirModule";
//		return null;
//	}
	
	
	private static JMap<Integer, DashBoardInfo> mDashMap = null;
	public static JMap<Integer, DashBoardInfo> getDashMap() throws Exception {
		if (mDashMap== null) {
			mDashMap= JCollectionFactory.createOrderedMap();
			JIterator<DashBoardModule> iter = DashBoardModule.getModuloMap().getValueIterator();
			while (iter.hasMoreElements()) {
				DashBoardModule m = iter.nextElement();
				m.initDescrip();
			}
		}
		return mDashMap;
	}
	

//	private static DashBoardModule initModules() throws Exception {
//		JIterator<String> iter = DashBoardModule.getModuloMap().getValueIterator();
//		while (iter.hasMoreElements()) {
//			String module = iter.nextElement();
//			try {
//				DashBoardModule m = (DashBoardModule)Class.forName(module).newInstance();
//				m.initDescrip();
//				map.addElement(m.getModuleId(), m);
//			} catch (Exception e) {
//				return null;
//			}
//		}
//		return null;
//	}	
	
//	public static GuiDashElements getDataElements() throws Exception {
//		GuiDashElements wins = new GuiDashElements();
//		JIterator<DashBoardData> iter= DashBoardModule.getDashMap().getValueIterator();
//		while (iter.hasMoreElements()) {
//			DashBoardData d = iter.nextElement();
//			BizDashElement e = new BizDashElement();
//			e.setId(d.getId());
//			e.setDescription(d.getDescrip());
//			e.setModule(d.getModule());
//			wins.getRecords().addItem(e);
//		}
//		wins.getRecords().setStatic(true);
//		return wins;
//	}
//	
	public static DashBoardInfo findInfo(int id) throws Exception {
		return DashBoardModule.getDashMap().getElement(id);
	}
		
	protected void addDashElement(int id,  DashBoardInfo data ) throws Exception {
		if (DashBoardModule.getDashMap().containsKey(id))
			JExcepcion.SendError("Ya existe Id Dashboard");
		data.setId(id);
		data.setModule(this);
		DashBoardModule.getDashMap().addElement(id, data);
	}
	
	public static GuiDynamicDash makeMyConfigDashBoard() throws Exception {
		// El BizDashboard es un record dinamico que junta todos los modulos del la lista de configs
		GuiDynamicDash dash = new GuiDynamicDash();
		dash.GetcDato().setCompany(BizUsuario.getUsr().getCompany());
		dash.GetcDato().setPais(BizUsuario.getUsr().getCountry());
		dash.GetcDato().setUserId(BizUsuario.getUsr().GetUsuario());
//		dash.makeDinamyc();
		return dash;
	}


//	
//	public void initConfig() throws Exception {
//		initDescrip();
//		clearFilters();
//		addFilter("company", BizUsuario.getUsr().getCompany());
//		addFilter("userid", BizUsuario.getUsr().GetUsuario());
//		long count = selectCount();
//		if (count > 0)
//			return;
//		
//		JExec oExec = new JExec(null, null) {
//			@Override
//			public void Do() throws Exception {
//				int count=1;
//				for (int i = 1; i <= mDashDescrips.size(); i++) {
//					count = insertDashBoardConfig(i, count);
//				}
//			}
//		};
//		oExec.execute();
//	
//	}
//
//	private int insertDashBoardConfig(int idx, int count) throws Exception {
//		if (hasToAddCurrentIdx( idx)==false)
//			return count;
//
//		DashBoardModule cfg = new DashBoardModule();
//		cfg.setCompany(BizUsuario.getUsr().getCompany());
//		cfg.setUserid(BizUsuario.getUsr().GetUsuario());
//		cfg.setDashOrder(count++);
//		cfg.setDashDescription(idx);
//		cfg.setExcluded(false);
//		cfg.insert();
//		
//		return count;
//	}
//
//	protected boolean hasToAddCurrentIdx(int idx)  throws Exception {
//	
//		return true;
//	}
//		


}
