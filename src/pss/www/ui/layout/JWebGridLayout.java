package pss.www.ui.layout;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import pss.core.tools.collections.JIterator;
import pss.www.ui.JWebViewComponent;
import pss.www.ui.JWebViewComposite;

public class JWebGridLayout  extends JWebCompositeLayout  {

  /**
   * This is the horizontal gap (in pixels) which specifies the space
   * between columns.  They can be changed at any time.
   * This should be a non-negative integer.
   *
   * @serial
   * @see #getHgap()
   * @see #setHgap(int)
   */
  int hgap;
  /**
   * This is the vertical gap (in pixels) which specifies the space
   * between rows.  They can be changed at any time.
   * This should be a non negative integer.
   *
   * @serial
   * @see #getVgap()
   * @see #setVgap(int)
   */
  int vgap;
  /**
   * This is the number of rows specified for the grid.  The number
   * of rows can be changed at any time.
   * This should be a non negative integer, where '0' means
   * 'any number' meaning that the number of Rows in that
   * dimension depends on the other dimension.
   *
   * @serial
   * @see #getRows()
   * @see #setRows(int)
   */
  int rows;
  /**
   * This is the number of columns specified for the grid.  The number
   * of columns can be changed at any time.
   * This should be a non negative integer, where '0' means
   * 'any number' meaning that the number of Columns in that
   * dimension depends on the other dimension.
   *
   * @serial
   * @see #getColumns()
   * @see #setColumns(int)
   */
  int cols;

  /**
   * Creates a grid layout with a default of one column per component,
   * in a single row.
   * @since JDK1.1
   */
  public JWebGridLayout() {
      this(1, 0, 0, 0);
  }

  /**
   * Creates a grid layout with the specified number of rows and
   * columns. All components in the layout are given equal size.
   * <p>
   * One, but not both, of <code>rows</code> and <code>cols</code> can
   * be zero, which means that any number of objects can be placed in a
   * row or in a column.
   * @param     rows   the rows, with the value zero meaning
   *                   any number of rows.
   * @param     cols   the columns, with the value zero meaning
   *                   any number of columns.
   */
  public JWebGridLayout(int rows, int cols) {
      this(rows, cols, 0, 0);
  }

  /**
   * Creates a grid layout with the specified number of rows and
   * columns. All components in the layout are given equal size.
   * <p>
   * In addition, the horizontal and vertical gaps are set to the
   * specified values. Horizontal gaps are placed between each
   * of the columns. Vertical gaps are placed between each of
   * the rows.
   * <p>
   * One, but not both, of <code>rows</code> and <code>cols</code> can
   * be zero, which means that any number of objects can be placed in a
   * row or in a column.
   * <p>
   * All <code>GridLayout</code> constructors defer to this one.
   * @param     rows   the rows, with the value zero meaning
   *                   any number of rows
   * @param     cols   the columns, with the value zero meaning
   *                   any number of columns
   * @param     hgap   the horizontal gap
   * @param     vgap   the vertical gap
   * @exception   IllegalArgumentException  if the value of both
   *                  <code>rows</code> and <code>cols</code> is
   *                  set to zero
   */
  public JWebGridLayout(int rows, int cols, int hgap, int vgap) {
      if ((rows == 0) && (cols == 0)) {
          throw new IllegalArgumentException("rows and cols cannot both be zero");
      }
      this.rows = rows;
      this.cols = cols;
      this.hgap = hgap;
      this.vgap = vgap;
  }

  /**
   * Gets the number of rows in this layout.
   * @return    the number of rows in this layout
   * @since     JDK1.1
   */
  public int getRows() {
      return rows;
  }

  /**
   * Sets the number of rows in this layout to the specified value.
   * @param        rows   the number of rows in this layout
   * @exception    IllegalArgumentException  if the value of both
   *               <code>rows</code> and <code>cols</code> is set to zero
   * @since        JDK1.1
   */
  public void setRows(int rows) {
      if ((rows == 0) && (this.cols == 0)) {
          throw new IllegalArgumentException("rows and cols cannot both be zero");
      }
      this.rows = rows;
  }

  /**
   * Gets the number of columns in this layout.
   * @return     the number of columns in this layout
   * @since      JDK1.1
   */
  public int getColumns() {
      return cols;
  }

