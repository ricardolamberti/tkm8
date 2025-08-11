package pss.core.winUI.responsiveControls;

import java.awt.event.ActionEvent;

import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;

public class JFormMultipleResponsive extends JFormControlResponsive {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private JWins     oWinsAsociado;
  private boolean   bNullItem       = true;
  private JList<JWin>     aItems          = null;
//  private RStructure rCopia;
  private JControlCombo oControlCombo = null;
	private boolean bViejaVersion;
	private boolean bFirstOcur=false;
  private JWin oWinNulo;
  private JWin oWinTodos;
  private JWin oDato;
  private boolean onlyShowSelected=true;
  private String extraClassImage;

  public String getExtraClassImage() {
		return extraClassImage;
	}


	public JFormMultipleResponsive setExtraClassImage(String extraClassImage) {
		this.extraClassImage = extraClassImage;
		return this;
	}


	public boolean isOnlyShowSelected() {
		return onlyShowSelected;
	}


	public JFormMultipleResponsive setOnlyShowSelected(boolean onlyShowSelected) {
		this.onlyShowSelected = onlyShowSelected;
		return this;
	}


	public JWin getWinNulo() {
		return oWinNulo;
	}


	public void setWinNulo(JWin oWinNulo) {
		this.oWinNulo = oWinNulo;
	}


	public JWin getWinTodos() {
		return oWinTodos;
	}


