package  pss.common.security;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;


public class GuiOperaciones extends JWins {

  public GuiOperaciones() throws Exception {
    getRecords().addOrderBy( "operacion" );
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiOperacion.class; }
  @Override
	public int GetNroIcono() throws Exception { return 44; }
  @Override
	public String GetTitle() throws Exception { return "Operaciones"; }

  @Override
	public void createActionMap() throws Exception {
//  	this.addAction(10, "Galeria de Funciones", null, GuiIcon.MULTI_MAS_ICON, true, true);
  }
  
//  @Override
//	public JAct getSubmitFor(BizAction a) throws Exception {
//  	if (a.getId()==10) return new JActWinsSelect(this.getActionGallery(), this, true);
//  	return null;
//  }
//  
//  public JAct Drop(JBaseWin baseWin) throws Exception {
//  	if (baseWin instanceof GuiAction) {
//  		this.createOperacion(JRecords.createRecords(baseWin.GetBaseDato()));
//  	}
//  	if (baseWin instanceof GuiActions) {
//  		this.createOperacion(JRecords.createRecords(baseWin.GetBaseDato()));
//  	}
//  	return null;
//  }
    
  @Override
  public void ConfigurarColumnasLista(JWinList lista) throws Exception {
  	lista.AddIcono("");
  	lista.AddColumnaLista("descripcion");
  }
  
//  private void createOperacion(JRecords<?> records) throws Exception {
//  	JIterator<?> iter = records.getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		BizAction a = (BizAction)iter.nextElement(); 
//	  	BizOperacion p = new BizOperacion();
//	  	p.setCompany(a.getCompany());
//	  	p.SetOperacion(a.getIdAction());
//	  	p.SetDescrip(a.GetDescr());
//	  	p.execProcessInsert();
//  	}
//  }
  
//  public JWins getActionGallery() throws Exception {
//  	GuiActions acts = new GuiActions();
//  	acts.setDropListener(this);
//  	String company = this.getRecords().getFilterValue("company");
//  	BizActions records = BizActions.createFromMap(company, JActionGallery.getActionGallery());
//  	acts.toStatic(records);
//  	return acts;
//  }

}


