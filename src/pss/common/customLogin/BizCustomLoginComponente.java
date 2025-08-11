package pss.common.customLogin;

import pss.common.help.BizQuestionDetail;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizCustomLoginComponente extends JRecord {

	private JLong pId=new JLong();
	private JLong pSecuencia=new JLong();
	private JString pPage=new JString();
	private JString pAction=new JString();
	private JString pStatus=new JString();
	private JLong pStep=new JLong();
	private JLong pLocalization=new JLong();
	private JString pImage=new JString();
	private JString pLink=new JString();
	private JLong pX=new JLong();
	private JLong pY=new JLong();
	private JLong pZ=new JLong();
	private JLong pWidth=new JLong();
	private JLong pHeight=new JLong();
	private JString pTypePos=new JString();
	private JString pIdhtml=new JString();
	private JString pType=new JString();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setId(long zValue) throws Exception {
		pId.setValue(zValue);
	}

	public long getId() throws Exception {
		return pId.getValue();
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

	public void setStep(long zValue) throws Exception {
		pStep.setValue(zValue);
	}

	public long getStep() throws Exception {
		return pStep.getValue();
	}

	public void setLocalization(long zValue) throws Exception {
		pLocalization.setValue(zValue);
	}

	public long getLocalization() throws Exception {
		return pLocalization.getValue();
	}

	public void setImage(String zValue) throws Exception {
		pImage.setValue(zValue);
	}

	public String getImage() throws Exception {
		return pImage.getValue();
	}

	public void setLink(String zValue) throws Exception {
		pLink.setValue(zValue);
	}

	public String getLink() throws Exception {
		return pLink.getValue();
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

	public void setIdhtml(String zValue) throws Exception {
		pIdhtml.setValue(zValue);
	}

	public String getIdhtml() throws Exception {
		return pIdhtml.getValue();
	}

	public void setTypePos(String zValue) throws Exception {
		pTypePos.setValue(zValue);
	}

	public String getTypePos() throws Exception {
		return pTypePos.getValue();
	}

	public void setType(String zValue) throws Exception {
		pType.setValue(zValue);
	}

	public String getType() throws Exception {
		return pType.getValue();
	}

	public void setStatus(String zValue) throws Exception {
		pStatus.setValue(zValue);
	}

	public String getStatus() throws Exception {
		return pStatus.getValue();
	}

	public void setZ(long zValue) throws Exception {
		pZ.setValue(zValue);
	}

	public long getZ() throws Exception {
		return pZ.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizCustomLoginComponente() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("id", pId);
		this.addItem("secuencia", pSecuencia);
		this.addItem("page", pPage);
		this.addItem("action", pAction);
		this.addItem("status", pStatus);
		this.addItem("step", pStep);
		this.addItem("localization", pLocalization);
		this.addItem("image", pImage);
		this.addItem("link", pLink);
		this.addItem("x", pX);
		this.addItem("y", pY);
		this.addItem("z", pZ);
		this.addItem("width", pWidth);
		this.addItem("height", pHeight);
		this.addItem("idhtml", pIdhtml);
		this.addItem("typePos", pTypePos);
		this.addItem("type", pType);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "Id", true, true, 18);
		this.addFixedItem(KEY, "secuencia", "Secuencia", true, true, 10);
		
		this.addFixedItem(FIELD, "page", "Pagina", true, true, 256);
		this.addFixedItem(FIELD, "action", "Accion", true, true, 256);
		this.addFixedItem(FIELD, "status", "Estado", true, true, 256);
		this.addFixedItem(FIELD, "step", "Paso", true, true, 18);
		this.addFixedItem(FIELD, "localization", "Localización imagen", true, true, 18);
		this.addFixedItem(FIELD, "image", "Imagen", true, true, 256);
		this.addFixedItem(FIELD, "link", "Link", true, true, 1000);
		this.addFixedItem(FIELD, "x", "X", true, true, 5);
		this.addFixedItem(FIELD, "y", "Y", true, true, 5);
		this.addFixedItem(FIELD, "z", "Z", true, true, 32);
		this.addFixedItem(FIELD, "width", "Ancho", true, true, 5);
		this.addFixedItem(FIELD, "height", "Alto", true, true, 5);
		this.addFixedItem(FIELD, "idhtml", "Idhtml", true, true, 100);
		this.addFixedItem(FIELD, "typePos", "Tipo posición", true, true, 50);
		this.addFixedItem(FIELD, "type", "Tipo", true, true, 50);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "SEG_CUSTOMLOGIN_COMPONENT";
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


	public static JRecords<BizCustomLoginComponent> ReadCampaign(long zIdCampaign) throws Exception {
		JRecords<BizCustomLoginComponent> detalles=new JRecords<BizCustomLoginComponent>(BizCustomLoginComponent.class);
		detalles.addFilter("id", zIdCampaign);
		detalles.addOrderBy("page");
		detalles.addOrderBy("step");
		detalles.readAll();
		return detalles;
	}

	public static JRecords<BizCustomLoginComponent> ReadPage(long zIdCampaign, String zPage) throws Exception {
		JRecords<BizCustomLoginComponent> detalles=new JRecords<BizCustomLoginComponent>(BizCustomLoginComponent.class);
		detalles.addFilter("id", zIdCampaign);
		detalles.addFilter("page", zPage);
		detalles.addOrderBy("step");
		detalles.readAll();
		return detalles;
	}

	public static JRecords<BizQuestionDetail> ReadAction(long zIdCampaign, String zPage, String zAction) throws Exception {
		JRecords<BizQuestionDetail> detalles=new JRecords<BizQuestionDetail>(BizQuestionDetail.class);
		detalles.addFilter("id", zIdCampaign);
		detalles.addFilter("page", zPage);
		detalles.addFilter("action", zAction);
		detalles.addOrderBy("step");
		detalles.readAll();
		return detalles;
	}

	@Override
	public void processInsert() throws Exception {
		if (pSecuencia.isNull()) {
			BizCustomLoginComponent max = new BizCustomLoginComponent();
			max.addFilter("id", this.pId.getValue());
			pSecuencia.setValue(max.SelectMaxLong("secuencia")+1);
		}
		super.insert();
	}
	

	
	

}
