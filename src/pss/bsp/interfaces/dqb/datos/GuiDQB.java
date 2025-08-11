package  pss.bsp.interfaces.dqb.datos;

import pss.bsp.consolidador.IConsolidador;
import pss.bsp.consolidador.JConsolidador;
import pss.bsp.consolidador.consolidacion.detalle.GuiConsolidaciones;
import pss.bsp.parseo.JParseoFactory;
import pss.bsp.parseo.bspParseo.IInterfazParseo;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiDQB extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDQB() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDQB(); }
  public int GetNroIcono()   throws Exception { return 10056; }
  public String GetTitle()   throws Exception { return "Interfaz DQB"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDQB.class; }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormNewDQB.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizDQB GetcDato() throws Exception { return (BizDQB) this.getRecord(); }
   public void createActionMap() throws Exception {
 		BizAction  a=this.addAction(40, "Conciliar", null, 10045, true, true, true, "Group");
 		a.setConfirmMessageDescription("Realizar la conciliacion puede insumir varios minutos");
 		a.setMulti(true);
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
		if (a.getId() == 40)	return new JActSubmit(this) {
			public void submit() throws Exception {
				generateConciliancion(true);
			}  
		};	
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
	
	private void generateConciliancion(boolean force) throws Exception {
		GetcDato().execConsolidar();
	}
	
 }
