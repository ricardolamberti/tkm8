package pss.common.customDashboard.config;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiDashBoardConfig extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDashBoardConfig() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDashBoardConfig(); }
  public String GetTitle()   throws Exception { return "Elemento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDashBoardConfig.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "company"; }
  public BizDashBoardConfig GetcDato() throws Exception { return (BizDashBoardConfig) this.getRecord(); }

	public int GetNroIcono() throws Exception {
		return 5012;
	}
	
	@Override
	public boolean OkAction(BizAction zAct) throws Exception {

		switch (zAct.getId()) {
		case 35:
			return this.GetcDato().getExcluded()==false;
		case 36:
			return this.GetcDato().getExcluded();
	
		}

		return super.OkAction(zAct);
	}

	@Override
	public void createActionMap() throws Exception {
//	  this.addActionQuery( 1, "Consultar" );
	  this.addActionUpdate( 2, "Modificar" );		
	  this.addActionDelete(3, "Eliminar");
//		this.addAction(35, "Excluir", null, 48, true, true).setMulti(true);
//		this.addAction(36, "Incluir", null, 3, true, true).setMulti(true);

		this.addAction(40, "Subir", null, 15056, true, true).setPostFunction("goUp('provider_pss.common.dashboard.GuiModuloCustoDashBoard_10');");
		this.addAction(50, "Bajar", null, 15057, true, true).setPostFunction("goDown('provider_pss.common.dashboard.GuiModuloCustoDashBoard_10');");
		
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		
		if (a.getId()==35) return new JActSubmit(this, 35) {
			public void execSubmit() throws Exception {
				GetcDato().processExclude();
			}
		};
		if (a.getId()==36) return new JActSubmit(this, 36) {
			public void execSubmit() throws Exception {
				GetcDato().processInclude();
			}
		};	
		
	
		if (a.getId()==40) return new JActSubmit(this, 40) {
			public void execSubmit() throws Exception {
				GetcDato().processUp();
			}
		};
		if (a.getId()==50) return new JActSubmit(this, 50) {
			public void execSubmit() throws Exception {
				GetcDato().processDown();
			}
		};			

		return super.getSubmitFor(a);
	}
	
  
 }