	public void setWinTodos(JWin oWinTodos) {
		this.oWinTodos = oWinTodos;
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JFormMultipleResponsive() throws Exception {
    oWinTodos = JWin.createVirtualWin("", "Todos", 380);
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
  public void SetNullItem   ( boolean zValor )  { bNullItem  = zValor; }
  public void setFirstOcur( boolean zValor )  { bFirstOcur= zValor; }
  public boolean isbFirstOcur() {
		return bFirstOcur;
	}


	public void SetWinsAsociado( JWins     zWins  )  throws Exception {
    bViejaVersion = true;
    oWinsAsociado = zWins;
    aItems=null;
  }

  /**
   * Clears the current value.
   * @throws Exception
   */
  @Override
	public void clear() throws Exception {
 	 if (getProp()==null) return;
  	getProp().setNull();
  	updateFromProp();
  }

  /**
   * Answers the choosen win.
   * @param zClave the key to identify the win among the items
   * @return the currently selected Win
   * @throws Exception
   */
  public JWin GetWinElegido(String zClave) throws Exception {
  	JWins oWins = this.ObtenerWins(false);
	  if ( oWins == null ) {
	    crearComboVacio();
	    return null;
	  }
    if ( zClave==null ) return oWinNulo;

    JIterator<JWin> oIt = this.getIterator(oWins,this.ifPermitirTodo() ? "T" : "R");
    while ( oIt.hasMoreElements()) {
      JWin oWin = (JWin) oIt.nextElement();
      if ( oWin == oWinNulo) continue;
      if (oWin.GetValorItemClave().trim().toUpperCase().equals(zClave.trim().toUpperCase()))
        return oWin;
    }
    
    if ( zClave != null ) return null;
    return null;
  }
  
  public JWin GetFirstWinElegido() throws Exception {
  	JWins oWins = this.ObtenerWins(false);
	  if ( oWins == null ) {
	    crearComboVacio();
	    return oWinNulo;
	  }
	  
	   JIterator<JWin> oIt = this.getIterator(oWins,this.ifPermitirTodo() ? "T" : "R");
	    while ( oIt.hasMoreElements()) {
	      JWin oWin = (JWin) oIt.nextElement();
	      if ( oWin == oWinNulo) continue;
      return oWin;
    }
    return null;
  }

  /**
   * Loads the given combo data.
   * @param zCombo
   * @param zStyle
   * @throws Exception
   */
  public JIterator<JWin> getIterator(JWins wins, String zStyle) throws Exception {
  	if (aItems!=null) return aItems.getIterator();
  	aItems= JCollectionFactory.createList();

    if (zStyle.equals("T") ) {
      aItems.addElement(oWinTodos );
    } else if ( bNullItem && !ifRequerido() ) {
      aItems.addElement(oWinNulo);
    }

    wins.ReRead();
    wins.firstRecord();
    wins.getRecords().clearFilters();
    boolean bWinVerificado = false;
    while ( wins.nextRecord() ) {
      JWin oWin = wins.getRecord();
      if( !bWinVerificado ) {
        if( oWin.getKeyField() == null || oWin.getKeyField().length() == 0 ) {
          JExcepcion.SendError( oWin.getClass().getName() + " must answer with a valid field name through GetItemClave() in order to associate it to this combo box" );
        }
        if( oWin.getDescripField() == null || oWin.getDescripField().length() == 0 ) {
          JExcepcion.SendError( oWin.getClass().getName() + " must answer with a valid field name through GetItemDescrip() in order to associate it to this combo box" );
        }
        bWinVerificado = true;
      }
      
      aItems.addElement( oWin );
    }

     return aItems.getIterator();
  }
// 
//  public void RefreshAndSet(boolean zOneRow, String zClave) throws Exception {
//    this.RefreshList(zOneRow);
//    JWin oObj;
//    if (zClave==null) {
//    	oObj =  oDato;
//    } else 
//    	oObj = this.GetWinElegido(zClave);
//    oDato=oObj;
//  }

  public JControlCombo getControlCombo() {
  	if (oControlCombo!=null) return this.oControlCombo;
  	this.oControlCombo = new JControlCombo() {
  		@Override
			public JWins getRecords(boolean zOneRow) throws Exception {return oWinsAsociado;}
  	};
    return this.oControlCombo;
  }
  

  
  private JWins ObtenerWins(boolean zOneRow) throws Exception {
  	
  	getControlCombo().setFormControl(this);
    JWins oWins = null;
    try {
    	oWins = this.getControlCombo().getRecords(this, zOneRow);
    } catch (Exception e) {
    	return null;
    }
    
    if ( oWins == null ) return null;
     if ( zOneRow && bViejaVersion) 
    	 this.SetearClave(oWins);
     
    if (oWinsAsociado==null || !oWins.equals(oWinsAsociado))
    	aItems=null;
    oWinsAsociado=oWins;
    return oWins;
  }

//  @Override
//	protected void asignDefaultValue(JObject value) throws Exception {
//  	if (bFirstOcur) this.RefreshAndSet(false, null );
//  	if (value==null) return;
//  	this.RefreshAndSet(false, value.toString() );
//  }
  //-------------------------------------------------------------------------//
  // Seteo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public void setValue( String zVal ) throws Exception {
	 if (getProp()==null) return;
   getProp().setValue(zVal);
   updateFromProp();
  }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() throws Exception {
    JWin oWin;
    updateFromProp();
    Object Obj = oDato;
    oWin =  (JWin) Obj;
    if ( oWin == null ) return "";
    if ( oWin == oWinNulo ) return "";
    return oWin.GetValorItemClave();
  }

    @Override
		public String getValueDescription() throws Exception {
      JWin oWin = (JWin)oDato;
      if ( oWin == null ) return "";
      if ( oWin == oWinNulo ) return "";
      return oWin.getDescripFieldValue();
    }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() {
  try {
  	updateFromProp();
    if ( oWinsAsociado == null ) return false;
    if ( getValue().equals("") )
      return false;
    else
      return true;
   } catch ( Exception e) {
      return false;
   }
  }


/*  public boolean ifRelacionado() {
    return (this.sFiltrosFKey != null);
  }
*/
  public void SetearClave(JWins zWins) throws Exception {
    JWin  oWin  = zWins.createWin();
    if ( oWin.getRecord()==null ) return;
    if (oWin.getKeyField()==null || oWin.getKeyField().trim().length() < 1) {
      JExcepcion.SendError("No 'item_clave' attribute is defined for Wins of class " + oWin.getClass().getName());
    }
    zWins.getRecords().setFilterValue(oWin.getKeyField(), this.getProp().toString(),this.getProp().getObjectType() );
  }


  public boolean ifDependenciaVirtual() {
    return ( this.GetDependVirtual() != null );
  }

//
//  /**
//   * Refreshes the combo data.
//   * @param zOneRow
//   * @throws Exception
//   */
//  public void RefreshList(boolean zOneRow) throws Exception {
//  	
//    JWin ValorAnt = (JWin) oDato;
//
//    oWinsAsociado = this.ObtenerWins(zOneRow);
//    if ( oWinsAsociado == null ) {
//      crearComboVacio();
//      return;
//    }
//
//    this.CargarList(this.ifPermitirTodo() ? "T" : "R");
//    
//    
//    if ( zOneRow ) return;
//    if ( this.ifSetearValorAnterior(ValorAnt) ) {
//    	oDato=this.GetWinElegido(ValorAnt.GetValorItemClave());
//    } else if ( this.bFirstOcur && aItems.getElementAt(0)!=null ) {
//    	oDato=null;
//      this.SetValorDefault(aItems.getElementAt(0).GetValorItemClave());
//    } else {
//    	oDato=oWinNulo;
//    }
//
//  }


  public void ProcesarEnter(ActionEvent e) {
  }

  @Override
	public void Remove() throws Exception {
  }

//
//  private boolean ifSetearValorAnterior(JWin zValorAnt) throws Exception {
//   if ( zValorAnt == null ) return false;
//   if ( zValorAnt.getRecord() == null ) return false;
//   if ( zValorAnt.getRecord() instanceof BizVirtual ) return true;
//   if ( ! zValorAnt.getClass().equals(oWinsAsociado.GetClassWin()) ) return false;
//   return true;
//  }
//
  private void crearComboVacio() throws Exception {
    aItems.removeAllElements();
    aItems.addElement(oWinNulo);
    clear();
  }


	private void updateFromProp() throws Exception {
		if (getProp()==null) return;
		if (getProp().isNull()) {
	  	if (this.aItems != null && this.aItems.containsElement(oWinNulo)) {
	  		oDato=this.oWinNulo;
	    } else {
	      oDato=null;
	    }
			return;
		}
 	  oDato = this.GetWinElegido( getProp().toString());
	}

}



