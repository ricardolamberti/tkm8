package pss.core.winUI.responsiveControls;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.winUI.controls.JFormControl;
import pss.www.ui.JWebViewsConstants;

//========================================================================== //
// Clase para el manejo standard de EditBoxes
//========================================================================== //
public class JFormRadioResponsive extends JFormControlResponsive {

	public static final String NO_FILTER = "";
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  private JList<String>       aValores = JCollectionFactory.createList();
  private JList<String>       aLabels = JCollectionFactory.createList();
//  String oDato;
  boolean notNull;
	private int iOrientation=JWebViewsConstants.ORIENTATION_TOOGLE;
	
	public int getOrientation() {
		return this.iOrientation;
	}

	public JFormRadioResponsive setOrientation(int i) {
			this.iOrientation=i;
			return this;
	}

	public JFormControl SetValorDefault(String zVal) throws Exception {
		notNull = zVal!=null&&zVal.equals(NO_FILTER);
		return super.SetValorDefault(zVal);
	}

  //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormRadioResponsive() {
  }

  //-------------------------------------------------------------------------//
  // Blanqueo el campo
  //-------------------------------------------------------------------------//
  @Override
	public void clear() throws Exception {
  	if (notNull)
  		getProp().setValue(NO_FILTER);
  	else
  		getProp().setNull();
  }


  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() throws Exception {
  	if (getProp().isNull() && notNull) 
  		return NO_FILTER;
    return getProp().toString();
  }

  public JIterator<String> getValuesInOrder() {
    return this.aValores.getIterator();
  }
  public JIterator<String> getLabelsInOrder() {
    return this.aLabels.getIterator();
  }
  //-------------------------------------------------------------------------//
  // Seteo el valor de un radio
  //-------------------------------------------------------------------------//
  public void AddValor( String zValor ) {
    aLabels.addElement( JLanguage.translate(zValor ));
    aValores.addElement( zValor );
  }
  public void AddValor( String zLabel, String zValor ) {
    aLabels.addElement( JLanguage.translate(zLabel) );
    aValores.addElement( zValor );
  }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() throws Exception {
  	if (notNull) return true;
    return getProp().isNotNull();
  }

  //-------------------------------------------------------------------------//
  // De la base al control
  //-------------------------------------------------------------------------//
  @Override
	public void setValue( String zValor ) throws Exception {
    getProp().setValue(zValor);
  }
  
	private String sClassRadio="btncheck-primary btn-sm";
	public String getClassRadio() {
		return sClassRadio;
	}

	public void setClassRadio(String sClassRadio) {
		this.sClassRadio = sClassRadio;
	}


}


