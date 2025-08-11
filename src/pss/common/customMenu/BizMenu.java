package  pss.common.customMenu;

import pss.common.components.JSetupParameters;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizMenu extends JRecord {

	private JString pIdMenu = new JString();
	private JString pDescription = new JString();
	private JString pBusiness = new JString();

// falla cuando hay mucha reentrancia,
//	public static JMap<String, BizMenu> hCache=null;
	
	private JRecords<BizMenuGroup> groups;

	
	/**
	 * Constructor
	 */
	public BizMenu() throws Exception {
	}
	/**
	 * Adds the var properties
	 */
	@Override
	public void createProperties() throws Exception {
		addItem("id_menu", this.pIdMenu);
		addItem("description", pDescription);
		addItem("business", pBusiness);
	}
	/**
	 * Adds the fixed properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "id_menu", "Id de menu", true, true, 15, 0, JObject.JUPPER_NOSPACE);
		addFixedItem( FIELD, "description", "Descripción", true, true, 50);
		addFixedItem( FIELD, "business", "Negocio", true, false, 50);
	}
	/**
	 * Overrides the gettable()
	 */
	@Override
	public String GetTable() {
		return "UI_MENU";
	}

	public void setIdMenu(String zVal) throws Exception {
		this.pIdMenu.setValue(zVal);
	}
	public String getIdMenu() throws Exception {
		return this.pIdMenu.getValue();
	}
	public void setDescription(String zVal) throws Exception {
		this.pDescription.setValue(zVal);
	}
	public String getDescription() throws Exception {
		return this.pDescription.getValue();
	}
	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(true);
		zParams.setTruncateData(true);
	}

  public boolean Read(String zMenuId) throws Exception {
    this.addFilter("id_menu", zMenuId);
    return read();
  }
  public static BizMenu getMenu(String idMenu) throws Exception {
	  	BizMenu menu = new BizMenu();
	  	menu.Read(idMenu);
	  	return menu;
	  }  
//  public static BizMenu getMenu(String idMenu) throws Exception {
//  	return getMenuCache().getElement(idMenu);
//  }
//  
//  public static synchronized JMap<String, BizMenu> getMenuCache() throws Exception {
//  	if (hCache!=null) return hCache;
//  	JMap<String, BizMenu> map = JCollectionFactory.createMap();
//  	JRecords<BizMenu> recs = new JRecords<BizMenu>(BizMenu.class);
//  	recs.readAll();
//  	recs.firstRecord();
//  	while (recs.nextRecord()) {
//  		BizMenu m = recs.getRecord();
//  		map.addElement(m.getIdMenu(), m);
//  	}
//  	return (hCache=map);
//  }
  
	public JRecords<BizMenuGroup> getObjMenuGroups() throws Exception {
		if (this.groups!=null && BizUsuario.getUsr().getVisionCustomMenu()==null) return this.groups;
		long parent=0;
		boolean full=true;
		if  (BizUsuario.getUsr().getVisionCustomMenu()!=null) {
			BizMenuGroup gr = new BizMenuGroup();
			gr.dontThrowException(true);
			gr.addFilter("id_menu", getIdMenu());
			gr.addFilter("id_action", BizUsuario.getUsr().getVisionCustomMenu());
			if (gr.read()) {
				parent = gr.getIdGroup();
				full=false;
			}
		}

		JRecords<BizMenuGroup> r=new JRecords<BizMenuGroup>(BizMenuGroup.class);
		r.addFilter("id_menu", this.getIdMenu());
		if (full)
			r.addFilter("id_group_parent", parent);
		else
			r.addFilter("id_group", parent);
		r.addOrderBy("orden");
		r.readAll();
		if (BizUsuario.getUsr().getVisionCustomMenu()!=null) 
			return r;
		return (this.groups=r);
	}

  
	
//	private JRecords getMenuGroups() throws Exception {
//		JRecords records = new JRecords(BizMenuGroup.class);
//		records.addFilter("id_menu", this.getIdMenu());
//		records.addFilter("id_group_parent", 0L);
//		records.addOrderBy("description");
//		records.readAll();
//		records.toStatic();
//		return records;
//	}
  
}
