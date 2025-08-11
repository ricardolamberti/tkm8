package  pss.bsp.interfaces.copa.datos;

import pss.bsp.parseo.JParseoFactory;
import pss.bsp.parseo.bspParseo.IInterfazParseo;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiCopa extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCopa() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCopa(); }
  public int GetNroIcono()   throws Exception { return 10056; }
  public String GetTitle()   throws Exception { return "Interfaz Copa"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCopa.class; }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormNewCopa.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizCopa GetcDato() throws Exception { return (BizCopa) this.getRecord(); }
   public void createActionMap() throws Exception {
		createActionQuery();
		createActionDelete();
 		this.addAction(10, "Cabecera", null, 10020, false, false, true, "Group");
 		this.addAction(20, "Detalle", null, 10020, false, false, true, "Group");
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
//		if (a.getId() == 55)	return new JActNew(GuiModuloBSP.getBSPConsolaUsuario().getNewBO(),0);
		if (a.getId() == 10)	return new JActQuery(this.getHeader());
		if (a.getId() == 20)	return new JActWins(this.getDetail());

		return super.getSubmitFor(a);
	}

	public JWin getHeader() throws Exception {
		IInterfazParseo parseo = (IInterfazParseo)JParseoFactory.getInstanceFromClass(GetcDato().getParseo());
		return parseo.getGuiHeader(GetcDato().getCompany(), GetcDato().getOwner(), GetcDato().getIdpdf());
	}
	public JWins getDetail() throws Exception {
		IInterfazParseo parseo = (IInterfazParseo)JParseoFactory.getInstanceFromClass(GetcDato().getParseo());
		return parseo.getGuisDetail(GetcDato().getCompany(), GetcDato().getOwner(), GetcDato().getIdpdf());
	}

	
 }
