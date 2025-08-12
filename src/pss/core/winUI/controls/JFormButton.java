	package pss.core.winUI.controls;

import java.awt.Component;

import pss.core.ui.components.JPssButton;
import pss.core.win.actions.BizAction;
import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;

public class JFormButton  extends JFormControl  {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  private JPssButton button;
  BizAction oAction;
  boolean submit;
  String descripcion;
  //-------------------------------------------------------------------------//
  // Metodos de acceso a las Propiedades de la Clase
  //-------------------------------------------------------------------------//

  public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

	public BizAction getAction() {
		return oAction;
	}

	public void setAction(BizAction oAction) {
		this.oAction = oAction;
	}

	public JPssButton getButton() {
		return button;
	}

	public void setButton(JPssButton button) {
		this.button = button;
	}

	//-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormButton() {
  }
  
  @Override
	public Component getComponent() {
    return this.button;
  }
  @Override
	protected int getDefaultWidth() {
    return 300;
  }


  //-------------------------------------------------------------------------//
  // Blanqueo el campo
  //-------------------------------------------------------------------------//
  @Override
	public void clear() {
  }

  //-------------------------------------------------------------------------//
  // Protejo el campo
  //-------------------------------------------------------------------------//
  @Override
	public void disable() {
		button.setVisible(false);
  }



  @Override
	public void hide() throws Exception {
  	button.setVisible(false);
  }
  @Override
	public void show() throws Exception {
  	button.setVisible(true);
  }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() {
    return button.getText();
  }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() {
     return false;
  }

  @Override
	public void setValue( String zVal ) throws Exception {
  }

//  @Override
//	public Container CrearControl() {
//  	button = new JPssButton();
//  	button.setText(getDescripcion());
//    this.updateComponentSize();
//    return button;
//  }

//  @Override
//	public boolean isVisible() {
//    return this.label.isVisible();
//  }
  @Override
	protected boolean isComponentEditable() {
    return false;
  }

	public JFormControlResponsive getResponsiveVersion() throws Exception {
		JFormButtonResponsive newCtrl = new JFormButtonResponsive();
		newCtrl.getDataComponente(this);
		newCtrl.setDescripcion(this.getButton().getLabel());
		newCtrl.setLabel(this.getButton().getLabel());
		newCtrl.setAction(this.getAction());
		newCtrl.setSubmit(this.isSubmit());
		return newCtrl;
	}
}
