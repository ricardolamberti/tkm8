package pss.core.win.submits;

import pss.core.services.fields.JObjBDs;
import pss.core.services.records.JRecords;
import pss.core.win.IControl;
import pss.core.win.JBaseWin;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class JActFieldWins extends JActWins {

	JWin winSource;
	String field;
  Class clase;
  

		public JActFieldWins(JWin zSource,String field,Class clase) {
			this(zSource, field,clase,false);
		}
		public JActFieldWins(JWin zSource, String field,Class clase, boolean multi) {
			this(zSource,field,clase,multi,true);
		}
		public JActFieldWins(JWin zSource, String field,Class clase, boolean multi,boolean zLineSelect) {
			super(null,multi,zLineSelect);
			this.winSource=zSource;
			this.field=field;
			this.clase=clase;
		}
		
		
		@Override
		public JBaseWin GetBWin() throws Exception {
		  JWins wins = (JWins) clase.newInstance();
		  JObjBDs	 prop = (JObjBDs) getWinSource().getRecord().getProp(field);
		  JRecords recs = (JRecords)prop.getValue();
		  modifiedOnServer=prop.isModifiedOnServer();
		  if (recs!=null) recs.setFieldBased(true);
		  wins.setParent(getWinSource());
		  wins.setRecords(recs);
		  return wins;
		}


	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	public JWin getWinSource() {
		return winSource;
	}
	public void setWinSource(JWin winSource) {
		this.winSource = winSource;
	}
	public Class getWinsClass() {
		return clase;
	}
	public void setWinsClass(Class clase) {
		this.clase = clase;
	}
		
	public IControl createControlWin() throws Exception {
		return new JControlWin() {
			public JWins getRecords() throws Exception {
				return (JWins)GetBWin();
			}
		};
	}
}
