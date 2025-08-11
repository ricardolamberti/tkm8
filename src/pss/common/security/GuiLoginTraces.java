package pss.common.security;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.icons.GuiIcon;

public class GuiLoginTraces extends JWins {


  public GuiLoginTraces() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiLoginTrace.class; }
  @Override
	public int GetNroIcono() throws Exception { return GuiIcon.LOGIN_ICON; }
  @Override
	public String GetTitle() throws Exception { return "Ingresos"; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

}


