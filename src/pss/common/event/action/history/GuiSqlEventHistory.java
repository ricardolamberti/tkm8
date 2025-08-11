package pss.common.event.action.history;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActBack;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiSqlEventHistory extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSqlEventHistory() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizSqlEventHistory(); }
  public int GetNroIcono()   throws Exception { return 749; }
  public String GetTitle()   throws Exception { return "Historico aviso"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormSqlEventHistory.class; }
  public Class<? extends JBaseForm> getFormUpdate() throws Exception { return FormSqlEventHistorySimple.class; }
  public String  getKeyField() throws Exception { return "id_history"; }
  public String  getDescripField() { return "destinatario"; }
  public BizSqlEventHistory GetcDato() throws Exception { return (BizSqlEventHistory) this.getRecord(); }

	@Override
	public void createActionMap() throws Exception {
//		this.createActionQuery();
//		this.createActionUpdate();
		this.addAction(20, "Enviar", null, 10026, true, true, true, "Group" );
		this.addAction(10, "Reenviar", null, 10026, true, true, true, "Group" );
		this.addAction(30, "Ver archivo asoc.", null, 15018, true, true ).setNuevaVentana(true);
	 	super.createActionMap();
	}
	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==10) return GetcDato().getRemitido();
		if (a.getId()==20) return !GetcDato().getRemitido();
		if (a.getId()==30) return GetcDato().hasFileElectronico();
		return super.OkAction(a);
	}

		@Override
		public JAct getSubmitFor(BizAction a) throws Exception {
	  	if (a.getId()==10 ) return new JActSubmit(this) {
	  		@Override
	  		public void submit() throws Exception {
	  			GetcDato().execProcessReenviar();
	  		}
	  	};
	  	if (a.getId()==20 ) return new JActSubmit(this) {
	  		@Override
	  		public void submit() throws Exception {
	  			GetcDato().execProcessReenviar();
	  		}
	  		
	  		@Override
	  		public JAct nextAction() throws Exception {
	  			// TODO Auto-generated method stub
	  			return new JActBack(null);
	  		}
	  	};
		  if (a.getId()==30) return new JActFileGenerate(this) {
				public String generate() throws Exception {
					return GetcDato().getFileElectronico();
				}
		
		  };
			return super.getSubmitFor(a);
		}
	
	
 }
