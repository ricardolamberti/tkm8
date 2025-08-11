package pss.bsp.contrato.wizardBackend;
 
import pss.bsp.contrato.GuiContrato;
import pss.bsp.contrato.conocidos2.BizContratoConocidoV2;
import pss.bsp.contrato.conocidos2.GuiContratoConocidoV2;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleAvianca.GuiDetalleAvianca;
import pss.bsp.contrato.detalleBackendDobleRutas.GuiDetalleBackendDobleRuta;
import pss.bsp.contrato.detalleBackendRutas.GuiDetalleBackendRuta;
import pss.bsp.contrato.detalleCopa.GuiDetalleCopa;
import pss.bsp.contrato.detalleCopaPorRutas.GuiDetalleCopaPorRutas;
import pss.bsp.contrato.detalleDatamining.GuiDetalleDatamining;
import pss.bsp.contrato.detalleRutas.GuiDetalleRuta;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActUpdate;
import pss.core.winUI.forms.JBaseForm;
import pss.www.ui.JWebIcon;

public class GuiWizardContratoBackend extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiWizardContratoBackend() throws Exception {
  }
  



  public JRecord ObtenerDato()   throws Exception { return new BizWizardContratoBackend(); }
  public int GetNroIcono()   throws Exception { return 15014; }
  public String GetTitle()   throws Exception { return "Contrato"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
	 	if (GetcDato().getPaso()==1) return FormWizardContratoPasoElegirContrato.class; 
	 	if (GetcDato().getPaso()==2) return FormWizardContratoTipo.class; 
	 	if (GetcDato().getPaso()==3) return FormWizardContratoPasoBackend.class; 
	 	if (GetcDato().getPaso()==4) return FormWizardFinContrato.class; 
  	return FormWizardContratoPasoElegirContrato.class; 
  	
 }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizWizardContratoBackend GetcDato() throws Exception { return (BizWizardContratoBackend) this.getRecord(); }

  public void createActionMap() throws Exception {
 		this.addAction(10, "Siguiente", null, 10020, false, false, true, "Group");
 		this.addAction(11, "Siguiente", null, 10020, false, false, true, "Group");
 		this.addAction(12, "Siguiente", null, 10020, false, false, true, "Group");
 		this.addAction(13, "Siguiente", null, 10020, false, false, true, "Group");
 		this.addAction(14, "Siguiente", null, 10020, false, false, true, "Group");
 		this.addAction(15, "Siguiente", null, 10020, false, false, true, "Group");
 		this.addAction(16, "Siguiente", null, 10020, false, false, true, "Group");
 		this.addAction(17, "Siguiente", null, 10020, false, false, true, "Group");
 		this.addAction(18, "Generar", null, 10020, false, false, true, "Group");
		this.addAction(19, "Generar", null, 10020, false, false, true, "Group");
		this.addAction(20, "Anterior", null, 10020, false, false, true, "Group");
 		this.addAction(30, "Manual", null, 10020, false, false, true, "Group");
 		this.addAction(40, "Generar", null, 10020, false, false, true, "Group");
 		addActions();
		this.addAction(100, "Update Detalle", null, 10020, false, false, true, "Group");
		this.addAction(110, "Ver Detalle", null, 10020, false, false, true, "Group");
		this.addAction(120, "Update Detalle", null, 10020, false, false, true, "Group");
		super.createActionMap();
  }
  
  void addActions() throws Exception {
    JIterator<BizContratoConocidoV2> it = GetcDato().getModelosContratosCandidatos().getStaticIterator();
    
    while (it.hasMoreElements()) {
    	BizContratoConocidoV2 c = it.nextElement();
    	this.addAction((int)(1000+c.getId()), "Generar", null, 10020, false, false, true, "Group");
   	 	
    }  	
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
		if (a.getId() == 11)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				 winDet = new GuiDetalleBackendRuta();
				 winDet.setRecord(GetcDato().buildDetalle(winDet));
				 winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
				 winDet.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
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


		if (a.getId() == 12)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				winDet = new GuiDetalleBackendDobleRuta();
				winDet.setRecord(GetcDato().buildDetalle(winDet));
				winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
				winDet.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
				GetcDato().setDetalle(winDet.GetcDato());
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
		if (a.getId() == 13)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				winDet = new GuiDetalleRuta();
				winDet.setRecord(GetcDato().buildDetalle(winDet));
				winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
				winDet.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
				GetcDato().setDetalle(winDet.GetcDato());
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
		if (a.getId() == 14)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				winDet = new GuiDetalleCopa();
				winDet.setRecord(GetcDato().buildDetalle(winDet));
				winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
				winDet.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
				GetcDato().setDetalle(winDet.GetcDato());
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
		if (a.getId() == 15)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				winDet = new GuiDetalleCopaPorRutas();
				winDet.setRecord(GetcDato().buildDetalle(winDet));
				winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
				winDet.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
				GetcDato().setDetalle(winDet.GetcDato());
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
		if (a.getId() == 16)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				winDet = new GuiDetalleAvianca();
				winDet.setRecord(GetcDato().buildDetalle(winDet));
				winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
				winDet.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
				GetcDato().setDetalle(winDet.GetcDato());
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
		if (a.getId() == 17)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				winDet = new GuiDetalleDatamining();
				winDet.setRecord(GetcDato().buildDetalle(winDet));
				winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
				winDet.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
				GetcDato().setDetalle(winDet.GetcDato());
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
		if (a.getId() == 18)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				winContr = new GuiContrato();
				winContr.setRecord(GetcDato().execGenerarContrato(null));
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
		if (a.getId() == 19)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				winDet = new GuiDetalleCopaPorRutas();
				winDet.setRecord(GetcDato().buildDetalle(winDet));
				winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
				winDet.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
				GetcDato().setDetalle(winDet.GetcDato());
				GetcDato().setPaso(GetcDato().getNextPaso());
				super.submit();
			}
			@Override
			public JAct nextAction() throws Exception {
				return new JActQuery(winDet);
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
		if (a.getId() == 110)	return new JActQuery(getDetalle());
		if (a.getId() == 120)	return new JActUpdate(this,2);
		
  	if (a.getId()>1000) {
  		JActSubmit act=  new JActSubmit(this) {
	
				@Override
				public void submit() throws Exception {
					GuiContratoConocidoV2 cc = getContratoModelo((int)(getActionId()-1000));
					winDet = cc.getDetalle();
					winDet.setRecord(GetcDato().buildDetalle(winDet));
					winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
					winDet.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
					winDet.setDropListener(null);
					GetcDato().setDetalle(winDet.GetcDato());
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
	  	act.setActionId(a.getId());	
	  	return act;
  	}
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
	
	public GuiContratoConocidoV2 getContratoModelo(int id) throws Exception {
		GuiContratoConocidoV2 c=new GuiContratoConocidoV2();
		c.GetcDato().read(id);
			
		return c;
	}
	public GuiDetalle getDetalle() throws Exception {
		winDet.setRecord(GetcDato().buildDetalle(winDet));
		winDet.GetcDato().setObjContrato(GetcDato().getObjContrato());
		winDet.GetcDato().setIdAerolinea(GetcDato().getAerolineas());
		winDet.SetVision("VIEW");
		return winDet;
	}
	
	public JAct nextAccion() {
		try {
			return findAction(120).getSubmit();
		} catch (Exception e) {
			return new JActUpdate(this,BizAction.UPDATE);
		}
	}

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
 }
