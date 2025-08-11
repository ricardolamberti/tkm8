package pss.bsp.persona;

import java.awt.event.KeyEvent;

import pss.common.personalData.GuiPersonas;
import pss.common.personalData.types.GuiPersonaFisica;
import pss.common.personalData.types.GuiPersonaJuridica;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;

public class GuiPersonaBSPs extends GuiPersonas {

	public GuiPersonaBSPs() throws Exception {
		super();
	}
	
	 @Override
		public void createActionMap() throws Exception {
	    addActionNew( 1, "Nueva P.Física", KeyEvent.VK_F2 );
	    addActionNew( 2, "Nueva P.Jur�dica" ,KeyEvent.VK_F3);
	}

  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiPersonaFisicaBSP.class; }

	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActNew(this.getNewRegFisica(),0);
		if (a.getId()==2) return new JActNew(this.getNewRegJuridica(),0);
		return super.getSubmit(a);
	}
	
	public GuiPersonaFisicaBSP getNewRegFisica() throws Exception {
		GuiPersonaFisicaBSP e= new GuiPersonaFisicaBSP();
		e.setDropListener(this.getDropListener());
		e.SetVision(this.GetVision());
		e.setModeWinLov(isModeWinLov());
		return e;
	}

	public GuiPersonaJuridicaBSP getNewRegJuridica() throws Exception {
		GuiPersonaJuridicaBSP e= new GuiPersonaJuridicaBSP();
		e.setDropListener(this.getDropListener());
		e.SetVision(this.GetVision());
		e.setModeWinLov(isModeWinLov());
		return e;
	}
}
