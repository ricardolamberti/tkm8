package pss.bsp.contrato.detalle.prediccion;

import java.util.Date;

import pss.bsp.contrato.detalle.BizDetalle;

public interface IPrediccion {
  public  void processPopulate(BizDetalle detalle,Date fechaEsti,long variable) throws Exception;

}
