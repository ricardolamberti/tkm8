package  pss.common.help;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiQuestionDetail extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiQuestionDetail() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizQuestionDetail();
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 724;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Detalle Pregunta";
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormQuestionDetail.class;
	}

	@Override
	public String getKeyField() throws Exception {
		return "page";
	}

	@Override
	public String getDescripField() {
		return "Help";
	}

	public BizQuestionDetail GetcDato() throws Exception {
		return (BizQuestionDetail) this.getRecord();
	}

	@Override
	public void createActionMap() throws Exception {
		this.addActionQuery(1, "Consultar");
		this.addActionUpdate(2, "Modificar");
		this.addActionDelete(3, "Eliminar");
		this.addAction(10, "Clonar", null, 1, true, true);
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActSubmit(this, 10) {
			public void submit() throws Exception {
				GetcDato().execClonar();
			}
		};
		return super.getSubmitFor(a);
	}

}
