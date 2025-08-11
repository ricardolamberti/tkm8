package pss.core.win.actions;

import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;


public class GuiActions extends JWins {

  public GuiActions() throws Exception {
//    SetEstatico       ( true );
 }

  @Override
	public JRecords<BizAction> ObtenerDatos() throws Exception { return new BizActions();}
  @Override
	public Class<? extends JWin> GetClassWin() { return GuiAction.class; }
  @Override
	public int GetNroIcono() throws Exception { return 47; }
  @Override
	public String GetTitle() throws Exception { return "Acciones"; }

 @Override
 public boolean acceptsSecurityAction() {
   return false;
 }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("Seguridad?", "restringido");
    zLista.setColumnTranslated("descripcion", true);
  }


/*  public JList GetItemsArbol() throws Exception {
  	
//    String sClase = zOrigen.getClass().getName();
		JList oItemsArbol = JCollectionFactory.createList();
    firstRecord();
    while ( nextRecord() ) {
      GuiAction oAction = (GuiAction) getRecord();
//      if (! oAction.GetcDato().pOwner.GetValor().equals(sClase) ) continue;
      if (oAction.GetcDato().ifArbol()) oItemsArbol.addElement(oAction);
    }
    return oItemsArbol;
  }*/

/*  public JBaseWin GetArbolSelect(JBaseWin zOrigen) throws Exception {
    JList oActions = this.GetItemsArbol();
    JIterator oActionsIt = oActions.getIterator();
    while ( oActionsIt.hasMoreElements() ) {
      GuiAction oAction = (GuiAction) oActionsIt.nextElement();
      if ( ! oAction.GetcDato().ifArbolDetail() ) continue;
      return oAction.GetcDato().GetAction().getResult();
    }
    return null;
  }
*/
  public BizActions GetcDatos() throws Exception {
    return (BizActions) this.getRecords();
  }
  
  
//public static JActionGalleryRetail create(String company, String zOper, String zDescrip, int icono) throws Exception {
//JActionGalleryRetail oOpePos = new JActionGalleryRetail();
//oOpePos.pCompany.setValue(company);
//oOpePos.pAccion.setValue(zOper);
//oOpePos.pDescr.setValue(JLanguage.translate(zDescrip));
//oOpePos.SetTitle("Operacion de POS");
//oOpePos.pNroIcono.setValue(icono);
//return oOpePos;
//}

}

