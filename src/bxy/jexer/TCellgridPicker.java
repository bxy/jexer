package bxy.jexer;

import jexer.TAction;
import jexer.TWidget;
import jexer.bits.Cell;
import jexer.bits.Color;
import jexer.event.TKeypressEvent;
import jexer.event.TMouseEvent;

import static jexer.TKeypress.*;
import static jexer.TKeypress.kbEnter;
import static jexer.TKeypress.kbPgDn;

/**
 * Selectable Cellgrid widget
 */
public class TCellgridPicker extends TWidget {
    /**
     * Cellgrid to be drawn
     */
    private Cellgrid cellgrid;
    /**
     * x coordinate of selected cell
     */
    private int selectedX = 0;
    /**
     * y coordinate of selected cell
     */
    private int selectedY = 0;

    /**
     * The action to perform when the user selects an item (clicks or enter).
     */
    private TAction enterAction = null;

    /**
     * The action to perform when the user navigates with keyboard.
     */
    private TAction moveAction = null;

    /**
     * Perform user selection action.
     */
    public void dispatchEnter() {
        if (enterAction != null) {
            enterAction.DO();
        }
    }

    /**
     * Perform list movement action.
     */
    public void dispatchMove() {
        if (moveAction != null) {
            moveAction.DO();
        }
    }

    /**
     * Public constructor
     * @param parent parent widget
     * @param x column relative to parent
     * @param y row relative to parent
     * @param cellgrid cellgrid on the screen
     */
    public TCellgridPicker(final TWidget parent, final int x, final int y, final Cellgrid cellgrid) {
        this(parent, x, y, cellgrid, null);
    }

    /**
     * Public constructor
     * @param parent parent widget
     * @param x column relative to parent
     * @param y row relative to parent
     * @param cellgrid cellgrid on the screen
     * @param enterAction action to perform when an item is selected
     */
    public TCellgridPicker(final TWidget parent, final int x, final int y, final Cellgrid cellgrid, final TAction enterAction) {
        this(parent, x, y, cellgrid, enterAction, null);
    }

    /**
     * Public constructor
     * @param parent parent widget
     * @param x column relative to parent
     * @param y row relative to parent
     * @param cellgrid cellgrid on the screen
     * @param enterAction action to perform when an item is selected
     * @param moveAction action to perform when the user navigates to a new item with arrow/page keys
     */
    public TCellgridPicker(final TWidget parent, final int x, final int y, final Cellgrid cellgrid, final TAction enterAction, final TAction moveAction) {
        super(parent, x, y, cellgrid.getWidth(), cellgrid.getHeight());
        this.cellgrid = cellgrid;
        this.enterAction = enterAction;
        this.moveAction = moveAction;

        select();
    }

    private Cell oldSelectedCell;

    /**
     * color selected cell
     */
    private void select() {
        oldSelectedCell = cellgrid.getCellCopy(selectedX, selectedY);
        cellgrid.getCell(selectedX, selectedY).setForeColor(Color.YELLOW);
        cellgrid.getCell(selectedX, selectedY).setBold(true);
        cellgrid.getCell(selectedX, selectedY).setBackColor(Color.CYAN);
    }

    /**
     * restore default color for selected cell
     */
    private void unselect() {
        cellgrid.getCell(selectedX, selectedY).setForeColor(oldSelectedCell.getForeColor());
        cellgrid.getCell(selectedX, selectedY).setBold(oldSelectedCell.isBold());
        cellgrid.getCell(selectedX, selectedY).setBackColor(oldSelectedCell.getBackColor());
    }

    /**
     * Get selected character
     * @return selected character
     */
    public Cell getSelectedCell() {
        return cellgrid.getCellCopy(selectedX, selectedY);
    }

    /**
     * Sets selected row and column
     * @param x column to be selected
     * @param y row to be selected
     */
    public void setSelected(final int x, final int y) {
        if(0 <= x && x < getWidth() && 0 <= y && y < getHeight()) {
            unselect();
            selectedX = x;
            selectedY = y;
            select();
        } else {
            throw new IllegalArgumentException("Invalid coordinates: " + x + ", " + y);
        }
    }

    /**
     * Get selected column coordinate
     * @return
     */
    public int getSelectedX() {
        return selectedX;
    }

    /**
     * Get selected row coordinate
     * @return
     */
    public int getSelectedY() {
        return selectedY;
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


    /**
     * Handle mouse press events.
     *
     * @param mouse mouse button press event
     */
    @Override
    public void onMouseDown(TMouseEvent mouse) {
        if (mouse.isMouseWheelUp()) {
            unselect();
            if(selectedY > 0) selectedY--;
            select();
            dispatchMove();

        } else if (mouse.isMouseWheelDown()) {
            unselect();
            if(selectedY < getHeight() - 1) selectedY++;
            select();
            dispatchMove();

        } else {
            // Let parent class handle it.
            super.onMouseDown(mouse);
            return;
        }
    }

    /**
     * handle mouse up
     * @param mouse mouse button event
     */
    @Override
    public void onMouseUp(TMouseEvent mouse) {
        // clear previously selected cell
        unselect();

        // select clicked cell
        selectedX = mouse.getX();
        selectedY = mouse.getY();
        select();

        dispatchMove();

    }

    /**
     * Handle mouse double click.
     *
     * @param mouse mouse double click event
     */
    @Override
    public void onMouseDoubleClick(final TMouseEvent mouse) {
        dispatchEnter();

    }

    /**
     * Handle keystrokes.
     *
     * @param keypress keystroke event
     */
    @Override
    public void onKeypress(final TKeypressEvent keypress) {
        if (keypress.equals(kbRight)) {
            unselect();
            if(selectedX < getWidth() - 1) selectedX++;
            select();
            dispatchMove();

        } else if (keypress.equals(kbLeft)) {
            unselect();
            if(selectedX > 0) selectedX--;
            select();
            dispatchMove();

        } else if (keypress.equals(kbUp)) {
            unselect();
            if(selectedY > 0) selectedY--;
            select();
            dispatchMove();

        } else if (keypress.equals(kbDown)) {
            unselect();
            if(selectedY < getHeight() - 1) selectedY++;
            select();
            dispatchMove();

        } else if (keypress.equals(kbHome)) {
            unselect();
            selectedX = 0;
            selectedY = 0;
            select();
            dispatchMove();

        } else if (keypress.equals(kbEnd)) {
            unselect();
            selectedX = getWidth() - 1;
            selectedY = getHeight() - 1;
            select();
            dispatchMove();

        } else if (keypress.equals(kbPgUp)) {
            unselect();
            selectedY = 0;
            select();
            dispatchMove();

        } else if (keypress.equals(kbPgDn)) {
            unselect();
            selectedY = getHeight() - 1;
            select();
            dispatchMove();

        } else if (keypress.equals(kbEnter)) {
            dispatchEnter();

        } else {
            // Pass to my parent
            super.onKeypress(keypress);
            return;
        }

    }

}
