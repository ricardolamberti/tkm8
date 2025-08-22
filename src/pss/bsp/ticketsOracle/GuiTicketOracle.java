package  pss.bsp.ticketsOracle;

import pss.bsp.consolid.model.trxOra.GuiTrxOras;
import pss.bsp.consolidador.IConsolidador;
import pss.bsp.consolidador.JConsolidadorOracle;
import pss.bsp.consolidador.consolidacionOracle.GuiConsolidacionOracles;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiTicketOracle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTicketOracle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTicketsOracle(); }
  public int GetNroIcono()   throws Exception { return 10056; }
  public String GetTitle()   throws Exception { return "Ticket Oracle"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return ticketOracle.class; }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormNewTicketOracle.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizTicketsOracle GetcDato() throws Exception { return (BizTicketsOracle) this.getRecord(); }
   public void createActionMap() throws Exception {
		createActionQuery();
		createActionDelete();
// 		this.addAction(55, "Agregar Interfaz BO", null, 10058, true, true, true, "Group");
		BizAction  a=this.addAction(40, "Conciliar", null, 10045, true, true, true, "Group");
		a.setConfirmMessageDescription("Realizar la conciliacion puede insumir varios minutos");
		a.setMulti(true);
 		this.addAction(35, "Ver Conciliacion PNR", null, 10047, true, true, true, "Group");
 		 a=this.addAction(41, "Enviar datos a PNRs", null, 10006, true, true, true, "Group");
		a.setConfirmMessageDescription("Realizar la conciliacion puede insumir varios minutos");
		a.setMulti(true);

 		this.addAction(20, "Detalle", null, 10020, false, false, true, "Group");
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {

  	return super.OkAction(a);
  }
  
  
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
//		if (a.getId() == 55)	return new JActNew(GuiModuloBSP.getBSPConsolaUsuario().getNewBO(),0);
		if (a.getId() == 20)	return new JActWins(this.getDetail());
		if (a.getId() == 35)	return new JActWins(this.getConciliacionPNR(false));
		if (a.getId() == 40)	return new JActSubmit(this) {
			public void submit() throws Exception {
				getConciliacionPNR(true);
			}  
		};	
		if (a.getId() == 41)	return new JActSubmit(this) {
			public void submit() throws Exception {
				Igualar();
			}  
		};
		return null;
	}

	
	public JWins getDetail() throws Exception {
		GuiTrxOras tkts = new GuiTrxOras();
		tkts.getRecords().addFixedFilter(
				"	WHERE fecha_emision \n"
				+ "      BETWEEN TO_DATE('"+JDateTools.DateToString( GetcDato().getPeriodoDesde(),"dd/MM/YYYY")+"', 'DD/MM/YYYY') \n"
				+ "          AND TO_DATE('"+JDateTools.DateToString( GetcDato().getPeriodoHasta(),"dd/MM/YYYY")+"', 'DD/MM/YYYY')\n"
			);
	//	tkts.getRecords().addFilter("fecha_cupon", GetcDato().getPeriodoDesde(),">=");
	//	tkts.getRecords().addFilter("fecha_cupon", GetcDato().getPeriodoHasta(),"<=");
		return tkts;
	}
	public void Igualar() throws Exception {
		GetcDato().execProcessToPNRs();
//		GetcDato().execProcessRepoblate();
	}

	public JWins getConciliacionPNR(boolean force) throws Exception {
		generateConciliancion(force);
		GuiConsolidacionOracles con =	new GuiConsolidacionOracles();
		con.getRecords().addFilter("company", GetcDato().getCompany());
//		con.getRecords().addFilter("owner", GetccDato().getOwner());
		con.getRecords().addFilter("tipo", IConsolidador.PNR);
		con.getRecords().addFilter("idPDF", GetcDato().getId());
		return con;
	}
	
	private void generateConciliancion(boolean force) throws Exception {
		JConsolidadorOracle cons = new JConsolidadorOracle();
		cons.setOracle(this);
		cons.consolidar(force);
	}
	
	
 }
