package  pss.bsp.familia;

import pss.bsp.familia.detalle.GuiFamiliaDetails;
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

public class GuiFamilia extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiFamilia() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizFamilia(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Familia tarifaria"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormFamilia.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizFamilia GetcDato() throws Exception { return (BizFamilia) this.getRecord(); }

  public void createActionMap() throws Exception {
 		super.createActionMap();
 		this.addAction(10, "Familia-Aerolineas", null, 10020, false, false, true, "Group");
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
		GuiFamiliaDetails wins = new GuiFamiliaDetails();
		wins.getRecords().addFilter("id_familia", GetcDato().getId());
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
