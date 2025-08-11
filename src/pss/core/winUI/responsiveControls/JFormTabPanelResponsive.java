package pss.core.winUI.responsiveControls;

import java.awt.Component;

import javax.swing.JTabbedPane;

import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.submits.JAct;
import pss.core.winUI.controls.JFormControl;

public class JFormTabPanelResponsive extends JFormPanelResponsive  {

	
  JMap<String,JFormTabResponsive> oTabs;
  String oDato;
  JTabbedPane oldComponente;
  


	public void setOldComponente(JTabbedPane oldComponente) {
		this.oldComponente = oldComponente;
	}

	@Override
	public Component getComponent() {
		return oldComponente;
	}
	public String getActive() {
		return oDato;
	}

	public void setActive(String active) {
		this.oDato = active;
	}

	public JMap<String,JFormTabResponsive> getTabs() throws Exception {
  	if (oTabs==null) oTabs=JCollectionFactory.createOrderedMap();
  	return oTabs;
  }
  
  public void addTab(String id,JFormTabResponsive tab) throws Exception {
  	tab.setIdControl(id);
  	if (getTabs().getElement(id)!=null) {
  		getTabs().removeElement(id);
  	};
  	getTabs().addElement(id,tab);
  	if (oDato==null)
  		oDato=id;
  	
  }
  
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
//			if (!(oCon instanceof JFormLocalForm)) continue;
			oCon.BaseToControl(zModo, userRequest);
		}
		this.setValue(this.findId());
	}
	
	private String findId() throws Exception {
		if (this.getForm()==null) return null;
		JAct act = this.getForm().getOwnerAction();
		if (act==null) return null;
		if (!act.hasActionSource()) return null;
		return act.getActionSource().getExtraData(getName());
	}
  
	 @Override
	public boolean isResponsive() {
		if (getComponent()!=null) return false;
		return super.isResponsive();
	}
 
	 @Override
	public JFormControlResponsive getResponsiveVersion() throws Exception {
		setOldComponente(null);
		return this;
	}
  
  @Override
  public String getName() {
  	try {
			JIterator<String> it = getTabs().getKeyIterator();
			if (it.hasMoreElements()) {
				String name = JTools.replace(it.nextElement(), ".", "_");
				return name+"_selecttab";
			}
  	} catch (Exception e) {
		}
  	
  	return super.getName();
  }
  public void removeTab(String id) throws Exception {
  	getTabs().removeElement(id);
  	getControls().removeById(id);
  }
  public boolean hasTabs() throws Exception {
  	return getTabs().size()!=0;
  }
  
  //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormTabPanelResponsive() {
		setResponsiveClass("nav nav-tabs");

  }
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() {
    return oDato;
  }

  @Override
  public void clear() throws Exception {
  //	oDato="";
  }
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() {
    if ( oDato == null       ) return false;
    if ( oDato.trim().equals( "" )  ) return false;
    return true;
  }

  @Override
	public void setValue( String zVal ) throws Exception {
  	oDato= zVal;
  }


}
