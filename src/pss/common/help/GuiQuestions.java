package pss.common.help;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.lists.JWinList;

public class GuiQuestions extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiQuestions() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 953;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Preguntas";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiQuestion.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Registro");
		this.addAction(11, "Ocultar Ayuda", null, 953, true, false, true, "Group");
		this.addAction(20, "Manuales de Usuario", null, 10000, true, true, true, "Group");
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==11) return new JActSubmit(this, a.getId()) {

			@Override
			public void submit() throws Exception {
				BizQuestion.clearPregunta();
			}
		};
		if ( a.getId() == 20 ) return new JActWins(GuiUserManuals.getManuales());
		return null;
	}
	@Override
	public boolean OkAction(BizAction zAct) throws Exception {
		if (!BizUsuario.IsAdminUser() && zAct.getId()==1 )return false;
		return super.OkAction(zAct);
	}
	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("Question");
	}
	
	public void readAll() throws Exception {
		getRecords().endStatic();
		getRecords().readAll();
    while ( getRecords().nextRecord() ) {
    	BizQuestion q = (BizQuestion) getRecords().getRecord();
  		if ( BizUsuario.ifOperacionHabilitada(q.getStartPoint()) ) 
        getRecords().addItem(q);
  		getRecords().setStatic(false);
    }
		getRecords().setStatic(true);
		getRecords().firstRecord();
	}
	
	private JWins getPreguntas() throws Exception {
		GuiQuestions records=new GuiQuestions();
		records.getRecords().addFilter("business", BizUsuario.getUsr().getObjBusiness().getTipoBusiness() );
		return records;
	}
	
	@Override
	public String getPreviewFlag() throws Exception {
		return JWins.PREVIEW_NO;
	}
	

	
}
