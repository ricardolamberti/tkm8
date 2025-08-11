package pss.core.services.records;

/**
 * Evento producido durante operaciones de un {@link JRecord}. Permite
 * notificar acciones como altas, actualizaciones o eliminaciones sobre los
 * registros.
 */
public class JRecordEvent {

  String   Id;
  JBaseRecord OrigenBaseBD  = null;
  JBaseRecord Args          = null;
  JBaseRecord Rta           = null;
  boolean  bConsume = false;

  public void SetId(String zId) { Id = zId;}
  public void SetArgs(JBaseRecord zBaseBD) { Args = zBaseBD;}
  public void SetRta(JBaseRecord zBaseBD) { Rta = zBaseBD;}
  public String   GetId() { return Id;}
  public JBaseRecord GetOrigenBaseBD() { return  OrigenBaseBD;}
  public JBaseRecord GetArgs() { return  Args;}
  public JBaseRecord GetRta() { return  Rta;}
  public void Consume() { bConsume = true ; }
  public boolean ifConsume() { return bConsume; }

  public JRecordEvent(JBaseRecord zBaseBD) {
    super();
    OrigenBaseBD = zBaseBD;
  }

  public boolean ifEventoAlta()  {return Id.equals("AltaOk");}
  public boolean ifEventoModif() {return Id.equals("UpdateOk");}
  public boolean ifEventoDelte() {return Id.equals("DeleteOk");}


}


