package bxy.jexer;

import jexer.TWidget;
import jexer.bits.Cell;

/**
 * TCellgridLabel is simple widget implementation of Cellgrid that knows how to draw itself
 */
public final class TCellgridLabel extends TWidget {

    /**
     * Cellgrid to be drawn
     */
    private Cellgrid cellgrid;

    /**
     * Public constructor
     * Creates TCellgridLabel with Cellgrid
     * @param parent parent widget
     * @param x column relative to parent
     * @param y row relative to parent
     * @param cellgrid cellgrid on the screen
     */
    public TCellgridLabel(final TWidget parent, final int x, final int y, final Cellgrid cellgrid) {
        super(parent, x, y, cellgrid.getWidth(), cellgrid.getHeight());
        this.cellgrid = cellgrid;
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
     * Draw cellgrid
     */
    @Override
    public void draw() {
        for (int i = 0; i < getWidth(); i++)
            for (int j = 0; j < getHeight(); j++)
                getScreen().putCharXY(i, j, cellgrid.getCell(i,j));
    }

}
