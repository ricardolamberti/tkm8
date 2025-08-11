package pss.bsp.consolid.model.clientes;

import pss.bsp.dk.GuiClienteDKs;
import pss.common.event.mailing.BizMailingPersona;
import pss.common.event.mailing.GuiMailingPersona;
import pss.common.event.mailing.GuiMailingPersonas;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiCliente extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCliente() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCliente(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Cliente"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCliente.class; }
  public String  getKeyField() throws Exception { return "customer_number"; }
  public String  getDescripField() { return "customer_name"; }
  public BizCliente GetcDato() throws Exception { return (BizCliente) this.getRecord(); }


	public void createActionMap() throws Exception {
		this.addAction(10, "Detalles", null, 10020, false, false, true, "Group");		
//		this.addAction(20, "Asociar", null, 10020, true, true, true, "Group");		
		this.addAction(50, "Generar Uno", null, 10020, true, true, true, "Group");		
		
		this.createActionQuery();

	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
	
		return super.OkAction(a);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActWins(this.getDetail());
//		if (a.getId() == 20)
//			return new JActWinsSelect(this.getMailing(),this,true);
		if (a.getId() == 50)	return new JActSubmit(this) {
			public void submit() throws Exception {
	  		BizCompany comp = new BizCompany();
	  		comp.dontThrowException(true);
	  		if (comp.Read("C_"+GetcDato().getCustomerNumber())) {
	  			GetcDato().execActualizar(BizUsuario.getUsr().getCompany());
	  			return;
	  		}
				GetcDato().execGenerar();
			}  
		};	
		return super.getSubmitFor(a);
	}
	

	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiMailingPersona) {
			GuiMailingPersona mail = (GuiMailingPersona) zBaseWin;
			mail.GetcDato().setNumero(this.GetcDato().getCustomerNumber());
			mail.GetcDato().setDescipcion(this.GetcDato().getCustomerName());
			mail.GetcDato().execProcessUpdate();
		}
		return super.Drop(zBaseWin);
	}
	public GuiClienteDKs getMailing() throws Exception {
		GuiClienteDKs wins = new GuiClienteDKs();
		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
			
		wins.getRecords().addOrderBy("dk","asc");
		
		return wins;
	}
	public GuiClienteDKs getDetail() throws Exception {
		GuiClienteDKs wins = new GuiClienteDKs();
		//wins.getRecords().addFixedFilter("where (dk='"+ GetcDato().getCustomerNumber()+"' or numero='"+GetcDato().getCustomerNumber()+"') ");
		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		wins.getRecords().addFilter("dk", GetcDato().getCustomerNumber());

		wins.getRecords().addOrderBy("dk","asc");
		
		return wins;
	}

 }
