package  pss.common.personalData;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;
import pss.core.winUI.icons.GuiIcon;

public class GuiPersona extends JWin {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiPersona() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizPersona();
	}

	@Override
	public int GetNroIcono() throws Exception {
		if (this.GetcDato().isAEliminar()) return GuiIcon.CANCELAR_ICON;
		if (this.GetcDato().isPersonaFisica()) return 761;
		if (this.GetcDato().isPersonaJuridica()) return 1102;
		if (this.GetcDato().isPersonaAdm()) return 77;
		return 1;
	}

	@Override
	public String getKeyField() throws Exception {
		return "persona";
	}
	
	@Override
	public String getDescripField() {
		return "description";
	}
	
	@Override
	public String GetTitle() throws Exception {
		return "Persona "+GetcDato().getNombreCompleto();
	}
	
	@Override
	public String getOwnerTitle() throws Exception {
		return "Persona";
	}


	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
  	this.addActionQuery ( 1, "Consultar" );
    this.addActionUpdate( 2, "Modificar" );
    this.addAction(10, "Domicilios",null, 763, false, false);
    this.addAction(12, "Teléfonos",null, 762, false, false);
   // this.addAction(14, "Hobbies",InputEvent.CTRL_MASK|KeyEvent.VK_H, null, 986, false, false);
		this.addAction(16, "Marcar a Eliminar", null, 7, true, true);
		this.addAction(17, "DesMarcar", null, 2, true, true);
		this.addAction(18, "Unificar", null, 2, true, true);
		
  }
	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==16) return !this.GetcDato().isAEliminar();
		if (a.getId()==17) return this.GetcDato().isAEliminar();
		return super.OkAction(a);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getDomicilios());
		if (a.getId()==12) return new JActWins(this.getTelefonos());
		if (a.getId()==14) return new JActQuery(this.getPersonaMaster());
		return null;
	}

	// -------------------------------------------------------------------------//
	// Devuelvo el dato ya casteado
	// -------------------------------------------------------------------------//
	public BizPersona GetcDato() throws Exception {
		return (BizPersona) this.getRecord();
	}

	public JWins getDomicilios() throws Exception {
		GuiDomicilios guis=new GuiDomicilios();
		guis.getRecords().addFilter("persona", GetcDato().GetPersona());
		return guis;
	}

	public JWins getTelefonos() throws Exception {
		GuiTelefonos guis=new GuiTelefonos();
		guis.getRecords().addFilter("persona", GetcDato().GetPersona());
		return guis;
	}

	public JWin getPersonaMaster() throws Exception {
		return null;
	}


}
