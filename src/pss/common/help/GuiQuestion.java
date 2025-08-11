package  pss.common.help;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiQuestion extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiQuestion() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizQuestion();
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 953;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Pregunta";
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormQuestion.class;
	}

	@Override
	public String getKeyField() throws Exception {
		return "idQuestion";
	}

	@Override
	public String getDescripField() {
		return "Question";
	}

	public BizQuestion GetcDato() throws Exception {
		return (BizQuestion) this.getRecord();
	}

	@Override
	public void createActionMap() throws Exception {
		this.addActionQuery(1, "Consultar");
		this.addActionUpdate(2, "Modificar");
		this.addActionDelete(3, "Eliminar");
		this.addAction(10, "Detalle", null, 953, true, false, true, "Group");
		this.addAction(45, "Preguntar", null, 953, true, true);

	}
	
	@Override
	public boolean OkAction(BizAction zAct) throws Exception {
		if (zAct.getId()!=45 && !BizUsuario.IsAdminUser())return false;
		return super.OkAction(zAct);
	}

	@Override
	public int GetDobleClick() {
		return 45;
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getDetalles());
		if (a.getId()==45) return new JActSubmit(this, a.getId()) {
			private static final long serialVersionUID=1L;
			@Override
			public void submit() throws Exception {
				GetcDato().setPregunta();
			}
			public JAct nextAction() throws Exception { 
				return getStartPoint();
			}
			 
		};;
		return null;
	}

	public JWins getDetalles() throws Exception {
		GuiQuestionDetails wins=new GuiQuestionDetails();
		wins.getRecords().addFilter("idQuestion", this.GetcDato().getIdquestion());
		wins.getRecords().addOrderBy("step");
		return wins;
	}

	public JAct getStartPoint() throws Exception {
		JAct startPoint=null;
		try {
//			if (GetcDato().getStartPoint()!=null) startPoint=GuiModuloPss.getModuloPss().findActionByUniqueId(GetcDato().getStartPoint()).getSubmit();
			if (GetcDato().getStartPoint()!=null) startPoint=BizUsuario.getUsr().getModuloPss().findActionByUniqueId(GetcDato().getStartPoint()).getSubmit();
			else startPoint=new JActWins(this.getDetalles());
		} catch (RuntimeException e) {
			startPoint=new JActWins(this.getDetalles());
		}
		startPoint.setActionId(45);
		return startPoint;
	}
}
