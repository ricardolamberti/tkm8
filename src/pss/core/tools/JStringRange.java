package pss.core.tools;

import java.util.ArrayList;
import java.util.List;

import com.ibm.icu.util.StringTokenizer;

public class JStringRange {
	String value;
  List<String> values;
  
	public List<String> getValues() {
		return values;
	}

	public String getString() {
		return value;
	}

	public void setString(String value) {
		this.value = value;
		values = new ArrayList<String>();
		
	  StringTokenizer toks = new StringTokenizer(value,",");
	  while (toks.hasMoreElements()) {
	  	String tok = (String)toks.nextElement();
	  	evaluate(tok);
	  }
	}
	
	void evaluate(String val) {
		int pos1 = val.indexOf("-");
		int pos2 = val.indexOf("*");
		if (pos1!=-1) evalRange(val,pos1); 
		else if (pos2!=-1) evalIte(val,pos2);
		else values.add(val);
	}
	
	void evalIte(String val,int pos) {
		String value = val.substring(0,pos);
		boolean isNumericValue1 = JTools.isNumberPure(val.substring(pos+1));
		if (isNumericValue1) {
			int ite = Integer.parseInt(val.substring(pos+1));
			for (int i=0;i<ite;i++)
				values.add(value);
			return;
		}
		values.add(val);
	}
	
	void evalRange(String val,int pos) {
		String value1 = val.substring(0,pos);
		String value2 = val.substring(pos+1);
		boolean isNumericValue1 = JTools.isNumberPure(value1);
		boolean isNumericValue2 = JTools.isNumberPure(value2);
		if (isNumericValue1 && isNumericValue2) {
			for (int i=Integer.parseInt(value1);i<=Integer.parseInt(value2);i++)
				values.add(""+i);
			return;
		}
		boolean isLetterValue1 = value1.length()==1;
		boolean isLetterValue2 = value2.length()==1;
		if (isLetterValue1 && isLetterValue2) {
			for (char i=value1.charAt(0);i<=value2.charAt(0);i++)
				values.add(""+i);
			return;
		}
		values.add(val);
		
	}
	
}
