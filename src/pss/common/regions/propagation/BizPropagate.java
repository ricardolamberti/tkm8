package  pss.common.regions.propagation;

import pss.common.regions.nodes.BizNodo;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizPropagate extends JRecord {

	private static boolean bDoPropagate=true;

	private JString pIdpropagate=new JString();
	private JString pDescription=new JString();
	private JBoolean pPropagate=new JBoolean();
	private JBoolean pFromChildren=new JBoolean();
	private JBoolean pToChildren=new JBoolean();
	private JBoolean pToMaster=new JBoolean();

	public static final String YPF_PROPAGATE="YPF_PROPAGATE";// identificador para la propagacion de YPF

	private static final String DEFAULT="_config_default_propagate";

	private static JMap<String, JSetupPropagate> oMapping;
	private static String oMappingKey;

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setIdpropagate(String zValue) throws Exception {
		pIdpropagate.setValue(zValue);
	}

	public String getIdpropagate() throws Exception {
		return pIdpropagate.getValue();
	}

	public void setDescription(String zValue) throws Exception {
		pDescription.setValue(zValue);
	}

	public String getDescription() throws Exception {
		return pDescription.getValue();
	}

	public void setPropagate(boolean zValue) throws Exception {
		pPropagate.setValue(zValue);
	}

	public boolean isPropagate() throws Exception {
		return pPropagate.getValue();
	}

	public void setFromChildren(boolean zValue) throws Exception {
		pFromChildren.setValue(zValue);
	}

	public boolean acceptFromChildren() throws Exception {
		return pFromChildren.getValue();
	}

	public void setToChildren(boolean zValue) throws Exception {
		pToChildren.setValue(zValue);
	}

	public boolean sendToChildren() throws Exception {
		return pToChildren.getValue();
	}

	public void setToMaster(boolean zValue) throws Exception {
		pToMaster.setValue(zValue);
	}

	public boolean sendToMaster() throws Exception {
		return pToMaster.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizPropagate() throws Exception {
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Properties methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Adds the object properties
	 */
	@Override
	public void createProperties() throws Exception {
		this.addItem("id_propagate", pIdpropagate);
		this.addItem("description", pDescription);
		this.addItem("propagate", pPropagate);
		this.addItem("fromChildren", pFromChildren);
		this.addItem("toChildren", pToChildren);
		this.addItem("toMaster", pToMaster);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_propagate", "Id propagate", true, true, 50);
		this.addFixedItem(FIELD, "description", "Description", true, true, 100);
		this.addFixedItem(FIELD, "propagate", "Propagate", true, true, 1);
		this.addFixedItem(FIELD, "fromChildren", "Acepta mensaje de los hijos", true, true, 1);
		this.addFixedItem(FIELD, "toChildren", "Envia mensaje de los hijos", true, true, 1);
		this.addFixedItem(FIELD, "toMaster", "Envia mensajes al master", true, true, 1);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "PRO_PROPAGATE";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean Read(String zIdpropagate) throws Exception {
		addFilter("id_propagate", zIdpropagate);
		return Read();
	}

	public static BizPropagatesDetail get(BizNodo zNodo, String zTable) throws Exception {
		if (zNodo==null) return null;
		if (zNodo.getPropagateModel().equals("")) return null;

		BizPropagatesDetail oDetail=new BizPropagatesDetail();
		oDetail.dontThrowException(true);

		if (!oDetail.Read(zNodo.getPropagateModel(), zTable)) {
			BizPropagate oProp=new BizPropagate();
			oProp.dontThrowException(true);

			if (!oProp.Read(zNodo.getPropagateModel())) return null;

			oDetail.setPropagate(oProp.isPropagate());
			oDetail.setFromChildren(oProp.acceptFromChildren());
			oDetail.setToChildren(oProp.sendToChildren());
			oDetail.setToMaster(oProp.sendToMaster());
		}

		return oDetail;
	}

	public static void expire() throws Exception {
		oMapping=null;
	}

	private static void make() throws Exception {
		if (!bDoPropagate) return;
		expire();
		// agrega las diferentes tablas
		oMappingKey=BizUsuario.getUsr().getObjNodo().getPropagateModel();
		if (!oMappingKey.equals("")) {
			oMapping=JCollectionFactory.createMap();
			JRecords<BizPropagatesDetail> oBDs=new JRecords<BizPropagatesDetail>(BizPropagatesDetail.class);
			oBDs.addFilter("id_propagate", oMappingKey);
			oBDs.readAll();
			for(oBDs.firstRecord(); oBDs.nextRecord();) {
				JSetupPropagate oSetup=new JSetupPropagate();
				BizPropagatesDetail oDetail=oBDs.getRecord();
				oSetup.setPropagate(oDetail.isPropagate());
				oSetup.setPropagateChildren(oDetail.sendToChildren());

				BizPropagatesDetail fatherRule=get(BizUsuario.getUsr().getObjNodo().ObtenerNodoPadre(), oDetail.getBdclass());
				if (fatherRule!=null) {
					oSetup.setPropagateParent(fatherRule.acceptFromChildren());
				} else {
					oSetup.setPropagateParent(false);
				}

				oSetup.setPropagateMaster(oDetail.sendToMaster());

				oMapping.addElement(oDetail.getBdclass().toUpperCase(), oSetup);
			}
			oBDs.closeRecord();
		}

		// agrega el default
		BizPropagate oDefault=new BizPropagate();
		oDefault.dontThrowException(true);
		if (!oDefault.Read(oMappingKey)) {
			bDoPropagate=false;
			return;
		}
		JSetupPropagate oSetup=new JSetupPropagate();
		oSetup.setPropagate(oDefault.isPropagate());
		oSetup.setPropagateChildren(oDefault.sendToChildren());

		BizPropagatesDetail fatherRule=get(BizUsuario.getUsr().getObjNodo().ObtenerNodoPadre(), BizPropagate.DEFAULT);
		if (fatherRule!=null) {
			oSetup.setPropagateParent(fatherRule.acceptFromChildren());
		} else {
			oSetup.setPropagateParent(false);
		}

		oSetup.setPropagateMaster(oDefault.sendToMaster());
		oMapping.addElement(BizPropagate.DEFAULT, oSetup);
	}

	public static synchronized JSetupPropagate get(String zTable) throws Exception {
		if (BizUsuario.getUsr().getObjNodo().getPropagateModel()==null) return null;

		if (oMapping==null||!BizUsuario.getUsr().getObjNodo().getPropagateModel().equals(oMappingKey)) make();

		JSetupPropagate oSetup=oMapping.getElement(zTable.toUpperCase());
		if (oSetup==null) oSetup=oMapping.getElement(BizPropagate.DEFAULT);

		return oSetup;
	}

	@Override
	public void processInsert() throws Exception {
		expire();
		super.processInsert();
	}

	@Override
	public void processUpdate() throws Exception {
		expire();
		super.processUpdate();
	}

	@Override
	public void processDelete() throws Exception {
		expire();
		super.processDelete();
	}
}
