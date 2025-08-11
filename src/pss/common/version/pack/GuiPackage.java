package pss.common.version.pack;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPackage  extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPackage() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPackage(); }
  public int GetNroIcono()   throws Exception { return 10039; }
  public String GetTitle()   throws Exception { return "Paquete"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPackage.class; }
  public String  getKeyField() throws Exception { return "id_package"; }
  public String  getDescripField() { return "description"; }
  public BizPackage  GetcDato() throws Exception { return (BizPackage) this.getRecord(); }

   
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}


 }
