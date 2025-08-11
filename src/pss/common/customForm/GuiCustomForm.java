package pss.common.customForm;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiCustomForm extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCustomForm() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizCustomForm(); }
  @Override
	public int GetNroIcono()   throws Exception {
  	return 5104; 
  }
  @Override
	public String GetTitle()   throws Exception { return "Form"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCustomForm.class; }
  public BizCustomForm GetcDato() throws Exception { return (BizCustomForm) this.getRecord(); }


  @Override
	public void createActionMap() throws Exception {
  	super.createActionMap();
    this.addAction( 10, "Campos" , null , 716 , false, false);
  }
  
 
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==10) return new JActWins(this.getCampos());
  	return super.getSubmitFor(a);
  }
  
  public JWins getCampos() throws Exception {
  	GuiCustomFormFields guis = new GuiCustomFormFields();
  	guis.getRecords().addFilter("company", this.GetcDato().getCompany());
  	guis.getRecords().addFilter("secuencia", this.GetcDato().getSecuencia());
  	return guis;
  }
  
 }
