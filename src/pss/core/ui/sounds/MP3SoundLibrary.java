package pss.core.ui.sounds;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import pss.core.tools.PssLogger;

/**
 * <p>Title: Pss project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author eaguilar
 * @version 1.0
 */

public class MP3SoundLibrary implements SoundLibrary {

  private HashMap oStreams = null;

/*******************************************************
 * Constructor
 *******************************************************/
  public MP3SoundLibrary() {
    super();
    this.oStreams = new HashMap(89);
  }

/*******************************************************
 * Load method.
 *******************************************************/
  @SuppressWarnings("unchecked")
 public void load(String zID, String zFile) throws Exception {
    try {
      // checks the file type
      if (!zFile.endsWith(".mp3")) {
        throw new Exception("The file must be .mp3");
      }
      // creates the input stream and audio format
      File oFile = new File(zFile);
      // creates the line and adds to map
      AudioInputStream oStream = AudioSystem.getAudioInputStream(oFile);
      AudioFormat oFormat = oStream.getFormat();
      // the encoding  ULAW or ALAW are yet not supported
/*      if (oFormat.getEncoding() == AudioFormat.Encoding.ULAW || oFormat.getEncoding() == AudioFormat.Encoding.ALAW) {
        throw new UnsupportedAudioFileException("Unsupported file encoding. Try with PCM encoding.");
      } */
      this.oStreams.put(zID, oFile);

    } catch (Exception e) {
      PssLogger.logError(e, e.getMessage() + ": " + zFile);
      return;
    }

  }

  /*******************************************************
   * Returns audio format
   *******************************************************/
  public File getFile(String zID) {
    if (this.hasSound(zID))
      return (File)this.oStreams.get(zID);
    return null;
  }

/*  public AudioInputStream getStream(String zID) {
    if (this.containsKey(zID))
      return (AudioInputStream)this.get(zID);
    return null;
  }*/

  public boolean hasSound(String id) {
    return this.oStreams.containsKey(id);
  }

}
