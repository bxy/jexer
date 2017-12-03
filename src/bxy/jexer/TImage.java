package bxy.jexer;

import jexer.bits.Cell;
import jexer.bits.CellAttributes;

import java.util.Arrays;

/**
 * Container for grid of Cells
 */
public final class TImage {

    /**
     * Image width
     */
    private int width;
    /**
     * Image height
     */
    private int height;

    /**
     * Image data
     */
    private Cell[] data;

    /**
     * calculate data array index from image x,y coordinates
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
     * Creates empty TImage with default foreground/background
     * @param width image width
     * @param height image height
     */
    public TImage(final int width, final int height) {
        this(width, height, new CellAttributes(), null);
    }

    /**
     * Public constructor
     * Creates empty TImage with custom foreground/background
     * @param width image width
     * @param height image height
     * @param cellAttributes image foreground/background attributes
     */
    public TImage(final int width, final int height, final CellAttributes cellAttributes) {
        this(width, height, cellAttributes, null);
    }

    /**
     * Public constructor
     * Creates TImage with default foreground/background and image data populated from 'content'
     * Image data is populated from left to right, top to bottom. If content is bigger than image size rest of the content will be discarded.
     * @param width image width
     * @param height image height
     * @param content image data (only content, without cellAttributes)
     */
    public TImage(final int width, final int height, final char[] content) {
        this(width, height, new CellAttributes(), content);
    }

    /**
     * Public constructor
     * Creates TImage with custom foreground/background and image data populated from 'content'
     * Image data is populated from left to right, top to bottom.
     * If content is bigger than image size rest of the content will be discarded.
     * If content is smaller content will be used from start
     * @param width image width
     * @param height image height
     * @param cellAttributes image foreground/background attributes
     * @param content image data (only content, without cellAttributes)
     */
    public TImage(final int width, final int height, final CellAttributes cellAttributes, final char[] content) {
        this.width = width;
        this.height = height;
        this.data = new Cell[width * height];

        Cell cell = new Cell();
        cell.setAttr(cellAttributes);
        Arrays.fill(this.data, cell);

        if (content == null) return;

        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++) {
                int index = calcIndex(j, i);
                cell = new Cell();
                cell.setAttr(cellAttributes);
                cell.setChar(content[index % content.length]);
                this.data[index] = cell;
            }
    }

    /**
     * Get Cell data at x,y coordinates
     * @param x cell column
     * @param y cell row
     * @return
     */
    public Cell getCell(final int x, final int y) {
        return data[calcIndex(x, y)];
    }

    /**
     * Set Cell data at x,y coordinates
     * @param x cell column
     * @param y cell row
     * @param cell cell data
     */
    public void setCell(final int x, final int y, Cell cell) {
        data[calcIndex(x, y)] = cell;
    }

    /**
     * Get image width
     * @return image width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get image height
     * @return image height
     */
    public int getHeight() {
        return height;
    }
}
