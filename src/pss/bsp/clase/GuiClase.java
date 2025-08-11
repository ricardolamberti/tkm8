package  pss.bsp.clase;

import pss.bsp.clase.detalle.GuiClaseDetails;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.company.GuiCompany;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;

public class GuiClase extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiClase() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizClase(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Clase"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormClase.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizClase GetcDato() throws Exception { return (BizClase) this.getRecord(); }

  public void createActionMap() throws Exception {
 		super.createActionMap();
 		this.addAction(10, "Clase-Aerolineas", null, 10020, false, false, true, "Group");
 		this.addAction(20, "Recalcular", null, 15016, true, true, true, "Group");
 		this.addAction(100, "Copiar otra empresa", null, 10020, true, true, true, "Group").setInToolbarMore(true);
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==100) return BizUsuario.getUsr().isAnyAdminUser();
  	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getDetail());
		if (a.getId() == 20)	return new JActSubmit(this){
			@Override
			public void submit() throws Exception {
				GetcDato().execReprocessAllTicket();
				super.submit();
			}
		};
 		if (a.getId() == 100) return new JActWinsSelect(new GuiCompanies(), this);
		return super.getSubmitFor(a);
	}

	public JWins getDetail() throws Exception {
		GuiClaseDetails wins = new GuiClaseDetails();
		wins.getRecords().addFilter("id_clase", GetcDato().getId());
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		return wins;
	}
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
//		if (zBaseWin instanceof GuiPaisLista) {
//			GuiPaisLista p = (GuiPaisLista) zBaseWin;
//			this.GetcDato().execProcessAddMiembro(p.GetcDato());
//		}
//		if (zBaseWin instanceof GuiPaisesLista) {
//			GuiPaisesLista p = (GuiPaisesLista) zBaseWin;
//			JIterator<BizPaisLista> it = p.getRecords().getStaticIterator();
//			while (it.hasMoreElements())
//				this.GetcDato().execProcessAddMiembro(it.nextElement());
//		}
	  if (zBaseWin instanceof GuiCompany) {
	  	GuiCompany compania = (GuiCompany)zBaseWin;
					GetcDato().execProcessCopiarOtraEmpresa(compania.GetcDato());
		}
		return super.Drop(zBaseWin);
	}
 }
