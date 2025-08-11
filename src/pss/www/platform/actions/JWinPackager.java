package pss.www.platform.actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.fasterxml.jackson.databind.ObjectMapper;

import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.fields.JObject;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.submits.JAct;
import pss.www.platform.cache.CacheProvider;

public class JWinPackager {

	private final JWebWinFactory factory;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public static String b64url(byte[] data) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
	}

	public static byte[] b64urlDecode(String data) {
		return Base64.getUrlDecoder().decode(data);
	}

	public static byte[] deflate(byte[] input) {
		Deflater deflater = new Deflater();
		deflater.setInput(input);
		deflater.finish();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			baos.write(buffer, 0, count);
		}
		deflater.end();
		return baos.toByteArray();
	}

	public static byte[] inflate(byte[] input) throws IOException {
		Inflater inflater = new Inflater();
		inflater.setInput(input);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				baos.write(buffer, 0, count);
			}
		} catch (java.util.zip.DataFormatException e) {
			throw new IOException(e);
		} finally {
			inflater.end();
		}
		return baos.toByteArray();
	}

	public JWinPackager(JWebWinFactory factory) {
		this.factory = factory;
	}

	public JBaseWin getRegisterObjectTemp(String zKey) throws Exception {
		try {
			return createWinFromPack(zKey, null);
		} catch (Exception e) {
			byte[] decoded;
			try {
				decoded = java.util.Base64.getUrlDecoder().decode(zKey);
			} catch (IllegalArgumentException ex) {
				decoded = java.util.Base64.getDecoder().decode(zKey);
			}
			String json;
			try {
				json = new String(inflate(decoded), StandardCharsets.UTF_8);
			} catch (Exception ex2) {
				json = JTools.byteVectorToString(decoded);
			}
			return createWinFromJson(json, null);
		}
	}

	public JBaseRecord getRegisterObjectRecTemp(String zKey) throws Exception {
		try {
			return createRecFromPack(zKey, null);
		} catch (Exception e) {
			byte[] decoded;
			try {
				decoded = java.util.Base64.getUrlDecoder().decode(zKey);
			} catch (IllegalArgumentException ex) {
				decoded = java.util.Base64.getDecoder().decode(zKey);
			}
			String json;
			try {
				json = new String(inflate(decoded), StandardCharsets.UTF_8);
			} catch (Exception ex2) {
				json = JTools.byteVectorToString(decoded);
			}
			return createRecFromJson(json, null);
		}
	}

	private boolean okFilter(JBaseRecord baseRec, String filter) throws Exception {
		if (!(baseRec instanceof JRecord))
			return true;
		return ((JRecord) baseRec).getFixedProp(filter).isKey();
	}

	private String serializeWinToJson(JBaseWin win) throws Exception {
		JSerializableBaseWin serializableWin = prepareSerializableWin(win);
		return objectMapper.writeValueAsString(serializableWin);
	}

	private String serializeRecToJson(JBaseRecord rec) throws Exception {
		JSerializableBaseWin serializableWin = prepareSerializableRec(rec, false);
		return objectMapper.writeValueAsString(serializableWin);
	}

	public String baseWinToJSON(JBaseWin win) throws Exception {
		String key = win.getUniqueId() + "_win";
		String cached = null;
		try {
			byte[] data = CacheProvider.get().getBytes(key);
			if (data != null)
				cached = JTools.byteVectorToString(data);
		} catch (Exception ignore) {
		}
		if (cached != null)
			return cached;
		String json = serializeWinToJson(win);
		String encoded = Base64.getEncoder().encodeToString(deflate(json.getBytes(StandardCharsets.UTF_8)));
		CacheProvider.get().putBytes(key, JTools.stringToByteArray(encoded), 0);
		return encoded;
	}

	public String baseRecToJSON(JBaseRecord rec) throws Exception {
		String key = rec.getUniqueId() + "_rec";
		String cached = null;
		try {
			byte[] data = CacheProvider.get().getBytes(key);
			if (data != null)
				cached = JTools.byteVectorToString(data);
		} catch (Exception ignore) {
		}
		if (cached != null)
			return cached;
		String json = serializeRecToJson(rec);
		String encoded = Base64.getEncoder().encodeToString(deflate(json.getBytes(StandardCharsets.UTF_8)));
		CacheProvider.get().putBytes(key, JTools.stringToByteArray(encoded), 0);
		return encoded;
	}

	private static String packJson(String json) throws Exception {
		byte[] raw = JTools.stringToByteArray(json);
		return b64url(deflate(raw));
	}

	private static String unpackToJson(String packed) throws Exception {
		byte[] raw = inflate(b64urlDecode(packed));
		return JTools.byteVectorToString(raw);
	}

	public String baseWinToPack(JBaseWin win) throws Exception {
		return packJson(serializeWinToJson(win));
	}

	public String baseRecToPack(JBaseRecord rec) throws Exception {
		return packJson(serializeRecToJson(rec));
	}

	public JBaseWin jsonToBaseWin(String json) throws Exception {
		return createWinFromJson(json, null);
	}

	public JBaseRecord jsonToBaseRec(String json) throws Exception {
		return createRecFromJson(json, null);
	}

	public Object deserializeObject(String json, Class<?> clazz) throws IOException {
		return objectMapper.readValue(json, clazz);
	}

	public JBaseWin createWinFromPack(String packed, String id) throws Exception {
		String json = unpackToJson(packed);
		return createWinFromJson(json, id);
	}

	public JBaseRecord createRecFromPack(String packed, String id) throws Exception {
		String json = unpackToJson(packed);
		return createRecFromJson(json, id);
	}

	public JBaseWin createWinFromJson(String json, String id) throws Exception {
		JSerializableBaseWin dto = (JSerializableBaseWin) deserializeObject(json, JSerializableBaseWin.class);
		String sUniqueId = (id != null) ? id : dto.uniqueId;
		JBaseWin actionOwner = getOrCreateWin(dto.cls, sUniqueId);
		actionOwner.setUniqueID(sUniqueId);
		actionOwner.SetVision(dto.vision);
		if (actionOwner.isWin())
			((JWin) actionOwner).getRecord().setDatosLeidos(dto.readed);
		assignFilters(dto, actionOwner.GetBaseDato());
		assignProps(dto, actionOwner.GetBaseDato());
		if (dto.drop != null)
			actionOwner.setDropListener(this.createWinFromPack(JTools.byteVectorToString(java.util.Base64.getDecoder().decode(dto.drop)), null));
		if (dto.dropControl != null)
			actionOwner.setDropControlIdListener((JAct) JWebActionFactory.getCurrentRequest().deserializeObject(JTools.byteVectorToString(java.util.Base64.getDecoder().decode(dto.dropControl))));
		return actionOwner;
	}

	public JBaseRecord createRecFromJson(String json, String id) throws Exception {
		JSerializableBaseWin dto = (JSerializableBaseWin) deserializeObject(json, JSerializableBaseWin.class);
		String sUniqueId = (id != null) ? id : dto.uniqueId;
		JBaseRecord actionOwner = getOrCreateRec(dto.cls, sUniqueId);
		actionOwner.SetVision(dto.vision);
		if (actionOwner instanceof JRecord)
			((JRecord) actionOwner).setDatosLeidos(dto.readed);
		if (actionOwner instanceof JRecords)
			((JRecords) actionOwner).setRecordRef(dto.recordClass);
		assignFilters(dto, actionOwner);
		assignProps(dto, actionOwner);
		asaignElements(dto, actionOwner);
		return actionOwner;
	}

	public JBaseWin createWin(String encoded, String id) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(encoded);
		String json;
		try {
			json = new String(inflate(decoded), StandardCharsets.UTF_8);
		} catch (IOException e) {
			json = new String(decoded, StandardCharsets.UTF_8);
		}
		return createWinFromJson(json, id);
	}

	public JBaseRecord createRec(String encoded, String id) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(encoded);
		String json;
		try {
			json = new String(inflate(decoded), StandardCharsets.UTF_8);
		} catch (IOException e) {
			json = new String(decoded, StandardCharsets.UTF_8);
		}
		return createRecFromJson(json, id);
	}

	private void asaignElements(JSerializableBaseWin serializableWin, JBaseRecord actionOwner) throws Exception {
		if (!(actionOwner instanceof JRecords) || serializableWin.elements == null)
			return;
		JRecords recs = (JRecords<JRecord>) actionOwner;
		recs.setStatic(true);
		for (String element : serializableWin.elements) {
			if (element.startsWith("obj_rec_")) {
				recs.getStaticItems().addElement(getRegisterObjectRecTemp(element.substring(8)));
			} else {
				Serializable obj = JWebActionFactory.getCurrentRequest().getRegisterObject(element);
				if (obj instanceof JBaseRecord) {
					recs.getStaticItems().addElement((JBaseRecord) obj);
				}
			}
		}

	}

	private void assignProps(JSerializableBaseWin serializableWin, JBaseRecord actionOwner) throws Exception {
		if (!(actionOwner instanceof JRecord) || serializableWin.properties == null)
			return;

		Class<?> currentClass = actionOwner.getClass();
		Field[] fields = currentClass.getDeclaredFields();

		for (Map.Entry<String, String> entry : serializableWin.properties.entrySet()) {
			String fieldKey = entry.getKey();
			String propValue = entry.getValue();

			if (fieldKey.startsWith("REC_")) {
				JObject<?> obj = ((JRecord) actionOwner).getProp(fieldKey.substring(4));
				if (propValue.startsWith("obj_rec_")) {
					obj.setValue(getRegisterObjectRecTemp(propValue.substring(8)));
				} else {
					obj.setValue((JBaseRecord) JWebActionFactory.getCurrentRequest().getRegisterObject(propValue));
				}
			} else if (fieldKey.startsWith("RECS_")) {
				JObject<?> obj = ((JRecord) actionOwner).getProp(fieldKey.substring(5));
				if (propValue.startsWith("obj_rec_")) {
					obj.setValue(getRegisterObjectRecTemp(propValue.substring(8)));
				} else {
					obj.setValue((JBaseRecord) JWebActionFactory.getCurrentRequest().getRegisterObject(propValue));
				}
			} else if (fieldKey.startsWith("UID_")) {
				JObject<?> obj = ((JRecord) actionOwner).getProp(fieldKey.substring(4));
				obj.setUniqueId(propValue);
			} else if (fieldKey.startsWith("PROP_")) {
				JObject<?> obj = ((JRecord) actionOwner).getProp(fieldKey.substring(5));
				obj.setValueFormUI(propValue);
			} else if (fieldKey.startsWith("OTH_")) {
				String fieldName = fieldKey.substring(4);
				try {
					Field field = currentClass.getDeclaredField(fieldName);
					field.setAccessible(true);
					Object value = convertToFieldType(field.getType(), propValue);
					field.set(actionOwner, value);
				} catch (NoSuchFieldException | IllegalAccessException e) {
					System.err.println("Error al asignar campo OTH: " + fieldName + " -> " + e.getMessage());
				}
			} else if (fieldKey.startsWith("SREC_")) {
				Field field = currentClass.getDeclaredField(fieldKey.substring(5));
				field.setAccessible(true);
				JBaseRecord obj;
				if (propValue.startsWith("obj_rec_")) {
					obj = getRegisterObjectRecTemp(propValue.substring(8));
				} else {
					obj = (JBaseRecord) JWebActionFactory.getCurrentRequest().getRegisterObject(propValue);
				}
				field.set(actionOwner, obj);
			} else if (fieldKey.startsWith("SRECS_")) {
				Field field = currentClass.getDeclaredField(fieldKey.substring(6));
				field.setAccessible(true);
				JBaseRecord obj;
				if (propValue.startsWith("obj_rec_")) {
					obj = getRegisterObjectRecTemp(propValue.substring(8));
				} else {
					obj = (JBaseRecord) JWebActionFactory.getCurrentRequest().getRegisterObject(propValue);
				}
				field.set(actionOwner, obj);
			} else if (fieldKey.startsWith("SER_")) {
				String fieldName = fieldKey.substring(4);
				try {
					Field field = currentClass.getDeclaredField(fieldName);
					field.setAccessible(true);

					String serializedData = propValue;
					byte[] decodedBytes = Base64.getDecoder().decode(serializedData);
					String jsonString = JTools.byteVectorToString(decodedBytes);
					Serializable obj = (Serializable) JWebActionFactory.getCurrentRequest().deserializeObject(jsonString);

					field.set(actionOwner, obj);
				} catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
					System.err.println("Error al asignar campo SER: " + fieldName + " -> " + e.getMessage());
				}
			}
		}
	}

	private Object convertToFieldType(Class<?> fieldType, String value) {
		if (fieldType == int.class || fieldType == Integer.class) {
			return Integer.parseInt(value);
		} else if (fieldType == long.class || fieldType == Long.class) {
			return Long.parseLong(value);
		} else if (fieldType == double.class || fieldType == Double.class) {
			return Double.parseDouble(value);
		} else if (fieldType == boolean.class || fieldType == Boolean.class) {
			return Boolean.parseBoolean(value);
		} else if (fieldType == String.class) {
			return value;
		}
		return value;
	}

	private void assignFilters(JSerializableBaseWin serializableWin, JBaseRecord actionOwner) throws Exception {
		if (serializableWin.filters == null)
			return;

		for (SerializableFilter filter : serializableWin.filters) {
			if (!okFilter(actionOwner, filter.field))
				continue;

			actionOwner.addFilter(filter.field, filter.value, filter.operator);
			actionOwner.forceFilterToData();
		}
	}

	private JBaseRecord getOrCreateRec(String className, String uniqueId) throws Exception {
		JBaseRecord rec = (factory != null) ? factory.getRememberRec(uniqueId) : null;
		if (rec != null)
			return rec;

		Class<?> clazz = Class.forName(className);
		JBaseRecord newRec = (JBaseRecord) clazz.newInstance();
		newRec.setUniqueId(uniqueId);
		if (factory != null)
			factory.rememberRec(uniqueId, newRec);

		return newRec;
	}

	private JBaseWin getOrCreateWin(String className, String uniqueId) throws Exception {
		JBaseWin win = (factory != null) ? factory.getRememberWin(uniqueId) : null;
		if (win != null)
			return win;

		Class<?> clazz = Class.forName(className);
		JBaseWin newWin = (JBaseWin) clazz.newInstance();
		newWin.setUniqueID(uniqueId);
		if (factory != null)
			factory.rememberWin(uniqueId, newWin);

		return newWin;
	}

	private JSerializableBaseWin prepareSerializableWin(JBaseWin win) throws Exception {
		JSerializableBaseWin serializableWin = prepareSerializableRec(win.GetBaseDato(), win.canConvertToURL());
		serializableWin.cls = win.getClass().getName();

		if (win.hasDropListener()) {
			serializableWin.drop = Base64.getEncoder().encodeToString(JTools.stringToByteArray(baseWinToPack(win.getDropListener())));
		}
		if (win.hasDropControlIdListener()) {
			serializableWin.dropControl = Base64.getEncoder().encodeToString(JTools.stringToByteArray(JWebActionFactory.getCurrentRequest().serializeObject(win.getDropControlIdListener())));
		}
		return serializableWin;
	}

	private JSerializableBaseWin prepareSerializableRec(JBaseRecord rec, boolean onlyProperties) throws Exception {
		JSerializableBaseWin serializableWin = new JSerializableBaseWin();
		serializableWin.cls = rec.getClass().getName();
		serializableWin.uniqueId = rec.getUniqueId();
		serializableWin.vision = rec.GetVision();
		serializableWin.readed = rec instanceof JRecord && ((JRecord) rec).wasDbRead() && rec.isStatic();

		serializableWin.filters = new ArrayList<SerializableFilter>();
		JList<RFilter> filters = rec.getFilters();
		if (filters != null && !filters.isEmpty()) {
			JIterator<RFilter> iter = filters.getIterator();
			RFilter filter;
			while (iter.hasMoreElements()) {
				filter = iter.nextElement();
				if (filter.isDynamic())
					continue;
				serializableWin.filters.add(new SerializableFilter(filter.getField(), filter.getOperator(), filter.getValue()));
			}
		}

		serializableWin.properties = new HashMap<String, String>();
		if (rec instanceof JRecord) {
			JMap<String, JObject<?>> props = ((JRecord) rec).getProperties();
			JIterator<String> it = props.getKeyIterator();
			while (it.hasMoreElements()) {
				String key = it.nextElement();
				JObject prop = props.getElement(key);
				if (prop.getUniqueId() != null)
					serializableWin.properties.put("UID_" + key, prop.getUniqueId());

				if (prop.isRecord() && prop.getInternalVal() != null) {
					serializableWin.properties.put("REC_" + key, JWebActionFactory.getCurrentRequest().registerRecObjectObj((JRecord) prop.getInternalVal()));
				}
				if (prop.isRecords() && prop.getInternalVal() != null) {
					serializableWin.properties.put("RECS_" + key, JWebActionFactory.getCurrentRequest().registerRecObjectObj((JRecords) prop.getInternalVal()));

				}
				if (!prop.hasValue())
					continue;
				if (serializableWin.readed)
					continue;
				serializableWin.properties.put("PROP_" + key, prop.toRawString());
			}
		}
		if (!onlyProperties) {
			Class<?> currentClass = rec.getClass();
			Field[] fields = currentClass.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);
				if (Modifier.isTransient(field.getModifiers()))
					continue;

				String fieldName = field.getName();
				Object fieldValue = field.get(rec);

				if (fieldValue == null)
					continue;

				if (fieldValue instanceof JObject) {
					continue;
				} else if (fieldValue instanceof JRecord) {
					String serialized = JWebActionFactory.getCurrentRequest().registerRecObjectObj((JRecord) fieldValue);
					serializableWin.properties.put("SREC_" + fieldName, serialized);
				} else if (fieldValue instanceof JRecords) {
					String serialized = JWebActionFactory.getCurrentRequest().registerRecObjectObj((JRecords) fieldValue);
					serializableWin.properties.put("SRECS_" + fieldName, serialized);
				} else if (fieldValue instanceof String || fieldValue.getClass().isPrimitive()) {
					serializableWin.properties.put("OTH_" + fieldName, fieldValue.toString());
				} else if (fieldValue instanceof Serializable) {
					Serializable serObj = (Serializable) fieldValue;
					serializableWin.properties.put("SER_" + fieldName, Base64.getEncoder().encodeToString(JTools.stringToByteArray(JWebActionFactory.getCurrentRequest().serializeObject(serObj))));
				} else {
					serializableWin.properties.put("OTH_" + fieldName, fieldValue.toString());
				}
			}
		}

		serializableWin.elements = null;
		if (rec instanceof JRecords && rec.isStatic()) {
			JRecords recs = (JRecords) rec;
			serializableWin.recordClass = recs.getBasedClass();
			serializableWin.elements = new ArrayList<String>();
			JIterator<JRecord> it = recs.getStaticIterator();
			while (it.hasMoreElements()) {
				JRecord localrec = it.nextElement();
				serializableWin.elements.add(JWebActionFactory.getCurrentRequest().registerRecObjectObj(localrec));

			}
		}

		return serializableWin;
	}

	private JBaseWin convertToJBaseWin(JSerializableBaseWin serializableWin) throws Exception {
		JBaseWin win = (JBaseWin) Class.forName(serializableWin.cls).newInstance();
		win.setUniqueID(serializableWin.uniqueId);
		win.SetVision(serializableWin.vision);

		if (win.isWin()) {
			((JWin) win).getRecord().setDatosLeidos(serializableWin.readed);
		}

		if (serializableWin.filters != null) {
			for (SerializableFilter filter : serializableWin.filters) {
				win.GetBaseDato().addFilter(filter.field, filter.value, filter.operator);
			}
		}

		if (serializableWin.properties != null) {
			for (Map.Entry<String, String> entry : serializableWin.properties.entrySet()) {
				JObject<?> obj = ((JWin) win).getRecord().getProp(entry.getKey());
				obj.setValue(entry.getValue());
			}
		}

		return win;
	}

	public static class JSerializableBaseWin {
		public boolean readed;
		public String cls;
		public String uniqueId;
		public String vision;
		public String drop;
		public String dropControl;
		public Class recordClass;

		public List<SerializableFilter> filters;
		public Map<String, String> properties;
		public List<String> elements;

		public JSerializableBaseWin() {
		}
	}

	public static class SerializableFilter {
		public String field;
		public String operator;
		public String value;

		public SerializableFilter() {
		}

		public SerializableFilter(String field, String operator, String value) {
			this.field = field;
			this.operator = operator;
			this.value = value;
		}
	}
}
