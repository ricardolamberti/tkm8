package pss.core.ui.sounds;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Marconi Commerce Systems
 * @author PSS
 * @version 1.0
 */

public class WavSoundLibrary implements SoundLibrary {

  private HashMap streams = null;

  /**
   * Constructor
   */
  public WavSoundLibrary() {
    super();
    this.streams = new HashMap(89);
  }

  /**
   * Load method.
   */
  @SuppressWarnings("unchecked")
public void load(String id, String fileName) throws Exception {
    // checks the file type
    if (!fileName.endsWith(".wav")) {
      JExcepcion.SendError("el sonido "+ id +" tiene que tener formato WAV");
    }
    // creates the input stream and audio format
    File file = new File(fileName);
    // creates the line and adds to map
    AudioInputStream stream = AudioSystem.getAudioInputStream(file);
    AudioFormat format = stream.getFormat();
    // the encoding  ULAW or ALAW are yet not supported
    if (format.getEncoding() == AudioFormat.Encoding.ULAW || format.getEncoding() == AudioFormat.Encoding.ALAW) {
      JExcepcion.SendError("Unsupported file encoding. Try with PCM encoding.");
    }
    DataLine.Info oInfo = new DataLine.Info(Clip.class, format);
    Clip clip = (Clip)AudioSystem.getLine(oInfo);
    clip.open(stream);
    this.streams.put(id, clip);
  }

  /**
   * Returns audio format
   */
  public Clip getClip(String id) {
    if (!this.hasSound(id)) {
      PssLogger.logDebug("No existe el sonido " + id);
      return null;
    }
    return (Clip)this.streams.get(id);
  }

  public boolean hasSound(String id) {
    return this.streams.containsKey(id);
  }

  public void clear() throws Exception {
    Iterator iterator = this.streams.values().iterator();
    while (iterator.hasNext()) {
      Clip clip = (Clip)iterator.next();
      clip.close();
    }
    this.streams.clear();
  }

}
