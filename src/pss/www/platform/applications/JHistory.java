package pss.www.platform.applications;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;

import pss.core.tools.PssLogger;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;

public class JHistory implements Serializable {

	private LinkedHashMap<String, JHistoryProvider> providers; 
	private LinkedHashMap<String, String> providersWithinLevel; 
	JHistoryProvider firstProvider;
  boolean isModal;

	public boolean isJSModal() {
		return isModal;
	}

	public void setJSModal(boolean isModal) {
		this.isModal = isModal;
	}

	public LinkedHashMap<String, JHistoryProvider> getProviders() {
		return providers;
	}

//	public void setProviders(ConcurrentHashMap<String, JHistoryProvider> providers) {
//		this.providers = providers;
//	}
	
	public void cleanUpToLeaveInMemory() throws Exception {
		Iterator<JHistoryProvider> it = getProviders().values().iterator();
		while (it.hasNext()) {
			JHistoryProvider m = it.next();
			m.cleanUpToLeaveInMemory();
		}
	}

	public JHistory() {
	}
	
	public String removeLevel(String name) throws Exception {
		if (name.indexOf("__l")==-1) return name;
		return name.substring(0,name.indexOf("__l"));
	}

	public JHistoryProvider findHistoryProvider(BizAction action) throws Exception {
		if (action==null) return null;
		if (this.hasProviders()) {
			Iterator<JHistoryProvider> iter = this.providers.values().iterator();
			while (iter.hasNext()) {
				JHistoryProvider p = iter.next();
				if (!p.getAction().isSameAction(action)) continue;
				p.setAction(action);
				return p;
			} 
		}
		JHistoryProvider p = new JHistoryProvider();
		p.setAction(action);
		return p;
	}

	public void addProvider(BizAction a) throws Exception {
		this.addProvider(this.findHistoryProvider(a));
	}

	public void removeProvider(JHistoryProvider p) throws Exception {
		if (this.providers==null) return;
		if (p==firstProvider)
			firstProvider=null;
		this.providers.remove(p.getAction().getProviderName());
		this.providersWithinLevel.remove(removeLevel(p.getAction().getProviderName()));
	}

	public void addProvider(JHistoryProvider p) throws Exception {
		if (this.providers==null) this.providers=new LinkedHashMap();
		if (this.providersWithinLevel==null) this.providersWithinLevel=new LinkedHashMap();
		if (this.providers.size()==0)
			firstProvider=p;
		p.getAction().getObjSubmit().setInHistory(true);
		this.providers.put(p.getAction().getProviderName(), p);
		this.providersWithinLevel.put(removeLevel(p.getAction().getProviderName()),p.getAction().getProviderName());
	}


	public JHistoryProvider findProvider(JAct a) throws Exception {
		if (a==null) return null;
		return this.findProvider(a.getActionSource());
	}
	public JHistoryProvider findProvider(BizAction a) throws Exception {
		if (a==null) return null;
		return this.findProvider(a.getProviderName());
	}
	
	public JHistoryProvider findProvider(String p) throws Exception {
		if (this.providers==null) return null;
		if (p==null) {
			PssLogger.logError("Find provider unknown");
		 return null;
		}
		if (p.equals("")) return this.getFirstProvider();
		return this.providers.get(p);
	}

	public void returnning() throws Exception {
		if (this.providers==null) return;
		Iterator<JHistoryProvider> iter = this.providers.values().iterator();
		while (iter.hasNext()) {
			JHistoryProvider p = iter.next();
			p.returnning();
		}
	}

	String ref = this.toString();
	public String getRef() {
		return ref;
	}
	public JHistoryProvider getFirstProvider() throws Exception {
		if (this.hasFirstProviders()) return firstProvider;
		if (!this.hasProviders()) return null;
		Iterator<JHistoryProvider> iter =  this.providers.values().iterator();
		return iter.next();
	}

	public boolean hasProviders() throws Exception {
		return this.providers!=null;
	}

	public boolean hasFirstProviders() throws Exception {
		return this.firstProvider!=null;
	}

	public JAct getMainSumbit() throws Exception {
		if (!this.hasProviders()) return null;
//		((JHistoryProvider)this.providers.values().toArray()[0]).action.getObjSubmit().getResult().getModeView()
		return this.getFirstProvider().getActionSubmit();
	}

	public BizAction getFirstAction() throws Exception {
		return this.getFirstProvider().getAction();
	}

