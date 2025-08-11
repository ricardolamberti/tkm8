package pss.www.ui;

import javax.swing.JTextField;

import pss.core.winUI.controls.JFormSwingEdit;

public class JWebLabelData extends JWebTextField {

	@Override
	public String getComponentType() {
		return "text_label";
	}

	public static JWebLabelData create(JWebViewComposite parent, JTextField zComp, JFormSwingEdit zEdit) throws Exception {
		JWebLabelData webText=new JWebLabelData();
		webText.takeAttributesForm(parent,zComp);
		webText.takeAttributesFormControl(zEdit);
		webText.setResponsive(zEdit.isResponsive());
		if (webText.getToolTip()==null&&zComp.getToolTipText()!=null) webText.setToolTip(zComp.getToolTipText());
		if (zComp.getParent()==null) webText.setVisible(false);
		webText.setSize(webText.getSize().width, webText.getSize().height);
//		webText.setValue(zEdit.getValue());
		if (parent!=null) parent.addChild(zEdit.getName(), webText);
		webText.setPopupIcon(zEdit.hasPopupIcon());
		if (zEdit.isOutstanding() && webText.getSkinStereotype().indexOf("_outstanding")==-1) {
			webText.setSkinStereotype(webText.getSkinStereotype()+"_outstanding");
		}
		webText.oEdit=zEdit;

		return webText;
	}


}
