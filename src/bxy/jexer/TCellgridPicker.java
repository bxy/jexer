package bxy.jexer;

import jexer.TAction;
import jexer.TWidget;
import jexer.bits.Cell;
import jexer.bits.CellAttributes;
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
     * Attributes of selected cell
     */
    private CellAttributes selectedCellAttributes;

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

        this.selectedCellAttributes = new CellAttributes();
        this.selectedCellAttributes.setForeColor(Color.YELLOW);
        this.selectedCellAttributes.setBold(true);
        this.selectedCellAttributes.setBackColor(Color.CYAN);

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
            selectedX = x;
            selectedY = y;
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
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (i == selectedX && j == selectedY) {
                    getScreen().putCharXY(i, j, cellgrid.getCell(i, j).getChar(), selectedCellAttributes);
                } else {
                    getScreen().putCharXY(i, j, cellgrid.getCell(i, j));
                }
            }
        }
    }


    /**
     * Handle mouse press events.
     *
     * @param mouse mouse button press event
     */
    @Override
    public void onMouseDown(TMouseEvent mouse) {
        if (mouse.isMouseWheelUp()) {
            if(selectedY > 0) selectedY--;
            dispatchMove();

        } else if (mouse.isMouseWheelDown()) {
            if(selectedY < getHeight() - 1) selectedY++;
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
        // select clicked cell
        selectedX = mouse.getX();
        selectedY = mouse.getY();

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
            if(selectedX < getWidth() - 1) selectedX++;
            dispatchMove();

        } else if (keypress.equals(kbLeft)) {
            if(selectedX > 0) selectedX--;
            dispatchMove();

        } else if (keypress.equals(kbUp)) {
            if(selectedY > 0) selectedY--;
            dispatchMove();

        } else if (keypress.equals(kbDown)) {
            if(selectedY < getHeight() - 1) selectedY++;
            dispatchMove();

        } else if (keypress.equals(kbHome)) {
            selectedX = 0;
            selectedY = 0;
            dispatchMove();

        } else if (keypress.equals(kbEnd)) {
            selectedX = getWidth() - 1;
            selectedY = getHeight() - 1;
            dispatchMove();

        } else if (keypress.equals(kbPgUp)) {
            selectedY = 0;
            dispatchMove();

        } else if (keypress.equals(kbPgDn)) {
            selectedY = getHeight() - 1;
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
