package  pss.common.help;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.winUI.forms.JBaseForm;

public class GuiUserManual extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiUserManual() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizUserManual();
	}
	
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 10000;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Manual de Usuario";
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return null;
	}

	@Override
	public String getKeyField() throws Exception {
		return "id";
	}

	@Override
	public String getDescripField() {
		return "file";
	}

	public BizUserManual GetcDato() throws Exception {
		return (BizUserManual) this.getRecord();
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(10, "Obtener", null, 10000, true, true, true, "Group" ).setNuevaVentana(true);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return getUserManual(a);
		return null;
	}

  private JAct getUserManual(BizAction a) throws Exception {
  	return new JActFileGenerate(this, a.getId()) {
			public String generate() throws Exception {
				return GetcDato().getFile() ;
			}
		};		
	}
}
