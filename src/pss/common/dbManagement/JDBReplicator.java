package  pss.common.dbManagement;

import java.util.ArrayList;

import pss.JPath;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;


/**
 * Title: Replicador de Base de Datos.
 * Description: Configura un replicador en SQL Server.
 *  - Solo se crea una publicación sobre la base de datos default configurada en el Pss.ini.
 *  - El nombre de la publicación es 'LaPublicacion'.
 *  - El único tipo de artículo que se puede publicar es una tabla.
 *  - Todos los campos y todas las filas de la tabla son publicados.
 *  - Para borrar una tabla(artículo) publicada: primero borrar las suscripciones, luego
 *    borrar los artículos, y finalmente agregar las suscripciones borradas.
 *
 * Ejemplo:
 *    Para configurar por completo una replicación, a una instancia de JDBReplicator:
 *
 *    installReplication()
 *    for (x pc_a_replicar) {
 *      addSubscriber(x)
 *      addSuscription(x)
 *    }
 *    for (t tabla_a_publicar) {
 *      addArticle(t)
 *    }
 *
 *
 * Copyright:    Copyright (c) 2002
 * Company: PSS
 * @author CJG
 * @version 1.0
 */

public class JDBReplicator {

  //////////////////    M A I N   ///////////////////////////////
  public static void main(String a[]) throws Exception {

/*
    // Ejemplo de como invocar
    String[] oPCs    = new String[1];
    String[] oTablas = new String[3];
    oPCs[0]    = "DESARROLLO";
    oTablas[0] = "PER_TELEFONO";
    oTablas[1] = "PER_DOMICILIO";
    oTablas[2] = "RTL_CLIENTE";
    JDBReplicator oReplic = new JDBReplicator(oPCs , oTablas);
    oReplic.installReplication();
*/

    JDBReplicator oReplic = new JDBReplicator();
//    oReplic.installReplication();
    oReplic.addSubscriber("DESARROLLO");
    oReplic.addSubscription("DESARROLLO");
    oReplic.addArticle("PER_TELEFONO");
    //  boolean aaa = oReplic.isReplicationInstalled();
  }

  //--------------------------------------------------------------------- //
  //                          Constructores                               //
  //--------------------------------------------------------------------- //

  /**************************************************************************
   * Devuelva la unica instancia del Singleton
   *************************************************************************/
  public static JDBReplicator getInstance() throws Exception {
    if (oInstance == null) {
      oInstance = new JDBReplicator();
    }
    return oInstance;
  }

  /**************************************************************************
   * Crea un objeto Replicador, encargado de instalar y configurar la
   * replicación en la base de datos default configurada en el Pss.ini.
   * @param zSuscriptores Maquinas donde se replicará la base default.
   *                      La base de datos destino de cada una de estas máquinas
   *                      tiene el mismo nombre que la base a replicar
   * @param zTablas Tablas de la base default que se replicarán
   **************************************************************************/
  private JDBReplicator() throws Exception {
/*
  public JDBReplicator(String[] zSuscriptores, String[] zTablas) throws Exception {
    if ( zSuscriptores.length == 0 ) JExcepcion.SendError("Debe especificar al menos un suscriptor");
    if ( zTablas.length == 0 )       JExcepcion.SendError("Debe especificar al menos una tabla a publicar");
    pSuscriptores   = zSuscriptores;
    pTablas         = zTablas;
*/
    pMaquinaLocal   = JTools.GetHostName();

    // si nunca se inicio la aplicación, se inicializa la variable de las bases
//    if (JBDatos.GetBases() == null)
//      JBDatos.CrearGlobal();

    // Se fija si esta cargada la base default, sino la lee del ini
    JBDato oDatoApp = JBDatos.GetBases().getPrivateCurrentDatabase();
    if (oDatoApp == null) {
      oDatoApp = JBDatos.createBaseFromConfig(null, JBaseJDBC.COMMON_USER);
      JBDatos.GetBases().addDatabase(oDatoApp);
      oDatoApp.open();
    }

    // Se fija si esta cargada la base Master, sino la lee del ini
    JBDato oDatoMast = JBDatos.GetBases().getDatabaseByName(JBDatos.GetBases().getCurrentDatabase()+"_CONFIG");
    if (oDatoMast == null) {
      //      oDatoMast = JBDatos.ReadBaseFromConfig("CONFIG", JBaseJDBC.COMMON_USER);
      oDatoMast = JBDatos.getBaseMaster();
      JBDatos.GetBases().addItem(oDatoMast);
      oDatoMast.open();
    }

    pBaseaReplicar  = oDatoApp.getDatabaseName(); // setea nombre de la base default

    pRegApp  = JBaseRegistro.VirtualCreate(oDatoApp);
    pRegMast = JBaseRegistro.VirtualCreate(oDatoMast);

/*
    JBDatos.GetBases().AddItem(oDatoApp);
    JBDatos.GetBases().AddItem(oDatoMast);
    oDatoMast.Abrir();
    oDatoApp.Abrir();
*/
  }

