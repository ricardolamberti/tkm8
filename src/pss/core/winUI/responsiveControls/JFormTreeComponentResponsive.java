package pss.core.winUI.responsiveControls;

import java.awt.event.ActionEvent;

import pss.core.services.fields.JObject;
import pss.core.services.records.BizVirtual;
import pss.core.tools.JExcepcion;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlTree;

public class JFormTreeComponentResponsive extends JFormControlResponsive {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private JWins     									oWinsAsociado;
  private IControl        						oControlTree;
  private String 											icon;

	String oDato;
	JWin oDatoWin;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JFormTreeComponentResponsive() throws Exception {
  }


	public void setControlTree(JControlTree oControlTree) {
		this.oControlTree = oControlTree;
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //  getters / setters
  //
//  public javax.swing.JTree GetFormTree() { return oTree ; }
  public void SetControlWin ( IControl zValor )  { oControlTree = zValor; }
  public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}

  public JWins GetWinsAsociado() throws Exception { return oWinsAsociado ; }

	public void SetWinsAsociado( JWins     zWins  )  throws Exception {
    oWinsAsociado = zWins;
 }
  public IControl getControlTree() {
  	if (oControlTree!=null) return this.oControlTree;
  	this.oControlTree = new JControlTree() {
  		@Override
			public JWins getRecords(boolean zOneRow) throws Exception {return oWinsAsociado;}
  	};
    return this.oControlTree;
  }
  

  @Override
	protected int getDefaultWidth() {
    return 120;
  }


  /**
   * Lets the component editable.
   * @param zModo the edit mode
   * @throws Exception
   */
  @Override
	public void edit( String zModo ) throws Exception {
  	SetReadOnly(false);
  }

 private JWins ObtenerWins(boolean zOneRow) throws Exception {
  	getControlTree().setFormControl(this);
    JWins oWins = null;
    try {
    	oWins = this.getControlTree().getRecords(this, zOneRow);
    } catch (Exception e) {
    	return null;
    }
    if ( oWins == null ) return null;
    return oWins;
  }


  public JWin GetFirstWinElegido() throws Exception {
    if ( this.oWinsAsociado == null ) return null;
//    Iterator oIt = aItems.iterator();
//    while ( oIt.hasNext()) {
//      JWin oWin = (JWin) oIt.next();
//      if ( oWin == oWinNulo) continue;
//      return oWin;
//    }
    return null;
  }

  
	

  public void RefreshAndSet(boolean zOneRow, String zClave) throws Exception {
    this.RefreshTree(zOneRow);
    if (zClave!=null) 
    	oDato=zClave;
  }

  @Override
	protected void asignDefaultValue(JObject value) throws Exception {
  	if (value==null) return;
  	this.RefreshAndSet(false, value.toString() );
  }
  
  //-------------------------------------------------------------------------//
  // Seteo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public void setValue( String zVal ) throws Exception {
    this.RefreshAndSet( true, zVal );
  }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() throws Exception {
 

    if ( oDato == null ) return "";
    return oDato;
  }

    @Override
		public String getValueDescription() throws Exception {
      JWin oWin = (JWin)oDatoWin;
      if ( oWin == null ) return "";
 //     if ( oWin == oWinNulo ) return "";
      return oWin.getDescripFieldValue();
    }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() {
  try {
    if ( oWinsAsociado == null ) return false;
    if ( getValue().equals("") )
      return false;
    else
      return true;
   } catch ( Exception e) {
      return false;
   }
  }



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


  /**
   * Refreshes the combo data.
   * @param zOneRow
   * @throws Exception
   */
  public void RefreshTree(boolean zOneRow) throws Exception {
    oWinsAsociado = this.ObtenerWins(zOneRow);
  }


  public void ProcesarEnter(ActionEvent e) {
  }

  @Override
	public void Remove() throws Exception {
  }


  private boolean ifSetearValorAnterior(JWin zValorAnt) throws Exception {
   if ( zValorAnt == null ) return false;
   if ( zValorAnt.getRecord() == null ) return false;
   if ( zValorAnt.getRecord() instanceof BizVirtual ) return true;
   if ( !zValorAnt.getClass().equals(oWinsAsociado.GetClassWin()) ) return false;
   return true;
  }


  @Override
	public int getAditionalComponentWidth() throws Exception {
    return 30;
  }
}