/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.requestBundle;

import java.io.Serializable;
import java.util.Map;

import com.google.gson.Gson;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.processing.JXMLRepresentable;

/**
 * 
 * 
 * Created on 17-jun-2003
 * 
 * @author PSS
 */

public class JWebActionData implements JXMLRepresentable,Serializable {

	public static final JWebActionData NULL = new JWebActionData("NULL", true);

	private JMap<String, JWebActionDataField> oMap;

	private String sId;

	private boolean bReadOnly;

	public JWebActionData(String zId) {
		this.oMap = JCollectionFactory.createOrderedMap();
		this.sId = zId;
	}

	public JWebActionData(String zId, boolean zReadOnly) {
		this.oMap = JCollectionFactory.createOrderedMap();
		this.sId = zId;
		this.bReadOnly = zReadOnly;
	}
	public void clear() {
		if (this.oMap != null) {
			this.oMap = JCollectionFactory.createOrderedMap();
		}
	}
	public void destroy() {
		if (this.oMap != null) {
			this.oMap.removeAllElements();
			this.oMap = null;
		}
		this.sId = null;
	}

	public boolean isNull() {
		return this == NULL;
	}
	
	public boolean isEmpty() {
		return this.oMap.isEmpty();
	}

	public void add(String zFieldName, String zValue) {
		this.add(zFieldName, "=", zValue);
	}
	public void replace(String zFieldName, String zValue) {
		this.replace(zFieldName, "=", zValue,false);
	}
	public void addEncrypted(String zFieldName, String zOperator, String zValue) {
		add(zFieldName, zOperator, zValue, true);
	}

	public void add(String zFieldName, String zOperator, String zValue) {
		add(zFieldName, zOperator, zValue, false);
	}

	private void add(String zFieldName, String zOperator, String zValue,boolean zEncrypted) {
		if (this.bReadOnly) {
			throw new RuntimeException("Data is read only !!!");
		}
		this.oMap.addElement(zFieldName, new JWebActionDataField(zFieldName, zOperator, zValue, zEncrypted));
	}


	private void replace(String zFieldName, String zOperator, String zValue, boolean zEncrypted) {
		if (this.oMap.removeElement(zFieldName)==null) return;
		this.oMap.addElement(zFieldName, new JWebActionDataField(zFieldName,zOperator, zValue, zEncrypted));
	}
	public JWebActionDataField getField(String zFieldName) {
		return this.oMap.getElement(zFieldName);
	}

	public boolean hasField(String f) {
		return this.get(f)!=null;
	}
	public String get(String zFieldName) {
		JWebActionDataField oArg = this.oMap.getElement(zFieldName);
		if (oArg == null) {
			return null;
		} else {
                        String s = oArg.getValue().replace("á", "");
                        s = s.replace("é", "");
                        s = s.replace("í", "");
                        s = s.replace("ó", "");
                        s = s.replace("ú", "");
                        s = s.replace("ñ", "");
			return s;
		}
	}

	public void toXML(JXMLContent zContent) throws Exception {
		zContent.startNode("data");
		zContent.setAttribute("id", this.sId);
		JIterator<JWebActionDataField> oArgIt = this.oMap.getValueIterator();
		while (oArgIt.hasMoreElements()) {
			oArgIt.nextElement().toXML(zContent);
		}
		zContent.endNode("data");
	}

	public JIterator<JWebActionDataField> getFieldIterator() {
		return this.oMap.getValueIterator();
	}

	public String getId() {
		return this.sId;
	}

	@Override
	public String toString() {
		StringBuffer oBuff = new StringBuffer();
		String sIndent = "    ";
		oBuff.append(sIndent).append("data[").append(this.sId).append("] {\n");
		JIterator<JWebActionDataField> oFldIt = this.getFieldIterator();
		JWebActionDataField oField;
		while (oFldIt.hasMoreElements()) {
			oField = oFldIt.nextElement();
			oBuff.append(sIndent).append(sIndent).append(oField.getName()).append(
					oField.getOperator()).append('\'').append(oField.getValue()).append(
					'\'').append('\n');
		}
		oBuff.append(sIndent).append("}");
		return oBuff.toString();
	}

	public boolean isReadOnly() {
		return this.bReadOnly;
	}

        public void setReadOnly(boolean b) {
                this.bReadOnly = b;
        }

        public String serialize() throws Exception {
                Gson gson = new Gson();
                DataWrapper d = new DataWrapper();
                d.id = this.sId;
                d.readOnly = this.bReadOnly;
                d.fields = this.oMap != null ? this.oMap.toMap() : null;
                return gson.toJson(d);
        }

        public static JWebActionData unserialize(String json) throws Exception {
                Gson gson = new Gson();
                DataWrapper d = gson.fromJson(json, DataWrapper.class);
                JWebActionData data = new JWebActionData(d.id, d.readOnly);
                if (d.fields != null) {
                        for (Map.Entry<String, JWebActionDataField> e : d.fields.entrySet()) {
                                data.oMap.addElement(e.getKey(), e.getValue());
                        }
                }
                return data;
        }

        private static class DataWrapper {
                String id;
                boolean readOnly;
                Map<String, JWebActionDataField> fields;
        }

}
