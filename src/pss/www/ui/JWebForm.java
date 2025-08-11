package pss.www.ui;

import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.www.platform.content.generators.JXMLContent;

public class JWebForm extends JWebViewInternalComposite implements JAbsolutelyNamedWebViewComponent, JWebEditComponentContainer {


	@Override
	public void destroy() {
		super.destroy();
	}

	public String getAbsoluteName() {
		return this.getName();
	}
	
  public boolean isCard() throws Exception {
  	return false;
  }

	@SuppressWarnings("unchecked")
	public Class getAbsoluteClass() {
		return JWebForm.class;
	}
  public boolean isUploadData() throws Exception {
  	return false;
  }

  public boolean isInLine() {
  	return false;
  }

	public JWebViewEditComponent findComponent(String zChildName) throws Exception {
		return this.findComponent(this, zChildName);
	}

	private JWebViewEditComponent findComponent(JWebViewComposite zComposite, String zChildName) throws Exception {
		JWebViewEditComponent oResult=null;
		JWebViewComponent oComp=(JWebViewComponent) zComposite.getChild(zChildName);
		if (oComp!=null&&oComp.isEditComponent()) {
			oResult=(JWebViewEditComponent) oComp;
		} else {
			JIterator<JWebViewComponent> oChildIt=zComposite.getChildren();
			while (oChildIt.hasMoreElements()&&oResult==null) {
				oComp=oChildIt.nextElement();
				if (oComp.isEditComponent()&&oComp.getName().equals(zChildName)) {
					oResult=(JWebViewEditComponent) oComp;
				} else if (oComp.isComposite()) {
					oResult=this.findComponent((JWebViewComposite) oComp, zChildName);
				}
			}
		}
		return oResult;
	}

	@Override
	public void add(String zChildName, JWebViewEditComponent zComponent) throws Exception {
		this.addChild(zChildName, zComponent);
	}

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
	}

	public JWin getWin() {
		return this.getForm().getWin();
	}



  public String getFormName() throws Exception {
  	return this.getName();
  }


	@Override
	public String getComponentType() {
		return "form";
		
	}
	
	public boolean isModoConsulta() throws Exception {
		return false;
	}
  public boolean isAlta() throws Exception {
  	return false;
  }

}