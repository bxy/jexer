package bxy.jexer;

import jexer.TWidget;
import jexer.bits.Cell;
import jexer.bits.Color;
import jexer.bits.GraphicsChars;
import jexer.event.TMouseEvent;

/**
 * Grid view of Code Page 437 characters
 * Chars can be selected with a mouse (not with keyboard yet)
 */
public final class TAsciiChart extends TWidget {

    /**
     * image to be drawn
     */
    private TImage asciiImage;
    /**
     * x coordinate of selected cell
     */
    private int selectedX = -1;
    /**
     * y coordinate of selected cell
     */
    private int selectedY = -1;

    /**
     * Public constructor
     * @param parent parent widget
     * @param x column relative to parent
     * @param y row relative to parent
     */
    public TAsciiChart(final TWidget parent, int x, int y) {
        super(parent, x, y, 32, 8);
        asciiImage = new TImage(32, 8, getTheme().getColor("ttext"), GraphicsChars.CP437);
    }

    /**
     * Get selected cell
     * @return selected cell
     */
    public Cell getSelectedCell() {
        if (selectedX == -1 || selectedY == -1)
            return null;
        return asciiImage.getCell(selectedX, selectedY);
    }

    /**
     * Get selected index in the image data array
     * @return selected index
     */
    public int getSelectedIndex() {
        if (selectedX == -1 || selectedY == -1) return -1;
        return selectedY * asciiImage.getWidth() + selectedX;
    }

    /**
     * Draw ascii chart
     */
    @Override
    public void draw() {
        for (int i = 0; i < getWidth(); i++)
            for (int j = 0; j < getHeight(); j++)
                getScreen().putCharXY(i, j, asciiImage.getCell(i,j));
    }

    /**
     * handle mouse
     * @param mouse mouse button event
     */
    @Override
    public void onMouseUp(TMouseEvent mouse) {
        // clear previously selected cell
        if (selectedX != -1 && selectedY != -1)
            asciiImage.getCell(selectedX, selectedY).setAttr(getTheme().getColor("ttext"));

        // select clicked cell
        selectedX = mouse.getX();
        selectedY = mouse.getY();
        getSelectedCell().setForeColor(Color.YELLOW);
        getSelectedCell().setBold(true);

    }
}
