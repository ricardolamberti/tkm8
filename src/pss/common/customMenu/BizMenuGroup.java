package  pss.common.customMenu;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.actions.BizAction;
import pss.core.winUI.icons.GuiIconos;

public class BizMenuGroup extends JRecord {

  private JString pIdMenu = new JString();
  private JLong pIdGroup = new JLong();
  private JString pDescription = new JString();
  private JLong pIdGroupParent = new JLong();
  private JString pIconFile = new JString();
  private JInteger pOrden = new JInteger();
  private JString pIdAction = new JString();
  private JString pFinalDescr = new JString() {@Override
	public void preset() throws Exception {pFinalDescr.setValue(findDescription());}};
  

  public String getIdMenu() throws Exception { return this.pIdMenu.getValue();}
  public String getDescription() throws Exception { return this.pDescription.getValue();}
  public long getIdGroup() throws Exception { return this.pIdGroup.getValue();}
  public long getIdGroupParent() throws Exception { return this.pIdGroupParent.getValue();}
  public String getIconFile() throws Exception { return this.pIconFile.getValue();}
  public String getActionId() throws Exception { return this.pIdAction.getValue();}
  public long getOrden() throws Exception { return this.pOrden.getValue();}
  
  private JRecords<BizMenuItem> subitems;
  private JRecords<BizMenuGroup> subgroups;
	/**
	 * Constructor
	 */
	public BizMenuGroup() throws Exception {
	}
	/**
	 * Adds the var properties
	 */
	@Override
	public void createProperties() throws Exception {
    addItem("id_menu", this.pIdMenu);
    addItem("id_group", this.pIdGroup);
    addItem("description", this.pDescription);
    addItem("id_group_parent", this.pIdGroupParent);
    addItem("icon_file", this.pIconFile);
    addItem("orden", this.pOrden);
    addItem("id_action", this.pIdAction);
    addItem("final_descr", this.pFinalDescr);
	}
	/**
	 * Adds the fixed properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "id_menu", "Id de menu", true, true, 15);
		addFixedItem( KEY, "id_group", "Id Grupo", true, true, 15);
		addFixedItem( FIELD, "description", "Descripción", true, false, 100);
		addFixedItem( FIELD, "id_group_parent", "Id Grupo Padre", true, true, 15);
		addFixedItem( FIELD, "icon_file", "Icon File", true, false, 15);
		addFixedItem( FIELD, "orden", "Orden", true, false, 5);
    addFixedItem( FIELD, "id_action", "Acción", true, false, 200);
    addFixedItem( VIRTUAL, "final_descr", "Descripción", true, true, 200);
	}
	/**
	 * Overrides the gettable()
	 */
	@Override
	public String GetTable() {
		return "UI_MENU_GROUP";
	}

  public boolean Read(String menu, long group) throws Exception {
    this.addFilter("id_menu", menu);
    this.addFilter("id_group", group);
    return read();
  }

  public String getMenuId() throws Exception {
    return this.pIdMenu.getValue();
  }
  
  @Override
	public void processInsert() throws Exception {
  	if (pIdGroup.isNull()) {
  		BizMenuGroup max = new BizMenuGroup();
  		max.addFilter("id_menu", this.pIdMenu.getValue());
  		this.pIdGroup.setValue(max.SelectMaxLong("id_group")+1);
  	}
  	super.insert();
  }

 
//  
//	private JRecords getSubGroups() throws Exception {
//		JRecords records = new JRecords(BizMenuGroup.class);
//		records.addFilter("id_menu", this.getIdMenu());
//		records.addFilter("id_group_parent", this.pIdGroup.getValue());
//		records.addOrderBy("description");
//		records.readAll();
//		records.toStatic();
//		return records;
//	}
//
//	private JRecords getSubItems() throws Exception {
//		JRecords records = new JRecords(BizMenuItem.class);
//		records.addFilter("id_menu", this.getIdMenu());
//		records.addFilter("id_group_parent", this.pIdGroup.getValue());
//		records.addOrderBy("description");
//		records.readAll();
//		records.toStatic();
//		return records;
//	}
	
  private BizAction findAction() throws Exception {
//		return GuiModuloPss.getModuloPss().findActionByUniqueId(this.pIdAction.getValue());
  	return BizUsuario.getUsr().getModuloPss().findActionByUniqueId(this.pIdAction.getValue());
  }

