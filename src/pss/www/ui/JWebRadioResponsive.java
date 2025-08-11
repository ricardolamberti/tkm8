package pss.www.ui;

import java.awt.Dimension;

import pss.core.tools.collections.JIterator;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebRadioResponsive  extends JWebAbstractValueChooser implements JWebActionable {

	private String sOnClickEvent="";
	private String sClassRadio="";

	private int iOrientation=ORIENTATION_TOOGLE;
	
	public JWebRadioResponsive() {
		bConvertForeignCharsForWeb = true;
	}

	public JWebRadioResponsive(int zOrientation) {
		this.iOrientation=zOrientation;
	}
	
	protected boolean checkConstraints() {
		return false;
	}


	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		if (this.iOrientation==ORIENTATION_TOOGLE) {
			zContent.setAttribute("orientation", "toogle");
		} if (this.iOrientation==ORIENTATION_HORIZONTAL) {
			zContent.setAttribute("orientation", "horizontal");
		} else {
			zContent.setAttribute("orientation", "vertical");
		}
		zContent.setAttribute("class_radio", sClassRadio);
		zContent.setAttribute("onclick_event", sOnClickEvent);
		super.widgetToXML(zContent);
	}

	@Override
	public String getComponentType() {
		return "radio_button_set_responsive";
	}

	@Override
	protected Dimension getDefaultSize() {
		return new Dimension(150, 24);
	}

	public int getOrientation() {
		return this.iOrientation;
	}

	public void setOrientation(int i) {
		this.iOrientation=i;

	}
	public String getClassRadio() {
		return sClassRadio;
	}

	public void setClassRadio(String sClassRadio) {
		this.sClassRadio = sClassRadio;
	}

	public void setOnChangeEvent(String zOnClickEvent) {
		sOnClickEvent=zOnClickEvent;
	}

	public static JWebRadioResponsive create(JWebViewComposite parent, JFormRadioResponsive control) throws Exception {
		JWebRadioResponsive oButtonSet=new JWebRadioResponsive();
		JWebDefaultValueChooserModel oModel=new JWebDefaultValueChooserModel();
		oButtonSet.takeAttributesFormControlResponsive(control);
		oButtonSet.setOrientation(control.getOrientation());
		JIterator<String> oValueIt=control.getValuesInOrder();
		JIterator<String> buttons=control.getLabelsInOrder();
		while (buttons.hasMoreElements()) {
			String oButton=buttons.nextElement();
			oModel.addValue(oValueIt.nextElement(), oButton);
		}
		oButtonSet.setClassRadio(control.getClassRadio());
		oButtonSet.setModel(oModel);
		
		if (parent!=null) parent.addChild(control.getName(), oButtonSet);
		return oButtonSet;
		

	}

  public JWebAction getWebAction() throws Exception {
  	if (!this.hasToRefreshForm()) return null;
  	return this.getObjectProvider().getWebSourceAction();
  }
}
