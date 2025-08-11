/*
 * Created on 19-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.io.Serializable;

import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.JWin;
import pss.www.platform.content.generators.JXMLContent;

public abstract class JWebAbstractValueChooser extends JWebViewEditComponent {

	private JWebValueChooserModel oModel;
	protected String sSelectedValue="";
	protected boolean bConvertForeignCharsForWeb = true;
	private boolean useCache;
  private boolean neverUseCache;
	protected boolean showKey=false;

	public boolean isNeverUseCache() {
		return neverUseCache;
	}

	public void setNeverUseCache(boolean neverUseCache) {
		this.neverUseCache = neverUseCache;
	}	
	public boolean isUseCache() {
		return useCache;
	}

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	public JWebAbstractValueChooser() {
	}

	public JWebAbstractValueChooser(JWebValueChooserModel zModel) {
		this.oModel=zModel;
	}

	public void setModel(JWebValueChooserModel zModel) {
		this.oModel=zModel;
	}

	@Override
	public void destroy() {
		if (this.oModel!=null) {
			this.oModel.destroy();
			this.oModel=null;
		}
		this.sSelectedValue="";
		super.destroy();
	}

	public JWebValueChooserModel getModel() {
		if (this.oModel==null) {
			throw new RuntimeException("Value chooser without model");
		}
		return this.oModel;
	}

	public String getDisplayValue() throws Exception {
		return this.getSelectedDescription(); 
	}
	
	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		JOrderedMap<String, Serializable> map = this.getModel().getValues(this.isModoCnsulta() || !this.isEditable());
		JIterator<String> iter=map.getKeyIterator();
		String sValue;
		String selectedValue=null;
		String selectedDescripcion=null;
		while (iter.hasMoreElements()) {
			sValue=iter.nextElement();
			String descripcion = this.getModel().getDescription(sValue);
			boolean isHtml = this.getModel().isHtmlDescription(sValue);
			boolean isSelected =isSelectedValue(sValue);
		//	if (sValue.equals("")) continue;
			zContent.startNode("item");
			if (isUseCache() && !sValue.equals("")) {
				String id = isSelected?zContent.addObjectObj(map.getElement(sValue)): zContent.addObjectObjTemp(map.getElement(sValue));
				zContent.setAttribute("id", id);
				zContent.setAttribute("real_id", sValue);
				zContent.setAttribute("id_name", JTools.getValidFilename(id));
				
			} else {
				zContent.setAttribute("id", sValue);
				zContent.setAttribute("real_id", sValue);
				zContent.setAttribute("id_name", JTools.getValidFilename(sValue));
				
			}

			if (descripcion.equals("--------------------")) {
				zContent.setAttributeEscape("description", descripcion);
				zContent.setAttribute("separator", true);
			}
			else {
				if (bConvertForeignCharsForWeb)
					zContent.setAttributeEscape("description", JTools.replaceForeignCharsForWeb(descripcion));
				else if (isHtml)
					zContent.setAttribute("description", descripcion);
				else
					zContent.setAttributeNLS("description", descripcion);
				
	
				if (isSelected) {
					selectedValue=sValue;
					selectedDescripcion=(sValue.equals("")?"":(showKey?sValue+"-":""))+descripcion;
					zContent.setAttribute("selected", true);
				}
			  JWebIcon icon = this.getModel().getIcon(sValue);
			  if (icon!=null) {
			  	icon.setClassImage(this.getModel().getExtraClassImage());
			  	icon.toXML(zContent);
			  } else {
				  int nroIcon = this.getModel().getNroIcon(sValue);
  				if (nroIcon!=-1) {
  					JWebIcon webIcon = JWebIcon.findIcon(nroIcon);
  					if (webIcon!=null)
  						webIcon.toXML(zContent);
  				} 
			  }
			}
			zContent.endNode("item");
		}
		if (selectedValue!=null) {
			zContent.startNode("selected");
					zContent.setAttribute("value", selectedValue);
					zContent.setAttribute("description",  selectedDescripcion);
			zContent.endNode("selected");
		}
	}

	public boolean isSelectedValue(String sValue) {
		if (this.sSelectedValue==null) return false;
		if (this.sSelectedValue.equals("")) return false;
		if (sValue.equals(this.sSelectedValue)) return true;
		if ((","+this.sSelectedValue+",").indexOf(","+sValue+",")!=-1) return true; // caso multipe:  sSelctedValue= 2,3,4 
		return false;
	}
	public String getSelectedValue() {
		return this.sSelectedValue;
	}

	public void setSelectedValue(String zValue) throws Exception {
		this.setValueFromDBString(zValue);
	}

	public String getSelectedDescription() throws Exception {
		if (this.sSelectedValue==null) return "";
		String sDesc=this.getModel().getDescription(this.sSelectedValue);
		if (sDesc==null) return "";
		return sDesc;
	}

	//
	// value providing
	//

	@Override
	public String getValueAsUIString() throws Exception {
		return this.getSelectedValue();
	}

	@Override
	public void setValueFromUIString(String zValue) throws Exception {
		this.setValueFromDBString(zValue);
	}

	@Override
	public String getValueAsDBString() throws Exception {
		return this.getSelectedValue();
	}

	@Override
	public void setValueFromDBString(String zDBString) throws Exception {
//		if (this.getModel().hasValue(zDBString)) {
			this.sSelectedValue=zDBString;
//		} else {
//			this.sSelectedValue="";
//		}
	}

	@Override
	public Object getValue() throws Exception {
		if (this.oModel instanceof JControlComboValueChooserModel) {
			return ((JControlComboValueChooserModel) this.oModel).getWin(this.getSelectedValue());
		} else {
			return this.getSelectedValue();
		}
	}
	@Override
	public void setValue(JObject zVal) throws Exception {
		if (zVal.isRecord()) {
			JWin win = ((JControlComboValueChooserModel) this.oModel).getWins(true).getWinRef().getClass().newInstance();
			win.setRecord((JRecord)zVal.getObjectValue());
			setValue(win);
			this.setSelectedValue(win.GetValorItemClave());
		} else
			setValue(zVal.getObjectValue());
	}

	@Override
	public void setValue(Object zValue) throws Exception {
		if (zValue==null) {
			this.setSelectedValue(null);
		} else if (this.oModel instanceof JControlComboValueChooserModel&&zValue instanceof JWin) {
			this.setSelectedValue(((JWin) zValue).GetValorItemClave());
		} else {
			this.setSelectedValue((String) zValue);
		}
	}

	
	@Override
	public void clear() throws Exception {
		this.sSelectedValue="";
		this.getModel().blanquear();
	}

	private boolean modoConsulta=false;
	public void setModoConsulta(boolean v) {
		this.modoConsulta = v;
	}

	public boolean isModoCnsulta() {
		return this.modoConsulta;
	}

}
