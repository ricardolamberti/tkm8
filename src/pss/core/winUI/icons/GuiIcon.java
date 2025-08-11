package pss.core.winUI.icons;

import javax.swing.ImageIcon;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNewSubmit;
import pss.core.winUI.forms.JBaseForm;


public class GuiIcon extends JWin {

  public final static int DEFAULT_ICON      = 0;

  public final static int ARBOL_ICON        = 2005;
  public final static int APLICAR_ICON      = 2;
  public final static int BOLAVERDE_ICON    = 3;
  public final static int PROCESANDO        = 4;
  public final static int BOLAROJA_ICON     = 1000;
  public final static int CASA_ICON         = 6;
  public final static int CANCELAR_ICON     = 7;
  public final static int CARITA_ICON       = 8;
  public final static int CONSULTAR_ICON    = 117;
  public final static int CORE_ICON         = 10;
  public final static int MAS_ICON          = 11;
  public final static int MULTI_MAS_ICON    = 6076;
  public final static int MENOS_ICON        = 12;
  public final static int MODIFICAR_ICON    = 13;
  public final static int MUNDO_ICON        = 14;
  public final static int OPCIONES_ICON     = 15;
  public final static int PESOS_ICON        = 16;
  public final static int REFRESH_ICON      = 17;
  public final static int SALIR_ICON        = 18;
  public final static int VISA_ICON         = 19;
  public final static int STOP_ICON         = 110;
  public final static int DATABASE_ICON     = 406;
  public final static int LOGIN_ICON        = 45;
  public final static int SECURITY_ICON     = 26;
  public final static int RETURN_ICON		= 5024;
  public final static int SHOW_FILTERS		= 15001;
  public final static int HIDE_FILTERS		= 15002;
  public final static int SHOWHIDE_FILTERS		= 15003;
  
  public final static int REMOTE_REFRESH_ICON     = 10315;
  public final static int FORCE_REFRESH_ICON     = 10316;


  public final static int BACK_ICON     = 707;
  public final static int LOGOUT_ICON     = 735;
  public final static int PRINTER_ICON     = 526;
  public final static int REPORT_ICON     = 5;
  public final static int SEPARATOR_ICON     = 704;

  // iconos para el instalador
  public static final int SETUP_STEP_STATUS_PENDING_ICON              = 951; // status_pending.gif
  public static final int SETUP_STEP_STATUS_WAITING_INPUT_ICON        = 952; // status_waitinput.gif
  public static final int SETUP_STEP_STATUS_WAITING_CONFIRMATION_ICON = 953; // status_waitconf.gif
  public static final int SETUP_STEP_STATUS_CANCELLED_ICON            = 954; // status_cancelled.gif
  public static final int SETUP_STEP_STATUS_RUNNING_ICON              = 955; // status_running.gif
  public static final int SETUP_STEP_STATUS_DONE_ICON                 = 956; // status_done.gif
  public static final int SETUP_STEP_STATUS_FAILED_ICON               = 957; // status_failed.gif
  public static final int SETUP_GO_NEXT_STEP_ICON                     = 958; // setup_go_next.gif
  public static final int SETUP_GO_BACK_STEP_ICON                     = 959; // setup_go_back.gif
  public static final int SETUP_FINISH_ICON                           = 960; // setup_finish.gif
  public static final int SETUP_STEP_STATUS_SKIPPED_ICON              = 961; // status_skipped.gif

  public static final int COPY_ICON                                   = 962; // copy.gif
  public static final int COPY_ALL_ICON                               = 963; // copy_all.gif
  public static final int REMOVE_ICON                                 = 964; // remove.gif
  public static final int REMOVE_ALL_ICON                             = 965; // remove_all.gif
  public static final int INFO_MESSAGE_ICON                           = 966; // info_message.gif
  public static final int WAIT_MESSAGE_ICON                           = 1086; // wait_message.gif
  public static final int ERROR_MESSAGE_ICON                          = 967; // error_message.gif
  public static final int DEBUG_MESSAGE_ICON                          = 968; // debug_message.gif
  public static final int SQL_DEBUG_MESSAGE_ICON                      = 969; // debug_sql_message.gif
  public static final int XML_DEBUG_MESSAGE_ICON                      = 1087; // debug_xml_message.gif
  public static final int TEXT_PANE_AUTOSCROLL_ICON                   = 970; // text_pane_autoscroll.gif

