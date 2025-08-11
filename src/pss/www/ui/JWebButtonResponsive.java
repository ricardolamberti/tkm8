package pss.www.ui;

import java.io.Serializable;

import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebButtonResponsive  extends JWebButton  implements JWebControlInterface, JWebIconHolder , JWebLabelHolder{
  
	JWebIcon icon;
  boolean back;
  boolean extra;

	private Serializable data; //datos libre para enviar dentro de un submit

	public Serializable getData() {
		return data;
	}

	public void setData(Serializable data) {
		this.data = data;
	}
 
  public boolean isBack() {
		return back;
  }

	public void setBack(boolean back) {
		this.back = back;
	}

  public JWebButtonResponsive() {
  }

  public JWebButtonResponsive(String zLabel) {    
    this.setLabel(zLabel);
  }

  @Override
	public String getComponentType() {
    return "web_button_responsive";
  }
  
	public JWebIcon getIcon() {
		return icon;
	}

	public void setIcon(JWebIcon icon) {
		this.icon = icon;
	}
  public boolean isExtra() {
		return extra;
	}

	public void setExtra(boolean extra) {
		this.extra = extra;
	}

//	@Override
//	public JWebActionOwnerProvider getObjectProvider()  {
////		if (forceProvider) {
////			return findActionOwnerProvider(forceProvider);
////		}
//		try {
//			JWebActionOwnerProvider result = null;
//			if (getLabel().equalsIgnoreCase("abm.button.delete"))
//				result =  findActionOwnerProvider("stockDetails");
//			if (result==null) 
//				return super.getObjectProvider();
//			return result;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			return super.getObjectProvider();
//		}
//	//	return super.getObjectProvider();
//	}

	public static JWebButtonResponsive create(JWebViewComposite parent, JFormButtonResponsive zControl) throws Exception {
		JWebButtonResponsive webButton=new JWebButtonResponsive();
		webButton.takeAttributesFormControlResponsive(zControl);
		webButton.setResponsive(zControl.isResponsive());
		webButton.setLabel(zControl.getLabel());
		webButton.setEnterIsTab(zControl.isEnteristab());
		webButton.setIcon(zControl.getImagen());
		webButton.setData(zControl.getData());
		webButton.setBack(zControl.isBack());
		
		webButton.setExtra(false);
		if (parent!=null) parent.addChild(zControl.getName(), webButton);
		if (parent instanceof JWebFilterPanel) ((JWebFilterPanel)parent).addButtons(webButton);
		return webButton;
	}
	public static JWebButtonResponsive createExtraButton(JFormButtonResponsive zControl,JWebViewComposite zParent) throws Exception {
		JWebButtonResponsive webButton=new JWebButtonResponsive();
		webButton.takeAttributesFormControlResponsive(zControl);
		webButton.setResponsive(zControl.isResponsive());
		webButton.setLabel(zControl.getLabel());
		webButton.setIcon(zControl.getImagen());
		webButton.setParent(zParent);
		webButton.setScript(zControl.getScript());
		webButton.setData(zControl.getData());
		webButton.setExtra(true);


		return webButton;
	}
	
	 public JWebAction getWebAction()  throws Exception{
	  	if (super.getWebAction()!=null) return super.getWebAction();
	  	if (!this.hasToRefreshForm()) return null;
	  	JWebAction a= this.getObjectProvider().getWebSourceAction();
	  	a.setActionParent(a);
	  	return a;
	  }
	
	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		// TODO Auto-generated method stub
		JFormButtonResponsive zControl = (JFormButtonResponsive) getControl();
		if (getData()!=null)
			zContent.setAttribute("data", zContent.addObjectObjTemp(getData()));
		if (getControl()!=null&&getControl().ifReadOnly())
			zContent.setAttribute("readonly", true);

		if (zControl!=null) {
			zContent.setAttribute("is_submit", zControl.isSubmit());
//	  	zContent.setAttribute("align", zControl.resolveAlignContent());
			if (!extra) {
				if (zControl.getAction()!=null) {
					zControl.getAction().setUploadData( this.getForm().isUploadData());
					String objid= this.getForm().isCard() ? getObjectProviderWithCard().getRegisteredObjectId():zControl.getAction().getObjOwner().getUniqueId(); //HGK ya nos pisamos varias veces, dejar asi 
					this.setWebAction(JWebActionFactory.createViewAreaAndTitleAction(zControl.getAction(),this.getObjectProvider(),zControl.isSubmit(), objid, zControl.findRow()));
					if (zControl.getData()!=null)
							this.getWebAction().setData(zControl.getData());
				} else if (zControl.isBack()) {
					this.setWebAction(JWebActionFactory.createGoBack());
				} 			
			} else {
				if (zControl.getAction()!=null) {
					zControl.getAction().setUploadData( this.getForm().isUploadData());
					this.setWebAction(JWebActionFactory.createViewAreaAndTitleAction(zControl.getAction(),this.getObjectProvider(),zControl.isSubmit(),zControl.getAction().getObjOwner().getUniqueId()));
			
				} else if (zControl.isBack()) {
					this.setWebAction(JWebActionFactory.createGoBack());
					
				}			
			}
		}
		super.componentToXML(zContent);
	}
	
	@Override
	public void clear() throws Exception {
	
	}

	@Override
	public String getDisplayValue() throws Exception {
		return null;
	}



	@Override
	public void onShow(String modo) throws Exception {
		
	}

	@Override
	public void setController(JWebFormControl control) {
		
	}

	@Override
	public void setEditable(boolean editable) throws Exception {
		
	}

	@Override
	public void setValue(String zVal) throws Exception {
		
	}

	@Override
	public void setValueFromUIString(String zVal) throws Exception {
	
	}
	

}
