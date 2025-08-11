package pss.core.ui.sounds;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PlayerFactory {

  public static Player createWavPlayer() {
    return new WavPlayer();
  }

  public static Player createMP3Player() {
    return null;
  }

  public static Player createAIFFPlayer() {
    return null;
  }


}
