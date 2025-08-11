package  pss.bsp.regions;

import pss.bsp.regions.detalle.GuiRegionDetails;
import pss.common.regions.divitions.BizPaisLista;
import pss.common.regions.divitions.GuiPaisLista;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;

public class GuiRegion extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiRegion() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizRegion(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Región"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormRegion.class; }
  public String  getKeyField() throws Exception { return "region"; }
  public String  getDescripField() { return "descripcion"; }
  public BizRegion GetcDato() throws Exception { return (BizRegion) this.getRecord(); }

  public void createActionMap() throws Exception {
 		super.createActionMap();
 		this.addAction(10, "Paises miembros", null, 10020, false, false);
 		this.addAction(20, "Agregar miembros", null, 10020, true, true).setMulti(true);
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	 	return super.OkAction(a);
  }
  

    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getDetail());
		if (a.getId() == 20)	return new JActWinsSelect(new GuiPaisesLista(),this,true);
		return super.getSubmitFor(a);
	}

	public JWins getDetail() throws Exception {
		GuiRegionDetails wins = new GuiRegionDetails();
		wins.getRecords().addFilter("id_region", GetcDato().getId());
		return wins;
	}
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiPaisLista) {
			GuiPaisLista p = (GuiPaisLista) zBaseWin;
			this.GetcDato().execProcessAddMiembro(p.GetcDato());
		}
		if (zBaseWin instanceof GuiPaisesLista) {
			GuiPaisesLista p = (GuiPaisesLista) zBaseWin;
			JIterator<BizPaisLista> it = p.getRecords().getStaticIterator();
			while (it.hasMoreElements())
				this.GetcDato().execProcessAddMiembro(it.nextElement());
		}
		return super.Drop(zBaseWin);
	}
 }