  //--------------------------------------------------------------------- //
  //                          Propiedades                                 //
  //--------------------------------------------------------------------- //

  String  pMaquinaLocal  = "";
  String  pBaseaReplicar = "" ;

  JBaseRegistro pRegMast = null;
  JBaseRegistro pRegApp  = null;
  String[] pSuscriptores = null;
  String[] pTablas       = null;
  StringBuffer  pStrBuffer           = new StringBuffer();
  ArrayList<String>     pArticulosPublicados = null;

  private static JDBReplicator oInstance = null;  // Contiene la instancia del JDBReplicator

  public  static final String REPLICATION_DATABASE = "DB_Replicacion";

  //-------------------------------------------------------------------------//
  //                          Metodos de Instancia                           //
  //-------------------------------------------------------------------------//


  protected void onBegin() throws Exception {
    // porque los comandos siguientes no puede estar dentro de una transaccion
    pRegMast.getDatabase().commit();
  }

  protected void onFinish() throws Exception {
  }

  /**************************************************************************
   * Instala en el servidor local una replicación de la base de datos default
   * configurada en el Pss.ini
   **************************************************************************/
  public void installReplication() throws Exception {
    this.addDistributor();
    this.addPublisher();
    this.createPublication();
//    pRegApp.GetBase().Cerrar();
//    pRegMast.GetBase().Cerrar();
  //    pRegApp.Execute("exec sp_reinitmergesubscription @publication='LaPublicacion'");
  }

  protected void addSubscribers() throws Exception {
    for (int i=0; i < pSuscriptores.length; i++) {
        this.addSubscriber( pSuscriptores[i] );
    }
  }

  /**************************************************************************
   * Crea el distribuidor (si no estaba creado anteriormente)
   **************************************************************************/
  protected void addDistributor() throws Exception {
    pRegMast.getDatabase().commit();

    this.initString();
    pStrBuffer.append("exec sp_adddistributor ");
    pStrBuffer.append(pMaquinaLocal);
    try {
      pRegMast.Execute( pStrBuffer.toString() );
    } catch (Exception e) {PssLogger.logError(e.getMessage());}

    this.initString();
    pStrBuffer.append("exec sp_adddistributiondb @database='");
    pStrBuffer.append(REPLICATION_DATABASE);
    pStrBuffer.append("', @data_folder='");
    pStrBuffer.append(JPath.PssPathData().replace('/','\\'));
    pStrBuffer.append("', @data_file='");
    pStrBuffer.append(REPLICATION_DATABASE);
    pStrBuffer.append(".mdf', @log_folder='");
    pStrBuffer.append(JPath.PssPathData().replace('/','\\'));
    pStrBuffer.append("',@log_file='");
    pStrBuffer.append(REPLICATION_DATABASE);
    pStrBuffer.append(".ldf'");
    try {
      pRegMast.ExecuteQuery( pStrBuffer.toString() );
    } catch (Exception e) {
        PssLogger.logError(e.getMessage());
      }
  }

