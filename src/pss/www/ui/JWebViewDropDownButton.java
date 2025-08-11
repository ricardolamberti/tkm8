package pss.www.ui;

import pss.core.tools.collections.JIterator;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.core.winUI.responsiveControls.JFormDropDownButtonResponsive;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebViewDropDownButton extends JWebViewInternalComposite implements JWebControlInterface,JWebArrow,JWebLabelHolder {

	String classButton="btn btn-primary dropdown-toggle";
	JWebIcon arrow;
	String label;
	private String image;
	private String labelButton;

	private boolean editable;
	public boolean isEditable() {
		return editable;
	}
	public String getLabelButton() {
		return labelButton;
	}

	public void setLabelButton(String labelbutton) {
		this.labelButton = labelbutton;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	JFormDropDownButtonResponsive control;
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getClassButton() {
		return classButton;
	}

	public void setClassButton(String classButton) {
		this.classButton = classButton;
	}

	public JWebIcon getArrow() {
		return arrow;
	}

	public void setArrow(JWebIcon arrow) {
		this.arrow = arrow;
	}

	public JWebViewDropDownButton() {
	}
	
	public static JWebViewDropDownButton create(JWebViewComposite parent, JFormDropDownButtonResponsive zControl) throws Exception {
		JWebViewDropDownButton webButton=new JWebViewDropDownButton();
		webButton.takeAttributesFormControlResponsive(zControl);
		webButton.setResponsive(zControl.isResponsive());
		webButton.setLabelButton(zControl.getLabelButton());
	//	webButton.setEditable(!zControl.ifReadOnly());
		if (zControl.getArrow()!=null)
			webButton.setArrow(zControl.getArrow());
		webButton.setClassResponsive("btn-group");
		webButton.setClassButton("btn btn-primary dropdown-toggle btn-sm");
		webButton.setLabel(zControl.getLabel());
		if (zControl.getImagen()!=null) {
		  webButton.setImage(zControl.getImagen().getURL());
		}
		webButton.control=zControl;

		if (parent!=null) parent.addChild(zControl.getName(), webButton);
		return webButton;
	}
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
    zContent.setAttribute("image", image);
		zContent.setAttribute("class_button", classButton);
		zContent.setAttribute("label_button", labelButton);
		zContent.setAttribute("visible", isVisible());
		zContent.setAttribute("editable", isEditable());
		if (control==null) return;
		
		JIterator<JFormControl> oControlsIt = control.getControles().GetItems();
		while (oControlsIt.hasMoreElements()) {
			JFormControl oFormControl = (JFormControl) oControlsIt.nextElement();
			if (!(oFormControl instanceof JFormButtonResponsive)) continue;
			JFormButtonResponsive button = (JFormButtonResponsive)oFormControl;
			if ( button.getAction()!=null) {
				JWebLink oLink = new JWebLink();
				button.getAction().setUploadData( oLink.getForm().isUploadData());
				JWebServerAction webAction = JWebActionFactory.createViewAreaAndTitleAction( button.getAction(),	this.getObjectProvider(), button.isSubmit(),null);
				oLink.setLabel(button.getLabel());
				oLink.setWebAction(webAction);
				if (button.getTitle()!=null)
					oLink.setTitle(button.getTitle());
				oLink.setCancel(webAction.isCancel());
				oLink.setAccessKey(webAction.getKey());
				oLink.setImportant( button.getAction().getImportance());
				oLink.setOpensNewWindow(webAction.isNuevaVentana());
				oLink.setVisible(true);
				JWebElementList li = new JWebElementList();
				li.addChild(button.getAction().getIdAction()+"_button", oLink);
				this.addChild(button.getAction().getIdAction(), li);
				
			} 
		

		}
		
	}

	@Override
	public String getComponentType() {
		return "dropdown"+(control==null?"":"_responsive");
	}

	@Override
	public void onShow(String modo) throws Exception {
	
	}

}
