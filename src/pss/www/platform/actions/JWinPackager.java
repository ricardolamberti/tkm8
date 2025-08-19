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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;

import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.fields.JObject;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.www.platform.cache.CacheProvider;

public class JWinPackager {


        private final JWebWinFactory factory;
        private final ObjectMapper objectMapper = new ObjectMapper()
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

	public static String b64url(byte[] data) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
	}

	/**
	 * Decodes a Base64 string that may be encoded using either the URL-safe
	 * alphabet ("-" and "_") or the standard alphabet ("+" and "/").
	 *
	 * <p>
	 * Historically some parts of the system produced Base64 strings with the
	 * standard alphabet while consumers expected URL-safe encoding. This method
	 * first attempts to decode using the URL-safe decoder and, if the input
	 * contains characters outside that alphabet (e.g. "+"), it falls back to the
	 * standard decoder. This prevents {@link IllegalArgumentException} such as
	 * "Illegal base64 character 2b" when encountering legacy data.
	 * </p>
	 */
	public static byte[] b64urlDecode(String data) {
		try {
			return Base64.getUrlDecoder().decode(data);
		} catch (IllegalArgumentException ex) {
			// Fallback for legacy data encoded with the standard Base64 alphabet
			return Base64.getDecoder().decode(data);
		}
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
				throw ex2;
				// json = JTools.byteVectorToString(decoded);
			}
			return createWinFromJson(json, null);
		}
	}

	public JBaseRecord getRegisterObjectRecTemp(String zKey) throws Exception {
		try {
			String json = zKey;
			byte[] data = null;
			try {
				data = CacheProvider.get().getBytes(zKey);
			} catch (Exception ignore) {
			}
			if (data != null) {
				json = JTools.byteVectorToString(data);
			}

			// 2) Intentar como "pack" (url-safe b64 + deflate)
			return createRecFromPack(json, null);
		} catch (Exception e) {
			PssLogger.logError(e);
			throw e;
		}
	}

	private boolean okFilter(JBaseRecord baseRec, String filter) throws Exception {
		if (!(baseRec instanceof JRecord))
			return true;
		return ((JRecord) baseRec).getFixedProp(filter).isKey();
	}

	public String serializeWinToJson(JBaseWin win) throws Exception {
		JSerializableBase serializableWin = prepareSerializableWin(win);
		return objectMapper.writeValueAsString(serializableWin);
	}

	public String serializeRecToJson(JBaseRecord rec, Boolean onlyProperies) throws Exception {
		JSerializableBase serializableWin = prepareSerializableRec(rec, onlyProperies);
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

//	public String baseRecToJSON(JBaseRecord rec, String clase) throws Exception {
//		String key = rec.getUniqueId() + "_rec";
//
//		// 1) cache hit
//		String cached = null;
//		try {
//			byte[] data = CacheProvider.get().getBytes(key);
//			if (data != null)
//				cached = JTools.byteVectorToString(data);
//		} catch (Exception ignore) {
//		}
//		if (cached != null)
//			return cached;
//
//		// 2) Si ya estamos serializando este mismo rec, devolvemos SOLO referencia
//		// (evita recursión)
//		if (isSerializingKey(key)) {
//			return key; // referencia; se resolverá por cache en el receptor (ver
//									// getRegisterObjectRecTemp)
//		}
//
//		markSerializingKey(key);
//		try {
//			String json = serializeRecToJson(rec, clase);
//			String encoded = packJson(json);
//			CacheProvider.get().putBytes(key, JTools.stringToByteArray(encoded), 0);
//			return encoded;
//		} finally {
//			unmarkSerializingKey(key);
//		}
//	}

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
		return packJson(serializeRecToJson(rec,null));
	}

	public JBaseWin jsonToBaseWin(String json) throws Exception {
		return createWinFromJson(json, null);
	}

	public JBaseRecord jsonToBaseRec(String json) throws Exception {
		return createRecFromJson(json, null);
	}

	public Object deserializeObject(String json, Class<?> clazz) throws IOException {
		if (json.startsWith("­"))
			PssLogger.logInfo("log point");

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
		JSerializableBase dto = (JSerializableBase) deserializeObject(json, JSerializableBase.class);
		String sUniqueId = (id != null) ? id : dto.uniqueId;
		Serializable ser = JWebActionFactory.getCurrentRequest().getObjectCreated(sUniqueId);
		if (ser != null)
			return (JBaseWin) ser;


		JBaseWin actionOwner = getOrCreateWin(dto.cls, sUniqueId);
		actionOwner.SetBaseDato((JBaseRecord)JWebActionFactory.getCurrentRequest().getRegisterObject(dto.record));
		actionOwner.setUniqueID(sUniqueId);
		actionOwner.SetVision(dto.vision==null?"":dto.vision);
		actionOwner.setParent(dto.parent==null?null:(JBaseWin) JWebActionFactory.getCurrentRequest().getRegisterObject(dto.parent));
		if (actionOwner.isWin())
			((JWin) actionOwner).getRecord().setDatosLeidos(dto.readed);
		if (dto.drop != null)
			actionOwner.setDropListener((JBaseWin)JWebActionFactory.getCurrentRequest().getRegisterObject(dto.drop));
		if (dto.dropControl != null)
			actionOwner.setDropControlIdListener(JAct.deserialize(dto.dropControl));
		return actionOwner;
	}

	public JBaseRecord createRecFromJson(String json, String id) throws Exception {
		JSerializableBase dto = (JSerializableBase) deserializeObject(json, JSerializableBase.class);
		String sUniqueId = (id != null) ? id : dto.uniqueId;
	if (dto.uniqueId.indexOf("rec_9a5ca46d-9ea6-47a1-a477-6bb8c5fe694c") != -1)
			PssLogger.logInfo("log point");
		JBaseRecord rec = (JBaseRecord) JWebActionFactory.getCurrentRequest().getObjectCreated(sUniqueId);
		if (rec != null)
			return rec;

	
		JBaseRecord actionOwner = getOrCreateRec(dto.cls, sUniqueId);
		actionOwner.SetVision(dto.vision==null?"":dto.vision);
		actionOwner.setParent(dto.parent==null?null:(JBaseRecord) JWebActionFactory.getCurrentRequest().getRegisterObject(dto.parent));
		if (actionOwner instanceof JRecord) {
			((JRecord) actionOwner).setDatosLeidos(dto.readed);
			((JRecord) actionOwner).setRowId(dto.rowid);
			((JRecord) actionOwner).setExtraData(dto.extraData==null?null: JCollectionFactory.createMap(dto.extraData));
			
		}
		if (actionOwner instanceof JRecords)
			((JRecords) actionOwner).setRecordRef(dto.recordClass);
		
		assignFilters(dto, actionOwner);
		assignProps(dto, actionOwner);
		assignElements(dto, actionOwner);
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

	private void assignElements(JSerializableBase serializableWin, JBaseRecord actionOwner) throws Exception {
		if (!(actionOwner instanceof JRecords) || serializableWin.elements == null)
			return;
		JRecords recs = (JRecords<JRecord>) actionOwner;
		recs.setStatic(true);
		for (String element : serializableWin.elements) {
	
				Serializable obj = JWebActionFactory.getCurrentRequest().getRegisterObject(element);
				if (obj instanceof JBaseRecord) {
					recs.getStaticItems().addElement((JBaseRecord) obj);
				
			}
		}

	}

	private static Field findField(Class<?> type, String name) {
		Class<?> c = type;
		while (c != null && c != Object.class) {
			try {
				Field f = c.getDeclaredField(name);
				f.setAccessible(true);
				return f;
			} catch (NoSuchFieldException ignore) {
				c = c.getSuperclass();
			}
		}
		return null;
	}

	private void assignProps(JSerializableBase serializableWin, JBaseRecord actionOwner) throws Exception {
		if (serializableWin.properties == null)
			return;

		final Class<?> rootClass = actionOwner.getClass();

		for (Map.Entry<String, String> entry : serializableWin.properties.entrySet()) {
			final String fieldKey = entry.getKey();
			final String propValue = entry.getValue();
			if (fieldKey.indexOf("allCampos")!=-1)
				PssLogger.logError("log point");
			
			// ------- Props del modelo (JObject) -------
			if (fieldKey.startsWith("REC_")) {
				JObject<?> obj = ((JRecord) actionOwner).getProp(fieldKey.substring(4));
				obj.setValue((JBaseRecord) JWebActionFactory.getCurrentRequest().getRegisterObject(propValue));
				continue;
			}

			if (fieldKey.startsWith("RECS_")) {
				JObject<?> obj = ((JRecord) actionOwner).getProp(fieldKey.substring(5));
				obj.setValue((JBaseRecord) JWebActionFactory.getCurrentRequest().getRegisterObject(propValue));
				continue;
			}

			if (fieldKey.startsWith("UID_")) {
				JObject<?> obj = ((JRecord) actionOwner).getProp(fieldKey.substring(4));
				obj.setUniqueId(propValue);
				continue;
			}

			if (fieldKey.startsWith("PROP_")) {
				JObject<?> obj = ((JRecord) actionOwner).getProp(fieldKey.substring(5));
				obj.setValueFormUI(propValue);
				continue;
			}

			// ------- Campos simples/record/records del objeto (incluye padres) -------
			if (fieldKey.startsWith("OTH_")) {
				final String fieldName = fieldKey.substring(4);
				Field f = findField(rootClass, fieldName);
				if (f == null) {
					PssLogger.logInfo("assignProps OTH_: campo no encontrado: " + fieldName + " en " + rootClass.getName());
					continue;
				}
				try {
					Object value = convertToFieldType(f.getType(), propValue);
					f.set(actionOwner, value);
				} catch (Exception e) {
					PssLogger.logError(e, "assignProps OTH_: error seteando " + fieldName);
				}
				continue;
			}

			if (fieldKey.startsWith("SREC_")) {
				final String fieldName = fieldKey.substring(5);
				Field f = findField(rootClass, fieldName);
				if (f == null) {
					PssLogger.logInfo("assignProps SREC_: campo no encontrado: " + fieldName + " en " + rootClass.getName());
					continue;
				}
				try {
					JBaseRecord obj;
					obj = (JBaseRecord) JWebActionFactory.getCurrentRequest().getRegisterObject(propValue);
					f.set(actionOwner, obj);
				} catch (Exception e) {
					PssLogger.logError(e, "assignProps SREC_: error seteando " + fieldName);
				}
				continue;
			}

			if (fieldKey.startsWith("SRECS_")) {
				final String fieldName = fieldKey.substring(6);
				Field f = findField(rootClass, fieldName);
				if (f == null) {
					PssLogger.logInfo("assignProps SRECS_: campo no encontrado: " + fieldName + " en " + rootClass.getName());
					continue;
				}
				try {
					JBaseRecord obj;
					obj = (JBaseRecord) JWebActionFactory.getCurrentRequest().getRegisterObject(propValue);
					
					f.set(actionOwner, obj);
				} catch (Exception e) {
					PssLogger.logError(e, "assignProps SRECS_: error seteando " + fieldName);
				}
				continue;
			}

			if (fieldKey.startsWith("SER_")) {
				final String fieldName = fieldKey.substring(4);
				Field f = findField(rootClass, fieldName);
				if (f == null) {
					PssLogger.logInfo("assignProps SER_: campo no encontrado: " + fieldName + " en " + rootClass.getName());
					continue;
				}
				try {
					// Alineado con la escritura: Base64 de BYTES, no string re-Base64
					byte[] decoded = Base64.getDecoder().decode(propValue);
					Serializable obj = JWebActionFactory.getCurrentRequest().deserializeObjectFromBytes(decoded);
					f.set(actionOwner, obj);
				} catch (Exception e) {
					PssLogger.logError(e, "assignProps SER_: error seteando " + fieldName);
				}
				continue;
			}

			// Claves no reconocidas se ignoran de forma segura.
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
		} else if (fieldType == boolean.class || fieldType == Byte.class) {
			return Byte.parseByte(value);
		} else if (fieldType == String.class) {
			return value;
		}
		return value;
	}

	private void assignFilters(JSerializableBase serializableWin, JBaseRecord actionOwner) throws Exception {
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

		Class<?> clazz = Class.forName(className);
		JBaseRecord newRec = (JBaseRecord) clazz.newInstance();
		newRec.setUniqueId(uniqueId);
		JWebActionFactory.getCurrentRequest().addObjectCreated(uniqueId, newRec);

		return newRec;
	}

	private JBaseWin getOrCreateWin(String className, String uniqueId) throws Exception {

		Class<?> clazz = Class.forName(className);
		JBaseWin newWin = (JBaseWin) clazz.newInstance();
		newWin.setUniqueID(uniqueId);
		JWebActionFactory.getCurrentRequest().addObjectCreated(uniqueId, newWin);

		return newWin;
	}

	private JSerializableBase prepareSerializableWin(JBaseWin win) throws Exception {
		JSerializableBase serializableWin = new JSerializableBase();
		serializableWin.cls = win.getClass().getName();
		serializableWin.uniqueId = win.getUniqueId();
		serializableWin.record = JWebActionFactory.getCurrentRequest().registerRecObjectObj( win.GetBaseDato(), win.canConvertToURL());
		serializableWin.parent = win.getParent()==null?null:JWebActionFactory.getCurrentRequest().registerWinObjectObj( win.getParent());
		if (serializableWin.cls.indexOf("Campos") != -1)
			PssLogger.logInfo("log point");

		if (win.hasDropListener()) {
			serializableWin.drop = JWebActionFactory.getCurrentRequest().registerWinObjectObj(win.getDropListener());
		}
		if (win.hasDropControlIdListener()) {
			serializableWin.dropControl = win.getDropControlIdListener().serialize();
		}
		return serializableWin;
	}

	// onlyProperties como parametro es por compatibilidad, asi puedo decidirlo siguiendo el canConvertToURL de jwin si lo hubiera
	private JSerializableBase prepareSerializableRec(JBaseRecord rec, Boolean onlyProperties) throws Exception {
		JSerializableBase serializableRec = new JSerializableBase();
		serializableRec.cls =  rec.getClass().getName();
		boolean serializeOnlyProperties = rec.serializeOnlyProperties() && (onlyProperties!=null?onlyProperties.booleanValue():true);
		
		if (rec.getUniqueId().indexOf("rec_9a5ca46d-9ea6-47a1-a477-6bb8c5fe694c") != -1)
			PssLogger.logInfo("log point");

		if (serializableRec.cls.indexOf("allCampos") != -1)
			PssLogger.logInfo("log point");
		if (serializableRec.cls.indexOf("CustomList") != -1)
			PssLogger.logInfo("log point");
		serializableRec.uniqueId = rec.getUniqueId();
		serializableRec.vision = rec.GetVision();
		serializableRec.readed = rec instanceof JRecord && ((JRecord) rec).wasDbRead() && rec.isStatic();
		serializableRec.recordClass = rec instanceof JRecords ? ((JRecords)rec).getBasedClass():null;
		serializableRec.rowid = rec instanceof JRecord ? ((JRecord)rec).getRowId():null;
		serializableRec.extraData = rec instanceof JRecord  && ((JRecord)rec).getExtraData()!=null? ((JRecord)rec).getExtraData().toMap():null;
		serializableRec.parent = rec.getParent()==null?null: JWebActionFactory.getCurrentRequest().registerRecObjectObj( rec.getParent(), null);
		serializableRec.filters = new ArrayList<SerializableFilter>();
		JList<RFilter> filters = rec.getFilters();
		if (filters != null && !filters.isEmpty()) {
			JIterator<RFilter> iter = filters.getIterator();
			RFilter filter;
			while (iter.hasMoreElements()) {
				filter = iter.nextElement();
				if (filter.isDynamic())
					continue;
				serializableRec.filters.add(new SerializableFilter(filter.getField(), filter.getOperator(), filter.getValue()));
			}
		}

		serializableRec.properties = new HashMap<String, String>();
		if (rec instanceof JRecord) {
			JMap<String, JObject<?>> props = ((JRecord) rec).getProperties();
			if (props != null) {
				JIterator<String> it = props.getKeyIterator();
				while (it.hasMoreElements()) {
					String key = it.nextElement();
					JObject prop = props.getElement(key);
					if (prop.getUniqueId() != null)
						serializableRec.properties.put("UID_" + key, prop.getUniqueId());

					if (prop.isRecord()) {
						if (prop.getInternalVal() != null) {
							JRecord recAux = (JRecord) prop.getInternalVal();
							serializableRec.properties.put("REC_" + key, JWebActionFactory.getCurrentRequest().registerRecObjectObj(recAux,null));					
						}
						continue;
					}
					if (prop.isRecords()) {
						if (prop.getInternalVal() != null) {
							serializableRec.properties.put("RECS_" + key, JWebActionFactory.getCurrentRequest().registerRecObjectObj((JRecords) prop.getInternalVal(),null));
							
						}
						continue;
					}
					if (!prop.hasValue())
						continue;
					if (serializableRec.readed)
						continue;
					serializableRec.properties.put("PROP_" + key, prop.toRawString());
				}
			}

		}
		if (!serializeOnlyProperties) {
			// Visitamos clase concreta y superclases hasta Object
			Class<?> c = rec.getClass();
			java.util.Set<String> seen = new java.util.HashSet<>(); // evita duplicados (prefiere campos de la clase hija)
			while (c != null && c != Object.class && c != JRecord.class && c!= JWin.class  && c!= JWins.class  && c!= JRecords.class) {
				Field[] fields = c.getDeclaredFields();
				for (Field field : fields) {
					// filtros: no queremos sintéticos, outer refs, serialVersionUID, static,
					// transient, final
					if (field.isSynthetic())
						continue;
					String fn = field.getName();
					if (fn.startsWith("this$"))
						continue;
					if ("serialVersionUID".equals(fn))
						continue;

					int mods = field.getModifiers();
					if (Modifier.isStatic(mods))
						continue;
					if (Modifier.isTransient(mods))
						continue;
					if (Modifier.isFinal(mods))
						continue;

					// si ya lo vimos en la clase hija, salteamos el del padre
					if (!seen.add(fn))
						continue;
					if (fn.indexOf("allCampos")!=-1)
						PssLogger.logError("log point");
					

					field.setAccessible(true);
					Object val = field.get(rec);
					if (val == null)
						continue;

					if (val instanceof JObject) {
						// se ignora: ya lo tratás en la sección de properties
						continue;
					} else if (val instanceof JRecords) {
						String serialized = JWebActionFactory.getCurrentRequest().registerRecObjectObj((JRecords) val,null);
						serializableRec.properties.put("SRECS_" + fn, serialized);
					} else if (val instanceof JRecord) {
						String serialized = JWebActionFactory.getCurrentRequest().registerRecObjectObj((JRecord) val,null);
						serializableRec.properties.put("SREC_" + fn, serialized);
					} else if (field.getType().isPrimitive() || val instanceof String) {
						serializableRec.properties.put("OTH_" + fn, String.valueOf(val));
					} else if (val instanceof Serializable) {
						// serializamos a bytes y luego Base64 (sin doble Base64)
						byte[] bytes = JWebActionFactory.getCurrentRequest().serializeObjectToBytes((Serializable) val);
						String b64 = Base64.getEncoder().encodeToString(bytes);
						serializableRec.properties.put("SER_" + fn, b64);
					} else {
						// fallback seguro
						serializableRec.properties.put("OTH_" + fn, String.valueOf(val));
					}
				}
				c = c.getSuperclass();
			}
		}

		serializableRec.elements = null;
		if (rec instanceof JRecords && rec.isStatic()) {
			JRecords recs = (JRecords) rec;
			serializableRec.recordClass = recs.getBasedClass();
			serializableRec.elements = new ArrayList<String>();
			JIterator<JRecord> it = recs.getStaticIterator();
			while (it.hasMoreElements()) {
				JRecord localrec = it.nextElement();
				serializableRec.elements.add(JWebActionFactory.getCurrentRequest().registerRecObjectObj(localrec,false));

			}
		}

		return serializableRec;
	}

//	private JBaseWin convertToJBaseWin(JSerializableBase serializableWin) throws Exception {
//		JBaseWin win = (JBaseWin) Class.forName(serializableWin.cls).newInstance();
//		win.setUniqueID(serializableWin.uniqueId);
//		win.SetVision(serializableWin.vision);
//		win.setParent(serializableWin.parent);
//
//		if (win.isWin()) {
//			((JWin) win).getRecord().setDatosLeidos(serializableWin.readed);
//		}
//
//		if (serializableWin.filters != null) {
//			for (SerializableFilter filter : serializableWin.filters) {
//				win.GetBaseDato().addFilter(filter.field, filter.value, filter.operator);
//			}
//		}
//
//		if (serializableWin.properties != null) {
//			for (Map.Entry<String, String> entry : serializableWin.properties.entrySet()) {
//				JObject<?> obj = ((JWin) win).getRecord().getProp(entry.getKey());
//				obj.setValue(entry.getValue());
//			}
//		}
//
//		return win;
//	}

	public static class JSerializableBase {
		public boolean readed;
		public String cls;
		public String uniqueId;
		public String vision;
		public String drop;
		public String dropControl;
		public Class recordClass;
		public String record;
		public String rowid;
		public String parent;
		
		public List<SerializableFilter> filters;
		public Map<String, String> properties;
		public Map<String, String> extraData;
		public List<String> elements;

		public JSerializableBase() {
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
