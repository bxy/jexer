package bxy.jexer;

import jexer.bits.Cell;
import jexer.bits.CellAttributes;

import java.util.Arrays;

/**
 * Container for grid of Cells
 */
public final class Cellgrid {

    /**
     * Cellgrid width
     */
    private int width;
    /**
     * Cellgrid height
     */
    private int height;

    /**
     * Cellgrid data
     */
    private Cell[] data;

    /**
     * calculate data array index from cellgrid x,y coordinates
     * @param x
     * @param y
     * @return
     */
    private int calcIndex(int x, int y) {
        assert(x >= 0 && x < width && y >= 0 && y < height);
        return y * width + x;
    }

    /**
     * Public constructor
     * Creates empty Cellgrid with default foreground/background
     * @param width cellgrid width
     * @param height cellgrid height
     */
    public Cellgrid(final int width, final int height) {
        this(width, height, new CellAttributes(), null);
    }

    /**
     * Public constructor
     * Creates empty Cellgrid with custom foreground/background
     * @param width cellgrid width
     * @param height cellgrid height
     * @param cellAttributes cellgrid foreground/background attributes
     */
    public Cellgrid(final int width, final int height, final CellAttributes cellAttributes) {
        this(width, height, cellAttributes, null);
    }

    /**
     * Public constructor
     * Creates Cellgrid with default foreground/background and cellgrid data populated from 'content'
     * Cellgrid data is populated from left to right, top to bottom. If content is bigger than cellgrid size rest of the content will be discarded.
     * @param width cellgrid width
     * @param height cellgrid height
     * @param content cellgrid data (only content, without cellAttributes)
     */
    public Cellgrid(final int width, final int height, final char[] content) {
        this(width, height, new CellAttributes(), content);
    }

    /**
     * Public constructor
     * Creates Cellgrid with custom foreground/background and cellgrid data populated from 'content'
     * Cellgrid data is populated from left to right, top to bottom.
     * If content is bigger than cellgrid size rest of the content will be discarded.
     * If content is smaller content will be used from start
     * @param width cellgrid width
     * @param height cellgrid height
     * @param cellAttributes cellgrid foreground/background attributes
     * @param content cellgrid data (only content, without cellAttributes)
     */
    public Cellgrid(final int width, final int height, final CellAttributes cellAttributes, final char[] content) {
        this.width = width;
        this.height = height;
        this.data = new Cell[width * height];

        for (int i = 0; i < data.length; i++) {
            Cell cell = new Cell();
            cell.setAttr(cellAttributes == null ? new CellAttributes() : cellAttributes);
            cell.setChar(content == null ? ' ' : content[i % content.length]);
            this.data[i] = cell;
        }
    }

    /**
     * Get original Cell data at column x, row y coordinates.
     * Can be used to set Cell properties e.g getCell(x,y).setTo(newCell)
     * @param x cell column
     * @param y cell row
     * @return cell data
     */
    public Cell getCell(final int x, final int y) {
        return data[calcIndex(x, y)];
    }

    /**
     * Get Cell copy at column x, row y.
     * Can be used instead of getCell(x,y) to get cell copy to prevent changing cell grid data with cell.set...
     * If you need to change Cell data use getCell(x,y)
     * @param x cell column
     * @param y cell row
     * @return cell copy
     */
    public Cell getCellCopy(final int x, final int y) {
        Cell cell = new Cell();
        cell.setTo(data[calcIndex(x, y)]);
        return cell;
    }

    /**
     * Get cellgrid width
     * @return cellgrid width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get cellgrid height
     * @return cellgrid height
     */
    public int getHeight() {
        return height;
    }
}
