package pss.bsp.contrato.detalle.nivel;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiNivel extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiNivel() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizNivel(); }
  public int GetNroIcono()   throws Exception { 
  	return 15010; 
  };
  public String GetTitle()   throws Exception { return "Nivel"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormNivel.class; }
  public String  getKeyField() throws Exception { return "id_nivel"; }
  public String  getDescripField() { return "id_nivel"; }
  public BizNivel GetcDato() throws Exception { return (BizNivel) this.getRecord(); }

  public void createActionMap() throws Exception {
		this.createActionQuery();
		this.createActionUpdate().setOnlyInForm(true);
		this.createActionDelete();
		this.addAction(10, "Objetivo deseado", null, 15022, true, true, true, "Group").setOnlyInForm(true);
  }
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().execProcessMarcarComoFavorito();
				super.submit();
			}
		};
		return super.getSubmitFor(a);
	}

 }
