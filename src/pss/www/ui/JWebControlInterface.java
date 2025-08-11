package pss.www.ui;

import pss.core.services.fields.JObject;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.www.ui.controller.JWebFormControl;

public interface JWebControlInterface {

	public void setEditable(boolean editable) throws Exception;
  public void clear() throws Exception;
  public void setVisible(boolean zValue) throws Exception;
  public String getSpecificValue() throws Exception;
  public String getDisplayValue() throws Exception;
  public void setValue(String zVal) throws Exception;
  public void setValue(JWin zVal) throws Exception;
  public void setValue(JWins zVal) throws Exception;
  public void setValue(JObject zVal) throws Exception;
  public void setValueFromUIString(String zVal) throws Exception;
  public void setController(JWebFormControl control);
  public boolean isVisible();
  public void onShow(String modo) throws Exception;
  public JWin getWinElegido(String zClave) throws Exception;
  public JWin[] getWinElegidos(String zClave) throws Exception;
}
