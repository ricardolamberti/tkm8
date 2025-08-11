package pss.core.winUI.lists;

import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JOrderedMap;

public class BizListExcludeCol extends JRecord {

	private JString company = new JString();
	private JString userid = new JString();
	private JString className = new JString();
	private JString colName = new JString();
	private JString colDesc = new JString();
	private JBoolean excluded = new JBoolean();
	private JBoolean isAdmin = new JBoolean();
	private JString vision = new JString();
	private JLong order = new JLong();
	public static String DEFAULT_VISION = "XXXXX";

	// private static InheritableThreadLocal<BizListExcludeCol> excludedFields =
	// new InheritableThreadLocal<BizListExcludeCol>();

	static JOrderedMap<String, String> excludedFields = null;
	static JOrderedMap<String, JList<JColumnaLista>> orderedFields = null;
	static JOrderedMap<String, String> adminFields = null;

	static JList<String> companies = JCollectionFactory.createList();

	public static void cleanCache() {
		excludedFields = null;
		orderedFields = null;
		adminFields = null;
		companies = JCollectionFactory.createList();
	}

	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////

	public long getOrder() throws Exception {
		return order.getValue();
	}

	public void setOrder(long val) {
		order.setValue(val);
	}

	/**
	 * @return the pDescription
	 */
	public String getVisionName() throws Exception {
		return vision.getValue();
	}

	public void setVisionName(String val) {
		vision.setValue(val);
	}

	public String getClassName() throws Exception {
		return className.getValue();
	}

	public void setClassName(String val) {
		className.setValue(val);
	}

	public String getColName() throws Exception {
		return colName.getValue();
	}

	public void setColName(String val) {
		colName.setValue(val);
	}

	public String getColDesc() throws Exception {
		return colDesc.getValue();
	}

	public void setColDesc(String val) {
		colDesc.setValue(val);
	}

	public boolean isExcluded() throws Exception {
		return excluded.getValue();
	}

	public void setExcluded(boolean val) {
		excluded.setValue(val);
	}

	public boolean isAdmin() throws Exception {
		return isAdmin.getValue();
	}

	public void setAdmin(boolean val) {
		isAdmin.setValue(val);
	}

	public void setCompany(String zValue) throws Exception {
		company.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return company.getValue();
	}

	public void setUserId(String zValue) throws Exception {
		userid.setValue(zValue);
	}

	public String getUserId() throws Exception {
		return userid.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizListExcludeCol() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", company);
		this.addItem("userid", userid);
		this.addItem("class_name", className);
		this.addItem("col_order", order);
		this.addItem("col_name", colName);
		this.addItem("col_desc", colDesc);
		this.addItem("excluded", excluded);
		this.addItem("vision_name", vision);
		this.addItem("is_admin", isAdmin);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Empresa", true, true, 250);
		this.addFixedItem(KEY, "class_name", "Clase", true, true, 500);
		this.addFixedItem(KEY, "userid", "Usuario", true, false, 250);
		this.addFixedItem(KEY, "col_name", "Columna", true, true, 500);
		this.addFixedItem(FIELD, "col_order", "Orden", true, false, 3);
		this.addFixedItem(FIELD, "col_desc", "Columna", true, true, 500);
		this.addFixedItem(FIELD, "excluded", "Excluido?", true, false, 1);
		this.addFixedItem(FIELD, "vision_name", "Vision", true, false, 500);
		this.addFixedItem(FIELD, "is_admin", "Admin?", true, false, 1);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "list_exclude_cols";
	}

	public static String getAdminUser(String company) {
		return "AMDIN." + company;
	}

	public static boolean findCol(String company, String classname, String vision, String field) throws Exception {
		if (excludedFields == null)
			excludedFields = JCollectionFactory.createOrderedMap();
		if (vision == null)
			vision = DEFAULT_VISION;
		if (vision.equalsIgnoreCase(""))
			vision = DEFAULT_VISION;
		if (companies.containsElement(company) == false)
			generateList(company);
		boolean ret = excludedFields.containsKey(company + BizUsuario.getUsr().GetUsuario() + classname + vision + field);
		if (ret == false)
			ret = excludedFields.containsKey(company + getAdminUser(company) + classname + vision + field);
		return ret;

	}

