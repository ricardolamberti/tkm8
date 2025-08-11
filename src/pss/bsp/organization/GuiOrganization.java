package  pss.bsp.organization;

import pss.bsp.organization.detalle.GuiOrganizationDetails;
import pss.common.event.mailing.BizBSPPersona;
import pss.common.event.mailing.GuiBSPPersona;
import pss.common.event.mailing.GuiBSPPersonas;
import pss.common.regions.divitions.BizPaisLista;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;

public class GuiOrganization extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiOrganization() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizOrganization(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Organización"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormOrganization.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizOrganization GetcDato() throws Exception { return (BizOrganization) this.getRecord(); }

  public void createActionMap() throws Exception {
 		super.createActionMap();
 		this.addAction(10, "Iata miembros", null, 10020, false, false);
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	 	return super.OkAction(a);
  }
  

    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getDetail());
			return super.getSubmitFor(a);
	}

	public JWins getDetail() throws Exception {
		GuiOrganizationDetails wins = new GuiOrganizationDetails();
		wins.getRecords().addFilter("id_org", GetcDato().getId());
		wins.getRecords().addFilter("company", GetcDato().getCompany());
			return wins;
	}
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {

		return super.Drop(zBaseWin);
	}
 }