  /**************************************************************************
   * Agrega el publicador a la base de datos.
   **************************************************************************/
  protected void addPublisher() throws Exception {
    pRegMast.getDatabase().commit();

    this.initString();
    pStrBuffer.append("exec sp_adddistpublisher @publisher='");
    pStrBuffer.append(pMaquinaLocal);
    pStrBuffer.append("', @distribution_db='");
    pStrBuffer.append(REPLICATION_DATABASE);
    pStrBuffer.append("', @security_mode=1, @working_directory='\\\\");
    pStrBuffer.append(pMaquinaLocal);
    pStrBuffer.append("\\ReplData'");
    try {
      pRegMast.Execute( pStrBuffer.toString() );
    } catch (Exception e) { PssLogger.logError(e.getMessage()); }

    this.initString();
    pStrBuffer.append("exec sp_replicationdboption @dbname='");
    pStrBuffer.append(pBaseaReplicar);
    pStrBuffer.append("', @optname='merge publish', @value='true'");
    try {
      pRegMast.Execute( pStrBuffer.toString() );
    } catch (Exception e) {
        PssLogger.logError(e.getMessage());
      }
  }

  /**************************************************************************
   * Crea una publicación en la máquina local
   **************************************************************************/
  protected void createPublication() throws Exception {
    try {
      pRegApp.getDatabase().beginTransaction();

      this.initString();
      pStrBuffer.append("exec sp_addmergepublication @publication='LaPublicacion', @description='Publicacion Merge de ");
      pStrBuffer.append(pMaquinaLocal);
      pStrBuffer.append("', @sync_mode='native', @allow_push='true', @allow_pull='true', ");
      pStrBuffer.append("@allow_anonymous='false', @enabled_for_internet='false', @centralized_conflicts='true'");
      pRegApp.Execute( pStrBuffer.toString() );

      this.initString();
      pStrBuffer.append("exec sp_addpublication_snapshot @publication='LaPublicacion', @frequency_type=0x40");
      pRegApp.Execute( pStrBuffer.toString() );
      /*
      +"@frequency_interval=2, @frequency_relative_interval=0, @frequency_recurrence_factor=1, "+
        "@frequency_subday=1, @frequency_subday_interval=1, @active_start_date=0, @active_end_date=99991231, "+
        "@active_start_time_of_day=120000, @active_end_time_of_day=0");
      */
      pRegApp.getDatabase().commit();
    } catch (Exception e) {
        PssLogger.logError(e.getMessage());
        pRegApp.getDatabase().rollback();
//        throw e;
      }
  }

  /**************************************************************************
   * Agrega los articulos ingresados como parámetros en la publicación
   **************************************************************************/
  protected void addArticles() throws Exception {
    for (int i=0; i < pTablas.length; i++) {
        this.addArticle(pTablas[i]);
    }
  }

  /**************************************************************************
   * Agrega un suscriptor a la publicacion de la base local
   **************************************************************************/
  protected void addSuscriptions() throws Exception {
    for (int i=0; i < pSuscriptores.length; i++) {
        this.addSubscription( pSuscriptores[i] );
    }
  }

  /**************************************************************************
   * Habilita a una máquina como suscriptor de la Base de Datos
   **************************************************************************/
  public void addSubscriber(String zSuscriptor) throws Exception {
    pRegMast.getDatabase().commit();
    this.initString();
    pStrBuffer.append("exec sp_addsubscriber @subscriber='");
    pStrBuffer.append(zSuscriptor);
    pStrBuffer.append("', @type=0, @security_mode=1, @frequency_type=64");
    //    pStrBuffer.append("', @type=0, @login='replicacion', @password='1234', @security_mode=0, @frequency_type=64");
    try { pRegMast.Execute( pStrBuffer.toString() );
    } catch (Exception e) {
        PssLogger.logError(e.getMessage());
      }
      finally {
        pRegMast.close();
      }

  }

