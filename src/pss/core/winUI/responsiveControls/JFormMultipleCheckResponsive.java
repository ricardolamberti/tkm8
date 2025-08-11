package pss.core.winUI.responsiveControls;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;

public class JFormMultipleCheckResponsive extends JFormControlResponsive {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private JWins     oWinsAsociado;
  private String oDato;
  private JControlCombo oControlCombo = null;
	
  

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JFormMultipleCheckResponsive() throws Exception {
  }

  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //  getters / setters
  //
  public JWins GetWinsAsociado() throws Exception { return oWinsAsociado ; }
  public void SetControlWin ( JControlCombo zValor )  { oControlCombo  = zValor; }


	public void SetWinsAsociado( JWins     zWins  )  throws Exception {
    oWinsAsociado = zWins;
  }


  /**
   * Clears the current value.
   * @throws Exception
   */
  @Override
	public void clear() throws Exception {
 	 if (getProp()==null) return;
 	 getProp().setNull();
  }


  public  JList<JWin> GetWinsElegido() throws Exception {
  	updateFromProp();
    if ( this.oWinsAsociado == null ) return null;
    if ( oDato==null ) return null;
    if ( oDato.equals("") ) return null;
    JList<JWin> aItems = JCollectionFactory.createList();
    JIterator<JWin> oIt = GetWinsAsociado().getRecords().getStaticIterator();
    while ( oIt.hasMoreElements()) {
      JWin oWin = (JWin) oIt.nextElement();
      if (oDato.indexOf(","+oWin.GetValorItemClave().trim())!=-1) {
      	aItems.addElement(oWin);
      }
        
    }
    return aItems;
  }
 
 

  public JControlCombo getControlCombo() {
  	if (oControlCombo!=null) return this.oControlCombo;
  	this.oControlCombo = new JControlCombo() {
  		@Override
			public JWins getRecords(boolean zOneRow) throws Exception {return oWinsAsociado;}
  	};
    return this.oControlCombo;
  }
  


  //-------------------------------------------------------------------------//
  // Seteo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public void setValue( String zVal ) throws Exception {
  	getProp().setValue(zVal);
  	updateFromProp();
  }
  
 

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() throws Exception {
  	updateFromProp();
    return oDato;
  }

  @Override
	public String getValueDescription() throws Exception {
  	updateFromProp();
    return oDato;
  }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() throws Exception {
  try {
  	updateFromProp();
    if ( oWinsAsociado == null ) return false;
    if ( oDato.equals("") )
      return false;
    else
      return true;
   } catch ( Exception e) {
      return false;
   }
  }

  public boolean ifDependenciaVirtual() {
    return ( this.GetDependVirtual() != null );
  }



  @Override
	public void Remove() throws Exception {
  }

	private void updateFromProp() throws Exception {
		if (getProp()==null) return;
		if (getProp().isNull()) {
	    this.oDato=null;
			return;
		}
    oDato=getProp().toString();

	}



}



