package pss.core.ui.components;

/**
 * A holder of a scrollable text area.
 *
 * @author Leonardo Pronzolino
 * @email PSS@mcsla.com.ar
 */

import javax.swing.ScrollPaneConstants;

public class JScrollableTextArea extends JPssScrollPane {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private JPssEditTextArea textArea;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JScrollableTextArea() {
    this.textArea = new JPssEditTextArea();
    this.textArea.setLayoutComponent(this);
    this.setAutoscrolls(true);
    this.setViewportView(this.textArea);
    this.setWordWrap(true);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JPssEditTextArea getTextArea() {
    return textArea;
  }
  public boolean isShowingAnyScrollBars() {
    return this.getHorizontalScrollBar().isVisible() ||
            this.getVerticalScrollBar().isVisible();
  }

  public void setWordWrap(boolean zWrap) {
    if (zWrap) {
      this.textArea.setLineWrap(true);
      this.textArea.setWrapStyleWord(true);
      this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    } else {
      this.textArea.setLineWrap(false);
      this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
  }


}
