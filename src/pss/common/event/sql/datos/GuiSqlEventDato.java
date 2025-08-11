package  pss.common.event.sql.datos;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiSqlEventDato extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSqlEventDato() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizSqlEventDato(); }
  public int GetNroIcono()   throws Exception { return 15012; }
  public String GetTitle()   throws Exception { return "Evento dato"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormSqlEventDato.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "value"; }
  public BizSqlEventDato GetcDato() throws Exception { return (BizSqlEventDato) this.getRecord(); }

	@Override
	public String getFieldForeground(String zColName) throws Exception {
		if (zColName.equals("var_porc")) {
			return GetcDato().getVarPorc()<0?"ff0000":GetcDato().getVarPorc()>0?"00ff00":"0000ff";
		}
		else if (zColName.equals("var_val")) {
			return GetcDato().getVarPorc()<0?"ff0000":GetcDato().getVarPorc()>0?"00ff00":"0000ff";
		}
		else if (zColName.equals("tendencia")) {
			return GetcDato().getVarPorc()<0?"ff0000":GetcDato().getVarPorc()>0?"00ff00":"0000ff";
		}
		return super.getFieldForeground(zColName);
	}

	@Override
	public void createActionMap() throws Exception {
	}
	
 }
