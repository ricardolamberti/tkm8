package pss.bsp.contrato.wizardObjetivo;

import pss.bsp.contrato.GuiContrato;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleObjetivo.GuiDetalleObjetivo;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActUpdate;
import pss.core.winUI.forms.JBaseForm;

public class GuiWizardContratoObjetivo extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiWizardContratoObjetivo() throws Exception {
  }
  



  public JRecord ObtenerDato()   throws Exception { return new BizWizardContratoObjetivo(); }
  public int GetNroIcono()   throws Exception { return 15014; }
  public String GetTitle()   throws Exception { return "Objetivo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
	 	if (GetcDato().getPaso()==1) return FormWizardContratoPasoElegirContrato.class; 
	 	if (GetcDato().getPaso()==2) return FormWizardContratoPasoObjetivo.class; 
	 	if (GetcDato().getPaso()==3) return FormWizardFinContrato.class; 
  	return FormWizardContratoPasoElegirContrato.class; 
  	
 }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizWizardContratoObjetivo GetcDato() throws Exception { return (BizWizardContratoObjetivo) this.getRecord(); }

  public void createActionMap() throws Exception {
 		this.addAction(10, "Siguiente", null, 10020, false, false, true, "Group");
 		this.addAction(20, "Anterior", null, 10020, false, false, true, "Group");
 		this.addAction(30, "Manual", null, 10020, false, false, true, "Group");
 		this.addAction(40, "Generar", null, 10020, false, false, true, "Group");
		this.addAction(100, "Update Detalle", null, 10020, false, false, true, "Group");
		this.addAction(110, "Ver Detalle", null, 10020, false, false, true, "Group");
		super.createActionMap();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==10) return GetcDato().hayMasPasos();
  	return super.OkAction(a);
  }
  GuiDetalle winDet;
  GuiContrato winContr;

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				winDet = new GuiDetalleObjetivo();
				winDet.setRecord(GetcDato().getDetalle());
				winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
				GetcDato().setPaso(GetcDato().getNextPaso());
				super.submit();
			}
			@Override
			public JAct nextAction() throws Exception {
				return nextAccion();
			}
			@Override
			public boolean isHistoryAction() throws Exception {
				return false;
			}
			@Override
			public boolean backAfterSubmit() throws Exception {
				return true;
			}
		};
		if (a.getId() == 20)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().setPaso(GetcDato().getPrevPaso());
				super.submit();
			}
			@Override
			public JAct nextAction() throws Exception {
				return nextAccion();
			}
			@Override
			public boolean isHistoryAction() throws Exception {
				return false;
			}
			@Override
			public boolean backAfterSubmit() throws Exception {
				return true;
			}
		};
		if (a.getId() == 30)	return new JActNew(getNewContrato(),0);
		if (a.getId() == 40)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				winContr = new GuiContrato();
				winContr.setRecord( GetcDato().execGenerarContrato(winDet.GetcDato()) );
				super.submit();
			}
			@Override
			public JAct nextAction() throws Exception {
				return new JActQuery(winContr);
			}
			@Override
			public boolean isHistoryAction() throws Exception {
				return false;
			}
		  public boolean backAfterSubmit() throws Exception { 
		  	return true;
		  }
		};
		if (a.getId() == 100)	return new JActUpdate(getDetalle(),2);
		if (a.getId() == 110)	return new JActUpdate(getDetalle(),2);

		return super.getSubmitFor(a);
	}
	public boolean forceCleanIdemHistory() throws Exception {
		return false;
	}
	public GuiContrato getNewContrato() throws Exception {
		GuiContrato c=new GuiContrato();
		c.getRecord().addFilter("company", GetcDato().getCompany());
			
		return c;
		
	}
	
	GuiDetalleObjetivo objDetalle;

	public GuiDetalle getDetalle() throws Exception {
		GuiDetalleObjetivo win = new GuiDetalleObjetivo();
		win.setRecord(GetcDato().getDetalle());
		win.GetcDato().setObjContrato(GetcDato().getObjContrato());
		win.SetVision("VIEW");
		return win;
	}
	
	public JAct nextAccion() {
		return new JActUpdate(this,BizAction.UPDATE);
	}
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
 }
