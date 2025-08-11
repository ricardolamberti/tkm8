package pss.www.ui;

import java.awt.Dimension;
import java.text.DecimalFormat;

import javax.swing.Icon;
import javax.swing.JTextField;

import org.w3c.dom.Element;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormSwingEdit;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

/**
 * 
 * 
 * Created on 12-jun-2003
 * 
 * @author PSS
 */

public class JWebTextField extends JWebTextComponent  implements JWebActionable {

	boolean bPopUpIcon=true;
	JFormControl oEdit=null;
 
	public void setPopupIcon(boolean b) {
		this.bPopUpIcon=b;
	}

	@Override
	public String getComponentType() {
		return isResponsive()?"text_field_responsive":"text_field";
	}

	public static JWebTextField create(JWebViewComposite parent, JTextField zComp, JFormSwingEdit zEdit) throws Exception {
		JWebTextField webText=new JWebTextField();
		webText.takeAttributesForm(parent,zComp);
		webText.takeAttributesFormControl(zEdit);
		webText.setResponsive(zEdit.isResponsive());
		if (webText.getToolTip()==null&&zComp.getToolTipText()!=null) webText.setToolTip(zComp.getToolTipText());
		if (zComp.getParent()==null) webText.setVisible(false);
		webText.setSize(webText.getSize().width, webText.getSize().height);
//		webText.setValue(zEdit.getValue());
		if (parent!=null) parent.addChild(zEdit.getName(), webText);
		webText.setPopupIcon(zEdit.hasPopupIcon());
		if (zEdit.isOutstanding() && webText.getSkinStereotype().indexOf("_oustanding")==-1) {
			webText.setSkinStereotype(webText.getSkinStereotype()+"_oustanding");
		}
		webText.oEdit=zEdit;

		return webText;
	}

//	private void generateOnCalculate(JXMLContent zContent) throws Exception {
//		if ( this.getForm().isModoConsulta()) return ;
//		if (oEdit==null) return ;
//		if (oEdit.getProp()==null) return ;
//		if (!oEdit.getProp().hasScript()) return ;
//		String onCalculate="";
//		String formatLinea = "";
//		JWebEditComponentContainer form = this.getForm();
//		JScript oScript=oEdit.getProp().getObjScript();
//		if (oScript==null) return ;		LinkedHashMap<String, String> map= new LinkedHashMap<String, String>();		
//		JIterator<String> i = oScript.getBind().getKeyIterator();		
//		while (i.hasMoreElements()) {			
//			String key = i.nextElement();			
//			String value = (String)oScript.getBind().getElement(key);			
//			JFormControl c = oEdit.getControls().findControl(value);
//			if (c==null) continue;
//			map.put(key+"=", "document.getElementById('dgf_"+form.getFormName()+"_fd."+value+"').value=" );
//			map.put(key, "resolve('dgf_"+form.getFormName()+"_fd."+value+"')");
//			formatLinea+="formatear('dgf_"+form.getFormName()+"_fd."+value+"');";
//		}
//		if (oScript.isCalculeOthersFields())
//			onCalculate=oScript.getFormulaInContext(map)+";"+formatLinea;
//		else
//		  onCalculate="document.getElementById('dgf_"+form.getFormName()+"_fd."+this.getName()+"').value="+oScript.getFormulaInContext(map)+";";
//		zContent.setAttribute("onCalculate", onCalculate);
//		if (oScript.isCalculeOthersFields()) zContent.setAttribute("isCalculeOthersFields", oScript.isCalculeOthersFields());
//		if (oScript.isCalculeOnStart()) zContent.setAttribute("isCalculeOnStart", oScript.isCalculeOnStart());
//	}
	

	@Override
	public boolean isFormatAllowed() throws Exception {
		if (this.getParent()!=null && this.getForm().isModoConsulta()) return true;
		if (this.getFormatter() instanceof DecimalFormat) return false;
		return true;
	}

	@Override
	protected char getGroupingSeparator() throws Exception {
		if (this.getParent()!=null && this.getForm().isModoConsulta()) return super.getGroupingSeparator();
		return ',';
	}

	@Override
	protected char getDecimalSeparator() throws Exception {
		if (this.getParent()!=null && this.getForm().isModoConsulta()) return super.getDecimalSeparator();
		return '.';
	}

	@Override
	protected void sizeToXML(JXMLContent zContent) throws Exception {
//		generateOnCalculate(zContent);
		if (this.isEditable()==false) {
			super.sizeToXML(zContent);
			return;
		}
		// String type = this.getController().GetObjectType();
		this.addPopupIcon(zContent);
		super.sizeToXML(zContent); // SAX does not replace existing attributes
	}

	private void addPopupIcon(JXMLContent zContent) throws Exception {
		if (!this.hasPopupIcon()) return;
		Element oIconDOM=this.getSkinChildNode("foreground/icon");
		if (oIconDOM==null) return;
		Icon oIcon=BizUsuario.retrieveSkin().getIcon(oIconDOM);
		if (oIcon==null) return;
		zContent.setAttribute("pop_up_icon", oIconDOM.getAttribute("file"));
		Dimension oSize=this.getSize();
		if (oSize==null) return;
		int iIconWidth=oIcon.getIconWidth();
		int iIconHeight=oIcon.getIconHeight();
		int iGap=0;
		if (oSize.width<0) return;
		zContent.setAttribute("width", oSize.width-iIconWidth);
		zContent.setAttribute("icon_x", this.getLocationLazyly().x+oSize.width-iIconWidth+iGap);
		zContent.setAttribute("icon_y", iIconHeight+1);
	}

	protected boolean hasPopupIcon() throws Exception {
		if (!this.bPopUpIcon) return false;
		String type=this.getController().GetObjectType();
		if (type.equals(JObject.JFLOAT)) return true;
		if (type.equals(JObject.JINTEGER)) return true;
		if (type.equals(JObject.JCURRENCY)) return true;
		if (type.equals(JObject.JLONG)) return true;
		if (type.equals(JObject.JUNSIGNED)) return true;
		return false;
	}
  public JWebAction getWebAction() throws Exception {
  	if (!this.hasToRefreshForm()) return null;
  	return this.getObjectProvider().getWebSourceAction();
  }
  

}