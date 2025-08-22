package pss.bsp.consolid.model.cuf.detail;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiCufDetail extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiCufDetail() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizCufDetail(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5052; }
  @Override
	public String GetTitle()       throws Exception { return "Reporte detalle"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormCufDetail.class; }
   @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "total"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  public void createActionMap() throws Exception {
//  	this.addAction(34, "Interfaz Excel", null, 10020, true, true, true, "Group");		
  		super.createActionMap();
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
  public BizCufDetail GetcDato() throws Exception {
    return (BizCufDetail) this.getRecord();
  }


}
