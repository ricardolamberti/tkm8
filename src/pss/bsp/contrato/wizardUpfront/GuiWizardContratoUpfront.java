package pss.bsp.contrato.wizardUpfront;

import pss.bsp.contrato.GuiContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleUpfrontRutas.GuiDetalleUpfrontRuta;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActUpdate;
import pss.core.winUI.forms.JBaseForm;

public class GuiWizardContratoUpfront extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiWizardContratoUpfront() throws Exception {
  }
  



  public JRecord ObtenerDato()   throws Exception { return new BizWizardContratoUpfront(); }
  public int GetNroIcono()   throws Exception { return 15014; }
  public String GetTitle()   throws Exception { return "Contrato"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
	 	if (GetcDato().getPaso()==1) return FormWizardContratoPasoElegirContrato.class; 
	 	if (GetcDato().getPaso()==2) return FormWizardContratoPasoUpfront.class; 
	 	if (GetcDato().getPaso()==3) return FormWizardFinContrato.class; 
  	return FormWizardContratoPasoElegirContrato.class; 
  	
 }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizWizardContratoUpfront GetcDato() throws Exception { return (BizWizardContratoUpfront) this.getRecord(); }

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
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GuiDetalleUpfrontRuta win = new GuiDetalleUpfrontRuta();
				win.setRecord(GetcDato().getDetalle());
				win.GetcDato().setObjContrato(GetcDato().getObjContrato());
				win.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
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
			GuiDetalle deta;
			@Override
			public void submit() throws Exception {
				BizDetalle det = GetcDato().execGenerarContrato();
				deta=det.getObjLogicaInstance().getWin();
				deta.setRecord(det);
				super.submit();
			}
			@Override
			public JAct nextAction() throws Exception {
				return new JActQuery(deta);
			}
			@Override
			public boolean isHistoryAction() throws Exception {
				return false;
			}
		  public boolean backAfterSubmit() throws Exception { 
		  	return true;
		  }
		};
		if (a.getId() == 100)	return new JActQuery(getDetalle());
		if (a.getId() == 110)	return new JActQuery(getDetalle());

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
	
	GuiDetalleUpfrontRuta objDetalle;

	public GuiDetalle getDetalle() throws Exception {
		GuiDetalleUpfrontRuta win = new GuiDetalleUpfrontRuta();
		win.setRecord(GetcDato().getDetalle());
		win.GetcDato().setObjContrato(GetcDato().getObjContrato());
		win.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
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
