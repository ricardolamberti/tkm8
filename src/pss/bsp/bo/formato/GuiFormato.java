package  pss.bsp.bo.formato;

import pss.bsp.parseo.JParseoFactory;
import pss.bsp.parseo.bspParseo.IBspParseo;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiFormato extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiFormato() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizFormato(); }
  public int GetNroIcono()   throws Exception { return 10023; }
  public String GetTitle()   throws Exception { return "Formato Interfaz"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormFormato.class; }
  public String  getKeyField() throws Exception { return "id_formato"; }
  public String  getDescripField() { return "descripcion"; }
  public BizFormato GetcDato() throws Exception { return (BizFormato) this.getRecord(); }

  
  public void createActionMap() throws Exception {
 		this.addAction(20, "Ejemplo", null, 10020, false, false, true, "Group");
 		super.createActionMap();
  }

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 20)	return new JActWins(this.getDetail());
		return null;
	}
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	public JWins getDetail() throws Exception {
		IBspParseo parseo = (IBspParseo)JParseoFactory.getInstanceFromClass(GetcDato().getExampleParseo());
		JWins wins =  parseo.getGuisDetail(GetcDato().getCompany(), GetcDato().getOwner(),""+GetcDato().getExampleIdInterfaz());
	  wins.SetVision("CONFIG");
		return wins;
	}

	

 }
