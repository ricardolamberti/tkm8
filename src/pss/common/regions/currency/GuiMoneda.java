package pss.common.regions.currency;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActDelete;
import pss.core.win.submits.JActNewSubmit;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActUpdate;
import pss.core.winUI.forms.JBaseForm;

public class GuiMoneda extends JWin {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiMoneda() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizMoneda(); }
  @Override
	public int GetNroIcono() throws Exception  { 
  	int nro = this.GetcDato().getNroIcono();
  	if (nro!=0) return nro;
  	return 5041; 
  }
  @Override
	public String  GetTitle()    throws Exception  { return "Moneda"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormMoneda.class; }
  @Override
	public String  getKeyField() throws Exception { return "codigo"; }
  @Override
	public String  getDescripField()                { return "descripcion"; }

  public BizMoneda GetcDato() throws Exception {
    return (BizMoneda) getRecord();
  }

  @Override
	public void createActionMap() throws Exception {
  	super.createActionMap();
    this.addAction(10, "Conversiones", null, 41, false, false);
  }
  
  @Override
	public JAct getSubmit(BizAction a) throws Exception {
  	if ( a.getId()==0 ) return new JActNewSubmit(this.getRelativeWin(), a.getId(), true);
  	if ( a.getId()==1 ) return new JActQuery (this.getRelativeWin());
  	if ( a.getId()==2 ) return new JActUpdate(this.getRelativeWin(), a.getId());
  	if ( a.getId()==3 ) return new JActDelete(this.getRelativeWin(), a.getId());
//  	if ( a.getId()==10) return new JActWins(ObtenerMonedaPaises());
  	return super.getSubmit(a);
  }

//  public JWins ObtenerMonedaPaises() throws Exception {
//    JEnlace oEnlace = new JEnlace();
//    oEnlace.AddValor("codigo", "moneda", GetcDato().pCodigo.getValue() );
//
//    GuiMonedaPaises oMonPaises = new GuiMonedaPaises();
//    oMonPaises.SetVision("Pais");
//    oMonPaises.SetEnlazado(oEnlace);
//    return oMonPaises;
//  }

//  public static JWins getMonedasPaisLocal() throws Exception {
//    GuiMonedas oMonedas = new GuiMonedas();
//    oMonedas.getRecords().addJoin("mon_moneda_pais");
//    oMonedas.getRecords().addFixedFilter(" where mon_moneda.codigo = mon_moneda_pais.moneda ");
//    oMonedas.getRecords().addFixedFilter(" and   mon_moneda_pais.pais = '"+BizUsuario.getUsr().getCountry()+"'");
//    return oMonedas;
//  }
  
  @Override
  public int GetSimpleClick() throws Exception {
  	return 1;
  }

}
