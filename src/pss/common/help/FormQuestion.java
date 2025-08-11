package  pss.common.help;

import pss.common.regions.company.JCompanyBusinessModules;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormQuestion extends JBaseForm {


  public FormQuestion() throws Exception {
  }

  public GuiQuestion GetWin() { return (GuiQuestion) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo( "business", CHAR, OPT, "business", JCompanyBusinessModules.getWins() );
    AddItemEdit( "idQuestion", UINT, REQ, "idQuestion" );
    AddItemEdit( "Question", CHAR, REQ, "Question" );
    AddItemEdit( "StartPoint", CHAR, OPT, "startPoint" );
    AddItemTabPanel().AddItemTab(10 );

  } 
  
} 
