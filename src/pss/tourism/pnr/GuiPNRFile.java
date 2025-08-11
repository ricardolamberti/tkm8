package pss.tourism.pnr;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;

public class GuiPNRFile extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPNRFile() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPNRFile(); }
  public int GetNroIcono()   throws Exception { return 15006; }
  public String GetTitle()   throws Exception { return "Archivo datos"; }
//  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPNRFare.class; }
  public String  getKeyField() throws Exception { return "archivo"; }
  public String  getDescripField() { return "descripcion"; }
  public BizPNRFile GetcDato() throws Exception { return (BizPNRFile) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
  	addAction(30, "Ver Archivo", null, 10047, true, true).setNuevaVentana(true);

  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
	  if (a.getId()==30) return new JActFileGenerate(this) {
			public String generate() throws Exception {
				return returnFilePNR();
			}
	
	  };
	  return super.getSubmitFor(a);
  }
  
	private String returnFilePNR() throws Exception {
		return this.GetcDato().getPNRFilePath();
	}
	
	@Override
	public int GetDobleClick() throws Exception {
		return 30;
	}
 }
