package pss.common.pki.ejbca;

import pss.common.pki.signs.GuiSigns;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiPKIConfiguracion extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPKIConfiguracion() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPKIConfiguracion(); }
  public int GetNroIcono()   throws Exception { return 10046; }
  public String GetTitle()   throws Exception { return "Configurador validador"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPKIConfiguracion.class; }
  public Class<? extends JBaseForm> getFormEmbedded() throws Exception { return FormPKIConfiguracionEmbedded.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "pki_address"; }
  public BizPKIConfiguracion GetcDato() throws Exception { return (BizPKIConfiguracion) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
  	createActionUpdate();
		addAction(10, "Firmas", null, 10003, false, false);
		super.createActionMap();
	}


	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getFirmas());
		return null;
	}



	public JWins getFirmas() throws Exception {
		GuiSigns cert=new GuiSigns();
		cert.getRecords().addFilter("company", GetcDato().getCompany());
		return cert;
	}

}