	private static void generateList(String company) throws Exception {
		companies.addElement(company);
		JRecords<BizListExcludeCol> cols = new JRecords<BizListExcludeCol>(BizListExcludeCol.class);
		cols.addFilter("company", company);
		cols.addFilter("excluded", true);
		cols.toStatic();
		cols.firstRecord();
		while (cols.nextRecord()) {
			BizListExcludeCol col = cols.getRecord();
			excludedFields.addElement(col.getCompany() + col.getUserId() + col.getClassName() + col.getVisionName() + col.getColName(), col.getColName());
		}

	}

	public static boolean generateCustomColumnList(String company, String className, String vision, JWinList weblist) throws Exception {

		if (vision == null)
			vision = DEFAULT_VISION;
		if (vision.equalsIgnoreCase(""))
			vision = DEFAULT_VISION;

		// JList<JColumnaLista> list = JCollectionFactory.createList();
		JRecords<BizListExcludeCol> cols = new JRecords<BizListExcludeCol>(BizListExcludeCol.class);
		cols.addFilter("company", company);
		cols.addFilter("userid", BizUsuario.getUsr().GetUsuario());
		cols.addFilter("class_name", className);
		cols.addFilter("vision_name", vision);
		long count = cols.selectCount();
		if (count == 0) {
			cols = new JRecords<BizListExcludeCol>(BizListExcludeCol.class);
			cols.addFilter("company", company);
			cols.addFilter("userid", "ADMIN." + company);
			cols.addFilter("class_name", className);
			cols.addFilter("vision_name", vision);
		}

		cols.addOrderBy("col_order");
		cols.toStatic();
		cols.firstRecord();
		// list = JCollectionFactory.createList();
		// JColumnaLista icon = new JColumnaLista(ImageIcon.class);
		// icon.SetTitulo("");
		// list.addElement(icon);
		if (cols.nextRecord() == false)
			return false;

		weblist.AddIcono("");

		do {
			BizListExcludeCol col = cols.getRecord();
			if (col.getColName().equals(""))
				continue;
			if (col.isExcluded())
				continue;
			// JColumnaLista cl = weblist.getColumn(col.getColName());
			// list.addElement(cl);
			if (hasToShowAdminColumn(company,className, col.getColName()))
				continue;

			weblist.AddColumnaLista(col.getColName());
		} while (cols.nextRecord());
		// orderedFields.addElement(company + className+vision, list);
		// weblist.setColumnaLista(list);

		return true;

	}

	public static boolean hasToShowAdminColumn(String company,String className, String colName) {
		try {
			if (adminFields == null)
				buildAdminList();
			
			if (adminFields.getElement(company+className+colName) == null)
				return false;
			
			if (BizUsuario.IsAdminCompanyUser() )
				return false;
			
			return true;
		} catch (Exception eee) {
		}
		return false;
	}

	public boolean read(String company, String userid, String className, String vision, String colName) throws Exception {
		this.addFilter("company", company);
		this.addFilter("userid", userid);
		this.addFilter("class_name", className);
		this.addFilter("col_name", colName);
		this.addFilter("vision_name", vision);
		return super.read();
	}

	public boolean read(String company, String userid, String className, String vision, long colOrder) throws Exception {
		this.addFilter("company", company);
		this.addFilter("userid", userid);
		this.addFilter("class_name", className);
		this.addFilter("vision_name", vision);
		this.addFilter("col_order", colOrder);
		return super.read();
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
		BizListExcludeCol oIdent = (BizListExcludeCol) this.getPreInstance();
		oIdent.setAdmin(true);
		if (adminFields == null)
			adminFields = JCollectionFactory.createOrderedMap();
		adminFields.addElement(getCompany() + getClassName() + getColName(), getColName());

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
		BizListExcludeCol oIdent = (BizListExcludeCol) this.getPreInstance();
		oIdent.setAdmin(false);
		if (adminFields == null)
			adminFields = JCollectionFactory.createOrderedMap();
		adminFields.removeElement(getCompany() + getClassName() + getColName());

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
		BizListExcludeCol oIdent = (BizListExcludeCol) this.getPreInstance();
		oIdent.setExcluded(true);
		if (excludedFields == null)
			excludedFields = JCollectionFactory.createOrderedMap();
		excludedFields.addElement(getCompany() + getUserId() + getClassName() + getVisionName() + getColName(), getColName());
		oIdent.updateRecord();
	}

