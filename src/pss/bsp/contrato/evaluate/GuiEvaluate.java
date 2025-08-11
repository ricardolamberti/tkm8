package pss.bsp.contrato.evaluate;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.evaluate.detail.BizEvaluateDetail;
import pss.bsp.contrato.evaluate.detail.GuiEvaluateDetails;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiEvaluate extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiEvaluate() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizEvaluate(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Evaluar "+GetcDato().getObjDetalle().getDescripcion(); }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEvaluate.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizEvaluate GetcDato() throws Exception { return (BizEvaluate) this.getRecord(); }

  public void createActionMap() throws Exception {
   	this.addAction(10, "Tests", null, 5012, false, false, false, "Group" );
   	this.addAction(20, "Evaluar", null, 9, false, false, false, "Group" );
  
  	super.createActionMap(); 
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)  return new JActWins(getTests());
		if (a.getId() == 20)  return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().processEvaluar();
				super.submit();
			}
		};
		
		return super.getSubmitFor(a);
	}

	public GuiEvaluateDetails getTests() throws Exception {
		JRecords<BizEvaluateDetail> dets = GetcDato().getTest();
		GuiEvaluateDetails wins = new GuiEvaluateDetails();
		wins.setRecords(dets);
		wins.setWebPageable(false);
		wins.setDropListener(this);
		return wins;
	}
	
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
 }
