package pss.core.win.tools.date;

/**
 * Description: at GuiYears.java
 * @author Iván Rubín
 */

import java.util.Calendar;

import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.JExcepcion;

public class BizYears extends JRecords<BizYear> {

	private String sTabla;
	private String sCampo;
	private Class oType;
	private boolean bReaded=false;

	public BizYears(String zTabla, String zCampo, java.lang.Class zType) {
		this.sTabla=zTabla;
		this.sCampo=zCampo;
		this.oType=zType;
	}

	@Override
	public Class<BizYear> getBasedClass() {
		return BizYear.class;
	}

	@Override
	public synchronized boolean readAll() throws Exception {
		if (this.bReaded) return true;

		JBaseRegistro oRegistro=JBaseRegistro.VirtualCreate();
		String SQL;
		JObject oInicio, oFin;

		// Will drop results here
		oInicio=(JObject) this.oType.newInstance();
		oFin=(JObject) this.oType.newInstance();

		this.setStatic(true);

		// Tries to fetch min and max
		try {
			SQL="SELECT min( "+this.sCampo+" ) as inicio, max( "+this.sCampo+" ) as fin ";
			SQL+=" from "+this.sTabla;
			oRegistro.ExecuteQuery(SQL);
			if (oRegistro.next()) {

				// A better choice to instanceof.. uh...uh....
				if (oInicio instanceof JInteger) {
					oInicio.setValue(oRegistro.CampoAsInt("inicio"));
					oFin.setValue(oRegistro.CampoAsInt("fin"));
				} else if (oInicio instanceof JDate) {
					oInicio.setValue(oRegistro.CampoAsDate("inicio"));
					oFin.setValue(oRegistro.CampoAsDate("fin"));
				} else {
					JExcepcion.SendError("Unrecognized object type");
				}
			}
		} finally {
			try {
				oRegistro.close();
			} catch (Exception ignored) {
				PssLogger.logError(ignored);
			}
		}

		// If it was able to fetch the values
		if (oInicio.hasValue()&&oFin.hasValue()) {
			int iInicio=0, iFin=0;

			// instanceuh..uh...of... drop the values into ints
			if (oInicio instanceof JInteger) {
				iInicio=((JInteger) oInicio).getValue();
				iFin=((JInteger) oFin).getValue();
			} else if (oInicio instanceof JDate) {
				Calendar p=Calendar.getInstance();
				p.setTime(((JDate) oInicio).getValue());
				iInicio=p.get(Calendar.YEAR);
				p.setTime(((JDate) oFin).getValue());
				iFin=p.get(Calendar.YEAR);
			}

			// Creates a BizYear for each element in between iInicio, iFin
			while (iInicio<=iFin) {
				addItem(new BizYear(iInicio, new Integer(iInicio).toString()));
				iInicio++;
			}
		}

		this.bReaded=true;
		return true;
	}
}
