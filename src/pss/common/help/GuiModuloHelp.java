package  pss.common.help;

import pss.common.security.BizUsuario;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;

public class GuiModuloHelp extends GuiModulo {

	public GuiModuloHelp() throws Exception {
		super();
		SetModuleName("Ayuda Dirigida");
		SetNroIcono(953);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return addAction(1, "Ayuda Dirigida", null, 953, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(10, "Preguntas", null, 953, true, false, true, "Group");
		this.addAction(11, "Ocultar Ayuda", null, 953, true, false, true, "Group");
		this.addAction(16, "Activar ayuda", null, 953, true, false, true, "Group");
		this.addAction(20, "Manuales de Usuario", null, 10000, true, true, true, "Group");
		this.addAction(25, "Ayuda", null, 953, true, false, true, "Group");
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==16) return new JActSubmit(this, a.getId()) {
			@Override
			public void submit() throws Exception {
				BizQuestion.toggleHelp();
			}
		  
		};

		if (a.getId()==10) {
			if (BizQuestion.isQuestionMode()) return new JActSubmit(this, a.getId()) {
				private static final long serialVersionUID=1L;
				@Override
				public void submit() throws Exception {
					BizQuestion.clearPregunta();
				}
			  
			};
			else return new JActWins(this.getPreguntas());
		}
		
		if (a.getId()==11) return new JActSubmit(this, a.getId()) {
			private static final long serialVersionUID=1L;
			@Override
			public void submit() throws Exception {
				BizQuestion.clearPregunta();
			}
		};
		if ( a.getId() == 20 ) return new JActWins(GuiUserManuals.getManuales());
		if ( a.getId() == 25 ) return new JActWins(new GuiHelp());
		return null;
	}

	private JWins getPreguntas() throws Exception {
		GuiQuestions records=new GuiQuestions();
		records.getRecords().addFilter("business", BizUsuario.getUsr().getObjBusiness().getTipoBusiness() );
		return records;
	}

}
