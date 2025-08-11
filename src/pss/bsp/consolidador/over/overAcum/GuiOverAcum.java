package pss.bsp.consolidador.over.overAcum;

import pss.bsp.consolidador.over.GuiOvers;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiOverAcum extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiOverAcum() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizOverAcum(); }
  public int GetNroIcono()   throws Exception { return 10006; }
  public String GetTitle()   throws Exception { return "Analisis Over por aerolineas"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormOverAcum.class; }
  public String  getKeyField() throws Exception { return "id_aerolinea"; }
  public String  getDescripField() { return "id_aerolinea"; }
  public BizOverAcum GetcDato() throws Exception { return (BizOverAcum) this.getRecord(); }
  
  @Override
  public int GetDobleClick() {
  	return 30;
  }
  public void createActionMap() throws Exception {
 		this.addAction(20, "Detalle", null, 10020, false, false, true, "Group").setAccessToDetail(true);
 		this.addAction(30, "Ver Detalle", null, 10020, true, true, true, "Group");
  }

 
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 20)	return new JActWins(this.getDetail());
		if (a.getId() == 30)	return new JActWins(this.getDetail());
		return null;
	}

	
	public JWins getDetail() throws Exception {
		GuiOvers overs = new GuiOvers();
		overs.getRecords().addFilter("company", GetcDato().getCompany());
//		overs.getRecords().addFilter("owner", GetcDato().getOwner());
		overs.getRecords().addFilter("idpdf", GetcDato().getIdpdf());
		overs.getRecords().addFilter("id_aerolinea", GetcDato().getIdaerolinea());
		return overs;
	}
  @Override
  public String getFieldForeground(String zColName) throws Exception {
  	
  	return GetcDato().getFieldForeground(zColName);
  }
 


 }
