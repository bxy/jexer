package bxy.jexer;

import jexer.TWidget;
import jexer.bits.Cell;
import jexer.bits.CellAttributes;

/**
 * TCellgridLabel is simple widget implementation of Cellgrid that knows how to draw itself
 */
public final class TCellgridLabel extends TWidget {

    /**
     * Cellgrid to be drawn
     */
    private Cellgrid cellgrid;

    /**
     * CellAttributes to override Cellgrid's cellAttributes
     */
    private CellAttributes cellAttributes;


    /**
     * Public constructor
     * Creates TCellgridLabel with Cellgrid
     * @param parent parent widget
     * @param x column relative to parent
     * @param y row relative to parent
     * @param cellgrid cellgrid on the screen
     */
    public TCellgridLabel(final TWidget parent, final int x, final int y, final Cellgrid cellgrid) {
        this(parent, x, y, cellgrid, null);
    }

    /**
     * Public constructor
     * Creates TCellgridLabel with Cellgrid and override cell attributes
     * @param parent parent widget
     * @param x column relative to parent
     * @param y row relative to parent
     * @param cellgrid cellgrid on the screen
     * @param cellAttributes cell attributes that will override cell attributes from cellgrid
     */
    public TCellgridLabel(final TWidget parent, final int x, final int y, final Cellgrid cellgrid, final CellAttributes cellAttributes) {
        super(parent, x, y, cellgrid.getWidth(), cellgrid.getHeight());
        this.cellgrid = cellgrid;
        this.cellAttributes = cellAttributes;
    }

    /**
     * Change Cell data at x,y coordinates
     * @param x cell column
     * @param y cell row
     * @param cell
     */
    public void setCell(final int x, final int y, final Cell cell) {
        cellgrid.getCell(x, y).setTo(cell);
    }

    /**
     * set cell attributes widget will use instead of cellgrid cell attributes
     * @param cellAttributes if null widget will use attributes from cellgrid
     */
    public void setCellAttributes(CellAttributes cellAttributes) {
        this.cellAttributes = cellAttributes;

    }

    public CellAttributes getCellAttributes() {
        return cellAttributes;
    }

    /**
     * Draw cellgrid
     */
    @Override
    public void draw() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if(cellAttributes != null)
                    getScreen().putCharXY(i, j, cellgrid.getCell(i, j).getChar(), cellAttributes);
                else {
                    getScreen().putCharXY(i, j, cellgrid.getCell(i, j));
                }
            }
        }
    }

}
