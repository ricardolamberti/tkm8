package pss.bsp.consolid.model.liquidacion.detail;

import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRTicket;

public class GuiLiqDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLiqDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLiqDetail(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Detalle"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLiqDetail.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "nro_boleto"; }
  public BizLiqDetail GetcDato() throws Exception { return (BizLiqDetail) this.getRecord(); }

  public void createActionMap() throws Exception {
		this.addAction(10, "Ver Boleto", null, 10020, false, false, true, "Group");
		
	
		this.createActionQuery();
		this.createActionDelete();

	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
			if (a.getId()==10) return GetcDato().getInterfaceId()!=0 && getTicket()!=null;
			if (a.getId()==3) return  !GetVision().equals("DK_ADMIN") && !GetcDato().getObjHeader().getEstado().equals(BizLiqHeader.PUBLICADO);
				
		return super.OkAction(a);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		
		if (a.getId()==10) return new JActQuery(getTicket());
		
		return super.getSubmitFor(a);
	}
	
	public GuiPNRTicket getTicket() throws Exception {
		GuiPNRTicket tkt = new GuiPNRTicket();
		tkt.GetcDato().dontThrowException(true);
		tkt.GetcDato().read(GetcDato().getInterfaceId());
		return tkt;
	}
	
 }