	public void changeActionSource(BizAction a) throws Exception {
		if (!this.hasProviders()) return;
		JHistoryProvider p = this.getFirstProvider();
		a.setObjSubmit(p.getActionSubmit());
		p.setAction(a);
	}

	public void clearProviders() throws Exception {
		this.providers=null;
		this.firstProvider=null;
	}

        public void clearAllSubmits() throws Exception {
                Iterator<JHistoryProvider> iter = this.providers.values().iterator();
                while (iter.hasNext()) {
                        JHistoryProvider p = iter.next();
                        if (!p.refreshOnSubmit()) continue;
                        p.getAction().clearSubmit();
                }
        }

        // --- lazy serialization helpers ---
        public String serializeShallow() throws Exception {
                StringBuilder sb = new StringBuilder();
                sb.append(isModal ? "1" : "0");
                sb.append('|');
                if (providers != null) {
                        boolean first = true;
                        for (String k : providers.keySet()) {
                                if (!first) sb.append(',');
                                sb.append(k);
                                first = false;
                        }
                }
                sb.append('|');
                if (firstProvider != null && firstProvider.getAction() != null)
                        sb.append(firstProvider.getAction().getProviderName());
                sb.append('|');
                if (providersWithinLevel != null) {
                        boolean first = true;
                        for (java.util.Map.Entry<String, String> e : providersWithinLevel.entrySet()) {
                                if (!first) sb.append(',');
                                sb.append(e.getKey()).append('=').append(e.getValue());
                                first = false;
                        }
                }
                return sb.toString();
        }

        public void unSerializeShallow(String data) throws Exception {
                if (data == null) return;
                String[] parts = data.split("\\|", -1);
                if (parts.length > 0) this.isModal = "1".equals(parts[0]);
                if (parts.length > 1 && !parts[1].isEmpty()) {
                        this.providers = new LinkedHashMap<String, JHistoryProvider>();
                        for (String k : parts[1].split(",")) {
                                if (!k.isEmpty()) this.providers.put(k, null);
                        }
                }
                if (parts.length > 3 && !parts[3].isEmpty()) {
                        this.providersWithinLevel = new LinkedHashMap<String, String>();
                        for (String kv : parts[3].split(",")) {
                                int p = kv.indexOf('=');
                                if (p > 0) this.providersWithinLevel.put(kv.substring(0, p), kv.substring(p + 1));
                        }
                }
        }

        public java.util.Set<String> getProviderKeysFromShallow() {
                if (providers == null) return java.util.Collections.emptySet();
                return providers.keySet();
        }
	public void leaveHistory() throws Exception {
		this.markInHistory(false);
	}
	public void markInHistory(boolean v) throws Exception {
		// limpio la variable por si me queda el objecto en memoria (ej, los del menu)
		if(this.providers==null) return;
		Iterator<JHistoryProvider> iter = this.providers.values().iterator();
		while (iter.hasNext()) {
			JHistoryProvider p = iter.next();
			if (!p.getAction().hasSubmit()) continue;
			p.getAction().getObjSubmit().setInHistory(v);
		}
	}
		
	@Override
	public String toString() {
		String out="";
		if (this.providers==null) return super.toString();
		Iterator<JHistoryProvider> iter = this.providers.values().iterator();
		while (iter.hasNext()) {
			JHistoryProvider p = iter.next();
			out += (out.equals("")?"":", ")+ p.toString();
		}
		return "["+out+"]";
	}
	public LinkedHashMap<String, String> getProvidersWithinLevel() {
		return providersWithinLevel;
	}
	public JHistoryProvider findProviderWithinLevel(BizAction a) throws Exception {
		if (a==null) return null;
		return this.findProviderWithinLevel(a.getProviderName());
	}
	public JHistoryProvider findProviderWithinLevel(String p) throws Exception {
		if (this.providersWithinLevel==null) return null;
		if (p.equals("")) return this.getFirstProvider();
		String key = this.providersWithinLevel.get(removeLevel(p));
		if (key==null) // revisar esto
			return this.getFirstProvider();
		return this.findProvider( key );
	}
	
	private boolean confirmHistory=false;
	public void confirmHistory() throws Exception {
		this.confirmHistory=true;
	}

	public void setConfirmHistory(boolean v) throws Exception {
		this.confirmHistory=v;
	}
	
	public boolean isConfirmHistory() throws Exception {
		return this.confirmHistory;
	}

}
