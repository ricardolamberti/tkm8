package  pss.common.dbManagement.depuration;

import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;

public class BizDepuration extends JRecord {

	JString pPais=new JString();
	JInteger pDiasSinDepurar=new JInteger();
	JString pModulo=new JString();
	JString pEntidad=new JString();
	JString pDescrEntidad=new JString() {

		@Override
		public void preset() throws Exception {
			setearDescrEntidad();
		}
	};
	JString pDescrModulo=new JString() {

		@Override
		public void preset() throws Exception {
			setearDescrModulo();
		}
	};

	public int getDiasSinDepurar() throws Exception {
		return pDiasSinDepurar.getValue();
	}

	public String GetModulo() throws Exception {
		return this.pModulo.getValue();
	}

	public String GetEntidad() throws Exception {
		return this.pEntidad.getValue();
	}

	public BizDepuration() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("pais", pPais);
		addItem("modulo", pModulo);
		addItem("entidad", pEntidad);
		addItem("descr_modulo", pDescrModulo);
		addItem("descr_entidad", pDescrEntidad);
		addItem("dias_sin_depurar", pDiasSinDepurar);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "pais", "Pais", true, true, 2);
		addFixedItem(KEY, "modulo", "Modulo", true, true, 50);
		addFixedItem(KEY, "entidad", "Entidad", true, true, 30);
		addFixedItem(VIRTUAL, "descr_modulo", "Modulo", true, false, 30);
		addFixedItem(VIRTUAL, "descr_entidad", "Entidad", true, false, 30);
		addFixedItem(FIELD, "dias_sin_depurar", "Dias sin depurar", true, false, 4);
	}

	@Override
	public String GetTable() {
		return "dep_depuration";
	}

	public static void depurarModulos() throws Exception {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		JRecords<BizDepuration> oDeps=new JRecords<BizDepuration>(BizDepuration.class);
		oDeps.addFilter("pais", BizUsuario.getUsr().getCountry());
		oDeps.readAll();
		oDeps.toStatic();
		oDeps.firstRecord();
		while (oDeps.nextRecord()) {
			BizDepuration oDep=oDeps.getRecord();

			PssLogger.logWait("Depurando Entidad: "+oDep.pEntidad.getValue());
			oDep.execPurge();
		}
	}

	private void execPurge() throws Exception {
		JExec oExec=new JExec(null, null) {

			@Override
			public void Do() throws Exception {
				purge();
			}
		};
		oExec.execute();
	}

	private void purge() throws Exception {
		JPurgeInterface oClase=(JPurgeInterface) Class.forName(pEntidad.getValue()).newInstance();
		oClase.doPurgetion(this);
	}

	public Date maxDate() throws Exception {
		return JDateTools.SubDays(BizUsuario.getUsr().todayGMT(), pDiasSinDepurar.getValue());
	}

	private void setearDescrEntidad() throws Exception {
		pDescrEntidad.setValue(JPurgeList.getPurgeList(pModulo.getValue()).findVirtualKey(pEntidad.getValue()).getDescrip());
	}

	private void setearDescrModulo() throws Exception {
		pDescrModulo.setValue(JPurgeList.getPurgeSets().findVirtualKey(pModulo.getValue()).getDescrip());
	}


}
