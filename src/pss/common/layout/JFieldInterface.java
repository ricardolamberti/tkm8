package pss.common.layout;


public interface JFieldInterface {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//

//  public Object getField(Object zSource, String zSector, String zField, String zParam) throws Exception;
  public Object getField(JFieldReq req) throws Exception;

}