  public String findIconFile(int size) throws Exception { 
		if (this.pIconFile.isNotNull()) {
			if (JTools.isNumber(this.pIconFile.getValue())) { 
				return GuiIconos.GetGlobal().buscarIcono(Integer.parseInt(this.pIconFile.getValue())).GetIconFile();
			  
			}
			return GuiIconos.makeFileName(size, this.pIconFile.getValue());
		}
		if (this.pIdAction.isNotNull()) {
			BizAction action = this.findAction();
			if (action==null) return null;
			return action.getIconFile(size);
		}
		return null;
	}
  public int findIconID(int size) throws Exception { 
		if (this.pIconFile.isNotNull()) {
			if (JTools.isNumber(this.pIconFile.getValue())) { 
				return Integer.parseInt(this.pIconFile.getValue());
			  
			}
			return 0;
		}
		if (this.pIdAction.isNotNull()) {
			BizAction action = this.findAction();
			if (action==null) return 0;
			return action.GetNroIcono();
		}
		return 0;
	}
	
	
	public String findDescription() throws Exception { 
		if (this.pDescription.isNotNull())
			return this.pDescription.getValue();
		if (this.pIdAction.isNotNull()) {
			BizAction action = this.findAction();
			if (action==null) return "Desconocido";
			return action.GetDescr();
		}
		return "";
	}  
	
	public JRecords<BizMenuItem> getObjSubItems() throws Exception {
		if (subitems!=null) return this.subitems;
		JRecords<BizMenuItem> oItems=new JRecords<BizMenuItem>(BizMenuItem.class);
		oItems.addFilter("id_menu", this.getIdMenu());
		oItems.addFilter("id_group_parent", this.getIdGroup());
		oItems.addOrderBy("orden");
		oItems.readAll();
		oItems.toStatic();
		return (this.subitems=oItems);
	}
	
	public JRecords<BizMenuGroup> getObjSubGroups() throws Exception {
		if (subgroups!=null) return this.subgroups;
		JRecords<BizMenuGroup> r=new JRecords<BizMenuGroup>(BizMenuGroup.class);
		r.addFilter("id_menu", this.getIdMenu());
		r.addFilter("id_group_parent", this.getIdGroup());
		r.addOrderBy("orden");
		return (this.subgroups=r);
	}

	public int generateActionMenu(BizAction action, boolean okAction, int size) throws Exception {
		
 		if (okAction && !BizUsuario.ifOperacionHabilitada(this.getActionId()) )
			return 0;
		int hijos=0;
		BizAction groupAction=new BizAction();
		groupAction.SetTitle("Menú");
		groupAction.setDescrip(this.findDescription());
		groupAction.SetNroIcono(this.findIconID(size));
		groupAction.setIconFile(size, this.findIconFile(size));
		groupAction.setInMenu(true);
//		groupAction.setInTree(true);
		groupAction.SetIdAction(this.getActionId());
		BizMenuItem item;
		BizMenuGroup group;
		JIterator<BizMenuItem> iter=this.getObjSubItems().getStaticIterator();
		JIterator<BizMenuGroup> iter2=this.getObjSubGroups().getStaticIterator();
		item=iter.hasMoreElements()?(BizMenuItem) iter.nextElement():null;
		group=iter2.hasMoreElements()?(BizMenuGroup) iter2.nextElement():null;
		while (true) {
			if (item!=null ) {
				while (group==null||item.getOrden()<group.getOrden()) {
					if (! ( okAction && !BizUsuario.ifOperacionHabilitada(item.getActionId()) )) {
						item.generateActionItem(groupAction, size);
						hijos++;
					}
					item=iter.hasMoreElements()?(BizMenuItem) iter.nextElement():null;
					if (item==null)
						break;
				}
			}
			if (group!=null) {
				while (item==null||item.getOrden()>=group.getOrden()) {
					hijos+=group.generateActionMenu(groupAction, okAction, size);
					group=iter2.hasMoreElements()?(BizMenuGroup) iter2.nextElement():null;
					if (group==null)
						break;
				}
			}
			if (item==null && group==null)
				break;
		}
		
		
//		JIterator<BizMenuItem> iter=this.getObjSubItems().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizMenuItem item=(BizMenuItem) iter.nextElement();
//			if ( okAction && BizUsuario.ifOperacionHabilitada(item.getActionId()) == false )
//				continue;
//			item.generateActionItem(groupAction, size);
//			hijos++;
//		}
//		JIterator<BizMenuGroup> iter2=this.getObjSubGroups().getStaticIterator();
//		while (iter2.hasMoreElements()) {
//			BizMenuGroup group=(BizMenuGroup) iter2.nextElement();
//			hijos+=group.generateActionMenu(groupAction, okAction, size);
//		}
		if (hijos>0) 
			action.addSubAction(groupAction);
		return hijos;
	}


	
}
