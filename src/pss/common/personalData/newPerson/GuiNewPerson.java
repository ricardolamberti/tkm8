package  pss.common.personalData.newPerson;

import pss.common.personalData.GuiDomicilio;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;

public class GuiNewPerson extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiNewPerson() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizNewPerson(); }
  @Override
	public int     GetNroIcono() throws Exception  { return 763; }
  

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizNewPerson GetcDato() throws Exception {
    return (BizNewPerson) this.getRecord();
  }
  
	protected boolean checkActionOnDrop(BizAction a) throws Exception {
		return true;
	}
	
	@Override
	public JAct Drop(JBaseWin baseWin) throws Exception {
		if (baseWin instanceof GuiDomicilio) {
			((GuiDomicilio) baseWin).GetcDato().execProcessMain();
		}
		return super.Drop(baseWin);
	}


}
