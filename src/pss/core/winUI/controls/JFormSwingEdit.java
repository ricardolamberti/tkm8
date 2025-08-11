package pss.core.winUI.controls;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;

import pss.core.services.fields.JObject;
import pss.core.ui.components.JPssEdit;
import pss.core.winUI.forms.JBaseForm;

//========================================================================== //
// Clase para el manejo standard de EditBoxes
//========================================================================== //
public class JFormSwingEdit extends JFormControl  {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  private JTextField oEdit;
  private boolean outstanding = false; //destacado

	//-------------------------------------------------------------------------//
  // Metodos de acceso a las Propiedades de la Clase
  //-------------------------------------------------------------------------//
  public JTextField GetFormEdit()             { return oEdit ; }
  public void SetFormEdit( JTextField zEdit ) { oEdit = zEdit; }

  //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormSwingEdit() {
  }
  
	public String getTipoDatoConvert() { // ver de unificar para no hacer estas negradas
		if (GetTipoDato().equals("CHAR")) return JObject.JSTRING;
		return this.GetTipoDato();
	}


//  @Override
//	public Container CrearControl() {
//    oEdit = new JPssEdit(getTipoDatoConvert());
//    oEdit.setText( "" );
//    this.updateComponentSize();
//    this.updateComponentWidth();
//    return oEdit;
//  }


  @Override
	public Component getComponent() {
    return this.oEdit;
  }
  @Override
	protected int getDefaultWidth() {
    return 100;
  }


  //-------------------------------------------------------------------------//
  // Blanqueo el campo
  //-------------------------------------------------------------------------//
  @Override
	public void clear() {
    oEdit.setText( "" );
  }

  //-------------------------------------------------------------------------//
  // Protejo el campo
  //-------------------------------------------------------------------------//
  @Override
	public void disable() {
    oEdit.setEditable ( false );
    oEdit.setBackground( Color.lightGray );
  }


  @Override
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
    if ( oEdit instanceof JPssEdit )
      ((JPssEdit) oEdit).resetDefaultRestrictions();
    if (this.getProp() == null) return;
    if (zModo.equals(JBaseForm.MODO_CONSULTA)/* || this.ifReadOnly() */)
    	setValue( this.getProp().toFormattedString() );
    else 
      setValue( this.getProp().toInputString() );
  }

//  @Override
//	public void SetFocus() {
//    oEdit.setRequestFocusEnabled( true );
//    oEdit.requestFocus();
////    oEdit.selectAll();
//  }
  //-------------------------------------------------------------------------//
  // Edito el campo
  //-------------------------------------------------------------------------//
  @Override
	public void edit( String zModo ) throws Exception {
    oEdit.setEnabled  ( true );
    oEdit.setEditable ( true );
    oEdit.setBackground( Color.white );
//    if ( oEdit instanceof JPssEdit ) {
//        ((JPssEdit) oEdit).SetType(this.getProp().getObjectType());
//        ((JPssEdit) oEdit).SetAtributo(this.getFixedProp().GetAtributo());
//        ((JPssEdit) oEdit).SetSize(this.getFixedProp().getSize());
//        if ( this.getProp().hasCustomPrecision() )
//          ((JPssEdit) oEdit).SetPrecision(this.getProp().getCustomPrecision());
//        else
//          ((JPssEdit) oEdit).SetPrecision(this.getFixedProp().GetPrecision());
//    }
//    if ( oEdit instanceof JPssPasswordEdit ) {
//      ((JPssPasswordEdit) oEdit).SetSize(this.getFixedProp().getSize());
//      ((JPssPasswordEdit) oEdit).SetType(this.getProp().getObjectType());
//      ((JPssPasswordEdit) oEdit).SetAtributo(this.getFixedProp().GetAtributo());
//      ((JPssPasswordEdit) oEdit).SetPrecision(this.getFixedProp().GetPrecision());
//    }
    oEdit.selectAll();
    
 }

  @Override
	public void hide() throws Exception {
    oEdit.setVisible(false);
  }
  @Override
	public void show() throws Exception {
    oEdit.setVisible(true);
  }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() {
    return oEdit.getText();
  }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() {
    if ( oEdit.getText() == null       ) return false;
    if ( oEdit.getText().trim().equals( "" )  ) return false;
    return true;
  }

  @Override
	public void setValue( String zVal ) throws Exception {
    oEdit.setText( zVal );
  }

//  @Override
//	public boolean isVisible() {
//    return this.oEdit.isVisible();
//  }
  @Override
	protected boolean isComponentEditable() {
    return this.oEdit.isEditable();
  }

  public boolean isOutstanding() {
		return outstanding;
	}
	public void setOutstanding(boolean outstanding) {
		this.outstanding = outstanding;
	}
	
//	public JFormControlResponsive getResponsiveVersion() throws Exception {
//		if (getComponent() instanceof JPssPasswordEdit) {
//			JFormPasswordResponsive newCtrl = new JFormPasswordResponsive();
//			newCtrl.getDataComponente(this);
//			return newCtrl;
//		}
//		if (getProp() instanceof JHour) {
//			JFormCDatetimeResponsive newCtrl2 = new JFormCDatetimeResponsive();
//			newCtrl2.getDataComponente(this);
//			newCtrl2.setWithTime(true);
//			newCtrl2.setWithDate(false);
//			return newCtrl2;
//		}
//		JFormEditResponsive newCtrl = new JFormEditResponsive();
//		newCtrl.getDataComponente(this);
//		return newCtrl;
//	}
}
