package pss.bsp.consolid.model.liquidacion.errors;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActUpdate;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRTicket;

public class GuiLiqError extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLiqError() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLiqError(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Informe"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLiqError.class; }
  public Class<? extends JBaseForm> getFormUpdate() throws Exception { return FormEditLiqError.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "error"; }
  public BizLiqError GetcDato() throws Exception { return (BizLiqError) this.getRecord(); }

  public void createActionMap() throws Exception {
		this.addAction(10, "Ver Boleto", null, 10020, true, true, true, "Group");
		this.addAction(20, "No es problema", null, 10020, true, true, true, "Group").setMulti(true);
		this.addAction(30, "Es problema", null, 10020, true, true, true, "Group");

	
		this.createActionQuery();

	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==10) return GetcDato().getInterfaceId()!=0;
		if (a.getId()==20) return !GetcDato().getTipoError().equals(BizLiqError.CR_OK);
		if (a.getId()==30) return GetcDato().getTipoError().equals(BizLiqError.CR_OK);
		
				
		return super.OkAction(a);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		
		if (a.getId()==10) return new JActQuery(getTicket());
		if (a.getId()==20) return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().execProcessNoProblem();
				super.submit();
			}
		};
		if (a.getId()==30) return new JActUpdate(this, 30);
		return super.getSubmitFor(a);
	}
	
	public GuiPNRTicket getTicket() throws Exception {
		GuiPNRTicket tkt = new GuiPNRTicket();
		tkt.GetcDato().read(GetcDato().getInterfaceId());
		return tkt;
	}
	@Override
	public String getFieldForeground(String zColName) throws Exception {
		if (zColName.equals("")||zColName.indexOf("control_")==-1 || GetcDato().getProp(zColName)==null || GetcDato().getProp(zColName).toFormattedString()==null)
			return super.getFieldForeground(zColName);
		return GetcDato().getProp(zColName).toString().indexOf("ORA:")==-1?"039e03":"DD0000";
	}
 }
