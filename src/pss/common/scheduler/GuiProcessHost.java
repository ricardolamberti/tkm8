package  pss.common.scheduler;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiProcessHost extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiProcessHost() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizProcessHost(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5005; }
  @Override
	public String GetTitle()       throws Exception { return "Servidor de Ejecución"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormProcessHost.class; }
  @Override
	public String getKeyField()   throws Exception { return "uniqueid"; }
  @Override
	public String getDescripField()                  { return "host"; }


    //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
		this.createActionUpdate();
		this.createActionDelete();
    addAction(35, "Detener Proceso", null, 7, true, true);
  }

  public JAct getSubmitFor(BizAction a) throws Exception {
    if (a.getId() == 35)
      return new JActSubmit(this) {
        @Override
        public void submit() throws Exception {
          GetcDato().stopProcess();
        }
      };
    return super.getSubmitFor(a);
  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizProcessHost GetcDato() throws Exception {
    return (BizProcessHost) this.getRecord();
  }


}