	public void processInclude() throws Exception {
		BizListExcludeCol oIdent = (BizListExcludeCol) this.getPreInstance();
		oIdent.setExcluded(false);
		if (excludedFields != null)
			excludedFields.removeElement(getCompany() + getUserId() + getClassName() + getVisionName() + getColName());
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

	public void addColumns(String clase, String vision, JList<JColumnaLista> list) throws Exception {

		String company = BizUsuario.getUsr().getCompany();
		JIterator<JColumnaLista> it = list.getIterator();

		if (vision == null)
			vision = DEFAULT_VISION;

		int i = 1;
		while (it.hasMoreElements()) {
			JColumnaLista col = it.nextElement();

			if (col.GetCampo() == null)
				continue;
			if (col.GetCampo().equals(""))
				continue;

			BizListExcludeCol bcol = new BizListExcludeCol();
			bcol.dontThrowException(true);
			if (bcol.read(company, BizUsuario.getUsr().GetUsuario(), clase, vision, col.GetCampo()) == false) {
				bcol.setOrder(i++);
				bcol.setCompany(company);
				bcol.setUserId(BizUsuario.getUsr().GetUsuario());
				bcol.setClassName(clase);
				bcol.setColName(col.GetCampo());
				bcol.setColDesc(col.GetTitulo());
				bcol.setExcluded(false);
				bcol.setVisionName(vision);
				bcol.insert();
			}
		}

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
		BizListExcludeCol bcol = new BizListExcludeCol();
		bcol.dontThrowException(true);
		if (bcol.read(getCompany(), getUserId(), getClassName(), getVisionName(), getOrder() - 1)) {
			bcol.setOrder(getOrder());
			bcol.update();
			this.setOrder(getOrder() - 1);
			this.update();
		}
	}

	public void processDown() throws Exception {
		BizListExcludeCol bcol = new BizListExcludeCol();
		bcol.dontThrowException(true);
		if (bcol.read(getCompany(), getUserId(), getClassName(), getVisionName(), getOrder() + 1)) {
			bcol.setOrder(getOrder());
			bcol.update();
			this.setOrder(getOrder() + 1);
			this.update();
		}
	}

	public void removeColumns(String clase, String vision, JList<JColumnaLista> list) throws Exception {
		String company = BizUsuario.getUsr().getCompany();

		BizListExcludeCol bcol = new BizListExcludeCol();
		bcol.dontThrowException(true);
		bcol.addFilter("company", company);
		bcol.addFilter("userid", BizUsuario.getUsr().GetUsuario());
		bcol.addFilter("class_name", clase);
		if (vision == null)
			vision = DEFAULT_VISION;
		bcol.addFilter("vision_name", vision);
		bcol.deleteMultiple();

	}

	public static void buildAdminList()  throws Exception {
		try {
			if (adminFields==null) {
				adminFields=JCollectionFactory.createOrderedMap();
			
				JRecords<BizListExcludeCol> cols = new JRecords<BizListExcludeCol>(BizListExcludeCol.class);
				cols.addFilter("is_admin", true);
				cols.toStatic();
				
				cols.firstRecord();
				
				while (cols.nextRecord()) {
					BizListExcludeCol c = cols.getRecord();
					adminFields.addElement(c.getCompany()+c.getClassName()+c.getColName(), c.getColName());
				}
			}
		} catch (Exception ee){
		}
		
	}

	// public void execInitialize(final String clase, final String vision2,
	// final JList<JColumnaLista> list) throws Exception {
	// // TODO Auto-generated method stub
	// // addColumns(clase, vision2, list);
	//
	// JExec oExec = new JExec(this, "initialize") {
	//
	// @Override
	// public void Do() throws Exception {
	// addColumns(clase, vision2, list);
	// }
	// };
	// oExec.execute();
	//
	// }
	//
	// public void execdeInitialize(final String clase, final String vision2,
	// final JList<JColumnaLista> list) throws Exception {
	//
	// JExec oExec = new JExec(this, "deInitialize") {
	//
	// @Override
	// public void Do() throws Exception {
	// removeColumns(clase, vision2, list);
	// }
	// };
	// oExec.execute();
	//
	// }

}
