package  pss.common.customList.config.customlist;

import pss.core.winUI.forms.JBaseForm;

public class GuiSQL extends GuiCustomList {



  /**
   * Constructor de la Clase
   */
  public GuiSQL() throws Exception {
  }


  public String GetTitle()   throws Exception { return "SQL"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception {
  		return FormSQL.class; 
  }

  
 }
