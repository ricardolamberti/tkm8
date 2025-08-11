package pss.core.services.fields;

import java.util.Map;

import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class JScript {

	private JMap<String, String> bind=null;
//	private Bindings context=null;
	private String script=null;
	private boolean calculeOthersFields=false;
	private boolean calculeOnStart=false;

//	public String getValue() throws Exception {
//		if (this.context==null) new Exception("Invalid Context");
//		ScriptEngineManager mgr=new ScriptEngineManager();
//		ScriptEngine engine=mgr.getEngineByExtension("js");
//		engine.setBindings(this.context, ScriptContext.ENGINE_SCOPE);
//		String f="function getValue() {return "+createGetValues()+";}";
//		engine.eval(f);
//		Invocable invocableEngine=(Invocable) engine;
//		return invocableEngine.invokeFunction("getValue").toString();
//
//	};

	public boolean isCalculeOnStart() {
		return calculeOnStart;
	}

	public JScript setCalculeOnStart(boolean calculeOnStart) {
		this.calculeOnStart = calculeOnStart;
		return this;
	}

	public boolean isCalculeOthersFields() {
		return calculeOthersFields;
	}

	public JScript setCalculeOthersFields(boolean calculeOthersFields) {
		this.calculeOthersFields = calculeOthersFields;
		return this;
	}

	private boolean execScripts=false;
	public void executeScripts() {
		this.execScripts=true;
	}

	public JScript() {
	}
	
	public JScript(String script) {
		this.setScript(script);
	}
	
	private boolean calculeOnAnyChange=true;

	public void setCalculeOnAnyChange(boolean calculeOnAnyChange) {
		this.calculeOnAnyChange = calculeOnAnyChange;
	}

	public boolean isCalculeOnAnyChange() {
		if (this.isCalculeOthersFields()) return false;
		return calculeOnAnyChange;
	}

	private boolean isPure=false;
	public boolean isPure() {
		return this.isPure;
	}
	public void setPure(boolean v) {
		this.isPure=v;
	}

//	public String createGetValues() throws Exception {
//		String newFormula=script;
//		Iterator<String> i=context.keySet().iterator();
//		while (i.hasNext()) {
//			String key=i.next();
//			Object obj=context.get(key);
//			if (obj instanceof JFormControl) {
//				JFormControl control=(JFormControl) obj;
//				if (control.getProp().isCurrency()||control.getProp().isFloat()) newFormula=newFormula.replaceAll(key, "parseFloat("+key+"\\.getValue\\(\\)==\"\"?0:"+key+"\\.getValue\\(\\))");
//				else if (control.getProp().isLong()||control.getProp().isInteger()) newFormula=newFormula.replaceAll(key, "parseInt("+key+"\\.getValue\\(\\)==\"\"?0:"+key+"\\.getValue\\(\\))");
//				else newFormula=newFormula.replaceAll(key, key+"\\.getValue\\(\\)");
//
//			} else if (obj instanceof JObject) {
//				JObject control=(JObject) obj;
//				newFormula=newFormula.replaceAll(key, key+"\\.getValue\\(\\)");
//			}
//		}
//		return newFormula;
//	}

//	public JScript(Bindings zBind, String zVal) {
//		bind=zBind;
//		script=zVal;
//	}

	public JMap<String, String> getNewBind() {
		return JCollectionFactory.createMap();
	}

	private int idDicc =0;
	private String getUniqueName() {
		return  "@@"+(idDicc++)+"@@";
	}
	public void addBind(String zComp) {
		addBind(getUniqueName(), zComp);
	}

	public void addBind(String zKey, String zComp) {
		if (bind==null) bind=getNewBind();
		bind.addElement(zKey, zComp);
	}

	public void setScript(String zVal) {
		script=zVal;
	}
	public String getScript() {
		return script;
	}

	public boolean isEmpty() throws Exception {
		return script==null||script.equals("");
	}

	public boolean equals(String zString) {
		return script.equals(zString);
	}

	public JMap<String, String> getBind() {
		return bind;
	}

//	public void setContext(Bindings bind) {
//		this.context=bind;
//	}

	public String getBindingFormula() throws Exception {
		String newFormula=script;
		JMap<String, String> binds=this.getBindSortByLen();//.getBind();
		JIterator<String> iter=binds.getKeyIterator();
		while (iter.hasMoreElements()) {
			String key=iter.nextElement();
			String value=binds.getElement(key);
			newFormula=JTools.replace(newFormula,value, key);
		}
		return newFormula;
	}
	
	public String getFormulaInContext(Map<String, String> map) throws Exception {
		String newFormula="";
		if (execScripts) newFormula="executeScripts();";
		newFormula+=this.getBindingFormula();
		
		for (String key : map.keySet()) {
			String value=map.get(key);
			newFormula=JTools.replace(newFormula, key, value);
		}
		return newFormula;

	}
	
	public JScript append(String other) throws Exception {
		this.setScript((this.script==null)?other:this.getScript()+ "; " + other);
		return this;
	}

	public JScript append(JScript other) throws Exception {
		JIterator<String> iter = other.getBind().getValueIterator();
		while (iter.hasMoreElements()) {
			this.addBind(iter.nextElement());
		}
		this.setScript(this.getScript()+ "; " + other.getScript());
		return this;
	}

//	public void setContext(JMap<String, JObject<?>> hProperties) throws Exception {
//		SimpleBindings bind=new SimpleBindings();
//		Bindings originalBind=getBind();
//		Iterator<String> i=originalBind.keySet().iterator();
//		while (i.hasNext()) {
//			String key=i.next();
//			String value=(String) originalBind.get(key);
//			JObject obj=hProperties.getElement(value);
//			if (obj!=null) {
//				bind.put(key, obj);
//			}
//		}
//		setContext(bind);
//	}

//	public void setContext(JFormControles oControles) throws Exception {
//		SimpleBindings bind=new SimpleBindings();
//		Bindings originalBind=getBind();
//		Iterator<String> i=originalBind.keySet().iterator();
//		while (i.hasNext()) {
//			String key=i.next();
//			String value=(String) originalBind.get(key);
//			JFormControl oControl=oControles.findControl(value);
//			if (oControl!=null) {
//				bind.put(key, oControl);
//			}
//		}
//		setContext(bind);
//	}
	
	private JMap<String, String> getBindSortByLen() throws Exception {
		JMap<String, String> m = JCollectionFactory.createOrderedMap();
		String maxv = "";
		String maxk = "";
		JMap<String, String> b = this.getBind();
		int len = b.size();
		for (int i=0; i<len;i++) {
			maxv = ""; maxk = "";
			JIterator<String> iter = b.getKeyIterator();
			while (iter.hasMoreElements()) {
				String k = iter.nextElement();
				String v = b.getElement(k);
				if (v.length()< maxv.length()) continue;
				if (m.containsKey(k)) continue;
				maxv=v;
				maxk=k;
			}
			m.addElement(maxk, maxv);
		}
		return m;
	}


	private String orden="";
	public void setOrden(String v) {
		this.orden=v;
	}
	public String getOrden() {
		return this.orden;
	}

}
