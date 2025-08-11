package pss.common.restJason.apiReqHistory;

import java.util.Date;

import pss.common.regions.company.GuiCompanies;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiApiRequestHistorys extends JWins {



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiApiRequestHistorys() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiApiRequestHistory.class; }
  @Override
	public int GetNroIcono() throws Exception { return 6043	; }
  @Override
	public String GetTitle() throws Exception { return "API History"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    //addActionNew     ( 1, "Nueva Cama" );
  	this.addAction(10, "Start robot", null, 6043, true, true);
  	this.addAction(20, "Stop robot", null, 48, true, true);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActSubmit(this, 10) {
  		public void submit() throws Exception {
    		JApiRobot.startRobot();
  		}
  	};
  	if (a.getId()==20) return new JActSubmit(this, 20) {
  		public void submit() throws Exception {
    		JApiRobot.stopRobot();
  		}
  	};
  	return super.getSubmitFor(a);
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==10) return !JApiRobot.isRunning();
  	if (a.getId()==20) return JApiRobot.isRunning();
  	return super.OkAction(a);
  }


  //-------------------------------------------------------------------------//
  // Configuro las columnas que quiero mostrar en la grilla
  //-------------------------------------------------------------------------//
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    if (zLista.isForExport()) {
	  	zLista.AddIcono("");
	    if (!this.getRecords().filterHasValue("company"))
	    		zLista.AddColumnaLista("company");
	    zLista.AddColumnaLista("id").setActionOnClick(1);
	    zLista.AddColumnaLista("creation_date");
	    zLista.AddColumnaLista("usuario");
	    zLista.AddColumnaLista("url");
	    zLista.AddColumnaLista("method");
	    zLista.AddColumnaLista("status");
	    zLista.AddColumnaLista("status_msg");
    } else {
    	zLista.addColumnWinAction("Flat", 1);
    }
  }
  
  @Override
  protected void configureList(JWinList list) throws Exception {
  	list.setHideActions(true);
  }

  @Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormControl oControl;

		zFiltros.addWinLovResponsive("Empresa", "company", new JControlWin() {
			public JWins getRecords(boolean one) throws Exception {
				return new GuiCompanies();
			}
		}).setFilterNeverHide(true);
		
		oControl = zFiltros.addEditResponsive("Usuario", "usuario");
		oControl.setOperator("like");

		zFiltros.addComboResponsive("Flujo", "oper", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return JWins.createVirtualWinsFromMap(BizApiRequestHistory.getOperMap());
			}
		}, true).setFilterNeverHide(true);		
		zFiltros.addComboResponsive("Status", "status", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return JWins.createVirtualWinsFromMap(BizApiRequestHistory.getStatusMap());
			}
		}, true).setFilterNeverHide(true);		
		
		
		oControl = zFiltros.addCDateResponsive("Fecha", "creation_date");
		oControl.SetValorDefault(new Date());
		oControl.setFilterNeverHide(true);
		
//		oControl = zFiltros.addEditResponsive("Contenido", "request");
//		oControl.setOperator("like");
		

	}
  
  
  
	@Override
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("creation_date")) {
			this.getRecords().addFilter("creation_date", control.getValue(), ">=").setDynamic(true);
			return;
		}
		if (control.getIdControl().equals("usuario") && control.hasValue()) { 
			String value = control.getValue();
			value="%"+value.replaceAll(" ", "%")+"%";
			this.getRecords().addFixedFilter(" where (lower(usuario) like lower('"+value+"'))" ).setDynamic(true);
			return;
		}		
		
		
		super.asignFilterByControl(control);
	}
	
  
}
