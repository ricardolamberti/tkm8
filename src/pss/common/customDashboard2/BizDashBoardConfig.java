package pss.common.customDashboard2;

import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.JExec;
import pss.core.services.fields.JString;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizDashBoardConfig extends BizDashBoardConfigBase {

	private static JMap<Integer, DashBoardData> mDashDescrips = JCollectionFactory.createOrderedMap();

	public static void clearDashBoard() throws Exception {
		mDashDescrips = JCollectionFactory.createOrderedMap();
	}

	public JString pStore = new JString();
	public JString pStoreGroup = new JString();
	public JString pDescr = new JString() {
		public void preset() throws Exception {
			pDescr.setValue(getDashBoardDescrip((int) pDashDescription.getValue()));
		}
	};

	public String getStore() throws Exception {
		return pStore.getValue();
	}

	public String getGroupStore() throws Exception {
		return pStoreGroup.getValue();
	}

	public DashBoardData getData(int key) throws Exception {
		if (mDashDescrips.size() == 0)
			initDescrip();
		return mDashDescrips.getElement(key);
	}

	public boolean isImage(int idx) throws Exception {
		if (mDashDescrips.size() == 0)
			initDescrip();
		return mDashDescrips.getElement((int) idx).isImage();
	}

	public String getDashBoardDescrip(int idx) throws Exception {
		if (mDashDescrips.size() == 0)
			initDescrip();
		return (String) mDashDescrips.getElement(idx).getDescrip();
	}

	public String getDashBoardAction(int idx) throws Exception {
		if (mDashDescrips.size() == 0)
			initDescrip();
		return (String) mDashDescrips.getElement(idx).getActionDescrip();
	}

	public BizDashBoardConfig() throws Exception {

	}

	protected void addDashElement(int id, DashBoardData data) throws Exception {
		mDashDescrips.addElement(id, data);
	}

	protected String getDashBoardName() {
		return "DEFAULT";
	}

	protected void initDescrip() throws Exception {

	}

	public void createProperties() throws Exception {

		super.createProperties();
		addItem("DASH_DESCR", pDescr);
		addItem("DASH_STORE", pStore);
		addItem("DASH_STORE_GROUP", pStoreGroup);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		super.createFixedProperties();
		addFixedItem(VIRTUAL, "DASH_DESCR", "Descripcion", true, true, 500);
		addFixedItem(VIRTUAL, "DASH_STORE", "Estación", true, true, 500);
		addFixedItem(VIRTUAL, "DASH_STORE_GROUP", "Grupo Estación", true, true, 500);
	}

	public void initConfig() throws Exception {
		initDescrip();
		// clearFilters();
		// addFilter("company", BizUsuario.getUsr().getCompany());
		// addFilter("userid", BizUsuario.getUsr().GetUsuario());
		// long count = selectCount();
		// if (count == mDashDescrips.size())
		// return;

		JExec oExec = new JExec(this, "processInitConfig") {
			@Override
			public void Do() throws Exception {
				int count = 1;
				JIterator<Integer> ids = mDashDescrips.getKeyIterator();
				while (ids.hasMoreElements()) {
					int id = ids.nextElement();
					count = insertDashBoardConfig(id, count);
				}
			}
		};
		oExec.execute();

	}

	private int insertDashBoardConfig(int idx, int count) throws Exception {
		if (hasToAddCurrentIdx(idx) == false)
			return count;

		BizDashBoardConfig cfg = new BizDashBoardConfig();
		cfg.addFilter("company", BizUsuario.getUsr().getCompany());
		cfg.addFilter("userid", BizUsuario.getAdminCompanyUser());
		cfg.addFilter("dash_description", idx);
		cfg.addFilter("only_admin", true);
		cfg.dontThrowException(true);
		if (cfg.read()) {
			if (BizUsuario.getUsr().isAnyAdminUser() == false) {
				BizDashBoardConfig cfg2 = new BizDashBoardConfig();
				cfg2.addFilter("company", BizUsuario.getUsr().getCompany());
				cfg2.addFilter("userid", BizUsuario.getUsr().GetUsuario());
				cfg2.addFilter("dash_description", idx);
				cfg2.dontThrowException(true);
				if (cfg2.read()) {
					cfg2.delete();
					this.processReOrderDelete(cfg2);
				}
				
			}
			return count;
		}

		cfg = new BizDashBoardConfig();
		cfg.addFilter("company", BizUsuario.getUsr().getCompany());
		cfg.addFilter("userid", BizUsuario.getUsr().GetUsuario());
		cfg.addFilter("dash_description", idx);
		cfg.dontThrowException(true);
		if (cfg.read())
			return count;

		cfg = new BizDashBoardConfig();
		cfg.addFilter("company", BizUsuario.getUsr().getCompany());
		cfg.addFilter("userid", BizUsuario.getUsr().GetUsuario());
		count = (int) cfg.selectCount() + 1;

		cfg = new BizDashBoardConfig();
		cfg.setCompany(BizUsuario.getUsr().getCompany());
		cfg.setUserid(BizUsuario.getUsr().GetUsuario());
		cfg.setDashName(this.getDashBoardName());
		cfg.setDashOrder(count++);
		cfg.setDashDescription(idx);
		cfg.setExcluded(false);
		cfg.insert();

		return count;
	}

	protected boolean hasToAddCurrentIdx(int idx) throws Exception {

		return true;
	}

	public void execProcessSetAdmin() throws Exception {
		JExec oExec = new JExec(this, "ProcessAdmin") {

			@Override
			public void Do() throws Exception {
				processSetAdmin();
			}
		};
		oExec.execute();
	}

	public void processSetAdmin() throws Exception {
		BizDashBoardConfig oIdent = (BizDashBoardConfig) this.getPreInstance();
		oIdent.setOnlyAdmin(true);

		oIdent.updateRecord();
	}

	public void execProcessUnsetAdmin() throws Exception {
		JExec oExec = new JExec(this, "ProcessUnsetAdmin") {

			@Override
			public void Do() throws Exception {
				processUnsetAdmin();
			}
		};
		oExec.execute();
	}

	public void processUnsetAdmin() throws Exception {
		BizDashBoardConfig oIdent = (BizDashBoardConfig) this.getPreInstance();
		oIdent.setOnlyAdmin(false);

		oIdent.updateRecord();
	}

	public void execProcessExclude() throws Exception {
		JExec oExec = new JExec(this, "ProcesarExclude") {

			@Override
			public void Do() throws Exception {
				processExclude();
			}
		};
		oExec.execute();
	}

	public void processExclude() throws Exception {
		BizDashBoardConfig oIdent = (BizDashBoardConfig) this.getPreInstance();
		oIdent.setExcluded(true);
		oIdent.updateRecord();

		if (BizUsuario.IsAdminCompanyUser()) {
			BizDashBoardConfig cfg = new BizDashBoardConfig();
			cfg.addFilter("company", this.getCompany());
			cfg.addFilter("userid", BizUsuario.getAdminCompanyUser(), "<>");
			cfg.addFilter("dash_description", this.getDashDescription());
			cfg.addFilter("excluded", false);
			cfg.deleteMultiple();
		}

	}

	public void processInclude() throws Exception {
		BizDashBoardConfig oIdent = (BizDashBoardConfig) this.getPreInstance();
		oIdent.setExcluded(false);
		oIdent.updateRecord();

	}

	public void execProcessInclude() throws Exception {
		JExec oExec = new JExec(this, "ProcesarInclude") {

			@Override
			public void Do() throws Exception {
				processInclude();
			}
		};
		oExec.execute();
	}

	public void execProcessUp() throws Exception {
		JExec oExec = new JExec(this, "processUp") {

			@Override
			public void Do() throws Exception {
				processUp();
			}
		};
		oExec.execute();
	}

	public void execProcessDown() throws Exception {
		JExec oExec = new JExec(this, "processDown") {

			@Override
			public void Do() throws Exception {
				processDown();
			}
		};
		oExec.execute();
	}

	public void processUp() throws Exception {
		BizDashBoardConfig bcol = new BizDashBoardConfig();
		bcol.dontThrowException(true);

		long currid = this.getId();

		if (bcol.read(this.getCompany(), this.getUserid(), this.getDashName(), this.getDashOrder() - 1)) {
			long id = bcol.getId();
			bcol.setDashOrder(this.getDashOrder());
			bcol.update();

			setDashOrder(getDashOrder() - 1);
			update();
		}
	}

	public void processDown() throws Exception {
		BizDashBoardConfig bcol = new BizDashBoardConfig();
		bcol.dontThrowException(true);

		if (bcol.read(this.getCompany(), this.getUserid(), this.getDashName(), this.getDashOrder() + 1)) {
			bcol.setDashOrder(this.getDashOrder());
			bcol.update();
			setDashOrder(getDashOrder() + 1);
			update();
		}

	}

	public boolean read(String company, String userid, String dashName, long order) throws Exception {
		addFilter("company", company);
		addFilter("userid", userid);
		addFilter("dash_name", dashName);
		addFilter("dash_order", order);
		return read();
	}

	public void execProcessReOrder(BizDashBoardConfig destino, boolean up) throws Exception {
		JExec oExec = new JExec(this, "processReOrder") {

			@Override
			public void Do() throws Exception {
				processReOrder(destino, up);
			}
		};
		oExec.execute();
	}
	
	
	public void processReOrderDelete(BizDashBoardConfig deleted) throws Exception {

			JBaseRegistro regs = JBaseRegistro.recordsetFactory();
			String zSQL = " update UI_DASHBOARD_ORDER set DASH_ORDER= DASH_ORDER - 1 where " + " company='" + deleted.getCompany() + "'" + " and userid='" + deleted.getUserid() + "'" + " and dash_name='"
					+ deleted.getDashName() + "'" + " and DASH_ORDER>'" + deleted.getDashOrder() + "'" ;
			regs.Execute(zSQL);


	}
	

	public void processReOrder(BizDashBoardConfig destino, boolean up) throws Exception {

		BizDashBoardConfig bcol = new BizDashBoardConfig();
		bcol.dontThrowException(true);
		if (bcol.read(this.getCompany(), this.getUserid(), this.getDashName(), this.getDashOrder())) {
			bcol.setDashOrder(destino.getDashOrder());
			bcol.update();
		}

		if (up) {

			JBaseRegistro regs = JBaseRegistro.recordsetFactory();
			String zSQL = " update UI_DASHBOARD_ORDER set DASH_ORDER= DASH_ORDER + 1 where " + " company='" + getCompany() + "'" + " and userid='" + getUserid() + "'" + " and dash_name='"
					+ getDashName() + "'" + " and DASH_ORDER>='" + destino.getDashOrder() + "'" + " and DASH_ORDER<'" + this.getDashOrder() + "'" + " and id<> " + this.getId();
			regs.Execute(zSQL);

		} else {
			JBaseRegistro regs = JBaseRegistro.recordsetFactory();
			String zSQL = " update UI_DASHBOARD_ORDER set DASH_ORDER= DASH_ORDER - 1 where " + " company='" + getCompany() + "'" + " and userid='" + getUserid() + "'" + " and dash_name='"
					+ getDashName() + "'" + " and DASH_ORDER<='" + destino.getDashOrder() + "'" + " and DASH_ORDER>'" + this.getDashOrder() + "'" + " and id<> " + this.getId();
			regs.Execute(zSQL);

		}

		// TODO Auto-generated method stub

	}

}
