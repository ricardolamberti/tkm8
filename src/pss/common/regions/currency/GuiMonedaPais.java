package pss.common.regions.currency;

import pss.common.regions.currency.conversion.GuiMonedaConvers;
import pss.common.regions.currency.history.GuiMonedaCotizacion;
import pss.common.regions.currency.history.GuiMonedaCotizaciones;
import pss.common.regions.divitions.GuiPais;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiMonedaPais extends JWin {
	
//	public static final String CURRENCY = "Currency";

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  GuiPais oPais;
  GuiMoneda oMoneda;
  public GuiMonedaPais() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizMonedaPais(); }
  @Override
	public int     GetNroIcono() throws Exception  { 
  	if (this.GetcDato().getMoneda().isEmpty()) return 1;
  	return this.GetcDato().ObtenerMoneda().getNroIcono(); 
  }
  @Override
	public String  GetTitle()    throws Exception  { return "Moneda País"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormMonedaPais.class; }
  @Override
	public String  getKeyField() throws Exception { return "moneda"; }

  @Override
	public String getDescripField() {
//    if ( GetVision().equals(BizMonedaPais.VISION_WITH_CTZ) )
//      return "descr_moneda_ctz";
//    else
      return "descr_moneda";
  }
  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    this.addActionQuery ( 1, "Consultar" );
    this.addActionDelete( 3, "Eliminar"  );
    this.addAction(20, "Moneda", null, 5041, true, true );
//    this.addAction(8, "País", null, 1, true, true );
//    this.addAction( 10, "Cotizaciones" , null, 5041, false, false, true, "Detail");
//    this.addAction( 15, "Cambio de Cotización" , null , 708 , true, true);

  }
  
//  @Override
//  public boolean checkActionOnGlobal(BizAction a) throws Exception {
//  	if (this.GetVision().equals(BizMonedaPais.VISION_VIEW))
//  		return false;
//  	return true;
//  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId() == 20 ) return new JActQuery(this.ObtenerMoneda()); 
//  	if ( a.getId() == 8 ) return new JActQuery(this.ObtenerPais());
//  	if ( a.getId() == 10) return new JActWins(this.ObtenerCotizacionesMoneda());  	
//  	if ( a.getId() == 15) return new JActNew(this.getNewCotiz(), 0);  	
    return null;
  }    
  

  @Override
	public boolean OkAction(BizAction zAct) throws Exception {
//    if ( zAct.getId() == 5 ) return this.GetVision().equals(CURRENCY);
//    if ( zAct.getId() == 8 ) return this.GetVision().equals(COUNTRY);
    return true;
  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizMonedaPais GetcDato() throws Exception {
    return (BizMonedaPais) this.getRecord();
  }


//  @Override
//	public String GetIconFile() throws Exception {
////    if ( GetVision().equals(COUNTRY) )
////      return ObtenerPais().GetIconFile();
////    else
//    return ObtenerMoneda().GetIconFile();
//  }

//  public GuiPais ObtenerPais() throws Exception {
//    if ( oPais != null ) return oPais;
//    oPais = new GuiPais();
//    oPais.GetcDato().Read(GetcDato().pPais.getValue());
//    return oPais;
//  }

  public GuiMoneda ObtenerMoneda() throws Exception {
    if ( oMoneda != null ) return oMoneda;
    oMoneda = new GuiMoneda();
    oMoneda.GetcDato().Read(GetcDato().pMoneda.getValue());
    return oMoneda;
  }

//  public JWins getConversiones() throws Exception{
//    GuiMonedaConvers g = new GuiMonedaConvers();
//    g.getRecords().addFilter("company", GetcDato().pCompany.getValue());
//    g.getRecords().addFilter("pais", GetcDato().pPais.getValue());
//    g.getRecords().addFilter("moneda", GetcDato().pMoneda.getValue());
//    g.getRecords().addOrderBy("fecha_hora","desc");
//  	return g;
//  }

//  public GuiMonedaCotizacion getNewCotiz() throws Exception {
//    GuiMonedaCotizacion oCotiz = new GuiMonedaCotizacion();
//    oCotiz.getRecord().addFilter("company", GetcDato().pCompany.getValue());
//    oCotiz.getRecord().addFilter("pais", GetcDato().pPais.getValue());
//    oCotiz.getRecord().addFilter("moneda", GetcDato().pMoneda.getValue());
//    oCotiz.getRecord().addFilter("cotiz_dolar",  "<a href=\"http://www.dolarsi.com\" target=\"_blank\">" +
//    		"<img src=\"http://www.dolarsi.com/cotizador/cotizador_blanco_full.asp\"></a>");
//    return oCotiz;
//  }

}
