package pss.core.ui.components;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.SwingConstants;

public class JToolBarContainer extends JPssPanel {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private int relativePosition = SwingConstants.TOP;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //  API
  //
  public boolean isScrollable() {
    return false;
  }
  /**
   * Answers the relative position in the container. This applies when the
   * container has a border layout.<br>
   * Possible values are:<br>
   * SwingConstants.TOP<br>
   * SwingConstants.BOTTOM<br>
   * SwingConstants.LEFT<br>
   * SwingConstants.RIGHT<br>
   */
  public int getRelativePosition() {
    return this.relativePosition;
  }
  /**
   * Sets the relative position in the container. This applies when the
   * container has a border layout.<br>
   * Possible values are:<br>
   * SwingConstants.TOP<br>
   * SwingConstants.BOTTOM<br>
   * SwingConstants.LEFT<br>
   * SwingConstants.RIGHT<br>
   */
  public void setRelativePosition(int zRelativePosition) {
    if (this.isValidRelativePosition(zRelativePosition)) {
      this.relativePosition = zRelativePosition;
    }
  }
  public String getBorderLayoutPosition() {
    String position = BorderLayout.NORTH;
    switch (this.getRelativePosition()) {
      case SwingConstants.TOP:
        position = BorderLayout.NORTH;
        break;
      case SwingConstants.BOTTOM:
        position = BorderLayout.SOUTH;
        break;
      case SwingConstants.LEFT:
        position = BorderLayout.WEST;
        break;
      case SwingConstants.RIGHT:
        position = BorderLayout.EAST;
        break;
    }
    return position;
  }
  public void addTo(Container zBorderLayoutContainer) {
    zBorderLayoutContainer.add(this, this.getBorderLayoutPosition());
  }
  /**
   * Answers this container orientarion, deducing it from its relative position
   * in the parent.<br>
   * The orientarion is one of the following:<br>
   * SwingConstants.HORIZONTAL<br>
   * SwingConstants.VERTICAL<br>
   */
  public int getOrientation() {
    int orientation = SwingConstants.HORIZONTAL;
    if (this.isVertical()) {
      orientation = SwingConstants.VERTICAL;
    }
    return orientation;
  }
  public boolean isHorizontal() {
    return this.relativePosition==SwingConstants.TOP
      || this.relativePosition==SwingConstants.BOTTOM;
  }
  public boolean isVertical() {
    return this.relativePosition==SwingConstants.LEFT
      || this.relativePosition==SwingConstants.RIGHT;
  }
  public int getToolBarCount() {
    return this.getComponentCount();
  }
  //
  //  internal methods
  //
  protected boolean isValidRelativePosition(int zRelativePosition) {
    return zRelativePosition==SwingConstants.TOP
      || zRelativePosition==SwingConstants.BOTTOM
      || zRelativePosition==SwingConstants.LEFT
      || zRelativePosition==SwingConstants.RIGHT;
  }
  //
  //  overridden
  //

}
