package pss.core.winUI.responsiveControls;

import pss.core.winUI.lists.JPlantilla;

//========================================================================== //
// Clase para el manejo standard de EditBoxes
//========================================================================== //
public class JFormTextAreaResponsive extends JFormControlResponsive {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  //private JTextArea oArea;
//  private String oArea;
	boolean isWeb = false;
  JPlantilla plantilla;
  String mapaSource;
	long margenIzq;
	long margenImgSup;
	long margenImgLeft;
	long margenImgSize;
	long anchoPagina;
	String fondo;
	boolean formulario=true; //true es autodetectar

	public boolean isFormulario() {
		return formulario;
	}
	public boolean isAcceptURL() {
		return true;
	}

	public void setFormulario(boolean formulario) {
		this.formulario = formulario;
	}
	public long getMargenImgSup() {
		return margenImgSup;
	}

	public void setMargenImgSup(long margenDer) {
		this.margenImgSup = margenDer;
	}

	public long getMargenImgLeft() {
		return margenImgLeft;
	}

	public void setMargenImgLeft(long margenSup) {
		this.margenImgLeft = margenSup;
	}

	public long getMargenImgSize() {
		return margenImgSize;
	}

	public void setMargenImgSize(long margenInf) {
		this.margenImgSize = margenInf;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public long getMargenIzq() {
		return margenIzq;
	}

	public void setMargenIzq(long margenIzq) {
		this.margenIzq = margenIzq;
	}

	public long getAnchoPagina() {
		return anchoPagina;
	}

	public void setAnchoPagina(long anchoPagina) {
		this.anchoPagina = anchoPagina;
	}
	public String getMapaSource() {
		return mapaSource;
	}

	public void setMapaSource(String mapaSource) {
		this.mapaSource = mapaSource;
	}


	public boolean isWeb() {
		return isWeb;
	}

	public void setWeb(boolean isWeb) {
		this.isWeb = isWeb;
	}
	public JPlantilla getPlantilla() {
		return plantilla;
	}
	public void setPlantilla(JPlantilla p) {
		this.plantilla = p;
	}
 //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormTextAreaResponsive() {
  }

  //-------------------------------------------------------------------------//
  // Blanqueo el campo
  //-------------------------------------------------------------------------//
  @Override
	public void clear() throws Exception {
    getProp().setValue("");
  }

 
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() throws Exception {
    return getProp().toString();
  }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() throws Exception {
    if ( getProp().isNull()       ) return false;
    if ( getProp().toString().trim().equals( "" )  ) return false;
    return true;
  }

  @Override
	public void setValue( String zVal ) throws Exception {
    getProp().setValue( zVal );
  }

}


