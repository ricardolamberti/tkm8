package  pss.bsp.bo;

import pss.bsp.bo.formato.GuiFormato;
import pss.bsp.consolidador.IConsolidador;
import pss.bsp.consolidador.consolidacion.detalle.GuiConsolidaciones;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiInterfazBO extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiInterfazBO() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizInterfazBO(); }
  public int GetNroIcono()   throws Exception { return 10058; }
  public String GetTitle()   throws Exception { return "Interfaz BackOffice"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormInterfazBO.class; }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormNewInterfazBO.class; }
  public String  getKeyField() throws Exception { return "idInterfaz"; }
  public String  getDescripField() { return "descripcion"; }
  public BizInterfazBO GetcDato() throws Exception { return (BizInterfazBO) this.getRecord(); }

  
  public void createActionMap() throws Exception {
		createActionQuery();
		createActionDelete();
 		this.addAction(10, "Cabecera", null, 10020, false, false, true, "Group");
 		this.addAction(20, "Detalle", null, 10020, false, false, true, "Group");
 		this.addAction(25, "Consolidado", null, 10020, false, false, true, "Group");
 		this.addAction(30, "Configurar Formato", null, 10023, true, true, true, "Group");
// 		this.addAction(40, "Conciliar", null, 10023, true, true, true, "Group");
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActQuery(this.GetcDato().getHeader());
		if (a.getId() == 20)	return new JActWins(this.GetcDato().getDetail());
		if (a.getId() == 25)	return new JActWins(this.GetcDato().getDetailConsolidado());
		if (a.getId() == 30)	return new JActQuery(this.getFormato());
//		if (a.getId() == 40)	return new JActSubmit(this) {
//			public void submit() throws Exception {
//				getConciliacion(true);
//			}  
//		};	
		return null;
	}

	
	public GuiFormato getFormato() throws Exception {
		GuiFormato formato = new GuiFormato();
		formato.SetBaseDato(GetcDato().getObjFormato());
		return formato;
	}
	public JWins getConciliacion(boolean force) throws Exception {
//		generateConciliancion(force);
		GuiConsolidaciones con =	new GuiConsolidaciones();
		con.getRecords().addFilter("company", GetcDato().getCompany());
//		con.getRecords().addFilter("owner", GetcDato().getOwner());
		con.getRecords().addFilter("tipo", IConsolidador.BO);
//		con.getRecords().addFilter("idPDF", GetcDato().getIdpdf());
		return con;
	}
	
 }
