/*
 * Created on 22-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.processing;

/**
 * An interface to tell a tree coordinator whether a tree selection consumer
 * applies for a given tree id and selection levels.<br>
 * When a consumer answers <code>false</code>, the <code>applyFilters(...)</code>
 * method of a tree coordinator answers <code>false</code>, indicating the
 * filters were not applied.  
 * 
 * Created on 22-jul-2003
 * @author PSS
 */

public interface JWebTreeSelectionConsumer {

  public boolean acceptsSelection(String zTreeId, int zLevel);
  public boolean appliesTreeFiltersManually(String zTreeId);
  
  public String getFilterField(String zTreeId, int zLevel);

}
