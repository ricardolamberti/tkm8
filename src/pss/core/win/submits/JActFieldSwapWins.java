package pss.core.win.submits;

import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWins;

public class JActFieldSwapWins extends JActWins {

	JWins selecteds;
	JWins options;
	String fieldKeySource;
	String fieldKeyOptions;
 
	public JActFieldSwapWins(JWins zSource, JWins zOptions)  throws Exception {
		this(zSource,zOptions,zSource);
	}
	public JActFieldSwapWins(JWins zSource, JWins zOptions, JBaseWin listener)  throws Exception {
		this(zSource,zOptions,listener,findId(zSource,zOptions),zOptions.getWinRef().getKeyField());

	}
	public String getTitle() throws Exception {
		if (getOptions() != null) {
			String title = getOptions().GetTitle();
			if (title != null && !title.equals(""))
				return title;
		}
	
		return super.getTitle();
	}
  	
	private static String findId(JWins zSource, JWins zOptions) throws Exception {
		Class classToFind = zOptions.getWinRef().getRecord().getClass();
		JProperty prop = zSource.getWinRef().getRecord().findFirstFixedPropByClass(classToFind,false);
		if (prop==null) {
			return zSource.getWinRef().getKeyField();
		}
		String out = prop.GetCampo()+".";
		if (!prop.isRecord()) return null;
		JRecord  winChild = (JRecord)prop.getClase().newInstance();
		out+=winChild.getKeyField();
		return out;
	}
	
	public JActFieldSwapWins(JWins zSource, JWins zOptions, JBaseWin listener, String keySource,String keyOptions)  throws Exception {
		super(zSource,true); 
		options=zOptions;
		fieldKeySource=keySource;
		fieldKeyOptions=keyOptions;
		zSource.assignDropListener(listener);
		zSource.setModeView(JBaseWin.MODE_SWAP);
		zOptions.setModeView(JBaseWin.MODE_SWAP);
	}
	public JWins getSelecteds() {
		return selecteds;
	}
	public void setSelecteds(JWins selecteds) {
		this.selecteds = selecteds;
	}
	 public boolean hasSelected() { 
	  	return true;
	  }
	public JWins getOptions() {
		return options;
	}
	public void setOptions(JWins options) {
		this.options = options;
	}
	
	public String getFieldKeySource() {
		return fieldKeySource;
	}
	public void setFieldKeySource(String fieldKeySource) {
		this.fieldKeySource = fieldKeySource;
	}
	public String getFieldKeyOptions() {
		return fieldKeyOptions;
	}
	public void setFieldKeyOptions(String fieldKeyOptions) {
		this.fieldKeyOptions = fieldKeyOptions;
	}
	@Override
	public boolean isHistoryAction() throws Exception {
		return true; 
	}
	public boolean isTargetAction() throws Exception {
		if (this.historyTarget) return true;
		return !getWinsResult().hasDropListener();
	}


}