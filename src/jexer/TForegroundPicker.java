package jexer;

import jexer.bits.CellAttributes;
import jexer.bits.Color;
import jexer.bits.GraphicsChars;
import jexer.event.TKeypressEvent;
import jexer.event.TMouseEvent;

import static jexer.TKeypress.*;

/**
 * The foreground color picker.
 */
public class TForegroundPicker extends TWidget {

    /**
     * The selected color.
     */
    private Color color = Color.BLACK;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * The bold flag.
     */
    private boolean bold;

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    /**
     * Title
     */
    private String title;

    /**
     * The action to perform when the user selects an item (clicks or enter).
     */
    private TAction enterAction = null;

    /**
     * The action to perform when the user navigates with keyboard.
     */
    private TAction moveAction = null;

    /**
     * Public constructor.
     *
     * @param parent parent widget
     * @param title widget title
     * @param x column relative to parent
     * @param y row relative to parent
     */
    public TForegroundPicker(final TWidget parent, final String title, final int x, final int y) {
        this(parent, title, x, y, null, null);
    }

    /**
     * Public constructor.
     *
     * @param parent parent widget
     * @param title widget title
     * @param x column relative to parent
     * @param y row relative to parent
     * @param enterAction action to perform when an item is selected
     * @param moveAction action to perform when the user navigates to a new item with arrow/page keys
     */
    public TForegroundPicker(final TWidget parent, final String title, final int x, final int y,
                             final TAction enterAction, final TAction moveAction) {

        super(parent, x, y, 14, 6);
        this.title = title;
        this.enterAction = enterAction;
        this.moveAction = moveAction;
    }

    /**
     * Get the X grid coordinate for this color.
     *
     * @param color the Color value
     * @return the X coordinate
     */
    private int getXColorPosition(final Color color) {
        if (color.equals(Color.BLACK)) {
            return 2;
        } else if (color.equals(Color.BLUE)) {
            return 5;
        } else if (color.equals(Color.GREEN)) {
            return 8;
        } else if (color.equals(Color.CYAN)) {
            return 11;
        } else if (color.equals(Color.RED)) {
            return 2;
        } else if (color.equals(Color.MAGENTA)) {
            return 5;
        } else if (color.equals(Color.YELLOW)) {
            return 8;
        } else if (color.equals(Color.WHITE)) {
            return 11;
        }
        throw new IllegalArgumentException("Invalid color: " + color);
    }

    /**
     * Get the Y grid coordinate for this color.
     *
     * @param color the Color value
     * @param bold if true use bold color
     * @return the Y coordinate
     */
    private int getYColorPosition(final Color color, final boolean bold) {
        int dotY = 1;
        if (color.equals(Color.RED)) {
            dotY = 2;
        } else if (color.equals(Color.MAGENTA)) {
            dotY = 2;
        } else if (color.equals(Color.YELLOW)) {
            dotY = 2;
        } else if (color.equals(Color.WHITE)) {
            dotY = 2;
        }
        if (bold) {
            dotY += 2;
        }
        return dotY;
    }

    /**
     * Get the bold value based on Y grid coordinate.
     *
     * @param dotY the Y coordinate
     * @return the bold value
     */
    private boolean getBoldFromPosition(final int dotY) {
        if (dotY > 2) {
            return true;
        }
        return false;
    }

    /**
     * Get the color based on (X, Y) grid coordinate.
     *
     * @param dotX the X coordinate
     * @param dotY the Y coordinate
     * @return the Color value
     */
    private Color getColorFromPosition(final int dotX, final int dotY) {
        int y = dotY;
        if (y > 2) {
            y -= 2;
        }
        if ((1 <= dotX) && (dotX <= 3) && (y == 1)) {
            return Color.BLACK;
        }
        if ((4 <= dotX) && (dotX <= 6) && (y == 1)) {
            return Color.BLUE;
        }
        if ((7 <= dotX) && (dotX <= 9) && (y == 1)) {
            return Color.GREEN;
        }
        if ((10 <= dotX) && (dotX <= 12) && (y == 1)) {
            return Color.CYAN;
        }
        if ((1 <= dotX) && (dotX <= 3) && (y == 2)) {
            return Color.RED;
        }
        if ((4 <= dotX) && (dotX <= 6) && (y == 2)) {
            return Color.MAGENTA;
        }
        if ((7 <= dotX) && (dotX <= 9) && (y == 2)) {
            return Color.YELLOW;
        }
        if ((10 <= dotX) && (dotX <= 12) && (y == 2)) {
            return Color.WHITE;
        }

        throw new IllegalArgumentException("Invalid coordinates: "
                + dotX + ", " + dotY);
    }