  public static final int ADD_COLUMN_ICON                             = 971; // add_column.gif
  public static final int DROP_COLUMN_ICON                            = 972; // drop_column.gif
  public static final int ALTER_TABLE_ICON                            = 973; // alter_table.gif
  public static final int ADD_INDEX_ICON                              = 974; // add_index.gif
  public static final int DROP_INDEX_ICON                             = 975; // drop_index.gif
  public static final int CREATE_TABLE_ICON                           = 976; // create_table.gif
  public static final int DROP_TABLE_ICON                             = 977; // drop_table.gif
  public static final int TRUNCATE_TABLE_ICON                         = 978; // truncate_table.gif
  public static final int INSERT_ROWS_ICON                            = 979; // add_data.gif
  public static final int DELETE_ROWS_ICON                            = 980; // delete_data.gif

  public static final int EXPORT_ICON                                 = 981; // export.gif
  public static final int EXPORT_ALL_ICON                             = 982; // export_all.gif

  public static final int Z_UP_ICON                                   = 983; // z-up.gif



  public GuiIcon() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizIcon(); }
  @Override
	public String GetTitle()       throws Exception { return "Datos del Icono"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return null; }
  @Override
	public String getKeyField()   throws Exception  { return "nro_icono"; }
  @Override
	public String getDescripField()                 { return "descripcion"; }

  @Override
	public void createActionMap() throws Exception {
    addActionQuery(1, "Consultar" );
    addActionUpdate(2, "Modificar" );
    addActionDelete (3, "Eliminar"  );
  }
  
  @Override
	public JAct getSubmit(BizAction a) throws Exception {
  	if (a.getId()==0) return new JActNewSubmit(this, 0) {
  		@Override
			public JAct nextAction() throws Exception {addIcon();	return null;}
  	};
  	return super.getSubmit(a);
  }

  public BizIcon GetcDato() throws Exception {
  	return (BizIcon) this.getRecord();
  }

  public int GetNro() throws Exception {
  	return this.GetcDato().pNroIcono.getValue();
  }

  public String GetFile() throws Exception {
  	return this.GetcDato().pFile.getValue();
  }
  @Override
	public String GetIconFile() throws Exception {
  	return this.GetFile();
  }
  @Override
	public String GetIconoFile() throws Exception {
  	return this.GetFile();
  }

  public String GetFile32() throws Exception {
  	return this.GetcDato().pFile2.getValue();
  }


  public ImageIcon GetImage() throws Exception {
    return GuiIconGalerys.GetGlobal().getIcon(GetcDato().pFile.getValue());
  }

  public ImageIcon GetImage32x32() throws Exception {
    return GuiIconGalerys.GetGlobal().getIcon(GetcDato().pFile2.getValue());
  }


  @Override
	public int GetNroIcono() throws Exception {
	  int Nro = this.GetcDato().pNroIcono.getValue();
	  if ( Nro == -1 ) return super.GetNroIcono();
	  return Nro ;
  }

  public void addIcon() throws Exception {
    GuiIconos.GetGlobal().addRecord(this);
  }

  public static GuiIcon ConvertNroIcono(int zNroIcono) throws Exception {
    String sFile = getFixedIconFileName(zNroIcono);
    if ( sFile == null ) return null;

    GuiIcon oIcon = new GuiIcon();
    oIcon.GetcDato().pNroIcono.setValue(zNroIcono);
    oIcon.GetcDato().pDescrip.setValue(sFile);
    oIcon.GetcDato().pFile.setValue(sFile);
    return oIcon;
  }


  public static GuiIcon GetDefaultIcon() throws Exception {
    int iNroIcono = DEFAULT_ICON;
    String sFile = getFixedIconFileName(iNroIcono);

    GuiIcon oIcon = new GuiIcon();
    oIcon.GetcDato().pNroIcono.setValue(iNroIcono);
    oIcon.GetcDato().pDescrip.setValue(sFile);
    oIcon.GetcDato().pFile.setValue(sFile);
    return oIcon;
  }


  private static String getFixedIconFileName(int zIconNumber) {

    if ( zIconNumber == DEFAULT_ICON   ) return "marconi_m.gif";

    if ( zIconNumber == SALIR_ICON     ) return "Salir.gif";
    if ( zIconNumber == CANCELAR_ICON  ) return "Cancelar.gif";
    if ( zIconNumber == APLICAR_ICON   ) return "Aplicar.gif";
    if ( zIconNumber == STOP_ICON      ) return "StopSign.gif";
    if ( zIconNumber == DATABASE_ICON  ) return "Database.gif";
    if ( zIconNumber == LOGIN_ICON     ) return "seg_login.gif";
    if ( zIconNumber == REFRESH_ICON   ) return "refresh.gif";
    if ( zIconNumber == SECURITY_ICON  ) return "seguridad.gif";

    // íconos fijos, que los necesita el instalador

    if ( zIconNumber == SETUP_STEP_STATUS_PENDING_ICON              ) return "status_pending.gif";
    if ( zIconNumber == SETUP_STEP_STATUS_WAITING_INPUT_ICON        ) return "status_waitinput.gif";
    if ( zIconNumber == SETUP_STEP_STATUS_WAITING_CONFIRMATION_ICON ) return "status_waitconf.gif";
    if ( zIconNumber == SETUP_STEP_STATUS_CANCELLED_ICON            ) return "status_cancelled.gif";
    if ( zIconNumber == SETUP_STEP_STATUS_RUNNING_ICON              ) return "status_running.gif";
    if ( zIconNumber == SETUP_STEP_STATUS_DONE_ICON                 ) return "status_done.gif";
    if ( zIconNumber == SETUP_STEP_STATUS_FAILED_ICON               ) return "status_failed.gif";
    if ( zIconNumber == SETUP_GO_NEXT_STEP_ICON                     ) return "setup_go_next.gif";
    if ( zIconNumber == SETUP_GO_BACK_STEP_ICON                     ) return "setup_go_back.gif";
    if ( zIconNumber == SETUP_FINISH_ICON                           ) return "setup_finish.gif";
    if ( zIconNumber == SETUP_STEP_STATUS_SKIPPED_ICON              ) return "status_skipped.gif";

    if ( zIconNumber == COPY_ICON                                   ) return "copy.gif";
    if ( zIconNumber == COPY_ALL_ICON                               ) return "copy_all.gif";
    if ( zIconNumber == REMOVE_ICON                                 ) return "remove.gif";
    if ( zIconNumber == REMOVE_ALL_ICON                             ) return "remove_all.gif";
    if ( zIconNumber == INFO_MESSAGE_ICON                           ) return "info_message.gif";
    if ( zIconNumber == WAIT_MESSAGE_ICON                           ) return "wait_message.gif";
    if ( zIconNumber == XML_DEBUG_MESSAGE_ICON                      ) return "debug_xml_message.gif";
    if ( zIconNumber == ERROR_MESSAGE_ICON                          ) return "error_message.gif";
    if ( zIconNumber == DEBUG_MESSAGE_ICON                          ) return "debug_message.gif";
    if ( zIconNumber == SQL_DEBUG_MESSAGE_ICON                      ) return "debug_sql_message.gif";
    if ( zIconNumber == TEXT_PANE_AUTOSCROLL_ICON                   ) return "text_pane_autoscroll.gif";

    if ( zIconNumber == ADD_COLUMN_ICON                             ) return "add_column.gif";
    if ( zIconNumber == DROP_COLUMN_ICON                            ) return "drop_column.gif";
    if ( zIconNumber == ALTER_TABLE_ICON                            ) return "alter_table.gif";
    if ( zIconNumber == ADD_INDEX_ICON                              ) return "add_index.gif";
    if ( zIconNumber == DROP_INDEX_ICON                             ) return "drop_index.gif";
    if ( zIconNumber == CREATE_TABLE_ICON                           ) return "create_table.gif";
    if ( zIconNumber == DROP_TABLE_ICON                             ) return "drop_table.gif";
    if ( zIconNumber == TRUNCATE_TABLE_ICON                         ) return "truncate_table.gif";
    if ( zIconNumber == INSERT_ROWS_ICON                            ) return "add_data.gif";
    if ( zIconNumber == DELETE_ROWS_ICON                            ) return "delete_data.gif";

    if ( zIconNumber == EXPORT_ICON                                 ) return "export.gif";
    if ( zIconNumber == EXPORT_ALL_ICON                             ) return "export_all.gif";

    if ( zIconNumber == Z_UP_ICON                                   ) return "z-up.gif";

    return null;
  }


}
