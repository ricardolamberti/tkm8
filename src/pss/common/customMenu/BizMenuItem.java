package  pss.common.customMenu;

import pss.common.components.JSetupParameters;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.win.actions.BizAction;
import pss.core.winUI.icons.GuiIconos;

public class BizMenuItem extends JRecord {

  private JString pIdMenu = new JString();
  private JLong pIdItem = new JLong();
  private JString pIconFile = new JString();
	private JString pDescription = new JString();
	private JBoolean newWindow = new JBoolean();
	private JBoolean newSession = new JBoolean();
  private JString pIdAction = new JString();
  private JLong pIdGroupParent = new JLong();
  private JInteger pOrden = new JInteger();
  private JString pFinalDescr = new JString() {@Override
	public void preset() throws Exception {pFinalDescr.setValue(findDescription());}};

	/**
	 * Constructor
	 */
	public BizMenuItem() throws Exception {
	}
	/**
	 * Adds the var properties
	 */
	@Override
	public void createProperties() throws Exception {
    this.addItem("id_item", this.pIdItem);
    this.addItem("id_menu", this.pIdMenu);
    this.addItem("icon_file", this.pIconFile);
    this.addItem("description", this.pDescription);
    this.addItem("id_action", this.pIdAction);
    this.addItem("id_group_parent", this.pIdGroupParent);
    this.addItem("orden", this.pOrden);
    this.addItem("final_descr", this.pFinalDescr);
	}
	/**
	 * Adds the fixed properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem( KEY, "id_menu", "Id de menu", true, true, 15, 0, JObject.JUPPER_NOSPACE);
    this.addFixedItem( KEY, "id_item", "Id de item", true, true, 5, 0);
    this.addFixedItem( FIELD, "icon_file", "Icono File", true, false, 6);
    this.addFixedItem( FIELD, "description", "Descripción", true, false, 50);
    this.addFixedItem( FIELD, "id_action", "Acción", true, true, 200);
    this.addFixedItem( FIELD, "id_group_parent", "Grupo", true, true, 15);
    this.addFixedItem( FIELD, "orden", "Orden", true, true, 5);
    this.addFixedItem( VIRTUAL, "final_descr", "Descripción", true, true, 200);
	}
	/**
	 * Overrides the gettable()
	 */
	@Override
	public String GetTable() {
		return "UI_MENU_ITEM";
	}

	public String getIdMenu() throws Exception {
		return this.pIdMenu.getValue();
	}
  public long getIdItem() throws Exception {
    return this.pIdItem.getValue();
  }
  public String getActionId() throws Exception {
    return this.pIdAction.getValue();
  }
//  public String getIdGroup() throws Exception {
//    return this.pIdGroup.getValue();
//  }
  public String getDescription() throws Exception {
    return pDescription.getValue();
  }
  public String getIconFile() throws Exception {
    return pIconFile.getValue();
  }
  public long getOrden() throws Exception {
    return pOrden.getValue();
  }
	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(true);
		zParams.setTruncateData(true);
	}

//  public boolean isActionItem() throws Exception {
//    return this.pIdAction.isNotNull() && !this.pIdAction.isEmpty();
//  }
//  public boolean isChildMenuItem() throws Exception {
//    return this.pIdChildMenu.isNotNull();
//  }

  @Override
	public void processInsert() throws Exception {
  	if (this.pIdItem.isNull()) {
	    BizMenuItem max = new BizMenuItem();
	    max.addFilter("id_menu", this.pIdMenu.getValue());
	    long lMax = max.SelectMaxLong("id_item");
	    this.pIdItem.setValue(lMax+1);
  	}
    super.processInsert();
  }
  
  public BizAction findAction() throws Exception {
//		return GuiModuloPss.getModuloPss().findActionByUniqueId(this.pIdAction.getValue());
  	return BizUsuario.getUsr().getModuloPss().findActionByUniqueId(this.pIdAction.getValue());
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
	public String findIconFile(int size) throws Exception { 
		if (this.pIconFile.isNotNull()) {
			if (JTools.isNumber(this.pIconFile.getValue())) 
				return GuiIconos.GetGlobal().buscarIcono(size, Integer.parseInt(this.pIconFile.getValue())).GetIconFile();
			return GuiIconos.makeFileName(size, this.pIconFile.getValue());
		}
		BizAction action = this.findAction();
		if (action==null) return null;
		return action.getIconFile(size);
	}
	
	public String findDescription() throws Exception { 
		if (this.pDescription.isNotNull())
			return this.pDescription.getValue();
		BizAction action = this.findAction();
		if (action==null) return "Desconocido";
		return action.GetDescr();
	}
	
  public void generateActionItem(BizAction groupAction, int size) throws Exception {
  	BizAction action = new BizAction();
  	action.SetTitle("Menú");
  	action.setDescrip(this.findDescription());
  	action.SetNroIcono( this.findIconID(size));
  	action.setIconFile(size, this.findIconFile(size));
  	action.SetIdAction(this.getActionId());
  	action.setNuevaVentana(this.findNewWindow());
  	action.setNuevaSession(this.findNewSession());
  	action.setConfirmMessageDescription(findConfirmation());
  	action.setModal(findModal());
  	action.setInMenu(true);
//  	action.setInTree(true);
  	groupAction.addSubAction(action);
  }
	
	public boolean findNewWindow() throws Exception { 
		if (this.newWindow.isNotNull())
			return this.newWindow.getValue();
		BizAction action = this.findAction();
		if (action==null) return false;
		return action.isNuevaVentana();
	}
	
	public String findConfirmation() throws Exception { 
		BizAction action = this.findAction();
		if (action==null) return null;
		if (!action.hasConfirmMessage()) return null;
		return action.getConfirmMessageDescription();
	}
	public boolean findModal() throws Exception { 
		BizAction action = this.findAction();
		if (action==null) return false;
		return action.isModal();
	}

	public boolean findNewSession() throws Exception { 
		if (this.newSession.isNotNull())
			return this.newSession.getValue();
		BizAction action = this.findAction();
		if (action==null) return false;
		return action.isNuevaSession();
	}

  
}
