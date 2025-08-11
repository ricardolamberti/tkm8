/*
 * Created on 19-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.core.tools.collections.JIterator;

/**
 * A widget to select a single value from a list of values.
 * 
 * Created on 19-jul-2003
 * 
 * @author PSS
 */

public interface JWebValueChooser {

	public abstract void addValue(String zValue, String zDescription);

	public abstract String getSelectedValue();

	public abstract String getSelectedDescription();

	public abstract JIterator<String> getValues();

	public abstract String getDescription(String zValue);

	public abstract void setSelectedValue(String zValue);
}
