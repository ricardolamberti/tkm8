package pss.core.winUI.responsiveControls;

import pss.www.ui.JWebIcon;

public class JFormDropDownButtonResponsive extends JFormPanelResponsive {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
	private String labelButton;


	
	
	public String getLabelButton() {
		return labelButton;
	}

	public void setLabelButton(String labelButton) {
		this.labelButton = labelButton;
	}


	JWebIcon imagen;

	public JWebIcon getImagen() {
		return imagen;
	}

	public JFormDropDownButtonResponsive setImagen(JWebIcon imagen) {
		this.imagen = imagen;
		return this;
	}
	

	JWebIcon arrow;

	public JWebIcon getArrow() {
		return arrow;
	}

	public JFormDropDownButtonResponsive setArrow(JWebIcon imagen) {
		this.arrow = imagen;
		return this;
	}
	@Override
	public void initialize() throws Exception {
		arrow= JWebIcon.getResponsiveIcon( "caret");
		super.initialize();
	}


	//-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormDropDownButtonResponsive() {
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
	public void disable() throws Exception {
		setReadOnly(true);
  }
	public boolean isEditable()  {
		return !ifReadOnly();
	}


  @Override
  public void editIfPosible(String zModo, boolean partialRefresh) throws Exception {
		setReadOnly(false);
 }
	@Override
	public void edit(String zModo) throws Exception {
		setReadOnly(false);
	}
	public void enable() throws Exception {
		SetReadOnly(false);
	}
  @Override
	public void hide() throws Exception {
  	setVisible(false);
  }
  @Override
	public void show() throws Exception {
  	setVisible(true);
  }

  
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() {
    return getLabelButton();
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

  @Override
	protected boolean isComponentEditable() {
    return false;
  }

}
