package pss.bsp.contrato.evaluate;

import java.util.Calendar;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.evaluate.detail.BizEvaluateDetail;
import pss.common.event.sql.BizSqlEvent;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWins;

public class BizEvaluate extends JRecord {

	private JString pNumeroBoleto = new JString();
	private JLong pIdLinea = new JLong();
	private JLong pEvent = new JLong();
	private JObjBDs<BizEvaluateDetail> pTests = new JObjBDs<BizEvaluateDetail>();
	private JString pDiagnostico = new JString();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setId(long zValue) throws Exception {
		pIdLinea.setValue(zValue);
	}

	public long getId() throws Exception {
		return pIdLinea.getValue();
	}
	public void setEvent(long zValue) throws Exception {
		pEvent.setValue(zValue);
	}

	public long getEvent() throws Exception {
		return pEvent.getValue();
	}

	public boolean isNullId() throws Exception {
		return pIdLinea.isNull();
	}

	public void setNullToId() throws Exception {
		pIdLinea.setNull();
	}

	public void setNumeroBoleto(String zValue) throws Exception {
		pNumeroBoleto.setValue(zValue);
	}

	public String getNumeroBoleto() throws Exception {
		return pNumeroBoleto.getValue();
	}

	public void setDiagnostico(String zValue) throws Exception {
		pDiagnostico.setValue(zValue);
	}

	public String getDiagnostico() throws Exception {
		return pDiagnostico.getValue();
	}

	public JRecords<BizEvaluateDetail> getTest() throws Exception {
		if (pTests.isRawNull()) {
			JRecords<BizEvaluateDetail> convs = new JRecords<BizEvaluateDetail>(BizEvaluateDetail.class);
			convs.setStatic(true);
			pTests.setValue(convs);
			convs.addItem(new BizEvaluateDetail(1, "Chequear si es primera, es reemision"));
			convs.addItem(new BizEvaluateDetail(2, "Chequear si es openJaw, es roundtrip"));
			convs.addItem(new BizEvaluateDetail(3, "Chequear si es posterior al despegue, es ante del despegue"));
			convs.addItem(new BizEvaluateDetail(4, "Chequear si es multi destino >4, es menos de 4 destinos"));
			convs.addItem(new BizEvaluateDetail(5, "Chequear si es stop over, es no stop over"));
			convs.addItem(new BizEvaluateDetail(6, "Chequear si es vuelta al mundo, no es vuelta al mundo"));
			convs.addItem(new BizEvaluateDetail(7, "Chequear si es fecha"));
			convs.addItem(new BizEvaluateDetail(8, "Chequear si es aerolinea"));
			convs.addItem(new BizEvaluateDetail(9, "Chequear si es interlineal"));
			convs.addItem(new BizEvaluateDetail(10, "Chequear si es iata"));
			convs.addItem(new BizEvaluateDetail(11, "Chequear si es tourcode excluded"));
			convs.addItem(new BizEvaluateDetail(12, "Chequear si es tourcode included"));
			convs.addItem(new BizEvaluateDetail(13, "Chequear si es tipo pasajero excluded"));
			convs.addItem(new BizEvaluateDetail(14, "Chequear si es tipo pasaero included"));
			convs.addItem(new BizEvaluateDetail(15, "Chequear si es Fare excluidas"));
			convs.addItem(new BizEvaluateDetail(16, "Chequear si es Fare incluidas"));
			convs.addItem(new BizEvaluateDetail(17, "Chequear si es gds"));
			convs.addItem(new BizEvaluateDetail(18, "Chequear si es clases excluded"));
			convs.addItem(new BizEvaluateDetail(19, "Chequear si es clases included"));
			convs.addItem(new BizEvaluateDetail(20, "Chequear si es Mercado"));
			convs.addItem(new BizEvaluateDetail(21, "Chequear si es No mercado"));
			convs.addItem(new BizEvaluateDetail(22, "Chequear si es vuelos"));
			convs.addItem(new BizEvaluateDetail(23, "Chequear si es no vuelos"));
			convs.addItem(new BizEvaluateDetail(24, "Chequear si es rutas"));
			convs.addItem(new BizEvaluateDetail(25, "Chequear si es no rutas"));
			convs.addItem(new BizEvaluateDetail(26, "Chequear si es domestico internacional"));
			convs.addItem(new BizEvaluateDetail(27, "Chequear si es origen-destino"));
		
		}
		return pTests.getValue();
	}

