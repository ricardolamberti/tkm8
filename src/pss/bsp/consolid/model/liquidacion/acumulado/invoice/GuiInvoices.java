package  pss.bsp.consolid.model.liquidacion.acumulado.invoice;

import pss.bsp.consolid.model.liquidacion.acumulado.BizLiqAcum;
import pss.bsp.dk.BizClienteDK;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActReport;
import pss.core.winUI.lists.JWinList;
import pss.tourism.dks.BizDk;

public class GuiInvoices extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiInvoices() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10036; } 
  public String  GetTitle()    throws Exception  { return "Invoices"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiInvoice.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 10, "Nueva Invoice Liquidacion" );
    addActionNew( 20, "Nueva Invoice Servicios BSP" );
    addActionNew( 30, "Nueva Invoice Servicion Aereo" );
    addActionNew( 40, "Nueva Invoice Liquidacion" );
    addActionNew( 50, "Nueva Invoice Liquidacion" );
    addActionNew( 60, "Nueva Invoice Liquidacion" );
    addActionNew( 70, "Nueva Invoice Liquidacion" );
 }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
   	if (getObjLiqAcum()==null) return false;
   	if (getObjLiqAcum().getObjMailing()==null) return false;
   	if (getObjLiqAcum().getObjMailing().getFormato()==null) return false;
     	if (a.getId() == 10)
  		return getObjLiqAcum().getObjMailing().getFormato().equals(BizInvoice.FM_INVOICE_AER917);
   	if (a.getId() == 20)
   		return getObjLiqAcum().getObjMailing().getFormato().equals(BizInvoice.FM_INVOICE_AER917);
   	if (a.getId() == 30)
   		return getObjLiqAcum().getObjMailing().getFormato().equals(BizInvoice.FM_INVOICE_AER917);
  	if (a.getId() == 40)
  		return getObjLiqAcum().getObjMailing().getFormato().equals(BizInvoice.FM_INVOICE_ETV917);
   	if (a.getId() == 50)
  		return getObjLiqAcum().getObjMailing().getFormato().equals(BizInvoice.FM_INVOICE_KIW917);
  	if (a.getId() == 60)
  		return getObjLiqAcum().getObjMailing().getFormato().equals(BizInvoice.FM_INVOICE_HMI917);
  	if (a.getId() == 70)
  		return getObjLiqAcum().getObjMailing().getFormato().equals(BizInvoice.FM_INVOICE_FLR917);
 	return super.OkAction(a);
  }
  
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActNew(this.createJoinWinH1(), 0);
		if (a.getId() == 20)
			return new JActNew(this.createJoinWinH2(), 0);
		if (a.getId() == 30)
			return new JActNew(this.createJoinWinH3(), 0);
		if (a.getId() == 40)
			return new JActNew(this.createJoinWinH4(), 0);
		if (a.getId() == 50)
			return new JActNew(this.createJoinWinH5(), 0);
		if (a.getId() == 60)
			return new JActNew(this.createJoinWinH6(), 0);
		if (a.getId() == 70)
			return new JActNew(this.createJoinWinH7(), 0);
		return this.getSubmitFor(a);
	}
  
  public JWin createJoinWinH1() throws Exception {
  	GuiInvoice win = (GuiInvoice) super.createJoinWin();
  	win.GetcDato().fillFormato(1,getFilterValue("company"),getFilterValue("dk"),Long.parseLong(getFilterValue("liq_acum_id")));
  	return win;
  }
  public JWin createJoinWinH2() throws Exception {
  	GuiInvoice win = (GuiInvoice) super.createJoinWin();
  	win.GetcDato().fillFormato(2,getFilterValue("company"),getFilterValue("dk"),Long.parseLong(getFilterValue("liq_acum_id")));
  	return win;
  } 
  public JWin createJoinWinH3() throws Exception {
  	GuiInvoice win = (GuiInvoice) super.createJoinWin();
  	win.GetcDato().fillFormato(3,getFilterValue("company"),getFilterValue("dk"),Long.parseLong(getFilterValue("liq_acum_id")));
  	return win;
  }
  public JWin createJoinWinH4() throws Exception {
  	GuiInvoice win = (GuiInvoice) super.createJoinWin();
  	win.GetcDato().fillFormato(4,getFilterValue("company"),getFilterValue("dk"),Long.parseLong(getFilterValue("liq_acum_id")));
  	return win;
  }
  public JWin createJoinWinH5() throws Exception {
  	GuiInvoice win = (GuiInvoice) super.createJoinWin();
  	win.GetcDato().fillFormato(5,getFilterValue("company"),getFilterValue("dk"),Long.parseLong(getFilterValue("liq_acum_id")));
  	return win;
  }
  public JWin createJoinWinH6() throws Exception {
  	GuiInvoice win = (GuiInvoice) super.createJoinWin();
  	win.GetcDato().fillFormato(6,getFilterValue("company"),getFilterValue("dk"),Long.parseLong(getFilterValue("liq_acum_id")));
  	return win;
  }
  public JWin createJoinWinH7() throws Exception {
  	GuiInvoice win = (GuiInvoice) super.createJoinWin();
  	win.GetcDato().fillFormato(7,getFilterValue("company"),getFilterValue("dk"),Long.parseLong(getFilterValue("liq_acum_id")));
  	return win;
  }
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("fecha_desde");
    zLista.AddColumnaLista("fecha_hasta");
    zLista.AddColumnaLista("numero");
    zLista.AddColumnaLista("hoja_str");
    
  }
  BizLiqAcum objLiqAcum;
  public BizLiqAcum getObjLiqAcum() throws Exception {
  	if (objLiqAcum!=null) return objLiqAcum;
  	BizLiqAcum o = new BizLiqAcum();
  	o.dontThrowException(true);
  	if(!o.readById(Long.parseLong(getFilterValue("liq_acum_id")))) return null;
  	return objLiqAcum=o;
  	
  }

}
