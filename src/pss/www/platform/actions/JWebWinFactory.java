package pss.www.platform.actions;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JObject;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFieldSwapWins;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataField;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.platform.applications.JWebHistoryManager;
import pss.www.platform.cache.CacheProvider;
import pss.www.platform.cache.DistCache;

public class JWebWinFactory {

	public JWebWinFactory(IControlToBD aControToBD) {
		this.controToBD = aControToBD;
	}

	IControlToBD controToBD;

	TreeMap<String, SoftReference<JBaseWin>> winRefference = new TreeMap<String, SoftReference<JBaseWin>>();
	TreeMap<String, SoftReference<JBaseRecord>> recRefference = new TreeMap<String, SoftReference<JBaseRecord>>();

	private final JWinPackager packager = new JWinPackager(this);

	public JWinPackager getPackager() {
		return packager;
	}

	public TreeMap<String, SoftReference<JBaseWin>> getWinRefference() {
		return winRefference;
	}

	public void forget() {
		winRefference = new TreeMap<String, SoftReference<JBaseWin>>();
	}

	public TreeMap<String, SoftReference<JBaseRecord>> getRecRefference() {
		return recRefference;
	}

	public void setRecRefference(TreeMap<String, SoftReference<JBaseRecord>> recRefference) {
		this.recRefference = recRefference;
	}

