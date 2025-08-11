package pss.core.ui.sounds;

/**
 * Copyright:    Copyright (c) 2001
 * Company:      PSS
 * @author PSS
 * @version 1.0
 */

public interface SoundLibrary {


  /**
   * @todo must override
   */
  public void load(String id, String file) throws Exception;
  public boolean hasSound(String id);

}