    /**
     * Draw the foreground colors grid.
     */
    @Override
    public void draw() {
        CellAttributes border = getWindow().getBorder();
        CellAttributes background = getWindow().getBackground();
        CellAttributes attr = new CellAttributes();

        getScreen().drawBox(0, 0, getWidth(), getHeight(), border,
                background, 1, false);

        attr.setTo(getParent().getWindow().getBackground());
        if (isActive()) {
            attr.setForeColor(getTheme().getColor("tlabel").getForeColor());
            attr.setBold(getTheme().getColor("tlabel").isBold());
        }
        getScreen().putStringXY(1, 0, title, attr);

        // Have to draw the colors manually because the int value matches
        // SGR, not CGA.
        attr.reset();
        attr.setForeColor(Color.BLACK);
        getScreen().putStringXY(1, 1, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.BLUE);
        getScreen().putStringXY(4, 1, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.GREEN);
        getScreen().putStringXY(7, 1, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.CYAN);
        getScreen().putStringXY(10, 1, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.RED);
        getScreen().putStringXY(1, 2, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.MAGENTA);
        getScreen().putStringXY(4, 2, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.YELLOW);
        getScreen().putStringXY(7, 2, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.WHITE);
        getScreen().putStringXY(10, 2, "\u2588\u2588\u2588", attr);

        attr.setBold(true);
        attr.setForeColor(Color.BLACK);
        getScreen().putStringXY(1, 3, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.BLUE);
        getScreen().putStringXY(4, 3, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.GREEN);
        getScreen().putStringXY(7, 3, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.CYAN);
        getScreen().putStringXY(10, 3, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.RED);
        getScreen().putStringXY(1, 4, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.MAGENTA);
        getScreen().putStringXY(4, 4, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.YELLOW);
        getScreen().putStringXY(7, 4, "\u2588\u2588\u2588", attr);
        attr.setForeColor(Color.WHITE);
        getScreen().putStringXY(10, 4, "\u2588\u2588\u2588", attr);

        // Draw the dot
        int dotX = getXColorPosition(color);
        int dotY = getYColorPosition(color, bold);
        if (color.equals(Color.BLACK) && !bold) {
            // Use white-on-black for black.  All other colors use
            // black-on-whatever.
            attr.reset();
            getScreen().putCharXY(dotX, dotY, GraphicsChars.DOT,
                    attr);
        } else {
            attr.setForeColor(color);
            attr.setBold(bold);
            getScreen().putCharXY(dotX, dotY, GraphicsChars.DOT_INVERTED, attr);
        }
    }

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
     * Handle keystrokes.
     *
     * @param keypress keystroke event
     */
    @Override
    public void onKeypress(final TKeypressEvent keypress) {
        if (keypress.equals(kbRight)) {
            int dotX = getXColorPosition(color);
            int dotY = getYColorPosition(color, bold);
            if (dotX < 10) {
                dotX += 3;
            }
            color = getColorFromPosition(dotX, dotY);
            dispatchMove();
        } else if (keypress.equals(kbLeft)) {
            int dotX = getXColorPosition(color);
            int dotY = getYColorPosition(color, bold);
            if (dotX > 3) {
                dotX -= 3;
            }
            color = getColorFromPosition(dotX, dotY);
            dispatchMove();
        } else if (keypress.equals(kbUp)) {
            int dotX = getXColorPosition(color);
            int dotY = getYColorPosition(color, bold);
            if (dotY > 1) {
                dotY--;
            }
            color = getColorFromPosition(dotX, dotY);
            bold = getBoldFromPosition(dotY);
            dispatchMove();
        } else if (keypress.equals(kbDown)) {
            int dotX = getXColorPosition(color);
            int dotY = getYColorPosition(color, bold);
            if (dotY < 4) {
                dotY++;
            }
            color = getColorFromPosition(dotX, dotY);
            bold = getBoldFromPosition(dotY);
            dispatchMove();
        } else if (keypress.equals(kbEnter)) {
            dispatchEnter();
        } else {
            // Pass to my parent
            super.onKeypress(keypress);
            return;
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
            // Do this like kbUp
            int dotX = getXColorPosition(color);
            int dotY = getYColorPosition(color, bold);
            if (dotY > 1) {
                dotY--;
            }
            color = getColorFromPosition(dotX, dotY);
            bold = getBoldFromPosition(dotY);
            dispatchMove();
        } else if (mouse.isMouseWheelDown()) {
            // Do this like kbDown
            int dotX = getXColorPosition(color);
            int dotY = getYColorPosition(color, bold);
            if (dotY < 4) {
                dotY++;
            }
            color = getColorFromPosition(dotX, dotY);
            bold = getBoldFromPosition(dotY);
            dispatchMove();
        } else {
            // Let parent class handle it.
            super.onMouseDown(mouse);
            return;
        }
    }

    /**
     * Handle mouse up events.
     *
     * @param mouse mouse button up event
     */
    @Override
    public void onMouseUp(final TMouseEvent mouse) {
        if ((mouse.getX() > 0)
                && (mouse.getX() < getWidth() - 1)
                && (mouse.getY() > 0)
                && (mouse.getY() < getHeight() - 1)
                ) {
            color = getColorFromPosition(mouse.getX(), mouse.getY());
            bold = getBoldFromPosition(mouse.getY());
            dispatchMove();
        } else {
            // Let parent class handle it.
            super.onMouseUp(mouse);
            return;
        }

    }

    /**
     * Handle mouse double click.
     *
     * @param mouse mouse double click event
     */
    @Override
    public void onMouseDoubleClick(final TMouseEvent mouse) {
        if ((mouse.getX() > 0)
                && (mouse.getX() < getWidth() - 1)
                && (mouse.getY() > 0)
                && (mouse.getY() < getHeight() - 1)
                ) {
            dispatchEnter();
            return;
        }

        // Pass to children
        super.onMouseDoubleClick(mouse);
    }

}
