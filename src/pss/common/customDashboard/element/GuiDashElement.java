package pss.common.customDashboard.element;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;

public class GuiDashElement extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDashElement() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDashElement(); }
//  public int GetNroIcono()   throws Exception { return 92; }
  public String GetTitle()   throws Exception { return "Elemento"; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "description"; }
  public BizDashElement GetcDato() throws Exception { return (BizDashElement) this.getRecord(); }

	public int GetNroIcono() throws Exception {
			return 801;
	}
	
  
 }
