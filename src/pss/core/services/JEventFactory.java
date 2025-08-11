package pss.core.services;

import pss.common.components.BizCompInstalados;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecord;

public class JEventFactory {
	
  private static InheritableThreadLocal oNotifyEvent;

  public static void createEvent( String sModulo, int iEventCode, String sMotivo, String Ref1, String Ref2, String Ref3,
                                  String textRef, Double monto, long grupo_cierre, String[] sOperaciones ) throws Exception {
    doCreateEvent( sModulo, iEventCode, BizUsuario.getCurrentUser(), sMotivo,
                   Ref1, Ref2, Ref3, textRef, monto, grupo_cierre, sOperaciones );
  }

  public static synchronized void doCreateEvent( String sModulo, int iEventCode, String sUsuario, String sMotivo, String Ref1, String Ref2, String Ref3, String textRef, Double monto, long grupo_cierre, String[] sOperaciones ) throws Exception {
    if( !BizCompInstalados.ifInstallEvents() )return;
    if (BizUsuario.getUsr()==null) return;
    if(!BizUsuario.getUsr().getObjNodo().isModuloEventosHabilitado())return;
    if ( oNotifyEvent!=null ) return;

    JRecord oEvent = BizCompInstalados.BizEvent();
    oEvent.getProp( "modulo" ).setValue( sModulo );
    oEvent.getProp( "cod_evento" ).setValue( iEventCode );
    oEvent.getProp( "usuario" ).setValue( sUsuario );
    oEvent.getProp( "motivo" ).setValue( sMotivo );
    oEvent.getProp( "referencia1" ).setValue( Ref1 );
    oEvent.getProp( "referencia2" ).setValue( Ref2 );
    oEvent.getProp( "referencia3" ).setValue( Ref3 );
    oEvent.getProp( "text_reference" ).setValue( textRef );
    if (monto!=null) oEvent.getProp( "monto" ).setValue( monto.doubleValue() );
    oEvent.getProp( "grupo_cierre" ).setValue( grupo_cierre );
    oEvent.getProp( "usuario_autorizante" ).setValue( BizUsuario.getUsr().getAuthorization( sOperaciones ) );
    boolean bNotify = JBDatos.GetBases().isNotifyEvents();
    try {
      JBDatos.GetBases().setNotifyEvents( false );
      oNotifyEvent = new InheritableThreadLocal(); //este flag es para que no entre en recursividad
      if( JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress() ) {
        oEvent.processInsert();
      } else {
        oEvent.execProcessInsert();
      }
    } finally {
      JBDatos.GetBases().setNotifyEvents( bNotify );
      oNotifyEvent = null;
    }
  }

  public static void createEvent(String sModulo,  int iEventCode, String sMotivo , String Ref1) throws Exception {
    createEvent(sModulo, iEventCode, sMotivo, Ref1, null, null, null, null, 0, null );
  }

  public static void createEvent(String sModulo, int iEventCode, String sMotivo, String Ref1, String Ref2 ) throws Exception {
    createEvent(sModulo, iEventCode, sMotivo, Ref1, Ref2, null, null, null, 0, null);
  }

  public static void createEvent(String sModulo, int iEventCode, String sMotivo, String Ref1, String Ref2, String Ref3 ) throws Exception {
    createEvent(sModulo, iEventCode, sMotivo, Ref1, Ref2, Ref3, null, null, 0, null);
  }

  public static void createEvent(String sModulo, int iEventCode, String sMotivo, String Ref1, String Ref2, String Ref3, String sTextRef ) throws Exception {
    createEvent(sModulo, iEventCode, sMotivo, Ref1, Ref2, Ref3, sTextRef, null, 0, null);
  }

  public static void createEvent(String sModulo,  int iEventCode) throws Exception {
    createEvent(sModulo, iEventCode, null, null, null, null, null, null, 0, null );
  }


}
