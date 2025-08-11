package pss.bsp.consolid.model.repoDK.detailMonth;

import pss.bsp.consolid.model.liquidacion.detail.GuiLiqDetails;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.winUI.forms.JBaseForm;

public class GuiRepoDKDetailMonth extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiRepoDKDetailMonth() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizRepoDKDetailMonth(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5052; }
  @Override
	public String GetTitle()       throws Exception { return "Reporte detalle"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormRepoDKDetailMonth.class; }
   @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "total"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  public void createActionMap() throws Exception {
		this.createActionQuery();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
   	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
//	 	if (a.getId() == 34)	return new JActFileGenerate(this) {
//  		public String generate() throws Exception {
//  			return getInterfaz4();
//  		}
//  	};
  	
		return super.getSubmitFor(a);
	}

//	public String getInterfaz4() throws Exception {
//		return GetcDato().getFileElectronico();
//	}
  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizRepoDKDetailMonth GetcDato() throws Exception {
    return (BizRepoDKDetailMonth) this.getRecord();
  }


}
