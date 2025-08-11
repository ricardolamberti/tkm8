package pss.core.tools;


public class JPair<F,S>  {

	
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  public F fisrtObject = null;
  public S secondObject = null;
  
  public F firstObject() {
  	return this.fisrtObject;
  }

  public S secondObject() {
  	return this.secondObject;
  }

  public void setFirstObject(F v) {
  	this.fisrtObject=v;
  }

  public void setSecondObject(S v) {
  	this.secondObject=v;
  }

  //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JPair() {
  }
  public JPair(F first, S second) {
    this.fisrtObject = first;
    this.secondObject = second;
  }

}
