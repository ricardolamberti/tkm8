package  pss.common.personalData;

import pss.common.regions.divitions.BizPais;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiDomicilio extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiDomicilio() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizDomicilio(); }
  @Override
	public int     GetNroIcono() throws Exception  { return 20; }
  @Override
	public String  GetTitle()    throws Exception  { return "Domicilio"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormDomicilio.class; }
  @Override
	public String  getKeyField() throws Exception { return "persona"; }
  @Override
	public String  getDescripField()                { return "calle"; }

	@Override
	public Class<? extends JBaseForm> getFormFlat() throws Exception {
		return FormDomicilioFlat.class;
	}
  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    this.addActionQuery ( 1, "Consultar" );
    this.addActionUpdate( 2, "Modificar" );
    this.addActionDelete( 3, "Eliminar"  );
    this.addAction(10, "Principal", null, 1, true, true);
  }
  

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizDomicilio GetcDato() throws Exception {
    return (BizDomicilio) this.getRecord();
  }

  public BizPais getPais () throws Exception{
    BizPais oPais = new BizPais();
    oPais.Read(GetcDato().getPais());
    return oPais;
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActSubmit(this, a.getId()) {
  		@Override
  		public void execSubmit() throws Exception {
  			GetcDato().processMain();
  		}
  	};
  	return super.getSubmitFor(a);
  }

	protected boolean checkActionOnDrop(BizAction a) throws Exception {
		return true;
	}

 
}