  /**************************************************************************
   * Agrega una suscripción a la publicacion de la base local.
   * En algún momento previo, tiene que haber sido invocado 'addSuscriptor'
   * para registrar la máquina destino zSuscriptor.
   **************************************************************************/
  public void addSubscription(String zSuscriptor) throws Exception {
    try {
      pRegApp.getDatabase().beginTransaction();

      String old = pBaseaReplicar ;          // SACAR despues...
      pBaseaReplicar = "ReplicacionTest";    // SACAR despues...
      this.initString();
      pStrBuffer.append("exec sp_addmergesubscription @publication='LaPublicacion', @subscriber='");
      pStrBuffer.append(zSuscriptor);
      pStrBuffer.append("', @subscriber_db='");
      pStrBuffer.append(pBaseaReplicar);
      pStrBuffer.append("', @subscription_type='push', @subscriber_type='local', @subscription_priority=0.000000, @sync_type='automatic', ");
      pStrBuffer.append("@frequency_type=4, @frequency_interval=1, @frequency_relative_interval=2, ");
      pStrBuffer.append("@frequency_recurrence_factor=0, @frequency_subday=4, @frequency_subday_interval=1, @active_start_date=0, ");
      pStrBuffer.append("@active_end_date=99991231, @active_start_time_of_day=0, @active_end_time_of_day=235959");
      pRegApp.Execute( pStrBuffer.toString() );
      // CADA 3 MINUTOS.... @frequency_subday_interval=3
      pBaseaReplicar = old;               // SACAR despues...

      pRegApp.getDatabase().commit();
    } catch (Exception e) {
        PssLogger.logError(e.getMessage());
        pRegApp.getDatabase().rollback();
//        throw e;
      }
      finally {
        pRegApp.close();
      }
  }

  /**************************************************************************
   * Agrega una suscripción a la publicacion de la base local.
   * En algún momento previo, tiene que haber sido invocado 'addSuscriptor'
   * para registrar la máquina destino zSuscriptor.
   **************************************************************************/
  public void deleteSubscription(String zSuscriptor) throws Exception {
    try {
      pRegApp.getDatabase().beginTransaction();

      String old = pBaseaReplicar ;          // SACAR despues...
      pBaseaReplicar = "ReplicacionTest";    // SACAR despues...
      this.initString();
      pStrBuffer.append("exec sp_dropmergesubscription @publication='LaPublicacion', @subscriber='");
      pStrBuffer.append(zSuscriptor);
      pStrBuffer.append("', @subscriber_db='");
      pStrBuffer.append(pBaseaReplicar);
      pStrBuffer.append("', @subscription_type='push'");
      pRegApp.Execute( pStrBuffer.toString() );
      // CADA 3 MINUTOS.... @frequency_subday_interval=3
      pBaseaReplicar = old;               // SACAR despues...

      pRegApp.getDatabase().commit();
    } catch (Exception e) {
        PssLogger.logError(e.getMessage());
        pRegApp.getDatabase().rollback();
//        throw e;
      }
      finally {
        pRegApp.close();
      }
  }

  /**************************************************************************
   * Agrega un articulo a la publicación
   **************************************************************************/
  public void addArticle(String zTabla) throws Exception {
    try {
    // "select article from MSarticles where article = 'PER_TELEFONO'"
      pRegApp.getDatabase().beginTransaction();

      this.initString();
      pStrBuffer.append("exec sp_addmergearticle @publication='LaPublicacion', @article='");
      pStrBuffer.append(zTabla);
      pStrBuffer.append("', @source_object='");
      pStrBuffer.append(zTabla);
      pStrBuffer.append("', @type='table', @force_invalidate_snapshot=1");
      pRegApp.Execute( pStrBuffer.toString() );

      pRegApp.getDatabase().commit();
    } catch (Exception e) {
    	PssLogger.logError(e.getMessage());
        pRegApp.getDatabase().rollback();
//        throw e;
      }
      finally {
        pRegApp.close();
      }
  }

