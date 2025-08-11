package pss.common.issueTracker.reportes ;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;


public class GuiIssueSummary extends JWin {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiIssueSummary() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizIssueSummary();
	}

	@Override
	public String getDescripField() {
		return "usuario";
	}

	@Override
	public String getKeyField() {
		return "usuario";
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 41;
	}


	@Override
	public Class<? extends JBaseForm> getFormBase() {
		return null;
	}

	@Override
	public String GetTitle() {
		return "Transacciones Agrupadas";
	}

	@Override
	public boolean canExpandTree() {
		return false;
	}

	@Override
	public void createActionMap() throws Exception {
		//addAction(40, "Detalle", null, 10051, true, true);
	}
	
	@Override
	public int GetDobleClick() throws Exception {
		return 40;
	}
	
	@Override
	public int GetSimpleClick() throws Exception {
		return 40;
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		//if (a.getId()==40) return new JActWins(this.getDetalles(a));
		return null;
	}



	public BizIssueSummary GetcDato() throws Exception {
		return (BizIssueSummary) getRecord();
	}

  /*private JWins getDetalles(BizAction a) throws Exception {
  	GuiTransSumaryDetalles t = new GuiTransSumaryDetalles();
  	t.setRecords(this.GetcDato().getTransDetalles(a));
//  	t.setShowFilters(false);
//  	t.SetVision(BizTransDetalle.VISION_VENTAS);
  	t.setRefreshOnlyOnUserRequest(false) ;
  	t.setPreviewFlag(JWins.PREVIEW_MAX);
  	return t;
  }	*/  

  
  public boolean canConvertToURL() {
  	return false;
  }


}
