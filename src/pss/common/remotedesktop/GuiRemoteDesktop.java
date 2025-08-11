package pss.common.remotedesktop;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiRemoteDesktop extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiRemoteDesktop() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizRemoteDesktop(); }
  @Override
	public int GetNroIcono()   throws Exception { 
  	return 15018;
  }
  @Override
	public String GetTitle()   throws Exception { return "Consola"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormRemoteDesktop.class; }
  @Override
	public String  getKeyField() throws Exception { return "comando"; }
  @Override
	public String  getDescripField() { return "resultado"; }
  public BizRemoteDesktop GetcDato() throws Exception { return (BizRemoteDesktop) this.getRecord(); }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  
	@Override
	public void createActionMap() throws Exception {
	  	this.addAction(10, "Run", null, 5005, true, false, true, "Group");
	  	this.addAction(20, "Upload", null, 5005, true, false, true, "Group");
	}

	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10) 
			return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().upload();
				GetcDato().ejecutar();
			
			}
		};
		if (a.getId() == 20) 
			return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
			
				setMessage(	GetcDato().upload());
			}
		};
		return super.getSubmit(a);
	}
 }
