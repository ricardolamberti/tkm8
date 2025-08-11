package pss.core.winUI.responsiveControls;

//========================================================================== //
// Clase para el manejo standard de EditBoxes
//========================================================================== //
public class JFormFileResponsive extends JFormEditResponsive  {


  //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormFileResponsive() {
  	setHeight(30);
  }
  
	public boolean isAcceptURL() {
		return true;
	}

	}
