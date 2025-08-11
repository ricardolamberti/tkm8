package  pss.common.regions.divitions;

import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;

public class GuiCiudad extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiCiudad() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizCiudad(); }
  @Override
	public int     GetNroIcono() throws Exception  { return 1; }
  @Override
	public String  GetTitle()    throws Exception  { return "Sub División"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormCiudad.class; }
  @Override
	public String  getKeyField() throws Exception { return "ciudad_id"; }
  @Override
	public String  getDescripField()                { return "ciudad"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
    addAction(4, "Sub-Sub Divisiones", null, 1, false, false, true );
    addAction(40, "Codigos postales", null, 1, false, false, true );
		addAction(45, "Vincular Part/depto", null, 10013, true, true);
		addAction(46, "Desvincular Part/dpto", null, 10013, true, true);
		addAction(50, "Ir a part/depto", null, 10013, true, true);
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==45) return getCiudadDepartamento()==null;
  	if (a.getId()==46) return getCiudadDepartamento()!=null;
  	if (a.getId()==50) return getCiudadDepartamento()!=null;
  	return super.OkAction(a);
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==4) return new JActWins(ObtenerLocalidades());
  	if (a.getId()==40) return new JActWins(ObtenerCodigosPostales());
  	if (a.getId()==45) return new JActWinsSelect(getDepartamentos(),this);
  	if (a.getId()==46) return new JActSubmit(this) {
			public void submit() throws Exception {
				GetcDato().execProcessDesvincularPartido();
			}
		};
  	if (a.getId()==50) return new JActQuery(getDepartamento());

  	return super.getSubmitFor(a);
  }
  public GuiDepartamento getDepartamento() throws Exception {
  	if (GetcDato().getObjDepartamento()==null) return null;
  	GuiDepartamento dep = new GuiDepartamento();
  	dep.setRecord(GetcDato().getObjDepartamento());
  	return dep;
  }
  public GuiCiudadDepartamento getCiudadDepartamento() throws Exception {
  	if (GetcDato().getObjCiudadDepartamento()==null) return null;
  	GuiCiudadDepartamento dep = new GuiCiudadDepartamento();
  	dep.setRecord(GetcDato().getObjCiudadDepartamento());
  	return dep;
  }
  public JWins getDepartamentos() throws Exception {
    GuiDepartamentos oLocalidades = new GuiDepartamentos();
    oLocalidades.getRecords().addFilter("pais", GetcDato().getPais());
    oLocalidades.getRecords().addFilter("provincia", GetcDato().getProvincia());
    oLocalidades.getRecords().setParent(this.GetcDato());
    return oLocalidades;
  }
  
  public JWins ObtenerLocalidades() throws Exception {
    GuiLocalidades oLocalidades = new GuiLocalidades();
    oLocalidades.getRecords().addFilter("pais", GetcDato().getPais());
    oLocalidades.getRecords().addFilter("provincia", GetcDato().getProvincia());
    oLocalidades.getRecords().addFilter("ciudad_id", GetcDato().getCiudad());
    oLocalidades.getRecords().setParent(this.GetcDato());
    return oLocalidades;
  }
  public JWins ObtenerCodigosPostales() throws Exception {
    GuiCiudadCPes oCP = new GuiCiudadCPes();
    oCP.getRecords().addFilter("pais", GetcDato().getPais());
    oCP.getRecords().addFilter("provincia", GetcDato().getProvincia());
    oCP.getRecords().addFilter("ciudad_id", GetcDato().getCiudad());
    oCP.getRecords().setParent(this.GetcDato());
    return oCP;
  }

  public String getTipoLocalidad() throws Exception{
    String sPais = GetcDato().getPais();
    if (!sPais.equals("")){
      BizPais oPais = new BizPais();
      oPais.Read(sPais);
      return oPais.GetLocalidad();
    }
    else return "";
  }

  public String getTipoCiudad() throws Exception{
    String sPais = GetcDato().getPais();
    if (!sPais.equals("")){
      BizPais oPais = new BizPais();
      oPais.Read(sPais);
      return oPais.GetCiudad();
    }
    else return "";
  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizCiudad GetcDato() throws Exception {
    return (BizCiudad) this.getRecord();
  }
  @Override
  public JAct Drop(JBaseWin zBaseWin) throws Exception {
  	if (zBaseWin instanceof GuiDepartamento) {
  		GuiDepartamento dep = (GuiDepartamento)zBaseWin;
  		dep.GetcDato().execVincularCiudad(this.GetcDato());
  		return null;
  	}
  	return super.Drop(zBaseWin);
  }
}