	BizDetalle objDetalle;

	public BizDetalle getObjDetalle() throws Exception {
		if (objDetalle == null) {
			BizDetalle det = new BizDetalle();
			det.dontThrowException(true);
			if (!det.read(getId()))
				return null;
			BizDetalle detFinal = det.getObjLogicaInstance().getBiz();
			detFinal.copyProperties(det);
			objDetalle = detFinal;
		}

		return objDetalle;
	}

	/**
	 * Constructor de la Clase
	 */
	public BizEvaluate() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id", pIdLinea);
		this.addItem("event", pEvent);
			this.addItem("numero_boleto", pNumeroBoleto);
		this.addItem("diagnostico", pDiagnostico);
		this.addItem("tests", pTests);
		// this.addItem( "serialize_detalle", pSerializeDetalle );
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "Id", false, false, 32);
		this.addFixedItem(FIELD, "event", "event", false, false, 32);
			this.addFixedItem(FIELD, "numero_boleto", "numero_boleto", true, false, 32);
		this.addFixedItem(FIELD, "diagnostico", "diagnostico", true, false, 5000);
		this.addFixedItem(FIELD, "tests", "tests", true, false, 4000).setClase(BizEvaluateDetail.class);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return null;
	}

	public void processEvaluar() throws Exception {
		if (getNumeroBoleto().isEmpty()) {
			setDiagnostico("Ingrese un número de boleto");
		}
		BizSqlEvent event = null;
		if (getEvent()==1)
			event=getObjDetalle().getObjEvent();
		else if (getEvent()==2)
			event=getObjDetalle().getObjEventGanancia();
		else
			event=getObjDetalle().getObjEventAuxiliar();
		JWins wins = null;
		
		boolean isBooking = event.getConsulta().indexOf("ES_BOOKING")!=-1;
		Calendar factual = Calendar.getInstance();
		factual.setTime(getObjDetalle().getObjContrato().getFechaHasta());
		wins = event.getDetalles(null, factual, "", (isBooking?" and TUR_PNR_BOOKING.numeroboleto='":" and numeroboleto='") + getNumeroBoleto() + "'");

		if (wins.getRecords().exists()) {
			setDiagnostico("Pertenece al contrato");
			return;
		}
		JRecords<BizEvaluateDetail> recs = new JRecords<BizEvaluateDetail>(BizEvaluateDetail.class);
		recs.setStatic(true);
		setDiagnostico("No pertenece al contrato");

		JIterator<BizEvaluateDetail> it = getTest().getStaticIterator();
		while (it.hasMoreElements()) {
			BizEvaluateDetail detail = it.nextElement();
			boolean hasMarca = event.getSQLDetalles(null, null,(isBooking?" and TUR_PNR_BOOKING.numeroboleto='":" and numeroboleto='") + getNumeroBoleto() + "'").indexOf("/*" + detail.getNumero() + "*/") != -1;

			if (!hasMarca) {
				detail.setResult("");
				detail.setOk("NO APLICA");
				recs.addItem(detail);
				continue;
			}
			wins = event.procEvaluate(detail.getNumero(),(isBooking?" and TUR_PNR_BOOKING.numeroboleto='":" and numeroboleto='") + getNumeroBoleto() + "'");

			if (wins.getRecords().exists()) {
				detail.setResult("Cumple!");
				detail.setOk("OK");
				recs.addItem(detail);
			} else {
				detail.setResult("No Cumple");
				detail.setOk("NO");
				recs.addItem(detail);

			}

		}
		pTests.setValue(recs);
	}
}
