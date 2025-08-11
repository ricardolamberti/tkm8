package  pss.common.help;

import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizQuestionDetail extends JRecord {

	private JLong pIdquestion=new JLong();
	private JLong pSecuencia=new JLong();
	private JString pPage=new JString();
	private JString pAction=new JString();
	private JString pStatus=new JString();
	private JLong pStep=new JLong();
	private JLong pX=new JLong();
	private JLong pY=new JLong();
	private JLong pZ=new JLong();
	private JLong pWidth=new JLong();
	private JLong pHeight=new JLong();
	private JString pType=new JString();
	private JString pTypePos=new JString();
	private JString pId=new JString();
	private JString pHelp=new JString();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setIdquestion(long zValue) throws Exception {
		pIdquestion.setValue(zValue);
	}

	public long getIdquestion() throws Exception {
		return pIdquestion.getValue();
	}

	public void setPage(String zValue) throws Exception {
		pPage.setValue(zValue);
	}

	public String getPage() throws Exception {
		return pPage.getValue();
	}

	public void setAction(String zValue) throws Exception {
		pAction.setValue(zValue);
	}

	public String getAction() throws Exception {
		return pAction.getValue();
	}

	public void setStatus(String zValue) throws Exception {
		pStatus.setValue(zValue);
	}

	public String getStatus() throws Exception {
		return pStatus.getValue();
	}

	public void setStep(long zValue) throws Exception {
		pStep.setValue(zValue);
	}

	public long getStep() throws Exception {
		return pStep.getValue();
	}

	public void setX(long zValue) throws Exception {
		pX.setValue(zValue);
	}

	public long getX() throws Exception {
		return pX.getValue();
	}

	public void setY(long zValue) throws Exception {
		pY.setValue(zValue);
	}

	public long getY() throws Exception {
		return pY.getValue();
	}

	public void setZ(long zValue) throws Exception {
		pZ.setValue(zValue);
	}

	public long getZ() throws Exception {
		return pZ.getValue();
	}

	public void setWidth(long zValue) throws Exception {
		pWidth.setValue(zValue);
	}

	public long getWidth() throws Exception {
		return pWidth.getValue();
	}

	public void setHeight(long zValue) throws Exception {
		pHeight.setValue(zValue);
	}

	public long getHeight() throws Exception {
		return pHeight.getValue();
	}

	public void setType(String zValue) throws Exception {
		pType.setValue(zValue);
	}

	public String getType() throws Exception {
		return pType.getValue();
	}

	public void setTypePos(String zValue) throws Exception {
		pTypePos.setValue(zValue);
	}

	public String getTypePos() throws Exception {
		return pTypePos.getValue();
	}

	public void setId(String zValue) throws Exception {
		pId.setValue(zValue);
	}

	public String getId() throws Exception {
		return pId.getValue();
	}

	public void setHelp(String zValue) throws Exception {
		pHelp.setValue(zValue);
	}

	public String getHelp() throws Exception {
		return pHelp.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizQuestionDetail() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("idQuestion", pIdquestion);
		this.addItem("secuencia", pSecuencia);
		this.addItem("page", pPage);
		this.addItem("action", pAction);
		this.addItem("status", pStatus);
		this.addItem("step", pStep);
		this.addItem("x", pX);
		this.addItem("y", pY);
		this.addItem("z", pZ);
		this.addItem("width", pWidth);
		this.addItem("height", pHeight);
		this.addItem("id", pId);
		this.addItem("typePos", pTypePos);
		this.addItem("type", pType);
		this.addItem("Help", pHelp);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "idQuestion", "Identificador", true, true, 10);
		this.addFixedItem(KEY, "secuencia", "Secuencia", true, true, 10);
		this.addFixedItem(FIELD, "page", "Pagina", true, true, 256);
		this.addFixedItem(FIELD, "action", "Accion", true, true, 256);
		this.addFixedItem(FIELD, "status", "Estado", true, false, 256);
		this.addFixedItem(FIELD, "step", "Paso", true, false, 18);
		this.addFixedItem(FIELD, "x", "X", true, true, 32);
		this.addFixedItem(FIELD, "y", "Y", true, true, 32);
		this.addFixedItem(FIELD, "z", "Z", true, true, 32);
		this.addFixedItem(FIELD, "width", "Ancho", true, false, 32);
		this.addFixedItem(FIELD, "height", "Alto", true, false, 32);
		this.addFixedItem(FIELD, "typePos", "Tipo Posición", true, true, 50);
		this.addFixedItem(FIELD, "id", "Identificador", true, true, 100);
		this.addFixedItem(FIELD, "type", "type", true, true, 50);
		this.addFixedItem(FIELD, "Help", "Ayuda", true, true, 1000);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "HELP_ACTION";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean Read(long zIdQuestion, long sec) throws Exception {
		addFilter("idQuestion", zIdQuestion);
		addFilter("secuencia", sec);
		return read();
	}

	public static JRecords<BizQuestionDetail> ReadByPage(long zIdQuestion, String zPage) throws Exception {
		JRecords<BizQuestionDetail> detalles=new JRecords<BizQuestionDetail>(BizQuestionDetail.class);
		detalles.addFilter("idQuestion", zIdQuestion);
		detalles.addFilter("page", zPage);
		detalles.addOrderBy("step");
		detalles.readAll();
		return detalles;
	}

//	public static JRecords<BizQuestionDetail> ReadPage(long zIdQuestion, String zPage, String zAction) throws Exception {
//		JRecords<BizQuestionDetail> detalles=new JRecords<BizQuestionDetail>(BizQuestionDetail.class);
//		detalles.addFilter("idQuestion", zIdQuestion);
//		detalles.addFilter("page", zPage);
//		detalles.addFilter("action", zAction);
//		detalles.addOrderBy("step");
//		detalles.readAll();
//		return detalles;
//	}
//	
	@Override
	public void processInsert() throws Exception {
		if (pSecuencia.isNull()) {
			BizQuestionDetail max = new BizQuestionDetail();
			max.addFilter("idQuestion", this.pIdquestion.getValue());
			pSecuencia.setValue(max.SelectMaxLong("secuencia")+1);
		}
		this.pZ.setValue(100);
		super.insert();
	}
	
	public void execClonar() throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				clonar();
			}
		};
		exec.execute();
	}
	
	public void clonar() throws Exception {
		BizQuestionDetail clone = new BizQuestionDetail();
		clone.copyProperties(this);
		clone.pSecuencia.setNull();
		clone.processInsert();
	}
	
	
}
