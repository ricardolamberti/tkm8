package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;


public class GuiOperacion extends JWin {

  public GuiOperacion() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizOperacion(); }
  @Override
	public int GetNroIcono()       throws Exception { return this.GetcDato().getNroIcono(); }
  @Override
	public String GetTitle()       throws Exception { return "Operación"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormOperacion.class; }
  @Override
	public String getKeyField()   throws Exception { return "operacion"; }
  @Override
	public String getDescripField()                  { return "descripcion"; }


  public BizOperacion GetcDato() throws Exception {
    return (BizOperacion) getRecord();
  }

  @Override
	public void createActionMap() throws Exception {
    addActionQuery(1, "Consultar" );
//    addActionUpdate(2, "Modificar" );
//    addActionDelete (3, "Eliminar" );
    addAction(10, "Roles", null, 43, false, false, true, "Detail" );
  };
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(this.getRoles());
  	return super.getSubmitFor(a);
  }
  
  public JWins getRoles() throws Exception {
    GuiOperacionRoles records = new GuiOperacionRoles();
    records.setOperParent(this);
    records.getRecords().addFilter("company", GetcDato().pCompany.getValue());
    records.getRecords().addFilter("operacion", GetcDato().pOperacion.getValue());
    records.SetVision(BizOperacionRol.VISION_ROL);
    return records;
  }
  
  public JAct Drop(JBaseWin baseWin) throws Exception {
  	if (baseWin instanceof GuiRol) {
  		this.createOperacionRol(JRecords.createRecords(baseWin.GetBaseDato()));
  	}
  	if (baseWin instanceof GuiRoles) {
  		this.createOperacionRol(JRecords.createRecords(baseWin.GetBaseDato()));
  	}
  	return null;
  }

  private void createOperacionRol(JRecords<?> roles) throws Exception {
  	JIterator<?> iter = roles.getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizRol rol = (BizRol)iter.nextElement(); 
  		BizOperacionRol v = new BizOperacionRol();
	  	v.setCompany(this.GetcDato().getCompany());
	  	v.SetOperacion(this.GetcDato().GetOperacion());
	  	v.setRol(rol.getRol());
	  	v.execProcessInsert();
  	}
  }

}



