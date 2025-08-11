package  pss.common.regions.divitions;

import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;

public class GuiDepartamento extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDepartamento() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDepartamento(); }
  public int GetNroIcono()   throws Exception { return 911; }
  public String GetTitle()   throws Exception { return "Departamento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDepartamento.class; }
  public String  getKeyField() throws Exception { return "dpto_id"; }
  public String  getDescripField() { return "nombre"; }
  public BizDepartamento GetcDato() throws Exception { return (BizDepartamento) this.getRecord(); }

	public void createActionMap() throws Exception {
		addAction(10, "Ciudades", null, 10017, false, false);
		addAction(20, "Vincular Ciudad", null, 10017, true, true);
		super.createActionMap();
	}
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==10)	return new JActWins(this.getCiudades());
		if (a.getId()==20)	return new JActWinsSelect(this.getCiudadesVinculables(),this, true);
		return super.getSubmit(a);
	}

	public JWins getCiudades() throws Exception {
		JWins records=new GuiCiudades();
		records.getRecords().addJoin("reg_ciudad_departamento");
		records.getRecords().addFixedFilter("where reg_ciudad.pais=reg_ciudad_departamento.pais and " +
				" reg_ciudad.provincia=reg_ciudad_departamento.provincia and " +
				" reg_ciudad.ciudad_id=reg_ciudad_departamento.ciudad_id");
		records.getRecords().addFilter("pais", this.GetcDato().getPais());
		records.getRecords().addFilter("provincia", this.GetcDato().getProvincia());
		records.getRecords().addFilter("reg_ciudad_departamento","departamento_id", ""+this.GetcDato().getDptoId(),"=");
		// records.setParent(this);
		records.getRecords().addOrderBy("ciudad", "ASC");

		return records;
	}
	public JWins getCiudadesVinculables() throws Exception {
		JWins records=new GuiCiudades();
		records.getRecords().addFilter("pais", this.GetcDato().getPais());
		records.getRecords().addFilter("provincia", this.GetcDato().getProvincia());
		// records.setParent(this);
		records.getRecords().addOrderBy("ciudad", "ASC");
    records.setDropListener(this);
		return records;
	}
	
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiCiudad) {
			GuiCiudad c = (GuiCiudad) zBaseWin;
			this.GetcDato().execVincularCiudad(c.GetcDato());
		}
		return super.Drop(zBaseWin);
	}
 }
