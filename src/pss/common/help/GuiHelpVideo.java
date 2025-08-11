package pss.common.help;

import pss.JPath;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.winUI.forms.JBaseForm;

public class GuiHelpVideo extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiHelpVideo() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizHelpVideo(); }
  public int GetNroIcono()   throws Exception { return 5606; }
  public String GetTitle()   throws Exception { return "Video de Ayuda"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormHelpVideo.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "description"; }
  public BizHelpVideo GetcDato() throws Exception { return (BizHelpVideo) this.getRecord(); }


  public void createActionMap() throws Exception {
		this.addAction(100, "Bajar Video", null, 5606, true, true, true, "Group").setNuevaVentana(true);
  }
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {

		if (a.getId() == 100)
			return getVideo(a);
		

		// JActExternalLink
		return null;
	}
	
	private JAct getVideo(BizAction a) throws Exception {

		return new JActFileGenerate(this, a.getId()) {
			
			public String getPath() throws Exception {
				return JPath.PssPathDataFiles();
				// return fileName;
			}

			public String generate() throws Exception {
				return GetcDato().getLink();
			}
		};
		
	}
	
  
  
 }