	public void cleanRefference() {
		Iterator<String> it = winRefference.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			SoftReference<JBaseWin> swin = winRefference.get(key);
			if (swin != null) {
				if (swin.get() != null)
					continue;
			}
			it.remove();
		}
	}

	public void fillHistory() throws Exception {
		JWebHistoryManager hm = JWebActionFactory.getCurrentRequest().getHistoryManager();
		if (hm.getActionHistory().size() == 0)
			return;
		for (int i = 0; i < hm.getActionHistory().size(); i++) {
			JHistory hist = hm.getActionHistory().getElementAt(i);
			Iterator<String> it = hist.getProviders().keySet().iterator();
			while (it.hasNext()) {
				String shp = it.next();
				JHistoryProvider hp = hist.getProviders().get(shp);
				rememberAct(hp.getAction());
			}
		}

	}

	public void buildWinsFromField(JBaseWin baseWin, String fields) throws Exception {
		JWins wins = (JWins) baseWin;
		wins.SetEstatico(true);
		String objectList = fields;
		StringTokenizer st = new StringTokenizer(objectList, ";,");
		while (st.hasMoreTokens()) {
			String idObject = st.nextToken();
			JWin win = null;
			Object obj = JWebActionFactory.getCurrentRequest().getRegisterObject(idObject);
			if (obj == null)
				continue; // HGK en seleccion multimple vienen rows que son sub-rows y da error, corregir
									// en el .js para que no vengan
			if (obj instanceof String) {
				String resolveString = (String) obj;
				win = (JWin) getBaseWinFromBundle(resolveString, true, null);
			} else {
				if (!obj.getClass().isAssignableFrom(wins.GetClassWin()))
					continue; // lo mismo que arriba, me aseguro que no se meta un row que no va, truchex!!!
				win = (JWin) obj;
			}

//			wins.AddItem(win, 0);
			wins.addRecord(win);
		}
		return;
	}

	public JBaseWin getBaseWinFromBundle(String zWinBundle) throws Exception {
		return getBaseWinFromBundle(zWinBundle, true, null);
	}

	public JBaseWin getBaseWinFromBundle(String zWinBundle, boolean loadData, String id) throws Exception {
		JBaseWin actionOwner = getPackager().createWin(zWinBundle, id);
		if (loadData)
			this.loadData(actionOwner, true, JWebActionFactory.getCurrentRequest().hasTableProvider() ? JWebActionFactory.getCurrentRequest().getTableProvider() : null, null);
		return actionOwner;
	}

	public JBaseRecord getBaseRecFromBundle(String zWinBundle, String id) throws Exception {
		JBaseRecord actionOwner = getPackager().createRec(zWinBundle, id);
		return actionOwner;
	}

	public String loadWinBundle() throws Exception {
		return JWebActionFactory.getCurrentRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "act_owner");
	}

	public JWebActionData loadWinBundle(String data) throws Exception {
		JWebActionData winBundle = JWebActionFactory.getCurrentRequest().getData(data);
		if (winBundle == null)
			return null;
		if (winBundle.isNull()) {
			// winBundle=null;
			return null;
		}
		if (winBundle.isEmpty()) {
			// winBundle=null;
			return null;
		}
		return winBundle;
	}

	public JBaseWin processObjectRegisteredDest(boolean loadData) throws Exception {
		return processObjectRegisteredById(JWebActionFactory.getCurrentRequest().getPssObjectOwnerDest(), loadData);
	}

	public JBaseWin processObjectRegistered(boolean loadData) throws Exception {
		return processObjectRegisteredById(JWebActionFactory.getCurrentRequest().getPssObjectOwner(), loadData);
	}

	public JBaseWin processObjectRegisteredSelect(boolean loadData) throws Exception {
		return processObjectRegisteredById(JWebActionFactory.getCurrentRequest().getPssObjectSelect(), loadData);
	}

	public JBaseWin processObjectRegisteredById(String id, boolean loadData) throws Exception {
		Object obj = JWebActionFactory.getCurrentRequest().getRegisterObject(id);
//		PssLogger.logDebug("|------------------------------------------------> TRY FIND "+id);
		if (obj instanceof JBaseWin) {
//			PssLogger.logDebug("|------------------------------------------------> FIND WIN "+id);
			JBaseWin actionOwner = (JBaseWin) obj;
			JBaseWin contextAtionOwner = actionOwner;
			if (JWebActionFactory.getCurrentRequest().getPssObjectOwnerContext() != null)
				contextAtionOwner = (JBaseWin) JWebActionFactory.getCurrentRequest().getRegisterObject(JWebActionFactory.getCurrentRequest().getPssObjectOwnerContext());
			if (loadData)
				this.loadData(actionOwner, true, JWebActionFactory.getCurrentRequest().hasTableProvider() ? JWebActionFactory.getCurrentRequest().getTableProvider() : null, contextAtionOwner);
			return actionOwner;
		}
		if (obj instanceof String) {
//			PssLogger.logDebug("|------------------------------------------------> FIND STRING "+id);
			return this.getBaseWinFromBundle((String) obj, true, id);
		}
//		PssLogger.logDebug("|------------------------------------------------> NO FIND STRING "+id);

		return null;
	}

	public JBaseWin processObjectRegisteredByIdForCard(String id) throws Exception {
		Object obj = JWebActionFactory.getCurrentRequest().getRegisterObject(id);
		if (obj instanceof JBaseWin) {
			JBaseWin actionOwner = (JBaseWin) obj;
			return actionOwner;
		}
		if (obj instanceof String) {
			return this.getBaseWinFromBundle((String) obj, false, id);
		}

		return null;
	}

	public void rememberAct(BizAction action) throws Exception {
		JBaseWin owner = action.getObjOwner();
		if (owner != null)
			rememberWin(owner.getUniqueId(), owner);

		if (action.hasSubmit()) {
			JAct act = action.getObjSubmit();
			if (act.GetBWin() != null)
				rememberWin(act.GetBWin().getUniqueId(), act.GetBWin());
			if (act.hasResult())
				rememberWin(act.getResult().getUniqueId(), act.getResult());
		}
	}

	public void rememberWin(String uniqueId, JBaseWin win) throws Exception {
		if (!winRefference.containsKey(uniqueId)) {
			winRefference.put(uniqueId, new SoftReference<JBaseWin>(win));
		}
	}

	public void rememberRec(String uniqueId, JBaseRecord rec) throws Exception {
		if (!winRefference.containsKey(uniqueId)) {
			recRefference.put(uniqueId, new SoftReference<JBaseRecord>(rec));
		}
	}

	public JBaseWin getRememberWin(String uniqueId) throws Exception {
		if (winRefference.containsKey(uniqueId)) {
			SoftReference<JBaseWin> swin = winRefference.get(uniqueId);
			if (swin == null)
				return null;
			JBaseWin win = swin.get();
			if (win == null)
				return null;
			return win;
		}
		return null;
	}

	public JBaseRecord getRememberRec(String uniqueId) throws Exception {
		if (recRefference.containsKey(uniqueId)) {
			SoftReference<JBaseRecord> srec = recRefference.get(uniqueId);
			if (srec == null)
				return null;
			JBaseRecord rec = srec.get();
			if (rec == null)
				return null;
			return rec;
		}
		return null;
	}

	private JWebActionData getExtraFormData() throws Exception {
		return JWebActionFactory.getCurrentRequest().getData("extra_form_data");
	}

	public boolean isEmbedded() throws Exception {
		if (getExtraFormData().get("embedded") == null)
			return false;
		return getExtraFormData().get("embedded").equalsIgnoreCase("true");
	}

	private boolean isParseable(JBaseWin zActionOwner) throws Exception {
		if (zActionOwner instanceof JWins) { // deberia tender a desaparecer
//		addWinListFilters(zActionOwner, getFilters());
			return false;
		}
		return true;
	}

	private JWin loadWin(JBaseWin zActionOwner, String tableProvider, boolean force) throws Exception {
		JWin win = (JWin) zActionOwner;
		win.setEmbedded(this.isEmbedded());
		JWebActionData data = JWebActionFactory.getCurrentRequest().getFormData(tableProvider);

		if (data.isNull() && force) {
			if (win.isReadeable())
				win.getRecord().read();
		} else
			this.controlsToBD(data, win.getRecord());
		return win;
	}

	private JRecords getTable(JWin win, String providerRow, JRecords table) throws Exception {
		if (table == null) {
			JObjBDs objs = ((JObjBDs) win.getRecord().getProp(providerRow, false));
			if (objs == null)
				return null;
			if (win.canConvertToURL())
				table = objs.getRawValue();

			if (table == null) {
				table = objs.getValue();
				// table.clearStaticItems();
			}
		}
		if (table != null && !table.isStatic())
			table.setStatic(true);
		return table;
	}

	private JWin getCard(JWin win, String providerRow, JWin table) throws Exception {
		Object obj = processObjectRegisteredByIdForCard(providerRow);
		return ((JWin) obj);
	}

	private JRecord addRowToTable(JWin win, String providerRow, JRecords table, boolean estatica) throws Exception {
		JRecord rec = (JRecord) win.getRecord().getFixedProp(providerRow).getClase().newInstance();
		rec.setStatic(estatica);
		table.getStaticItems().addElement(rec);
		return rec;
	}

	private boolean addRowNoData(long idRow, int idx, JWin win, String providerRow, JRecords table) throws Exception {
		if (idRow > idx) { // cuando viene un form lov solo viene la fila que interesa
			addRowToTable(win, providerRow, table, false);
			return true;
		}
		return false;
	}

	private void addRowWithData(int idx, JWin win, String providerRow, JRecords table, JWebActionData dataRow) throws Exception {
		JRecord rec;
		if ((rec = table.findRowId(idx)) == null) {
			rec = addRowToTable(win, providerRow, table, true);
			rec.setRowId("" + idx);
		}
		this.controlsToBD(dataRow, rec);

	}

	/**
	 * Carga la información enviada por la request dentro de la ventana.
	 * <p>
	 * Durante la carga se recorren todos los parámetros recibidos y se interpretan
	 * según su prefijo. Los formatos reconocidos son:
	 * </p>
	 * <ul>
	 * <li><b>Cards</b>: el nombre del parámetro contiene la cadena
	 * <code>"_card_"</code>. El valor asociado corresponde a los campos de una
	 * ventana anidada y se procesa mediante {@link #processCardElement}.</li>
	 * <li><b>Tablas</b>: el nombre comienza con <code>"dgt_"</code>. El valor
	 * utiliza el formato
	 * 
	 * <pre>
	 * [tabla|campo,indice:nombreCampo,...]{indice:valor,...}{...}
	 * </pre>
	 * 
	 * donde la sección entre corchetes define el nombre de la tabla destino y el
	 * mapa de índices a nombres de campo, mientras que cada grupo entre llaves
	 * representa una fila.</li>
	 * <li><b>Tablas extendidas</b>: contienen la cadena <code>"_row_"</code> y
	 * representan filas enviadas como datos independientes. Se interpretan con
	 * {@link #processTableExtendElement}.</li>
	 * </ul>
	 * Los valores de cada campo son decodificados posteriormente por
	 * {@link #controlsToBD} para actualizar las propiedades del {@link JRecord}
	 * correspondiente.
	 *
	 * @param zActionOwner   ventana cuyo registro se debe popular
	 * @param force          indica si se fuerza la carga aunque la ventana no sea
	 *                       parseable
	 * @param zTableProvider nombre opcional del provider de tablas
	 * @param zContextOwner  ventana de contexto a utilizar en lugar de
	 *                       {@code zActionOwner}
	 * @throws Exception si se produce algún problema durante el parseo
	 */
	public void loadData(JBaseWin zActionOwner, boolean force, String zTableProvider, JBaseWin zContextOwner) throws Exception {
		String tableProvider = zTableProvider;
		boolean total = !JWebActionFactory.getCurrentRequest().isParcial();
//		if (tableProvider!=null) tableProvider = zTableProvider;
//		if (JWebActionFactory.getCurrentRequest().hasTableProvider()) tableProvider=JWebActionFactory.getCurrentRequest().getTableProvider();
		zActionOwner.GetBaseDato().clearDynamicFilters();

		if (!isParseable(zActionOwner))
			return;

		JWin win = this.loadWin(zActionOwner, tableProvider, force);

		if (zContextOwner != null)
			win = (JWin) zContextOwner;

		// buscador de multiples provider, para las filas de las grillas
		JList<String> finder = JCollectionFactory.createList();
		JIterator<String> dataArg = JWebActionFactory.getCurrentRequest().getAllArgumentNames();

		while (dataArg.hasMoreElements()) {
			String ds = dataArg.nextElement();
			if (processCardElement(ds, total, finder, win))
				continue;
			if (processTableElement(ds, total, finder, win))
				continue;
			if (processTableExtendElement(ds, total, finder, win))
				continue;

		}

	}

	public void processSwapElement(JAct submit, String zTableProvider) throws Exception {
		if (!submit.hasSelected())
			return;
		JActFieldSwapWins act = (JActFieldSwapWins) submit;
		String data = JWebActionFactory.getCurrentRequest().getArgument("dgf_" + zTableProvider + "_fd-swap");
		if (data == null)
			return;
		act.setSelecteds(act.getOptions().createClone());
		act.getSelecteds().SetEstatico(true);
		act.getSelecteds().setDropListener(submit.getResult().getDropListener());
		buildWinsFromField(act.getSelecteds(), data);
	}

	/**
	 * Procesa los datos correspondientes a un "card" dentro del request. Un card
	 * representa una ventana embebida dentro de otra.
	 * <p>
	 * El nombre del parámetro debe contener la cadena <code>"_card_"</code> e
	 * incluye información del provider y del identificador del objeto card. En
	 * caso de que el nombre también contenga <code>"_row_"</code> se trata de una
	 * tabla cuyos datos se parsean delegando en {@link #processTableElement}.
	 * </p>
	 *
	 * @param ds     nombre del parámetro recibido
	 * @param total  indica si la carga es completa o parcial
	 * @param finder lista que evita procesar el mismo provider más de una vez
	 * @param win    ventana base donde se inyectará la información del card
	 * @return {@code true} si el parámetro fue reconocido y procesado
	 * @throws Exception en caso de errores durante el parseo
	 */
	public boolean processCardElement(String ds, boolean total, JList<String> finder, JWin win) throws Exception {
		int p = ds.indexOf("-");
		if (p == -1)
			return false;
		if (finder.containsElement(ds.substring(0, p)))
			return false;
		int p2 = ds.indexOf("_card_");
		if (p2 == -1)
			return false;
		finder.addElement(ds.substring(0, p));

		JWin card = null;

		int p3 = ds.indexOf("_row_");
		if (p3 != -1) { // tabla
			if (finder.containsElement(ds.substring(0, p3)))
				return false;
			String providerCard = ds.substring(4, p - 3);
			String providerCardObj = ds.substring(p2 + 1, ds.lastIndexOf("_", p3 - 3));

			JWebActionData dataCard = JWebActionFactory.getCurrentRequest().getFormData(providerCard);
			card = getCard(win, providerCardObj, card);
			if (card == null)
				return false;

			processTableElement(ds, total, null, card);
			finder.addElement(ds.substring(0, p3));
			return true;
		}
		String providerCard = ds.substring(4, p - 3);
		String providerCardObj = ds.substring(p2 + 1, p - 3);

		JWebActionData dataCard = JWebActionFactory.getCurrentRequest().getFormData(providerCard);
		card = getCard(win, providerCardObj, card);
		if (card == null || dataCard.isEmpty())
			return false;
		this.controlsToBD(dataCard, card.getRecord());

		return true;
	}

	/**
	 * Procesa la serialización de una tabla enviada en un único parámetro.
	 * <p>
	 * El nombre del parámetro debe comenzar con el prefijo <code>"dgt_"</code>. El
	 * valor asociado utiliza la sintaxis:
	 * </p>
	 *
	 * <pre>
	 * [tabla|campo,indice:nombreCampo,...]{indice:valor,...}{...}
	 * </pre>
	 *
	 * donde:
	 * <ul>
	 * <li>Entre corchetes se especifica el nombre de la propiedad tabla del
	 * registro y un campo utilizado para asociar tarjetas.</li>
	 * <li>Los pares <code>indice:nombreCampo</code> definen el orden de los campos
	 * dentro de cada fila.</li>
	 * <li>Cada grupo entre llaves representa una fila utilizando los índices
	 * definidos.</li>
	 * </ul>
	 *
	 * @param ds     nombre del parámetro recibido
	 * @param total  indica si la carga es completa o parcial
	 * @param finder lista para evitar procesar repetidos
	 * @param zwin   ventana donde reside la tabla
	 * @return {@code true} si el parámetro es una tabla y se procesó
	 *         correctamente
	 * @throws Exception en caso de errores de parseo
	 */
	public boolean processTableElement(String ds, boolean total, JList<String> finder, JWin zwin) throws Exception {
		if (!ds.startsWith("dgt_"))
			return false;
		String data = JWebActionFactory.getCurrentRequest().getArgument(ds);
		String variableTable = null;
		String fieldname = null;
		JWin win = zwin;
		TreeMap<Long, String> fieldnames = new TreeMap<Long, String>();
		String toksField = JTools.getFirstTokens(data, '[', ']');
		StringTokenizer toksFieldNamess = new StringTokenizer(toksField, ",");
		while (toksFieldNamess.hasMoreTokens()) {
			String posandvalue = toksFieldNamess.nextToken();
			if (variableTable == null) {
				variableTable = posandvalue.substring(0, posandvalue.indexOf('|'));
				fieldname = posandvalue.substring(posandvalue.indexOf('|') + 1);

				int p = fieldname.indexOf("-");
				if (p != -1) {
					int p2 = fieldname.indexOf("_card_");
					if (p2 != -1) {
						String providerCardObj = fieldname.substring(p2 + 1, p - 3);
						JWin card = null;
						card = getCard(win, providerCardObj, card);
						if (card != null)
							win = card;
					}
					;
				}

				continue;
			}
			StringTokenizer toksInternalFields = new StringTokenizer(posandvalue, ":");
			long idxField = JTools.getLongFirstNumberEmbedded(toksInternalFields.nextToken());
			String value = toksInternalFields.nextToken();
			fieldnames.put(idxField, value);

		}

		// getCard(win, providerRow, table)
		JRecords table = null;
		table = getTable(win, variableTable, table);
		if (table == null)
			return false;
		int idx = 0;
		List<String> toks = JTools.getTokens(data, '{', '}');
		Iterator<String> it = toks.iterator();
		while (it.hasNext()) {
			String stringRow = it.next();
			StringTokenizer toksFields = new StringTokenizer(stringRow, ",");
			JWebActionData dataRow = new JWebActionData("row_" + idx);
			while (toksFields.hasMoreTokens()) {
				String posandvalue = toksFields.nextToken();
				long idxField = JTools.getLongFirstNumberEmbedded(posandvalue.substring(0, posandvalue.indexOf(":")));
				String value = posandvalue.substring(posandvalue.indexOf(":") + 1);
				dataRow.add(fieldnames.get(idxField), value);
			}
			addRowWithData(idx, win, variableTable, table, dataRow);
			idx++;

		}

		if (table != null) {
			JObjBDs dtable = (JObjBDs) win.getRecord().getProp(variableTable);
			if (dtable != null)
				dtable.setValue(table);
		}

		return true;
	}

	/**
	 * Procesa tablas cuyos datos vienen distribuidos en varios parámetros de la
	 * request. El nombre del parámetro debe incluir la cadena <code>"_row_"</code>
	 * seguida por el índice de fila. Este formato se utiliza cuando cada fila se
	 * envía como un formulario independiente.
	 *
	 * <p>
	 * Algunos nombres pueden contener el marcador <code>"__l"</code> para indicar
	 * listas anidadas. En todos los casos se reconstruye el {@link JRecords}
	 * asociado creando nuevas filas o completando las existentes.
	 * </p>
	 *
	 * @param ds     nombre del parámetro analizado
	 * @param total  indica si la carga es completa o parcial
	 * @param finder lista para evitar duplicados (puede ser {@code null})
	 * @param win    ventana que contiene la tabla a completar
	 * @return {@code true} si el parámetro representaba una tabla extendida
	 * @throws Exception si ocurre un error durante el parseo
	 */
	public boolean processTableExtendElement(String ds, boolean total, JList<String> finder, JWin win) throws Exception {
		int idx = 0;
		JRecords table = null;
		String providerRow;
		String variableTable;
		String spos;
		long idRow;
		int p = ds.indexOf("-");
		if (p == -1)
			return false;
//		if (finder!=null) {
//			if (finder.containsElement(ds.substring(0,p))) return false;
//			finder.addElement(ds.substring(0,p));
//		}
		int p2 = ds.indexOf("_row_");
		if (p2 == -1)
			return false;
//		if (finder!=null) {
//			if (finder.containsElement(ds.substring(0,p2))) return false;
//			finder.addElement(ds.substring(0,p2));
//		}
		if (ds.indexOf("__l") != -1) {
			int pInicial = ds.lastIndexOf("_", ds.lastIndexOf("__") - 1) + 1;
			int pos = ds.lastIndexOf("_", p2 - 1) - 1;
			if (pos == -1)
				return false;
			int pos2 = ds.substring(pInicial).indexOf("__");
			if (pos2 == -1)
				return false;

			providerRow = ds.substring(4, pInicial + pos);
			variableTable = ds.substring(pInicial, pInicial + pos2);
			spos = JTools.getNumberEmbedded(ds.substring(p2 + 5, p));
			idRow = spos.equals("") ? 0 : Long.parseLong(spos);

		} else {
			int pInicial = ds.lastIndexOf("_", p2 - 1) + 1;
			int pos = ds.substring(pInicial).indexOf("_");
			if (pos == -1)
				return false;

			providerRow = ds.substring(4, pInicial + pos);
			variableTable = ds.substring(pInicial, pInicial + pos);
			spos = JTools.getNumberEmbedded(ds.substring(p2 + 5, p));
			idRow = spos.equals("") ? 0 : Long.parseLong(spos);

		}

		while (true) {
			JWebActionData dataRow = JWebActionFactory.getCurrentRequest().getFormData(providerRow + "_row_" + idx);
			table = getTable(win, variableTable, table);
			if (table == null)
				break;
			if (dataRow.isNull()) {
				if (!total) {
					if (table.sizeStaticElements() < idx)
						break;
					idx++;
					continue;
				}
				if (addRowNoData(idRow, idx, win, variableTable, table)) {
					idx++;
					continue;
				}
				break;
			}
			if (finder != null)
				finder.addElement(ds.substring(0, p2));
			addRowWithData(idx, win, variableTable, table, dataRow);
			idx++;
		}
		if (table != null) {
			JObjBDs dtable = (JObjBDs) win.getRecord().getProp(variableTable);
			if (dtable != null)
				dtable.setValue(table);
		}
		return true;
	}

	public void controlsToBD(JWebActionData data, JRecord record) throws Exception {
		if (controToBD != null) {
			controToBD.controlsToBD(data, record);
			return;
		}
		JIterator<JWebActionDataField> iter = data.getFieldIterator();
		while (iter.hasMoreElements()) {
			JWebActionDataField field = iter.nextElement();
			if (field == null)
				continue;
			if (record == null)
				continue;
			JObject<?> prop = record.getPropDeep(field.getName(), false);
			if (prop == null) {
				record.addExtraData(field.getName(), field.getValue());
				continue;
			}
//			if (field.getName().equals("previousPriceQuantity"))
//			PssLogger.logDebug(field.getName()+"="+field.getValue());

			String value = field.getValue();
			Object obj = null;
			if (value.startsWith("obj_")) {
				if (value.startsWith("obj_t_")) {
					obj = getRegisterObjectTemp(value.substring(6));
				} else {
					obj = JWebActionFactory.getCurrentRequest().getRegisterObject(value);
				}
			}
			if (prop.isRecord()) {
				if (obj != null && obj instanceof JWin)
					prop.setValue(record.getPropValue(field.getName(), prop, ((JWin) obj).getRecord()));
				else
					prop.setNull();
			} else if (obj != null && obj instanceof JWin)
				prop.setValue(record.getPropValue(field.getName(), prop, ((JWin) obj).GetValorItemClave()));
			else
				prop.setValueFormUI(record.getPropValue(field.getName(), prop, JTools.decodeURLIfNecesary(value)));
		}
	}

	public JBaseWin getRegisterObjectTemp(String zKey) throws Exception {
		return packager.getRegisterObjectTemp(zKey);
	}

	public JBaseRecord getRegisterObjectRecTemp(String zKey) throws Exception {
		return packager.getRegisterObjectRecTemp(zKey);
	}

	private boolean isExport() throws Exception {
		JWebActionData p = JWebActionFactory.getCurrentRequest().getData("export");
		if (p == null)
			return false;
		return p.get("range") != null;
	}

	public Serializable resolveData() throws Exception {
		String data = JWebActionFactory.getCurrentRequest().getArgument("data_asoc");
		if (data == null || data.equals(""))
			return null;
		return JWebActionFactory.getCurrentRequest().getRegisterObject(data);
	}

	public boolean detectVirtualActionIsMultiple() throws Exception {
		JWebActionData data = JWebActionFactory.getCurrentRequest().getData("export");
		if (data == null)
			return false;
		String dato = data.get("multiple");
		if (dato == null)
			return false;
		return dato.equals("true");

	}

	public String convertActionToURL(BizAction zAction) throws Exception {
		Map<String, String> dict = new HashMap<>();
		String id = zAction.getIdAction();
		if (id != null)
			dict.put("i", id);

		if (zAction.needsFullSerialization()) {
			byte[] serialized = JTools.stringToByteVector(JWebActionFactory.getCurrentRequest().serializeObject(zAction));
			dict.put("a", JWinPackager.b64url(JWinPackager.deflate(serialized)));
		} else {
			// pack in
			// JWinPackager packer = new JWinPackager(null);
			String idOwner = JWebActionFactory.getCurrentRequest().registerObjectObj(zAction.getObjOwner());
			if (idOwner != null && !idOwner.isEmpty()) {
				dict.put("o", idOwner);
			}

			if (zAction.hasSubmit()) {
				JAct submit = zAction.getObjSubmit();
				if (submit != null && submit.hasResult()) {
					String idResult = JWebActionFactory.getCurrentRequest().registerObjectObj(zAction.getObjOwner());
					// String resultPacked = packer.baseWinToPack(submit.getResult());
					if (idResult != null && !idResult.isEmpty()) {
						dict.put("r", idResult);
					}
				}
			}
		}
		return JWebActionFactory.getCurrentRequest().serializeRegisterMapJSON(dict);
	}

	public BizAction convertURLToAction(String sAction) throws Exception {
		Map<String, String> dict = JWebActionFactory.getCurrentRequest().deserializeRegisterMapJSON(sAction);
		BizAction action;

		if (dict.containsKey("ay") || dict.containsKey("action")) {
			String data = dict.containsKey("a") ? dict.get("a") : dict.get("action");
			byte[] bytes = dict.containsKey("a") ? JWinPackager.inflate(JWinPackager.b64urlDecode(data)) : Base64.getDecoder().decode(data);
			action = (BizAction) JWebActionFactory.getCurrentRequest().deserializeObject(JTools.byteVectorToString(bytes));
		} else {

			String ownerKey = dict.containsKey("o") ? dict.get("o") : dict.get("owner");
			String id = dict.containsKey("i") ? dict.get("i") : dict.get("actionid");

			JBaseWin win = (JBaseWin) JWebActionFactory.getCurrentRequest().getRegisterObject(ownerKey);

			action = win.findActionByUniqueId(id);

			String resultKey = dict.containsKey("r") ? dict.get("r") : dict.get("result");
			if (resultKey != null) {
				JBaseWin result = (JBaseWin) JWebActionFactory.getCurrentRequest().getRegisterObject(resultKey);
				action.getObjSubmit().setResult(result);
			}
		}
		return action;
	}

	private String winStamp(JBaseWin win) throws Exception {
		boolean readed = win.isWin() && ((JWin) win).getRecord().wasDbRead();
		int filters = win.GetBaseDato().getFilters() != null ? win.GetBaseDato().getFilters().size() : 0;
		return win.getUniqueId() + "|" + win.GetVision() + "|" + readed + "|" + filters;
	}

	private String recStamp(JBaseRecord rec) throws Exception {
		boolean readed = (rec instanceof JRecord) && ((JRecord) rec).wasDbRead();
		int filters = rec.getFilters() != null ? rec.getFilters().size() : 0;
		return rec.getUniqueId() + "|" + rec.GetVision() + "|" + readed + "|" + filters;
	}

	public String baseWinToURL(JBaseWin zOwner) throws Exception {
		final String key = "win:" + winStamp(zOwner);
		DistCache cache = CacheProvider.get();
		byte[] cached = cache.getBytes(key);
		if (cached != null)
			return JTools.byteVectorToString(cached);
		String packed = packager.baseWinToPack(zOwner);
		cache.putBytes(key, JTools.stringToByteArray(packed), 0);
		return packed;
	}

	public String baseRecToURL(JBaseRecord rec) throws Exception {
		final String key = "rec:" + recStamp(rec);
		DistCache cache = CacheProvider.get();
		byte[] cached = cache.getBytes(key);
		if (cached != null)
			return JTools.byteVectorToString(cached);
		String packed = packager.baseRecToPack(rec);
		cache.putBytes(key, JTools.stringToByteArray(packed), 0);
		return packed;
	}

	public void invalidateWinPack(JBaseWin win) throws Exception {
		CacheProvider.get().delete("win:" + winStamp(win));
	}

	public void invalidateRecPack(JBaseRecord rec) throws Exception {
		CacheProvider.get().delete("rec:" + recStamp(rec));
	}

}
