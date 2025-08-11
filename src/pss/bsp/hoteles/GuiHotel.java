package  pss.bsp.hoteles;

import pss.bsp.hoteles.detalle.GuiHotelDetails;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiHotel extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiHotel() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizHotel(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Grupo hoteles"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormHotel.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizHotel GetcDato() throws Exception { return (BizHotel) this.getRecord(); }

  public void createActionMap() throws Exception {
 		super.createActionMap();
 		this.addAction(10, "Detalle Hoteles", null, 10020, false, false, true, "Group");
 		this.addAction(20, "Recalcular", null, 15016, true, true, true, "Group");
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
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
		GuiHotelDetails wins = new GuiHotelDetails();
		wins.getRecords().addFilter("id_hotel", GetcDato().getId());
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
