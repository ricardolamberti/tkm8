package  pss.common.help;

import pss.core.JAplicacion;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizQuestion extends JRecord {

	private JLong pIdquestion=new JLong();
	private JString pQuestion=new JString();
	private JString pStartPoint=new JString();
	private JString business=new JString();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setIdquestion(long zValue) throws Exception {
		pIdquestion.setValue(zValue);
	}

	public long getIdquestion() throws Exception {
		return pIdquestion.getValue();
	}

	public void setQuestion(String zValue) throws Exception {
		pQuestion.setValue(zValue);
	}

	public String getQuestion() throws Exception {
		return pQuestion.getValue();
	}

	public void setStartPoint(String zValue) throws Exception {
		pStartPoint.setValue(zValue);
	}

	public String getStartPoint() throws Exception {
		return pStartPoint.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizQuestion() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("idQuestion", pIdquestion);
		this.addItem("Question", pQuestion);
		this.addItem("startPoint", pStartPoint);
		this.addItem("business", business);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "idQuestion", "Identificador", true, true, 10);
		this.addFixedItem(FIELD, "Question", "Ayuda", true, true, 256);
		this.addFixedItem(FIELD, "startPoint", "Punto de entrada", true, true, 256);
		this.addFixedItem(FIELD, "business", "Negocio", true, false, 50);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "HELP_QUESTION";
	}

	public void setPregunta() {
		JAplicacion.GetApp().setQuestionHelp(this);
	}

	public static boolean isQuestionMode() {
		return JAplicacion.GetApp().getQuestionHelp()!=null;
	}
	
	public static void clearPregunta() {
		JAplicacion.GetApp().setQuestionHelp(null);
	}

	public static void toggleHelp() {
		JAplicacion.GetApp().setActiveHelp(!JAplicacion.GetApp().isActiveHelp());
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean Read(long zIdquestion) throws Exception {
		addFilter("idQuestion", zIdquestion);
		return Read();
	}

	public JRecords<BizQuestionDetail> getDetalles() throws Exception {
		JRecords<BizQuestionDetail> records=new JRecords<BizQuestionDetail>(BizQuestionDetail.class);
		records.addFilter("idQuestion", this.pIdquestion.getValue());
		records.readAll();
		return records;
	}

}
