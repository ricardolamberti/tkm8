package  pss.common.logviewer;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormLog extends JBaseForm {

	public FormLog() throws Exception {
  }

  public GuiLog GetWin() { return (GuiLog) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "fileName", CHAR, REQ, "file_name" );
    AddItemEdit( "from_date", DATE, OPT, "from_date" );
    AddItemEdit( "from_time", HOUR, OPT, "from_time" );
    AddItemEdit( "to_date", DATE, OPT, "to_date" );
    AddItemEdit( "to_time", HOUR, OPT, "to_time" );

    AddItemCheck( "error_check", CHAR, OPT, "show_error", "S", "N" ).SetValorDefault(true);
    AddItemCheck( "info_check", CHAR, OPT, "show_info", "S", "N" ).SetValorDefault(true);
    AddItemCheck( "wait_check", CHAR, OPT, "show_wait", "S", "N" ).SetValorDefault(true);
    AddItemCheck( "debug_check", CHAR, OPT, "show_debug", "S", "N" ).SetValorDefault(true);
    AddItemCheck( "sql_check", CHAR, OPT, "show_sql", "S", "N" ).SetValorDefault(true);
    AddItemCheck( "xml_check", CHAR, OPT, "show_xml", "S", "N" ).SetValorDefault(true);
    AddItemCheck( "other_check", CHAR, OPT, "show_other", "S", "N" ).SetValorDefault(true);

    AddItemTabPanel().AddItemTab(10 );
  }
}


