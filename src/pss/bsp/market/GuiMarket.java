package  pss.bsp.market;

import pss.bsp.market.detalle.GuiMarketsDetails;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiMarket extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMarket() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMarket(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Mercados"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMarket.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizMarket GetcDato() throws Exception { return (BizMarket) this.getRecord(); }

  public void createActionMap() throws Exception {
 		super.createActionMap();
 		this.addAction(10, "Rutas mercado", null, 10020, false, false, true, "Group");
 		this.addAction(20, "Recalcular", null, 15016, true, true, true, "Group");
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==BizAction.REMOVE) return BizUsuario.IsAdminCompanyUser();
  	if (a.getId()==BizAction.UPDATE) return BizUsuario.IsAdminCompanyUser();
  	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getDetail());
		if (a.getId() == 20)	return new JActSubmit(this){
			@Override
			public void submit() throws Exception {
				GetcDato().execReprocessAllTicket();
				super.submit();
			}
		};
		return super.getSubmitFor(a);
	}

	public JWins getDetail() throws Exception {
		GuiMarketsDetails wins = new GuiMarketsDetails();
		wins.getRecords().addFilter("id_market", GetcDato().getId());
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		return wins;
	}
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
//		if (zBaseWin instanceof GuiPaisLista) {
//			GuiPaisLista p = (GuiPaisLista) zBaseWin;
//			this.GetcDato().execProcessAddMiembro(p.GetcDato());
//		}
//		if (zBaseWin instanceof GuiPaisesLista) {
//			GuiPaisesLista p = (GuiPaisesLista) zBaseWin;
//			JIterator<BizPaisLista> it = p.getRecords().getStaticIterator();
//			while (it.hasMoreElements())
//				this.GetcDato().execProcessAddMiembro(it.nextElement());
//		}
		return super.Drop(zBaseWin);
	}
 }
