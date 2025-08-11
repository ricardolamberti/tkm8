package pss.core.tools;

import pss.common.components.BizCompAlta;
import pss.common.components.JSetupParameters;
import pss.core.JAplicacion;

public class JXmlGenerator {

  public static final int[] levelArray = { JSetupParameters.NIVEL_SIN_DATOS,
                                           JSetupParameters.NIVEL_DATOS_GLOBALES,
                                           JSetupParameters.NIVEL_DATOS_PAIS,
                                           JSetupParameters.NIVEL_DATOS_BUSINESS,
                                           JSetupParameters.NIVEL_DATOS_NODO,
                                           JSetupParameters.NIVEL_DATOS_TRANSACCION };

  public JXmlGenerator() {  }

  public static void main(String[] args) {
    try {

      PssLogger.logDebug("**********************************");
      PssLogger.logDebug("*  Iniciando Generacion de XMLs  *");
      PssLogger.logDebug("**********************************");

      int level = JSetupParameters.NIVEL_SIN_DATOS;
      String database = null;
      if( args.length >= 1 && JTools.isIntegerNumber(args[0]) ) {
        level = Integer.parseInt(args[0]);
        level = (level == levelArray[level]) ? level : JSetupParameters.NIVEL_SIN_DATOS;
      }
      if( args.length >= 2 ) {
        database = args[1];
      }

      JAplicacion.AbrirSesion();
      if (database!=null) JAplicacion.GetApp().setForzedDatabase(database);
      JAplicacion.GetApp().AbrirApp("XmlGenerator", JAplicacion.AppTipoThread(), true );

      BizCompAlta oComp = new BizCompAlta();
      oComp.setComponente("Pss");
      oComp.setIncludeTables(true);
      oComp.setRecursivo(true);
      oComp.setDataLevel(level);
      oComp.processInsert();
      PssLogger.logDebug("************************************");
      PssLogger.logDebug("*  Finalizó la Generacion de XMLs  *");
      PssLogger.logDebug("************************************");
      System.exit(0);
    }
    catch(Throwable t) {
//      JDebugPrint.logDebug("*******************************************************");
      PssLogger.logError(t);
//      JDebugPrint.logDebug("*******************************************************");
      System.exit(1);
    }
  }


}
