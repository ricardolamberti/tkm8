package pss.common.dbManagement.depuration;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiDepuration extends JWin {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiDepuration() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizDepuration(); }
  @Override
	public int     GetNroIcono() throws Exception  { return 901; }
  @Override
	public String  GetTitle()    throws Exception  { return "Depuración de Tablas"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormDepuration.class; }
  @Override
	public String  getKeyField() throws Exception { return "modulo"; }
  @Override
	public String  getDescripField()                { return "descr_entidad"; }


  @Override
	public void createActionMap() throws Exception {
    addActionQuery(1, "Consultar");
    addActionUpdate(2, "Modificar");
    addActionDelete (3, "Eliminar");
  }
 /* 
  * Devuelvo el dato ya casteado
  */
 public BizDepuration GetcDato() throws Exception { return (BizDepuration) this.getRecord(); }
  

}
