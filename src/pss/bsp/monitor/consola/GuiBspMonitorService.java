package  pss.bsp.monitor.consola;

import pss.bsp.contrato.GuiContratos;
import pss.bsp.monitor.log.BizBspLog;
import pss.bsp.monitor.log.GuiBspLogs;
import pss.common.event.sql.BizSqlEvent;
import pss.common.event.sql.GuiSqlEvents;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiBspMonitorService extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBspMonitorService() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBspMonitorService(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Monitor back"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMonitorService.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "company"; }
  public BizBspMonitorService GetcDato() throws Exception { return (BizBspMonitorService) this.getRecord(); }

  public void createActionMap() throws Exception {
 	//	super.createActionMap();
 		this.addAction(10, "Errores", null, 10020, false, false, true, "Group");
 		this.addAction(20, "Logs", null, 10020, false, false, true, "Group");
		this.addAction(30, "Indicadores en error", null, 10020, false, false, true, "Group");
		this.addAction(40, "Indicadores repro", null, 10020, false, false, true, "Group");
		this.addAction(50, "Indicadores ok", null, 10020, false, false, true, "Group");
		this.addAction(60, "Tickets pendientes", null, 10020, false, false, true, "Group");
		this.addAction(70, "Contratos pendientes", null, 10020, false, false, true, "Group");
	 }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
 	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getErrors());
		if (a.getId() == 20)	return new JActWins(this.getLogs());
		if (a.getId() == 30)	return new JActWins(this.getIndicadores());
		if (a.getId() == 40)	return new JActWins(this.getIndicadoresRepro());
		if (a.getId() == 50)	return new JActWins(this.getIndicadoresOk());
		if (a.getId() == 60)	return new JActWins(this.getPendientes());
		if (a.getId() == 70)	return new JActWins(this.getContratosPendientes());
		return super.getSubmitFor(a);
	}

	public JWins getErrors() throws Exception {
		GuiBspLogs wins = new GuiBspLogs();
		wins.getRecords().addFilter("log_type", BizBspLog.BSPLOG_ERROR);
		wins.getRecords().addOrderBy("log_date","DESC");
		
		return wins;
	}
	
	public JWins getLogs() throws Exception {
		GuiBspLogs wins = new GuiBspLogs();
		wins.getRecords().addOrderBy("log_date","DESC");
		return wins;
	}
	public JWins getPendientes() throws Exception {
		GuiPNRTickets wins = new GuiPNRTickets();
		wins.getRecords().addFilter("calculed",false);
			wins.SetVision("BACK");

		return wins;
	}
	public JWins getContratosPendientes() throws Exception {
		GuiContratos wins = new GuiContratos();
		wins.getRecords().addFixedFilter("where  (fecha_version_cambio IS DISTINCT FROM fecha_version_calculo) ");
		wins.SetVision("BACK");

		return wins;
	}
	public JWins getIndicadores() throws Exception {
		GuiSqlEvents wins = new GuiSqlEvents();
		wins.getRecords().addFilter("estado", BizSqlEvent.ERROR);
		wins.getRecords().addOrderBy("fecha_update", "ASC");
		wins.getRecords().addOrderBy("descripcion", "ASC");
		wins.SetVision("BACK");

		return wins;
	}

	public JWins getIndicadoresOk() throws Exception {
		GuiSqlEvents wins = new GuiSqlEvents();
		wins.getRecords().addFilter("estado", BizSqlEvent.OK);
		wins.getRecords().addOrderBy("fecha_update", "ASC");
		wins.getRecords().addOrderBy("descripcion", "ASC");
		wins.SetVision("BACK");

		return wins;
	}

	public JWins getIndicadoresRepro() throws Exception {
		GuiSqlEvents wins = new GuiSqlEvents();
		wins.getRecords().addFilter("estado", BizSqlEvent.ERROR, "<>");
		wins.getRecords().addFilter("estado", BizSqlEvent.OK, "<>");
		wins.getRecords().addOrderBy("fecha_update", "ASC");
		wins.getRecords().addOrderBy("descripcion", "ASC");
		wins.SetVision("BACK");

		return wins;
	}
	
	

 }
