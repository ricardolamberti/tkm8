package  pss.common.event.sql.serie;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;

public class GuiVirtualSerie extends JWin {
	

	  public GuiVirtualSerie() throws Exception {
	  }

	  @Override
		public JRecord ObtenerDato()       throws Exception { return new BizVirtualSerie(); }
	  @Override
		public String GetTitle()       throws Exception { return ""; }
	  @Override
		public String getKeyField()   throws Exception { return "valor"; }
	  @Override
		public String getDescripField()                  { return "descripcion"; }


	  @Override
		public void createActionMap() throws Exception {
	  }

	  public BizVirtualSerie GetcDato() throws Exception {
	    return (BizVirtualSerie) this.getRecord();
	  }



	  @Override
		public int GetNroIcono() throws Exception {
	    return 10000;
	  }


	}