  /**
   * Sets the number of columns in this layout to the specified value.
   * Setting the number of columns has no affect on the layout
   * if the number of rows specified by a constructor or by
   * the <tt>setRows</tt> method is non-zero. In that case, the number
   * of columns displayed in the layout is determined by the total
   * number of components and the number of rows specified.
   * @param        cols   the number of columns in this layout
   * @exception    IllegalArgumentException  if the value of both
   *               <code>rows</code> and <code>cols</code> is set to zero
   * @since        JDK1.1
   */
  public void setColumns(int cols) {
      if ((cols == 0) && (this.rows == 0)) {
          throw new IllegalArgumentException("rows and cols cannot both be zero");
      }
      this.cols = cols;
  }

  /**
   * Gets the horizontal gap between components.
   * @return       the horizontal gap between components
   * @since        JDK1.1
   */
  public int getHgap() {
      return hgap;
  }

  /**
   * Sets the horizontal gap between components to the specified value.
   * @param        hgap   the horizontal gap between components
   * @since        JDK1.1
   */
  public void setHgap(int hgap) {
      this.hgap = hgap;
  }

  /**
   * Gets the vertical gap between components.
   * @return       the vertical gap between components
   * @since        JDK1.1
   */
  public int getVgap() {
      return vgap;
  }

  /**
   * Sets the vertical gap between components to the specified value.
   * @param         vgap  the vertical gap between components
   * @since        JDK1.1
   */
  public void setVgap(int vgap) {
      this.vgap = vgap;
  }

	protected void doLayout(JWebViewComposite zComposite, Rectangle zLayoutArea) throws Exception {
		int ncomponents = zComposite.getChildrenCount();
		int nrows = rows;
		int ncols = cols;

		if (ncomponents == 0) {
			return;
		}
		// if (nrows==1 && ncols==0) {
		// if (zLayoutArea.width>zLayoutArea.height)
		// ncols=ncomponents;
		// else
		// nrows=ncomponents;
		//      	
		// }
		if (nrows > 0) {
			ncols = (ncomponents + nrows - 1) / nrows;
		} else {
			nrows = (ncomponents + ncols - 1) / ncols;
		}
		int totalGapsWidth = (ncols - 1) * hgap;
		int widthWOInsets = zLayoutArea.width;
		int widthOnComponent = (widthWOInsets - totalGapsWidth) / ncols;
		int extraWidthAvailable = (widthWOInsets - (widthOnComponent * ncols + totalGapsWidth)) / 2;

		int totalGapsHeight = (nrows - 1) * vgap;
		int heightWOInsets = zLayoutArea.height;
		int heightOnComponent = (heightWOInsets - totalGapsHeight) / nrows;
		int extraHeightAvailable = (heightWOInsets - (heightOnComponent * nrows + totalGapsHeight)) / 2;
		JIterator<JWebViewComponent> oChildrenIt = zComposite.getChildren();
		int c = 0, r = 0;
		while (oChildrenIt.hasMoreElements()) {
			JWebViewComponent oComp = oChildrenIt.nextElement();
			if (oComp.isVisible()) {
				boolean filaPar = r % 2 == 0;
				boolean colPar = c % 2 == 0;
				int extraAncho = ncols == 2 ? (int) (widthOnComponent * 0.2) * (filaPar ? (colPar ? -1 : 1) : (colPar ? 1 : -1)) : 0;
				int initExtra = ncols == 2 ? (c == 0 ? 0 : -extraAncho) : 0;
				oComp.setLocation((c * (widthOnComponent + hgap)) + extraWidthAvailable + initExtra, (r * (heightOnComponent + vgap)) + extraHeightAvailable);
				oComp.setSize(widthOnComponent + extraAncho, heightOnComponent);
				c++;
				if (c >= ncols) {
					c = 0;
					r++;
				}
				if (r > nrows)
					break;

			}
		}
	}

	@Override
	public void takeLayoutAttributesFrom(LayoutManager layout) throws Exception {
		GridLayout lay = (GridLayout)layout;
		this.setRows(lay.getRows());
		this.setColumns(lay.getColumns());
		this.setHgap(lay.getHgap());
		this.setVgap(lay.getVgap());
	}
	
}
