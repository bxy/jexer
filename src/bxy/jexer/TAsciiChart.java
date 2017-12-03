package bxy.jexer;

import jexer.TAction;
import jexer.TWidget;
import jexer.bits.Color;
import jexer.bits.GraphicsChars;
import jexer.event.TKeypressEvent;
import jexer.event.TMouseEvent;

import static jexer.TKeypress.*;

/**
 * Grid view of Code Page 437 characters
 * Chars can be selected with a mouse or keyboard
 */
public final class TAsciiChart extends TWidget {

    /**
     * image to be drawn
     */
    private TImage asciiImage;
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
     */
    public TAsciiChart(final TWidget parent, final int x, final int y) {
        this(parent, x, y, null, null);
    }

    /**
     * Public constructor
     * @param parent parent widget
     * @param x column relative to parent
     * @param y row relative to parent
     * @param enterAction action to perform when an item is selected
     */
    public TAsciiChart(final TWidget parent, final int x, final int y, final TAction enterAction) {
        this(parent, x, y, enterAction, null);
    }

    /**
     * Public constructor
     * @param parent parent widget
     * @param x column relative to parent
     * @param y row relative to parent
     * @param enterAction action to perform when an item is selected
     * @param moveAction action to perform when the user navigates to a new item with arrow/page keys
     */
    public TAsciiChart(final TWidget parent, final int x, final int y, final TAction enterAction, final TAction moveAction) {
        super(parent, x, y, 32, 8);
        this.asciiImage = new TImage(32, 8, getTheme().getColor("ttext"), GraphicsChars.CP437);
        this.enterAction = enterAction;
        this.moveAction = moveAction;

        selectCell();
    }

    /**
     * color selected cell
     */
    private void selectCell() {
        asciiImage.getCell(selectedX, selectedY).setForeColor(Color.YELLOW);
        asciiImage.getCell(selectedX, selectedY).setBold(true);
        asciiImage.getCell(selectedX, selectedY).setBackColor(Color.CYAN);
    }

    /**
     * restore default color for selected cell
     */
    private void unselectCell() {
        asciiImage.getCell(selectedX, selectedY).setAttr(getTheme().getColor("ttext"));
    }

    /**
     * Get selected character
     * @return selected character
     */
    public char getSelectedChar() {
        return asciiImage.getCell(selectedX, selectedY).getChar();
    }

    /**
     * Get selected index in the image data array
     * @return selected index
     */
    public int getSelectedIndex() {
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
     * handle mouse up
     * @param mouse mouse button event
     */
    @Override
    public void onMouseUp(TMouseEvent mouse) {
        // clear previously selected cell
        unselectCell();

        // select clicked cell
        selectedX = mouse.getX();
        selectedY = mouse.getY();
        selectCell();

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
            unselectCell();
            if(selectedX < getWidth() - 1) selectedX++;
            selectCell();
            dispatchMove();

        } else if (keypress.equals(kbLeft)) {
            unselectCell();
            if(selectedX > 0) selectedX--;
            selectCell();
            dispatchMove();

        } else if (keypress.equals(kbUp)) {
            unselectCell();
            if(selectedY > 0) selectedY--;
            selectCell();
            dispatchMove();

        } else if (keypress.equals(kbDown)) {
            unselectCell();
            if(selectedY < getHeight() - 1) selectedY++;
            selectCell();
            dispatchMove();

        } else if (keypress.equals(kbHome)) {
            unselectCell();
            selectedX = 0;
            selectedY = 0;
            selectCell();
            dispatchMove();

        } else if (keypress.equals(kbEnd)) {
            unselectCell();
            selectedX = getWidth() - 1;
            selectedY = getHeight() - 1;
            selectCell();
            dispatchMove();

        } else if (keypress.equals(kbPgUp)) {
            unselectCell();
            selectedY = 0;
            selectCell();
            dispatchMove();

        } else if (keypress.equals(kbPgDn)) {
            unselectCell();
            selectedY = getHeight() - 1;
            selectCell();
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
