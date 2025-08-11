package pss.core.winUI.lists;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiListExcludeCol extends JWin {

	/**
	 * Constructor de la Blase
	 */
	public GuiListExcludeCol() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizListExcludeCol();
	}

	public int GetNroIcono() throws Exception {
		if (this.GetcDato().isExcluded())
			return 800;
		else
			return 801;
	}

	@Override
	public void createActionMap() throws Exception {
		BizAction a = this.addAction(35, "Excluir", null, 48, true, true);
		a.setMulti(true);
		BizAction b = this.addAction(36, "Incluir", null, 3, true, true);
		b.setMulti(true);
		BizAction c = this.addAction(45, "Solo Admin", null, 3, true, true);
		c.setMulti(true);
		BizAction d = this.addAction(46, "Sacar Admin", null, 48, true, true);
		d.setMulti(true);

		this.addAction(40, "Subir", null, 15056, true, true).setPostFunction("goUp('provider_ExcludeCols');");
		this.addAction(50, "Bajar", null, 15057, true, true).setPostFunction("goDown('provider_ExcludeCols');");
		
	}

	@Override
	public boolean OkAction(BizAction zAct) throws Exception {

		switch (zAct.getId()) {
		case 35:
			return this.GetcDato().isExcluded()==false;
		case 36:
			return this.GetcDato().isExcluded();
		case 45:
			return this.GetcDato().isAdmin()==false  && BizUsuario.IsAdminCompanyUser();
		case 46:
			return this.GetcDato().isAdmin() && BizUsuario.IsAdminCompanyUser();
		}

		return super.OkAction(zAct);
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		
		if (a.getId()==35) return new JActSubmit(this, 35) {
			public void submit() throws Exception {
				GetcDato().execProcessExclude();
			}
		};
		if (a.getId()==36) return new JActSubmit(this, 36) {
			public void submit() throws Exception {
				GetcDato().execProcessInclude();
			}
		};	
		
		if (a.getId()==45) return new JActSubmit(this, 35) {
			public void submit() throws Exception {
				GetcDato().execProcessSetAdmin();
			}
		};
		if (a.getId()==46) return new JActSubmit(this, 36) {
			public void submit() throws Exception {
				GetcDato().execProcessUnsetAdmin();
			}
		};	
		
		if (a.getId()==40) return new JActSubmit(this, 40) {
			public void submit() throws Exception {
				GetcDato().execProcessUp();
			}
		};
		if (a.getId()==50) return new JActSubmit(this, 50) {
			public void submit() throws Exception {
				GetcDato().execProcessDown();
			}
		};			

		return null;
	}


	public String GetTitle() throws Exception {
		return "Configurar Columna";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormListExcludeCol.class;
	}

	public String getKeyField() throws Exception {
		return "class_name";
	}

	public String getDescripField() {
		return "col_name";
	}

	public BizListExcludeCol GetcDato() throws Exception {
		return (BizListExcludeCol) this.getRecord();
	}

}
