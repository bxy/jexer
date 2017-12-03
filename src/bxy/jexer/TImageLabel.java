package bxy.jexer;

import jexer.TWidget;
import jexer.bits.Cell;

/**
 * TImageLabel is simple implementation of TImage that knows how to draw itself
 */
public final class TImageLabel extends TWidget {

    /**
     * Image to be drawn
     */
    private TImage image;

    /**
     * Public constructor
     * Creates TImageLabel with TImage
     * @param parent parent widget
     * @param x column relative to parent
     * @param y row relative to parent
     * @param image image on the screen
     */
    public TImageLabel(final TWidget parent, final int x, final int y, final TImage image) {
        super(parent, x, y, image.getWidth(), image.getHeight());
        this.image = image;
    }

    /**
     * Change Cell data at x,y coordinates
     * @param x cell column
     * @param y cell row
     * @param cell
     */
    public void setCell(final int x, final int y, final Cell cell) {
        image.setCell(x, y, cell);
    }

    /**
     * Draw image
     */
    @Override
    public void draw() {
        for (int i = 0; i < getWidth(); i++)
            for (int j = 0; j < getHeight(); j++)
                getScreen().putCharXY(i, j, image.getCell(i,j));
    }

}
