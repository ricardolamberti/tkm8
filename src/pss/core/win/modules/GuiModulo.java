package pss.core.win.modules;

import pss.common.components.BizCompInstalado;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.JAplicacion;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;

public class GuiModulo extends JWin {

	private String sModuleName = "";
  private BizCompInstalado oCompAsociado = null;


	public GuiModulo() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizModulo();
	}

	//-------------------------------------------------------------------------- //
	// Agrego un Item al Modulo
	//-------------------------------------------------------------------------- //
	public void SetModuleName(String sName) {
		sModuleName = sName;
	}
	public String GetModuleName() {
		return sModuleName;
	}
	@Override
	public String getDescripFieldValue() throws Exception {
		return this.GetModuleName();
	}
  public void setCompAsociado(BizCompInstalado zComp) {
    oCompAsociado = zComp;
  }
  public BizCompInstalado getCompAsociado() throws Exception {
    if ( oCompAsociado != null ) return oCompAsociado;
    oCompAsociado = new BizCompInstalado();
    oCompAsociado.setComponente(this.getDirectory());
    return oCompAsociado;
  }

  private String getDirectory() throws Exception {
    String sDirectorio = this.getClass().getName();
    sDirectorio = sDirectorio.substring(0, sDirectorio.lastIndexOf("."));
    sDirectorio = sDirectorio.replace('.', '/');
    return sDirectorio;
  }

	@Override
	public String GetTitle() throws Exception {
		String title = super.GetTitle();
		if (title == null || title.trim().length() < 1) {
			return "Módulo {" + JLanguage.translate(sModuleName) + "}";
    } else {
      return title;
    }

	}
	//-------------------------------------------------------------------------- //
	// Devuelvo el nombre del Modulo
	//-------------------------------------------------------------------------- //
	@Override
	public String toString() {
		if (this.getDescripField().length() == 0)
			return sModuleName;
		return this.getDescripField();
	}

	public BizModulo GetcDato() throws Exception {
		return (BizModulo)this.getRecord();
	}

	// Does nothing for a GuiModulo
//	public void WebBind(JPssSession session, JPssRequest request) throws Exception {
//
//		this.bWasBinded = true;
//
//		// Levanta acciones y acción dinámica
//		generateActionMap(true);
//		createDynamicAction();
//	}

  public BizAction createDynamicAction() throws Exception { return null; }

  @Override
	public void createGenericActionMap() throws Exception {}
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		return this.getSubmitFor(a);
	}
	
	

	//-------------------------------------------------------------------------- //
	// Accion a sobreescribir para describir el proceso de depuracion de cada
	// modulo en si mismo
	//-------------------------------------------------------------------------- //
	public void FormDepurar() throws Exception {
	}

	public boolean isModuleComponent() throws Exception {
		return true;
	}
	
  public void appendSubActions(BizAction zAction) throws Exception {
  	this.createActionMap();
    JIterator iter = this.getActionMap().getStaticIterator();
    while ( iter.hasMoreElements() ) {
      BizAction action = ( BizAction ) iter.nextElement();
      zAction.addSubAction(action);
    }
  } 




	public void loadDynamicActions(BizAction zAction) throws Exception {
		GuiModulos oMods = new GuiModulos();
    oMods.setModuloPadre(this);
		oMods.readAll();
		oMods.firstRecord();
		while (oMods.nextRecord()) {
			GuiModulo subModulo = (GuiModulo)oMods.getRecord();
			this.loadMyDynamicActions(subModulo);
		}
	}
	
	private void loadMyDynamicActions(GuiModulo subModulo) throws Exception {
		BizAction dynamicAction = subModulo.createDynamicAction();
		subModulo.clearActions();
		if (dynamicAction == null) return;
		dynamicAction.changeOwner(this.getClass().getName());
		this.addAction(dynamicAction);
		subModulo.appendSubActions(dynamicAction);
	}
	

  protected boolean isWindowsApplication() throws Exception {
    return JAplicacion.GetApp().ifAppFrontEndWindows();
  }
  protected boolean isWebApplication() throws Exception {
    return JAplicacion.GetApp().ifAppFrontEndWeb();
  }

}