  /**************************************************************************
   * Borra un articulo de la publicación.
   * Solo puede ejecutar cuando no hay una subscripcion activa.
   **************************************************************************/
  public void deleteArticle(String zTabla) throws Exception {
    try {
      // "select article from MSarticles where article = 'PER_TELEFONO'"
      pRegApp.getDatabase().beginTransaction();
      this.initString();
      pStrBuffer.append("exec sp_dropmergearticle @publication='LaPublicacion', @article='");
      pStrBuffer.append(zTabla);
      pStrBuffer.append("', @force_invalidate_snapshot=1");
      pRegApp.Execute( pStrBuffer.toString() );

      pRegApp.getDatabase().commit();
    } catch (Exception e) {
        PssLogger.logError(e.getMessage());
        pRegApp.getDatabase().rollback();
        throw e;
      }
      finally {
        pRegApp.close();
      }
  }

  /**************************************************************************
   * Informa si el articulo esta publicado para la replicación.
   * @param zArticle Articulo que se desea verificar
   * @return true  Si zArticulo está publicado; false en caso contrario
   **************************************************************************/
   public boolean isArticlePublicated(String zArticle) throws Exception {
    if ( ! isReplicationInstalled() ) return false;
    return this.obtainPublicatedArticles().contains(zArticle);
  }

  /**************************************************************************
   * Informa si la replicación está instalada en la base de datos
   **************************************************************************/
  public boolean isReplicationInstalled() throws Exception {
    pRegMast.ExecuteQuery("select name from sysdatabases where name='"+REPLICATION_DATABASE+"'");
    return pRegMast.next();
  }

  /**************************************************************************
   * Obtiene los articulos publicados de la Base de Datos la primera vez
   * @return Lista de los artículos publicados
   **************************************************************************/
  public ArrayList<String> obtainPublicatedArticles() throws Exception {
    if (pArticulosPublicados == null) {
        // la primera vez que se invoca, carga la lista de artículos publicados.
        pArticulosPublicados = new ArrayList<String>(20);
        this.initString();
        pStrBuffer.append("select article from ");
        pStrBuffer.append(REPLICATION_DATABASE);
        pStrBuffer.append("..MSarticles where publisher_db='");
        pStrBuffer.append(pBaseaReplicar);
        pStrBuffer.append("'");
        pRegApp.ExecuteQuery( pStrBuffer.toString() );
        while (pRegApp.next()) {
           pArticulosPublicados.add( pRegApp.CampoAsStr("article") );
        }
    }
    return pArticulosPublicados;
  }

  /**************************************************************************
   * Inicializa el string que utiliza la instancia para armar los Strings SQL
   **************************************************************************/
  private void initString() {
    pStrBuffer.delete( 0 , pStrBuffer.length() );
  }


  /**************************************************************************
   * Borra los triggers creados por la replicación
   * @param zTable Tabla en la que se eliminaran todos los triggers
   **************************************************************************/
  public void dropTriggers(String zTable) throws Exception {
    // buscar los nombres de los triggers que se eliminaran
    JList<String> oTriggers= JCollectionFactory.createList();
    pRegApp.ExecuteQuery( pStrBuffer.toString() );
    String sTriggers ="select s2.name from sysobjects s1, sysobjects s2 where s1.name='" +
                      zTable + "' and s1.id=s2.parent_obj and s2.xtype='TR'";
    pRegApp.ExecuteQuery( sTriggers );
    // Guarda en un array los nombres de los triggers que se eliminaran
    while (pRegApp.next()) {
       oTriggers.addElement( pRegApp.CampoAsStr("name") );
    }

    // Ejecuta el drop trigger
    JIterator<String> oTriggersIt = oTriggers.getIterator();
    while (oTriggersIt.hasMoreElements()) {
      pRegApp.Execute( "drop trigger " + oTriggersIt.nextElement() );
    }
  }


}
